/// Sleep Timeline Chart - Fitbit-style horizontal timeline visualization
/// 
/// Displays sleep phases over time with:
/// - Horizontal bars showing Deep, Light, REM, Awake phases
/// - Color-coded segments (Deep=Indigo, Light=Cyan, Awake=Pink/Orange)
/// - Time labels on X-axis
/// - Interactive touch support
library;

import 'package:flutter/material.dart';
import '../models/historical_data.dart';

/// Sleep phase timeline data point
class SleepTimelineSegment {
  final DateTime startTime;
  final DateTime endTime;
  final SleepPhaseType phase;
  final int durationMinutes;

  SleepTimelineSegment({
    required this.startTime,
    required this.endTime,
    required this.phase,
    required this.durationMinutes,
  });

  /// Get color for this phase
  Color get color {
    switch (phase) {
      case SleepPhaseType.deep:
        return const Color(0xFF3F51B5); // Indigo - Deep blue
      case SleepPhaseType.light:
        return const Color(0xFF64B5F6); // Light blue/cyan
      case SleepPhaseType.awake:
        return const Color(0xFFFF9E80); // Pink/orange
    }
  }

  /// Get label for this phase
  String get label {
    switch (phase) {
      case SleepPhaseType.deep:
        return 'Deep';
      case SleepPhaseType.light:
        return 'Light';
      case SleepPhaseType.awake:
        return 'Awake';
    }
  }
}

enum SleepPhaseType { deep, light, awake }

/// Fitbit-style sleep timeline chart widget
class SleepTimelineChart extends StatelessWidget {
  final SleepData31 sleepData;
  final double height;
  final bool showTimeLabels;
  final bool showPhaseLabels;

  const SleepTimelineChart({
    super.key,
    required this.sleepData,
    this.height = 120,
    this.showTimeLabels = true,
    this.showPhaseLabels = true,
  });

  /// Build segments with proper deep sleep detection
  List<SleepTimelineSegment> _buildAccurateSegments() {
    final indices = sleepData.activityIndices;
    final segments = <SleepTimelineSegment>[];
    
    if (indices.isEmpty) return segments;

    // Track consecutive zeros for deep sleep detection
    const minutesPerIndex = 5;
    var consecutiveZeros = 0;
    var currentPhase = SleepPhaseType.light;
    var segmentStart = sleepData.timestamp;
    
    for (int i = 0; i < indices.length; i++) {
      final index = indices[i];
      SleepPhaseType phase;
      
      if (index > 20) {
        phase = SleepPhaseType.awake;
        consecutiveZeros = 0;
      } else if (index == 0) {
        consecutiveZeros++;
        // Deep sleep if 3+ consecutive zeros
        phase = consecutiveZeros >= 3 ? SleepPhaseType.deep : SleepPhaseType.light;
      } else {
        phase = SleepPhaseType.light;
        consecutiveZeros = 0;
      }
      
      // Phase changed - close segment and start new one
      if (phase != currentPhase || i == indices.length - 1) {
        final segmentEnd = sleepData.timestamp.add(Duration(minutes: (i + 1) * minutesPerIndex));
        final segmentDuration = segmentEnd.difference(segmentStart).inMinutes;
        
        if (segmentDuration > 0) {
          segments.add(SleepTimelineSegment(
            startTime: segmentStart,
            endTime: segmentEnd,
            phase: currentPhase,
            durationMinutes: segmentDuration,
          ));
        }
        
        segmentStart = sleepData.timestamp.add(Duration(minutes: i * minutesPerIndex));
        currentPhase = phase;
      }
    }
    
    return segments;
  }

  @override
  Widget build(BuildContext context) {
    final segments = _buildAccurateSegments();
    
    if (segments.isEmpty) {
      return SizedBox(
        height: height,
        child: const Center(
          child: Text('No sleep data available'),
        ),
      );
    }

    return Container(
      height: height,
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          // Timeline bars
          Expanded(
            child: _buildTimelineBars(segments),
          ),
          
          if (showTimeLabels) ...[
            const SizedBox(height: 8),
            _buildTimeLabels(segments),
          ],
        ],
      ),
    );
  }

  /// Build horizontal timeline bars
  Widget _buildTimelineBars(List<SleepTimelineSegment> segments) {
    final totalMinutes = segments.fold<int>(0, (sum, seg) => sum + seg.durationMinutes);
    
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(8),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 4,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(8),
        child: Row(
          children: segments.map((segment) {
            final widthFraction = segment.durationMinutes / totalMinutes;
            
            return Expanded(
              flex: (widthFraction * 1000).round(),
              child: Tooltip(
                message: '${segment.label}\n${segment.durationMinutes}m\n${_formatTime(segment.startTime)} - ${_formatTime(segment.endTime)}',
                child: Container(
                  color: segment.color,
                  child: Center(
                    child: segment.durationMinutes >= 15
                        ? Text(
                            '${segment.durationMinutes}m',
                            style: const TextStyle(
                              color: Colors.white,
                              fontSize: 10,
                              fontWeight: FontWeight.bold,
                            ),
                          )
                        : null,
                  ),
                ),
              ),
            );
          }).toList(),
        ),
      ),
    );
  }

  /// Build time labels below timeline
  Widget _buildTimeLabels(List<SleepTimelineSegment> segments) {
    if (segments.isEmpty) return const SizedBox.shrink();
    
    final startTime = segments.first.startTime;
    final endTime = segments.last.endTime;
    
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(
          _formatTime(startTime),
          style: TextStyle(
            fontSize: 12,
            color: Colors.grey.shade600,
            fontWeight: FontWeight.w500,
          ),
        ),
        Text(
          'Sleep Timeline',
          style: TextStyle(
            fontSize: 11,
            color: Colors.grey.shade500,
          ),
        ),
        Text(
          _formatTime(endTime),
          style: TextStyle(
            fontSize: 12,
            color: Colors.grey.shade600,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  /// Format time for display
  String _formatTime(DateTime time) {
    final hour = time.hour.toString().padLeft(2, '0');
    final minute = time.minute.toString().padLeft(2, '0');
    return '$hour:$minute';
  }
}

/// Phase legend widget
class SleepPhaseLegend extends StatelessWidget {
  const SleepPhaseLegend({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        _buildLegendItem('Deep', const Color(0xFF3F51B5)),
        _buildLegendItem('Light', const Color(0xFF64B5F6)),
        _buildLegendItem('Awake', const Color(0xFFFF9E80)),
      ],
    );
  }

  Widget _buildLegendItem(String label, Color color) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Container(
          width: 16,
          height: 16,
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        const SizedBox(width: 6),
        Text(
          label,
          style: const TextStyle(
            fontSize: 12,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }
}
