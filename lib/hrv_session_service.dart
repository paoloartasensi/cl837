import 'dart:async';
import 'dart:math';
import 'package:flutter/foundation.dart';
import 'models/heart_rate_data.dart';

enum HRVSessionState {
  idle,
  recording,
  paused,
  completed,
  error
}

class HRVSessionData {
  final DateTime startTime;
  final DateTime? endTime;
  final Duration duration;
  final int totalRRIntervals;
  final List<double> rrIntervals;
  final HRVMetrics? metrics;
  final HRVSessionState state;
  final double? quality; // Signal quality percentage (0-100)

  const HRVSessionData({
    required this.startTime,
    this.endTime,
    required this.duration,
    required this.totalRRIntervals,
    required this.rrIntervals,
    this.metrics,
    required this.state,
    this.quality,
  });

  HRVSessionData copyWith({
    DateTime? startTime,
    DateTime? endTime,
    Duration? duration,
    int? totalRRIntervals,
    List<double>? rrIntervals,
    HRVMetrics? metrics,
    HRVSessionState? state,
    double? quality,
  }) {
    return HRVSessionData(
      startTime: startTime ?? this.startTime,
      endTime: endTime ?? this.endTime,
      duration: duration ?? this.duration,
      totalRRIntervals: totalRRIntervals ?? this.totalRRIntervals,
      rrIntervals: rrIntervals ?? this.rrIntervals,
      metrics: metrics ?? this.metrics,
      state: state ?? this.state,
      quality: quality ?? this.quality,
    );
  }
}

class HRVMetrics {
  final double rmssd; // Root Mean Square of Successive Differences (ms)
  final double sdnn;  // Standard Deviation of NN intervals (ms)
  final double pnn50; // Percentage of NN intervals > 50ms different from previous (%)
  final double meanRR; // Mean RR interval (ms)
  final double meanHR; // Mean heart rate (bpm)
  final int totalBeats;
  final double hrv4t; // HRV Triangular Index (if enough data)

  const HRVMetrics({
    required this.rmssd,
    required this.sdnn,
    required this.pnn50,
    required this.meanRR,
    required this.meanHR,
    required this.totalBeats,
    required this.hrv4t,
  });
}

class HRVSessionService {
  static const Duration _minSessionDuration = Duration(minutes: 1);
  static const Duration _maxSessionDuration = Duration(minutes: 5);
  static const int _minRRIntervalsForMetrics = 50; // Minimum for reliable HRV calculation
  static const double _rrIntervalMinMs = 300.0;  // 200 BPM max
  static const double _rrIntervalMaxMs = 2000.0; // 30 BPM min

  HRVSessionData? _currentSession;
  final List<double> _sessionRRIntervals = [];
  DateTime? _sessionStartTime;
  Timer? _sessionTimer;
  StreamSubscription? _heartRateSubscription;
  
  final StreamController<HRVSessionData> _sessionController = StreamController<HRVSessionData>.broadcast();
  
  Stream<HRVSessionData> get sessionStream => _sessionController.stream;
  HRVSessionData? get currentSession => _currentSession;
  
  bool get isRecording => _currentSession?.state == HRVSessionState.recording;
  bool get canStartSession => _currentSession?.state != HRVSessionState.recording;

  /// Start a new HRV measurement session
  Future<void> startSession(Stream<HeartRateData?> heartRateStream) async {
    if (!canStartSession) {
      throw Exception('Cannot start session: another session is already recording');
    }

    debugPrint('Starting HRV session...');
    
    _sessionStartTime = DateTime.now();
    _sessionRRIntervals.clear();
    
    _currentSession = HRVSessionData(
      startTime: _sessionStartTime!,
      duration: Duration.zero,
      totalRRIntervals: 0,
      rrIntervals: [],
      state: HRVSessionState.recording,
    );
    
    _sessionController.add(_currentSession!);

    // Listen to heart rate data
    _heartRateSubscription = heartRateStream.listen(
      (heartRate) => _processHeartRateForSession(heartRate),
      onError: (error) {
        debugPrint('HRV session heart rate stream error: $error');
        _updateSessionState(HRVSessionState.error);
      },
    );

    // Start session timer
    _sessionTimer = Timer.periodic(const Duration(seconds: 1), (_) {
      _updateSessionProgress();
    });

    debugPrint('HRV session started successfully');
  }

  /// Stop the current session
  Future<void> stopSession() async {
    if (_currentSession?.state != HRVSessionState.recording) {
      return;
    }

    debugPrint('Stopping HRV session...');
    
    await _heartRateSubscription?.cancel();
    _heartRateSubscription = null;
    
    _sessionTimer?.cancel();
    _sessionTimer = null;

    final endTime = DateTime.now();
    final duration = endTime.difference(_sessionStartTime!);

    // Calculate final metrics
    final metrics = _calculateHRVMetrics(_sessionRRIntervals);
    final quality = _calculateSessionQuality(_sessionRRIntervals, duration);

    _currentSession = _currentSession!.copyWith(
      endTime: endTime,
      duration: duration,
      totalRRIntervals: _sessionRRIntervals.length,
      rrIntervals: List.from(_sessionRRIntervals), // Copy the intervals
      metrics: metrics,
      state: HRVSessionState.completed,
      quality: quality,
    );

    _sessionController.add(_currentSession!);
    debugPrint('HRV session completed - Duration: ${duration.inSeconds}s, RR intervals: ${_sessionRRIntervals.length}');
  }

  /// Pause the current session
  void pauseSession() {
    if (_currentSession?.state == HRVSessionState.recording) {
      _updateSessionState(HRVSessionState.paused);
      _sessionTimer?.cancel();
      debugPrint('HRV session paused');
    }
  }

  /// Resume a paused session
  void resumeSession() {
    if (_currentSession?.state == HRVSessionState.paused) {
      _updateSessionState(HRVSessionState.recording);
      _sessionTimer = Timer.periodic(const Duration(seconds: 1), (_) {
        _updateSessionProgress();
      });
      debugPrint('HRV session resumed');
    }
  }

  void _processHeartRateForSession(HeartRateData? heartRateData) {
    if (heartRateData?.rrIntervals == null || 
        _currentSession?.state != HRVSessionState.recording) {
      return;
    }

    final newRRIntervals = heartRateData!.rrIntervals!;
    for (final rrInterval in newRRIntervals) {
      // Filter out obviously bad RR intervals
      if (rrInterval >= _rrIntervalMinMs && rrInterval <= _rrIntervalMaxMs) {
        _sessionRRIntervals.add(rrInterval);
      }
    }

    // Update session with current progress (but don't calculate full metrics yet for performance)
    if (_sessionRRIntervals.isNotEmpty) {
      final duration = DateTime.now().difference(_sessionStartTime!);
      
      _currentSession = _currentSession!.copyWith(
        duration: duration,
        totalRRIntervals: _sessionRRIntervals.length,
        // Only send last few intervals to avoid large data transfers
        rrIntervals: _sessionRRIntervals.length > 10 
            ? _sessionRRIntervals.sublist(_sessionRRIntervals.length - 10)
            : List.from(_sessionRRIntervals),
      );
      
      // Don't broadcast every single update to avoid UI spam
      if (_sessionRRIntervals.length % 5 == 0) {
        _sessionController.add(_currentSession!);
      }
    }
  }

  void _updateSessionProgress() {
    if (_currentSession == null || _sessionStartTime == null) return;

    final duration = DateTime.now().difference(_sessionStartTime!);
    
    _currentSession = _currentSession!.copyWith(
      duration: duration,
      totalRRIntervals: _sessionRRIntervals.length,
    );

    _sessionController.add(_currentSession!);

    // Auto-stop after max duration
    if (duration >= _maxSessionDuration) {
      stopSession();
    }
  }

  void _updateSessionState(HRVSessionState newState) {
    if (_currentSession != null) {
      _currentSession = _currentSession!.copyWith(state: newState);
      _sessionController.add(_currentSession!);
    }
  }

  /// Calculate HRV metrics from RR intervals
  HRVMetrics? _calculateHRVMetrics(List<double> rrIntervals) {
    if (rrIntervals.length < _minRRIntervalsForMetrics) {
      return null;
    }

    try {
      // Calculate basic statistics
      final meanRR = rrIntervals.reduce((a, b) => a + b) / rrIntervals.length;
      final meanHR = 60000.0 / meanRR; // Convert from RR interval to BPM

      // Calculate SDNN (Standard Deviation of NN intervals)
      final variance = rrIntervals
          .map((rr) => pow(rr - meanRR, 2))
          .reduce((a, b) => a + b) / rrIntervals.length;
      final sdnn = sqrt(variance);

      // Calculate RMSSD (Root Mean Square of Successive Differences)
      final successiveDiffs = <double>[];
      for (int i = 1; i < rrIntervals.length; i++) {
        successiveDiffs.add(rrIntervals[i] - rrIntervals[i - 1]);
      }
      
      if (successiveDiffs.isEmpty) return null;
      
      final rmssdVariance = successiveDiffs
          .map((diff) => pow(diff, 2))
          .reduce((a, b) => a + b) / successiveDiffs.length;
      final rmssd = sqrt(rmssdVariance);

      // Calculate pNN50 (percentage of successive RR intervals that differ by more than 50ms)
      final nn50Count = successiveDiffs
          .where((diff) => diff.abs() > 50.0)
          .length;
      final pnn50 = (nn50Count / successiveDiffs.length) * 100.0;

      // Simple HRV Triangular Index approximation
      final hrv4t = rrIntervals.length / (2.0 * _calculateMode(rrIntervals));

      return HRVMetrics(
        rmssd: rmssd,
        sdnn: sdnn,
        pnn50: pnn50,
        meanRR: meanRR,
        meanHR: meanHR,
        totalBeats: rrIntervals.length,
        hrv4t: hrv4t,
      );
    } catch (e) {
      debugPrint('Error calculating HRV metrics: $e');
      return null;
    }
  }

  /// Calculate session quality based on data completeness and stability
  double _calculateSessionQuality(List<double> rrIntervals, Duration duration) {
    if (rrIntervals.length < _minRRIntervalsForMetrics) {
      return 0.0;
    }

    try {
      // Factor 1: Duration completeness (want at least 1 minute)
      final durationScore = min(1.0, duration.inSeconds / _minSessionDuration.inSeconds);
      
      // Factor 2: Data density (RR intervals per second)
      const expectedIntervalsPerSecond = 1.0; // Roughly 60 BPM = 1 RR interval per second
      final actualIntervalsPerSecond = rrIntervals.length / duration.inSeconds;
      final densityScore = min(1.0, actualIntervalsPerSecond / expectedIntervalsPerSecond);
      
      // Factor 3: Signal stability (coefficient of variation)
      final meanRR = rrIntervals.reduce((a, b) => a + b) / rrIntervals.length;
      final stdDev = sqrt(rrIntervals.map((rr) => pow(rr - meanRR, 2)).reduce((a, b) => a + b) / rrIntervals.length);
      final cv = stdDev / meanRR;
      final stabilityScore = max(0.0, 1.0 - (cv * 2.0)); // Lower CV = higher stability
      
      // Combine factors
      final quality = (durationScore * 0.4 + densityScore * 0.4 + stabilityScore * 0.2) * 100.0;
      return min(100.0, max(0.0, quality));
    } catch (e) {
      debugPrint('Error calculating session quality: $e');
      return 0.0;
    }
  }

  /// Simple mode calculation for HRV triangular index
  double _calculateMode(List<double> values) {
    if (values.isEmpty) return 0.0;
    
    // Round to nearest 10ms for binning
    final bins = <int, int>{};
    for (final value in values) {
      final bin = (value / 10.0).round();
      bins[bin] = (bins[bin] ?? 0) + 1;
    }
    
    var maxCount = 0;
    var modeValue = 0;
    bins.forEach((bin, count) {
      if (count > maxCount) {
        maxCount = count;
        modeValue = bin;
      }
    });
    
    return modeValue * 10.0;
  }

  /// Clear current session
  void clearSession() {
    _sessionTimer?.cancel();
    _sessionTimer = null;
    _heartRateSubscription?.cancel();
    _heartRateSubscription = null;
    _sessionRRIntervals.clear();
    _sessionStartTime = null;
    _currentSession = null;
  }

  void dispose() {
    clearSession();
    _sessionController.close();
  }
}
