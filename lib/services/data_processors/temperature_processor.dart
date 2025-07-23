import 'dart:async';
import 'package:flutter/foundation.dart';
import '../../models/temperature_data.dart';

/// Processore specializzato per i dati di temperatura
/// Gestisce l'analisi e l'estrazione dei valori di temperatura dai pacchetti BLE
class TemperatureProcessor {
  final StreamController<TemperatureData> _temperatureDataController = StreamController<TemperatureData>.broadcast();
  
  /// Stream dei dati di temperatura processati
  Stream<TemperatureData> get temperatureDataStream => _temperatureDataController.stream;

  /// Processa i dati di temperatura dal comando 0x38
  void processTemperatureData(List<int> data) {
    // Temperature-0x38 response format from SDK:
    // Byte 3-4: Ambient temperature (MSB first, unit: *10°C)
    // Byte 5-6: Wrist temperature (MSB first, unit: *10°C)  
    // Byte 7-8: Body temperature (MSB first, unit: *10°C)
    
    if (data.length < 9) {
      debugPrint('Temperature data too short: ${data.length} bytes');
      return;
    }

    try {
      // Parse temperatures (MSB first, divide by 10 for actual °C)
      final ambientTempRaw = (data[3] << 8) | data[4];
      final wristTempRaw = (data[5] << 8) | data[6];
      final bodyTempRaw = (data[7] << 8) | data[8];
      
      final ambientTemp = ambientTempRaw / 10.0;
      final wristTemp = wristTempRaw / 10.0;
      final bodyTemp = bodyTempRaw / 10.0;
      
      // SILENT - no debug print for temperature values

      // Temperature readings are generally stable, send all valid readings
      // Similar to professional medical devices: continuous monitoring approach
      if (ambientTemp >= 10 && ambientTemp <= 50 && 
          wristTemp >= 20 && wristTemp <= 45 &&
          bodyTemp >= 30 && bodyTemp <= 45) {
        final temperatureData = TemperatureData(
          ambientTempC: ambientTemp,
          wristTempC: wristTemp,
          bodyTempC: bodyTemp,
        );
        _temperatureDataController.add(temperatureData);
        // SILENT - no debug print for successful temperature data
      } else {
        debugPrint('⚠️ Temperature readings out of expected range');
      }
    } catch (e) {
      debugPrint('Error parsing temperature data: $e');
    }
  }

  /// Rileva pattern di temperatura in dati senza protocollo rigoroso
  void detectTemperaturePattern(List<int> data) {
    if (data.length >= 6) {
      final possibleTemp1 = ((data[0] << 8) | data[1]) / 10.0;
      final possibleTemp2 = ((data[2] << 8) | data[3]) / 10.0;
      final possibleTemp3 = ((data[4] << 8) | data[5]) / 10.0;
      
      if (possibleTemp1 >= 10 && possibleTemp1 <= 50 && 
          possibleTemp2 >= 10 && possibleTemp2 <= 50 && 
          possibleTemp3 >= 10 && possibleTemp3 <= 50) {
        final temperatureData = TemperatureData(
          ambientTempC: possibleTemp1,
          wristTempC: possibleTemp2,
          bodyTempC: possibleTemp3,
        );
        _temperatureDataController.add(temperatureData);
        debugPrint('Detected temperature pattern: $temperatureData');
      }
    }
  }

  void dispose() {
    _temperatureDataController.close();
  }
}
