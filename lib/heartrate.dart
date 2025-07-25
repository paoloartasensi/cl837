import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'models/heart_rate_data.dart';

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

  HeartRateData? _lastHeartRateData;
  final _dataStreamController = StreamController<HeartRateData?>.broadcast();
  StreamSubscription? _heartRateSubscription;

  Stream<HeartRateData?> get dataStream => _dataStreamController.stream;
  HeartRateData? get lastHeartRateData => _lastHeartRateData;

  /// Parses Heart Rate Measurement data according to BLE specification
  /// Based on reverse-engineered Java code from HeartRateMeasurementParser
  HeartRateData? _parseHeartRateData(List<int> data) {
    try {
      if (data.isEmpty) return null;

      // Silent processing - no logging for performance
      
      // First byte contains flags (based on BLE Heart Rate Measurement spec)
      final flags = data[0];
      int index = 1;

      // Bit 0: Heart Rate Value Format (0 = UINT8, 1 = UINT16)
      final isFormat16Bit = (flags & 0x01) != 0;
      
      // Bit 1: Sensor Contact Status (0 = not supported, 1 = supported)
      final contactSupported = (flags & 0x02) != 0;
      
      // Bit 2: Sensor Contact Status (if bit 1 is 1, then 0 = not detected, 1 = detected)
      bool? contactDetected;
      if (contactSupported) {
        contactDetected = (flags & 0x04) != 0;
      }
      
      // Bit 3: Energy Expended Status (0 = not present, 1 = present)
      final energyExpendedPresent = (flags & 0x08) != 0;
      
      // Bit 4: RR-Interval (0 = not present, 1 = present)
      final rrIntervalsPresent = (flags & 0x10) != 0;

      // Parse heart rate value
      int heartRate;
      if (isFormat16Bit) {
        if (data.length < index + 2) return null;
        heartRate = data[index] | (data[index + 1] << 8);  // Little endian
        index += 2;
      } else {
        if (data.length < index + 1) return null;
        heartRate = data[index];
        index += 1;
      }

      // Parse Energy Expended (if present)
      int? energyExpanded;
      if (energyExpendedPresent) {
        if (data.length < index + 2) return null;
        energyExpanded = data[index] | (data[index + 1] << 8);  // Little endian, kJ
        index += 2;
      }

      // Parse RR-Intervals (if present)
      List<double>? rrIntervals;
      if (rrIntervalsPresent) {
        rrIntervals = [];
        // RR-Intervals are in units of 1/1024 seconds, stored as UINT16 little endian
        while (index + 1 < data.length) {
          final rrRaw = data[index] | (data[index + 1] << 8);
          final rrMs = (rrRaw / 1024.0) * 1000.0;  // Convert to milliseconds
          rrIntervals.add(rrMs);
          index += 2;
        }
      }

      final heartRateData = HeartRateData(
        heartRate: heartRate,
        contactDetected: contactDetected,
        contactSupported: contactSupported,
        energyExpanded: energyExpanded,
        rrIntervals: rrIntervals,
        timestamp: DateTime.now(),
      );

      return heartRateData;

    } catch (e) {
      debugPrint('Error parsing heart rate data: $e');
      return null;
    }
  }

  void _processHeartRate(List<int> value) {
    try {
      final heartRateData = _parseHeartRateData(value);
      if (heartRateData != null) {
        _lastHeartRateData = heartRateData;
        _dataStreamController.add(heartRateData);
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
      debugPrint('Heart Rate: Found ${services.length} services - looking for $_heartRateServiceUuid');

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
                // Silent processing - no logging for performance
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
      _lastHeartRateData = null;
      rethrow;
    }
  }

  Future<void> stop() async {
    debugPrint('Stopping Heart Rate service...');
    await _heartRateSubscription?.cancel();
    _heartRateSubscription = null;
    _lastHeartRateData = null;
    debugPrint('Heart Rate service stopped');
  }

  void dispose() {
    debugPrint('Disposing Heart Rate service...');
    stop();
    _dataStreamController.close();
    debugPrint('Heart Rate service disposed');
  }
}