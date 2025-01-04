import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

/// Classe per analizzare la frequenza di campionamento
class FrequencyAnalyzer {
  final int windowSizeMs;
  final List<DateTime> _timestamps = [];
  DateTime? _lastPrintTime;
  double _currentFrequency = 0;
  double _currentInterval = 0;
  
  FrequencyAnalyzer({this.windowSizeMs = 1000});

  double get frequency => _currentFrequency;
  double get interval => _currentInterval;

  void addSample(DateTime timestamp) {
    _timestamps.add(timestamp);
    
    // Rimuovi i timestamp più vecchi della finestra
    while (_timestamps.isNotEmpty && 
           timestamp.difference(_timestamps.first).inMilliseconds > windowSizeMs) {
      _timestamps.removeAt(0);
    }

    // Aggiorna le statistiche ogni 500ms
    if (_lastPrintTime == null || 
        timestamp.difference(_lastPrintTime!).inMilliseconds >= 500) {
      _currentFrequency = calculateFrequency();
      _currentInterval = calculateAverageInterval();
      _lastPrintTime = timestamp;
    }
  }

  double calculateFrequency() {
    if (_timestamps.length < 2) return 0;
    final duration = _timestamps.last.difference(_timestamps.first).inMicroseconds / 1000000;
    if (duration == 0) return 0;
    return (_timestamps.length - 1) / duration;
  }

  double calculateAverageInterval() {
    if (_timestamps.length < 2) return 0;
    double totalInterval = 0;
    for (int i = 1; i < _timestamps.length; i++) {
      totalInterval += _timestamps[i].difference(_timestamps[i-1]).inMicroseconds / 1000;
    }
    return totalInterval / (_timestamps.length - 1);
  }
}

/// Enumerazione delle frequenze di campionamento dell'accelerometro
enum AccelerometerFrequency {
  hz25(0, "25 Hz"),
  hz50(1, "50 Hz"),
  hz100(2, "100 Hz"), 
  hz200(3, "200 Hz"),
  hz400(4, "400 Hz");

  final int value;
  final String label;
  const AccelerometerFrequency(this.value, this.label);
}

/// Classe per rappresentare i dati dell'accelerometro
class AccelerometerData {
  final DateTime timestamp;
  final double x;
  final double y;
  final double z;
  final List<int> rawBytes;
  final double frequency;
  final double interval;

  const AccelerometerData({
    required this.timestamp,
    required this.x,
    required this.y,
    required this.z,
    required this.rawBytes,
    required this.frequency,
    required this.interval,
  });

  @override
  String toString() {
    return '[${_formatTime(timestamp)}] '
           'X: ${x.toStringAsFixed(3)}g '
           'Y: ${y.toStringAsFixed(3)}g '
           'Z: ${z.toStringAsFixed(3)}g '
           'Rate: ${frequency.toStringAsFixed(1)}Hz '
           'Interval: ${interval.toStringAsFixed(1)}ms';
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:'
           '${time.minute.toString().padLeft(2, '0')}:'
           '${time.second.toString().padLeft(2, '0')}.'
           '${time.millisecond.toString().padLeft(3, '0')}';
  }
}

/// Servizio per gestire la comunicazione Bluetooth con l'accelerometro
class AccelerometerService {
  static const String _serviceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _dataCharacteristicUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String _txCharacteristicUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0';
  
  static const double _scaleFactor = 8.0 / 32768.0; // Per range ±8g
  
  final _dataStreamController = StreamController<AccelerometerData>.broadcast();
  final _frequencyStreamController = StreamController<AccelerometerFrequency>.broadcast();
  final _stateStreamController = StreamController<bool>.broadcast();
  final _frequencyAnalyzer = FrequencyAnalyzer();

  BluetoothCharacteristic? _dataCharacteristic;
  BluetoothCharacteristic? _txCharacteristic;
  StreamSubscription? _dataSubscription;
  AccelerometerFrequency _currentFrequency = AccelerometerFrequency.hz50;

  Stream<AccelerometerData> get dataStream => _dataStreamController.stream;
  Stream<AccelerometerFrequency> get frequencyStream => _frequencyStreamController.stream;
  Stream<bool> get connectionStream => _stateStreamController.stream;
  AccelerometerFrequency get currentFrequency => _currentFrequency;

  int _calculateChecksum(List<int> data) {
    int sum = 0;
    for (var byte in data) {
      sum += byte;
    }
    sum = -sum;
    sum ^= 0x3a;
    return sum & 0xFF;
  }

  Future<void> sendCommand(int cmd, List<int> values) async {
    if (_txCharacteristic == null) {
      throw StateError('TX characteristic not found');
    }

    try {
      final int len = values.length + 4;
      final command = [0xFF, len, cmd];
      final packetWithValues = [...command, ...values];
      final check = _calculateChecksum(packetWithValues);
      final commandWithCheck = [...packetWithValues, check];

      debugPrint('📡 Sending command: ${commandWithCheck.map((e) => e.toRadixString(16).padLeft(2, '0')).join(' ')}');
      
      await Future.delayed(const Duration(milliseconds: 50));
      await _txCharacteristic!.write(commandWithCheck, withoutResponse: false);
      await Future.delayed(const Duration(milliseconds: 50));
    } catch (e) {
      debugPrint('❌ Error sending command: $e');
      rethrow;
    }
  }

  Future<void> set3DFrequency(AccelerometerFrequency frequency) async {
    try {
      debugPrint('🕒 Setting frequency to: ${frequency.label}');
      await sendCommand(116, [0, 11, frequency.value]);
      _currentFrequency = frequency;
      _frequencyStreamController.add(frequency);
      await _restartNotifications();
    } catch (e) {
      debugPrint('❌ Error setting frequency: $e');
      rethrow;
    }
  }

  Future<void> _restartNotifications() async {
    if (_dataCharacteristic == null) return;
    
    await _dataCharacteristic!.setNotifyValue(false);
    await Future.delayed(const Duration(milliseconds: 100));
    await _dataCharacteristic!.setNotifyValue(true);
  }

  void _processRawData(List<int> value) {
    if (value.length < 3) {
      debugPrint('❌ Invalid data length: ${value.length}');
      return;
    }

    try {
      if (value[0] != 0xFF) {
        debugPrint('❌ Invalid header: ${value[0]}');
        return;
      }

      final command = value[2];
      if (command == 0x0c) {
        for (var i = 3; i < value.length - 1; i += 6) {
          if (i + 5 >= value.length) break;
          
          final rawX = _convertToSigned16(value[i] | (value[i + 1] << 8));
          final rawY = _convertToSigned16(value[i + 2] | (value[i + 3] << 8));
          final rawZ = _convertToSigned16(value[i + 4] | (value[i + 5] << 8));

          final x = rawX * _scaleFactor;
          final y = rawY * _scaleFactor;
          final z = rawZ * _scaleFactor;

          final timestamp = DateTime.now();
          _frequencyAnalyzer.addSample(timestamp);

          final data = AccelerometerData(
            timestamp: timestamp,
            x: x,
            y: y,
            z: z,
            rawBytes: value.sublist(i, i + 6),
            frequency: _frequencyAnalyzer.frequency,
            interval: _frequencyAnalyzer.interval
          );

          _dataStreamController.add(data);
        }
      }
    } catch (e, stackTrace) {
      debugPrint('❌ Error processing data: $e');
      debugPrint(stackTrace.toString());
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

      _dataSubscription = characteristic.value.listen(
        (value) {
          _processRawData(value);
        },
        onError: (error) {
          debugPrint('❌ Notification stream error: $error');
          _stateStreamController.add(false);
        },
      );
      
      _stateStreamController.add(true);
    } catch (e) {
      debugPrint('❌ Error setting up notifications: $e');
      _stateStreamController.add(false);
      rethrow;
    }
  }

  Future<void> start(BluetoothDevice device) async {
    try {
      final services = await device.discoverServices();
      
      final service = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == _serviceUuid.toLowerCase(),
        orElse: () => throw Exception('Accelerometer service not found'),
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

      await set3DFrequency(_currentFrequency);
      
    } catch (e) {
      debugPrint('❌ Error during initialization: $e');
      _stateStreamController.add(false);
      rethrow;
    }
  }

  Future<void> stop() async {
    await _dataSubscription?.cancel();
    _dataSubscription = null;
    _stateStreamController.add(false);
  }

  void dispose() {
    stop();
    _dataStreamController.close();
    _frequencyStreamController.close();
    _stateStreamController.close();
  }
}