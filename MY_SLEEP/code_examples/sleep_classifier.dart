/// Sleep Type Classifier
/// 
/// Classifies sleep sessions into categories based on duration and time
/// MUST be used AFTER session merging!
/// 
/// Author: Extracted from lib/services/sleep_classifier.dart
/// Date: November 5, 2025
library;

/// Sleep classification types
enum SleepType {
  nightSleep,    // 🌙 3+ hours during night (18:00-10:00)
  longNap,       // 🛋️ 3+ hours outside night period
  shortNap,      // 😴 30-180 minutes
  briefRest,     // ⏱️ < 30 minutes
  insufficient,  // ⚠️ Too short for analysis
}

/// Sleep type with emoji for display
class SleepTypeInfo {
  final SleepType type;
  final String emoji;
  final String name;
  final String description;
  
  const SleepTypeInfo({
    required this.type,
    required this.emoji,
    required this.name,
    required this.description,
  });
}

/// Maps sleep types to display information
const Map<SleepType, SleepTypeInfo> sleepTypeInfo = {
  SleepType.nightSleep: SleepTypeInfo(
    type: SleepType.nightSleep,
    emoji: '🌙',
    name: 'Night Sleep',
    description: 'Main sleep period (3+ hours, 18:00-10:00)',
  ),
  SleepType.longNap: SleepTypeInfo(
    type: SleepType.longNap,
    emoji: '🛋️',
    name: 'Long Nap',
    description: 'Extended daytime rest (3+ hours)',
  ),
  SleepType.shortNap: SleepTypeInfo(
    type: SleepType.shortNap,
    emoji: '😴',
    name: 'Short Nap',
    description: 'Brief daytime rest (30-180 min)',
  ),
  SleepType.briefRest: SleepTypeInfo(
    type: SleepType.briefRest,
    emoji: '⏱️',
    name: 'Brief Rest',
    description: 'Very short rest period (< 30 min)',
  ),
  SleepType.insufficient: SleepTypeInfo(
    type: SleepType.insufficient,
    emoji: '⚠️',
    name: 'Insufficient',
    description: 'Too short for sleep analysis',
  ),
};

/// Classifies a sleep session based on duration and time
/// 
/// Classification Rules:
/// - Night Sleep: >= 180min (3h) AND hour in [18:00, 10:00]
/// - Long Nap: >= 180min AND hour outside night period
/// - Short Nap: >= 30min AND < 180min
/// - Brief Rest: >= 15min AND < 30min
/// - Insufficient: < 15min
SleepType classifySleepSession({
  required DateTime timestamp,
  required int durationMinutes,
}) {
  int hour = timestamp.hour;
  
  // Check for insufficient data
  if (durationMinutes < 15) {
    return SleepType.insufficient;
  }
  
  // Brief rest (15-29 minutes)
  if (durationMinutes < 30) {
    return SleepType.briefRest;
  }
  
  // Night time check (18:00-10:00)
  bool isNightTime = hour >= 18 || hour <= 10;
  
  // Main night sleep: 3+ hours during night
  if (durationMinutes >= 180 && isNightTime) {
    return SleepType.nightSleep;
  }
  
  // Long daytime nap: 3+ hours outside night
  if (durationMinutes >= 180) {
    return SleepType.longNap;
  }
  
  // Short nap: 30-180 minutes (any time)
  return SleepType.shortNap;
}

/// Filters sessions to get only main night sleeps
/// 
/// Used for sleep score calculations and main dashboard
List<Map<String, dynamic>> filterMainNightSleeps(List<Map<String, dynamic>> sessions) {
  return sessions.where((session) {
    SleepType type = classifySleepSession(
      timestamp: session['timestamp'] as DateTime,
      durationMinutes: session['durationMinutes'] as int,
    );
    return type == SleepType.nightSleep;
  }).toList();
}

/// Gets recent main night sleeps (last N days)
/// 
/// Parameters:
/// - sessions: All sleep sessions
/// - days: Number of days to look back (default: 7)
/// 
/// Returns: List of main night sleep sessions, sorted newest first
List<Map<String, dynamic>> getRecentMainSleeps(
  List<Map<String, dynamic>> sessions,
  {int days = 7}
) {
  DateTime cutoff = DateTime.now().subtract(Duration(days: days));
  
  return sessions
      .where((session) {
        DateTime timestamp = session['timestamp'] as DateTime;
        int duration = session['durationMinutes'] as int;
        
        // Filter by date and type
        return timestamp.isAfter(cutoff) &&
               classifySleepSession(
                 timestamp: timestamp,
                 durationMinutes: duration,
               ) == SleepType.nightSleep;
      })
      .toList()
      ..sort((a, b) => (b['timestamp'] as DateTime)
          .compareTo(a['timestamp'] as DateTime)); // Newest first
}

/// Example usage
void main() {
  // Example sessions (after merging!)
  List<Map<String, dynamic>> sessions = [
    {
      'timestamp': DateTime(2025, 11, 4, 22, 0, 0),
      'durationMinutes': 420,  // 7 hours - Night Sleep
      'actions': List.filled(84, 0),
    },
    {
      'timestamp': DateTime(2025, 11, 5, 14, 30, 0),
      'durationMinutes': 60,   // 1 hour - Short Nap
      'actions': List.filled(12, 0),
    },
    {
      'timestamp': DateTime(2025, 11, 5, 19, 0, 0),
      'durationMinutes': 20,   // 20 min - Brief Rest
      'actions': List.filled(4, 0),
    },
  ];
  
  print('📊 SLEEP CLASSIFICATION:');
  print('');
  
  for (var session in sessions) {
    SleepType type = classifySleepSession(
      timestamp: session['timestamp'] as DateTime,
      durationMinutes: session['durationMinutes'] as int,
    );
    
    SleepTypeInfo info = sleepTypeInfo[type]!;
    
    print('${info.emoji} ${info.name}');
    print('   Time: ${session['timestamp']}');
    print('   Duration: ${session['durationMinutes']} minutes');
    print('   Description: ${info.description}');
    print('');
  }
  
  // Filter for main night sleeps only
  List<Map<String, dynamic>> nightSleeps = filterMainNightSleeps(sessions);
  
  print('🌙 MAIN NIGHT SLEEPS ONLY:');
  for (var session in nightSleeps) {
    print('   ${session['timestamp']} - ${session['durationMinutes']}min');
  }
  
  print('');
  print('✅ Classification complete:');
  print('   Total sessions: ${sessions.length}');
  print('   Night sleeps: ${nightSleeps.length}');
  print('   Naps/Rest: ${sessions.length - nightSleeps.length}');
}

/// IMPORTANT NOTES:
/// 
/// 1. ALWAYS classify AFTER merging sessions
///    Otherwise 60min chunks will be classified as naps!
/// 
/// 2. Night time window: 18:00-10:00
///    Based on typical sleep patterns
///    Adjust if needed for specific use cases
/// 
/// 3. Duration thresholds:
///    - Night Sleep: >= 180min (3 hours)
///    - Short Nap: 30-179min
///    - Brief Rest: 15-29min
///    - Insufficient: < 15min
/// 
/// 4. Main night sleep filter
///    Use for sleep score calculations
///    Excludes naps and daytime sleep
/// 
/// 5. Time-based classification
///    Hour of start time determines night vs day
///    Session may span across midnight
