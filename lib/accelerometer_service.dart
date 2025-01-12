import 'dart:async';
import 'dart:collection';
import 'dart:core';
import 'dart:typed_data';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

enum FrequencyControlMode {
  hardware('Hardware Control'),
  software('Software Interpolation');

  final String label;
  const FrequencyControlMode(this.label);
}

enum TargetRate {
  hz25(25, "25 Hz"),
  hz50(50, "50 Hz"),
  hz100(100, "100 Hz"),
  hz200(200, "200 Hz"),
  hz400(400, "400 Hz");

  final int frequency;
  final String label;
  const TargetRate(this.frequency, this.label);
}

class AccelerometerData {
  final DateTime timestamp;
  final double x;
  final double y;
  final double z;
  final double frequency;
  final double interval;

  const AccelerometerData({
    required this.timestamp,
    required this.x,
    required this.y,
    required this.z,
    required this.frequency,
    required this.interval,
  });
}

class DataInterpolator {
  final Queue<AccelerometerData> _buffer = Queue();
  TargetRate _targetRate = TargetRate.hz100;
  DateTime? _lastOutputTime;

  void setTargetRate(TargetRate rate) {
    _targetRate = rate;
    reset();
  }

  List<AccelerometerData> process(AccelerometerData newData) {
    List<AccelerometerData> outputData = [];
    _buffer.add(newData);
    
    if (_buffer.length < 2) return [];

    final targetInterval = 1000.0 / _targetRate.frequency; // in milliseconds
    _lastOutputTime ??= _buffer.first.timestamp;

    while (_lastOutputTime!.isBefore(newData.timestamp)) {
      var p1 = _buffer.first;
      var p2 = _buffer.last;
      
      for (var i = 0; i < _buffer.length - 1; i++) {
        var current = _buffer.elementAt(i);
        var next = _buffer.elementAt(i + 1);
        if (current.timestamp.isBefore(_lastOutputTime!) && 
            next.timestamp.isAfter(_lastOutputTime!)) {
          p1 = current;
          p2 = next;
          break;
        }
      }

      final totalDuration = p2.timestamp.difference(p1.timestamp).inMicroseconds;
      final currentDuration = _lastOutputTime!.difference(p1.timestamp).inMicroseconds;
      final factor = totalDuration == 0 ? 0.0 : currentDuration / totalDuration;

      final interpolatedData = AccelerometerData(
        timestamp: _lastOutputTime!,
        x: p1.x + (p2.x - p1.x) * factor,
        y: p1.y + (p2.y - p1.y) * factor,
        z: p1.z + (p2.z - p1.z) * factor,
        frequency: _targetRate.frequency.toDouble(),
        interval: targetInterval,
      );

      outputData.add(interpolatedData);
      _lastOutputTime = _lastOutputTime!.add(Duration(microseconds: (targetInterval * 1000).round()));
    }

    while (_buffer.length > 2 && 
           _buffer.first.timestamp.isBefore(_lastOutputTime!.subtract(const Duration(milliseconds: 100)))) {
      _buffer.removeFirst();
    }

    return outputData;
  }

  void reset() {
    _buffer.clear();
    _lastOutputTime = null;
  }
}

class AccelerometerService {
  // BLE UUIDs
  static const String _serviceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _dataCharacteristicUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _txCharacteristicUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0';

  // LIS3DH Constants
  static const CTRL_REG1 = 0x20;
  static const RATE_25HZ = 0x30;
  static const RATE_50HZ = 0x40;
  static const RATE_100HZ = 0x50;
  static const RATE_200HZ = 0x60;
  static const RATE_400HZ = 0x70;

  // CL837 Protocol Constants
  static const CL837_SET_3D_FREQ = 0x74;
  static const CL837_COMMAND_TYPE = 0x0B;

  static const double _scaleFactor = 8.0 / 32768.0; // For ±8g range

  final _dataStreamController = StreamController<AccelerometerData>.broadcast();
  final _stateStreamController = StreamController<bool>.broadcast();

  BluetoothCharacteristic? _dataCharacteristic;
  BluetoothCharacteristic? _txCharacteristic;
  StreamSubscription? _dataSubscription;
  DateTime? _lastDataTime;
  final List<double> _intervals = [];
  static const int _maxIntervals = 10;

  FrequencyControlMode _controlMode = FrequencyControlMode.hardware;
  final DataInterpolator _interpolator = DataInterpolator();

  Stream<AccelerometerData> get dataStream => _dataStreamController.stream;
  Stream<bool> get connectionStream => _stateStreamController.stream;

  Future<void> setHardwareFrequency(TargetRate rate) async {
    if (_txCharacteristic == null) return;

    // Prova prima il protocollo CL837
    try {
      debugPrint('Trying CL837 protocol for ${rate.frequency} Hz');
      final freqIndex = TargetRate.values.indexOf(rate);
      final command = [0xFF, 0x06, CL837_SET_3D_FREQ, 0x00, CL837_COMMAND_TYPE, freqIndex];
      command.add(_calculateChecksum(command));
      await _txCharacteristic!.write(Uint8List.fromList(command));
      return;
    } catch (e) {
      debugPrint('CL837 protocol failed: $e');
    }

    // Se fallisce, prova il controllo diretto LIS3DH
    try {
      debugPrint('Trying LIS3DH direct control');
      int rateValue;
      switch (rate) {
        case TargetRate.hz25:
          rateValue = RATE_25HZ;
          break;
        case TargetRate.hz50:
          rateValue = RATE_50HZ;
          break;
        case TargetRate.hz100:
          rateValue = RATE_100HZ;
          break;
        case TargetRate.hz200:
          rateValue = RATE_200HZ;
          break;
        case TargetRate.hz400:
          rateValue = RATE_400HZ;
          break;
      }

      final command = [0xFF, 0x05, 0x3A, CTRL_REG1, rateValue];
      command.add(_calculateChecksum(command));
      await _txCharacteristic!.write(Uint8List.fromList(command));
    } catch (e) {
      debugPrint('LIS3DH direct control failed: $e');
      rethrow;
    }
  }

  void setControlMode(FrequencyControlMode mode) {
    _controlMode = mode;
    _interpolator.reset();
  }

  void setTargetRate(TargetRate rate) {
    if (_controlMode == FrequencyControlMode.hardware) {
      setHardwareFrequency(rate);
    } else {
      _interpolator.setTargetRate(rate);
    }
  }

  int _calculateChecksum(List<int> data) {
    int sum = 0;
    for (var byte in data) {
      sum += byte;
    }
    return ((0 - sum) & 0xFF) ^ 0x3A;
  }

  void _processRawData(List<int> value) {
    if (value.length < 3) return;
    try {
      if (value[0] != 0xFF) return;
      final command = value[2];
      if (command == 0x0c) {
        final now = DateTime.now();
        if (_lastDataTime != null) {
          final interval = now.difference(_lastDataTime!).inMicroseconds / 1000.0;
          _intervals.add(interval);
          if (_intervals.length > _maxIntervals) {
            _intervals.removeAt(0);
          }
        }
        _lastDataTime = now;

        double avgInterval = 0.0;
        if (_intervals.isNotEmpty) {
          avgInterval = _intervals.reduce((a, b) => a + b) / _intervals.length;
        }
        final double frequency = avgInterval > 0 ? (1000.0 / avgInterval) : 0.0;

        for (var i = 3; i < value.length - 1; i += 6) {
          if (i + 5 >= value.length) break;
          final rawX = _convertToSigned16(value[i] | (value[i + 1] << 8));
          final rawY = _convertToSigned16(value[i + 2] | (value[i + 3] << 8));
          final rawZ = _convertToSigned16(value[i + 4] | (value[i + 5] << 8));

          final x = rawX * _scaleFactor;
          final y = rawY * _scaleFactor;
          final z = rawZ * _scaleFactor;

          final data = AccelerometerData(
            timestamp: now,
            x: x,
            y: y,
            z: z,
            frequency: frequency,
            interval: avgInterval,
          );

          if (_controlMode == FrequencyControlMode.software) {
            final processedData = _interpolator.process(data);
            for (var point in processedData) {
              _dataStreamController.add(point);
            }
          } else {
            _dataStreamController.add(data);
          }
        }
      }
    } catch (e) {
      debugPrint('Error processing data: $e');
    }
  }

  int _convertToSigned16(int value) {
    value &= 0xFFFF;
    return (value & 0x8000) != 0 ? -(0x10000 - value) : value;
  }

  Future<void> _setupNotifications(BluetoothCharacteristic characteristic) async {
    try {
      await characteristic.setNotifyValue(false);
      await Future.delayed(const Duration(milliseconds: 100));
      final success = await characteristic.setNotifyValue(true);
      if (!success) {
        throw Exception('Could not enable notifications');
      }
      _dataSubscription = characteristic.lastValueStream.listen(
        _processRawData,
        onError: (error) {
          debugPrint('Notification error: $error');
          _stateStreamController.add(false);
        },
      );
      _stateStreamController.add(true);
    } catch (e) {
      debugPrint('Setup notifications error: $e');
      _stateStreamController.add(false);
      rethrow;
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      final services = await device.discoverServices();
      final service = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _serviceUuid.toLowerCase(),
        orElse: () => throw Exception('Service not found'),
      );

      _dataCharacteristic = null;
      _txCharacteristic = null;

      for (var characteristic in service.characteristics) {
        final charUuid = characteristic.uuid.toString().toLowerCase();
        if (charUuid == _dataCharacteristicUuid.toLowerCase()) {
          _dataCharacteristic = characteristic;
          await _setupNotifications(characteristic);
        } else if (charUuid == _txCharacteristicUuid.toLowerCase()) {
          _txCharacteristic = characteristic;
        }
      }

      if (_dataCharacteristic == null || _txCharacteristic == null) {
        throw Exception('Required characteristics not found');
      }
    } catch (e) {
      debugPrint('Start error: $e');
      _stateStreamController.add(false);
      rethrow;
    }
  }

  Future<void> stop() async {
    await _dataSubscription?.cancel();
    _dataSubscription = null;
    _stateStreamController.add(false);
    _intervals.clear();
    _lastDataTime = null;
    _interpolator.reset();
  }

  void dispose() {
    stop();
    _dataStreamController.close();
    _stateStreamController.close();
  }
}