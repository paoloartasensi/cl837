/// Sleep Score Dashboard Widget
/// 
/// Beautiful, premium-quality sleep score display inspired by Whoop, Oura Ring
/// Features:
/// - Large circular score indicator (0-100)
/// - Gradient background based on score quality
/// - Breakdown of score components
/// - Actionable insights
/// - Emoji feedback
library;

import 'package:flutter/material.dart';
import '../models/sleep_score.dart';
import '../models/historical_data.dart';
import '../services/sleep_score_calculator.dart';

class SleepScoreDashboard extends StatelessWidget {
  final SleepData31? sleepData;
  final SleepScore? preCalculatedScore;

  const SleepScoreDashboard({
    super.key,
    this.sleepData,
    this.preCalculatedScore,
  });

  @override
  Widget build(BuildContext context) {
    if (sleepData == null && preCalculatedScore == null) {
      return _buildEmptyState();
    }

    final score = preCalculatedScore ?? 
        SleepScoreCalculator().calculateScore(sleepData!);

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
              const SizedBox(height: 24),
              _buildScoreCircle(score),
              const SizedBox(height: 24),
              _buildStatsRow(score),
              const SizedBox(height: 20),
              _buildComponentBreakdown(score),
              const SizedBox(height: 20),
              _buildInsights(score),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildHeader(SleepScore score) {
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
                  'Sleep Score',
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
              score.rating.description,
              style: const TextStyle(
                fontSize: 14,
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
            score.rating.displayName.toUpperCase(),
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

  Widget _buildScoreCircle(SleepScore score) {
    return Center(
      child: SizedBox(
        width: 200,
        height: 200,
        child: Stack(
          children: [
            // Background circle
            const SizedBox(
              width: 200,
              height: 200,
              child: CircularProgressIndicator(
                value: 1.0,
                strokeWidth: 16,
                backgroundColor: Colors.white12,
                valueColor: AlwaysStoppedAnimation<Color>(Colors.white12),
              ),
            ),
            // Score circle
            SizedBox(
              width: 200,
              height: 200,
              child: CircularProgressIndicator(
                value: score.totalScore / 100,
                strokeWidth: 16,
                backgroundColor: Colors.transparent,
                valueColor: const AlwaysStoppedAnimation<Color>(Colors.white),
                strokeCap: StrokeCap.round,
              ),
            ),
            // Center score
            Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    score.totalScore.toStringAsFixed(0),
                    style: const TextStyle(
                      fontSize: 64,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                      height: 1.0,
                    ),
                  ),
                  const Text(
                    'out of 100',
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.white70,
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

  Widget _buildStatsRow(SleepScore score) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        _buildStatCard(
          icon: '🛌',
          label: 'Total Sleep',
          value: _formatDuration(score.totalSleepTime),
        ),
        _buildStatCard(
          icon: '💤',
          label: 'Deep Sleep',
          value: '${score.deepSleepMinutes}m',
          subtitle: '${score.deepSleepPercentage.toStringAsFixed(0)}%',
        ),
        _buildStatCard(
          icon: '📊',
          label: 'Efficiency',
          value: '${score.sleepEfficiency.toStringAsFixed(0)}%',
        ),
      ],
    );
  }

  Widget _buildStatCard({
    required String icon,
    required String label,
    required String value,
    String? subtitle,
  }) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.white12,
        borderRadius: BorderRadius.circular(16),
      ),
      child: Column(
        children: [
          Text(
            icon,
            style: const TextStyle(fontSize: 24),
          ),
          const SizedBox(height: 4),
          Text(
            label,
            style: const TextStyle(
              fontSize: 11,
              color: Colors.white70,
            ),
          ),
          const SizedBox(height: 2),
          Text(
            value,
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),
          if (subtitle != null) ...[
            Text(
              subtitle,
              style: const TextStyle(
                fontSize: 12,
                color: Colors.white60,
              ),
            ),
          ],
        ],
      ),
    );
  }

  Widget _buildComponentBreakdown(SleepScore score) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Score Breakdown',
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
        const SizedBox(height: 12),
        _buildComponentBar(
          'Duration',
          score.durationScore,
          SleepScoreCalculator.maxDurationPoints,
          Icons.schedule,
        ),
        const SizedBox(height: 8),
        _buildComponentBar(
          'Efficiency',
          score.efficiencyScore,
          SleepScoreCalculator.maxEfficiencyPoints,
          Icons.trending_up,
        ),
        const SizedBox(height: 8),
        _buildComponentBar(
          'Quality',
          score.qualityScore,
          SleepScoreCalculator.maxQualityPoints,
          Icons.star,
        ),
        const SizedBox(height: 8),
        _buildComponentBar(
          'Consistency',
          score.consistencyScore,
          SleepScoreCalculator.maxConsistencyPoints,
          Icons.check_circle,
        ),
      ],
    );
  }

  Widget _buildComponentBar(
    String label,
    double value,
    double maxValue,
    IconData icon,
  ) {
    final percentage = value / maxValue;
    
    return Row(
      children: [
        Icon(icon, size: 16, color: Colors.white70),
        const SizedBox(width: 8),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    label,
                    style: const TextStyle(
                      fontSize: 13,
                      color: Colors.white,
                    ),
                  ),
                  Text(
                    '${value.toStringAsFixed(1)}/${maxValue.toStringAsFixed(0)}',
                    style: const TextStyle(
                      fontSize: 12,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 4),
              ClipRRect(
                borderRadius: BorderRadius.circular(4),
                child: LinearProgressIndicator(
                  value: percentage,
                  backgroundColor: Colors.white12,
                  valueColor: const AlwaysStoppedAnimation<Color>(Colors.white),
                  minHeight: 6,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildInsights(SleepScore score) {
    final insights = score.insights;
    
    if (insights.isEmpty) {
      return const SizedBox.shrink();
    }

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          '💡 Insights',
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
        const SizedBox(height: 12),
        ...insights.take(3).map((insight) => Padding(
          padding: const EdgeInsets.only(bottom: 8),
          child: _buildInsightCard(insight),
        )),
      ],
    );
  }

  Widget _buildInsightCard(SleepInsight insight) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.white12,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(
          color: _getInsightBorderColor(insight.severity),
          width: 1.5,
        ),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            insight.icon,
            style: const TextStyle(fontSize: 20),
          ),
          const SizedBox(width: 12),
          Expanded(
            child: Text(
              insight.message,
              style: const TextStyle(
                fontSize: 13,
                color: Colors.white,
                height: 1.3,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Color _getInsightBorderColor(InsightSeverity severity) {
    switch (severity) {
      case InsightSeverity.positive:
        return Colors.greenAccent;
      case InsightSeverity.neutral:
        return Colors.blueAccent;
      case InsightSeverity.warning:
        return Colors.orangeAccent;
      case InsightSeverity.critical:
        return Colors.redAccent;
    }
  }

  LinearGradient _getGradientForScore(double score) {
    if (score >= 90) {
      return const LinearGradient(
        colors: [Color(0xFF11998e), Color(0xFF38ef7d)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else if (score >= 80) {
      return const LinearGradient(
        colors: [Color(0xFF667eea), Color(0xFF764ba2)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else if (score >= 70) {
      return const LinearGradient(
        colors: [Color(0xFFf093fb), Color(0xFff5576c)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else if (score >= 60) {
      return const LinearGradient(
        colors: [Color(0xFFfa709a), Color(0xFFfee140)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    } else {
      return const LinearGradient(
        colors: [Color(0xFFfc4a1a), Color(0xFFf7b733)],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
      );
    }
  }

  String _formatDuration(Duration duration) {
    final hours = duration.inHours;
    final minutes = duration.inMinutes.remainder(60);
    return '${hours}h ${minutes}m';
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
              Icons.bedtime_outlined,
              size: 64,
              color: Colors.grey[400],
            ),
            const SizedBox(height: 16),
            Text(
              'No Sleep Data',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: Colors.grey[600],
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Request sleep data to see your score',
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
