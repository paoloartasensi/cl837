import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'models/sports_data.dart';
import 'models/spo2_data.dart';
import 'models/temperature_data.dart';
import 'models/hrv_data.dart';
import 'models/heart_rate_data.dart';

class ChileafExtendedService {
  // Chileaf Custom Service & Characteristics (from SDK documentation)
  static const String _customServiceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _txCharUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0'; // Read from device (NOTIFY)
  static const String _rxCharUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0'; // Write to device (WRITE)

  // Command codes from Chileaf BLE Protocol SDK v0.6
  static const int _commandSpo2 = 0x37;
  static const int _commandTemperature = 0x38;
  static const int _commandSports = 0x15; // Real-time sports data notification
  static const int _commandHealthData = 0x75; // Extended health data (discovered from logs)
  static const int _commandAccelerometer = 0x0C; // High-frequency accelerometer/motion data

  // Stream controllers for each data type
  final _sportsDataController = StreamController<SportsData>.broadcast();
  final _spo2DataController = StreamController<SpO2Data>.broadcast();
  final _temperatureDataController = StreamController<TemperatureData>.broadcast();
  final _hrvDataController = StreamController<HRVData>.broadcast();

  // Bluetooth characteristics
  BluetoothCharacteristic? _txCharacteristic;
  BluetoothCharacteristic? _rxCharacteristic;
  StreamSubscription? _dataSubscription;
  Timer? _dataRequestTimer;
  Timer? _spo2Timer; // Separate timer for SpO2 with longer intervals

  // RR intervals buffer for HRV calculation
  List<double> _rrIntervalsBuffer = [];
  static const int _maxRRIntervals = 30; // Store last 30 intervals for HRV
  
  // Accelerometer data analysis
  int _accelerometerLogCounter = 0;
  static const int _accelerometerLogInterval = 100; // Log every 100th packet

  // Public streams
  Stream<SportsData> get sportsDataStream => _sportsDataController.stream;
  Stream<SpO2Data> get spo2DataStream => _spo2DataController.stream;
  Stream<TemperatureData> get temperatureDataStream => _temperatureDataController.stream;
  Stream<HRVData> get hrvDataStream => _hrvDataController.stream;

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
      // Non attiviamo SpO2 all'avvio, ma solo on-demand per evitare che il LED rimanga acceso
      // await _enableSPO2Mode();
      await Future.delayed(const Duration(milliseconds: 500));
      await _requestTemperatureData();
      await Future.delayed(const Duration(milliseconds: 500));
      await _requestSportsData();

      // Set up periodic data requests - frequent for temperature and sports
      _dataRequestTimer = Timer.periodic(const Duration(seconds: 5), (timer) async {
        try {
          await _requestTemperatureData();
          await Future.delayed(const Duration(milliseconds: 300));
          await _requestSportsData();
        } catch (e) {
          debugPrint('Error in periodic data request: $e');
        }
      });

      // SpO2 misurazione ora è on-demand tramite measureSpO2() method
      // Rimuovo il timer automatico per una migliore UX

      debugPrint('Chileaf Extended Service started successfully');
    } catch (e) {
      debugPrint('Failed to start Chileaf Extended Service: $e');
      // Don't rethrow - let the app continue without extended features
    }
  }

  void _processIncomingData(List<int> data) {
    debugPrint('🔄 _processIncomingData called with ${data.length} bytes'); // CRITICAL DEBUG
    if (data.isEmpty) return;

    try {
      // Debug: log all data for SPO2/temperature debugging
      debugPrint('Extended service data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');

      // SPECIAL CHECK: Look for ANY data that could be SpO2 response
      _checkForSpO2Response(data);

      // Handle different data formats
      if (data[0] == 0xFF && data.length >= 3) {
        // Standard Chileaf protocol
        final command = data[2];
        debugPrint('Chileaf command: 0x${command.toRadixString(16).padLeft(2, '0')}');
        
        switch (command) {
          case _commandSports: // 0x15 - Real-time sports data
            _processSportsData(data);
            break;
          case _commandSpo2: // 0x37 - SPO2 data
            debugPrint('🫁 RECEIVED SPO2 DATA! Processing...');
            _processSPO2Data(data);
            break;
          case _commandTemperature: // 0x38 - Temperature data
            _processTemperatureData(data);
            break;
          case _commandAccelerometer: // 0x0C - High-frequency accelerometer/motion data
            _processAccelerometerData(data);
            break;
          case _commandHealthData: // 0x75 - Extended health data (discovered)
            _processHealthData(data);
            break;
          default:
            debugPrint('Unhandled Chileaf command: 0x${command.toRadixString(16)} (${data.length} bytes)');
            // Check if this could be a SpO2 response in a different format
            if (data.length >= 7) {
              debugPrint('🔍 Checking if this could be SpO2 data in different format...');
              final possibleSpo2 = data[3];
              if (possibleSpo2 >= 70 && possibleSpo2 <= 100) {
                debugPrint('🫁 Possible SpO2 value detected: $possibleSpo2%');
              }
            }
        }
      } else if (data.length >= 4) {
        // Try to detect data patterns without strict protocol
        _tryDetectDataPatterns(data);
        
        // Also aggressively check for SpO2 responses
        _checkForSpO2Response(data);
      }
    } catch (e) {
      debugPrint('Error processing Chileaf data: $e');
    }
  }

  // Aggressively check for SpO2 responses in any incoming data
  void _checkForSpO2Response(List<int> data) {
    // Scan all bytes in the data for possible SpO2 values
    for (int i = 0; i < data.length; i++) {
      final possibleSpO2 = data[i];
      
      // SpO2 values are typically between 70-100%
      if (possibleSpO2 >= 70 && possibleSpO2 <= 100) {
        debugPrint('🔍 POSSIBLE SpO2 value detected at position $i: $possibleSpO2%');
        
        // Try to extract additional context if available
        bool hasPosture = (i + 1 < data.length);
        bool hasSignal = (i + 2 < data.length);
        bool hasWearing = (i + 3 < data.length);
        
        final spo2Data = SpO2Data(
          spo2Value: possibleSpO2,
          correctWristPosture: hasPosture ? data[i + 1] == 1 : true,
          signalQuality: hasSignal ? data[i + 2] : 50,
          isWearing: hasWearing ? data[i + 3] == 1 : true,
        );
        
        _spo2DataController.add(spo2Data);
        debugPrint('🫁 Pushed possible SpO2 data to UI: $possibleSpO2%');
        
        // Don't check further to avoid false positives
        break;
      }
    }
  }

  void _processSportsData(List<int> data) {
    // Real-time sports data notification-0x15 from SDK:
    // Bytes 3-5: Steps (3-byte value)
    // Bytes 6-8: Distance in cm (3-byte value)  
    // Bytes 9-11: Calories in 0.1 kcal units (3-byte value)
    
    if (data.length < 12) {
      debugPrint('Sports data too short: ${data.length} bytes');
      return;
    }

    try {
      final steps = (data[3] << 16) | (data[4] << 8) | data[5];
      final distanceCm = ((data[6] << 16) | (data[7] << 8) | data[8]).toDouble();
      final caloriesRaw = (data[9] << 16) | (data[10] << 8) | data[11];
      final caloriesKcal = caloriesRaw * 0.1; // Convert from 0.1 kcal units

      final sportsData = SportsData(
        steps: steps,
        distanceCm: distanceCm,
        caloriesKcal: caloriesKcal,
      );

      _sportsDataController.add(sportsData);
      debugPrint('Sports data: steps=$steps, distance=${distanceCm}cm, calories=${caloriesKcal}kcal');
    } catch (e) {
      debugPrint('Error parsing sports data: $e');
    }
  }

  void _processSPO2Data(List<int> data) {
    // SPO2 Mode-0x37 response format from SDK:
    // Byte 3: SPO2 value
    // Byte 4: 0=wrist posture wrong, 1=wrist posture correct (face up)
    // Byte 5: 0=no signal, <8=signal weak, >15=signal good
    // Byte 6: 0=not wear, 1=wear
    
    if (data.length < 7) {
      debugPrint('SPO2 data too short: ${data.length} bytes');
      return;
    }

    try {
      final spo2Value = data[3];
      final correctPosture = data[4] == 1;
      final signalQuality = data[5];
      final isWearing = data[6] == 1;

      debugPrint('SPO2 raw: value=$spo2Value, posture=$correctPosture, signal=$signalQuality, wearing=$isWearing');

      // SpO2 requires time to stabilize - only accept readings with good conditions
      // Similar to Elite HRV approach: wait for proper conditions before showing data
      if (isWearing && correctPosture && signalQuality >= 8 && spo2Value >= 70 && spo2Value <= 100) {
        final spo2Data = SpO2Data(
          spo2Value: spo2Value,
          correctWristPosture: correctPosture,
          signalQuality: signalQuality,
          isWearing: isWearing,
        );
        _spo2DataController.add(spo2Data);
        debugPrint('✅ Valid SpO2 Data: $spo2Value%, signal: $signalQuality');
      } else {
        // Still send data but mark as invalid for UI feedback
        final spo2Data = SpO2Data(
          spo2Value: spo2Value,
          correctWristPosture: correctPosture,
          signalQuality: signalQuality,
          isWearing: isWearing,
        );
        _spo2DataController.add(spo2Data);
        
        String reason = '';
        if (!isWearing) reason += 'Not wearing device. ';
        if (!correctPosture) reason += 'Wrong wrist posture (turn face up). ';
        if (signalQuality < 8) reason += 'Weak signal (stay still). ';
        if (spo2Value < 70 || spo2Value > 100) reason += 'Reading stabilizing. ';
        
        debugPrint('⚠️ SpO2 needs adjustment: $reason');
      }
    } catch (e) {
      debugPrint('Error parsing SPO2 data: $e');
    }
  }

  void _processTemperatureData(List<int> data) {
    // Temperature-0x38 response format from SDK:
    // Byte 3-4: Ambient temperature (MSB first, unit: *10°C)
    // Byte 5-6: Wrist temperature (MSB first, unit: *10°C)  
    // Byte 7-8: Body temperature (MSB first, unit: *10°C)
    
    if (data.length < 9) {
      debugPrint('Temperature data too short: ${data.length} bytes');
      return;
    }

    try {
      // Parse temperatures (MSB first, divide by 10 for actual °C)
      final ambientTempRaw = (data[3] << 8) | data[4];
      final wristTempRaw = (data[5] << 8) | data[6];
      final bodyTempRaw = (data[7] << 8) | data[8];
      
      final ambientTemp = ambientTempRaw / 10.0;
      final wristTemp = wristTempRaw / 10.0;
      final bodyTemp = bodyTempRaw / 10.0;
      
      debugPrint('Temperature raw: ambient=$ambientTempRaw ($ambientTemp°C), wrist=$wristTempRaw ($wristTemp°C), body=$bodyTempRaw ($bodyTemp°C)');

      // Temperature readings are generally stable, send all valid readings
      // Similar to professional medical devices: continuous monitoring approach
      if (ambientTemp >= 10 && ambientTemp <= 50 && 
          wristTemp >= 20 && wristTemp <= 45 &&
          bodyTemp >= 30 && bodyTemp <= 45) {
        final temperatureData = TemperatureData(
          ambientTempC: ambientTemp,
          wristTempC: wristTemp,
          bodyTempC: bodyTemp,
        );
        _temperatureDataController.add(temperatureData);
        debugPrint('✅ Temperature Data: ambient: $ambientTemp°C, wrist: $wristTemp°C, body: $bodyTemp°C');
      } else {
        debugPrint('⚠️ Temperature readings out of expected range');
      }
    } catch (e) {
      debugPrint('Error parsing temperature data: $e');
    }
  }

  void _processHealthData(List<int> data) {
    // Extended health data from command 0x75 (discovered from logs)
    // 23-byte packets with health metrics
    if (data.length < 10) {
      debugPrint('Health data too short: ${data.length} bytes');
      return;
    }

    try {
      debugPrint('🏥 Extended health data (${data.length} bytes): ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // This could contain additional metrics like:
      // - Detailed heart rate variability
      // - Sleep analysis data
      // - Stress levels
      // - Additional sensor readings
      
      // For now, just log for analysis
      // Future: parse specific health metrics based on protocol documentation
    } catch (e) {
      debugPrint('Error parsing health data: $e');
    }
  }

  void _tryDetectDataPatterns(List<int> data) {
    // Fallback pattern detection for devices not following strict protocol
    debugPrint('Trying pattern detection on ${data.length} bytes: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    // Try to detect temperature data patterns
    if (data.length >= 6) {
      final possibleTemp1 = ((data[0] << 8) | data[1]) / 10.0;
      final possibleTemp2 = ((data[2] << 8) | data[3]) / 10.0;
      final possibleTemp3 = ((data[4] << 8) | data[5]) / 10.0;
      
      if (possibleTemp1 >= 10 && possibleTemp1 <= 50 && 
          possibleTemp2 >= 10 && possibleTemp2 <= 50 && 
          possibleTemp3 >= 10 && possibleTemp3 <= 50) {
        final temperatureData = TemperatureData(
          ambientTempC: possibleTemp1,
          wristTempC: possibleTemp2,
          bodyTempC: possibleTemp3,
        );
        _temperatureDataController.add(temperatureData);
        debugPrint('Detected temperature pattern: $temperatureData');
        return;
      }
    }
    
    // Try to detect SpO2 data patterns
    if (data.length >= 4) {
      final possibleSpO2 = data[0];
      if (possibleSpO2 >= 70 && possibleSpO2 <= 100) {
        final spo2Data = SpO2Data(
          spo2Value: possibleSpO2,
          correctWristPosture: data.length > 1 ? data[1] == 1 : true,
          signalQuality: data.length > 2 ? data[2] : 100,
          isWearing: data.length > 3 ? data[3] == 1 : true,
        );
        _spo2DataController.add(spo2Data);
        debugPrint('Detected SpO2 pattern: $spo2Data');
        return;
      }
    }
  }

  // Process RR intervals from heart rate data for HRV calculation
  void processRRIntervalsForHRV(HeartRateData heartRateData) {
    if (heartRateData.rrIntervals != null && heartRateData.rrIntervals!.isNotEmpty) {
      _rrIntervalsBuffer.addAll(heartRateData.rrIntervals!);
      
      // Keep only the most recent intervals
      if (_rrIntervalsBuffer.length > _maxRRIntervals) {
        _rrIntervalsBuffer = _rrIntervalsBuffer.sublist(_rrIntervalsBuffer.length - _maxRRIntervals);
      }

      // Calculate HRV if we have enough data points (at least 10 intervals)
      if (_rrIntervalsBuffer.length >= 10) {
        final hrvData = HRVData(rrIntervals: List.from(_rrIntervalsBuffer));
        _hrvDataController.add(hrvData);
        debugPrint('HRV calculated from ${_rrIntervalsBuffer.length} RR intervals');
      }
    }
  }

  // Protocol frame helpers
  List<int> _buildProtocolFrame(List<int> data) {
    // Chileaf protocol: [0xFF, Length, Data..., Checksum]
    final frame = [0xFF, data.length + 1, ...data];
    final checksum = _calculateChecksum(data);
    frame.add(checksum);
    return frame;
  }

  int _calculateChecksum(List<int> data) {
    // Calculate checksum according to SDK
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    int temp = sum & 0xFF;
    temp = (0 - temp) & 0xFF;
    temp ^= 0x3A;
    
    return temp & 0xFF;
  }

  Future<void> _sendCommand(List<int> command) async {
    if (_rxCharacteristic == null) {
      const error = 'RX characteristic not available, command not sent';
      debugPrint('❌ $error');
      throw Exception(error);
    }

    // Check if the characteristic is writable
    if (!_rxCharacteristic!.properties.write && !_rxCharacteristic!.properties.writeWithoutResponse) {
      final error = 'RX characteristic not writable: ${_rxCharacteristic!.properties}';
      debugPrint('❌ $error');
      throw Exception(error);
    }

    try {
      final frame = _buildProtocolFrame(command);
      debugPrint('📡 Sending command frame: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      debugPrint('📡 RX Characteristic UUID: ${_rxCharacteristic!.uuid}');
      debugPrint('📡 RX Characteristic Properties: ${_rxCharacteristic!.properties}');
      
      // Try to write with proper error handling and timeout
      bool success = false;
      try {
        if (_rxCharacteristic!.properties.writeWithoutResponse) {
          await _rxCharacteristic!.write(frame, withoutResponse: true);
          debugPrint('📡 Sent with writeWithoutResponse');
        } else {
          await _rxCharacteristic!.write(frame, withoutResponse: false);
          debugPrint('📡 Sent with write (with response)');
        }
        success = true;
      } catch (writeError) {
        debugPrint('❌ BLE Write error: $writeError');
        throw Exception('BLE write failed: $writeError');
      }
      
      if (success) {
        debugPrint('✅ Command sent successfully');
        // Add a small delay to let the device process the command
        await Future.delayed(const Duration(milliseconds: 100));
      }
    } catch (e) {
      debugPrint('❌ Error sending command: $e');
      throw Exception('Command sending failed: $e');
    }
  }

  Future<void> _requestSportsData() async {
    // Command 0x15 for real-time sports data
    await _sendCommand([_commandSports]);
  }

  Future<void> _requestTemperatureData() async {
    // Command 0x38 for temperature data
    await _sendCommand([_commandTemperature]);
  }

  Future<void> _enableSPO2Mode() async {
    // Command 0x37 with parameter 1 to enter SPO2 mode
    debugPrint('🔍 Sending SPO2 mode ENABLE command: [0x${_commandSpo2.toRadixString(16)}, 0x01]');
    try {
      await _sendCommand([_commandSpo2, 0x01]);
      debugPrint('✅ SPO2 mode ENABLE command sent successfully');
    } catch (e) {
      debugPrint('❌ SPO2 mode ENABLE command FAILED: $e');
      rethrow;
    }
  }

  Future<void> exitSPO2Mode() async {
    // Command 0x37 with parameter 0 to exit SPO2 mode
    debugPrint('🔍 Sending SPO2 mode EXIT command: [0x${_commandSpo2.toRadixString(16)}, 0x00]');
    try {
      await _sendCommand([_commandSpo2, 0x00]);
      debugPrint('✅ SPO2 mode EXIT command sent successfully');
    } catch (e) {
      debugPrint('❌ SPO2 mode EXIT command FAILED: $e');
      rethrow;
    }
  }

  // Emergency method to force exit SpO2 mode if device gets stuck
  Future<void> forceExitSpO2Mode() async {
    try {
      debugPrint('🚨 Force exiting SpO2 mode...');
      
      // Try multiple times to ensure exit
      for (int i = 0; i < 3; i++) {
        await exitSPO2Mode();
        await Future.delayed(const Duration(milliseconds: 500));
        debugPrint('🚨 Exit attempt ${i + 1}/3');
      }
      
      debugPrint('🚨 Force exit completed - LED should be OFF');
    } catch (e) {
      debugPrint('🚨 Force exit error: $e');
    }
  }

  Future<void> inquireSPO2Status() async {
    // Command 0x37 with parameter 2 to inquire status
    debugPrint('🔍 Sending SPO2 status INQUIRY command: [0x${_commandSpo2.toRadixString(16)}, 0x02]');
    try {
      await _sendCommand([_commandSpo2, 0x02]);
      debugPrint('✅ SPO2 status INQUIRY command sent successfully');
    } catch (e) {
      debugPrint('❌ SPO2 status INQUIRY command FAILED: $e');
      rethrow;
    }
  }

  // Public method for on-demand SpO2 measurement
  Future<void> measureSpO2() async {
    try {
      debugPrint('🫁 Starting on-demand SpO2 measurement...');
      
      // Step 0: First ensure we're NOT in SpO2 mode
      await exitSPO2Mode();
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Step 1: Enter SpO2 mode (LED rosso si accende)
      await _enableSPO2Mode();
      debugPrint('🫁 SpO2 mode enabled, LED should be RED, stabilizing...');
      
      // Step 2: Wait for stabilization (important for accurate reading)
      debugPrint('🫁 Waiting 4 seconds for stabilization...');
      await Future.delayed(const Duration(milliseconds: 4000));
      
      // Step 3: Request SpO2 status multiple times for better accuracy
      debugPrint('🫁 Phase 1: Requesting SpO2 status...');
      await inquireSPO2Status();
      await Future.delayed(const Duration(milliseconds: 2000)); // Increased delay
      
      debugPrint('🫁 Phase 2: Second SpO2 inquiry...');
      await inquireSPO2Status();
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🫁 Phase 3: Final SpO2 inquiry...');
      await inquireSPO2Status();
      await Future.delayed(const Duration(milliseconds: 3000)); // Longer wait for final reading
      
      debugPrint('🫁 SpO2 measurement requests sent, checking for responses...');
      
      // Step 4: IMPORTANTE - Exit SpO2 mode per spegnere LED
      debugPrint('🫁 Exiting SpO2 mode...');
      await exitSPO2Mode();
      debugPrint('🫁 SpO2 mode exited, LED should turn OFF');
      
    } catch (e) {
      debugPrint('🫁 Error in SpO2 measurement: $e');
      // In caso di errore, assicuriamoci comunque di uscire dalla modalità SpO2
      try {
        await exitSPO2Mode();
        debugPrint('🫁 Emergency SpO2 mode exit completed');
      } catch (exitError) {
        debugPrint('🫁 Failed to exit SpO2 mode: $exitError');
      }
      rethrow;
    }
  }

  // Alternative SpO2 command method (try different formats)
  Future<void> measureSpO2Alternative() async {
    try {
      debugPrint('🧪 Trying alternative SpO2 command formats...');
      
      // Method 1: Try simple 0x37 command without parameters
      debugPrint('🧪 Method 1: Simple 0x37 command');
      await _sendCommand([_commandSpo2]);
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Method 2: Try different parameter values
      debugPrint('🧪 Method 2: 0x37 with different parameters');
      for (int param in [0x01, 0x02, 0x03, 0xFF]) {
        debugPrint('🧪 Trying parameter: 0x${param.toRadixString(16)}');
        await _sendCommand([_commandSpo2, param]);
        await Future.delayed(const Duration(milliseconds: 1500));
      }
      
      // Method 3: Try raw command without protocol frame
      debugPrint('🧪 Method 3: Raw command without frame');
      if (_rxCharacteristic != null) {
        try {
          await _rxCharacteristic!.write([0x37, 0x01]);
          debugPrint('🧪 Raw command sent');
          await Future.delayed(const Duration(milliseconds: 2000));
        } catch (e) {
          debugPrint('🧪 Raw command failed: $e');
        }
      }
      
      debugPrint('🧪 Alternative methods completed');
      
    } catch (e) {
      debugPrint('🧪 Alternative SpO2 methods error: $e');
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
    _rrIntervalsBuffer.clear();
    
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
    _sportsDataController.close();
    _spo2DataController.close();
    _temperatureDataController.close();
    _hrvDataController.close();
  }

  void _processAccelerometerData(List<int> data) {
    // Command 0x0C - High-frequency accelerometer/motion data
    // Format: 0xff 0x0a 0x0c [motion data bytes] 0x0e [checksum]
    // Length: 10 bytes total
    
    _accelerometerLogCounter++;
    
    // Only log occasionally to reduce spam, but still analyze data patterns
    if (_accelerometerLogCounter % _accelerometerLogInterval == 0) {
      debugPrint('📊 Accelerometer 0x0C sample (packet #$_accelerometerLogCounter): ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    }
    
    if (data.length < 10) {
      return; // Invalid packet length
    }
    
    try {
      // Analyze data patterns:
      // Bytes 3-7 seem to contain motion/accelerometer values
      // Based on log patterns, these values change frequently
      
      final byte3 = data[3]; // Motion/acceleration X?
      final byte4 = data[4]; // Motion/acceleration Y?
      final byte5 = data[5]; // Motion/acceleration Z?
      final byte6 = data[6]; // Additional motion data
      final byte7 = data[7]; // Additional motion data
      
      // Log detailed analysis occasionally
      if (_accelerometerLogCounter % (_accelerometerLogInterval * 10) == 0) {
        debugPrint('📊 Accelerometer Pattern Analysis:');
        debugPrint('   Byte 3 (X?): 0x${byte3.toRadixString(16)} ($byte3)');
        debugPrint('   Byte 4 (Y?): 0x${byte4.toRadixString(16)} ($byte4)');  
        debugPrint('   Byte 5 (Z?): 0x${byte5.toRadixString(16)} ($byte5)');
        debugPrint('   Byte 6: 0x${byte6.toRadixString(16)} ($byte6)');
        debugPrint('   Byte 7: 0x${byte7.toRadixString(16)} ($byte7)');
        debugPrint('   Full packet: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      }
      
      // Note: This data is very high frequency (many packets per second)
      // It's likely raw accelerometer data that could be used for:
      // - Step counting refinement
      // - Fall detection
      // - Activity recognition
      // - Motion artifact detection for other sensors
      
    } catch (e) {
      debugPrint('Error parsing accelerometer data: $e');
    }
  }

  // Metodo diagnostico per testare il LED SpO2
  Future<void> testLEDFunctionality() async {
    debugPrint('🚨 Iniziando test funzionalità LED...');
    
    try {
      // Step 1: Assicuriamoci che siamo in modalità normale (LED spento)
      await exitSPO2Mode();
      debugPrint('🚨 LED dovrebbe essere SPENTO, attendere 2 secondi...');
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 2: Accendiamo il LED
      await _enableSPO2Mode();
      debugPrint('🚨 LED dovrebbe essere ACCESO (ROSSO), attendere 3 secondi...');
      await Future.delayed(const Duration(seconds: 3));
      
      // Step 3: Test comando inquiry
      debugPrint('🚨 Testing SpO2 inquiry command...');
      await inquireSPO2Status();
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 4: Spegniamo il LED
      await exitSPO2Mode();
      debugPrint('🚨 LED dovrebbe essere di nuovo SPENTO');
      
      debugPrint('✅ Test LED completato con successo');
    } catch (e) {
      debugPrint('❌ Test LED fallito: $e');
      
      // Tenta di recuperare lo stato
      try {
        await exitSPO2Mode();
      } catch (_) {}
    }
  }

  // Metodo per verificare lo stato della connessione BLE
  Future<bool> checkBLEConnection() async {
    try {
      if (_rxCharacteristic == null || _txCharacteristic == null) {
        debugPrint('❌ Caratteristiche BLE non inizializzate');
        return false;
      }

      final device = _rxCharacteristic!.device;
      final connectionState = await device.connectionState.first;
      
      debugPrint('🔍 Stato connessione dispositivo: $connectionState');
      
      if (connectionState != BluetoothConnectionState.connected) {
        debugPrint('❌ Dispositivo non connesso');
        return false;
      }
      
      debugPrint('✅ Dispositivo connesso correttamente');
      return true;
    } catch (e) {
      debugPrint('❌ Errore controllo connessione BLE: $e');
      return false;
    }
  }

  // Metodo per diagnosticare i problemi di comunicazione BLE
  Future<void> diagnoseBLEIssues() async {
    debugPrint('🔧 Avvio diagnostica BLE...');
    
    try {
      // Check 1: Connessione
      final isConnected = await checkBLEConnection();
      if (!isConnected) {
        debugPrint('❌ PROBLEMA: Dispositivo non connesso');
        return;
      }
      
      // Check 2: Caratteristiche
      if (_rxCharacteristic == null || _txCharacteristic == null) {
        debugPrint('❌ PROBLEMA: Caratteristiche BLE non trovate');
        return;
      }
      
      // Check 3: Proprietà delle caratteristiche
      debugPrint('📊 RX Char Properties: ${_rxCharacteristic!.properties}');
      debugPrint('📊 TX Char Properties: ${_txCharacteristic!.properties}');
      
      // Check 4: Notifiche abilitate
      final isNotifying = _txCharacteristic!.isNotifying;
      debugPrint('📊 TX Notifications enabled: $isNotifying');
      
      if (!isNotifying) {
        debugPrint('⚠️ Tentativo di riabilitare notifiche...');
        try {
          await _txCharacteristic!.setNotifyValue(true);
          debugPrint('✅ Notifiche riabilitate');
        } catch (e) {
          debugPrint('❌ Impossibile riabilitare notifiche: $e');
        }
      }
      
      // Check 5: Test invio comando semplice
      debugPrint('🧪 Test invio comando base...');
      try {
        await _requestTemperatureData();
        debugPrint('✅ Comando base inviato con successo');
      } catch (e) {
        debugPrint('❌ Errore invio comando base: $e');
      }
      
      debugPrint('🔧 Diagnostica BLE completata');
      
    } catch (e) {
      debugPrint('❌ Errore durante diagnostica BLE: $e');
    }
  }
}
