import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'models/sensor_data.dart';

class AccelerometerServiceOptimized {
  // Ripristino gli UUID originali del dispositivo CL837
  static const String serviceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
  static const String characteristicUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';
  // Ripristino il fattore di scala originale
  static const double _scaleFactor = 8.0 / 32768.0; // Per range ±8g

  BluetoothCharacteristic? _accelerometerCharacteristic;
  final StreamController<AccelerometerData> _dataStreamController = StreamController<AccelerometerData>.broadcast();
  StreamSubscription<List<int>>? _notificationSubscription;
  
  bool _isRunning = false;
  bool get isRunning => _isRunning;
  
  Stream<AccelerometerData> get dataStream => _dataStreamController.stream;

  // Definisci un intervallo di campionamento ottimale
  int samplingIntervalMs = 20;  // Campiona a 50Hz, un po' più lento ma più stabile
  
  // Implementa una coda per gestire i dati in arrivo
  final _bufferSize = 3;
  final List<AccelerometerData> _dataBuffer = [];
  Timer? _processingTimer;

  // Conversione da int16 a signed
  int _convertToSigned16(int value) {
    value &= 0xFFFF;
    return (value & 0x8000) != 0 ? -(0x10000 - value) : value;
  }

  Future<void> start(BluetoothDevice device) async {
    if (_isRunning) return;
    
    try {
      // Richiedi un MTU più grande per aumentare la velocità di trasmissione
      try {
        await device.requestMtu(512);
        debugPrint('MTU size increased to 512');
      } catch (e) {
        debugPrint('Failed to increase MTU: $e');
      }
      
      debugPrint('\n=== STARTING ACCELEROMETER SETUP ===');
      List<BluetoothService> services = await device.discoverServices();
      BluetoothService? accelerometerService;
      
      // Elenca tutti i servizi disponibili per debug
      debugPrint('Found ${services.length} services:');
      for (var service in services) {
        debugPrint('Service: ${service.uuid}');
        for (var char in service.characteristics) {
          debugPrint('  Char: ${char.uuid}');
          debugPrint('    Properties: Read=${char.properties.read}, Notify=${char.properties.notify}');
        }
      }
      
      // Trova il servizio accelerometro usando l'UUID corretto
      accelerometerService = services.firstWhere(
        (s) => s.uuid.toString().toLowerCase() == serviceUuid.toLowerCase(),
        orElse: () => throw Exception('Accelerometer service not found'),
      );
      
      debugPrint('Found Accelerometer service: ${accelerometerService.uuid}');
      
      // Trova la caratteristica usando l'UUID corretto
      _accelerometerCharacteristic = accelerometerService.characteristics.firstWhere(
        (c) => c.uuid.toString().toLowerCase() == characteristicUuid.toLowerCase(),
        orElse: () => throw Exception('Accelerometer characteristic not found'),
      );
      
      debugPrint('Found Accelerometer characteristic: ${_accelerometerCharacteristic!.uuid}');
      debugPrint('Accelerometer Properties: read=${_accelerometerCharacteristic!.properties.read}, notify=${_accelerometerCharacteristic!.properties.notify}');
      
      // Attiva le notifiche
      final success = await _accelerometerCharacteristic!.setNotifyValue(true);
      debugPrint('Accelerometer notifications enabled: $success');
      
      if (!success) {
        throw Exception('Failed to enable accelerometer notifications');
      }
      
      // Utilizziamo lastValueStream invece di value (che è deprecato)
      _notificationSubscription = _accelerometerCharacteristic!.lastValueStream.listen(
        (value) {
          debugPrint('Accelerometer data received: ${value.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(', ')}');
          _handleAccelerometerData(value);
        },
        onError: (error) {
          debugPrint("Accelerometer notification error: $error");
          _dataStreamController.addError(error);
        }
      );
      
      _isRunning = true;
      
      // Avvia il timer per l'elaborazione dei dati in batch
      _processingTimer = Timer.periodic(Duration(milliseconds: samplingIntervalMs), (_) {
        _processDataBuffer();
      });
      
      debugPrint('Accelerometer service started successfully');
    } catch (e) {
      debugPrint("Error starting accelerometer service: $e");
      rethrow;
    }
  }
  
  void _handleAccelerometerData(List<int> value) {
    if (value.length < 3) return;

    try {
      // Ripristino la logica di parsing originale che funziona con CL837
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
          
          _addToBuffer(AccelerometerData(x: x, y: y, z: z));
        }
      }
    } catch (e) {
      debugPrint('Error processing accelerometer data: $e');
    }
  }
  
  void _addToBuffer(AccelerometerData data) {
    _dataBuffer.add(data);
    if (_dataBuffer.length > _bufferSize) {
      _dataBuffer.removeAt(0);
    }
  }
  
  void _processDataBuffer() {
    if (_dataBuffer.isEmpty) return;
    
    // Emetti solo il dato più recente
    final latestData = _dataBuffer.last;
    _dataStreamController.add(latestData);
    
    // Clear buffer after processing
    _dataBuffer.clear();
  }

  Future<void> stop() async {
    _processingTimer?.cancel();
    await _notificationSubscription?.cancel();
    _notificationSubscription = null;
    
    if (_accelerometerCharacteristic != null) {
      try {
        await _accelerometerCharacteristic!.setNotifyValue(false);
      } catch (e) {
        debugPrint("Error stopping accelerometer notifications: $e");
      }
    }
    _isRunning = false;
    debugPrint('Accelerometer service stopped');
  }
  
  void dispose() {
    stop();
    _dataStreamController.close();
    debugPrint('Accelerometer service disposed');
  }
}