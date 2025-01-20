import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'battery.dart';
import 'heartrate.dart';

class SensorData {
  final DateTime timestamp;
  final double x;
  final double y;
  final double z;
  final int? heartRate;
  final int? batteryLevel;

  const SensorData({
    required this.timestamp,
    required this.x,
    required this.y,
    required this.z,
    this.heartRate,
    this.batteryLevel,
  });
}

class SensorService {
  // Accelerometer Service & Characteristics
  static const String _accelServiceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _accelDataCharUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';
  static const double _scaleFactor = 8.0 / 32768.0; // For ±8 g range

  final _dataStreamController = StreamController<SensorData>.broadcast();
  final _stateStreamController = StreamController<bool>.broadcast();
  StreamSubscription? _accelSubscription;

  Stream<SensorData> get dataStream => _dataStreamController.stream;
  Stream<bool> get connectionStream => _stateStreamController.stream;

  void _processAccelData(List<int> value, int? heartRate, int? batteryLevel) {
    if (value.length < 3) return;
    try {
      if (value[0] != 0xFF) return;
      if (value[2] == 0x0c) {
        final now = DateTime.now();
        for (var i = 3; i < value.length - 1; i += 6) {
          if (i + 5 >= value.length) break;
          final rawX = _convertToSigned16(value[i] | (value[i + 1] << 8));
          final rawY = _convertToSigned16(value[i + 2] | (value[i + 3] << 8));
          final rawZ = _convertToSigned16(value[i + 4] | (value[i + 5] << 8));
          final x = rawX * _scaleFactor;
          final y = rawY * _scaleFactor;
          final z = rawZ * _scaleFactor;
          _dataStreamController.add(SensorData(
            timestamp: now,
            x: x,
            y: y,
            z: z,
            heartRate: heartRate,
            batteryLevel: batteryLevel,
          ));
        }
      }
    } catch (e) {
      debugPrint('Error processing accelerometer data: $e');
    }
  }

  int _convertToSigned16(int value) {
    value &= 0xFFFF;
    return (value & 0x8000) != 0 ? -(0x10000 - value) : value;
  }

  Future<void> start(BluetoothDevice device, HeartRateService heartRateService, BatteryService batteryService) async {
    try {
      debugPrint('\n=== STARTING BLE SERVICE SETUP ===');
      final services = await device.discoverServices();
      debugPrint('Found ${services.length} services:');
      for (var service in services) {
        debugPrint('Service: ${service.uuid}');
      }

      // Log all discovered services and their characteristics
      for (var service in services) {
        debugPrint('Service UUID: ${service.uuid}');
        for (var char in service.characteristics) {
          debugPrint(' Characteristic UUID: ${char.uuid}');
          debugPrint(' Properties: read=${char.properties.read}, write=${char.properties.write}, notify=${char.properties.notify}, indicate=${char.properties.indicate}');
        }
      }

      // Setup Accelerometer
      try {
        debugPrint('Setting up Accelerometer service...');
        final accelService = services.firstWhere(
          (s) => s.uuid.toString().toLowerCase() == _accelServiceUuid.toLowerCase(),
          orElse: () => throw Exception('Accelerometer service not found'),
        );
        debugPrint('Found Accelerometer service: ${accelService.uuid}');
        final accelChar = accelService.characteristics.firstWhere(
          (c) => c.uuid.toString().toLowerCase() == _accelDataCharUuid.toLowerCase(),
          orElse: () => throw Exception('Accelerometer characteristic not found'),
        );
        debugPrint('Found Accelerometer characteristic: ${accelChar.uuid}');
        debugPrint('Accelerometer Properties: read=${accelChar.properties.read}, notify=${accelChar.properties.notify}');

        // Enable notifications
        final success = await accelChar.setNotifyValue(true);
        debugPrint('Accelerometer notifications enabled: $success');
        _accelSubscription = accelChar.lastValueStream.listen(
          (value) {
            debugPrint('Accelerometer data received: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
            _processAccelData(value, heartRateService.lastHeartRate, batteryService.lastBatteryLevel);
          },
          onError: (error) {
            debugPrint('Accelerometer notification error: $error');
            _stateStreamController.add(false);
          },
        );
      } catch (e) {
        debugPrint('Accelerometer setup error: $e');
      }

      await heartRateService.start(device);
      await batteryService.start(device);

      _stateStreamController.add(true);
    } catch (e) {
      debugPrint('Start error: $e');
      _stateStreamController.add(false);
      rethrow;
    }
  }

  Future<void> stop() async {
    debugPrint('Stopping BLE services...');
    await _accelSubscription?.cancel();
    _accelSubscription = null;
    _stateStreamController.add(false);
    debugPrint('All BLE services stopped');
  }

  void dispose() {
    debugPrint('Disposing BLE services...');
    stop();
    _dataStreamController.close();
    _stateStreamController.close();
    debugPrint('BLE services disposed');
  }
}
