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

// Data Processors
import 'services/data_processors/spo2_processor.dart';
import 'services/data_processors/temperature_processor.dart';
import 'services/data_processors/sports_processor.dart';
import 'services/data_processors/accelerometer_processor.dart';
import 'services/data_processors/health_processor.dart';
import 'services/data_processors/historical_data_processor.dart';

// Protocol & Commands
import 'services/ble_protocol/chileaf_protocol.dart';
import 'services/ble_protocol/command_builder.dart';

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
    debugPrint('🔄 _processIncomingData called with ${data.length} bytes');
    if (data.isEmpty) return;

    try {
      // Log frame details for debugging
      ChileafProtocol.logFrameDetails(data);

      // Handle different data formats
      if (ChileafProtocol.isValidChileafFrame(data)) {
        final command = ChileafProtocol.extractCommand(data);
        if (command == null) return;

        debugPrint('Processing command: ${ChileafProtocol.getCommandName(command)}');
        
        // 🎯 TARGETED SpO2 SEARCH: Only search in packets that actually contain SpO2 data
        if (ChileafProtocol.commandContainsSpO2Data(command)) {
          if (command == ChileafProtocol.commandHealthData) {
            debugPrint('🎯 Command 0x75: Searching for REAL SpO2 data');
            _spo2Processor.aggressiveSpO2Search(data);
          }
          _spo2Processor.enhancedSpO2Analysis(data);
        }
        
        // Route to appropriate processor
        _routeToProcessor(command, data);
      } else if (data.length >= 4) {
        // Try to detect data patterns without strict protocol
        _tryDetectDataPatterns(data);
      }
    } catch (e) {
      debugPrint('Error processing Chileaf data: $e');
    }
  }

  void _routeToProcessor(int command, List<int> data) {
    switch (command) {
      case ChileafProtocol.commandSports:
        _sportsProcessor.processSportsData(data);
        break;
      case ChileafProtocol.commandSpo2:
        debugPrint('🫁 RECEIVED SPO2 DATA! Processing...');
        _spo2Processor.processSPO2Data(data);
        break;
      case ChileafProtocol.commandTemperature:
        _temperatureProcessor.processTemperatureData(data);
        break;
      case ChileafProtocol.commandAccelerometer:
        debugPrint('📊 ACCELEROMETER DATA: Processing motion data (NOT SpO2)');
        _accelerometerProcessor.processAccelerometerData(data);
        break;
      case ChileafProtocol.commandHealthData:
        _healthProcessor.processHealthData(data);
        break;
      case 0x16: // Exercise History
        debugPrint('📊 EXERCISE HISTORY DATA: Processing historical exercise data');
        var exerciseHistory = HistoricalDataProcessor.processExerciseHistory(Uint8List.fromList(data));
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

  // Public SpO2 measurement methods
  Future<void> measureSpO2() async {
    if (_spo2Diagnostics != null) {
      await _spo2Diagnostics!.performSpO2Measurement();
    } else {
      throw Exception('SpO2 diagnostics not initialized');
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
    
    // Dispose all processors
    _spo2Processor.dispose();
    _temperatureProcessor.dispose();
    _sportsProcessor.dispose();
    _healthProcessor.dispose();
  }

  // === Historical Data Methods ===
  
  /// Richiede lo storico degli esercizi degli ultimi 7 giorni
  Future<void> requestExerciseHistory() async {
    debugPrint('📊 Requesting 7 days exercise history...');
    try {
      List<int> command = CommandBuilder.buildExerciseHistoryRequest();
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request exercise history: $e');
    }
  }
  
  /// Richiede la lista degli storici della frequenza cardiaca
  Future<void> requestHRHistoryList() async {
    debugPrint('💓 Requesting HR history list...');
    try {
      List<int> command = CommandBuilder.buildHRHistoryListRequest();
      await _sendCommand(command);
    } catch (e) {
      debugPrint('❌ Failed to request HR history list: $e');
    }
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
    
    for (int i = 0; i < hrHistoryList.timestamps.length; i++) {
      try {
        await Future.delayed(Duration(milliseconds: 300 * i)); // Delay between requests
        await requestHRHistoryData(hrHistoryList.timestamps[i]);
      } catch (e) {
        debugPrint('❌ Failed to request HR data for timestamp ${hrHistoryList.timestamps[i]}: $e');
      }
    }
  }

  /// Richiede tutti i dati storici disponibili (sequenza completa)
  Future<void> requestAllHistoricalData() async {
    debugPrint('📚 Requesting all historical data...');
    try {
      // 1. Prima richiedi lo storico esercizi
      await requestExerciseHistory();
      await Future.delayed(const Duration(milliseconds: 500));
      
      // 2. Poi richiedi la lista HR
      await requestHRHistoryList();
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Nota: I dati HR specifici verranno richiesti quando arriva la lista
    } catch (e) {
      debugPrint('❌ Failed to request all historical data: $e');
    }
  }
}
