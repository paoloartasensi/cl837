import 'dart:math' as math;

class HRVData {
  final List<double> rrIntervals;
  final double rmssd;
  final double sdnn;
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

  String get hrvQuality {
    // Basato sugli standard Elite HRV (media ~59.3ms RMSSD)
    // Aggiustato per essere più realistico con i valori reali
    if (rmssd < 15) return 'Very Poor';  // Stress severo/malattia
    if (rmssd < 30) return 'Poor';       // Sotto la media, stress
    if (rmssd < 50) return 'Fair';       // Leggermente sotto la media
    if (rmssd < 70) return 'Good';       // Sopra la media (>59.3)
    return 'Excellent';                  // Ottima forma fisica
  }

  // Valida se gli RR intervals sono realistici
  bool get isDataValid {
    if (rrIntervals.length < 2) return false;
    
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

  @override
  String toString() {
    return 'HRV - RMSSD: ${rmssd.toStringAsFixed(1)}ms ($hrvQuality), SDNN: ${sdnn.toStringAsFixed(1)}ms, Mean RR: ${meanRR.toStringAsFixed(1)}ms (${estimatedHR.toStringAsFixed(0)} BPM)';
  }
}

// Extension to add sqrt method to double
extension DoubleExtension on double {
  double sqrt() => math.sqrt(this);
}
