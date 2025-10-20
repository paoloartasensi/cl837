/// Sleep Score Model - Comprehensive sleep quality metrics
/// 
/// Based on algorithms similar to Whoop, Oura Ring, and Apple Watch
/// Score components:
/// - Duration Score (0-35 points): How close to optimal 8h
/// - Efficiency Score (0-30 points): Time asleep vs in bed
/// - Quality Score (0-25 points): Deep/Light sleep ratio
/// - Consistency Score (0-10 points): Interruptions/awakenings
library;

class SleepScore {
  /// Overall sleep score (0-100)
  final double totalScore;
  
  /// Individual component scores
  final double durationScore;    // 0-35 points
  final double efficiencyScore;  // 0-30 points
  final double qualityScore;     // 0-25 points
  final double consistencyScore; // 0-10 points
  
  /// Contributing metrics
  final Duration totalSleepTime;
  final Duration timeInBed;
  final double sleepEfficiency;
  final int deepSleepMinutes;
  final int lightSleepMinutes;
  final int awakeMinutes;
  final int awakenings;
  final double deepSleepPercentage;
  final double lightSleepPercentage;
  final double awakePercentage;
  
  /// Calculated at
  final DateTime calculatedAt;
  
  /// Sleep session identifier
  final DateTime sleepDate;

  SleepScore({
    required this.totalScore,
    required this.durationScore,
    required this.efficiencyScore,
    required this.qualityScore,
    required this.consistencyScore,
    required this.totalSleepTime,
    required this.timeInBed,
    required this.sleepEfficiency,
    required this.deepSleepMinutes,
    required this.lightSleepMinutes,
    required this.awakeMinutes,
    required this.awakenings,
    required this.deepSleepPercentage,
    required this.lightSleepPercentage,
    required this.awakePercentage,
    required this.calculatedAt,
    required this.sleepDate,
  });

  /// Get qualitative rating based on total score
  SleepRating get rating {
    if (totalScore >= 90) return SleepRating.excellent;
    if (totalScore >= 80) return SleepRating.good;
    if (totalScore >= 70) return SleepRating.fair;
    if (totalScore >= 60) return SleepRating.poor;
    return SleepRating.veryPoor;
  }

  /// Get color for UI display
  String get scoreColorHex {
    if (totalScore >= 90) return '#4CAF50'; // Green
    if (totalScore >= 80) return '#8BC34A'; // Light Green
    if (totalScore >= 70) return '#FFC107'; // Amber
    if (totalScore >= 60) return '#FF9800'; // Orange
    return '#F44336'; // Red
  }

  /// Get emoji representation
  String get emoji {
    if (totalScore >= 90) return '🌟';
    if (totalScore >= 80) return '😊';
    if (totalScore >= 70) return '😐';
    if (totalScore >= 60) return '😕';
    return '😫';
  }

  /// Primary insights based on score components
  List<SleepInsight> get insights {
    final insights = <SleepInsight>[];

    // Duration insights
    if (durationScore < 20) {
      const targetMinutes = 480; // 8 hours
      final deficit = targetMinutes - totalSleepTime.inMinutes;
      insights.add(SleepInsight(
        type: InsightType.duration,
        severity: InsightSeverity.warning,
        message: 'Sleep duration is ${deficit}m below optimal. Aim for 7-9 hours.',
        icon: '⏰',
      ));
    } else if (durationScore >= 30) {
      insights.add(SleepInsight(
        type: InsightType.duration,
        severity: InsightSeverity.positive,
        message: 'Excellent sleep duration! You hit your target.',
        icon: '✅',
      ));
    }

    // Efficiency insights
    if (efficiencyScore < 20) {
      insights.add(SleepInsight(
        type: InsightType.efficiency,
        severity: InsightSeverity.warning,
        message: 'Sleep efficiency is low (${sleepEfficiency.toStringAsFixed(0)}%). Try reducing screen time before bed.',
        icon: '📱',
      ));
    } else if (efficiencyScore >= 25) {
      insights.add(SleepInsight(
        type: InsightType.efficiency,
        severity: InsightSeverity.positive,
        message: 'Great sleep efficiency! You fell asleep quickly.',
        icon: '🎯',
      ));
    }

    // Quality insights (deep sleep)
    if (qualityScore < 15) {
      insights.add(SleepInsight(
        type: InsightType.quality,
        severity: InsightSeverity.warning,
        message: 'Deep sleep is below optimal (${deepSleepPercentage.toStringAsFixed(0)}%). Consider exercise earlier in the day.',
        icon: '💪',
      ));
    } else if (qualityScore >= 20) {
      insights.add(SleepInsight(
        type: InsightType.quality,
        severity: InsightSeverity.positive,
        message: 'Excellent deep sleep! Your body is recovering well.',
        icon: '💤',
      ));
    }

    // Consistency insights (awakenings)
    if (consistencyScore < 5) {
      insights.add(SleepInsight(
        type: InsightType.consistency,
        severity: InsightSeverity.warning,
        message: 'You woke up ${awakenings}x during the night. Try a cooler room temperature.',
        icon: '🌡️',
      ));
    } else if (consistencyScore >= 8) {
      insights.add(SleepInsight(
        type: InsightType.consistency,
        severity: InsightSeverity.positive,
        message: 'Very few interruptions! Your sleep was consistent.',
        icon: '🌙',
      ));
    }

    return insights;
  }

  /// Convert to JSON for storage
  Map<String, dynamic> toJson() {
    return {
      'totalScore': totalScore,
      'durationScore': durationScore,
      'efficiencyScore': efficiencyScore,
      'qualityScore': qualityScore,
      'consistencyScore': consistencyScore,
      'totalSleepTime': totalSleepTime.inMinutes,
      'timeInBed': timeInBed.inMinutes,
      'sleepEfficiency': sleepEfficiency,
      'deepSleepMinutes': deepSleepMinutes,
      'lightSleepMinutes': lightSleepMinutes,
      'awakeMinutes': awakeMinutes,
      'awakenings': awakenings,
      'deepSleepPercentage': deepSleepPercentage,
      'lightSleepPercentage': lightSleepPercentage,
      'awakePercentage': awakePercentage,
      'calculatedAt': calculatedAt.toIso8601String(),
      'sleepDate': sleepDate.toIso8601String(),
    };
  }

  /// Create from JSON
  factory SleepScore.fromJson(Map<String, dynamic> json) {
    return SleepScore(
      totalScore: json['totalScore'],
      durationScore: json['durationScore'],
      efficiencyScore: json['efficiencyScore'],
      qualityScore: json['qualityScore'],
      consistencyScore: json['consistencyScore'],
      totalSleepTime: Duration(minutes: json['totalSleepTime']),
      timeInBed: Duration(minutes: json['timeInBed']),
      sleepEfficiency: json['sleepEfficiency'],
      deepSleepMinutes: json['deepSleepMinutes'],
      lightSleepMinutes: json['lightSleepMinutes'],
      awakeMinutes: json['awakeMinutes'],
      awakenings: json['awakenings'],
      deepSleepPercentage: json['deepSleepPercentage'],
      lightSleepPercentage: json['lightSleepPercentage'],
      awakePercentage: json['awakePercentage'],
      calculatedAt: DateTime.parse(json['calculatedAt']),
      sleepDate: DateTime.parse(json['sleepDate']),
    );
  }

  @override
  String toString() {
    return 'SleepScore(${totalScore.toStringAsFixed(0)}/100 - ${rating.displayName})';
  }
}

/// Sleep quality rating categories
enum SleepRating {
  excellent,
  good,
  fair,
  poor,
  veryPoor;

  String get displayName {
    switch (this) {
      case SleepRating.excellent:
        return 'Excellent';
      case SleepRating.good:
        return 'Good';
      case SleepRating.fair:
        return 'Fair';
      case SleepRating.poor:
        return 'Poor';
      case SleepRating.veryPoor:
        return 'Very Poor';
    }
  }

  String get description {
    switch (this) {
      case SleepRating.excellent:
        return 'Outstanding recovery! You\'re ready for anything.';
      case SleepRating.good:
        return 'Great sleep! Your body recovered well.';
      case SleepRating.fair:
        return 'Decent sleep, but there\'s room for improvement.';
      case SleepRating.poor:
        return 'Below optimal. Consider earlier bedtime.';
      case SleepRating.veryPoor:
        return 'Your body needs better rest. Prioritize sleep tonight.';
    }
  }
}

/// Sleep insight for actionable recommendations
class SleepInsight {
  final InsightType type;
  final InsightSeverity severity;
  final String message;
  final String icon;

  SleepInsight({
    required this.type,
    required this.severity,
    required this.message,
    required this.icon,
  });

  Map<String, dynamic> toJson() {
    return {
      'type': type.name,
      'severity': severity.name,
      'message': message,
      'icon': icon,
    };
  }

  factory SleepInsight.fromJson(Map<String, dynamic> json) {
    return SleepInsight(
      type: InsightType.values.firstWhere((e) => e.name == json['type']),
      severity: InsightSeverity.values.firstWhere((e) => e.name == json['severity']),
      message: json['message'],
      icon: json['icon'],
    );
  }
}

enum InsightType {
  duration,
  efficiency,
  quality,
  consistency,
  general;
}

enum InsightSeverity {
  positive,
  neutral,
  warning,
  critical;

  String get colorHex {
    switch (this) {
      case InsightSeverity.positive:
        return '#4CAF50';
      case InsightSeverity.neutral:
        return '#2196F3';
      case InsightSeverity.warning:
        return '#FF9800';
      case InsightSeverity.critical:
        return '#F44336';
    }
  }
}
