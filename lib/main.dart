import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'accelerometer_service_optimized.dart';
import 'battery.dart';
import 'heartrate.dart';
import 'widgets/accelerometer_widget.dart';
import 'widgets/battery_widget.dart';
import 'widgets/heart_rate_widget.dart';
import 'models/sensor_data.dart';

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
    final AccelerometerServiceOptimized _sensorService = AccelerometerServiceOptimized();
    final HeartRateService _heartRateService = HeartRateService();
    final BatteryService _batteryService = BatteryService();
    
    BluetoothDevice? connectedDevice;
    List<BluetoothDevice> foundDevices = [];
    BluetoothDevice? selectedDevice;
    
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
            foundDevices.clear();
        });
        try {
            await FlutterBluePlus.startScan(timeout: const Duration(seconds: 10));
            FlutterBluePlus.scanResults.listen((results) {
                setState(() {
                    // Filter out duplicate devices and devices without names
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
            
            // Richiedi un MTU più grande
            try {
                await device.requestMtu(512);
                debugPrint('MTU size increased to 512');
            } catch (e) {
                debugPrint('Failed to increase MTU: $e');
            }
            
            // Imposta priorità di connessione elevata - corretto l'uso dell'API
            try {
                // Parametro obbligatorio connectionPriorityRequest aggiunto
                await device.requestConnectionPriority(
                    connectionPriorityRequest: ConnectionPriority.high
                );
                debugPrint('Connection priority set to high');
            } catch (e) {
                debugPrint('Failed to set connection priority: $e');
            }
            
            try {
                // Avvia i servizi separatamente per gestire meglio gli errori
                await _sensorService.start(device);
                try { await _heartRateService.start(device); } catch (e) { debugPrint('Heart rate service error: $e'); }
                try { await _batteryService.start(device); } catch (e) { debugPrint('Battery service error: $e'); }
            } catch (e) {
                if (!_sensorService.isRunning) {
                    showError('Critical error: Accelerometer not working');
                    await disconnectDevice();
                    return;
                } else {
                    showWarning('Some sensors may not work properly. Accelerometer is still functional.');
                }
            }
            
            _setupStreamSubscriptions();
            
            if (mounted) {
                setState(() {
                    connectedDevice = device;
                    selectedDevice = device;
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

    void _setupStreamSubscriptions() {
        _accelDataSubscription = _sensorService.dataStream.listen(
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
                selectedDevice = null;
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
                                            isScanning ? 'Scanning...' : 'Scan for Devices',
                                        ),
                                        onPressed: isScanning ? null : startScan,
                                    ),
                                ),
                            ],
                        ),
                    ),
                    if (foundDevices.isNotEmpty)
                        Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: DropdownButtonFormField<BluetoothDevice>(
                                decoration: InputDecoration(
                                    labelText: 'Select Device',
                                    border: OutlineInputBorder(
                                        borderRadius: BorderRadius.circular(10),
                                    ),
                                ),
                                value: selectedDevice,
                                hint: const Text('Choose a Bluetooth Device'),
                                items: foundDevices.map((device) {
                                    return DropdownMenuItem<BluetoothDevice>(
                                        value: device,
                                        child: Text(device.platformName.isNotEmpty ? device.platformName : 'Unknown Device'),
                                    );
                                }).toList(),
                                onChanged: (device) {
                                    if (device != null) {
                                        setState(() {
                                            selectedDevice = device;
                                        });
                                    }
                                },
                            ),
                        ),
                    if (selectedDevice != null && connectedDevice == null)
                        Padding(
                            padding: const EdgeInsets.symmetric(horizontal: 16.0),
                            child: ElevatedButton(
                                onPressed: isConnecting ? null : () => connectToDevice(selectedDevice!),
                                child: Text(
                                    isConnecting ? 'Connecting...' : 'Connect to ${selectedDevice!.platformName}',
                                ),
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