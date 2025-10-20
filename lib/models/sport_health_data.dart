/// Sport Health Data Model
/// 
/// Advanced health metrics for fitness and wellness tracking
/// Similar to Whoop, Oura Ring, and Garmin metrics
library;

/// Body Sport Health Data
/// Received via callback from device real-time measurements
class SportHealthData {
  /// VO2 Max - Maximum oxygen consumption (ml/kg/min)
  /// Indicator of cardiovascular fitness
  /// Normal ranges: 
  /// - Excellent: >50 (men), >45 (women)
  /// - Good: 40-50 (men), 35-45 (women)
  /// - Fair: 30-40 (men), 25-35 (women)
  /// - Poor: <30 (men), <25 (women)
  final int vo2Max;

  /// Breath Rate - Respiratory rate (breaths per minute)
  /// Normal range: 12-20 breaths/min
  /// Higher values may indicate stress or physical exertion
  final int breathRate;

  /// Emotion Level (0-5)
  /// 0 = Very Low
  /// 1 = Low
  /// 2 = Normal
  /// 3 = High
  /// 4 = Very High
  /// 5 = Extreme
  final int emotionLevel;

  /// Stress Percentage (0-100%)
  /// Based on HRV analysis and other metrics
  /// <30% = Low stress
  /// 30-60% = Moderate stress
  /// >60% = High stress
  final int stressPercent;

  /// Stamina Level (0-5)
  /// Physical endurance indicator
  /// 0 = Very Low
  /// 1 = Low
  /// 2 = Normal
  /// 3 = Good
  /// 4 = Very Good
  /// 5 = Excellent
  final int stamina;

  /// Total Power (ms²) - HRV frequency domain
  final double? totalPower;

  /// Low Frequency (ms²) - Sympathetic nervous system activity
  final double? lowFrequency;

  /// High Frequency (ms²) - Parasympathetic nervous system activity
  final double? highFrequency;

  /// Timestamp when data was recorded
  final DateTime timestamp;

  SportHealthData({
    required this.vo2Max,
    required this.breathRate,
    required this.emotionLevel,
    required this.stressPercent,
    required this.stamina,
    this.totalPower,
    this.lowFrequency,
    this.highFrequency,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  /// Get emotion description
  String get emotionDescription {
    switch (emotionLevel) {
      case 0:
        return 'Very Low';
      case 1:
        return 'Low';
      case 2:
        return 'Normal';
      case 3:
        return 'High';
      case 4:
        return 'Very High';
      case 5:
        return 'Extreme';
      default:
        return 'Unknown';
    }
  }

  /// Get stamina description
  String get staminaDescription {
    switch (stamina) {
      case 0:
        return 'Very Low';
      case 1:
        return 'Low';
      case 2:
        return 'Normal';
      case 3:
        return 'Good';
      case 4:
        return 'Very Good';
      case 5:
        return 'Excellent';
      default:
        return 'Unknown';
    }
  }

  /// Get VO2 Max fitness level
  String getVO2MaxLevel(bool isMale) {
    if (isMale) {
      if (vo2Max > 50) return 'Excellent';
      if (vo2Max >= 40) return 'Good';
      if (vo2Max >= 30) return 'Fair';
      return 'Poor';
    } else {
      if (vo2Max > 45) return 'Excellent';
      if (vo2Max >= 35) return 'Good';
      if (vo2Max >= 25) return 'Fair';
      return 'Poor';
    }
  }

  /// Get stress level description
  String get stressLevel {
    if (stressPercent < 30) return 'Low';
    if (stressPercent < 60) return 'Moderate';
    return 'High';
  }

  /// Calculate LF/HF ratio (sympathetic/parasympathetic balance)
  /// Normal range: 0.5-2.0
  /// <0.5 = Parasympathetic dominance (relaxed)
  /// >2.0 = Sympathetic dominance (stressed)
  double? get lfHfRatio {
    if (lowFrequency != null && highFrequency != null && highFrequency! > 0) {
      return lowFrequency! / highFrequency!;
    }
    return null;
  }

  @override
  String toString() {
    return 'SportHealthData(vo2Max: $vo2Max, breathRate: $breathRate, '
        'emotion: $emotionDescription, stress: $stressPercent%, '
        'stamina: $staminaDescription, TP: $totalPower, LF: $lowFrequency, '
        'HF: $highFrequency, LF/HF: ${lfHfRatio?.toStringAsFixed(2)})';
  }
}

/// Heart Rate Configuration (Min/Max/Goal)
class HeartRateConfig {
  /// Minimum heart rate threshold (BPM)
  final int min;

  /// Maximum heart rate threshold (BPM)
  final int max;

  /// Daily heart rate goal (BPM or duration)
  final int goal;

  const HeartRateConfig({
    required this.min,
    required this.max,
    required this.goal,
  });

  @override
  String toString() {
    return 'HeartRateConfig(min: $min BPM, max: $max BPM, goal: $goal)';
  }
}

/// Heart Rate Alarm Configuration
class HeartRateAlarm {
  /// Whether alarm is enabled
  final bool enabled;

  /// Timestamp when alarm was set/changed
  final DateTime timestamp;

  HeartRateAlarm({
    required this.enabled,
    required this.timestamp,
  });

  @override
  String toString() {
    return 'HeartRateAlarm(enabled: $enabled, timestamp: $timestamp)';
  }
}

/// Heart Rate Maximum by Age
class HeartRateMax {
  /// Maximum heart rate value (BPM)
  final int max;

  const HeartRateMax({required this.max});

  /// Calculate max HR by age (220 - age formula)
  static int calculateByAge(int age) {
    return 220 - age;
  }

  /// Calculate target HR zones
  Map<String, int> getTargetZones() {
    return {
      'resting': (max * 0.5).round(), // 50% max
      'fatBurn': (max * 0.6).round(), // 60% max
      'cardio': (max * 0.7).round(), // 70% max
      'peak': (max * 0.85).round(), // 85% max
      'maximum': max,
    };
  }

  @override
  String toString() {
    return 'HeartRateMax($max BPM)';
  }
}
