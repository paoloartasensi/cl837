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
import 'widgets/manual_tests_widget.dart';
import 'widgets/historical_data_widget.dart';
import 'widgets/device_info_widget.dart';
import 'widgets/device_control_widget.dart';
import 'widgets/hr_control_widget.dart';
import 'models/sensor_data.dart';
import 'models/heart_rate_data.dart';
import 'models/hrv_data.dart';
import 'models/spo2_data.dart';
import 'models/temperature_data.dart';
import 'models/historical_data.dart';
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

class _SensorDisplayPageState extends State<SensorDisplayPage> with TickerProviderStateMixin {
    final SensorService _sensorService = SensorService();
    final HeartRateService _heartRateService = HeartRateService();
    final BatteryService _batteryService = BatteryService();
    final ChileafExtendedService _extendedService = ChileafExtendedService();
    final HRVSessionService _hrvSessionService = HRVSessionService();
    
    late TabController _tabController;
    
    BluetoothDevice? connectedDevice;
    List<BluetoothDevice> foundDevices = [];
    BluetoothDevice? selectedDevice;
    
    AccelerometerData? latestAccelData;
    HeartRateData? latestHeartRate;
    int? latestBatteryLevel;
    HRVData? latestHRVData;
    SpO2Data? latestSpO2Data;  
    TemperatureData? latestTemperatureData;
    
    // Historical data
    List<ExerciseHistoryData>? latestExerciseHistory;
    HeartRateHistoryList? latestHRHistoryList;
    List<HeartRateHistoryData>? latestHRHistoryData;
    
    bool isScanning = false;
    bool isConnecting = false;
    
    late StreamSubscription<AccelerometerData> _accelDataSubscription;
    late StreamSubscription<HeartRateData?> _heartRateSubscription;
    late StreamSubscription<int?> _batteryLevelSubscription;
    StreamSubscription<HRVData?>? _hrvDataSubscription;
    StreamSubscription<SpO2Data?>? _spo2DataSubscription;
    StreamSubscription<TemperatureData?>? _temperatureDataSubscription;
    
    // Historical data subscriptions
    StreamSubscription<List<ExerciseHistoryData>>? _exerciseHistorySubscription;
    StreamSubscription<HeartRateHistoryList>? _hrHistoryListSubscription;
    StreamSubscription<HeartRateHistoryData>? _hrHistoryDataSubscription;

    @override
    void initState() {
        super.initState();
        _tabController = TabController(length: 5, vsync: this); // Sensori, Test Manuali, Device Control, Feedback Tests, Info
        _setupStreamSubscriptions();
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
        
        // Subscribe to historical data streams
        _exerciseHistorySubscription = _extendedService.exerciseHistoryStream.listen(
            (exerciseHistory) {
                setState(() {
                    latestExerciseHistory = exerciseHistory;
                });
                debugPrint('📊 Exercise History received: ${exerciseHistory.length} entries');
            },
            onError: (error) {
                debugPrint('Exercise history stream error: $error');
            },
        );
        
        _hrHistoryListSubscription = _extendedService.hrHistoryListStream.listen(
            (hrHistoryList) {
                setState(() {
                    latestHRHistoryList = hrHistoryList;
                });
                debugPrint('💓 HR History List received: ${hrHistoryList.timestamps.length} timestamps');
            },
            onError: (error) {
                debugPrint('HR history list stream error: $error');
            },
        );
        
        _hrHistoryDataSubscription = _extendedService.hrHistoryDataStream.listen(
            (hrHistoryData) {
                setState(() {
                    latestHRHistoryData ??= [];
                    latestHRHistoryData!.add(hrHistoryData);
                });
                debugPrint('💓 HR History Data received: ${hrHistoryData.entries.length} entries for ${hrHistoryData.timestamp}');
            },
            onError: (error) {
                debugPrint('HR history data stream error: $error');
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
            await _exerciseHistorySubscription?.cancel();
            await _hrHistoryListSubscription?.cancel();
            await _hrHistoryDataSubscription?.cancel();
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
                latestExerciseHistory = null;
                latestHRHistoryList = null;
                latestHRHistoryData = null;
            });
        }
    }

    @override
    void dispose() {
        _tabController.dispose();
        _accelDataSubscription.cancel();
        _heartRateSubscription.cancel();
        _batteryLevelSubscription.cancel();
        _hrvDataSubscription?.cancel();
        _spo2DataSubscription?.cancel();
        _temperatureDataSubscription?.cancel();
        _exerciseHistorySubscription?.cancel();
        _hrHistoryListSubscription?.cancel();
        _hrHistoryDataSubscription?.cancel();
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
            
            await _extendedService.startBloodOxygenMeasurement();
            showSuccess('SpO2 measurement started');
        } catch (e) {
            debugPrint('Failed to measure SpO2: $e');
            showError('Failed to measure SpO2');
        }
    }


    Future<void> forceExitSpO2Mode() async {
        try {
            await _extendedService.stopBloodOxygenMeasurement();
            showSuccess('SpO2 measurement stopped');
        } catch (e) {
            debugPrint('Failed to stop SpO2 measurement: $e');
            showError('Failed to stop SpO2 measurement');
        }
    }

    Future<void> diagnoseBLE() async {
        try {
            // Diagnostica BLE usando il nuovo sistema di alto livello
            bool isActive = _extendedService.isBloodOxygenMeasurementActive;
            showSuccess('BLE diagnostics: SpO2 active = $isActive (command 55 system)');
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

    // Historical data methods
    Future<void> requestExerciseHistory() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            await _extendedService.requestExerciseHistory();
            showSuccess('Exercise history requested');
        } catch (e) {
            debugPrint('Failed to request exercise history: $e');
            showError('Failed to request exercise history');
        }
    }

    Future<void> requestHRHistory() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            await _extendedService.requestHRHistoryList();
            showSuccess('HR history requested');
        } catch (e) {
            debugPrint('Failed to request HR history: $e');
            showError('Failed to request HR history');
        }
    }

    Future<void> requestAllHistoricalData() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            await _extendedService.requestAllHistoricalData();
            showSuccess('All historical data requested');
        } catch (e) {
            debugPrint('Failed to request all historical data: $e');
            showError('Failed to request all historical data');
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
                        ),
                        AccelerometerWidget(
                            latestData: latestAccelData,
                        ),
                        // Spazio per un futuro widget dei risultati test
                        Container(
                            height: 150,
                            padding: const EdgeInsets.all(16),
                            decoration: BoxDecoration(
                                border: Border.all(color: Colors.grey.shade300),
                                borderRadius: BorderRadius.circular(8),
                                color: Colors.grey.shade50,
                            ),
                            child: const Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                    Icon(Icons.analytics, size: 32, color: Colors.grey),
                                    SizedBox(height: 8),
                                    Text('Risultati Test',
                                        style: TextStyle(fontWeight: FontWeight.w600)),
                                    Text('I risultati dei test manuali\nappaiono qui',
                                        textAlign: TextAlign.center,
                                        style: TextStyle(color: Colors.grey)),
                                ],
                            ),
                        ),
                    ],
                ),
                const SizedBox(height: 16),
                // Accelerometer a larghezza piena
                AccelerometerWidget(latestData: latestAccelData),
                const SizedBox(height: 16),
                // Historical Data Widget
                HistoricalDataWidget(
                    exerciseHistory: latestExerciseHistory,
                    hrHistoryList: latestHRHistoryList,
                    hrHistoryData: latestHRHistoryData,
                    isConnected: connectedDevice != null,
                    onRequestExercise: requestExerciseHistory,
                    onRequestHRHistory: requestHRHistory,
                    onRequestAllHistory: requestAllHistoricalData,
                ),
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
                    ),
                ]),
                
                const SizedBox(height: 8),
                
                // Riga inferiore - Accelerometro e dati storici
                _buildResponsiveRow([
                    AccelerometerWidget(
                        latestData: latestAccelData,
                    ),
                    HistoricalDataWidget(
                        isConnected: connectedDevice != null,
                        onRequestAllHistory: requestAllHistoricalData,
                    ),
                ]),
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

    Widget _buildSensorTab() {
        return Column(
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
        );
    }

    Widget _buildInfoTab() {
        return SingleChildScrollView(
            padding: const EdgeInsets.all(16.0),
            child: Column(
                children: [
                    DeviceInfoWidget(service: _extendedService),
                    const SizedBox(height: 16),
                    // Info aggiuntive sul dispositivo e la connessione
                    Card(
                        child: Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                    const Text(
                                        'Stato Connessione',
                                        style: TextStyle(
                                            fontSize: 18,
                                            fontWeight: FontWeight.bold,
                                        ),
                                    ),
                                    const SizedBox(height: 12),
                                    Row(
                                        children: [
                                            Icon(
                                                connectedDevice != null ? Icons.bluetooth_connected : Icons.bluetooth_disabled,
                                                color: connectedDevice != null ? Colors.green : Colors.red,
                                            ),
                                            const SizedBox(width: 8),
                                            Text(
                                                connectedDevice != null 
                                                    ? 'Connesso a ${connectedDevice!.platformName}'
                                                    : 'Dispositivo non connesso',
                                                style: TextStyle(
                                                    color: connectedDevice != null ? Colors.green : Colors.red,
                                                    fontWeight: FontWeight.w500,
                                                ),
                                            ),
                                        ],
                                    ),
                                ],
                            ),
                        ),
                    ),
                ],
            ),
        );
    }

    @override
    Widget build(BuildContext context) {
        return Scaffold(
            appBar: AppBar(
                title: Row(
                    children: [
                        const Icon(Icons.sensors),
                        const SizedBox(width: 8),
                        Text(connectedDevice?.platformName ?? 'CL837 Sensor Display'),
                    ],
                ),
                backgroundColor: connectedDevice != null ? Colors.green : Colors.blue,
                actions: [
                    if (connectedDevice != null) ...[
                        IconButton(
                            icon: const Icon(Icons.refresh),
                            onPressed: () {
                                _extendedService.requestAllDeviceInfo();
                                _extendedService.requestAllHistoricalData();
                            },
                            tooltip: 'Refresh Data',
                        ),
                        IconButton(
                            icon: const Icon(Icons.bluetooth_disabled),
                            onPressed: disconnectDevice,
                            tooltip: 'Disconnect',
                        ),
                    ],
                ],
                bottom: TabBar(
                    controller: _tabController,
                    isScrollable: true,
                    tabs: const [
                        Tab(icon: Icon(Icons.sensors), text: 'Sensori'),
                        Tab(icon: Icon(Icons.build), text: 'Test Manuali'),
                        Tab(icon: Icon(Icons.settings_remote), text: 'Device Control'),
                        Tab(icon: Icon(Icons.favorite), text: 'HR Control'),
                        Tab(icon: Icon(Icons.info), text: 'Info'),
                    ],
                ),
            ),
            body: TabBarView(
                controller: _tabController,
                children: [
                    // Tab 1: Sensori
                    _buildSensorTab(),
                    // Tab 2: Test Manuali
                    Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: ManualTestsWidget(
                            extendedService: _extendedService,
                            hrvService: _hrvSessionService,
                            heartRateStream: _heartRateService.dataStream, // Passa il main heart rate stream
                        ),
                    ),
                    // Tab 3: Device Control
                    Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: DeviceControlWidget(
                            extendedService: _extendedService,
                            isConnected: connectedDevice != null,
                            deviceName: connectedDevice?.platformName ?? 'Unknown Device',
                        ),
                    ),
                    // Tab 4: HR Control & Vibration Tests
                    connectedDevice != null
                        ? HRControlWidget(service: _extendedService)
                        : const Center(
                            child: Text(
                                'Connetti un dispositivo CL837 per configurare HR e testare vibrazione',
                                style: TextStyle(fontSize: 16, color: Colors.grey),
                                textAlign: TextAlign.center,
                            ),
                          ),
                    // Tab 5: Info dispositivo
                    _buildInfoTab(),
                ],
            ),
        );
    }
}