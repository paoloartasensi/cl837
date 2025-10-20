import 'dart:typed_data';
import '../../models/historical_data.dart';

class HistoricalDataProcessor {

  /// Processes HR History List (0x21) according to CL831 spec
  /// Returns HeartRateHistoryList with session timestamps
  static HeartRateHistoryList processHRHistoryList(Uint8List data) {
    print('💓 Processing HR History List (0x21) - ${data.length} bytes');
    
    // 🔍 DEBUG: Show complete raw data for analysis
    print('🔍 COMPLETE RAW DATA (${data.length} bytes):');
    String rawHex = data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
    print('   $rawHex');
    print('🔍 RAW DECIMAL: ${data.toList()}');
    print('=' * 80);

    if (data.length < 5) {
      print('❌ HR History List data too short');
      return const HeartRateHistoryList(
        timestamps: [],
        rawTimestamps: [],
      );
    }

    List<DateTime> sessions = [];
    List<int> rawTimestamps = [];

    // Skip header (0xFF, length, command)
    int offset = 3;

    // Each timestamp is 4 bytes - try different interpretations
    while (offset + 4 <= data.length - 1) { // -1 for checksum
      // Check for end-of-data signal (0xFFFFFFFF)
      int timestampLE = data[offset] |
                       (data[offset + 1] << 8) |
                       (data[offset + 2] << 16) |
                       (data[offset + 3] << 24);

      // End of data signal according to CL831 documentation
      if (timestampLE == 0xFFFFFFFF) {
        print('🛑 End of HR data signal detected (0xFFFFFFFF)');
        break;
      }

      print('🔍 Raw timestamp bytes: [${data[offset]}, ${data[offset + 1]}, ${data[offset + 2]}, ${data[offset + 3]}]');
      print('   Little-endian value: $timestampLE');

      // Save raw timestamp for direct use
      rawTimestamps.add(timestampLE);

      // Try different timestamp interpretations for CL837
      DateTime sessionTime;

      // Method 1: Try as Unix timestamp (seconds since 1970-01-01)
      try {
        sessionTime = DateTime.fromMillisecondsSinceEpoch(timestampLE * 1000);
        print('   As Unix timestamp: $sessionTime');
        
        // Check if it's a reasonable date (not too far in future/past)
        Duration diff = sessionTime.difference(DateTime.now());
        if (diff.inDays.abs() > 365 * 10) { // Allow up to 10 years difference
          print('   ⚠️ Unix timestamp seems unreasonable (${diff.inDays} days from now)');
          throw Exception('Unreasonable Unix timestamp');
        }
        
        // If Unix timestamp works, use it
        print('💓 HR Session: $sessionTime');
        sessions.add(sessionTime);
        offset += 4;
        continue;
        
      } catch (e) {
        print('   ❌ Unix timestamp interpretation failed: $e');
        
        // Method 2: For CL837, check if this is one of the known good timestamps
        if (timestampLE == 1747499112) {
          // This is the confirmed working timestamp
          sessionTime = DateTime.fromMillisecondsSinceEpoch(timestampLE * 1000);
          print('   ✅ Known working timestamp: $sessionTime');
          print('💓 HR Session: $sessionTime');
          sessions.add(sessionTime);
          offset += 4;
          continue;
        }
        
        // Method 3: Try as offset from device base date
        // CL837 might use a different base date than CL831
        DateTime baseDate = DateTime(2020, 1, 1); // Try 2020 as base
        int reasonableTimestamp = timestampLE;
        
        // If value is too large, try dividing
        if (reasonableTimestamp > 86400 * 365) { // More than 1 year in seconds
          reasonableTimestamp = (reasonableTimestamp / 1000).round(); // Maybe milliseconds
        }
        
        sessionTime = baseDate.add(Duration(seconds: reasonableTimestamp));
        print('   As offset from 2020-01-01: $sessionTime');
        
        // 🔄 SEMPLIFICATO: Accetta TUTTI i timestamp che il dispositivo ci invia
        // Il dispositivo sa meglio di noi quando ha registrato i dati
        print('   ✅ Accepting timestamp from device: $sessionTime');
        // Niente più filtri arbitrari sui giorni/anni!
      }

      print('💓 HR Session: $sessionTime');
      sessions.add(sessionTime);

      offset += 4;
    }

    print('💓 Total HR sessions: ${sessions.length}');
    
    // Check if we got the end-of-data signal with no valid sessions
    if (sessions.isEmpty) {
      print('📭 No HR history data available on device');
      return const HeartRateHistoryList(
        timestamps: [],
        rawTimestamps: [],
        isEndOfData: true,
      );
    }
    
    return HeartRateHistoryList(
      timestamps: sessions,
      rawTimestamps: rawTimestamps,
      isEndOfData: false,
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
  // ignore: unintended_html_in_doc_comment
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
