import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../chileaf_extended_service.dart';
import '../models/realtime_data.dart';
import 'dart:async';

/// Dashboard principale che mostra i dati real-time ricevuti automaticamente
/// Simile alla MainViewController dell'SDK iOS
class DashboardScreen extends StatefulWidget {
  final BluetoothDevice device;
  final ChileafExtendedService service;

  const DashboardScreen({
    super.key,
    required this.device,
    required this.service,
  });

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  RealtimeDeviceData _realtimeData = const RealtimeDeviceData();
  final List<StreamSubscription> _subscriptions = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _setupListeners();
    _loadInitialData();
  }

  @override
  void dispose() {
    for (var sub in _subscriptions) {
      sub.cancel();
    }
    super.dispose();
  }

  void _setupListeners() {
    // Listen to real-time heart rate (BLE Heart Rate Service 0x2A37)
    _subscriptions.add(
      widget.service.realtimeHRStream.listen((hr) {
        setState(() {
          _realtimeData = _realtimeData.copyWith(heartRate: hr);
        });
      }),
    );

    // Listen to sport health data (VO2, breath rate, stress, stamina)
    _subscriptions.add(
      widget.service.sportHealthStream.listen((healthData) {
        setState(() {
          _realtimeData = _realtimeData.copyWith(
            vo2Max: healthData.vo2Max,
            breathRate: healthData.breathRate,
            emotionLevel: getEmotionLevelString(healthData.emotionLevel),
            stressPercent: healthData.stressPercent,
            stamina: getStaminaString(healthData.stamina),
          );
        });
      }),
    );

    // Listen to device info updates
    _subscriptions.add(
      widget.service.deviceInfoStream.listen((info) {
        setState(() {
          _realtimeData = _realtimeData.copyWith(
            deviceName: widget.device.platformName,
            softwareVersion: info.firmwareVersion,
          );
        });
      }),
    );
    
    // Listen to device status (battery, charging, etc.)
    _subscriptions.add(
      widget.service.deviceStatusStream.listen((status) {
        setState(() {
          _realtimeData = _realtimeData.copyWith(
            battery: status.batteryLevel,
          );
        });
      }),
    );
    
    // Listen to sport real-time data (steps, distance, calories)
    _subscriptions.add(
      widget.service.sportRealtimeStream.listen((sportData) {
        setState(() {
          _realtimeData = _realtimeData.copyWith(
            steps: sportData.steps,
            distanceMeters: sportData.distanceMeters,
            calories: sportData.caloriesKcal,
          );
        });
      }),
    );

    // Listen to firmware version
    _subscriptions.add(
      widget.service.firmwareVersionStream.listen((version) {
        setState(() {
          _realtimeData = _realtimeData.copyWith(
            softwareVersion: version,
          );
        });
      }),
    );

    // Request RSSI periodically
    Timer.periodic(const Duration(seconds: 5), (timer) {
      if (!mounted) {
        timer.cancel();
        return;
      }
      widget.device.readRssi().then((rssi) {
        if (mounted) {
          setState(() {
            _realtimeData = _realtimeData.copyWith(
              rssi: '${rssi}dBm',
            );
          });
        }
      }).catchError((error) {
        debugPrint('Error reading RSSI: $error');
      });
    });
  }

  Future<void> _loadInitialData() async {
    setState(() => _isLoading = true);

    try {
      // Set initial device name
      _realtimeData = _realtimeData.copyWith(
        deviceName: widget.device.platformName,
        sdkVersion: 'v3.0.4',
      );

      // Request RSSI
      await widget.device.readRssi();

      // Request device info (battery, firmware, etc.)
      await widget.service.requestDeviceInfo();

      await Future.delayed(const Duration(milliseconds: 500));

      setState(() => _isLoading = false);
    } catch (e) {
      debugPrint('Error loading initial data: $e');
      setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dashboard'),
        backgroundColor: Colors.teal,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _loadInitialData,
            tooltip: 'Aggiorna',
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : RefreshIndicator(
              onRefresh: _loadInitialData,
              child: SingleChildScrollView(
                physics: const AlwaysScrollableScrollPhysics(),
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    _buildDeviceInfoCard(),
                    const SizedBox(height: 16),
                    _buildVitalSignsCard(),
                    const SizedBox(height: 16),
                    _buildActivityCard(),
                    const SizedBox(height: 16),
                    _buildHealthMetricsCard(),
                  ],
                ),
              ),
            ),
    );
  }

  Widget _buildDeviceInfoCard() {
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
                Icon(Icons.watch, color: Colors.teal.shade700, size: 28),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        _realtimeData.deviceName ?? 'Unknown Device',
                        style: const TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      if (_realtimeData.softwareVersion != null)
                        Text(
                          'Software Version: ${_realtimeData.softwareVersion}',
                          style: TextStyle(
                            fontSize: 12,
                            color: Colors.grey.shade600,
                          ),
                        ),
                    ],
                  ),
                ),
              ],
            ),
            const Divider(height: 24),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildInfoItem(
                  'SDK',
                  _realtimeData.sdkVersion ?? 'v3.0.4',
                  Icons.code,
                  Colors.blue,
                ),
                _buildInfoItem(
                  'RSSI',
                  _realtimeData.rssi ?? '--',
                  Icons.signal_cellular_alt,
                  Colors.green,
                ),
                _buildInfoItem(
                  'Battery',
                  _realtimeData.battery != null 
                      ? '${_realtimeData.battery}%' 
                      : '--',
                  Icons.battery_charging_full,
                  Colors.orange,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildVitalSignsCard() {
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
                Icon(Icons.favorite, color: Colors.red.shade400, size: 24),
                const SizedBox(width: 8),
                const Text(
                  'Vital Signs',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Center(
              child: Column(
                children: [
                  Text(
                    _realtimeData.heartRate?.toString() ?? '--',
                    style: TextStyle(
                      fontSize: 64,
                      fontWeight: FontWeight.bold,
                      color: Colors.red.shade400,
                    ),
                  ),
                  const Text(
                    'bpm',
                    style: TextStyle(
                      fontSize: 18,
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

  Widget _buildActivityCard() {
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
                Icon(Icons.directions_run, color: Colors.blue.shade600, size: 24),
                const SizedBox(width: 8),
                const Text(
                  'Activity',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildMetricColumn(
                  'Steps',
                  _realtimeData.steps?.toString() ?? '--',
                  Icons.nordic_walking,
                  Colors.blue,
                ),
                _buildMetricColumn(
                  'Distance',
                  _realtimeData.distanceMeters != null
                      ? '${(_realtimeData.distanceMeters! / 1000).toStringAsFixed(2)}km'
                      : '--',
                  Icons.straighten,
                  Colors.green,
                ),
                _buildMetricColumn(
                  'Calories',
                  _realtimeData.calories != null
                      ? '${_realtimeData.calories!.toStringAsFixed(1)}kCal'
                      : '--',
                  Icons.local_fire_department,
                  Colors.orange,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildHealthMetricsCard() {
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
                Icon(Icons.health_and_safety, color: Colors.purple.shade400, size: 24),
                const SizedBox(width: 8),
                const Text(
                  'Health Metrics',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            const SizedBox(height: 16),
            _buildHealthRow(
              'VO2 Max',
              _realtimeData.vo2Max?.toString() ?? '--',
              Icons.air,
            ),
            const Divider(),
            _buildHealthRow(
              'Breath Rate',
              _realtimeData.breathRate != null
                  ? '${_realtimeData.breathRate}/min'
                  : '--',
              Icons.wind_power,
            ),
            const Divider(),
            _buildHealthRow(
              'Emotion Level',
              _realtimeData.emotionLevel ?? '--',
              Icons.mood,
            ),
            const Divider(),
            _buildHealthRow(
              'Stress',
              _realtimeData.stressPercent != null
                  ? '${_realtimeData.stressPercent}%'
                  : '--',
              Icons.psychology,
            ),
            const Divider(),
            _buildHealthRow(
              'Stamina',
              _realtimeData.stamina ?? '--',
              Icons.energy_savings_leaf,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoItem(String label, String value, IconData icon, Color color) {
    return Column(
      children: [
        Icon(icon, color: color, size: 32),
        const SizedBox(height: 4),
        Text(
          value,
          style: const TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
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

  Widget _buildMetricColumn(String label, String value, IconData icon, Color color) {
    return Column(
      children: [
        Icon(icon, color: color, size: 32),
        const SizedBox(height: 8),
        Text(
          value,
          style: const TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
          ),
        ),
        Text(
          label,
          style: TextStyle(
            fontSize: 14,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }

  Widget _buildHealthRow(String label, String value, IconData icon) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        children: [
          Icon(icon, color: Colors.purple.shade300, size: 24),
          const SizedBox(width: 12),
          Expanded(
            child: Text(
              label,
              style: const TextStyle(fontSize: 16),
            ),
          ),
          Text(
            value,
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }
}
