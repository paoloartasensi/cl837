/// Readiness Dashboard Widget
/// 
/// Beautiful ring display showing readiness score
/// Similar to Oura Ring and Whoop Recovery interfaces
/// 
/// Features:
/// - Circular progress ring
/// - Component breakdown (Sleep/HRV/HR)
/// - Activity recommendation
/// - Color-coded by level

import 'package:flutter/material.dart';
import 'dart:math' as math;
import '../services/readiness_calculator.dart';

class ReadinessDashboard extends StatelessWidget {
  final ReadinessScore? readinessScore;

  const ReadinessDashboard({
    Key? key,
    this.readinessScore,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (readinessScore == null) {
      return _buildEmptyState();
    }

    final score = readinessScore!;
    final calculator = ReadinessCalculator();
    final recommendation = calculator.getRecommendation(score);

    return Card(
      elevation: 8,
      shadowColor: Colors.black26,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(24),
      ),
      child: Container(
        decoration: BoxDecoration(
          gradient: _getGradientForScore(score.totalScore),
          borderRadius: BorderRadius.circular(24),
        ),
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _buildHeader(score),
              const SizedBox(height: 32),
              _buildReadinessRing(score),
              const SizedBox(height: 32),
              _buildComponentsBreakdown(score),
              const SizedBox(height: 24),
              _buildRecommendation(recommendation),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildHeader(ReadinessScore score) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Text(
                  score.emoji,
                  style: const TextStyle(fontSize: 32),
                ),
                const SizedBox(width: 12),
                const Text(
                  'Readiness',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 4),
            Text(
              score.level.description,
              style: const TextStyle(
                fontSize: 13,
                color: Colors.white70,
              ),
            ),
          ],
        ),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          decoration: BoxDecoration(
            color: Colors.white24,
            borderRadius: BorderRadius.circular(20),
          ),
          child: Text(
            score.level.displayName.toUpperCase(),
            style: const TextStyle(
              fontSize: 12,
              fontWeight: FontWeight.bold,
              color: Colors.white,
              letterSpacing: 1.2,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildReadinessRing(ReadinessScore score) {
    return Center(
      child: SizedBox(
        width: 220,
        height: 220,
        child: Stack(
          children: [
            // Background rings for each component
            _buildComponentRing(
              value: score.sleepComponent / 100,
              color: Colors.white.withOpacity(0.3),
              strokeWidth: 6,
              radius: 110,
            ),
            _buildComponentRing(
              value: score.hrvComponent / 100,
              color: Colors.white.withOpacity(0.3),
              strokeWidth: 6,
              radius: 95,
            ),
            _buildComponentRing(
              value: score.hrComponent / 100,
              color: Colors.white.withOpacity(0.3),
              strokeWidth: 6,
              radius: 80,
            ),
            // Actual progress rings
            _buildComponentRing(
              value: score.sleepComponent / 100,
              color: Colors.white,
              strokeWidth: 10,
              radius: 110,
            ),
            _buildComponentRing(
              value: score.hrvComponent / 100,
              color: Colors.white.withOpacity(0.85),
              strokeWidth: 10,
              radius: 95,
            ),
            _buildComponentRing(
              value: score.hrComponent / 100,
              color: Colors.white.withOpacity(0.7),
              strokeWidth: 10,
              radius: 80,
            ),
            // Center score
            Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    score.totalScore.toStringAsFixed(0),
                    style: const TextStyle(
                      fontSize: 56,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                      height: 1.0,
                    ),
                  ),
                  const Text(
                    'READINESS',
                    style: TextStyle(
                      fontSize: 12,
                      fontWeight: FontWeight.w600,
                      color: Colors.white70,
                      letterSpacing: 2,
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildComponentRing({
    required double value,
    required Color color,
    required double strokeWidth,
    required double radius,
  }) {
    return Center(
      child: SizedBox(
        width: radius * 2,
        height: radius * 2,
        child: CustomPaint(
          painter: _CircularProgressPainter(
            progress: value,
            color: color,
            strokeWidth: strokeWidth,
          ),
        ),
      ),
    );
  }

  Widget _buildComponentsBreakdown(ReadinessScore score) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white.withOpacity(0.1),
        borderRadius: BorderRadius.circular(16),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Components',
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),
          const SizedBox(height: 12),
          _buildComponentRow(
            '💤 Sleep',
            score.sleepComponent,
            '50%',
            Colors.white,
          ),
          const SizedBox(height: 8),
          _buildComponentRow(
            '❤️ HRV',
            score.hrvComponent,
            '30%',
            Colors.white.withOpacity(0.85),
          ),
          const SizedBox(height: 8),
          _buildComponentRow(
            '💓 Resting HR',
            score.hrComponent,
            '20%',
            Colors.white.withOpacity(0.7),
          ),
        ],
      ),
    );
  }

  Widget _buildComponentRow(
    String label,
    double value,
    String weight,
    Color color,
  ) {
    return Row(
      children: [
        Expanded(
          flex: 2,
          child: Text(
            label,
            style: TextStyle(
              fontSize: 14,
              color: color,
            ),
          ),
        ),
        Expanded(
          flex: 3,
          child: ClipRRect(
            borderRadius: BorderRadius.circular(4),
            child: LinearProgressIndicator(
              value: value / 100,
              backgroundColor: Colors.white.withOpacity(0.2),
              valueColor: AlwaysStoppedAnimation<Color>(color),
              minHeight: 8,
            ),
          ),
        ),
        const SizedBox(width: 12),
        SizedBox(
          width: 35,
          child: Text(
            value.toStringAsFixed(0),
            style: TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
              color: color,
            ),
            textAlign: TextAlign.right,
          ),
        ),
        const SizedBox(width: 8),
        SizedBox(
          width: 30,
          child: Text(
            weight,
            style: TextStyle(
              fontSize: 11,
              color: color.withOpacity(0.7),
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildRecommendation(ActivityRecommendation recommendation) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white.withOpacity(0.15),
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: Colors.white.withOpacity(0.3), width: 1.5),
      ),
      child: Row(
        children: [
          Text(
            recommendation.icon,
            style: const TextStyle(fontSize: 40),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  recommendation.displayName,
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  recommendation.description,
                  style: const TextStyle(
                    fontSize: 13,
                    color: Colors.white70,
                    height: 1.3,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  LinearGradient _getGradientForScore(double score) {
    if (score >= 90) {
      return const LinearGradient(
        colors: [Color(0xFF11998e), Color(0xFF38ef7d)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else if (score >= 70) {
      return const LinearGradient(
        colors: [Color(0xFF667eea), Color(0xFF764ba2)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else if (score >= 50) {
      return const LinearGradient(
        colors: [Color(0xFFf093fb), Color(0xFFf5576c)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else {
      return const LinearGradient(
        colors: [Color(0xFFfa709a), Color(0xFFfee140)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    }
  }

  Widget _buildEmptyState() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(24),
      ),
      child: Container(
        padding: const EdgeInsets.all(48),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.favorite_border,
              size: 64,
              color: Colors.grey[400],
            ),
            const SizedBox(height: 16),
            Text(
              'No Readiness Data',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: Colors.grey[600],
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Track sleep and HRV to see readiness',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[500],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

/// Custom painter for circular progress ring
class _CircularProgressPainter extends CustomPainter {
  final double progress;
  final Color color;
  final double strokeWidth;

  _CircularProgressPainter({
    required this.progress,
    required this.color,
    required this.strokeWidth,
  });

  @override
  void paint(Canvas canvas, Size size) {
    final center = Offset(size.width / 2, size.height / 2);
    final radius = (size.width - strokeWidth) / 2;

    final paint = Paint()
      ..color = color
      ..strokeWidth = strokeWidth
      ..style = PaintingStyle.stroke
      ..strokeCap = StrokeCap.round;

    const startAngle = -math.pi / 2; // Start from top
    final sweepAngle = 2 * math.pi * progress;

    canvas.drawArc(
      Rect.fromCircle(center: center, radius: radius),
      startAngle,
      sweepAngle,
      false,
      paint,
    );
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
