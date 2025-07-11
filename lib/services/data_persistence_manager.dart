import 'package:flutter/foundation.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'dart:async';

/// Data persistence manager for storing HRV sessions, battery history, and other data
class DataPersistenceManager {
  static const String _hrvSessionsKey = 'hrv_sessions';
  static const String _batteryHistoryKey = 'battery_history';
  static const String _settingsKey = 'app_settings';
  static const String _deviceInfoKey = 'device_info';
  
  static const int _maxHrvSessions = 100;
  static const int _maxBatteryEntries = 1000;
  
  SharedPreferences? _prefs;
  
  /// Initialize the persistence manager
  Future<void> initialize() async {
    _prefs = await SharedPreferences.getInstance();
    debugPrint('📁 Data persistence manager initialized');
  }

  // HRV Sessions Management
  
  /// Save HRV session
  Future<void> saveHrvSession(HrvSession session) async {
    await _ensureInitialized();
    
    List<HrvSession> sessions = await getHrvSessions();
    sessions.add(session);
    
    // Keep only the most recent sessions
    if (sessions.length > _maxHrvSessions) {
      sessions.removeRange(0, sessions.length - _maxHrvSessions);
    }
    
    // Sort by timestamp (most recent first)
    sessions.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    
    String jsonData = jsonEncode(sessions.map((s) => s.toJson()).toList());
    await _prefs!.setString(_hrvSessionsKey, jsonData);
    
    debugPrint('💾 HRV session saved: ${session.id}');
  }

  /// Get all HRV sessions
  Future<List<HrvSession>> getHrvSessions() async {
    await _ensureInitialized();
    
    String? jsonData = _prefs!.getString(_hrvSessionsKey);
    if (jsonData == null || jsonData.isEmpty) return [];
    
    try {
      List<dynamic> jsonList = jsonDecode(jsonData);
      return jsonList.map((json) => HrvSession.fromJson(json)).toList();
    } catch (e) {
      debugPrint('❌ Error loading HRV sessions: $e');
      return [];
    }
  }

  /// Get recent HRV sessions
  Future<List<HrvSession>> getRecentHrvSessions({int limit = 10}) async {
    List<HrvSession> sessions = await getHrvSessions();
    return sessions.take(limit).toList();
  }

  /// Delete HRV session
  Future<void> deleteHrvSession(String sessionId) async {
    List<HrvSession> sessions = await getHrvSessions();
    sessions.removeWhere((session) => session.id == sessionId);
    
    String jsonData = jsonEncode(sessions.map((s) => s.toJson()).toList());
    await _prefs!.setString(_hrvSessionsKey, jsonData);
    
    debugPrint('🗑️ HRV session deleted: $sessionId');
  }

  // Battery History Management
  
  /// Save battery reading
  Future<void> saveBatteryReading(BatteryReading reading) async {
    await _ensureInitialized();
    
    List<BatteryReading> history = await getBatteryHistory();
    history.add(reading);
    
    // Keep only the most recent readings
    if (history.length > _maxBatteryEntries) {
      history.removeRange(0, history.length - _maxBatteryEntries);
    }
    
    // Sort by timestamp (most recent first)
    history.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    
    String jsonData = jsonEncode(history.map((r) => r.toJson()).toList());
    await _prefs!.setString(_batteryHistoryKey, jsonData);
  }

  /// Get battery history
  Future<List<BatteryReading>> getBatteryHistory() async {
    await _ensureInitialized();
    
    String? jsonData = _prefs!.getString(_batteryHistoryKey);
    if (jsonData == null || jsonData.isEmpty) return [];
    
    try {
      List<dynamic> jsonList = jsonDecode(jsonData);
      return jsonList.map((json) => BatteryReading.fromJson(json)).toList();
    } catch (e) {
      debugPrint('❌ Error loading battery history: $e');
      return [];
    }
  }

  /// Get recent battery readings
  Future<List<BatteryReading>> getRecentBatteryReadings({int limit = 100}) async {
    List<BatteryReading> readings = await getBatteryHistory();
    return readings.take(limit).toList();
  }

  /// Clean old battery readings
  Future<void> cleanOldBatteryReadings({Duration? olderThan}) async {
    olderThan ??= const Duration(days: 30);
    DateTime cutoff = DateTime.now().subtract(olderThan);
    
    List<BatteryReading> readings = await getBatteryHistory();
    readings.removeWhere((reading) => reading.timestamp.isBefore(cutoff));
    
    String jsonData = jsonEncode(readings.map((r) => r.toJson()).toList());
    await _prefs!.setString(_batteryHistoryKey, jsonData);
    
    debugPrint('🧹 Cleaned old battery readings older than $olderThan');
  }

  // App Settings Management
  
  /// Save app settings
  Future<void> saveSettings(Map<String, dynamic> settings) async {
    await _ensureInitialized();
    
    String jsonData = jsonEncode(settings);
    await _prefs!.setString(_settingsKey, jsonData);
    
    debugPrint('⚙️ Settings saved');
  }

  /// Get app settings
  Future<Map<String, dynamic>> getSettings() async {
    await _ensureInitialized();
    
    String? jsonData = _prefs!.getString(_settingsKey);
    if (jsonData == null || jsonData.isEmpty) return {};
    
    try {
      return Map<String, dynamic>.from(jsonDecode(jsonData));
    } catch (e) {
      debugPrint('❌ Error loading settings: $e');
      return {};
    }
  }

  /// Save device info
  Future<void> saveDeviceInfo(Map<String, dynamic> deviceInfo) async {
    await _ensureInitialized();
    
    String jsonData = jsonEncode(deviceInfo);
    await _prefs!.setString(_deviceInfoKey, jsonData);
    
    debugPrint('📱 Device info saved');
  }

  /// Get device info
  Future<Map<String, dynamic>> getDeviceInfo() async {
    await _ensureInitialized();
    
    String? jsonData = _prefs!.getString(_deviceInfoKey);
    if (jsonData == null || jsonData.isEmpty) return {};
    
    try {
      return Map<String, dynamic>.from(jsonDecode(jsonData));
    } catch (e) {
      debugPrint('❌ Error loading device info: $e');
      return {};
    }
  }

  // Data Export/Import
  
  /// Export all data
  Future<Map<String, dynamic>> exportAllData() async {
    return {
      'hrv_sessions': await getHrvSessions(),
      'battery_history': await getBatteryHistory(),
      'settings': await getSettings(),
      'device_info': await getDeviceInfo(),
      'export_timestamp': DateTime.now().toIso8601String(),
    };
  }

  /// Import data
  Future<void> importData(Map<String, dynamic> data) async {
    await _ensureInitialized();
    
    try {
      // Import HRV sessions
      if (data['hrv_sessions'] != null) {
        List<HrvSession> sessions = (data['hrv_sessions'] as List)
            .map((json) => HrvSession.fromJson(json))
            .toList();
        
        String jsonData = jsonEncode(sessions.map((s) => s.toJson()).toList());
        await _prefs!.setString(_hrvSessionsKey, jsonData);
      }
      
      // Import battery history
      if (data['battery_history'] != null) {
        List<BatteryReading> readings = (data['battery_history'] as List)
            .map((json) => BatteryReading.fromJson(json))
            .toList();
        
        String jsonData = jsonEncode(readings.map((r) => r.toJson()).toList());
        await _prefs!.setString(_batteryHistoryKey, jsonData);
      }
      
      // Import settings
      if (data['settings'] != null) {
        await saveSettings(Map<String, dynamic>.from(data['settings']));
      }
      
      // Import device info
      if (data['device_info'] != null) {
        await saveDeviceInfo(Map<String, dynamic>.from(data['device_info']));
      }
      
      debugPrint('📥 Data imported successfully');
    } catch (e) {
      debugPrint('❌ Error importing data: $e');
      throw Exception('Failed to import data: $e');
    }
  }

  /// Clear all data
  Future<void> clearAllData() async {
    await _ensureInitialized();
    
    await _prefs!.remove(_hrvSessionsKey);
    await _prefs!.remove(_batteryHistoryKey);
    await _prefs!.remove(_settingsKey);
    await _prefs!.remove(_deviceInfoKey);
    
    debugPrint('🗑️ All data cleared');
  }

  /// Ensure initialization
  Future<void> _ensureInitialized() async {
    if (_prefs == null) {
      await initialize();
    }
  }

  /// Get storage usage info
  Future<StorageInfo> getStorageInfo() async {
    await _ensureInitialized();
    
    int hrvSessions = (await getHrvSessions()).length;
    int batteryReadings = (await getBatteryHistory()).length;
    
    return StorageInfo(
      hrvSessionsCount: hrvSessions,
      batteryReadingsCount: batteryReadings,
      totalKeys: _prefs!.getKeys().length,
    );
  }
}

/// HRV Session model
class HrvSession {
  final String id;
  final DateTime timestamp;
  final Duration duration;
  final List<double> hrvValues;
  final double averageHrv;
  final Map<String, dynamic> metadata;

  HrvSession({
    required this.id,
    required this.timestamp,
    required this.duration,
    required this.hrvValues,
    required this.averageHrv,
    this.metadata = const {},
  });

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'timestamp': timestamp.toIso8601String(),
      'duration': duration.inMilliseconds,
      'hrv_values': hrvValues,
      'average_hrv': averageHrv,
      'metadata': metadata,
    };
  }

  static HrvSession fromJson(Map<String, dynamic> json) {
    return HrvSession(
      id: json['id'],
      timestamp: DateTime.parse(json['timestamp']),
      duration: Duration(milliseconds: json['duration']),
      hrvValues: List<double>.from(json['hrv_values']),
      averageHrv: json['average_hrv'].toDouble(),
      metadata: Map<String, dynamic>.from(json['metadata'] ?? {}),
    );
  }
}

/// Battery reading model
class BatteryReading {
  final DateTime timestamp;
  final int level;
  final bool isCharging;
  final double? voltage;
  final double? temperature;

  BatteryReading({
    required this.timestamp,
    required this.level,
    required this.isCharging,
    this.voltage,
    this.temperature,
  });

  Map<String, dynamic> toJson() {
    return {
      'timestamp': timestamp.toIso8601String(),
      'level': level,
      'is_charging': isCharging,
      'voltage': voltage,
      'temperature': temperature,
    };
  }

  static BatteryReading fromJson(Map<String, dynamic> json) {
    return BatteryReading(
      timestamp: DateTime.parse(json['timestamp']),
      level: json['level'],
      isCharging: json['is_charging'],
      voltage: json['voltage']?.toDouble(),
      temperature: json['temperature']?.toDouble(),
    );
  }
}

/// Storage information
class StorageInfo {
  final int hrvSessionsCount;
  final int batteryReadingsCount;
  final int totalKeys;

  StorageInfo({
    required this.hrvSessionsCount,
    required this.batteryReadingsCount,
    required this.totalKeys,
  });

  @override
  String toString() {
    return 'StorageInfo(HRV sessions: $hrvSessionsCount, Battery readings: $batteryReadingsCount, Total keys: $totalKeys)';
  }
}

/// Global data persistence manager instance
final DataPersistenceManager dataPersistenceManager = DataPersistenceManager();
