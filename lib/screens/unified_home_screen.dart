import 'dart:async';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:intl/intl.dart';
import 'package:path_provider/path_provider.dart';
import 'package:share_plus/share_plus.dart';
import 'grok_hr_screen.dart';
import 'sleep_premium_screen.dart';
import 'advanced_features_test_screen.dart';
import 'dashboard_screen.dart';
import 'advanced_health_dashboard.dart';
import 'timezone_test_screen.dart';
import '../chileaf_extended_service.dart';
import '../models/heart_rate_data.dart';
import '../models/historical_data.dart';
import '../battery.dart' show BatteryService;
import '../heartrate.dart' show HeartRateService;

/// Unified Home Screen - Persistent BT connection with individual data download
class UnifiedHomeScreen extends StatefulWidget {
  const UnifiedHomeScreen({super.key});

  @override
  State<UnifiedHomeScreen> createState() => _UnifiedHomeScreenState();
}

class _UnifiedHomeScreenState extends State<UnifiedHomeScreen> {
  final ChileafExtendedService _service = ChileafExtendedService();
  final BatteryService _batteryService = BatteryService();
  final HeartRateService _heartRateService = HeartRateService();
  
  // BLE Connection
  BluetoothDevice? _connectedDevice;
  List<BluetoothDevice> _foundDevices = [];
  bool _isScanning = false;
  bool _isConnecting = false;
  int _batteryLevel = 0;
  StreamSubscription<BluetoothConnectionState>? _connectionSubscription;
  StreamSubscription<List<ScanResult>>? _scanSubscription;
  StreamSubscription<int?>? _batterySubscription;
  StreamSubscription<HeartRateData?>? _heartRateSubscription;
  
  // Stream controller to notify dialog of device updates
  final StreamController<void> _dialogUpdateController = StreamController<void>.broadcast();

  // Data download status
  final Map<String, bool> _downloading = {};
  final Map<String, String> _lastDownload = {};
  final Map<String, int> _dataCount = {};
  
  // Sleep data cache for CSV export
  final List<SleepHistoryEntry> _sleepHistoryData = [];

  @override
  void initState() {
    super.initState();
    _initializeBluetooth();
    _setupStreams();
  }

  @override
  void dispose() {
    _dialogUpdateController.close();
    _scanSubscription?.cancel();
    _connectionSubscription?.cancel();
    _batterySubscription?.cancel();
    _heartRateSubscription?.cancel();
    _disconnect();
    super.dispose();
  }

  Future<void> _initializeBluetooth() async {
    await _requestPermissions();
    await FlutterBluePlus.turnOn();
  }

  Future<void> _requestPermissions() async {
    await Future.wait([
      Permission.bluetooth.request(),
      Permission.bluetoothScan.request(),
      Permission.bluetoothConnect.request(),
      Permission.location.request(),
    ]);
  }

  void _setupStreams() {
    // HR History List
    _service.hrHistoryListStream.listen((list) {
      if (mounted) {
        setState(() {
          _dataCount['hr'] = list.timestamps.length;
          _downloading['hr'] = false;
          _lastDownload['hr'] = 'Now';
        });
      }
    });

    // Sleep History - Use sleepHistoryStream
    _service.sleepHistoryStream.listen((list) {
      debugPrint('🏠 HOME SCREEN: Received ${list.length} sleep sessions from stream');
      if (mounted) {
        setState(() {
          _sleepHistoryData.clear();
          _sleepHistoryData.addAll(list);
          _dataCount['sleep'] = list.length;
          _downloading['sleep'] = false;
          _lastDownload['sleep'] = '${list.length} sessions - Now';
          debugPrint('🏠 HOME SCREEN: Updated UI with ${list.length} sleep sessions');
        });
      }
    });

    // Steps History - Use stepsHistoryStream
    _service.stepsHistoryStream.listen((list) {
      if (mounted) {
        setState(() {
          _dataCount['steps'] = list.length;
          _downloading['steps'] = false;
          _lastDownload['steps'] = 'Now';
        });
      }
    });

    // User Info
    _service.userInfoStream.listen((userInfo) {
      if (mounted) {
        setState(() {
          _downloading['user'] = false;
          _lastDownload['user'] = 'Age: ${userInfo.age}, BMI: ${userInfo.bmi.toStringAsFixed(1)}';
        });
      }
    });

    // Sport Health
    _service.sportHealthStream.listen((data) {
      if (mounted) {
        setState(() {
          _downloading['sport'] = false;
          _lastDownload['sport'] = 'VO2: ${data.vo2Max}, HRV: ${data.totalPower?.toStringAsFixed(0) ?? "N/A"}';
        });
      }
    });

    // Heart Rate - real-time from ChileafExtendedService
    _service.realTimeHeartRateStream.listen((hr) {
      if (mounted) {
        setState(() {
        });
      }
      debugPrint('💓 HOME SCREEN: Received HR from service: $hr BPM');
    });
    
    // Battery - will be set up when device connects
    // Heart Rate - will also use HeartRateService for redundancy
  }

  // Setup dedicated Battery and HeartRate services
  Future<void> _setupDedicatedServices(BluetoothDevice device) async {
    // Battery Service
    try {
      await _batteryService.start(device);
      debugPrint('🔋 HOME SCREEN: Battery service started');
      
      _batterySubscription = _batteryService.dataStream.listen((batteryLevel) {
        if (mounted && batteryLevel != null) {
          setState(() {
            _batteryLevel = batteryLevel;
          });
          debugPrint('🔋 HOME SCREEN: Battery updated: $batteryLevel%');
        }
      });
    } catch (e) {
      debugPrint('🔋 HOME SCREEN: Failed to start battery service: $e');
    }
    
    // Heart Rate Service (redundancy + RR intervals)
    try {
      await _heartRateService.start(device);
      debugPrint('💓 HOME SCREEN: Heart Rate service started');
      
      // Reduce logging frequency
      int hrLogCounter = 0;
      _heartRateSubscription = _heartRateService.dataStream.listen((hrData) {
        if (mounted && hrData != null) {
          setState(() {
          });
          
          // Log only every 10th HR update to reduce spam
          hrLogCounter++;
          if (hrLogCounter % 10 == 0) {
            debugPrint('💓 HOME SCREEN: HR from service: ${hrData.heartRate} BPM');
            
            // Log RR intervals if available
            if (hrData.rrIntervals != null && hrData.rrIntervals!.isNotEmpty) {
              debugPrint('💓 RR Intervals: ${hrData.rrIntervals!.length} intervals');
            }
          }
        }
      });
    } catch (e) {
      debugPrint('💓 HOME SCREEN: Failed to start HR service: $e');
    }
  }

  // ===== BLE CONNECTION =====

  Future<void> _startScan() async {
    if (_isScanning) return;

    debugPrint('🔍 Starting BLE scan...');

    // Cancel previous scan subscription
    await _scanSubscription?.cancel();

    setState(() {
      _isScanning = true;
      _foundDevices = []; // Create new list instead of clearing
    });

    try {
      // Check if Bluetooth is available
      if (await FlutterBluePlus.isSupported == false) {
        debugPrint("❌ Bluetooth not supported by this device");
        return;
      }

      // Check Bluetooth adapter state
      final adapterState = await FlutterBluePlus.adapterState.first;
      debugPrint('📱 Bluetooth adapter state: $adapterState');
      
      if (adapterState != BluetoothAdapterState.on) {
        debugPrint("❌ Bluetooth is off, trying to turn on...");
        await FlutterBluePlus.turnOn();
        await Future.delayed(const Duration(seconds: 2));
      }

      // Start scan
      debugPrint('🔍 Starting scan with 10s timeout...');
      await FlutterBluePlus.startScan(
        timeout: const Duration(seconds: 10),
        androidUsesFineLocation: true,
      );

      // Listen to scan results
      _scanSubscription = FlutterBluePlus.scanResults.listen((results) {
        debugPrint('📡 Scan results received: ${results.length} devices');
        
        if (!mounted) return;
        
        final List<BluetoothDevice> newDevices = [];
        for (var result in results) {
          final name = result.device.platformName;
          final id = result.device.remoteId.toString();
          
          debugPrint('  Device: $name ($id)');
          
          // Accept ANY device for testing
          if (!_foundDevices.any((d) => d.remoteId == result.device.remoteId)) {
            newDevices.add(result.device);
            debugPrint('  ✅ Added: $name');
          }
        }
        
        if (newDevices.isNotEmpty && mounted) {
          setState(() {
            _foundDevices = [..._foundDevices, ...newDevices];
            debugPrint('📱 Total devices found: ${_foundDevices.length}');
          });
          // Notify dialog to update
          _dialogUpdateController.add(null);
        }
      });

      // Wait for scan to complete
      await Future.delayed(const Duration(seconds: 10));
      debugPrint('⏱️ Scan timeout reached');
      
    } catch (e) {
      debugPrint('❌ Scan error: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Scan error: $e'), backgroundColor: Colors.red),
        );
      }
    } finally {
      await FlutterBluePlus.stopScan();
      debugPrint('🛑 Scan stopped. Found ${_foundDevices.length} devices');
      if (mounted) {
        setState(() => _isScanning = false);
      }
    }
  }

  Future<void> _connectToDevice(BluetoothDevice device) async {
    if (_isConnecting) return;

    setState(() {
      _isConnecting = true;
    });

    try {
      debugPrint('🔗 Connecting to ${device.platformName}...');
      
      // Connect with license parameter (required by flutter_blue_plus)
      await device.connect(mtu: null, license: License.free);
      
      debugPrint('✅ Connected, discovering services...');
      
      // Discover services
      await device.discoverServices();
      
      debugPrint('🔧 Configuring ChileafExtendedService...');
      
      // Initialize the service with the device (this sets up characteristics)
      await _service.start(device);
      
      debugPrint('✅ Service configured successfully');
      
      // Setup dedicated Battery and HeartRate services
      debugPrint('🔧 Setting up Battery and HR services...');
      await _setupDedicatedServices(device);
      debugPrint('✅ Battery and HR services configured');

      _connectionSubscription = device.connectionState.listen((state) {
        if (mounted) {
          setState(() {
            if (state == BluetoothConnectionState.connected) {
              _connectedDevice = device;
            } else {
              _connectedDevice = null;
            }
          });
        }
      });

      setState(() {
        _connectedDevice = device;
      });

      debugPrint('📥 Requesting initial data...');
      
      // Request initial data
      await _service.requestUserInfo();
      await _service.getBodyHealth();

      if (mounted) {
        Navigator.pop(context); // Close scan dialog
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('✅ Device connected successfully!')),
        );
      }
    } catch (e) {
      debugPrint('❌ Connection error: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Connection failed: $e')),
        );
      }
    } finally {
      setState(() => _isConnecting = false);
    }
  }

  Future<void> _disconnect() async {
    if (_connectedDevice != null) {
      await _connectedDevice!.disconnect();
      setState(() {
        _connectedDevice = null;
        _batteryLevel = 0;
      });
    }
  }

  void _showScanDialog() {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => StreamBuilder<void>(
        stream: _dialogUpdateController.stream,
        builder: (context, snapshot) {
          // Auto-start scan when dialog first builds
          if (!_isScanning && _foundDevices.isEmpty) {
            Future.microtask(() => _startScan());
          }

          return AlertDialog(
            title: const Text('🔍 Scan for Devices'),
            content: SizedBox(
              width: double.maxFinite,
              height: 300,
              child: _isScanning && _foundDevices.isEmpty
                  ? const Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          CircularProgressIndicator(),
                          SizedBox(height: 16),
                          Text('Scanning for CL837 devices...'),
                        ],
                      ),
                    )
                  : _foundDevices.isEmpty && !_isScanning
                      ? Center(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Icon(Icons.bluetooth_disabled, size: 48, color: Colors.grey),
                              const SizedBox(height: 16),
                              const Text('No devices found'),
                              const SizedBox(height: 8),
                              const Text(
                                'Make sure your device is on and nearby',
                                style: TextStyle(fontSize: 12, color: Colors.grey),
                                textAlign: TextAlign.center,
                              ),
                            ],
                          ),
                        )
                      : ListView.builder(
                          itemCount: _foundDevices.length,
                          itemBuilder: (context, index) {
                            final device = _foundDevices[index];
                            return Card(
                              child: ListTile(
                                leading: const Icon(Icons.bluetooth, color: Colors.blue),
                                title: Text(device.platformName.isEmpty ? 'Unknown Device' : device.platformName),
                                subtitle: Text(device.remoteId.toString()),
                                trailing: _isConnecting
                                    ? const SizedBox(
                                        width: 20,
                                        height: 20,
                                        child: CircularProgressIndicator(strokeWidth: 2),
                                      )
                                    : const Icon(Icons.arrow_forward),
                                onTap: () => _connectToDevice(device),
                              ),
                            );
                          },
                        ),
            ),
            actions: [
              TextButton(
                onPressed: () {
                  _scanSubscription?.cancel();
                  FlutterBluePlus.stopScan();
                  Navigator.pop(context);
                },
                child: const Text('Cancel'),
              ),
              if (!_isScanning)
                ElevatedButton.icon(
                  onPressed: () async {
                    setState(() => _foundDevices = []);
                    _dialogUpdateController.add(null);
                    await _startScan();
                  },
                  icon: const Icon(Icons.refresh),
                  label: const Text('Rescan'),
                ),
            ],
          );
        },
      ),
    );
  }

  // ===== DATA DOWNLOAD =====

  Future<void> _downloadHRHistory() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['hr'] = true);
    await _service.requestHRHistoryList();
  }

  Future<void> _downloadSleepHistory() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['sleep'] = true);
    await _service.requestOptimizedSleepHistory(force: true);
  }

  Future<void> _downloadStepsHistory() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['steps'] = true);
    await _service.requestStepIntervalHistory();
  }

  Future<void> _downloadUserInfo() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['user'] = true);
    await _service.requestUserInfo();
  }

  Future<void> _downloadSportHealth() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['sport'] = true);
    await _service.getBodyHealth();
  }

  Future<void> _downloadRealTimeHR() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    // startHeartRateMonitoring is the correct method name
    await _service.startHeartRateMonitoring();
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('▶️ Real-time HR monitoring started')),
      );
    }
  }

  Future<void> _downloadRealTimeSteps() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    // Use getBodyHealth for current step count (real-time steps is part of sport health)
    await _service.getBodyHealth();
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('▶️ Body health refreshed (includes steps)')),
      );
    }
  }

  void _showNotConnectedSnackbar() {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('❌ Please connect to device first'),
        backgroundColor: Colors.red,
      ),
    );
  }

  // ===== CSV EXPORT =====
  
  /// Genera il contenuto CSV per i dati del sonno
  String _generateSleepCSVContent(List<SleepHistoryEntry> sleepData) {
    StringBuffer csvContent = StringBuffer();
    
    // Header CSV
    csvContent.writeln('Session_DateTime,Duration_Minutes,Total_Sleep_Minutes,Deep_Sleep_Minutes,Light_Sleep_Minutes,Awake_Minutes,Sleep_Efficiency_%,Sleep_Quality,Action_Index,Action_Timestamp');
    csvContent.writeln('# NOTE: Each Action Index = 5-MINUTE block (SDK 0x31 specification)');
    
    // Dati per ogni sessione
    for (var sleep in sleepData) {
      final phases = sleep.calculateSleepPhases();
      final totalSleep = phases.lightSleep + phases.deepSleep;
      final totalMinutes = phases.totalMinutes;
      final efficiency = totalMinutes > 0 ? ((totalSleep / totalMinutes) * 100).toStringAsFixed(1) : '0';
      
      // Determina qualità del sonno
      String quality = 'Poor';
      if (totalSleep > 360 && phases.deepSleep > totalSleep * 0.2) {
        quality = 'Excellent';
      } else if (totalSleep > 300 && phases.deepSleep > totalSleep * 0.15) {
        quality = 'Good';
      } else if (totalSleep > 240) {
        quality = 'Fair';
      }
      
      String sessionDateTime = DateFormat('yyyy-MM-dd HH:mm:ss').format(sleep.timestamp);
      
      // Riga sommaria della sessione
      csvContent.writeln('$sessionDateTime,$totalMinutes,$totalSleep,${phases.deepSleep},${phases.lightSleep},${phases.awake},$efficiency,$quality,,');
      
      // Dettaglio azioni (ogni azione = 5 MINUTI secondo SDK)
      for (int i = 0; i < sleep.actions.length; i++) {
        int action = sleep.actions[i];
        DateTime actionTime = sleep.timestamp.add(Duration(minutes: i * 5)); // 5 minuti per action
        String actionTimestamp = DateFormat('yyyy-MM-dd HH:mm:ss').format(actionTime);
        
        // Determina fase del sonno per questa azione (5-minute block)
        String phase = 'Unknown';
        if (action == 0) {
          phase = 'Very Still (0)';
        } else if (action > 20) {
          phase = 'Active/Awake';
        } else {
          phase = 'Light Activity';
        }
        
        csvContent.writeln(',,,,,,,,$action ($phase),$actionTimestamp');
      }
      
      // Riga vuota tra sessioni
      csvContent.writeln();
    }
    
    return csvContent.toString();
  }

  /// Esporta i dati del sonno in CSV
  Future<void> _exportSleepDataToCSV() async {
    if (_sleepHistoryData.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('⚠️ No sleep data to export. Download Sleep History first!'),
          backgroundColor: Colors.orange,
          duration: Duration(seconds: 3),
        ),
      );
      return;
    }
    
    try {
      // Calcola statistiche
      final uniqueDates = _sleepHistoryData
          .map((s) => DateFormat('yyyy-MM-dd').format(s.timestamp))
          .toSet()
          .length;
      
      final csvContent = _generateSleepCSVContent(_sleepHistoryData);
      final timestamp = DateFormat('yyyyMMdd_HHmmss').format(DateTime.now());
      final filename = 'CL837_Sleep_Data_$timestamp.csv';
      
      // Usa share_plus per condividere il file
      final directory = await getTemporaryDirectory();
      final path = '${directory.path}/$filename';
      final file = File(path);
      await file.writeAsString(csvContent);
      
      await Share.shareXFiles(
        [XFile(path)],
        text: 'CL837 Sleep Data Export - ${_sleepHistoryData.length} sessions from $uniqueDates days',
        subject: filename,
      );
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('✅ Exported ${_sleepHistoryData.length} sessions from $uniqueDates days'),
            backgroundColor: Colors.green,
            duration: const Duration(seconds: 3),
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Export failed: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(),
      body: _buildBody(),
      floatingActionButton: _connectedDevice == null
          ? FloatingActionButton.extended(
              onPressed: _showScanDialog,
              icon: const Icon(Icons.bluetooth_searching),
              label: const Text('Connect Device'),
              backgroundColor: Colors.deepPurple,
            )
          : null,
    );
  }

  PreferredSizeWidget _buildAppBar() {
    return AppBar(
      title: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(
            _connectedDevice != null ? Icons.bluetooth_connected : Icons.bluetooth_disabled,
            color: Colors.white,
          ),
          const SizedBox(width: 8),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                _connectedDevice != null ? 'Connected' : 'Not Connected',
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
              if (_connectedDevice != null)
                Text(
                  _connectedDevice!.platformName,
                  style: const TextStyle(fontSize: 11),
                ),
            ],
          ),
        ],
      ),
      backgroundColor: _connectedDevice != null ? Colors.green.shade700 : Colors.grey.shade700,
      foregroundColor: Colors.white,
      actions: [
        if (_batteryLevel > 0)
          Center(
            child: Padding(
              padding: const EdgeInsets.only(right: 8),
              child: Row(
                children: [
                  Icon(
                    _batteryLevel > 20 ? Icons.battery_std : Icons.battery_alert,
                    size: 20,
                  ),
                  Text('$_batteryLevel%', style: const TextStyle(fontSize: 14)),
                ],
              ),
            ),
          ),
        if (_connectedDevice != null)
          IconButton(
            icon: const Icon(Icons.bluetooth_disabled),
            onPressed: _disconnect,
            tooltip: 'Disconnect',
          ),
        if (_connectedDevice == null)
          IconButton(
            icon: const Icon(Icons.bluetooth_searching),
            onPressed: _showScanDialog,
            tooltip: 'Connect',
          ),
      ],
    );
  }

  Widget _buildBody() {
    if (_connectedDevice == null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.bluetooth_disabled, size: 80, color: Colors.grey.shade400),
            const SizedBox(height: 24),
            Text(
              'No Device Connected',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Colors.grey.shade700),
            ),
            const SizedBox(height: 8),
            Text(
              'Tap the button below to scan for devices',
              style: TextStyle(fontSize: 16, color: Colors.grey.shade600),
            ),
          ],
        ),
      );
    }

    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            '📥 Download Data',
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          Text(
            'Download individual data from your CL837 device',
            style: TextStyle(fontSize: 14, color: Colors.grey.shade600),
          ),
          const SizedBox(height: 24),
          
          // Historical Data Section
          _buildSectionTitle('Historical Data'),
          // Persistent Export CSV button - DIRECT export without navigation
          Padding(
            padding: const EdgeInsets.only(bottom: 8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                ElevatedButton.icon(
                  onPressed: (_dataCount['sleep'] ?? 0) > 0 ? _exportSleepDataToCSV : null,
                  icon: Icon(
                    Icons.download, 
                    size: 16,
                    color: (_dataCount['sleep'] ?? 0) > 0 ? Colors.white : Colors.grey.shade400,
                  ),
                  label: Text(
                    (_dataCount['sleep'] ?? 0) > 0 
                        ? 'Export Sleep CSV (${_dataCount['sleep']})' 
                        : 'Export Sleep CSV',
                    style: TextStyle(
                      color: (_dataCount['sleep'] ?? 0) > 0 ? Colors.white : Colors.grey.shade400,
                    ),
                  ),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: (_dataCount['sleep'] ?? 0) > 0 
                        ? Colors.green.shade700 
                        : Colors.grey.shade600,
                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                    textStyle: const TextStyle(fontSize: 13, fontWeight: FontWeight.w600),
                  ),
                ),
              ],
            ),
          ),
          _buildDownloadTile(
            title: 'Heart Rate History',
            subtitle: _lastDownload['hr'] ?? 'Not downloaded yet',
            icon: Icons.favorite,
            color: Colors.red,
            count: _dataCount['hr'],
            isDownloading: _downloading['hr'] ?? false,
            onDownload: _downloadHRHistory,
          ),
          _buildDownloadTile(
            title: 'Sleep History',
            subtitle: _lastDownload['sleep'] ?? 'Not downloaded yet',
            icon: Icons.nightlight_round,
            color: Colors.deepPurple,
            count: _dataCount['sleep'],
            isDownloading: _downloading['sleep'] ?? false,
            onDownload: _downloadSleepHistory,
          ),
          _buildDownloadTile(
            title: 'Steps History',
            subtitle: _lastDownload['steps'] ?? 'Not downloaded yet',
            icon: Icons.directions_walk,
            color: Colors.green,
            count: _dataCount['steps'],
            isDownloading: _downloading['steps'] ?? false,
            onDownload: _downloadStepsHistory,
          ),
          
          const SizedBox(height: 24),
          
          // Profile & Health Section
          _buildSectionTitle('Profile & Health'),
          _buildDownloadTile(
            title: 'User Info',
            subtitle: _lastDownload['user'] ?? 'Age, weight, height, BMI',
            icon: Icons.person,
            color: Colors.blue,
            isDownloading: _downloading['user'] ?? false,
            onDownload: _downloadUserInfo,
          ),
          _buildDownloadTile(
            title: 'Sport Health',
            subtitle: _lastDownload['sport'] ?? 'VO2 Max, HRV, Stress, Stamina',
            icon: Icons.fitness_center,
            color: Colors.orange,
            isDownloading: _downloading['sport'] ?? false,
            onDownload: _downloadSportHealth,
          ),
          
          const SizedBox(height: 24),
          
          // Real-time Monitoring Section
          _buildSectionTitle('Real-time Monitoring'),
          _buildDownloadTile(
            title: 'Real-time Heart Rate',
            subtitle: 'Start continuous HR monitoring',
            icon: Icons.monitor_heart,
            color: Colors.pink,
            isDownloading: false,
            onDownload: _downloadRealTimeHR,
          ),
          _buildDownloadTile(
            title: 'Real-time Steps',
            subtitle: 'Start continuous steps monitoring',
            icon: Icons.directions_run,
            color: Colors.teal,
            isDownloading: false,
            onDownload: _downloadRealTimeSteps,
          ),
          
          const SizedBox(height: 32),
          
          // Analysis Tools Section
          const Text(
            '📊 Analysis Tools',
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16),
          
          // NEW: Advanced Health Dashboard - Whoop-style Recovery
          if (_connectedDevice != null)
            SizedBox(
              width: double.infinity,
              child: _buildAnalysisButton(
                title: '⭐ Health Analytics (Recovery & HRV)',
                icon: Icons.health_and_safety,
                color: Colors.green.shade700,
                onTap: () => Navigator.push(
                  context, 
                  MaterialPageRoute(
                    builder: (_) => AdvancedHealthDashboard(
                      device: _connectedDevice!,
                      service: _service,
                    ),
                  ),
                ),
              ),
            ),
          if (_connectedDevice != null) const SizedBox(height: 12),
          
          // OLD: Simple Dashboard - Real-time data (DEPRECATED - will be removed)
          if (_connectedDevice != null)
            SizedBox(
              width: double.infinity,
              child: _buildAnalysisButton(
                title: 'Dashboard (Real-time) - OLD',
                icon: Icons.dashboard,
                color: Colors.grey,
                onTap: () => Navigator.push(
                  context, 
                  MaterialPageRoute(
                    builder: (_) => DashboardScreen(
                      device: _connectedDevice!,
                      service: _service,
                    ),
                  ),
                ),
              ),
            ),
          if (_connectedDevice != null) const SizedBox(height: 12),
          
          Row(
            children: [
              Expanded(
                child: _buildAnalysisButton(
                  title: 'HR Analysis',
                  icon: Icons.show_chart,
                  color: Colors.red,
                  onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => GrokHrScreen(service: _service))),
                ),
              ),
              const SizedBox(width: 12),
              Expanded(
                child: _buildAnalysisButton(
                  title: 'Sleep Premium',
                  icon: Icons.bedtime,
                  color: Colors.deepPurple,
                  onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => SleepPremiumScreen(chileafService: _service))),
                ),
              ),
            ],
          ),
          const SizedBox(height: 12),
          SizedBox(
            width: double.infinity,
            child: _buildAnalysisButton(
              title: 'Advanced Features',
              icon: Icons.settings,
              color: Colors.blueGrey,
              onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => AdvancedFeaturesTestScreen(service: _service))),
            ),
          ),
          const SizedBox(height: 12),
          SizedBox(
            width: double.infinity,
            child: _buildAnalysisButton(
              title: '🌍 Timezone Test (UTC Fix)',
              icon: Icons.schedule,
              color: Colors.teal,
              onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const TimezoneTestScreen())),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSectionTitle(String title) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Text(
        title,
        style: TextStyle(
          fontSize: 18,
          fontWeight: FontWeight.w600,
          color: Colors.grey.shade700,
        ),
      ),
    );
  }

  Widget _buildDownloadTile({
    required String title,
    required String subtitle,
    required IconData icon,
    required Color color,
    int? count,
    required bool isDownloading,
    required VoidCallback onDownload,
  }) {
    return Card(
      margin: const EdgeInsets.only(bottom: 8),
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: color.withOpacity(0.1),
          child: Icon(icon, color: color, size: 22),
        ),
        title: Row(
          children: [
            Expanded(child: Text(title, style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 15))),
            if (count != null)
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                decoration: BoxDecoration(
                  color: color.withOpacity(0.1),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Text(
                  '$count',
                  style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: color),
                ),
              ),
          ],
        ),
        subtitle: Text(subtitle, style: const TextStyle(fontSize: 13)),
        trailing: isDownloading
            ? const SizedBox(
                width: 24,
                height: 24,
                child: CircularProgressIndicator(strokeWidth: 2),
              )
            : IconButton(
                icon: Icon(Icons.download, color: color),
                onPressed: onDownload,
                tooltip: 'Download',
              ),
      ),
    );
  }

  Widget _buildAnalysisButton({
    required String title,
    required IconData icon,
    required Color color,
    required VoidCallback onTap,
  }) {
    return ElevatedButton.icon(
      onPressed: onTap,
      icon: Icon(icon),
      label: Text(title),
      style: ElevatedButton.styleFrom(
        backgroundColor: color,
        foregroundColor: Colors.white,
        padding: const EdgeInsets.symmetric(vertical: 16),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      ),
    );
  }
}
