import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/manual_test_result.dart';

class ManualTestStorage {
  static const String _keyPrefix = 'manual_test_results';
  
  // Salva un risultato del test
  static Future<void> saveTestResult(ManualTestResult result) async {
    final prefs = await SharedPreferences.getInstance();
    
    // Genera una chiave unica basata su timestamp e tipo di test
    final key = '${_keyPrefix}_${result.timestamp.millisecondsSinceEpoch}_${result.testType.name}';
    
    // Salva il risultato come JSON
    final jsonString = json.encode(result.toJson());
    await prefs.setString(key, jsonString);
  }
  
  // Ottieni tutti i risultati salvati
  static Future<List<ManualTestResult>> getAllResults() async {
    final prefs = await SharedPreferences.getInstance();
    final keys = prefs.getKeys().where((key) => key.startsWith(_keyPrefix)).toList();
    
    final results = <ManualTestResult>[];
    for (final key in keys) {
      try {
        final jsonString = prefs.getString(key);
        if (jsonString != null) {
          final jsonMap = json.decode(jsonString) as Map<String, dynamic>;
          results.add(ManualTestResult.fromJson(jsonMap));
        }
      } catch (e) {
        // Ignora i risultati corrotti e continua
        debugPrint('Error loading test result from key $key: $e');
      }
    }
    
    // Ordina per timestamp (più recenti prima)
    results.sort((a, b) => b.timestamp.compareTo(a.timestamp));
    return results;
  }
  
  // Ottieni i risultati per una data specifica
  static Future<List<ManualTestResult>> getResultsByDate(DateTime date) async {
    final allResults = await getAllResults();
    
    final startOfDay = DateTime(date.year, date.month, date.day);
    final endOfDay = startOfDay.add(const Duration(days: 1));
    
    return allResults
        .where((result) => 
            result.timestamp.isAfter(startOfDay) && 
            result.timestamp.isBefore(endOfDay))
        .toList();
  }
  
  // Raggruppa i risultati per giorno
  static Future<List<DailyTestResults>> getResultsGroupedByDay() async {
    final allResults = await getAllResults();
    
    final Map<String, List<ManualTestResult>> dayGroups = {};
    
    for (final result in allResults) {
      final dateKey = '${result.timestamp.year}-${result.timestamp.month.toString().padLeft(2, '0')}-${result.timestamp.day.toString().padLeft(2, '0')}';
      
      if (!dayGroups.containsKey(dateKey)) {
        dayGroups[dateKey] = [];
      }
      dayGroups[dateKey]!.add(result);
    }
    
    final dailyResults = <DailyTestResults>[];
    for (final entry in dayGroups.entries) {
      dailyResults.add(DailyTestResults(
        date: entry.key,
        results: entry.value,
      ));
    }
    
    // Ordina per data (più recenti prima)
    dailyResults.sort((a, b) => b.date.compareTo(a.date));
    return dailyResults;
  }
  
  // Cancella tutti i risultati (per debug/testing)
  static Future<void> clearAllResults() async {
    final prefs = await SharedPreferences.getInstance();
    final keys = prefs.getKeys().where((key) => key.startsWith(_keyPrefix)).toList();
    
    for (final key in keys) {
      await prefs.remove(key);
    }
  }
  
  // Ottieni statistiche sui risultati salvati
  static Future<Map<String, dynamic>> getStorageStats() async {
    final allResults = await getAllResults();
    
    final stats = <String, int>{};
    for (final result in allResults) {
      final type = result.testType.name;
      stats[type] = (stats[type] ?? 0) + 1;
    }
    
    return {
      'totalResults': allResults.length,
      'resultsByType': stats,
      'oldestResult': allResults.isNotEmpty ? allResults.last.timestamp.toIso8601String() : null,
      'newestResult': allResults.isNotEmpty ? allResults.first.timestamp.toIso8601String() : null,
    };
  }
}
