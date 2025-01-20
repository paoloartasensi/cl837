import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'accelerometer_service.dart';
import 'battery.dart';
import 'heartrate.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  FlutterBluePlus.setLogLevel(LogLevel.verbose, color: true);
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'CL837 Sensor Display',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const SensorDisplayPage(),
    );
  }
}

class SensorDisplayPage extends StatefulWidget {
  const SensorDisplayPage({Key? key}) : super(key: key);

  @override
  State<SensorDisplayPage> createState() => _SensorDisplayPageState();
}

class _SensorDisplayPageState extends State<SensorDisplayPage> {
  final SensorService _sensorService = SensorService();
  final HeartRateService _heartRateService = HeartRateService();
  final BatteryService _batteryService = BatteryService();
  static const String targetDeviceName = 'CL837-0753644';
  BluetoothDevice? connectedDevice;
  SensorData? latestData;
  bool isScanning = false;
  bool isConnecting = false;
  late StreamSubscription<SensorData> _dataSubscription;

  @override
  void initState() {
    super.initState();
    _initializeBluetooth();
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

  Widget _buildStatusRow(String label, dynamic value, IconData icon, {Color? color}) {
    return Card(
      elevation: 2,
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Row(
          children: [
            Icon(icon, size: 24, color: color),
            const SizedBox(width: 12),
            Text(
              label,
              style: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            const Spacer(),
            Text(
              value?.toString() ?? 'N/A',
              style: TextStyle(
                fontSize: 16,
                color: color,
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildAxisRow(String axis, double value, Color color) {
    return Card(
      elevation: 2,
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Container(
                  width: 24,
                  height: 24,
                  decoration: BoxDecoration(
                    color: color.withOpacity(0.2),
                    borderRadius: BorderRadius.circular(4),
                  ),
                  child: Center(
                    child: Text(
                      axis,
                      style: TextStyle(
                        color: color,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      LinearProgressIndicator(
                        value: (value + 8.0) / 16.0, // Normalize from -8g to 8g
                        backgroundColor: color.withOpacity(0.1),
                        valueColor: AlwaysStoppedAnimation<Color>(color),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        '${value.toStringAsFixed(3)} g',
                        style: TextStyle(
                          color: color,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDataCard() {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          if (latestData != null) ...[
            _buildStatusRow(
              'Heart Rate',
              latestData!.heartRate != null ? '${latestData!.heartRate} BPM' : 'N/A',
              Icons.favorite,
              color: Colors.red,
            ),
            const SizedBox(height: 8),
            _buildStatusRow(
              'Battery',
              latestData!.batteryLevel != null ? '${latestData!.batteryLevel}%' : 'N/A',
              Icons.battery_full,
              color: _getBatteryColor(latestData!.batteryLevel),
            ),
            const SizedBox(height: 16),
            const Text(
              'Accelerometer',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 8),
            _buildAxisRow('X', latestData!.x, Colors.red),
            const SizedBox(height: 8),
            _buildAxisRow('Y', latestData!.y, Colors.green),
            const SizedBox(height: 8),
            _buildAxisRow('Z', latestData!.z, Colors.blue),
          ] else
            const Center(
              child: Text(
                'Waiting for data...',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.grey,
                ),
              ),
            ),
        ],
      ),
    );
  }

  Color _getBatteryColor(int? level) {
    if (level == null) return Colors.grey;
    if (level > 60) return Colors.green;
    if (level > 30) return Colors.orange;
    return Colors.red;
  }

  Future<void> startScan() async {
    if (isScanning) return;
    setState(() {
      isScanning = true;
    });
    try {
      await FlutterBluePlus.startScan(timeout: const Duration(seconds: 5));
      FlutterBluePlus.scanResults.listen((results) {
        for (ScanResult r in results) {
          debugPrint('Found device: ${r.device.name}');
          if (r.device.name == targetDeviceName) {
            connectToDevice(r.device);
            FlutterBluePlus.stopScan();
            break;
          }
        }
      });
      await Future.delayed(const Duration(seconds: 5));
      if (mounted) {
        setState(() {
          isScanning = false;
        });
      }
    } catch (e) {
      debugPrint('Error during scan: $e');
      if (mounted) {
        setState(() {
          isScanning = false;
        });
      }
      showError('Error during scan: $e');
    }
  }

  Future<void> connectToDevice(BluetoothDevice device) async {
    if (isConnecting) return;
    setState(() {
      isConnecting = true;
    });
    try {
      await device.connect(timeout: const Duration(seconds: 10));
      await _sensorService.start(device, _heartRateService, _batteryService);
      _dataSubscription = _sensorService.dataStream.listen((data) {
        setState(() {
          latestData = data;
        });
      });
      if (mounted) {
        setState(() {
          connectedDevice = device;
          isConnecting = false;
        });
      }
    } catch (e) {
      debugPrint('Error during connection: $e');
      if (mounted) {
        setState(() {
          isConnecting = false;
        });
      }
      showError('Error during connection: $e');
    }
  }

  Future<void> disconnectDevice() async {
    try {
      await _dataSubscription.cancel();
      await _sensorService.stop();
      await _heartRateService.stop();
      await _batteryService.stop();
      await connectedDevice?.disconnect();
    } catch (e) {
      debugPrint('Error during disconnect: $e');
    }
    if (mounted) {
      setState(() {
        connectedDevice = null;
        latestData = null;
      });
    }
  }

  void showError(String message) {
    if (!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: Colors.red,
        duration: const Duration(seconds: 3),
      ),
    );
  }

  @override
  void dispose() {
    _dataSubscription.cancel();
    _sensorService.dispose();
    _heartRateService.dispose();
    _batteryService.dispose();
    disconnectDevice();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Sensor Display'),
        actions: [
          if (connectedDevice != null)
            IconButton(
              icon: const Icon(Icons.bluetooth_connected),
              tooltip: 'Disconnect',
              onPressed: disconnectDevice,
            )
        ],
      ),
      body: Column(
        children: [
          Container(
            color: Colors.grey[100],
            padding: const EdgeInsets.all(16.0),
            child: Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    icon: const Icon(Icons.search),
                    label: Text(
                      isScanning ? 'Scanning...' : isConnecting ? 'Connecting...' : 'Scan for Device',
                    ),
                    onPressed: (isScanning || isConnecting) ? null : startScan,
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: _buildDataCard(),
          ),
        ],
      ),
    );
  }
}
