import 'package:flutter/foundation.dart';

/// Processore specializzato per i dati dell'accelerometro
/// Gestisce l'analisi dei dati di movimento/accelerazione 3D
class AccelerometerProcessor {
  int _accelerometerLogCounter = 0;
  static const int _accelerometerLogInterval = 100; // Log every 100th packet

  /// Processa i dati dell'accelerometro dal comando 0x0C
  void processAccelerometerData(List<int> data) {
    // Command 0x0C - High-frequency accelerometer/motion data
    // Format: 0xff 0x0a 0x0c [motion data bytes] 0x0e [checksum]
    // Length: 10 bytes total
    
    _accelerometerLogCounter++;
    
    // SILENT processing - no debug print for accelerometer data
    
    if (data.length < 10) {
      return; // Invalid packet length
    }
    
    try {
      // Analyze data patterns:
      // Bytes 3-7 seem to contain motion/accelerometer values
      // Based on log patterns, these values change frequently
      
      final byte3 = data[3]; // Motion/acceleration X?
      final byte4 = data[4]; // Motion/acceleration Y?
      final byte5 = data[5]; // Motion/acceleration Z?
      final byte6 = data[6]; // Additional motion data
      final byte7 = data[7]; // Additional motion data
      
      // Log detailed analysis occasionally
      if (_accelerometerLogCounter % (_accelerometerLogInterval * 10) == 0) {
        debugPrint('📊 Accelerometer Pattern Analysis:');
        debugPrint('   Byte 3 (X?): 0x${byte3.toRadixString(16)} ($byte3)');
        debugPrint('   Byte 4 (Y?): 0x${byte4.toRadixString(16)} ($byte4)');  
        debugPrint('   Byte 5 (Z?): 0x${byte5.toRadixString(16)} ($byte5)');
        debugPrint('   Byte 6: 0x${byte6.toRadixString(16)} ($byte6)');
        debugPrint('   Byte 7: 0x${byte7.toRadixString(16)} ($byte7)');
        debugPrint('   Full packet: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
        
        // ⚠️ IMPORTANT NOTE: Values 80-100 in accelerometer data are NOT SpO2!
        _warnAboutFalseSpO2Values(data);
      }
      
      // Note: This data is very high frequency (many packets per second)
      // It's likely raw accelerometer data that could be used for:
      // - Step counting refinement
      // - Fall detection
      // - Activity recognition
      // - Motion artifact detection for other sensors
      
    } catch (e) {
      debugPrint('Error parsing accelerometer data: $e');
    }
  }

  /// Avverte sui falsi positivi SpO2 nei dati accelerometro
  void _warnAboutFalseSpO2Values(List<int> data) {
    List<int> suspiciousValues = [];
    for (int i = 3; i < data.length; i++) {
      if (data[i] >= 80 && data[i] <= 100) {
        suspiciousValues.add(data[i]);
      }
    }
    
    if (suspiciousValues.isNotEmpty) {
      debugPrint('⚠️ ACCELEROMETER WARNING: Found values that could be mistaken for SpO2: $suspiciousValues');
      debugPrint('⚠️ These are MOTION VALUES, NOT SpO2 readings!');
      debugPrint('⚠️ Real SpO2 data comes from command 0x75, not 0x0C');
    }
  }

  /// Reset del contatore per test
  void resetCounter() {
    _accelerometerLogCounter = 0;
  }

  /// Ottiene statistiche del processore
  Map<String, dynamic> getStats() {
    return {
      'packetsProcessed': _accelerometerLogCounter,
      'logInterval': _accelerometerLogInterval,
    };
  }
}
