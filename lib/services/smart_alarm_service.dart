/// Smart Alarm Service
/// 
/// Calculates optimal wake time within a user-defined window
/// Looks for light sleep phases to ensure gentle, natural waking
/// 
/// Algorithm:
/// 1. User sets desired wake time + window (e.g., 7:00 AM ± 30 min)
/// 2. Service monitors sleep data in real-time
/// 3. Finds first light sleep occurrence in window (6:30-7:00 AM)
/// 4. Triggers alarm at optimal time
/// 
/// Based on research showing waking during light sleep reduces grogginess

import 'dart:async';
import '../models/smart_alarm.dart';
import '../models/historical_data.dart';

class SmartAlarmService {
  /// Active alarm being monitored
  SmartAlarm? _activeAlarm;
  
  /// Callback when optimal wake time is found
  Function(OptimalWakeResult)? onOptimalWakeTimeFound;
  
  /// Callback when alarm should trigger
  Function(SmartAlarm)? onAlarmTrigger;
  
  /// Timer for checking sleep data
  Timer? _monitoringTimer;
  
  /// Whether service is actively monitoring
  bool get isMonitoring => _monitoringTimer != null && _monitoringTimer!.isActive;

  /// Set active alarm
  void setAlarm(SmartAlarm alarm) {
    _activeAlarm = alarm;
    
    if (alarm.isEnabled) {
      _startMonitoring();
    } else {
      _stopMonitoring();
    }
  }

  /// Cancel active alarm
  void cancelAlarm() {
    _activeAlarm = null;
    _stopMonitoring();
  }

  /// Get active alarm
  SmartAlarm? get activeAlarm => _activeAlarm;

  /// Calculate optimal wake time from sleep data
  /// 
  /// Strategy:
  /// - Prefer light sleep phases within window
  /// - If multiple light sleep periods, choose closest to desired time
  /// - If no light sleep, choose time with lowest activity index
  OptimalWakeResult? calculateOptimalWakeTime(
    SmartAlarm alarm,
    SleepData31 currentSleepData,
  ) {
    // Get sleep data within alarm window
    final windowDataPoints = _getDataPointsInWindow(
      alarm,
      currentSleepData,
    );

    if (windowDataPoints.isEmpty) {
      return null; // Not enough data yet
    }

    // Find light sleep periods in window
    final lightSleepPeriods = <_DataPoint>[];
    final awakePeriods = <_DataPoint>[];
    
    for (final point in windowDataPoints) {
      if (_isLightSleep(point.activityIndex)) {
        lightSleepPeriods.add(point);
      } else if (_isAwake(point.activityIndex)) {
        awakePeriods.add(point);
      }
    }

    // Strategy 1: Wake during light sleep (optimal)
    if (lightSleepPeriods.isNotEmpty) {
      // Choose light sleep period closest to desired time
      final optimal = _findClosestToDesired(lightSleepPeriods, alarm.desiredWakeTime);
      return OptimalWakeResult(
        optimalTime: optimal.timestamp,
        phase: WakePhase.lightSleep,
        confidence: _calculateConfidence(optimal, windowDataPoints),
        reason: 'Light sleep detected - optimal for waking',
      );
    }

    // Strategy 2: Wake during awake period (acceptable)
    if (awakePeriods.isNotEmpty) {
      final optimal = _findClosestToDesired(awakePeriods, alarm.desiredWakeTime);
      return OptimalWakeResult(
        optimalTime: optimal.timestamp,
        phase: WakePhase.awake,
        confidence: _calculateConfidence(optimal, windowDataPoints),
        reason: 'Already awake - good time to wake',
      );
    }

    // Strategy 3: Choose lowest activity period (fallback)
    final lowestActivity = windowDataPoints.reduce(
      (a, b) => a.activityIndex < b.activityIndex ? a : b,
    );
    
    return OptimalWakeResult(
      optimalTime: lowestActivity.timestamp,
      phase: _isDeepSleep(lowestActivity.activityIndex) 
          ? WakePhase.deepSleep 
          : WakePhase.unknown,
      confidence: 50.0, // Lower confidence for non-optimal wake
      reason: 'Best available time in window',
    );
  }

  /// Start monitoring sleep data for alarm trigger
  void _startMonitoring() {
    _stopMonitoring(); // Clear any existing timer
    
    // Check every 30 seconds if we're in alarm window
    _monitoringTimer = Timer.periodic(
      const Duration(seconds: 30),
      (_) => _checkAlarmConditions(),
    );
  }

  /// Stop monitoring
  void _stopMonitoring() {
    _monitoringTimer?.cancel();
    _monitoringTimer = null;
  }

  /// Check if alarm should trigger
  void _checkAlarmConditions() {
    if (_activeAlarm == null || !_activeAlarm!.isEnabled) {
      return;
    }

    final now = DateTime.now();
    
    // Check if we're past desired wake time
    if (now.isAfter(_activeAlarm!.desiredWakeTime)) {
      // Trigger alarm at desired time as fallback
      onAlarmTrigger?.call(_activeAlarm!);
      _stopMonitoring();
      return;
    }

    // Check if we're in window but haven't found optimal time yet
    if (now.isAfter(_activeAlarm!.windowStart) && 
        now.isBefore(_activeAlarm!.desiredWakeTime)) {
      // In window - need to analyze current sleep data
      // This would be triggered by external sleep data updates
    }
  }

  /// Process new sleep data to check for optimal wake time
  void processSleepData(SleepData31 sleepData) {
    if (_activeAlarm == null || !_activeAlarm!.isEnabled) {
      return;
    }

    final now = DateTime.now();
    
    // Only process if we're in the alarm window
    if (!now.isAfter(_activeAlarm!.windowStart) || 
        now.isAfter(_activeAlarm!.desiredWakeTime)) {
      return;
    }

    final optimalResult = calculateOptimalWakeTime(_activeAlarm!, sleepData);
    
    if (optimalResult != null) {
      // Notify about optimal time found
      onOptimalWakeTimeFound?.call(optimalResult);
      
      // Check if optimal time is now (within 5 min tolerance)
      if (optimalResult.optimalTime.difference(now).abs().inMinutes <= 5 &&
          optimalResult.phase.isOptimalForWaking) {
        // Trigger alarm!
        onAlarmTrigger?.call(_activeAlarm!.copyWith(
          optimalWakeTime: optimalResult.optimalTime,
          confidence: optimalResult.confidence,
          wakePhase: optimalResult.phase,
        ));
        _stopMonitoring();
      }
    }
  }

  /// Get data points within alarm window
  List<_DataPoint> _getDataPointsInWindow(
    SmartAlarm alarm,
    SleepData31 sleepData,
  ) {
    final dataPoints = <_DataPoint>[];
    
    // Each activity index represents 5 minutes
    var currentTime = sleepData.timestamp;
    
    for (final index in sleepData.activityIndices) {
      if (currentTime.isAfter(alarm.windowStart) && 
          currentTime.isBefore(alarm.windowEnd)) {
        dataPoints.add(_DataPoint(
          timestamp: currentTime,
          activityIndex: index,
        ));
      }
      currentTime = currentTime.add(const Duration(minutes: 5));
    }
    
    return dataPoints;
  }

  /// Find data point closest to desired time
  _DataPoint _findClosestToDesired(List<_DataPoint> points, DateTime desired) {
    return points.reduce((a, b) {
      final aDiff = a.timestamp.difference(desired).abs();
      final bDiff = b.timestamp.difference(desired).abs();
      return aDiff < bDiff ? a : b;
    });
  }

  /// Calculate confidence score for optimal wake time
  /// 
  /// Higher confidence when:
  /// - Point is in light sleep (not transitioning)
  /// - Surrounded by consistent sleep phase
  /// - Close to desired time
  double _calculateConfidence(_DataPoint optimal, List<_DataPoint> allPoints) {
    double confidence = 60.0; // Base confidence
    
    // Bonus for light sleep
    if (_isLightSleep(optimal.activityIndex)) {
      confidence += 20.0;
    }
    
    // Check surrounding points for consistency
    final optimalIndex = allPoints.indexOf(optimal);
    if (optimalIndex > 0 && optimalIndex < allPoints.length - 1) {
      final prev = allPoints[optimalIndex - 1];
      final next = allPoints[optimalIndex + 1];
      
      if (_isLightSleep(prev.activityIndex) && _isLightSleep(next.activityIndex)) {
        confidence += 15.0; // Stable light sleep period
      }
    }
    
    // Bonus for being close to desired time
    // (closer = more convenient, assuming equal sleep quality)
    final indexFromEnd = allPoints.length - optimalIndex - 1;
    final proximityBonus = (indexFromEnd / allPoints.length) * 5.0;
    confidence += proximityBonus;
    
    return confidence.clamp(0.0, 100.0);
  }

  /// Check if activity index indicates light sleep
  bool _isLightSleep(int index) {
    return index >= 1 && index <= 20;
  }

  /// Check if activity index indicates deep sleep
  bool _isDeepSleep(int index) {
    return index == 0;
  }

  /// Check if activity index indicates awake
  bool _isAwake(int index) {
    return index > 20;
  }

  /// Dispose service
  void dispose() {
    _stopMonitoring();
    _activeAlarm = null;
    onOptimalWakeTimeFound = null;
    onAlarmTrigger = null;
  }
}

/// Internal data point for analysis
class _DataPoint {
  final DateTime timestamp;
  final int activityIndex;

  _DataPoint({
    required this.timestamp,
    required this.activityIndex,
  });
}
