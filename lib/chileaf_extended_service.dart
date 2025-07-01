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
      
      final services = await device.discoverServices();
      final customService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _customServiceUuid.toLowerCase(),
        orElse: () => throw Exception('Chileaf custom service not found'),
      );

      // Find TX (notify) and RX (write) characteristics
      _txCharacteristic = customService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == _txCharUuid.toLowerCase(),
        orElse: () => throw Exception('TX characteristic not found'),
      );

      _rxCharacteristic = customService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == _rxCharUuid.toLowerCase(),
        orElse: () => throw Exception('RX characteristic not found'),
      );

      // Enable notifications on TX characteristic
      await _txCharacteristic!.setNotifyValue(true);
      
      _dataSubscription = _txCharacteristic!.lastValueStream.listen(
        _processIncomingData,
        onError: (error) => debugPrint('Chileaf data stream error: $error'),
      );

      // Request initial data
      await _requestSportsData();
      await _requestTemperatureData();
      await _enableSPO2Mode();

      debugPrint('Chileaf Extended Service started successfully');
    } catch (e) {
      debugPrint('Failed to start Chileaf Extended Service: $e');
      rethrow;
    }
  }

  void _processIncomingData(List<int> data) {
    if (data.isEmpty || data[0] != 0xFF) return;

    try {
      final command = data.length > 2 ? data[2] : 0;
      
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
          if (DateTime.now().millisecondsSinceEpoch % 1000 < 50) {
            debugPrint('Unhandled Chileaf command: 0x${command.toRadixString(16)}');
          }
      }
    } catch (e) {
      debugPrint('Error processing Chileaf data: $e');
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
