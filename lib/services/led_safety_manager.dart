// ignore_for_file: unnecessary_brace_in_string_interps

import 'package:flutter/foundation.dart';
import 'dart:async';

/// LED Safety and Performance Manager for SpO2 measurements
class LedSafetyManager {
  static const Duration _maxContinuousLedTime = Duration(minutes: 5);
  static const Duration _ledCooldownTime = Duration(minutes: 2);
  static const Duration _batteryCheckInterval = Duration(seconds: 30);
  static const int _minBatteryLevel = 20;
  
  Timer? _safetyTimer;
  Timer? _batteryCheckTimer;
  DateTime? _ledStartTime;
  bool _isLedActive = false;
  bool _isInCooldown = false;
  int _currentBatteryLevel = 100;
  
  final void Function(String) _onWarning;
  final void Function() _onEmergencyStop;
  final Future<int> Function() _getBatteryLevel;
  
  LedSafetyManager({
    required void Function(String) onWarning,
    required void Function() onEmergencyStop,
    required Future<int> Function() getBatteryLevel,
  }) : _onWarning = onWarning, 
       _onEmergencyStop = onEmergencyStop,
       _getBatteryLevel = getBatteryLevel;

  /// Start LED safety monitoring
  Future<bool> startLedSafety() async {
    if (_isInCooldown) {
      _onWarning('LED is in cooldown period. Please wait ${_remainingCooldownTime.inSeconds} seconds.');
      return false;
    }
    
    if (_isLedActive) {
      debugPrint('⚠️ LED safety already active');
      return true;
    }
    
    // Check battery level before starting
    _currentBatteryLevel = await _getBatteryLevel();
    if (_currentBatteryLevel < _minBatteryLevel) {
      _onWarning('Battery level too low for LED operation ($_currentBatteryLevel%). Please charge device.');
      return false;
    }
    
    _isLedActive = true;
    _ledStartTime = DateTime.now();
    
    // Start safety timer
    _safetyTimer = Timer(_maxContinuousLedTime, () {
      debugPrint('🔴 LED safety timeout - forcing stop');
      _onWarning('LED automatically stopped for safety after ${_maxContinuousLedTime.inMinutes} minutes.');
      _emergencyStop();
    });
    
    // Start battery monitoring
    _batteryCheckTimer = Timer.periodic(_batteryCheckInterval, (_) => _checkBatteryLevel());
    
    debugPrint('🔴 LED safety started - max time: ${_maxContinuousLedTime.inMinutes} minutes');
    return true;
  }

  /// Stop LED and safety monitoring
  void stopLedSafety() {
    if (!_isLedActive) return;
    
    _isLedActive = false;
    _safetyTimer?.cancel();
    _batteryCheckTimer?.cancel();
    
    // Start cooldown period
    _isInCooldown = true;
    Timer(_ledCooldownTime, () {
      _isInCooldown = false;
      debugPrint('✅ LED cooldown period ended');
    });
    
    Duration usageTime = DateTime.now().difference(_ledStartTime!);
    debugPrint('🔴 LED safety stopped - used for: ${usageTime.inSeconds} seconds');
  }

  /// Emergency stop for LED
  void _emergencyStop() {
    _isLedActive = false;
    _safetyTimer?.cancel();
    _batteryCheckTimer?.cancel();
    _onEmergencyStop();
    
    // Extended cooldown after emergency stop
    _isInCooldown = true;
    Timer(_ledCooldownTime * 2, () {
      _isInCooldown = false;
      debugPrint('✅ Extended LED cooldown period ended');
    });
  }

  /// Check battery level during LED operation
  Future<void> _checkBatteryLevel() async {
    if (!_isLedActive) return;
    
    try {
      _currentBatteryLevel = await _getBatteryLevel();
      
      if (_currentBatteryLevel < _minBatteryLevel) {
        _onWarning('Battery level critical (${_currentBatteryLevel}%). Stopping LED operation.');
        _emergencyStop();
      } else if (_currentBatteryLevel < _minBatteryLevel + 10) {
        _onWarning('Battery level low (${_currentBatteryLevel}%). LED operation may stop soon.');
      }
    } catch (e) {
      debugPrint('❌ Failed to check battery level: $e');
    }
  }

  /// Get remaining LED time
  Duration get remainingLedTime {
    if (!_isLedActive || _ledStartTime == null) return Duration.zero;
    
    Duration elapsed = DateTime.now().difference(_ledStartTime!);
    Duration remaining = _maxContinuousLedTime - elapsed;
    
    return remaining.isNegative ? Duration.zero : remaining;
  }

  /// Get remaining cooldown time
  Duration get _remainingCooldownTime {
    if (!_isInCooldown || _ledStartTime == null) return Duration.zero;
    
    Duration elapsed = DateTime.now().difference(_ledStartTime!);
    Duration remaining = _ledCooldownTime - elapsed;
    
    return remaining.isNegative ? Duration.zero : remaining;
  }

  /// Check if LED can be started
  bool get canStartLed => !_isLedActive && !_isInCooldown && _currentBatteryLevel >= _minBatteryLevel;

  /// Get current LED status
  LedStatus get status {
    if (_isLedActive) {
      return LedStatus.active;
    } else if (_isInCooldown) {
      return LedStatus.cooldown;
    } else {
      return LedStatus.ready;
    }
  }

  /// Get status information
  LedStatusInfo get statusInfo {
    return LedStatusInfo(
      status: status,
      isActive: _isLedActive,
      isInCooldown: _isInCooldown,
      remainingTime: remainingLedTime,
      cooldownTime: _remainingCooldownTime,
      batteryLevel: _currentBatteryLevel,
      canStart: canStartLed,
    );
  }

  /// Dispose resources
  void dispose() {
    _safetyTimer?.cancel();
    _batteryCheckTimer?.cancel();
  }
}

/// LED status enumeration
enum LedStatus {
  ready,
  active,
  cooldown,
}

/// LED status information
class LedStatusInfo {
  final LedStatus status;
  final bool isActive;
  final bool isInCooldown;
  final Duration remainingTime;
  final Duration cooldownTime;
  final int batteryLevel;
  final bool canStart;

  LedStatusInfo({
    required this.status,
    required this.isActive,
    required this.isInCooldown,
    required this.remainingTime,
    required this.cooldownTime,
    required this.batteryLevel,
    required this.canStart,
  });

  @override
  String toString() {
    return 'LedStatusInfo(status: $status, active: $isActive, cooldown: $isInCooldown, '
           'remaining: ${remainingTime.inSeconds}s, battery: $batteryLevel%)';
  }
}
