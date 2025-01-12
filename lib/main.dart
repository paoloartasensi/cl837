import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:intl/intl.dart';
import 'accelerometer_service.dart';

void main() async {
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
      title: 'CL837 Accelerometer',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const BluetoothAccelerometerPage(),
    );
  }
}

class BluetoothAccelerometerPage extends StatefulWidget {
  const BluetoothAccelerometerPage({Key? key}) : super(key: key);

  @override
  State<BluetoothAccelerometerPage> createState() => _BluetoothAccelerometerPageState();
}

class _BluetoothAccelerometerPageState extends State<BluetoothAccelerometerPage> {
  final AccelerometerService _accelerometerService = AccelerometerService();
  static const String targetDeviceName = 'CL837-0753644';
  BluetoothDevice? connectedDevice;
  AccelerometerData? latestData;
  bool isScanning = false;
  bool isConnecting = false;
  late StreamSubscription<AccelerometerData> _dataSubscription;
  FrequencyControlMode _controlMode = FrequencyControlMode.hardware;
  TargetRate _selectedRate = TargetRate.hz100;

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

  Widget _buildControlsCard() {
    return Card(
      elevation: 4,
      margin: const EdgeInsets.all(16.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Control Mode',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            DropdownButton<FrequencyControlMode>(
              value: _controlMode,
              isExpanded: true,
              items: FrequencyControlMode.values.map((mode) {
                return DropdownMenuItem<FrequencyControlMode>(
                  value: mode,
                  child: Text(mode.label),
                );
              }).toList(),
              onChanged: (mode) {
                if (mode != null) {
                  setState(() {
                    _controlMode = mode;
                    _accelerometerService.setControlMode(mode);
                  });
                }
              },
            ),
            const SizedBox(height: 16),
            const Text(
              'Target Rate',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            DropdownButton<TargetRate>(
              value: _selectedRate,
              isExpanded: true,
              items: TargetRate.values.map((rate) {
                return DropdownMenuItem<TargetRate>(
                  value: rate,
                  child: Text(rate.label),
                );
              }).toList(),
              onChanged: (rate) async {
                if (rate != null) {
                  try {
                    setState(() {
                      _selectedRate = rate;
                    });
                    _accelerometerService.setTargetRate(rate);
                    if (mounted) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text('Target rate set to ${rate.label}'),
                          duration: const Duration(seconds: 2),
                        ),
                      );
                    }
                  } catch (e) {
                    if (mounted) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text('Error setting rate: $e'),
                          backgroundColor: Colors.red,
                          duration: const Duration(seconds: 3),
                        ),
                      );
                    }
                  }
                }
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDataCard() {
    return Card(
      elevation: 4,
      margin: const EdgeInsets.all(16.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  'Accelerometer Data',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                if (latestData != null)
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      Text(
                        DateFormat('HH:mm:ss.SSS').format(latestData!.timestamp),
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                      Text(
                        'Rate: ${latestData!.frequency.toStringAsFixed(1)} Hz',
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                      Text(
                        'Interval: ${latestData!.interval.toStringAsFixed(1)} ms',
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                    ],
                  ),
              ],
            ),
            const SizedBox(height: 16),
            if (latestData != null) ...[
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
      ),
    );
  }

  Widget _buildAxisRow(String axis, double value, Color color) {
    return Row(
      children: [
        SizedBox(
          width: 30,
          child: Text(
            axis,
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
        ),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              LinearProgressIndicator(
                value: (value + 8.0) / 16.0, // Normalize from -8g to +8g
                backgroundColor: color.withOpacity(0.1),
                valueColor: AlwaysStoppedAnimation<Color>(color),
              ),
              const SizedBox(height: 4),
              Text(
                '${value.toStringAsFixed(3)} g',
                style: const TextStyle(
                  fontFamily: 'Monospace',
                  fontSize: 14,
                ),
              ),
            ],
          ),
        ),
      ],
    );
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
      await _accelerometerService.start(device);
      _dataSubscription = _accelerometerService.dataStream.listen((data) {
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
      await _accelerometerService.stop();
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
    _accelerometerService.dispose();
    disconnectDevice();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Accelerometer'),
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
                      isScanning ? 'Scanning...' :
                      isConnecting ? 'Connecting...' :
                      'Scan for Device'
                    ),
                    onPressed: (isScanning || isConnecting) ? null : startScan,
                  ),
                ),
              ],
            ),
          ),
          if (connectedDevice != null) ...[
            _buildControlsCard(),
            Expanded(
              child: SingleChildScrollView(
                child: _buildDataCard(),
              ),
            ),
          ],
        ],
      ),
    );
  }
}

