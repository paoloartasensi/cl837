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
import 'dart:async';
import '../chileaf_extended_service.dart';
import '../models/historical_data.dart';
import '../models/sleep_score.dart';
import '../models/hrv_data.dart';
import '../services/sleep_history_manager.dart';
import '../services/readiness_calculator.dart';
import '../widgets/readiness_dashboard.dart';
import '../widgets/sleep_trends_chart.dart';
import '../widgets/sleep_timeline_chart.dart';
import '../widgets/fitbit_sleep_score_card.dart';
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
  StreamSubscription? _sleepDataSubscription;
  StreamSubscription? _hrvDataSubscription;
  
  SleepScore? _latestScore;
  ReadinessScore? _readinessScore;
  List<SleepScore> _recentScores = [];
  SleepData31? _latestSleepData; // Raw sleep data for timeline chart
  bool _isLoading = true;
  HRVData? _latestHRV; // Latest HRV data for readiness calculation

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 3, vsync: this);
    _setupSleepDataListener();
    _setupHRVListener();
    _loadSleepData();
  }

  @override
  void dispose() {
    _tabController.dispose();
    _sleepDataSubscription?.cancel();
    _hrvDataSubscription?.cancel();
    super.dispose();
  }

  /// Setup listener for HRV data
  void _setupHRVListener() {
    _hrvDataSubscription = widget.chileafService.hrvDataStream.listen((hrvData) {
      if (mounted) {
        setState(() {
          _latestHRV = hrvData;
          debugPrint('💓 Premium Screen: Updated HRV data - RMSSD: ${hrvData.rmssd.toStringAsFixed(1)}ms');
        });
        // Recalculate readiness with new HRV data
        if (_latestScore != null) {
          _updateReadinessScore();
        }
      }
    });
  }

  /// Update readiness score with latest data
  void _updateReadinessScore() {
    if (_latestScore == null) return;
    
    setState(() {
      _readinessScore = _readinessCalculator.calculateReadiness(
        sleepScore: _latestScore,
        hrvData: _latestHRV, // ✅ Now using actual HRV data when available
      );
      
      if (_latestHRV != null) {
        debugPrint('✅ Premium Screen: Readiness updated with HRV - Score: ${_readinessScore!.totalScore.toStringAsFixed(1)}');
      }
    });
  }

  /// Setup listener for real-time sleep data from BLE
  void _setupSleepDataListener() {
    // Carica immediatamente dalla cache del servizio (se ci sono dati recenti)
    _loadFromServiceCache();
    
    // Ascolta nuovi dati dallo stream BLE
    _sleepDataSubscription = widget.chileafService.sleepHistoryStream.listen((sleepSessions) async {
      debugPrint('✨ Premium Screen: Received ${sleepSessions.length} sleep sessions from BLE stream');
      
      // Convert and save each session
      for (final session in sleepSessions) {
        try {
          // Convert SleepHistoryEntry to SleepData31 format
          final sleepData31 = SleepData31(
            timestamp: session.timestamp,
            activityIndices: session.actions,
            packetSequence: 0,
          );
          
          // Save to persistent storage
          final saved = await _historyManager.saveSleepSession(sleepData31);
          if (saved) {
            debugPrint('✨ Saved sleep session: ${session.timestamp}');
            
            // Calculate and save sleep score
            final score = _calculateSleepScore(sleepData31);
            await _historyManager.saveSleepScore(score);
            debugPrint('✨ Saved sleep score: ${score.totalScore.toStringAsFixed(1)}');
          }
        } catch (e) {
          debugPrint('❌ Error saving sleep session: $e');
        }
      }
      
      // ✅ FIX: Reload ONLY from storage, DON'T request from device again!
      await _loadSleepDataFromStorage();
    });
  }
  
  /// Load sleep data from service cache (recent BLE data)
  Future<void> _loadFromServiceCache() async {
    final cachedSessions = widget.chileafService.cachedSleepSessions;
    
    debugPrint('📦 Premium Screen: Checking cached sessions - Found ${cachedSessions.length} sessions');
    
    if (cachedSessions.isNotEmpty) {
      debugPrint('📦 Premium Screen: Processing ${cachedSessions.length} cached sessions from service');
      
      // Process cached sessions the same way as stream data
      for (final session in cachedSessions) {
        try {
          final sleepData31 = SleepData31(
            timestamp: session.timestamp,
            activityIndices: session.actions,
            packetSequence: 0,
          );
          
          final saved = await _historyManager.saveSleepSession(sleepData31);
          if (saved) {
            final score = _calculateSleepScore(sleepData31);
            await _historyManager.saveSleepScore(score);
          }
        } catch (e) {
          debugPrint('❌ Error processing cached session: $e');
        }
      }
      
      // Reload UI with cached data
      debugPrint('✅ Premium Screen: Finished processing cached sessions, reloading UI...');
      await _loadSleepData();
    } else {
      debugPrint('📦 Premium Screen: No cached sessions found in service');
    }
  }

  /// Calculate number of awakenings from sleep data
  /// An awakening is when we transition from sleep (deep/light) to awake state
  int _calculateAwakenings(List<int> activityIndices) {
    if (activityIndices.isEmpty) return 0;
    
    int awakenings = 0;
    bool wasSleeping = false;
    
    for (final index in activityIndices) {
      final isAwake = index == 2;
      final isSleeping = index == 0 || index == 1;
      
      // Count transition from sleeping to awake
      if (wasSleeping && isAwake) {
        awakenings++;
      }
      
      wasSleeping = isSleeping;
    }
    
    return awakenings;
  }

  /// Calculate sleep score from sleep data
  SleepScore _calculateSleepScore(SleepData31 sleepData) {
    final phases = sleepData.calculateSleepPhases();
    final totalSleep = phases.lightSleepMinutes + phases.deepSleepMinutes;
    final totalTime = phases.totalIntervals * 5;
    
    // Calculate efficiency
    final efficiency = totalTime > 0 ? (totalSleep / totalTime) : 0.0;
    
    // Calculate deep sleep percentage
    final totalSleepDouble = totalSleep.toDouble();
    final deepSleepPercentage = totalSleep > 0 ? (phases.deepSleepMinutes / totalSleepDouble) : 0.0;
    final lightSleepPercentage = totalSleep > 0 ? (phases.lightSleepMinutes / totalSleepDouble) : 0.0;
    final awakePercentage = totalTime > 0 ? (phases.awakeMinutes / totalTime.toDouble()) : 0.0;
    
    // Calculate awakenings
    final awakenings = _calculateAwakenings(sleepData.activityIndices);
    
    // Calculate duration score (0-35 points) - target 7-9 hours
    final hoursSlept = totalSleep / 60;
    double durationScore = 0.0;
    if (hoursSlept >= 7 && hoursSlept <= 9) {
      durationScore = 35;
    } else if (hoursSlept >= 6 && hoursSlept < 7) {
      durationScore = 25;
    } else if (hoursSlept >= 5) {
      durationScore = 15;
    } else {
      durationScore = 5;
    }
    
    // Calculate efficiency score (0-30 points) - target >85%
    double efficiencyScore = (efficiency * 30).clamp(0, 30);
    
    // Calculate quality score (0-25 points) - based on deep sleep percentage
    double qualityScore = 0.0;
    if (deepSleepPercentage > 0.20) {
      qualityScore = 25;
    } else if (deepSleepPercentage > 0.15) {
      qualityScore = 18;
    } else if (deepSleepPercentage > 0.10) {
      qualityScore = 12;
    } else {
      qualityScore = 5;
    }
    
    // Consistency score (0-10 points) - based on awakenings
    // Fewer awakenings = better consistency
    double consistencyScore = 10.0;
    if (awakenings > 5) {
      consistencyScore = 3.0;
    } else if (awakenings > 3) {
      consistencyScore = 6.0;
    } else if (awakenings > 1) {
      consistencyScore = 8.0;
    }
    
    // Calculate overall score
    final totalScore = durationScore + efficiencyScore + qualityScore + consistencyScore;
    
    return SleepScore(
      sleepDate: sleepData.timestamp,
      totalScore: totalScore,
      durationScore: durationScore,
      efficiencyScore: efficiencyScore,
      qualityScore: qualityScore,
      consistencyScore: consistencyScore,
      totalSleepTime: Duration(minutes: totalSleep),
      timeInBed: Duration(minutes: totalTime),
      sleepEfficiency: efficiency,
      deepSleepMinutes: phases.deepSleepMinutes,
      lightSleepMinutes: phases.lightSleepMinutes,
      awakeMinutes: phases.awakeMinutes,
      awakenings: awakenings, // ✅ Now calculated from actual sleep data transitions
      deepSleepPercentage: deepSleepPercentage,
      lightSleepPercentage: lightSleepPercentage,
      awakePercentage: awakePercentage,
      calculatedAt: DateTime.now(),
    );
  }

  Future<void> _loadSleepData() async {
    setState(() => _isLoading = true);

    try {
      debugPrint('🔄 Premium Screen: Starting sleep data sync...');
      
      // Step 1: Try to download fresh data from device
      try {
        debugPrint('📡 Premium Screen: Requesting sleep data from device...');
        await widget.chileafService.getHistoryOfSleep();
        
        // Wait a bit for data to be received and processed
        await Future.delayed(const Duration(seconds: 2));
        
        debugPrint('✅ Premium Screen: Sleep data request sent to device');
      } catch (deviceError) {
        debugPrint('⚠️ Premium Screen: Could not request from device: $deviceError');
        // Continue anyway - we'll try to load from storage
      }
      
      // Step 2: Load from storage
      await _loadSleepDataFromStorage();
      
    } catch (e) {
      debugPrint('❌ Premium Screen: Error loading sleep data: $e');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Error syncing data: $e'),
            backgroundColor: Colors.red,
            duration: const Duration(seconds: 3),
          ),
        );
      }
    }

    setState(() => _isLoading = false);
  }

  /// Load sleep data from storage only (NO device request)
  /// Used by stream listener to avoid infinite loops
  Future<void> _loadSleepDataFromStorage() async {
    try {
      // Load recent sleep scores from storage
      final recentScores = await _historyManager.getRecentMainSleepScores(7);  // ✅ Only main night sleeps
      debugPrint('📊 Premium Screen: Loaded ${recentScores.length} main night sleep scores from storage');
      
      if (recentScores.isNotEmpty) {
        setState(() {
          _latestScore = recentScores.first;
          _recentScores = recentScores;
        });
        
        debugPrint('✅ Premium Screen: Latest score = ${_latestScore!.totalScore.toStringAsFixed(1)}, Date = ${_latestScore!.sleepDate}');
        
        // Load raw sleep data for timeline chart
        final recentSessions = await _historyManager.getRecentSessions(1);
        if (recentSessions.isNotEmpty) {
          setState(() {
            _latestSleepData = recentSessions.first;
          });
          debugPrint('✅ Premium Screen: Loaded raw sleep data with ${recentSessions.first.activityIndices.length} intervals');
        }
        
        // Calculate readiness score (will use HRV if available)
        _updateReadinessScore();
        
        debugPrint('✅ Premium Screen: Readiness score = ${_readinessScore!.totalScore.toStringAsFixed(1)}');
        
        // ✅ Update UI silently (no snackbar - already shown by main load)
      } else {
        debugPrint('⚠️ Premium Screen: No sleep scores found in storage');
      }
    } catch (e) {
      debugPrint('❌ Premium Screen: Error loading from storage: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildRegularAppBar(),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _latestScore == null
              ? _buildEmptyState()
              : _buildContent(),
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

  PreferredSizeWidget _buildRegularAppBar() {
    return AppBar(
      title: const Text('Sleep Analytics'),
      backgroundColor: Colors.deepPurple,
      elevation: 0,
      actions: [
        IconButton(
          icon: const Icon(Icons.trending_up),
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
          // Fitbit-style Hero Score Card
          FitbitSleepScoreCard(
            sleepScore: _latestScore!,
            showBreakdown: true,
          ),
          
          const SizedBox(height: 24),
          
          // Sleep Timeline (Fitbit-style horizontal bars)
          _buildSleepPhasesCard(),
          
          const SizedBox(height: 24),
          
          // Quick Stats Row
          _buildQuickStats(),
          
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
    
    // Calculate phase percentages - FIXED: Use timeInBed as base (includes awake time)
    final totalMinutes = score.timeInBed.inMinutes; // ✅ CORRECT: Total time in bed
    final deepMinutes = score.deepSleepMinutes;
    final lightMinutes = score.lightSleepMinutes;
    final awakeMinutes = score.awakeMinutes;
    final deepPercent = totalMinutes > 0 ? ((deepMinutes / totalMinutes * 100).round()) : 0;
    final lightPercent = totalMinutes > 0 ? ((lightMinutes / totalMinutes * 100).round()) : 0;
    final awakePercent = totalMinutes > 0 ? ((awakeMinutes / totalMinutes * 100).round()) : 0;
    
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  'Sleep Timeline',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                // Sleep score badge (Fitbit-style)
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                  decoration: BoxDecoration(
                    color: _getScoreColor(score.totalScore),
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Text(
                        score.totalScore.round().toString(),
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      const SizedBox(width: 4),
                      Text(
                        score.rating.displayName,
                        style: const TextStyle(
                          fontSize: 12,
                          fontWeight: FontWeight.w500,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            
            // Fitbit-style horizontal timeline chart
            if (_latestSleepData != null)
              SleepTimelineChart(
                sleepData: _latestSleepData!,
                height: 100,
              )
            else
              _buildPhaseBar(deepPercent, lightPercent, awakePercent),
            
            const SizedBox(height: 16),
            
            // Phase legend with accurate data
            _buildPhaseLegend('🌊 Deep', deepMinutes, deepPercent, const Color(0xFF3F51B5)),
            const SizedBox(height: 8),
            _buildPhaseLegend('😴 Light', lightMinutes, lightPercent, const Color(0xFF64B5F6)),
            const SizedBox(height: 8),
            _buildPhaseLegend('👀 Awake', awakeMinutes, awakePercent, const Color(0xFFFF9E80)),
          ],
        ),
      ),
    );
  }

  /// Get color based on sleep score (Fitbit-style)
  Color _getScoreColor(double score) {
    if (score >= 90) return const Color(0xFF4CAF50); // Green - Excellent
    if (score >= 80) return const Color(0xFF8BC34A); // Light Green - Good
    if (score >= 70) return const Color(0xFFFFA726); // Orange - Fair
    if (score >= 60) return const Color(0xFFFF7043); // Deep Orange - Poor
    return const Color(0xFFF44336); // Red - Very Poor
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
