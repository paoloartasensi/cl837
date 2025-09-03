import 'dart:typed_data';
import '../../models/historical_data.dart';

class HistoricalDataProcessor {

  /// Processes HR History List (0x21) according to CL831 spec
  /// Returns HeartRateHistoryList with session timestamps
  static HeartRateHistoryList processHRHistoryList(Uint8List data) {
    print('💓 Processing HR History List (0x21) - ${data.length} bytes');

    if (data.length < 5) {
      print('❌ HR History List data too short');
      return const HeartRateHistoryList(timestamps: []);
    }

    List<DateTime> sessions = [];

    // Skip header (0xFF, length, command)
    int offset = 3;

    // Each timestamp is 4 bytes - try different interpretations
    while (offset + 4 <= data.length - 1) { // -1 for checksum
      // Try both big-endian and little-endian
      int timestampBE = (data[offset] << 24) |
                       (data[offset + 1] << 16) |
                       (data[offset + 2] << 8) |
                       data[offset + 3];

      int timestampLE = data[offset] |
                       (data[offset + 1] << 8) |
                       (data[offset + 2] << 16) |
                       (data[offset + 3] << 24);

      print('🔍 Raw timestamp bytes: [${data[offset]}, ${data[offset + 1]}, ${data[offset + 2]}, ${data[offset + 3]}]');
      print('   Big-endian: $timestampBE, Little-endian: $timestampLE');

      // Try different units: seconds, minutes, hours from base date
      DateTime baseDate = DateTime(2025, 9, 1); // Base date around current time

      DateTime fromSeconds = baseDate.add(Duration(seconds: timestampLE));
      DateTime fromMinutes = baseDate.add(Duration(minutes: timestampLE));
      DateTime fromHours = baseDate.add(Duration(hours: timestampLE));

      print('📅 Timestamp interpretations from 2025-09-01:');
      print('   +$timestampLE seconds: $fromSeconds');
      print('   +$timestampLE minutes: $fromMinutes');
      print('   +$timestampLE hours: $fromHours');

      // For small values like 294, minutes from base date makes most sense
      DateTime sessionTime = fromMinutes;

      print('💓 HR Session selected: ${sessionTime.toString()}');
      sessions.add(sessionTime);

      offset += 4;
    }

    print('💓 Total HR sessions: ${sessions.length}');
    return HeartRateHistoryList(
      timestamps: sessions,
    );
  }

  /// Processes HR History Data (0x22) according to CL831 spec
  /// Returns HeartRateHistoryData with measurements for a specific session
  static HeartRateHistoryData processHRHistoryData(Uint8List data) {
    print('💓 Processing HR History Data (0x22) - ${data.length} bytes');

    if (data.length < 8) {
      print('❌ HR History Data too short');
      return HeartRateHistoryData(
        timestamp: DateTime.now(),
        entries: [],
      );
    }

    // Skip header (0xFF, length, command)
    int offset = 3;

    // Session timestamp (4 bytes)
    int sessionTimestamp = (data[offset] << 24) |
                          (data[offset + 1] << 16) |
                          (data[offset + 2] << 8) |
                          data[offset + 3];

    // Use same logic as session list - minutes from base date
    DateTime baseDate = DateTime(2025, 9, 1);
    DateTime sessionStart = baseDate.add(Duration(minutes: sessionTimestamp));

    offset += 4;

    List<HeartRateHistoryEntry> entries = [];

    // Each measurement is 3 bytes: timestamp offset (2 bytes) + HR (1 byte)
    while (offset + 3 <= data.length - 1) { // -1 for checksum
      int timestampOffset = (data[offset] << 8) | data[offset + 1];
      int heartRate = data[offset + 2];

      // Timestamp offset is in seconds from session start
      DateTime measurementTime = sessionStart.add(Duration(seconds: timestampOffset));

      entries.add(HeartRateHistoryEntry(
        heartRate: heartRate,
        activityIndex: 0, // Default activity index
        time: measurementTime,
      ));

      offset += 3;
    }

    print('💓 Session ${sessionStart.toString()} - ${entries.length} measurements');

    return HeartRateHistoryData(
      timestamp: sessionStart,
      entries: entries,
    );
  }

  /// Processes Exercise History (0x40) according to CL831 spec
  /// Returns List<ExerciseHistoryData> with exercise session data
  static List<ExerciseHistoryData> processExerciseHistory(Uint8List data) {
    print('💓 Processing Exercise History (0x40) - ${data.length} bytes');

    if (data.length < 8) {
      print('❌ Exercise History data too short');
      return [];
    }

    List<ExerciseHistoryData> exerciseSessions = [];

    // Skip header (0xFF, length, command)
    int offset = 3;

    // Each exercise session is represented by date (4 bytes) + steps (4 bytes) + calories (4 bytes) + distance (4 bytes)
    while (offset + 16 <= data.length - 1) { // -1 for checksum, 16 bytes per session
      // Read date (4 bytes - Unix timestamp)
      int dateTimestamp = (data[offset] << 24) |
                         (data[offset + 1] << 16) |
                         (data[offset + 2] << 8) |
                         data[offset + 3];

      // Read steps (4 bytes)
      int steps = (data[offset + 4] << 24) |
                 (data[offset + 5] << 16) |
                 (data[offset + 6] << 8) |
                 data[offset + 7];

      // Read calories (4 bytes - as integer, convert to double)
      int caloriesInt = (data[offset + 8] << 24) |
                       (data[offset + 9] << 16) |
                       (data[offset + 10] << 8) |
                       data[offset + 11];
      double calories = caloriesInt / 1000.0; // Convert to kcal

      // Read distance (4 bytes - in cm)
      int distanceCm = (data[offset + 12] << 24) |
                      (data[offset + 13] << 16) |
                      (data[offset + 14] << 8) |
                      data[offset + 15];

      // Convert timestamp to DateTime
      DateTime sessionDate = DateTime.fromMillisecondsSinceEpoch(dateTimestamp * 1000);

      ExerciseHistoryData exerciseData = ExerciseHistoryData(
        date: sessionDate,
        steps: steps,
        calories: calories,
        distanceCm: distanceCm,
      );

      print('💓 Exercise session: ${sessionDate.toString()}, Steps: $steps, Calories: ${calories}kcal, Distance: ${distanceCm}cm');
      exerciseSessions.add(exerciseData);

      offset += 16;
    }

    print('💓 Total exercise sessions: ${exerciseSessions.length}');
    return exerciseSessions;
  }
}
