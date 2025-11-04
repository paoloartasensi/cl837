/// Sleep Session Classifier Service
/// 
/// Centralizes intelligent sleep classification logic:
/// - Main night sleep vs naps
/// - Quality thresholds
/// - Time-of-day analysis
/// - Duration filtering
/// 
/// Used by: Sleep Premium, Advanced Dashboard, CSV Export, etc.
library;

import '../models/historical_data.dart';
import '../models/sleep_score.dart';

/// Sleep session type classification
enum SleepType {
  mainNightSleep,  // Primary nighttime sleep (3+ hours, 18:00-10:00)
  napShort,        // Short nap (<3 hours)
  napLong,         // Long daytime sleep (3+ hours, 10:00-18:00)
  insufficient,    // Too short to be meaningful (<30 minutes)
}

extension SleepTypeExtension on SleepType {
  String get displayName {
    switch (this) {
      case SleepType.mainNightSleep:
        return 'Night Sleep';
      case SleepType.napShort:
        return 'Short Nap';
      case SleepType.napLong:
        return 'Long Nap';
      case SleepType.insufficient:
        return 'Brief Rest';
    }
  }

  String get emoji {
    switch (this) {
      case SleepType.mainNightSleep:
        return '🌙';
      case SleepType.napShort:
        return '😴';
      case SleepType.napLong:
        return '🛋️';
      case SleepType.insufficient:
        return '⏱️';
    }
  }

  bool get isMainSleep => this == SleepType.mainNightSleep;
  bool get isNap => this == SleepType.napShort || this == SleepType.napLong;
  bool get isQualifying => this != SleepType.insufficient;
}

/// Classified sleep session with metadata
class ClassifiedSleepSession {
  final SleepData31 data;
  final SleepType type;
  final int durationMinutes;
  final DateTime timestamp;

  ClassifiedSleepSession({
    required this.data,
    required this.type,
    required this.durationMinutes,
    required this.timestamp,
  });

  bool get isMainSleep => type.isMainSleep;
  bool get isNap => type.isNap;
  bool get isQualifying => type.isQualifying;
}

/// Centralized sleep classification service
class SleepClassifier {
  /// Minimum duration for main sleep (minutes)
  static const int minMainSleepMinutes = 180; // 3 hours

  /// Minimum duration for any meaningful sleep (minutes)
  static const int minMeaningfulSleepMinutes = 30;

  /// Night hours range (18:00 - 10:00)
  static const int nightStartHour = 18;
  static const int nightEndHour = 10;

  /// Classify a single sleep session
  static SleepType classifySession(SleepData31 sleepData) {
    final durationMinutes = sleepData.activityIndices.length * 5;
    final hour = sleepData.timestamp.hour;

    return classifyByDurationAndTime(durationMinutes, hour);
  }

  /// Classify by duration and time of day
  static SleepType classifyByDurationAndTime(int durationMinutes, int hour) {
    // Too short to be meaningful
    if (durationMinutes < minMeaningfulSleepMinutes) {
      return SleepType.insufficient;
    }

    // Check if it's night hours (18:00-10:00)
    final isNightTime = hour >= nightStartHour || hour <= nightEndHour;

    // Main night sleep: 3+ hours during night
    if (durationMinutes >= minMainSleepMinutes && isNightTime) {
      return SleepType.mainNightSleep;
    }

    // Long daytime nap
    if (durationMinutes >= minMainSleepMinutes) {
      return SleepType.napLong;
    }

    // Short nap
    return SleepType.napShort;
  }

  /// Classify from SleepHistoryEntry (legacy format)
  static SleepType classifyHistoryEntry(SleepHistoryEntry entry) {
    final durationMinutes = entry.count * 5;
    final hour = entry.timestamp.hour;

    return classifyByDurationAndTime(durationMinutes, hour);
  }

  /// Classify from SleepScore
  static SleepType classifyScore(SleepScore score) {
    final durationMinutes = score.totalSleepTime.inMinutes;
    final hour = score.sleepDate.hour;

    return classifyByDurationAndTime(durationMinutes, hour);
  }

  /// Filter list to get only main night sleeps
  static List<SleepData31> filterMainSleeps(List<SleepData31> sessions) {
    return sessions
        .where((s) => classifySession(s) == SleepType.mainNightSleep)
        .toList();
  }

  /// Filter SleepHistoryEntry list for main sleeps
  static List<SleepHistoryEntry> filterMainSleepHistory(
      List<SleepHistoryEntry> sessions) {
    return sessions
        .where((s) => classifyHistoryEntry(s) == SleepType.mainNightSleep)
        .toList();
  }

  /// Filter SleepScore list for main sleeps
  static List<SleepScore> filterMainSleepScores(List<SleepScore> scores) {
    return scores
        .where((s) => classifyScore(s) == SleepType.mainNightSleep)
        .toList();
  }

  /// Get most recent main sleep from list
  static SleepData31? getMostRecentMainSleep(List<SleepData31> sessions) {
    final mainSleeps = filterMainSleeps(sessions);
    if (mainSleeps.isEmpty) return null;

    mainSleeps.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    return mainSleeps.first;
  }

  /// Get most recent main sleep from SleepHistoryEntry
  static SleepHistoryEntry? getMostRecentMainSleepHistory(
      List<SleepHistoryEntry> sessions) {
    final mainSleeps = filterMainSleepHistory(sessions);
    if (mainSleeps.isEmpty) return null;

    mainSleeps.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    return mainSleeps.first;
  }

  /// Get most recent main sleep from SleepScore
  static SleepScore? getMostRecentMainSleepScore(List<SleepScore> scores) {
    final mainSleeps = filterMainSleepScores(scores);
    if (mainSleeps.isEmpty) return null;

    mainSleeps.sort((a, b) => b.sleepDate.compareTo(a.sleepDate));
    return mainSleeps.first;
  }

  /// Classify and sort sessions into categories
  static Map<SleepType, List<SleepData31>> categorizeSessions(
      List<SleepData31> sessions) {
    final Map<SleepType, List<SleepData31>> categories = {
      SleepType.mainNightSleep: [],
      SleepType.napShort: [],
      SleepType.napLong: [],
      SleepType.insufficient: [],
    };

    for (final session in sessions) {
      final type = classifySession(session);
      categories[type]!.add(session);
    }

    // Sort each category by timestamp (most recent first)
    for (final list in categories.values) {
      list.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    }

    return categories;
  }

  /// Get sleep statistics by type
  static Map<String, dynamic> getStatistics(List<SleepData31> sessions) {
    final categories = categorizeSessions(sessions);

    return {
      'totalSessions': sessions.length,
      'mainNightSleeps': categories[SleepType.mainNightSleep]!.length,
      'shortNaps': categories[SleepType.napShort]!.length,
      'longNaps': categories[SleepType.napLong]!.length,
      'insufficient': categories[SleepType.insufficient]!.length,
      'avgMainSleepDuration': _calculateAvgDuration(
          categories[SleepType.mainNightSleep]!),
      'avgNapDuration': _calculateAvgDuration([
        ...categories[SleepType.napShort]!,
        ...categories[SleepType.napLong]!,
      ]),
    };
  }

  /// Calculate average duration for sessions
  static Duration _calculateAvgDuration(List<SleepData31> sessions) {
    if (sessions.isEmpty) return Duration.zero;

    final totalMinutes = sessions.fold<int>(
      0,
      (sum, s) => sum + (s.activityIndices.length * 5),
    );

    return Duration(minutes: totalMinutes ~/ sessions.length);
  }

  /// Check if time is during night hours
  static bool isNightTime(DateTime time) {
    final hour = time.hour;
    return hour >= nightStartHour || hour <= nightEndHour;
  }

  /// Get human-readable time of day
  static String getTimeOfDayLabel(DateTime time) {
    final hour = time.hour;

    if (hour >= 22 || hour < 6) return 'Late Night';
    if (hour >= 6 && hour < 12) return 'Morning';
    if (hour >= 12 && hour < 18) return 'Afternoon';
    if (hour >= 18 && hour < 22) return 'Evening';

    return 'Night';
  }

  /// Create classified session wrapper
  static ClassifiedSleepSession classifyWithMetadata(SleepData31 data) {
    return ClassifiedSleepSession(
      data: data,
      type: classifySession(data),
      durationMinutes: data.activityIndices.length * 5,
      timestamp: data.timestamp,
    );
  }

  /// Batch classify sessions
  static List<ClassifiedSleepSession> classifyAll(List<SleepData31> sessions) {
    return sessions.map(classifyWithMetadata).toList();
  }

  /// Filter for quality sleeps (excluding insufficient)
  static List<SleepData31> filterQualifying(List<SleepData31> sessions) {
    return sessions
        .where((s) => classifySession(s).isQualifying)
        .toList();
  }

  /// Get sleep type distribution summary
  static String getSummary(List<SleepData31> sessions) {
    final stats = getStatistics(sessions);
    return '${stats['mainNightSleeps']} night sleeps, '
        '${stats['shortNaps']} short naps, '
        '${stats['longNaps']} long naps';
  }
}
