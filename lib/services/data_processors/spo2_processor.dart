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
      final status = data[3];
      final spo2Value = data[4];
      final correctPosture = data[5];
      final piSignalQuality = data[6];
      final isWearing = data[7];

      debugPrint('SPO2: status=$status, value=$spo2Value, correctPosture=$correctPosture, piSignalQuality=$piSignalQuality, isWearing=$isWearing');

      // 🔬 ENHANCED ANALYSIS: Distinguish between status and actual readings
      if (spo2Value >= 70 && spo2Value <= 100) {
        debugPrint('🔬 ANALYSIS: This appears to be ACTUAL SpO2 measurement data');

        // SEMPRE invia i dati al UI per feedback in tempo reale
        final spo2Data = SpO2Data(
          status: status,
          value: spo2Value,
          gesture: correctPosture,
          piValue: piSignalQuality,
          onWrist: isWearing,
        );
        _spo2DataController.add(spo2Data);

        // Determinazione se il dato è valido (per logging)
        if (spo2Data.isDeviceReady) {
          debugPrint('✅ Valid SpO2 Data: $spo2Value%');
        } else {
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
        debugPrint(
            '🔬 ANALYSIS: Unexpected SpO2 value: $spo2Value (not typical range 70-100%)');

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
    } catch (e) {
      debugPrint('Error parsing SPO2 data: $e');
    }
  }

  void dispose() {
    _spo2DataController.close();
  }
}
