import 'dart:async';
import 'dart:core';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

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

enum DataRateMode {
    normal(1, "Normal Rate"),
    doubleR(2, "2x Rate"),
    half(0.5, "1/2 Rate"),
    quarter(0.25, "1/4 Rate"),
    interpolate50(50, "Interpolate to 50 Hz"); // Nuova modalità

    final double factor;
    final String label;

    const DataRateMode(this.factor, this.label);
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

class AccelerometerService {
    static const String _serviceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
    static const String _dataCharacteristicUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';
    static const String _txCharacteristicUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0';
    static const double _scaleFactor = 8.0 / 32768.0; // Per range ±8g

    final _dataStreamController = StreamController<AccelerometerData>.broadcast();
    final _stateStreamController = StreamController<bool>.broadcast();

    BluetoothCharacteristic? _dataCharacteristic;
    BluetoothCharacteristic? _txCharacteristic;
    StreamSubscription? _dataSubscription;
    DateTime? _lastDataTime;
    final List<double> _intervals = [];
    static const int _maxIntervals = 10;

    DataRateMode _currentMode = DataRateMode.normal;
    int _dataCounter = 0;
    AccelerometerData? _bufferedData;

    Stream<AccelerometerData> get dataStream => _dataStreamController.stream;
    Stream<bool> get connectionStream => _stateStreamController.stream;

    void setDataRateMode(DataRateMode mode) {
        _currentMode = mode;
        _dataCounter = 0;
        _bufferedData = null;
    }

    void _processRawData(List<int> value) {
        if (value.length < 3) return;
        try {
            if (value[0] != 0xFF) return;
            final command = value[2];
            if (command == 0x0c) {
                final now = DateTime.now();
                // Calculate interval and frequency
                if (_lastDataTime != null) {
                    final interval = now.difference(_lastDataTime!).inMicroseconds / 1000.0;
                    _intervals.add(interval);
                    if (_intervals.length > _maxIntervals) {
                        _intervals.removeAt(0);
                    }
                }
                _lastDataTime = now;
                // Calculate average frequency
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
                        frequency: frequency * _currentMode.factor, // Adjust reported frequency
                        interval: avgInterval / _currentMode.factor // Adjust reported interval
                    );
                    _processDataWithMode(data);
                }
            }
        } catch (e) {
            debugPrint('Error processing data: $e');
        }
    }

    void _processDataWithMode(AccelerometerData data) {
        switch (_currentMode) {
            case DataRateMode.normal:
                _dataStreamController.add(data);
                break;
            case DataRateMode.doubleR:
                // Simulate double rate by interpolating between current and previous data
                if (_bufferedData != null) {
                    final interpolatedData = AccelerometerData(
                        timestamp: DateTime.fromMillisecondsSinceEpoch(
                            (_bufferedData!.timestamp.millisecondsSinceEpoch + data.timestamp.millisecondsSinceEpoch) ~/ 2
                        ),
                        x: (_bufferedData!.x + data.x) / 2,
                        y: (_bufferedData!.y + data.y) / 2,
                        z: (_bufferedData!.z + data.z) / 2,
                        frequency: data.frequency,
                        interval: data.interval
                    );
                    _dataStreamController.add(_bufferedData!);
                    _dataStreamController.add(interpolatedData);
                    _dataStreamController.add(data);
                    _bufferedData = null;
                } else {
                    _bufferedData = data;
                }
                break;
            case DataRateMode.half:
                // Send every other sample
                if (_dataCounter % 2 == 0) {
                    _dataStreamController.add(data);
                }
                _dataCounter++;
                break;
            case DataRateMode.quarter:
                // Send every fourth sample
                if (_dataCounter % 4 == 0) {
                    _dataStreamController.add(data);
                }
                _dataCounter++;
                break;
            case DataRateMode.interpolate50:
                // Interpolate to 50 Hz
                if (_bufferedData != null) {
                    final interpolatedData = AccelerometerData(
                        timestamp: DateTime.fromMillisecondsSinceEpoch(
                            (_bufferedData!.timestamp.millisecondsSinceEpoch + data.timestamp.millisecondsSinceEpoch) ~/ 2
                        ),
                        x: (_bufferedData!.x + data.x) / 2,
                        y: (_bufferedData!.y + data.y) / 2,
                        z: (_bufferedData!.z + data.z) / 2,
                        frequency: 50.0, // Set frequency to 50 Hz
                        interval: 20.0 // Set interval to 20 ms (50 Hz)
                    );
                    _dataStreamController.add(_bufferedData!);
                    _dataStreamController.add(interpolatedData);
                    _dataStreamController.add(data);
                    _bufferedData = null;
                } else {
                    _bufferedData = data;
                }
                break;
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
        _bufferedData = null;
        _dataCounter = 0;
    }

    void dispose() {
        stop();
        _dataStreamController.close();
        _stateStreamController.close();
    }
}
