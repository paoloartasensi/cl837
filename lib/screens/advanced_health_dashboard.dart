import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../chileaf_extended_service.dart';
import '../models/recovery_score.dart';
import '../models/sport_health_data.dart';
import '../models/historical_data.dart';
import 'dart:async';

/// Advanced Health Dashboard - Whoop-style Recovery Analytics
class AdvancedHealthDashboard extends StatefulWidget {
  final BluetoothDevice device;
  final ChileafExtendedService service;

  const AdvancedHealthDashboard({
    super.key,
    required this.device,
    required this.service,
  });

  @override
  State<AdvancedHealthDashboard> createState() => _AdvancedHealthDashboardState();
}

class _AdvancedHealthDashboardState extends State<AdvancedHealthDashboard> with SingleTickerProviderStateMixin {
  final List<StreamSubscription> _subscriptions = [];
  late TabController _tabController;
  
  // Current data
  RecoveryScore? _recoveryScore;
  SportHealthData? _currentSportHealth;
  SleepQuality? _lastSleepQuality;
  int? _currentHeartRate;
  List<SleepHistoryEntry> _sleepHistory = [];
  
  // Loading state
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 4, vsync: this);
    _setupListeners();
    _loadInitialData();
  }

  @override
  void dispose() {
    _tabController.dispose();
    for (var sub in _subscriptions) {
      sub.cancel();
    }
    super.dispose();
  }

  void _setupListeners() {
    // Real-time heart rate
    _subscriptions.add(
      widget.service.realtimeHRStream.listen((hr) {
        if (mounted) {
          setState(() => _currentHeartRate = hr);
        }
      }),
    );

    // Sport health data (HRV, respiratory rate, etc.)
    _subscriptions.add(
      widget.service.sportHealthStream.listen((healthData) {
        if (mounted) {
          setState(() {
            _currentSportHealth = healthData;
            _calculateRecoveryScore();
          });
        }
      }),
    );

    // Sleep history
    _subscriptions.add(
      widget.service.sleepHistoryStream.listen((sleepList) {
        if (mounted) {
          setState(() {
            _sleepHistory = sleepList;
            _calculateLastSleepQuality();
            _calculateRecoveryScore();
          });
        }
      }),
    );
  }

  Future<void> _loadInitialData() async {
    setState(() => _isLoading = true);
    
    // Request sleep history to calculate recovery
    await widget.service.requestOptimizedSleepHistory(force: false);
    
    setState(() => _isLoading = false);
  }

  void _calculateLastSleepQuality() {
    if (_sleepHistory.isEmpty) return;
    
    // Find main sleep (longest and most recent)
    final mainSleeps = _sleepHistory.where((s) => 
      s.count >= 36 && // At least 3 hours
      (s.timestamp.hour >= 18 || s.timestamp.hour <= 10) // Night hours
    ).toList();
    
    if (mainSleeps.isNotEmpty) {
      mainSleeps.sort((a, b) => b.timestamp.compareTo(a.timestamp));
      _lastSleepQuality = SleepQuality.fromSleepSession(mainSleeps.first);
    }
  }

  void _calculateRecoveryScore() {
    _recoveryScore = RecoveryScore.calculate(
      sportHealth: _currentSportHealth,
      sleepQuality: _lastSleepQuality,
      restingHeartRate: _currentHeartRate,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Health Analytics'),
        backgroundColor: Colors.black,
        bottom: TabBar(
          controller: _tabController,
          indicatorColor: Colors.greenAccent,
          tabs: const [
            Tab(icon: Icon(Icons.favorite), text: 'Recovery'),
            Tab(icon: Icon(Icons.bedtime), text: 'Sleep'),
            Tab(icon: Icon(Icons.health_and_safety), text: 'HRV'),
            Tab(icon: Icon(Icons.air), text: 'Respiratory'),
          ],
        ),
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : TabBarView(
              controller: _tabController,
              children: [
                _buildRecoveryTab(),
                _buildSleepTab(),
                _buildHRVTab(),
                _buildRespiratoryTab(),
              ],
            ),
    );
  }

  Widget _buildRecoveryTab() {
    if (_recoveryScore == null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.refresh, size: 64, color: Colors.grey),
            const SizedBox(height: 16),
            const Text('Calculating recovery...', style: TextStyle(fontSize: 18)),
            const SizedBox(height: 24),
            ElevatedButton.icon(
              onPressed: _loadInitialData,
              icon: const Icon(Icons.download),
              label: const Text('Download Sleep Data'),
            ),
          ],
        ),
      );
    }

    final recovery = _recoveryScore!;
    
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Recovery Score Card
          Card(
            color: _getRecoveryColor(recovery.zone),
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        recovery.zone.emoji,
                        style: const TextStyle(fontSize: 48),
                      ),
                      const SizedBox(width: 16),
                      Text(
                        '${recovery.score.toStringAsFixed(0)}%',
                        style: const TextStyle(
                          fontSize: 72,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  Text(
                    'Recovery ${recovery.zone.label}',
                    style: const TextStyle(
                      fontSize: 24,
                      color: Colors.white,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 16),
                  Text(
                    recovery.recommendation,
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
            ),
          ),
          
          const SizedBox(height: 24),
          
          // Component Scores
          const Text(
            'Recovery Components',
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 12),
          
          _buildMetricBar(
            'HRV Score',
            recovery.hrvScore,
            Icons.favorite,
            Colors.red,
            subtitle: recovery.lfHfRatio != null 
                ? 'LF/HF: ${recovery.lfHfRatio!.toStringAsFixed(2)}'
                : null,
          ),
          const SizedBox(height: 12),
          
          _buildMetricBar(
            'Sleep Quality',
            recovery.sleepScore,
            Icons.bedtime,
            Colors.blue,
            subtitle: recovery.sleepQuality != null
                ? '${(recovery.sleepQuality! * 100).toStringAsFixed(0)}%'
                : null,
          ),
          const SizedBox(height: 12),
          
          _buildMetricBar(
            'Resting HR',
            recovery.rhrScore,
            Icons.monitor_heart,
            Colors.pink,
            subtitle: recovery.restingHeartRate != null
                ? '${recovery.restingHeartRate} BPM'
                : null,
          ),
          
          const SizedBox(height: 24),
          
          // Current Metrics
          if (_currentSportHealth != null) ...[
            const Text(
              'Current Metrics',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            _buildMetricsGrid(_currentSportHealth!),
          ],
        ],
      ),
    );
  }

  Widget _buildSleepTab() {
    if (_lastSleepQuality == null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.bedtime_outlined, size: 64, color: Colors.grey),
            const SizedBox(height: 16),
            const Text('No sleep data available', style: TextStyle(fontSize: 18)),
            const SizedBox(height: 24),
            ElevatedButton.icon(
              onPressed: _loadInitialData,
              icon: const Icon(Icons.download),
              label: const Text('Download Sleep Data'),
            ),
          ],
        ),
      );
    }

    final sleep = _lastSleepQuality!;
    
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Sleep Score Card
          Card(
            color: _getSleepScoreColor(sleep.overallScore),
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                children: [
                  Text(
                    sleep.grade,
                    style: const TextStyle(
                      fontSize: 72,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  Text(
                    'Sleep Quality',
                    style: const TextStyle(
                      fontSize: 24,
                      color: Colors.white,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    '${(sleep.overallScore * 100).toStringAsFixed(0)}% Performance',
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
            ),
          ),
          
          const SizedBox(height: 24),
          
          // Sleep Duration
          _buildSleepMetricCard(
            'Sleep Duration',
            '${(sleep.totalMinutes / 60).toStringAsFixed(1)}h',
            Icons.access_time,
            Colors.blue,
            details: [
              'Deep: ${sleep.deepSleepMinutes}min (${(sleep.deepSleepMinutes / sleep.totalMinutes * 100).toStringAsFixed(0)}%)',
              'Light: ${sleep.lightSleepMinutes}min (${(sleep.lightSleepMinutes / sleep.totalMinutes * 100).toStringAsFixed(0)}%)',
              'Awake: ${sleep.awakeMinutes}min (${(sleep.awakeMinutes / sleep.totalMinutes * 100).toStringAsFixed(0)}%)',
            ],
          ),
          
          const SizedBox(height: 12),
          
          // Sleep Efficiency
          _buildSleepMetricCard(
            'Sleep Efficiency',
            '${(sleep.efficiency * 100).toStringAsFixed(0)}%',
            Icons.trending_up,
            Colors.green,
            details: [
              'Time asleep: ${sleep.totalMinutes - sleep.awakeMinutes}min',
              'Target: >85%',
            ],
          ),
          
          const SizedBox(height: 24),
          
          // Sleep Stages Breakdown
          const Text(
            'Sleep Stages Breakdown',
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 12),
          
          _buildSleepStagesChart(sleep),
        ],
      ),
    );
  }

  Widget _buildHRVTab() {
    if (_currentSportHealth == null || _currentSportHealth!.lfHfRatio == null) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.heart_broken, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text('Waiting for HRV data...', style: TextStyle(fontSize: 18)),
            SizedBox(height: 8),
            Text(
              'HRV is measured during rest or sleep',
              style: TextStyle(color: Colors.grey),
            ),
          ],
        ),
      );
    }

    final hrv = _currentSportHealth!;
    final lfHfRatio = hrv.lfHfRatio!;
    
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // LF/HF Ratio Card
          Card(
            color: _getHRVColor(lfHfRatio),
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                children: [
                  Text(
                    lfHfRatio.toStringAsFixed(2),
                    style: const TextStyle(
                      fontSize: 72,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  const Text(
                    'LF/HF Ratio',
                    style: TextStyle(
                      fontSize: 24,
                      color: Colors.white,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    _getHRVStatus(lfHfRatio),
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
            ),
          ),
          
          const SizedBox(height: 24),
          
          // HRV Components
          const Text(
            'HRV Frequency Components',
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 12),
          
          if (hrv.totalPower != null)
            _buildHRVMetricCard(
              'Total Power',
              '${hrv.totalPower!.toStringAsFixed(1)} ms²',
              Icons.power,
              Colors.purple,
              'Overall heart rate variability',
            ),
          
          const SizedBox(height: 12),
          
          if (hrv.lowFrequency != null)
            _buildHRVMetricCard(
              'Low Frequency (LF)',
              '${hrv.lowFrequency!.toStringAsFixed(1)} ms²',
              Icons.trending_up,
              Colors.orange,
              'Sympathetic activity (stress response)',
            ),
          
          const SizedBox(height: 12),
          
          if (hrv.highFrequency != null)
            _buildHRVMetricCard(
              'High Frequency (HF)',
              '${hrv.highFrequency!.toStringAsFixed(1)} ms²',
              Icons.trending_down,
              Colors.green,
              'Parasympathetic activity (recovery)',
            ),
          
          const SizedBox(height: 24),
          
          // Interpretation Guide
          _buildHRVGuide(),
        ],
      ),
    );
  }

  Widget _buildRespiratoryTab() {
    if (_currentSportHealth == null) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.air, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text('Waiting for respiratory data...', style: TextStyle(fontSize: 18)),
          ],
        ),
      );
    }

    final breathRate = _currentSportHealth!.breathRate;
    
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Breath Rate Card
          Card(
            color: _getRespiratoryColor(breathRate),
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                children: [
                  Text(
                    '$breathRate',
                    style: const TextStyle(
                      fontSize: 72,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  const Text(
                    'Breaths/Minute',
                    style: TextStyle(
                      fontSize: 24,
                      color: Colors.white,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    _getRespiratoryStatus(breathRate),
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
            ),
          ),
          
          const SizedBox(height: 24),
          
          // Respiratory Guide
          const Text(
            'Respiratory Rate Guide',
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 12),
          
          _buildRespiratoryGuideCard(
            'During Sleep',
            '12-16 breaths/min',
            'Optimal range for restful sleep',
            Icons.bedtime,
            Colors.blue,
          ),
          
          const SizedBox(height: 12),
          
          _buildRespiratoryGuideCard(
            'At Rest',
            '12-20 breaths/min',
            'Normal resting respiratory rate',
            Icons.self_improvement,
            Colors.green,
          ),
          
          const SizedBox(height: 12),
          
          _buildRespiratoryGuideCard(
            'During Activity',
            '20-30 breaths/min',
            'Elevated due to physical exertion',
            Icons.directions_run,
            Colors.orange,
          ),
          
          const SizedBox(height: 24),
          
          // Additional Metrics
          if (_currentSportHealth != null) ...[
            const Text(
              'Related Metrics',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            
            _buildMetricCard(
              'Stress Level',
              '${_currentSportHealth!.stressPercent}%',
              Icons.psychology,
              _getStressColor(_currentSportHealth!.stressPercent),
              _currentSportHealth!.stressLevel,
            ),
            
            const SizedBox(height: 12),
            
            _buildMetricCard(
              'VO2 Max',
              '${_currentSportHealth!.vo2Max}',
              Icons.air,
              Colors.blue,
              _currentSportHealth!.getVO2MaxLevel(true),
            ),
          ],
        ],
      ),
    );
  }

  // Helper widgets
  Widget _buildMetricBar(String title, double value, IconData icon, Color color, {String? subtitle}) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(icon, color: color, size: 24),
                const SizedBox(width: 12),
                Text(
                  title,
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w600,
                  ),
                ),
                const Spacer(),
                Text(
                  '${value.toStringAsFixed(0)}%',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: color,
                  ),
                ),
              ],
            ),
            if (subtitle != null) ...[
              const SizedBox(height: 4),
              Text(
                subtitle,
                style: const TextStyle(
                  fontSize: 12,
                  color: Colors.grey,
                ),
              ),
            ],
            const SizedBox(height: 8),
            ClipRRect(
              borderRadius: BorderRadius.circular(8),
              child: LinearProgressIndicator(
                value: value / 100,
                backgroundColor: Colors.grey[300],
                color: color,
                minHeight: 8,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildMetricsGrid(SportHealthData health) {
    return GridView.count(
      crossAxisCount: 2,
      shrinkWrap: true,
      physics: const NeverScrollableScrollPhysics(),
      childAspectRatio: 1.5,
      mainAxisSpacing: 12,
      crossAxisSpacing: 12,
      children: [
        _buildMetricCard('VO2 Max', '${health.vo2Max}', Icons.air, Colors.blue, health.getVO2MaxLevel(true)),
        _buildMetricCard('Breath Rate', '${health.breathRate} BPM', Icons.air, Colors.cyan, 'Respiratory'),
        _buildMetricCard('Emotion', health.emotionDescription, Icons.mood, Colors.amber, 'Level ${health.emotionLevel}'),
        _buildMetricCard('Stress', '${health.stressPercent}%', Icons.psychology, _getStressColor(health.stressPercent), health.stressLevel),
        _buildMetricCard('Stamina', health.staminaDescription, Icons.bolt, Colors.orange, 'Level ${health.stamina}'),
        if (_currentHeartRate != null)
          _buildMetricCard('Heart Rate', '$_currentHeartRate BPM', Icons.favorite, Colors.red, 'Current'),
      ],
    );
  }

  Widget _buildMetricCard(String title, String value, IconData icon, Color color, String subtitle) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, color: color, size: 32),
            const SizedBox(height: 8),
            Text(
              value,
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
            Text(
              title,
              style: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w600,
              ),
            ),
            Text(
              subtitle,
              style: const TextStyle(
                fontSize: 10,
                color: Colors.grey,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSleepMetricCard(String title, String value, IconData icon, Color color, {required List<String> details}) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(icon, color: color, size: 32),
                const SizedBox(width: 12),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      title,
                      style: const TextStyle(
                        fontSize: 14,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                    Text(
                      value,
                      style: TextStyle(
                        fontSize: 28,
                        fontWeight: FontWeight.bold,
                        color: color,
                      ),
                    ),
                  ],
                ),
              ],
            ),
            const SizedBox(height: 12),
            ...details.map((detail) => Padding(
              padding: const EdgeInsets.only(bottom: 4),
              child: Text(
                '• $detail',
                style: const TextStyle(fontSize: 12, color: Colors.grey),
              ),
            )),
          ],
        ),
      ),
    );
  }

  Widget _buildSleepStagesChart(SleepQuality sleep) {
    final total = sleep.totalMinutes.toDouble();
    
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Row(
              children: [
                Expanded(
                  flex: sleep.deepSleepMinutes,
                  child: Container(
                    height: 40,
                    color: Colors.indigo,
                    alignment: Alignment.center,
                    child: Text(
                      '${(sleep.deepSleepMinutes / total * 100).toStringAsFixed(0)}%',
                      style: const TextStyle(color: Colors.white, fontSize: 12, fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
                Expanded(
                  flex: sleep.lightSleepMinutes,
                  child: Container(
                    height: 40,
                    color: Colors.lightBlue,
                    alignment: Alignment.center,
                    child: Text(
                      '${(sleep.lightSleepMinutes / total * 100).toStringAsFixed(0)}%',
                      style: const TextStyle(color: Colors.white, fontSize: 12, fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
                Expanded(
                  flex: sleep.awakeMinutes,
                  child: Container(
                    height: 40,
                    color: Colors.orange,
                    alignment: Alignment.center,
                    child: Text(
                      '${(sleep.awakeMinutes / total * 100).toStringAsFixed(0)}%',
                      style: const TextStyle(color: Colors.white, fontSize: 12, fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildLegendItem('Deep', Colors.indigo),
                _buildLegendItem('Light', Colors.lightBlue),
                _buildLegendItem('Awake', Colors.orange),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildLegendItem(String label, Color color) {
    return Row(
      children: [
        Container(
          width: 16,
          height: 16,
          color: color,
        ),
        const SizedBox(width: 8),
        Text(label, style: const TextStyle(fontSize: 12)),
      ],
    );
  }

  Widget _buildHRVMetricCard(String title, String value, IconData icon, Color color, String description) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Row(
          children: [
            Icon(icon, color: color, size: 40),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  Text(
                    value,
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: color,
                    ),
                  ),
                  Text(
                    description,
                    style: const TextStyle(
                      fontSize: 11,
                      color: Colors.grey,
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

  Widget _buildHRVGuide() {
    return Card(
      color: Colors.grey[100],
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'LF/HF Ratio Interpretation',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 12),
            _buildGuideRow('0.5-1.0', '🟢 Excellent Recovery', 'Parasympathetic dominance'),
            _buildGuideRow('1.0-2.0', '🟡 Balanced', 'Normal autonomic balance'),
            _buildGuideRow('2.0-3.0', '🟠 Elevated Stress', 'Sympathetic dominance'),
            _buildGuideRow('>3.0', '🔴 High Stress', 'Overtraining risk'),
          ],
        ),
      ),
    );
  }

  Widget _buildRespiratoryGuideCard(String title, String range, String description, IconData icon, Color color) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Row(
          children: [
            Icon(icon, color: color, size: 40),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  Text(
                    range,
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: color,
                    ),
                  ),
                  Text(
                    description,
                    style: const TextStyle(
                      fontSize: 12,
                      color: Colors.grey,
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

  Widget _buildGuideRow(String range, String status, String description) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8),
      child: Row(
        children: [
          SizedBox(
            width: 70,
            child: Text(
              range,
              style: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  status,
                  style: const TextStyle(
                    fontSize: 13,
                    fontWeight: FontWeight.w600,
                  ),
                ),
                Text(
                  description,
                  style: const TextStyle(
                    fontSize: 11,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  // Color helpers
  Color _getRecoveryColor(RecoveryZone zone) {
    switch (zone) {
      case RecoveryZone.green:
        return Colors.green.shade700;
      case RecoveryZone.yellow:
        return Colors.orange.shade700;
      case RecoveryZone.red:
        return Colors.red.shade700;
    }
  }

  Color _getSleepScoreColor(double score) {
    if (score >= 0.80) return Colors.green.shade700;
    if (score >= 0.65) return Colors.orange.shade700;
    return Colors.red.shade700;
  }

  Color _getHRVColor(double lfHfRatio) {
    if (lfHfRatio < 1.5) return Colors.green.shade700;
    if (lfHfRatio < 2.5) return Colors.orange.shade700;
    return Colors.red.shade700;
  }

  Color _getRespiratoryColor(int breathRate) {
    if (breathRate >= 12 && breathRate <= 18) return Colors.green.shade700;
    if (breathRate >= 10 && breathRate <= 22) return Colors.orange.shade700;
    return Colors.red.shade700;
  }

  Color _getStressColor(int stressPercent) {
    if (stressPercent < 30) return Colors.green;
    if (stressPercent < 60) return Colors.orange;
    return Colors.red;
  }

  String _getHRVStatus(double lfHfRatio) {
    if (lfHfRatio < 1.0) return '🟢 Excellent Recovery\nParasympathetic dominance';
    if (lfHfRatio < 2.0) return '🟡 Balanced State\nNormal autonomic function';
    if (lfHfRatio < 3.0) return '🟠 Elevated Stress\nSympathetic dominance';
    return '🔴 High Stress / Overtraining\nConsider rest';
  }

  String _getRespiratoryStatus(int breathRate) {
    if (breathRate >= 12 && breathRate <= 16) return '🟢 Optimal respiratory rate';
    if (breathRate >= 10 && breathRate <= 20) return '🟡 Normal range';
    if (breathRate > 20) return '🟠 Elevated (activity or stress)';
    return '🔴 Low (check sensor)';
  }
}
