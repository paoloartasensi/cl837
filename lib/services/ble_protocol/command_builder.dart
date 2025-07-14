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

  // SpO2 Commands (Protocol v0.6 - Command 0x37)
  
  /// Abilita modalità SpO2 (0x37 con parametro 0x01)
  static List<int> buildEnableSpO2Mode() {
    debugPrint('🏗️ Building Enable SpO2 Mode (0x37, 0x01)');
    return ChileafProtocol.buildProtocolFrame([0x37, 0x01]);
  }

  /// Disabilita modalità SpO2 (0x37 con parametro 0x00)
  static List<int> buildDisableSpO2Mode() {
    debugPrint('🏗️ Building Disable SpO2 Mode (0x37, 0x00)');
    return ChileafProtocol.buildProtocolFrame([0x37, 0x00]);
  }

  /// Richiede stato SpO2 (0x37 con parametro 0x02)
  static List<int> buildSpO2StatusInquiry() {
    debugPrint('🏗️ Building SpO2 Status Inquiry (0x37, 0x02)');
    return ChileafProtocol.buildProtocolFrame([0x37, 0x02]);
  }

  /// Comando SpO2 semplice (solo 0x37)
  static List<int> buildSimpleSpO2Command() {
    debugPrint('🏗️ Building Simple SpO2 Command (0x37)');
    return ChileafProtocol.buildProtocolFrame([0x37]);
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

  // Device Information Commands (Protocol v0.6)
  
  /// Richiede informazioni del dispositivo (0x01)
  static List<int> buildDeviceInfoCommand() {
    debugPrint('🏗️ Building Device Info Request (0x01)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandDeviceInfo]);
  }

  /// Richiede livello batteria (0x02)
  static List<int> buildBatteryLevelCommand() {
    debugPrint('🏗️ Building Battery Level Request (0x02)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandBatteryLevel]);
  }

  /// Richiede versione firmware (0x03)
  static List<int> buildFirmwareVersionCommand() {
    debugPrint('🏗️ Building Firmware Version Request (0x03)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandFirmwareVersion]);
  }

  /// Richiede versione hardware (0x04)
  static List<int> buildHardwareVersionCommand() {
    debugPrint('🏗️ Building Hardware Version Request (0x04)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandHardwareVersion]);
  }

  /// Richiede nome dispositivo (0x05)
  static List<int> buildDeviceNameCommand() {
    debugPrint('🏗️ Building Device Name Request (0x05)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandDeviceName]);
  }

  /// Richiede indirizzo MAC (0x06)
  static List<int> buildMacAddressCommand() {
    debugPrint('🏗️ Building MAC Address Request (0x06)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandMacAddress]);
  }

  // Rope Skipping Commands (Protocol v0.6)
  
  /// Imposta modalità rope skipping (0x42)
  static List<int> buildSetRopeModeCommand(int mode) {
    debugPrint('🏗️ Building Set Rope Mode (0x42) with mode: $mode');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandRopeSetMode, mode]);
  }

  /// Cancella dati rope skipping (0x45)
  static List<int> buildClearRopeDataCommand() {
    debugPrint('🏗️ Building Clear Rope Data (0x45)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandRopeClearData]);
  }

  /// Richiede stato rope skipping (0x40)
  static List<int> buildRopeStatusCommand() {
    debugPrint('🏗️ Building Rope Status Request (0x40)');
    return ChileafProtocol.buildProtocolFrame([ChileafProtocol.commandRopeStatus]);
  }

  /// Ottiene lista di tutti i comandi disponibili per test
  static Map<String, List<int>> getAllTestCommands() {
    return {
      // Device Information
      'deviceInfo': buildDeviceInfoCommand(),
      'batteryLevel': buildBatteryLevelCommand(),
      'firmwareVersion': buildFirmwareVersionCommand(),
      'hardwareVersion': buildHardwareVersionCommand(),
      'deviceName': buildDeviceNameCommand(),
      'macAddress': buildMacAddressCommand(),
      // Data Commands
      'sportsData': buildSportsDataRequest(),
      'temperatureData': buildTemperatureDataRequest(),
      // SpO2 Commands
      'enableSpO2': buildEnableSpO2Mode(),
      'disableSpO2': buildDisableSpO2Mode(),
      'inquireSpO2': buildSpO2StatusInquiry(),
      'simpleSpO2': buildSimpleSpO2Command(),
      // Historical Data
      'exerciseHistory': buildExerciseHistoryRequest(),
      'hrHistoryList': buildHRHistoryListRequest(),
      'hrHistoryData': buildHRHistoryDataRequest(DateTime.now().millisecondsSinceEpoch ~/ 1000),
      // Rope Skipping
      'ropeStatus': buildRopeStatusCommand(),
      'setRopeMode': buildSetRopeModeCommand(1), // Default mode 1
      'clearRopeData': buildClearRopeDataCommand(),
    };
  }
}
