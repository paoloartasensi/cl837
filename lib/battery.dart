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
  // Alternative UUIDs for battery services (standard and custom)
  static const List<String> _alternativeBatteryServiceUuids = [
    '180f', // Standard Battery Service
    'bf03', // Some custom battery service
    'fee7', // Another common custom UUID
  ];
  
  static const List<String> _alternativeBatteryCharUuids = [
    '2a19', // Standard Battery Level characteristic
    'bf04', // Custom battery char
    'fee8', // Another custom battery char
  ];

  int? _lastBatteryLevel;
  final _dataStreamController = StreamController<int?>.broadcast();
  StreamSubscription? _batterySubscription;

  Stream<int?> get dataStream => _dataStreamController.stream;
  int? get lastBatteryLevel => _lastBatteryLevel;

  void _processBattery(List<int> value) {
    try {
      if (value.isEmpty) return;

      _lastBatteryLevel = value[0];
      _dataStreamController.add(_lastBatteryLevel);
      debugPrint('🔋 Battery level updated: $_lastBatteryLevel%');
    } catch (e) {
      debugPrint('Error processing battery data: $e');
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('🔋 Setting up Battery service...');
      await Future.delayed(const Duration(milliseconds: 1000));
      
      final services = await device.discoverServices();
      
      // Try to find battery service using standard or alternative UUIDs
      BluetoothService? batteryService;
      for (final serviceUuid in _alternativeBatteryServiceUuids) {
        try {
          batteryService = services.firstWhere(
            (s) => s.uuid.toString().toLowerCase().contains(serviceUuid.toLowerCase()),
          );
          debugPrint('🔋 Found battery service with UUID: $serviceUuid');
          break;
        } catch (e) {
          // Continue trying other UUIDs
        }
      }

      if (batteryService == null) {
        // Fallback: try to find any service that might contain battery data
        for (final service in services) {
          for (final char in service.characteristics) {
            for (final charUuid in _alternativeBatteryCharUuids) {
              if (char.uuid.toString().toLowerCase().contains(charUuid.toLowerCase())) {
                batteryService = service;
                debugPrint('🔋 Found battery service via characteristic: ${service.uuid}');
                break;
              }
            }
            if (batteryService != null) break;
          }
          if (batteryService != null) break;
        }
      }

      if (batteryService == null) {
        throw BatteryServiceException('Battery service not found with any known UUID');
      }

      debugPrint('🔋 Found Battery service: ${batteryService.uuid}');
      
      // Try to find battery characteristic using standard or alternative UUIDs
      BluetoothCharacteristic? batteryChar;
      for (final charUuid in _alternativeBatteryCharUuids) {
        try {
          batteryChar = batteryService.characteristics.firstWhere(
            (c) => c.uuid.toString().toLowerCase().contains(charUuid.toLowerCase()),
          );
          debugPrint('🔋 Found battery characteristic with UUID: $charUuid');
          break;
        } catch (e) {
          // Continue trying other UUIDs
        }
      }

      if (batteryChar == null) {
        throw BatteryServiceException('Battery characteristic not found with any known UUID');
      }

      debugPrint('🔋 Found Battery characteristic: ${batteryChar.uuid}');
      debugPrint('🔋 Battery Properties: read=${batteryChar.properties.read}, notify=${batteryChar.properties.notify}');

      // Try initial read if supported
      if (batteryChar.properties.read) {
        try {
          final value = await batteryChar.read().timeout(
            const Duration(seconds: 2),
            onTimeout: () => throw TimeoutException('Battery read timeout')
          );
          debugPrint('🔋 Initial battery read successful: $value');
          _processBattery(value);
        } catch (e) {
          debugPrint('Battery initial read failed: $e');
        }
      }

      // Enable notifications if supported
      if (batteryChar.properties.notify) {
        try {
          final success = await batteryChar.setNotifyValue(true);
          debugPrint('🔋 Battery notifications enabled: $success');

          if (!success) {
            throw BatteryServiceException('Failed to enable battery notifications');
          }

          _batterySubscription = batteryChar.lastValueStream.listen(
            (value) {
              try {
                // Processa solo notifiche con cambiamenti significativi
                _processBattery(value);
              } catch (e) {
                debugPrint('🔋 Battery notification processing error: $e');
              }
            },
            onError: (e) {
              debugPrint('🔋 Battery notification stream error: $e');
            },
          );
        } catch (e) {
          debugPrint('Error setting up battery notifications: $e');
        }        } else {
          debugPrint('🔋 Battery characteristic does not support notify - trying periodic reads');
          // Se non supporta notify, proviamo una lettura periodica
          _setupPeriodicBatteryRead(batteryChar);
        }

        debugPrint('🔋 Battery service setup complete');
      } catch (e) {
        debugPrint('🔋 Battery service start failed: $e');
        _lastBatteryLevel = null;
        rethrow;
      }
    }

    void _setupPeriodicBatteryRead(BluetoothCharacteristic batteryChar) {
      Timer.periodic(const Duration(seconds: 30), (timer) async {
        if (_batterySubscription == null) {
          timer.cancel();
          return;
        }
        try {
          if (batteryChar.properties.read) {
            final value = await batteryChar.read();
            debugPrint('🔋 Periodic battery read: $value');
            _processBattery(value);
          }
        } catch (e) {
          debugPrint('🔋 Periodic battery read error: $e');
        }
      });
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