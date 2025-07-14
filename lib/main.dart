// ignore_for_file: avoid_print, prefer_const_constructors

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
import 'widgets/historical_data_widget.dart';
import 'widgets/rope_skipping_widget.dart';
import 'widgets/device_info_widget.dart';
import 'widgets/test_diary_widget.dart';
import 'services/test_diary_service.dart';
import 'models/sensor_data.dart';
import 'models/heart_rate_data.dart';
import 'models/hrv_data.dart';
import 'models/spo2_data.dart';
import 'models/temperature_data.dart';
import 'models/sports_data.dart';
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
    SportsData? latestSportsData;
    
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
    StreamSubscription<SportsData?>? _sportsDataSubscription;
    
    // Historical data subscriptions
    StreamSubscription<List<ExerciseHistoryData>>? _exerciseHistorySubscription;
    StreamSubscription<HeartRateHistoryList>? _hrHistoryListSubscription;
    StreamSubscription<HeartRateHistoryData>? _hrHistoryDataSubscription;

    @override
    void initState() {
        super.initState();
        _tabController = TabController(length: 3, vsync: this); // Sensori, Diario, Info
        _setupStreamSubscriptions();
        _initializeBluetooth();
        _initializeTestDiary();
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

    Future<void> _initializeTestDiary() async {
        try {
            // Temporaneamente forza la reinizializzazione per risolvere problemi di tipo
            debugPrint('🔄 Force reinitializing diary to fix type issues...');
            await TestDiaryService.instance.forceReinitialize();
        } catch (e) {
            debugPrint('❌ Error initializing test diary: $e');
        }
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
            
            // Auto-request historical data after successful connection
            // Manual mode: Don't auto-request historical data
            // debugPrint('🎯 AUTO-REQUESTING HISTORICAL DATA after connection...');
            // _autoRequestHistoricalDataAfterConnection();
            
            // Manual mode: Pause all periodic requests for manual control
            debugPrint('📋 MANUAL MODE: Disabling automatic data requests for accuracy testing...');
            _extendedService.pausePeriodicRequests();
            
            // 🎮 TOGGLE MODES: Uncomment line below to switch to AUTOMATIC mode
            // _extendedService.resumePeriodicRequests(); // 🔄 AUTOMATIC MODE
            
            // Optional: Pause heart rate service to turn off green LED
            // _heartRateService.pause(); // Uncomment to test LED off state
            
            // Force exit SpO2 mode to stop red LED and vibration
            debugPrint('🚨 FORCE EXITING SpO2 MODE to stop red LED...');
            await forceExitSpO2Mode();
            
            debugPrint('✅ MANUAL MODE ENABLED: Use buttons to request data manually for accuracy testing');
            debugPrint('🔋 Device LED should now be stable GREEN (Heart Rate mode)');
            debugPrint('🎯 AUTO-REQUEST METHOD CALLED successfully!');
            
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
            await _sportsDataSubscription?.cancel();
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
                latestSportsData = null;
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
        _sportsDataSubscription?.cancel();
        _exerciseHistorySubscription?.cancel();
        _hrHistoryListSubscription?.cancel();
        _hrHistoryDataSubscription?.cancel();
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
        debugPrint('🔄 requestHRHistory() called from UI');
        if (connectedDevice == null) {
            debugPrint('❌ No device connected for HR History request');
            showError('No device connected');
            return;
        }
        
        try {
            debugPrint('📞 Calling _extendedService.requestHRHistoryList()...');
            await _extendedService.requestHRHistoryList();
            debugPrint('✅ HR History request sent successfully');
            showSuccess('HR history requested');
        } catch (e) {
            debugPrint('❌ Failed to request HR history: $e');
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


    // SpO2 measurement methods (CL837 Protocol v0.6 - Command 0x37)
    Future<void> measureSpO2() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            debugPrint('📡 Starting SpO2 measurement...');
            await _extendedService.enableSpO2Mode();
            showSuccess('SpO2 measurement started');
        } catch (e) {
            debugPrint('Failed to start SpO2 measurement: $e');
            showError('Failed to start SpO2 measurement');
        }
    }

    Future<void> exitSpO2Mode() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            debugPrint('📡 Exiting SpO2 mode...');
            await _extendedService.disableSpO2Mode();
            showSuccess('Exited SpO2 mode');
        } catch (e) {
            debugPrint('Failed to exit SpO2 mode: $e');
            showError('Failed to exit SpO2 mode');
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

    // Manual control methods for accuracy testing
    bool _isManualMode = true; // Track current mode
    
    Future<void> toggleDataRequestMode() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        setState(() {
            _isManualMode = !_isManualMode;
        });
        
        if (_isManualMode) {
            debugPrint('🎮 SWITCHING TO MANUAL MODE...');
            _extendedService.pausePeriodicRequests();
            showSuccess('Manual mode enabled - Use buttons to request data');
        } else {
            debugPrint('🔄 SWITCHING TO AUTOMATIC MODE...');
            _extendedService.resumePeriodicRequests();
            showSuccess('Automatic mode enabled - Data requests every 10s');
        }
    }

    Future<void> manualRequestTemperature() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            debugPrint('🌡️ MANUAL REQUEST: Temperature data');
            await _extendedService.requestTemperatureData();
            showSuccess('Temperature data requested');
        } catch (e) {
            debugPrint('Failed to request temperature data: $e');
            showError('Failed to request temperature data');
        }
    }

    Future<void> manualRequestSportsData() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            debugPrint('🏃 MANUAL REQUEST: Sports data');
            await _extendedService.requestSportsData();
            showSuccess('Sports data requested');
        } catch (e) {
            debugPrint('Failed to request sports data: $e');
            showError('Failed to request sports data');
        }
    }

    Future<void> manualRequestDeviceInfo() async {
        if (connectedDevice == null) {
            showError('No device connected');
            return;
        }
        
        try {
            debugPrint('ℹ️ MANUAL REQUEST: Device info');
            await _extendedService.requestDeviceInfo();
            showSuccess('Device info requested');
        } catch (e) {
            debugPrint('Failed to request device info: $e');
            showError('Failed to request device info');
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
                            onExitSpO2Mode: exitSpO2Mode,
                        ),
                        // Manual Control Panel
                        _buildManualControlPanel(),
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
                const SizedBox(height: 16),
                // Temperature and Sports Data Row
                Row(
                    children: [
                        Expanded(
                            child: TemperatureWidget(
                                temperatureData: latestTemperatureData,
                                isConnected: connectedDevice != null,
                            ),
                        ),
                        const SizedBox(width: 8),
                        Expanded(
                            child: SportsWidget(
                                sportsData: latestSportsData,
                                isConnected: connectedDevice != null,
                            ),
                        ),
                    ],
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
                        onMeasureSpO2: measureSpO2,
                        onExitSpO2Mode: exitSpO2Mode,
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
                
                const SizedBox(height: 16),
                
                // Rope Skipping Widget
                RopeSkippingWidget(service: _extendedService),
                
                const SizedBox(height: 16),
                
                // Device Information Widget
                DeviceInfoWidget(service: _extendedService),
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
                    const SensorInfoWidget(),
                ],
            ),
        );
    }

    // Manual Control Panel for accuracy testing
    Widget _buildManualControlPanel() {
        if (connectedDevice == null) return Container();
        
        return Card(
            child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                        Row(
                            children: [
                                Text(
                                    'Manual Control Panel',
                                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                                        fontWeight: FontWeight.bold,
                                    ),
                                ),
                                const Spacer(),
                                // Mode Toggle Button
                                ElevatedButton.icon(
                                    onPressed: toggleDataRequestMode,
                                    icon: Icon(_isManualMode ? Icons.touch_app : Icons.refresh),
                                    label: Text(_isManualMode ? 'MANUAL' : 'AUTO'),
                                    style: ElevatedButton.styleFrom(
                                        backgroundColor: _isManualMode ? Colors.orange : Colors.green,
                                        foregroundColor: Colors.white,
                                    ),
                                ),
                            ],
                        ),
                        const SizedBox(height: 8),
                        Text(
                            _isManualMode 
                                ? 'Manual Mode: Use buttons to request data'
                                : 'Automatic Mode: Data requested every 10 seconds',
                            style: Theme.of(context).textTheme.bodySmall?.copyWith(
                                color: _isManualMode ? Colors.orange : Colors.green,
                                fontWeight: FontWeight.w500,
                            ),
                        ),
                        const SizedBox(height: 12),
                        Wrap(
                            spacing: 8.0,
                            runSpacing: 8.0,
                            children: [
                                ElevatedButton.icon(
                                    onPressed: () async {
                                        print('Manual request: Temperature');
                                        await _extendedService.requestTemperatureData();
                                        // Add delay to prevent rapid commands
                                        await Future.delayed(Duration(milliseconds: 1500));
                                    },
                                    icon: Icon(Icons.thermostat),
                                    label: Text('Temperature'),
                                ),
                                ElevatedButton.icon(
                                    onPressed: () async {
                                        print('Manual request: Sports');
                                        await _extendedService.requestSportsData();
                                        // Add delay to prevent rapid commands
                                        await Future.delayed(Duration(milliseconds: 1500));
                                    },
                                    icon: Icon(Icons.sports),
                                    label: Text('Sports'),
                                ),
                                ElevatedButton.icon(
                                    onPressed: () {
                                        print('Manual request: Device Info');
                                        _extendedService.requestAllDeviceInfo();
                                    },
                                    icon: Icon(Icons.info),
                                    label: Text('Device Info'),
                                ),
                                ElevatedButton.icon(
                                    onPressed: () {
                                        print('Manual request: SpO2');
                                        measureSpO2();
                                    },
                                    icon: Icon(Icons.favorite),
                                    label: Text('SpO2'),
                                ),
                                ElevatedButton.icon(
                                    onPressed: () {
                                        print('Manual request: All Historical Data');
                                        requestAllHistoricalData();
                                    },
                                    icon: Icon(Icons.history),
                                    label: Text('All History'),
                                ),
                            ],
                        ),
                    ],
                ),
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
                    ]
                ],
                bottom: TabBar(
                    controller: _tabController,
                    tabs: const [
                        Tab(icon: Icon(Icons.sensors), text: 'Sensori'),
                        Tab(icon: Icon(Icons.book), text: 'Diario'),
                        Tab(icon: Icon(Icons.info), text: 'Info'),
                    ],
                ),
            ),
            body: TabBarView(
                controller: _tabController,
                children: [
                    // Tab 1: Sensori
                    _buildSensorTab(),
                    // Tab 2: Diario  
                    const TestDiaryWidget(),
                    // Tab 3: Info dispositivo
                    _buildInfoTab(),
                ],
            ),
        );
    }
}