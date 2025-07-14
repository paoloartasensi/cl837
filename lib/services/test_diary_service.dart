import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/foundation.dart';
import '../models/test_record.dart';

/// Servizio per gestire il diario dei test con SharedPreferences
class TestDiaryService {
  static const String _keyTestRecords = 'test_records';
  static const String _keyLastBackup = 'last_backup';
  
  static TestDiaryService? _instance;
  static TestDiaryService get instance => _instance ??= TestDiaryService._();
  
  TestDiaryService._();

  /// Salva un nuovo record nel diario
  Future<void> saveTestRecord(TestRecord record) async {
    final prefs = await SharedPreferences.getInstance();
    final records = await getAllRecords();
    
    records.add(record);
    
    // Ordina per timestamp più recente
    records.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    
    final jsonList = records.map((r) => r.toJson()).toList();
    await prefs.setString(_keyTestRecords, jsonEncode(jsonList));
    
    debugPrint('📝 Test record saved: ${record.summary}');
  }

  /// Recupera tutti i record dal diario
  Future<List<TestRecord>> getAllRecords() async {
    final prefs = await SharedPreferences.getInstance();
    final jsonString = prefs.getString(_keyTestRecords);
    
    if (jsonString == null) return [];
    
    try {
      final jsonList = jsonDecode(jsonString) as List;
      final records = <TestRecord>[];
      
      for (final json in jsonList) {
        try {
          final record = TestRecord.fromJson(json);
          records.add(record);
        } catch (e) {
          debugPrint('⚠️ Skipping corrupted record: $e');
          // Skip corrupted records instead of failing completely
        }
      }
      
      return records;
    } catch (e) {
      debugPrint('❌ Error loading test records, clearing corrupted data: $e');
      // Clear corrupted data and return empty list
      await prefs.remove(_keyTestRecords);
      return [];
    }
  }

  /// Recupera record filtrati per tipo
  Future<List<TestRecord>> getRecordsByType(TestType type) async {
    final allRecords = await getAllRecords();
    return allRecords.where((record) => record.type == type).toList();
  }

  /// Recupera record per data
  Future<List<TestRecord>> getRecordsByDate(DateTime date) async {
    final allRecords = await getAllRecords();
    return allRecords.where((record) {
      return record.timestamp.year == date.year &&
             record.timestamp.month == date.month &&
             record.timestamp.day == date.day;
    }).toList();
  }

  /// Recupera record nell'intervallo di date
  Future<List<TestRecord>> getRecordsByDateRange(DateTime start, DateTime end) async {
    final allRecords = await getAllRecords();
    return allRecords.where((record) {
      return record.timestamp.isAfter(start.subtract(const Duration(days: 1))) &&
             record.timestamp.isBefore(end.add(const Duration(days: 1)));
    }).toList();
  }

  /// Elimina un singolo record
  Future<bool> deleteRecord(String recordId) async {
    final prefs = await SharedPreferences.getInstance();
    final records = await getAllRecords();
    
    final initialCount = records.length;
    records.removeWhere((record) => record.id == recordId);
    
    if (records.length < initialCount) {
      final jsonList = records.map((r) => r.toJson()).toList();
      await prefs.setString(_keyTestRecords, jsonEncode(jsonList));
      debugPrint('🗑️ Test record deleted: $recordId');
      return true;
    }
    
    return false;
  }

  /// Elimina tutti i record
  Future<void> deleteAllRecords() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_keyTestRecords);
    debugPrint('🗑️ All test records deleted');
  }

  /// Elimina record per tipo
  Future<int> deleteRecordsByType(TestType type) async {
    final prefs = await SharedPreferences.getInstance();
    final records = await getAllRecords();
    
    final initialCount = records.length;
    records.removeWhere((record) => record.type == type);
    
    final deletedCount = initialCount - records.length;
    if (deletedCount > 0) {
      final jsonList = records.map((r) => r.toJson()).toList();
      await prefs.setString(_keyTestRecords, jsonEncode(jsonList));
      debugPrint('🗑️ Deleted $deletedCount ${type.displayName} records');
    }
    
    return deletedCount;
  }

  /// Elimina record più vecchi di X giorni
  Future<int> deleteOldRecords(int daysOld) async {
    final prefs = await SharedPreferences.getInstance();
    final records = await getAllRecords();
    final cutoffDate = DateTime.now().subtract(Duration(days: daysOld));
    
    final initialCount = records.length;
    records.removeWhere((record) => record.timestamp.isBefore(cutoffDate));
    
    final deletedCount = initialCount - records.length;
    if (deletedCount > 0) {
      final jsonList = records.map((r) => r.toJson()).toList();
      await prefs.setString(_keyTestRecords, jsonEncode(jsonList));
      debugPrint('🗑️ Deleted $deletedCount old records (older than $daysOld days)');
    }
    
    return deletedCount;
  }

  /// Aggiorna le note di un record
  Future<bool> updateRecordNotes(String recordId, String notes) async {
    final prefs = await SharedPreferences.getInstance();
    final records = await getAllRecords();
    
    final recordIndex = records.indexWhere((record) => record.id == recordId);
    if (recordIndex != -1) {
      records[recordIndex] = TestRecord(
        id: records[recordIndex].id,
        timestamp: records[recordIndex].timestamp,
        type: records[recordIndex].type,
        data: records[recordIndex].data,
        notes: notes.isNotEmpty ? notes : null,
      );
      
      final jsonList = records.map((r) => r.toJson()).toList();
      await prefs.setString(_keyTestRecords, jsonEncode(jsonList));
      debugPrint('📝 Updated notes for record: $recordId');
      return true;
    }
    
    return false;
  }

  /// Ottieni statistiche del diario
  Future<Map<String, dynamic>> getDiaryStatistics() async {
    final records = await getAllRecords();
    
    if (records.isEmpty) {
      return {
        'totalRecords': 0,
        'byType': <String, int>{},
        'oldestRecord': null,
        'newestRecord': null,
        'averagePerDay': 0.0,
      };
    }

    final byType = <TestType, int>{};
    for (final record in records) {
      byType[record.type] = (byType[record.type] ?? 0) + 1;
    }

    final oldest = records.last.timestamp;
    final newest = records.first.timestamp;
    final daysDiff = newest.difference(oldest).inDays + 1;
    final averagePerDay = records.length / daysDiff;

    return {
      'totalRecords': records.length,
      'byType': Map.fromEntries(byType.entries.map((e) => MapEntry(e.key.displayName, e.value))),
      'oldestRecord': oldest,
      'newestRecord': newest,
      'averagePerDay': averagePerDay,
      'daysCovered': daysDiff,
    };
  }

  /// Esporta tutti i record come JSON
  Future<String> exportRecordsAsJson() async {
    final records = await getAllRecords();
    final export = {
      'exportDate': DateTime.now().toIso8601String(),
      'totalRecords': records.length,
      'records': records.map((r) => r.toJson()).toList(),
    };
    
    await _updateLastBackup();
    return jsonEncode(export);
  }

  /// Importa record da JSON
  Future<int> importRecordsFromJson(String jsonString) async {
    try {
      final data = jsonDecode(jsonString);
      final importedRecords = (data['records'] as List)
          .map((json) => TestRecord.fromJson(json))
          .toList();
      
      final existingRecords = await getAllRecords();
      final existingIds = existingRecords.map((r) => r.id).toSet();
      
      // Aggiungi solo record nuovi (evita duplicati)
      final newRecords = importedRecords.where((r) => !existingIds.contains(r.id)).toList();
      
      for (final record in newRecords) {
        await saveTestRecord(record);
      }
      
      debugPrint('📥 Imported ${newRecords.length} new records');
      return newRecords.length;
    } catch (e) {
      debugPrint('❌ Error importing records: $e');
      return 0;
    }
  }

  /// Aggiorna timestamp ultimo backup
  Future<void> _updateLastBackup() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_keyLastBackup, DateTime.now().toIso8601String());
  }

  /// Ottieni timestamp ultimo backup
  Future<DateTime?> getLastBackupDate() async {
    final prefs = await SharedPreferences.getInstance();
    final dateString = prefs.getString(_keyLastBackup);
    return dateString != null ? DateTime.parse(dateString) : null;
  }

  /// Inizializza dati di test se il diario è vuoto
  Future<void> initializeSampleDataIfEmpty() async {
    try {
      final existingRecords = await getAllRecords();
      if (existingRecords.isNotEmpty) {
        debugPrint('📝 Diario già popolato con ${existingRecords.length} record');
        return;
      }
      
      debugPrint('📝 Inizializzando dati di test per il diario...');
      await _createSampleData();
    } catch (e) {
      debugPrint('❌ Error initializing sample data: $e');
      // In case of any error, try to clear and retry once
      try {
        await clearAndReinitialize();
      } catch (e2) {
        debugPrint('❌ Failed to recover from error: $e2');
      }
    }
  }

  /// Pulisce dati corrotti e reinizializza il diario
  Future<void> clearAndReinitialize() async {
    debugPrint('🔄 Clearing corrupted diary data and reinitializing...');
    await deleteAllRecords();
    await _createSampleData(); // Use internal method to avoid recursion
  }

  /// Crea i dati di esempio (metodo interno)
  Future<void> _createSampleData() async {
    debugPrint('📝 Creando dati di test...');
    
    // Heart Rate test
    final hrRecord = TestRecord.fromHeartRate(75, [800, 820, 810, 790]);
    await saveTestRecord(hrRecord);
    
    // SpO2 test
    final spo2Record = TestRecord.fromSpO2(98, 85, 'Good');
    await saveTestRecord(spo2Record);
    
    // Temperature test
    final tempRecord = TestRecord.fromTemperature(25.5, 32.1, 36.7);
    await saveTestRecord(tempRecord);
    
    // Sports test
    final sportsRecord = TestRecord.fromSports(1250, 85000, 42.5);
    await saveTestRecord(sportsRecord);
    
    // Rope skipping test
    final ropeRecord = TestRecord.fromRopeSkipping('Counter', 150, 120, 8.3);
    await saveTestRecord(ropeRecord);
    
    // Battery test
    final batteryRecord = TestRecord.fromBattery(85, false, 3850);
    await saveTestRecord(batteryRecord);
    
    debugPrint('📝 Dati di test creati con successo');
  }
}
