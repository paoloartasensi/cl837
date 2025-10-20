/// Readiness Score Calculator
/// 
/// Calculates overall readiness score (0-100) based on:
/// - Sleep quality (50% weight)
/// - HRV recovery (30% weight)
/// - Resting heart rate (20% weight)
/// 
/// Similar to Whoop Recovery, Oura Readiness

import '../models/sleep_score.dart';
import '../models/hrv_data.dart';

class ReadinessCalculator {
  /// Calculate comprehensive readiness score
  /// 
  /// Returns 0-100 score indicating how ready the body is for exertion
  /// - 90-100: Peak readiness - go for it!
  /// - 70-89: Good readiness - normal activity
  /// - 50-69: Moderate - take it easier
  /// - <50: Low - prioritize recovery
  ReadinessScore calculateReadiness({
    required SleepScore? sleepScore,
    HRVData? hrvData,
    double? restingHeartRate,
    double? baselineRestingHR,
  }) {
    // Component scores
    final sleepComponent = _calculateSleepComponent(sleepScore);
    final hrvComponent = _calculateHRVComponent(hrvData);
    final hrComponent = _calculateHRComponent(restingHeartRate, baselineRestingHR);

    // Weighted total (sleep is most important)
    final totalScore = (sleepComponent * 0.5) + 
                      (hrvComponent * 0.3) + 
                      (hrComponent * 0.2);

    return ReadinessScore(
      totalScore: totalScore,
      sleepComponent: sleepComponent,
      hrvComponent: hrvComponent,
      hrComponent: hrComponent,
      sleepScore: sleepScore,
      hrvData: hrvData,
      restingHR: restingHeartRate,
      calculatedAt: DateTime.now(),
    );
  }

  /// Calculate sleep contribution to readiness (0-100)
  /// Based directly on sleep score
  double _calculateSleepComponent(SleepScore? sleepScore) {
    if (sleepScore == null) {
      return 50.0; // Neutral if no data
    }

    // Sleep score already 0-100, use directly
    // But add bonus for excellent sleep, penalty for poor sleep
    double component = sleepScore.totalScore;

    // Bonus for excellent deep sleep
    if (sleepScore.deepSleepPercentage >= 20) {
      component += 5;
    }

    // Penalty for low efficiency
    if (sleepScore.sleepEfficiency < 80) {
      component -= 10;
    }

    // Penalty for many awakenings
    if (sleepScore.awakenings > 5) {
      component -= 5;
    }

    return component.clamp(0.0, 100.0);
  }

  /// Calculate HRV contribution to readiness (0-100)
  /// 
  /// HRV (Heart Rate Variability) indicates nervous system recovery
  /// Higher HRV = better recovery/readiness
  /// Lower HRV = body still recovering
  double _calculateHRVComponent(HRVData? hrvData) {
    if (hrvData == null) {
      return 50.0; // Neutral if no data
    }

    // Typical HRV ranges (SDNN in ms):
    // Excellent: >100ms
    // Good: 50-100ms
    // Fair: 20-50ms
    // Poor: <20ms

    final sdnn = hrvData.sdnn;
    double component;

    if (sdnn >= 100) {
      component = 100.0; // Excellent HRV
    } else if (sdnn >= 50) {
      // Linear interpolation 50-100ms → 70-100 score
      component = 70 + ((sdnn - 50) / 50 * 30);
    } else if (sdnn >= 20) {
      // Linear interpolation 20-50ms → 40-70 score
      component = 40 + ((sdnn - 20) / 30 * 30);
    } else {
      // Below 20ms → 0-40 score
      component = (sdnn / 20 * 40).clamp(0.0, 40.0);
    }

    // Bonus for high RMSSD (parasympathetic activity)
    if (hrvData.rmssd > 50) {
      component += 10;
    }

    return component.clamp(0.0, 100.0);
  }

  /// Calculate heart rate contribution to readiness (0-100)
  /// 
  /// Resting HR indicates recovery state
  /// Lower than baseline = good recovery
  /// Higher than baseline = still recovering
  double _calculateHRComponent(double? currentHR, double? baselineHR) {
    if (currentHR == null) {
      return 50.0; // Neutral if no data
    }

    // If no baseline, use general guidelines
    // Excellent resting HR: <60 bpm
    // Good: 60-70 bpm
    // Fair: 70-80 bpm
    // Poor: >80 bpm

    double component;

    if (baselineHR != null) {
      // Compare to personal baseline
      final difference = currentHR - baselineHR;

      if (difference <= -5) {
        component = 100.0; // Well below baseline - excellent
      } else if (difference <= 0) {
        component = 85 + ((-difference) / 5 * 15);
      } else if (difference <= 5) {
        component = 70 + ((5 - difference) / 5 * 15);
      } else if (difference <= 10) {
        component = 50 + ((10 - difference) / 5 * 20);
      } else {
        // More than 10 bpm above baseline
        component = (50 - (difference - 10) * 5).clamp(0.0, 50.0);
      }
    } else {
      // Use absolute values
      if (currentHR < 60) {
        component = 100.0;
      } else if (currentHR <= 70) {
        component = 70 + ((70 - currentHR) / 10 * 30);
      } else if (currentHR <= 80) {
        component = 40 + ((80 - currentHR) / 10 * 30);
      } else {
        component = (40 - (currentHR - 80) * 2).clamp(0.0, 40.0);
      }
    }

    return component.clamp(0.0, 100.0);
  }

  /// Calculate recommended activity level based on readiness
  ActivityRecommendation getRecommendation(ReadinessScore score) {
    if (score.totalScore >= 90) {
      return ActivityRecommendation.peak;
    } else if (score.totalScore >= 70) {
      return ActivityRecommendation.normal;
    } else if (score.totalScore >= 50) {
      return ActivityRecommendation.moderate;
    } else {
      return ActivityRecommendation.recovery;
    }
  }
}

/// Readiness score with component breakdown
class ReadinessScore {
  final double totalScore;
  final double sleepComponent;
  final double hrvComponent;
  final double hrComponent;
  
  final SleepScore? sleepScore;
  final HRVData? hrvData;
  final double? restingHR;
  
  final DateTime calculatedAt;

  ReadinessScore({
    required this.totalScore,
    required this.sleepComponent,
    required this.hrvComponent,
    required this.hrComponent,
    this.sleepScore,
    this.hrvData,
    this.restingHR,
    required this.calculatedAt,
  });

  /// Get readiness level
  ReadinessLevel get level {
    if (totalScore >= 90) return ReadinessLevel.peak;
    if (totalScore >= 70) return ReadinessLevel.good;
    if (totalScore >= 50) return ReadinessLevel.moderate;
    return ReadinessLevel.low;
  }

  /// Get color for UI
  String get colorHex {
    if (totalScore >= 90) return '#4CAF50'; // Green
    if (totalScore >= 70) return '#8BC34A'; // Light Green
    if (totalScore >= 50) return '#FFC107'; // Amber
    return '#FF9800'; // Orange
  }

  /// Get emoji
  String get emoji {
    if (totalScore >= 90) return '🚀';
    if (totalScore >= 70) return '✅';
    if (totalScore >= 50) return '⚠️';
    return '🛑';
  }

  @override
  String toString() {
    return 'ReadinessScore(${totalScore.toStringAsFixed(0)}/100 - ${level.displayName})';
  }
}

/// Readiness level categories
enum ReadinessLevel {
  peak,
  good,
  moderate,
  low;

  String get displayName {
    switch (this) {
      case ReadinessLevel.peak:
        return 'Peak';
      case ReadinessLevel.good:
        return 'Good';
      case ReadinessLevel.moderate:
        return 'Moderate';
      case ReadinessLevel.low:
        return 'Low';
    }
  }

  String get description {
    switch (this) {
      case ReadinessLevel.peak:
        return 'Your body is fully recovered and ready for peak performance!';
      case ReadinessLevel.good:
        return 'You\'re in good shape for normal activities and workouts.';
      case ReadinessLevel.moderate:
        return 'Consider lighter activities today. Your body needs more recovery.';
      case ReadinessLevel.low:
        return 'Prioritize rest and recovery. Avoid intense workouts.';
    }
  }

  String get emoji {
    switch (this) {
      case ReadinessLevel.peak:
        return '🚀';
      case ReadinessLevel.good:
        return '✅';
      case ReadinessLevel.moderate:
        return '⚠️';
      case ReadinessLevel.low:
        return '🛑';
    }
  }
}

/// Activity recommendation based on readiness
enum ActivityRecommendation {
  peak,
  normal,
  moderate,
  recovery;

  String get displayName {
    switch (this) {
      case ActivityRecommendation.peak:
        return 'Peak Performance';
      case ActivityRecommendation.normal:
        return 'Normal Activity';
      case ActivityRecommendation.moderate:
        return 'Take It Easier';
      case ActivityRecommendation.recovery:
        return 'Prioritize Recovery';
    }
  }

  String get description {
    switch (this) {
      case ActivityRecommendation.peak:
        return 'Go for your hardest workout! Your body is ready.';
      case ActivityRecommendation.normal:
        return 'Stick to your regular routine. You\'re good to go.';
      case ActivityRecommendation.moderate:
        return 'Reduce intensity by 30-40%. Focus on technique.';
      case ActivityRecommendation.recovery:
        return 'Rest day or very light activity (walking, stretching).';
    }
  }

  String get icon {
    switch (this) {
      case ActivityRecommendation.peak:
        return '🏋️';
      case ActivityRecommendation.normal:
        return '🏃';
      case ActivityRecommendation.moderate:
        return '🚶';
      case ActivityRecommendation.recovery:
        return '🧘';
    }
  }
}
