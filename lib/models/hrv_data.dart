import 'dart:math' as math;

class HRVData {
  final List<double> rrIntervals;
  final double rmssd;
  final double sdnn;
  final double meanRR;
  final double medianRR;
  final DateTime timestamp;

  HRVData({
    required this.rrIntervals,
    DateTime? timestamp,
  }) : 
    rmssd = _calculateRMSSD(rrIntervals),
    sdnn = _calculateSDNN(rrIntervals),
    meanRR = rrIntervals.isNotEmpty ? rrIntervals.reduce((a, b) => a + b) / rrIntervals.length : 0,
    medianRR = _calculateMedian(rrIntervals),
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
    if (rmssd < 20) return 'Poor';
    if (rmssd < 40) return 'Fair';
    if (rmssd < 60) return 'Good';
    return 'Excellent';
  }

  @override
  String toString() {
    return 'HRV - RMSSD: ${rmssd.toStringAsFixed(1)}ms ($hrvQuality), SDNN: ${sdnn.toStringAsFixed(1)}ms, Mean RR: ${meanRR.toStringAsFixed(1)}ms';
  }
}

// Extension to add sqrt method to double
extension DoubleExtension on double {
  double sqrt() => math.sqrt(this);
}
