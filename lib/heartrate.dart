import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class HeartRateData {
  final int? heartRate;

  const HeartRateData({
    this.heartRate,
  });
}

class HeartRateService {
  // Heart Rate Service & Characteristic
  static const String _heartRateServiceUuid = '0000180d-0000-1000-8000-00805f9b34fb';
  static const String _heartRateCharUuid = '00002a37-0000-1000-8000-00805f9b34fb';

  int? _lastHeartRate;
  final _dataStreamController = StreamController<HeartRateData>.broadcast();
  StreamSubscription? _heartRateSubscription;

  Stream<HeartRateData> get dataStream => _dataStreamController.stream;

  int? get lastHeartRate => _lastHeartRate;

  void _processHeartRate(List<int> value) {
    try {
      debugPrint('Raw heart rate data: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
      if (value.isEmpty) return;
      final flags = value[0];
      final isHint16 = (flags & 0x01) == 1;
      int heartRate;
      if (isHint16 && value.length >= 3) {
        heartRate = value[1] | (value[2] << 8);
      } else if (value.length >= 2) {
        heartRate = value[1];
      } else {
        return;
      }
      _lastHeartRate = heartRate;
      debugPrint('Processed Heart Rate: $_lastHeartRate BPM');
      _dataStreamController.add(HeartRateData(heartRate: _lastHeartRate));
    } catch (e) {
      debugPrint('Error processing heart rate data: $e');
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('Setting up Heart Rate service...');
      final services = await device.discoverServices();
      final hrService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _heartRateServiceUuid.toLowerCase(),
        orElse: () => throw Exception('Heart rate service not found'),
      );
      debugPrint('Found Heart Rate service: ${hrService.uuid}');
      final hrChar = hrService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == _heartRateCharUuid.toLowerCase(),
        orElse: () => throw Exception('Heart rate characteristic not found'),
      );
      debugPrint('Found Heart Rate characteristic: ${hrChar.uuid}');
      debugPrint('Heart Rate Properties: read=${hrChar.properties.read}, notify=${hrChar.properties.notify}');
      
      
      if (hrChar.properties.notify) {
        final success = await hrChar.setNotifyValue(true);
        if (success) {
          _heartRateSubscription = hrChar.lastValueStream.listen(
            (value) {
              _processHeartRate(value);
            },
            onError: (e) => debugPrint('Heart rate notification error: $e'),
          );
        }
      }

      // Enable notifications
      if (hrChar.properties.notify) {
        try {
          final success = await hrChar.setNotifyValue(true);
          debugPrint('Heart Rate notifications enabled: $success');
          _heartRateSubscription = hrChar.lastValueStream.listen(
            (value) {
              debugPrint('Heart Rate notification received: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
              _processHeartRate(value);
            },
            onError: (e) => debugPrint('Heart rate notification error: $e'),
          );
        } catch (e) {
          debugPrint('Error setting up heart rate notifications: $e');
        }
      } else {
        debugPrint('Heart Rate characteristic does not support notify');
      }
    } catch (e) {
      debugPrint('Heart rate complete setup error: $e');
    }
  }

  Future<void> stop() async {
    debugPrint('Stopping Heart Rate service...');
    await _heartRateSubscription?.cancel();
    _heartRateSubscription = null;
    _lastHeartRate = null;
    debugPrint('Heart Rate service stopped');
  }

  void dispose() {
    debugPrint('Disposing Heart Rate service...');
    stop();
    _dataStreamController.close();
    debugPrint('Heart Rate service disposed');
  }
}
