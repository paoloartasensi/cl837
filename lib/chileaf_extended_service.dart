import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

// Data Models
import 'models/spo2_data.dart';
import 'models/temperature_data.dart';
import 'models/hrv_data.dart';
import 'models/heart_rate_data.dart';
import 'models/historical_data.dart';
import 'models/rope_data.dart';
import 'models/device_info.dart';

// Data Processors
import 'services/data_processors/spo2_processor.dart';
import 'services/data_processors/temperature_processor.dart';
import 'services/data_processors/accelerometer_processor.dart';
import 'services/data_processors/health_processor.dart';
import 'services/data_processors/historical_data_processor.dart';
import 'services/historical_data_service.dart';
import 'services/data_processors/rope_processor.dart';
import 'services/data_processors/device_info_processor.dart';

// Protocol & Commands
import 'services/ble_protocol/chileaf_protocol.dart';
import 'services/ble_protocol/command_builder.dart';
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

  // Bluetooth characteristics
  BluetoothCharacteristic? _txCharacteristic;
  BluetoothCharacteristic? _rxCharacteristic;
  StreamSubscription? _dataSubscription;
  Timer? _dataRequestTimer;

  // Historical data service with optimized checksum
  late final HistoricalDataService _historicalDataService;

  // ===== BLOOD OXYGEN (SpO2) MEASUREMENT SYSTEM =====
  // Stato della misurazione SpO2 ottimizzato seguendo pipeline ufficiale
  bool _spo2MeasurementActive = false;
  bool _spo2MeasurementPaused = false;
  String? _lastSpO2Value;
  Timer? _spo2MeasurementTimer;
  StreamSubscription? _spo2DataSubscription;

  // Callback per UI updates e completamento automatico
  void Function(String spo2Value)? _onSpO2ValueReceived;
  void Function()? _onSpO2MeasurementComplete;
  void Function(String error)? _onSpO2Error;

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

  // Log throttling for high-frequency data
  int _accelerometerLogCount = 0;
  int _totalDataPackets = 0;
  int _healthDataLogCount = 0;
  int _temperatureLogCount = 0;
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
  final int _temperatureThrottleInterval =
      100; // Log every 100th temperature (was 50)
  final int _sportsThrottleInterval =
      500; // Log every 500th sports data (was 50) - MUCH LESS NOISE
  final int _accelerometerThrottleInterval =
      500; // Log every 500th accelerometer batch (was 200)

  // Data Processors
  late final SpO2Processor _spo2Processor;
  late final TemperatureProcessor _temperatureProcessor;
  late final AccelerometerProcessor _accelerometerProcessor;
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
  final StreamController<BatteryInfo> _batteryInfoController =
      StreamController<BatteryInfo>.broadcast();
  final StreamController<String> _firmwareVersionController =
      StreamController<String>.broadcast();
  final StreamController<String> _hardwareVersionController =
      StreamController<String>.broadcast();
  final StreamController<String> _deviceNameController =
      StreamController<String>.broadcast();
  final StreamController<String> _macAddressController =
      StreamController<String>.broadcast();

  // Constructor
  ChileafExtendedService() {
    _initializeProcessors();
  }

  void _initializeProcessors() {
    _spo2Processor = SpO2Processor();
    _temperatureProcessor = TemperatureProcessor();
    _accelerometerProcessor = AccelerometerProcessor();
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

  // Rope skipping streams
  Stream<RopeSkippingData> get ropeStatusStream => _ropeStatusController.stream;
  Stream<RopeRealtimeData> get ropeRealtimeStream =>
      _ropeRealtimeController.stream;

  // Device info streams
  Stream<DeviceInfo> get deviceInfoStream => _deviceInfoController.stream;
  Stream<BatteryInfo> get batteryInfoStream => _batteryInfoController.stream;
  Stream<String> get firmwareVersionStream => _firmwareVersionController.stream;
  Stream<String> get hardwareVersionStream => _hardwareVersionController.stream;
  Stream<String> get deviceNameStream => _deviceNameController.stream;
  Stream<String> get macAddressStream => _macAddressController.stream;

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('Starting Chileaf Extended Service...');

      // Add delay to ensure services are discovered
      await Future.delayed(const Duration(milliseconds: 2000));

      final services = await device.discoverServices();
      debugPrint('Extended Service: Found ${services.length} services');

      // Find custom service - more robust matching
      BluetoothService? customService;

      for (var service in services) {
        debugPrint('Service UUID: ${service.uuid}');
        if (service.uuid.toString().toLowerCase() ==
            _customServiceUuid.toLowerCase()) {
          customService = service;
          break;
        }
      }

      customService ??= services.firstWhere(
        (s) => s.uuid
            .toString()
            .toLowerCase()
            .contains(_customServiceUuid.split('-')[0].toLowerCase()),
        orElse: () => throw Exception('Custom service not found'),
      );

      debugPrint('Found custom service: ${customService.uuid}');

      // Setup characteristics
      await _setupCharacteristics(customService);

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
    debugPrint(
        'Service has ${customService.characteristics.length} characteristics:');
    for (var char in customService.characteristics) {
      debugPrint(
          '  - ${char.uuid} (properties: notify=${char.properties.notify}, read=${char.properties.read}, write=${char.properties.write})');
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

    // Initial commands to start data flow
    await Future.delayed(const Duration(milliseconds: 500));
    await _sendCommand(CommandBuilder.buildTemperatureDataRequest());

    // Set up periodic data requests (only medical-grade sensors)
    _dataRequestTimer =
        Timer.periodic(const Duration(seconds: 5), (timer) async {
      try {
        await _sendCommand(CommandBuilder.buildTemperatureDataRequest());
      } catch (e) {
        debugPrint('Error in periodic data request: $e');
      }
    });
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
      bool isHighFrequency = command == ChileafProtocol.commandAccelerometer ||
          command == ChileafProtocol.commandHealthData ||
          command == ChileafProtocol.commandTemperature ||
          command == ChileafProtocol.commandSports;

      // NEVER log frame details for high frequency data
      if (_enableVerboseLogging && !isHighFrequency) {
        ChileafProtocol.logFrameDetails(data);
      }

      // Handle different data formats
      if (ChileafProtocol.isValidChileafFrame(data)) {
        if (command == null) return;

        // Only log command processing for NON-high frequency commands
        if (_enableVerboseLogging || !isHighFrequency) {
          debugPrint(
              'Processing command: ${ChileafProtocol.getCommandName(command)}');
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
      case ChileafProtocol.commandAccelerometer:
        _accelerometerLogCount++;
        return _accelerometerLogCount % _accelerometerThrottleInterval == 0;
      case ChileafProtocol.commandHealthData:
        _healthDataLogCount++;
        return _healthDataLogCount % _healthDataThrottleInterval == 0;
      case ChileafProtocol.commandTemperature:
        _temperatureLogCount++;
        return _temperatureLogCount % _temperatureThrottleInterval == 0;
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
      case 0x02: // Battery Level
        debugPrint('🔋 BATTERY LEVEL: Processing battery information');
        var batteryInfo = DeviceInfoProcessor.processBatteryLevel(data);
        if (batteryInfo != null) {
          _batteryInfoController.add(batteryInfo);
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
        debugPrint('🫁 BLOOD OXYGEN DATA RECEIVED (Command 0x37)!');
        debugPrint('📊 Processing through official pipeline...');
        _spo2Processor.processSPO2Data(data);
        break;
      case ChileafProtocol.commandTemperature:
        // SILENTLY process temperature data - no logging
        _temperatureProcessor.processTemperatureData(data);
        break;
      case ChileafProtocol.commandAccelerometer:
        // SILENTLY process accelerometer data - no logging
        _accelerometerProcessor.processAccelerometerData(data);
        break;
      case ChileafProtocol.commandHealthData:
        // SILENTLY process health data - no logging
        _healthProcessor.processHealthData(data);
        break;
      case 0x16: // Exercise History
        debugPrint(
            '📊 EXERCISE HISTORY DATA: Processing historical exercise data with OFFICIAL format');
        var exerciseHistory =
            HistoricalDataProcessor.processExerciseHistoryOfficial(
                Uint8List.fromList(data));
        if (exerciseHistory.isNotEmpty) {
          _exerciseHistoryController.add(exerciseHistory);
        }
        break;
      case 0x21: // HR History List
        debugPrint('💓 HR HISTORY LIST: Processing HR timestamp list');
        var hrHistoryList = HistoricalDataProcessor.processHRHistoryList(
            Uint8List.fromList(data));
        _hrHistoryListController.add(hrHistoryList);

        // Auto-request detailed data for each timestamp
        _requestDetailedHRData(hrHistoryList);
        break;
      case 0x22: // HR History Data
        debugPrint(
            '💓 HR HISTORY DATA: Processing detailed HR historical data');
        var hrHistoryData = HistoricalDataProcessor.processHRHistoryData(
            Uint8List.fromList(data));
        if (hrHistoryData != null) {
          _hrHistoryDataController.add(hrHistoryData);
        }
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
  /// Pipeline: UI → Command → BLE TX → Device → BLE RX → Parse → Callback → UI Update
  Future<void> startBloodOxygenMeasurement() async {
    if (_spo2MeasurementActive) {
      debugPrint('🩸 SpO2 measurement already active, ignoring start request');
      return;
    }

    debugPrint('🩸 STARTING Blood Oxygen Measurement');

    try {
      debugPrint('🩸🔧 Phase 1: Sending BLE Command');
      debugPrint('   Command: 0x37 (55 decimal) - 0 = Stop');
      _spo2MeasurementActive = true;
      _spo2MeasurementPaused = false;
      _lastSpO2Value = null;
      var bloodOxygenCommand = OfficialChileafCommands.setBloodOxygen(0);
      await _sendCommand(bloodOxygenCommand);
      debugPrint('🩸✅ Phase 1 Complete: Reset');

      debugPrint('🩸🔧 Phase 2: Sending BLE Command');
      debugPrint('   Command: 0x37 (55 decimal) - 1 = Start');
      bloodOxygenCommand = OfficialChileafCommands.setBloodOxygen(1);
      await _sendCommand(bloodOxygenCommand);
      debugPrint('🩸✅ Phase 2 Complete: Start');

      // Phase 4: Setup data reception callback
      debugPrint('🩸🔄 Phase 3: Setting up data reception pipeline');
      _setupBloodOxygenDataReception();

      // Phase 5: Setup measurement timer (similar to Android app Timer/TimerTask)
      _setupBloodOxygenTimer();

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

  /// Setup del timer di misurazione (equivalente al Timer/TimerTask dell'app Android)
  void _setupBloodOxygenTimer() {
    debugPrint('⏱️ Setting up measurement timer (60 seconds max)');

    _spo2MeasurementTimer?.cancel();
    _spo2MeasurementTimer = Timer(const Duration(seconds: 60), () async {
      debugPrint('⏰ Blood oxygen measurement timeout (60s) - auto stopping');
      await stopBloodOxygenMeasurement();

      if (_onSpO2MeasurementComplete != null) {
        _onSpO2MeasurementComplete!();
      }
    });
  }

  /// Setup della ricezione dati SpO2 (equivalente a BloodOxygenCallback.onBloodOxygenReceived)
  void _setupBloodOxygenDataReception() {
    debugPrint('📥 Setting up Blood Oxygen data reception callback');

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
  }

  /// Gestisce i dati SpO2 ricevuti (REPLICA ESATTA del comportamento Android)
  /// Equivalente a: onBloodOxygenReceived(bluetoothDevice, final int i, final String str, int i2, int i3, int i4)
  /// Logica Android: if (str != "" && str != null && Integer.valueOf(str) > 0) → pause = true
  void _handleBloodOxygenReceived(SpO2Data spo2Data) {
    if (!_spo2MeasurementActive) return;

    debugPrint('📊 Blood Oxygen Data Received:');
    debugPrint('   Value: ${spo2Data.value}%');
    debugPrint('   PI (Signal): ${spo2Data.piValue}');
    debugPrint(
        '   Gesture (Posture): ${spo2Data.gesture} (${spo2Data.correctWristPosture ? "Correct" : "Incorrect"})');
    debugPrint(
        '   On Wrist: ${spo2Data.onWrist} (${spo2Data.isWearing ? "Wearing" : "Not Wearing"})');
    debugPrint('   Reliable: ${spo2Data.isReliable}');

    // Validation following Android app logic
    if (spo2Data.value > 0 && spo2Data.isValidMeasurement) {
      String valueStr = spo2Data.value.toString();
      _lastSpO2Value = valueStr;

      debugPrint('✅ Valid SpO2 reading: $valueStr%');
      debugPrint('🔄 Updating UI (equivalent to runOnUiThread)');

      // Trigger UI update callback (equivalent to mTxtBloodOxygenValue.setText(str + "%"))
      if (_onSpO2ValueReceived != null) {
        _onSpO2ValueReceived!(valueStr);
      }

      // Pause measurement when valid value received (like Android app)
      if (!_spo2MeasurementPaused) {
        _spo2MeasurementPaused = true;
        debugPrint('⏸️ Measurement paused after valid reading');

        // Auto-complete after valid reading (user can save or continue)
        Future.delayed(const Duration(seconds: 2), () async {
          if (_spo2MeasurementActive && _spo2MeasurementPaused) {
            debugPrint('� Auto-completing measurement after valid reading');
            await stopBloodOxygenMeasurement();

            if (_onSpO2MeasurementComplete != null) {
              _onSpO2MeasurementComplete!();
            }
          }
        });
      }
    } else {
      debugPrint(
          '⚠️ Invalid or unreliable SpO2 reading - continuing measurement');
    }
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
      var stopCommand = OfficialChileafCommands.setBloodOxygen(0);
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

  // ===== COMMAND SENDING =====

  /// Invia un comando BLE al dispositivo
  /// Gestisce automaticamente writeWithoutResponse vs write normale
  Future<void> _sendCommand(List<int> frame) async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }

    debugPrint(
        '📡 Sending: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');

    try {
      if (_rxCharacteristic!.properties.writeWithoutResponse) {
        await _rxCharacteristic!.write(frame, withoutResponse: true);
      } else {
        await _rxCharacteristic!.write(frame, withoutResponse: false);
      }
      await Future.delayed(const Duration(milliseconds: 100));
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

    // Close historical data streams
    await _exerciseHistoryController.close();
    await _hrHistoryListController.close();
    await _hrHistoryDataController.close();
  }

  void dispose() {
    debugPrint('Disposing Chileaf Extended Service...');
    stop();

    // Close all stream controllers
    _exerciseHistoryController.close();
    _hrHistoryListController.close();
    _hrHistoryDataController.close();
    _ropeStatusController.close();
    _ropeRealtimeController.close();
    _deviceInfoController.close();
    _batteryInfoController.close();
    _firmwareVersionController.close();
    _hardwareVersionController.close();
    _deviceNameController.close();
    _macAddressController.close();

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
      List<int> command = CommandBuilder.buildHRHistoryListRequest();
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
      List<int> command =
          CommandBuilder.buildHRHistoryDataRequest(utcTimestamp);
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

    // Filter out obviously invalid timestamps to prevent infinite loops
    List<DateTime> validTimestamps = [];
    final now = DateTime.now();
    final earliestValid = DateTime(2020, 1, 1); // Nothing before 2020
    final latestValid = now.add(
        const Duration(days: 30)); // Nothing more than 30 days in the future

    for (var timestamp in hrHistoryList.timestamps) {
      if (timestamp.isAfter(earliestValid) && timestamp.isBefore(latestValid)) {
        validTimestamps.add(timestamp);
      }
      // SILENT - no logging for invalid timestamps to reduce spam
    }

    if (validTimestamps.isEmpty) {
      debugPrint(
          '💓 ⚠️ No valid HR timestamps found (all outside range 2020-${latestValid.year}), skipping detailed requests');
      return;
    }

    // Count invalid timestamps for summary
    int invalidCount = hrHistoryList.timestamps.length - validTimestamps.length;
    if (invalidCount > 0) {
      debugPrint(
          '💓 📊 Filtered out $invalidCount invalid timestamps (keeping ${validTimestamps.length} valid)');
    }

    // Limit to max 5 detailed requests to prevent spam
    const maxRequests = 5;
    final requestTimestamps = validTimestamps.take(maxRequests).toList();

    debugPrint(
        '💓 Requesting detailed data for ${requestTimestamps.length}/${hrHistoryList.timestamps.length} valid timestamps');

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

  /// Richiede livello batteria esteso
  Future<void> requestBatteryInfo() async {
    debugPrint('🔋 Requesting battery info...');
    try {
      if (_txCharacteristic != null) {
        var frame = ChileafProtocol.buildProtocolFrame([0x02]);
        await _txCharacteristic!.write(frame, withoutResponse: false);
        debugPrint('✅ Battery info request sent');
      } else {
        debugPrint(
            '❌ TX characteristic not available for battery info request');
      }
    } catch (e) {
      debugPrint('❌ Failed to request battery info: $e');
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

      await requestBatteryInfo();
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
}
