import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

// Data Models
import 'models/sports_data.dart';
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
import 'services/data_processors/sports_processor.dart';
import 'services/data_processors/accelerometer_processor.dart';
import 'services/data_processors/health_processor.dart';
import 'services/data_processors/historical_data_processor.dart';
import 'services/data_processors/rope_processor.dart';
import 'services/data_processors/device_info_processor.dart';

// Protocol & Commands
import 'services/ble_protocol/chileaf_protocol.dart';
import 'services/ble_protocol/command_builder.dart';
import 'services/ble_protocol/official_commands_complete.dart';

// Diagnostics
import 'services/diagnostics/spo2_diagnostics.dart';
import 'services/diagnostics/ble_diagnostics.dart';

/// Servizio principale per la gestione del dispositivo Chileaf Extended
/// Coordinatore che orchestra tutti i processori di dati e la comunicazione BLE
class ChileafExtendedService {
  // Chileaf Custom Service & Characteristics (from SDK documentation)
  static const String _customServiceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _txCharUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0'; // Read from device (NOTIFY)
  static const String _rxCharUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0'; // Write to device (WRITE)

  // Bluetooth characteristics
  BluetoothCharacteristic? _txCharacteristic;
  BluetoothCharacteristic? _rxCharacteristic;
  StreamSubscription? _dataSubscription;
  Timer? _dataRequestTimer;
  Timer? _spo2Timer;
  
  // Callback per notificare il completamento automatico del test SpO2
  void Function()? _onSpO2AutoComplete;
  
  // Tracciamento letture consecutive valide per WatchFit
  int _consecutiveValidReadings = 0;
  bool _spo2MeasurementActive = false;
  
  void setSpO2AutoCompleteCallback(void Function()? callback) {
    _onSpO2AutoComplete = callback;
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
  static const Duration _historicalRequestCooldown = Duration(minutes: 5); // 5 min cooldown
  
  // Debug logging control - VERY AGGRESSIVE THROTTLING
  final bool _enableVerboseLogging = false; // Set to true for detailed logs
  final int _logThrottleInterval = 500; // Log every 500 packets (was 50)
  final int _healthDataThrottleInterval = 100; // Log health data every 100 occurrences (was 10)
  final int _temperatureThrottleInterval = 50; // Log every 50th temperature (was 5)
  final int _sportsThrottleInterval = 50; // Log every 50th sports data (was 5)
  final int _accelerometerThrottleInterval = 200; // Log every 200th accelerometer batch

  // Data Processors
  late final SpO2Processor _spo2Processor;
  late final TemperatureProcessor _temperatureProcessor;
  late final SportsProcessor _sportsProcessor;
  late final AccelerometerProcessor _accelerometerProcessor;
  late final HealthProcessor _healthProcessor;

  // Diagnostics
  SpO2Diagnostics? _spo2Diagnostics;
  BLEDiagnostics? _bleDiagnostics;

  // Historical data streams
  final StreamController<List<ExerciseHistoryData>> _exerciseHistoryController = StreamController<List<ExerciseHistoryData>>.broadcast();
  final StreamController<HeartRateHistoryList> _hrHistoryListController = StreamController<HeartRateHistoryList>.broadcast();
  final StreamController<HeartRateHistoryData> _hrHistoryDataController = StreamController<HeartRateHistoryData>.broadcast();

  // Rope skipping streams
  final StreamController<RopeSkippingData> _ropeStatusController = StreamController<RopeSkippingData>.broadcast();
  final StreamController<RopeRealtimeData> _ropeRealtimeController = StreamController<RopeRealtimeData>.broadcast();

  // Device info streams
  final StreamController<DeviceInfo> _deviceInfoController = StreamController<DeviceInfo>.broadcast();
  final StreamController<BatteryInfo> _batteryInfoController = StreamController<BatteryInfo>.broadcast();
  final StreamController<String> _firmwareVersionController = StreamController<String>.broadcast();
  final StreamController<String> _hardwareVersionController = StreamController<String>.broadcast();
  final StreamController<String> _deviceNameController = StreamController<String>.broadcast();
  final StreamController<String> _macAddressController = StreamController<String>.broadcast();

  // Constructor
  ChileafExtendedService() {
    _initializeProcessors();
  }

  void _initializeProcessors() {
    _spo2Processor = SpO2Processor();
    _temperatureProcessor = TemperatureProcessor();
    _sportsProcessor = SportsProcessor();
    _accelerometerProcessor = AccelerometerProcessor();
    _healthProcessor = HealthProcessor();
  }

  // Public streams - delegate to processors
  Stream<SportsData> get sportsDataStream => _sportsProcessor.sportsDataStream;
  Stream<SpO2Data> get spo2DataStream => _spo2Processor.spo2DataStream;
  Stream<TemperatureData> get temperatureDataStream => _temperatureProcessor.temperatureDataStream;
  Stream<HRVData> get hrvDataStream => _healthProcessor.hrvDataStream;
  
  // Historical data streams
  Stream<List<ExerciseHistoryData>> get exerciseHistoryStream => _exerciseHistoryController.stream;
  Stream<HeartRateHistoryList> get hrHistoryListStream => _hrHistoryListController.stream;
  Stream<HeartRateHistoryData> get hrHistoryDataStream => _hrHistoryDataController.stream;

  // Rope skipping streams
  Stream<RopeSkippingData> get ropeStatusStream => _ropeStatusController.stream;
  Stream<RopeRealtimeData> get ropeRealtimeStream => _ropeRealtimeController.stream;

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
        if (service.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase()) {
          customService = service;
          break;
        }
      }
      
      customService ??= services.firstWhere(
          (s) => s.uuid.toString().toLowerCase().contains(_customServiceUuid.split('-')[0].toLowerCase()),
          orElse: () => throw Exception('Custom service not found'),
        );

      debugPrint('Found custom service: ${customService.uuid}');
      
      // Find and setup characteristics
      await _setupCharacteristics(customService);
      
      // Initialize diagnostics
      _spo2Diagnostics = SpO2Diagnostics(_rxCharacteristic);
      _bleDiagnostics = BLEDiagnostics(_rxCharacteristic, _txCharacteristic);

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
    await Future.delayed(const Duration(milliseconds: 500));
    await _sendCommand(CommandBuilder.buildSportsDataRequest());

    // Set up periodic data requests
    _dataRequestTimer = Timer.periodic(const Duration(seconds: 5), (timer) async {
      try {
        await _sendCommand(CommandBuilder.buildTemperatureDataRequest());
        await Future.delayed(const Duration(milliseconds: 300));
        await _sendCommand(CommandBuilder.buildSportsDataRequest());
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
          debugPrint('🔄 _processIncomingData called with ${data.length} bytes');
        } else {
          // Log batch updates much less frequently
          if (_totalDataPackets % _logThrottleInterval == 0) {
            debugPrint('🔄 Processed $_totalDataPackets packets (batch update)');
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
          debugPrint('Processing command: ${ChileafProtocol.getCommandName(command)}');
        }
        
        // 🎯 TARGETED SpO2 SEARCH: Only for important SpO2 analysis - completely silent
        if (ChileafProtocol.commandContainsSpO2Data(command)) {
          if (command == ChileafProtocol.commandHealthData) {
            _healthDataLogCount++;
            // Always silent - no SpO2 search logging
            _spo2Processor.aggressiveSpO2Search(data, shouldLogDetails: false);
          }
          // Enhanced analysis is always silent now
          _spo2Processor.enhancedSpO2Analysis(data, shouldLogDetails: false);
        }
        
        // Route to appropriate processor
        _routeToProcessor(command, data, shouldLog);
      } else if (data.length >= 4) {
        // Try to detect data patterns without strict protocol
        _tryDetectDataPatterns(data);
      }
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
        // SILENTLY process sports data - no logging
        _sportsProcessor.processSportsData(data);
        break;
      case ChileafProtocol.commandSpo2:
        debugPrint('🫁 RECEIVED SPO2 DATA! Processing...');
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
        debugPrint('📊 EXERCISE HISTORY DATA: Processing historical exercise data with OFFICIAL format');
        var exerciseHistory = HistoricalDataProcessor.processExerciseHistoryOfficial(Uint8List.fromList(data));
        if (exerciseHistory.isNotEmpty) {
          _exerciseHistoryController.add(exerciseHistory);
        }
        break;
      case 0x21: // HR History List
        debugPrint('💓 HR HISTORY LIST: Processing HR timestamp list');
        var hrHistoryList = HistoricalDataProcessor.processHRHistoryList(Uint8List.fromList(data));
        _hrHistoryListController.add(hrHistoryList);
        
        // Auto-request detailed data for each timestamp
        _requestDetailedHRData(hrHistoryList);
        break;
      case 0x22: // HR History Data
        debugPrint('💓 HR HISTORY DATA: Processing detailed HR historical data');
        var hrHistoryData = HistoricalDataProcessor.processHRHistoryData(Uint8List.fromList(data));
        if (hrHistoryData != null) {
          _hrHistoryDataController.add(hrHistoryData);
        }
        break;
      case 0x23: // HR History End Signal
        debugPrint('🏁 HR HISTORY END: Received end signal for HR history data');
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
        debugPrint('Unhandled Chileaf command: 0x${command.toRadixString(16)} (${data.length} bytes)');
    }
  }

  void _tryDetectDataPatterns(List<int> data) {
    debugPrint('Trying pattern detection on ${data.length} bytes');
    
    // Try temperature pattern
    _temperatureProcessor.detectTemperaturePattern(data);
    
    // Try SpO2 pattern (only if not from accelerometer)
    _spo2Processor.detectSpO2Pattern(data);
  }

  // Process RR intervals from heart rate data for HRV calculation
  void processRRIntervalsForHRV(HeartRateData heartRateData) {
    _healthProcessor.processRRIntervalsForHRV(heartRateData);
  }

  // Public SpO2 measurement methods using OFFICIAL commands
  /// Avvia la misurazione SpO2 WatchFit: 50 secondi max, interruzione anticipata con 2 letture valide consecutive
  /// Criteri per lettura valida WatchFit:
  /// - Segnale qualità > 15 (eccellente)
  /// - Postura corretta (correctWristPosture = true)
  /// - Dispositivo indossato (isWearing = true)
  /// - SpO2 nel range 70-100%
  /// Se 2 letture consecutive soddisfano questi criteri, il test termina automaticamente
  Future<void> measureSpO2() async {
    debugPrint('🩸 Starting WatchFit SpO2 measurement: 50s max, early termination with 2 valid consecutive readings...');
    
    // Reset contatori per nuova misurazione
    _consecutiveValidReadings = 0;
    _spo2MeasurementActive = true;
    
    try {
      // Prima prova con il comando ufficiale
      var officialCommand = OfficialChileafCommands.setBloodOxygen(1);
      
      debugPrint('🔍 Trying official SpO2 command first:');
      debugPrint('   Command: 0x37 mode=1 (setBloodOxygen from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
      await _sendCommand(officialCommand);
      debugPrint('✅ Official SpO2 command sent');
      
      // Aggiungi un piccolo delay
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Se il comando ufficiale non accende il LED, usa il formato che funziona nel test
      debugPrint('🔄 Ensuring LED activation with alternative format...');
      await _sendCommand(CommandBuilder.buildEnableSpO2Mode());
      debugPrint('🚨 LED SpO2 activation command sent - LED rosso acceso per 50 secondi max');
      
      // Timer automatico di 50 secondi per spegnere il LED (WatchFit ottimizzato)
      _spo2Timer?.cancel();
      _spo2Timer = Timer(const Duration(seconds: 50), () async {
        debugPrint('⏰ 50-second WatchFit SpO2 measurement completed - auto-stopping');
        await stopSpO2Measurement();
        
        // Notifica il completamento automatico al widget
        if (_onSpO2AutoComplete != null) {
          _onSpO2AutoComplete!();
        }
      });
      
      // Setup stream listener per rilevare terminazione anticipata
      _setupSpO2EarlyTermination();
      
    } catch (e) {
      debugPrint('❌ Failed to start SpO2 measurement: $e');
      // Fallback to diagnostics if available
      try {
        debugPrint('🔄 Fallback to diagnostics method...');
        await _sendCommand(CommandBuilder.buildEnableSpO2Mode());
      } catch (fallbackError) {
        throw Exception('SpO2 measurement failed: $e, Fallback failed: $fallbackError');
      }
    }
  }

  /// Setup listener per terminazione anticipata WatchFit
  void _setupSpO2EarlyTermination() {
    if (!_spo2MeasurementActive) return;
    
    // Ascolta le letture SpO2 per rilevare 2 consecutive valide
    _spo2Processor.spo2DataStream.listen((data) {
      if (!_spo2MeasurementActive) return;
      
      // Controlla se la lettura è valida per WatchFit (segnale >15, postura corretta)
      if (_isWatchFitValidReading(data)) {
        _consecutiveValidReadings++;
        debugPrint('📊 WatchFit valid reading #$_consecutiveValidReadings: SpO2=${data.spo2Value}%, signal=${data.signalQuality}/15');
        
        // Se abbiamo 2 letture consecutive valide, termina anticipatamente
        if (_consecutiveValidReadings >= 2) {
          debugPrint('🎯 WatchFit EARLY TERMINATION: 2 consecutive valid readings achieved!');
          _triggerEarlyCompletion();
        }
      } else {
        // Reset contatore se la lettura non è valida
        if (_consecutiveValidReadings > 0) {
          debugPrint('🔄 WatchFit: Invalid reading, resetting counter (signal=${data.signalQuality}, posture=${data.correctWristPosture})');
          _consecutiveValidReadings = 0;
        }
      }
    });
  }

  /// Controlla se una lettura SpO2 è valida per WatchFit (segnale >15, postura corretta)
  bool _isWatchFitValidReading(SpO2Data data) {
    return data.signalQuality > 15 && 
           data.correctWristPosture && 
           data.isWearing && 
           data.spo2Value != null && 
           data.spo2Value! >= 70 && 
           data.spo2Value! <= 100;
  }

  /// Attiva la terminazione anticipata del test SpO2
  Future<void> _triggerEarlyCompletion() async {
    if (!_spo2MeasurementActive) return;
    
    debugPrint('🏁 WatchFit early completion triggered - stopping measurement');
    await stopSpO2Measurement();
    
    // Notifica il completamento automatico al widget
    if (_onSpO2AutoComplete != null) {
      _onSpO2AutoComplete!();
    }
  }

  /// Ferma la misurazione SpO2 e spegne il LED rosso
  Future<void> stopSpO2Measurement() async {
    debugPrint('🛑 Stopping WatchFit SpO2 measurement and turning off LED...');
    
    // Reset stati WatchFit
    _spo2MeasurementActive = false;
    _consecutiveValidReadings = 0;
    
    try {
      // Cancella il timer automatico se attivo
      _spo2Timer?.cancel();
      _spo2Timer = null;
      
      // Prima usa il comando ufficiale per fermare
      var officialCommand = OfficialChileafCommands.setBloodOxygen(0);
      
      debugPrint('🔍 Official SpO2 stop command:');
      debugPrint('   Command: 0x37 mode=0 (stop setBloodOxygen)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
      await _sendCommand(officialCommand);
      debugPrint('✅ Official SpO2 stop command sent');
      
      // Aggiungi un piccolo delay
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Assicurati che il LED sia spento usando il comando che funziona
      debugPrint('🔄 Ensuring LED deactivation...');
      await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      debugPrint('🚨 LED SpO2 deactivation command sent - LED rosso spento, torna verde');
      
    } catch (e) {
      debugPrint('❌ Failed to stop SpO2 measurement with official command: $e');
      rethrow;
    }
  }

  Future<void> exitSPO2Mode() async {
    await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
  }

  Future<void> forceExitSpO2Mode() async {
    if (_spo2Diagnostics != null) {
      await _spo2Diagnostics!.forceExitSpO2Mode();
    }
  }

  // Diagnostic methods
  Future<void> testLEDFunctionality() async {
    if (_spo2Diagnostics != null) {
      await _spo2Diagnostics!.testLEDFunctionality();
    }
  }

  Future<void> diagnoseBLEIssues() async {
    if (_bleDiagnostics != null) {
      await _bleDiagnostics!.diagnoseBLEIssues();
    }
  }

  Future<bool> checkBLEConnection() async {
    if (_bleDiagnostics != null) {
      return await _bleDiagnostics!.checkBLEConnection();
    }
    return false;
  }

  // Test methods
  Future<void> testSpO2CommandFormats() async {
    if (_spo2Diagnostics != null) {
      await _spo2Diagnostics!.testSpO2CommandFormats();
    }
  }

  Future<void> measureSpO2Alternative() async {
    if (_spo2Diagnostics != null) {
      await _spo2Diagnostics!.testAlternativeSpO2Methods();
    }
  }

  // Command sending
  Future<void> _sendCommand(List<int> frame) async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }

    debugPrint('📡 Sending: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
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
    _spo2Timer?.cancel();
    _spo2Timer = null;
    await _dataSubscription?.cancel();
    _dataSubscription = null;
    
    // Close historical data streams
    await _exerciseHistoryController.close();
    await _hrHistoryListController.close();
    await _hrHistoryDataController.close();
    
    // Exit SPO2 mode before stopping
    try {
      await exitSPO2Mode();
    } catch (e) {
      debugPrint('Error exiting SPO2 mode: $e');
    }
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
    _sportsProcessor.dispose();
    _healthProcessor.dispose();
  }

  // === Historical Data Methods ===
  
  /// Richiede lo storico degli esercizi usando comando ufficiale 0x16
  Future<void> requestExerciseHistory() async {
    // Check if we should throttle historical data requests
    if (_shouldThrottleHistoricalRequests('exercise')) {
      debugPrint('📊 ⏸️ Exercise history request throttled (too many recent requests)');
      return;
    }
    
    debugPrint('📊 Requesting exercise history using OFFICIAL command...');
    try {
      // Usa il comando ufficiale 0x16 dal SDK (getHistoryOfSport)
      var officialCommand = OfficialChileafCommands.getHistoryOfSport();
      
      debugPrint('🔍 Official exercise history command:');
      debugPrint('   Command: 0x16 (getHistoryOfSport from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
      await _sendCommand(officialCommand);
      
      // Update throttling counters
      _exerciseHistoryRequests++;
      _lastExerciseHistoryRequest = DateTime.now();
      debugPrint('✅ Official exercise history command sent');
    } catch (e) {
      debugPrint('❌ Failed to request exercise history with official command: $e');
    }
  }
  
  /// Richiede la lista degli storici della frequenza cardiaca
  Future<void> requestHRHistoryList() async {
    // Check if we should throttle historical data requests
    if (_shouldThrottleHistoricalRequests('hr')) {
      debugPrint('💓 ⏸️ HR history request throttled (too many recent requests)');
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
          final timeSinceLastRequest = now.difference(_lastExerciseHistoryRequest!);
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
    debugPrint('💓 Requesting HR history data for timestamp: $timestamp ($utcTimestamp)');
    try {
      List<int> command = CommandBuilder.buildHRHistoryDataRequest(utcTimestamp);
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request HR history data: $e');
    }
  }
  
  /// Richiede automaticamente i dati HR dettagliati per ogni timestamp nella lista
  Future<void> _requestDetailedHRData(HeartRateHistoryList hrHistoryList) async {
    debugPrint('💓 Auto-requesting detailed HR data for ${hrHistoryList.timestamps.length} timestamps');
    
    // Filter out obviously invalid timestamps to prevent infinite loops
    List<DateTime> validTimestamps = [];
    final now = DateTime.now();
    final earliestValid = DateTime(2020, 1, 1); // Nothing before 2020
    final latestValid = now.add(const Duration(days: 30)); // Nothing more than 30 days in the future
    
    for (var timestamp in hrHistoryList.timestamps) {
      if (timestamp.isAfter(earliestValid) && timestamp.isBefore(latestValid)) {
        validTimestamps.add(timestamp);
      }
      // SILENT - no logging for invalid timestamps to reduce spam
    }
    
    if (validTimestamps.isEmpty) {
      debugPrint('💓 ⚠️ No valid HR timestamps found (all outside range 2020-${latestValid.year}), skipping detailed requests');
      return;
    }
    
    // Count invalid timestamps for summary
    int invalidCount = hrHistoryList.timestamps.length - validTimestamps.length;
    if (invalidCount > 0) {
      debugPrint('💓 📊 Filtered out $invalidCount invalid timestamps (keeping ${validTimestamps.length} valid)');
    }
    
    // Limit to max 5 detailed requests to prevent spam
    const maxRequests = 5;
    final requestTimestamps = validTimestamps.take(maxRequests).toList();
    
    debugPrint('💓 Requesting detailed data for ${requestTimestamps.length}/${hrHistoryList.timestamps.length} valid timestamps');
    
    for (int i = 0; i < requestTimestamps.length; i++) {
      try {
        await Future.delayed(Duration(milliseconds: 500 * i)); // Longer delay between requests
        await requestHRHistoryData(requestTimestamps[i]);
      } catch (e) {
        debugPrint('❌ Failed to request HR data for timestamp ${requestTimestamps[i]}: $e');
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
    debugPrint('🗑️🧹 CLEARING ALL HISTORICAL DATA FROM DEVICE (OFFICIAL COMMAND)...');
    debugPrint('🔧 Using official SDK command 0xF3 (restoration)');
    
    try {
      // Usa il comando ufficiale 0xF3 dal SDK Android
      var officialCommand = OfficialChileafCommands.deviceReset();
      
      debugPrint('🔍 Official reset command details:');
      debugPrint('   Command: 0xF3 (Official Restoration/Reset from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   RX Characteristic: ${_rxCharacteristic?.uuid}');
      debugPrint('   Command valid: ${OfficialChileafCommands.isValidCommand(officialCommand)}');
      
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
        debugPrint('❌ TX characteristic not available for battery info request');
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
        debugPrint('❌ TX characteristic not available for firmware version request');
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
        debugPrint('❌ TX characteristic not available for hardware version request');
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
    if (_shouldThrottleHistoricalRequests('exercise') && _shouldThrottleHistoricalRequests('hr')) {
      debugPrint('📚 ⏸️ All historical data requests throttled (too many recent requests)');
      return;
    }
    
    try {
      // 1. Prima richiedi lo storico esercizi (se non throttled)
      if (!_shouldThrottleHistoricalRequests('exercise')) {
        await requestExerciseHistory();
        await Future.delayed(const Duration(milliseconds: 1000)); // Longer delay
      }
      
      // 2. Poi richiedi la lista HR (se non throttled)
      if (!_shouldThrottleHistoricalRequests('hr')) {
        await requestHRHistoryList();
        await Future.delayed(const Duration(milliseconds: 1000)); // Longer delay
      }
      
      // Nota: I dati HR specifici verranno richiesti quando arriva la lista (con filtri)
    } catch (e) {
      debugPrint('❌ Failed to request all historical data: $e');
    }
  }

  // ===== NUOVE FUNZIONI DAL REVERSE ENGINEERING =====

  /// Imposta informazioni utente usando comando ufficiale (0x04)
  /// Equivalente al metodo setUserInfo() del SDK Android
  Future<void> setUserInfo(int age, int sex, int weight, int height, int userId) async {
    debugPrint('👤 Setting user info using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.setUserInfo(age, sex, weight, height, userId);
      
      debugPrint('🔍 Official user info command:');
      debugPrint('   Command: 0x04 (setUserInfo from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   Data: age=$age, sex=$sex, weight=$weight, height=$height, userId=$userId');
      
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
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
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
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   UTC Timestamp: $currentUtc (${DateTime.fromMillisecondsSinceEpoch(currentUtc * 1000)})');
      
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
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
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
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
      await _sendCommand(officialCommand);
      debugPrint('✅ Official HR alarm status command sent');
    } catch (e) {
      debugPrint('❌ Failed to request HR alarm status with official command: $e');
    }
  }

  /// Richiede passi intervallari usando comando ufficiale (0x40)
  /// Equivalente al metodo getIntervalSteps() del SDK Android
  Future<void> requestIntervalSteps() async {
    debugPrint('👣 Requesting interval steps using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.getIntervalSteps();
      
      debugPrint('🔍 Official interval steps command:');
      debugPrint('   Command: 0x40 (getIntervalSteps from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
      await _sendCommand(officialCommand);
      debugPrint('✅ Official interval steps command sent');
    } catch (e) {
      debugPrint('❌ Failed to request interval steps with official command: $e');
    }
  }

  /// Spegne il dispositivo usando comando ufficiale (0xF1)
  /// Equivalente al metodo shutdown() del SDK Android
  Future<void> shutdownDevice() async {
    debugPrint('🔌 Shutting down device using OFFICIAL command...');
    try {
      var officialCommand = OfficialChileafCommands.deviceShutdown();
      
      debugPrint('🔍 Official shutdown command:');
      debugPrint('   Command: 0xF1 (shutdown from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   ⚠️  Device will power off after this command!');
      
      await _sendCommand(officialCommand);
      debugPrint('✅ Official shutdown command sent - device should power off');
    } catch (e) {
      debugPrint('❌ Failed to shutdown device with official command: $e');
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
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      
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
      List<int> command = [0xFF, 0x04, 0x38, 0x00, 0x3D]; // Temperature request command (sperimentale)
      
      debugPrint('🔍 Experimental temperature command:');
      debugPrint('   Command: 0x38 (experimental - not in official SDK)');
      debugPrint('   Frame: ${command.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
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
      debugPrint('   Command: 0x22 (getHistoryOfHRRecord from WearManager.java)');
      debugPrint('   Frame: ${OfficialChileafCommands.commandToHexString(officialCommand)}');
      debugPrint('   Note: RR intervals will be processed for HRV calculation');
      
      await _sendCommand(officialCommand);
      debugPrint('✅ HRV data request sent');
    } catch (e) {
      debugPrint('❌ Failed to request HRV data: $e');
      rethrow;
    }
  }
}
