import 'package:flutter/foundation.dart';

/// Centralized error handling for BLE operations
class BleErrorHandler {
  static const Map<String, String> _errorMessages = {
    'connection_timeout': 'Connection timeout. Please check device distance and try again.',
    'device_not_found': 'Device not found. Please ensure the device is powered on and in range.',
    'gatt_error': 'Bluetooth communication error. Please reconnect and try again.',
    'service_not_found': 'Required service not available. Please check device compatibility.',
    'characteristic_not_found': 'Device feature not available. Please check device firmware.',
    'bluetooth_disabled': 'Bluetooth is disabled. Please enable Bluetooth and try again.',
    'location_permission': 'Location permission required for Bluetooth scanning.',
    'read_failed': 'Failed to read data from device. Please check connection.',
    'write_failed': 'Failed to send data to device. Please check connection.',
    'notification_failed': 'Failed to enable notifications. Please reconnect.',
  };

  static const Map<String, String> _recoveryActions = {
    'connection_timeout': 'Move closer to device and retry connection',
    'device_not_found': 'Check device power and scan again',
    'gatt_error': 'Restart Bluetooth and reconnect',
    'service_not_found': 'Verify device model and firmware version',
    'characteristic_not_found': 'Update device firmware if available',
    'bluetooth_disabled': 'Enable Bluetooth in device settings',
    'location_permission': 'Grant location permission in app settings',
    'read_failed': 'Check connection stability and retry',
    'write_failed': 'Ensure device is connected and retry',
    'notification_failed': 'Reconnect device and try again',
  };

  /// Handle BLE exceptions and provide user-friendly messages
  static BleErrorInfo handleBleError(dynamic error) {
    debugPrint('🔴 BLE Error: $error');
    
    String errorType = _categorizeError(error);
    String message = _errorMessages[errorType] ?? 'An unexpected error occurred';
    String recovery = _recoveryActions[errorType] ?? 'Please try again';
    
    return BleErrorInfo(
      type: errorType,
      message: message,
      recoveryAction: recovery,
      originalError: error.toString(),
      timestamp: DateTime.now(),
    );
  }

  /// Categorize different types of BLE errors
  static String _categorizeError(dynamic error) {
    String errorString = error.toString().toLowerCase();
    
    if (errorString.contains('timeout')) {
      return 'connection_timeout';
    } else if (errorString.contains('not found') || errorString.contains('device')) {
      return 'device_not_found';
    } else if (errorString.contains('gatt') || errorString.contains('133')) {
      return 'gatt_error';
    } else if (errorString.contains('service')) {
      return 'service_not_found';
    } else if (errorString.contains('characteristic')) {
      return 'characteristic_not_found';
    } else if (errorString.contains('bluetooth') && errorString.contains('disabled')) {
      return 'bluetooth_disabled';
    } else if (errorString.contains('location') || errorString.contains('permission')) {
      return 'location_permission';
    } else if (errorString.contains('read')) {
      return 'read_failed';
    } else if (errorString.contains('write')) {
      return 'write_failed';
    } else if (errorString.contains('notification')) {
      return 'notification_failed';
    }
    
    return 'unknown_error';
  }

  /// Check if error is recoverable
  static bool isRecoverable(String errorType) {
    const recoverableErrors = [
      'connection_timeout',
      'device_not_found',
      'read_failed',
      'write_failed',
      'notification_failed',
    ];
    
    return recoverableErrors.contains(errorType);
  }

  /// Get retry delay based on error type
  static Duration getRetryDelay(String errorType) {
    switch (errorType) {
      case 'connection_timeout':
        return const Duration(seconds: 5);
      case 'gatt_error':
        return const Duration(seconds: 10);
      case 'device_not_found':
        return const Duration(seconds: 3);
      default:
        return const Duration(seconds: 2);
    }
  }
}

/// Information about BLE errors
class BleErrorInfo {
  final String type;
  final String message;
  final String recoveryAction;
  final String originalError;
  final DateTime timestamp;

  BleErrorInfo({
    required this.type,
    required this.message,
    required this.recoveryAction,
    required this.originalError,
    required this.timestamp,
  });

  @override
  String toString() {
    return 'BleErrorInfo(type: $type, message: $message, recovery: $recoveryAction)';
  }
}

/// Retry mechanism for BLE operations
class BleRetryManager {
  static const int maxRetries = 3;
  static const Duration baseDelay = Duration(seconds: 2);

  /// Execute operation with retry logic
  static Future<T> executeWithRetry<T>(
    Future<T> Function() operation,
    String operationName, {
    int maxRetries = 3,
    Duration? customDelay,
  }) async {
    int attempts = 0;
    
    while (attempts < maxRetries) {
      try {
        debugPrint('🔄 Attempting $operationName (attempt ${attempts + 1}/$maxRetries)');
        return await operation();
      } catch (error) {
        attempts++;
        BleErrorInfo errorInfo = BleErrorHandler.handleBleError(error);
        
        if (attempts >= maxRetries || !BleErrorHandler.isRecoverable(errorInfo.type)) {
          debugPrint('❌ $operationName failed after $attempts attempts: ${errorInfo.message}');
          rethrow;
        }
        
        Duration delay = customDelay ?? BleErrorHandler.getRetryDelay(errorInfo.type);
        debugPrint('⏳ Retrying $operationName in ${delay.inSeconds} seconds...');
        await Future.delayed(delay);
      }
    }
    
    throw Exception('Max retries exceeded for $operationName');
  }
}
