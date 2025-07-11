import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'accelerometer_service.dart';
import 'battery.dart';
import 'heartrate.dart';
import 'chileaf_extended_service.dart';
import 'hrv_session_service.dart';
import 'widgets/accelerometer_widget.dart';
import 'widgets/battery_widget.dart';
import 'widgets/heart_rate_widget.dart';
import 'widgets/hrv_session_widget.dart';
import 'widgets/spo2_widget.dart';
import 'widgets/temperature_widget.dart';
import 'widgets/sports_widget.dart';
import 'widgets/sensor_info_widget.dart';
import 'models/sensor_data.dart';
import 'models/heart_rate_data.dart';
import 'models/hrv_data.dart';
import 'models/spo2_data.dart';
import 'models/temperature_data.dart';
import 'models/sports_data.dart';
import 'services/error_handler.dart';
import 'services/performance_monitor.dart';
import 'services/data_persistence_manager.dart';

void main() async {
    WidgetsFlutterBinding.ensureInitialized();
    
    // Initialize data persistence
    await dataPersistenceManager.initialize();
    
    // Suppress BLE log spam - only show critical errors
    FlutterBluePlus.setLogLevel(LogLevel.none, color: false);
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
    final ChileafExtendedService _extendedService = ChileafExtendedService();
    final HRVSessionService _hrvSessionService = HRVSessionService();
    
    BluetoothDevice? connectedDevice;
    List<BluetoothDevice> foundDevices = [];
    BluetoothDevice? selectedDevice;
    
    AccelerometerData? latestAccelData;
    HeartRateData? latestHeartRate;
    int? latestBatteryLevel;
    HRVData? latestHRVData;
    SpO2Data? latestSpO2Data;  
    TemperatureData? latestTemperatureData;
    SportsData? latestSportsData;
    
    bool isScanning = false;
    bool isConnecting = false;
    
    late StreamSubscription<AccelerometerData> _accelDataSubscription;
    late StreamSubscription<HeartRateData?> _heartRateSubscription;
    late StreamSubscription<int?> _batteryLevelSubscription;
    StreamSubscription<HRVData?>? _hrvDataSubscription;
    StreamSubscription<SpO2Data?>? _spo2DataSubscription;
    StreamSubscription<TemperatureData?>? _temperatureDataSubscription;
    StreamSubscription<SportsData?>? _sportsDataSubscription;

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
        
        // Start performance tracking
        var perfTracker = performanceMonitor.startOperation('device_connection');
        
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
                await _sensorService.start(device, _heartRateService, _batteryService, _extendedService);
                try { await _heartRateService.start(device); } catch (e) { debugPrint('Heart rate service error: $e'); }
                try { await _batteryService.start(device); } catch (e) { debugPrint('Battery service error: $e'); }
                try { 
                    await _extendedService.start(device); 
                    debugPrint('Extended service started successfully');
                } catch (e) { 
                    debugPrint('Extended service error: $e'); 
                }
            } catch (e) {
                if (!_sensorService.isAccelerometerWorking) {
                    showError('Critical error: Accelerometer not working');
                    await disconnectDevice();
                    perfTracker.error('Accelerometer not working: $e');
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
            
            perfTracker.complete();
        } catch (e) {
            debugPrint('Connection error: $e');
            
            // Handle error with error handler
            BleErrorInfo errorInfo = BleErrorHandler.handleBleError(e);
            showError(errorInfo.message);
            
            if (mounted) {
                setState(() {
                    isConnecting = false;
                });
            }
            
            perfTracker.error('Connection failed: $e');
        }
    }

    void _setupStreamSubscriptions() {
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
                debugPrint('🔋🎨 Main.dart received battery level: $batteryLevel, calling setState...');
                setState(() {
                    latestBatteryLevel = batteryLevel;
                    debugPrint('🔋🎨 setState completed, latestBatteryLevel = $latestBatteryLevel');
                });
            },
            onError: (error) {
                debugPrint('Battery stream error: $error');
                showWarning('Battery data may be temporarily unavailable');
            },
        );
        
        // Force refresh battery data in case it was already loaded before subscription
        _batteryService.forceRefreshBatteryData();
        
        // Subscribe to extended service streams
        _hrvDataSubscription = _extendedService.hrvDataStream.listen(
            (hrvData) {
                setState(() {
                    latestHRVData = hrvData;
                });
            },
            onError: (error) {
                debugPrint('HRV stream error: $error');
            },
        );
        
        _spo2DataSubscription = _extendedService.spo2DataStream.listen(
            (spo2Data) {
                setState(() {
                    latestSpO2Data = spo2Data;
                });
            },
            onError: (error) {
                debugPrint('SpO2 stream error: $error');
            },
        );
        
        _temperatureDataSubscription = _extendedService.temperatureDataStream.listen(
            (temperatureData) {
                setState(() {
                    latestTemperatureData = temperatureData;
                });
            },
            onError: (error) {
                debugPrint('Temperature stream error: $error');
            },
        );
        
        _sportsDataSubscription = _extendedService.sportsDataStream.listen(
            (sportsData) {
                setState(() {
                    latestSportsData = sportsData;
                });
            },
            onError: (error) {
                debugPrint('Sports stream error: $error');
            },
        );
    }

    Future<void> disconnectDevice() async {
        try {
            await _accelDataSubscription.cancel();
            await _heartRateSubscription.cancel();
            await _batteryLevelSubscription.cancel();
            await _hrvDataSubscription?.cancel();
            await _spo2DataSubscription?.cancel();
            await _temperatureDataSubscription?.cancel();
            await _sportsDataSubscription?.cancel();
            await _sensorService.stop();
            await _heartRateService.stop();
            await _batteryService.stop();
            await _extendedService.stop();
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
                latestHRVData = null;
                latestSpO2Data = null;
                latestTemperatureData = null;
                latestSportsData = null;
            });
        }
    }

    @override
    void dispose() {
        _accelDataSubscription.cancel();
        _heartRateSubscription.cancel();
        _batteryLevelSubscription.cancel();
        _hrvDataSubscription?.cancel();
        _spo2DataSubscription?.cancel();
        _temperatureDataSubscription?.cancel();
        _sportsDataSubscription?.cancel();
        _sensorService.dispose();
        _heartRateService.dispose();
        _batteryService.dispose();
        _extendedService.dispose();
        _hrvSessionService.dispose();
        performanceMonitor.dispose();
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

    void showSuccess(String message) {
        if (!mounted) return;
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
                content: Text(message),
                backgroundColor: Colors.green,
                duration: const Duration(seconds: 3),
            ),
        );
    }

    // SpO2 measurement methods
    Future<void> measureSpO2() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            setState(() {
                // You can add an isMeasuring state variable if needed
            });
            
            await _extendedService.measureSpO2();
            showSuccess('SpO2 measurement started');
        } catch (e) {
            debugPrint('Failed to measure SpO2: $e');
            showError('Failed to measure SpO2');
        }
    }

    Future<void> measureSpO2Alternative() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            setState(() {
                // You can add an isMeasuring state variable if needed
            });
            
            await _extendedService.measureSpO2Alternative();
            showSuccess('Alternative SpO2 measurement started');
        } catch (e) {
            debugPrint('Failed to measure SpO2 (alternative): $e');
            showError('Failed to measure SpO2 (alternative)');
        }
    }

    Future<void> forceExitSpO2Mode() async {
        try {
            await _extendedService.forceExitSpO2Mode();
            showSuccess('Force exit SpO2 mode completed');
        } catch (e) {
            debugPrint('Failed to force exit SpO2 mode: $e');
            showError('Failed to force exit SpO2 mode');
        }
    }

    Future<void> testSpO2LED() async {
        try {
            await _extendedService.testLEDFunctionality();
            showSuccess('LED test completed');
        } catch (e) {
            debugPrint('Failed to test LED: $e');
            showError('Failed to test LED');
        }
    }

    Future<void> diagnoseBLE() async {
        try {
            await _extendedService.diagnoseBLEIssues();
            showSuccess('BLE diagnostics completed');
        } catch (e) {
            debugPrint('Failed to diagnose BLE: $e');
            showError('Failed to diagnose BLE');
        }
    }

    Future<void> startHRVSession() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            await _hrvSessionService.startSession(_heartRateService.dataStream);
            showSuccess('HRV session started');
        } catch (e) {
            debugPrint('Failed to start HRV session: $e');
            showError('Failed to start HRV session');
        }
    }

    // Responsive layout methods
    Widget _buildGridLayout() {
        return Column(
            children: [
                // Grid di 2x3 per i widget principali
                GridView.count(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics(),
                    crossAxisCount: 3,
                    childAspectRatio: 1.2,
                    mainAxisSpacing: 8,
                    crossAxisSpacing: 8,
                    children: [
                        HeartRateWidget(latestData: latestHeartRate, isConnected: connectedDevice != null),
                        BatteryWidget(latestData: latestBatteryLevel, isConnected: connectedDevice != null),
                        HRVSessionWidget(
                            sessionService: _hrvSessionService,
                            isConnected: connectedDevice != null,
                            onStartSession: startHRVSession,
                        ),
                        SpO2Widget(
                            spo2Data: latestSpO2Data,
                            isConnected: connectedDevice != null,
                            onMeasureSpO2: measureSpO2,
                            onMeasureSpO2Alternative: measureSpO2Alternative,
                            onForceExit: forceExitSpO2Mode,
                            onTestLED: testSpO2LED,
                            onDiagnoseBLE: diagnoseBLE,
                        ),
                        TemperatureWidget(
                            temperatureData: latestTemperatureData,
                            isConnected: connectedDevice != null,
                        ),
                        SportsWidget(
                            sportsData: latestSportsData,
                            isConnected: connectedDevice != null,
                        ),
                    ],
                ),
                const SizedBox(height: 16),
                // Accelerometer a larghezza piena
                AccelerometerWidget(latestData: latestAccelData),
            ],
        );
    }

    Widget _buildColumnLayout() {
        return Column(
            children: [
                // Riga superiore - Vitali principali
                _buildResponsiveRow([
                    HeartRateWidget(latestData: latestHeartRate, isConnected: connectedDevice != null),
                    BatteryWidget(latestData: latestBatteryLevel, isConnected: connectedDevice != null),
                ]),
                
                const SizedBox(height: 8),
                
                // Riga centrale - Salute avanzata 
                _buildResponsiveRow([
                    HRVSessionWidget(
                        sessionService: _hrvSessionService,
                        isConnected: connectedDevice != null,
                        onStartSession: startHRVSession,
                    ),
                    SpO2Widget(
                        spo2Data: latestSpO2Data,
                        isConnected: connectedDevice != null,
                        onMeasureSpO2: measureSpO2,
                        onMeasureSpO2Alternative: measureSpO2Alternative,
                        onForceExit: forceExitSpO2Mode,
                        onTestLED: testSpO2LED,
                        onDiagnoseBLE: diagnoseBLE,
                    ),
                ]),
                
                const SizedBox(height: 8),
                
                // Riga inferiore - Temperatura e attività
                _buildResponsiveRow([
                    TemperatureWidget(
                        temperatureData: latestTemperatureData,
                        isConnected: connectedDevice != null,
                    ),
                    SportsWidget(
                        sportsData: latestSportsData,
                        isConnected: connectedDevice != null,
                    ),
                ]),
                
                const SizedBox(height: 16),
                
                // Accelerometer a larghezza piena
                AccelerometerWidget(latestData: latestAccelData),
                
                const SizedBox(height: 16),
                
                // Sensor Information Widget
                const SensorInfoWidget(),
                
                const SizedBox(height: 16),
                
                // Sensor Information Widget
                const SensorInfoWidget(),
            ],
        );
    }

    Widget _buildResponsiveRow(List<Widget> children) {
        return LayoutBuilder(
            builder: (context, constraints) {
                if (constraints.maxWidth < 500) {
                    // Su schermi molto stretti, impila verticalmente
                    return Column(
                        children: children
                            .map((child) => Padding(
                                padding: const EdgeInsets.only(bottom: 8.0),
                                child: child,
                            ))
                            .toList(),
                    );
                } else {
                    // Su schermi normali, usa Row
                    return Row(
                        children: children
                            .map((child) => Expanded(child: child))
                            .toList(),
                    );
                }
            },
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
                        child: SingleChildScrollView(
                            padding: const EdgeInsets.all(8.0),
                            child: LayoutBuilder(
                                builder: (context, constraints) {
                                    // Determina se usiamo layout a colonne o griglia
                                    final isWideScreen = constraints.maxWidth > 600;
                                    
                                    if (isWideScreen) {
                                        // Layout a griglia per schermi larghi
                                        return _buildGridLayout();
                                    } else {
                                        // Layout a colonna per schermi stretti
                                        return _buildColumnLayout();
                                    }
                                },
                            ),
                        ),
                    ),
                ],
            ),
        );
    }
}