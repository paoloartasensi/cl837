/// Sleep Score Calculator Service
/// 
/// Calculates comprehensive sleep quality scores based on:
/// - Duration (0-35 points): Proximity to optimal sleep duration
/// - Efficiency (0-30 points): Time asleep vs time in bed
/// - Quality (0-25 points): Deep sleep ratio and distribution
/// - Consistency (0-10 points): Number of awakenings
/// 
/// Algorithm inspired by Whoop, Oura Ring, and sleep science research

import '../models/historical_data.dart';
import '../models/sleep_score.dart';

class SleepScoreCalculator {
  /// Optimal sleep duration targets (minutes)
  static const int optimalDurationMin = 420;  // 7 hours
  static const int optimalDurationMax = 540;  // 9 hours
  static const int targetDuration = 480;      // 8 hours (ideal)

  /// Optimal deep sleep percentage (15-25% is ideal)
  static const double optimalDeepSleepMin = 15.0;
  static const double optimalDeepSleepMax = 25.0;
  static const double targetDeepSleep = 20.0;

  /// Optimal efficiency threshold
  static const double optimalEfficiency = 85.0;

  /// Maximum points for each component
  static const double maxDurationPoints = 35.0;
  static const double maxEfficiencyPoints = 30.0;
  static const double maxQualityPoints = 25.0;
  static const double maxConsistencyPoints = 10.0;

  /// Calculate comprehensive sleep score from SleepData31
  SleepScore calculateScore(SleepData31 sleepData) {
    final phases = sleepData.calculateSleepPhases();
    
    // Calculate total sleep time (excluding awake periods)
    final totalSleepMinutes = phases.deepSleepMinutes + phases.lightSleepMinutes;
    final totalSleepTime = Duration(minutes: totalSleepMinutes);
    
    // Calculate time in bed (entire session)
    final timeInBed = Duration(minutes: sleepData.activityIndices.length * 5);
    
    // Calculate awakenings (transitions to awake state)
    final awakenings = _countAwakenings(sleepData.activityIndices);

    // Component scores
    final durationScore = _calculateDurationScore(totalSleepMinutes);
    final efficiencyScore = _calculateEfficiencyScore(phases.sleepEfficiency);
    final qualityScore = _calculateQualityScore(
      phases.deepSleepMinutes,
      totalSleepMinutes,
    );
    final consistencyScore = _calculateConsistencyScore(awakenings);

    // Total score (0-100)
    final totalScore = durationScore + efficiencyScore + qualityScore + consistencyScore;

    return SleepScore(
      totalScore: totalScore,
      durationScore: durationScore,
      efficiencyScore: efficiencyScore,
      qualityScore: qualityScore,
      consistencyScore: consistencyScore,
      totalSleepTime: totalSleepTime,
      timeInBed: timeInBed,
      sleepEfficiency: phases.sleepEfficiency,
      deepSleepMinutes: phases.deepSleepMinutes,
      lightSleepMinutes: phases.lightSleepMinutes,
      awakeMinutes: phases.awakeMinutes,
      awakenings: awakenings,
      deepSleepPercentage: (phases.deepSleepMinutes / totalSleepMinutes * 100),
      lightSleepPercentage: (phases.lightSleepMinutes / totalSleepMinutes * 100),
      awakePercentage: (phases.awakeMinutes / timeInBed.inMinutes * 100),
      calculatedAt: DateTime.now(),
      sleepDate: sleepData.timestamp,
    );
  }

  /// Calculate duration score (0-35 points)
  /// 
  /// Scoring logic:
  /// - 35 points: 7-9 hours (optimal range)
  /// - Decreasing points as duration moves away from optimal
  /// - Penalize both too short AND too long sleep
  double _calculateDurationScore(int totalMinutes) {
    if (totalMinutes >= optimalDurationMin && totalMinutes <= optimalDurationMax) {
      // Within optimal range - award full points
      // But give slight bonus for being closest to 8h target
      final distanceFromTarget = (totalMinutes - targetDuration).abs();
      final proximityBonus = (1 - (distanceFromTarget / 60.0).clamp(0.0, 1.0)) * 5;
      return (maxDurationPoints - 5) + proximityBonus;
    } else if (totalMinutes < optimalDurationMin) {
      // Too short - penalize proportionally
      final deficit = optimalDurationMin - totalMinutes;
      final penaltyFactor = (deficit / 180.0).clamp(0.0, 1.0); // 3h deficit = max penalty
      return maxDurationPoints * (1 - penaltyFactor);
    } else {
      // Too long - slight penalty (oversleeping is less bad than undersleeping)
      final excess = totalMinutes - optimalDurationMax;
      final penaltyFactor = (excess / 240.0).clamp(0.0, 1.0); // 4h excess = max penalty
      return maxDurationPoints * (1 - penaltyFactor * 0.7); // Gentler penalty
    }
  }

  /// Calculate efficiency score (0-30 points)
  /// 
  /// Based on sleep efficiency percentage:
  /// - 95%+ efficiency = 30 points
  /// - 85-95% = 25-30 points (optimal range)
  /// - 70-85% = 15-25 points
  /// - <70% = 0-15 points
  double _calculateEfficiencyScore(double efficiency) {
    if (efficiency >= 95.0) {
      return maxEfficiencyPoints;
    } else if (efficiency >= optimalEfficiency) {
      // Linear interpolation between 85% and 95%
      final range = 95.0 - optimalEfficiency;
      final position = efficiency - optimalEfficiency;
      return 25 + (position / range * 5);
    } else if (efficiency >= 70.0) {
      // Linear interpolation between 70% and 85%
      final range = optimalEfficiency - 70.0;
      final position = efficiency - 70.0;
      return 15 + (position / range * 10);
    } else {
      // Below 70% - poor efficiency
      final range = 70.0;
      final position = efficiency.clamp(0.0, 70.0);
      return (position / range * 15);
    }
  }

  /// Calculate quality score (0-25 points)
  /// 
  /// Based on deep sleep percentage:
  /// - 15-25% = optimal (full points)
  /// - Higher deep sleep ratio = better recovery
  /// - Also consider distribution and consistency
  double _calculateQualityScore(int deepMinutes, int totalSleepMinutes) {
    if (totalSleepMinutes == 0) return 0.0;

    final deepPercentage = (deepMinutes / totalSleepMinutes * 100);

    if (deepPercentage >= optimalDeepSleepMin && deepPercentage <= optimalDeepSleepMax) {
      // Within optimal range
      // Give bonus for being close to 20% target
      final distanceFromTarget = (deepPercentage - targetDeepSleep).abs();
      final proximityBonus = (1 - (distanceFromTarget / 5.0).clamp(0.0, 1.0)) * 5;
      return (maxQualityPoints - 5) + proximityBonus;
    } else if (deepPercentage < optimalDeepSleepMin) {
      // Too little deep sleep
      final deficit = optimalDeepSleepMin - deepPercentage;
      final penaltyFactor = (deficit / 15.0).clamp(0.0, 1.0);
      return maxQualityPoints * (1 - penaltyFactor);
    } else {
      // More deep sleep than typical (rare but possible)
      // Slight penalty as it might indicate sleep debt recovery
      final excess = deepPercentage - optimalDeepSleepMax;
      final penaltyFactor = (excess / 10.0).clamp(0.0, 1.0);
      return maxQualityPoints * (1 - penaltyFactor * 0.3);
    }
  }

  /// Calculate consistency score (0-10 points)
  /// 
  /// Based on number of awakenings:
  /// - 0-1 awakenings = 10 points (excellent)
  /// - 2-3 awakenings = 7-9 points (good)
  /// - 4-5 awakenings = 4-6 points (fair)
  /// - 6+ awakenings = 0-3 points (poor)
  double _calculateConsistencyScore(int awakenings) {
    if (awakenings <= 1) {
      return maxConsistencyPoints;
    } else if (awakenings <= 3) {
      return 7 + (3 - awakenings) * 1.0;
    } else if (awakenings <= 5) {
      return 4 + (5 - awakenings) * 1.0;
    } else {
      final excess = awakenings - 5;
      final penalty = (excess * 0.5).clamp(0.0, 4.0);
      return (4 - penalty).clamp(0.0, maxConsistencyPoints);
    }
  }

  /// Count number of awakenings (transitions to awake state)
  int _countAwakenings(List<int> activityIndices) {
    int awakenings = 0;
    bool wasAwake = false;

    for (final index in activityIndices) {
      final isAwake = index > 20;

      if (isAwake && !wasAwake) {
        awakenings++;
      }

      wasAwake = isAwake;
    }

    return awakenings;
  }

  /// Calculate sleep debt over period
  /// Returns negative value if sleep deprived, positive if surplus
  double calculateSleepDebt(List<SleepScore> recentScores, {int targetHoursPerNight = 8}) {
    if (recentScores.isEmpty) return 0.0;

    final targetMinutesPerNight = targetHoursPerNight * 60;
    double totalDebt = 0.0;

    for (final score in recentScores) {
      final deficit = score.totalSleepTime.inMinutes - targetMinutesPerNight;
      totalDebt += deficit;
    }

    return totalDebt / 60.0; // Return in hours
  }

  /// Calculate average score over period
  double calculateAverageScore(List<SleepScore> scores) {
    if (scores.isEmpty) return 0.0;
    final sum = scores.fold(0.0, (sum, score) => sum + score.totalScore);
    return sum / scores.length;
  }

  /// Detect trends (improving, declining, stable)
  SleepTrend analyzeTrend(List<SleepScore> chronologicalScores) {
    if (chronologicalScores.length < 3) return SleepTrend.insufficient;

    // Compare first half vs second half
    final midpoint = chronologicalScores.length ~/ 2;
    final firstHalf = chronologicalScores.sublist(0, midpoint);
    final secondHalf = chronologicalScores.sublist(midpoint);

    final firstAvg = calculateAverageScore(firstHalf);
    final secondAvg = calculateAverageScore(secondHalf);

    final difference = secondAvg - firstAvg;

    if (difference >= 5.0) return SleepTrend.improving;
    if (difference <= -5.0) return SleepTrend.declining;
    return SleepTrend.stable;
  }
}

/// Sleep trend analysis
enum SleepTrend {
  improving,
  stable,
  declining,
  insufficient;

  String get displayName {
    switch (this) {
      case SleepTrend.improving:
        return 'Improving';
      case SleepTrend.stable:
        return 'Stable';
      case SleepTrend.declining:
        return 'Declining';
      case SleepTrend.insufficient:
        return 'Need More Data';
    }
  }

  String get emoji {
    switch (this) {
      case SleepTrend.improving:
        return '📈';
      case SleepTrend.stable:
        return '➡️';
      case SleepTrend.declining:
        return '📉';
      case SleepTrend.insufficient:
        return '❓';
    }
  }

  String get description {
    switch (this) {
      case SleepTrend.improving:
        return 'Your sleep quality is getting better!';
      case SleepTrend.stable:
        return 'Your sleep quality is consistent.';
      case SleepTrend.declining:
        return 'Your sleep quality needs attention.';
      case SleepTrend.insufficient:
        return 'Track more nights to see trends.';
    }
  }
}
