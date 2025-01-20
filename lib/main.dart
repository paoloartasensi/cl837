import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'accelerometer_service.dart';
import 'battery.dart';
import 'heartrate.dart';
import 'widgets/accelerometer_widget.dart';
import 'widgets/battery_widget.dart';
import 'widgets/heart_rate_widget.dart';

void main() {
    WidgetsFlutterBinding.ensureInitialized();
    FlutterBluePlus.setLogLevel(LogLevel.verbose, color: true);
    runApp(const MyApp());
}

class MyApp extends StatelessWidget {
    const MyApp({super.key});

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
    const SensorDisplayPage({super.key});

    @override
    State<SensorDisplayPage> createState() => _SensorDisplayPageState();
}

class _SensorDisplayPageState extends State<SensorDisplayPage> {
    final SensorService _sensorService = SensorService();
    final HeartRateService _heartRateService = HeartRateService();
    final BatteryService _batteryService = BatteryService();
    static const String targetDeviceName = 'CL837-0753644';
    BluetoothDevice? connectedDevice;
    AccelerometerData? latestAccelData;
    int? latestHeartRate;
    int? latestBatteryLevel;
    bool isScanning = false;
    bool isConnecting = false;
    late StreamSubscription<AccelerometerData> _accelDataSubscription;
    late StreamSubscription<int?> _heartRateSubscription;
    late StreamSubscription<int?> _batteryLevelSubscription;

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

    Future<void> startScan() async {
        if (isScanning) return;
        setState(() {
            isScanning = true;
        });
        try {
            await FlutterBluePlus.startScan(timeout: const Duration(seconds: 5));
            FlutterBluePlus.scanResults.listen((results) {
                for (ScanResult r in results) {
                    // ignore: deprecated_member_use
                    debugPrint('Found device: ${r.device.name}');
                    // ignore: deprecated_member_use
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
            try {
                await _sensorService.start(device, _heartRateService, _batteryService);
            } catch (e) {
                if (!_sensorService.isAccelerometerWorking) {
                    showError('Critical error: Accelerometer not working');
                    await disconnectDevice();
                    return;
                } else {
                    showWarning('Some sensors may not work properly. Accelerometer is still functional.');
                }
            }
            _accelDataSubscription = _sensorService.accelDataStream.listen(
                (data) {
                    setState(() {
                        latestAccelData = data;
                    });
                },
                onError: (error) {
                    debugPrint('Sensor data stream error: $error');
                    showWarning('Some sensor data may be temporarily unavailable');
                },
            );
            _heartRateSubscription = _heartRateService.dataStream.listen(
                (heartRate) {
                    setState(() {
                        latestHeartRate = heartRate;
                    });
                },
                onError: (error) {
                    debugPrint('Heart rate stream error: $error');
                    showWarning('Heart rate data may be temporarily unavailable');
                },
            );
            _batteryLevelSubscription = _batteryService.dataStream.listen(
                (batteryLevel) {
                    setState(() {
                        latestBatteryLevel = batteryLevel;
                    });
                },
                onError: (error) {
                    debugPrint('Battery stream error: $error');
                    showWarning('Battery data may be temporarily unavailable');
                },
            );
            if (mounted) {
                setState(() {
                    connectedDevice = device;
                    isConnecting = false;
                });
            }
        } catch (e) {
            debugPrint('Connection error: $e');
            if (mounted) {
                setState(() {
                    isConnecting = false;
                });
            }
            showError('Failed to connect to device');
        }
    }

    Future<void> disconnectDevice() async {
        try {
            await _accelDataSubscription.cancel();
            await _heartRateSubscription.cancel();
            await _batteryLevelSubscription.cancel();
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
                latestAccelData = null;
                latestHeartRate = null;
                latestBatteryLevel = null;
            });
        }
    }

    @override
    void dispose() {
        _accelDataSubscription.cancel();
        _heartRateSubscription.cancel();
        _batteryLevelSubscription.cancel();
        _sensorService.dispose();
        _heartRateService.dispose();
        _batteryService.dispose();
        disconnectDevice();
        super.dispose();
    }

    void showWarning(String message) {
        if (!mounted) return;
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
                content: Text(message),
                backgroundColor: Colors.orange,
                duration: const Duration(seconds: 3),
            ),
        );
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
                                            isScanning ? 'Scanning...' :
                                            isConnecting ? 'Connecting...' :
                                            'Scan for Device',
                                        ),
                                        onPressed: (isScanning || isConnecting) ? null : startScan,
                                    ),
                                ),
                            ],
                        ),
                    ),
                    Expanded(
                        child: Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: Column(
                                crossAxisAlignment: CrossAxisAlignment.stretch,
                                children: [
                                    HeartRateWidget(latestData: latestHeartRate),
                                    const SizedBox(height: 8),
                                    BatteryWidget(latestData: latestBatteryLevel),
                                    const SizedBox(height: 16),
                                    AccelerometerWidget(latestData: latestAccelData),
                                ],
                            ),
                        ),
                    ),
                ],
            ),
        );
    }
}
