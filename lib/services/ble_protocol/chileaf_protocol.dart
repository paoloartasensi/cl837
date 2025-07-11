import 'package:flutter/foundation.dart';

/// Gestisce il protocollo di comunicazione Chileaf BLE
/// Fornisce metodi per costruire frame, calcolare checksum e validare pacchetti
class ChileafProtocol {
  // Command codes from Chileaf BLE Protocol SDK v0.6
  static const int commandSpo2 = 0x37;
  static const int commandTemperature = 0x38;
  static const int commandSports = 0x15; // Real-time sports data notification
  static const int commandHealthData = 0x75; // Extended health data (discovered from logs)
  static const int commandAccelerometer = 0x0C; // High-frequency accelerometer/motion data

  /// Costruisce un frame di protocollo Chileaf
  static List<int> buildProtocolFrame(List<int> data) {
    // Chileaf protocol: [0xFF, Length, Command/Data..., Checksum]
    // Length = N + 4 (where N is data length, +4 for Head+Length+Checksum+padding)
    final length = data.length + 4;
    final frame = [0xFF, length, ...data];
    final checksum = calculateChecksum(frame.sublist(0, frame.length)); // Calculate from Head to Data
    frame.add(checksum);
    return frame;
  }

  /// Calcola il checksum secondo il protocollo SDK
  static int calculateChecksum(List<int> frameData) {
    // Calculate checksum according to SDK: XOR(0x3A, -sum(Head to Data))
    int sum = 0;
    for (int byte in frameData) {
      sum += byte;
    }
    int temp = sum & 0xFF;
    temp = (0 - temp) & 0xFF;
    temp ^= 0x3A;
    
    return temp & 0xFF;
  }

  /// Valida se un pacchetto è un frame Chileaf valido
  static bool isValidChileafFrame(List<int> data) {
    if (data.length < 4) return false;
    if (data[0] != 0xFF) return false;
    
    final expectedLength = data[1];
    if (data.length != expectedLength) return false;
    
    return true;
  }

  /// Estrae il comando da un frame Chileaf
  static int? extractCommand(List<int> data) {
    if (!isValidChileafFrame(data)) return null;
    if (data.length < 3) return null;
    return data[2];
  }

  /// Ottiene il nome del comando per debug
  static String getCommandName(int command) {
    switch (command) {
      case commandSpo2:
        return 'SpO2 (0x37)';
      case commandTemperature:
        return 'Temperature (0x38)';
      case commandSports:
        return 'Sports (0x15)';
      case commandHealthData:
        return 'Health Data (0x75)';
      case commandAccelerometer:
        return 'Accelerometer (0x0C)';
      default:
        return 'Unknown (0x${command.toRadixString(16)})';
    }
  }

  /// Verifica se un comando contiene potenzialmente dati SpO2
  static bool commandContainsSpO2Data(int command) {
    return command == commandSpo2 || command == commandHealthData;
  }

  /// Verifica se un comando è di tipo high-frequency (accelerometro)
  static bool isHighFrequencyCommand(int command) {
    return command == commandAccelerometer;
  }

  /// Logga i dettagli di un frame per debug
  static void logFrameDetails(List<int> data) {
    if (data.isEmpty) {
      debugPrint('📦 Empty frame');
      return;
    }

    final isValid = isValidChileafFrame(data);
    final command = extractCommand(data);
    
    debugPrint('📦 Frame Analysis:');
    debugPrint('   Raw: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    debugPrint('   Valid: $isValid');
    debugPrint('   Length: ${data.length} bytes');
    
    if (command != null) {
      debugPrint('   Command: ${getCommandName(command)}');
      debugPrint('   Contains SpO2: ${commandContainsSpO2Data(command)}');
      debugPrint('   High Frequency: ${isHighFrequencyCommand(command)}');
    }
  }
}
