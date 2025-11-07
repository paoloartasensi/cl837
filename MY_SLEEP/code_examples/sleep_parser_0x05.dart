/// Sleep Data Parser for 0x05 Format (SDK Compatible)
/// 
/// This is the RECOMMENDED parser for CL837/CL831 devices
/// Compatible with official Android (JAVA) and iOS (Objective-C) SDKs
/// 
/// Author: Extracted from chileaf_extended_service.dart
/// Date: November 5, 2025
/// 
/// CRITICAL FEATURES:
/// 1. UTC → Local timestamp conversion
/// 2. Multi-session parsing in single packet
/// 3. Big-endian timestamp reading
/// 4. SDK-compatible format

/// Parses sleep data in 0x05 format (legacy SDK compatible)
/// 
/// Packet Format:
/// [0xFF][length][0x05][0x03][session1_data][session2_data]...[checksum]
/// 
/// Session Data Format:
/// [count][utc_4bytes_big_endian][action_indices...]
/// 
/// Returns: List of sleep sessions with parsed data
List<Map<String, dynamic>> parseSleepData05(List<int> data) {
  List<Map<String, dynamic>> sessions = [];
  
  // Validate packet
  if (data.length < 10) {
    print('❌ Packet too short: ${data.length} bytes');
    return sessions;
  }
  
  // Verify header: [0xFF][len][0x05][0x03]
  if (data[0] != 0xFF || data[2] != 0x05 || data[3] != 0x03) {
    print('❌ Invalid header: ${data.take(4).map((e) => '0x${e.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    return sessions;
  }
  
  int length = data[1] - 5; // Data length (excluding header + checksum)
  int start = 4;  // Start after header
  int sessionCount = 0;
  
  print('📦 Parsing 0x05 packet: $length bytes of data');
  
  // Parse all sessions in packet
  while (start < length && start < data.length - 1) {
    sessionCount++;
    
    // Read count (number of 5-minute blocks)
    int count = data[start];
    
    if (start + 4 >= data.length) {
      print('⚠️ Incomplete session $sessionCount at offset $start');
      break;
    }
    
    // Read UTC timestamp (4 bytes, big-endian)
    // Device sends timestamp in SECONDS (not milliseconds!)
    int utcTime = (data[start + 1] << 24) +  // Most significant byte
                  (data[start + 2] << 16) +
                  (data[start + 3] << 8) +
                  data[start + 4];              // Least significant byte
    
    // ⚠️ CRITICAL: Convert UTC → Local Time
    // iOS SDK: [NSDate dateWithTimeIntervalSince1970:stamp - timeZoneSecond]
    // Android SDK: new Date(utcTime * 1000L) with local formatter
    DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
      utcTime * 1000,  // Convert seconds → milliseconds
      isUtc: true      // ✅ Interpret as UTC!
    ).toLocal();       // ✅ Convert to local timezone
    
    // Read activity indices (each = 5 minutes)
    List<int> actions = [];
    int actionStart = start + 5;
    
    // Validate action indices length
    int availableBytes = data.length - 1 - actionStart; // -1 for checksum
    if (count > availableBytes) {
      print('⚠️ Truncating session $sessionCount: need $count, have $availableBytes');
      count = availableBytes;
    }
    
    for (int i = 0; i < count; i++) {
      actions.add(data[actionStart + i] & 0xFF);
    }
    
    // Create session object
    Map<String, dynamic> session = {
      'utcTime': utcTime,           // Original UTC timestamp (seconds)
      'timestamp': timestamp,        // Converted local DateTime
      'count': count,                // Number of 5-min blocks
      'actions': actions,            // Activity indices
      'durationMinutes': count * 5,  // Total duration in minutes
    };
    
    sessions.add(session);
    
    print('  ✅ Session $sessionCount: ${count * 5}min @ ${timestamp.toString().substring(0, 19)}');
    print('     Actions: ${actions.take(10).join(', ')}${actions.length > 10 ? '...' : ''}');
    
    // Move to next session
    start = start + 5 + count;
  }
  
  print('📊 Parsed $sessionCount sessions from 0x05 packet');
  
  return sessions;
}

/// Detects end signal for 0x05 sleep data
/// 
/// End Signal Format: [0xFF][0x04][0x05][0xFF][checksum]
/// 
/// Returns: true if this is an end signal packet
bool isSleepData05EndSignal(List<int> data) {
  if (data.length < 5) return false;
  
  return data[0] == 0xFF &&
         data[1] == 0x04 &&
         data[2] == 0x05 &&
         data[3] == 0xFF;
}

/// Example usage
void main() {
  // Example packet with 2 sessions (simplified)
  List<int> examplePacket = [
    0xFF, 0x8D, 0x05, 0x03,  // Header: [0xFF][length=141][cmd=0x05][subcmd=0x03]
    
    // Session 1: 12 blocks (60 min), timestamp: 1730731841
    0x0C,                     // count = 12
    0x67, 0x2A, 0x9E, 0x41,  // UTC timestamp (big-endian)
    0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,  // 12 activity indices
    
    // Session 2: 4 blocks (20 min), timestamp: 1730747073
    0x04,                     // count = 4
    0x67, 0x2E, 0x66, 0x41,  // UTC timestamp (big-endian)
    0, 0, 0, 0,              // 4 activity indices
    
    0x00,                     // checksum (would be calculated in real packet)
  ];
  
  List<Map<String, dynamic>> sessions = parseSleepData05(examplePacket);
  
  for (var session in sessions) {
    print('\n📅 Session:');
    print('   Timestamp: ${session['timestamp']}');
    print('   Duration: ${session['durationMinutes']} minutes');
    print('   Actions: ${session['actions']}');
  }
}

/// NOTES:
/// 
/// 1. ALWAYS convert UTC → Local using isUtc: true
///    Without this, timestamps will be wrong by timezone offset!
/// 
/// 2. Device sends multiple sessions in ONE packet
///    Parse all sessions before processing
/// 
/// 3. Each activity index = 5 minutes
///    Total duration = count × 5 minutes
/// 
/// 4. Timestamp is in SECONDS, not milliseconds
///    Must multiply by 1000 for DateTime
/// 
/// 5. Big-endian byte order for timestamp
///    Most significant byte first
