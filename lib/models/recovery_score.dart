/// Recovery Score Model - Similar to Whoop Recovery
/// 
/// Combines HRV, Sleep Quality, and Resting Heart Rate to calculate
/// a comprehensive recovery score (0-100%)
library;

import 'sport_health_data.dart';
import 'historical_data.dart';
import 'dart:math';

class RecoveryScore {
  final double score; // 0-100%
  final RecoveryZone zone;
  final DateTime timestamp;
  
  // Component scores
  final double hrvScore;
  final double sleepScore;
  final double rhrScore;
  
  // Raw metrics used
  final double? lfHfRatio;
  final int? restingHeartRate;
  final double? sleepQuality;
  
  const RecoveryScore({
    required this.score,
    required this.zone,
    required this.timestamp,
    required this.hrvScore,
    required this.sleepScore,
    required this.rhrScore,
    this.lfHfRatio,
    this.restingHeartRate,
    this.sleepQuality,
  });
  
  /// Calculate recovery score from health data
  factory RecoveryScore.calculate({
    required SportHealthData? sportHealth,
    required SleepQuality? sleepQuality,
    int? restingHeartRate,
  }) {
    final now = DateTime.now();
    
    // 1. HRV Score (50% weight) - Most important metric
    double hrvScore = 0;
    double? lfHfRatio;
    
    if (sportHealth?.lfHfRatio != null) {
      lfHfRatio = sportHealth!.lfHfRatio!;
      
      // Optimal LF/HF ratio: 0.5-1.5 (parasympathetic dominance = good recovery)
      if (lfHfRatio < 0.5) {
        hrvScore = 100; // Excellent parasympathetic activity
      } else if (lfHfRatio <= 1.5) {
        // Linear interpolation: 0.5->100%, 1.5->80%
        hrvScore = 100 - ((lfHfRatio - 0.5) / 1.0 * 20);
      } else if (lfHfRatio <= 2.5) {
        // Moderate stress: 1.5->80%, 2.5->50%
        hrvScore = 80 - ((lfHfRatio - 1.5) / 1.0 * 30);
      } else {
        // High stress/overtraining: >2.5 = <50%
        hrvScore = max(0, 50 - (lfHfRatio - 2.5) * 10);
      }
    } else if (sportHealth?.stressPercent != null) {
      // Fallback: use stress percentage (inverse relationship)
      hrvScore = 100 - sportHealth!.stressPercent.toDouble();
    }
    
    // 2. Sleep Score (35% weight)
    double sleepScoreValue = 0;
    if (sleepQuality != null) {
      sleepScoreValue = sleepQuality.overallScore * 100;
    }
    
    // 3. Resting HR Score (15% weight)
    double rhrScore = 0;
    if (restingHeartRate != null) {
      // Typical RHR: 50-70 bpm = good recovery
      // Lower is better
      if (restingHeartRate < 50) {
        rhrScore = 100; // Athlete level
      } else if (restingHeartRate <= 60) {
        rhrScore = 100 - ((restingHeartRate - 50) / 10 * 15); // 85-100%
      } else if (restingHeartRate <= 70) {
        rhrScore = 85 - ((restingHeartRate - 60) / 10 * 25); // 60-85%
      } else if (restingHeartRate <= 80) {
        rhrScore = 60 - ((restingHeartRate - 70) / 10 * 30); // 30-60%
      } else {
        rhrScore = max(0, 30 - (restingHeartRate - 80));
      }
    }
    
    // Calculate weighted average
    final totalScore = (hrvScore * 0.50) + (sleepScoreValue * 0.35) + (rhrScore * 0.15);
    
    // Determine recovery zone
    final zone = RecoveryZone.fromScore(totalScore);
    
    return RecoveryScore(
      score: totalScore.clamp(0, 100),
      zone: zone,
      timestamp: now,
      hrvScore: hrvScore,
      sleepScore: sleepScoreValue,
      rhrScore: rhrScore,
      lfHfRatio: lfHfRatio,
      restingHeartRate: restingHeartRate,
      sleepQuality: sleepQuality?.overallScore,
    );
  }
  
  String get recommendation {
    switch (zone) {
      case RecoveryZone.green:
        return 'Your body is fully recovered! Great day for intense training.';
      case RecoveryZone.yellow:
        return 'Moderate recovery. Consider moderate activity or active recovery.';
      case RecoveryZone.red:
        return 'Low recovery. Prioritize rest, sleep, and light activity.';
    }
  }
  
  @override
  String toString() {
    return 'RecoveryScore(score: ${score.toStringAsFixed(1)}%, zone: $zone, '
        'HRV: ${hrvScore.toStringAsFixed(1)}%, Sleep: ${sleepScore.toStringAsFixed(1)}%, '
        'RHR: ${rhrScore.toStringAsFixed(1)}%)';
  }
}

enum RecoveryZone {
  green,  // 67-100% - Ready to perform
  yellow, // 34-66% - Maintain/moderate activity
  red;    // 0-33% - Rest and recover
  
  static RecoveryZone fromScore(double score) {
    if (score >= 67) return RecoveryZone.green;
    if (score >= 34) return RecoveryZone.yellow;
    return RecoveryZone.red;
  }
  
  String get emoji {
    switch (this) {
      case RecoveryZone.green:
        return '🟢';
      case RecoveryZone.yellow:
        return '🟡';
      case RecoveryZone.red:
        return '🔴';
    }
  }
  
  String get label {
    switch (this) {
      case RecoveryZone.green:
        return 'Ready';
      case RecoveryZone.yellow:
        return 'Moderate';
      case RecoveryZone.red:
        return 'Low';
    }
  }
}

/// Sleep Quality Analysis
class SleepQuality {
  final double overallScore; // 0-1
  final double durationScore;
  final double efficiencyScore;
  final double deepSleepScore;
  final double respiratoryScore;
  final double hrvScore;
  
  // Raw metrics
  final int totalMinutes;
  final int deepSleepMinutes;
  final int lightSleepMinutes;
  final int awakeMinutes;
  final double efficiency; // 0-1
  final int? averageRespiratoryRate;
  final double? averageHF; // High Frequency HRV during sleep
  
  const SleepQuality({
    required this.overallScore,
    required this.durationScore,
    required this.efficiencyScore,
    required this.deepSleepScore,
    required this.respiratoryScore,
    required this.hrvScore,
    required this.totalMinutes,
    required this.deepSleepMinutes,
    required this.lightSleepMinutes,
    required this.awakeMinutes,
    required this.efficiency,
    this.averageRespiratoryRate,
    this.averageHF,
  });
  
  /// Calculate sleep quality from sleep session
  factory SleepQuality.fromSleepSession(SleepHistoryEntry session) {
    // Parse sleep phases
    final phases = session.calculateSleepPhases();
    
    final totalMinutes = phases.totalMinutes;
    final deepMinutes = phases.deepSleep;
    final lightMinutes = phases.lightSleep;
    final awakeMinutes = phases.awake;
    
    // 1. Duration Score (25%)
    final hours = totalMinutes / 60;
    final durationScore = (min(hours / 8.0, 1.0) * 100).toDouble();
    
    // 2. Deep Sleep Score (25%)
    final deepPercent = deepMinutes / totalMinutes;
    double deepScore = 0;
    if (deepPercent >= 0.15 && deepPercent <= 0.25) {
      deepScore = 100; // Optimal range 15-25%
    } else if (deepPercent >= 0.10 && deepPercent < 0.15) {
      deepScore = 50 + ((deepPercent - 0.10) / 0.05 * 50); // 50-100%
    } else if (deepPercent > 0.25 && deepPercent <= 0.30) {
      deepScore = 100 - ((deepPercent - 0.25) / 0.05 * 30); // 70-100%
    } else {
      deepScore = max(0, 50 - (0.10 - deepPercent) * 500); // <10% = poor
    }
    
    // 3. Efficiency Score (20%)
    final asleepMinutes = totalMinutes - awakeMinutes;
    final efficiency = totalMinutes > 0 ? asleepMinutes / totalMinutes : 0.0;
    final efficiencyScore = (efficiency * 100).toDouble();
    
    // 4. Respiratory Score (15%) - placeholder, needs actual data
    const respiratoryScore = 75.0; // Default
    
    // 5. HRV Score (15%) - placeholder, needs actual data
    const hrvScore = 75.0; // Default
    
    // Calculate overall score
    final overallScore = (
      durationScore * 0.25 +
      deepScore * 0.25 +
      efficiencyScore * 0.20 +
      respiratoryScore * 0.15 +
      hrvScore * 0.15
    ) / 100;
    
    return SleepQuality(
      overallScore: overallScore.clamp(0, 1),
      durationScore: durationScore / 100,
      efficiencyScore: efficiency,
      deepSleepScore: deepScore / 100,
      respiratoryScore: respiratoryScore / 100,
      hrvScore: hrvScore / 100,
      totalMinutes: totalMinutes,
      deepSleepMinutes: deepMinutes,
      lightSleepMinutes: lightMinutes,
      awakeMinutes: awakeMinutes,
      efficiency: efficiency,
    );
  }
  
  String get grade {
    if (overallScore >= 0.90) return 'A+';
    if (overallScore >= 0.85) return 'A';
    if (overallScore >= 0.80) return 'B+';
    if (overallScore >= 0.75) return 'B';
    if (overallScore >= 0.70) return 'C+';
    if (overallScore >= 0.65) return 'C';
    if (overallScore >= 0.60) return 'D';
    return 'F';
  }
  
  @override
  String toString() {
    return 'SleepQuality(score: ${(overallScore * 100).toStringAsFixed(1)}%, '
        'grade: $grade, duration: ${totalMinutes}min, deep: ${deepSleepMinutes}min, '
        'efficiency: ${(efficiency * 100).toStringAsFixed(1)}%)';
  }
}
