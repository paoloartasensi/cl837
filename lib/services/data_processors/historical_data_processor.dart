import 'package:flutter/foundation.dart';
import '../../models/historical_data.dart';

/// Processore specializzato per i dati storici del dispositivo Chileaf
/// Gestisce comandi 0x16 (Exercise History), 0x21 (HR History List), 0x22 (HR History Data)
class HistoricalDataProcessor {
  
  /// Processa i dati storici dell'esercizio (comando 0x16)
  /// Formato: 7 giorni di dati con UTC + steps + calories per ogni giorno
  static List<ExerciseHistoryData> processExerciseHistory(Uint8List data) {
    debugPrint('📊 Processing Exercise History (0x16) - ${data.length} bytes');
    List<ExerciseHistoryData> history = [];
    
    try {
      // Ogni entry dovrebbe essere 12 bytes: 4 UTC + 4 steps + 4 calories
      const int entrySize = 12;
      int numEntries = (data.length - 3) ~/ entrySize; // -3 per header (0xFF, length, command)
      
      debugPrint('📊 Exercise History: Expected $numEntries entries');
      
      for (int i = 0; i < numEntries && i < 7; i++) {
        int offset = 3 + (i * entrySize); // Start after header
        
        if (offset + entrySize <= data.length) {
          // Parse UTC timestamp (4 bytes, little endian)
          int utcTimestamp = data[offset] | 
                           (data[offset + 1] << 8) | 
                           (data[offset + 2] << 16) | 
                           (data[offset + 3] << 24);
          
          // Parse steps (4 bytes, little endian)
          int steps = data[offset + 4] | 
                     (data[offset + 5] << 8) | 
                     (data[offset + 6] << 16) | 
                     (data[offset + 7] << 24);
          
          // Parse calories (4 bytes, little endian, in 0.1 kcal units)
          int caloriesRaw = data[offset + 8] | 
                           (data[offset + 9] << 8) | 
                           (data[offset + 10] << 16) | 
                           (data[offset + 11] << 24);
          
          // Convert to actual values
          DateTime date = DateTime.fromMillisecondsSinceEpoch(utcTimestamp * 1000);
          double calories = caloriesRaw / 10.0; // Convert from 0.1 kcal units
          
          // Skip invalid entries (0xFFFFFFFF indicates no data)
          if (utcTimestamp != 0xFFFFFFFF && steps > 0) {
            ExerciseHistoryData entry = ExerciseHistoryData(
              date: date,
              steps: steps,
              calories: calories,
            );
            
            history.add(entry);
            debugPrint('📊 Exercise Day ${i + 1}: ${entry.toString()}');
          } else {
            debugPrint('📊 Exercise Day ${i + 1}: No data (0xFFFFFFFF)');
          }
        }
      }
      
      debugPrint('📊 Exercise History: Parsed ${history.length} valid entries');
      
    } catch (e) {
      debugPrint('❌ Error parsing Exercise History: $e');
    }
    
    return history;
  }
  
  /// Processa la lista degli storici HR (comando 0x21)
  /// Formato: Lista di timestamp UTC (0xFFFFFFFF = no data)
  static HeartRateHistoryList processHRHistoryList(Uint8List data) {
    debugPrint('💓 Processing HR History List (0x21) - ${data.length} bytes');
    List<DateTime> timestamps = [];
    
    try {
      // Ogni timestamp è 4 bytes
      const int timestampSize = 4;
      int numTimestamps = (data.length - 3) ~/ timestampSize; // -3 per header
      
      debugPrint('💓 HR History List: Expected $numTimestamps timestamps');
      
      for (int i = 0; i < numTimestamps; i++) {
        int offset = 3 + (i * timestampSize);
        
        if (offset + timestampSize <= data.length) {
          int utcTimestamp = data[offset] | 
                           (data[offset + 1] << 8) | 
                           (data[offset + 2] << 16) | 
                           (data[offset + 3] << 24);
          
          if (utcTimestamp != 0xFFFFFFFF) {
            DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(utcTimestamp * 1000);
            timestamps.add(timestamp);
            debugPrint('💓 HR Timestamp ${i + 1}: $timestamp');
          } else {
            debugPrint('💓 HR Timestamp ${i + 1}: No data (0xFFFFFFFF)');
          }
        }
      }
      
      debugPrint('💓 HR History List: Found ${timestamps.length} valid timestamps');
      
    } catch (e) {
      debugPrint('❌ Error parsing HR History List: $e');
    }
    
    return HeartRateHistoryList(timestamps: timestamps);
  }
  
  /// Processa i dati storici HR (comando 0x22)
  /// Formato: Usa UTC da 0x21; include heart rate + activity index
  static HeartRateHistoryData? processHRHistoryData(Uint8List data) {
    debugPrint('💓 Processing HR History Data (0x22) - ${data.length} bytes');
    
    try {
      if (data.length < 7) { // Minimum: header + timestamp
        debugPrint('❌ HR History Data too short');
        return null;
      }
      
      // Parse UTC timestamp (4 bytes after header)
      int utcTimestamp = data[3] | 
                       (data[4] << 8) | 
                       (data[5] << 16) | 
                       (data[6] << 24);
      
      DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(utcTimestamp * 1000);
      debugPrint('💓 HR History Data for: $timestamp');
      
      List<HeartRateHistoryEntry> entries = [];
      
      // Each HR entry: 1 byte HR + 1 byte activity index
      for (int i = 7; i < data.length - 1; i += 2) {
        if (i + 1 < data.length) {
          int heartRate = data[i];
          int activityIndex = data[i + 1];
          
          if (heartRate > 0 && heartRate < 200) { // Valid HR range
            // Estimate time within the session (every minute?)
            DateTime entryTime = timestamp.add(Duration(minutes: (i - 7) ~/ 2));
            
            HeartRateHistoryEntry entry = HeartRateHistoryEntry(
              heartRate: heartRate,
              activityIndex: activityIndex,
              time: entryTime,
            );
            
            entries.add(entry);
            debugPrint('💓 HR Entry: ${entry.toString()}');
          }
        }
      }
      
      debugPrint('💓 HR History Data: ${entries.length} entries parsed');
      
      return HeartRateHistoryData(
        timestamp: timestamp,
        entries: entries,
      );
      
    } catch (e) {
      debugPrint('❌ Error parsing HR History Data: $e');
      return null;
    }
  }
  
  /// Verifica se il comando contiene dati storici
  static bool isHistoricalCommand(int command) {
    return command == 0x16 || command == 0x21 || command == 0x22;
  }
  
  /// Gestisce tutti i tipi di dati storici
  static dynamic processHistoricalData(int command, Uint8List data) {
    switch (command) {
      case 0x16:
        return processExerciseHistory(data);
      case 0x21:
        return processHRHistoryList(data);
      case 0x22:
        return processHRHistoryData(data);
      default:
        debugPrint('❓ Unknown historical command: 0x${command.toRadixString(16)}');
        return null;
    }
  }
}
