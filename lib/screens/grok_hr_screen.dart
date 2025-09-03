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
      _statusMessage = 'Starting HR history download...';
    });

    try {
      debugPrint('🫀 🔐 SMART HR DOWNLOAD: UTC SYNC FIRST STRATEGY!');
      
      // STEP 1: UTC Sync to unlock historical data  
      setState(() {
        _statusMessage = '🔐 Phase 1: UTC sync to unlock historical data...';
      });
      debugPrint('🔐 Phase 1: UTC sync to unlock device historical data...');
      await _service.syncDeviceTime();
      
      // Wait for sync to complete
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // STEP 2: Request HR history list (now unlocked)
      setState(() {
        _statusMessage = '📋 Phase 2: Requesting HR timestamp list...';
      });
      debugPrint('📋 Phase 2: Requesting HR timestamp list after UTC sync...');
      await _service.requestHRHistoryList();
      
      // Wait for timestamp list
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // STEP 3: Request detailed HR data 
      setState(() {
        _statusMessage = '💓 Phase 3: Requesting detailed HR data...';
      });
      debugPrint('💓 Phase 3: Requesting detailed HR data from unlocked timestamps...');
      await _service.requestCompleteHRHistory();
      
      // Wait for data to arrive
      await Future.delayed(const Duration(seconds: 5));
      
      setState(() {
        _isDownloading = false;
        _statusMessage = '✅ Smart HR download completed! Check logs for unlocked data.';
      });
      
      debugPrint('✅ Smart HR download with UTC sync unlock completed!');
    } catch (e) {
      debugPrint('❌ Smart HR download failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Smart HR download failed: $e';
      });
    }
  }

  // ===== UTC SYNC UNLOCK TEST METHOD =====

  Future<void> _testUTCAfterTimestamps() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing UTC SYNC AFTER theory...';
    });

    try {
      debugPrint('🕐 TESTING UTC SYNC AFTER THEORY!');
      debugPrint('📋 Step 1: Get timestamp list (no UTC sync)');
      debugPrint('🔐 Step 2: UTC sync to "unlock" historical data');
      debugPrint('💓 Step 3: Try requesting HR data again');
      
      // Step 1: Get timestamps WITHOUT UTC sync first
      debugPrint('📋 Phase 1: Getting timestamp list...');
      await _service.requestHRHistoryList();
      
      // Wait a moment
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Step 2: NOW do UTC sync (this might "unlock" the data)
      debugPrint('🔐 Phase 2: UTC sync to potentially unlock data...');
      await _service.syncDeviceTime();
      
      // Wait for sync to complete
      await Future.delayed(const Duration(milliseconds: 1500));
      
      // Step 3: Try requesting data again AFTER UTC sync
      debugPrint('💓 Phase 3: Requesting HR data AFTER UTC sync...');
      await _service.requestCompleteHRHistory();
      
      setState(() {
        _statusMessage = 'UTC sync after theory test completed - check logs!';
        _isDownloading = false;
      });
      debugPrint('✅ UTC sync after theory test completed');
    } catch (e) {
      debugPrint('❌ UTC sync after theory test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'UTC sync after theory test failed: $e';
      });
    }
  }

  // ===== TEST DELLE 3 MODALITÀ 0x22 DALLA DOCUMENTAZIONE =====

  Future<void> _testAllHRRequestModes() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing all 3 HR request modes from documentation...';
    });

    try {
      debugPrint('📚 TESTING ALL 3 MODES FROM CHILEAF DOCUMENTATION!');
      debugPrint('📋 Mode 1: Request single data (param 1)');
      debugPrint('📋 Mode 2: Request all data (param 2)'); 
      debugPrint('📋 Mode 3: Request all data after UTC (param 3)');
      
      // First: UTC sync to unlock data
      debugPrint('🔐 Pre-step: UTC sync to unlock historical data...');
      await _service.syncDeviceTime();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Get timestamp list to have valid timestamps
      debugPrint('📋 Pre-step: Get timestamp list...');
      await _service.requestHRHistoryList();
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Test all 3 modes from documentation
      debugPrint('🧪 MODE 1: Request single data (current implementation)');
      await _service.testHRRequestMode1();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🧪 MODE 2: Request all data (NEW!)');
      await _service.testHRRequestMode2();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🧪 MODE 3: Request all data after UTC (NEW!)');
      await _service.testHRRequestMode3();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      setState(() {
        _statusMessage = '✅ All 3 HR request modes tested! Check logs for results.';
        _isDownloading = false;
      });
      debugPrint('✅ All 3 HR request modes from documentation tested!');
    } catch (e) {
      debugPrint('❌ HR request modes test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR request modes test failed: $e';
      });
    }
  }

  // ===== TEST PROTOCOLLO UFFICIALE SEQUENZIALE =====

  Future<void> _testOfficialProtocolSequential() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing official protocol sequential access...';
    });

    try {
      debugPrint('🎯 OFFICIAL PROTOCOL SEQUENTIAL TEST!');
      debugPrint('📖 Following EXACT documentation sequence:');
      debugPrint('   1. UTC sync (0x08) to unlock historical data');
      debugPrint('   2. Get HR timestamp list (0x21)');
      debugPrint('   3. Request each timestamp individually (0x22 Mode 1)');
      
      // Step 1: UTC sync FIRST
      debugPrint('🔐 Step 1: UTC sync to unlock historical data...');
      await _service.syncDeviceTime();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Step 2: Get ALL timestamp list
      debugPrint('📋 Step 2: Getting complete timestamp list...');
      await _service.requestHRHistoryList();
      await Future.delayed(const Duration(milliseconds: 1500));
      
      // Step 3: Request ALL timestamps sequentially using Mode 1
      debugPrint('💓 Step 3: Requesting ALL timestamps sequentially with Mode 1...');
      await _service.testSequentialHRRequests();
      
      setState(() {
        _statusMessage = '✅ Official protocol sequential test completed! Check logs.';
        _isDownloading = false;
      });
      debugPrint('✅ Official protocol sequential test completed!');
    } catch (e) {
      debugPrint('❌ Official protocol sequential test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Official protocol sequential test failed: $e';
      });
    }
  }

  // ===== ANALISI AVANZATA TIMESTAMP =====

  Future<void> _analyzeTimestampsAdvanced() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Analyzing timestamps with advanced decoder...';
    });

    try {
      debugPrint('🔬 ADVANCED TIMESTAMP ANALYSIS STARTING!');
      debugPrint('📋 Using TimestampDecoder with multiple interpretation methods');
      debugPrint('📖 Following Chileaf BLE Protocol v0.6 specifications');
      
      // Perform advanced analysis using existing service instance
      Map<String, dynamic> analysis = await _service.analyzeTimestampsAdvanced();
      
      setState(() {
        _statusMessage = '✅ Advanced timestamp analysis completed! Check logs for detailed results.';
        _isDownloading = false;
      });
      
      debugPrint('✅ Advanced timestamp analysis completed successfully!');
      
      // Print summary in UI message if analysis successful
      if (analysis['bestMethod'] != null) {
        setState(() {
          _statusMessage = '✅ Analysis complete! Best method: ${analysis['bestMethod']} (${analysis['bestMethodCount']}/${analysis['totalTimestamps']} timestamps). Check logs for details.';
        });
      }
      
    } catch (e) {
      debugPrint('❌ Advanced timestamp analysis failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Advanced timestamp analysis failed: $e';
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
                label: Text(_isDownloading ? 'Downloading...' : '🔐 Smart HR Download (UTC Sync First)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 16),

              // UTC SYNC UNLOCK Test Section
              const Text(
                '� UTC SYNC UNLOCK TEST:',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.orange,
                ),
              ),
              const SizedBox(height: 8),

              // UTC SYNC AFTER Button (Unlock Theory)
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testUTCAfterTimestamps,
                icon: const Icon(Icons.sync),
                label: const Text('🕐 UTC SYNC AFTER (Unlock Theory)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.orange.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // NEW: Test all 3 modes from documentation
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testAllHRRequestModes,
                icon: const Icon(Icons.science),
                label: const Text('📚 Test 3 Modes (Documentation)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // NEW: Official protocol sequential test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testOfficialProtocolSequential,
                icon: const Icon(Icons.timeline),
                label: const Text('🎯 Official Protocol Sequential'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // NEW: Advanced timestamp analysis
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _analyzeTimestampsAdvanced,
                icon: const Icon(Icons.analytics),
                label: const Text('🔬 Advanced Timestamp Analysis'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepPurple.shade700,
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
