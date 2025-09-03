/// Modello per i dati storici dell'esercizio (comando 0x16)
class ExerciseHistoryData {
  final DateTime date;
  final int steps;
  final double calories; // in kcal
  final int distanceCm;
  
  const ExerciseHistoryData({
    required this.date,
    required this.steps,
    required this.calories,
    this.distanceCm = 0,
  });
  
  @override
  String toString() {
    return 'ExerciseHistoryData{date: $date, steps: $steps, calories: ${calories}kcal, distance: ${distanceCm}cm}';
  }
}

/// Modello per la lista degli storici della frequenza cardiaca (comando 0x21)
class HeartRateHistoryList {
  final List<DateTime> timestamps;
  final List<int> rawTimestamps; // Raw timestamp values from device for direct use
  final bool isEndOfData;
  
  const HeartRateHistoryList({
    required this.timestamps,
    required this.rawTimestamps,
    this.isEndOfData = false,
  });
  
  @override
  String toString() {
    return 'HeartRateHistoryList{entries: ${timestamps.length}, rawTimestamps: ${rawTimestamps.length}, isEndOfData: $isEndOfData}';
  }
}

/// Modello per i dati storici della frequenza cardiaca (comando 0x22)
class HeartRateHistoryData {
  final DateTime timestamp;
  final List<HeartRateHistoryEntry> entries;
  
  const HeartRateHistoryData({
    required this.timestamp,
    required this.entries,
  });
  
  @override
  String toString() {
    return 'HeartRateHistoryData{timestamp: $timestamp, entries: ${entries.length}}';
  }
}

/// Singola voce dei dati storici HR
class HeartRateHistoryEntry {
  final int heartRate;
  final int activityIndex;
  final DateTime time;
  
  const HeartRateHistoryEntry({
    required this.heartRate,
    required this.activityIndex,
    required this.time,
  });
  
  @override
  String toString() {
    return 'HR: ${heartRate}bpm, Activity: $activityIndex, Time: $time';
  }
}

/// Modello per i dati del sonno (sleep data) - Basato su SDK docs
class SleepHistoryEntry {
  final DateTime timestamp;
  final int count;
  final List<int> actions; // Action index list: >20 no sleep, <20 light sleep, 3 consecutive 0s are deep sleep
  
  const SleepHistoryEntry({
    required this.timestamp,
    required this.count,
    required this.actions,
  });
  
  /// Calcola le fasi del sonno basandosi sui dati degli indici di azione
  SleepPhases calculateSleepPhases() {
    int lightSleep = 0;
    int deepSleep = 0;
    int awake = 0;
    
    int consecutiveZeros = 0;
    bool inDeepSleep = false;
    
    for (int i = 0; i < actions.length; i++) {
      int action = actions[i];
      
      if (action == 0) {
        consecutiveZeros++;
        if (consecutiveZeros >= 3 && !inDeepSleep) {
          inDeepSleep = true;
          // Convert previous light sleep to deep sleep
          lightSleep = lightSleep > 3 ? lightSleep - 3 : 0;
          deepSleep += 3;
        } else if (inDeepSleep) {
          deepSleep++;
        } else {
          lightSleep++;
        }
      } else {
        consecutiveZeros = 0;
        inDeepSleep = false;
        
        if (action > 20) {
          awake++;
        } else if (action <= 20) {
          lightSleep++;
        }
      }
    }
    
    return SleepPhases(
      lightSleep: lightSleep,
      deepSleep: deepSleep,
      awake: awake,
      totalMinutes: actions.length,
    );
  }
  
  @override
  String toString() {
    return 'SleepHistoryEntry{timestamp: $timestamp, count: $count, actions: ${actions.length} entries}';
  }
}

/// Fasi del sonno calcolate dai dati grezzi
class SleepPhases {
  final int lightSleep;    // minuti di sonno leggero
  final int deepSleep;     // minuti di sonno profondo
  final int awake;         // minuti di veglia
  final int totalMinutes;  // durata totale in minuti
  
  const SleepPhases({
    required this.lightSleep,
    required this.deepSleep,
    required this.awake,
    required this.totalMinutes,
  });
  
  /// Durata totale del sonno (leggero + profondo)
  int get totalSleep => lightSleep + deepSleep;
  
  /// Efficienza del sonno (percentuale di tempo dormito)
  double get sleepEfficiency => totalMinutes > 0 ? (totalSleep / totalMinutes) * 100 : 0;
  
  @override
  String toString() {
    return 'SleepPhases{light: ${lightSleep}min, deep: ${deepSleep}min, awake: ${awake}min, efficiency: ${sleepEfficiency.toStringAsFixed(1)}%}';
  }
}

/// Modello per i dati degli intervalli di passi - Basato su comandi 0x90/0x91
class StepIntervalEntry {
  final DateTime timestamp;
  final int steps;
  
  const StepIntervalEntry({
    required this.timestamp,
    required this.steps,
  });
  
  @override
  String toString() {
    return 'StepIntervalEntry{timestamp: $timestamp, steps: $steps}';
  }
}
