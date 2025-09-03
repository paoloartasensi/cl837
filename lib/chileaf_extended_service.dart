import 'dart:async';
import 'services/data_processors/historical_data_processor.dart';
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

// Data Processors
import 'services/data_processors/spo2_processor.dart';
import 'services/data_processors/temperature_processor.dart';
import 'services/data_processors/health_processor.dart';
import 'services/historical_data_service.dart';
import 'services/data_processors/rope_processor.dart';
import 'services/data_processors/device_info_processor.dart';

// Protocol & Commands
import 'services/ble_protocol/chileaf_protocol.dart';
import 'services/ble_protocol/official_commands_complete.dart';

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

  // Historical data service with optimized checksum
  late final HistoricalDataService _historicalDataService;

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

  // Debug logging control - VERY AGGRESSIVE THROTTLING
  final bool _enableVerboseLogging = false; // Set to true for detailed logs
  final int _logThrottleInterval = 500; // Log every 500 packets (was 50)
  final int _healthDataThrottleInterval =
      200; // Log health data every 200 occurrences (was 100)
  final int _sportsThrottleInterval =
      500; // Log every 500th sports data (was 50) - MUCH LESS NOISE

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

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('🚀 Starting ROBUST Chileaf Extended Service for GROK HR...');
      
      // Track connection
      _isConnected = true;

      // Longer delay to ensure full service discovery
      debugPrint('⏳ Waiting for complete service discovery...');
      await Future.delayed(const Duration(milliseconds: 3000));

      final services = await device.discoverServices();
      debugPrint('📡 Extended Service: Found ${services.length} services');

      // Find custom service - more robust matching
      BluetoothService? customService;
      BluetoothService? heartRateService;

      for (var service in services) {
        debugPrint('🔍 Service UUID: ${service.uuid}');
        if (service.uuid.toString().toLowerCase() ==
            _customServiceUuid.toLowerCase()) {
          customService = service;
          debugPrint('✅ Found CUSTOM service: ${service.uuid}');
        }
        // Look for Heart Rate Service
        if (service.uuid.toString().toLowerCase() ==
            _heartRateServiceUuid.toLowerCase()) {
          heartRateService = service;
          debugPrint('💓 Found Heart Rate Service: ${service.uuid}');
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
      
      debugPrint('✅ TX Characteristic verified: ${_txCharacteristic!.uuid}');
      debugPrint('✅ RX Characteristic verified: ${_rxCharacteristic!.uuid}');
      
      // Setup Heart Rate Service if available
      if (heartRateService != null) {
        await _setupHeartRateService(heartRateService);
      } else {
        debugPrint('💓 Heart Rate Service not found - using custom protocol only');
      }

      // Diagnostics rimossi - utilizziamo solo il comando ufficiale 0x37

      // Enable notifications and start data flow
      await _startDataFlow();

      debugPrint('Chileaf Extended Service started successfully');
    } catch (e) {
      debugPrint('Failed to start Chileaf Extended Service: $e');
      // Don't rethrow - let the app continue without extended features
    }
  }

  Future<void> _setupCharacteristics(BluetoothService customService) async {
    // Debug: List all characteristics
    debugPrint('Service has ${customService.characteristics.length} characteristics:');
    for (var char in customService.characteristics) {
      debugPrint('  - ${char.uuid} (properties: notify=${char.properties.notify}, read=${char.properties.read}, write=${char.properties.write})');
    }

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
    debugPrint('📋 Service has ${customService.characteristics.length} characteristics:');
    for (var char in customService.characteristics) {
      final props = char.properties;
      debugPrint('  - ${char.uuid}');
      debugPrint('    Properties: notify=${props.notify}, read=${props.read}, write=${props.write}, writeWithoutResponse=${props.writeWithoutResponse}');
    }

    // Find characteristics with multiple attempts
    BluetoothCharacteristic? txChar, rxChar;
    int attempts = 0;
    const maxAttempts = 3;

    while ((txChar == null || rxChar == null) && attempts < maxAttempts) {
      attempts++;
      debugPrint('🔄 Characteristic discovery attempt $attempts/$maxAttempts');
      
      for (var char in customService.characteristics) {
        final charUuid = char.uuid.toString().toLowerCase();
        if (charUuid == _txCharUuid.toLowerCase()) {
          txChar = char;
          debugPrint('✅ Found TX characteristic: ${char.uuid}');
        } else if (charUuid == _rxCharUuid.toLowerCase()) {
          rxChar = char;
          debugPrint('✅ Found RX characteristic: ${char.uuid}');
        }
      }
      
      if (txChar == null || rxChar == null) {
        debugPrint('⚠️ Missing characteristics, waiting before retry...');
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

        // Only log command processing for NON-high frequency commands
        if (_enableVerboseLogging || (!isHighFrequency && command != ChileafProtocol.commandTemperature)) {
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
      case 0x03: // Firmware Version
        debugPrint('💾 FIRMWARE VERSION: Processing firmware version');
        var firmwareVersion = DeviceInfoProcessor.processFirmwareVersion(data);
        if (firmwareVersion != null) {
          _firmwareVersionController.add(firmwareVersion);
        }
        break;
      case 0x04: // Hardware Version
        debugPrint('🔧 HARDWARE VERSION: Processing hardware version');
        var hardwareVersion = DeviceInfoProcessor.processHardwareVersion(data);
        if (hardwareVersion != null) {
          _hardwareVersionController.add(hardwareVersion);
        }
        break;
      case 0x05: // Device Name
        debugPrint('📱 DEVICE NAME: Processing device name');
        var deviceName = DeviceInfoProcessor.processDeviceName(data);
        if (deviceName != null) {
          _deviceNameController.add(deviceName);
        }
        break;
      case 0x06: // MAC Address
        debugPrint('🔗 MAC ADDRESS: Processing MAC address');
        var macAddress = DeviceInfoProcessor.processMacAddress(data);
        if (macAddress != null) {
          _macAddressController.add(macAddress);
        }
        break;
      case ChileafProtocol.commandSports:
        // SPORTS DATA IGNORED - Focus on medical-grade sensors only
        _sportsLogCount++;
        if (_sportsLogCount % _sportsThrottleInterval == 0) {
          debugPrint(
              '🚫 Sports data ignored ($_sportsLogCount packets, steps/calories unreliable)');
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
      case 0x21: // HR History List
        debugPrint('💓 HR HISTORY LIST: Processing HR timestamp list');
        var hrHistoryList = HistoricalDataProcessor.processHRHistoryList(
            Uint8List.fromList(data));
        
        // Ignore "end of data" messages to preserve existing valid data
        if (!hrHistoryList.isEndOfData) {
          _hrHistoryListController.add(hrHistoryList);
          // Auto-request detailed data for each timestamp
          _requestDetailedHRData(hrHistoryList);
        } else {
          debugPrint('💓 🛑 Ignoring END-OF-DATA message - preserving existing HR history list');
        }
        break;
      case 0x22: // HR History Data
        debugPrint(
            '💓 HR HISTORY DATA: Processing detailed HR historical data');
        var hrHistoryData = HistoricalDataProcessor.processHRHistoryData(
            Uint8List.fromList(data));
        _hrHistoryDataController.add(hrHistoryData);
        break;
      case 0x23: // HR History End Signal
        debugPrint(
            '🏁 HR HISTORY END: Received end signal for HR history data');
        // Signal that HR history transfer is complete
        break;
      case 0x40: // Rope Status
        debugPrint('🪢 ROPE STATUS: Processing rope skipping status data');
        var ropeStatus = RopeSkippingProcessor.processRopeStatus(data);
        if (ropeStatus != null) {
          _ropeStatusController.add(ropeStatus);
        }
        break;
      case 0x41: // Rope Realtime
        debugPrint('🪢⚡ ROPE REALTIME: Processing realtime rope notifications');
        var ropeRealtime = RopeSkippingProcessor.processRopeRealtime(data);
        if (ropeRealtime != null) {
          _ropeRealtimeController.add(ropeRealtime);
        }
        break;
      default:
        debugPrint(
            'Unhandled Chileaf command: 0x${command.toRadixString(16)} (${data.length} bytes)');
        
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
        
        // SPECIAL HANDLER for command 0x47 (HR configuration response - 23 bytes)
        if (command == 0x47 && data.length == 23) {
          debugPrint('🔍 COMMAND 0x47 RAW BYTES (HR CONFIG):');
          String hexString = data.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ');
          debugPrint('🔍 Full 23 bytes: $hexString');
          
          // Try to decode HR configuration
          debugPrint('🔍 DECODING HR CONFIG:');
          debugPrint('🔍 Byte 0-2: Header ${data[0].toRadixString(16)} ${data[1].toRadixString(16)} ${data[2].toRadixString(16)}');
          
          if (data.length >= 10) {
            debugPrint('🔍 Byte 3-6: ${data[3]} ${data[4]} ${data[5]} ${data[6]} (potential HR values)');
            debugPrint('🔍 Byte 7-10: ${data[7]} ${data[8]} ${data[9]} ${data[10]} (potential thresholds)');
          }
          
          // Look for HR threshold patterns (typical values 40-200)
          for (int i = 3; i < data.length; i++) {
            int value = data[i];
            if (value >= 40 && value <= 200) {
              debugPrint('🔍 Potential HR value at byte $i: $value BPM');
            }
          }
          
          debugPrint('🔍 Raw decimal values: ${data.sublist(3).join(', ')}');
        }
    }
  }

  // Process RR intervals from heart rate data for HRV calculation
  void processRRIntervalsForHRV(HeartRateData heartRateData) {
    _healthProcessor.processRRIntervalsForHRV(heartRateData);
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
        await device.connect();
        await Future.delayed(const Duration(milliseconds: 1000));
        
        // Re-discover services and characteristics
        final services = await device.discoverServices();
        final customService = services.firstWhere(
          (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
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

  /// Richiede la lista degli storici della frequenza cardiaca
  Future<void> requestHRHistoryList() async {
    // Check if we should throttle historical data requests
    if (_shouldThrottleHistoricalRequests('hr')) {
      debugPrint(
          '💓 ⏸️ HR history request throttled (too many recent requests)');
      return;
    }

    debugPrint('💓 Requesting HR history list...');
    try {
      // Usa il comando ufficiale con checksum corretto
      List<int> command = OfficialChileafCommands.getHistoryOfHRRecord();
      debugPrint('🔄💓 Requesting COMPLETE HR History with optimized checksum...');
      debugPrint('📡 Official Command: ${OfficialChileafCommands.commandToHexString(command)}');
      await _sendCommand(command);

      // Update throttling counters
      _hrHistoryRequests++;
      _lastHRHistoryRequest = DateTime.now();
    } catch (e) {
      debugPrint('❌ Failed to request HR history list: $e');
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

  /// Richiede i dati storici HR per un timestamp specifico
  Future<void> requestHRHistoryData(DateTime timestamp) async {
    int utcTimestamp = timestamp.millisecondsSinceEpoch ~/ 1000;
    debugPrint(
        '💓 Requesting HR history data for timestamp: $timestamp ($utcTimestamp)');
    try {
      // Usa il comando ufficiale con checksum corretto
      List<int> command = OfficialChileafCommands.getHistoryOfHRData(utcTimestamp);
      
      debugPrint('🏗️ Building HR History Data Request (0x22) for timestamp: $utcTimestamp');
      debugPrint('📡 Official Command: ${OfficialChileafCommands.commandToHexString(command)}');
      
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request HR history data: $e');
    }
  }

  /// Richiede automaticamente i dati HR dettagliati per ogni timestamp nella lista
  Future<void> _requestDetailedHRData(
      HeartRateHistoryList hrHistoryList) async {
    debugPrint(
        '💓 Auto-requesting detailed HR data for ${hrHistoryList.timestamps.length} timestamps');

    // DEBUG: Filtra per timestamp di agosto 2025 (più probabili di essere validi)
    List<DateTime> validTimestamps = hrHistoryList.timestamps.where((timestamp) {
      // Solo timestamp di agosto 2025 (più probabili di essere corretti)
      return timestamp.year == 2025 && timestamp.month == 8;
    }).toList();
    
    debugPrint('💓 📊 DEBUG: Found ${validTimestamps.length} August 2025 timestamps out of ${hrHistoryList.timestamps.length} total');
    
    // Se non ci sono timestamp di agosto 2025, prova quelli più recenti
    if (validTimestamps.isEmpty) {
      DateTime now = DateTime.now();
      validTimestamps = hrHistoryList.timestamps.where((timestamp) {
        // Prendi timestamp entro 7 giorni da oggi
        Duration diff = (timestamp.difference(now)).abs();
        return diff.inDays <= 7;
      }).toList();
      debugPrint('💓 📊 DEBUG: No August 2025 timestamps, trying ${validTimestamps.length} recent timestamps (within 7 days)');
    }
    
    // Se ancora vuoti, prendi tutti ma logga il problema
    if (validTimestamps.isEmpty) {
      validTimestamps = hrHistoryList.timestamps;
      debugPrint('💓 ⚠️ Using all ${validTimestamps.length} timestamps (may include problematic dates)');
    }

    if (validTimestamps.isEmpty) {
      debugPrint('💓 ⚠️ No HR timestamps found in list');
      return;
    }

    // Limit to max 3 detailed requests to prevent spam durante il debug
    const maxRequests = 3;
    final requestTimestamps = validTimestamps.take(maxRequests).toList();

    debugPrint(
        '💓 Requesting detailed data for ${requestTimestamps.length}/${hrHistoryList.timestamps.length} timestamps (DEBUG MODE)');

    for (int i = 0; i < requestTimestamps.length; i++) {
      try {
        await Future.delayed(
            Duration(milliseconds: 500 * i)); // Longer delay between requests
        await requestHRHistoryData(requestTimestamps[i]);
      } catch (e) {
        debugPrint(
            '❌ Failed to request HR data for timestamp ${requestTimestamps[i]}: $e');
      }
    }
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

  /// Reset device usando comando ufficiale 0xF3
  Future<void> deviceReset() async {
    debugPrint('🔄 Resetting device using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.deviceReset();

      debugPrint('🔍 Official reset command:');
      debugPrint('   Command: 0xF3 (restoration from WearManager.java)');
      debugPrint(
          '   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');

      await _sendCommand(officialCommand);
      debugPrint('✅ Official reset command sent');
    } catch (e) {
      debugPrint('❌ Failed to reset device with official command: $e');
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
  /// Utilizza comando 0x05 con checksum Java ottimizzato
  Future<void> requestOptimizedSleepHistory() async {
    debugPrint('🔄😴 Requesting Sleep History with optimized checksum...');
    await _historicalDataService.requestSleepHistoryEnhanced();
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
    try {
      // Command 0x90: Step counting history data list request
      List<int> command = [0xFF, 0x05, 0x90, 0x00];
      int checksum = _calculateJavaChecksum(command.sublist(1));
      command[3] = checksum;
      
      await _sendCommand(command);
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

  /// Calcola checksum Java secondo il protocollo ufficiale Chileaf
  /// Algoritmo: (-sum) ^ 0x3A & 0xFF
  int _calculateJavaChecksum(List<int> frame) {
    int sum = 0;
    for (int byte in frame) {
      sum += byte;
    }
    int checksum = (-sum) & 0xFF;  // Negazione + mask 8-bit
    checksum ^= 0x3A;              // XOR con costante 0x3A
    return checksum & 0xFF;        // Final mask
  }
}
