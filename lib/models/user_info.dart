/// User Information Model
/// 
/// User profile data stored on the CL837 device
library;

import 'package:flutter/foundation.dart';

/// User Information
class UserInfo {
  /// User age (years)
  final int age;

  /// User gender (0 = female, 1 = male)
  final int gender;

  /// User weight (kg)
  final int weight;

  /// User height (cm)
  final int height;

  /// User phone number or ID (stored as 5 bytes - uint40)
  final int userId;

  /// Timestamp when data was received/updated
  final DateTime timestamp;

  UserInfo({
    required this.age,
    required this.gender,
    required this.weight,
    required this.height,
    required this.userId,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  /// Get gender as string
  String get genderString => gender == 1 ? 'Male' : 'Female';

  /// Get gender as boolean (true = male, false = female)
  bool get isMale => gender == 1;

  /// Calculate BMI (Body Mass Index)
  /// Formula: weight (kg) / (height (m))^2
  double get bmi {
    if (height > 0) {
      double heightInMeters = height / 100.0;
      return weight / (heightInMeters * heightInMeters);
    }
    return 0.0;
  }

  /// Get BMI category
  String get bmiCategory {
    double bmiValue = bmi;
    if (bmiValue < 18.5) return 'Underweight';
    if (bmiValue < 25) return 'Normal';
    if (bmiValue < 30) return 'Overweight';
    return 'Obese';
  }

  /// Calculate ideal weight range (Devine formula)
  /// Male: 50 kg + 2.3 kg per inch over 5 feet
  /// Female: 45.5 kg + 2.3 kg per inch over 5 feet
  Map<String, double> get idealWeightRange {
    double heightInInches = height / 2.54;
    double baseWeight = isMale ? 50.0 : 45.5;
    
    if (heightInInches > 60) {
      double idealWeight = baseWeight + 2.3 * (heightInInches - 60);
      return {
        'min': idealWeight - 5,
        'ideal': idealWeight,
        'max': idealWeight + 5,
      };
    }
    
    return {'min': baseWeight - 5, 'ideal': baseWeight, 'max': baseWeight + 5};
  }

  /// Calculate maximum heart rate by age (220 - age formula)
  int get maxHeartRate => 220 - age;

  /// Calculate target heart rate zones
  Map<String, int> get heartRateZones {
    int max = maxHeartRate;
    return {
      'resting': (max * 0.5).round(),
      'warmUp': (max * 0.6).round(),
      'fatBurn': (max * 0.7).round(),
      'aerobic': (max * 0.8).round(),
      'anaerobic': (max * 0.9).round(),
      'maximum': max,
    };
  }

  // ===== RECOMMENDED HEART RATE THRESHOLDS =====

  /// Recommended minimum heart rate (resting + 10%)
  /// Below this value, heart rate is too low for normal activity
  int get recommendedMinHeartRate {
    // Resting HR typically 60-100 BPM
    // Use 50 BPM as safe minimum (or 50% of max HR, whichever is higher)
    int restingHR = (maxHeartRate * 0.5).round();
    return restingHR < 50 ? 50 : restingHR;
  }

  /// Recommended maximum heart rate for safety
  /// Should not exceed 85-90% of maximum HR during normal workouts
  int get recommendedMaxHeartRate {
    // 90% of max HR is a safe upper limit for most workouts
    return (maxHeartRate * 0.9).round();
  }

  /// Recommended goal heart rate for fitness
  /// Typically 70-80% of max HR (aerobic zone)
  int get recommendedGoalHeartRate {
    // 75% of max HR - optimal for aerobic fitness
    return (maxHeartRate * 0.75).round();
  }

  /// Get all recommended HR settings as a map
  /// Can be used directly with setHeartRateStatus()
  Map<String, int> get recommendedHeartRateSettings {
    return {
      'min': recommendedMinHeartRate,
      'max': recommendedMaxHeartRate,
      'goal': recommendedGoalHeartRate,
    };
  }

  /// Copy with method for creating modified copies
  UserInfo copyWith({
    int? age,
    int? gender,
    int? weight,
    int? height,
    int? userId,
    DateTime? timestamp,
  }) {
    return UserInfo(
      age: age ?? this.age,
      gender: gender ?? this.gender,
      weight: weight ?? this.weight,
      height: height ?? this.height,
      userId: userId ?? this.userId,
      timestamp: timestamp ?? this.timestamp,
    );
  }

  @override
  String toString() {
    return 'UserInfo(age: $age, gender: $genderString, weight: $weight kg, '
        'height: $height cm, userId: $userId, BMI: ${bmi.toStringAsFixed(1)} - $bmiCategory)';
  }

  /// Create UserInfo from device response bytes
  /// Protocol 0x03 response format (CORRECTED with real device data):
  /// [0xFF, length, 0x03, ecg_open, charging_info, AGE, GENDER, WEIGHT, HEIGHT, 0x00, 0x00, 0x00, 0x00, USER_ID(1 byte), checksum]
  /// ✅ VERIFIED: Byte 5=age, 6=gender, 7=weight, 8=height, 13=userId
  static UserInfo? fromDeviceResponse(List<int> data) {
    try {
      if (data.length < 15) {
        return null; // Not enough data
      }

      // ✅ CORRECTED MAPPING (verified with real device):
      // Skip: header(1) + length(1) + command(1) + ecg(1) + charging(1) = 5 bytes
      int age = data[5];        // Byte 5 = AGE (0x28 = 40)
      int genderRaw = data[6];  // Byte 6 = GENDER (0x01 = Male)
      int weight = data[7];     // Byte 7 = WEIGHT (0x58 = 88 kg)
      int height = data[8];     // Byte 8 = HEIGHT (0xB0 = 176 cm)
      
      // ✅ FIX: Validate gender - must be 0 (female) or 1 (male)
      // If device sends invalid value, default to male (1)
      int gender = (genderRaw == 0 || genderRaw == 1) ? genderRaw : 1;
      
      if (genderRaw != 0 && genderRaw != 1) {
        debugPrint('⚠️ Invalid gender value from device: $genderRaw (using default: 1=Male)');
      }

      // ✅ CORRECTED: User ID is 1 byte at position 13 (0x4B = 75)
      int userId = data[13];

      debugPrint('📊 Parsed UserInfo: age=$age, gender=$gender, weight=$weight kg, height=$height cm, userId=$userId');

      return UserInfo(
        age: age,
        gender: gender,
        weight: weight,
        height: height,
        userId: userId,
      );
    } catch (e) {
      debugPrint('❌ Error parsing UserInfo: $e');
      return null;
    }
  }

  /// Convert UserInfo to command bytes for setting
  /// Protocol 0x04 format:
  /// [0xFF, 0x0E, 0x04, age, gender, weight, height, userId(5 bytes), checksum]
  List<int> toCommandBytes() {
    // User ID as 5 bytes (big endian)
    int byte0 = (userId >> 32) & 0xFF;
    int byte1 = (userId >> 24) & 0xFF;
    int byte2 = (userId >> 16) & 0xFF;
    int byte3 = (userId >> 8) & 0xFF;
    int byte4 = userId & 0xFF;

    return [
      age,
      gender,
      weight,
      height,
      byte0,
      byte1,
      byte2,
      byte3,
      byte4,
    ];
  }
}

/// Device Status Information (from 0x03 command)
class DeviceStatus {
  /// ECG is open (0 = not open, 1 = open)
  final bool ecgOpen;

  /// Charging information
  /// 0 = not charged
  /// 1 = charging
  /// 2 = fully charged while charging
  final int chargingStatus;

  /// Battery percentage (0-100%)
  final int batteryLevel;

  /// User information
  final UserInfo userInfo;

  /// Timestamp when data was received
  final DateTime timestamp;

  DeviceStatus({
    required this.ecgOpen,
    required this.chargingStatus,
    required this.batteryLevel,
    required this.userInfo,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  /// Get charging status as string
  String get chargingStatusString {
    switch (chargingStatus) {
      case 0:
        return 'Not Charging';
      case 1:
        return 'Charging';
      case 2:
        return 'Fully Charged';
      default:
        return 'Unknown';
    }
  }

  /// Check if device needs charging (< 20%)
  bool get needsCharging => batteryLevel < 20;

  /// Check if battery is low (< 10%)
  bool get batteryLow => batteryLevel < 10;

  @override
  String toString() {
    return 'DeviceStatus(ECG: ${ecgOpen ? "ON" : "OFF"}, '
        'Charging: $chargingStatusString, Battery: $batteryLevel%, '
        'User: ${userInfo.toString()})';
  }

  /// Parse complete device status from 0x03 response
  static DeviceStatus? fromDeviceResponse(List<int> data) {
    try {
      if (data.length < 15) {
        return null; // Not enough data
      }

      // Parse device info (first 3 bytes after command)
      bool ecgOpen = data[3] == 1;
      int chargingStatus = data[4];
      int batteryLevel = data[5];

      // Parse user info
      UserInfo? userInfo = UserInfo.fromDeviceResponse(data);
      if (userInfo == null) {
        return null;
      }

      return DeviceStatus(
        ecgOpen: ecgOpen,
        chargingStatus: chargingStatus,
        batteryLevel: batteryLevel,
        userInfo: userInfo,
      );
    } catch (e) {
      return null;
    }
  }
}
