import 'package:flutter/foundation.dart';
import 'dart:async';
import 'dart:collection';

/// Performance monitoring for BLE operations and data processing
class PerformanceMonitor {
  static const int _maxLogEntries = 1000;
  static const Duration _cleanupInterval = Duration(minutes: 5);
  
  final Queue<PerformanceEntry> _entries = Queue<PerformanceEntry>();
  Timer? _cleanupTimer;
  
  // Statistics
  int _totalOperations = 0;
  int _successfulOperations = 0;
  int _failedOperations = 0;
  double _averageResponseTime = 0.0;
  
  PerformanceMonitor() {
    _startCleanupTimer();
  }

  /// Start timing an operation
  PerformanceTracker startOperation(String operationName, {Map<String, dynamic>? metadata}) {
    return PerformanceTracker(
      operationName: operationName,
      metadata: metadata,
      onComplete: _recordOperation,
    );
  }

  /// Record a completed operation
  void _recordOperation(PerformanceEntry entry) {
    _entries.add(entry);
    _totalOperations++;
    
    if (entry.success) {
      _successfulOperations++;
    } else {
      _failedOperations++;
    }
    
    // Update average response time
    _updateAverageResponseTime();
    
    // Clean up old entries if needed
    _cleanupOldEntries();
    
    // Log performance issues
    _checkPerformanceIssues(entry);
  }

  /// Update average response time
  void _updateAverageResponseTime() {
    if (_entries.isEmpty) return;
    
    double totalTime = _entries
        .where((entry) => entry.success)
        .map((entry) => entry.duration.inMilliseconds)
        .fold(0.0, (sum, duration) => sum + duration);
    
    int successfulCount = _entries.where((entry) => entry.success).length;
    _averageResponseTime = successfulCount > 0 ? totalTime / successfulCount : 0.0;
  }

  /// Check for performance issues
  void _checkPerformanceIssues(PerformanceEntry entry) {
    // Log slow operations
    if (entry.duration.inMilliseconds > 5000) {
      debugPrint('🐌 Slow operation detected: ${entry.operationName} took ${entry.duration.inMilliseconds}ms');
    }
    
    // Log high error rate
    double errorRate = _failedOperations / _totalOperations;
    if (errorRate > 0.1 && _totalOperations > 10) {
      debugPrint('⚠️ High error rate detected: ${(errorRate * 100).toStringAsFixed(1)}%');
    }
    
    // Log memory usage if entries are growing too fast
    if (_entries.length > _maxLogEntries * 0.8) {
      debugPrint('📈 Performance monitor approaching memory limit: ${_entries.length}/$_maxLogEntries entries');
    }
  }

  /// Clean up old entries
  void _cleanupOldEntries() {
    DateTime cutoff = DateTime.now().subtract(const Duration(hours: 1));
    
    while (_entries.isNotEmpty && _entries.first.timestamp.isBefore(cutoff)) {
      _entries.removeFirst();
    }
    
    // Keep only the most recent entries if still too many
    while (_entries.length > _maxLogEntries) {
      _entries.removeFirst();
    }
  }

  /// Start cleanup timer
  void _startCleanupTimer() {
    _cleanupTimer = Timer.periodic(_cleanupInterval, (_) => _cleanupOldEntries());
  }

  /// Get performance statistics
  PerformanceStats getStats() {
    return PerformanceStats(
      totalOperations: _totalOperations,
      successfulOperations: _successfulOperations,
      failedOperations: _failedOperations,
      successRate: _totalOperations > 0 ? _successfulOperations / _totalOperations : 0.0,
      averageResponseTime: _averageResponseTime,
      entriesCount: _entries.length,
      oldestEntry: _entries.isNotEmpty ? _entries.first.timestamp : null,
      newestEntry: _entries.isNotEmpty ? _entries.last.timestamp : null,
    );
  }

  /// Get recent operations
  List<PerformanceEntry> getRecentOperations({int limit = 50}) {
    return _entries.take(limit).toList();
  }

  /// Get operations by type
  List<PerformanceEntry> getOperationsByType(String operationName) {
    return _entries.where((entry) => entry.operationName == operationName).toList();
  }

  /// Get slow operations
  List<PerformanceEntry> getSlowOperations({int thresholdMs = 1000}) {
    return _entries
        .where((entry) => entry.duration.inMilliseconds > thresholdMs)
        .toList();
  }

  /// Get error summary
  Map<String, int> getErrorSummary() {
    Map<String, int> errorCounts = {};
    
    for (var entry in _entries.where((e) => !e.success)) {
      String error = entry.error ?? 'Unknown error';
      errorCounts[error] = (errorCounts[error] ?? 0) + 1;
    }
    
    return errorCounts;
  }

  /// Reset statistics
  void reset() {
    _entries.clear();
    _totalOperations = 0;
    _successfulOperations = 0;
    _failedOperations = 0;
    _averageResponseTime = 0.0;
    debugPrint('🔄 Performance monitor reset');
  }

  /// Dispose resources
  void dispose() {
    _cleanupTimer?.cancel();
    _entries.clear();
  }
}

/// Performance tracker for individual operations
class PerformanceTracker {
  final String operationName;
  final Map<String, dynamic>? metadata;
  final DateTime _startTime;
  final void Function(PerformanceEntry) onComplete;
  
  PerformanceTracker({
    required this.operationName,
    this.metadata,
    required this.onComplete,
  }) : _startTime = DateTime.now();

  /// Complete the operation successfully
  void complete() {
    _finish(success: true);
  }

  /// Complete the operation with error
  void error(String errorMessage) {
    _finish(success: false, error: errorMessage);
  }

  /// Finish tracking
  void _finish({required bool success, String? error}) {
    Duration duration = DateTime.now().difference(_startTime);
    
    PerformanceEntry entry = PerformanceEntry(
      operationName: operationName,
      timestamp: _startTime,
      duration: duration,
      success: success,
      error: error,
      metadata: metadata,
    );
    
    onComplete(entry);
  }
}

/// Performance entry data
class PerformanceEntry {
  final String operationName;
  final DateTime timestamp;
  final Duration duration;
  final bool success;
  final String? error;
  final Map<String, dynamic>? metadata;

  PerformanceEntry({
    required this.operationName,
    required this.timestamp,
    required this.duration,
    required this.success,
    this.error,
    this.metadata,
  });

  @override
  String toString() {
    return 'PerformanceEntry(operation: $operationName, duration: ${duration.inMilliseconds}ms, '
           'success: $success, error: $error)';
  }
}

/// Performance statistics
class PerformanceStats {
  final int totalOperations;
  final int successfulOperations;
  final int failedOperations;
  final double successRate;
  final double averageResponseTime;
  final int entriesCount;
  final DateTime? oldestEntry;
  final DateTime? newestEntry;

  PerformanceStats({
    required this.totalOperations,
    required this.successfulOperations,
    required this.failedOperations,
    required this.successRate,
    required this.averageResponseTime,
    required this.entriesCount,
    this.oldestEntry,
    this.newestEntry,
  });

  @override
  String toString() {
    return 'PerformanceStats(total: $totalOperations, success: ${(successRate * 100).toStringAsFixed(1)}%, '
           'avgTime: ${averageResponseTime.toStringAsFixed(1)}ms, entries: $entriesCount)';
  }
}

/// Global performance monitor instance
final PerformanceMonitor performanceMonitor = PerformanceMonitor();
