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
      
      // Find custom service
      final customService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase().contains(_customServiceUuid.split('-')[0].toLowerCase()),
        orElse: () => throw Exception('Custom service not found'),
      );

      debugPrint('Found custom service: ${customService.uuid}');

      // Find characteristics
      _txCharacteristic = customService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase().contains(_txCharUuid.split('-')[0].toLowerCase()),
        orElse: () => throw Exception('TX characteristic not found'),
      );

      _rxCharacteristic = customService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase().contains(_rxCharUuid.split('-')[0].toLowerCase()),
        orElse: () => throw Exception('RX characteristic not found'),
      );

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
      await _enableSPO2Mode();
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
    if (data.isEmpty) return;

    try {
      // Debug: log all data for SPO2/temperature debugging
      debugPrint('Extended service data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');

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
        }
      } else if (data.length >= 4) {
        // Try to detect data patterns without strict protocol
        _tryDetectDataPatterns(data);
      }
    } catch (e) {
      debugPrint('Error processing Chileaf data: $e');
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
      debugPrint('RX characteristic not available');
      return;
    }

    try {
      final frame = _buildProtocolFrame(command);
      debugPrint('Sending command: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      await _rxCharacteristic!.write(frame, withoutResponse: true);
    } catch (e) {
      debugPrint('Error sending command: $e');
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
    await _sendCommand([_commandSpo2, 0x01]);
  }

  Future<void> exitSPO2Mode() async {
    // Command 0x37 with parameter 0 to exit SPO2 mode
    await _sendCommand([_commandSpo2, 0x00]);
  }

  Future<void> inquireSPO2Status() async {
    // Command 0x37 with parameter 2 to inquire status
    await _sendCommand([_commandSpo2, 0x02]);
  }

  // Public method for on-demand SpO2 measurement
  Future<void> measureSpO2() async {
    try {
      debugPrint('🫁 Starting on-demand SpO2 measurement...');
      
      // Step 1: Enter SpO2 mode
      await _enableSPO2Mode();
      debugPrint('🫁 SpO2 mode enabled, stabilizing...');
      
      // Step 2: Wait for stabilization (important for accurate reading)
      await Future.delayed(const Duration(milliseconds: 3000));
      
      // Step 3: Request SpO2 status multiple times for better accuracy
      await inquireSPO2Status();
      await Future.delayed(const Duration(milliseconds: 1000));
      await inquireSPO2Status();
      await Future.delayed(const Duration(milliseconds: 1000));
      await inquireSPO2Status(); // Final reading
      
      debugPrint('🫁 SpO2 measurement requests sent');
    } catch (e) {
      debugPrint('🫁 Error in SpO2 measurement: $e');
      rethrow;
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
}
