import 'package:flutter/foundation.dart';
import '../../models/historical_data.dart';

/// Processore specializzato per i dati storici del dispositivo Chileaf
/// Gestisce comandi 0x16 (Exercise History), 0x21 (HR History List), 0x22 (HR History Data)
/// 
/// ANALISI MEMORIA DISPOSITIVO CL837:
/// - Capacità stimata: 1MB (8 Megabit) di memoria flash
/// - Gestione circolare: sovrascrive automaticamente i dati più vecchi
/// - HR History: ~128 sessioni max (timestamp corrotti indicano overflow)
/// - Exercise History: 7 giorni fissi (84 bytes totali)
/// - Ogni sessione HR: ~100-500 bytes (dipende dalla durata)
/// - Memoria totale per HR: ~64KB (128 sessioni × 500 bytes avg)
/// - Resto memoria: accelerometro, temperature, settings (~936KB)
class HistoricalDataProcessor {

  /// Analizza i timestamp HR per determinare la gestione memoria
  static Map<String, dynamic> analyzeMemoryUsage(List<DateTime> timestamps) {
    if (timestamps.isEmpty) return {'analysis': 'No data'};
    
    // Analizza la distribuzione temporale
    List<DateTime> sortedTimestamps = List.from(timestamps)..sort();
    DateTime earliest = sortedTimestamps.first;
    DateTime latest = sortedTimestamps.last;
    
    // Cerca timestamp corrotti (indicano overflow del buffer circolare)
    int corruptedCount = 0;
    int validCount = 0;
    
    for (DateTime timestamp in timestamps) {
      if (timestamp.year < 2020 || timestamp.year > 2030) {
        corruptedCount++;
      } else {
        validCount++;
      }
    }
    
    // Stima capacità memoria basata sui pattern osservati
    int estimatedSessionsCapacity = timestamps.length; // Attualmente 128
    int avgBytesPerSession = 300; // Stima basata su durata media sessioni
    int hrMemoryUsage = estimatedSessionsCapacity * avgBytesPerSession; // ~38KB
    
    return {
      'totalSessions': timestamps.length,
      'validSessions': validCount,
      'corruptedSessions': corruptedCount,
      'memoryPattern': corruptedCount > validCount ? 'circular_overflow' : 'normal',
      'estimatedHRMemory': hrMemoryUsage,
      'memoryType': 'Probabilmente 1MB Flash (8 Megabit)',
      'management': 'Buffer circolare - sovrascrive automaticamente',
      'earliest': earliest,
      'latest': latest,
      'analysis': corruptedCount > 0 
        ? 'Memoria piena: buffer circolare attivo, timestamp corrotti indicano overflow'
        : 'Memoria normale: ancora spazio disponibile'
    };
  }
  
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
          
          // Validate data ranges (realistic values)
          bool isValidTimestamp = utcTimestamp != 0xFFFFFFFF && date.year >= 2020 && date.year <= 2030;
          bool isValidSteps = steps > 0 && steps <= 100000; // Max 100k steps per day
          bool isValidCalories = calories >= 0 && calories <= 10000; // Max 10k kcal per day
          
          // Skip invalid entries
          if (isValidTimestamp && isValidSteps && isValidCalories) {
            ExerciseHistoryData entry = ExerciseHistoryData(
              date: date,
              steps: steps,
              calories: calories,
            );
            
            history.add(entry);
            debugPrint('📊✅ Exercise Day ${i + 1}: ${entry.toString()} (VALID)');
          } else {
            debugPrint('📊❌ Exercise Day ${i + 1}: INVALID DATA - Timestamp: ${isValidTimestamp ? 'OK' : 'BAD'}, Steps: ${isValidSteps ? steps : 'BAD ($steps)'}, Calories: ${isValidCalories ? calories.toStringAsFixed(1) : 'BAD (${calories.toStringAsFixed(1)})'}');
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
    debugPrint('💓 Raw HR History List data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    List<DateTime> timestamps = [];
    List<DateTime> validTimestamps = [];
    List<DateTime> corruptedTimestamps = [];
    
    try {
      // Ogni timestamp è 4 bytes
      const int timestampSize = 4;
      int numTimestamps = (data.length - 3) ~/ timestampSize; // -3 per header
      
      debugPrint('💓 HR History List: Expected $numTimestamps timestamps');
      
      if (numTimestamps == 0) {
        debugPrint('💓 HR History List: No timestamps found - device might not have HR history data');
        return const HeartRateHistoryList(timestamps: []);
      }
      
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
            
            // Validate timestamp (reasonable range)
            if (timestamp.year >= 2020 && timestamp.year <= 2030) {
              validTimestamps.add(timestamp);
              debugPrint('💓✅ HR Timestamp ${i + 1}: $timestamp (VALID)');
            } else {
              corruptedTimestamps.add(timestamp);
              debugPrint('💓❌ HR Timestamp ${i + 1}: $timestamp (CORRUPTED - year ${timestamp.year})');
            }
          } else {
            debugPrint('💓 HR Timestamp ${i + 1}: No data (0xFFFFFFFF)');
          }
        }
      }
      
      debugPrint('💓 HR History List Summary:');
      debugPrint('💓   Total: ${timestamps.length} timestamps');
      debugPrint('💓   Valid: ${validTimestamps.length} timestamps');
      debugPrint('💓   Corrupted: ${corruptedTimestamps.length} timestamps');
      debugPrint('💓   Empty slots: ${numTimestamps - timestamps.length}');
      
      // If most timestamps are corrupted, device memory is likely in circular buffer overflow
      if (corruptedTimestamps.length > validTimestamps.length) {
        debugPrint('💓⚠️ WARNING: Device memory appears to be in circular buffer overflow state');
        debugPrint('💓⚠️ Only using ${validTimestamps.length} valid timestamps for data requests');
        return HeartRateHistoryList(timestamps: validTimestamps);
      }
      
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
          
          // Validate HR range (30-220 BPM) and timestamp
          bool isValidHR = heartRate >= 30 && heartRate <= 220;
          bool isValidTimestamp = timestamp.year >= 2020 && timestamp.year <= 2030;
          
          if (isValidHR && isValidTimestamp) {
            // Estimate time within the session (every minute?)
            DateTime entryTime = timestamp.add(Duration(minutes: (i - 7) ~/ 2));
            
            HeartRateHistoryEntry entry = HeartRateHistoryEntry(
              heartRate: heartRate,
              activityIndex: activityIndex,
              time: entryTime,
            );
            
            entries.add(entry);
            debugPrint('💓✅ HR Entry: ${entry.toString()} (VALID)');
          } else {
            debugPrint('💓❌ HR Entry: HR=$heartRate (${isValidHR ? 'OK' : 'BAD'}), Time=${timestamp.year} (${isValidTimestamp ? 'OK' : 'BAD'}) - SKIPPED');
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
