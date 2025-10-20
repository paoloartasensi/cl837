/// Sleep History Manager
/// 
/// Manages persistent storage of sleep data and scores
/// Uses shared_preferences for lightweight data storage
/// 
/// Features:
/// - Save/load sleep sessions
/// - Save/load sleep scores
/// - Query by date range
/// - Calculate trends and averages
/// - Export data

import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/historical_data.dart';
import '../models/sleep_score.dart';
import '../models/smart_alarm.dart';

class SleepHistoryManager {
  static final SleepHistoryManager _instance = SleepHistoryManager._internal();
  factory SleepHistoryManager() => _instance;
  SleepHistoryManager._internal();

  static const String _keyPrefix = 'sleep_history_';
  static const String _keyScorePrefix = 'sleep_score_';
  static const String _keyAlarmPrefix = 'smart_alarm_';
  static const String _keySessionList = 'sleep_session_list';
  static const String _keyLastSync = 'sleep_last_sync';

  SharedPreferences? _prefs;

  /// Initialize the service
  Future<void> initialize() async {
    _prefs ??= await SharedPreferences.getInstance();
  }

  /// Save sleep session
  Future<bool> saveSleepSession(SleepData31 sleepData) async {
    await initialize();
    
    final key = _getSessionKey(sleepData.timestamp);
    final json = jsonEncode(sleepData.toJson());
    
    // Save session data
    final saved = await _prefs!.setString(key, json);
    
    if (saved) {
      // Add to session list
      await _addToSessionList(sleepData.timestamp);
    }
    
    return saved;
  }

  /// Load sleep session
  Future<SleepData31?> loadSleepSession(DateTime date) async {
    await initialize();
    
    final key = _getSessionKey(date);
    final json = _prefs!.getString(key);
    
    if (json == null) return null;
    
    try {
      final map = jsonDecode(json) as Map<String, dynamic>;
      return SleepData31.fromJson(map);
    } catch (e) {
      print('❌ Error loading sleep session: $e');
      return null;
    }
  }

  /// Save sleep score
  Future<bool> saveSleepScore(SleepScore score) async {
    await initialize();
    
    final key = _getScoreKey(score.sleepDate);
    final json = jsonEncode(score.toJson());
    
    return await _prefs!.setString(key, json);
  }

  /// Load sleep score
  Future<SleepScore?> loadSleepScore(DateTime date) async {
    await initialize();
    
    final key = _getScoreKey(date);
    final json = _prefs!.getString(key);
    
    if (json == null) return null;
    
    try {
      final map = jsonDecode(json) as Map<String, dynamic>;
      return SleepScore.fromJson(map);
    } catch (e) {
      print('❌ Error loading sleep score: $e');
      return null;
    }
  }

  /// Get all sleep sessions
  Future<List<SleepData31>> getAllSessions() async {
    await initialize();
    
    final sessionDates = await _getSessionList();
    final sessions = <SleepData31>[];
    
    for (final date in sessionDates) {
      final session = await loadSleepSession(date);
      if (session != null) {
        sessions.add(session);
      }
    }
    
    // Sort by date (newest first)
    sessions.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    
    return sessions;
  }

  /// Get all sleep scores
  Future<List<SleepScore>> getAllScores() async {
    await initialize();
    
    final sessionDates = await _getSessionList();
    final scores = <SleepScore>[];
    
    for (final date in sessionDates) {
      final score = await loadSleepScore(date);
      if (score != null) {
        scores.add(score);
      }
    }
    
    // Sort by date (newest first)
    scores.sort((a, b) => b.sleepDate.compareTo(a.sleepDate));
    
    return scores;
  }

  /// Get sessions in date range
  Future<List<SleepData31>> getSessionsInRange(
    DateTime startDate,
    DateTime endDate,
  ) async {
    final allSessions = await getAllSessions();
    
    return allSessions.where((session) {
      return session.timestamp.isAfter(startDate) &&
             session.timestamp.isBefore(endDate);
    }).toList();
  }

  /// Get scores in date range
  Future<List<SleepScore>> getScoresInRange(
    DateTime startDate,
    DateTime endDate,
  ) async {
    final allScores = await getAllScores();
    
    return allScores.where((score) {
      return score.sleepDate.isAfter(startDate) &&
             score.sleepDate.isBefore(endDate);
    }).toList();
  }

  /// Get last N sessions
  Future<List<SleepData31>> getRecentSessions(int count) async {
    final allSessions = await getAllSessions();
    return allSessions.take(count).toList();
  }

  /// Get last N scores
  Future<List<SleepScore>> getRecentScores(int count) async {
    final allScores = await getAllScores();
    return allScores.take(count).toList();
  }

  /// Delete session
  Future<bool> deleteSession(DateTime date) async {
    await initialize();
    
    final sessionKey = _getSessionKey(date);
    final scoreKey = _getScoreKey(date);
    
    // Delete both session and score
    await _prefs!.remove(sessionKey);
    await _prefs!.remove(scoreKey);
    
    // Remove from session list
    await _removeFromSessionList(date);
    
    return true;
  }

  /// Clear all data
  Future<void> clearAllData() async {
    await initialize();
    
    final keys = _prefs!.getKeys();
    final sleepKeys = keys.where((key) => 
      key.startsWith(_keyPrefix) || 
      key.startsWith(_keyScorePrefix) ||
      key == _keySessionList
    );
    
    for (final key in sleepKeys) {
      await _prefs!.remove(key);
    }
  }

  /// Save smart alarm
  Future<bool> saveSmartAlarm(SmartAlarm alarm) async {
    await initialize();
    
    final key = '$_keyAlarmPrefix${alarm.desiredWakeTime.toIso8601String()}';
    final json = jsonEncode(alarm.toJson());
    
    return await _prefs!.setString(key, json);
  }

  /// Load active smart alarm
  Future<SmartAlarm?> loadActiveAlarm() async {
    await initialize();
    
    final keys = _prefs!.getKeys();
    final alarmKeys = keys.where((key) => key.startsWith(_keyAlarmPrefix));
    
    for (final key in alarmKeys) {
      final json = _prefs!.getString(key);
      if (json != null) {
        try {
          final map = jsonDecode(json) as Map<String, dynamic>;
          final alarm = SmartAlarm.fromJson(map);
          if (alarm.isEnabled) {
            return alarm;
          }
        } catch (e) {
          print('❌ Error loading alarm: $e');
        }
      }
    }
    
    return null;
  }

  /// Delete alarm
  Future<bool> deleteAlarm(DateTime alarmTime) async {
    await initialize();
    
    final key = '$_keyAlarmPrefix${alarmTime.toIso8601String()}';
    return await _prefs!.remove(key);
  }

  /// Get statistics
  Future<SleepStatistics> getStatistics({int days = 30}) async {
    final endDate = DateTime.now();
    final startDate = endDate.subtract(Duration(days: days));
    
    final scores = await getScoresInRange(startDate, endDate);
    
    if (scores.isEmpty) {
      return SleepStatistics.empty();
    }
    
    // Calculate averages
    final avgScore = scores.fold(0.0, (sum, s) => sum + s.totalScore) / scores.length;
    final avgDuration = Duration(
      minutes: scores.fold(0, (sum, s) => sum + s.totalSleepTime.inMinutes) ~/ scores.length,
    );
    final avgEfficiency = scores.fold(0.0, (sum, s) => sum + s.sleepEfficiency) / scores.length;
    final avgDeepSleep = scores.fold(0, (sum, s) => sum + s.deepSleepMinutes) ~/ scores.length;
    final avgAwakenings = scores.fold(0, (sum, s) => sum + s.awakenings) ~/ scores.length;
    
    // Find best and worst nights
    scores.sort((a, b) => b.totalScore.compareTo(a.totalScore));
    final bestNight = scores.first;
    final worstNight = scores.last;
    
    return SleepStatistics(
      periodDays: days,
      totalNights: scores.length,
      averageScore: avgScore,
      averageDuration: avgDuration,
      averageEfficiency: avgEfficiency,
      averageDeepSleep: avgDeepSleep,
      averageAwakenings: avgAwakenings,
      bestNight: bestNight,
      worstNight: worstNight,
      scores: scores,
    );
  }

  /// Update last sync time
  Future<void> updateLastSync() async {
    await initialize();
    await _prefs!.setString(_keyLastSync, DateTime.now().toIso8601String());
  }

  /// Get last sync time
  Future<DateTime?> getLastSync() async {
    await initialize();
    final syncStr = _prefs!.getString(_keyLastSync);
    return syncStr != null ? DateTime.parse(syncStr) : null;
  }

  // Private helper methods

  String _getSessionKey(DateTime date) {
    return '$_keyPrefix${_formatDate(date)}';
  }

  String _getScoreKey(DateTime date) {
    return '$_keyScorePrefix${_formatDate(date)}';
  }

  String _formatDate(DateTime date) {
    return '${date.year}-${date.month.toString().padLeft(2, '0')}-${date.day.toString().padLeft(2, '0')}';
  }

  Future<List<DateTime>> _getSessionList() async {
    await initialize();
    
    final listJson = _prefs!.getString(_keySessionList);
    if (listJson == null) return [];
    
    try {
      final List<dynamic> dateStrings = jsonDecode(listJson);
      return dateStrings
          .map((s) => DateTime.parse(s as String))
          .toList();
    } catch (e) {
      print('❌ Error loading session list: $e');
      return [];
    }
  }

  Future<void> _addToSessionList(DateTime date) async {
    final list = await _getSessionList();
    
    // Check if already in list
    final dateStr = date.toIso8601String();
    if (!list.any((d) => d.toIso8601String() == dateStr)) {
      list.add(date);
      list.sort((a, b) => b.compareTo(a)); // Newest first
      
      final json = jsonEncode(list.map((d) => d.toIso8601String()).toList());
      await _prefs!.setString(_keySessionList, json);
    }
  }

  Future<void> _removeFromSessionList(DateTime date) async {
    final list = await _getSessionList();
    final dateStr = date.toIso8601String();
    
    list.removeWhere((d) => d.toIso8601String() == dateStr);
    
    final json = jsonEncode(list.map((d) => d.toIso8601String()).toList());
    await _prefs!.setString(_keySessionList, json);
  }
}

/// Sleep statistics over a period
class SleepStatistics {
  final int periodDays;
  final int totalNights;
  final double averageScore;
  final Duration averageDuration;
  final double averageEfficiency;
  final int averageDeepSleep;
  final int averageAwakenings;
  final SleepScore? bestNight;
  final SleepScore? worstNight;
  final List<SleepScore> scores;

  SleepStatistics({
    required this.periodDays,
    required this.totalNights,
    required this.averageScore,
    required this.averageDuration,
    required this.averageEfficiency,
    required this.averageDeepSleep,
    required this.averageAwakenings,
    this.bestNight,
    this.worstNight,
    required this.scores,
  });

  factory SleepStatistics.empty() {
    return SleepStatistics(
      periodDays: 0,
      totalNights: 0,
      averageScore: 0,
      averageDuration: Duration.zero,
      averageEfficiency: 0,
      averageDeepSleep: 0,
      averageAwakenings: 0,
      scores: [],
    );
  }

  bool get hasData => totalNights > 0;

  String get averageDurationFormatted {
    final hours = averageDuration.inHours;
    final minutes = averageDuration.inMinutes.remainder(60);
    return '${hours}h ${minutes}m';
  }
}
