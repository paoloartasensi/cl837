import 'package:flutter/material.dart';
import 'dart:async';
import '../chileaf_extended_service.dart';
import '../models/sensor_data.dart';

/// Real-time 3D Accelerometer Data Visualization Screen
/// Displays X, Y, Z acceleration values at 50Hz
class AccelerometerRealtimeScreen extends StatefulWidget {
  final ChileafExtendedService service;

  const AccelerometerRealtimeScreen({
    super.key,
    required this.service,
  });

  @override
  State<AccelerometerRealtimeScreen> createState() => _AccelerometerRealtimeScreenState();
}

class _AccelerometerRealtimeScreenState extends State<AccelerometerRealtimeScreen> {
  StreamSubscription<List<AccelerometerData>>? _accelSubscription;
  
  AccelerometerData? _latestSample;
  List<AccelerometerData> _recentSamples = [];
  int _totalSamplesReceived = 0;
  DateTime? _lastUpdateTime;
  double _currentFrequency = 0.0;
  
  final int _maxSamplesToShow = 20;

  @override
  void initState() {
    super.initState();
    _setupAccelerometerStream();
  }

  void _setupAccelerometerStream() {
    _accelSubscription = widget.service.accelerometer3DStream.listen(
      (samples) {
        setState(() {
          _totalSamplesReceived += samples.length;
          
          // Update frequency calculation
          if (_lastUpdateTime != null) {
            final timeDiff = DateTime.now().difference(_lastUpdateTime!).inMilliseconds;
            if (timeDiff > 0) {
              _currentFrequency = (samples.length / timeDiff) * 1000;
            }
          }
          _lastUpdateTime = DateTime.now();
          
          // Store latest sample
          if (samples.isNotEmpty) {
            _latestSample = samples.last;
          }
          
          // Keep recent samples for display
          _recentSamples.addAll(samples);
          if (_recentSamples.length > _maxSamplesToShow) {
            _recentSamples = _recentSamples.sublist(_recentSamples.length - _maxSamplesToShow);
          }
        });
      },
      onError: (error) {
        debugPrint('❌ Accelerometer stream error: $error');
      },
    );
  }

  @override
  void dispose() {
    _accelSubscription?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('3D Accelerometer (50Hz)'),
        backgroundColor: Colors.blue.shade700,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              setState(() {
                _recentSamples.clear();
                _totalSamplesReceived = 0;
                _currentFrequency = 0.0;
              });
            },
            tooltip: 'Clear data',
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Status Card
            _buildStatusCard(),
            const SizedBox(height: 16),
            
            // Current Values Card
            if (_latestSample != null) _buildCurrentValuesCard(),
            const SizedBox(height: 16),
            
            // Recent Samples List
            _buildRecentSamplesCard(),
          ],
        ),
      ),
    );
  }

  Widget _buildStatusCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '📊 Stream Status',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const Divider(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('Total Samples:', style: TextStyle(color: Colors.grey.shade600)),
                    Text(
                      _totalSamplesReceived.toString(),
                      style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: [
                    Text('Frequency:', style: TextStyle(color: Colors.grey.shade600)),
                    Text(
                      '${_currentFrequency.toStringAsFixed(1)} Hz',
                      style: TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                        color: _currentFrequency > 40 ? Colors.green : Colors.orange,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildCurrentValuesCard() {
    final sample = _latestSample!;
    
    // Calculate magnitude
    final magnitude = (sample.x * sample.x + sample.y * sample.y + sample.z * sample.z).toDouble();
    final magnitudeG = magnitude / 1000; // Convert mg to g
    
    return Card(
      color: Colors.blue.shade50,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '📍 Current Values',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const Divider(),
            _buildAxisRow('X', sample.x, Colors.red),
            const SizedBox(height: 8),
            _buildAxisRow('Y', sample.y, Colors.green),
            const SizedBox(height: 8),
            _buildAxisRow('Z', sample.z, Colors.blue),
            const Divider(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text('Magnitude:', style: TextStyle(fontWeight: FontWeight.bold)),
                Text(
                  '${magnitudeG.toStringAsFixed(2)} g',
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildAxisRow(String axis, double value, Color color) {
    final valueG = value / 1000; // Convert mg to g
    
    return Row(
      children: [
        Container(
          width: 30,
          height: 30,
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(4),
          ),
          alignment: Alignment.center,
          child: Text(
            axis,
            style: const TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 16,
            ),
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                '${value.toInt()} mg',
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
              Text(
                '${valueG.toStringAsFixed(3)} g',
                style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
              ),
            ],
          ),
        ),
        // Visual bar
        Expanded(
          flex: 2,
          child: Container(
            height: 8,
            decoration: BoxDecoration(
              color: Colors.grey.shade200,
              borderRadius: BorderRadius.circular(4),
            ),
            child: FractionallySizedBox(
              alignment: value >= 0 ? Alignment.centerLeft : Alignment.centerRight,
              widthFactor: (value.abs() / 2000).clamp(0.0, 1.0),
              child: Container(
                decoration: BoxDecoration(
                  color: color,
                  borderRadius: BorderRadius.circular(4),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildRecentSamplesCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '📜 Recent Samples (${_recentSamples.length})',
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const Divider(),
            if (_recentSamples.isEmpty)
              const Center(
                child: Padding(
                  padding: EdgeInsets.all(32.0),
                  child: Text(
                    'No data received yet.\nMake sure 3D sensor is enabled at 50Hz.',
                    textAlign: TextAlign.center,
                    style: TextStyle(color: Colors.grey),
                  ),
                ),
              )
            else
              ListView.builder(
                shrinkWrap: true,
                physics: const NeverScrollableScrollPhysics(),
                itemCount: _recentSamples.length,
                itemBuilder: (context, index) {
                  final sample = _recentSamples[_recentSamples.length - 1 - index];
                  return ListTile(
                    dense: true,
                    leading: CircleAvatar(
                      backgroundColor: Colors.blue.shade100,
                      child: Text(
                        '${_recentSamples.length - index}',
                        style: const TextStyle(fontSize: 12),
                      ),
                    ),
                    title: Text(
                      'X:${sample.x.toInt()} Y:${sample.y.toInt()} Z:${sample.z.toInt()}',
                      style: const TextStyle(fontFamily: 'monospace'),
                    ),
                    subtitle: Text(
                      'Magnitude: ${((sample.x * sample.x + sample.y * sample.y + sample.z * sample.z) / 1000).toStringAsFixed(2)} g',
                      style: TextStyle(fontSize: 11, color: Colors.grey.shade600),
                    ),
                  );
                },
              ),
          ],
        ),
      ),
    );
  }
}
