/// Heart Rate Auto-Configuration Example
/// 
/// Shows how to automatically configure heart rate thresholds based on user age
library;

import '../chileaf_extended_service.dart';
import '../models/user_info.dart';

/// Example 1: Basic Auto-Configuration
/// 
/// When you set user info, automatically configure optimal HR thresholds
Future<void> example1BasicAutoConfig(ChileafExtendedService service) async {
  print('\n=== Example 1: Basic Auto-Configuration ===\n');

  // User profile
  final userInfo = UserInfo(
    age: 35,
    gender: 1, // male
    weight: 75,
    height: 175,
    userId: 1234567890,
  );

  print('User Profile:');
  print('  Age: ${userInfo.age} years');
  print('  Gender: ${userInfo.genderString}');
  print('  Weight: ${userInfo.weight} kg');
  print('  Height: ${userInfo.height} cm');
  print('');

  // Show calculated values
  print('Calculated Heart Rate Values:');
  print('  Max HR (220-age): ${userInfo.maxHeartRate} BPM');
  print('  Min HR (50% max): ${userInfo.recommendedMinHeartRate} BPM');
  print('  Goal HR (75% max): ${userInfo.recommendedGoalHeartRate} BPM');
  print('  Max Safe (90% max): ${userInfo.recommendedMaxHeartRate} BPM');
  print('');

  // Set user info on device
  await service.setUserInfo(
    userInfo.age,
    userInfo.gender,
    userInfo.weight,
    userInfo.height,
    userInfo.userId,
  );

  // Auto-configure HR thresholds
  await service.autoConfigureHeartRate(userInfo);

  print('✅ Device configured with optimal HR thresholds for age ${userInfo.age}');
}

/// Example 2: Age-Based Comparison
/// 
/// Compare recommended HR thresholds for different ages
Future<void> example2AgeBasedComparison() async {
  print('\n=== Example 2: Age-Based Comparison ===\n');

  final ages = [20, 30, 40, 50, 60, 70];

  print('HR Thresholds by Age:');
  print('Age | Max HR | Min HR | Goal HR | Max Safe');
  print('------------------------------------------------');

  for (final age in ages) {
    final user = UserInfo(
      age: age,
      gender: 1,
      weight: 75,
      height: 175,
      userId: 12345,
    );

    print('${age.toString().padLeft(3)} | '
        '${user.maxHeartRate.toString().padLeft(6)} | '
        '${user.recommendedMinHeartRate.toString().padLeft(6)} | '
        '${user.recommendedGoalHeartRate.toString().padLeft(7)} | '
        '${user.recommendedMaxHeartRate.toString().padLeft(8)}');
  }

  print('\nPattern: As age increases, all HR thresholds decrease');
}

/// Example 3: Manual vs Auto Configuration
/// 
/// Compare manual HR setup vs auto-configuration
Future<void> example3ManualVsAuto(ChileafExtendedService service) async {
  print('\n=== Example 3: Manual vs Auto Configuration ===\n');

  final userInfo = UserInfo(
    age: 45,
    gender: 0, // female
    weight: 65,
    height: 165,
    userId: 9876543210,
  );

  // Manual configuration (old way)
  print('❌ Manual Configuration (NOT age-specific):');
  print('  Min: 60 BPM (fixed)');
  print('  Goal: 120 BPM (fixed)');
  print('  Max: 180 BPM (fixed)');
  await service.setHeartRateStatus(60, 180, 120);
  print('');

  // Auto configuration (new way)
  print('✅ Auto Configuration (age-specific for 45 years):');
  print('  Min: ${userInfo.recommendedMinHeartRate} BPM (50% of max)');
  print('  Goal: ${userInfo.recommendedGoalHeartRate} BPM (75% of max)');
  print('  Max: ${userInfo.recommendedMaxHeartRate} BPM (90% of max)');
  await service.autoConfigureHeartRate(userInfo);
  print('');

  print('Benefits of Auto-Configuration:');
  print('  ✓ Age-appropriate thresholds');
  print('  ✓ Safer workout limits');
  print('  ✓ Optimal training zones');
  print('  ✓ No manual calculation needed');
}

/// Example 4: Training Zones
/// 
/// Show all training zones based on user profile
Future<void> example4TrainingZones() async {
  print('\n=== Example 4: Training Zones ===\n');

  final athlete = UserInfo(
    age: 28,
    gender: 1, // male
    weight: 72,
    height: 180,
    userId: 11111,
  );

  print('Athlete Profile:');
  print('  Age: ${athlete.age} years');
  print('  Max HR: ${athlete.maxHeartRate} BPM (220 - age)');
  print('');

  final zones = athlete.heartRateZones;

  print('Training Zones:');
  print('  Zone 1 - Resting:    ${zones['resting']} BPM (50% max)');
  print('  Zone 2 - Warm-up:    ${zones['warmUp']} BPM (60% max)');
  print('  Zone 3 - Fat Burn:   ${zones['fatBurn']} BPM (70% max) 🔥');
  print('  Zone 4 - Aerobic:    ${zones['aerobic']} BPM (80% max) 💪');
  print('  Zone 5 - Anaerobic:  ${zones['anaerobic']} BPM (90% max) ⚡');
  print('  Zone 6 - Maximum:    ${zones['maximum']} BPM (100% max) 🚀');
  print('');

  print('Recommended Device Settings:');
  print('  Min HR Threshold: ${athlete.recommendedMinHeartRate} BPM');
  print('  Goal HR (Aerobic): ${athlete.recommendedGoalHeartRate} BPM');
  print('  Max HR Threshold: ${athlete.recommendedMaxHeartRate} BPM');
}

/// Example 5: Listen for User Info and Auto-Configure
/// 
/// When device sends user info, automatically configure HR
Future<void> example5ListenAndAutoConfigure(
    ChileafExtendedService service) async {
  print('\n=== Example 5: Listen and Auto-Configure ===\n');

  // Listen for user info updates
  service.userInfoStream.listen((userInfo) async {
    print('📥 User info received from device:');
    print('   Age: ${userInfo.age} years');
    print('   Gender: ${userInfo.genderString}');
    print('');

    print('🎯 Auto-configuring HR thresholds...');
    print('   Min: ${userInfo.recommendedMinHeartRate} BPM');
    print('   Goal: ${userInfo.recommendedGoalHeartRate} BPM');
    print('   Max: ${userInfo.recommendedMaxHeartRate} BPM');
    print('');

    // Auto-configure based on received data
    await service.autoConfigureHeartRate(userInfo);

    print('✅ Device HR thresholds updated!');
  });

  // Request user info from device
  print('📡 Requesting user info from device...');
  await service.requestUserInfo();
}

/// Example 6: Validate Custom HR Settings
/// 
/// Check if custom HR settings are safe for user's age
void example6ValidateCustomSettings(UserInfo userInfo, int minHR, int maxHR, int goalHR) {
  print('\n=== Example 6: Validate Custom HR Settings ===\n');

  print('User: ${userInfo.age} years old');
  print('Max possible HR: ${userInfo.maxHeartRate} BPM');
  print('');

  print('Custom Settings:');
  print('  Min: $minHR BPM');
  print('  Goal: $goalHR BPM');
  print('  Max: $maxHR BPM');
  print('');

  // Validate
  bool valid = true;
  final recommended = userInfo.recommendedHeartRateSettings;

  if (maxHR > userInfo.maxHeartRate) {
    print('⚠️  Warning: Max HR ($maxHR) exceeds age-based max (${userInfo.maxHeartRate})');
    valid = false;
  }

  if (maxHR > recommended['max']!) {
    print('⚠️  Warning: Max HR ($maxHR) exceeds safe limit (${recommended['max']})');
    valid = false;
  }

  if (minHR < 40) {
    print('⚠️  Warning: Min HR ($minHR) too low (minimum 40 BPM)');
    valid = false;
  }

  if (goalHR > maxHR) {
    print('❌ Error: Goal HR ($goalHR) exceeds Max HR ($maxHR)');
    valid = false;
  }

  if (goalHR < minHR) {
    print('❌ Error: Goal HR ($goalHR) below Min HR ($minHR)');
    valid = false;
  }

  if (valid) {
    print('✅ Custom settings are safe and valid!');
  } else {
    print('');
    print('Recommended settings for age ${userInfo.age}:');
    print('  Min: ${recommended['min']} BPM');
    print('  Goal: ${recommended['goal']} BPM');
    print('  Max: ${recommended['max']} BPM');
  }
}

/// Main example runner
Future<void> runAllExamples(ChileafExtendedService service) async {
  await example1BasicAutoConfig(service);
  await example2AgeBasedComparison();
  await example3ManualVsAuto(service);
  await example4TrainingZones();
  await example5ListenAndAutoConfigure(service);
  
  // Example 6 validation
  final testUser = UserInfo(age: 50, gender: 1, weight: 80, height: 175, userId: 123);
  example6ValidateCustomSettings(testUser, 60, 200, 140); // Unsafe
  example6ValidateCustomSettings(testUser, 85, 153, 128); // Safe
}
