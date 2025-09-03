import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:intl/intl.dart';
import '../chileaf_extended_service.dart';
import '../models/historical_data.dart';

class GrokHrScreen extends StatefulWidget {
  const GrokHrScreen({super.key});

  @override
  State<GrokHrScreen> createState() => _GrokHrScreenState();
}

class _GrokHrScreenState extends State<GrokHrScreen> {
  final ChileafExtendedService _service = ChileafExtendedService();
  
  // BLE Connection variables
  BluetoothDevice? connectedDevice;
  List<BluetoothDevice> foundDevices = [];
  bool isScanning = false;
  bool isConnecting = false;
  String connectionStatus = 'Disconnected';
  
  // HR Data variables
  List<HeartRateHistoryData> _hrHistoryData = [];
  bool _isDownloading = false;
  String _statusMessage = 'Ready to connect';
  StreamSubscription? _hrDataSubscription;
  StreamSubscription<BluetoothConnectionState>? _connectionSubscription;

  @override
  void initState() {
    super.initState();
    _initializeBluetooth();
    _setupStreams();
  }

  @override
  void dispose() {
    _hrDataSubscription?.cancel();
    _connectionSubscription?.cancel();
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
    _hrDataSubscription = _service.hrHistoryDataStream.listen((data) {
      setState(() {
        _hrHistoryData = [data]; // Wrap single data in list for compatibility
        _isDownloading = false;
        _statusMessage = 'HR data downloaded successfully';
      });
    });
  }

  Future<void> _startScan() async {
    if (isScanning) return;
    
    setState(() {
      isScanning = true;
      foundDevices.clear();
      _statusMessage = 'Scanning for devices...';
    });

    try {
      await FlutterBluePlus.startScan(timeout: const Duration(seconds: 10));
      
      FlutterBluePlus.scanResults.listen((results) {
        setState(() {
          foundDevices = results
              .where((r) => r.device.platformName.isNotEmpty)
              .map((r) => r.device)
              .toSet()
              .toList();
        });
      });

      await Future.delayed(const Duration(seconds: 10));
      await FlutterBluePlus.stopScan();
      
      if (mounted) {
        setState(() {
          isScanning = false;
          _statusMessage = foundDevices.isEmpty 
              ? 'No devices found' 
              : 'Found ${foundDevices.length} devices';
        });
      }
    } catch (e) {
      setState(() {
        isScanning = false;
        _statusMessage = 'Scan error: $e';
      });
    }
  }

  Future<void> _connectToDevice(BluetoothDevice device) async {
    if (isConnecting) return;

    setState(() {
      isConnecting = true;
      _statusMessage = 'Connecting to ${device.platformName}...';
    });

    try {
      // Disconnect any existing connection
      if (connectedDevice != null) {
        await connectedDevice!.disconnect();
      }

      // Connect to new device
      await device.connect(timeout: const Duration(seconds: 15));
      
      // Listen to connection state
      _connectionSubscription = device.connectionState.listen((state) {
        setState(() {
          connectionStatus = state.toString().split('.').last;
          if (state == BluetoothConnectionState.connected) {
            connectedDevice = device;
            _statusMessage = 'Connected to ${device.platformName}';
          } else if (state == BluetoothConnectionState.disconnected) {
            connectedDevice = null;
            _statusMessage = 'Disconnected from device';
          }
        });
      });

      // Initialize the service with the connected device
      await _service.start(device);
      
      setState(() {
        isConnecting = false;
        connectedDevice = device;
        _statusMessage = 'Connected and services initialized';
      });

    } catch (e) {
      setState(() {
        isConnecting = false;
        _statusMessage = 'Connection failed: $e';
        connectionStatus = 'Failed';
      });
    }
  }

  Future<void> _downloadHRData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading HR data...';
    });

    try {
      debugPrint('🫀 Starting HR history download...');
      
      setState(() {
        _statusMessage = 'Verifying connection...';
      });
      
      debugPrint('🫀 Requesting complete HR history...');
      setState(() {
        _statusMessage = 'Requesting HR history from device...';
      });
      
      await _service.requestCompleteHRHistory();
      
      // Wait for data to arrive
      await Future.delayed(const Duration(seconds: 8));
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR download completed - check data below';
      });
      
      debugPrint('🫀 HR history download request completed');
    } catch (e) {
      debugPrint('❌ HR download failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Download Error: $e';
      });
    }
  }

  // ===== TEST METHODS =====

  Future<void> _testWorkingTimestamp() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing working timestamp...';
    });

    try {
      debugPrint('🎯 Calling testWorkingTimestamp()...');
      await _service.testWorkingTimestamp();
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Working timestamp test completed - check logs';
      });
      debugPrint('✅ testWorkingTimestamp() completed');
    } catch (e) {
      debugPrint('❌ testWorkingTimestamp() failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Test failed: $e';
      });
    }
  }

  Future<void> _testMultipleWorkingTimestamps() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing multiple timestamps...';
    });

    try {
      debugPrint('🎯 Calling testMultipleWorkingTimestamps()...');
      await _service.testMultipleWorkingTimestamps();
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Multiple timestamps test completed - check logs';
      });
      debugPrint('✅ testMultipleWorkingTimestamps() completed');
    } catch (e) {
      debugPrint('❌ testMultipleWorkingTimestamps() failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Test failed: $e';
      });
    }
  }

  Future<void> _testCompleteHRHistoryFlow() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing complete HR history flow...';
    });

    try {
      debugPrint('🎯 Calling testCompleteHRHistoryFlow()...');
      await _service.testCompleteHRHistoryFlow();
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Complete flow test completed - check logs';
      });
      debugPrint('✅ testCompleteHRHistoryFlow() completed');
    } catch (e) {
      debugPrint('❌ testCompleteHRHistoryFlow() failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Test failed: $e';
      });
    }
  }

  // ===== NEW TEST METHODS =====

  Future<void> _testRecentTimestamps() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing recent timestamps...';
    });

    try {
      debugPrint('🕒 Calling testRecentTimestamps()...');
      await _service.testRecentTimestamps();
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Recent timestamps test completed - check logs';
      });
      debugPrint('✅ testRecentTimestamps() completed');
    } catch (e) {
      debugPrint('❌ testRecentTimestamps() failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Test failed: $e';
      });
    }
  }

  Future<void> _testDeviceHasAnyHRData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Checking device data availability...';
    });

    try {
      debugPrint('🔍 Calling testDeviceHasAnyHRData()...');
      await _service.testDeviceHasAnyHRData();
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Device data check completed - check logs';
      });
      debugPrint('✅ testDeviceHasAnyHRData() completed');
    } catch (e) {
      debugPrint('❌ testDeviceHasAnyHRData() failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Test failed: $e';
      });
    }
  }

  // ===== ALTERNATIVE FEATURES TEST METHODS =====

  Future<void> _testSpO2Measurement() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing SpO2 measurement...';
    });

    try {
      debugPrint('🩸 Testing SpO2 measurement functionality...');

      // Setup callbacks
      _service.setSpO2Callbacks(
        onValueReceived: (value) {
          debugPrint('🩸 SpO2 value received: $value%');
          setState(() {
            _statusMessage = 'SpO2: $value% - Measurement active!';
          });
        },
        onComplete: () {
          debugPrint('🩸 SpO2 measurement completed');
          setState(() {
            _statusMessage = 'SpO2 measurement completed!';
            _isDownloading = false;
          });
        },
        onError: (error) {
          debugPrint('🩸 SpO2 error: $error');
          setState(() {
            _statusMessage = 'SpO2 error: $error';
            _isDownloading = false;
          });
        },
      );

      // Start measurement
      await _service.startBloodOxygenMeasurement();
      setState(() {
        _statusMessage = 'SpO2 measurement started - wait for results...';
      });

      debugPrint('✅ SpO2 measurement test initiated');
    } catch (e) {
      debugPrint('❌ SpO2 measurement test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'SpO2 test failed: $e';
      });
    }
  }

  Future<void> _testDeviceInfo() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing device info requests...';
    });

    try {
      debugPrint('📱 Testing device info functionality...');
      await _service.requestAllDeviceInfo();
      setState(() {
        _statusMessage = 'Device info requests sent - check logs';
        _isDownloading = false;
      });
      debugPrint('✅ Device info test completed');
    } catch (e) {
      debugPrint('❌ Device info test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Device info test failed: $e';
      });
    }
  }

  Future<void> _testRealtimeHR() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing real-time HR monitoring...';
    });

    try {
      debugPrint('💓 Testing real-time HR functionality...');

      // Setup HR callbacks
      _service.setHRCallbacks(
        onRealtimeHRReceived: (hr) {
          debugPrint('💓 Real-time HR: $hr bpm');
          setState(() {
            _statusMessage = 'Real-time HR: $hr bpm';
          });
        },
        onConfigReceived: (min, max, goal, alarm) {
          debugPrint('💓 HR Config: Min=$min, Max=$max, Goal=$goal, Alarm=$alarm');
        },
        onHRStatusChanged: (status) {
          debugPrint('💓 HR Status: $status');
        },
      );

      // The real-time HR should start automatically when connected
      // Wait a bit to see if we get data
      await Future.delayed(const Duration(seconds: 5));

      setState(() {
        _statusMessage = 'Real-time HR test completed - check logs';
        _isDownloading = false;
      });

      debugPrint('✅ Real-time HR test completed');
    } catch (e) {
      debugPrint('❌ Real-time HR test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Real-time HR test failed: $e';
      });
    }
  }

  // ===== OFFICIAL PROTOCOL TEST METHODS =====

  Future<void> _testOfficialHRHistoryList() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing official HR history list (0x21)...';
    });

    try {
      debugPrint('📋 Testing OFFICIAL HR History List (0x21)...');
      await _service.requestHRHistoryList();
      setState(() {
        _statusMessage = 'Official HR list command sent - check logs for 0x21 response';
        _isDownloading = false;
      });
      debugPrint('✅ Official HR history list test completed');
    } catch (e) {
      debugPrint('❌ Official HR history list test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Official HR list test failed: $e';
      });
    }
  }

  Future<void> _testOfficialHRHistoryFlow() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing official complete HR history flow...';
    });

    try {
      debugPrint('🔄 Testing OFFICIAL Complete HR History Flow...');
      await _service.testCompleteHRHistoryFlow();
      setState(() {
        _statusMessage = 'Official HR flow test completed - check logs';
        _isDownloading = false;
      });
      debugPrint('✅ Official complete HR history flow test completed');
    } catch (e) {
      debugPrint('❌ Official HR flow test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Official HR flow test failed: $e';
      });
    }
  }

  Future<void> _testOfficialDeviceHasHRData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing if device has HR data (official protocol)...';
    });

    try {
      debugPrint('🔍 Testing OFFICIAL Device HR Data Availability...');
      await _service.testDeviceHasHRData();
      setState(() {
        _statusMessage = 'Official HR data check completed - check logs';
        _isDownloading = false;
      });
      debugPrint('✅ Official device HR data test completed');
    } catch (e) {
      debugPrint('❌ Official device HR data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Official HR data test failed: $e';
      });
    }
  }

  Future<void> _testWithoutSyncUTC() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing HR history WITHOUT UTC sync...';
    });

    try {
      debugPrint('🚫 Testing HR History WITHOUT UTC sync (to see if data reappears)...');
      await _service.requestHRHistoryList();
      setState(() {
        _statusMessage = 'HR test without sync completed - check logs';
        _isDownloading = false;
      });
      debugPrint('✅ HR test without UTC sync completed');
    } catch (e) {
      debugPrint('❌ HR test without sync failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR test without sync failed: $e';
      });
    }
  }

  Future<void> _generateHRDataNow() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Generating HR data in real-time...';
    });

    try {
      debugPrint('💓 GENERATING HR DATA NOW - Start monitoring HR...');
      
      // Setup HR callbacks to monitor real-time data
      _service.setHRCallbacks(
        onRealtimeHRReceived: (hr) {
          debugPrint('💓 REAL-TIME HR: $hr bpm - Data being recorded!');
          setState(() {
            _statusMessage = 'Recording HR: $hr bpm - Keep device on wrist!';
          });
        },
        onConfigReceived: (min, max, goal, alarm) {
          debugPrint('💓 HR Config received');
        },
        onHRStatusChanged: (status) {
          debugPrint('💓 HR Status: $status');
        },
      );

      debugPrint('💓 INSTRUCTIONS:');
      debugPrint('   1. Keep device on your wrist');
      debugPrint('   2. Move around or do light exercise');
      debugPrint('   3. Watch for real-time HR values');
      debugPrint('   4. After 2-3 minutes, test HR history again');

      setState(() {
        _statusMessage = 'HR monitoring active! Move around to generate data...';
        _isDownloading = false;
      });

    } catch (e) {
      debugPrint('❌ HR data generation failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR data generation failed: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 GROK HR Test'),
        backgroundColor: Colors.blue.shade700,
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // Connection Status Card
            Card(
              color: _getConnectionStatusColor(),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    Text(
                      'Connection Status: $connectionStatus',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    if (connectedDevice != null)
                      Text(
                        'Device: ${connectedDevice!.platformName}',
                        style: const TextStyle(color: Colors.white70),
                      ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Control Buttons
            if (connectedDevice == null) ...[
              ElevatedButton.icon(
                onPressed: isScanning ? null : _startScan,
                icon: isScanning 
                    ? const SizedBox(
                        width: 16, 
                        height: 16, 
                        child: CircularProgressIndicator(strokeWidth: 2)
                      )
                    : const Icon(Icons.search),
                label: Text(isScanning ? 'Scanning...' : 'Scan for Devices'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),
              
              const SizedBox(height: 12),
              
              // Device List
              if (foundDevices.isNotEmpty) ...[
                const Text(
                  'Found Devices:',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                ...foundDevices.map((device) => Card(
                  child: ListTile(
                    title: Text(device.platformName),
                    subtitle: Text(device.remoteId.toString()),
                    trailing: ElevatedButton(
                      onPressed: isConnecting ? null : () => _connectToDevice(device),
                      child: isConnecting 
                          ? const SizedBox(
                              width: 16, 
                              height: 16, 
                              child: CircularProgressIndicator(strokeWidth: 2)
                            )
                          : const Text('Connect'),
                    ),
                  ),
                )),
              ],
            ] else ...[
              // Connected Device Controls
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadHRData,
                icon: _isDownloading 
                    ? const SizedBox(
                        width: 16, 
                        height: 16, 
                        child: CircularProgressIndicator(strokeWidth: 2)
                      )
                    : const Icon(Icons.download),
                label: Text(_isDownloading ? 'Downloading...' : 'Download HR History'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 16),

              // Test Methods Section
              const Text(
                '🧪 HR History Test Methods:',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.blue,
                ),
              ),
              const SizedBox(height: 8),

              // Test Working Timestamp Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testWorkingTimestamp,
                icon: const Icon(Icons.access_time),
                label: const Text('Test Working Timestamp'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Multiple Timestamps Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testMultipleWorkingTimestamps,
                icon: const Icon(Icons.schedule),
                label: const Text('Test Multiple Timestamps'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.indigo,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Complete Flow Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testCompleteHRHistoryFlow,
                icon: const Icon(Icons.sync),
                label: const Text('Test Complete Flow'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Recent Timestamps Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testRecentTimestamps,
                icon: const Icon(Icons.today),
                label: const Text('Test Recent Days'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepOrange,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Device Data Availability Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testDeviceHasAnyHRData,
                icon: const Icon(Icons.search),
                label: const Text('Check Device Data'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepPurple,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 16),

              // Official Protocol Test Methods Section
              const Text(
                '🔬 OFFICIAL PROTOCOL TESTS:',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.orange,
                ),
              ),
              const SizedBox(height: 8),

              // Test Official HR History List Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testOfficialHRHistoryList,
                icon: const Icon(Icons.list),
                label: const Text('Official HR List (0x21)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.orange,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Official Complete Flow Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testOfficialHRHistoryFlow,
                icon: const Icon(Icons.sync_alt),
                label: const Text('Official Complete Flow'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepOrange,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Official Device Data Check Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testOfficialDeviceHasHRData,
                icon: const Icon(Icons.verified),
                label: const Text('Official Data Check'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.amber,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test WITHOUT UTC Sync Button  
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testWithoutSyncUTC,
                icon: const Icon(Icons.sync_disabled),
                label: const Text('Test WITHOUT UTC Sync'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Generate HR Data Now Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _generateHRDataNow,
                icon: const Icon(Icons.favorite_border),
                label: const Text('Generate HR Data NOW'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 16),

              // Alternative Features Section
              const Text(
                '🎯 ALTERNATIVE FEATURES TO TEST:',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.green,
                ),
              ),
              const SizedBox(height: 8),

              // Test SpO2 Measurement Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testSpO2Measurement,
                icon: const Icon(Icons.bloodtype),
                label: const Text('Test SpO2 Measurement'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Device Info Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testDeviceInfo,
                icon: const Icon(Icons.info),
                label: const Text('Test Device Info'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Test Real-time HR Button
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testRealtimeHR,
                icon: const Icon(Icons.favorite),
                label: const Text('Test Real-time HR'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.pink,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),
            ],
            
            const SizedBox(height: 16),
            
            // Status Message
            Card(
              color: Colors.grey.shade100,
              child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Text(
                  _statusMessage,
                  style: const TextStyle(fontSize: 14),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // HR Data Display
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'HR History Data (${_hrHistoryData.length} entries)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 300, // Fixed height instead of Expanded
                      child: _hrHistoryData.isEmpty
                          ? const Center(
                              child: Text(
                                'No HR data downloaded yet.\nConnect to device and download history.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _hrHistoryData.length,
                              itemBuilder: (context, index) {
                                final data = _hrHistoryData[index];
                                return Card(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  child: ExpansionTile(
                                    title: Text(
                                      'HR History - ${DateFormat('yyyy-MM-dd HH:mm:ss').format(data.timestamp)}',
                                      style: const TextStyle(fontWeight: FontWeight.bold),
                                    ),
                                    subtitle: Text('${data.entries.length} entries'),
                                    children: data.entries.map((entry) => ListTile(
                                      leading: CircleAvatar(
                                        backgroundColor: _getHRColor(entry.heartRate),
                                        child: Text(
                                          '${entry.heartRate}',
                                          style: const TextStyle(
                                            color: Colors.white,
                                            fontWeight: FontWeight.bold,
                                            fontSize: 12,
                                          ),
                                        ),
                                      ),
                                      title: Text(
                                        'HR: ${entry.heartRate} bpm',
                                        style: const TextStyle(fontWeight: FontWeight.bold),
                                      ),
                                      subtitle: Text(
                                        'Time: ${DateFormat('HH:mm:ss').format(entry.time)}\nActivity: ${entry.activityIndex}',
                                      ),
                                    )).toList(),
                                  ),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Color _getConnectionStatusColor() {
    switch (connectionStatus) {
      case 'connected':
        return Colors.green;
      case 'connecting':
        return Colors.orange;
      case 'disconnected':
        return Colors.red;
      default:
        return Colors.grey;
    }
  }

  Color _getHRColor(int heartRate) {
    if (heartRate < 60) return Colors.blue;
    if (heartRate < 100) return Colors.green;
    if (heartRate < 150) return Colors.orange;
    return Colors.red;
  }
}
