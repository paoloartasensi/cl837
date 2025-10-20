/// Smart Alarm Model
/// 
/// Represents a smart alarm that wakes user during optimal sleep phase
/// (light sleep) within a configurable time window
library;

class SmartAlarm {
  /// Desired wake time set by user
  final DateTime desiredWakeTime;
  
  /// How many minutes before desired time to start looking for light sleep
  /// Example: 30 means alarm can trigger between desired-30min and desired time
  final int windowMinutes;
  
  /// Whether alarm is enabled
  final bool isEnabled;
  
  /// Optimal wake time calculated by algorithm (during light sleep)
  final DateTime? optimalWakeTime;
  
  /// Confidence score (0-100) for optimal wake time
  /// Higher = more confident this is ideal time
  final double? confidence;
  
  /// Sleep phase at optimal wake time
  final WakePhase? wakePhase;
  
  /// Whether to use device vibration
  final bool useDeviceVibration;
  
  /// Whether to use phone notification
  final bool usePhoneNotification;
  
  /// Custom alarm label
  final String label;
  
  /// Days of week (0=Sunday, 6=Saturday), empty = one-time alarm
  final List<int> repeatDays;

  SmartAlarm({
    required this.desiredWakeTime,
    this.windowMinutes = 30,
    this.isEnabled = true,
    this.optimalWakeTime,
    this.confidence,
    this.wakePhase,
    this.useDeviceVibration = true,
    this.usePhoneNotification = true,
    this.label = 'Wake Up',
    this.repeatDays = const [],
  });

  /// Get window start time
  DateTime get windowStart {
    return desiredWakeTime.subtract(Duration(minutes: windowMinutes));
  }

  /// Get window end time (same as desired)
  DateTime get windowEnd {
    return desiredWakeTime;
  }

  /// Whether alarm is for today
  bool get isForToday {
    final now = DateTime.now();
    return desiredWakeTime.year == now.year &&
           desiredWakeTime.month == now.month &&
           desiredWakeTime.day == now.day;
  }

  /// Whether alarm is repeating
  bool get isRepeating {
    return repeatDays.isNotEmpty;
  }

  /// Get repeat schedule description
  String get repeatDescription {
    if (!isRepeating) return 'One time';
    
    if (repeatDays.length == 7) return 'Every day';
    if (repeatDays.length == 5 && !repeatDays.contains(0) && !repeatDays.contains(6)) {
      return 'Weekdays';
    }
    if (repeatDays.length == 2 && repeatDays.contains(0) && repeatDays.contains(6)) {
      return 'Weekends';
    }
    
    final dayNames = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    return repeatDays.map((d) => dayNames[d]).join(', ');
  }

  /// Copy with modifications
  SmartAlarm copyWith({
    DateTime? desiredWakeTime,
    int? windowMinutes,
    bool? isEnabled,
    DateTime? optimalWakeTime,
    double? confidence,
    WakePhase? wakePhase,
    bool? useDeviceVibration,
    bool? usePhoneNotification,
    String? label,
    List<int>? repeatDays,
  }) {
    return SmartAlarm(
      desiredWakeTime: desiredWakeTime ?? this.desiredWakeTime,
      windowMinutes: windowMinutes ?? this.windowMinutes,
      isEnabled: isEnabled ?? this.isEnabled,
      optimalWakeTime: optimalWakeTime ?? this.optimalWakeTime,
      confidence: confidence ?? this.confidence,
      wakePhase: wakePhase ?? this.wakePhase,
      useDeviceVibration: useDeviceVibration ?? this.useDeviceVibration,
      usePhoneNotification: usePhoneNotification ?? this.usePhoneNotification,
      label: label ?? this.label,
      repeatDays: repeatDays ?? this.repeatDays,
    );
  }

  /// Convert to JSON for storage
  Map<String, dynamic> toJson() {
    return {
      'desiredWakeTime': desiredWakeTime.toIso8601String(),
      'windowMinutes': windowMinutes,
      'isEnabled': isEnabled,
      'optimalWakeTime': optimalWakeTime?.toIso8601String(),
      'confidence': confidence,
      'wakePhase': wakePhase?.name,
      'useDeviceVibration': useDeviceVibration,
      'usePhoneNotification': usePhoneNotification,
      'label': label,
      'repeatDays': repeatDays,
    };
  }

  /// Create from JSON
  factory SmartAlarm.fromJson(Map<String, dynamic> json) {
    return SmartAlarm(
      desiredWakeTime: DateTime.parse(json['desiredWakeTime']),
      windowMinutes: json['windowMinutes'] ?? 30,
      isEnabled: json['isEnabled'] ?? true,
      optimalWakeTime: json['optimalWakeTime'] != null 
          ? DateTime.parse(json['optimalWakeTime']) 
          : null,
      confidence: json['confidence'],
      wakePhase: json['wakePhase'] != null 
          ? WakePhase.values.firstWhere((e) => e.name == json['wakePhase'])
          : null,
      useDeviceVibration: json['useDeviceVibration'] ?? true,
      usePhoneNotification: json['usePhoneNotification'] ?? true,
      label: json['label'] ?? 'Wake Up',
      repeatDays: List<int>.from(json['repeatDays'] ?? []),
    );
  }

  @override
  String toString() {
    final timeStr = '${desiredWakeTime.hour}:${desiredWakeTime.minute.toString().padLeft(2, '0')}';
    return 'SmartAlarm($label at $timeStr, window: ${windowMinutes}min, enabled: $isEnabled)';
  }
}

/// Sleep phase at wake time
enum WakePhase {
  lightSleep,
  deepSleep,
  awake,
  unknown;

  String get displayName {
    switch (this) {
      case WakePhase.lightSleep:
        return 'Light Sleep';
      case WakePhase.deepSleep:
        return 'Deep Sleep';
      case WakePhase.awake:
        return 'Awake';
      case WakePhase.unknown:
        return 'Unknown';
    }
  }

  String get emoji {
    switch (this) {
      case WakePhase.lightSleep:
        return '🌙';
      case WakePhase.deepSleep:
        return '💤';
      case WakePhase.awake:
        return '👁️';
      case WakePhase.unknown:
        return '❓';
    }
  }

  bool get isOptimalForWaking {
    return this == WakePhase.lightSleep || this == WakePhase.awake;
  }
}

/// Optimal wake time result
class OptimalWakeResult {
  final DateTime optimalTime;
  final WakePhase phase;
  final double confidence;
  final String reason;

  OptimalWakeResult({
    required this.optimalTime,
    required this.phase,
    required this.confidence,
    required this.reason,
  });

  @override
  String toString() {
    final timeStr = '${optimalTime.hour}:${optimalTime.minute.toString().padLeft(2, '0')}';
    return 'OptimalWake($timeStr, ${phase.displayName}, confidence: ${confidence.toStringAsFixed(0)}%)';
  }
}
