import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class HeartRateServiceException implements Exception {
  final String message;
  HeartRateServiceException(this.message);
  
  @override
  String toString() => 'HeartRateServiceException: $message';
}

class HeartRateService {
  // Heart Rate Service & Characteristic UUIDs - shortened version
  static const String _heartRateServiceUuid = '180d';
  static const String _heartRateCharUuid = '2a37';

  int? _lastHeartRate;
  final _dataStreamController = StreamController<int?>.broadcast();
  StreamSubscription? _heartRateSubscription;

  Stream<int?> get dataStream => _dataStreamController.stream;
  int? get lastHeartRate => _lastHeartRate;

  void _processHeartRate(List<int> value) {
    try {
      debugPrint('Raw heart rate data: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
      if (value.isEmpty) return;

      // First byte contains flags
      final flags = value[0];
      final isFormat16Bit = (flags & 0x01) != 0;  // Check first bit
      debugPrint('Heart Rate flags: 0x${flags.toRadixString(16)} (16-bit format: $isFormat16Bit)');

      // Heart rate measurement value format
      if (value.length >= 2) {
        if (isFormat16Bit && value.length >= 3) {
          // 16-bit format
          _lastHeartRate = value[1] | (value[2] << 8);
        } else {
          // 8-bit format
          _lastHeartRate = value[1];
        }
        debugPrint('Processed Heart Rate: $_lastHeartRate BPM');
        _dataStreamController.add(_lastHeartRate);
      }
    } catch (e) {
      debugPrint('Error processing heart rate data: $e');
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      debugPrint('Setting up Heart Rate service...');
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

      final heartRateService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase().contains(_heartRateServiceUuid.toLowerCase()),
        orElse: () => throw HeartRateServiceException('Heart Rate service not found'),
      );

      debugPrint('Found Heart Rate service: ${heartRateService.uuid}');
      final heartRateChar = heartRateService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase().contains(_heartRateCharUuid.toLowerCase()),
        orElse: () => throw HeartRateServiceException('Heart Rate characteristic not found'),
      );

      debugPrint('Found Heart Rate characteristic: ${heartRateChar.uuid}');
      debugPrint('Heart Rate Properties: read=${heartRateChar.properties.read}, notify=${heartRateChar.properties.notify}');

      // Heart rate measurement only supports notifications
      if (heartRateChar.properties.notify) {
        try {
          final success = await heartRateChar.setNotifyValue(true);
          debugPrint('Heart Rate notifications enabled: $success');

          if (!success) {
            throw HeartRateServiceException('Failed to enable heart rate notifications');
          }

          _heartRateSubscription = heartRateChar.lastValueStream.listen(
            (value) {
              try {
                debugPrint('Heart Rate notification received');
                _processHeartRate(value);
              } catch (e) {
                debugPrint('Heart Rate notification processing error: $e');
              }
            },
            onError: (e) {
              debugPrint('Heart Rate notification stream error: $e');
            },
          );

          debugPrint('Heart Rate subscription set up successfully');
        } catch (e) {
          debugPrint('Error setting up heart rate notifications: $e');
          rethrow;
        }
      } else {
        throw HeartRateServiceException('Heart Rate characteristic does not support notifications');
      }

      debugPrint('Heart Rate service setup complete');
    } catch (e) {
      debugPrint('Heart Rate service start failed: $e');
      _lastHeartRate = null;
      rethrow;
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