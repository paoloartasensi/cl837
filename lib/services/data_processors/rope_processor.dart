import 'package:flutter/foundation.dart';
import '../../models/rope_data.dart';

class RopeSkippingProcessor {
  /// Processes rope status data (command 0x40)
  /// Returns status with mode, stats, and day totals
  static RopeSkippingData? processRopeStatus(List<int> data) {
    try {
      if (data.length < 8) {
        debugPrint('⚠️ Rope status data too short: ${data.length} bytes');
        return null;
      }

      // Parse according to protocol:
      // Mode (1 byte) + Time (2 bytes) + Jumps (2 bytes) + Calories (2 bytes) + Day total (2 bytes)
      int modeValue = data[2]; // Skip header + length
      RopeMode mode = RopeMode.fromValue(modeValue);
      
      int timeSeconds = (data[4] << 8) | data[3]; // Little endian
      int jumps = (data[6] << 8) | data[5];
      int caloriesRaw = (data[8] << 8) | data[7];
      double calories = caloriesRaw / 10.0; // Protocol specifies 0.1 kcal units
      
      int dayTotalJumps = 0;
      if (data.length >= 10) {
        dayTotalJumps = (data[10] << 8) | data[9];
      }

      final ropeData = RopeSkippingData(
        mode: mode,
        jumps: jumps,
        timeSeconds: timeSeconds,
        calories: calories,
        dayTotalJumps: dayTotalJumps,
      );

      debugPrint('🪢 Rope Status: $ropeData');
      return ropeData;

    } catch (e) {
      debugPrint('❌ Error processing rope status data: $e');
      return null;
    }
  }

  /// Processes realtime rope notifications (command 0x41)
  /// Returns realtime data with current session info
  static RopeRealtimeData? processRopeRealtime(List<int> data) {
    try {
      if (data.length < 8) {
        debugPrint('⚠️ Rope realtime data too short: ${data.length} bytes');
        return null;
      }

      // Parse according to protocol:
      // Mode + Jumps + Time + Calories + Countdown + Restart flag
      int modeValue = data[2];
      RopeMode mode = RopeMode.fromValue(modeValue);
      
      int jumps = (data[4] << 8) | data[3];
      int timeSeconds = (data[6] << 8) | data[5];
      int caloriesRaw = (data[8] << 8) | data[7];
      double calories = caloriesRaw / 10.0;
      
      int? countdown;
      bool restartFlag = false;
      
      if (data.length >= 10) {
        countdown = (data[10] << 8) | data[9];
      }
      
      if (data.length >= 11) {
        restartFlag = data[11] == 1;
      }

      final realtimeData = RopeRealtimeData(
        mode: mode,
        jumps: jumps,
        timeSeconds: timeSeconds,
        calories: calories,
        countdown: countdown,
        restartFlag: restartFlag,
      );

      debugPrint('🪢⚡ Rope Realtime: $realtimeData');
      return realtimeData;

    } catch (e) {
      debugPrint('❌ Error processing rope realtime data: $e');
      return null;
    }
  }

  /// Creates a command to set rope mode (command 0x42)
  static List<int> createSetModeCommand(RopeMode mode) {
    // Protocol: [0xFF, Length, 0x42, Mode, Checksum]
    return [0x42, mode.value];
  }

  /// Creates a command to clear rope data (command 0x45)
  static List<int> createClearDataCommand() {
    // Protocol: [0xFF, Length, 0x45, Checksum]
    return [0x45];
  }

  /// Determines if a command is rope-related
  static bool canHandle(int command) {
    return command == 0x40 || command == 0x41;
  }

  /// Helper to format time as HH:MM:SS
  static String formatTime(int seconds) {
    int hours = seconds ~/ 3600;
    int minutes = (seconds % 3600) ~/ 60;
    int secs = seconds % 60;
    
    if (hours > 0) {
      return '${hours.toString().padLeft(2, '0')}:${minutes.toString().padLeft(2, '0')}:${secs.toString().padLeft(2, '0')}';
    } else {
      return '${minutes.toString().padLeft(2, '0')}:${secs.toString().padLeft(2, '0')}';
    }
  }
}
