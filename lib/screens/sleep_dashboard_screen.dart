/// Premium Sleep Dashboard Screen
/// 
/// Full-featured sleep tracking dashboard with all premium features
/// Inspired by Whoop, Oura Ring, Apple Watch interfaces
/// 
/// Features:
/// - Sleep Score with breakdown (TODAY)
/// - Readiness Score (multi-factor)
/// - 7-day trend chart
/// - Smart Alarm configuration
/// - Quick stats cards
/// - Navigation to detailed trends
library;

import 'package:flutter/material.dart';
import '../models/historical_data.dart';
import '../models/sleep_score.dart';
import '../services/sleep_score_calculator.dart';
import '../services/sleep_history_manager.dart';
import '../services/readiness_calculator.dart';
import '../widgets/sleep_score_dashboard.dart';
import '../widgets/readiness_dashboard.dart';
import '../widgets/sleep_trends_chart.dart';
import 'sleep_trends_screen.dart';
import 'alarm_config_screen.dart';

class SleepDashboardScreen extends StatefulWidget {
  final SleepData31? latestSleepData;

  const SleepDashboardScreen({
    super.key,
    this.latestSleepData,
  });

  @override
  State<SleepDashboardScreen> createState() => _SleepDashboardScreenState();
}

class _SleepDashboardScreenState extends State<SleepDashboardScreen> {
  final _historyManager = SleepHistoryManager();
  final _calculator = SleepScoreCalculator();
  final _readinessCalculator = ReadinessCalculator();

  List<SleepScore> _recentScores = [];
  ReadinessScore? _readinessScore;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  @override
  void didUpdateWidget(SleepDashboardScreen oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.latestSleepData != oldWidget.latestSleepData) {
      _loadData();
    }
  }

  Future<void> _loadData() async {
    setState(() => _isLoading = true);

    try {
      // Load recent scores (last 7 days)
      final scores = await _historyManager.getRecentScores(7);

      // Calculate readiness if we have sleep data
      ReadinessScore? readiness;
      if (widget.latestSleepData != null) {
        final latestScore = _calculator.calculateScore(widget.latestSleepData!);
        readiness = _readinessCalculator.calculateReadiness(
          sleepScore: latestScore,
          // Note: HRV data not available in this screen (use SleepPremiumScreen for full features)
        );
      }

      setState(() {
        _recentScores = scores;
        _readinessScore = readiness;
        _isLoading = false;
      });
    } catch (e) {
      debugPrint('Error loading sleep data: $e');
      setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sleep Tracking'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _loadData,
          ),
          IconButton(
            icon: const Icon(Icons.alarm),
            onPressed: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (_) => const AlarmConfigScreen()),
            ),
          ),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: _loadData,
        child: _isLoading
            ? const Center(child: CircularProgressIndicator())
            : _buildContent(),
      ),
    );
  }

  Widget _buildContent() {
    return ListView(
      padding: const EdgeInsets.all(16),
      children: [
        // Header
        _buildHeader(),
        const SizedBox(height: 24),

        // Today's Sleep Score
        _buildTodaySleepSection(),
        const SizedBox(height: 24),

        // Readiness Score
        if (_readinessScore != null) ...[
          _buildReadinessSection(),
          const SizedBox(height: 24),
        ],

        // Quick Stats
        _buildQuickStats(),
        const SizedBox(height: 24),

        // 7-Day Trend
        _buildTrendSection(),
        const SizedBox(height: 24),

        // Action Buttons
        _buildActionButtons(),
      ],
    );
  }

  Widget _buildHeader() {
    return Row(
      children: [
        const Icon(Icons.nightlight_round, size: 32, color: Color(0xFF667eea)),
        const SizedBox(width: 12),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                'Sleep Dashboard',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Text(
                _recentScores.isEmpty 
                    ? 'No sleep data yet'
                    : '${_recentScores.length} nights tracked',
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.grey[600],
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildTodaySleepSection() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Text(
                  "Today's Sleep",
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const Spacer(),
                if (_recentScores.isNotEmpty)
                  Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 12,
                      vertical: 6,
                    ),
                    decoration: BoxDecoration(
                      color: _getColorForScore(_recentScores.first.totalScore)
                          .withOpacity(0.2),
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: Text(
                      _recentScores.first.rating.displayName,
                      style: TextStyle(
                        color: _getColorForScore(_recentScores.first.totalScore),
                        fontWeight: FontWeight.bold,
                        fontSize: 12,
                      ),
                    ),
                  ),
              ],
            ),
            const SizedBox(height: 16),
            SleepScoreDashboard(
              sleepData: widget.latestSleepData,
              preCalculatedScore: _recentScores.isNotEmpty ? _recentScores.first : null,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildReadinessSection() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              children: [
                Icon(Icons.favorite, color: Colors.red, size: 24),
                SizedBox(width: 8),
                Text(
                  'Recovery & Readiness',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            ReadinessDashboard(readinessScore: _readinessScore),
          ],
        ),
      ),
    );
  }

  Widget _buildQuickStats() {
    if (_recentScores.isEmpty) return const SizedBox.shrink();

    final avgScore = _recentScores
        .map((s) => s.totalScore)
        .reduce((a, b) => a + b) / _recentScores.length;

    final avgDuration = Duration(
      minutes: _recentScores
          .map((s) => s.totalSleepTime.inMinutes)
          .reduce((a, b) => a + b) ~/ _recentScores.length,
    );

    final avgEfficiency = _recentScores
        .map((s) => s.sleepEfficiency)
        .reduce((a, b) => a + b) / _recentScores.length;

    return Row(
      children: [
        Expanded(
          child: _buildStatCard(
            'Avg Score',
            avgScore.toStringAsFixed(0),
            Icons.star,
            _getColorForScore(avgScore),
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: _buildStatCard(
            'Avg Sleep',
            '${avgDuration.inHours}h ${avgDuration.inMinutes.remainder(60)}m',
            Icons.schedule,
            Colors.blue,
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: _buildStatCard(
            'Efficiency',
            '${(avgEfficiency * 100 / 30).toStringAsFixed(0)}%',
            Icons.trending_up,
            Colors.green,
          ),
        ),
      ],
    );
  }

  Widget _buildStatCard(String label, String value, IconData icon, Color color) {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          children: [
            Icon(icon, color: color, size: 24),
            const SizedBox(height: 8),
            Text(
              value,
              style: const TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              label,
              style: TextStyle(
                fontSize: 11,
                color: Colors.grey[600],
              ),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTrendSection() {
    if (_recentScores.length < 3) {
      return Card(
        elevation: 2,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
        child: Padding(
          padding: const EdgeInsets.all(24),
          child: Center(
            child: Column(
              children: [
                Icon(Icons.show_chart, size: 48, color: Colors.grey[400]),
                const SizedBox(height: 12),
                Text(
                  'Track ${3 - _recentScores.length} more nights to see trends',
                  style: TextStyle(color: Colors.grey[600]),
                ),
              ],
            ),
          ),
        ),
      );
    }

    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Text(
                  '7-Day Trend',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const Spacer(),
                TextButton.icon(
                  onPressed: () => Navigator.push(
                    context,
                    MaterialPageRoute(builder: (_) => const SleepTrendsScreen()),
                  ),
                  icon: const Icon(Icons.arrow_forward, size: 16),
                  label: const Text('View All'),
                ),
              ],
            ),
            const SizedBox(height: 16),
            SizedBox(
              height: 200,
              child: SleepTrendsChart(
                scores: _recentScores,
                period: SleepTrendPeriod.week,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildActionButtons() {
    return Row(
      children: [
        Expanded(
          child: ElevatedButton.icon(
            onPressed: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (_) => const SleepTrendsScreen()),
            ),
            icon: const Icon(Icons.analytics),
            label: const Text('Detailed Trends'),
            style: ElevatedButton.styleFrom(
              padding: const EdgeInsets.all(16),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12),
              ),
            ),
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: ElevatedButton.icon(
            onPressed: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (_) => const AlarmConfigScreen()),
            ),
            icon: const Icon(Icons.alarm_add),
            label: const Text('Smart Alarm'),
            style: ElevatedButton.styleFrom(
              padding: const EdgeInsets.all(16),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12),
              ),
            ),
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
}
