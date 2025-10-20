/// Sleep Trends Chart Widget
/// 
/// Beautiful visualization of sleep trends over 7 or 30 days
/// Uses fl_chart for professional-looking charts
/// 
/// Features:
/// - Bar chart for daily scores
/// - Line chart for trends
/// - Average line indicator
/// - Color coding by score quality
/// - Interactive tooltips

import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import '../models/sleep_score.dart';
import '../services/sleep_score_calculator.dart';

class SleepTrendsChart extends StatelessWidget {
  final List<SleepScore> scores;
  final SleepTrendPeriod period;

  const SleepTrendsChart({
    Key? key,
    required this.scores,
    this.period = SleepTrendPeriod.week,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (scores.isEmpty) {
      return _buildEmptyState();
    }

    // Sort scores by date (oldest first for chart)
    final sortedScores = List<SleepScore>.from(scores);
    sortedScores.sort((a, b) => a.sleepDate.compareTo(b.sleepDate));

    final calculator = SleepScoreCalculator();
    final avgScore = calculator.calculateAverageScore(sortedScores);
    final trend = calculator.analyzeTrend(sortedScores);

    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildHeader(avgScore, trend),
            const SizedBox(height: 24),
            SizedBox(
              height: 250,
              child: _buildBarChart(sortedScores, avgScore),
            ),
            const SizedBox(height: 16),
            _buildLegend(),
          ],
        ),
      ),
    );
  }

  Widget _buildHeader(double avgScore, SleepTrend trend) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              period.displayName,
              style: const TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              '${scores.length} nights tracked',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[600],
              ),
            ),
          ],
        ),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          decoration: BoxDecoration(
            gradient: LinearGradient(
              colors: [
                _getColorForScore(avgScore).withOpacity(0.2),
                _getColorForScore(avgScore).withOpacity(0.1),
              ],
            ),
            borderRadius: BorderRadius.circular(12),
            border: Border.all(color: _getColorForScore(avgScore), width: 2),
          ),
          child: Column(
            children: [
              Text(
                avgScore.toStringAsFixed(0),
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  color: _getColorForScore(avgScore),
                ),
              ),
              const Text(
                'AVG',
                style: TextStyle(
                  fontSize: 11,
                  fontWeight: FontWeight.w600,
                  color: Colors.grey,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildBarChart(List<SleepScore> sortedScores, double avgScore) {
    return BarChart(
      BarChartData(
        alignment: BarChartAlignment.spaceEvenly,
        maxY: 100,
        minY: 0,
        gridData: FlGridData(
          show: true,
          drawVerticalLine: false,
          horizontalInterval: 20,
          getDrawingHorizontalLine: (value) {
            if (value == avgScore) {
              // Highlight average line
              return FlLine(
                color: Colors.blue.withOpacity(0.5),
                strokeWidth: 2,
                dashArray: [8, 4],
              );
            }
            return FlLine(
              color: Colors.grey.withOpacity(0.2),
              strokeWidth: 1,
            );
          },
        ),
        titlesData: FlTitlesData(
          show: true,
          bottomTitles: AxisTitles(
            sideTitles: SideTitles(
              showTitles: true,
              getTitlesWidget: (value, meta) {
                if (value.toInt() >= sortedScores.length) {
                  return const SizedBox.shrink();
                }
                final score = sortedScores[value.toInt()];
                return Padding(
                  padding: const EdgeInsets.only(top: 8),
                  child: Text(
                    _formatDate(score.sleepDate),
                    style: const TextStyle(
                      fontSize: 10,
                      color: Colors.grey,
                    ),
                  ),
                );
              },
              reservedSize: 30,
            ),
          ),
          leftTitles: AxisTitles(
            sideTitles: SideTitles(
              showTitles: true,
              getTitlesWidget: (value, meta) {
                if (value == 0 || value == 50 || value == 100) {
                  return Text(
                    value.toInt().toString(),
                    style: TextStyle(
                      fontSize: 12,
                      color: Colors.grey[600],
                    ),
                  );
                }
                return const SizedBox.shrink();
              },
              reservedSize: 30,
            ),
          ),
          topTitles: const AxisTitles(
            sideTitles: SideTitles(showTitles: false),
          ),
          rightTitles: const AxisTitles(
            sideTitles: SideTitles(showTitles: false),
          ),
        ),
        borderData: FlBorderData(show: false),
        barGroups: _createBarGroups(sortedScores),
        barTouchData: BarTouchData(
          enabled: true,
          touchTooltipData: BarTouchTooltipData(
            getTooltipColor: (group) => Colors.black87,
            getTooltipItem: (group, groupIndex, rod, rodIndex) {
              final score = sortedScores[groupIndex];
              return BarTooltipItem(
                '${score.totalScore.toStringAsFixed(0)}/100\n',
                const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 14,
                ),
                children: [
                  TextSpan(
                    text: score.rating.displayName,
                    style: const TextStyle(
                      color: Colors.white70,
                      fontSize: 12,
                      fontWeight: FontWeight.normal,
                    ),
                  ),
                ],
              );
            },
          ),
        ),
      ),
    );
  }

  List<BarChartGroupData> _createBarGroups(List<SleepScore> sortedScores) {
    return List.generate(
      sortedScores.length,
      (index) {
        final score = sortedScores[index];
        return BarChartGroupData(
          x: index,
          barRods: [
            BarChartRodData(
              toY: score.totalScore,
              gradient: LinearGradient(
                colors: [
                  _getColorForScore(score.totalScore),
                  _getColorForScore(score.totalScore).withOpacity(0.7),
                ],
                begin: Alignment.bottomCenter,
                end: Alignment.topCenter,
              ),
              width: period == SleepTrendPeriod.week ? 24 : 12,
              borderRadius: const BorderRadius.vertical(
                top: Radius.circular(4),
              ),
            ),
          ],
        );
      },
    );
  }

  Widget _buildLegend() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        _buildLegendItem('Excellent', const Color(0xFF4CAF50)),
        _buildLegendItem('Good', const Color(0xFF8BC34A)),
        _buildLegendItem('Fair', const Color(0xFFFFC107)),
        _buildLegendItem('Poor', const Color(0xFFFF9800)),
        _buildLegendItem('Very Poor', const Color(0xFFF44336)),
      ],
    );
  }

  Widget _buildLegendItem(String label, Color color) {
    return Row(
      children: [
        Container(
          width: 12,
          height: 12,
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(2),
          ),
        ),
        const SizedBox(width: 4),
        Text(
          label,
          style: const TextStyle(
            fontSize: 10,
            color: Colors.grey,
          ),
        ),
      ],
    );
  }

  Color _getColorForScore(double score) {
    if (score >= 90) return const Color(0xFF4CAF50);
    if (score >= 80) return const Color(0xFF8BC34A);
    if (score >= 70) return const Color(0xFFFFC107);
    if (score >= 60) return const Color(0xFFFF9800);
    return const Color(0xFFF44336);
  }

  String _formatDate(DateTime date) {
    if (period == SleepTrendPeriod.week) {
      // Show weekday for weekly view
      const weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
      return weekdays[date.weekday % 7];
    } else {
      // Show day number for monthly view
      return date.day.toString();
    }
  }

  Widget _buildEmptyState() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Container(
        padding: const EdgeInsets.all(48),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.show_chart,
              size: 64,
              color: Colors.grey[400],
            ),
            const SizedBox(height: 16),
            Text(
              'No Trend Data',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: Colors.grey[600],
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Track more nights to see trends',
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

enum SleepTrendPeriod {
  week,
  month;

  String get displayName {
    switch (this) {
      case SleepTrendPeriod.week:
        return 'Last 7 Days';
      case SleepTrendPeriod.month:
        return 'Last 30 Days';
    }
  }

  int get days {
    switch (this) {
      case SleepTrendPeriod.week:
        return 7;
      case SleepTrendPeriod.month:
        return 30;
    }
  }
}
