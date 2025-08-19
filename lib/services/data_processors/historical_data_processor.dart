import 'package:flutter/foundation.dart';
import '../../models/historical_data.dart';
import 'enhanced_historical_data_processor.dart' as enhanced;

/// Processore specializzato per i dati storici del dispositivo Chileaf
/// Gestisce comandi 0x16 (Exercise History), 0x21 (HR History List), 0x22 (HR History Data)
/// ENHANCED: Ora include parser reverse-engineered dall'app originale
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
    debugPrint('📊 Raw Exercise History data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    List<ExerciseHistoryData> history = [];
    
    try {
      // PROVA FORMATO ALTERNATIVO: Come i sports data (3 bytes per campo)
      debugPrint('📊 TESTING ALTERNATIVE FORMAT (3 bytes per field):');
      if (data.length >= 15) {
        const int altEntrySize = 10; // 4 UTC + 3 steps + 3 calories
        int altNumEntries = (data.length - 3) ~/ altEntrySize;
        debugPrint('📊 Alternative format: $altNumEntries entries with 10 bytes each');
        
        for (int i = 0; i < altNumEntries && i < 7; i++) {
          int offset = 3 + (i * altEntrySize);
          if (offset + altEntrySize <= data.length) {
            // UTC (4 bytes)
            int utc = data[offset] | (data[offset + 1] << 8) | (data[offset + 2] << 16) | (data[offset + 3] << 24);
            // Steps (3 bytes)
            int steps = data[offset + 4] | (data[offset + 5] << 8) | (data[offset + 6] << 16);
            // Calories (3 bytes)
            int calories = data[offset + 7] | (data[offset + 8] << 8) | (data[offset + 9] << 16);
            
            if (utc != 0xFFFFFFFF && utc > 0 && utc < 2147483647) {
              DateTime date = DateTime.fromMillisecondsSinceEpoch(utc * 1000);
              double caloriesKcal = calories / 10.0;
              debugPrint('📊 ALT Entry $i: UTC=$utc (${date.toString().substring(0, 10)}), steps=$steps, calories=$caloriesKcal kcal');
              
              // Se i valori sembrano ragionevoli, usa questo formato
              if (steps < 100000 && calories < 10000) {
                ExerciseHistoryData entry = ExerciseHistoryData(
                  date: date,
                  steps: steps,
                  calories: caloriesKcal,
                );
                history.add(entry);
                debugPrint('📊 ✅ Alternative format looks good! Using this entry.');
                continue;
              }
            }
          }
        }
      }
      
      // Se il formato alternativo ha funzionato, ritorna
      if (history.isNotEmpty) {
        debugPrint('📊 Alternative format successful! Returning ${history.length} entries');
        return history;
      }
      
      // Altrimenti prova formato originale (4 bytes per campo)
      debugPrint('📊 Alternative format failed, trying original format (4 bytes per field):');
      
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
    debugPrint('💓 Raw HR History List data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    List<DateTime> timestamps = [];
    
    try {
      // Ogni timestamp è 4 bytes
      const int timestampSize = 4;
      int numTimestamps = (data.length - 3) ~/ timestampSize; // -3 per header
      
      debugPrint('💓 HR History List: Expected $numTimestamps timestamps');
      
      if (numTimestamps == 0) {
        debugPrint('💓 HR History List: No timestamps found - device might not have HR history data');
        return const HeartRateHistoryList(timestamps: []);
      }
      
      // Check if this is an "end of data" message (all 0xFFFFFFFF)
      if (numTimestamps == 1) {
        int testOffset = 3;
        if (testOffset + 4 <= data.length) {
          int testUtc = data[testOffset] | 
                       (data[testOffset + 1] << 8) | 
                       (data[testOffset + 2] << 16) | 
                       (data[testOffset + 3] << 24);
          if (testUtc == 0xFFFFFFFF) {
            debugPrint('💓 🛑 This is an END-OF-DATA message (0xFFFFFFFF) - ignoring to preserve existing data');
            return const HeartRateHistoryList(timestamps: [], isEndOfData: true);
          }
        }
      }
      
      for (int i = 0; i < numTimestamps; i++) {
        int offset = 3 + (i * timestampSize);
        
        if (offset + timestampSize <= data.length) {
          // Prova entrambi little-endian e big-endian per debug
          int utcTimestampLE = data[offset] | 
                             (data[offset + 1] << 8) | 
                             (data[offset + 2] << 16) | 
                             (data[offset + 3] << 24);
          
          int utcTimestampBE = (data[offset] << 24) | 
                             (data[offset + 1] << 16) | 
                             (data[offset + 2] << 8) | 
                             data[offset + 3];
          
          debugPrint('💓 🔍 Timestamp ${i + 1} DEBUG:');
          debugPrint('💓   Raw bytes: 0x${data[offset].toRadixString(16).padLeft(2, '0')} 0x${data[offset + 1].toRadixString(16).padLeft(2, '0')} 0x${data[offset + 2].toRadixString(16).padLeft(2, '0')} 0x${data[offset + 3].toRadixString(16).padLeft(2, '0')}');
          debugPrint('💓   Little-endian: $utcTimestampLE = ${DateTime.fromMillisecondsSinceEpoch(utcTimestampLE * 1000)}');
          debugPrint('💓   Big-endian: $utcTimestampBE = ${DateTime.fromMillisecondsSinceEpoch(utcTimestampBE * 1000)}');
          
          // ALGORITMO INTELLIGENTE per scegliere l'endian corretto
          // Criteri in ordine di priorità:
          // 1. Timestamp ragionevole (2020-2030)
          // 2. Vicinanza alla data corrente
          // 3. Preferenza per date recenti vs future
          
          DateTime nowDate = DateTime.now();
          DateTime dateLE = DateTime.fromMillisecondsSinceEpoch(utcTimestampLE * 1000);
          DateTime dateBE = DateTime.fromMillisecondsSinceEpoch(utcTimestampBE * 1000);
          
          bool leIsReasonable = dateLE.year >= 2020 && dateLE.year <= 2030;
          bool beIsReasonable = dateBE.year >= 2020 && dateBE.year <= 2030;
          
          bool useLE;
          String reason;
          
          if (leIsReasonable && !beIsReasonable) {
            useLE = true;
            reason = "Only LE is in valid range (2020-2030)";
          } else if (!leIsReasonable && beIsReasonable) {
            useLE = false;
            reason = "Only BE is in valid range (2020-2030)";
          } else if (leIsReasonable && beIsReasonable) {
            // Entrambi validi - scegli quello più vicino ad agosto 2025
            DateTime augustTarget = DateTime(2025, 8, 19);
            Duration diffLE = (dateLE.difference(augustTarget)).abs();
            Duration diffBE = (dateBE.difference(augustTarget)).abs();
            
            useLE = diffLE < diffBE;
            reason = "Both valid - chosen closer to Aug 2025 (LE: ${diffLE.inDays}d, BE: ${diffBE.inDays}d)";
          } else {
            // Nessuno dei due valido - scegli quello meno lontano dalla data corrente
            Duration diffLE = (dateLE.difference(nowDate)).abs();
            Duration diffBE = (dateBE.difference(nowDate)).abs();
            
            useLE = diffLE < diffBE;
            reason = "Neither valid - chosen closer to current date";
          }
          
          int utcTimestamp = useLE ? utcTimestampLE : utcTimestampBE;
          DateTime timestamp = useLE ? dateLE : dateBE;
          
          debugPrint('💓   ✅ Using ${useLE ? "Little-endian" : "Big-endian"}: $timestamp (UTC: $utcTimestamp)');
          debugPrint('💓   📝 Reason: $reason');
          
          if (utcTimestamp != 0xFFFFFFFF) {
            timestamps.add(timestamp);
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
        return processExerciseHistoryOfficial(data);
      case 0x21:
        return processHRHistoryList(data);
      case 0x22:
        return processHRHistoryData(data);
      default:
        debugPrint('❓ Unknown historical command: 0x${command.toRadixString(16)}');
        return null;
    }
  }

  /// Processa i dati storici dell'esercizio (comando 0x16) - FORMATO UFFICIALE
  /// FORMATO REALE osservato dal dispositivo CL837:
  /// - UTC: 4 bytes (little endian)  
  /// - Steps: 4 bytes (little endian) - spesso 0x00000000
  /// - Calories: 3 bytes (little endian) - spesso 0x3de0 = 1584.0 kcal
  /// Totale: 7 giorni × 11 bytes = 77 bytes payload (ma ricevuti 71 bytes)
  static List<ExerciseHistoryData> processExerciseHistoryOfficial(Uint8List data) {
    debugPrint('📋 REAL-WORLD Exercise History Processing (0x16) - ${data.length} bytes');
    debugPrint('📋 Raw data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    List<ExerciseHistoryData> history = [];
    
    try {
      if (data.length <= 3) {
        debugPrint('📋 ❌ No payload data - only header present');
        return history;
      }
      
      int payloadLength = data.length - 3;
      debugPrint('📋 Payload length: $payloadLength bytes');
      
      // FORMATO REALE osservato: Sembra essere variabile
      // Analizziamo la struttura reale dei dati
      debugPrint('📋 🔍 ANALYZING REAL DATA STRUCTURE:');
      
      // Pattern osservato: sembra che i dati abbiano UTC validi in posizioni specifiche
      List<int> utcPositions = [];
      
      // Cerchiamo pattern UTC validi (> 2020 e < 2030)
      for (int i = 3; i < data.length - 3; i++) {
        if (i + 4 <= data.length) {
          int testUtc = data[i] | 
                       (data[i + 1] << 8) | 
                       (data[i + 2] << 16) | 
                       (data[i + 3] << 24);
          
          if (testUtc > 1577836800 && testUtc < 1893456000) { // 2020-2030
            DateTime testDate = DateTime.fromMillisecondsSinceEpoch(testUtc * 1000);
            
            // Calcola quanti giorni fa era questa data
            Duration difference = DateTime.now().difference(testDate);
            int daysAgo = difference.inDays;
            
            debugPrint('📋 🎯 Found UTC at offset $i: $testUtc (${testDate.toString().substring(0, 10)}) - $daysAgo days ago');
            
            // Solo timestamp relativamente recenti (entro 1 anno)
            if (daysAgo <= 365) {
              utcPositions.add(i);
            } else {
              debugPrint('📋 ⏰ Skipping old data from ${testDate.toString().substring(0, 10)} ($daysAgo days ago)');
            }
          }
        }
      }
      
      if (utcPositions.isEmpty) {
        debugPrint('📋 ❌ No valid UTC timestamps found in data');
        return history;
      }
      
      // Prova a dedurre la struttura dai pattern UTC trovati
      for (int i = 0; i < utcPositions.length && i < 7; i++) {
        int utcOffset = utcPositions[i];
        
        // UTC (4 bytes)
        int utc = data[utcOffset] | 
                 (data[utcOffset + 1] << 8) | 
                 (data[utcOffset + 2] << 16) | 
                 (data[utcOffset + 3] << 24);
        
        DateTime date = DateTime.fromMillisecondsSinceEpoch(utc * 1000);
        
        // Cerca steps e calories nelle posizioni adiacenti
        int steps = 0;
        double calories = 0.0;
        
        // Prova a leggere steps (4 bytes dopo UTC)
        if (utcOffset + 7 < data.length) {
          steps = data[utcOffset + 4] | 
                 (data[utcOffset + 5] << 8) | 
                 (data[utcOffset + 6] << 16) | 
                 (data[utcOffset + 7] << 24);
        }
        
        // Prova a leggere calories (3 bytes dopo steps)
        if (utcOffset + 10 < data.length) {
          int caloriesRaw = data[utcOffset + 8] | 
                           (data[utcOffset + 9] << 8) | 
                           (data[utcOffset + 10] << 16);
          calories = caloriesRaw / 10.0;
          
          // Debug aggiuntivo per capire il formato calorie
          debugPrint('📋 🔬 Calories debug: bytes[${utcOffset + 8}-${utcOffset + 10}] = 0x${data[utcOffset + 8].toRadixString(16)} 0x${data[utcOffset + 9].toRadixString(16)} 0x${data[utcOffset + 10].toRadixString(16)} = $caloriesRaw raw = $calories kcal');
        }
        
        debugPrint('📋 Day $i: UTC=$utc, steps=$steps, calories_raw=${(calories * 10).toInt()}, calories=$calories');
        
        // Se abbiamo almeno una data valida, aggiungi l'entry
        if (date.year >= 2020 && date.year <= 2030) {
          // Valida gli steps per ragionevolezza (max 100,000 steps al giorno)
          if (steps > 100000) {
            debugPrint('📋 ⚠️ Steps value too high ($steps), probably corrupted data - resetting to 0');
            steps = 0;
          }
          
          // Le calorie sembrano non essere presenti nei dati storici di questo dispositivo
          // o usano un formato diverso - per ora manteniamo 0
          calories = 0.0;
          
          ExerciseHistoryData entry = ExerciseHistoryData(
            date: date,
            steps: steps,
            calories: calories,
          );
          
          history.add(entry);
          
          if (steps > 0) {
            debugPrint('📋 ✅ Valid entry: ${date.toString().substring(0, 10)}, $steps steps, $calories kcal');
          } else {
            debugPrint('📋 ⭕ Entry (no activity): ${date.toString().substring(0, 10)}, 0 steps, 0.0 kcal');
          }
        }
      }
      
      debugPrint('📋 Successfully parsed ${history.length} exercise history entries from real data');
      
    } catch (e) {
      debugPrint('❌ Error parsing Exercise History: $e');
    }
    
    return history;
  }

  /// Debug analyzer per dati di esercizio grezzi
  static List<ExerciseHistoryData> analyzeExerciseDataBytes(Uint8List data) {
    debugPrint('🔬 DETAILED EXERCISE DATA ANALYSIS');
    debugPrint('🔬 Total bytes: ${data.length}');
    debugPrint('🔬 Raw hex: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    List<ExerciseHistoryData> results = [];
    
    if (data.length < 3) return results;
    
    // Analizza payload
    List<int> payload = data.sublist(3);
    debugPrint('🔬 Payload (${payload.length} bytes): ${payload.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    // Test formato 12 bytes per entry (UTC + Steps + Calories, 4 bytes ciascuno)
    if (payload.length >= 12) {
      debugPrint('🔬 Testing 12-byte format (UTC+Steps+Calories, 4 bytes each):');
      
      for (int i = 0; i < payload.length; i += 12) {
        if (i + 12 <= payload.length) {
          int utc = payload[i] | (payload[i+1] << 8) | (payload[i+2] << 16) | (payload[i+3] << 24);
          int steps = payload[i+4] | (payload[i+5] << 8) | (payload[i+6] << 16) | (payload[i+7] << 24);
          int calories = payload[i+8] | (payload[i+9] << 8) | (payload[i+10] << 16) | (payload[i+11] << 24);
          
          // Test anche big-endian per UTC
          int utcBE = (payload[i] << 24) | (payload[i+1] << 16) | (payload[i+2] << 8) | payload[i+3];
          
          debugPrint('🔬 Entry ${(i~/12)+1}:');
          debugPrint('🔬   UTC LE: $utc (${_utcToDateString(utc)})');
          debugPrint('🔬   UTC BE: $utcBE (${_utcToDateString(utcBE)})');
          debugPrint('🔬   Steps: $steps');
          debugPrint('🔬   Calories raw: $calories (${calories/10.0}kcal)');
          
          // Scegli l'UTC che ha più senso (più vicino alla data corrente)
          int currentUtc = DateTime.now().millisecondsSinceEpoch ~/ 1000;
          int diffLE = (utc - currentUtc).abs();
          int diffBE = (utcBE - currentUtc).abs();
          bool useLE = diffLE < diffBE;
          int finalUtc = useLE ? utc : utcBE;
          
          debugPrint('🔬   ✅ Using ${useLE ? "Little-endian" : "Big-endian"}: ${_utcToDateString(finalUtc)} (UTC: $finalUtc)');
          
          // Aggiungi comunque per test, anche se la data è sbagliata
          if (finalUtc != 0xFFFFFFFF && finalUtc > 0) {
            try {
              DateTime date = DateTime.fromMillisecondsSinceEpoch(finalUtc * 1000);
              results.add(ExerciseHistoryData(
                date: date,
                steps: steps,
                calories: calories / 10.0,
              ));
            } catch (e) {
              debugPrint('🔬   ❌ Invalid date conversion for UTC $finalUtc');
            }
          }
        }
      }
    }
    
    // Test formato alternativo
    if (payload.length == 11) {
      debugPrint('🔬 Testing 11-byte payload - possible different format:');
      // Forse il formato è diverso - proviamo a interpretare come singola entry
      if (payload.length >= 8) {
        int utc = payload[0] | (payload[1] << 8) | (payload[2] << 16) | (payload[3] << 24);
        int steps = payload[4] | (payload[5] << 8);
        int calories = payload[6] | (payload[7] << 8);
        
        debugPrint('🔬 Single entry attempt:');
        debugPrint('🔬   UTC: $utc (${_utcToDateString(utc)})');
        debugPrint('🔬   Steps (2 bytes): $steps');
        debugPrint('🔬   Calories (2 bytes): $calories');
        
        if (utc != 0xFFFFFFFF && utc > 0) {
          try {
            DateTime date = DateTime.fromMillisecondsSinceEpoch(utc * 1000);
            results.add(ExerciseHistoryData(
              date: date,
              steps: steps,
              calories: calories.toDouble(),
            ));
          } catch (e) {
            debugPrint('🔬   ❌ Invalid date conversion for UTC $utc');
          }
        }
      }
    }
    
    return results;
  }
  
  static String _utcToDateString(int utc) {
    try {
      DateTime date = DateTime.fromMillisecondsSinceEpoch(utc * 1000);
      return '${date.year}-${date.month.toString().padLeft(2, '0')}-${date.day.toString().padLeft(2, '0')} ${date.hour.toString().padLeft(2, '0')}:${date.minute.toString().padLeft(2, '0')}';
    } catch (e) {
      return 'Invalid date';
    }
  }

  /// ENHANCED: Process Exercise History usando il parser reverse-engineered
  /// Basato sul codice Java originale del WearReceivedDataCallback
  static List<ExerciseHistoryData> processExerciseHistoryEnhanced(Uint8List data) {
    debugPrint('🧬 ENHANCED Exercise History Processing (reverse-engineered)');
    
    // Usa il parser enhanced basato sull'app originale
    List<enhanced.ExerciseHistoryEntry> enhancedEntries = enhanced.EnhancedHistoricalDataProcessor.parseSportHistory(data);
    
    // Converti al formato esistente
    List<ExerciseHistoryData> history = [];
    for (var entry in enhancedEntries) {
      history.add(ExerciseHistoryData(
        date: entry.timestamp,
        steps: entry.steps,
        calories: entry.calories.toDouble(),
        distanceCm: 0, // Non disponibile nel formato originale
      ));
    }
    
    debugPrint('🧬 Enhanced parser found ${history.length} exercise entries');
    return history;
  }

  /// ENHANCED: Process Sleep History usando il parser reverse-engineered
  static void processSleepHistoryEnhanced(Uint8List data) {
    debugPrint('🧬 ENHANCED Sleep History Processing (reverse-engineered)');
    
    List<enhanced.SleepHistoryEntry> enhancedEntries = enhanced.EnhancedHistoricalDataProcessor.parseSleepHistory(data);
    
    debugPrint('🧬 Enhanced parser found ${enhancedEntries.length} sleep entries');
    for (var entry in enhancedEntries) {
      debugPrint('😴 Sleep: ${entry.timestamp} - ${entry.actions.length} actions');
    }
  }

  /// ENHANCED: Process Interval Steps usando il parser reverse-engineered
  static void processIntervalStepsEnhanced(Uint8List data) {
    debugPrint('🧬 ENHANCED Interval Steps Processing (reverse-engineered)');
    
    List<enhanced.IntervalStepEntry> enhancedEntries = enhanced.EnhancedHistoricalDataProcessor.parseIntervalSteps(data);
    
    debugPrint('🧬 Enhanced parser found ${enhancedEntries.length} interval step entries');
    for (var entry in enhancedEntries) {
      debugPrint('🚶 Steps: ${entry.timestamp} - ${entry.steps} steps');
    }
  }
}
