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
    
    // Conversion factor: Standard ±2g range → 1g = 16384 LSB (typical for MEMS accelerometers)
    // If device is stationary: Z-axis should show ~1g (9.81 m/s²), X and Y near 0
    const double lsbPerG = 16384.0; // ±2g range (adjust if device uses ±4g = 8192, ±8g = 4096)
    
    // Convert raw int16 values to g
    final xG = sample.x / lsbPerG;
    final yG = sample.y / lsbPerG;
    final zG = sample.z / lsbPerG;
    
    // Calculate magnitude in g using proper square root
    final magnitudeG = (xG * xG + yG * yG + zG * zG) > 0 
        ? ((xG * xG + yG * yG + zG * zG).abs()).toDouble() 
        : 0.0;
    
    // Convert to m/s²
    final xMS2 = xG * 9.81;
    final yMS2 = yG * 9.81;
    final zMS2 = zG * 9.81;
    final magnitudeMS2 = magnitudeG * 9.81;
    
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
            _buildAxisRow('X', xG, xMS2, Colors.red),
            const SizedBox(height: 8),
            _buildAxisRow('Y', yG, yMS2, Colors.green),
            const SizedBox(height: 8),
            _buildAxisRow('Z', zG, zMS2, Colors.blue),
            const Divider(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text('Magnitude:', style: TextStyle(fontWeight: FontWeight.bold)),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: [
                    Text(
                      '${magnitudeG.toStringAsFixed(3)} g',
                      style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                    Text(
                      '${magnitudeMS2.toStringAsFixed(2)} m/s²',
                      style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
                    ),
                  ],
                ),
              ],
            ),
            const SizedBox(height: 8),
            Container(
              padding: const EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.amber.shade50,
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.amber.shade200),
              ),
              child: Row(
                children: [
                  Icon(Icons.info_outline, size: 16, color: Colors.amber.shade700),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      'Device stationary: Z ≈ 1.0g (9.81 m/s²), X/Y ≈ 0',
                      style: TextStyle(fontSize: 11, color: Colors.amber.shade900),
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

  Widget _buildAxisRow(String axis, double valueG, double valueMS2, Color color) {
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
                '${valueG.toStringAsFixed(3)} g',
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
              Text(
                '${valueMS2.toStringAsFixed(2)} m/s²',
                style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
              ),
            ],
          ),
        ),
        // Visual bar (scaled to ±2g range)
        Expanded(
          flex: 2,
          child: Container(
            height: 8,
            decoration: BoxDecoration(
              color: Colors.grey.shade200,
              borderRadius: BorderRadius.circular(4),
            ),
            child: FractionallySizedBox(
              alignment: valueG >= 0 ? Alignment.centerLeft : Alignment.centerRight,
              widthFactor: (valueG.abs() / 2.0).clamp(0.0, 1.0), // Bar scale: ±2g = full width
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
