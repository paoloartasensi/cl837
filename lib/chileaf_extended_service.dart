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

  // RR intervals buffer for HRV calculation
  List<double> _rrIntervalsBuffer = [];
  static const int _maxRRIntervals = 30; // Store last 30 intervals for HRV

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
      
      // Print all available services for debugging
      for (final service in services) {
        debugPrint('Available service: ${service.uuid}');
      }
      
      final customService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
        orElse: () => throw Exception('Chileaf custom service not found'),
      );

      debugPrint('Found custom service: ${customService.uuid}');

      // Find TX (notify) and RX (write) characteristics
      debugPrint('Looking for characteristics...');
      for (final char in customService.characteristics) {
        debugPrint('Available characteristic: ${char.uuid} - properties: read=${char.properties.read}, write=${char.properties.write}, notify=${char.properties.notify}');
      }

      _txCharacteristic = customService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == _txCharUuid.toLowerCase(),
        orElse: () => throw Exception('TX characteristic not found'),
      );

      _rxCharacteristic = customService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == _rxCharUuid.toLowerCase(),
        orElse: () => throw Exception('RX characteristic not found'),
      );

      debugPrint('Found TX characteristic: ${_txCharacteristic!.uuid}');
      debugPrint('Found RX characteristic: ${_rxCharacteristic!.uuid}');

      // Enable notifications on TX characteristic
      await _txCharacteristic!.setNotifyValue(true);
      debugPrint('TX notifications enabled');
      
      _dataSubscription = _txCharacteristic!.lastValueStream.listen(
        _processIncomingData,
        onError: (error) => debugPrint('Chileaf data stream error: $error'),
      );

      // Request initial data with delays between commands
      debugPrint('Requesting initial data...');
      await _requestSportsData();
      await Future.delayed(const Duration(milliseconds: 500));
      await _requestTemperatureData();
      await Future.delayed(const Duration(milliseconds: 500));
      await _enableSPO2Mode();

      // Start periodic data requests every 10 seconds
      _dataRequestTimer = Timer.periodic(const Duration(seconds: 10), (timer) async {
        try {
          await _requestTemperatureData();
          await Future.delayed(const Duration(milliseconds: 200));
          await _requestSportsData();
          await Future.delayed(const Duration(milliseconds: 200));
          await inquireSPO2Status();
        } catch (e) {
          debugPrint('Error in periodic data request: $e');
        }
      });

      debugPrint('Chileaf Extended Service started successfully');
    } catch (e) {
      debugPrint('Failed to start Chileaf Extended Service: $e');
      // Don't rethrow - let the app continue without extended features
    }
  }

  void _processIncomingData(List<int> data) {
    if (data.isEmpty) return;

    try {
      // Debug: log raw data occasionally
      if (DateTime.now().millisecondsSinceEpoch % 5000 < 100) {
        debugPrint('Raw extended data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      }

      // Handle different data formats
      if (data[0] == 0xFF && data.length >= 3) {
        // Standard Chileaf protocol
        final command = data[2];
        switch (command) {
          case 0x15: // Real-time sports data
            _processSportsData(data);
            break;
          case 0x37: // SPO2 data
            _processSPO2Data(data);
            break;
          case 0x38: // Temperature data
            _processTemperatureData(data);
            break;
          default:
            // Log unhandled commands occasionally
            if (DateTime.now().millisecondsSinceEpoch % 3000 < 50) {
              debugPrint('Unhandled Chileaf command: 0x${command.toRadixString(16)} (${data.length} bytes)');
            }
        }
      } else if (data.length >= 4) {
        // Try to detect data patterns without strict protocol
        _tryDetectDataPatterns(data);
      }
    } catch (e) {
      debugPrint('Error processing Chileaf data: $e');
    }
  }

  void _tryDetectDataPatterns(List<int> data) {
    // Try to detect temperature data patterns (typically higher values)
    if (data.length >= 6) {
      final possibleTemp1 = ((data[0] << 8) | data[1]) / 10.0;
      final possibleTemp2 = ((data[2] << 8) | data[3]) / 10.0;
      final possibleTemp3 = ((data[4] << 8) | data[5]) / 10.0;
      
      if (possibleTemp1 > 10 && possibleTemp1 < 50 && 
          possibleTemp2 > 10 && possibleTemp2 < 50 && 
          possibleTemp3 > 10 && possibleTemp3 < 50) {
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
    
    // Try to detect SpO2 data patterns (typically 70-100 range)
    if (data.length >= 2) {
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

  void _processSportsData(List<int> data) {
    if (data.length < 12) return;

    try {
      // According to SDK: bytes 3-5 = steps, 6-8 = distance (cm), 9-11 = calories (0.1 kcal)
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
      debugPrint('Sports data: $sportsData');
    } catch (e) {
      debugPrint('Error parsing sports data: $e');
    }
  }

  void _processSPO2Data(List<int> data) {
    if (data.length < 7) return;

    try {
      // According to SDK: byte 3 = SPO2 value, 4 = posture, 5 = signal quality, 6 = wearing
      final spo2Value = data[3];
      final correctPosture = data[4] == 1;
      final signalQuality = data[5];
      final isWearing = data[6] == 1;

      final spo2Data = SpO2Data(
        spo2Value: spo2Value,
        correctWristPosture: correctPosture,
        signalQuality: signalQuality,
        isWearing: isWearing,
      );

      _spo2DataController.add(spo2Data);
      debugPrint('SpO2 data: $spo2Data');
    } catch (e) {
      debugPrint('Error parsing SPO2 data: $e');
    }
  }

  void _processTemperatureData(List<int> data) {
    if (data.length < 9) return;

    try {
      // According to SDK: temperatures are in 16-bit format with *10 multiplier
      final ambientTemp = ((data[3] << 8) | data[4]) / 10.0;
      final wristTemp = ((data[5] << 8) | data[6]) / 10.0;
      final bodyTemp = ((data[7] << 8) | data[8]) / 10.0;

      final temperatureData = TemperatureData(
        ambientTempC: ambientTemp,
        wristTempC: wristTemp,
        bodyTempC: bodyTemp,
      );

      _temperatureDataController.add(temperatureData);
      debugPrint('Temperature data: $temperatureData');
    } catch (e) {
      debugPrint('Error parsing temperature data: $e');
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
        debugPrint('HRV calculated: $hrvData');
      }
    }
  }

  // Command methods based on Chileaf protocol
  Future<void> _sendCommand(List<int> command) async {
    if (_rxCharacteristic == null) return;

    try {
      // Add checksum according to Chileaf protocol
      final commandWithChecksum = _addChecksum(command);
      await _rxCharacteristic!.write(commandWithChecksum);
    } catch (e) {
      debugPrint('Error sending command: $e');
    }
  }

  List<int> _addChecksum(List<int> data) {
    // Chileaf checksum calculation from SDK
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    
    int temp = sum & 0xFF;
    temp = (0 - temp) & 0xFF;
    temp ^= 0x3A;
    
    return [...data, temp & 0xFF];
  }

  Future<void> _requestSportsData() async {
    // Command 0x15 for real-time sports data
    await _sendCommand([0xFF, 0x04, 0x15]);
  }

  Future<void> _requestTemperatureData() async {
    // Command 0x38 for temperature data
    await _sendCommand([0xFF, 0x04, 0x38]);
  }

  Future<void> _enableSPO2Mode() async {
    // Command 0x37 with parameter 1 to enter SPO2 mode
    await _sendCommand([0xFF, 0x05, 0x37, 0x01]);
  }

  Future<void> exitSPO2Mode() async {
    // Command 0x37 with parameter 0 to exit SPO2 mode
    await _sendCommand([0xFF, 0x05, 0x37, 0x00]);
  }

  Future<void> inquireSPO2Status() async {
    // Command 0x37 with parameter 2 to inquire status
    await _sendCommand([0xFF, 0x05, 0x37, 0x02]);
  }

  Future<void> stop() async {
    debugPrint('Stopping Chileaf Extended Service...');
    _dataRequestTimer?.cancel();
    _dataRequestTimer = null;
    await _dataSubscription?.cancel();
    _dataSubscription = null;
    _rrIntervalsBuffer.clear();
  }

  void dispose() {
    debugPrint('Disposing Chileaf Extended Service...');
    stop();
    _sportsDataController.close();
    _spo2DataController.close();
    _temperatureDataController.close();
    _hrvDataController.close();
  }
}
