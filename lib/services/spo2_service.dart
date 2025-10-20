import 'dart:async';
import 'dart:developer' as developer;
import '../models/spo2_data.dart';

/// Servizio per la gestione delle letture SpO2 con controllo qualità avanzato
class SpO2Service {
  static const int minPiValue = 8;  // Soglia minima PI per affidabilità
  static const int criticalSpO2Threshold = 90;
  static const int lowSpO2Threshold = 95;
  static const int minReliableReadings = 3;
  static const int maxReadingsHistory = 10;

  final StreamController<SpO2Data> _spO2Controller = StreamController<SpO2Data>.broadcast();
  final StreamController<String> _alertController = StreamController<String>.broadcast();
  
  final List<SpO2Data> _recentReadings = [];
  
  Stream<SpO2Data> get spO2Stream => _spO2Controller.stream;
  Stream<String> get alertStream => _alertController.stream;

  /// Elabora i dati SpO2 grezzi dal dispositivo CL837
  /// Implementa filtri di qualità per evitare letture inaffidabili
  void parseSpO2Data(List<int> data) {
    if (data.length < 8) {
      developer.log("SpO2 data packet too short: ${data.length} bytes", name: 'SpO2Service');
      return;
    }

    // Estrai dati dal pacchetto (basato su analisi decompilazione)
    int status = data[3];      // Stato misurazione
    int spO2Value = data[4];    // Valore SpO2 (0-100%)
    int gesture = data[5];      // Postura polso (0=errata, 1=corretta)
    int piValue = data[6];      // Perfusion Index (0-255)
    int onWrist = data[7];      // Contatto polso (0=non indossato, 1=indossato)

    developer.log("SpO2 Raw Data: switch=$status, value=$spO2Value%, gesture=$gesture, PI=$piValue, onWrist=$onWrist", name: 'SpO2Service');

    // Validazione qualità del segnale
    bool isReliableReading = _validateSignalQuality(gesture, piValue, onWrist);
    
    SpO2Data spO2Data = SpO2Data(
      value: spO2Value,
      piValue: piValue,
      gesture: gesture,
      onWrist: onWrist,
      status: status,
      timestamp: DateTime.now(),
    );

    // Emetti il dato (UI può mostrare tutte le letture con indicatori qualità)
    _spO2Controller.add(spO2Data);

    if (isReliableReading) {
      // Solo letture affidabili vanno nell'analisi salute
      _addToHealthMonitoring(spO2Data);
      developer.log("SpO2 reliable reading accepted: $spO2Value% (PI: $piValue)", name: 'SpO2Service');
    } else {
      developer.log("SpO2 reading discarded: Poor signal quality (PI: $piValue, gesture: $gesture, onWrist: $onWrist)", name: 'SpO2Service');
    }
  }

  /// Valida se una lettura SpO2 è affidabile basandosi sui parametri di qualità
  bool _validateSignalQuality(int gesture, int piValue, int onWrist) {
    return gesture == 1 &&           // Postura corretta del polso
           piValue >= minPiValue && // Segnale forte abbastanza (PI >= 8)
           onWrist == 1;              // Dispositivo indossato correttamente
  }


  /// Aggiunge lettura affidabile al sistema di monitoraggio salute
  void _addToHealthMonitoring(SpO2Data reading) {
    _recentReadings.add(reading);
    
    // Mantieni solo le ultime letture per calcolo trend
    if (_recentReadings.length > maxReadingsHistory) {
      _recentReadings.removeAt(0);
    }
    
    _evaluateSpO2Trend();
  }

  /// Valuta il trend delle letture SpO2 e genera allarmi se necessario
  void _evaluateSpO2Trend() {
    if (_recentReadings.length < minReliableReadings) {
      developer.log("Not enough reliable readings for trend analysis: ${_recentReadings.length}", name: 'SpO2Service');
      return;
    }

    // Calcola media delle letture affidabili recenti
    double avgSpO2 = _recentReadings
        .map((r) => r.value)
        .reduce((a, b) => a + b) / _recentReadings.length;

    // Controlla se tutte le letture recenti sono basse (trend consistente)
    bool allReadingsLow = _recentReadings.every((r) => r.value < lowSpO2Threshold);
    bool allReadingsCritical = _recentReadings.every((r) => r.value < criticalSpO2Threshold);

    developer.log("SpO2 Trend Analysis: avg=${avgSpO2.toStringAsFixed(1)}%, readings=${_recentReadings.length}, allLow=$allReadingsLow", name: 'SpO2Service');

    // Genera allarmi solo su trend consistenti
    if (avgSpO2 < criticalSpO2Threshold && allReadingsCritical) {
      String alert = "🚨 ALLARME CRITICO: SpO2 ${avgSpO2.toStringAsFixed(1)}% - Consultare immediatamente un medico";
      _alertController.add(alert);
      developer.log("CRITICAL ALERT: $alert", name: 'SpO2Service', level: 1000);
    } else if (avgSpO2 < lowSpO2Threshold && allReadingsLow) {
      String warning = "⚠️ Avviso: SpO2 basso ${avgSpO2.toStringAsFixed(1)}% - Monitorare attentamente";
      _alertController.add(warning);
      developer.log("WARNING: $warning", name: 'SpO2Service', level: 900);
    }
  }

  /// Ottieni statistiche delle letture recenti
  SpO2Statistics? getRecentStatistics() {
    if (_recentReadings.isEmpty) return null;

    List<int> values = _recentReadings.map((r) => r.value).toList();
    values.sort();

    return SpO2Statistics(
      count: _recentReadings.length,
      average: values.reduce((a, b) => a + b) / values.length,
      minimum: values.first,
      maximum: values.last,
      median: values[values.length ~/ 2],
      latest: _recentReadings.last,
    );
  }

  /// Fornisce istruzioni per migliorare la qualità della misurazione
  List<String> getQualityImprovementTips(SpO2Data? currentReading) {
    if (currentReading == null || currentReading.isReliable) {
      return ["Misurazione ottimale in corso"];
    }

    List<String> tips = [];
    
    if (currentReading.onWrist == 0) {
      tips.add("• Indossa il dispositivo saldamente al polso");
    }
    
    if (currentReading.gesture == 0) {
      tips.add("• Mantieni il polso fermo e in posizione naturale");
      tips.add("• Evita movimenti durante la misurazione");
    }
    
    if (currentReading.piValue < minPiValue) {
      tips.add("• Posiziona il sensore correttamente sulla pelle");
      tips.add("• Assicurati che il dispositivo non sia troppo largo");
      tips.add("• Pulisci il sensore da sudore o sporco");
    }

    if (tips.isEmpty) {
      tips.add("• Attendi alcuni secondi per stabilizzare la misurazione");
    }

    return tips;
  }

  /// Pulisce lo storico delle letture
  void clearHistory() {
    _recentReadings.clear();
    developer.log("SpO2 reading history cleared", name: 'SpO2Service');
  }

  /// Rilascia le risorse
  void dispose() {
    _spO2Controller.close();
    _alertController.close();
    _recentReadings.clear();
  }
}

/// Statistiche aggregate delle letture SpO2
class SpO2Statistics {
  final int count;
  final double average;
  final int minimum;
  final int maximum;
  final int median;
  final SpO2Data latest;

  const SpO2Statistics({
    required this.count,
    required this.average,
    required this.minimum,
    required this.maximum,
    required this.median,
    required this.latest,
  });

  @override
  String toString() {
    return 'SpO2Stats(count: $count, avg: ${average.toStringAsFixed(1)}%, '
           'range: $minimum-$maximum%, median: $median%, latest: ${latest.value}%)';
  }
}
