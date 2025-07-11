import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../ble_protocol/command_builder.dart';

/// Diagnostica e test per funzionalità SpO2
/// Fornisce metodi per testare il LED e i comandi SpO2
class SpO2Diagnostics {
  final BluetoothCharacteristic? _rxCharacteristic;

  SpO2Diagnostics(this._rxCharacteristic);

  /// Test funzionalità LED SpO2
  Future<void> testLEDFunctionality() async {
    debugPrint('🚨 Iniziando test funzionalità LED...');
    
    try {
      // Step 1: Assicuriamoci che siamo in modalità normale (LED spento)
      await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      debugPrint('🚨 LED dovrebbe essere SPENTO, attendere 2 secondi...');
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 2: Accendiamo il LED
      await _sendCommand(CommandBuilder.buildEnableSpO2Mode());
      debugPrint('🚨 LED dovrebbe essere ACCESO (ROSSO), attendere 3 secondi...');
      await Future.delayed(const Duration(seconds: 3));
      
      // Step 3: Test comando inquiry
      debugPrint('🚨 Testing SpO2 inquiry command...');
      await _sendCommand(CommandBuilder.buildSpO2StatusInquiry());
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 4: Spegniamo il LED
      await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      debugPrint('🚨 LED dovrebbe essere di nuovo SPENTO');
      
      debugPrint('✅ Test LED completato con successo');
    } catch (e) {
      debugPrint('❌ Test LED fallito: $e');
      
      // Tenta di recuperare lo stato
      try {
        await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      } catch (_) {}
    }
  }

  /// Test formati comandi SpO2 diversi
  Future<void> testSpO2CommandFormats() async {
    debugPrint('🧪 Testing different SpO2 command formats...');
    
    try {
      // Test 1: Frame di protocollo corretto
      debugPrint('🧪 Test 1: Corrected protocol frame for SpO2 ENABLE');
      await _sendCommand(CommandBuilder.buildEnableSpO2Mode());
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🧪 Test 1b: Corrected protocol frame for SpO2 INQUIRY');
      await _sendCommand(CommandBuilder.buildSpO2StatusInquiry());
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🧪 Test 1c: Corrected protocol frame for SpO2 EXIT');
      await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Test 2: Costruzione frame manuale
      debugPrint('🧪 Test 2: Manual frame construction');
      final manualFrame = [0xFF, 0x06, 0x37, 0x01];
      int sum = manualFrame.reduce((a, b) => a + b);
      int checksum = ((0 - sum) & 0xFF) ^ 0x3A;
      manualFrame.add(checksum);
      
      debugPrint('🧪 Manual frame: ${manualFrame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      await _sendRawCommand(manualFrame);
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🧪 SpO2 command format testing completed');
      
    } catch (e) {
      debugPrint('🧪 SpO2 command format testing error: $e');
    }
  }

  /// Test metodi SpO2 alternativi
  Future<void> testAlternativeSpO2Methods() async {
    try {
      debugPrint('🧪 Starting comprehensive SpO2 alternative testing...');
      
      // Prima, testa i formati di protocollo corretti
      await testSpO2CommandFormats();
      
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Metodo 1: Prova comando 0x37 semplice senza parametri
      debugPrint('🧪 Method 1: Simple 0x37 command');
      await _sendCommand(CommandBuilder.buildSimpleSpO2Command());
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Metodo 2: Prova valori di parametri diversi con protocollo corretto
      debugPrint('🧪 Method 2: 0x37 with different parameters (corrected protocol)');
      for (int param in [0x01, 0x02, 0x03, 0xFF]) {
        debugPrint('🧪 Trying parameter: 0x${param.toRadixString(16)}');
        await _sendCommand(CommandBuilder.buildSpO2CommandWithParameter(param));
        await Future.delayed(const Duration(milliseconds: 1500));
      }
      
      // Metodo 3: Prova altri possibili codici comando SpO2
      debugPrint('🧪 Method 3: Alternative command codes');
      for (int cmd in [0x36, 0x38, 0x39, 0x3A]) {
        debugPrint('🧪 Trying command: 0x${cmd.toRadixString(16)}');
        try {
          await _sendCommand(CommandBuilder.buildCustomCommand(cmd, [0x01]));
          await Future.delayed(const Duration(milliseconds: 1500));
        } catch (e) {
          debugPrint('🧪 Command 0x${cmd.toRadixString(16)} failed: $e');
        }
      }
      
      debugPrint('🧪 Alternative methods completed');
      
    } catch (e) {
      debugPrint('🧪 Alternative SpO2 methods error: $e');
    }
  }

  /// Misurazione SpO2 completa on-demand
  Future<void> performSpO2Measurement() async {
    try {
      debugPrint('🫁 Starting on-demand SpO2 measurement...');
      
      // Step 0: Prima assicuriamoci di NON essere in modalità SpO2
      await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Step 1: Entra in modalità SpO2 (LED rosso si accende)
      await _sendCommand(CommandBuilder.buildEnableSpO2Mode());
      debugPrint('🫁 SpO2 mode enabled, LED should be RED, stabilizing...');
      
      // Step 2: Attendi stabilizzazione (importante per lettura accurata)
      debugPrint('🫁 Waiting 4 seconds for stabilization...');
      await Future.delayed(const Duration(milliseconds: 4000));
      
      // Step 3: Richiedi stato SpO2 più volte per migliore accuratezza
      debugPrint('🫁 Phase 1: Requesting SpO2 status...');
      await _sendCommand(CommandBuilder.buildSpO2StatusInquiry());
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🫁 Phase 2: Second SpO2 inquiry...');
      await _sendCommand(CommandBuilder.buildSpO2StatusInquiry());
      await Future.delayed(const Duration(milliseconds: 2000));
      
      debugPrint('🫁 Phase 3: Final SpO2 inquiry...');
      await _sendCommand(CommandBuilder.buildSpO2StatusInquiry());
      await Future.delayed(const Duration(milliseconds: 3000));
      
      debugPrint('🫁 SpO2 measurement requests sent, checking for responses...');
      
      // Step 4: IMPORTANTE - Esci dalla modalità SpO2 per spegnere LED
      debugPrint('🫁 Exiting SpO2 mode...');
      await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
      debugPrint('🫁 SpO2 mode exited, LED should turn OFF');
      
    } catch (e) {
      debugPrint('🫁 Error in SpO2 measurement: $e');
      // In caso di errore, assicuriamoci comunque di uscire dalla modalità SpO2
      try {
        await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
        debugPrint('🫁 Emergency SpO2 mode exit completed');
      } catch (exitError) {
        debugPrint('🫁 Failed to exit SpO2 mode: $exitError');
      }
      rethrow;
    }
  }

  /// Forza uscita dalla modalità SpO2 se il dispositivo si blocca
  Future<void> forceExitSpO2Mode() async {
    try {
      debugPrint('🚨 Force exiting SpO2 mode...');
      
      // Prova più volte per assicurare l'uscita
      for (int i = 0; i < 3; i++) {
        await _sendCommand(CommandBuilder.buildDisableSpO2Mode());
        await Future.delayed(const Duration(milliseconds: 500));
        debugPrint('🚨 Exit attempt ${i + 1}/3');
      }
      
      debugPrint('🚨 Force exit completed - LED should be OFF');
    } catch (e) {
      debugPrint('🚨 Force exit error: $e');
    }
  }

  /// Invia comando con gestione errori
  Future<void> _sendCommand(List<int> frame) async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }

    debugPrint('📡 Sending command: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    try {
      if (_rxCharacteristic!.properties.writeWithoutResponse) {
        await _rxCharacteristic!.write(frame, withoutResponse: true);
      } else {
        await _rxCharacteristic!.write(frame, withoutResponse: false);
      }
      await Future.delayed(const Duration(milliseconds: 100));
    } catch (e) {
      debugPrint('❌ Command send failed: $e');
      throw Exception('Command sending failed: $e');
    }
  }

  /// Invia comando raw senza wrapper
  Future<void> _sendRawCommand(List<int> frame) async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }

    debugPrint('📡 Sending RAW command: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    try {
      await _rxCharacteristic!.write(frame, withoutResponse: true);
      await Future.delayed(const Duration(milliseconds: 100));
    } catch (e) {
      debugPrint('❌ RAW command send failed: $e');
      throw Exception('RAW command sending failed: $e');
    }
  }
}
