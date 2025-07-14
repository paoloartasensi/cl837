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

  // Statistics tracking
  int _accelerometerPacketCount = 0;
  DateTime? _lastAccelerometerSummary;

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

    // Set up periodic data requests (reduced frequency to prevent device overload)
    _dataRequestTimer = Timer.periodic(const Duration(seconds: 30), (timer) async {
      try {
        debugPrint('📡 Periodic request: Temperature + Sports data (30s interval)');
        await _sendCommand(CommandBuilder.buildTemperatureDataRequest());
        await Future.delayed(const Duration(milliseconds: 1000));
        await _sendCommand(CommandBuilder.buildSportsDataRequest());
      } catch (e) {
        debugPrint('Error in periodic data request: $e');
      }
    });
  }

  void _processIncomingData(List<int> data) {
    if (data.isEmpty) return;

    try {
      // Handle different data formats
      if (ChileafProtocol.isValidChileafFrame(data)) {
        final command = ChileafProtocol.extractCommand(data);
        if (command == null) return;

        // Only log non-accelerometer data to reduce noise
        if (command != ChileafProtocol.commandAccelerometer) {
          debugPrint('🔄 Processing ${ChileafProtocol.getCommandName(command)} (${data.length} bytes)');
          ChileafProtocol.logFrameDetails(data);
        }
        
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
        _sportsProcessor.processSportsData(data);
        
        // Auto-save to diary if enabled (rimuovi per ora)
        // if (_autoSaveEnabled && data.length >= 13) {
        //   _saveSportsDataToDiary(sportsData);
        // }
        break;
      case ChileafProtocol.commandSpo2:
        debugPrint('🫁 RECEIVED SPO2 DATA! Processing...');
        _spo2Processor.processSPO2Data(data);
        break;
      case ChileafProtocol.commandTemperature:
        _temperatureProcessor.processTemperatureData(data);
        break;
      case ChileafProtocol.commandAccelerometer:
        // Process accelerometer data quietly (high frequency data)
        _accelerometerProcessor.processAccelerometerData(data);
        
        // Track packets and show periodic summary
        _accelerometerPacketCount++;
        final now = DateTime.now();
        if (_lastAccelerometerSummary == null || 
            now.difference(_lastAccelerometerSummary!).inSeconds >= 10) {
          debugPrint('📊 Accelerometer: $_accelerometerPacketCount packets in last 10s');
          _accelerometerPacketCount = 0;
          _lastAccelerometerSummary = now;
        }
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
        debugPrint('💓📋 HR HISTORY LIST: Processing HR timestamp list (${data.length} bytes)');
        var hrHistoryList = HistoricalDataProcessor.processHRHistoryList(Uint8List.fromList(data));
        debugPrint('💓📋 HR History List processed: ${hrHistoryList.timestamps.length} timestamps');
        _hrHistoryListController.add(hrHistoryList);
        
        // Auto-request detailed data for each timestamp
        debugPrint('💓🔄 Auto-requesting detailed HR data...');
        _requestDetailedHRData(hrHistoryList);
        break;
      case 0x22: // HR History Data
        debugPrint('💓📊 HR HISTORY DATA: Processing detailed HR historical data (${data.length} bytes)');
        var hrHistoryData = HistoricalDataProcessor.processHRHistoryData(Uint8List.fromList(data));
        if (hrHistoryData != null) {
          debugPrint('💓📊 HR History Data processed: ${hrHistoryData.entries.length} HR values');
          _hrHistoryDataController.add(hrHistoryData);
        } else {
          debugPrint('💓❌ Failed to process HR History Data');
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
    debugPrint('💓🔄 requestHRHistoryList() called');
    try {
      List<int> command = CommandBuilder.buildHRHistoryListRequest();
      debugPrint('💓📤 Sending HR history list command: $command');
      await _sendCommand(command);
      debugPrint('💓✅ HR history list command sent successfully');
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
    
    // Limit requests to avoid overwhelming the device
    int maxRequests = 5; // Only request first 5 valid timestamps
    int requestCount = 0;
    
    for (int i = 0; i < hrHistoryList.timestamps.length && requestCount < maxRequests; i++) {
      try {
        DateTime timestamp = hrHistoryList.timestamps[i];
        
        // Skip timestamps that are clearly corrupted
        if (timestamp.year < 2020 || timestamp.year > 2030) {
          debugPrint('💓⚠️ Skipping corrupted timestamp: $timestamp');
          continue;
        }
        
        await Future.delayed(Duration(milliseconds: 500 * requestCount)); // Longer delay between requests
        await requestHRHistoryData(timestamp);
        requestCount++;
        debugPrint('💓 Requested HR data for timestamp $requestCount/$maxRequests: $timestamp');
      } catch (e) {
        debugPrint('❌ Failed to request HR data for timestamp $i: $e');
      }
    }
    
    if (requestCount == 0) {
      debugPrint('💓⚠️ No valid timestamps found - all timestamps appear to be corrupted');
    } else {
      debugPrint('💓✅ Requested HR data for $requestCount valid timestamps (limited to reduce device load)');
    }
  }

  // SpO2 Methods (CL837 Protocol v0.6 - Command 0x37)
  
  /// Abilita modalità SpO2 (0x37, 0x01)
  Future<void> enableSpO2Mode() async {
    debugPrint('🫁📡 Enabling SpO2 Mode...');
    try {
      List<int> command = CommandBuilder.buildEnableSpO2Mode();
      await _sendCommand(command);
      debugPrint('🫁✅ SpO2 mode enabled successfully');
    } catch (e) {
      debugPrint('❌ Failed to enable SpO2 mode: $e');
    }
  }

  /// Disabilita modalità SpO2 (0x37, 0x00)
  Future<void> disableSpO2Mode() async {
    debugPrint('🫁📡 Disabling SpO2 Mode...');
    try {
      List<int> command = CommandBuilder.buildDisableSpO2Mode();
      await _sendCommand(command);
      debugPrint('🫁✅ SpO2 mode disabled successfully');
    } catch (e) {
      debugPrint('❌ Failed to disable SpO2 mode: $e');
    }
  }

  /// Richiede stato SpO2 (0x37, 0x02)
  Future<void> inquireSpO2Status() async {
    debugPrint('🫁📡 Inquiring SpO2 Status...');
    try {
      List<int> command = CommandBuilder.buildSpO2StatusInquiry();
      await _sendCommand(command);
      debugPrint('🫁✅ SpO2 status inquiry sent successfully');
    } catch (e) {
      debugPrint('❌ Failed to inquire SpO2 status: $e');
    }
  }

  // Convenience methods for bulk requests
  
  /// Richiede tutte le informazioni del dispositivo
  Future<void> requestAllDeviceInfo() async {
    debugPrint('📱 Requesting all device information...');
    await requestDeviceInfo();
    await requestBatteryLevel();
    await requestFirmwareVersion();
    await requestHardwareVersion();
    await requestDeviceName();
    await requestMacAddress();
  }

  /// Richiede tutti i dati storici disponibili
  Future<void> requestAllHistoricalData() async {
    debugPrint('📊 Requesting all historical data...');
    await requestExerciseHistory();
    await requestHRHistoryList();
  }

  // Device Info Request Methods
  
  /// Richiede informazioni del dispositivo
  Future<void> requestDeviceInfo() async {
    debugPrint('📱 Requesting device info...');
    try {
      List<int> command = CommandBuilder.buildDeviceInfoCommand();
      await _sendCommand(command);
      debugPrint('📱✅ Device info request sent');
    } catch (e) {
      debugPrint('❌ Failed to request device info: $e');
    }
  }

  /// Richiede livello batteria
  Future<void> requestBatteryLevel() async {
    debugPrint('🔋 Requesting battery level...');
    try {
      List<int> command = CommandBuilder.buildBatteryLevelCommand();
      await _sendCommand(command);
      debugPrint('🔋✅ Battery level request sent');
    } catch (e) {
      debugPrint('❌ Failed to request battery level: $e');
    }
  }

  /// Richiede versione firmware
  Future<void> requestFirmwareVersion() async {
    debugPrint('⚡ Requesting firmware version...');
    try {
      List<int> command = CommandBuilder.buildFirmwareVersionCommand();
      await _sendCommand(command);
      debugPrint('⚡✅ Firmware version request sent');
    } catch (e) {
      debugPrint('❌ Failed to request firmware version: $e');
    }
  }

  /// Richiede versione hardware
  Future<void> requestHardwareVersion() async {
    debugPrint('🔧 Requesting hardware version...');
    try {
      List<int> command = CommandBuilder.buildHardwareVersionCommand();
      await _sendCommand(command);
      debugPrint('🔧✅ Hardware version request sent');
    } catch (e) {
      debugPrint('❌ Failed to request hardware version: $e');
    }
  }

  /// Richiede nome dispositivo
  Future<void> requestDeviceName() async {
    debugPrint('🏷️ Requesting device name...');
    try {
      List<int> command = CommandBuilder.buildDeviceNameCommand();
      await _sendCommand(command);
      debugPrint('🏷️✅ Device name request sent');
    } catch (e) {
      debugPrint('❌ Failed to request device name: $e');
    }
  }

  /// Richiede indirizzo MAC
  Future<void> requestMacAddress() async {
    debugPrint('🆔 Requesting MAC address...');
    try {
      List<int> command = CommandBuilder.buildMacAddressCommand();
      await _sendCommand(command);
      debugPrint('🆔✅ MAC address request sent');
    } catch (e) {
      debugPrint('❌ Failed to request MAC address: $e');
    }
  }

  // Rope Skipping Methods (CL837 Protocol v0.6)
  
  /// Imposta modalità rope skipping (0x42)
  Future<void> setRopeMode(int mode) async {
    debugPrint('🪢📡 Setting rope mode to: $mode');
    try {
      List<int> command = CommandBuilder.buildSetRopeModeCommand(mode);
      await _sendCommand(command);
      debugPrint('🪢✅ Rope mode set successfully');
    } catch (e) {
      debugPrint('❌ Failed to set rope mode: $e');
    }
  }

  /// Cancella dati rope skipping (0x45)
  Future<void> clearRopeData() async {
    debugPrint('🪢🗑️ Clearing rope data...');
    try {
      List<int> command = CommandBuilder.buildClearRopeDataCommand();
      await _sendCommand(command);
      debugPrint('🪢✅ Rope data cleared successfully');
    } catch (e) {
      debugPrint('❌ Failed to clear rope data: $e');
    }
  }

  /// Richiede stato rope skipping (0x40)
  Future<void> requestRopeStatus() async {
    debugPrint('🪢📡 Requesting rope status...');
    try {
      List<int> command = CommandBuilder.buildRopeStatusCommand();
      await _sendCommand(command);
      debugPrint('🪢✅ Rope status request sent');
    } catch (e) {
      debugPrint('❌ Failed to request rope status: $e');
    }
  }

  /// Temporarily pause periodic requests to reduce device load
  void pausePeriodicRequests() {
    debugPrint('⏸️ Pausing periodic requests to reduce device load...');
    _dataRequestTimer?.cancel();
    _dataRequestTimer = null;
  }
  
  /// Resume periodic requests after a pause
  void resumePeriodicRequests() {
    if (_dataRequestTimer != null) return; // Already running
    
    debugPrint('▶️ Resuming periodic requests...');
    _dataRequestTimer = Timer.periodic(const Duration(seconds: 30), (timer) async {
      try {
        debugPrint('📡 Periodic request: Temperature + Sports data (30s interval)');
        await _sendCommand(CommandBuilder.buildTemperatureDataRequest());
        await Future.delayed(const Duration(milliseconds: 1000));
        await _sendCommand(CommandBuilder.buildSportsDataRequest());
      } catch (e) {
        debugPrint('Error in periodic data request: $e');
      }
    });
  }

  // Manual request methods for accuracy testing
  Future<void> requestTemperatureData() async {
    debugPrint('🌡️ Manual request: Temperature data');
    await _sendCommand(CommandBuilder.buildTemperatureDataRequest());
  }
  
  Future<void> requestSportsData() async {
    debugPrint('🏃 Manual request: Sports data');
    await _sendCommand(CommandBuilder.buildSportsDataRequest());
  }
}
