import 'package:flutter/foundation.dart';
import 'chileaf_protocol.dart';

/// Costruttore di comandi per il protocollo Chileaf
/// Fornisce metodi per creare comandi specifici del dispositivo
class CommandBuilder {
  
  /// Crea comando per richiedere dati sportivi
  static List<int> buildSportsDataRequest() {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandSports]);
  }

  /// Crea comando per richiedere dati di temperatura
  static List<int> buildTemperatureDataRequest() {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandTemperature]);
  }

  /// Crea comando per abilitare modalità SpO2
  static List<int> buildEnableSpO2Mode() {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandSpo2, 0x01]);
  }

  /// Crea comando per disabilitare modalità SpO2
  static List<int> buildDisableSpO2Mode() {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandSpo2, 0x00]);
  }

  /// Crea comando per interrogare stato SpO2
  static List<int> buildSpO2StatusInquiry() {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandSpo2, 0x02]);
  }

  /// Crea comando SpO2 semplice (senza parametri)
  static List<int> buildSimpleSpO2Command() {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandSpo2]);
  }

  /// Crea comando personalizzato
  static List<int> buildCustomCommand(int command, [List<int>? parameters]) {
    final commandData = [command];
    if (parameters != null) {
      commandData.addAll(parameters);
    }
    return ChileafProtocol.buildProtocolFrame(commandData);
  }

  /// Crea frame manuale per test (bypass del builder automatico)
  static List<int> buildManualFrame(List<int> frameData) {
    // For advanced testing - constructs frame manually
    // User is responsible for correct format
    return frameData;
  }

  /// Crea comando SpO2 con parametro personalizzato
  static List<int> buildSpO2CommandWithParameter(int parameter) {
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandSpo2, parameter]);
  }

  /// Costruisce comando per richiedere storico esercizio (7 giorni)
  static List<int> buildExerciseHistoryRequest() {
    debugPrint('🏗️ Building Exercise History Request (0x16)');
    return ChileafProtocol.buildProtocolFrame([0x16]);
  }

  /// Costruisce comando per richiedere lista storico HR
  static List<int> buildHRHistoryListRequest() {
    debugPrint('🏗️ Building HR History List Request (0x21)');
    return ChileafProtocol.buildProtocolFrame([0x21]);
  }

  /// Costruisce comando per richiedere dati storico HR con timestamp
  static List<int> buildHRHistoryDataRequest(int utcTimestamp) {
    debugPrint('🏗️ Building HR History Data Request (0x22) for timestamp: $utcTimestamp');
    List<int> payload = [0x22];
    
    // Aggiungi timestamp UTC (4 bytes, little endian)
    payload.add(utcTimestamp & 0xFF);
    payload.add((utcTimestamp >> 8) & 0xFF);
    payload.add((utcTimestamp >> 16) & 0xFF);
    payload.add((utcTimestamp >> 24) & 0xFF);
    
    return ChileafProtocol.buildProtocolFrame(payload);
  }

  /// Ottiene lista di tutti i comandi disponibili per test
  static Map<String, List<int>> getAllTestCommands() {
    return {
      'sportsData': buildSportsDataRequest(),
      'temperatureData': buildTemperatureDataRequest(),
      'enableSpO2': buildEnableSpO2Mode(),
      'disableSpO2': buildDisableSpO2Mode(),
      'inquireSpO2': buildSpO2StatusInquiry(),
      'simpleSpO2': buildSimpleSpO2Command(),
      'exerciseHistory': buildExerciseHistoryRequest(),
      'hrHistoryList': buildHRHistoryListRequest(),
      'hrHistoryData': buildHRHistoryDataRequest(DateTime.now().millisecondsSinceEpoch ~/ 1000),
    };
  }
}
