import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'battery.dart';
import 'heartrate.dart';

class AccelerometerData {
    final double x;
    final double y;
    final double z;
    const AccelerometerData({
        required this.x,
        required this.y,
        required this.z,
    });
}

class SensorService {
    // Accelerometer Service & Characteristics
    static const String _accelServiceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
    static const String _accelDataCharUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';
    static const double _scaleFactor = 8.0 / 32768.0; // For z8 g range

    final _accelDataStreamController = StreamController<AccelerometerData>.broadcast();
    final _heartRateStreamController = StreamController<int?>.broadcast();
    final _batteryLevelStreamController = StreamController<int?>.broadcast();
    final _stateStreamController = StreamController<bool>.broadcast();

    StreamSubscription? _accelSubscription;
    bool _isAccelerometerWorking = false;

    Stream<AccelerometerData> get accelDataStream => _accelDataStreamController.stream;
    Stream<int?> get heartRateStream => _heartRateStreamController.stream;
    Stream<int?> get batteryLevelStream => _batteryLevelStreamController.stream;
    Stream<bool> get connectionStream => _stateStreamController.stream;
    bool get isAccelerometerWorking => _isAccelerometerWorking;

    void _processAccelData(List<int> value, int? heartRate, int? batteryLevel) {
        if (value.length < 3) return;
        try {
            if (value[0] != 0xFF) return;
            if (value[2] == 0x0c) {
                for (var i = 3; i < value.length - 1; i += 6) {
                    if (i + 5 >= value.length) break;
                    final rawX = _convertToSigned16(value[i] | (value[i + 1] << 8));
                    final rawY = _convertToSigned16(value[i + 2] | (value[i + 3] << 8));
                    final rawZ = _convertToSigned16(value[i + 4] | (value[i + 5] << 8));
                    final x = rawX * _scaleFactor;
                    final y = rawY * _scaleFactor;
                    final z = rawZ * _scaleFactor;
                    _accelDataStreamController.add(AccelerometerData(x: x, y: y, z: z));
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

    Future<void> _startAccelerometer(BluetoothDevice device) async {
        try {
            debugPrint('\n=== STARTING ACCELEROMETER SETUP ===');
            final services = await device.discoverServices();
            // Setup Accelerometer
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
            if (!success) {
                throw Exception('Failed to enable accelerometer notifications');
            }
            _accelSubscription = accelChar.lastValueStream.listen(
                (value) {
                    debugPrint('Accelerometer data received: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
                    _processAccelData(value, null, null); // Initial values without HR and battery
                },
                onError: (error) {
                    debugPrint('Accelerometer notification error: $error');
                    _stateStreamController.add(false);
                    _isAccelerometerWorking = false;
                },
            );
            _isAccelerometerWorking = true;
        } catch (e) {
            debugPrint('Accelerometer setup error: $e');
            _isAccelerometerWorking = false;
            rethrow;
        }
    }

    Future<void> _startServiceWithRetry(Future<void> Function() startFunction) async {
        const maxRetries = 3;
        for (var i = 0; i < maxRetries; i++) {
            try {
                await startFunction();
                return;
            } catch (e) {
                if (i == maxRetries - 1) {
                    debugPrint('Service failed after $maxRetries retries: $e');
                    rethrow;
                } else {
                    debugPrint('Retrying service after error: $e');
                    await Future.delayed(Duration(milliseconds: 500 * (i + 1)));
                }
            }
        }
    }

    Future<void> start(BluetoothDevice device, HeartRateService heartRateService, BatteryService batteryService) async {
        try {
            // Start accelerometer service first - this is critical
            await _startAccelerometer(device);
            // Start heart rate and battery services in parallel with error handling
            await Future.wait([
                _startServiceWithRetry(() => heartRateService.start(device))
                    .catchError((e) {
                        debugPrint('Heart rate service failed to start: $e');
                        return null; // Continue without heart rate
                    }),
                _startServiceWithRetry(() => batteryService.start(device))
                    .catchError((e) {
                        debugPrint('Battery service failed to start: $e');
                        return null; // Continue without battery
                    }),
            ], eagerError: false);
            // Set up subscriptions for heart rate and battery updates
            heartRateService.dataStream.listen((heartRate) {
                if (_accelSubscription != null && _isAccelerometerWorking) {
                    // Update the sensor data with the new heart rate
                    _heartRateStreamController.add(heartRate);
                }
            }, onError: (e) => debugPrint('Heart rate stream error: $e'));
            batteryService.dataStream.listen((battery) {
                if (_accelSubscription != null && _isAccelerometerWorking) {
                    // Update the sensor data with the new battery level
                    _batteryLevelStreamController.add(battery);
                }
            }, onError: (e) => debugPrint('Battery stream error: $e'));
            _stateStreamController.add(true);
        } catch (e) {
            debugPrint('Start error: $e');
            _stateStreamController.add(false);
            if (!_isAccelerometerWorking) {
                // Only rethrow if accelerometer failed
                rethrow;
            }
        }
    }

    Future<void> stop() async {
        debugPrint('Stopping BLE services...');
        await _accelSubscription?.cancel();
        _accelSubscription = null;
        _isAccelerometerWorking = false;
        _stateStreamController.add(false);
        debugPrint('All BLE services stopped');
    }

    void dispose() {
        debugPrint('Disposing BLE services...');
        stop();
        _accelDataStreamController.close();
        _heartRateStreamController.close();
        _batteryLevelStreamController.close();
        _stateStreamController.close();
        debugPrint('BLE services disposed');
    }
}
