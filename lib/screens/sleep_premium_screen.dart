/// Premium Sleep Tracking Dashboard
/// 
/// Main screen showcasing all premium sleep features:
/// - Sleep Score (Whoop/Oura style)
/// - Readiness Score
/// - Sleep Trends Chart
/// - Smart Alarm Configuration
/// - Recent Sleep History
library;

import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../models/sleep_score.dart';
import '../services/sleep_history_manager.dart';
import '../services/readiness_calculator.dart';
import '../widgets/sleep_score_dashboard.dart';
import '../widgets/readiness_dashboard.dart';
import '../widgets/sleep_trends_chart.dart';
import 'sleep_trends_screen.dart';
import 'alarm_config_screen.dart';

class SleepPremiumScreen extends StatefulWidget {
  final ChileafExtendedService chileafService;

  const SleepPremiumScreen({
    super.key,
    required this.chileafService,
  });

  @override
  State<SleepPremiumScreen> createState() => _SleepPremiumScreenState();
}

class _SleepPremiumScreenState extends State<SleepPremiumScreen> with SingleTickerProviderStateMixin {
  final _historyManager = SleepHistoryManager();
  final _readinessCalculator = ReadinessCalculator();
  
  late TabController _tabController;
  
  SleepScore? _latestScore;
  ReadinessScore? _readinessScore;
  List<SleepScore> _recentScores = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 3, vsync: this);
    _loadSleepData();
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  Future<void> _loadSleepData() async {
    setState(() => _isLoading = true);

    try {
      // Load recent sleep scores from history
      final recentScores = await _historyManager.getRecentScores(7);
      
      if (recentScores.isNotEmpty) {
        _latestScore = recentScores.first;
        _recentScores = recentScores;
        
        // Calculate readiness score
        _readinessScore = _readinessCalculator.calculateReadiness(
          sleepScore: _latestScore,
          hrvData: null, // TODO: Add HRV data when available
        );
      }
    } catch (e) {
      debugPrint('Error loading sleep data: $e');
    }

    setState(() => _isLoading = false);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: CustomScrollView(
        slivers: [
          _buildAppBar(),
          SliverToBoxAdapter(
            child: _isLoading
                ? const Center(
                    child: Padding(
                      padding: EdgeInsets.all(40),
                      child: CircularProgressIndicator(),
                    ),
                  )
                : _latestScore == null
                    ? _buildEmptyState()
                    : _buildContent(),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => const AlarmConfigScreen(),
            ),
          );
        },
        icon: const Icon(Icons.alarm),
        label: const Text('Smart Alarm'),
        backgroundColor: Colors.deepPurple,
      ),
    );
  }

  Widget _buildAppBar() {
    return SliverAppBar(
      expandedHeight: 200,
      floating: false,
      pinned: true,
      backgroundColor: Colors.deepPurple,
      flexibleSpace: FlexibleSpaceBar(
        title: const Text(
          'Sleep Analytics',
          style: TextStyle(
            fontWeight: FontWeight.bold,
            shadows: [
              Shadow(
                offset: Offset(0, 1),
                blurRadius: 3,
                color: Colors.black26,
              ),
            ],
          ),
        ),
        background: Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [
                Colors.deepPurple.shade400,
                Colors.deepPurple.shade700,
              ],
            ),
          ),
          child: Stack(
            children: [
              Positioned(
                top: 60,
                right: -30,
                child: Icon(
                  Icons.nightlight_round,
                  size: 150,
                  color: Colors.white.withOpacity(0.1),
                ),
              ),
              Positioned(
                bottom: 20,
                left: 20,
                child: Icon(
                  Icons.bedtime,
                  size: 100,
                  color: Colors.white.withOpacity(0.1),
                ),
              ),
            ],
          ),
        ),
      ),
      actions: [
        IconButton(
          icon: const Icon(Icons.refresh),
          onPressed: _loadSleepData,
        ),
        IconButton(
          icon: const Icon(Icons.show_chart),
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => const SleepTrendsScreen(),
              ),
            );
          },
        ),
      ],
      bottom: TabBar(
        controller: _tabController,
        indicatorColor: Colors.white,
        tabs: const [
          Tab(text: 'Today', icon: Icon(Icons.today, size: 20)),
          Tab(text: 'Trends', icon: Icon(Icons.timeline, size: 20)),
          Tab(text: 'Readiness', icon: Icon(Icons.fitness_center, size: 20)),
        ],
      ),
    );
  }

  Widget _buildContent() {
    return TabBarView(
      controller: _tabController,
      children: [
        _buildTodayTab(),
        _buildTrendsTab(),
        _buildReadinessTab(),
      ],
    );
  }

  Widget _buildTodayTab() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Hero Score Card
          SleepScoreDashboard(
            preCalculatedScore: _latestScore,
          ),
          
          const SizedBox(height: 24),
          
          // Quick Stats Row
          _buildQuickStats(),
          
          const SizedBox(height: 24),
          
          // Sleep Phases Breakdown
          _buildSleepPhasesCard(),
          
          const SizedBox(height: 24),
          
          // Insights & Recommendations
          _buildInsightsCard(),
          
          const SizedBox(height: 80), // Space for FAB
        ],
      ),
    );
  }

  Widget _buildTrendsTab() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Last 7 Nights',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 16),
          
          if (_recentScores.length >= 3)
            Card(
              elevation: 4,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(16),
              ),
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: SleepTrendsChart(
                  scores: _recentScores,
                  period: SleepTrendPeriod.week,
                ),
              ),
            )
          else
            _buildNotEnoughDataCard(),
          
          const SizedBox(height: 24),
          
          // Average Stats
          if (_recentScores.length >= 3) _buildAverageStatsCard(),
          
          const SizedBox(height: 16),
          
          // View Full Trends Button
          ElevatedButton.icon(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const SleepTrendsScreen(),
                ),
              );
            },
            icon: const Icon(Icons.arrow_forward),
            label: const Text('View Detailed Trends'),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.deepPurple,
              foregroundColor: Colors.white,
              padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildReadinessTab() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [
          ReadinessDashboard(
            readinessScore: _readinessScore,
          ),
          
          const SizedBox(height: 24),
          
          _buildReadinessExplanationCard(),
        ],
      ),
    );
  }

  Widget _buildQuickStats() {
    final score = _latestScore!;
    
    return Row(
      children: [
        Expanded(
          child: _buildStatCard(
            '💤',
            '${score.totalSleepTime.inHours}h ${score.totalSleepTime.inMinutes % 60}m',
            'Total Sleep',
            Colors.blue,
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: _buildStatCard(
            '⚡',
            '${score.sleepEfficiency.toStringAsFixed(0)}%',
            'Efficiency',
            Colors.green,
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: _buildStatCard(
            '🌊',
            '${score.deepSleepMinutes}m',
            'Deep Sleep',
            Colors.purple,
          ),
        ),
      ],
    );
  }

  Widget _buildStatCard(String emoji, String value, String label, Color color) {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Container(
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(12),
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [Colors.white, color.withOpacity(0.05)],
          ),
        ),
        child: Column(
          children: [
            Text(
              emoji,
              style: const TextStyle(fontSize: 32),
            ),
            const SizedBox(height: 8),
            Text(
              value,
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              label,
              style: TextStyle(
                fontSize: 11,
                color: Colors.grey.shade600,
              ),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSleepPhasesCard() {
    final score = _latestScore!;
    
    // Calculate phase percentages
    final totalMinutes = score.totalSleepTime.inMinutes;
    final deepMinutes = score.deepSleepMinutes;
    final lightMinutes = totalMinutes - deepMinutes; // Light = Total - Deep
    final deepPercent = ((deepMinutes / totalMinutes * 100).round());
    final lightPercent = ((lightMinutes / totalMinutes * 100).round());
    final awakePercent = 100 - deepPercent - lightPercent;
    
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Sleep Phases',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            
            _buildPhaseBar(deepPercent, lightPercent, awakePercent),
            
            const SizedBox(height: 16),
            
            _buildPhaseLegend('🌊 Deep', deepMinutes, deepPercent, Colors.indigo),
            const SizedBox(height: 8),
            _buildPhaseLegend('😴 Light', lightMinutes, lightPercent, Colors.blue.shade300),
            const SizedBox(height: 8),
            _buildPhaseLegend('👀 Awake', totalMinutes - deepMinutes - lightMinutes, awakePercent, Colors.orange),
          ],
        ),
      ),
    );
  }

  Widget _buildPhaseBar(int deep, int light, int awake) {
    return Container(
      height: 40,
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
          children: [
            if (deep > 0)
              Flexible(
                flex: deep,
                child: Container(color: Colors.indigo),
              ),
            if (light > 0)
              Flexible(
                flex: light,
                child: Container(color: Colors.blue.shade300),
              ),
            if (awake > 0)
              Flexible(
                flex: awake,
                child: Container(color: Colors.orange),
              ),
          ],
        ),
      ),
    );
  }

  Widget _buildPhaseLegend(String label, int minutes, int percent, Color color) {
    return Row(
      children: [
        Container(
          width: 16,
          height: 16,
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: Text(
            label,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
        Text(
          '${minutes}m',
          style: TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.bold,
            color: color,
          ),
        ),
        const SizedBox(width: 8),
        Text(
          '($percent%)',
          style: TextStyle(
            fontSize: 12,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }

  Widget _buildInsightsCard() {
    final insights = _latestScore!.insights;
    
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Icon(Icons.lightbulb, color: Colors.amber, size: 24),
                const SizedBox(width: 8),
                const Text(
                  'Insights & Tips',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            
            ...insights.map((insight) => Padding(
              padding: const EdgeInsets.only(bottom: 12),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text('💡', style: TextStyle(fontSize: 16)),
                  const SizedBox(width: 12),
                  Expanded(
                    child: Text(
                      insight.message,
                      style: const TextStyle(fontSize: 14, height: 1.5),
                    ),
                  ),
                ],
              ),
            )),
          ],
        ),
      ),
    );
  }

  Widget _buildAverageStatsCard() {
    final avgScore = _recentScores.map((s) => s.totalScore).reduce((a, b) => a + b) / _recentScores.length;
    final avgDuration = _recentScores.map((s) => s.totalSleepTime.inMinutes).reduce((a, b) => a + b) / _recentScores.length;
    final avgEfficiency = _recentScores.map((s) => s.sleepEfficiency).reduce((a, b) => a + b) / _recentScores.length;
    
    return Card(
      elevation: 4,
      color: Colors.deepPurple.shade50,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            const Text(
              '7-Day Averages',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildAvgStat('Score', avgScore.toStringAsFixed(0)),
                _buildAvgStat('Duration', '${(avgDuration / 60).toStringAsFixed(1)}h'),
                _buildAvgStat('Efficiency', '${avgEfficiency.toStringAsFixed(0)}%'),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildAvgStat(String label, String value) {
    return Column(
      children: [
        Text(
          value,
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            color: Colors.deepPurple.shade700,
          ),
        ),
        Text(
          label,
          style: TextStyle(
            fontSize: 12,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }

  Widget _buildReadinessExplanationCard() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'What is Readiness?',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 12),
            const Text(
              'Your Readiness Score combines multiple metrics to indicate how prepared your body is for physical and mental exertion.',
              style: TextStyle(fontSize: 14, height: 1.5),
            ),
            const SizedBox(height: 16),
            _buildReadinessComponent('💤 Sleep Quality', '50%', 'Recent sleep duration, efficiency, and phases'),
            const SizedBox(height: 12),
            _buildReadinessComponent('❤️ HRV Recovery', '30%', 'Heart rate variability indicating recovery'),
            const SizedBox(height: 12),
            _buildReadinessComponent('💓 Resting HR', '20%', 'Morning resting heart rate compared to baseline'),
          ],
        ),
      ),
    );
  }

  Widget _buildReadinessComponent(String title, String weight, String description) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(width: 8),
        Text(
          weight,
          style: TextStyle(
            fontSize: 12,
            color: Colors.grey.shade600,
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: Text(
            description,
            style: TextStyle(
              fontSize: 12,
              color: Colors.grey.shade600,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildNotEnoughDataCard() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(40),
        child: Column(
          children: [
            Icon(
              Icons.show_chart,
              size: 64,
              color: Colors.grey.shade400,
            ),
            const SizedBox(height: 16),
            Text(
              'Not Enough Data',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: Colors.grey.shade600,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Track at least 3 nights to see trends',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey.shade500,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildEmptyState() {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(40),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.bedtime_outlined,
              size: 100,
              color: Colors.grey.shade400,
            ),
            const SizedBox(height: 24),
            Text(
              'No Sleep Data Yet',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.grey.shade600,
              ),
            ),
            const SizedBox(height: 12),
            Text(
              'Start tracking your sleep with the CL837 device to see detailed analytics here',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 16,
                color: Colors.grey.shade500,
              ),
            ),
            const SizedBox(height: 32),
            ElevatedButton.icon(
              onPressed: _loadSleepData,
              icon: const Icon(Icons.refresh),
              label: const Text('Sync Data'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
