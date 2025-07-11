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
  
  const HeartRateHistoryList({
    required this.timestamps,
  });
  
  @override
  String toString() {
    return 'HeartRateHistoryList{entries: ${timestamps.length}}';
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
