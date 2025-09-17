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
  List<DateTime> _hrRecordList = [];
  
  // Sleep and Steps data variables
  final List<SleepHistoryEntry> _sleepHistoryData = [];
  final List<StepIntervalEntry> _stepsHistoryData = [];
  
  bool _isDownloading = false;
  String _statusMessage = 'Ready to connect';
  StreamSubscription? _hrDataSubscription;
  StreamSubscription? _hrListSubscription;
  StreamSubscription? _sleepDataSubscription;
  StreamSubscription? _stepsDataSubscription;
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
    _hrListSubscription?.cancel();
    _sleepDataSubscription?.cancel();
    _stepsDataSubscription?.cancel();
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
        _statusMessage = 'HR data downloaded successfully - ${data.entries.length} measurements';
      });
    });
    
    _hrListSubscription = _service.hrHistoryListStream.listen((list) {
      setState(() {
        _hrRecordList = list.timestamps;
        _statusMessage = 'HR record list received - ${list.timestamps.length} timestamps';
      });
    });
    
    _sleepDataSubscription = _service.sleepHistoryStream.listen((sleepData) {
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data received - ${sleepData.length} sessions';
      });
    });
    
    _stepsDataSubscription = _service.stepsHistoryStream.listen((stepsData) {
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Steps data received - ${stepsData.length} intervals';
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
      _statusMessage = 'Testing WearManager protocol...';
    });

    try {
      debugPrint('🫀 WEARMANAGER PROTOCOL TEST');
      
      // Test completo del protocollo WearManager
      setState(() {
        _statusMessage = 'Running complete WearManager sequence...';
      });
      
      await _service.performCompleteHRHistorySequence();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'WearManager protocol test completed! Check logs.';
      });
      
      debugPrint('✅ WearManager protocol test completed!');
    } catch (e) {
      debugPrint('❌ WearManager protocol test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'WearManager protocol test failed: $e';
      });
    }
  }

  // ===== WEARMANAGER TEST METHODS =====

  Future<void> _testUTCSync() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing UTC synchronization...';
    });

    try {
      debugPrint('⏰ Testing UTC sync (WearManager setUTCTime)');
      await _service.setUTCTime();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'UTC sync completed! Check logs for details.';
      });
      
      debugPrint('✅ UTC sync test completed!');
    } catch (e) {
      debugPrint('❌ UTC sync failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'UTC sync failed: $e';
      });
    }
  }

  Future<void> _testHRRecordList() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing HR record list (0x21)...';
    });

    try {
      debugPrint('📋 Testing HR record list (WearManager getHistoryOfHRRecord)');
      await _service.getHistoryOfHRRecord();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR record list test completed! Check logs for timestamps.';
      });
      
      debugPrint('✅ HR record list test completed!');
    } catch (e) {
      debugPrint('❌ HR record list test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR record list test failed: $e';
      });
    }
  }

  Future<void> _testHRData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing HR data retrieval (0x22)...';
    });

    try {
      debugPrint('💓 Testing HR data (WearManager getHistoryOfHRData)');
      
      // Test con timestamp recente
      int recentTimestamp = DateTime.now().subtract(const Duration(days: 1)).millisecondsSinceEpoch ~/ 1000;
      await _service.getHistoryOfHRData(recentTimestamp);
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR data test completed! Check logs for HR measurements.';
      });
      
      debugPrint('✅ HR data test completed!');
    } catch (e) {
      debugPrint('❌ HR data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR data test failed: $e';
      });
    }
  }

  Future<void> _testSleepData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing sleep data retrieval (0x05)...';
    });

    try {
      debugPrint('🌙 Testing sleep data (WearManager getHistoryOfSleep)');
      await _service.getHistoryOfSleep();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data test completed! Check logs for sleep sessions.';
      });
      
      debugPrint('✅ Sleep data test completed!');
    } catch (e) {
      debugPrint('❌ Sleep data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data test failed: $e';
      });
    }
  }

  Future<void> _testStepsData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing steps data retrieval (0x40)...';
    });

    try {
      debugPrint('👟 Testing steps data (WearManager getIntervalSteps)');
      await _service.getIntervalSteps();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Steps data test completed! Check logs for step intervals.';
      });
      
      debugPrint('✅ Steps data test completed!');
    } catch (e) {
      debugPrint('❌ Steps data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Steps data test failed: $e';
      });
    }
  }

  Future<void> _downloadAllData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading ALL data (HR + Sleep + Steps)...';
    });

    try {
      debugPrint('🔄 Starting complete data sequence (HR + Sleep + Steps)');
      await _service.performCompleteDataSequence();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Complete data sequence finished! Check UI sections below.';
      });
      
      debugPrint('✅ Complete data sequence completed!');
    } catch (e) {
      debugPrint('❌ Complete data sequence failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Complete data sequence failed: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 WearManager HR Test'),
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
                label: Text(_isDownloading ? 'Running...' : '🔄 Complete WearManager Test'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 16),

              // WearManager HR Test Section
              const Text(
                '🫀 HR History Test (WearManager Compatible):',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.blue,
                ),
              ),
              const SizedBox(height: 8),

              // UTC Sync Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testUTCSync,
                icon: const Icon(Icons.sync),
                label: const Text('⏰ Sync UTC Time (0x08)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.orange.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // HR Record List Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testHRRecordList,
                icon: const Icon(Icons.schedule),
                label: const Text('📋 Get HR Record List (0x21)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // HR Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testHRData,
                icon: const Icon(Icons.data_usage),
                label: const Text('💓 Get HR Data (0x22)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 20),

              // Sleep and Steps Test Section
              const Text(
                '🌙👟 Sleep & Steps Test (WearManager Compatible):',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.purple,
                ),
              ),
              const SizedBox(height: 8),

              // Sleep Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testSleepData,
                icon: const Icon(Icons.nightlight_round),
                label: const Text('🌙 Get Sleep Data (0x05)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // Steps Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testStepsData,
                icon: const Icon(Icons.directions_walk),
                label: const Text('👟 Get Steps Data (0x40)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // Complete Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadAllData,
                icon: const Icon(Icons.download_for_offline),
                label: const Text('📊 Download ALL Data (HR+Sleep+Steps)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.indigo.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
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
                                'No HR data downloaded yet.\nConnect to device and test WearManager protocol.',
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
            
            const SizedBox(height: 16),
            
            // HR Record List Display
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'HR Record Timestamps (${_hrRecordList.length} entries)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 200, // Fixed height for record list
                      child: _hrRecordList.isEmpty
                          ? const Center(
                              child: Text(
                                'No HR record list downloaded yet.\nUse "Get HR Record List" button first.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _hrRecordList.length,
                              itemBuilder: (context, index) {
                                final timestamp = _hrRecordList[index];
                                return ListTile(
                                  leading: CircleAvatar(
                                    backgroundColor: Colors.blue,
                                    child: Text(
                                      '${index + 1}',
                                      style: const TextStyle(
                                        color: Colors.white,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 12,
                                      ),
                                    ),
                                  ),
                                  title: Text(
                                    DateFormat('yyyy-MM-dd HH:mm:ss').format(timestamp),
                                    style: const TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                  subtitle: Text(
                                    'HR session recorded at this timestamp',
                                    style: TextStyle(color: Colors.grey[600]),
                                  ),
                                  trailing: const Icon(Icons.schedule, color: Colors.blue),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Sleep Data Display
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Sleep History (${_sleepHistoryData.length} sessions)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 250, // Fixed height for sleep list
                      child: _sleepHistoryData.isEmpty
                          ? const Center(
                              child: Text(
                                'No sleep data downloaded yet.\nUse "Get Sleep Data" button to download.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _sleepHistoryData.length,
                              itemBuilder: (context, index) {
                                final sleep = _sleepHistoryData[index];
                                final phases = sleep.calculateSleepPhases();
                                return Card(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  child: ExpansionTile(
                                    title: Text(
                                      'Sleep ${DateFormat('MMM dd, HH:mm').format(sleep.timestamp)}',
                                      style: const TextStyle(fontWeight: FontWeight.bold),
                                    ),
                                    subtitle: Text(
                                      'Total: ${phases.totalSleep}min (Light: ${phases.lightSleep}min, Deep: ${phases.deepSleep}min)',
                                    ),
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(16.0),
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            Text('💤 Sleep Efficiency: ${phases.sleepEfficiency.toStringAsFixed(1)}%'),
                                            Text('⏰ Total Duration: ${phases.totalMinutes} minutes'),
                                            Text('🌙 Light Sleep: ${phases.lightSleep} minutes'),
                                            Text('🌊 Deep Sleep: ${phases.deepSleep} minutes'),
                                            Text('😴 Awake Time: ${phases.awake} minutes'),
                                            Text('📊 Actions Recorded: ${sleep.actions.length}'),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Steps Data Display
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Steps History (${_stepsHistoryData.length} intervals)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 250, // Fixed height for steps list
                      child: _stepsHistoryData.isEmpty
                          ? const Center(
                              child: Text(
                                'No steps data downloaded yet.\nUse "Get Steps Data" button to download.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _stepsHistoryData.length,
                              itemBuilder: (context, index) {
                                final steps = _stepsHistoryData[index];
                                return ListTile(
                                  leading: CircleAvatar(
                                    backgroundColor: _getStepsColor(steps.steps),
                                    child: Text(
                                      '${steps.steps}',
                                      style: const TextStyle(
                                        color: Colors.white,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 10,
                                      ),
                                    ),
                                  ),
                                  title: Text(
                                    '👟 ${steps.steps} steps',
                                    style: const TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                  subtitle: Text(
                                    'Time: ${DateFormat('MMM dd, HH:mm').format(steps.timestamp)}',
                                  ),
                                  trailing: Icon(
                                    steps.steps > 500 ? Icons.directions_run : Icons.directions_walk,
                                    color: _getStepsColor(steps.steps),
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

  Color _getStepsColor(int steps) {
    if (steps < 100) return Colors.red;
    if (steps < 300) return Colors.orange;
    if (steps < 500) return Colors.blue;
    if (steps < 1000) return Colors.green;
    return Colors.purple;
  }
}