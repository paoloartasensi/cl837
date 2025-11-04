/// Fitbit-style Sleep Score Card
/// 
/// Large visual card displaying:
/// - Sleep score with color-coded badge
/// - Total sleep time
/// - Sleep efficiency percentage
/// - Score breakdown (Duration, Efficiency, Quality, Consistency)
library;

import 'package:flutter/material.dart';
import '../models/sleep_score.dart';

class FitbitSleepScoreCard extends StatelessWidget {
  final SleepScore sleepScore;
  final bool showBreakdown;

  const FitbitSleepScoreCard({
    super.key,
    required this.sleepScore,
    this.showBreakdown = true,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 8,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Colors.white,
              _getScoreColor(sleepScore.totalScore).withOpacity(0.05),
            ],
          ),
        ),
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header with date
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  _formatDate(sleepScore.sleepDate),
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey.shade600,
                  ),
                ),
                Icon(
                  Icons.bedtime,
                  color: Colors.grey.shade400,
                  size: 20,
                ),
              ],
            ),
            
            const SizedBox(height: 16),
            
            // Main score display
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Large score circle
                _buildScoreCircle(),
                
                const SizedBox(width: 24),
                
                // Sleep stats
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Sleep Score',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w500,
                          color: Colors.black87,
                        ),
                      ),
                      const SizedBox(height: 8),
                      _buildStatRow(
                        Icons.access_time,
                        'Total Sleep',
                        '${sleepScore.totalSleepTime.inHours}h ${sleepScore.totalSleepTime.inMinutes % 60}m',
                      ),
                      const SizedBox(height: 6),
                      _buildStatRow(
                        Icons.check_circle_outline,
                        'Efficiency',
                        '${(sleepScore.sleepEfficiency * 100).toStringAsFixed(0)}%',
                      ),
                    ],
                  ),
                ),
              ],
            ),
            
            if (showBreakdown) ...[
              const SizedBox(height: 24),
              const Divider(),
              const SizedBox(height: 16),
              
              // Score breakdown
              const Text(
                'Score Breakdown',
                style: TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.bold,
                  color: Colors.black87,
                ),
              ),
              const SizedBox(height: 12),
              
              _buildBreakdownBar('Duration', sleepScore.durationScore, 35),
              const SizedBox(height: 8),
              _buildBreakdownBar('Efficiency', sleepScore.efficiencyScore, 30),
              const SizedBox(height: 8),
              _buildBreakdownBar('Quality', sleepScore.qualityScore, 25),
              const SizedBox(height: 8),
              _buildBreakdownBar('Consistency', sleepScore.consistencyScore, 10),
            ],
          ],
        ),
      ),
    );
  }

  /// Build large circular score indicator
  Widget _buildScoreCircle() {
    final score = sleepScore.totalScore.round();
    final color = _getScoreColor(sleepScore.totalScore);
    
    return Container(
      width: 100,
      height: 100,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        gradient: RadialGradient(
          colors: [
            color.withOpacity(0.2),
            color.withOpacity(0.05),
          ],
        ),
        border: Border.all(
          color: color,
          width: 4,
        ),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            score.toString(),
            style: TextStyle(
              fontSize: 36,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
          Text(
            sleepScore.rating.displayName,
            style: TextStyle(
              fontSize: 11,
              fontWeight: FontWeight.w600,
              color: color,
            ),
          ),
        ],
      ),
    );
  }

  /// Build stat row with icon
  Widget _buildStatRow(IconData icon, String label, String value) {
    return Row(
      children: [
        Icon(
          icon,
          size: 16,
          color: Colors.grey.shade600,
        ),
        const SizedBox(width: 8),
        Text(
          label,
          style: TextStyle(
            fontSize: 13,
            color: Colors.grey.shade600,
          ),
        ),
        const Spacer(),
        Text(
          value,
          style: const TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.bold,
            color: Colors.black87,
          ),
        ),
      ],
    );
  }

  /// Build score breakdown bar
  Widget _buildBreakdownBar(String label, double score, double maxScore) {
    final percentage = (score / maxScore).clamp(0.0, 1.0);
    final color = _getScoreColor((score / maxScore) * 100);
    
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              label,
              style: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w500,
                color: Colors.black87,
              ),
            ),
            Text(
              '${score.toStringAsFixed(0)}/${maxScore.toStringAsFixed(0)}',
              style: TextStyle(
                fontSize: 11,
                color: Colors.grey.shade600,
              ),
            ),
          ],
        ),
        const SizedBox(height: 4),
        ClipRRect(
          borderRadius: BorderRadius.circular(4),
          child: LinearProgressIndicator(
            value: percentage,
            minHeight: 8,
            backgroundColor: Colors.grey.shade200,
            valueColor: AlwaysStoppedAnimation<Color>(color),
          ),
        ),
      ],
    );
  }

  /// Get color based on score
  Color _getScoreColor(double score) {
    if (score >= 90) return const Color(0xFF4CAF50); // Green
    if (score >= 80) return const Color(0xFF8BC34A); // Light Green
    if (score >= 70) return const Color(0xFFFFA726); // Orange
    if (score >= 60) return const Color(0xFFFF7043); // Deep Orange
    return const Color(0xFFF44336); // Red
  }

  /// Format date for display
  String _formatDate(DateTime date) {
    final now = DateTime.now();
    final today = DateTime(now.year, now.month, now.day);
    final yesterday = today.subtract(const Duration(days: 1));
    final dateOnly = DateTime(date.year, date.month, date.day);
    
    if (dateOnly == today) {
      return 'Last Night';
    } else if (dateOnly == yesterday) {
      return 'Yesterday';
    } else {
      final months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
      return '${months[date.month - 1]} ${date.day}';
    }
  }
}
