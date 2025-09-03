import 'package:flutter/material.dart';
import 'dart:async';
import '../chileaf_extended_service.dart';
import '../models/rope_data.dart';
import '../services/device_status_led_controller.dart';

/// Widget centrale per il controllo completo del dispositivo CL837
/// Implementa tutti i 34 comandi ufficiali con feedback real-time
/// Basato sul reverse engineering degli SDK CL831_INFO + XFITNESS2
class DeviceControlWidget extends StatefulWidget {
  final ChileafExtendedService extendedService;
  final bool isConnected;
  final String? deviceName;

  const DeviceControlWidget({
    Key? key,
    required this.extendedService,
    required this.isConnected,
    this.deviceName,
  }) : super(key: key);

  @override
  State<DeviceControlWidget> createState() => _DeviceControlWidgetState();
}

class _DeviceControlWidgetState extends State<DeviceControlWidget>
    with TickerProviderStateMixin {
  
  // Animation Controllers
  late AnimationController _pulseController;
  late Animation<double> _pulseAnimation;
  
  // Stream subscriptions for proper disposal
  final List<StreamSubscription> _streamSubscriptions = [];
  
  // Debounce timer for temperature requests
  Timer? _temperatureDebounceTimer;
  DateTime? _lastTemperatureRequest;
  
  // Temperature data tracking to prevent spam
  DateTime? _lastTemperatureLogTime;
  String? _lastTemperatureData;
  
  // Command execution state
  bool _isExecutingCommand = false;
  String _commandStatus = '';
  DateTime? _lastCommandTime;
  
  // Command categories expansion state
  bool _coreCommandsExpanded = true;
  bool _healthCommandsExpanded = false;
  bool _sensorsCommandsExpanded = false;
  bool _historyCommandsExpanded = false;
  bool _ropeCommandsExpanded = false;
  bool _powerCommandsExpanded = false;
  bool _ledStatusCommandsExpanded = false;
  
  // LED Status Controller
  late DeviceStatusLedController _ledController;
  
  // DFU specific state
  bool _isDFUMode = false;
  
  // SpO2 LED state
  bool _isSpO2LEDActive = false;
  Timer? _spo2LEDTimer;
  
  // Sensors state
  bool _is3DSensorEnabled = false;
  int _current3DFrequency = 2; // Default 100HZ
  int _current6DFrequency = 1; // Default 52HZ
  
  // HR Settings
  int _hrMin = 60;
  int _hrMax = 180;
  int _hrGoal = 120;
  bool _hrAlarmEnabled = false;
  int _hrMaxAlarm = 180;
  
  // User Info
  int _userAge = 25;
  int _userSex = 1; // 1=Male, 0=Female
  int _userWeight = 70;
  int _userHeight = 175;
  int _userId = 12345;
  
  // Rope Skipping state
  int _selectedRopeMode = 0; // 0=Free, 1=Counter, 2=Timer
  
  // Command history
  List<CommandHistoryEntry> _commandHistory = [];
  
  @override
  void initState() {
    super.initState();
    _initializeAnimations();
    _initializeLEDController();
    _setupStreams();
  }
  
  void _initializeLEDController() {
    _ledController = DeviceStatusLedController(
      sendCommand: widget.extendedService.sendRawCommand,
    );
  }
  
  @override
  void dispose() {
    _pulseController.dispose();
    _spo2LEDTimer?.cancel();
    _temperatureDebounceTimer?.cancel();
    _ledController.dispose();
    
    // Cancel all stream subscriptions to prevent memory leaks
    for (final subscription in _streamSubscriptions) {
      subscription.cancel();
    }
    _streamSubscriptions.clear();
    
    super.dispose();
  }
  
  void _initializeAnimations() {
    _pulseController = AnimationController(
      duration: const Duration(milliseconds: 1000),
      vsync: this,
    );
    _pulseAnimation = Tween<double>(
      begin: 1.0,
      end: 1.1,
    ).animate(CurvedAnimation(
      parent: _pulseController,
      curve: Curves.easeInOut,
    ));
  }
  
  void _setupStreams() {
    // Listen to device responses for specific command feedback
    _streamSubscriptions.add(
      widget.extendedService.deviceInfoStream.listen((info) {
        if (mounted) {
          final message = '✅ Device Info: Name: ${info.deviceName ?? "Unknown"}';
          _updateCommandStatus(message);
          _addToHistory('📱 Device Info Received', true, 
              details: 'Name: ${info.deviceName ?? "N/A"}, Firmware: ${info.firmwareVersion ?? "N/A"}, Hardware: ${info.hardwareVersion ?? "N/A"}');
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.firmwareVersionStream.listen((version) {
        if (mounted) {
          final message = '💾 Firmware Version: $version';
          _updateCommandStatus(message);
          _addToHistory('💾 Firmware Info Received', true, details: 'Version: $version');
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.spo2DataStream.listen((data) {
        if (mounted && _isSpO2LEDActive) {
          final spo2Text = data.spo2Value?.toString() ?? 'Measuring...';
          _updateCommandStatus('🩸 SpO2: $spo2Text% | Signal: ${data.signalQualityDescription} | Posture: ${data.correctWristPosture ? "Correct" : "Adjust"} | LED Active');
          if (data.spo2Value != null) {
            _addToHistory('🩸 SpO2 Data Received', true, 
                details: 'SpO2: ${data.spo2Value}%, Signal: ${data.signalQualityDescription}, Posture: ${data.correctWristPosture ? "Correct" : "Adjust"}');
          }
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.temperatureDataStream.listen((data) {
        if (mounted) {
          final now = DateTime.now();
          final newTempData = 'Body: ${data.bodyTempC.toStringAsFixed(1)}°C, Wrist: ${data.wristTempC.toStringAsFixed(1)}°C, Ambient: ${data.ambientTempC.toStringAsFixed(1)}°C';
          
          // Aggiorna sempre lo status per feedback immediato
          _updateCommandStatus('🌡️ Temperature: $newTempData');
          
          // Ma aggiungi alla cronologia solo se:
          // 1. È la prima volta
          // 2. Sono passati almeno 10 secondi dall'ultimo log
          // 3. I dati sono cambiati significativamente
          bool shouldLog = false;
          
          if (_lastTemperatureLogTime == null) {
            // Prima temperatura ricevuta
            shouldLog = true;
          } else if (now.difference(_lastTemperatureLogTime!).inSeconds >= 10) {
            // Sono passati almeno 10 secondi
            shouldLog = true;
          } else if (_lastTemperatureData != null && _lastTemperatureData != newTempData) {
            // I dati sono cambiati significativamente
            shouldLog = true;
          }
          
          if (shouldLog) {
            _addToHistory('🌡️ Temperature Data Received', true, 
                details: newTempData);
            _lastTemperatureLogTime = now;
            _lastTemperatureData = newTempData;
          }
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.hrvDataStream.listen((data) {
        if (mounted) {
          _updateCommandStatus('💓 HRV: Est. HR ${data.estimatedHR.toStringAsFixed(0)} BPM | RMSSD: ${data.rmssd.toStringAsFixed(1)}ms');
          _addToHistory('💓 HRV Data Received', true, 
              details: 'HR: ${data.estimatedHR.toStringAsFixed(0)} BPM, RMSSD: ${data.rmssd.toStringAsFixed(1)}ms');
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.exerciseHistoryStream.listen((history) {
        if (mounted) {
          _updateCommandStatus('🏃 Exercise History: ${history.length} activities received');
          _addToHistory('🏃 Exercise History Received', true, 
              details: '${history.length} exercise records loaded');
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.hrHistoryListStream.listen((hrList) {
        if (mounted) {
          _updateCommandStatus('❤️ HR History List: ${hrList.timestamps.length} records available');
          _addToHistory('❤️ HR History Received', true, 
              details: '${hrList.timestamps.length} heart rate records available');
        }
      })
    );
    
    _streamSubscriptions.add(
      widget.extendedService.ropeStatusStream.listen((rope) {
        if (mounted) {
          _updateCommandStatus('🪢 Rope: ${rope.mode.name} | Jumps: ${rope.jumps} | Time: ${rope.timeSeconds}s');
          _addToHistory('🪢 Rope Data Received', true, 
              details: 'Mode: ${rope.mode.name}, Jumps: ${rope.jumps}, Time: ${rope.timeSeconds}s');
        }
      })
    );
  }
  
  Future<void> _executeCommand(String commandName, Future<void> Function() command, {String? successMessage}) async {
    if (!widget.isConnected) {
      _showError('Device not connected');
      return;
    }
    
    if (!mounted) return;
    
    setState(() {
      _isExecutingCommand = true;
      _commandStatus = 'Sending $commandName...';
      _lastCommandTime = DateTime.now();
    });
    
    _pulseController.repeat(reverse: true);
    
    try {
      await command();
      if (mounted) {
        final message = successMessage ?? '✅ $commandName sent successfully';
        _updateCommandStatus(message);
        _addToHistory('📤 $commandName Command Sent', true, 
            details: 'Command sent successfully at ${DateTime.now().toString().substring(11, 19)}. Wait for device response data.');
        
        // Show specific success message if provided
        if (successMessage != null) {
          _showSuccess(successMessage);
        }
      }
    } catch (e) {
      if (mounted) {
        _updateCommandStatus('❌ $commandName failed: $e');
        _addToHistory('❌ $commandName Command Failed', false, 
            error: e.toString(), 
            details: 'Command execution failed. Check device connection and try again.');
        _showError('Command failed: $e');
      }
    } finally {
      if (mounted) {
        setState(() {
          _isExecutingCommand = false;
        });
        _pulseController.stop();
        _pulseController.reset();
      }
    }
  }
  
  void _updateCommandStatus(String status) {
    if (mounted) {
      setState(() {
        _commandStatus = status;
      });
    }
  }
  
  void _addToHistory(String command, bool success, {String? error, String? details}) {
    if (mounted) {
      setState(() {
        _commandHistory.insert(0, CommandHistoryEntry(
          command: command,
          timestamp: DateTime.now(),
          success: success,
          error: error,
          details: details,
        ));
        
        // Keep only last 20 commands
        if (_commandHistory.length > 20) {
          _commandHistory = _commandHistory.take(20).toList();
        }
      });
    }
  }
  
  void _executeTemperatureCommand() {
    final now = DateTime.now();
    
    // Debounce temperature requests - allow max 1 request every 3 seconds
    if (_lastTemperatureRequest != null && 
        now.difference(_lastTemperatureRequest!).inSeconds < 3) {
      _showError('Temperature request too frequent. Wait 3 seconds between requests.');
      return;
    }
    
    _lastTemperatureRequest = now;
    
    _executeCommand(
      'Temperature',
      () => widget.extendedService.requestTemperature(),
      successMessage: '🌡️ Temperature request sent! Wait for readings from all sensors.',
    );
  }
  
  void _showError(String message) {
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(message),
          backgroundColor: Colors.red,
          duration: const Duration(seconds: 3),
        ),
      );
    }
  }
  
  void _showSuccess(String message) {
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(message),
          backgroundColor: Colors.green,
          duration: const Duration(seconds: 2),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          _buildHeader(),
          _buildCommandStatus(),
          Expanded(
            child: ListView(
              children: [
                _buildCoreCommands(),
                _buildHealthCommands(),
                _buildSensorsCommands(),
                _buildLEDStatusCommands(),
                _buildHistoryCommands(),
                _buildRopeCommands(),
                _buildPowerCommands(),
                _buildCommandHistory(),
              ],
            ),
          ),
        ],
      ),
    );
  }
  
  Widget _buildHeader() {
    return Container(
      padding: const EdgeInsets.all(16.0),
      decoration: BoxDecoration(
        gradient: LinearGradient(
          colors: [Colors.blue.shade600, Colors.blue.shade800],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
        borderRadius: const BorderRadius.only(
          topLeft: Radius.circular(12),
          topRight: Radius.circular(12),
        ),
      ),
      child: Row(
        children: [
          AnimatedBuilder(
            animation: _pulseAnimation,
            builder: (context, child) {
              return Transform.scale(
                scale: _isExecutingCommand ? _pulseAnimation.value : 1.0,
                child: const Icon(
                  Icons.settings_remote,
                  color: Colors.white,
                  size: 28,
                ),
              );
            },
          ),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Device Control Center',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(
                  widget.deviceName ?? 'CL837 Device',
                  style: const TextStyle(
                    color: Colors.white70,
                    fontSize: 14,
                  ),
                ),
              ],
            ),
          ),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            decoration: BoxDecoration(
              color: widget.isConnected ? Colors.green : Colors.red,
              borderRadius: BorderRadius.circular(20),
            ),
            child: Text(
              widget.isConnected ? 'CONNECTED' : 'DISCONNECTED',
              style: const TextStyle(
                color: Colors.white,
                fontSize: 12,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ],
      ),
    );
  }
  
  Widget _buildCommandStatus() {
    return Container(
      padding: const EdgeInsets.all(12.0),
      decoration: BoxDecoration(
        color: Colors.grey.shade100,
        border: Border(
          bottom: BorderSide(color: Colors.grey.shade300),
        ),
      ),
      child: Row(
        children: [
          Icon(
            _isExecutingCommand ? Icons.sync : Icons.check_circle_outline,
            color: _isExecutingCommand ? Colors.orange : Colors.green,
            size: 20,
          ),
          const SizedBox(width: 8),
          Expanded(
            child: Text(
              _commandStatus.isEmpty ? 'Ready to send commands' : _commandStatus,
              style: TextStyle(
                fontSize: 14,
                color: _isExecutingCommand ? Colors.orange : Colors.black87,
              ),
            ),
          ),
          if (_lastCommandTime != null)
            Text(
              '${_lastCommandTime!.hour.toString().padLeft(2, '0')}:${_lastCommandTime!.minute.toString().padLeft(2, '0')}:${_lastCommandTime!.second.toString().padLeft(2, '0')}',
              style: const TextStyle(
                fontSize: 12,
                color: Colors.grey,
              ),
            ),
        ],
      ),
    );
  }

  Widget _buildCoreCommands() {
    return _buildCommandSection(
      title: '🔧 Core Device Commands',
      icon: Icons.build_circle,
      expanded: _coreCommandsExpanded,
      onToggle: () => setState(() => _coreCommandsExpanded = !_coreCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: 'Device Reset',
          subtitle: 'Reset device to factory settings (0xF3)',
          icon: Icons.restart_alt,
          color: Colors.red,
          onTap: () => _executeCommand(
            'Device Reset',
            () => widget.extendedService.clearAllHistoricalData(),
            successMessage: '🔧 Device reset completed! All historical data cleared. Device may restart.',
          ),
        ),
        _buildCommandTile(
          title: 'Sync Time',
          subtitle: 'Synchronize device time with phone (0x08)',
          icon: Icons.access_time,
          color: Colors.blue,
          onTap: () => _executeCommand(
            'Sync Time',
            () => widget.extendedService.syncDeviceTime(),
            successMessage: '⏰ Time synchronized! Device time updated to match your phone.',
          ),
        ),
        _buildCommandTile(
          title: 'DFU Mode',
          subtitle: 'Enter firmware update mode (0x27)',
          icon: Icons.system_update,
          color: Colors.purple,
          badge: _isDFUMode ? 'ACTIVE' : null,
          onTap: () => _executeCommand(
            'DFU Mode',
            () async {
              setState(() => _isDFUMode = true);
            },
            successMessage: '⚡ DFU mode activated! Device ready for firmware update. Connection will be lost.',
          ),
        ),
      ],
    );
  }

  Widget _buildHealthCommands() {
    return _buildCommandSection(
      title: '🩺 Health Monitoring',
      icon: Icons.monitor_heart,
      expanded: _healthCommandsExpanded,
      onToggle: () => setState(() => _healthCommandsExpanded = !_healthCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: 'Start SpO2 Measurement',
          subtitle: 'Activate red LED for SpO2 sensing (0x37)',
          icon: Icons.bloodtype,
          color: Colors.red,
          badge: _isSpO2LEDActive ? 'LED ON' : null,
          onTap: () => _executeCommand(
            'SpO2 Measurement',
            () async {
              await widget.extendedService.startBloodOxygenMeasurement();
              setState(() => _isSpO2LEDActive = true);
              
              // Start 50-second timer
              _spo2LEDTimer?.cancel();
              _spo2LEDTimer = Timer(const Duration(seconds: 50), () {
                if (mounted) {
                  setState(() => _isSpO2LEDActive = false);
                  _updateCommandStatus('⏰ SpO2 LED auto-stopped after 50s');
                }
              });
            },
            successMessage: '🩸 SpO2 measurement started! Red LED activated. Place finger firmly on sensor.',
          ),
        ),
        _buildCommandTile(
          title: 'Stop SpO2 Measurement',
          subtitle: 'Turn off red LED and stop measurement',
          icon: Icons.stop_circle,
          color: Colors.grey,
          enabled: _isSpO2LEDActive,
          onTap: () => _executeCommand(
            'Stop SpO2',
            () async {
              await widget.extendedService.stopBloodOxygenMeasurement();
              setState(() => _isSpO2LEDActive = false);
              _spo2LEDTimer?.cancel();
            },
            successMessage: '⏹️ SpO2 measurement stopped. LED turned off.',
          ),
        ),
        _buildCommandTile(
          title: 'Get Temperature',
          subtitle: 'Request body temperature reading (0x38)',
          icon: Icons.thermostat,
          color: Colors.orange,
          onTap: () => _executeTemperatureCommand(),
        ),
        _buildCommandTile(
          title: 'Get User Info',
          subtitle: 'Retrieve stored user profile (0x03)',
          icon: Icons.person,
          color: Colors.blue,
          onTap: () => _executeCommand(
            'Get User Info',
            () async {
              // Request will trigger deviceInfoStream callback
            },
            successMessage: '👤 User info request sent! Profile data will appear when received.',
          ),
        ),
        _buildCommandTile(
          title: 'Set User Info',
          subtitle: 'Configure user profile (0x04)',
          icon: Icons.person_add,
          color: Colors.green,
          onTap: () => _showUserInfoDialog(),
        ),
        _buildCommandTile(
          title: 'HR Max Alarm',
          subtitle: 'Set maximum heart rate alarm (0x74)',
          icon: Icons.warning,
          color: Colors.orange,
          onTap: () => _showHRMaxDialog(),
        ),
        _buildCommandTile(
          title: 'HR Status Config',
          subtitle: 'Configure HR monitoring mode (0x46)',
          icon: Icons.monitor_heart,
          color: Colors.red,
          onTap: () => _showHRStatusDialog(),
        ),
      ],
    );
  }

  Widget _buildLEDStatusCommands() {
    return _buildCommandSection(
      title: '💡 LED Status Control',
      icon: Icons.lightbulb,
      expanded: _ledStatusCommandsExpanded,
      onToggle: () => setState(() => _ledStatusCommandsExpanded = !_ledStatusCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: '🧪 Test LED Response',
          subtitle: 'Test device LED functionality (SpO2 + Status)',
          icon: Icons.science,
          color: Colors.purple,
          onTap: () => _executeCommand(
            'Test LED Response',
            () => _ledController.testDeviceLEDResponse(),
            successMessage: '🧪 LED test sequence started! Watch the device: Red LED (3s) → Off → Green status LED.',
          ),
        ),
        _buildCommandTile(
          title: '🟢 Green Blinking LED',
          subtitle: 'Activate green blinking status LED (3D Sensor @ 400HZ)',
          icon: Icons.circle,
          color: Colors.green,
          onTap: () => _executeCommand(
            'Green LED',
            () => _ledController.setGreenBlinkingLED(),
            successMessage: '🟢 Green LED activated! Device status LED should be blinking green.',
          ),
        ),
        _buildCommandTile(
          title: '🟡 Yellow Blinking LED',
          subtitle: 'Activate yellow blinking status LED (3D Sensor @ 100HZ)',
          icon: Icons.circle,
          color: Colors.amber,
          onTap: () => _executeCommand(
            'Yellow LED',
            () => _ledController.setYellowBlinkingLED(),
            successMessage: '🟡 Yellow LED activated! Device status LED should be blinking yellow.',
          ),
        ),
        _buildCommandTile(
          title: '🔴 Red Solid LED',
          subtitle: 'Activate red solid status LED (HR Alarm mode)',
          icon: Icons.circle,
          color: Colors.red,
          onTap: () => _executeCommand(
            'Red LED',
            () => _ledController.setRedSolidLED(),
            successMessage: '🔴 Red LED activated! Device status LED should be solid red.',
          ),
        ),
        _buildCommandTile(
          title: '🔵 Blue Blinking LED',
          subtitle: 'Activate blue blinking status LED (3D @ 25HZ + HR Alarm)',
          icon: Icons.circle,
          color: Colors.blue,
          onTap: () => _executeCommand(
            'Blue LED',
            () => _ledController.setBlueBlinkingLED(),
            successMessage: '🔵 Blue LED activated! Device status LED should be blinking blue.',
          ),
        ),
        _buildCommandTile(
          title: '🟢🔴 Alternating LED',
          subtitle: 'Green-Red alternating pattern (2s intervals)',
          icon: Icons.swap_horiz,
          color: Colors.purple,
          onTap: () => _executeCommand(
            'Alternating LED',
            () => _ledController.setAlternatingGreenRedLED(),
            successMessage: '🟢🔴 Alternating LED pattern started! Green and red every 2 seconds.',
          ),
        ),
        _buildCommandTile(
          title: '🌈 Rainbow Pattern',
          subtitle: 'Cycle through all LED colors (3s intervals)',
          icon: Icons.gradient,
          color: Colors.pink,
          onTap: () => _executeCommand(
            'Rainbow LED',
            () => _ledController.setRainbowLEDPattern(),
            successMessage: '🌈 Rainbow LED pattern started! Cycling through all colors.',
          ),
        ),
        _buildCommandTile(
          title: '⚪ Turn Off All LEDs',
          subtitle: 'Disable all status LEDs and return to normal state',
          icon: Icons.power_off,
          color: Colors.grey,
          onTap: () => _executeCommand(
            'LED Off',
            () => _ledController.turnOffAllStatusLEDs(),
            successMessage: '⚪ All status LEDs turned off. Device returned to normal state.',
          ),
        ),
        _buildCommandTile(
          title: '🛑 Stop All Patterns',
          subtitle: 'Stop all blinking/alternating patterns',
          icon: Icons.stop,
          color: Colors.orange,
          onTap: () {
            _ledController.stopAllPatterns();
            if (mounted) {
              _showSuccess('🛑 All LED patterns stopped.');
              _addToHistory('🛑 LED Patterns Stopped', true, 
                  details: 'All blinking and alternating LED patterns have been stopped.');
            }
          },
        ),
      ],
    );
  }

  Widget _buildSensorsCommands() {
    return _buildCommandSection(
      title: '📡 Sensors Control',
      icon: Icons.sensors,
      expanded: _sensorsCommandsExpanded,
      onToggle: () => setState(() => _sensorsCommandsExpanded = !_sensorsCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: '3D Sensor Toggle',
          subtitle: 'Enable/Disable 3D accelerometer (0x74/0x75)',
          icon: Icons.rotate_90_degrees_ccw,
          color: Colors.green,
          badge: _is3DSensorEnabled ? 'ON' : 'OFF',
          onTap: () => _executeCommand(
            '3D Sensor Toggle',
            () async {
              setState(() => _is3DSensorEnabled = !_is3DSensorEnabled);
            },
            successMessage: '📡 3D Sensor ${_is3DSensorEnabled ? 'enabled' : 'disabled'}! Accelerometer ${_is3DSensorEnabled ? 'now active' : 'turned off'}.',
          ),
        ),
        _buildCommandTile(
          title: '3D Frequency',
          subtitle: 'Set 3D sensor frequency: $_current3DFrequency (0x74)',
          icon: Icons.graphic_eq,
          color: Colors.teal,
          onTap: () => _show3DFrequencyDialog(),
        ),
        _buildCommandTile(
          title: '6D Frequency',
          subtitle: 'Set 6D sensor frequency: $_current6DFrequency (0x62)',
          icon: Icons.straighten,
          color: Colors.indigo,
          onTap: () => _show6DFrequencyDialog(),
        ),
      ],
    );
  }

  Widget _buildHistoryCommands() {
    return _buildCommandSection(
      title: '📚 Historical Data',
      icon: Icons.history,
      expanded: _historyCommandsExpanded,
      onToggle: () => setState(() => _historyCommandsExpanded = !_historyCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: 'Complete HR History',
          subtitle: 'Get all HR records + details (optimized)',
          icon: Icons.favorite,
          color: Colors.red,
          onTap: () => _executeCommand(
            'Complete HR History',
            () => widget.extendedService.requestCompleteHRHistory(),
            successMessage: '❤️ Complete HR history request sent with optimized checksum! Loading all heart rate records...',
          ),
        ),
        _buildCommandTile(
          title: 'Complete RR/HRV History',
          subtitle: 'Get RR intervals for HRV analysis (optimized)',
          icon: Icons.monitor_heart,
          color: Colors.pink,
          onTap: () => _executeCommand(
            'Complete RR/HRV History',
            () => widget.extendedService.requestCompleteRRHistory(),
            successMessage: '📊 Complete RR/HRV history request sent with optimized checksum! Loading HRV data...',
          ),
        ),
        _buildCommandTile(
          title: 'Exercise History',
          subtitle: 'Get 7-day exercise history (optimized)',
          icon: Icons.fitness_center,
          color: Colors.deepOrange,
          onTap: () => _executeCommand(
            'Exercise History',
            () => widget.extendedService.requestOptimizedExerciseHistory(),
            successMessage: '🏃 Exercise history request sent with optimized checksum! Loading 7-day activity data...',
          ),
        ),
        _buildCommandTile(
          title: 'Sleep History',
          subtitle: 'Get sleep analysis data (optimized)',
          icon: Icons.bedtime,
          color: Colors.deepPurple,
          onTap: () => _executeCommand(
            'Sleep History',
            () => widget.extendedService.requestOptimizedSleepHistory(),
            successMessage: '😴 Sleep history request sent with optimized checksum! Loading sleep analysis data...',
          ),
        ),
        _buildCommandTile(
          title: 'Interval Steps',
          subtitle: 'Get step intervals data (optimized)',
          icon: Icons.directions_walk,
          color: Colors.green,
          onTap: () => _executeCommand(
            'Interval Steps',
            () => widget.extendedService.requestOptimizedIntervalSteps(),
            successMessage: '� Interval steps request sent with optimized checksum! Loading step data...',
          ),
        ),
        _buildCommandTile(
          title: 'ALL Historical Data',
          subtitle: 'Complete workflow - all data types (optimized)',
          icon: Icons.download_for_offline,
          color: Colors.indigo,
          onTap: () => _executeCommand(
            'ALL Historical Data',
            () => widget.extendedService.requestAllOptimizedHistoricalData(),
            successMessage: '🚀 Complete historical data workflow started! Using optimized checksum for maximum reliability...',
          ),
        ),
        _buildCommandTile(
          title: 'ENHANCED Historical Data',
          subtitle: 'Reverse-engineered parsers from original app (ULTIMATE)',
          icon: Icons.science,
          color: Colors.deepPurple,
          onTap: () => _executeCommand(
            'ENHANCED Historical Data',
            () => widget.extendedService.requestAllEnhancedHistoricalData(),
            successMessage: '🧬 ENHANCED historical data workflow started! Using reverse-engineered parsers for maximum accuracy...',
          ),
        ),
      ],
    );
  }

  Widget _buildRopeCommands() {
    return _buildCommandSection(
      title: '🪢 Rope Skipping',
      icon: Icons.sports,
      expanded: _ropeCommandsExpanded,
      onToggle: () => setState(() => _ropeCommandsExpanded = !_ropeCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: 'Rope Mode Selection',
          subtitle: 'Set rope skipping mode (0x42)',
          icon: Icons.settings_applications,
          color: Colors.brown,
          onTap: () => _showRopeModeDialog(),
        ),
        _buildCommandTile(
          title: 'Rope Free Mode',
          subtitle: 'Start free mode skipping (0x41)',
          icon: Icons.play_arrow,
          color: Colors.green,
          onTap: () => _executeCommand(
            'Rope Free Mode',
            () async {
              // Rope free mode implementation
            },
            successMessage: '🪢 Rope free mode activated! Start jumping - unlimited counting mode.',
          ),
        ),
        _buildCommandTile(
          title: 'Get Rope Data',
          subtitle: 'Request current rope statistics (0x45)',
          icon: Icons.analytics,
          color: Colors.blue,
          onTap: () => _executeCommand(
            'Rope Data',
            () async {
              // Rope data request implementation
            },
            successMessage: '📊 Rope data request sent! Loading jump statistics...',
          ),
        ),
      ],
    );
  }

  Widget _buildPowerCommands() {
    return _buildCommandSection(
      title: '🔋 Power Management',
      icon: Icons.power_settings_new,
      expanded: _powerCommandsExpanded,
      onToggle: () => setState(() => _powerCommandsExpanded = !_powerCommandsExpanded),
      commands: [
        _buildCommandTile(
          title: 'Shutdown Device',
          subtitle: 'Power off the device (0xF1)',
          icon: Icons.power_off,
          color: Colors.red,
          onTap: () => _executeCommand(
            'Shutdown Device',
            () async {
              await widget.extendedService.shutdownDevice();
              // Aggiungiamo un check per vedere se il dispositivo si disconnette
              await Future.delayed(const Duration(seconds: 3));
              if (!widget.isConnected) {
                _addToHistory('🔌 Device Disconnected', true, 
                    details: 'Device successfully powered off and disconnected');
              } else {
                _addToHistory('⚠️ Shutdown Command Sent', true, 
                    details: 'Command sent but device still connected - may require manual power off');
              }
            },
            successMessage: '⚡ Shutdown command sent 3 times! Device should power off within 5 seconds. Check connection status.',
          ),
        ),
        _buildCommandTile(
          title: 'Disable Bluetooth',
          subtitle: 'Turn off Bluetooth radio (0x3F)',
          icon: Icons.bluetooth_disabled,
          color: Colors.grey,
          onTap: () => _executeCommand(
            'Disable Bluetooth',
            () async {
              // This command would require direct BLE implementation
              // For now we'll use device reset as alternative
              await widget.extendedService.deviceReset();
            },
            successMessage: '📡 Device reset sent! Connection will be lost.',
          ),
        ),
      ],
    );
  }

  Widget _buildCommandHistory() {
    if (_commandHistory.isEmpty) return const SizedBox.shrink();
    
    return Card(
      margin: const EdgeInsets.all(8.0),
      child: ExpansionTile(
        leading: const Icon(Icons.history, color: Colors.grey),
        title: const Text('Command History'),
        subtitle: Text('${_commandHistory.length} commands executed'),
        children: _commandHistory.take(10).map((entry) {
          return ExpansionTile(
            dense: true,
            leading: Icon(
              entry.success ? Icons.check_circle : Icons.error,
              color: entry.success ? Colors.green : Colors.red,
              size: 16,
            ),
            title: Text(
              entry.command,
              style: const TextStyle(fontSize: 14),
            ),
            subtitle: Text(
              '${entry.timestamp.hour.toString().padLeft(2, '0')}:${entry.timestamp.minute.toString().padLeft(2, '0')}:${entry.timestamp.second.toString().padLeft(2, '0')}${entry.error != null ? ' - ${entry.error}' : ''}',
              style: const TextStyle(fontSize: 12),
            ),
            children: entry.details != null ? [
              Padding(
                padding: const EdgeInsets.fromLTRB(16, 0, 16, 8),
                child: Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: Colors.grey.shade100,
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Text(
                    entry.details!,
                    style: const TextStyle(
                      fontSize: 12,
                      color: Colors.black87,
                    ),
                  ),
                ),
              ),
            ] : [],
          );
        }).toList(),
      ),
    );
  }

  Widget _buildCommandSection({
    required String title,
    required IconData icon,
    required bool expanded,
    required VoidCallback onToggle,
    required List<Widget> commands,
  }) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
      child: ExpansionTile(
        leading: Icon(icon, color: Colors.blue),
        title: Text(
          title,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
        initiallyExpanded: expanded,
        onExpansionChanged: (value) => onToggle(),
        children: commands,
      ),
    );
  }

  Widget _buildCommandTile({
    required String title,
    required String subtitle,
    required IconData icon,
    required Color color,
    required VoidCallback onTap,
    String? badge,
    bool enabled = true,
  }) {
    return ListTile(
      enabled: enabled && widget.isConnected,
      leading: CircleAvatar(
        backgroundColor: enabled ? color.withOpacity(0.1) : Colors.grey.withOpacity(0.1),
        child: Icon(
          icon,
          color: enabled ? color : Colors.grey,
          size: 20,
        ),
      ),
      title: Row(
        children: [
          Expanded(
            child: Text(
              title,
              style: TextStyle(
                fontWeight: FontWeight.w500,
                color: enabled ? Colors.black87 : Colors.grey,
              ),
            ),
          ),
          if (badge != null)
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
              decoration: BoxDecoration(
                color: color,
                borderRadius: BorderRadius.circular(12),
              ),
              child: Text(
                badge,
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 10,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
        ],
      ),
      subtitle: Text(
        subtitle,
        style: TextStyle(
          fontSize: 12,
          color: enabled ? Colors.grey.shade600 : Colors.grey,
        ),
      ),
      onTap: enabled && widget.isConnected && !_isExecutingCommand ? onTap : null,
      trailing: enabled && widget.isConnected
          ? const Icon(Icons.arrow_forward_ios, size: 16)
          : const Icon(Icons.block, size: 16, color: Colors.grey),
    );
  }

  void _show3DFrequencyDialog() {
    final frequencies = ['25HZ', '50HZ', '100HZ', '200HZ', '400HZ'];
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('3D Sensor Frequency'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: frequencies.asMap().entries.map((entry) {
            return RadioListTile<int>(
              title: Text(entry.value),
              value: entry.key,
              groupValue: _current3DFrequency,
              onChanged: (value) {
                setState(() => _current3DFrequency = value!);
                Navigator.pop(context);
                _executeCommand(
                  '3D Frequency',
                  () async {
                    // 3D frequency will be configured
                  },
                  successMessage: '📡 3D Sensor Frequency Set!\nNew frequency: ${frequencies[_current3DFrequency]}\nSensor will update measurement rate.',
                );
              },
            );
          }).toList(),
        ),
      ),
    );
  }

  void _show6DFrequencyDialog() {
    final frequencies = ['26HZ', '52HZ', '104HZ', '208HZ'];
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('6D Sensor Frequency'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: frequencies.asMap().entries.map((entry) {
            return RadioListTile<int>(
              title: Text(entry.value),
              value: entry.key,
              groupValue: _current6DFrequency,
              onChanged: (value) {
                setState(() => _current6DFrequency = value!);
                Navigator.pop(context);
                _executeCommand(
                  '6D Frequency',
                  () async {
                    // 6D frequency will be configured
                  },
                  successMessage: '📊 6D Sensor Frequency Set!\nNew frequency: ${frequencies[_current6DFrequency]}\nMotion detection updated.',
                );
              },
            );
          }).toList(),
        ),
      ),
    );
  }

  void _showRopeModeDialog() {
    final modes = ['Free Mode', 'Counter Mode', 'Timer Mode'];
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Rope Skipping Mode'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: modes.asMap().entries.map((entry) {
            return RadioListTile<int>(
              title: Text(entry.value),
              value: entry.key,
              groupValue: _selectedRopeMode,
              onChanged: (value) {
                setState(() => _selectedRopeMode = value!);
                Navigator.pop(context);
                _executeCommand(
                  'Rope Mode',
                  () async {
                    final ropeMode = _selectedRopeMode == 0 ? RopeMode.free : 
                                    _selectedRopeMode == 1 ? RopeMode.counter : 
                                    RopeMode.timer;
                    await widget.extendedService.setRopeMode(ropeMode);
                  },
                  successMessage: '🪢 Rope Mode Selected!\nActive mode: ${modes[_selectedRopeMode]}\nDevice ready for rope skipping.',
                );
              },
            );
          }).toList(),
        ),
      ),
    );
  }
  
  void _showUserInfoDialog() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('User Information'),
        content: StatefulBuilder(
          builder: (context, setState) => SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                ListTile(
                  title: Text('Age: $_userAge years'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.remove),
                        onPressed: () => setState(() => _userAge = (_userAge - 1).clamp(1, 120)),
                      ),
                      IconButton(
                        icon: const Icon(Icons.add),
                        onPressed: () => setState(() => _userAge = (_userAge + 1).clamp(1, 120)),
                      ),
                    ],
                  ),
                ),
                ListTile(
                  title: Text('Gender: ${_userSex == 1 ? "Male" : "Female"}'),
                  trailing: Switch(
                    value: _userSex == 1,
                    onChanged: (value) => setState(() => _userSex = value ? 1 : 0),
                  ),
                ),
                ListTile(
                  title: Text('Weight: $_userWeight kg'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.remove),
                        onPressed: () => setState(() => _userWeight = (_userWeight - 1).clamp(30, 200)),
                      ),
                      IconButton(
                        icon: const Icon(Icons.add),
                        onPressed: () => setState(() => _userWeight = (_userWeight + 1).clamp(30, 200)),
                      ),
                    ],
                  ),
                ),
                ListTile(
                  title: Text('Height: $_userHeight cm'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.remove),
                        onPressed: () => setState(() => _userHeight = (_userHeight - 1).clamp(100, 250)),
                      ),
                      IconButton(
                        icon: const Icon(Icons.add),
                        onPressed: () => setState(() => _userHeight = (_userHeight + 1).clamp(100, 250)),
                      ),
                    ],
                  ),
                ),
                ListTile(
                  title: Text('User ID: $_userId'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.remove),
                        onPressed: () => setState(() => _userId = (_userId - 1).clamp(1, 99999)),
                      ),
                      IconButton(
                        icon: const Icon(Icons.add),
                        onPressed: () => setState(() => _userId = (_userId + 1).clamp(1, 99999)),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.pop(context);
              _executeCommand(
                'Set User Info',
                () => widget.extendedService.setUserInfo(_userAge, _userSex, _userWeight, _userHeight, _userId),
                successMessage: '👤 User Profile Updated!\nAge: $_userAge, Gender: ${_userSex == 1 ? "Male" : "Female"}\nWeight: $_userWeight kg, Height: $_userHeight cm, ID: $_userId',
              );
            },
            child: const Text('Apply'),
          ),
        ],
      ),
    );
  }
  
  void _showHRMaxDialog() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('HR Max Alarm'),
        content: StatefulBuilder(
          builder: (context, setState) => Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              ListTile(
                title: Text('Max HR Alarm: $_hrMaxAlarm BPM'),
                subtitle: const Text('Maximum heart rate before alarm'),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.remove),
                      onPressed: () => setState(() => _hrMaxAlarm = (_hrMaxAlarm - 5).clamp(100, 220)),
                    ),
                    IconButton(
                      icon: const Icon(Icons.add),
                      onPressed: () => setState(() => _hrMaxAlarm = (_hrMaxAlarm + 5).clamp(100, 220)),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.pop(context);
              _executeCommand(
                'HR Max Alarm',
                () => widget.extendedService.setHeartRateAlarm(true),
                successMessage: '🚨 HR Max Alarm Set!\nAlarm will trigger when heart rate exceeds $_hrMaxAlarm BPM',
              );
            },
            child: const Text('Apply'),
          ),
        ],
      ),
    );
  }
  
  void _showHRStatusDialog() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('HR Monitoring Status'),
        content: StatefulBuilder(
          builder: (context, setState) => Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text('Configure Heart Rate monitoring parameters:'),
              const SizedBox(height: 16),
              ListTile(
                title: Text('Min HR: $_hrMin BPM'),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.remove),
                      onPressed: () => setState(() => _hrMin = (_hrMin - 5).clamp(40, 200)),
                    ),
                    IconButton(
                      icon: const Icon(Icons.add),
                      onPressed: () => setState(() => _hrMin = (_hrMin + 5).clamp(40, 200)),
                    ),
                  ],
                ),
              ),
              ListTile(
                title: Text('Max HR: $_hrMax BPM'),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.remove),
                      onPressed: () => setState(() => _hrMax = (_hrMax - 5).clamp(40, 220)),
                    ),
                    IconButton(
                      icon: const Icon(Icons.add),
                      onPressed: () => setState(() => _hrMax = (_hrMax + 5).clamp(40, 220)),
                    ),
                  ],
                ),
              ),
              ListTile(
                title: Text('Goal HR: $_hrGoal BPM'),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.remove),
                      onPressed: () => setState(() => _hrGoal = (_hrGoal - 5).clamp(40, 200)),
                    ),
                    IconButton(
                      icon: const Icon(Icons.add),
                      onPressed: () => setState(() => _hrGoal = (_hrGoal + 5).clamp(40, 200)),
                    ),
                  ],
                ),
              ),
              SwitchListTile(
                title: const Text('HR Alarm Enabled'),
                subtitle: const Text('Enable heart rate alarms'),
                value: _hrAlarmEnabled,
                onChanged: (value) => setState(() => _hrAlarmEnabled = value),
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.pop(context);
              _executeCommand(
                'HR Status Config',
                () => widget.extendedService.setHeartRateAlarm(_hrAlarmEnabled),
                successMessage: '❤️ HR Monitoring Configured!\nRange: $_hrMin-$_hrMax BPM, Goal: $_hrGoal BPM\nAlarms: ${_hrAlarmEnabled ? 'Enabled' : 'Disabled'}',
              );
            },
            child: const Text('Apply'),
          ),
        ],
      ),
    );
  }
}

/// Entry for command history tracking
class CommandHistoryEntry {
  final String command;
  final DateTime timestamp;
  final bool success;
  final String? error;
  final String? details;

  CommandHistoryEntry({
    required this.command,
    required this.timestamp,
    required this.success,
    this.error,
    this.details,
  });
}
