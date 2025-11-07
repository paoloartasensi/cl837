/// Sleep Phases Calculator
/// 
/// Calculates deep, light, and awake phases from action indices
/// Based on CL837 protocol: action index 0 = deep sleep
/// 
/// Author: Extracted from lib/services/sleep_phases_calculator.dart
/// Date: November 5, 2025

/// Sleep phase types
enum SleepPhase {
  deep,   // Action index = 0
  light,  // Action index = 1-2
  awake,  // Action index >= 3
}

/// Phase duration info
class PhaseInfo {
  final int deepMinutes;
  final int lightMinutes;
  final int awakeMinutes;
  final int totalMinutes;
  
  final int deepPercentage;
  final int lightPercentage;
  final int awakePercentage;
  
  final bool hasDeepSleep;  // True if 3+ consecutive deep periods
  
  const PhaseInfo({
    required this.deepMinutes,
    required this.lightMinutes,
    required this.awakeMinutes,
    required this.totalMinutes,
    required this.deepPercentage,
    required this.lightPercentage,
    required this.awakePercentage,
    required this.hasDeepSleep,
  });
  
  @override
  String toString() {
    return 'PhaseInfo(deep: ${deepMinutes}min ($deepPercentage%), '
           'light: ${lightMinutes}min ($lightPercentage%), '
           'awake: ${awakeMinutes}min ($awakePercentage%), '
           'hasDeep: $hasDeepSleep)';
  }
}

/// Classifies action index to sleep phase
/// 
/// CL837 Protocol:
/// - 0 = Deep Sleep
/// - 1-2 = Light Sleep
/// - 3+ = Awake
SleepPhase classifyActionIndex(int actionIndex) {
  if (actionIndex == 0) {
    return SleepPhase.deep;
  } else if (actionIndex <= 2) {
    return SleepPhase.light;
  } else {
    return SleepPhase.awake;
  }
}

/// Calculates sleep phase durations from action indices
/// 
/// Each action index represents 5 minutes
/// 
/// Parameters:
/// - actions: List of action indices (0-255)
/// 
/// Returns: PhaseInfo with durations and percentages
PhaseInfo calculatePhases(List<int> actions) {
  if (actions.isEmpty) {
    return PhaseInfo(
      deepMinutes: 0,
      lightMinutes: 0,
      awakeMinutes: 0,
      totalMinutes: 0,
      deepPercentage: 0,
      lightPercentage: 0,
      awakePercentage: 0,
      hasDeepSleep: false,
    );
  }
  
  // Each action = 5 minutes
  const int minutesPerAction = 5;
  
  int deepCount = 0;
  int lightCount = 0;
  int awakeCount = 0;
  
  // Count each phase type
  for (int action in actions) {
    SleepPhase phase = classifyActionIndex(action);
    
    switch (phase) {
      case SleepPhase.deep:
        deepCount++;
        break;
      case SleepPhase.light:
        lightCount++;
        break;
      case SleepPhase.awake:
        awakeCount++;
        break;
    }
  }
  
  // Convert to minutes
  int deepMinutes = deepCount * minutesPerAction;
  int lightMinutes = lightCount * minutesPerAction;
  int awakeMinutes = awakeCount * minutesPerAction;
  int totalMinutes = actions.length * minutesPerAction;
  
  // Calculate percentages
  int deepPercentage = totalMinutes > 0 
      ? ((deepMinutes / totalMinutes) * 100).round() 
      : 0;
  int lightPercentage = totalMinutes > 0 
      ? ((lightMinutes / totalMinutes) * 100).round() 
      : 0;
  int awakePercentage = totalMinutes > 0 
      ? ((awakeMinutes / totalMinutes) * 100).round() 
      : 0;
  
  // Check for real deep sleep (3+ consecutive periods)
  bool hasDeepSleep = hasConsecutiveDeepSleep(actions, minConsecutive: 3);
  
  return PhaseInfo(
    deepMinutes: deepMinutes,
    lightMinutes: lightMinutes,
    awakeMinutes: awakeMinutes,
    totalMinutes: totalMinutes,
    deepPercentage: deepPercentage,
    lightPercentage: lightPercentage,
    awakePercentage: awakePercentage,
    hasDeepSleep: hasDeepSleep,
  );
}

/// Checks for consecutive deep sleep periods
/// 
/// Parameters:
/// - actions: List of action indices
/// - minConsecutive: Minimum consecutive deep periods (default: 3)
/// 
/// Returns: True if deep sleep confirmed (3+ consecutive 0s)
bool hasConsecutiveDeepSleep(List<int> actions, {int minConsecutive = 3}) {
  int consecutiveCount = 0;
  
  for (int action in actions) {
    if (action == 0) {
      consecutiveCount++;
      if (consecutiveCount >= minConsecutive) {
        return true;
      }
    } else {
      consecutiveCount = 0; // Reset counter
    }
  }
  
  return false;
}

/// Gets sleep phase timeline (for charts)
/// 
/// Returns list of (timestamp, phase) for each 5-minute interval
List<Map<String, dynamic>> getPhaseTimeline({
  required DateTime startTime,
  required List<int> actions,
}) {
  List<Map<String, dynamic>> timeline = [];
  
  for (int i = 0; i < actions.length; i++) {
    DateTime periodTime = startTime.add(Duration(minutes: i * 5));
    SleepPhase phase = classifyActionIndex(actions[i]);
    
    timeline.add({
      'timestamp': periodTime,
      'phase': phase,
      'actionIndex': actions[i],
    });
  }
  
  return timeline;
}

/// Calculates sleep quality score (0-100)
/// 
/// Based on:
/// - Deep sleep percentage (40% weight)
/// - Light sleep percentage (30% weight)
/// - Awake percentage penalty (30% weight)
/// 
/// Ideal ratios:
/// - Deep: 20-25%
/// - Light: 50-60%
/// - Awake: < 15%
int calculateSleepQualityScore(PhaseInfo info) {
  if (info.totalMinutes == 0) return 0;
  
  // Deep sleep score (0-40 points)
  // Ideal: 20-25%, max score at 22.5%
  double deepScore = 0;
  if (info.deepPercentage >= 15 && info.deepPercentage <= 30) {
    double deepDeviation = (info.deepPercentage - 22.5).abs().toDouble();
    deepScore = 40 * (1 - deepDeviation / 7.5).clamp(0, 1);
  } else if (info.deepPercentage > 0) {
    deepScore = 20; // Some deep sleep, but not optimal
  }
  
  // Light sleep score (0-30 points)
  // Ideal: 50-60%, max score at 55%
  double lightScore = 0;
  if (info.lightPercentage >= 40 && info.lightPercentage <= 70) {
    double lightDeviation = (info.lightPercentage - 55).abs().toDouble();
    lightScore = 30 * (1 - lightDeviation / 15).clamp(0, 1);
  } else if (info.lightPercentage > 0) {
    lightScore = 15; // Some light sleep
  }
  
  // Awake penalty (0-30 points, less is better)
  // Ideal: < 10%, penalty increases with more awake time
  double awakeScore = 30;
  if (info.awakePercentage > 10) {
    double awakePenalty = (info.awakePercentage - 10).toDouble() / 2;
    awakeScore = (30 - awakePenalty).clamp(0, 30);
  }
  
  // Total score
  int totalScore = (deepScore + lightScore + awakeScore).round();
  
  return totalScore.clamp(0, 100);
}

/// Example usage
void main() {
  // Example 1: Good night sleep with deep sleep
  List<int> goodSleep = [
    2, 2, 1, 1, 0, 0, 0, 0,  // Going deeper
    0, 0, 0, 1, 1, 2, 2, 3,  // Deep sleep cycle
    1, 1, 0, 0, 0, 0, 0, 1,  // Another deep period
    1, 2, 2, 1, 1, 1, 2, 3,  // Light sleep
    // ... more cycles
  ];
  
  // Example 2: Restless sleep
  List<int> restlessSleep = [
    3, 3, 2, 2, 3, 1, 3, 2,  // Lots of movement
    2, 3, 3, 1, 2, 3, 3, 2,  // No deep sleep
    // ... continues restless
  ];
  
  print('📊 SLEEP PHASES ANALYSIS:');
  print('');
  
  // Analyze good sleep
  print('✅ Good Sleep Example:');
  PhaseInfo goodInfo = calculatePhases(goodSleep);
  print('   $goodInfo');
  print('   Quality Score: ${calculateSleepQualityScore(goodInfo)}/100');
  print('   Has confirmed deep sleep: ${goodInfo.hasDeepSleep}');
  print('');
  
  // Analyze restless sleep
  print('⚠️  Restless Sleep Example:');
  PhaseInfo restlessInfo = calculatePhases(restlessSleep);
  print('   $restlessInfo');
  print('   Quality Score: ${calculateSleepQualityScore(restlessInfo)}/100');
  print('   Has confirmed deep sleep: ${restlessInfo.hasDeepSleep}');
  print('');
  
  // Timeline example
  DateTime now = DateTime.now();
  List<Map<String, dynamic>> timeline = getPhaseTimeline(
    startTime: now,
    actions: goodSleep.take(8).toList(),
  );
  
  print('📈 PHASE TIMELINE (first 40 minutes):');
  for (var entry in timeline) {
    String phaseIcon = entry['phase'] == SleepPhase.deep ? '🌑' :
                      entry['phase'] == SleepPhase.light ? '🌘' : '🌕';
    print('   ${entry['timestamp'].toString().substring(11, 16)} '
          '$phaseIcon ${entry['phase']} (index: ${entry['actionIndex']})');
  }
}

/// IMPORTANT NOTES:
/// 
/// 1. Action Index Meaning:
///    0 = Deep Sleep (minimal movement)
///    1-2 = Light Sleep (some movement)
///    3+ = Awake (significant movement)
/// 
/// 2. Deep Sleep Confirmation:
///    Require 3+ consecutive periods (15+ minutes)
///    Prevents false positives from brief stillness
/// 
/// 3. Time Resolution:
///    Each action index = 5 minutes
///    Total duration = actions.length * 5
/// 
/// 4. Quality Score:
///    Based on sleep research ideal ratios
///    Deep: 20-25% (REM+Deep combined)
///    Light: 50-60%
///    Awake: < 15%
/// 
/// 5. Phase Timeline:
///    Use for sleep charts/graphs
///    Shows transitions between phases
///    5-minute granularity
/// 
/// 6. Integration:
///    Calculate AFTER session merging
///    Use merged action lists
///    One calculation per merged session
