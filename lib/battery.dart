import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class BatteryData {
  final int? batteryLevel;

  const BatteryData({this.batteryLevel});
}

class BatteryService {
  // Battery Service & Characteristic
  static const String _batteryServiceUuid = '0000180f-0000-1000-8000-00805f9b34fb';
  static const String _batteryCharUuid = '00002a19-0000-1000-8000-00805f9b34fb';

  int? _lastBatteryLevel;
  final _dataStreamController = StreamController<BatteryData>.broadcast();
  StreamSubscription? _batterySubscription;

  Stream<BatteryData> get dataStream => _dataStreamController.stream;
  int? get lastBatteryLevel => _lastBatteryLevel;

  void _processBattery(List<int> value) {
    try {
      debugPrint('Raw battery data: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
      if (value.isEmpty) return;
      _lastBatteryLevel = value[0];
      debugPrint('Processed Battery Level: $_lastBatteryLevel%');
      _dataStreamController.add(BatteryData(batteryLevel: _lastBatteryLevel));
    } catch (e) {
      debugPrint('Error processing battery data: $e');
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('Setting up Battery service...');
      final services = await device.discoverServices();
      final batteryService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _batteryServiceUuid.toLowerCase(),
        orElse: () => throw Exception('Battery service not found'),
      );
      debugPrint('Found Battery service: ${batteryService.uuid}');
      final batteryChar = batteryService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == _batteryCharUuid.toLowerCase(),
        orElse: () => throw Exception('Battery characteristic not found'),
      );
      debugPrint('Found Battery characteristic: ${batteryChar.uuid}');
      debugPrint('Battery Properties: read=${batteryChar.properties.read}, notify=${batteryChar.properties.notify}');

      // Try initial read
      if (batteryChar.properties.read) {
        try {
          final value = await batteryChar.read();
          debugPrint('Initial battery read: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
          _processBattery(value);
        } catch (e) {
          debugPrint('Error reading initial battery: $e');
        }
      }

      // Enable notifications
      if (batteryChar.properties.notify) {
        try {
          final success = await batteryChar.setNotifyValue(true);
          debugPrint('Battery notifications enabled: $success');
          _batterySubscription = batteryChar.lastValueStream.listen(
            (value) {
              debugPrint('Battery notification received: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
              _processBattery(value);
            },
            onError: (e) => debugPrint('Battery notification error: $e'),
          );
        } catch (e) {
          debugPrint('Error setting up battery notifications: $e');
        }
      } else {
        debugPrint('Battery characteristic does not support notify');
      }
    } catch (e) {
      debugPrint('Battery complete setup error: $e');
    }
  }

  Future<void> stop() async {
    debugPrint('Stopping Battery service...');
    await _batterySubscription?.cancel();
    _batterySubscription = null;
    _lastBatteryLevel = null;
    debugPrint('Battery service stopped');
  }

  void dispose() {
    debugPrint('Disposing Battery service...');
    stop();
    _dataStreamController.close();
    debugPrint('Battery service disposed');
  }
}
