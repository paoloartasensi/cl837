import 'dart:typed_data';

// Utility to parse little-endian data from a byte list.
int _getLongParse(List<int> data, int offset, int length) {
  int result = 0;
  for (int i = 0; i < length; i++) {
    result |= (data[offset + i] & 0xFF) << (i * 8);
  }
  return result;
}

// Converts a UTC timestamp (in seconds) to a DateTime object.
DateTime _restoreZoneUTC(int timestamp) {
  return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
}

class ExerciseHistoryEntry {
  final DateTime timestamp;
  final int steps;
  final int calories;

  ExerciseHistoryEntry({required this.timestamp, required this.steps, required this.calories});

  @override
  String toString() {
    return 'ExerciseHistoryEntry(timestamp: $timestamp, steps: $steps, calories: $calories)';
  }
}

class SleepHistoryEntry {
  final DateTime timestamp;
  final List<int> actions;

  SleepHistoryEntry({required this.timestamp, required this.actions});

   @override
  String toString() {
    return 'SleepHistoryEntry(timestamp: $timestamp, actions: $actions)';
  }
}

class IntervalStepEntry {
  final DateTime timestamp;
  final int steps;

  IntervalStepEntry({required this.timestamp, required this.steps});

   @override
  String toString() {
    return 'IntervalStepEntry(timestamp: $timestamp, steps: $steps)';
  }
}

class HeartRateHistoryEntry {
    final DateTime timestamp;

    HeartRateHistoryEntry({required this.timestamp});

     @override
    String toString() {
        return 'HeartRateHistoryEntry(timestamp: $timestamp)';
    }
}


class EnhancedHistoricalDataProcessor {
  static List<ExerciseHistoryEntry> parseSportHistory(Uint8List value) {
    final entries = <ExerciseHistoryEntry>[];
    for (int i = 0; i < value.lengthInBytes / 10; i++) {
      int offset = i * 10;
      if (offset + 10 > value.lengthInBytes) break;
      
      int stamp = _getLongParse(value, offset, 4);
      int step = _getLongParse(value, offset + 4, 3);
      int calorie = _getLongParse(value, offset + 7, 3);
      
      entries.add(ExerciseHistoryEntry(
        timestamp: _restoreZoneUTC(stamp),
        steps: step,
        calories: calorie,
      ));
    }
    return entries;
  }

  static List<SleepHistoryEntry> parseSleepHistory(Uint8List value) {
    final entries = <SleepHistoryEntry>[];
    if (value.lengthInBytes < 4) return entries;

    if (_getLongParse(value, 3, 1) == 3) { // Sleep indicator
        int i = 4; // Start after the indicator
        while (i < value.lengthInBytes) {
            if (i + 5 > value.lengthInBytes) break; 
            
            int actionCount = value[i];
            i++;

            if (i + 4 > value.lengthInBytes) break;
            int longParse = _getLongParse(value, i, 4);
            i += 4;
            
            DateTime timestamp = DateTime.fromMillisecondsSinceEpoch((longParse * 1000) - 28800000, isUtc: true);

            if (i + actionCount > value.lengthInBytes) break;
            List<int> actions = value.sublist(i, i + actionCount);
            i += actionCount;

            entries.add(SleepHistoryEntry(timestamp: timestamp, actions: actions));
        }
    }
    return entries;
  }

  static List<IntervalStepEntry> parseIntervalSteps(Uint8List value) {
    final entries = <IntervalStepEntry>[];
    for (int i = 0; i < value.lengthInBytes / 8; i++) {
      int offset = i * 8;
      if (offset + 8 > value.lengthInBytes) break;

      int stamp = _getLongParse(value, offset, 4);
      int steps = _getLongParse(value, offset + 4, 4);

      entries.add(IntervalStepEntry(
        timestamp: _restoreZoneUTC(stamp),
        steps: steps,
      ));
    }
    return entries;
  }

  static List<HeartRateHistoryEntry> parseHeartRateHistory(Uint8List value) {
    final entries = <HeartRateHistoryEntry>[];
    for (int i = 0; i < value.lengthInBytes / 4; i++) {
      int offset = i * 4;
      if (offset + 4 > value.lengthInBytes) break;

      int stamp = _getLongParse(value, offset, 4);
      if (stamp == 0xFFFFFFFF) continue; // Skip invalid markers

      DateTime timestamp = _restoreZoneUTC(stamp);
      
      if (timestamp.year >= 2020 && timestamp.year <= 2050) {
        entries.add(HeartRateHistoryEntry(timestamp: timestamp));
      }
    }
    return entries;
  }
}
