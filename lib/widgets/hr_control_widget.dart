import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../services/ble_protocol/official_commands.dart';

/// Widget per controllare e configurare Heart Rate e testare vibrazione
class HRControlWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const HRControlWidget({
    Key? key,
    required this.service,
  }) : super(key: key);

  @override
  State<HRControlWidget> createState() => _HRControlWidgetState();
}

class _HRControlWidgetState extends State<HRControlWidget> {
  // HR Configuration Data
  final Map<String, dynamic> _hrConfig = {
    'min': null,
    'max': null,
    'goal': null,
    'alarmEnabled': null,
  };

  @override
  void initState() {
    super.initState();
    _setupHRCallbacks();
  }
  
  void _setupHRCallbacks() {
    widget.service.setHRCallbacks(
      onConfigReceived: (int min, int max, int goal, bool alarmEnabled) {
        setState(() {
          _hrConfig['min'] = min;
          _hrConfig['max'] = max;
          _hrConfig['goal'] = goal;
          _hrConfig['alarmEnabled'] = alarmEnabled;
        });
        debugPrint('🔄 HR Config updated in UI: Min=$min, Max=$max, Goal=$goal, Alarm=$alarmEnabled');
      },
      onError: (String error) {
        debugPrint('❌ HR Config error: $error');
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('HR Error: $error')),
          );
        }
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('❤️ HR Configuration & Vibration Control'),
        backgroundColor: Colors.orange,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            // HR Configuration Display
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    const Text(
                      '❤️ Heart Rate Configuration',
                      style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 12),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Column(
                          children: [
                            const Text('Min HR', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text('${_hrConfig['min'] ?? '?'} BPM'),
                          ],
                        ),
                        Column(
                          children: [
                            const Text('Max HR', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text('${_hrConfig['max'] ?? '?'} BPM'),
                          ],
                        ),
                        Column(
                          children: [
                            const Text('Goal HR', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text('${_hrConfig['goal'] ?? '?'} BPM'),
                          ],
                        ),
                        Column(
                          children: [
                            const Text('Alarm', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text(_hrConfig['alarmEnabled'] == true ? 'ON' : 'OFF'),
                          ],
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        ElevatedButton.icon(
                          onPressed: _readHRConfiguration,
                          icon: const Icon(Icons.refresh, size: 16),
                          label: const Text('Read Config'),
                          style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
                        ),
                        ElevatedButton.icon(
                          onPressed: _showHRConfigDialog,
                          icon: const Icon(Icons.settings, size: 16),
                          label: const Text('Set Config'),
                          style: ElevatedButton.styleFrom(backgroundColor: Colors.orange),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 16),
            
            // Essential Test Buttons
            Expanded(
              child: GridView.count(
                crossAxisCount: 2,
                crossAxisSpacing: 12,
                mainAxisSpacing: 12,
                childAspectRatio: 1.2,
                children: [
                  _buildQuickTestButton(
                    '🔥 SHUTDOWN TEST',
                    'CONFIRMED VIBRATION\n(Device shuts down)',
                    Colors.red.shade600,
                    _testShutdownVibration,
                  ),
                  _buildQuickTestButton(
                    '🚨 TRIGGER ALARM',
                    'Test HR Threshold\nVibration',
                    Colors.orange.shade600,
                    _testHRAlarmVibration,
                  ),
                  _buildQuickTestButton(
                    '🔍 DEBUG MODE',
                    'Raw Command\nResponse Analysis',
                    Colors.teal.shade600,
                    _testDecodeResponses,
                  ),
                  _buildQuickTestButton(
                    '💡 SMART TRIGGER',
                    'High HR + Alarm\nCombination Test',
                    Colors.purple.shade600,
                    _testSmartTrigger,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
  
  // Quick test button builder
  Widget _buildQuickTestButton(String title, String description, Color color, VoidCallback onPressed) {
    return Card(
      elevation: 4,
      child: InkWell(
        onTap: onPressed,
        borderRadius: BorderRadius.circular(8),
        child: Container(
          padding: const EdgeInsets.all(12),
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(8),
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                title,
                style: const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 14,
                ),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 8),
              Text(
                description,
                style: const TextStyle(
                  color: Colors.white70,
                  fontSize: 11,
                ),
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      ),
    );
  }
  
  // ===== HR CONFIGURATION MANAGEMENT =====
  
  Future<void> _readHRConfiguration() async {
    try {
      debugPrint('🔍 Reading HR Configuration from device...');
      
      // Send commands to get HR configuration (triggers 0x47 response) using official commands
      final getHRStatusCommand = OfficialChileafCommands.getHeartRateStatus();
      await widget.service.sendRawCommand(getHRStatusCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      final getHRAlarmCommand = OfficialChileafCommands.getHeartRateAlarm();
      await widget.service.sendRawCommand(getHRAlarmCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      final getHRMaxCommand = OfficialChileafCommands.getHeartRateMax();
      await widget.service.sendRawCommand(getHRMaxCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Note: The actual decoding happens in chileaf_extended_service.dart command 0x47 handler
      // Values will be displayed in Flutter logs for now
      debugPrint('🔍 HR Configuration commands sent. Check Flutter logs for decoded values.');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('HR Configuration read - check Flutter logs')),
        );
      }
      
    } catch (e) {
      debugPrint('❌ Error reading HR configuration: $e');
    }
  }
  
  void _showHRConfigDialog() {
    int tempMin = _hrConfig['min'] ?? 60;
    int tempMax = _hrConfig['max'] ?? 180;
    int tempGoal = _hrConfig['goal'] ?? 120;
    bool tempAlarm = _hrConfig['alarmEnabled'] ?? false;
    
    showDialog(
      context: context,
      builder: (context) => StatefulBuilder(
        builder: (context, setDialogState) => AlertDialog(
          title: const Text('❤️ HR Configuration'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Row(
                children: [
                  const Expanded(child: Text('Min HR:')),
                  SizedBox(
                    width: 80,
                    child: TextFormField(
                      initialValue: tempMin.toString(),
                      keyboardType: TextInputType.number,
                      onChanged: (value) => tempMin = int.tryParse(value) ?? tempMin,
                    ),
                  ),
                  const Text(' BPM'),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Expanded(child: Text('Max HR:')),
                  SizedBox(
                    width: 80,
                    child: TextFormField(
                      initialValue: tempMax.toString(),
                      keyboardType: TextInputType.number,
                      onChanged: (value) => tempMax = int.tryParse(value) ?? tempMax,
                    ),
                  ),
                  const Text(' BPM'),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Expanded(child: Text('Goal HR:')),
                  SizedBox(
                    width: 80,
                    child: TextFormField(
                      initialValue: tempGoal.toString(),
                      keyboardType: TextInputType.number,
                      onChanged: (value) => tempGoal = int.tryParse(value) ?? tempGoal,
                    ),
                  ),
                  const Text(' BPM'),
                ],
              ),
              const SizedBox(height: 12),
              SwitchListTile(
                title: const Text('HR Alarm'),
                value: tempAlarm,
                onChanged: (value) => setDialogState(() => tempAlarm = value),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () async {
                await _setHRConfiguration(tempMin, tempMax, tempGoal, tempAlarm);
                if (context.mounted) Navigator.pop(context);
              },
              child: const Text('Apply'),
            ),
          ],
        ),
      ),
    );
  }
  
  Future<void> _setHRConfiguration(int min, int max, int goal, bool alarmEnabled) async {
    try {
      debugPrint('🔧 Setting HR Configuration: Min=$min, Max=$max, Goal=$goal, Alarm=$alarmEnabled');
      
      // Set heart rate status (min, max, goal) using official command
      final setHRCommand = OfficialChileafCommands.setHeartRateStatus(min, max, goal);
      await widget.service.sendRawCommand(setHRCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Set alarm switch using official command
      final setAlarmCommand = OfficialChileafCommands.setHeartRateAlarm(alarmEnabled);
      await widget.service.sendRawCommand(setAlarmCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      setState(() {
        _hrConfig['min'] = min;
        _hrConfig['max'] = max;
        _hrConfig['goal'] = goal;
        _hrConfig['alarmEnabled'] = alarmEnabled;
      });
      
      debugPrint('✅ HR Configuration applied successfully');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('HR Configuration applied successfully')),
        );
      }
      
      // Refresh to verify
      await Future.delayed(const Duration(milliseconds: 1000));
      await _readHRConfiguration();
      
    } catch (e) {
      debugPrint('❌ Error setting HR configuration: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e')),
        );
      }
    }
  }
  
  // ===== TEST FUNCTIONS =====
  
  Future<void> _testShutdownVibration() async {
    bool? confirm = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('⚠️ Shutdown Test'),
        content: const Text('This will cause vibration but SHUTDOWN the device. Continue?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context, false), child: const Text('Cancel')),
          ElevatedButton(onPressed: () => Navigator.pop(context, true), child: const Text('YES')),
        ],
      ),
    );
    
    if (confirm == true) {
      final command = OfficialChileafCommands.deviceShutdown();
      await widget.service.sendRawCommand(command);
      debugPrint('🔥 SHUTDOWN TEST: Device will vibrate and shutdown');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Shutdown command sent - device will vibrate and turn off')),
        );
      }
    }
  }
  
  Future<void> _testHRAlarmVibration() async {
    try {
      debugPrint('🚨 HR ALARM TEST: Setting extreme thresholds to trigger alarm');
      
      // Set very low thresholds to trigger alarm
      final setLowMax = OfficialChileafCommands.setHeartRateStatus(40, 50, 45); // Very low max
      await widget.service.sendRawCommand(setLowMax);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Enable alarm
      final enableAlarm = OfficialChileafCommands.setHeartRateAlarm(true);
      await widget.service.sendRawCommand(enableAlarm);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('HR alarm thresholds set - check if device vibrates')),
        );
      }
      
      // Auto-restore normal values after 10 seconds
      await Future.delayed(const Duration(seconds: 10));
      final restoreNormal = OfficialChileafCommands.setHeartRateStatus(60, 180, 120);
      await widget.service.sendRawCommand(restoreNormal);
      debugPrint('🔧 Auto-restored normal HR thresholds');
      
    } catch (e) {
      debugPrint('❌ Error in HR alarm test: $e');
    }
  }
  
  Future<void> _testDecodeResponses() async {
    try {
      debugPrint('🔍 DEBUG MODE: Testing commands for detailed responses');
      
      // Test various commands to trigger responses
      final commands = [
        OfficialChileafCommands.getHeartRateStatus(),
        OfficialChileafCommands.getHeartRateAlarm(),
        OfficialChileafCommands.getHeartRateMax(),
        OfficialChileafCommands.getUserInfo(),
      ];
      
      for (int i = 0; i < commands.length; i++) {
        debugPrint('🔍 TEST ${i+1}/${commands.length}: Sending command');
        await widget.service.sendRawCommand(commands[i]);
        await Future.delayed(const Duration(milliseconds: 1000));
      }
      
      // Special test: Try to trigger 0x47 response
      debugPrint('🔍 SPECIAL TEST: Triggering 0x47 response with setHeartRateStatus');
      final triggerCommand = OfficialChileafCommands.setHeartRateStatus(60, 80, 70);
      await widget.service.sendRawCommand(triggerCommand);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Debug commands sent - check Flutter logs')),
        );
      }
      
    } catch (e) {
      debugPrint('❌ Error in decode test: $e');
    }
  }
  
  Future<void> _testSmartTrigger() async {
    try {
      debugPrint('💡 SMART TRIGGER: Complex HR configuration to force vibration');
      
      // Step 1: Enable alarm
      final enableAlarm = OfficialChileafCommands.setHeartRateAlarm(true);
      await widget.service.sendRawCommand(enableAlarm);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Step 2: Set very tight thresholds
      final setTightThresholds = OfficialChileafCommands.setHeartRateStatus(70, 75, 72); 
      await widget.service.sendRawCommand(setTightThresholds);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Step 3: Try to trigger via max HR command
      final setMaxHR = OfficialChileafCommands.setHeartRateMax(65); // Lower than current HR
      await widget.service.sendRawCommand(setMaxHR);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Smart trigger applied - monitoring for vibration')),
        );
      }
      
      // Auto-restore after 15 seconds
      await Future.delayed(const Duration(seconds: 15));
      final restoreNormal = OfficialChileafCommands.setHeartRateStatus(60, 180, 120);
      await widget.service.sendRawCommand(restoreNormal);
      final restoreMaxHR = OfficialChileafCommands.setHeartRateMax(180);
      await widget.service.sendRawCommand(restoreMaxHR);
      debugPrint('🔧 Auto-restored normal HR configuration');
      
    } catch (e) {
      debugPrint('❌ Error in smart trigger test: $e');
    }
  }
}
