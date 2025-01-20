import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class BatteryServiceException implements Exception {
  final String message;
  BatteryServiceException(this.message);
  
  @override
  String toString() => 'BatteryServiceException: $message';
}

class BatteryService {
  // Battery Service & Characteristic UUIDs - shortened version
  static const String _batteryServiceUuid = '180f';
  static const String _batteryCharUuid = '2a19';

  int? _lastBatteryLevel;
  final _dataStreamController = StreamController<int?>.broadcast();
  StreamSubscription? _batterySubscription;

  Stream<int?> get dataStream => _dataStreamController.stream;
  int? get lastBatteryLevel => _lastBatteryLevel;

  void _processBattery(List<int> value) {
    try {
      debugPrint('Raw battery data: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
      if (value.isEmpty) return;

      _lastBatteryLevel = value[0];
      debugPrint('Processed Battery Level: $_lastBatteryLevel%');
      _dataStreamController.add(_lastBatteryLevel);
    } catch (e) {
      debugPrint('Error processing battery data: $e');
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('Setting up Battery service...');
      await Future.delayed(const Duration(milliseconds: 1000));
      
      final services = await device.discoverServices();
      debugPrint('Found ${services.length} services:');
      for (var service in services) {
        debugPrint('Service: ${service.uuid}');
        for (var char in service.characteristics) {
          debugPrint('  Char: ${char.uuid}');
          debugPrint('    Properties: Read=${char.properties.read}, Notify=${char.properties.notify}');
        }
      }

      final batteryService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase().contains(_batteryServiceUuid.toLowerCase()),
        orElse: () => throw BatteryServiceException('Battery service not found'),
      );

      debugPrint('Found Battery service: ${batteryService.uuid}');
      final batteryChar = batteryService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase().contains(_batteryCharUuid.toLowerCase()),
        orElse: () => throw BatteryServiceException('Battery characteristic not found'),
      );

      debugPrint('Found Battery characteristic: ${batteryChar.uuid}');
      debugPrint('Battery Properties: read=${batteryChar.properties.read}, notify=${batteryChar.properties.notify}');

      // Try initial read if supported
      if (batteryChar.properties.read) {
        try {
          final value = await batteryChar.read().timeout(
            const Duration(seconds: 2),
            onTimeout: () => throw TimeoutException('Battery read timeout')
          );
          debugPrint('Initial battery read successful');
          _processBattery(value);
        } catch (e) {
          debugPrint('Battery initial read failed: $e');
        }
      }

      // Enable notifications if supported
      if (batteryChar.properties.notify) {
        try {
          final success = await batteryChar.setNotifyValue(true);
          debugPrint('Battery notifications enabled: $success');

          if (!success) {
            throw BatteryServiceException('Failed to enable battery notifications');
          }

          _batterySubscription = batteryChar.lastValueStream.listen(
            (value) {
              try {
                debugPrint('Battery notification received');
                _processBattery(value);
              } catch (e) {
                debugPrint('Battery notification processing error: $e');
              }
            },
            onError: (e) {
              debugPrint('Battery notification stream error: $e');
            },
          );
        } catch (e) {
          debugPrint('Error setting up battery notifications: $e');
        }
      } else {
        debugPrint('Battery characteristic does not support notify');
      }

      debugPrint('Battery service setup complete');
    } catch (e) {
      debugPrint('Battery service start failed: $e');
      _lastBatteryLevel = null;
      rethrow;
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