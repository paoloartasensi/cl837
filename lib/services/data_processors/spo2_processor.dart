// ⚠️ DEPRECATO: Questo file usa il modello SpO2Data obsoleto
//
// Utilizzare invece il nuovo SpO2Service che implementa:
// 1. Validazione qualità segnale basata su PI, gesture, onWrist
// 2. Filtri per letture affidabili
// 3. Monitoraggio trend e allarmi intelligenti
//
// Per migrare: sostituire con SpO2Service.parseSpO2Data()

import 'dart:async';
import 'package:flutter/foundation.dart';
import '../../models/spo2_data.dart';

/// Processore specializzato per i dati SpO2
/// Gestisce l'analisi e l'estrazione dei valori SpO2 dai pacchetti BLE
class SpO2Processor {
  final StreamController<SpO2Data> _spo2DataController = StreamController<SpO2Data>.broadcast();

  /// Stream dei dati SpO2 processati
  Stream<SpO2Data> get spo2DataStream => _spo2DataController.stream;

  /// Processa i dati SpO2 dal comando 0x37 (SDK ufficiale)
  void processSPO2Data(List<int> data) {
    // SPO2 Mode-0x37 response format from SDK:
    
    if (data.length < 8) {
      debugPrint('SPO2 data too short: ${data.length} bytes');
      return;
    }

    try {
      // ===== ANALISI COMPLETA DEI DATI RAW =====
      debugPrint('🔍 ===== ANALISI COMPLETA SpO2 DATA =====');
      debugPrint('📦 Raw data (${data.length} bytes): ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      debugPrint('📦 Raw data (decimal): ${data.join(' ')}');
      
      final status = data[3];
      final spo2Value = data[4];
      final correctPosture = data[5];
      final piSignalQuality = data[6];
      final isWearing = data[7];

      // LOGGING DETTAGLIATO DI OGNI CAMPO
      debugPrint('🩸 ===== VALORI ESTRATTI =====');
      debugPrint('🔸 Status (byte 3): $status (0x${status.toRadixString(16)}) - ${status == 1 ? "AFFIDABILE" : "NON AFFIDABILE"}');
      debugPrint('🩸 SpO2 VALUE (byte 4): $spo2Value% (0x${spo2Value.toRadixString(16)}) - QUESTO È IL VALORE REALE');
      debugPrint('🤚 Postura (byte 5): $correctPosture (0x${correctPosture.toRadixString(16)}) - ${correctPosture == 1 ? "CORRETTA" : "ERRATA"}');
      debugPrint('📶 PI Signal (byte 6): $piSignalQuality (0x${piSignalQuality.toRadixString(16)}) - Qualità segnale');
      debugPrint('⌚ On Wrist (byte 7): $isWearing (0x${isWearing.toRadixString(16)}) - ${isWearing == 1 ? "INDOSSATO" : "NON INDOSSATO"}');
      
      // ANALISI DELLA VALIDITÀ
      bool isInNormalRange = spo2Value >= 70 && spo2Value <= 100;
      bool isHealthyRange = spo2Value >= 95 && spo2Value <= 100;
      bool isLowRange = spo2Value >= 90 && spo2Value < 95;
      bool isCriticalRange = spo2Value < 90;
      
      debugPrint('🔬 ===== ANALISI VALORE SpO2 =====');
      debugPrint('🩸 VALORE MISURATO: $spo2Value%');
      debugPrint('📊 Range normale (70-100%): ${isInNormalRange ? "✅ SÌ" : "❌ NO"}');
      debugPrint('💚 Range sano (95-100%): ${isHealthyRange ? "✅ SÌ" : "❌ NO"}');
      debugPrint('🟡 Range basso (90-94%): ${isLowRange ? "⚠️ SÌ" : "✅ NO"}');
      debugPrint('🔴 Range critico (<90%): ${isCriticalRange ? "🚨 SÌ" : "✅ NO"}');

      debugPrint('SPO2: status=$status, value=$spo2Value, correctPosture=$correctPosture, piSignalQuality=$piSignalQuality, isWearing=$isWearing');

      // 🔬 ENHANCED ANALYSIS: Distinguish between status and actual readings
      if (spo2Value >= 70 && spo2Value <= 100) {
        debugPrint('🔬 ANALYSIS: This appears to be ACTUAL SpO2 measurement data');
        debugPrint('✅ ===== DATO SpO2 VALIDO =====');
        debugPrint('🩸 SATURAZIONE OSSIGENO: $spo2Value%');
        
        // Interpretazione clinica del valore
        String interpretation = '';
        String alertLevel = '';
        if (spo2Value >= 95) {
          interpretation = 'NORMALE - Ossigenazione eccellente';
          alertLevel = '💚 VERDE';
        } else if (spo2Value >= 90) {
          interpretation = 'BASSA - Possibile ipossiemia lieve';
          alertLevel = '🟡 GIALLO';
        } else {
          interpretation = 'CRITICA - Ipossiemia severa, consultare medico';
          alertLevel = '🔴 ROSSO';
        }
        
        debugPrint('🏥 INTERPRETAZIONE CLINICA: $interpretation');
        debugPrint('🚨 LIVELLO ALLERTA: $alertLevel');

        // SEMPRE invia i dati al UI per feedback in tempo reale
        final spo2Data = SpO2Data(
          status: status,
          value: spo2Value,
          gesture: correctPosture,
          piValue: piSignalQuality,
          onWrist: isWearing,
        );
        
        // MOSTRA IL DETTAGLIO COMPLETO DEL VALORE SpO2
        debugPrint(spo2Data.toDetailedString());
        
        _spo2DataController.add(spo2Data);

        // Determinazione se il dato è valido (per logging)
        if (spo2Data.isDeviceReady) {
          debugPrint('✅ ===== MISURAZIONE AFFIDABILE =====');
          debugPrint('✅ Valid SpO2 Data: $spo2Value%');
          debugPrint('✅ Tutti i parametri sono corretti per una misurazione accurata');
          debugPrint('✅ Device indossato: ${spo2Data.isWearing}');
          debugPrint('✅ Postura corretta: ${spo2Data.correctWristPosture}');
          debugPrint('✅ Segnale buono: ${spo2Data.signalQuality >= 8}');
        } else {
          debugPrint('⚠️ ===== MISURAZIONE NON AFFIDABILE =====');
          debugPrint('⚠️ SpO2 value: $spo2Value% (VALORE NON AFFIDABILE)');
          
          // Feedback dettagliato per l'utente
          List<String> issues = [];
          if (!spo2Data.isWearing) issues.add('Device not detected on wrist');
          if (!spo2Data.correctWristPosture) issues.add('Turn wrist face up');
          if (spo2Data.signalQuality < 8) {
            issues.add('Stay very still (signal: ${spo2Data.signalQuality})');
          }

          final feedback = issues.join(' • ');
          debugPrint('⚠️ SpO2 measurement needs adjustment: $feedback');
        }
      } else {
        debugPrint('🔬 ===== VALORE SpO2 ANOMALO =====');
        debugPrint(
            '🔬 ANALYSIS: Unexpected SpO2 value: $spo2Value (not typical range 70-100%)');
        debugPrint('❌ Questo valore è fuori dal range normale di saturazione ossigeno');
        debugPrint('❌ Possibili cause: dispositivo non correttamente posizionato, movimento eccessivo, o errore del sensore');

        // Still send to UI but with null value to indicate error
        final spo2Data = SpO2Data(
          status: 0, // not ready?
          value: 0, // Use 0 instead of null or invalid value
          gesture: correctPosture,
          piValue: piSignalQuality,
          onWrist: isWearing,
        );
        _spo2DataController.add(spo2Data);
      }
      
      debugPrint('🔍 ===== FINE ANALISI SpO2 =====');
    } catch (e) {
      debugPrint('❌ Error parsing SPO2 data: $e');
    }
  }

  void dispose() {
    _spo2DataController.close();
  }
}
