/// 🎯 QUICK START GUIDE - CL837 Advanced Features
/// 
/// Esempi pratici di utilizzo per tutte le nuove funzionalità
library;

import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../models/sport_health_data.dart';
import '../models/sensor_data.dart';
import 'dart:math';

// ===== 1. SPORT HEALTH MONITORING =====
// Use case: Professional fitness tracking app

class SportHealthExample {
  final ChileafExtendedService service;

  SportHealthExample(this.service);

  /// Monitor athlete performance during workout
  Future<void> monitorWorkout() async {
    // Start continuous monitoring
    await service.startHealthMonitoring();

    // Listen to real-time data
    service.sportHealthStream.listen((data) {
      // VO2 Max - Aerobic capacity
      print('🏃 VO2 Max: ${data.vo2Max} ml/kg/min');
      print('   Fitness Level: ${data.getVO2MaxLevel(true)}');

      // Stress monitoring
      print('😰 Stress: ${data.stressPercent}% (${data.stressLevel})');
      if (data.stressPercent > 70) {
        print('⚠️ High stress detected! Recommend rest.');
      }

      // HRV analysis (recovery indicator)
      if (data.lfHfRatio != null) {
        double ratio = data.lfHfRatio!;
        print('💓 LF/HF Ratio: ${ratio.toStringAsFixed(2)}');
        
        if (ratio < 0.5) {
          print('😌 Parasympathetic dominance - Good recovery state');
        } else if (ratio > 2.0) {
          print('😰 Sympathetic dominance - Stress/Fatigue detected');
        } else {
          print('✅ Balanced autonomic nervous system');
        }
      }

      // Stamina tracking
      print('⚡ Stamina: ${data.staminaDescription}');
      if (data.stamina < 2) {
        print('⚠️ Low stamina - Consider reducing intensity');
      }
    });

    // Stop after workout
    await Future.delayed(const Duration(minutes: 30));
    await service.stopHealthMonitoring();
  }

  /// One-time health assessment
  Future<void> quickHealthCheck() async {
    await service.getBodyHealth();
    
    // Data arrives via stream
    // Ideal for pre-workout assessment or health screening
  }
}

// ===== 2. HEART RATE ZONES TRAINING =====
// Use case: Training with HR zones (à la Polar, Garmin)

class HeartRateZonesExample {
  final ChileafExtendedService service;

  HeartRateZonesExample(this.service);

  /// Configure HR zones for 30-year-old athlete
  Future<void> setupHRZones() async {
    int age = 30;
    int maxHR = HeartRateMax.calculateByAge(age); // 190 BPM
    
    // Calculate zones
    HeartRateMax hrMax = HeartRateMax(max: maxHR);
    Map<String, int> zones = hrMax.getTargetZones();
    
    print('🎯 Heart Rate Zones:');
    print('   Resting (50%):  ${zones['resting']} BPM');
    print('   Fat Burn (60%): ${zones['fatBurn']} BPM');
    print('   Cardio (70%):   ${zones['cardio']} BPM');
    print('   Peak (85%):     ${zones['peak']} BPM');
    print('   Maximum:        ${zones['maximum']} BPM');

    // Set device configuration
    int minHR = 50;  // Resting heart rate
    int goalHR = zones['cardio']!; // Target cardio zone
    
    await service.setHeartRateStatus(minHR, maxHR, goalHR);
    await service.setHeartRateMax(maxHR);
    
    // Enable alarm for out-of-zone alerts
    await service.setHeartRateAlarm(true);
    
    print('✅ HR zones configured on device');
  }

  /// Monitor zone compliance during training
  void monitorZoneCompliance() {
    service.realTimeHeartRateStream.listen((currentHR) {
      // Get zones (assuming we stored them)
      int targetMin = 130; // Cardio zone lower bound
      int targetMax = 150; // Cardio zone upper bound
      
      if (currentHR < targetMin) {
        print('⬆️ HR too low: $currentHR BPM - Increase intensity');
      } else if (currentHR > targetMax) {
        print('⬇️ HR too high: $currentHR BPM - Reduce intensity');
      } else {
        print('✅ In zone: $currentHR BPM');
      }
    });
  }
}

// ===== 3. ADVANCED HRV ANALYSIS =====
// Use case: Recovery tracking, stress management

class HRVAnalysisExample {
  final ChileafExtendedService service;

  HRVAnalysisExample(this.service);

  /// Calculate HRV metrics from RR intervals
  Future<void> analyzeHRV() async {
    await service.getRRIntervalsHistory();

    service.rrIntervalStream.listen((intervals) {
      if (intervals.length < 30) {
        print('⚠️ Need at least 30 RR intervals for accurate HRV');
        return;
      }

      // Extract RR values
      List<int> rr = intervals.map((i) => i.interval).toList();

      // 1. SDNN - Overall HRV (higher = better)
      double mean = rr.reduce((a, b) => a + b) / rr.length;
      double variance = rr.map((x) => pow(x - mean, 2)).reduce((a, b) => a + b) / rr.length;
      double sdnn = sqrt(variance);

      // 2. RMSSD - Short-term HRV (parasympathetic activity)
      List<double> diffs = [];
      for (int i = 0; i < rr.length - 1; i++) {
        diffs.add(pow(rr[i + 1] - rr[i], 2).toDouble());
      }
      double rmssd = sqrt(diffs.reduce((a, b) => a + b) / diffs.length);

      // 3. pNN50 - % of RR intervals >50ms difference
      int nn50 = 0;
      for (int i = 0; i < rr.length - 1; i++) {
        if ((rr[i + 1] - rr[i]).abs() > 50) nn50++;
      }
      double pnn50 = (nn50 / (rr.length - 1)) * 100;

      print('📊 HRV ANALYSIS:');
      print('   SDNN: ${sdnn.toStringAsFixed(2)} ms');
      print('   RMSSD: ${rmssd.toStringAsFixed(2)} ms');
      print('   pNN50: ${pnn50.toStringAsFixed(1)}%');

      // Recovery assessment
      _assessRecovery(sdnn, rmssd);
    });
  }

  void _assessRecovery(double sdnn, double rmssd) {
    // Professional athlete thresholds
    if (sdnn > 60 && rmssd > 40) {
      print('✅ EXCELLENT recovery - Ready for high intensity');
    } else if (sdnn > 40 && rmssd > 25) {
      print('👍 GOOD recovery - Normal training OK');
    } else if (sdnn > 20 && rmssd > 15) {
      print('⚠️ MODERATE recovery - Light training recommended');
    } else {
      print('❌ POOR recovery - REST DAY recommended!');
    }
  }
}

// ===== 4. MOTION ANALYSIS WITH 6D SENSOR =====
// Use case: Form analysis, fall detection, gesture recognition

class MotionAnalysisExample {
  final ChileafExtendedService service;
  final List<Sensor6DRawData> _buffer = [];

  MotionAnalysisExample(this.service);

  /// Analyze running form
  Future<void> analyzeRunningForm() async {
    // Set high frequency for accurate motion capture
    await service.set6DFrequency(Sensor6DFrequency.hz208);

    service.sensor6DDataStream.listen((data) {
      _buffer.add(data);
      
      // Analyze every second (208 samples at 208 Hz)
      if (_buffer.length >= 208) {
        _analyzeMotionPattern(_buffer);
        _buffer.clear();
      }
    });
  }

  void _analyzeMotionPattern(List<Sensor6DRawData> samples) {
    // Calculate average accelerometer magnitude (impact force)
    double avgImpact = samples
        .map((s) => s.accelerometerMagnitude)
        .reduce((a, b) => a + b) / samples.length;

    // Calculate rotation (gyroscope activity)
    double avgRotation = samples
        .map((s) => s.gyroscopeMagnitude)
        .reduce((a, b) => a + b) / samples.length;

    print('🏃 Running Metrics:');
    print('   Impact Force: ${avgImpact.toStringAsFixed(2)} mg');
    print('   Body Rotation: ${avgRotation.toStringAsFixed(2)} deg/s');

    // Form feedback
    if (avgImpact > 2000) {
      print('⚠️ HIGH IMPACT - Risk of injury, consider softer landing');
    }
    if (avgRotation > 100) {
      print('⚠️ EXCESSIVE ROTATION - Check running form');
    }

    // Cadence calculation (step frequency)
    int peaks = _detectPeaks(samples.map((s) => s.accelerometerZ).toList());
    int cadence = (peaks * 60) ~/ (samples.length / 208); // steps per minute
    print('   Cadence: $cadence steps/min');
    
    if (cadence < 160) {
      print('💡 TIP: Increase cadence to 170-180 for better efficiency');
    }
  }

  int _detectPeaks(List<int> signal) {
    int peaks = 0;
    int threshold = signal.reduce((a, b) => a > b ? a : b) ~/ 2;
    
    for (int i = 1; i < signal.length - 1; i++) {
      if (signal[i] > threshold && 
          signal[i] > signal[i - 1] && 
          signal[i] > signal[i + 1]) {
        peaks++;
      }
    }
    return peaks;
  }

  /// Fall detection
  void monitorFalls() {
    service.sensor6DDataStream.listen((data) {
      // Sudden acceleration spike = potential fall
      double totalAccel = data.accelerometerMagnitude;
      
      if (totalAccel > 3000) { // 3G threshold
        print('🚨 FALL DETECTED! High impact: ${totalAccel.toStringAsFixed(0)} mg');
        _triggerEmergencyAlert();
      }
    });
  }

  void _triggerEmergencyAlert() {
    // Send alert to emergency contacts, show notification, etc.
    print('📞 Sending emergency alert...');
  }
}

// ===== 5. COMPLETE WORKOUT SESSION EXAMPLE =====

class WorkoutSessionExample {
  final ChileafExtendedService service;

  WorkoutSessionExample(this.service);

  /// Full workout with all metrics
  Future<void> startWorkout() async {
    print('🏋️ STARTING WORKOUT SESSION');
    
    // 1. Configure device
    await _configureDevice();
    
    // 2. Start all monitoring
    await service.startHealthMonitoring();
    await service.set3DEnabled(true);
    
    // 3. Subscribe to all streams
    _monitorWorkoutMetrics();
    
    // 4. Workout duration (e.g., 45 minutes)
    await Future.delayed(const Duration(minutes: 45));
    
    // 5. Stop monitoring
    await service.stopHealthMonitoring();
    
    // 6. Get final analysis
    await _getFinalAnalysis();
    
    print('✅ WORKOUT COMPLETE');
  }

  Future<void> _configureDevice() async {
    // Set HR zones
    await service.setHeartRateStatus(60, 185, 140);
    await service.setHeartRateAlarm(true);
    
    // Configure sensors
    await service.set6DFrequency(Sensor6DFrequency.hz104);
    
    print('⚙️ Device configured');
  }

  void _monitorWorkoutMetrics() {
    // Real-time HR
    service.realTimeHeartRateStream.listen((hr) {
      print('💓 HR: $hr BPM');
    });

    // Sport Health updates
    service.sportHealthStream.listen((health) {
      print('🏃 VO2 Max: ${health.vo2Max}, Stress: ${health.stressPercent}%');
    });

    // Motion data
    service.sensor6DDataStream.listen((motion) {
      // Process motion for form analysis
    });
  }

  Future<void> _getFinalAnalysis() async {
    // Get RR intervals for HRV
    await service.getRRIntervalsHistory();
    
    // Get final health snapshot
    await service.getBodyHealth();
    
    print('📊 Generating workout report...');
  }
}

// ===== 6. USAGE IN MAIN APP =====

class ExampleUsageInApp extends StatefulWidget {
  final ChileafExtendedService service;

  const ExampleUsageInApp({super.key, required this.service});

  @override
  State<ExampleUsageInApp> createState() => _ExampleUsageInAppState();
}

class _ExampleUsageInAppState extends State<ExampleUsageInApp> {
  SportHealthData? _latestHealth;

  @override
  void initState() {
    super.initState();
    
    // Subscribe to sport health updates
    widget.service.sportHealthStream.listen((data) {
      setState(() => _latestHealth = data);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Fitness Dashboard')),
      body: Column(
        children: [
          // VO2 Max Card
          if (_latestHealth != null)
            Card(
              child: ListTile(
                leading: const Icon(Icons.favorite, color: Colors.red),
                title: Text('VO2 Max: ${_latestHealth!.vo2Max}'),
                subtitle: Text(
                  _latestHealth!.getVO2MaxLevel(true),
                  style: TextStyle(
                    color: _latestHealth!.vo2Max > 50 
                        ? Colors.green 
                        : Colors.orange,
                  ),
                ),
              ),
            ),
          
          // Quick action buttons
          ElevatedButton(
            onPressed: () => widget.service.startHealthMonitoring(),
            child: const Text('Start Workout'),
          ),
          ElevatedButton(
            onPressed: () => widget.service.getRRIntervalsHistory(),
            child: const Text('Check Recovery'),
          ),
        ],
      ),
    );
  }
}
