import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../services/data_persistence_manager.dart';
import '../services/performance_monitor.dart';
import '../widgets/battery_widget.dart';
import '../widgets/heart_rate_widget.dart';
import '../widgets/spo2_widget.dart';
import '../widgets/temperature_widget.dart';

class DashboardWidget extends StatefulWidget {
  final bool isConnected;
  final String? deviceName;
  final Map<String, dynamic> sensorData;
  final VoidCallback? onRefresh;
  final VoidCallback? onSettings;

  const DashboardWidget({
    Key? key,
    required this.isConnected,
    this.deviceName,
    required this.sensorData,
    this.onRefresh,
    this.onSettings,
  }) : super(key: key);

  @override
  State<DashboardWidget> createState() => _DashboardWidgetState();
}

class _DashboardWidgetState extends State<DashboardWidget> with TickerProviderStateMixin {
  late TabController _tabController;
  PerformanceStats? _performanceStats;
  StorageInfo? _storageInfo;
  bool _showPerformanceInfo = false;
  
  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 3, vsync: this);
    _loadDashboardData();
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  Future<void> _loadDashboardData() async {
    try {
      PerformanceStats stats = performanceMonitor.getStats();
      StorageInfo storage = await dataPersistenceManager.getStorageInfo();
      
      if (mounted) {
        setState(() {
          _performanceStats = stats;
          _storageInfo = storage;
        });
      }
    } catch (e) {
      debugPrint('❌ Error loading dashboard data: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: _buildAppBarTitle(),
        actions: [
          IconButton(
            icon: const Icon(Icons.analytics),
            onPressed: () {
              setState(() {
                _showPerformanceInfo = !_showPerformanceInfo;
              });
            },
          ),
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              widget.onRefresh?.call();
              _loadDashboardData();
            },
          ),
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: widget.onSettings,
          ),
        ],
        bottom: TabBar(
          controller: _tabController,
          tabs: const [
            Tab(icon: Icon(Icons.dashboard), text: 'Overview'),
            Tab(icon: Icon(Icons.trending_up), text: 'Trends'),
            Tab(icon: Icon(Icons.info), text: 'Details'),
          ],
        ),
      ),
      body: Column(
        children: [
          if (_showPerformanceInfo) _buildPerformanceBar(),
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: [
                _buildOverviewTab(),
                _buildTrendsTab(),
                _buildDetailsTab(),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildAppBarTitle() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          widget.deviceName ?? 'CL837 Monitor',
          style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
        Row(
          children: [
            Container(
              width: 8,
              height: 8,
              decoration: BoxDecoration(
                color: widget.isConnected ? Colors.green : Colors.red,
                shape: BoxShape.circle,
              ),
            ),
            const SizedBox(width: 4),
            Text(
              widget.isConnected ? 'Connected' : 'Disconnected',
              style: const TextStyle(fontSize: 12),
            ),
          ],
        ),
      ],
    );
  }

  Widget _buildPerformanceBar() {
    if (_performanceStats == null) return const SizedBox.shrink();
    
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      decoration: BoxDecoration(
        color: Theme.of(context).primaryColor.withOpacity(0.1),
        border: Border(
          bottom: BorderSide(
            color: Theme.of(context).dividerColor,
          ),
        ),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          _buildPerformanceChip(
            'Success Rate',
            '${(_performanceStats!.successRate * 100).toStringAsFixed(1)}%',
            _performanceStats!.successRate > 0.9 ? Colors.green : Colors.orange,
          ),
          _buildPerformanceChip(
            'Avg Response',
            '${_performanceStats!.averageResponseTime.toStringAsFixed(0)}ms',
            _performanceStats!.averageResponseTime < 1000 ? Colors.green : Colors.orange,
          ),
          _buildPerformanceChip(
            'Operations',
            '${_performanceStats!.totalOperations}',
            Colors.blue,
          ),
        ],
      ),
    );
  }

  Widget _buildPerformanceChip(String label, String value, Color color) {
    return Column(
      children: [
        Text(
          value,
          style: TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.bold,
            color: color,
          ),
        ),
        Text(
          label,
          style: const TextStyle(fontSize: 10),
        ),
      ],
    );
  }

  Widget _buildOverviewTab() {
    return RefreshIndicator(
      onRefresh: () async {
        widget.onRefresh?.call();
        await _loadDashboardData();
      },
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            _buildConnectionStatus(),
            const SizedBox(height: 16),
            _buildSensorGrid(),
            const SizedBox(height: 16),
            _buildQuickStats(),
          ],
        ),
      ),
    );
  }

  Widget _buildTrendsTab() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [
          _buildTrendChart('Heart Rate', 'bpm', Colors.red),
          const SizedBox(height: 16),
          _buildTrendChart('SpO2', '%', Colors.blue),
          const SizedBox(height: 16),
          _buildTrendChart('Temperature', '°C', Colors.orange),
          const SizedBox(height: 16),
          _buildTrendChart('Battery', '%', Colors.green),
        ],
      ),
    );
  }

  Widget _buildDetailsTab() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [
          _buildDeviceInfo(),
          const SizedBox(height: 16),
          _buildStorageInfo(),
          const SizedBox(height: 16),
          _buildPerformanceDetails(),
        ],
      ),
    );
  }

  Widget _buildConnectionStatus() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Row(
          children: [
            Icon(
              widget.isConnected ? Icons.bluetooth_connected : Icons.bluetooth_disabled,
              size: 32,
              color: widget.isConnected ? Colors.green : Colors.red,
            ),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    widget.isConnected ? 'Device Connected' : 'Device Disconnected',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    widget.deviceName ?? 'Unknown Device',
                    style: const TextStyle(fontSize: 14),
                  ),
                  Text(
                    'Last update: ${DateFormat('HH:mm:ss').format(DateTime.now())}',
                    style: const TextStyle(fontSize: 12, color: Colors.grey),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSensorGrid() {
    return GridView.count(
      crossAxisCount: 2,
      shrinkWrap: true,
      physics: const NeverScrollableScrollPhysics(),
      childAspectRatio: 1.2,
      children: [
        BatteryWidget(
          latestData: widget.sensorData['battery'],
          isConnected: widget.isConnected,
          isCharging: widget.sensorData['charging'] ?? false,
        ),
        HeartRateWidget(
          latestData: widget.sensorData['heartRate'],
          isConnected: widget.isConnected,
        ),
        SpO2Widget(
          spo2Data: widget.sensorData['spo2'],
          isConnected: widget.isConnected,
        ),
        TemperatureWidget(
          temperatureData: widget.sensorData['temperature'],
          isConnected: widget.isConnected,
        ),
      ],
    );
  }

  Widget _buildQuickStats() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Quick Stats',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildQuickStat('Sessions', '${_storageInfo?.hrvSessionsCount ?? 0}'),
                _buildQuickStat('Readings', '${_storageInfo?.batteryReadingsCount ?? 0}'),
                _buildQuickStat('Uptime', _calculateUptime()),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildQuickStat(String label, String value) {
    return Column(
      children: [
        Text(
          value,
          style: const TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
            color: Colors.blue,
          ),
        ),
        Text(
          label,
          style: const TextStyle(fontSize: 12),
        ),
      ],
    );
  }

  Widget _buildTrendChart(String title, String unit, Color color) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              title,
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            Container(
              height: 120,
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey.withOpacity(0.3)),
                borderRadius: BorderRadius.circular(8),
              ),
              child: Center(
                child: Text(
                  'Trend chart for $title\n(Coming soon)',
                  textAlign: TextAlign.center,
                  style: const TextStyle(color: Colors.grey),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDeviceInfo() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Device Information',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            _buildInfoRow('Device Name', widget.deviceName ?? 'Unknown'),
            _buildInfoRow('Connection Status', widget.isConnected ? 'Connected' : 'Disconnected'),
            _buildInfoRow('Battery Level', '${widget.sensorData['battery'] ?? 'N/A'}%'),
            _buildInfoRow('Last Update', DateFormat('dd/MM/yyyy HH:mm:ss').format(DateTime.now())),
          ],
        ),
      ),
    );
  }

  Widget _buildStorageInfo() {
    if (_storageInfo == null) return const SizedBox.shrink();
    
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Storage Information',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            _buildInfoRow('HRV Sessions', '${_storageInfo!.hrvSessionsCount}'),
            _buildInfoRow('Battery Readings', '${_storageInfo!.batteryReadingsCount}'),
            _buildInfoRow('Total Keys', '${_storageInfo!.totalKeys}'),
          ],
        ),
      ),
    );
  }

  Widget _buildPerformanceDetails() {
    if (_performanceStats == null) return const SizedBox.shrink();
    
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Performance Details',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            _buildInfoRow('Total Operations', '${_performanceStats!.totalOperations}'),
            _buildInfoRow('Success Rate', '${(_performanceStats!.successRate * 100).toStringAsFixed(1)}%'),
            _buildInfoRow('Average Response', '${_performanceStats!.averageResponseTime.toStringAsFixed(0)}ms'),
            _buildInfoRow('Failed Operations', '${_performanceStats!.failedOperations}'),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: const TextStyle(fontSize: 14),
          ),
          Text(
            value,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  String _calculateUptime() {
    // This would need to be implemented based on when the app started
    return '24h 12m';
  }
}
