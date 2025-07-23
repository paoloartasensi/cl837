import 'dart:math' as math;

class HRVData {
  final List<double> rrIntervals;
  final double rmssd;
  final double sdnn;
  final double pNN50;  // Percentuale di intervalli NN consecutivi che differiscono > 50ms
  final double meanRR;
  final double medianRR;
  final double estimatedHR;  // Frequenza cardiaca stimata dagli RR
  final DateTime timestamp;

  HRVData({
    required this.rrIntervals,
    DateTime? timestamp,
  }) : 
    rmssd = _calculateRMSSD(rrIntervals),
    sdnn = _calculateSDNN(rrIntervals),
    pNN50 = _calculatePNN50(rrIntervals),
    meanRR = rrIntervals.isNotEmpty ? rrIntervals.reduce((a, b) => a + b) / rrIntervals.length : 0,
    medianRR = _calculateMedian(rrIntervals),
    estimatedHR = rrIntervals.isNotEmpty ? 60000 / (rrIntervals.reduce((a, b) => a + b) / rrIntervals.length) : 0,
    timestamp = timestamp ?? DateTime.now();

  static double _calculateRMSSD(List<double> intervals) {
    if (intervals.length < 2) return 0;
    
    double sumSquaredDifferences = 0;
    for (int i = 1; i < intervals.length; i++) {
      double diff = intervals[i] - intervals[i - 1];
      sumSquaredDifferences += diff * diff;
    }
    
    return (sumSquaredDifferences / (intervals.length - 1)).sqrt();
  }

  static double _calculateSDNN(List<double> intervals) {
    if (intervals.isEmpty) return 0;
    
    double mean = intervals.reduce((a, b) => a + b) / intervals.length;
    double sumSquaredDeviations = intervals
        .map((interval) => (interval - mean) * (interval - mean))
        .reduce((a, b) => a + b);
    
    return (sumSquaredDeviations / intervals.length).sqrt();
  }

  static double _calculateMedian(List<double> intervals) {
    if (intervals.isEmpty) return 0;
    
    List<double> sorted = List.from(intervals)..sort();
    int middle = sorted.length ~/ 2;
    
    if (sorted.length % 2 == 0) {
      return (sorted[middle - 1] + sorted[middle]) / 2;
    } else {
      return sorted[middle];
    }
  }

  static double _calculatePNN50(List<double> intervals) {
    if (intervals.length < 2) return 0;
    
    int count = 0;
    for (int i = 1; i < intervals.length; i++) {
      double diff = (intervals[i] - intervals[i - 1]).abs();
      if (diff > 50) {
        count++;
      }
    }
    
    // Restituisce la percentuale (0-100)
    return (count / (intervals.length - 1)) * 100;
  }

  String get hrvQuality {
    // Debug per capire il problema della durata
    double duration = samplingDurationSeconds;
    
    // Prima verifica la durata del campionamento (standard clinico)
    if (duration < 60) {
      // Aggiungo debug per capire perché la durata è insufficiente
      return 'Insufficient Duration (${duration.toStringAsFixed(1)}s)'; 
    }
    
    // Basato sugli standard Elite HRV (media ~59.3ms RMSSD)
    // Aggiustato per essere più realistico con i valori reali
    if (rmssd < 15) return 'Very Poor';  // Stress severo/malattia
    if (rmssd < 30) return 'Poor';       // Sotto la media, stress
    if (rmssd < 50) return 'Fair';       // Leggermente sotto la media
    if (rmssd < 70) return 'Good';       // Sopra la media (>59.3)
    return 'Excellent';                  // Ottima forma fisica
  }

  // Valida se gli RR intervals sono realistici e sufficienti per analisi HRV
  bool get isDataValid {
    if (rrIntervals.length < 2) return false;
    
    // Controllo durata minima: almeno 1 minuto di campionamento (come Elite HRV)
    double totalDurationMs = rrIntervals.reduce((a, b) => a + b);
    if (totalDurationMs < 60000) return false; // 60 secondi = 60,000 ms
    
    // Controllo numero minimo di battiti per 1 minuto (almeno 40 battiti)
    if (rrIntervals.length < 40) return false;
    
    // Controlla se i valori RR sono nel range fisiologico
    for (double rr in rrIntervals) {
      if (rr < 300 || rr > 2000) return false;  // 30-200 BPM range
    }
    
    // Controlla se la variabilità è troppo estrema (possibile errore sensore)
    double maxRR = rrIntervals.reduce((a, b) => a > b ? a : b);
    double minRR = rrIntervals.reduce((a, b) => a < b ? a : b);
    double rrRange = maxRR - minRR;
    
    // Se la variazione è > 50% della media, potrebbe essere un errore
    return rrRange < (meanRR * 0.5);
  }

  // Categoria basata sulla HR stimata
  String get hrCategory {
    if (estimatedHR < 50) return 'Bradycardia';
    if (estimatedHR < 60) return 'Athletic';
    if (estimatedHR < 100) return 'Normal';
    if (estimatedHR < 150) return 'Elevated';
    return 'Tachycardia';
  }

  // Durata del campionamento in secondi
  double get samplingDurationSeconds {
    if (rrIntervals.isEmpty) return 0;
    // La durata totale è la somma di tutti gli RR intervals (tempo totale del campionamento)
    double totalMs = rrIntervals.reduce((a, b) => a + b);
    return totalMs / 1000.0;
  }

  // Durata formattata per display
  String get samplingDurationFormatted {
    double seconds = samplingDurationSeconds;
    if (seconds < 60) {
      return '${seconds.toStringAsFixed(1)}s';
    } else {
      int minutes = (seconds / 60).floor();
      int remainingSeconds = (seconds % 60).round();
      return '${minutes}m ${remainingSeconds}s';
    }
  }

  @override
  String toString() {
    return 'HRV - RMSSD: ${rmssd.toStringAsFixed(1)}ms ($hrvQuality), SDNN: ${sdnn.toStringAsFixed(1)}ms, pNN50: ${pNN50.toStringAsFixed(1)}%, Mean RR: ${meanRR.toStringAsFixed(1)}ms (${estimatedHR.toStringAsFixed(0)} BPM), Duration: $samplingDurationFormatted (${rrIntervals.length} intervals)';
  }
}

// Extension to add sqrt method to double
extension DoubleExtension on double {
  double sqrt() => math.sqrt(this);
}
