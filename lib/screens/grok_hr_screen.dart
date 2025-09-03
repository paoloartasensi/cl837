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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 GROK HR Test'),
        backgroundColor: Colors.blue.shade700,
        foregroundColor: Colors.white,
      ),
      body: Padding(
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
            Expanded(
              child: Card(
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
                      Expanded(
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
