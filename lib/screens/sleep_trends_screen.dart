/// Sleep Trends Screen
/// 
/// Full-screen view of sleep trends and statistics
/// Features:
/// - Weekly/Monthly toggle
/// - Trends chart
/// - Statistics cards
/// - Best/Worst nights
/// - Trend indicator
library;

import 'package:flutter/material.dart';
import '../models/sleep_score.dart';
import '../services/sleep_history_manager.dart';
import '../services/sleep_score_calculator.dart';
import '../widgets/sleep_trends_chart.dart';

class SleepTrendsScreen extends StatefulWidget {
  const SleepTrendsScreen({super.key});

  @override
  State<SleepTrendsScreen> createState() => _SleepTrendsScreenState();
}

class _SleepTrendsScreenState extends State<SleepTrendsScreen> {
  final _historyManager = SleepHistoryManager();
  SleepTrendPeriod _selectedPeriod = SleepTrendPeriod.week;
  SleepStatistics? _statistics;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadStatistics();
  }

  Future<void> _loadStatistics() async {
    setState(() => _isLoading = true);

    final stats = await _historyManager.getStatistics(
      days: _selectedPeriod.days,
    );

    setState(() {
      _statistics = stats;
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sleep Trends'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _loadStatistics,
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _statistics == null || !_statistics!.hasData
              ? _buildEmptyState()
              : _buildContent(),
    );
  }

  Widget _buildContent() {
    final stats = _statistics!;
    final calculator = SleepScoreCalculator();
    final trend = calculator.analyzeTrend(stats.scores);

    return RefreshIndicator(
      onRefresh: _loadStatistics,
      child: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          _buildPeriodSelector(),
          const SizedBox(height: 16),
          _buildTrendIndicator(trend),
          const SizedBox(height: 16),
          _buildStatisticsCards(stats),
          const SizedBox(height: 16),
          SleepTrendsChart(
            scores: stats.scores,
            period: _selectedPeriod,
          ),
          const SizedBox(height: 16),
          _buildBestWorstNights(stats),
        ],
      ),
    );
  }

  Widget _buildPeriodSelector() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(4),
        child: Row(
          children: [
            Expanded(
              child: _buildPeriodButton(
                SleepTrendPeriod.week,
                'Last 7 Days',
                Icons.calendar_view_week,
              ),
            ),
            Expanded(
              child: _buildPeriodButton(
                SleepTrendPeriod.month,
                'Last 30 Days',
                Icons.calendar_month,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildPeriodButton(
    SleepTrendPeriod period,
    String label,
    IconData icon,
  ) {
    final isSelected = _selectedPeriod == period;

    return InkWell(
      onTap: () {
        setState(() {
          _selectedPeriod = period;
        });
        _loadStatistics();
      },
      borderRadius: BorderRadius.circular(8),
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 12),
        decoration: BoxDecoration(
          color: isSelected ? Theme.of(context).primaryColor : Colors.transparent,
          borderRadius: BorderRadius.circular(8),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              icon,
              size: 20,
              color: isSelected ? Colors.white : Colors.grey,
            ),
            const SizedBox(width: 8),
            Text(
              label,
              style: TextStyle(
                fontSize: 14,
                fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
                color: isSelected ? Colors.white : Colors.grey,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTrendIndicator(SleepTrend trend) {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Container(
        padding: const EdgeInsets.all(20),
        decoration: BoxDecoration(
          gradient: LinearGradient(
            colors: _getTrendColors(trend),
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
          ),
          borderRadius: BorderRadius.circular(16),
        ),
        child: Row(
          children: [
            Text(
              trend.emoji,
              style: const TextStyle(fontSize: 48),
            ),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Sleep Trend',
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.white.withOpacity(0.9),
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    trend.displayName,
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    trend.description,
                    style: TextStyle(
                      fontSize: 13,
                      color: Colors.white.withOpacity(0.9),
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

  List<Color> _getTrendColors(SleepTrend trend) {
    switch (trend) {
      case SleepTrend.improving:
        return [const Color(0xFF11998e), const Color(0xFF38ef7d)];
      case SleepTrend.stable:
        return [const Color(0xFF667eea), const Color(0xFF764ba2)];
      case SleepTrend.declining:
        return [const Color(0xFFfc4a1a), const Color(0xFFf7b733)];
      case SleepTrend.insufficient:
        return [const Color(0xFF9E9E9E), const Color(0xFF757575)];
    }
  }

  Widget _buildStatisticsCards(SleepStatistics stats) {
    return Column(
      children: [
        Row(
          children: [
            Expanded(
              child: _buildStatCard(
                'Avg Score',
                stats.averageScore.toStringAsFixed(0),
                Icons.star,
                _getColorForScore(stats.averageScore),
              ),
            ),
            const SizedBox(width: 12),
            Expanded(
              child: _buildStatCard(
                'Avg Duration',
                stats.averageDurationFormatted,
                Icons.schedule,
                Colors.blue,
              ),
            ),
          ],
        ),
        const SizedBox(height: 12),
        Row(
          children: [
            Expanded(
              child: _buildStatCard(
                'Efficiency',
                '${stats.averageEfficiency.toStringAsFixed(0)}%',
                Icons.trending_up,
                Colors.green,
              ),
            ),
            const SizedBox(width: 12),
            Expanded(
              child: _buildStatCard(
                'Deep Sleep',
                '${stats.averageDeepSleep}m',
                Icons.nightlight,
                Colors.purple,
              ),
            ),
          ],
        ),
      ],
    );
  }

  Widget _buildStatCard(
    String label,
    String value,
    IconData icon,
    Color color,
  ) {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Icon(icon, color: color, size: 32),
            const SizedBox(height: 8),
            Text(
              value,
              style: const TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              label,
              style: TextStyle(
                fontSize: 12,
                color: Colors.grey[600],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildBestWorstNights(SleepStatistics stats) {
    if (stats.bestNight == null || stats.worstNight == null) {
      return const SizedBox.shrink();
    }

    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Best & Worst Nights',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            _buildNightCard(
              '🌟 Best Night',
              stats.bestNight!,
              Colors.green,
            ),
            const SizedBox(height: 12),
            _buildNightCard(
              '😕 Worst Night',
              stats.worstNight!,
              Colors.orange,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildNightCard(String title, SleepScore score, Color color) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color, width: 1.5),
      ),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: color.withOpacity(0.2),
              borderRadius: BorderRadius.circular(8),
            ),
            child: Text(
              score.totalScore.toStringAsFixed(0),
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  title,
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    color: color,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  _formatDate(score.sleepDate),
                  style: const TextStyle(fontSize: 13),
                ),
                const SizedBox(height: 4),
                Text(
                  '${score.totalSleepTime.inHours}h ${score.totalSleepTime.inMinutes.remainder(60)}m sleep',
                  style: TextStyle(fontSize: 12, color: Colors.grey[600]),
                ),
              ],
            ),
          ),
        ],
      ),
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
    const months = [
      'Jan',
      'Feb',
      'Mar',
      'Apr',
      'May',
      'Jun',
      'Jul',
      'Aug',
      'Sep',
      'Oct',
      'Nov',
      'Dec'
    ];
    return '${months[date.month - 1]} ${date.day}, ${date.year}';
  }

  Widget _buildEmptyState() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.show_chart,
            size: 80,
            color: Colors.grey[400],
          ),
          const SizedBox(height: 16),
          Text(
            'No Sleep Data Yet',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: Colors.grey[600],
            ),
          ),
          const SizedBox(height: 8),
          Text(
            'Track at least 3 nights to see trends',
            style: TextStyle(
              fontSize: 16,
              color: Colors.grey[500],
            ),
          ),
        ],
      ),
    );
  }
}
