import 'dart:async';
import 'services/data_processors/historical_data_processor.dart';
import 'services/data_processors/timestamp_decoder.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

// Data Models
import 'models/spo2_data.dart';
import 'models/temperature_data.dart';
import 'models/hrv_data.dart';
import 'models/heart_rate_data.dart';
import 'models/heart_rate_config.dart';
import 'models/historical_data.dart';
import 'models/rope_data.dart';
import 'models/device_info.dart';
import 'models/sport_health_data.dart' hide HeartRateConfig;
import 'models/sport_realtime_data.dart';
import 'models/sensor_data.dart';
import 'models/user_info.dart';

// Data Processors
import 'services/data_processors/spo2_processor.dart';
import 'services/data_processors/temperature_processor.dart';
import 'services/data_processors/health_processor.dart';
import 'services/historical_data_service.dart';
import 'services/data_processors/rope_processor.dart';
import 'services/data_processors/device_info_processor.dart';
import 'services/sleep_onset_detector.dart';

// Protocol & Commands
import 'services/ble_protocol/chileaf_protocol.dart';
import 'services/ble_protocol/official_commands.dart';

/// Servizio principale per la gestione del dispositivo Chileaf Extended
/// Coordinatore che orchestra tutti i processori di dati e la comunicazione BLE
///
/// BLOOD OXYGEN (SpO2) MEASUREMENT USAGE EXAMPLE:
/// ```dart
/// // Setup callbacks (similar to BloodOxygenSearchActivity)
/// service.setSpO2Callbacks(
///   onValueReceived: (value) => print('SpO2: $value%'),
///   onComplete: () => print('Measurement complete'),
///   onError: (error) => print('Error: $error'),
/// );
///
/// // Start measurement (equivalent to setBloodOxygen(1))
/// await service.startBloodOxygenMeasurement();
///
/// // Stop measurement (equivalent to setBloodOxygen(0))
/// await service.stopBloodOxygenMeasurement();
///
/// // Check status
/// if (service.isBloodOxygenMeasurementActive) {
///   print('Last value: ${service.lastBloodOxygenValue}%');
/// }
/// ```
class ChileafExtendedService {
  // Chileaf Custom Service & Characteristics (from SDK documentation)
  static const String _customServiceUuid =
      'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _txCharUuid =
      'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0'; // Read from device (NOTIFY)
  static const String _rxCharUuid =
      'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0'; // Write to device (WRITE)

  // Heart Rate Service (Standard BLE Service)
  static const String _heartRateServiceUuid = '0000180D-0000-1000-8000-00805F9B34FB';
  static const String _heartRateCharUuid = '00002A37-0000-1000-8000-00805F9B34FB';

  // Bluetooth characteristics
  BluetoothCharacteristic? _txCharacteristic;
  BluetoothCharacteristic? _rxCharacteristic;
  BluetoothCharacteristic? _heartRateCharacteristic;
  StreamSubscription? _dataSubscription;
  StreamSubscription? _heartRateSubscription;
  Timer? _dataRequestTimer;
  
  // Device connection tracking
  bool _isConnected = false;

  // ===== WEARMANAGER COMPATIBLE MULTI-PACKET SYSTEM =====
  // Variabili per gestire ricezione multi-packet come nel WearManager
  // ignore: constant_identifier_names
  static const int END_TAG = 0xFFFFFFFF;
  final List<List<int>> _hrRecordPackages = [];
  final List<List<int>> _hrDataPackages = [];
  final List<Map<String, dynamic>> _hrRecords = [];
  final List<Map<String, dynamic>> _hrDataList = [];
  int _hrDataStamp = 0;
  bool _isHRDataStamp = false;

  // ===== SLEEP AND STEPS DATA SYSTEM =====
  // Variabili per Sleep e Steps data (WearManager compatible)
  final List<List<int>> _sleepPackages = [];
  final List<List<int>> _stepsPackages = [];
  final List<Map<String, dynamic>> _sleepDataList = [];
  final List<Map<String, dynamic>> _stepsDataList = [];
  int _sleepDataStamp = 0;
  int _stepsDataStamp = 0;
  bool _isSleepDataStamp = false;
  bool _isStepsDataStamp = false;

  // Historical data service with optimized checksum
  late final HistoricalDataService _historicalDataService;
  
  // Sleep onset detector
  late final SleepOnsetDetector _sleepOnsetDetector;

  // ===== BLOOD OXYGEN (SpO2) MEASUREMENT SYSTEM =====
  // Stato della misurazione SpO2 ottimizzato seguendo pipeline ufficiale
  bool _spo2MeasurementActive = false;
  bool _spo2MeasurementPaused = false;
  bool _spo2ResultSaved = false; // NUOVO: Flag per evitare salvataggio multiplo
  String? _lastSpO2Value;
  Timer? _spo2MeasurementTimer;
  StreamSubscription? _spo2DataSubscription;

  // Callback per UI updates e completamento automatico
  void Function(String spo2Value)? _onSpO2ValueReceived;
  void Function()? _onSpO2MeasurementComplete;
  void Function(String error)? _onSpO2Error;

  // HR Callback functions
  void Function(int min, int max, int goal, bool alarmEnabled)? _onHRConfigReceived;
  void Function(int heartRate)? _onRealtimeHRReceived;
  void Function(HeartRateStatus status)? _onHRStatusChanged;

  // ===== HEART RATE CONFIGURATION SYSTEM =====
  // Configurazione HR corrente con supporto per entrambe le modalità di allarme
  HeartRateConfig? _currentHRConfig;
  int? _lastRealtimeHR;
  HeartRateStatus? _lastHRStatus;
  
  // Streams per real-time HR e configurazione
  final StreamController<int> _realtimeHRController = StreamController<int>.broadcast();
  final StreamController<HeartRateConfig> _hrConfigController = StreamController<HeartRateConfig>.broadcast();
  final StreamController<HeartRateStatus> _hrStatusController = StreamController<HeartRateStatus>.broadcast();
  
  // Getters pubblici per HR streams
  Stream<int> get realtimeHRStream => _realtimeHRController.stream;
  Stream<HeartRateConfig> get hrConfigStream => _hrConfigController.stream;
  Stream<HeartRateStatus> get hrStatusStream => _hrStatusController.stream;
  
  // Getters per valori correnti
  HeartRateConfig? get currentHRConfig => _currentHRConfig;
  int? get lastRealtimeHR => _lastRealtimeHR;
  HeartRateStatus? get lastHRStatus => _lastHRStatus;
  
  // Connection status getter
  bool get isConnected => _isConnected;

  // Setter per callback - seguendo pattern BloodOxygenSearchActivity
  void setSpO2Callbacks({
    void Function(String spo2Value)? onValueReceived,
    void Function()? onComplete,
    void Function(String error)? onError,
  }) {
    _onSpO2ValueReceived = onValueReceived;
    _onSpO2MeasurementComplete = onComplete;
    _onSpO2Error = onError;
  }

  // Setter per callback HR
  void setHRCallbacks({
    void Function(int min, int max, int goal, bool alarmEnabled)? onConfigReceived,
    void Function(int heartRate)? onRealtimeHRReceived,
    void Function(HeartRateStatus status)? onHRStatusChanged,
    void Function(String error)? onError,
  }) {
    _onHRConfigReceived = onConfigReceived;
    _onRealtimeHRReceived = onRealtimeHRReceived;
    _onHRStatusChanged = onHRStatusChanged;
  }

  // Log throttling for high-frequency data
  int _totalDataPackets = 0;
  int _healthDataLogCount = 0;
  int _sportsLogCount = 0;

  // Historical data request throttling - PREVENT INFINITE LOOPS
  int _exerciseHistoryRequests = 0;
  int _hrHistoryRequests = 0;
  DateTime? _lastExerciseHistoryRequest;
  DateTime? _lastHRHistoryRequest;
  final int _maxHistoricalRequests = 3; // Max 3 requests per session
  static const Duration _historicalRequestCooldown =
      Duration(minutes: 5); // 5 min cooldown

  // ===== LATEST HR TIMESTAMPS FOR SEQUENTIAL ACCESS =====
  final List<int> _lastRawTimestamps = [];

  // Debug logging control - VERY AGGRESSIVE THROTTLING
  final bool _enableVerboseLogging = false; // Set to true for detailed logs
  final int _logThrottleInterval = 2000; // Log every 2000 packets (reduced spam)
  final int _healthDataThrottleInterval =
      500; // Log health data every 500 occurrences
  final int _sportsThrottleInterval =
      1000; // Log every 1000th sports data (reduced spam)

  // Data Processors
  late final SpO2Processor _spo2Processor;
  late final TemperatureProcessor _temperatureProcessor;
  late final HealthProcessor _healthProcessor;

  // Diagnostics
  // Rimossi per semplificazione - la nuova pipeline gestisce tutto attraverso il comando ufficiale 0x37

  // Historical data streams
  final StreamController<List<ExerciseHistoryData>> _exerciseHistoryController =
      StreamController<List<ExerciseHistoryData>>.broadcast();
  final StreamController<HeartRateHistoryList> _hrHistoryListController =
      StreamController<HeartRateHistoryList>.broadcast();
  final StreamController<HeartRateHistoryData> _hrHistoryDataController =
      StreamController<HeartRateHistoryData>.broadcast();

  // Sleep and Steps streams
  final StreamController<List<SleepHistoryEntry>> _sleepHistoryController =
      StreamController<List<SleepHistoryEntry>>.broadcast();
  final StreamController<List<SleepData31>> _sleepData31Controller =
      StreamController<List<SleepData31>>.broadcast();
  final StreamController<List<StepIntervalEntry>> _stepsHistoryController =
      StreamController<List<StepIntervalEntry>>.broadcast();
  
  // Sleep 0x31 multi-packet buffer (current session packets)
  final List<SleepData31> _sleepData31Buffer = [];
  bool _isSleepData31Active = false;
  
  // Sleep 0x31 completed sessions accumulator (all finalized sessions)
  final List<SleepHistoryEntry> _sleepData31CompletedSessions = [];
  
  // Sleep 0x31 CACHE - keeps last received sessions even after clear
  final List<SleepHistoryEntry> _sleepData31Cache = [];
  
  // Legacy Sleep 0x05 accumulator - accumulates all packets
  final List<SleepHistoryEntry> _legacySleepAccumulator = [];
  
  // Sleep event streams (onset detection)
  final StreamController<SleepOnsetEvent> _sleepOnsetController =
      StreamController<SleepOnsetEvent>.broadcast();
  final StreamController<SleepWakeEvent> _sleepWakeController =
      StreamController<SleepWakeEvent>.broadcast();
  final StreamController<SleepPhaseChange> _sleepPhaseChangeController =
      StreamController<SleepPhaseChange>.broadcast();

  // Rope skipping streams
  final StreamController<RopeSkippingData> _ropeStatusController =
      StreamController<RopeSkippingData>.broadcast();
  final StreamController<RopeRealtimeData> _ropeRealtimeController =
      StreamController<RopeRealtimeData>.broadcast();

  // Device info streams
  final StreamController<DeviceInfo> _deviceInfoController =
      StreamController<DeviceInfo>.broadcast();
  final StreamController<String> _firmwareVersionController =
      StreamController<String>.broadcast();
  final StreamController<String> _hardwareVersionController =
      StreamController<String>.broadcast();
  final StreamController<String> _deviceNameController =
      StreamController<String>.broadcast();
  final StreamController<String> _macAddressController =
      StreamController<String>.broadcast();

  // Real-time Heart Rate streams (secondo documentazione SDK sezione 4.8)
  final StreamController<int> _realTimeHeartRateController =
      StreamController<int>.broadcast();

  // Sport Health streams (VO2 Max, HRV, Stress, Stamina)
  final StreamController<SportHealthData> _sportHealthController =
      StreamController<SportHealthData>.broadcast();
  
  // Sport Real-time streams (Steps, Distance, Calories) - Command 0x15
  final StreamController<SportRealtimeData> _sportRealtimeController =
      StreamController<SportRealtimeData>.broadcast();
  SportRealtimeData? _lastSportRealtimeData;
  
  // Heart Rate Management streams (Min/Max/Goal thresholds)
  final StreamController<HeartRateAlarm> _hrAlarmController =
      StreamController<HeartRateAlarm>.broadcast();
  final StreamController<HeartRateMax> _hrMaxController =
      StreamController<HeartRateMax>.broadcast();

  // Sensor streams (3D/6D Accelerometer + Gyroscope)
  final StreamController<Sensor3DStatus> _sensor3DStatusController =
      StreamController<Sensor3DStatus>.broadcast();
  final StreamController<Sensor3DFrequency> _sensor3DFrequencyController =
      StreamController<Sensor3DFrequency>.broadcast();
  final StreamController<Sensor6DFrequency> _sensor6DFrequencyController =
      StreamController<Sensor6DFrequency>.broadcast();
  final StreamController<Sensor6DRawData> _sensor6DDataController =
      StreamController<Sensor6DRawData>.broadcast();

  // RR Interval streams (advanced HRV analysis)
  final StreamController<List<RRIntervalData>> _rrIntervalController =
      StreamController<List<RRIntervalData>>.broadcast();

  // Single Button Press stream
  final StreamController<SingleButtonPress> _buttonPressController =
      StreamController<SingleButtonPress>.broadcast();

  // User Info streams
  final StreamController<UserInfo> _userInfoController =
      StreamController<UserInfo>.broadcast();
  final StreamController<DeviceStatus> _deviceStatusController =
      StreamController<DeviceStatus>.broadcast();

  // Constructor
  ChileafExtendedService() {
    _initializeProcessors();
  }

  void _initializeProcessors() {
    _spo2Processor = SpO2Processor();
    _temperatureProcessor = TemperatureProcessor();
    _healthProcessor = HealthProcessor();

    // Initialize historical data service with optimized checksum
    _historicalDataService = HistoricalDataService(_sendCommand);
    
    // Initialize sleep onset detector with callbacks
    _sleepOnsetDetector = SleepOnsetDetector();
    _sleepOnsetDetector.onSleepOnset = (event) {
      debugPrint(event.toString());
      _sleepOnsetController.add(event);
    };
    _sleepOnsetDetector.onWakeUp = (event) {
      debugPrint(event.toString());
      _sleepWakeController.add(event);
    };
    _sleepOnsetDetector.onPhaseChange = (event) {
      debugPrint(event.toString());
      _sleepPhaseChangeController.add(event);
    };
  }

  // Public streams - delegate to processors
  Stream<SpO2Data> get spo2DataStream => _spo2Processor.spo2DataStream;
  Stream<TemperatureData> get temperatureDataStream =>
      _temperatureProcessor.temperatureDataStream;
  Stream<HRVData> get hrvDataStream => _healthProcessor.hrvDataStream;

  // Historical data streams
  Stream<List<ExerciseHistoryData>> get exerciseHistoryStream =>
      _exerciseHistoryController.stream;
  Stream<HeartRateHistoryList> get hrHistoryListStream =>
      _hrHistoryListController.stream;
  Stream<HeartRateHistoryData> get hrHistoryDataStream =>
      _hrHistoryDataController.stream;

  // Sleep and Steps streams
  Stream<List<SleepHistoryEntry>> get sleepHistoryStream =>
      _sleepHistoryController.stream;
  Stream<List<SleepData31>> get sleepData31Stream =>
      _sleepData31Controller.stream;
  Stream<List<StepIntervalEntry>> get stepsHistoryStream =>
      _stepsHistoryController.stream;
  
  // Sleep data cache getter - returns accumulated sleep sessions
  List<SleepHistoryEntry> get cachedSleepSessions => List.from(_legacySleepAccumulator);
  
  // Sleep event streams (real-time onset detection)
  Stream<SleepOnsetEvent> get sleepOnsetStream => _sleepOnsetController.stream;
  Stream<SleepWakeEvent> get sleepWakeStream => _sleepWakeController.stream;
  Stream<SleepPhaseChange> get sleepPhaseChangeStream => _sleepPhaseChangeController.stream;

  // Real-time Heart Rate streams (SDK section 4.8)
  Stream<int> get realTimeHeartRateStream => _realTimeHeartRateController.stream;

  // Rope skipping streams
  Stream<RopeSkippingData> get ropeStatusStream => _ropeStatusController.stream;
  Stream<RopeRealtimeData> get ropeRealtimeStream =>
      _ropeRealtimeController.stream;

  // Device info streams
  Stream<DeviceInfo> get deviceInfoStream => _deviceInfoController.stream;
  Stream<String> get firmwareVersionStream => _firmwareVersionController.stream;
  Stream<String> get hardwareVersionStream => _hardwareVersionController.stream;
  Stream<String> get deviceNameStream => _deviceNameController.stream;
  Stream<String> get macAddressStream => _macAddressController.stream;

  // Sport Health streams (NEW: Advanced fitness metrics)
  Stream<SportHealthData> get sportHealthStream => _sportHealthController.stream;
  
  // Sport Real-time streams (NEW: Steps, Distance, Calories in real-time)
  Stream<SportRealtimeData> get sportRealtimeStream => _sportRealtimeController.stream;
  SportRealtimeData? get lastSportRealtimeData => _lastSportRealtimeData;
  
  // Heart Rate Management streams (NEW: Min/Max/Goal configuration)
  Stream<HeartRateAlarm> get hrAlarmStream => _hrAlarmController.stream;
  Stream<HeartRateMax> get hrMaxStream => _hrMaxController.stream;

  // Sensor streams (NEW: 3D/6D Accelerometer + Gyroscope)
  Stream<Sensor3DStatus> get sensor3DStatusStream => _sensor3DStatusController.stream;
  Stream<Sensor3DFrequency> get sensor3DFrequencyStream => _sensor3DFrequencyController.stream;
  Stream<Sensor6DFrequency> get sensor6DFrequencyStream => _sensor6DFrequencyController.stream;
  Stream<Sensor6DRawData> get sensor6DDataStream => _sensor6DDataController.stream;

  // RR Interval stream (NEW: Advanced HRV analysis)
  Stream<List<RRIntervalData>> get rrIntervalStream => _rrIntervalController.stream;

  // Button Press stream (NEW: Single button history)
  Stream<SingleButtonPress> get buttonPressStream => _buttonPressController.stream;

  // User Info streams (NEW: User profile and device status)
  Stream<UserInfo> get userInfoStream => _userInfoController.stream;
  Stream<DeviceStatus> get deviceStatusStream => _deviceStatusController.stream;

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('🚀 Starting ROBUST Chileaf Extended Service for GROK HR...');
      
      // Track connection
      _isConnected = true;

      // Longer delay to ensure full service discovery
      await Future.delayed(const Duration(milliseconds: 3000));

      final services = await device.discoverServices();

      // Find custom service - more robust matching
      BluetoothService? customService;
      BluetoothService? heartRateService;

      for (var service in services) {
        if (service.uuid.toString().toLowerCase() ==
            _customServiceUuid.toLowerCase()) {
          customService = service;
        }
        // Look for Heart Rate Service
        if (service.uuid.toString().toLowerCase() ==
            _heartRateServiceUuid.toLowerCase()) {
          heartRateService = service;
        }
      }

      customService ??= services.firstWhere(
        (s) => s.uuid
            .toString()
            .toLowerCase()
            .contains(_customServiceUuid.split('-')[0].toLowerCase()),
        orElse: () => throw Exception('Custom service not found'),
      );

      debugPrint('🎯 Using custom service: ${customService.uuid}');

      // Setup characteristics with extra verification
      await _setupCharacteristicsRobust(customService, device);

      // Verify characteristics are properly set
      if (_txCharacteristic == null || _rxCharacteristic == null) {
        throw Exception('Failed to setup critical characteristics');
      }

      // Setup Heart Rate Service if available
      if (heartRateService != null) {
        await _setupHeartRateService(heartRateService);
      }

      // Enable notifications and start data flow
      await _startDataFlow();
    } catch (e) {
      debugPrint('Failed to start Chileaf Extended Service: $e');
      // Don't rethrow - let the app continue without extended features
    }
  }

  Future<void> _setupCharacteristics(BluetoothService customService) async {
    // Find characteristics - more robust matching
    BluetoothCharacteristic? txChar, rxChar;

    for (var char in customService.characteristics) {
      final charUuid = char.uuid.toString().toLowerCase();
      if (charUuid == _txCharUuid.toLowerCase()) {
        txChar = char;
      } else if (charUuid == _rxCharUuid.toLowerCase()) {
        rxChar = char;
      }
    }

    if (txChar == null) throw Exception('TX characteristic not found');
    if (rxChar == null) throw Exception('RX characteristic not found');

    _txCharacteristic = txChar;
    _rxCharacteristic = rxChar;

    debugPrint('Found TX: ${_txCharacteristic!.uuid}');
    debugPrint('Found RX: ${_rxCharacteristic!.uuid}');
  }

  /// Robust setup with additional verification and retry logic
  Future<void> _setupCharacteristicsRobust(BluetoothService customService, BluetoothDevice device) async {
    debugPrint('🔧 Setting up characteristics with ROBUST verification...');
    
    // Debug: List all characteristics with properties
    // Find characteristics with multiple attempts
    BluetoothCharacteristic? txChar, rxChar;
    int attempts = 0;
    const maxAttempts = 3;

    while ((txChar == null || rxChar == null) && attempts < maxAttempts) {
      attempts++;

      for (var char in customService.characteristics) {
        final charUuid = char.uuid.toString().toLowerCase();
        if (charUuid == _txCharUuid.toLowerCase()) {
          txChar = char;
        } else if (charUuid == _rxCharUuid.toLowerCase()) {
          rxChar = char;
        }
      }

      if (txChar == null || rxChar == null) {
        await Future.delayed(const Duration(milliseconds: 1000));

        // Rediscover service characteristics
        final services = await device.discoverServices();
        final refreshedService = services.firstWhere(
          (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
        );
        customService = refreshedService;
      }
    }

    if (txChar == null) throw Exception('TX characteristic not found after $maxAttempts attempts');
    if (rxChar == null) throw Exception('RX characteristic not found after $maxAttempts attempts');

    _txCharacteristic = txChar;
    _rxCharacteristic = rxChar;

    // Verify properties
    debugPrint('🔍 Verifying TX properties: ${_txCharacteristic!.properties.notify}');
    debugPrint('🔍 Verifying RX properties: write=${_rxCharacteristic!.properties.write}, writeWithoutResponse=${_rxCharacteristic!.properties.writeWithoutResponse}');
    
    if (!_txCharacteristic!.properties.notify) {
      debugPrint('⚠️ WARNING: TX characteristic does not support notify');
    }
    
    if (!_rxCharacteristic!.properties.write && !_rxCharacteristic!.properties.writeWithoutResponse) {
      throw Exception('RX characteristic does not support write operations');
    }
  }

  Future<void> _setupHeartRateService(BluetoothService heartRateService) async {
    try {
      debugPrint('💓 Setting up Heart Rate Service...');
      
      // Find Heart Rate Measurement characteristic
      BluetoothCharacteristic? hrChar;
      
      for (var char in heartRateService.characteristics) {
        debugPrint('💓 HR Char: ${char.uuid}');
        if (char.uuid.toString().toLowerCase() == _heartRateCharUuid.toLowerCase()) {
          hrChar = char;
          break;
        }
      }
      
      if (hrChar == null) {
        debugPrint('💓 Heart Rate Measurement characteristic not found');
        return;
      }
      
      debugPrint('💓 Found HR characteristic: ${hrChar.uuid}');
      
      // Enable notifications for Heart Rate
      if (hrChar.properties.notify) {
        await hrChar.setNotifyValue(true);
        debugPrint('💓 Heart Rate notifications enabled');
        
        _heartRateSubscription = hrChar.lastValueStream.listen(
          _processHeartRateData,
          onError: (error) {
            debugPrint('💓 Heart Rate notification error: $error');
          },
        );
      } else {
        debugPrint('💓 Heart Rate characteristic does not support notifications');
      }
      
    } catch (e) {
      debugPrint('💓 Failed to setup Heart Rate Service: $e');
    }
  }

  void _processHeartRateData(List<int> data) {
    try {
      if (data.isEmpty) return;
      
      debugPrint('💓 RAW HR DATA: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // Parse Heart Rate according to BLE Heart Rate Service specification
      // https://www.bluetooth.com/specifications/gatt/viewer?attributeUuid=org.bluetooth.characteristic.heart_rate_measurement
      
      int heartRate = 0;
      
      // Check if Heart Rate is 16-bit (flag bit 0)
      if ((data[0] & 0x01) == 0) {
        // 8-bit heart rate
        if (data.length > 1) {
          heartRate = data[1];
        }
      } else {
        // 16-bit heart rate (little endian)
        if (data.length > 2) {
          heartRate = data[1] + (data[2] << 8);
        }
      }
      
      debugPrint('💓 Parsed HR: $heartRate BPM');
      
      // Validate heart rate range
      if (heartRate > 0 && heartRate < 250) {
        _realTimeHeartRateController.add(heartRate);
        _handleRealtimeHeartRate(heartRate);
        debugPrint('💓 Real-time HR via BLE Service: $heartRate BPM');
      } else {
        debugPrint('💓 Invalid HR value: $heartRate BPM');
      }
      
    } catch (e) {
      debugPrint('💓 Error processing HR data: $e');
    }
  }

  Future<void> _startDataFlow() async {
    // Enable notifications on TX characteristic
    if (_txCharacteristic!.properties.notify) {
      await _txCharacteristic!.setNotifyValue(true);
      debugPrint('Extended service notifications enabled');

      _dataSubscription = _txCharacteristic!.lastValueStream.listen(
        _processIncomingData,
        onError: (error) {
          debugPrint('Extended service notification error: $error');
        },
      );
    } else {
      debugPrint('TX characteristic does not support notifications');
    }

    // CRITICAL: Set device time FIRST before any other operations
    // This is essential for historical data accuracy
    await _syncDeviceTime();

    // Initial commands to start data flow
    // await Future.delayed(const Duration(milliseconds: 500));
    // await _sendCommand(CommandBuilder.buildTemperatureDataRequest());

    // // Set up periodic data requests (only medical-grade sensors)
    // _dataRequestTimer =
    //     Timer.periodic(const Duration(seconds: 5), (timer) async {
    //   try {
    //     await _sendCommand(CommandBuilder.buildTemperatureDataRequest());
    //   } catch (e) {
    //     debugPrint('Error in periodic data request: $e');
    //   }
    // });
  }

  /// Sincronizza l'ora del dispositivo con l'ora corrente del telefono
  /// ESSENZIALE per la corretta gestione dei dati storici
  Future<void> _syncDeviceTime() async {
    try {
      int currentUtc = DateTime.now().millisecondsSinceEpoch ~/ 1000;
      List<int> setTimeCommand = OfficialChileafCommands.setUTCTime(currentUtc);
      
      debugPrint('🕐 SYNC DEVICE TIME: Setting UTC to $currentUtc (${DateTime.fromMillisecondsSinceEpoch(currentUtc * 1000)})');
      debugPrint('🕐 Command: ${OfficialChileafCommands.commandToHexString(setTimeCommand)}');
      
      await _sendCommand(setTimeCommand);
      
      // Aspetta un momento per permettere al dispositivo di processare
      await Future.delayed(const Duration(milliseconds: 1000));
      
      debugPrint('✅ Device time synchronized successfully');
    } catch (e) {
      debugPrint('❌ Failed to sync device time: $e');
    }
  }

  void _processIncomingData(List<int> data) {
    _totalDataPackets++;

    if (data.isEmpty) return;

    try {
      // Get command first to determine logging strategy
      final command = ChileafProtocol.extractCommand(data);

      // Smart throttling based on command type
      bool shouldLog = _enableVerboseLogging || _shouldLogCommand(command);

      if (shouldLog) {
        if (_enableVerboseLogging) {
          debugPrint(
              '🔄 _processIncomingData called with ${data.length} bytes');
        } else {
          // Log batch updates much less frequently
          if (_totalDataPackets % _logThrottleInterval == 0) {
            debugPrint(
                '🔄 Processed $_totalDataPackets packets (batch update)');
          }
        }
      }

      // Log frame details ONLY for important commands or errors
      bool isHighFrequency = command == ChileafProtocol.commandHealthData ||
          command == ChileafProtocol.commandSports;

      // NEVER log frame details for high frequency data
      if (_enableVerboseLogging && !isHighFrequency) {
        ChileafProtocol.logFrameDetails(data);
      }

      // Handle different data formats
      if (ChileafProtocol.isValidChileafFrame(data)) {
        if (command == null) return;

        // Only log command processing for NON-high frequency commands and exclude accelerometer
        if (_enableVerboseLogging || (!isHighFrequency && 
            command != ChileafProtocol.commandTemperature && 
            command != ChileafProtocol.commandAccelerometer)) {
          debugPrint('Processing command: ${ChileafProtocol.getCommandName(command)}');
        }

        // Route to appropriate processor
        _routeToProcessor(command, data, shouldLog);
      } else if (data.length >= 4) {}
    } catch (e) {
      debugPrint('Error processing Chileaf data: $e');
    }
  }

  bool _shouldLogCommand(int? command) {
    if (command == null) return false;

    switch (command) {
      case ChileafProtocol.commandHealthData:
        _healthDataLogCount++;
        return _healthDataLogCount % _healthDataThrottleInterval == 0;
      case ChileafProtocol.commandSports:
        _sportsLogCount++;
        return _sportsLogCount % _sportsThrottleInterval == 0;
      default:
        return true; // Always log other commands (device info, historical data, etc.)
    }
  }

  void _routeToProcessor(int command, List<int> data, bool shouldLog) {
    switch (command) {
      case 0x01: // Device Info
        debugPrint('📱 DEVICE INFO: Processing device information');
        var deviceInfo = DeviceInfoProcessor.processDeviceInfo(data);
        if (deviceInfo != null) {
          _deviceInfoController.add(deviceInfo);
        }
        break;
      case 0x03: // User Info & Device Status (Protocol 0x03) OR Firmware Version (depending on length)
        if (data.length >= 15) {
          // Likely User Info response (longer packet)
          debugPrint('� USER INFO: Processing user information and device status');
          _processUserInfoResponse(data);
        } else {
          // Firmware version (shorter packet)
          debugPrint('�💾 FIRMWARE VERSION: Processing firmware version');
          var firmwareVersion = DeviceInfoProcessor.processFirmwareVersion(data);
          if (firmwareVersion != null) {
            _firmwareVersionController.add(firmwareVersion);
          }
        }
        break;
      case 0x04: // Hardware Version
        debugPrint('🔧 HARDWARE VERSION: Processing hardware version');
        var hardwareVersion = DeviceInfoProcessor.processHardwareVersion(data);
        if (hardwareVersion != null) {
          _hardwareVersionController.add(hardwareVersion);
        }
        break;
      case 0x05: // Device Name OR Sleep Data - Context dependent
        debugPrint('📱🌙 DEVICE NAME/SLEEP (0x05): Checking data context...');
        debugPrint('🔍 Raw data: ${_commandToHexString(data)}');
        
        // Verifica se è una risposta del sonno controllando il cmd byte (come nel WearManager)
        if (data.length > 3) {
          int cmd = data[3] & 0xFF;
          debugPrint('🔍 Command byte: $cmd (0x${cmd.toRadixString(16)})');
          
          if (cmd == 3) {
            // Sleep data response (WearManager exact match: mode 5, cmd 3)
            debugPrint('🌙 SLEEP DATA: Processing sleep history (cmd 3 detected - WearManager format)');
            _processSleepHistoryDataExact(data);
          } else if (data.length > 15) {
            // Fallback: longer packets might be sleep data
            debugPrint('🌙 SLEEP DATA: Processing sleep history (longer packet detected)');
            _processSleepHistoryData(data);
          } else {
            // Device name (shorter response)
            debugPrint('📱 DEVICE NAME: Processing device name');
            var deviceName = DeviceInfoProcessor.processDeviceName(data);
            if (deviceName != null) {
              _deviceNameController.add(deviceName);
            }
          }
        } else {
          debugPrint('📱 DEVICE NAME: Processing device name (short packet)');
          var deviceName = DeviceInfoProcessor.processDeviceName(data);
          if (deviceName != null) {
            _deviceNameController.add(deviceName);
          }
        }
        break;
      case 0x06: // MAC Address
        debugPrint('🔗 MAC ADDRESS: Processing MAC address');
        var macAddress = DeviceInfoProcessor.processMacAddress(data);
        if (macAddress != null) {
          _macAddressController.add(macAddress);
        }
        break;
      case 0x31: // Sleep Data (OFFICIAL COMMAND from documentation 2.14)
        debugPrint('🌙💤 SLEEP DATA 0x31: Processing official sleep data response');
        _processSleepData31(data);
        break;
      case 0x32: // Sleep Data End Signal (OFFICIAL from documentation)
        debugPrint('🌙✅ SLEEP DATA 0x32: End signal received - ALL sleep data transmitted');
        
        // Finalizza l'ultima sessione se presente
        if (_sleepData31Buffer.isNotEmpty) {
          debugPrint('🔄 Finalizing last session...');
          _finalizeSleepData31();
        }
        
        // Log summary totale
        debugPrint('📊 COMPLETE DOWNLOAD SUMMARY:');
        debugPrint('   ✅ Total sessions received: ${_sleepData31CompletedSessions.length}');
        if (_sleepData31CompletedSessions.isNotEmpty) {
          int totalMinutes = _sleepData31CompletedSessions.fold(0, (sum, s) => sum + (s.actions.length * 5));
          debugPrint('   ⏱️  Total sleep time: $totalMinutes minutes');
          debugPrint('   📅 Date range: ${_sleepData31CompletedSessions.first.timestamp} to ${_sleepData31CompletedSessions.last.timestamp}');
          
          // Salva in cache PRIMA di pulire
          _sleepData31Cache.clear();
          _sleepData31Cache.addAll(_sleepData31CompletedSessions);
          debugPrint('   💾 Cached ${_sleepData31Cache.length} sessions for later access');
        }
        
        // Reset accumulator per il prossimo download
        _sleepData31CompletedSessions.clear();
        debugPrint('🧹 Accumulator cleared - ready for next download cycle');
        break;
      case ChileafProtocol.commandSports:
        // SPORT REAL-TIME DATA (Command 0x15)
        // Format: FF LL 15 SSSSSS DDDDDD CCCCCC XX
        // Steps, Distance (cm), Calories*10
        // 
        // NOTE: This data arrives VERY frequently (every second), so we throttle logging
        try {
          final sportData = SportRealtimeData.fromBytes(data);
          
          // Only log if values changed significantly or it's been a while
          bool shouldLogSport = _lastSportRealtimeData == null ||
              (sportData.steps - _lastSportRealtimeData!.steps).abs() >= 5 ||
              (sportData.caloriesKcal - _lastSportRealtimeData!.caloriesKcal).abs() >= 5.0;
          
          if (shouldLogSport) {
            debugPrint('🏃 SPORT UPDATE: ${sportData.steps} steps, ${sportData.distanceKm.toStringAsFixed(2)}km, ${sportData.caloriesKcal.toStringAsFixed(1)}kcal');
          }
          
          _lastSportRealtimeData = sportData;
          _sportRealtimeController.add(sportData);
        } catch (e) {
          debugPrint('🏃 ❌ Error parsing sport data: $e');
          debugPrint('🏃 Raw bytes (${data.length}): ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
        }
        break;
      case ChileafProtocol.commandSpo2:
        debugPrint('🫁 ===== BLOOD OXYGEN DATA RECEIVED =====');
        debugPrint('🫁 BLOOD OXYGEN DATA RECEIVED (Command 0x37)!');
        debugPrint('📊 Processing through official pipeline...');
        debugPrint('🔍 RAW FRAME DATA: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
        debugPrint('🔍 RAW FRAME (decimal): ${data.join(' ')}');
        debugPrint('🔍 Frame length: ${data.length} bytes');
        
        // Analisi preliminare del frame prima del processing
        if (data.length >= 8) {
          debugPrint('🔍 FRAME ANALYSIS:');
          debugPrint('   Byte 0 (Start): 0x${data[0].toRadixString(16)} (${data[0]})');
          debugPrint('   Byte 1 (Length): 0x${data[1].toRadixString(16)} (${data[1]})');
          debugPrint('   Byte 2 (Command): 0x${data[2].toRadixString(16)} (${data[2]})');
          debugPrint('   Byte 3 (Status): 0x${data[3].toRadixString(16)} (${data[3]})');
          debugPrint('   Byte 4 (SpO2 Value): 0x${data[4].toRadixString(16)} (${data[4]}%) ⬅️ QUESTO È IL VALORE PRINCIPALE');
          debugPrint('   Byte 5 (Posture): 0x${data[5].toRadixString(16)} (${data[5]})');
          debugPrint('   Byte 6 (PI Signal): 0x${data[6].toRadixString(16)} (${data[6]})');
          debugPrint('   Byte 7 (On Wrist): 0x${data[7].toRadixString(16)} (${data[7]})');
          if (data.length > 8) {
            debugPrint('   Additional bytes: ${data.skip(8).map((b) => '0x${b.toRadixString(16)}').join(' ')}');
          }
        }
        
        debugPrint('🔄 Forwarding to SpO2Processor for detailed analysis...');
        _spo2Processor.processSPO2Data(data);
        debugPrint('🫁 ===== END BLOOD OXYGEN PROCESSING =====');
        break;
      case ChileafProtocol.commandTemperature:
        // SILENTLY process temperature data - no logging
        _temperatureProcessor.processTemperatureData(data);
        break;
      case ChileafProtocol.commandHealthData:
        // SILENTLY process health data - no logging
        _healthProcessor.processHealthData(data);
        break;
      case ChileafProtocol.commandAccelerometer:
        // SILENTLY ignore accelerometer data - no logging (simplified app focus)
        break;
      case 0x16: // Exercise History
        debugPrint(
            '📊 EXERCISE HISTORY DATA: Processing historical exercise data with OFFICIAL format');
        
        // Usa il processore per analizzare i dati grezzi
        var exerciseHistory =
            HistoricalDataProcessor.processExerciseHistory(
                Uint8List.fromList(data));
        
        if (exerciseHistory.isNotEmpty) {
          _exerciseHistoryController.add(exerciseHistory);
          debugPrint('📊 ✅ Exercise history processed: ${exerciseHistory.length} entries');
        } else {
          debugPrint('📊 ⚠️ No valid exercise history entries found');
        }
        break;
      case 0x21: // HR History Record List (WearManager compatible)
        debugPrint('📋 HR RECORD LIST: Processing HR timestamp list (WearManager mode)');
        _processHRRecordList(data);
        break;
      case 0x22: // HR History Data (WearManager compatible - mode 34)
      case 0x23: // HR History End/Data (WearManager compatible - mode 35) 
        debugPrint('💓 HR HISTORY DATA: Processing detailed HR data (WearManager mode ${command == 0x22 ? '34' : '35'})');
        _processHRHistoryData(data, command);
        break;
      case 0x40: // Rope Status OR Steps Interval - Context dependent
        debugPrint('🪢👟 ROPE/STEPS (0x40): Processing rope or steps data (context-dependent)...');
        debugPrint('🔍 Raw data: ${_commandToHexString(data)}');
        
        // Try to determine context by analyzing data patterns
        if (data.length > 10) {
          // Likely steps interval data (longer packets)
          debugPrint('👟 STEPS DATA: Processing step intervals (longer packet detected)');
          _processStepsIntervalData(data);
        } else {
          // Likely rope status (shorter packets)
          debugPrint('🪢 ROPE STATUS: Processing rope skipping status data');
          var ropeStatus = RopeSkippingProcessor.processRopeStatus(data);
          if (ropeStatus != null) {
            _ropeStatusController.add(ropeStatus);
          }
        }
        break;
      case 0x41: // Rope Realtime
        debugPrint('🪢⚡ ROPE REALTIME: Processing realtime rope notifications');
        var ropeRealtime = RopeSkippingProcessor.processRopeRealtime(data);
        if (ropeRealtime != null) {
          _ropeRealtimeController.add(ropeRealtime);
        }
        break;
      case 0x47: // Sleep Status/Configuration Response
        debugPrint('🌙💤 SLEEP STATUS (0x47): Processing sleep-related response');
        debugPrint('🔍 Raw data: ${_commandToHexString(data)}');
        
        // Check if this contains ASCII text (like "clear_sleep_")
        if (data.length >= 10) {
          try {
            // Try to decode as ASCII string starting from byte 3
            List<int> asciiBytes = data.sublist(3, data.length > 15 ? 15 : data.length);
            String asciiText = String.fromCharCodes(asciiBytes.where((b) => b >= 32 && b <= 126));
            if (asciiText.isNotEmpty) {
              debugPrint('🔍 ASCII content: "$asciiText"');
              
              if (asciiText.contains('clear_sleep')) {
                debugPrint('🧹 SLEEP CLEAR: Device is clearing sleep data (normal operation)');
              } else if (asciiText.contains('sleep')) {
                debugPrint('🌙 SLEEP STATUS: Sleep-related status message received');
              }
            }
          } catch (e) {
            debugPrint('🔍 Could not decode ASCII content: $e');
          }
        }
        
        // Log detailed analysis for debugging
        if (data.length == 23) {
          debugPrint('🔍 COMMAND 0x47 RAW BYTES (23 bytes - Sleep/HR Config):');
          String hexString = data.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ');
          debugPrint('🔍 Full response: $hexString');
          
          // Look for HR threshold patterns (typical values 40-200) or sleep data
          for (int i = 3; i < data.length; i++) {
            int value = data[i];
            if (value >= 40 && value <= 200) {
              debugPrint('🔍 Potential HR value at byte $i: $value BPM');
            }
          }
        }
        break;
      
      // ===== NEW HANDLERS FOR SPORT HEALTH & SENSORS =====
      
      case 0x13: // Sport Health Data (VO2 Max, Breath Rate, Emotion, Stress, Stamina)
        // Real-time data sent automatically during sports activity
        // Format from iOS SDK: [0xFF, length, 0x13, vo2Max, breathRate, emotion, stress, stamina, checksum]
        debugPrint('🏃 SPORT HEALTH: Processing real-time health metrics');
        _processSportHealthData(data);
        break;
      
      case 0x4E: // Body Health Response (LEGACY - may not be used)
        debugPrint('🏃 SPORT HEALTH (0x4E): Processing body health data');
        _processSportHealthData(data);
        break;
      
      case 0x4F: // Health Monitoring Status
        debugPrint('🏃 HEALTH MONITORING: Processing monitoring status');
        if (data.length >= 4) {
          int status = data[3];
          debugPrint('🏃 Monitoring ${status == 1 ? "STARTED" : "STOPPED"}');
        }
        break;
      
      case 0x43: // Heart Rate Status Response (Min/Max/Goal)
        debugPrint('❤️ HR STATUS: Processing heart rate configuration');
        _processHRConfigResponse(data);
        break;
      
      case 0x44: // Heart Rate Alarm Response
        debugPrint('⏰ HR ALARM: Processing heart rate alarm status');
        _processHRAlarmResponse(data);
        break;
      
      case 0x45: // Heart Rate Max Response
        debugPrint('📈 HR MAX: Processing heart rate maximum');
        _processHRMaxResponse(data);
        break;
      
      case 0x46: // 3D Sensor Frequency Response (existing but enhanced)
        debugPrint('📡 3D FREQ: Processing 3D sensor frequency');
        _process3DFrequencyResponse(data);
        break;
      
      case 0x48: // 6D Sensor Frequency Response
        debugPrint('📡 6D FREQ: Processing 6D sensor frequency');
        _process6DFrequencyResponse(data);
        break;
      
      case 0x49: // RR Intervals Response
        debugPrint('💓 RR INTERVALS: Processing RR interval history');
        _processRRIntervalsResponse(data);
        break;
      
      case 0x4A: // Shutdown Confirmation
        debugPrint('🔴 SHUTDOWN: Device shutdown acknowledged');
        break;
      
      case 0x4B: // Factory Restoration Confirmation
        debugPrint('⚠️ RESTORATION: Factory reset acknowledged');
        break;
      
      case 0x4C: // Button Press History Response
        debugPrint('🔘 BUTTON: Processing button press history');
        _processButtonPressResponse(data);
        break;
      
      case 0x6D: // 6D Sensor Raw Data Stream
        debugPrint('📊 6D DATA: Processing gyroscope + accelerometer data');
        _process6DRawDataStream(data);
        break;
      
      case 0x90: // Steps Interval List Response
        debugPrint('👟 STEPS INTERVAL LIST (0x90): Processing steps interval history response');
        debugPrint('🔍 Raw data: ${_commandToHexString(data)}');
        _processStepsIntervalData(data);
        break;
      
      case 0x91: // Steps Interval Data Response  
        debugPrint('👟 STEPS INTERVAL DATA (0x91): Processing detailed steps interval data');
        debugPrint('🔍 Raw data: ${_commandToHexString(data)}');
        _processStepsIntervalData(data);
        break;
      
      default:
        // Log ALL unhandled commands with full hex dump
        String hexString = data.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ');
        debugPrint('⚠️ Unhandled command: 0x${command.toRadixString(16)} (${data.length} bytes)');
        debugPrint('⚠️ Full hex: $hexString');
        
        // SPECIAL HANDLER for command 0x57 (HR Alarm SET response)
        if (command == 0x57) {
          debugPrint('🚨 COMMAND 0x57 RAW BYTES (HR ALARM SET RESPONSE):');
          String hexString = data.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ');
          debugPrint('🚨 Full response: $hexString');
          
          if (data.length >= 4) {
            int status = data[3];
            debugPrint('🚨 ===== HR ALARM SET RESPONSE =====');
            debugPrint('🚨 Status: $status (${status == 0 ? "SUCCESS" : status == 1 ? "ENABLED" : "ERROR/UNKNOWN"})');
            debugPrint('🚨 Alarm command processed');
            debugPrint('🚨 =====================================');
          }
        }
        
        // SPECIAL HANDLER for command 0x5B (HR Alarm GET response)
        if (command == 0x5B) {
          debugPrint('🔍 COMMAND 0x5B RAW BYTES (HR ALARM GET RESPONSE):');
          String hexString = data.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ');
          debugPrint('🔍 Full response: $hexString');
          
          if (data.length >= 4) {
            int status = data[3];
            debugPrint('🔍 ===== HR ALARM STATUS =====');
            debugPrint('🔍 Alarm Status: $status (${status == 0 ? "DISABLED" : status == 1 ? "ENABLED" : "UNKNOWN"})');
            debugPrint('🔍 ============================');
            
            // Update callback with current alarm status
            if (_onHRConfigReceived != null) {
              // Get current HR values from UI state or use defaults
              int currentMin = 60;  // We'll need to track these properly
              int currentMax = 180;
              int currentGoal = 120;
              _onHRConfigReceived!(currentMin, currentMax, currentGoal, status == 1);
              debugPrint('🔄 Alarm status sent to UI via callback: ${status == 1 ? "ENABLED" : "DISABLED"}');
            }
          }
        }
        
        // SPECIAL HANDLER for command 0x46 (HR configuration response - 8 bytes)
        if (command == 0x46 && data.length == 8) {
          debugPrint('❤️ COMMAND 0x46 RAW BYTES (HR STATUS RESPONSE):');
          String hexString = data.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ');
          debugPrint('❤️ Full 8 bytes: $hexString');
          
          // Decode HR status response according to official format
          // Expected: [0xFF, 0x08, 0x46, status, min, max, goal, checksum]
          debugPrint('❤️ DECODING HR STATUS:');
          debugPrint('❤️ Header: 0x${data[0].toRadixString(16)} 0x${data[1].toRadixString(16)} 0x${data[2].toRadixString(16)}');
          
          if (data.length >= 7) {
            int status = data[3];
            int minHR = data[4];
            int maxHR = data[5];
            int goalHR = data[6];
            int checksum = data[7];
            
            debugPrint('❤️ ===== HR CONFIGURATION DECODED =====');
            debugPrint('❤️ Status: $status (${status == 0 ? "GET Response" : status == 1 ? "SET Response" : "Unknown"})');
            debugPrint('❤️ Min HR: $minHR BPM');
            debugPrint('❤️ Max HR: $maxHR BPM');
            debugPrint('❤️ Goal HR: $goalHR BPM');
            debugPrint('❤️ Checksum: 0x${checksum.toRadixString(16).toUpperCase()}');
            debugPrint('❤️ =====================================');
            
            // Verify checksum
            List<int> frameWithoutChecksum = data.sublist(0, data.length - 1);
            int calculatedChecksum = _calculateJavaChecksum(frameWithoutChecksum);
            bool checksumValid = checksum == calculatedChecksum;
            debugPrint('❤️ Checksum verification: ${checksumValid ? "✅ VALID" : "❌ INVALID"} (expected: 0x${calculatedChecksum.toRadixString(16).toUpperCase()})');
            
            // Validate HR values (reasonable ranges)
            bool valuesValid = (minHR >= 40 && minHR <= 200) && 
                              (maxHR >= 40 && maxHR <= 200) && 
                              (goalHR >= 40 && goalHR <= 200) &&
                              (minHR < maxHR);
            debugPrint('❤️ Values validation: ${valuesValid ? "✅ VALID RANGES" : "❌ INVALID RANGES"}');
            
            if (checksumValid && valuesValid) {
              debugPrint('✅ HR Configuration successfully decoded and validated!');
              
              // Update internal configuration with manual mode (default)
              _currentHRConfig = HeartRateConfig.manual(
                minHeartRate: minHR,
                maxHeartRate: maxHR,
                goalHeartRate: goalHR,
                alarmEnabled: status != 0, // Will be updated by alarm status command
              );
              _hrConfigController.add(_currentHRConfig!);
              
              // Call callback to update UI
              if (_onHRConfigReceived != null) {
                _onHRConfigReceived!(minHR, maxHR, goalHR, status != 0);
                debugPrint('🔄 HR Configuration sent to UI via callback');
              }
              
              debugPrint('🔄 Internal HR configuration updated');
            }
          }
        }
    }
  }

  // Process RR intervals from heart rate data for HRV calculation
  void processRRIntervalsForHRV(HeartRateData heartRateData) {
    _healthProcessor.processRRIntervalsForHRV(heartRateData);
  }

  // ===== WEARMANAGER COMPATIBLE HR HISTORY METHODS =====
  // Implementazione secondo WearManager_HR_Analysis.md

  /// Imposta l'ora UTC sul dispositivo (equivalente a setUTCTime() nel WearManager)
  /// Comando: 0x08 (8)
  /// Formato: [0xFF, 0x09, 0x08, utc_bytes(4), checksum]
  Future<void> setUTCTime([int? customTimestamp]) async {
    debugPrint('⏰ Setting UTC time on device (WearManager compatible)...');
    
    try {
      // Usa timestamp custom o quello corrente
      int utcTimestamp = customTimestamp ?? _getZoneUTC();
      
      debugPrint('🕒 UTC timestamp to send: $utcTimestamp');
      debugPrint('🕒 Human readable: ${DateTime.fromMillisecondsSinceEpoch(utcTimestamp * 1000)}');
      
      // Costruisci comando secondo formato WearManager
      List<int> utcBytes = _utcToBytes(utcTimestamp);
      List<int> command = OfficialChileafCommands.buildOfficialCommand(8, utcBytes);
      
      debugPrint('📡 UTC sync command: ${_commandToHexString(command)}');
      
      await _sendCommand(command);
      debugPrint('✅ UTC time sync command sent successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to set UTC time: $e');
      rethrow;
    }
  }

  /// Ottiene timestamp UTC corrente (equivalente a DateUtil.getZoneUTC())
  int _getZoneUTC() {
    // Use TimestampDecoder helper (more explicit and correct)
    return TimestampDecoder.getZoneUTC();
    
    // Old implementation (kept for reference, but incorrect):
    // DateTime now = DateTime.now();
    // int zoneOffset = now.timeZoneOffset.inMilliseconds;
    // DateTime utcTime = now.add(Duration(milliseconds: zoneOffset));
    // return utcTime.millisecondsSinceEpoch ~/ 1000;
  }

  /// Converte timestamp UTC in array di 4 bytes (equivalente a utc2Bytes())
  List<int> _utcToBytes(int timestamp) {
    return [
      (timestamp >> 24) & 0xFF,
      (timestamp >> 16) & 0xFF,
      (timestamp >> 8) & 0xFF,
      timestamp & 0xFF,
    ];
  }

  /// Richiede la lista dei record HR storici (equivalente a getHistoryOfHRRecord())
  /// Comando: 0x21 (33)
  /// Formato: [0xFF, 0x04, 0x21, 0x00, checksum]
  Future<void> getHistoryOfHRRecord() async {
    debugPrint('📋 Requesting HR history record list (WearManager compatible)...');
    
    try {
      // Clear type per ricevere solo dati HR record (mode 33)
      // Equivalente a this.mReceivedDataCallback.clearType(4);
      
      // Costruisci comando secondo formato WearManager: comando 33 con parametro 0
      List<int> command = OfficialChileafCommands.buildOfficialCommand(33, [0]);
      
      debugPrint('📡 HR record command: ${_commandToHexString(command)}');
      debugPrint('🔍 Expected response: mode 33 with HR timestamp list');
      debugPrint('🔍 Each record: 4 bytes timestamp UTC (big-endian)');
      debugPrint('🔍 End marker: 0xFFFFFFFF indicates end of transmission');
      
      await _sendCommand(command);
      debugPrint('✅ HR history record command sent successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to request HR history records: $e');
      rethrow;
    }
  }

  /// Richiede dati HR dettagliati per timestamp specifico (equivalente a getHistoryOfHRData(long stamp))
  /// Comando: 0x22 (34)
  /// Formato: [0xFF, length, 0x22, 0x01, utc_bytes(4), checksum]
  Future<void> getHistoryOfHRData(int timestamp) async {
    debugPrint('💓 Requesting HR history data for timestamp: $timestamp (WearManager compatible)...');
    
    try {
      // Clear type per ricevere solo dati HR dettagliati (mode 34)
      // Equivalente a this.mReceivedDataCallback.clearType(6);
      
      // Costruisci parametri: 1 + timestamp in bytes
      List<int> timestampBytes = _utcToBytes(timestamp);
      List<int> parameters = [1] + timestampBytes;
      
      // Costruisci comando secondo formato WearManager
      List<int> command = OfficialChileafCommands.buildOfficialCommand(34, parameters);
      
      debugPrint('📡 HR data command: ${_commandToHexString(command)}');
      debugPrint('🔍 Timestamp: $timestamp (${DateTime.fromMillisecondsSinceEpoch(timestamp * 1000)})');
      debugPrint('🔍 Expected response: mode 34 with HR measurements');
      debugPrint('🔍 Format: skip 4 bytes, then 1 byte per HR value');
      debugPrint('🔍 End marker: 0xFFFFFFFF indicates end of transmission');
      
      await _sendCommand(command);
      debugPrint('✅ HR history data command sent successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to request HR history data: $e');
      rethrow;
    }
  }

  /// Richiede tutti i dati HR (equivalente a getHistoryOfHRData() senza parametri)
  /// Comando: 0x22 (34)
  /// Formato: [0xFF, 0x04, 0x22, 0x00, checksum]
  Future<void> getHistoryOfHRDataAll() async {
    debugPrint('💓 Requesting ALL HR history data (WearManager compatible)...');
    
    try {
      // Clear type per ricevere solo dati HR (mode 34)
      // Equivalente a this.mReceivedDataCallback.clearType(2);
      
      // Costruisci comando: comando 34 con parametro 0 (tutti i dati)
      List<int> command = OfficialChileafCommands.buildOfficialCommand(34, [0]);
      
      debugPrint('📡 HR data ALL command: ${_commandToHexString(command)}');
      debugPrint('🔍 Expected response: mode 34 with all available HR data');
      debugPrint('🔍 Multi-packet response expected');
      
      await _sendCommand(command);
      debugPrint('✅ HR history data ALL command sent successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to request all HR history data: $e');
      rethrow;
    }
  }

  /// Converte comando in stringa hex per debug
  String _commandToHexString(List<int> command) {
    return command.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
  }

  /// Sequenza completa di recupero HR history seguendo protocollo WearManager
  /// 1. Sync UTC per sbloccare dati storici
  /// 2. Richiedi lista timestamp HR
  /// 3. Richiedi dati dettagliati per ogni timestamp
  Future<void> performCompleteHRHistorySequence() async {
    debugPrint('🔄 Starting COMPLETE HR history sequence (WearManager protocol)...');
    
    try {
      // Step 1: Sync UTC per sbloccare dati storici del dispositivo
      debugPrint('🔐 Step 1: UTC sync to unlock historical data...');
      await setUTCTime();
      
      // Attendi che la sincronizzazione sia processata dal dispositivo
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Step 2: Richiedi lista timestamp HR disponibili
      debugPrint('📋 Step 2: Requesting HR record timestamps...');
      await getHistoryOfHRRecord();
      
      // Attendi che i timestamp arrivino
      await Future.delayed(const Duration(milliseconds: 1500));
      
      // Step 3: Richiedi tutti i dati HR (approccio alternativo)
      debugPrint('💓 Step 3: Requesting all HR data...');
      await getHistoryOfHRDataAll();
      
      // Attendi che i dati arrivino
      await Future.delayed(const Duration(milliseconds: 3000));
      
      debugPrint('✅ Complete HR history sequence finished!');
      debugPrint('📊 Check logs for HR record timestamps and data responses');
      
    } catch (e) {
      debugPrint('❌ Complete HR history sequence failed: $e');
      rethrow;
    }
  }

  // ===== SLEEP AND STEPS DATA METHODS (WearManager compatible) =====

  /// Richiede dati del sonno dal dispositivo (comando 0x05)
  /// Equivalente a getHistoryOfSleep() nel WearManager
  Future<void> getHistoryOfSleep() async {
    debugPrint('🌙 Requesting sleep history data (WearManager compatible)...');
    
    try {
      // Clear dei dati precedenti (equivalente a clearType(22) nel WearManager)
      _sleepPackages.clear();
      _sleepDataList.clear();
      _sleepDataStamp = 0;
      _isSleepDataStamp = false;
      debugPrint('🧹 Cleared previous sleep data (clearType 22)');
      
      // Costruisci comando secondo formato WearManager: sendCommand((byte)5, new int[] { 2 });
      List<int> command = OfficialChileafCommands.buildOfficialCommand(5, [2]);
      
      debugPrint('📡 Sleep data command: ${_commandToHexString(command)}');
      debugPrint('🔍 Expected response: mode 5, cmd 3 with sleep action indices');
      debugPrint('🔍 Java format: len + utc(4 bytes) + actions[len]');
      debugPrint('🔍 Time correction: utc *= 1000, utc -= 28800000 (8h offset)');
      
      await _sendCommand(command);
      debugPrint('✅ Sleep history command sent successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to request sleep history: $e');
      rethrow;
    }
  }

  /// Test completo per il sonno - prova diversi comandi e parametri
  Future<void> testAllSleepCommands() async {
    debugPrint('🌙🧪 COMPREHENSIVE SLEEP TEST - Testing all possible commands...');
    
    try {
      // Test 1: Comando WearManager standard
      debugPrint('🧪 TEST 1/4: Standard WearManager command (5, [2])');
      await Future.delayed(const Duration(milliseconds: 500));
      List<int> cmd1 = OfficialChileafCommands.buildOfficialCommand(5, [2]);
      debugPrint('📡 CMD1: ${_commandToHexString(cmd1)}');
      await _sendCommand(cmd1);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 2: Prova con parametro 3 (cmd che cerca il parser)
      debugPrint('🧪 TEST 2/4: Alternative parameter (5, [3])');
      List<int> cmd2 = OfficialChileafCommands.buildOfficialCommand(5, [3]);
      debugPrint('📡 CMD2: ${_commandToHexString(cmd2)}');
      await _sendCommand(cmd2);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 3: Prova senza parametri
      debugPrint('🧪 TEST 3/4: No parameters (5, [])');
      List<int> cmd3 = OfficialChileafCommands.buildOfficialCommand(5, []);
      debugPrint('📡 CMD3: ${_commandToHexString(cmd3)}');
      await _sendCommand(cmd3);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 4: Prova con parametri multipli
      debugPrint('🧪 TEST 4/4: Multiple parameters (5, [2, 3])');
      List<int> cmd4 = OfficialChileafCommands.buildOfficialCommand(5, [2, 3]);
      debugPrint('📡 CMD4: ${_commandToHexString(cmd4)}');
      await _sendCommand(cmd4);
      
      debugPrint('✅ All sleep command tests completed! Check logs for responses.');
      
    } catch (e) {
      debugPrint('❌ Sleep test failed: $e');
      rethrow;
    }
  }

  /// Richiede dati del contapassi dal dispositivo (comando 0x40)  
  /// Equivalente a getIntervalSteps() nel WearManager
  Future<void> getIntervalSteps() async {
    debugPrint('👟 Requesting steps interval data (WearManager compatible)...');
    
    try {
      // Costruisci comando secondo formato WearManager
      List<int> command = OfficialChileafCommands.buildOfficialCommand(64, []);
      
      debugPrint('📡 Steps data command: ${_commandToHexString(command)}');
      debugPrint('🔍 Expected response: mode 64 with step intervals');
      debugPrint('🔍 Format: timestamp + step count per interval');
      debugPrint('🔍 End marker: 0xFFFFFFFF indicates end of transmission');
      
      await _sendCommand(command);
      debugPrint('✅ Steps interval command sent successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to request steps data: $e');
      rethrow;
    }
  }

  /// Sequenza completa per ottenere tutti i dati (HR + Sleep + Steps)
  Future<void> performCompleteDataSequence() async {
    debugPrint('🔄 Starting COMPLETE data sequence (HR + Sleep + Steps)...');
    
    try {
      // Step 1: UTC sync per sbloccare tutti i dati storici
      debugPrint('🔐 Step 1: UTC sync to unlock all historical data...');
      await setUTCTime();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Step 2: Richiedi dati HR
      debugPrint('💓 Step 2: Requesting HR data...');
      await getHistoryOfHRRecord();
      await Future.delayed(const Duration(milliseconds: 1500));
      
      // Step 3: Richiedi dati del sonno
      debugPrint('🌙 Step 3: Requesting sleep data...');
      await getHistoryOfSleep();
      await Future.delayed(const Duration(milliseconds: 1500));
      
      // Step 4: Richiedi dati dei passi
      debugPrint('👟 Step 4: Requesting steps data...');
      await getIntervalSteps();
      await Future.delayed(const Duration(milliseconds: 1500));
      
      debugPrint('✅ Complete data sequence finished!');
      debugPrint('📊 Check logs and UI for HR, Sleep, and Steps data');
      
    } catch (e) {
      debugPrint('❌ Complete data sequence failed: $e');
      rethrow;
    }
  }

  // ===== WEARMANAGER DATA PARSING METHODS =====

  /// Processa lista di record HR (mode 0x21) secondo logica WearManager
  void _processHRRecordList(List<int> data) {
    debugPrint('📋 Processing HR record list (WearManager style)...');
    debugPrint('📋 Raw data: ${_commandToHexString(data)}');
    
    if (data.length < 7) {
      debugPrint('❌ HR record data too short: ${data.length} bytes');
      return;
    }
    
    // Parse UTC tag (bytes 3-6, big-endian)
    int utcTag = _getLongParse(data, 3, 4);
    debugPrint('📋 UTC tag: 0x${utcTag.toRadixString(16)} ($utcTag)');
    
    if (utcTag != END_TAG) {
      // Accumula pacchetti fino al tag di fine
      debugPrint('📋 Accumulating packet (${data.length} bytes)');
      _hrRecordPackages.add(List.from(data));
    } else {
      debugPrint('📋 End tag received, processing accumulated packets...');
      debugPrint('📋 Total packets accumulated: ${_hrRecordPackages.length}');
      
      // Processa tutti i pacchetti accumulati
      _hrRecords.clear();
      
      for (int index = 0; index < _hrRecordPackages.length; index++) {
        List<int> packet = _hrRecordPackages[index];
        List<int> slice = _subSlice(3, packet); // Skip header (3 bytes)
        
        debugPrint('📋 Processing packet $index: ${slice.length} bytes payload');
        
        // Ogni record HR è esattamente 4 bytes (timestamp UTC)
        for (int i = 0; i < slice.length ~/ 4; i++) {
          int offset = i * 4;
          if (offset + 4 <= slice.length) {
            int stamp = _getLongParse(slice, offset, 4); // Big-endian parsing
            int record = _restoreZoneUTC(stamp); // UTC → Local time
            
            debugPrint('📋 HR Record ${_hrRecords.length + 1}: stamp=$stamp, local=$record');
            debugPrint('📋 → ${DateTime.fromMillisecondsSinceEpoch(record)}');
            
            _hrRecords.add({
              'originalStamp': stamp,
              'localTime': record,
              'dateTime': DateTime.fromMillisecondsSinceEpoch(record),
            });
          }
        }
      }
      
      debugPrint('✅ HR Record processing complete: ${_hrRecords.length} records found');
      
      // Invia i dati al stream per l'UI
      if (_hrRecords.isNotEmpty) {
        debugPrint('📋 HR Records available for detailed data requests - sending to UI stream');
        
        // Converti i timestamp in formato HeartRateHistoryList
        List<DateTime> timestamps = _hrRecords.map((record) {
          return record['dateTime'] as DateTime;
        }).toList();
        
        List<int> rawTimestamps = _hrRecords.map((record) {
          return record['originalStamp'] as int;
        }).toList();
        
        HeartRateHistoryList historyList = HeartRateHistoryList(
          timestamps: timestamps,
          rawTimestamps: rawTimestamps,
          isEndOfData: true, // Assumiamo fine dati per ora
        );
        
        // Emetti la lista nel stream
        _hrHistoryListController.add(historyList);
        debugPrint('📤 Sent ${timestamps.length} HR record timestamps to UI stream');
        
      } else {
        debugPrint('📋 No HR records found in device');
      }
      
      // Clear packages per prossima richiesta
      _hrRecordPackages.clear();
    }
  }

  /// Processa dati HR dettagliati (mode 0x22/0x23) secondo logica WearManager
  void _processHRHistoryData(List<int> data, int mode) {
    debugPrint('💓 Processing HR history data (WearManager style) - mode: $mode (0x${mode.toRadixString(16)})');
    debugPrint('💓 Raw data: ${_commandToHexString(data)}');
    
    if (data.length < 7) {
      debugPrint('❌ HR data too short: ${data.length} bytes');
      return;
    }
    
    if (mode == 0x22) { // Mode 34 - Data packet accumulation
      debugPrint('💓 Mode 34 (0x22): Accumulating HR data packet...');
      
      // Extract timestamp from first packet if not set
      if (!_isHRDataStamp) {
        _hrDataStamp = _getLongParse(data, 3, 4);
        _isHRDataStamp = true;
        debugPrint('💓 Set initial timestamp: $_hrDataStamp');
      }
      
      // Add packet to accumulator
      _hrDataPackages.add(List.from(data));
      debugPrint('💓 Accumulated packet ${_hrDataPackages.length} (${data.length} bytes)');
      
    } else if (mode == 0x23) { // Mode 35 - Process accumulated data
      debugPrint('💓 Mode 35 (0x23): Processing all accumulated HR data...');
      debugPrint('💓 Total packets accumulated: ${_hrDataPackages.length}');
      
      // Clear previous data
      _hrDataList.clear();
      
      // Process all accumulated packets
      for (int index = 0; index < _hrDataPackages.length; index++) {
        List<int> packet = _hrDataPackages[index];
        List<int> slice = _subSlice(3, packet); // Skip header (3 bytes)
        
        debugPrint('💓 Processing packet $index: ${slice.length} bytes payload');
        
        // According to WearManager: skip first 4 bytes, then 1 byte per HR value
        for (int i = 4; i < slice.length; i++) {
          int heartRate = slice[i] & 0xFF; // 1 byte per HR value
          int localStamp = _restoreZoneUTC(_hrDataStamp);
          
          // Reduced logging - only log first and every 100th value
          if (_hrDataList.isEmpty || _hrDataList.length % 100 == 0) {
            debugPrint('💓 HR Value ${_hrDataList.length + 1}: $heartRate bpm at stamp $_hrDataStamp');
            debugPrint('💓 → ${DateTime.fromMillisecondsSinceEpoch(localStamp)}');
          }
          
          _hrDataList.add({
            'heartRate': heartRate,
            'stamp': _hrDataStamp,
            'localTime': localStamp,
            'dateTime': DateTime.fromMillisecondsSinceEpoch(localStamp),
          });
          
          _hrDataStamp++; // Increment timestamp for next record
        }
      }
      
      debugPrint('✅ HR Data processing complete: ${_hrDataList.length} measurements found');
      
      // Send processed data to UI stream
      if (_hrDataList.isNotEmpty) {
        debugPrint('💓 HR measurements available - applying outlier filter...');
        
        // OUTLIER FILTER: Remove HR values that are statistical outliers
        List<Map<String, dynamic>> filteredHRData = _filterHROutliers(_hrDataList);
        
        debugPrint('💓 Outlier filter: ${_hrDataList.length} → ${filteredHRData.length} measurements (${_hrDataList.length - filteredHRData.length} outliers removed)');
        
        // Convert to HeartRateHistoryData format
        List<HeartRateHistoryEntry> entries = filteredHRData.map((data) {
          return HeartRateHistoryEntry(
            heartRate: data['heartRate'],
            time: data['dateTime'],
            activityIndex: 0, // Default activity index
          );
        }).toList();
        
        HeartRateHistoryData historyData = HeartRateHistoryData(
          timestamp: DateTime.now(),
          entries: entries,
        );
        
        // Emit to stream
        _hrHistoryDataController.add(historyData);
        debugPrint('📤 Sent ${entries.length} filtered HR measurements to UI stream');
        
      } else {
        debugPrint('💓 No HR measurements found in accumulated data');
      }
      
      // Clear accumulators for next request
      _hrDataPackages.clear();
      _isHRDataStamp = false;
      _hrDataStamp = 0;
    }
  }

  // ===== UTILITY METHODS (WearManager compatible) =====

  /// Parse big-endian multi-byte value (equivalente a getLongParse())
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    int end = pos + len;
    for (int i = pos; i < end && i < bytes.length; i++) {
      val <<= 8;
      val |= bytes[i] & 0xFF;
    }
    return val;
  }

  /// Skip header bytes (equivalente a subSlice())
  List<int> _subSlice(int start, List<int> data) {
    if (start >= data.length) return [];
    return data.sublist(start);
  }

  /// Converte timestamp UTC in timestamp locale (equivalente a restoreZoneUTC())
  int _restoreZoneUTC(int stamp) {
    // Converte da secondi UTC a millisecondi locali
    DateTime utcTime = DateTime.fromMillisecondsSinceEpoch(stamp * 1000, isUtc: true);
    DateTime localTime = utcTime.toLocal();
    return localTime.millisecondsSinceEpoch;
  }

  /// Filtra outlier HR usando metodo IQR (Interquartile Range)
  /// Rimuove valori che si discostano eccessivamente dalla distribuzione normale
  /// Esempio: se la maggior parte dei valori è 70-80 BPM, rimuove il valore isolato di 159 BPM
  List<Map<String, dynamic>> _filterHROutliers(List<Map<String, dynamic>> hrDataList) {
    if (hrDataList.length < 5) {
      // Troppo pochi dati per applicare filtro statistico
      debugPrint('🔍 OUTLIER FILTER: Too few data points (${hrDataList.length}), skipping filter');
      return hrDataList;
    }

    // Estrai solo i valori HR per calcoli statistici
    List<int> hrValues = hrDataList.map((data) => data['heartRate'] as int).toList();
    hrValues.sort(); // Ordina per calcoli percentili

    // Calcola quartili per metodo IQR
    int n = hrValues.length;
    double q1 = _calculatePercentile(hrValues, 25); // Primo quartile (25%)
    double q3 = _calculatePercentile(hrValues, 75); // Terzo quartile (75%)
    double iqr = q3 - q1; // Interquartile Range
    
    // Calcola limiti outlier (1.5 * IQR è standard statistico)
    double lowerBound = q1 - (1.5 * iqr);
    double upperBound = q3 + (1.5 * iqr);

    debugPrint('🔍 OUTLIER FILTER STATISTICS:');
    debugPrint('   📊 Total HR values: $n');
    debugPrint('   📈 Q1 (25%): ${q1.toStringAsFixed(1)} BPM');
    debugPrint('   📈 Q3 (75%): ${q3.toStringAsFixed(1)} BPM');
    debugPrint('   📏 IQR: ${iqr.toStringAsFixed(1)} BPM');
    debugPrint('   🚫 Lower bound: ${lowerBound.toStringAsFixed(1)} BPM');
    debugPrint('   🚫 Upper bound: ${upperBound.toStringAsFixed(1)} BPM');

    // Filtra outlier mantenendo solo valori entro i limiti
    List<Map<String, dynamic>> filtered = [];
    List<int> removedOutliers = [];

    for (var data in hrDataList) {
      int hrValue = data['heartRate'] as int;
      
      if (hrValue >= lowerBound && hrValue <= upperBound) {
        filtered.add(data); // Valore normale, mantieni
      } else {
        removedOutliers.add(hrValue); // Outlier, rimuovi
      }
    }

    if (removedOutliers.isNotEmpty) {
      debugPrint('🗑️ REMOVED OUTLIERS: ${removedOutliers.join(', ')} BPM');
      debugPrint('✅ CLEAN DATA RANGE: ${filtered.map((d) => d['heartRate']).reduce((a, b) => a < b ? a : b)}-${filtered.map((d) => d['heartRate']).reduce((a, b) => a > b ? a : b)} BPM');
    } else {
      debugPrint('✅ NO OUTLIERS DETECTED: All HR values within normal range');
    }

    return filtered;
  }

  /// Calcola percentile per una lista ordinata di valori
  double _calculatePercentile(List<int> sortedValues, double percentile) {
    double index = (percentile / 100) * (sortedValues.length - 1);
    
    if (index == index.floorToDouble()) {
      // Indice esatto
      return sortedValues[index.toInt()].toDouble();
    } else {
      // Interpolazione lineare tra due valori
      int lowerIndex = index.floor();
      int upperIndex = index.ceil();
      double fraction = index - lowerIndex;
      
      return sortedValues[lowerIndex] + 
             (fraction * (sortedValues[upperIndex] - sortedValues[lowerIndex]));
    }
  }

  // ===== SLEEP AND STEPS DATA PROCESSING METHODS =====

  /// Processa dati del sonno con parsing esatto dal WearManager Java
  void _processSleepHistoryDataExact(List<int> data) {
    debugPrint('🌙 Processing sleep history data (EXACT WearManager Java format)...');
    debugPrint('🌙 Raw data: ${_commandToHexString(data)}');
    
    if (data.length < 4) {
      debugPrint('❌ Sleep data too short: ${data.length} bytes');
      return;
    }
    
    List<int> value = data;
    int cmd = value[3] & 0xFF;
    
    if (cmd == 3) {
      debugPrint('🌙 Processing cmd 3 - Sleep history data');
      
      List<SleepHistoryEntry> sleepSessions = [];
      
      // Parsing esatto dal WearManager Java (linee 112-129)
      for (int j = 4; j < value.length; j++) {
        int len = value[j] & 0xFF;
        
        // Reduced logging - only show problematic entries
        if (len == 0) {
          debugPrint('🌙 Skipping empty sleep entry (length 0) at position $j');
          continue;
        }
        
        // Log only unusual lengths
        if (len > 50 || sleepSessions.isEmpty) {
          debugPrint('🌙 Sleep entry length: $len at position $j');
        }
        
        if (len >= 1) {
          j++;
          if (j + 4 >= value.length) {
            debugPrint('❌ Not enough data for UTC timestamp');
            break;
          }
          
          // long utc = getLongParse(value, j, 4);
          int utc = _getLongParse(value, j, 4);
          j += 4;
          
          // ✅ FIX: Convert UTC timestamp to local timezone
          // Device sends UTC timestamp, we convert to user's local time
          // NO MORE hardcoded -8 hours (China timezone)!
          int utcMillis = utc * 1000;
          DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);
          DateTime localDateTime = utcDateTime.toLocal();
          
          // Reduced logging - only log every few sessions
          if (sleepSessions.isEmpty || sleepSessions.length % 3 == 0) {
            debugPrint('🌙 Sleep UTC: $utc -> ${utcDateTime.toIso8601String()} -> Local: ${localDateTime.toIso8601String()} (${localDateTime.timeZoneName})');
          }
          
          // Check if we have enough data for actions, but don't break - skip invalid entries
          if (j + len > value.length) {
            debugPrint('❌ Not enough data for actions array (need $len, have ${value.length - j} remaining), skipping this entry');
            // Skip this invalid entry - continue to next iteration
            continue;
          }
          
          // int[] actions = new int[len];
          List<int> actions = [];
          for (int i = 0; i < len; i++) {
            int action = value[i + j] & 0xFF;
            actions.add(action);
          }
          j += len - 1; // Move to the last action byte (loop will increment to next len)
          
          // Reduced logging - only show first few actions
          if (sleepSessions.isEmpty || sleepSessions.length % 5 == 0) {
            debugPrint('🌙 Sleep actions (${actions.length}): ${actions.take(10).join(", ")}${actions.length > 10 ? "..." : ""}');
          }
          
          // HistorySleep historySleep = new HistorySleep(utc, actions);
          SleepHistoryEntry sleepEntry = SleepHistoryEntry(
            timestamp: localDateTime,  // ✅ Now in user's local timezone
            count: actions.length,
            actions: actions,
          );
          
          sleepSessions.add(sleepEntry);
          
          // Check exit condition
          if (j >= value.length - 1) {
            debugPrint('🌙 Reached end of data');
            break;
          }
        }
      }
      
      debugPrint('✅ Sleep parsing complete: ${sleepSessions.length} sessions found in this packet');
      
      if (sleepSessions.isNotEmpty) {
        // Add to accumulator instead of replacing
        _legacySleepAccumulator.addAll(sleepSessions);
        debugPrint('� Accumulated ${sleepSessions.length} sessions -> Total: ${_legacySleepAccumulator.length}');
        
        // Send the full accumulated list to UI
        _sleepHistoryController.add(List.from(_legacySleepAccumulator));
        debugPrint('📤 Sent ${_legacySleepAccumulator.length} total sleep sessions to UI stream');
      } else {
        debugPrint('🌙 No valid sleep sessions found in this packet');
      }
    } else {
      debugPrint('❌ Expected cmd 3 for sleep data, got cmd $cmd');
    }
  }

  /// Processa dati del sonno (mode 0x05) secondo logica WearManager
  void _processSleepHistoryData(List<int> data) {
    debugPrint('🌙 Processing sleep history data (WearManager style)...');
    debugPrint('🌙 Raw data: ${_commandToHexString(data)}');
    
    if (data.length < 7) {
      debugPrint('❌ Sleep data too short: ${data.length} bytes');
      return;
    }
    
    // Parse UTC tag (bytes 3-6, big-endian)
    int utcTag = _getLongParse(data, 3, 4);
    debugPrint('🌙 UTC tag: 0x${utcTag.toRadixString(16)} ($utcTag)');
    
    if (utcTag != END_TAG) {
      // Accumula pacchetti fino al tag di fine
      debugPrint('🌙 Accumulating sleep packet (${data.length} bytes)');
      _sleepPackages.add(List.from(data));
    } else {
      debugPrint('🌙 End tag received, processing accumulated sleep packets...');
      debugPrint('🌙 Total packets accumulated: ${_sleepPackages.length}');
      
      // Processa tutti i pacchetti per dati del sonno
      _sleepDataList.clear();
      
      for (int index = 0; index < _sleepPackages.length; index++) {
        List<int> packet = _sleepPackages[index];
        List<int> slice = _subSlice(3, packet); // Skip header (3 bytes)
        
        debugPrint('🌙 Processing sleep packet $index: ${slice.length} bytes payload');
        
        // Inizializza timestamp se è il primo pacchetto
        if (!_isSleepDataStamp && slice.length >= 4) {
          _sleepDataStamp = _getLongParse(slice, 0, 4);
          _isSleepDataStamp = true;
          debugPrint('🌙 Initial sleep timestamp: $_sleepDataStamp');
        }
        
        // Skip 4 bytes iniziali, poi processa action indices
        List<int> actions = [];
        for (int i = 4; i < slice.length; i++) {
          int actionIndex = slice[i] & 0xFF;
          actions.add(actionIndex);
        }
        
        if (actions.isNotEmpty) {
          int localStamp = _restoreZoneUTC(_sleepDataStamp);
          DateTime sleepTime = DateTime.fromMillisecondsSinceEpoch(localStamp);
          
          debugPrint('🌙 Sleep session: ${actions.length} actions at $sleepTime');
          debugPrint('🌙 Action pattern: ${actions.take(10).join(", ")}${actions.length > 10 ? "..." : ""}');
          
          _sleepDataList.add({
            'timestamp': sleepTime,
            'actions': actions,
            'stamp': _sleepDataStamp,
          });
          
          _sleepDataStamp++; // Incrementa per sessione successiva
        }
      }
      
      debugPrint('✅ Sleep data processing complete: ${_sleepDataList.length} sessions found');
      
      // Invia i dati processati al stream per l'UI
      if (_sleepDataList.isNotEmpty) {
        debugPrint('🌙 Sleep sessions available - sending to UI stream');
        
        List<SleepHistoryEntry> entries = _sleepDataList.map((data) {
          return SleepHistoryEntry(
            timestamp: data['timestamp'],
            count: (data['actions'] as List<int>).length,
            actions: data['actions'],
          );
        }).toList();
        
        _sleepHistoryController.add(entries);
        debugPrint('📤 Sent ${entries.length} sleep sessions to UI stream');
        
      } else {
        debugPrint('🌙 No sleep sessions found in response');
      }
      
      // Reset per prossima richiesta
      _sleepPackages.clear();
      _isSleepDataStamp = false;
      _sleepDataStamp = 0;
    }
  }

  /// Processa dati del contapassi (mode 0x40) secondo logica WearManager
  void _processStepsIntervalData(List<int> data) {
    debugPrint('👟 Processing steps interval data (WearManager style)...');
    debugPrint('👟 Raw data: ${_commandToHexString(data)}');
    
    if (data.length < 7) {
      debugPrint('❌ Steps data too short: ${data.length} bytes');
      return;
    }
    
    // Parse UTC tag (bytes 3-6, big-endian)
    int utcTag = _getLongParse(data, 3, 4);
    debugPrint('👟 UTC tag: 0x${utcTag.toRadixString(16)} ($utcTag)');
    
    if (utcTag != END_TAG) {
      // Accumula pacchetti fino al tag di fine
      debugPrint('👟 Accumulating steps packet (${data.length} bytes)');
      _stepsPackages.add(List.from(data));
    } else {
      debugPrint('👟 End tag received, processing accumulated steps packets...');
      debugPrint('👟 Total packets accumulated: ${_stepsPackages.length}');
      
      // Processa tutti i pacchetti per dati dei passi
      _stepsDataList.clear();
      
      for (int index = 0; index < _stepsPackages.length; index++) {
        List<int> packet = _stepsPackages[index];
        List<int> slice = _subSlice(3, packet); // Skip header (3 bytes)
        
        debugPrint('👟 Processing steps packet $index: ${slice.length} bytes payload');
        
        // Inizializza timestamp se è il primo pacchetto
        if (!_isStepsDataStamp && slice.length >= 4) {
          _stepsDataStamp = _getLongParse(slice, 0, 4);
          _isStepsDataStamp = true;
          debugPrint('👟 Initial steps timestamp: $_stepsDataStamp');
        }
        
        // Skip 4 bytes iniziali, poi processa step counts (2 bytes per valore)
        for (int i = 4; i < slice.length; i += 2) {
          if (i + 1 < slice.length) {
            int stepCount = _getLongParse(slice, i, 2); // 2 bytes per step count
            int localStamp = _restoreZoneUTC(_stepsDataStamp);
            DateTime stepTime = DateTime.fromMillisecondsSinceEpoch(localStamp);
            
            debugPrint('👟 Step interval: $stepCount steps at $stepTime');
            
            _stepsDataList.add({
              'timestamp': stepTime,
              'steps': stepCount,
              'stamp': _stepsDataStamp,
            });
            
            _stepsDataStamp += 300; // Incrementa di 5 minuti (300 secondi) per intervallo
          }
        }
      }
      
      debugPrint('✅ Steps data processing complete: ${_stepsDataList.length} intervals found');
      
      // Invia i dati processati al stream per l'UI
      if (_stepsDataList.isNotEmpty) {
        debugPrint('👟 Step intervals available - sending to UI stream');
        
        List<StepIntervalEntry> entries = _stepsDataList.map((data) {
          return StepIntervalEntry(
            timestamp: data['timestamp'],
            steps: data['steps'],
          );
        }).toList();
        
        _stepsHistoryController.add(entries);
        debugPrint('📤 Sent ${entries.length} step intervals to UI stream');
        
      } else {
        debugPrint('👟 No step intervals found in response');
      }
      
      // Reset per prossima richiesta
      _stepsPackages.clear();
      _isStepsDataStamp = false;
      _stepsDataStamp = 0;
    }
  }

  // ===== SLEEP DATA 0x31 METHODS (OFFICIAL PROTOCOL) =====
  
  /// Processa dati sleep con comando 0x31 (formato UFFICIALE)
  /// Documentazione SDK sezione 2.14
  /// Formato: 0x31 [UTC 4 bytes] [ACT-0] [ACT-1] ... [ACT-n]
  /// Ogni byte ACT rappresenta 5 minuti di activity index
  void _processSleepData31(List<int> data) {
    debugPrint('🌙💤 Processing Sleep Data 0x31 (OFFICIAL SDK FORMAT)...');
    debugPrint('🔍 Raw data: ${_commandToHexString(data)}');
    debugPrint('🔍 Data length: ${data.length} bytes');
    
    if (data.length < 8) {
      debugPrint('❌ Sleep 0x31 data too short: ${data.length} bytes (minimum 8)');
      return;
    }
    
    // Verifica comando = 0x31
    if (data[2] != 0x31) {
      debugPrint('❌ Expected command 0x31, got 0x${data[2].toRadixString(16)}');
      return;
    }
    
    // Parse UTC o sequence number (bytes 3-6, big-endian)
    int utcOrSequence = _getLongParse(data, 3, 4);
    
    // 🎯 LOGICA CORRETTA DALLA SDK:
    // - Primo pacchetto di OGNI sessione ha UTC timestamp
    // - Pacchetti successivi della STESSA sessione hanno sequence number
    // - Quando arriva un NUOVO UTC = NUOVA SESSIONE (finalizza la precedente!)
    
    DateTime timestamp;
    int sequenceNumber = 0;
    
    // Controlla se è UTC (valore grande, tipicamente timestamp Unix) o sequence (piccolo, 1, 2, 3...)
    // UTC è nell'ordine di 1729000000+ (ottobre 2024)
    // Sequence è nell'ordine di 1, 2, 3, 4...
    if (utcOrSequence > 1000000000) {
      // È un UTC timestamp = NUOVA SESSIONE!
      
      debugPrint('📅 NEW SESSION detected - UTC timestamp found');
      
      // ✅ FINALIZZA la sessione precedente prima di iniziare la nuova
      if (_isSleepData31Active && _sleepData31Buffer.isNotEmpty) {
        debugPrint('🔄 Finalizing previous session before starting new one...');
        _finalizeSleepData31();
      }
      
      // Converti UTC in DateTime
      int utcMillis = utcOrSequence * 1000;
      timestamp = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);
      
      debugPrint('🕐 UTC timestamp: $utcOrSequence → $timestamp');
      _isSleepData31Active = true;
      
    } else {
      // È un sequence number = CONTINUAZIONE della sessione corrente
      sequenceNumber = utcOrSequence;
      debugPrint('📦 Continuation packet - sequence: $sequenceNumber');
      
      if (_sleepData31Buffer.isEmpty) {
        debugPrint('⚠️ Sequence packet without session start - using current time');
        timestamp = DateTime.now();
        _isSleepData31Active = true;
      } else {
        // ✅ CALCOLA timestamp basandosi sugli activity indices GIÀ RICEVUTI
        // Ogni activity index = 5 minuti
        DateTime baseTimestamp = _sleepData31Buffer.first.timestamp;
        int totalPreviousIndices = _sleepData31Buffer.fold(0, (sum, packet) => sum + packet.activityIndices.length);
        
        // Offset in minuti = numero di indici × 5 minuti per indice
        int offsetMinutes = totalPreviousIndices * 5;
        timestamp = baseTimestamp.add(Duration(minutes: offsetMinutes));
        
        debugPrint('🕐 Calculated timestamp: $timestamp (base + ${offsetMinutes}min from $totalPreviousIndices indices)');
      }
    }
    
    // Estrai activity indices (da byte 7 in poi, fino a checksum escluso)
    List<int> activityIndices = [];
    for (int i = 7; i < data.length - 1; i++) { // -1 per escludere checksum
      int activityIndex = data[i] & 0xFF;
      activityIndices.add(activityIndex);
    }
    
    debugPrint('📊 Activity indices: ${activityIndices.length} blocks (${activityIndices.length * 5} minutes)');
    if (activityIndices.isNotEmpty) {
      debugPrint('📊 Sample: ${activityIndices.take(10).join(", ")}${activityIndices.length > 10 ? "..." : ""}');
    }
    
    // Classifica activity indices secondo SDK
    int awakeCount = activityIndices.where((idx) => idx > 20).length;
    int lightSleepCount = activityIndices.where((idx) => idx > 0 && idx <= 20).length;
    int deepSleepIndicators = activityIndices.where((idx) => idx == 0).length;
    
    debugPrint('📊 Breakdown: Awake=$awakeCount×5min, Light=$lightSleepCount×5min, Still=$deepSleepIndicators×5min');
    
    // Crea oggetto SleepData31
    SleepData31 sleepData = SleepData31(
      timestamp: timestamp,
      activityIndices: activityIndices,
      packetSequence: sequenceNumber,
    );
    
    // Aggiungi al buffer della SESSIONE CORRENTE
    _sleepData31Buffer.add(sleepData);
    debugPrint('💾 Added to current session buffer. Total packets: ${_sleepData31Buffer.length}');
    
    // Calcola fasi del sonno per questo pacchetto
    SleepPhases31 phases = sleepData.calculateSleepPhases();
    debugPrint('📈 Packet phases: Light=${phases.lightSleepMinutes}min, Deep=${phases.deepSleepMinutes}min, Awake=${phases.awakeMinutes}min');
    
    // NUOVO: Analizza eventi sleep (onset/wake/phase changes)
    List<SleepEvent> events = _sleepOnsetDetector.analyzeSleepData(sleepData);
    debugPrint('🔔 Detected ${events.length} sleep events in this packet');
  }
  
  /// Finalizza e invia i dati sleep quando arriva il segnale 0x32
  void _finalizeSleepData31() {
    debugPrint('🌙✅ Finalizing Sleep Data 0x31...');
    
    if (_sleepData31Buffer.isEmpty) {
      debugPrint('⚠️ No sleep data in buffer - nothing to send');
      _isSleepData31Active = false;
      return;
    }
    
    debugPrint('📊 Total sleep packets collected: ${_sleepData31Buffer.length}');
    
    // Calcola statistiche totali
    int totalIntervals = _sleepData31Buffer.fold(0, (sum, item) => sum + item.activityIndices.length);
    int totalMinutes = totalIntervals * 5;
    
    debugPrint('⏱️ Total sleep duration: $totalMinutes minutes ($totalIntervals x 5-minute intervals)');
    
    // Calcola fasi totali del sonno
    int totalLight = 0;
    int totalDeep = 0;
    int totalAwake = 0;
    
    for (var sleepData in _sleepData31Buffer) {
      SleepPhases31 phases = sleepData.calculateSleepPhases();
      totalLight += phases.lightSleepMinutes;
      totalDeep += phases.deepSleepMinutes;
      totalAwake += phases.awakeMinutes;
    }
    
    debugPrint('📈 SESSION TOTAL PHASES:');
    debugPrint('   💤 Deep sleep: $totalDeep minutes');
    debugPrint('   🌙 Light sleep: $totalLight minutes');
    debugPrint('   😴 Awake: $totalAwake minutes');
    debugPrint('   ✅ Sleep efficiency: ${totalLight + totalDeep > 0 ? ((totalLight + totalDeep) / totalMinutes * 100).toStringAsFixed(1) : 0}%');
    
    // ✅ UNISCI tutti i pacchetti della STESSA SESSIONE in UNA SOLA SleepHistoryEntry
    // Tutti i pacchetti nel buffer appartengono alla stessa sessione (stesso UTC timestamp base)
    if (_sleepData31Buffer.isEmpty) {
      debugPrint('⚠️ Empty buffer after check - nothing to merge');
      return;
    }
    
    DateTime sessionTimestamp = _sleepData31Buffer.first.timestamp;
    List<int> allActivityIndices = [];
    
    // Concatena tutti gli activity indices dei pacchetti
    for (var packet in _sleepData31Buffer) {
      allActivityIndices.addAll(packet.activityIndices);
    }
    
    debugPrint('🔗 Merged ${_sleepData31Buffer.length} packets into ONE session');
    debugPrint('📊 Total activity indices: ${allActivityIndices.length} (${allActivityIndices.length * 5} minutes)');
    
    // 🔍 STAMPA DETTAGLIATA come nell'app Android (per debugging)
    _printDetailedSleepData(sessionTimestamp, allActivityIndices);
    
    // Crea UNA SOLA sessione con TUTTI gli activity indices
    SleepHistoryEntry sessionEntry = SleepHistoryEntry(
      timestamp: sessionTimestamp,
      count: allActivityIndices.length,
      actions: allActivityIndices,
    );
    
    // ✅ ACCUMULA questa sessione con le precedenti (invece di sovrascrivere)
    _sleepData31CompletedSessions.add(sessionEntry);
    
    debugPrint('✅ Session finalized and added to accumulator');
    debugPrint('📊 Total sessions accumulated: ${_sleepData31CompletedSessions.length}');
    
    // Aggiorna anche la cache permanente (non verrà pulita)
    _sleepData31Cache.clear();
    _sleepData31Cache.addAll(_sleepData31CompletedSessions);
    
    // Invia TUTTE le sessioni accumulate al stream PRINCIPALE
    _sleepHistoryController.add(List.from(_sleepData31CompletedSessions));
    debugPrint('📤 Sent ${_sleepData31CompletedSessions.length} sleep sessions to MAIN UI stream');
    debugPrint('💾 Cached ${_sleepData31Cache.length} sessions for later access');
    
    // Invia anche allo stream dedicato 0x31 (per compatibilità futura)
    List<SleepData31> sleepSessions = List.from(_sleepData31Buffer);
    _sleepData31Controller.add(sleepSessions);
    
    // Reset buffer della SESSIONE CORRENTE (ma mantieni l'accumulator!)
    _sleepData31Buffer.clear();
    _isSleepData31Active = false;
    debugPrint('🧹 Current session buffer cleared - ready for next session');
  }

  /// Stampa i dati del sonno in formato dettagliato come l'app Android
  /// Replica il formato di HistorySleepActivity.java per facilitare il confronto
  void _printDetailedSleepData(DateTime baseTimestamp, List<int> actions) {
    debugPrint('');
    debugPrint('═══════════════════════════════════════════════════════');
    debugPrint('📋 DETAILED SLEEP DATA (Android App Format)');
    debugPrint('═══════════════════════════════════════════════════════');
    
    int zeroIndex = 0;
    List<int> pendingZeroIndices = [];
    List<DateTime> pendingZeroTimes = [];
    
    for (int i = 0; i < actions.length; i++) {
      int action = actions[i];
      DateTime utc = baseTimestamp.add(Duration(minutes: i * 5));
      String utcStr = utc.toLocal().toString().substring(0, 19);
      
      if (action > 20) {
        // Wide awake - processa zeri accumulati
        if (zeroIndex >= 3) {
          // Deep sleep
          for (int j = 0; j < pendingZeroTimes.length; j++) {
            String timeStr = pendingZeroTimes[j].toLocal().toString().substring(0, 19);
            debugPrint('utc:$timeStr');
            debugPrint('action Index: deep Sleep');
          }
        } else if (zeroIndex > 0) {
          // Light sleep
          for (int j = 0; j < pendingZeroTimes.length; j++) {
            String timeStr = pendingZeroTimes[j].toLocal().toString().substring(0, 19);
            debugPrint('utc:$timeStr');
            debugPrint('action Index: light sleep');
          }
        }
        zeroIndex = 0;
        pendingZeroIndices.clear();
        pendingZeroTimes.clear();
        // Not sleeping
        debugPrint('utc:$utcStr');
        debugPrint('action Index: not Sleep');
        
      } else if (action <= 20 && action > 0) {
        // Light sleep - processa zeri accumulati
        if (zeroIndex >= 3) {
          // Deep sleep
          for (int j = 0; j < pendingZeroTimes.length; j++) {
            String timeStr = pendingZeroTimes[j].toLocal().toString().substring(0, 19);
            debugPrint('utc:$timeStr');
            debugPrint('action Index: deep Sleep');
          }
        } else if (zeroIndex > 0) {
          // Light sleep
          for (int j = 0; j < pendingZeroTimes.length; j++) {
            String timeStr = pendingZeroTimes[j].toLocal().toString().substring(0, 19);
            debugPrint('utc:$timeStr');
            debugPrint('action Index: light sleep');
          }
        }
        zeroIndex = 0;
        pendingZeroIndices.clear();
        pendingZeroTimes.clear();
        // Light sleep
        debugPrint('utc:$utcStr');
        debugPrint('action Index: light sleep');
        
      } else {
        // action == 0: accumula
        zeroIndex++;
        pendingZeroIndices.add(i);
        pendingZeroTimes.add(utc);
      }
    }
    
    // Processa eventuali zeri finali
    if (zeroIndex >= 3) {
      for (int j = 0; j < pendingZeroTimes.length; j++) {
        String timeStr = pendingZeroTimes[j].toLocal().toString().substring(0, 19);
        debugPrint('utc:$timeStr');
        debugPrint('action Index: deep Sleep');
      }
    } else if (zeroIndex > 0) {
      for (int j = 0; j < pendingZeroTimes.length; j++) {
        String timeStr = pendingZeroTimes[j].toLocal().toString().substring(0, 19);
        debugPrint('utc:$timeStr');
        debugPrint('action Index: light sleep');
      }
    }
    
    debugPrint('═══════════════════════════════════════════════════════');
    debugPrint('');
  }

  // ===== BLOOD OXYGEN (SpO2) MEASUREMENT METHODS =====
  // Seguendo la pipeline completa dell'app ufficiale Android

  /// Avvia la misurazione SpO2 utilizzando il comando ufficiale 0x37
  /// Equivalente a BloodOxygenSearchActivity.onClick() + CL880WearManager.setBloodOxygen(1)
  /// Pipeline: UI → Callback Setup → Command → BLE TX → Device → BLE RX → Parse → Callback → UI Update
  /// IMPORTANTE: Il dispositivo controlla autonomamente la durata della misurazione - NO TIMER ARBITRARIO
  /// Il device smette automaticamente di inviare frame 0x37 quando la misurazione è completa
  Future<void> startBloodOxygenMeasurement() async {
    if (_spo2MeasurementActive) {
      debugPrint('🩸 SpO2 measurement already active, ignoring start request');
      return;
    }

    debugPrint('🩸 STARTING Blood Oxygen Measurement (Android-Compatible Sequence)');

    // CRITICAL: Pre-flight checks (like Android DeviceManager validation)
    if (_rxCharacteristic == null || _txCharacteristic == null) {
      throw Exception('BLE characteristics not ready for SpO2 measurement');
    }
    
    if (!(_txCharacteristic?.isNotifying ?? false)) {
      debugPrint('⚠️ WARNING: TX characteristic notifications may not be enabled');
      // Try to enable them
      try {
        await _txCharacteristic!.setNotifyValue(true);
        debugPrint('🔄 TX notifications enabled for SpO2');
      } catch (e) {
        debugPrint('❌ Failed to enable TX notifications: $e');
      }
    }

    try {
      // PHASE 1: CRITICAL - Setup callback reception FIRST (like Android app)
      debugPrint('🩸🔧 Phase 1: Setting up callback reception (BEFORE command)');
      _spo2MeasurementActive = true;
      _spo2MeasurementPaused = false;
      _spo2ResultSaved = false; // Reset salvataggio per nuova sessione
      _lastSpO2Value = null;
      debugPrint('🩸✅ Phase 1 Complete: State ready');

      // PHASE 2: Setup data reception monitoring (Android pattern - NO TIMER!)
      debugPrint('🩸🔧 Phase 2: Setting up data monitoring (device-controlled duration)');
      _setupBloodOxygenDataMonitoring();
      debugPrint('🩸✅ Phase 2 Complete: Data monitoring ready');

      // PHASE 3: Send command ONLY after everything is ready (Android pattern)
      debugPrint('🩸🔧 Phase 3: Sending BLE Command (callback ready)');
      debugPrint('   Command: 0x37 (55 decimal) - 1 = Start');
      
      // CRITICAL FIX: Use manual command construction like Android app
      // Based on Java: int[] command = new int[]{(byte)mode, 0}; this.sendCommand((byte)55, command);
      // sendCommand builds: [255, len, cmd, ...values, checksum] where len = 4 + values.length
      List<int> bloodOxygenCommand = [
        0xFF,    // Start byte (255)
        0x06,    // Length = 4 (base) + 2 (values: mode + padding) = 6
        0x37,    // Command (55 decimal)
        0x01,    // Mode (1 = start)
        0x00,    // Padding (second value)
        0x00     // Checksum (will be calculated)
      ];
      
      // Calculate correct checksum using Java algorithm: (-sum) ^ 58 & 0xFF
      int checksumCalc = 0;
      for (int i = 0; i < bloodOxygenCommand.length - 1; i++) {
        checksumCalc += bloodOxygenCommand[i];
      }
      checksumCalc = (-checksumCalc) & 0xFF; // Negate and mask
      checksumCalc ^= 0x3A; // XOR with 58 (0x3A)
      checksumCalc &= 0xFF; // Final mask
      bloodOxygenCommand[5] = checksumCalc;
      
      // Debug the manual command
      debugPrint('🔍 Manual SpO2 command (CORRECTED): ${bloodOxygenCommand.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      debugPrint('🔍 Length: ${bloodOxygenCommand[1]} (was 5, now 6 - FIXED!)');
      debugPrint('🔍 Java checksum: 0x${checksumCalc.toRadixString(16).padLeft(2, '0')} ((-sum) ^ 58 & 0xFF)');
      debugPrint('🔍 Matches Android: sendCommand((byte)55, new int[]{1, 0})');
      
      await _sendCommand(bloodOxygenCommand);
      debugPrint('🩸✅ Phase 3 Complete: Start command sent');

      debugPrint('🩸 Blood Oxygen Measurement Started Successfully');
      debugPrint('🩸⏱️ Waiting for device response on command 0x37...');
    } catch (e) {
      debugPrint('❌ Failed to start blood oxygen measurement: $e');
      _spo2MeasurementActive = false;
      if (_onSpO2Error != null) {
        _onSpO2Error!('Failed to start measurement: $e');
      }
      rethrow;
    }
  }

  /// Setup del monitoraggio dei dati SpO2 - il dispositivo controlla la durata
  void _setupBloodOxygenDataMonitoring() {
    debugPrint('📥 Setting up Blood Oxygen data monitoring (device-controlled)');
    debugPrint('🎯 Device will automatically stop when measurement is complete');

    // Cancel any existing subscription
    _spo2DataSubscription?.cancel();

    // Listen to SpO2 data stream from processor
    _spo2DataSubscription = _spo2Processor.spo2DataStream.listen(
      (spo2Data) {
        if (!_spo2MeasurementActive) return;

        _handleBloodOxygenReceived(spo2Data);
      },
      onError: (error) {
        debugPrint('❌ SpO2 data stream error: $error');
        if (_onSpO2Error != null) {
          _onSpO2Error!('Data reception error: $error');
        }
      },
    );
    
    // Setup a timeout as safety fallback (much longer - 3 minutes)
    _spo2MeasurementTimer?.cancel();
    _spo2MeasurementTimer = Timer(const Duration(minutes: 3), () async {
      debugPrint('⏰ SpO2 safety timeout (3 minutes) - device may be unresponsive');
      await stopBloodOxygenMeasurement();
      
      if (_onSpO2Error != null) {
        _onSpO2Error!('Measurement timeout - device unresponsive');
      }
    });
  }

  /// Gestisce i dati SpO2 ricevuti (REPLICA ESATTA del comportamento Android)
  /// Equivalente a: onBloodOxygenReceived(bluetoothDevice, final int i, final String str, int i2, int i3, int i4)
  /// Logica Android: if (str != "" && str != null && Integer.valueOf(str) > 0) → measurement complete
  /// Il dispositivo controlla autonomamente quando fermarsi - nessun timer artificiale
  void _handleBloodOxygenReceived(SpO2Data spo2Data) {
    if (!_spo2MeasurementActive) return;

    debugPrint('📊 ===== BLOOD OXYGEN DATA RECEIVED =====');
    debugPrint('🩸 VALORE PRINCIPALE SpO2: ${spo2Data.value}%');
    debugPrint('📊 Blood Oxygen Data Received:');
    debugPrint('   Value: ${spo2Data.value}%');
    debugPrint('   PI (Signal): ${spo2Data.piValue}');
    debugPrint(
        '   Gesture (Posture): ${spo2Data.gesture} (${spo2Data.correctWristPosture ? "Correct" : "Incorrect"})');
    debugPrint(
        '   On Wrist: ${spo2Data.onWrist} (${spo2Data.isWearing ? "Wearing" : "Not Wearing"})');
    debugPrint('   Reliable: ${spo2Data.isReliable}');
    
    // ANALISI DETTAGLIATA DEL VALORE
    debugPrint('🔍 ===== ANALISI DETTAGLIATA VALORE =====');
    debugPrint('🩸 SATURAZIONE OSSIGENO RILEVATA: ${spo2Data.value}%');
    
    String valueAnalysis = '';
    if (spo2Data.value == 0) {
      valueAnalysis = 'Misurazione in corso o non valida';
    } else if (spo2Data.value >= 98) {
      valueAnalysis = 'ECCELLENTE - Ossigenazione ottimale';
    } else if (spo2Data.value >= 95) {
      valueAnalysis = 'NORMALE - Ossigenazione buona';
    } else if (spo2Data.value >= 90) {
      valueAnalysis = 'BASSA - Possibile ipossiemia lieve';
    } else if (spo2Data.value > 0) {
      valueAnalysis = 'CRITICA - Ipossiemia severa';
    }
    
    debugPrint('🏥 VALUTAZIONE CLINICA: $valueAnalysis');
    debugPrint('📊 Range normale: 95-100% (valori salutari)');
    debugPrint('📊 Range di attenzione: 90-94% (monitorare)');
    debugPrint('📊 Range critico: <90% (consultare medico)');

    // Always notify UI of progress updates (like Android app)
    if (_onSpO2ValueReceived != null) {
      String displayValue = spo2Data.value > 0 ? spo2Data.value.toString() : "--";
      debugPrint('📱 UI UPDATE: Mostrando valore $displayValue% all\'utente');
      _onSpO2ValueReceived!(displayValue);
    }

    // Check for valid final reading (following Android app logic)
    if (spo2Data.value > 0 && spo2Data.isValidMeasurement && spo2Data.isReliable) {
      String valueStr = spo2Data.value.toString();
      _lastSpO2Value = valueStr;

      debugPrint('✅ ===== LETTURA FINALE VALIDA =====');
      debugPrint('✅ Valid final SpO2 reading: $valueStr%');
      debugPrint('✅ QUESTO È IL VALORE DEFINITIVO DELLA SATURAZIONE OSSIGENO');
      debugPrint('✅ Condizioni di misurazione verificate:');
      debugPrint('   ✅ Dispositivo indossato correttamente');
      debugPrint('   ✅ Postura del polso corretta');
      debugPrint('   ✅ Segnale di qualità sufficiente');
      debugPrint('   ✅ Valore nel range medico valido');
      
      // SALVA SOLO UNA VOLTA per evitare toast multipli
      if (!_spo2ResultSaved) {
        _spo2ResultSaved = true;
        debugPrint('💾 ===== SALVATAGGIO RISULTATO =====');
        debugPrint('💾 Saving SpO2 result (first valid reading of session)');
        debugPrint('💾 VALORE SALVATO: ${spo2Data.value}% SpO2');
        debugPrint('💾 Timestamp: ${DateTime.now().toIso8601String()}');
        // Qui viene chiamato il callback per salvare - solo una volta
      } else {
        debugPrint('📊 Additional valid reading (not saving - already saved)');
        debugPrint('📊 VALORE AGGIUNTIVO: ${spo2Data.value}% (già salvato il primo)');
      }
      
      debugPrint('🏁 Measurement completed by device');

      // Mark as completed (device has provided final result)
      if (!_spo2MeasurementPaused) {
        _spo2MeasurementPaused = true;
        
        // The device will stop sending frames automatically
        // We complete after a short delay to allow final data processing
        Future.delayed(const Duration(seconds: 2), () async {
          if (_spo2MeasurementActive && _spo2MeasurementPaused) {
            debugPrint('🎯 Auto-completing measurement after device signaled completion');
            debugPrint('🎯 MISURAZIONE COMPLETATA - VALORE FINALE: ${spo2Data.value}%');
            await stopBloodOxygenMeasurement();

            if (_onSpO2MeasurementComplete != null) {
              _onSpO2MeasurementComplete!();
            }
          }
        });
      }
    } else {
      debugPrint('📊 ===== LETTURA INTERMEDIA =====');
      debugPrint('📊 Intermediate SpO2 reading - device continuing measurement...');
      debugPrint('📊 VALORE INTERMEDIO: ${spo2Data.value}% (misurazione in corso)');
      
      if (!spo2Data.isValidMeasurement) {
        debugPrint('⚠️ Lettura non valida - motivi possibili:');
        if (spo2Data.value < 70 || spo2Data.value > 100) {
          debugPrint('   ⚠️ Valore fuori range normale (70-100%)');
        }
        if (!spo2Data.isReliable) {
          debugPrint('   ⚠️ Status indica lettura non affidabile');
        }
      }
      
      if (!spo2Data.isReliable) {
        debugPrint('⚠️ Condizioni di misurazione non ottimali:');
        if (!spo2Data.isWearing) debugPrint('   ⚠️ Dispositivo non rilevato sul polso');
        if (!spo2Data.correctWristPosture) debugPrint('   ⚠️ Postura del polso non corretta');
        if (spo2Data.signalQuality < 8) debugPrint('   ⚠️ Qualità del segnale insufficiente');
      }
    }
    
    debugPrint('📊 ===== FINE GESTIONE DATO SpO2 =====');
  }

  /// Ferma la misurazione SpO2 (equivalente a setBloodOxygen(0) + cleanup)
  Future<void> stopBloodOxygenMeasurement() async {
    if (!_spo2MeasurementActive) {
      debugPrint('🩸 SpO2 measurement not active, ignoring stop request');
      return;
    }

    debugPrint('🛑 STOPPING Blood Oxygen Measurement');

    try {
      // Phase 1: Send stop command (setBloodOxygen(0))
      debugPrint('📡 Sending stop command: setBloodOxygen(0)');
      
      // CRITICAL FIX: Use manual command construction for stop too
      // Java: setBloodOxygen(0) -> sendCommand((byte)55, new int[]{0, 0})
      List<int> stopCommand = [
        0xFF,    // Start byte (255)
        0x06,    // Length = 4 (base) + 2 (values: mode + padding) = 6
        0x37,    // Command (55 decimal)
        0x00,    // Mode (0 = stop)
        0x00,    // Padding (second value)
        0x00     // Checksum (will be calculated)
      ];
      
      // Calculate correct checksum using Java algorithm: (-sum) ^ 58 & 0xFF
      int checksumCalc = 0;
      for (int i = 0; i < stopCommand.length - 1; i++) {
        checksumCalc += stopCommand[i];
      }
      checksumCalc = (-checksumCalc) & 0xFF; // Negate and mask
      checksumCalc ^= 0x3A; // XOR with 58 (0x3A)
      checksumCalc &= 0xFF; // Final mask
      stopCommand[5] = checksumCalc;
      
      debugPrint('🔍 Manual STOP command (CORRECTED): ${stopCommand.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      debugPrint('🔍 Java checksum: 0x${checksumCalc.toRadixString(16).padLeft(2, '0')}');
      
      await _sendCommand(stopCommand);

      debugPrint('✅ Stop command sent successfully');

      // Phase 2: Cleanup resources
      _cleanupBloodOxygenMeasurement();

      debugPrint('🧹 Blood oxygen measurement stopped and cleaned up');
    } catch (e) {
      debugPrint('❌ Failed to stop blood oxygen measurement: $e');
      // Still cleanup even if command fails
      _cleanupBloodOxygenMeasurement();
      rethrow;
    }
  }

  /// Pulizia risorse misurazione (equivalente a cleanup Android app)
  void _cleanupBloodOxygenMeasurement() {
    debugPrint('🧹 Cleaning up blood oxygen measurement resources');

    // Reset state
    _spo2MeasurementActive = false;
    _spo2MeasurementPaused = false;
    _spo2ResultSaved = false; // Reset flag salvataggio

    // Cancel timer
    _spo2MeasurementTimer?.cancel();
    _spo2MeasurementTimer = null;

    // Cancel data subscription
    _spo2DataSubscription?.cancel();
    _spo2DataSubscription = null;

    debugPrint('✅ Cleanup complete');
  }

  /// Getter per lo stato della misurazione
  bool get isBloodOxygenMeasurementActive => _spo2MeasurementActive;
  bool get isBloodOxygenMeasurementPaused => _spo2MeasurementPaused;
  String? get lastBloodOxygenValue => _lastSpO2Value;

  // ===== CONNECTION MANAGEMENT =====

  /// Ripristina solo la caratteristica RX quando si perde
  Future<bool> _restoreRxCharacteristic() async {
    try {
      if (_txCharacteristic == null) {
        debugPrint('❌ TX characteristic also null, need full reconnection');
        return false;
      }

      debugPrint('🔄 Attempting to restore RX characteristic...');
      
      // Get device from TX characteristic (which still works)
      final device = _txCharacteristic!.device;
      
      // Re-discover services to find RX characteristic
      final services = await device.discoverServices();
      final customService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
        orElse: () => throw Exception('Custom service not found'),
      );
      
      // Find RX characteristic specifically
      BluetoothCharacteristic? rxChar;
      for (var char in customService.characteristics) {
        final charUuid = char.uuid.toString().toLowerCase();
        if (charUuid == _rxCharUuid.toLowerCase()) {
          rxChar = char;
          break;
        }
      }
      
      if (rxChar == null) {
        debugPrint('❌ RX characteristic still not found');
        return false;
      }
      
      _rxCharacteristic = rxChar;
      debugPrint('✅ RX characteristic restored: ${_rxCharacteristic!.uuid}');
      return true;
      
    } catch (e) {
      debugPrint('❌ Failed to restore RX characteristic: $e');
      return false;
    }
  }

  /// Verifica e ripristina la connessione se necessario
  Future<bool> _ensureConnection() async {
    try {
      if (_rxCharacteristic == null) {
        debugPrint('⚠️ RX characteristic null, attempting restore...');
        
        // Try to restore just the RX characteristic first
        final restored = await _restoreRxCharacteristic();
        if (restored) {
          return true;
        }
        
        // If restore failed, try full rediscovery
        if (_isConnected) {
          debugPrint('🔄 Attempting full characteristics rediscovery...');
          
          // Find the device again
          final devices = FlutterBluePlus.connectedDevices;
          for (final device in devices) {
            try {
              final services = await device.discoverServices();
              final customService = services.firstWhere(
                (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
                orElse: () => throw Exception('Custom service not found'),
              );
              
              await _setupCharacteristics(customService);
              debugPrint('✅ Characteristics rediscovered successfully');
              return true;
            } catch (e) {
              continue;
            }
          }
        }
        
        return false;
      }

      final device = _rxCharacteristic!.device;
      final connectionState = await device.connectionState.first;
      
      if (connectionState != BluetoothConnectionState.connected) {
        debugPrint('⚠️ Device disconnected, attempting reconnection...');
        _isConnected = false;
        
        // Attempt reconnection
        await device.connect(mtu: null, license: License.free);
        await Future.delayed(const Duration(milliseconds: 1000));
        
        // Re-discover services and characteristics
        final services = await device.discoverServices();
        final customService = services.firstWhere(
          (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
          orElse: () => throw Exception('Custom service not found'),
        );
        
        await _setupCharacteristics(customService);
        _isConnected = true;
        debugPrint('✅ Device reconnected successfully');
        return true;
      }
      
      return true;
    } catch (e) {
      debugPrint('❌ Connection recovery failed: $e');
      _isConnected = false;
      return false;
    }
  }

  // ===== COMMAND SENDING =====

  /// Invia un comando BLE al dispositivo
  /// Gestisce automaticamente writeWithoutResponse vs write normale
  Future<void> _sendCommand(List<int> frame) async {
    // Verifica e ripristina connessione se necessaria
    final isConnected = await _ensureConnection();
    if (!isConnected) {
      throw Exception('Unable to establish connection to device');
    }
    
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available after connection check');
    }

    final hexString = frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
    debugPrint('📡 Sending BLE Command: $hexString');
    
    // Decodifica speciale per SpO2 per debug
    if (frame.length >= 4 && frame[2] == 0x37) {
      final mode = frame[3];
      debugPrint('🩸 SpO2 Command Details:');
      debugPrint('   Command: 0x37 (55 decimal)');
      debugPrint('   Mode: $mode (${mode == 1 ? "START" : mode == 0 ? "STOP" : "UNKNOWN"})');
      debugPrint('   Expected response: Command 55 frames with SpO2 data');
      debugPrint('   Frame structure: [0xFF, length, 0x37, mode, padding, checksum]');
      debugPrint('   RX Characteristic available: ${_rxCharacteristic != null}');
      debugPrint('   TX Characteristic available: ${_txCharacteristic != null}');
      debugPrint('   Notifications enabled: ${_txCharacteristic?.isNotifying ?? false}');
    }

    try {
      if (_rxCharacteristic!.properties.writeWithoutResponse) {
        await _rxCharacteristic!.write(frame, withoutResponse: true);
        debugPrint('✅ Command sent (writeWithoutResponse)');
      } else {
        await _rxCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Command sent (write with response)');
      }
      
      // Longer delay for SpO2 commands to ensure device processing
      if (frame.length >= 4 && frame[2] == 0x37) {
        await Future.delayed(const Duration(milliseconds: 500));
        debugPrint('🩸 SpO2 command processing delay completed');
      } else {
        await Future.delayed(const Duration(milliseconds: 100));
      }
    } catch (e) {
      debugPrint('❌ Command send failed: $e');
      throw Exception('Command sending failed: $e');
    }
  }

  Future<void> stop() async {
    debugPrint('Stopping Chileaf Extended Service...');
    _dataRequestTimer?.cancel();
    _dataRequestTimer = null;

    // Stop SpO2 measurement if active and cleanup
    if (_spo2MeasurementActive) {
      await stopBloodOxygenMeasurement();
    }
    _spo2MeasurementTimer?.cancel();
    _spo2MeasurementTimer = null;

    await _dataSubscription?.cancel();
    _dataSubscription = null;
    
    await _heartRateSubscription?.cancel();
    _heartRateSubscription = null;

    // Close historical data streams
    await _exerciseHistoryController.close();
    await _hrHistoryListController.close();
    await _hrHistoryDataController.close();
  }

  void dispose() {
    debugPrint('Disposing Chileaf Extended Service...');
    stop();
    
    // Reset connection state
    _isConnected = false;

    // Close all stream controllers
    _exerciseHistoryController.close();
    _hrHistoryListController.close();
    _hrHistoryDataController.close();
    _sleepHistoryController.close();
    _sleepData31Controller.close();
    _stepsHistoryController.close();
    _sleepOnsetController.close();
    _sleepWakeController.close();
    _sleepPhaseChangeController.close();
    _ropeStatusController.close();
    _ropeRealtimeController.close();
    _deviceInfoController.close();
    _firmwareVersionController.close();
    _hardwareVersionController.close();
    _deviceNameController.close();
    _macAddressController.close();
    
    // Close HR streams
    _realtimeHRController.close();
    _hrConfigController.close();
    _hrStatusController.close();
    _realTimeHeartRateController.close();

    // Dispose all processors
    _spo2Processor.dispose();
    _temperatureProcessor.dispose();
    _healthProcessor.dispose();
  }

  // === Historical Data Methods ===

  /// Richiede lo storico degli esercizi usando comando ufficiale 0x16
  Future<void> requestExerciseHistory() async {
    // Check if we should throttle historical data requests
    if (_shouldThrottleHistoricalRequests('exercise')) {
      debugPrint(
          '📊 ⏸️ Exercise history request throttled (too many recent requests)');
      return;
    }

    debugPrint('📊 Requesting exercise history using OFFICIAL command...');
    try {
      // Usa il comando ufficiale 0x16 dal SDK (getHistoryOfSport)
      var officialCommand = OfficialChileafCommands.getHistoryOfSport();

      debugPrint('🔍 Official exercise history command:');
      debugPrint('   Command: 0x16 (getHistoryOfSport from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');

      await _sendCommand(officialCommand);

      // Update throttling counters
      _exerciseHistoryRequests++;
      _lastExerciseHistoryRequest = DateTime.now();
      debugPrint('✅ Official exercise history command sent');
    } catch (e) {
      debugPrint(
          '❌ Failed to request exercise history with official command: $e');
    }
  }

  /// Check if historical data requests should be throttled to prevent infinite loops
  bool _shouldThrottleHistoricalRequests(String type) {
    final now = DateTime.now();

    if (type == 'exercise') {
      // Check request count
      if (_exerciseHistoryRequests >= _maxHistoricalRequests) {
        // Check cooldown period
        if (_lastExerciseHistoryRequest != null) {
          final timeSinceLastRequest =
              now.difference(_lastExerciseHistoryRequest!);
          if (timeSinceLastRequest < _historicalRequestCooldown) {
            return true; // Still in cooldown
          } else {
            // Reset counters after cooldown
            _exerciseHistoryRequests = 0;
            _lastExerciseHistoryRequest = null;
          }
        }
      }
    } else if (type == 'hr') {
      // Check request count
      if (_hrHistoryRequests >= _maxHistoricalRequests) {
        // Check cooldown period
        if (_lastHRHistoryRequest != null) {
          final timeSinceLastRequest = now.difference(_lastHRHistoryRequest!);
          if (timeSinceLastRequest < _historicalRequestCooldown) {
            return true; // Still in cooldown
          } else {
            // Reset counters after cooldown
            _hrHistoryRequests = 0;
            _lastHRHistoryRequest = null;
          }
        }
      }
    }

    return false; // Allow request
  }

  /// Test alternativo del comando 0x22 senza parametro iniziale
  Future<void> requestHRHistoryDataAlt(DateTime timestamp) async {
    int utcTimestamp = timestamp.millisecondsSinceEpoch ~/ 1000;
    debugPrint(
        '💓🔄 ALT: Requesting HR history data (no param) for timestamp: $timestamp ($utcTimestamp)');
    try {
      List<int> command = OfficialChileafCommands.getHistoryOfHRDataAlt(utcTimestamp);
      
      debugPrint('🏗️ Building ALT HR History Data Request (0x22) for timestamp: $utcTimestamp');
      debugPrint('📡 ALT Command: ${OfficialChileafCommands.commandToHexString(command)}');
      
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request ALT HR history data: $e');
    }
  }

  /// Test con parametro 0 invece di 1 per CL837
  Future<void> requestHRHistoryDataCL837(DateTime timestamp) async {
    int utcTimestamp = timestamp.millisecondsSinceEpoch ~/ 1000;
    debugPrint(
        '💓🔄 CL837: Requesting HR history data (param=0) for timestamp: $timestamp ($utcTimestamp)');
    try {
      List<int> command = OfficialChileafCommands.getHistoryOfHRDataCL837(utcTimestamp);
      
      debugPrint('🏗️ Building CL837 HR History Data Request (0x22) for timestamp: $utcTimestamp');
      debugPrint('📡 CL837 Command: ${OfficialChileafCommands.commandToHexString(command)}');
      
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request CL837 HR history data: $e');
    }
  }

  /// Richiede dati HR usando timestamp RAW originale dal dispositivo
  Future<void> requestHRHistoryDataRaw(int rawTimestamp) async {
    debugPrint(
        '💓🔢 RAW: Requesting HR history data using RAW timestamp: $rawTimestamp');
    try {
      // Use the raw timestamp directly in the command
      List<int> command = OfficialChileafCommands.getHistoryOfHRData(rawTimestamp);
      
      debugPrint('🏗️ Building RAW HR History Data Request (0x22) for raw timestamp: $rawTimestamp');
      debugPrint('📡 RAW Command: ${OfficialChileafCommands.commandToHexString(command)}');
      
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request RAW HR history data: $e');
    }
  }

  /// Richiede i dati HR estesi con intervalli RR per HRV per un timestamp specifico
  Future<void> requestHRHistoryDataExtended(DateTime timestamp) async {
    int utcTimestamp = timestamp.millisecondsSinceEpoch ~/ 1000;
    debugPrint(
        '💓🔬 Requesting EXTENDED HR history data with RR intervals for timestamp: $timestamp ($utcTimestamp)');
    try {
      // Usa il comando ufficiale con checksum corretto per dati estesi
      List<int> command = OfficialChileafCommands.getHistoryOfHRDataExtended(utcTimestamp);

      debugPrint('🏗️ Building EXTENDED HR History Data Request (0x23) for timestamp: $utcTimestamp');
      debugPrint('📡 Official Command: ${OfficialChileafCommands.commandToHexString(command)}');
      debugPrint('   Note: This includes RR intervals for HRV calculation');

      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request extended HR history data: $e');
    }
  }

  /// Metodo di test completo per confrontare tutti e 3 i metodi HR history
  /// Testa: 0x21 (lista), 0x22 (dati base), 0x23 (dati estesi con RR)
  Future<void> testAllHRHistoryMethods() async {
    debugPrint('🧪🔬 TEST: Starting comprehensive HR History Methods Comparison');
    debugPrint('=' * 60);

    // Test 1: Richiesta lista HR (0x21)
    debugPrint('🧪 TEST 1/3: Requesting HR History LIST (0x21)');
    try {
      await requestHRHistoryList();
      debugPrint('✅ TEST 1: HR History List request sent successfully');
    } catch (e) {
      debugPrint('❌ TEST 1: Failed to request HR History List: $e');
    }

    // Aspetta un po' prima del prossimo test
    await Future.delayed(const Duration(seconds: 2));

    // Test 2: Richiesta dati HR base per timestamp corrente (0x22)
    debugPrint('🧪 TEST 2/3: Requesting HR History DATA (0x22)');
    try {
      DateTime testTimestamp = DateTime.now().subtract(const Duration(hours: 1));
      await requestHRHistoryData(testTimestamp);
      debugPrint('✅ TEST 2: HR History Data request sent successfully');
    } catch (e) {
      debugPrint('❌ TEST 2: Failed to request HR History Data: $e');
    }

    // Aspetta un po' prima del prossimo test
    await Future.delayed(const Duration(seconds: 2));

    // Test 3: Richiesta dati HR estesi per timestamp corrente (0x23)
    debugPrint('🧪 TEST 3/3: Requesting EXTENDED HR History DATA with RR (0x23)');
    try {
      DateTime testTimestamp = DateTime.now().subtract(const Duration(hours: 1));
      await requestHRHistoryDataExtended(testTimestamp);
      debugPrint('✅ TEST 3: Extended HR History Data request sent successfully');
    } catch (e) {
      debugPrint('❌ TEST 3: Failed to request Extended HR History Data: $e');
    }

    debugPrint('=' * 60);
    debugPrint('🧪🔬 TEST COMPLETE: Monitor the device responses to see which method works best');
    debugPrint('💡 TIP: Check the BLE logs for responses from commands:');
    debugPrint('   - 0x21: Should return list of HR timestamps');
    debugPrint('   - 0x22: Should return detailed HR data for specific timestamp');
    debugPrint('   - 0x23: Should return extended HR data with RR intervals');
  }

  /// Test con timestamp che sono stati ricevuti dal dispositivo
  Future<void> testWithDeviceTimestamps() async {
    debugPrint('🧪🔬 DEVICE TIMESTAMP TEST: Testing with actual device-provided timestamps');
    debugPrint('=' * 80);

    // Prima richiedi la lista per avere timestamp validi dal dispositivo
    debugPrint('🧪 STEP 1: Requesting HR History List to get device timestamps...');
    try {
      await requestHRHistoryList();
      debugPrint('✅ HR History List requested');
    } catch (e) {
      debugPrint('❌ Failed to request HR History List: $e');
      return;
    }

    // Aspetta che arrivi la risposta
    await Future.delayed(const Duration(seconds: 3));

    // Lista di timestamp raw che abbiamo visto nei log e che potrebbero funzionare
    List<int> testRawTimestamps = [
      1747499112,  // Questo è stato parsato con successo come 2025-05-17
      1529395304,  // 2018-06-19 (potrebbe avere dati)
      1462876264,  // 2016-05-10 (potrebbe avere dati)
    ];

    debugPrint('🧪 STEP 2: Testing with known device raw timestamps...');
    for (int i = 0; i < testRawTimestamps.length; i++) {
      try {
        int rawTimestamp = testRawTimestamps[i];
        debugPrint('🧪 Testing raw timestamp ${i + 1}/${testRawTimestamps.length}: $rawTimestamp');

        await requestHRHistoryDataRaw(rawTimestamp);
        debugPrint('✅ Raw timestamp $rawTimestamp sent successfully');

        // Aspetta tra le richieste
        if (i < testRawTimestamps.length - 1) {
          await Future.delayed(const Duration(seconds: 2));
        }
      } catch (e) {
        debugPrint('❌ Failed to test raw timestamp ${testRawTimestamps[i]}: $e');
      }
    }

    debugPrint('=' * 80);
    debugPrint('🧪🔬 DEVICE TIMESTAMP TEST COMPLETE');
    debugPrint('💡 If any of these timestamps work, we know the device has data for those periods');
    debugPrint('💡 Look for 0x22 responses with actual HR data instead of 0x23 end signals');
  }

  /// Test avanzato con metodi alternativi per 0x22
  Future<void> testHRHistoryDataVariants() async {
    debugPrint('🧪🔬 ALT TEST: Testing different 0x22 command variants for CL837');
    debugPrint('=' * 70);

    // Prima richiedi la lista per avere timestamp validi
    debugPrint('🧪 STEP 1: Requesting HR History List first...');
    try {
      await requestHRHistoryList();
      debugPrint('✅ HR History List requested');
    } catch (e) {
      debugPrint('❌ Failed to request HR History List: $e');
      return;
    }

    // Aspetta che arrivi la risposta
    await Future.delayed(const Duration(seconds: 3));

    // Test con timestamp realistico invece di arbitrario
    debugPrint('🧪 STEP 2: Testing with REALISTIC timestamp (yesterday)...');
    try {
      DateTime realisticTimestamp = DateTime.now().subtract(const Duration(days: 1));
      await requestHRHistoryData(realisticTimestamp);
      debugPrint('✅ Realistic timestamp test sent: $realisticTimestamp');
    } catch (e) {
      debugPrint('❌ Realistic timestamp test failed: $e');
    }

    await Future.delayed(const Duration(seconds: 2));

    debugPrint('🧪 STEP 3: Testing with RAW timestamp from successful parsing...');
    try {
      // Use the timestamp that was successfully parsed: 1747499112
      int successfulRawTimestamp = 1747499112; // This was parsed as 2025-05-17
      await requestHRHistoryDataRaw(successfulRawTimestamp);
      debugPrint('✅ Successful raw timestamp test sent: $successfulRawTimestamp');
    } catch (e) {
      debugPrint('❌ Successful raw timestamp test failed: $e');
    }

    await Future.delayed(const Duration(seconds: 2));

    debugPrint('🧪 STEP 4: Testing ALT 0x22 command (no initial param)...');
    try {
      DateTime testTimestamp = DateTime.now().subtract(const Duration(hours: 24));
      await requestHRHistoryDataAlt(testTimestamp);
      debugPrint('✅ ALT 0x22 sent');
    } catch (e) {
      debugPrint('❌ ALT 0x22 failed: $e');
    }

    await Future.delayed(const Duration(seconds: 2));

    debugPrint('🧪 STEP 5: Testing CL837 0x22 command (param=0)...');
    try {
      DateTime testTimestamp = DateTime.now().subtract(const Duration(hours: 24));
      await requestHRHistoryDataCL837(testTimestamp);
      debugPrint('✅ CL837 0x22 sent');
    } catch (e) {
      debugPrint('❌ CL837 0x22 failed: $e');
    }

    debugPrint('=' * 70);
    debugPrint('🧪🔬 ALT TEST COMPLETE: Check which variant gets actual HR data');
    debugPrint('💡 Look for responses with command 0x22 containing actual HR measurements');
    debugPrint('💡 vs responses with command 0x23 (end signal only)');
    debugPrint('💡 The device may not have HR data for the tested timestamps');
  }

  /// Test avanzato con monitoraggio automatico delle risposte
  /// Confronta tutti e 3 i metodi HR history e determina quale funziona meglio
  Future<void> advancedHRHistoryTest() async {
    debugPrint('🔬🧪 ADVANCED HR HISTORY TEST STARTED');
    debugPrint('=' * 70);

    // Variabili per tracciare i risultati
    Map<String, bool> methodResults = {
      '0x21_List': false,
      '0x22_Data': false,
      '0x23_Extended': false,
    };

    Map<String, int> responseCounts = {
      '0x21_List': 0,
      '0x22_Data': 0,
      '0x23_Extended': 0,
    };

    // Timer per timeout del test
    Timer? testTimeout;
    testTimeout = Timer(const Duration(seconds: 30), () {
      debugPrint('⏰ TEST TIMEOUT: 30 seconds elapsed');
      _printTestResults(methodResults, responseCounts);
    });

    // Listener temporaneo per monitorare le risposte BLE
    StreamSubscription? tempSubscription;
    tempSubscription = _txCharacteristic!.lastValueStream.listen((data) {
      if (data.length >= 3) {
        int commandByte = data[2];

        // Monitora risposte per comando 0x21 (33)
        if (commandByte == 33) {
          methodResults['0x21_List'] = true;
          responseCounts['0x21_List'] = responseCounts['0x21_List']! + 1;
          debugPrint('📥 RESPONSE for 0x21: HR History List - Length: ${data.length}');
        }
        // Monitora risposte per comando 0x22 (34)
        else if (commandByte == 34) {
          methodResults['0x22_Data'] = true;
          responseCounts['0x22_Data'] = responseCounts['0x22_Data']! + 1;
          debugPrint('📥 RESPONSE for 0x22: HR History Data - Length: ${data.length}');
        }
        // Monitora risposte per comando 0x23 (35)
        else if (commandByte == 35) {
          methodResults['0x23_Extended'] = true;
          responseCounts['0x23_Extended'] = responseCounts['0x23_Extended']! + 1;
          debugPrint('📥 RESPONSE for 0x23: Extended HR Data with RR - Length: ${data.length}');
        }
      }
    });

    // Test sequenziale con delay
    debugPrint('🔬 Testing Method 1: HR History List (0x21)');
    try {
      await requestHRHistoryList();
      debugPrint('✅ Method 1 sent successfully');
    } catch (e) {
      debugPrint('❌ Method 1 failed: $e');
    }

    await Future.delayed(const Duration(seconds: 3));

    debugPrint('🔬 Testing Method 2: HR History Data (0x22)');
    try {
      DateTime testTimestamp = DateTime.now().subtract(const Duration(hours: 2));
      await requestHRHistoryData(testTimestamp);
      debugPrint('✅ Method 2 sent successfully');
    } catch (e) {
      debugPrint('❌ Method 2 failed: $e');
    }

    await Future.delayed(const Duration(seconds: 3));

    debugPrint('🔬 Testing Method 3: Extended HR Data with RR (0x23)');
    try {
      DateTime testTimestamp = DateTime.now().subtract(const Duration(hours: 2));
      await requestHRHistoryDataExtended(testTimestamp);
      debugPrint('✅ Method 3 sent successfully');
    } catch (e) {
      debugPrint('❌ Method 3 failed: $e');
    }

    // Aspetta ancora un po' per eventuali risposte tardive
    await Future.delayed(const Duration(seconds: 5));

    // Cancella timer e subscription
    testTimeout.cancel();
    tempSubscription.cancel();

    // Stampa risultati finali
    _printTestResults(methodResults, responseCounts);
  }

  /// Metodo helper per stampare i risultati del test
  void _printTestResults(Map<String, bool> methodResults, Map<String, int> responseCounts) {
    debugPrint('=' * 70);
    debugPrint('🔬🧪 ADVANCED HR HISTORY TEST RESULTS');
    debugPrint('=' * 70);

    methodResults.forEach((method, success) {
      int responses = responseCounts[method] ?? 0;
      String status = success ? '✅ SUCCESS' : '❌ NO RESPONSE';
      String responseText = responses > 0 ? '($responses responses)' : '';

      debugPrint('📊 $method: $status $responseText');

      // Spiegazione del metodo
      switch (method) {
        case '0x21_List':
          debugPrint('   └─ Command 0x21: Requests list of HR history timestamps');
          break;
        case '0x22_Data':
          debugPrint('   └─ Command 0x22: Requests detailed HR data for specific timestamp');
          break;
        case '0x23_Extended':
          debugPrint('   └─ Command 0x23: Requests extended HR data with RR intervals for HRV');
          break;
      }
    });

    // Determina il vincitore
    List<String> successfulMethods = methodResults.entries
        .where((entry) => entry.value)
        .map((entry) => entry.key)
        .toList();

    if (successfulMethods.isEmpty) {
      debugPrint('😞 RESULT: No methods received responses from the device');
      debugPrint('💡 SUGGESTIONS:');
      debugPrint('   - Check device connection');
      debugPrint('   - Verify device has HR history data');
      debugPrint('   - Check BLE permissions');
      debugPrint('   - Try different timestamps');
    } else if (successfulMethods.length == 1) {
      debugPrint('🏆 RESULT: ${successfulMethods[0]} is the most reliable method');
    } else {
      debugPrint('🎯 RESULT: Multiple methods work (${successfulMethods.length}/${methodResults.length})');
      debugPrint('💡 RECOMMENDATION: Use the method with most responses for production');
    }

    debugPrint('=' * 70);
  }

  /// Test specifico con il timestamp che ha funzionato nei log precedenti
  Future<void> testWorkingTimestamp() async {
    debugPrint('🎯 TESTING WORKING TIMESTAMP: Using the timestamp that was successfully parsed');
    debugPrint('=' * 70);

    // Questo è il timestamp che è stato parsato con successo nei log: 1747499112
    int workingTimestamp = 1747499112;

    debugPrint('📅 Timestamp: $workingTimestamp (parsed as 2025-05-17 18:25:12.000)');
    debugPrint('🔍 This timestamp was accepted by the device in previous tests');

    try {
      debugPrint('📡 Sending 0x22 command with working timestamp...');
      await requestHRHistoryDataRaw(workingTimestamp);
      debugPrint('✅ Working timestamp command sent successfully');

      debugPrint('⏳ Waiting for device response...');
      await Future.delayed(const Duration(seconds: 3));

      debugPrint('=' * 70);
      debugPrint('🎯 WORKING TIMESTAMP TEST COMPLETE');
      debugPrint('💡 Check logs for 0x22 response with actual HR data');
      debugPrint('💡 If you see 0x23 (end signal), the device has no data for this timestamp');
      debugPrint('💡 If you see 0x22 with data, we have successfully retrieved HR history!');

    } catch (e) {
      debugPrint('❌ Failed to test working timestamp: $e');
    }
  }

  /// Test con più timestamp che hanno funzionato nei log precedenti
  Future<void> testMultipleWorkingTimestamps() async {
    debugPrint('🎯 TESTING MULTIPLE WORKING TIMESTAMPS');
    debugPrint('=' * 70);

    // Timestamp che hanno funzionato nei log precedenti
    List<int> workingTimestamps = [
      1747499112,  // 2025-05-17 18:25:12.000
      1747499113,  // Prossimo secondo
      1747499114,  // Prossimo secondo
      1747499115,  // Prossimo secondo
      1747499116,  // Prossimo secondo
    ];

    debugPrint('📅 Testing ${workingTimestamps.length} working timestamps:');
    for (int i = 0; i < workingTimestamps.length; i++) {
      int ts = workingTimestamps[i];
      DateTime dt = DateTime.fromMillisecondsSinceEpoch(ts * 1000);
      debugPrint('   ${i + 1}. $ts → ${dt.toString()}');
    }

    debugPrint('🔍 These timestamps were accepted by the device in previous tests');

    for (int i = 0; i < workingTimestamps.length; i++) {
      int timestamp = workingTimestamps[i];
      debugPrint('');
      debugPrint('📡 [${i + 1}/${workingTimestamps.length}] Testing timestamp: $timestamp');

      try {
        await requestHRHistoryDataRaw(timestamp);
        debugPrint('✅ Command sent for timestamp $timestamp');

        // Aspetta risposta prima del prossimo
        await Future.delayed(const Duration(seconds: 2));

      } catch (e) {
        debugPrint('❌ Failed for timestamp $timestamp: $e');
      }
    }

    debugPrint('');
    debugPrint('=' * 70);
    debugPrint('🎯 MULTIPLE WORKING TIMESTAMPS TEST COMPLETE');
    debugPrint('💡 Check logs for 0x22 responses with HR data');
    debugPrint('💡 Look for patterns in which timestamps return data vs end signals');
  }

  /// Test con timestamp recenti (oggi e giorni scorsi) per trovare dati effettivi
  Future<void> testRecentTimestamps() async {
    debugPrint('🕒 TESTING RECENT TIMESTAMPS (Last 7 days)');
    debugPrint('=' * 70);

    // Genera timestamp per gli ultimi 7 giorni
    List<int> recentTimestamps = [];
    DateTime now = DateTime.now();

    for (int i = 0; i < 7; i++) {
      DateTime date = now.subtract(Duration(days: i));
      // Usa mezzanotte di ogni giorno
      DateTime midnight = DateTime(date.year, date.month, date.day);
      int timestamp = (midnight.millisecondsSinceEpoch / 1000).round();
      recentTimestamps.add(timestamp);
    }

    debugPrint('📅 Testing ${recentTimestamps.length} recent timestamps:');
    for (int i = 0; i < recentTimestamps.length; i++) {
      int ts = recentTimestamps[i];
      DateTime dt = DateTime.fromMillisecondsSinceEpoch(ts * 1000);
      debugPrint('   ${i + 1}. $ts → ${dt.toString().split(' ')[0]} (Day -$i)');
    }

    debugPrint('🔍 According to protocol: if device has data, should respond with 0x22 + HR data');
    debugPrint('🚫 If no data available, should respond with 0x23 (end signal)');

    for (int i = 0; i < recentTimestamps.length; i++) {
      int timestamp = recentTimestamps[i];
      DateTime date = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);

      debugPrint('');
      debugPrint('📡 [${i + 1}/${recentTimestamps.length}] Testing: ${date.toString().split(' ')[0]}');

      try {
        await requestHRHistoryDataRaw(timestamp);
        debugPrint('✅ Command sent for ${date.toString().split(' ')[0]}');

        // Aspetta risposta
        await Future.delayed(const Duration(seconds: 3));

      } catch (e) {
        debugPrint('❌ Failed for ${date.toString().split(' ')[0]}: $e');
      }
    }

    debugPrint('');
    debugPrint('=' * 70);
    debugPrint('🕒 RECENT TIMESTAMPS TEST COMPLETE');
    debugPrint('💡 Look for 0x22 responses with actual HR data');
    debugPrint('💡 If you see 0x23 for all, device may have no recent HR data');
    debugPrint('💡 Try testing during/after actual exercise sessions');
  }

  /// Test per verificare se il device ha mai avuto dati HR
  Future<void> testDeviceHasAnyHRData() async {
    debugPrint('🔍 TESTING IF DEVICE HAS ANY HR DATA AT ALL');
    debugPrint('=' * 70);

    debugPrint('📋 Step 1: Request HR History List (0x21)');
    debugPrint('🔍 Step 2: Analyze response for data availability');

    try {
      // Richiedi la lista completa dei timestamp disponibili
      debugPrint('📡 Sending 0x21 command to get complete HR history list...');
      await requestHRHistoryList();
      debugPrint('✅ HR History List command sent');

      // Aspetta la risposta completa
      debugPrint('⏳ Waiting for complete HR history list response...');
      await Future.delayed(const Duration(seconds: 5));

      debugPrint('');
      debugPrint('📊 ANALYSIS:');
      debugPrint('   📋 If you see "Total HR sessions: 0" → Device has no HR data');
      debugPrint('   📋 If you see "Total HR sessions: N" → Device has N sessions');
      debugPrint('   🚫 If you see 0xFFFFFFFF → No historical data available');
      debugPrint('   ✅ If you see valid timestamps → Device has data for those periods');

      debugPrint('');
      debugPrint('💡 RECOMMENDATIONS:');
      debugPrint('   - If no data: Try using device during exercise to generate HR data');
      debugPrint('   - If has data: Use those specific timestamps for 0x22 requests');
      debugPrint('   - Check device settings to ensure HR monitoring is enabled');

    } catch (e) {
      debugPrint('❌ Device HR data test failed: $e');
    }

    debugPrint('=' * 70);
    debugPrint('🔍 DEVICE HR DATA AVAILABILITY TEST COMPLETE');
  }

  // ===== ROPE SKIPPING METHODS =====

  /// Sets the rope skipping mode
  Future<void> setRopeMode(RopeMode mode) async {
    debugPrint('🪢⚙️ Setting rope mode to: ${mode.name}');
    try {
      if (_txCharacteristic != null) {
        var command = RopeSkippingProcessor.createSetModeCommand(mode);
        var frame = ChileafProtocol.buildProtocolFrame(command);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Rope mode command sent successfully');
      } else {
        debugPrint('❌ TX characteristic not available for rope mode command');
      }
    } catch (e) {
      debugPrint('❌ Failed to set rope mode: $e');
    }
  }

  /// Clears rope skipping data
  Future<void> clearRopeData() async {
    debugPrint('🪢🧹 Clearing rope skipping data...');
    try {
      if (_txCharacteristic != null) {
        var command = RopeSkippingProcessor.createClearDataCommand();
        var frame = ChileafProtocol.buildProtocolFrame(command);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Rope clear data command sent successfully');
      } else {
        debugPrint('❌ TX characteristic not available for rope clear command');
      }
    } catch (e) {
      debugPrint('❌ Failed to clear rope data: $e');
    }
  }

  /// Clears all historical data from device memory using OFFICIAL reset command
  /// Utilizza il comando 0xF3 dal SDK ufficiale (WearManager.restoration())
  Future<void> clearAllHistoricalData() async {
    debugPrint(
        '🗑️🧹 CLEARING ALL HISTORICAL DATA FROM DEVICE (OFFICIAL COMMAND)...');
    debugPrint('🔧 Using official SDK command 0xF3 (restoration)');

    try {
      // Usa il comando ufficiale 0xF3 dal SDK Android
      var officialCommand = OfficialChileafCommands.deviceReset();

      debugPrint('🔍 Official reset command details:');
      debugPrint(
          '   Command: 0xF3 (Official Restoration/Reset from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   RX Characteristic: ${_rxCharacteristic?.uuid}');
      debugPrint(
          '   Command valid: ${OfficialChileafCommands.isValidCommand(officialCommand)}');

      await _sendCommand(officialCommand);

      debugPrint('✅ Official reset command sent successfully');
      debugPrint('🔄 Device should now have cleared historical data');
      debugPrint('💡 Using same command as official Android app');

      // Wait a moment for the command to process
      await Future.delayed(const Duration(milliseconds: 1000));
    } catch (e) {
      debugPrint('❌ Failed to send official reset command: $e');
      debugPrint('🔍 Error details: ${e.runtimeType}');
      rethrow; // Re-throw to show error in UI
    }
  }

  /// Factory reset - clears all data and settings (if supported)
  Future<void> factoryReset() async {
    debugPrint('🏭🔄 FACTORY RESET - CLEARING ALL DATA AND SETTINGS...');

    try {
      // First clear all historical data
      await clearAllHistoricalData();

      // Add any additional reset commands here if discovered
      debugPrint('✅ Factory reset completed');
      debugPrint('💡 Device should now be in factory state');
    } catch (e) {
      debugPrint('❌ Failed to perform factory reset: $e');
    }
  }

  // ===== DEVICE INFO METHODS =====

  /// Richiede informazioni generali del dispositivo
  Future<void> requestDeviceInfo() async {
    debugPrint('📱 Requesting device info...');
    try {
      if (_txCharacteristic != null) {
        var frame = ChileafProtocol.buildProtocolFrame([0x01]);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Device info request sent');
      } else {
        debugPrint('❌ TX characteristic not available for device info request');
      }
    } catch (e) {
      debugPrint('❌ Failed to request device info: $e');
    }
  }

  /// Richiede versione firmware
  Future<void> requestFirmwareVersion() async {
    debugPrint('💾 Requesting firmware version...');
    try {
      if (_txCharacteristic != null) {
        var frame = ChileafProtocol.buildProtocolFrame([0x03]);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Firmware version request sent');
      } else {
        debugPrint(
            '❌ TX characteristic not available for firmware version request');
      }
    } catch (e) {
      debugPrint('❌ Failed to request firmware version: $e');
    }
  }

  /// Richiede versione hardware
  Future<void> requestHardwareVersion() async {
    debugPrint('🔧 Requesting hardware version...');
    try {
      if (_txCharacteristic != null) {
        var frame = ChileafProtocol.buildProtocolFrame([0x04]);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Hardware version request sent');
      } else {
        debugPrint(
            '❌ TX characteristic not available for hardware version request');
      }
    } catch (e) {
      debugPrint('❌ Failed to request hardware version: $e');
    }
  }

  /// Richiede nome dispositivo
  Future<void> requestDeviceName() async {
    debugPrint('📱 Requesting device name...');
    try {
      if (_txCharacteristic != null) {
        var frame = ChileafProtocol.buildProtocolFrame([0x05]);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Device name request sent');
      } else {
        debugPrint('❌ TX characteristic not available for device name request');
      }
    } catch (e) {
      debugPrint('❌ Failed to request device name: $e');
    }
  }

  /// Richiede MAC address
  Future<void> requestMacAddress() async {
    debugPrint('🔗 Requesting MAC address...');
    try {
      if (_txCharacteristic != null) {
        var frame = ChileafProtocol.buildProtocolFrame([0x06]);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ MAC address request sent');
      } else {
        debugPrint('❌ TX characteristic not available for MAC address request');
      }
    } catch (e) {
      debugPrint('❌ Failed to request MAC address: $e');
    }
  }

  /// Richiede tutte le informazioni del dispositivo
  Future<void> requestAllDeviceInfo() async {
    debugPrint('📱🔋💾 Requesting all device information...');
    try {
      await requestDeviceInfo();
      await Future.delayed(const Duration(milliseconds: 200));

      await requestFirmwareVersion();
      await Future.delayed(const Duration(milliseconds: 200));

      await requestHardwareVersion();
      await Future.delayed(const Duration(milliseconds: 200));

      await requestDeviceName();
      await Future.delayed(const Duration(milliseconds: 200));

      await requestMacAddress();
      await Future.delayed(const Duration(milliseconds: 200));

      debugPrint('✅ All device info requests sent');
    } catch (e) {
      debugPrint('❌ Failed to request all device info: $e');
    }
  }

  /// Richiede tutti i dati storici disponibili (sequenza completa)
  Future<void> requestAllHistoricalData() async {
    debugPrint('📚 Requesting all historical data...');

    // Check if we should throttle requests
    if (_shouldThrottleHistoricalRequests('exercise') &&
        _shouldThrottleHistoricalRequests('hr')) {
      debugPrint(
          '📚 ⏸️ All historical data requests throttled (too many recent requests)');
      return;
    }

    try {
      // 1. Prima richiedi lo storico esercizi (se non throttled)
      if (!_shouldThrottleHistoricalRequests('exercise')) {
        await requestExerciseHistory();
        await Future.delayed(
            const Duration(milliseconds: 1000)); // Longer delay
      }

      // 2. Poi richiedi la lista HR (se non throttled)
      if (!_shouldThrottleHistoricalRequests('hr')) {
        await requestHRHistoryList();
        await Future.delayed(
            const Duration(milliseconds: 1000)); // Longer delay
      }

      // Nota: I dati HR specifici verranno richiesti quando arriva la lista (con filtri)
    } catch (e) {
      debugPrint('❌ Failed to request all historical data: $e');
    }
  }

  // ===== NUOVE FUNZIONI DAL REVERSE ENGINEERING =====

  /// Imposta informazioni utente usando comando ufficiale (0x04)
  /// Equivalente al metodo setUserInfo() del SDK Android
  Future<void> setUserInfo(
      int age, int sex, int weight, int height, int userId) async {
    debugPrint('👤 Setting user info using OFFICIAL command...');
    try {
      var officialCommand =
          OfficialChileafCommands.setUserInfo(age, sex, weight, height, userId);

      debugPrint('🔍 Official user info command:');
      debugPrint('   Command: 0x04 (setUserInfo from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint(
          '   Data: age=$age, sex=$sex, weight=$weight, height=$height, userId=$userId');

      await _sendCommand(officialCommand);
      debugPrint('✅ Official user info command sent');
    } catch (e) {
      debugPrint('❌ Failed to set user info with official command: $e');
    }
  }

  /// Richiede informazioni utente usando comando ufficiale (0x03)
  /// Equivalente al metodo getUserInfo() del SDK Android
  Future<void> requestUserInfo() async {
    debugPrint('👤 Requesting user info using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.getUserInfo();

      debugPrint('🔍 Official get user info command:');
      debugPrint('   Command: 0x03 (getUserInfo from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');

      await _sendCommand(officialCommand);
      debugPrint('✅ Official get user info command sent');
    } catch (e) {
      debugPrint('❌ Failed to request user info with official command: $e');
    }
  }

  /// Imposta timestamp UTC usando comando ufficiale (0x08)
  /// Equivalente al metodo setUTCTime() del SDK Android
  Future<void> syncDeviceTime() async {
    debugPrint('⏰ Syncing device time using OFFICIAL command...');
    try {
      int currentUtc = DateTime.now().millisecondsSinceEpoch ~/ 1000;
      var officialCommand = OfficialChileafCommands.setUTCTime(currentUtc);

      debugPrint('🔍 Official time sync command:');
      debugPrint('   Command: 0x08 (setUTCTime from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint(
          '   UTC Timestamp: $currentUtc (${DateTime.fromMillisecondsSinceEpoch(currentUtc * 1000)})');

      await _sendCommand(officialCommand);
      debugPrint('✅ Official time sync command sent');
    } catch (e) {
      debugPrint('❌ Failed to sync device time with official command: $e');
    }
  }

  /// Metodo pubblico per inviare comandi BLE
  Future<void> sendCommand(List<int> command) async {
    await _sendCommand(command);
  }

  /// Imposta allarme frequenza cardiaca usando comando ufficiale (0x57)
  /// Equivalente al metodo setHeartRateAlarm() del SDK Android
  Future<void> setHeartRateAlarm(bool enabled) async {
    debugPrint('💓🔔 Setting HR alarm using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.setHeartRateAlarm(enabled);

      debugPrint('🔍 Official HR alarm command:');
      debugPrint('   Command: 0x57 (setHeartRateAlarm from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   Enabled: $enabled');

      await _sendCommand(officialCommand);
      debugPrint('✅ Official HR alarm command sent');
    } catch (e) {
      debugPrint('❌ Failed to set HR alarm with official command: $e');
    }
  }

  /// Richiede status allarme HR usando comando ufficiale (0x5B)
  /// Equivalente al metodo getHeartRateAlarm() del SDK Android
  Future<void> requestHeartRateAlarmStatus() async {
    debugPrint('💓🔔 Requesting HR alarm status using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.getHeartRateAlarm();

      debugPrint('🔍 Official HR alarm status command:');
      debugPrint('   Command: 0x5B (getHeartRateAlarm from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');

      await _sendCommand(officialCommand);
      debugPrint('✅ Official HR alarm status command sent');
    } catch (e) {
      debugPrint(
          '❌ Failed to request HR alarm status with official command: $e');
    }
  }

  /// Spegne il dispositivo usando comando ufficiale (0xF1)
  /// Equivalente al metodo shutdown() del SDK Android
  Future<void> shutdownDevice() async {
    debugPrint('🔌 Shutting down device using OPTIMIZED Java-style command...');
    try {
      // Frame corretto basato sull'analisi: [0xFF, 0x04, 0xF1, checksum_java]
      // Questo è identico al comando dell'app decompilata che funziona immediatamente
      List<int> frame = [0xFF, 4, 0xF1];

      // Calcola checksum Java come nell'app decompilata
      int sum = 0;
      for (int byte in frame) {
        sum += byte;
      }
      int javaChecksum = (-sum) & 0xFF;
      javaChecksum ^= 0x3A;
      javaChecksum &= 0xFF;

      frame.add(javaChecksum);

      debugPrint('🔍 Shutdown command (Java-style - IMMEDIATE):');
      debugPrint('   Command: 0xF1 (shutdown from WearManager.java)');
      debugPrint(
          '   Frame: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      debugPrint(
          '   Checksum: 0x${javaChecksum.toRadixString(16).padLeft(2, '0')}');
      debugPrint('   ⚡ Device will power off IMMEDIATELY after this command!');

      await _sendCommand(frame);
      debugPrint(
          '✅ Immediate shutdown command sent - device should power off now');
    } catch (e) {
      debugPrint('❌ Failed to shutdown device: $e');
    }
  }

  // === MANUAL TEST WRAPPER METHODS ===

  /// Reset device usando comando ufficiale 0xF3 (Factory Restoration)
  /// Equivalente al metodo restoration() del SDK Android/iOS
  Future<void> deviceReset() async {
    debugPrint('🔄 Factory Reset device using iOS/Android SDK command (0xF3)...');
    try {
      // Comando basato sull'SDK iOS: ff05f300 (senza checksum)
      // Il checksum viene calcolato come nello shutdown
      List<int> frame = [0xFF, 5, 0xF3, 0x00]; // Length 5 perché include il parametro 0x00

      // Calcola checksum identico allo shutdown (Java/iOS style)
      int sum = 0;
      for (int byte in frame) {
        sum += byte;
      }
      int javaChecksum = (-sum) & 0xFF;
      javaChecksum ^= 0x3A;
      javaChecksum &= 0xFF;

      frame.add(javaChecksum);

      debugPrint('🔍 Factory Reset command (iOS/Android SDK):');
      debugPrint('   Command: 0xF3 (restoration from MainViewController.m:350)');
      debugPrint('   iOS SDK: ff05f300 + checksum');
      debugPrint(
          '   Frame: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      debugPrint(
          '   Checksum: 0x${javaChecksum.toRadixString(16).padLeft(2, '0')}');
      debugPrint('   ⚠️  Device will be restored to factory settings!');

      await _sendCommand(frame);
      debugPrint('✅ Factory reset command sent - device should reset now');
    } catch (e) {
      debugPrint('❌ Failed to reset device: $e');
      rethrow;
    }
  }

  /// Richiesta temperatura usando comando personalizzato
  /// Non presente negli SDK ufficiali - comando sperimentale
  Future<void> requestTemperature() async {
    debugPrint('🌡️ Requesting temperature using experimental command...');
    try {
      List<int> command = [
        0xFF,
        0x04,
        0x38,
        0x00,
        0x3D
      ]; // Temperature request command (sperimentale)

      debugPrint('🔍 Experimental temperature command:');
      debugPrint('   Command: 0x38 (experimental - not in official SDK)');
      debugPrint(
          '   Frame: ${command.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');

      await _sendCommand(command);
      debugPrint('✅ Temperature request sent');
    } catch (e) {
      debugPrint('❌ Failed to request temperature: $e');
      rethrow;
    }
  }

  /// Richiesta HR esteso con RR intervals per HRV
  /// Utilizza comando ufficiale 0x22 (getHistoryOfHRRecord)
  Future<void> requestHRVData() async {
    debugPrint('💓 Requesting HRV data using OFFICIAL HR command...');
    try {
      var officialCommand = OfficialChileafCommands.getHistoryOfHRRecord();

      debugPrint('🔍 Official HR record command for HRV:');
      debugPrint(
          '   Command: 0x22 (getHistoryOfHRRecord from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   Note: RR intervals will be processed for HRV calculation');

      await _sendCommand(officialCommand);
      debugPrint('✅ HRV data request sent');
    } catch (e) {
      debugPrint('❌ Failed to request HRV data: $e');
      rethrow;
    }
  }

  // ===== HEART RATE CONFIGURATION METHODS (SDK Section 5.10-5.13) =====
  
  /// Imposta la configurazione HR con limiti manuali (Manual Mode)
  /// Corrisponde a setHeartAlertSwitch:(BOOL)isOn con isOn = NO
  Future<void> setHeartRateConfigManual({
    required int minHeartRate,
    required int maxHeartRate, 
    required int goalHeartRate,
    required bool alarmEnabled,
  }) async {
    try {
      debugPrint('🫀 Setting HR config (Manual Mode):');
      debugPrint('   Min: $minHeartRate, Max: $maxHeartRate, Goal: $goalHeartRate');
      debugPrint('   Alarm: ${alarmEnabled ? "ON" : "OFF"}');
      
      // Crea la configurazione manuale
      final config = HeartRateConfig.manual(
        minHeartRate: minHeartRate,
        maxHeartRate: maxHeartRate,
        goalHeartRate: goalHeartRate,
        alarmEnabled: alarmEnabled,
      );
      
      // Step 1: Set HR Goal and Range (SDK 5.10)
      final setRangeCommand = OfficialChileafCommands.setHeartRateStatus(
        minHeartRate, maxHeartRate, goalHeartRate);
      await _sendCommand(setRangeCommand);
      debugPrint('✅ HR range set: $minHeartRate-$maxHeartRate, goal: $goalHeartRate');
      
      await Future.delayed(const Duration(milliseconds: 300));
      
      // Step 2: Set Alarm Mode to Manual (SDK 5.13)
      final setAlarmModeCommand = OfficialChileafCommands.setHeartRateAlarmMode(false); // false = manual limits
      await _sendCommand(setAlarmModeCommand);
      debugPrint('✅ HR alarm mode set to: Manual Limits');
      
      await Future.delayed(const Duration(milliseconds: 300));
      
      // Step 3: Enable/Disable Alarm
      final setAlarmCommand = OfficialChileafCommands.setHeartRateAlarm(alarmEnabled);
      await _sendCommand(setAlarmCommand);
      debugPrint('✅ HR alarm ${alarmEnabled ? "enabled" : "disabled"}');
      
      // Update internal state
      _currentHRConfig = config;
      _hrConfigController.add(config);
      
      debugPrint('🫀 Manual HR configuration completed successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to set manual HR config: $e');
      rethrow;
    }
  }
  
  /// Imposta la configurazione HR basata sull'età (Age-Based Mode)
  /// Corrisponde a setHeartAlertSwitch:(BOOL)isOn con isOn = YES
  Future<void> setHeartRateConfigAgeBased({
    required int userAge,
    int? goalHeartRate,
    bool alarmEnabled = true,
    double targetZoneMin = 0.6, // 60% della HR max
    double targetZoneMax = 0.8, // 80% della HR max
  }) async {
    try {
      debugPrint('🫀 Setting HR config (Age-Based Mode):');
      debugPrint('   User Age: $userAge');
      debugPrint('   Target Zone: ${(targetZoneMin*100).round()}%-${(targetZoneMax*100).round()}%');
      debugPrint('   Alarm: ${alarmEnabled ? "ON" : "OFF"}');
      
      // Crea la configurazione basata sull'età
      final config = HeartRateConfig.fromAge(
        userAge,
        goalHeartRate: goalHeartRate,
        alarmEnabled: alarmEnabled,
        targetZoneMin: targetZoneMin,
        targetZoneMax: targetZoneMax,
      );
      
      debugPrint('   Calculated limits: ${config.minHeartRate}-${config.maxHeartRate} bpm');
      debugPrint('   Goal: ${config.goalHeartRate} bpm');
      
      // Step 1: Set HR Goal and Range (SDK 5.10) 
      final setRangeCommand = OfficialChileafCommands.setHeartRateStatus(
        config.minHeartRate, config.maxHeartRate, config.goalHeartRate);
      await _sendCommand(setRangeCommand);
      debugPrint('✅ HR range set based on age calculation');
      
      await Future.delayed(const Duration(milliseconds: 300));
      
      // Step 2: Set Alarm Mode to Age-Based (SDK 5.13)
      final setAlarmModeCommand = OfficialChileafCommands.setHeartRateAlarmMode(true); // true = age calculation
      await _sendCommand(setAlarmModeCommand);
      debugPrint('✅ HR alarm mode set to: Age-Based');
      
      await Future.delayed(const Duration(milliseconds: 300));
      
      // Step 3: Enable/Disable Alarm
      final setAlarmCommand = OfficialChileafCommands.setHeartRateAlarm(alarmEnabled);
      await _sendCommand(setAlarmCommand);
      debugPrint('✅ HR alarm ${alarmEnabled ? "enabled" : "disabled"}');
      
      // Update internal state
      _currentHRConfig = config;
      _hrConfigController.add(config);
      
      debugPrint('🫀 Age-based HR configuration completed successfully');
      
    } catch (e) {
      debugPrint('❌ Failed to set age-based HR config: $e');
      rethrow;
    }
  }
  
  /// Ottiene la configurazione HR corrente (SDK 5.11)
  Future<void> getHeartRateConfiguration() async {
    try {
      debugPrint('🫀 Requesting current HR configuration...');
      
      // Get HR Goal and Range (SDK 5.11)
      final getConfigCommand = OfficialChileafCommands.getHeartRateStatus();
      await _sendCommand(getConfigCommand);
      debugPrint('✅ HR configuration request sent');
      
      await Future.delayed(const Duration(milliseconds: 200));
      
      // Get Alarm Status
      final getAlarmCommand = OfficialChileafCommands.getHeartRateAlarm();
      await _sendCommand(getAlarmCommand);
      debugPrint('✅ HR alarm status request sent');
      
    } catch (e) {
      debugPrint('❌ Failed to get HR configuration: $e');
      rethrow;
    }
  }
  
  /// Abilita/disabilita solo l'allarme HR mantenendo la configurazione esistente
  Future<void> setHeartRateAlarmEnabled(bool enabled) async {
    try {
      debugPrint('🫀 ${enabled ? "Enabling" : "Disabling"} HR alarm...');
      
      final setAlarmCommand = OfficialChileafCommands.setHeartRateAlarm(enabled);
      await _sendCommand(setAlarmCommand);
      debugPrint('✅ HR alarm ${enabled ? "enabled" : "disabled"}');
      
      // Update internal state if we have a current config
      if (_currentHRConfig != null) {
        _currentHRConfig = _currentHRConfig!.copyWith(alarmEnabled: enabled);
        _hrConfigController.add(_currentHRConfig!);
      }
      
    } catch (e) {
      debugPrint('❌ Failed to set HR alarm: $e');
      rethrow;
    }
  }
  
  /// Avvia il monitoraggio HR real-time
  /// Utilizza il servizio BLE Heart Rate standard (più affidabile)
  Future<void> startHeartRateMonitoring() async {
    try {
      debugPrint('💓 Starting Heart Rate monitoring...');
      
      if (_heartRateCharacteristic != null) {
        debugPrint('💓 Using BLE Heart Rate Service for monitoring');
        debugPrint('💓 HR monitoring active - receiving data via standard BLE service');
      } else {
        debugPrint('💓 BLE Heart Rate Service not available');
        debugPrint('💓 Custom HR commands not supported by this device');
      }
      
      // Prova a richiedere la configurazione (anche se potrebbe non funzionare)
      debugPrint('💓 Attempting to read current HR configuration...');
      await getHeartRateConfiguration();
      
      debugPrint('💓 HR monitoring initialization completed');
      
    } catch (e) {
      debugPrint('❌ Failed to start HR monitoring: $e');
      rethrow;
    }
  }
  
  /// Callback per gestire real-time HR (SDK 4.8)
  void _handleRealtimeHeartRate(int heartRate) {
    _lastRealtimeHR = heartRate;
    _realtimeHRController.add(heartRate);
    
    // Calcola e aggiorna lo stato dell'allarme se abbiamo una configurazione
    if (_currentHRConfig != null) {
      final status = HeartRateStatus(
        currentHeartRate: heartRate,
        config: _currentHRConfig!,
      );
      
      _lastHRStatus = status;
      _hrStatusController.add(status);
      
      // Trigger callbacks se configurati
      _onRealtimeHRReceived?.call(heartRate);
      _onHRStatusChanged?.call(status);
      
      // Log allarmi
      if (status.shouldTriggerAlarm) {
        debugPrint('🚨 HR ALARM: ${status.alarmState.displayName} - $heartRate bpm');
      }
    }
  }

  /// Send raw command bytes directly to the device
  /// Espone il metodo _sendCommand per permettere comandi raw personalizzati
  Future<void> sendRawCommand(List<int> commandBytes) async {
    try {
      await _sendCommand(commandBytes);
    } catch (e) {
      debugPrint('❌ Failed to send raw command: $e');
      rethrow;
    }
  }

  // === OPTIMIZED HISTORICAL DATA METHODS ===

  /// Recupera tutti i dati HR storici (lista + dettagli)
  /// Utilizza i comandi 0x21, 0x22, 0x23 con checksum Java ottimizzato
  Future<void> requestCompleteHRHistory() async {
    debugPrint(
        '🔄💓 Requesting COMPLETE HR History with optimized checksum...');
    await _historicalDataService.requestCompleteHRHistory();
  }

  /// Recupera tutti i dati RR/HRV storici (per analisi Elite HRV)
  /// Utilizza i comandi 0x24, 0x25 con checksum Java ottimizzato
  Future<void> requestCompleteRRHistory() async {
    debugPrint(
        '🔄📊 Requesting COMPLETE RR/HRV History with optimized checksum...');
    await _historicalDataService.requestCompleteRRHistory();
  }

  /// Recupera dati di esercizio storici ottimizzati
  /// Utilizza comando 0x16 con checksum Java ottimizzato
  Future<void> requestOptimizedExerciseHistory() async {
    debugPrint('🔄🏃 Requesting Exercise History with optimized checksum...');
    await _historicalDataService.requestExerciseHistoryEnhanced();
  }

  /// Recupera dati di sonno storici ottimizzati
  /// Utilizza comando 0x05 con checksum Java ottimizzato (LEGACY)
  /// ⚠️ DEPRECATO: Usa requestSleepData31() per il protocollo ufficiale
  Future<void> requestOptimizedSleepHistory({bool force = false}) async {
    debugPrint('🔄😴 Requesting Sleep History with optimized checksum (LEGACY 0x05)...');
    
    // Clear accumulator before starting new download
    _legacySleepAccumulator.clear();
    debugPrint('🧹 Cleared legacy sleep accumulator');
    
    await _historicalDataService.requestSleepHistoryEnhanced(force: force);
  }

  /// Recupera dati di sonno con comando 0x31 (PROTOCOLLO UFFICIALE)
  /// ✅ Questo comando dovrebbe recuperare TUTTO lo storico (non solo oggi)
  /// Documentazione SDK sezione 2.14
  /// Risposta: 0x31 (dati) o 0x32 (fine/no data)
  /// Granularità: 1 byte = 5 minuti di activity index
  Future<void> requestSleepData31({bool force = false}) async {
    debugPrint('🔄🌙💤 Requesting Sleep Data with OFFICIAL command 0x31...');
    debugPrint('📖 Protocol: SDK 2.14 - Sleep Data Request');
    debugPrint('📊 Granularity: 1 byte = 5 minutes');
    debugPrint('🔍 Response: 0x31 (data) or 0x32 (end signal)');
    debugPrint('📅 Expected: ALL historical sleep data (multiple days)');
    
    // ✅ Reset tutti i buffer prima di iniziare nuovo download
    _sleepData31Buffer.clear();
    _sleepData31CompletedSessions.clear();
    _isSleepData31Active = false;
    debugPrint('🧹 Buffers cleared - starting fresh download');
    
    // Usa il nuovo metodo del service
    await _historicalDataService.requestSleepHistory0x31(force: force);
  }

  /// Recupera passi intervallari ottimizzati
  /// Utilizza comando 0x40 con checksum Java ottimizzato
  Future<void> requestOptimizedIntervalSteps() async {
    debugPrint('🔄🚶 Requesting Interval Steps with optimized checksum...');
    await _historicalDataService.requestIntervalStepsEnhanced();
  }

  /// Recupera TUTTI i dati storici in un workflow ottimizzato
  /// Sequenza coordinata di tutti i comandi con timing ottimale
  Future<void> requestAllOptimizedHistoricalData() async {
    debugPrint('🚀📊 Starting COMPLETE Optimized Historical Data Workflow...');
    debugPrint('🔧 Using Java checksum algorithm for maximum reliability');
    await _historicalDataService.requestAllHistoricalDataEnhanced();
  }

  /// Recupera TUTTI i dati storici con parser reverse-engineered dall'app originale
  /// Massima compatibilità e accuratezza nel parsing dei dati
  Future<void> requestAllEnhancedHistoricalData() async {
    debugPrint('🔬📊 Starting ENHANCED Historical Data Workflow...');
    debugPrint(
        '🧬 Using reverse-engineered parsers from original app for maximum accuracy');
    await _historicalDataService.requestAllHistoricalDataEnhanced();
  }

  /// Recupera HR dettagliato per timestamp specifico
  Future<void> requestHRDetailForTimestamp(int timestamp) async {
    debugPrint('💓🔍 Requesting HR Detail for timestamp: $timestamp');
    await _historicalDataService.requestHRDetailData(timestamp);
  }

  /// Recupera HR esteso (con RR intervals) per timestamp specifico
  Future<void> requestHRExtendedForTimestamp(int timestamp) async {
    debugPrint(
        '💓🔬 Requesting HR Extended (RR intervals) for timestamp: $timestamp');
    await _historicalDataService.requestHRExtendedData(timestamp);
  }

  /// Recupera RR detail per timestamp specifico
  Future<void> requestRRDetailForTimestamp(int timestamp) async {
    debugPrint('📊🔍 Requesting RR Detail for timestamp: $timestamp');
    await _historicalDataService.requestRRDetailData(timestamp);
  }

  /// Formatta timestamp in formato leggibile
  String formatTimestamp(int timestamp) {
    return HistoricalDataService.formatTimestamp(timestamp);
  }

  /// Verifica se un timestamp è valido
  bool isValidTimestamp(int timestamp) {
    return HistoricalDataService.isValidTimestamp(timestamp);
  }

  /// Pulisce la cache delle richieste dati storici
  void clearHistoricalDataCache() {
    _historicalDataService.clearRequestCache();
  }

  // === NEW DATA EXTRACTION METHODS (Based on SDK Documentation) ===

  /// Request Sleep Data - Based on SDK command getSleepData
  /// Returns sleep patterns with action indices
  Future<void> requestSleepData() async {
    debugPrint('🛌 Requesting sleep data...');
    try {
      // Command 0x05 with subcommand 0x03 for sleep data request
      // Based on WearReceivedDataCallback analysis: intValue == 5 and getIntParse(value, 3, 1) == 3
      List<int> command = [0xFF, 0x05, 0x05, 0x03, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[4] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ Sleep data request sent');
    } catch (e) {
      debugPrint('❌ Failed to request sleep data: $e');
    }
  }

  /// Request Step Interval Data - Commands 0x90 (list) and 0x91 (data)
  /// Based on SDK documentation section 5.22 and 5.23
  Future<void> requestStepIntervalHistory() async {
    debugPrint('🚶 Requesting step interval history...');
    debugPrint('⏱️ Waiting for device response (timeout: 10 seconds)...');
    
    try {
      // Command 0x90: Step counting history data list request
      List<int> command = [0xFF, 0x05, 0x90, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[3] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ Step interval command sent - monitoring for response with command 0x90 or 0x91...');
      
      // Add timeout warning
      Future.delayed(const Duration(seconds: 10), () {
        debugPrint('⚠️ No step interval response after 10 seconds');
        debugPrint('💡 The device may not have step interval data stored');
        debugPrint('💡 Or this command (0x90) may not be supported by CL837');
      });
      
    } catch (e) {
      debugPrint('❌ Failed to request step interval history: $e');
    }
  }

  /// Request Step Interval Data for specific UTC
  /// Command 0x91 with UTC timestamp
  Future<void> requestStepIntervalData(int utcTimestamp) async {
    debugPrint('🚶 Requesting step interval data for UTC: $utcTimestamp');
    try {
      // Command 0x91: Request step counting historical interval data
      // Format: ff 09 91 01 [4-byte UTC timestamp] [checksum]
      List<int> utcBytes = [
        utcTimestamp & 0xFF,
        (utcTimestamp >> 8) & 0xFF,
        (utcTimestamp >> 16) & 0xFF,
        (utcTimestamp >> 24) & 0xFF,
      ];
      
      List<int> command = [0xFF, 0x09, 0x91, 0x01, ...utcBytes, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[command.length - 1] = checksum;
      
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request step interval data: $e');
    }
  }

  /// Request Real-time Temperature Data
  /// Based on SDK requestRealTimeTemperature method
  Future<void> requestTemperatureData() async {
    debugPrint('🌡️ Requesting real-time temperature data...');
    try {
      // Command for real-time temperature request
      // Based on WearReceivedDataCallback: onTemperatureReceived with command parsing
      List<int> command = [0xFF, 0x05, 0x63, 0x01, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[4] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ Temperature data request sent');
    } catch (e) {
      debugPrint('❌ Failed to request temperature data: $e');
    }
  }

  /// Request 3D Data Frequency
  /// Based on SDK get3DFrequency method
  Future<void> request3DFrequency() async {
    debugPrint('📊 Requesting 3D data frequency...');
    try {
      // Command to get 3D frequency (based on WearReceivedDataCallback onSensor3DFrequencyReceived)
      List<int> command = [0xFF, 0x05, 0x0D, 0x01, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[4] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ 3D frequency request sent');
    } catch (e) {
      debugPrint('❌ Failed to request 3D frequency: $e');
    }
  }

  /// Set 3D Data Frequency
  /// Based on SDK set3DFrequency method
  Future<void> set3DFrequency(int frequency) async {
    debugPrint('📊 Setting 3D data frequency to: $frequency');
    try {
      // Command to set 3D frequency (based on SDK set3DFrequency)
      List<int> command = [0xFF, 0x06, 0x0E, frequency & 0xFF, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[4] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ 3D frequency set to: $frequency');
    } catch (e) {
      debugPrint('❌ Failed to set 3D frequency: $e');
    }
  }

  /// Request Device Maximum Heart Rate
  /// Based on SDK getMaxHeartRate method
  Future<void> requestMaxHeartRate() async {
    debugPrint('💓 Requesting maximum heart rate...');
    try {
      // Command to get maximum heart rate (based on SDK getMaxHeartRate)
      List<int> command = [0xFF, 0x05, 0x5A, 0x01, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[4] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ Max heart rate request sent');
    } catch (e) {
      debugPrint('❌ Failed to request max heart rate: $e');
    }
  }

  /// Set Device Maximum Heart Rate
  /// Based on SDK setMaxHeartRate method
  Future<void> setMaxHeartRate(int maxHR) async {
    debugPrint('💓 Setting maximum heart rate to: $maxHR');
    try {
      // Command to set maximum heart rate (based on SDK setMaxHeartRate)
      List<int> command = [0xFF, 0x06, 0x59, maxHR & 0xFF, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[4] = checksum;
      
      await _sendCommand(command);
      debugPrint('✅ Max heart rate set to: $maxHR');
    } catch (e) {
      debugPrint('❌ Failed to set max heart rate: $e');
    }
  }

  /// Request Device Info and Battery Status
  /// Based on existing device info command (0x01)
  Future<void> requestCompleteDeviceInfo() async {
    debugPrint('📱 Requesting complete device information...');
    try {
      // Device info command (already implemented)
      List<int> command = [0xFF, 0x05, 0x01, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[3] = checksum;
      
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request device info: $e');
    }
  }

  // === UTILITY METHODS ===

  /// Test completo per determinare quale metodo HR history funziona meglio
  /// Confronta: 0x21 (lista), 0x22 (dati base), 0x23 (dati estesi con RR)
  Future<void> testHRHistoryMethods() async {
    debugPrint('🧪🔬 Starting HR History Methods Test...');
    debugPrint('This will test all 3 HR history methods and show which one works best');
    await advancedHRHistoryTest();
  }

  /// Test rapido di tutti e 3 i metodi HR history (senza monitoraggio avanzato)
  Future<void> quickHRHistoryTest() async {
    debugPrint('⚡🔬 QUICK HR HISTORY TEST');
    debugPrint('=' * 50);

    // Metodo 1: Lista HR (0x21)
    debugPrint('⚡ 1/3: Testing HR List (0x21)');
    try {
      await requestHRHistoryList();
      debugPrint('✅ HR List sent');
    } catch (e) {
      debugPrint('❌ HR List failed: $e');
    }

    await Future.delayed(const Duration(seconds: 1));

    // Metodo 2: Dati HR base (0x22)
    debugPrint('⚡ 2/3: Testing HR Data (0x22)');
    try {
      DateTime ts = DateTime.now().subtract(const Duration(hours: 1));
      await requestHRHistoryData(ts);
      debugPrint('✅ HR Data sent');
    } catch (e) {
      debugPrint('❌ HR Data failed: $e');
    }

    await Future.delayed(const Duration(seconds: 1));

    // Metodo 3: Dati HR estesi (0x23)
    debugPrint('⚡ 3/3: Testing Extended HR Data (0x23)');
    try {
      DateTime ts = DateTime.now().subtract(const Duration(hours: 1));
      await requestHRHistoryDataExtended(ts);
      debugPrint('✅ Extended HR Data sent');
    } catch (e) {
      debugPrint('❌ Extended HR Data failed: $e');
    }

    debugPrint('=' * 50);
    debugPrint('⚡ QUICK TEST COMPLETE - Check BLE responses');
  }

  /// Calcola checksum Java secondo il protocollo ufficiale Chileaf
  /// Algoritmo: (-sum) ^ 0x3A & 0xFF
  /// Calculate checksum using EXACT Java algorithm from SDK
  /// Based on: public byte calcChecksum(byte[] dat)
  /// Richiede la lista degli storici della frequenza cardiaca usando comando ufficiale 0x21
  /// Questo è il PRIMO passo: ottenere i timestamp disponibili dal dispositivo
  Future<void> requestHRHistoryList() async {
    debugPrint('💓 Requesting HR History LIST (0x21) - FIRST STEP');
    debugPrint('🔍 This gets available timestamps from device');

    try {
      // Usa il comando ufficiale dal SDK Android
      var command = OfficialChileafCommands.getHistoryOfHRRecord();
      debugPrint('📡 Official HR List Command: ${OfficialChileafCommands.commandToHexString(command)}');

      await _sendCommand(command);
      debugPrint('✅ HR History List command sent successfully');
      debugPrint('⏳ Waiting for device response with available timestamps...');
    } catch (e) {
      debugPrint('❌ Failed to request HR history list: $e');
    }
  }

  /// Richiede i dati HR per un timestamp specifico usando comando ufficiale 0x22
  /// Questo è il SECONDO passo: ottenere i dati per un timestamp specifico
  Future<void> requestHRHistoryData(DateTime timestamp) async {
    int utcTimestamp = timestamp.millisecondsSinceEpoch ~/ 1000;
    debugPrint('💓 Requesting HR History DATA (0x22) for timestamp: $timestamp');
    debugPrint('🔍 UTC: $utcTimestamp');

    try {
      // Usa il comando ufficiale con parametro 1 + timestamp
      var command = OfficialChileafCommands.getHistoryOfHRData(utcTimestamp);
      debugPrint('📡 Official HR Data Command: ${OfficialChileafCommands.commandToHexString(command)}');

      await _sendCommand(command);
      debugPrint('✅ HR History Data command sent successfully');
    } catch (e) {
      debugPrint('❌ Failed to request HR history data: $e');
    }
  }

  /// Test completo del flusso HR History: lista + dati
  /// Questa è la sequenza CORRETTA secondo il protocollo ufficiale
  Future<void> testCompleteHRHistoryFlow() async {
    debugPrint('🔄 TESTING COMPLETE HR HISTORY FLOW (Official Protocol)');
    debugPrint('=' * 70);
    debugPrint('📋 Step 1: Request HR History List (0x21)');
    debugPrint('📊 Step 2: Request data for available timestamps (0x22)');
    debugPrint('=' * 70);

    try {
      // PASSO 1: Richiedi la lista dei timestamp disponibili
      debugPrint('📡 STEP 1: Sending 0x21 command to get available timestamps...');
      await requestHRHistoryList();
      debugPrint('✅ HR History List requested');

      // Aspetta la risposta (i timestamp disponibili)
      debugPrint('⏳ Waiting for timestamp list response...');
      await Future.delayed(const Duration(seconds: 3));

      // PASSO 2: Per ora testiamo con timestamp realistici
      // In produzione, questi verrebbero dalla risposta del comando 0x21
      List<int> testTimestamps = [
        DateTime.now().subtract(const Duration(hours: 1)).millisecondsSinceEpoch ~/ 1000,
        DateTime.now().subtract(const Duration(hours: 2)).millisecondsSinceEpoch ~/ 1000,
      ];

      debugPrint('📅 STEP 2: Testing with realistic timestamps:');
      for (int ts in testTimestamps) {
        DateTime dt = DateTime.fromMillisecondsSinceEpoch(ts * 1000);
        debugPrint('   - $ts → ${dt.toString()}');
      }

      // Richiedi dati per ogni timestamp disponibile
      for (int i = 0; i < testTimestamps.length; i++) {
        int timestamp = testTimestamps[i];
        DateTime dateTime = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);

        debugPrint('');
        debugPrint('� [${i + 1}/${testTimestamps.length}] Requesting HR data for: ${dateTime.toString()}');

        try {
          await requestHRHistoryData(dateTime);
          debugPrint('✅ Data request sent for ${dateTime.toString()}');

          // Aspetta risposta prima del prossimo
          await Future.delayed(const Duration(seconds: 2));

        } catch (e) {
          debugPrint('❌ Failed for ${dateTime.toString()}: $e');
        }
      }

      debugPrint('');
      debugPrint('=' * 70);
      debugPrint('🔄 COMPLETE HR HISTORY FLOW TEST COMPLETE');
      debugPrint('💡 Expected responses:');
      debugPrint('   📋 0x21: List of available timestamps');
      debugPrint('   💓 0x22: Actual HR data for each timestamp');
      debugPrint('   🚫 0x23: End signal (no data available)');

    } catch (e) {
      debugPrint('❌ Complete HR History flow test failed: $e');
    }
  }

  /// Test per verificare se il dispositivo ha dati HR storici
  Future<void> testDeviceHasHRData() async {
    debugPrint('🔍 TESTING IF DEVICE HAS HR HISTORICAL DATA');
    debugPrint('=' * 70);

    debugPrint('📋 Step 1: Request HR History List (0x21)');
    debugPrint('🔍 Step 2: Analyze response');

    try {
      debugPrint('📡 Sending 0x21 command to check for HR data...');
      await requestHRHistoryList();
      debugPrint('✅ HR History List command sent');

      // Aspetta la risposta
      debugPrint('⏳ Waiting for response...');
      await Future.delayed(const Duration(seconds: 3));

      debugPrint('');
      debugPrint('📊 ANALYSIS EXPECTED:');
      debugPrint('   ✅ If you see 0x21 response with timestamps → Device has HR data');
      debugPrint('   🚫 If you see 0x23 (end signal) → No HR historical data');
      debugPrint('   📋 If you see "Total HR sessions: 0" → Device has no data');

    } catch (e) {
      debugPrint('❌ Device HR data test failed: $e');
    }

    debugPrint('=' * 70);
    debugPrint('🔍 DEVICE HR DATA AVAILABILITY TEST COMPLETE');
  }

  /// Calcola checksum Java secondo il protocollo ufficiale Chileaf
  /// Algoritmo: (-sum) ^ 0x3A & 0xFF
  int _calculateJavaChecksum(List<int> frame) {
    // Exact Java implementation from SDK:
    // int len = dat.length-1;  // Exclude last byte (checksum itself)
    // for (i = 0; i < len; i++) { res += dat[i]; }
    // temp = (int) res; temp &= 0xFF; temp = (0 - temp); temp &= 0xFF;
    // temp ^= 0x3a; res = (byte) (temp & 0xff);

    int sum = 0;
    // Sum all bytes EXCEPT the last one (which is the checksum position)
    for (int i = 0; i < frame.length - 1; i++) {
      sum += frame[i];
    }

    // Exact Java algorithm steps:
    int temp = sum;
    temp &= 0xFF;           // Mask to 8-bit
    temp = (0 - temp);      // Negate
    temp &= 0xFF;           // Mask again
    temp ^= 0x3A;           // XOR with 0x3A
    int checksum = temp & 0xFF;  // Final mask

    debugPrint('🔢 Checksum calculation:');
    debugPrint('   Input frame: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0').toUpperCase()}').join(' ')}');
    debugPrint('   Sum (excluding last byte): $sum (0x${sum.toRadixString(16).toUpperCase()})');
    debugPrint('   After & 0xFF: ${temp & 0xFF} (0x${(temp & 0xFF).toRadixString(16).toUpperCase()})');
    debugPrint('   After negation: ${0 - (temp & 0xFF)}');
    debugPrint('   After ^ 0x3A: $checksum (0x${checksum.toRadixString(16).toUpperCase()})');

    return checksum;
  }

  // ===== NUOVI METODI PER TESTARE LE 3 MODALITÀ DALLA DOCUMENTAZIONE =====

  /// Test Modalità 1: Request single data (implementazione attuale)
  Future<void> testHRRequestMode1() async {
    debugPrint('🧪 MODE 1: Request single data (param 1) - Current implementation');
    try {
      // Usa un timestamp recente
      DateTime recentTimestamp = DateTime.now().subtract(const Duration(hours: 2));
      int utcTimestamp = recentTimestamp.millisecondsSinceEpoch ~/ 1000;
      
      List<int> command = OfficialChileafCommands.getHistoryOfHRData(utcTimestamp);
      debugPrint('📡 Mode 1 Command: ${OfficialChileafCommands.commandToHexString(command)}');
      debugPrint('🔍 Requesting data for specific timestamp: $recentTimestamp');
      
      await _sendCommand(command);
      debugPrint('✅ Mode 1 command sent successfully');
    } catch (e) {
      debugPrint('❌ Mode 1 test failed: $e');
    }
  }

  /// Test Modalità 2: Request all data (NUOVO dalla documentazione!)
  Future<void> testHRRequestMode2() async {
    debugPrint('🧪 MODE 2: Request all data (param 2) - NEW FROM DOCUMENTATION!');
    try {
      List<int> command = OfficialChileafCommands.getHistoryOfHRDataMode2();
      debugPrint('📡 Mode 2 Command: ${OfficialChileafCommands.commandToHexString(command)}');
      debugPrint('🔍 Requesting ALL HR data from device (no specific timestamp)');
      
      await _sendCommand(command);
      debugPrint('✅ Mode 2 command sent successfully');
    } catch (e) {
      debugPrint('❌ Mode 2 test failed: $e');
    }
  }

  /// Test Modalità 3: Request all data after UTC (NUOVO dalla documentazione!)
  Future<void> testHRRequestMode3() async {
    debugPrint('🧪 MODE 3: Request all data after UTC (param 3) - NEW FROM DOCUMENTATION!');
    try {
      // Usa timestamp di 24 ore fa per ottenere dati recenti
      DateTime yesterday = DateTime.now().subtract(const Duration(hours: 24));
      int utcTimestamp = yesterday.millisecondsSinceEpoch ~/ 1000;
      
      List<int> command = OfficialChileafCommands.getHistoryOfHRDataMode3(utcTimestamp);
      debugPrint('📡 Mode 3 Command: ${OfficialChileafCommands.commandToHexString(command)}');
      debugPrint('🔍 Requesting all HR data AFTER timestamp: $yesterday');
      
      await _sendCommand(command);
      debugPrint('✅ Mode 3 command sent successfully');
    } catch (e) {
      debugPrint('❌ Mode 3 test failed: $e');
    }
  }

  /// Test PROTOCOLLO UFFICIALE SEQUENZIALE: Richiede ogni timestamp individualmente 
  Future<void> testSequentialHRRequests() async {
    debugPrint('🎯 SEQUENTIAL HR REQUESTS: Official Protocol Implementation');
    debugPrint('📖 Following Chileaf BLE Protocol v0.6 documentation EXACTLY:');
    debugPrint('   "APP needs to request the heart rate list(0x21) first and get all the UTC time of records,');
    debugPrint('   and then use the UTC time to request data"');
    
    if (_lastRawTimestamps.isEmpty) {
      debugPrint('❌ No HR timestamps available! Must call requestHRHistoryList() first');
      return;
    }
    
    debugPrint('📋 Found ${_lastRawTimestamps.length} HR sessions to request');
    debugPrint('🔄 Requesting each timestamp individually using Mode 1 (0x22)...');
    
    int successCount = 0;
    int failCount = 0;
    
    for (int i = 0; i < _lastRawTimestamps.length; i++) {
      int timestamp = _lastRawTimestamps[i];
      DateTime parsedTime = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);
      
      debugPrint('💓 [$i/${_lastRawTimestamps.length}] Testing timestamp: $timestamp');
      debugPrint('   📅 Parsed time: ${parsedTime.toString()}');
      
      try {
        // Usa il comando Mode 1 ufficiale con timestamp specifico
        List<int> command = OfficialChileafCommands.getHistoryOfHRData(timestamp);
        debugPrint('   📡 Command: ${command.map((e) => '0x${e.toRadixString(16).padLeft(2, '0')}').join(' ')}');
        
        await _sendCommand(command);
        debugPrint('   ✅ Command sent successfully');
        
        // Aspetta risposta dal dispositivo
        await Future.delayed(const Duration(milliseconds: 1500));
        successCount++;
        
      } catch (e) {
        debugPrint('   ❌ Failed to request timestamp $timestamp: $e');
        failCount++;
      }
      
      // Piccola pausa tra richieste per non sovraccaricare il dispositivo
      if (i < _lastRawTimestamps.length - 1) {
        await Future.delayed(const Duration(milliseconds: 500));
      }
    }
    
    debugPrint('📊 SEQUENTIAL REQUEST RESULTS:');
    debugPrint('   ✅ Successful requests: $successCount');
    debugPrint('   ❌ Failed requests: $failCount');
    debugPrint('   📋 Total timestamps: ${_lastRawTimestamps.length}');
    
    if (successCount > 0) {
      debugPrint('🎉 SUCCESS! Some timestamps returned data!');
    } else {
      debugPrint('😞 All requests returned 0x23 (end signal) - no actual HR data');
    }
  }

  /// Advanced timestamp analysis using multiple decoding methods
  /// Following Chileaf BLE Protocol v0.6 specifications
  Future<Map<String, dynamic>> analyzeTimestampsAdvanced() async {
    debugPrint('🔬 ADVANCED TIMESTAMP ANALYSIS STARTING...');
    
    if (_lastRawTimestamps.isEmpty) {
      debugPrint('❌ No raw timestamps available. Call getHRHistoryList() first.');
      return {'error': 'No timestamps available'};
    }
    
    debugPrint('📊 Raw timestamps to analyze: ${_lastRawTimestamps.length}');
    
    // Convert each timestamp to 4-byte arrays for analysis
    List<List<int>> timestampBytes = [];
    for (var timestamp in _lastRawTimestamps) {
      // Convert int to 4-byte little-endian array (as per Chileaf protocol)
      List<int> bytes = [
        timestamp & 0xFF,
        (timestamp >> 8) & 0xFF,
        (timestamp >> 16) & 0xFF,
        (timestamp >> 24) & 0xFF,
      ];
      timestampBytes.add(bytes);
    }
    
    // Perform advanced analysis
    Map<String, dynamic> analysis = TimestampDecoder.analyzeTimestampPattern(timestampBytes);
    
    debugPrint('📈 ANALYSIS RESULTS:');
    debugPrint('   📊 Total timestamps: ${analysis['totalTimestamps']}');
    debugPrint('   🏆 Best method: ${analysis['bestMethod']}');
    debugPrint('   ✅ Success count: ${analysis['bestMethodCount']}');
    debugPrint('   📅 Chronological order: ${analysis['isChronological']}');
    
    // Print method success rates
    if (analysis['methodSuccessRates'] != null) {
      debugPrint('📊 METHOD SUCCESS RATES:');
      Map<String, int> rates = Map<String, int>.from(analysis['methodSuccessRates']);
      rates.forEach((method, count) {
        double percentage = (count / analysis['totalTimestamps']) * 100;
        debugPrint('   $method: $count/${analysis['totalTimestamps']} (${percentage.toStringAsFixed(1)}%)');
      });
    }
    
    // Print raw pattern analysis
    if (analysis['rawPattern'] != null) {
      Map<String, dynamic> pattern = Map<String, dynamic>.from(analysis['rawPattern']);
      debugPrint('🔍 RAW PATTERN ANALYSIS:');
      debugPrint('   🎯 Has constant prefix: ${pattern['hasConstantPrefix']}');
      debugPrint('   📌 Most common prefix: ${pattern['mostCommonPrefix']}');
      
      if (pattern['prefixDistribution'] != null) {
        debugPrint('   📊 Prefix distribution:');
        Map<String, int> prefixes = Map<String, int>.from(pattern['prefixDistribution']);
        prefixes.forEach((prefix, count) {
          debugPrint('      $prefix: $count times');
        });
      }
    }
    
    // Analyze individual timestamps with detailed breakdown
    debugPrint('🔬 INDIVIDUAL TIMESTAMP ANALYSIS:');
    for (int i = 0; i < _lastRawTimestamps.length && i < 5; i++) { // Show first 5
      int timestamp = _lastRawTimestamps[i];
      List<int> bytes = timestampBytes[i];
      
      TimestampResult result = TimestampDecoder.decodeTimestamp(bytes);
      
      debugPrint('   [$i] Raw: $timestamp (0x${timestamp.toRadixString(16).padLeft(8, '0')})');
      debugPrint('       Bytes: [${bytes.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}]');
      
      if (result.bestGuess != null) {
        DateTime? bestDate = result.interpretations[result.bestGuess];
        debugPrint('       🏆 Best guess: ${result.bestGuess} → ${bestDate?.toString() ?? 'null'}');
        debugPrint('       📝 Reasoning: ${result.reasoning}');
      }
      
      // Show all valid interpretations
      int validCount = 0;
      result.interpretations.forEach((method, date) {
        if (date != null) {
          validCount++;
          debugPrint('       ✅ $method: ${date.toString()}');
        }
      });
      
      if (validCount == 0) {
        debugPrint('       ❌ No valid interpretations found');
      }
      
      debugPrint(''); // Empty line for readability
    }
    
    if (_lastRawTimestamps.length > 5) {
      debugPrint('   ... and ${_lastRawTimestamps.length - 5} more timestamps');
    }
    
    return analysis;
  }

  // ===== SPORT HEALTH DATA (VO2 Max, HRV, Stress, Stamina) =====

  /// Get body health data (VO2 Max, HRV, Stress, Stamina)
  /// 
  /// ⚠️ IMPORTANT: Health metrics (VO2 Max, Breath Rate, Emotion, Stress, Stamina)
  /// are calculated ONLY during active sports/exercise sessions.
  /// They are NOT historical data that can be downloaded on demand.
  /// 
  /// These metrics are sent automatically via command 0x13 when the device
  /// is in sport mode and actively measuring during physical activity.
  /// 
  /// This method may trigger a request, but data will only be available
  /// if the user is currently exercising or has just finished a workout.
  /// 
  /// ALTERNATIVE COMMANDS TO TRY:
  /// - 0x4D: Request health data (may not be supported)
  /// - 0x13: Response command (automatically sent by device)
  /// - Some devices send health data periodically without explicit request
  Future<void> getBodyHealth() async {
    debugPrint('🏃 Requesting body health data...');
    debugPrint('⚠️ Note: Data only available during active sports session');
    
    // Try multiple command variations based on SDK analysis
    // Command 1: Try 0x4D (may be request command)
    try {
      await _sendCommand([0xFF, 0x04, 0x4D, 0x00]);
      debugPrint('✅ Sent request 0x4D for health data');
    } catch (e) {
      debugPrint('❌ Error sending 0x4D: $e');
    }
    
    // Wait a bit and try alternative command
    await Future.delayed(const Duration(milliseconds: 500));
    
    // Command 2: Try 0x4E (alternative)
    try {
      await _sendCommand([0xFF, 0x04, 0x4E, 0x00]);
      debugPrint('✅ Sent alternative request 0x4E for health data');
    } catch (e) {
      debugPrint('❌ Error sending 0x4E: $e');
    }
  }

  /// Start real-time health monitoring
  /// 
  /// ⚠️ IMPORTANT: This may not work as expected. Health metrics are typically
  /// sent automatically by the device during sports activities (command 0x13).
  /// 
  /// The device calculates VO2 Max, Breath Rate, Emotion, Stress, and Stamina
  /// in real-time during exercise. Simply start a workout on the device and
  /// listen to the sportHealthStream for automatic updates.
  Future<void> startHealthMonitoring() async {
    debugPrint('▶️ Starting real-time health monitoring...');
    debugPrint('⚠️ Note: Start a workout on the device to receive metrics');
    // Command: 0x4F with value 1 (start) - may not be supported
    await _sendCommand([0xFF, 0x05, 0x4F, 0x01, 0x00]);
  }

  /// Stop real-time health monitoring
  /// Equivalent to Android: WearManager.stopHealthMonitoring()
  Future<void> stopHealthMonitoring() async {
    debugPrint('⏹️ Stopping real-time health monitoring...');
    // Command: 0x4F (79 decimal) with value 0 (stop)
    await _sendCommand([0xFF, 0x05, 0x4F, 0x00, 0x00]);
  }

  // ===== HEART RATE MANAGEMENT (Min/Max/Goal) =====

  /// Get heart rate status (min, max, goal thresholds)
  /// Equivalent to Android: WearManager.getHeartRateStatus()
  Future<void> getHeartRateStatus() async {
    debugPrint('📊 Getting heart rate status (min/max/goal)...');
    // Command: 0x43 (67 decimal) - based on SDK
    await _sendCommand([0xFF, 0x04, 0x43, 0x00]);
  }

  /// Set heart rate status (min, max, goal thresholds)
  /// Equivalent to Android: WearManager.setHeartRateStatus(min, max, goal)
  Future<void> setHeartRateStatus(int min, int max, int goal) async {
    debugPrint('⚙️ Setting heart rate status: min=$min, max=$max, goal=$goal');
    // Command: 0x43 with 3 parameters (min, max, goal)
    await _sendCommand([0xFF, 0x07, 0x43, min, max, goal, 0x00]);
  }

  /// Get heart rate alarm status
  /// Equivalent to Android: WearManager.getHeartRateAlarm()
  Future<void> getHeartRateAlarm() async {
    debugPrint('⏰ Getting heart rate alarm status...');
    // Command: 0x44 (68 decimal)
    await _sendCommand([0xFF, 0x04, 0x44, 0x00]);
  }



  /// Get maximum heart rate by age
  /// Equivalent to Android: WearManager.getHeartRateMax()
  Future<void> getHeartRateMax() async {
    debugPrint('📈 Getting maximum heart rate...');
    // Command: 0x45 (69 decimal)
    await _sendCommand([0xFF, 0x04, 0x45, 0x00]);
  }

  /// Set maximum heart rate
  /// Equivalent to Android: WearManager.setHeartRateMax(max)
  Future<void> setHeartRateMax(int max) async {
    debugPrint('⚙️ Setting maximum heart rate: $max BPM');
    // Command: 0x45 with max value
    await _sendCommand([0xFF, 0x05, 0x45, max, 0x00]);
  }

  /// Auto-configure heart rate thresholds based on user age
  /// Calculates optimal min/max/goal values using user's profile
  /// 
  /// This is a convenience method that:
  /// 1. Calculates recommended values from UserInfo
  /// 2. Sets HR min/max/goal (command 0x43)
  /// 3. Sets max HR by age (command 0x45)
  /// 
  /// Example:
  /// ```dart
  /// UserInfo user = UserInfo(age: 35, gender: 1, weight: 75, height: 175, userId: 12345);
  /// await service.autoConfigureHeartRate(user);
  /// // Sets: min=93 BPM, max=167 BPM, goal=139 BPM (based on age 35)
  /// ```
  Future<void> autoConfigureHeartRate(UserInfo userInfo) async {
    debugPrint('🎯 Auto-configuring heart rate based on user profile:');
    debugPrint('   Age: ${userInfo.age} years');
    debugPrint('   Max HR: ${userInfo.maxHeartRate} BPM (220 - age)');
    
    final settings = userInfo.recommendedHeartRateSettings;
    final min = settings['min']!;
    final max = settings['max']!;
    final goal = settings['goal']!;
    
    debugPrint('   Recommended Min: $min BPM (${((min / userInfo.maxHeartRate) * 100).round()}% max)');
    debugPrint('   Recommended Goal: $goal BPM (${((goal / userInfo.maxHeartRate) * 100).round()}% max)');
    debugPrint('   Recommended Max: $max BPM (${((max / userInfo.maxHeartRate) * 100).round()}% max)');
    
    // Set HR min/max/goal thresholds
    await setHeartRateStatus(min, max, goal);
    
    // Set max HR by age
    await setHeartRateMax(userInfo.maxHeartRate);
    
    debugPrint('✅ Heart rate auto-configuration complete!');
  }

  // ===== 3D ACCELEROMETER CONTROL =====

  /// Get 3D sensor frequency
  /// Equivalent to Android: WearManager.get3DFrequency()
  Future<void> get3DFrequency() async {
    debugPrint('📡 Getting 3D sensor frequency...');
    // Command: 0x46 (70 decimal)
    await _sendCommand([0xFF, 0x04, 0x46, 0x00]);
  }



  /// Get 3D sensor status (enabled/disabled)
  /// Equivalent to Android: WearManager.get3DStatus()
  Future<void> get3DStatus() async {
    debugPrint('📡 Getting 3D sensor status...');
    // Command: 0x47 (71 decimal)
    await _sendCommand([0xFF, 0x04, 0x47, 0x00]);
  }

  /// Set 3D sensor enabled/disabled
  /// Equivalent to Android: WearManager.set3DEnabled(enabled)
  Future<void> set3DEnabled(bool enabled) async {
    debugPrint('⚙️ Setting 3D sensor: ${enabled ? "enabled" : "disabled"}');
    // Command: 0x47 with boolean value
    await _sendCommand([0xFF, 0x05, 0x47, enabled ? 0x01 : 0x00, 0x00]);
  }

  // ===== 6D SENSOR (GYROSCOPE + ACCELEROMETER) =====

  /// Get 6D sensor frequency
  /// Equivalent to Android: WearManager.get6DFrequency()
  Future<void> get6DFrequency() async {
    debugPrint('📡 Getting 6D sensor frequency...');
    // Command: 0x48 (72 decimal)
    await _sendCommand([0xFF, 0x04, 0x48, 0x00]);
  }

  /// Set 6D sensor frequency (0-3: 26/52/104/208 Hz)
  /// Equivalent to Android: WearManager.set6DFrequency(frequency)
  Future<void> set6DFrequency(Sensor6DFrequency frequency) async {
    debugPrint('⚙️ Setting 6D sensor frequency: ${frequency.label}');
    // Command: 0x48 with frequency value (0-3)
    await _sendCommand([0xFF, 0x05, 0x48, frequency.value, 0x00]);
  }

  // ===== RR INTERVALS (ADVANCED HRV) =====

  /// Get RR intervals history for advanced HRV analysis
  /// Equivalent to Android: WearManager.getRRIntervals()
  Future<void> getRRIntervalsHistory() async {
    debugPrint('💓 Getting RR intervals history for HRV analysis...');
    // Command: 0x49 (73 decimal) - hypothetical, needs verification
    await _sendCommand([0xFF, 0x04, 0x49, 0x00]);
  }

  // ===== DEVICE MANAGEMENT =====



  /// Factory restoration
  /// Equivalent to Android: WearManager.restoration()
  Future<void> factoryRestoration() async {
    debugPrint('⚠️ Performing factory restoration...');
    // Command: 0x4B (75 decimal)
    await _sendCommand([0xFF, 0x04, 0x4B, 0x00]);
  }

  /// Get single button press history
  /// Equivalent to Android: WearManager.getSingleButtonHistory()
  Future<void> getSingleButtonHistory() async {
    debugPrint('🔘 Getting single button press history...');
    // Command: 0x4C (76 decimal)
    await _sendCommand([0xFF, 0x04, 0x4C, 0x00]);
  }

  // ===== RESPONSE PROCESSORS FOR NEW FEATURES =====

  /// Process Sport Health Data (VO2 Max, HRV, Stress, Stamina)
  /// Format: [0xFF, length, 0x4E, vo2Max, breathRate, emotion, stress, stamina, tp(4), lf(4), hf(4), checksum]
  void _processSportHealthData(List<int> data) {
    try {
      if (data.length < 8) {
        debugPrint('❌ Invalid sport health data length: ${data.length}');
        return;
      }

      // Extract basic metrics (1 byte each)
      int vo2Max = data[3];
      int breathRate = data[4];
      int emotion = data[5];
      int stress = data[6];
      int stamina = data.length > 7 ? data[7] : 0;

      // Extract HRV frequency domain (if available, 4 bytes each as floats)
      double? tp, lf, hf;
      if (data.length >= 20) {
        // Convert 4-byte sequences to floats (little-endian)
        tp = _bytesToFloat(data.sublist(8, 12));
        lf = _bytesToFloat(data.sublist(12, 16));
        hf = _bytesToFloat(data.sublist(16, 20));
      }

      SportHealthData healthData = SportHealthData(
        vo2Max: vo2Max,
        breathRate: breathRate,
        emotionLevel: emotion,
        stressPercent: stress,
        stamina: stamina,
        totalPower: tp,
        lowFrequency: lf,
        highFrequency: hf,
      );

      debugPrint('🏃 Sport Health Data: $healthData');
      _sportHealthController.add(healthData);
    } catch (e) {
      debugPrint('❌ Error processing sport health data: $e');
    }
  }

  /// Process Heart Rate Configuration Response
  /// Format: [0xFF, 0x07, 0x43, min, max, goal, checksum]
  void _processHRConfigResponse(List<int> data) {
    try {
      if (data.length < 6) {
        debugPrint('❌ Invalid HR config data length: ${data.length}');
        return;
      }

      int min = data[3];
      int max = data[4];
      int goal = data[5];

      debugPrint('❤️ HR Config: min=$min BPM, max=$max BPM, goal=$goal BPM');
      
      // Note: Using sport_health_data's HeartRateConfig would conflict
      // This is just for the stream notification
      // The actual config is in heart_rate_config.dart
    } catch (e) {
      debugPrint('❌ Error processing HR config: $e');
    }
  }

  /// Process Heart Rate Alarm Response
  /// Format: [0xFF, 0x05, 0x44, enabled, checksum]
  void _processHRAlarmResponse(List<int> data) {
    try {
      if (data.length < 4) {
        debugPrint('❌ Invalid HR alarm data length: ${data.length}');
        return;
      }

      bool enabled = data[3] == 1;
      HeartRateAlarm alarm = HeartRateAlarm(
        enabled: enabled,
        timestamp: DateTime.now(),
      );

      debugPrint('⏰ HR Alarm: ${alarm.toString()}');
      _hrAlarmController.add(alarm);
    } catch (e) {
      debugPrint('❌ Error processing HR alarm: $e');
    }
  }

  /// Process Heart Rate Max Response
  /// Format: [0xFF, 0x05, 0x45, max, checksum]
  void _processHRMaxResponse(List<int> data) {
    try {
      if (data.length < 4) {
        debugPrint('❌ Invalid HR max data length: ${data.length}');
        return;
      }

      int max = data[3];
      HeartRateMax hrMax = HeartRateMax(max: max);

      debugPrint('📈 HR Max: ${hrMax.toString()}');
      _hrMaxController.add(hrMax);
    } catch (e) {
      debugPrint('❌ Error processing HR max: $e');
    }
  }

  /// Process 3D Sensor Frequency Response
  /// Format: [0xFF, 0x05, 0x46, frequency, checksum]
  void _process3DFrequencyResponse(List<int> data) {
    try {
      if (data.length < 4) {
        debugPrint('❌ Invalid 3D frequency data length: ${data.length}');
        return;
      }

      int frequencyValue = data[3];
      Sensor3DFrequency frequency = Sensor3DFrequency.fromValue(frequencyValue);

      debugPrint('📡 3D Sensor Frequency: ${frequency.label}');
      _sensor3DFrequencyController.add(frequency);
    } catch (e) {
      debugPrint('❌ Error processing 3D frequency: $e');
    }
  }

  /// Process 6D Sensor Frequency Response
  /// Format: [0xFF, 0x05, 0x48, frequency, checksum]
  void _process6DFrequencyResponse(List<int> data) {
    try {
      if (data.length < 4) {
        debugPrint('❌ Invalid 6D frequency data length: ${data.length}');
        return;
      }

      int frequencyValue = data[3];
      Sensor6DFrequency frequency = Sensor6DFrequency.fromValue(frequencyValue);

      debugPrint('📡 6D Sensor Frequency: ${frequency.label}');
      _sensor6DFrequencyController.add(frequency);
    } catch (e) {
      debugPrint('❌ Error processing 6D frequency: $e');
    }
  }

  /// Process RR Intervals Response
  /// Format: [0xFF, length, 0x49, count, intervals(2 bytes each)..., checksum]
  void _processRRIntervalsResponse(List<int> data) {
    try {
      if (data.length < 5) {
        debugPrint('❌ Invalid RR intervals data length: ${data.length}');
        return;
      }

      int count = data[3];
      List<RRIntervalData> intervals = [];

      // Each RR interval is 2 bytes (little-endian)
      for (int i = 0; i < count && (4 + i * 2 + 1) < data.length; i++) {
        int offset = 4 + i * 2;
        int interval = data[offset] | (data[offset + 1] << 8);
        
        intervals.add(RRIntervalData(
          interval: interval,
          timestamp: DateTime.now(),
        ));
      }

      debugPrint('💓 RR Intervals: ${intervals.length} intervals received');
      _rrIntervalController.add(intervals);
    } catch (e) {
      debugPrint('❌ Error processing RR intervals: $e');
    }
  }

  /// Process Button Press Response
  /// Format: [0xFF, length, 0x4C, count, utc_timestamps(4 bytes each)..., checksum]
  void _processButtonPressResponse(List<int> data) {
    try {
      if (data.length < 5) {
        debugPrint('❌ Invalid button press data length: ${data.length}');
        return;
      }

      int count = data[3];

      // Each timestamp is 4 bytes (little-endian)
      for (int i = 0; i < count && (4 + i * 4 + 3) < data.length; i++) {
        int offset = 4 + i * 4;
        int utc = data[offset] | 
                  (data[offset + 1] << 8) | 
                  (data[offset + 2] << 16) | 
                  (data[offset + 3] << 24);
        
        SingleButtonPress press = SingleButtonPress(
          utc: utc,
          timestamp: DateTime.fromMillisecondsSinceEpoch(utc * 1000),
        );
        
        debugPrint('🔘 Button Press: ${press.toString()}');
        _buttonPressController.add(press);
      }
    } catch (e) {
      debugPrint('❌ Error processing button press: $e');
    }
  }

  /// Process 6D Raw Data Stream (Gyroscope + Accelerometer)
  /// Format: [0xFF, length, 0x6D, utc(4), seq, gyroX(2), gyroY(2), gyroZ(2), accelX(2), accelY(2), accelZ(2), checksum]
  void _process6DRawDataStream(List<int> data) {
    try {
      if (data.length < 20) {
        debugPrint('❌ Invalid 6D data length: ${data.length}');
        return;
      }

      // Extract UTC timestamp (4 bytes, little-endian)
      int utc = data[3] | (data[4] << 8) | (data[5] << 16) | (data[6] << 24);
      
      // Sequence number
      int seq = data[7];
      
      // Gyroscope (3x 2 bytes, signed)
      int gyroX = _bytesToInt16(data[8], data[9]);
      int gyroY = _bytesToInt16(data[10], data[11]);
      int gyroZ = _bytesToInt16(data[12], data[13]);
      
      // Accelerometer (3x 2 bytes, signed)
      int accelX = _bytesToInt16(data[14], data[15]);
      int accelY = _bytesToInt16(data[16], data[17]);
      int accelZ = _bytesToInt16(data[18], data[19]);

      Sensor6DRawData sensorData = Sensor6DRawData(
        utc: utc == 0xFF ? null : utc,
        sequence: seq,
        gyroscopeX: gyroX,
        gyroscopeY: gyroY,
        gyroscopeZ: gyroZ,
        accelerometerX: accelX,
        accelerometerY: accelY,
        accelerometerZ: accelZ,
      );

      _sensor6DDataController.add(sensorData);
    } catch (e) {
      debugPrint('❌ Error processing 6D data: $e');
    }
  }

  // ===== UTILITY FUNCTIONS =====

  /// Convert 4 bytes to float (little-endian)
  double _bytesToFloat(List<int> bytes) {
    if (bytes.length != 4) return 0.0;
    
    // Convert bytes to 32-bit integer
    int bits = bytes[0] | (bytes[1] << 8) | (bytes[2] << 16) | (bytes[3] << 24);
    
    // Convert to float using IEEE 754 format
    // This is a simplified version - for production use a proper library
    if (bits == 0) return 0.0;
    
    int sign = (bits >> 31) == 0 ? 1 : -1;
    int exponent = ((bits >> 23) & 0xFF) - 127;
    int mantissa = bits & 0x7FFFFF;
    
    double value = sign * (1 + mantissa / 8388608.0) * (1 << exponent).toDouble();
    return value;
  }

  /// Convert 2 bytes to signed 16-bit integer (little-endian)
  int _bytesToInt16(int lowByte, int highByte) {
    int value = lowByte | (highByte << 8);
    // Convert to signed
    if (value > 32767) {
      value = value - 65536;
    }
    return value;
  }

  /// Process User Info Response (Command 0x03)
  /// Format: [0xFF, length, 0x03, ecg_open, charging_info, battery, age, gender, weight, height, userId(5 bytes), checksum]
  void _processUserInfoResponse(List<int> data) {
    try {
      debugPrint('👤 PROCESSING USER INFO RESPONSE');
      debugPrint('🔍 Raw data (${data.length} bytes): ${_commandToHexString(data)}');
      
      if (data.length < 15) {
        debugPrint('❌ Invalid user info data length: ${data.length} (expected >= 15)');
        return;
      }

      // Parse complete device status (includes user info)
      DeviceStatus? deviceStatus = DeviceStatus.fromDeviceResponse(data);
      
      if (deviceStatus != null) {
        debugPrint('✅ User Info parsed successfully:');
        debugPrint('   👤 User: ${deviceStatus.userInfo.toString()}');
        debugPrint('   🔋 Battery: ${deviceStatus.batteryLevel}% (${deviceStatus.chargingStatusString})');
        debugPrint('   💓 ECG: ${deviceStatus.ecgOpen ? "ON" : "OFF"}');
        
        // Emit to streams
        _userInfoController.add(deviceStatus.userInfo);
        _deviceStatusController.add(deviceStatus);
        
        // Log health insights
        debugPrint('📊 Health Insights:');
        debugPrint('   BMI: ${deviceStatus.userInfo.bmi.toStringAsFixed(1)} (${deviceStatus.userInfo.bmiCategory})');
        debugPrint('   Max HR: ${deviceStatus.userInfo.maxHeartRate} BPM (by age)');
        
        Map<String, double> idealWeight = deviceStatus.userInfo.idealWeightRange;
        debugPrint('   Ideal Weight: ${idealWeight['ideal']!.toStringAsFixed(1)} kg (±5 kg range)');
      } else {
        debugPrint('❌ Failed to parse user info from response');
      }
    } catch (e) {
      debugPrint('❌ Error processing user info response: $e');
    }
  }
}
