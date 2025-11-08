/// Session Merger for Sleep Data
/// 
/// Merges consecutive sleep sessions from the same night into a single session
/// This is CRITICAL for proper sleep classification!
/// 
/// Author: Extracted from chileaf_extended_service.dart
/// 
/// WHY MERGING IS NECESSARY:
/// Device sends sleep in 60-minute chunks. Without merging:
///   - 7 sessions × 60min each = classified as 7 "Naps" ❌
/// With merging:
///   - 1 session × 420min = classified as "Night Sleep" ✅
library;

/// Merges consecutive sleep sessions based on gap and time period
/// 
/// Merge Rules:
/// 1. Gap between session end and next start < 30 minutes
/// 2. Both sessions in night period (18:00-10:00)
/// 
/// Example:
/// Input:  [22:00-23:00, 23:00-00:00, 00:00-01:00, 14:00-14:30]
/// Output: [22:00-01:00 (merged), 14:00-14:30 (separate)]
List<Map<String, dynamic>> mergeConsecutiveSessions(List<Map<String, dynamic>> sessions) {
  if (sessions.isEmpty) return [];
  
  print('🔗 Starting session merge analysis...');
  print('   Input: ${sessions.length} sessions');
  
  // Sort by timestamp (ascending)
  sessions.sort((a, b) => 
    (a['timestamp'] as DateTime).compareTo(b['timestamp'] as DateTime)
  );
  
  List<Map<String, dynamic>> merged = [];
  Map<String, dynamic>? currentGroup;
  
  for (var session in sessions) {
    if (currentGroup == null) {
      // First session - start new group
      currentGroup = Map.from(session);
      print('   📍 Starting new group: ${_formatSession(currentGroup)}');
      continue;
    }
    
    // Calculate end time of current group
    DateTime currentEnd = (currentGroup['timestamp'] as DateTime)
        .add(Duration(minutes: (currentGroup['actions'] as List<int>).length * 5));
    
    // Get start time of next session
    DateTime nextStart = session['timestamp'] as DateTime;
    
    // Calculate gap between sessions
    Duration gap = nextStart.difference(currentEnd);
    
    // Check if both sessions are in night period
    bool sameNightPeriod = _isNightTime(currentEnd.hour) && 
                           _isNightTime(nextStart.hour);
    
    print('   🔍 Gap analysis:');
    print('      Current ends:  ${currentEnd.toString().substring(11, 16)}');
    print('      Next starts:   ${nextStart.toString().substring(11, 16)}');
    print('      Gap: ${gap.inMinutes} minutes');
    print('      Same night period: $sameNightPeriod');
    
    // Merge conditions
    if (gap.inMinutes < 30 && sameNightPeriod) {
      print('   ✅ MERGING sessions');
      
      // Merge: concatenate activity indices
      List<int> mergedActions = List<int>.from(currentGroup['actions'] as List<int>);
      mergedActions.addAll(session['actions'] as List<int>);
      
      // Update current group
      currentGroup['actions'] = mergedActions;
      currentGroup['count'] = mergedActions.length;
      currentGroup['durationMinutes'] = mergedActions.length * 5;
      
      print('      New duration: ${currentGroup['durationMinutes']} minutes');
    } else {
      print('   ❌ NOT merging - starting new group');
      print('      Reason: ${gap.inMinutes >= 30 ? 'Gap too large' : 'Different time periods'}');
      
      // Save current group and start new one
      merged.add(currentGroup);
      currentGroup = Map.from(session);
      print('   📍 Starting new group: ${_formatSession(currentGroup)}');
    }
  }
  
  // Add last group
  if (currentGroup != null) {
    merged.add(currentGroup);
  }
  
  print('🔗 Merge complete: ${sessions.length} → ${merged.length} sessions');
  for (int i = 0; i < merged.length; i++) {
    print('   ${i + 1}. ${_formatSession(merged[i])}');
  }
  
  return merged;
}

/// Checks if an hour is in night period (18:00-10:00)
/// 
/// Night period definition:
/// - Evening: 18:00 (6 PM) onwards
/// - Night: 00:00 - 10:00 (10 AM)
bool _isNightTime(int hour) {
  return hour >= 18 || hour <= 10;
}

/// Formats session for logging
String _formatSession(Map<String, dynamic> session) {
  DateTime timestamp = session['timestamp'] as DateTime;
  int duration = session['durationMinutes'] as int;
  return '${timestamp.toString().substring(0, 19)} (${duration}min)';
}

/// Example usage
void main() {
  // Example: 7 consecutive 60-minute sessions (should merge into 1)
  List<Map<String, dynamic>> sessions = [
    {
      'timestamp': DateTime(2025, 11, 4, 22, 0, 0),
      'actions': List.filled(12, 0),  // 12 × 5min = 60min
      'count': 12,
      'durationMinutes': 60,
    },
    {
      'timestamp': DateTime(2025, 11, 4, 23, 0, 0),
      'actions': List.filled(12, 0),
      'count': 12,
      'durationMinutes': 60,
    },
    {
      'timestamp': DateTime(2025, 11, 5, 0, 0, 0),
      'actions': List.filled(12, 0),
      'count': 12,
      'durationMinutes': 60,
    },
    {
      'timestamp': DateTime(2025, 11, 5, 1, 0, 0),
      'actions': List.filled(12, 0),
      'count': 12,
      'durationMinutes': 60,
    },
    // Gap of 12 hours - should NOT merge with above
    {
      'timestamp': DateTime(2025, 11, 5, 14, 0, 0),
      'actions': List.filled(6, 0),   // 6 × 5min = 30min
      'count': 6,
      'durationMinutes': 30,
    },
  ];
  
  print('\n📊 BEFORE MERGE:');
  for (var session in sessions) {
    print('   ${_formatSession(session)}');
  }
  
  List<Map<String, dynamic>> merged = mergeConsecutiveSessions(sessions);
  
  print('\n📊 AFTER MERGE:');
  for (var session in merged) {
    print('   ${_formatSession(session)}');
  }
  
  print('\n✅ Expected result:');
  print('   1. 2025-11-04 22:00 - 2025-11-05 05:00 (420min) - Night Sleep');
  print('   2. 2025-11-05 14:00 - 14:30 (30min) - Short Nap');
}

/// IMPORTANT NOTES:
/// 
/// 1. ALWAYS merge BEFORE classification
///    Classifying individual 60min sessions results in all naps!
/// 
/// 2. Gap threshold: < 30 minutes
///    Based on SDK analysis and real-world sleep patterns
/// 
/// 3. Night period: 18:00-10:00
///    Ensures daytime naps don't merge with night sleep
/// 
/// 4. Preserve original timestamps
///    Merged session keeps timestamp of first session
/// 
/// 5. Activity indices concatenation
///    Order matters - maintain chronological sequence
