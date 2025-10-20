import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../models/sport_health_data.dart';
import '../models/sensor_data.dart';

/// Advanced Features Test Screen
/// 
/// Tests all newly implemented SDK commands:
/// - Sport Health (VO2 Max, HRV, Stress, Stamina)
/// - Heart Rate Management (Min/Max/Goal, Alarms)
/// - 3D/6D Sensors (Accelerometer + Gyroscope)
/// - RR Intervals (Advanced HRV)
/// - Device Management
class AdvancedFeaturesTestScreen extends StatefulWidget {
  final ChileafExtendedService service;

  const AdvancedFeaturesTestScreen({
    super.key,
    required this.service,
  });

  @override
  State<AdvancedFeaturesTestScreen> createState() =>
      _AdvancedFeaturesTestScreenState();
}

class _AdvancedFeaturesTestScreenState
    extends State<AdvancedFeaturesTestScreen> {
  // Sport Health Data
  SportHealthData? _latestSportHealth;
  bool _healthMonitoring = false;

  // Heart Rate Management
  final int _hrMin = 60;
  final int _hrMax = 180;
  final int _hrGoal = 120;
  bool _hrAlarmEnabled = false;
  int _hrMaxByAge = 185;

  // Sensor Data
  Sensor3DFrequency _sensor3DFreq = Sensor3DFrequency.hz25;
  Sensor6DFrequency _sensor6DFreq = Sensor6DFrequency.hz26;
  bool _sensor3DEnabled = false;
  final List<Sensor6DRawData> _sensor6DData = [];

  // RR Intervals
  List<RRIntervalData> _rrIntervals = [];

  // Button Presses
  final List<SingleButtonPress> _buttonPresses = [];

  @override
  void initState() {
    super.initState();
    _setupListeners();
  }

  void _setupListeners() {
    // Sport Health
    widget.service.sportHealthStream.listen((data) {
      if (mounted) {
        setState(() {
          _latestSportHealth = data;
        });
      }
    });

    // Heart Rate Management
    widget.service.hrAlarmStream.listen((alarm) {
      if (mounted) {
        setState(() {
          _hrAlarmEnabled = alarm.enabled;
        });
      }
    });

    widget.service.hrMaxStream.listen((hrMax) {
      if (mounted) {
        setState(() {
          _hrMaxByAge = hrMax.max;
        });
      }
    });

    // Sensors
    widget.service.sensor3DFrequencyStream.listen((freq) {
      if (mounted) {
        setState(() {
          _sensor3DFreq = freq;
        });
      }
    });

    widget.service.sensor6DFrequencyStream.listen((freq) {
      if (mounted) {
        setState(() {
          _sensor6DFreq = freq;
        });
      }
    });

    widget.service.sensor6DDataStream.listen((data) {
      if (mounted) {
        setState(() {
          _sensor6DData.add(data);
          if (_sensor6DData.length > 100) {
            _sensor6DData.removeAt(0); // Keep last 100 samples
          }
        });
      }
    });

    // RR Intervals
    widget.service.rrIntervalStream.listen((intervals) {
      if (mounted) {
        setState(() {
          _rrIntervals = intervals;
        });
      }
    });

    // Button Presses
    widget.service.buttonPressStream.listen((press) {
      if (mounted) {
        setState(() {
          _buttonPresses.add(press);
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Advanced Features Test'),
        backgroundColor: Colors.deepPurple,
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          _buildSportHealthSection(),
          const SizedBox(height: 24),
          _buildHeartRateManagementSection(),
          const SizedBox(height: 24),
          _buildSensorsSection(),
          const SizedBox(height: 24),
          _buildRRIntervalsSection(),
          const SizedBox(height: 24),
          _buildDeviceManagementSection(),
        ],
      ),
    );
  }

  Widget _buildSportHealthSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '🏃 Sport Health Data',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            
            // Display current data
            if (_latestSportHealth != null) ...[
              _buildDataRow('VO2 Max', '${_latestSportHealth!.vo2Max} ml/kg/min'),
              _buildDataRow('Breath Rate', '${_latestSportHealth!.breathRate} breaths/min'),
              _buildDataRow('Emotion', _latestSportHealth!.emotionDescription),
              _buildDataRow('Stress', '${_latestSportHealth!.stressPercent}% (${_latestSportHealth!.stressLevel})'),
              _buildDataRow('Stamina', _latestSportHealth!.staminaDescription),
              if (_latestSportHealth!.totalPower != null)
                _buildDataRow('HRV TP', '${_latestSportHealth!.totalPower!.toStringAsFixed(2)} ms²'),
              if (_latestSportHealth!.lowFrequency != null)
                _buildDataRow('HRV LF', '${_latestSportHealth!.lowFrequency!.toStringAsFixed(2)} ms²'),
              if (_latestSportHealth!.highFrequency != null)
                _buildDataRow('HRV HF', '${_latestSportHealth!.highFrequency!.toStringAsFixed(2)} ms²'),
              if (_latestSportHealth!.lfHfRatio != null)
                _buildDataRow('LF/HF Ratio', _latestSportHealth!.lfHfRatio!.toStringAsFixed(2)),
            ] else
              const Text('No data yet', style: TextStyle(color: Colors.grey)),
            
            const SizedBox(height: 12),
            
            // Control buttons
            Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: () => widget.service.getBodyHealth(),
                    icon: const Icon(Icons.download),
                    label: const Text('Get Data'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: () {
                      if (_healthMonitoring) {
                        widget.service.stopHealthMonitoring();
                      } else {
                        widget.service.startHealthMonitoring();
                      }
                      setState(() => _healthMonitoring = !_healthMonitoring);
                    },
                    icon: Icon(_healthMonitoring ? Icons.stop : Icons.play_arrow),
                    label: Text(_healthMonitoring ? 'Stop' : 'Start'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _healthMonitoring ? Colors.red : Colors.green,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildHeartRateManagementSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '❤️ Heart Rate Management',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            
            // Current settings
            _buildDataRow('Min HR', '$_hrMin BPM'),
            _buildDataRow('Max HR', '$_hrMax BPM'),
            _buildDataRow('Goal HR', '$_hrGoal BPM'),
            _buildDataRow('Alarm', _hrAlarmEnabled ? 'Enabled' : 'Disabled'),
            _buildDataRow('Max by Age', '$_hrMaxByAge BPM'),
            
            const SizedBox(height: 12),
            
            // Control buttons
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                ElevatedButton(
                  onPressed: () => widget.service.getHeartRateStatus(),
                  child: const Text('Get Status'),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.setHeartRateStatus(_hrMin, _hrMax, _hrGoal),
                  child: const Text('Set Status'),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.getHeartRateAlarm(),
                  child: const Text('Get Alarm'),
                ),
                ElevatedButton(
                  onPressed: () {
                    widget.service.setHeartRateAlarm(!_hrAlarmEnabled);
                  },
                  child: Text(_hrAlarmEnabled ? 'Disable Alarm' : 'Enable Alarm'),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.getHeartRateMax(),
                  child: const Text('Get Max'),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.setHeartRateMax(_hrMaxByAge),
                  child: const Text('Set Max'),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSensorsSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '📡 Sensors (3D/6D)',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            
            // 3D Sensor
            _buildDataRow('3D Status', _sensor3DEnabled ? 'Enabled' : 'Disabled'),
            _buildDataRow('3D Frequency', _sensor3DFreq.label),
            
            const SizedBox(height: 8),
            
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => widget.service.get3DFrequency(),
                    child: const Text('Get 3D Freq'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => widget.service.get3DStatus(),
                    child: const Text('Get 3D Status'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {
                      widget.service.set3DEnabled(!_sensor3DEnabled);
                      setState(() => _sensor3DEnabled = !_sensor3DEnabled);
                    },
                    child: Text(_sensor3DEnabled ? 'Disable' : 'Enable'),
                  ),
                ),
              ],
            ),
            
            const Divider(height: 24),
            
            // 6D Sensor
            _buildDataRow('6D Frequency', _sensor6DFreq.label),
            _buildDataRow('6D Samples', '${_sensor6DData.length}'),
            
            if (_sensor6DData.isNotEmpty) ...[
              const SizedBox(height: 8),
              const Text('Latest 6D Data:', style: TextStyle(fontWeight: FontWeight.bold)),
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Text(
                  _sensor6DData.last.toString(),
                  style: const TextStyle(fontFamily: 'monospace', fontSize: 11),
                ),
              ),
            ],
            
            const SizedBox(height: 8),
            
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => widget.service.get6DFrequency(),
                    child: const Text('Get 6D Freq'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => setState(() => _sensor6DData.clear()),
                    child: const Text('Clear Data'),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildRRIntervalsSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '💓 RR Intervals (HRV Analysis)',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            
            _buildDataRow('Intervals', '${_rrIntervals.length}'),
            
            if (_rrIntervals.isNotEmpty) ...[
              const SizedBox(height: 8),
              const Text('Latest RR Intervals:', style: TextStyle(fontWeight: FontWeight.bold)),
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                  borderRadius: BorderRadius.circular(8),
                ),
                height: 150,
                child: ListView.builder(
                  itemCount: _rrIntervals.length > 10 ? 10 : _rrIntervals.length,
                  itemBuilder: (context, index) {
                    final interval = _rrIntervals[_rrIntervals.length - 1 - index];
                    return Text(
                      '${interval.interval} ms (${interval.heartRate} BPM)',
                      style: const TextStyle(fontFamily: 'monospace', fontSize: 11),
                    );
                  },
                ),
              ),
            ],
            
            const SizedBox(height: 8),
            
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => widget.service.getRRIntervalsHistory(),
                    child: const Text('Get RR History'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => setState(() => _rrIntervals.clear()),
                    child: const Text('Clear'),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDeviceManagementSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '🔧 Device Management',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            
            _buildDataRow('Button Presses', '${_buttonPresses.length}'),
            
            if (_buttonPresses.isNotEmpty) ...[
              const SizedBox(height: 8),
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                  borderRadius: BorderRadius.circular(8),
                ),
                height: 100,
                child: ListView.builder(
                  itemCount: _buttonPresses.length > 5 ? 5 : _buttonPresses.length,
                  itemBuilder: (context, index) {
                    final press = _buttonPresses[_buttonPresses.length - 1 - index];
                    return Text(
                      press.toString(),
                      style: const TextStyle(fontFamily: 'monospace', fontSize: 11),
                    );
                  },
                ),
              ),
            ],
            
            const SizedBox(height: 12),
            
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                ElevatedButton(
                  onPressed: () => widget.service.getSingleButtonHistory(),
                  child: const Text('Get Button History'),
                ),
                ElevatedButton(
                  onPressed: () => _showConfirmDialog(
                    'Factory Restoration',
                    'This will reset the device to factory settings. Continue?',
                    () => widget.service.factoryRestoration(),
                  ),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.orange),
                  child: const Text('Factory Reset'),
                ),
                ElevatedButton(
                  onPressed: () => _showConfirmDialog(
                    'Shutdown Device',
                    'This will power off the device. Continue?',
                    () => widget.service.shutdownDevice(),
                  ),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
                  child: const Text('Shutdown'),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDataRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: const TextStyle(fontWeight: FontWeight.w500)),
          Text(value, style: const TextStyle(color: Colors.blue)),
        ],
      ),
    );
  }

  Future<void> _showConfirmDialog(
    String title,
    String message,
    VoidCallback onConfirm,
  ) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(title),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
            child: const Text('Confirm'),
          ),
        ],
      ),
    );

    if (confirmed == true) {
      onConfirm();
    }
  }
}
