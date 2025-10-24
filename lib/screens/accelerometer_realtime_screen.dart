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
  
  double _ax = 0.0;
  double _ay = 0.0;
  double _az = 0.0;
  int _sampleCount = 0;

  @override
  void initState() {
    super.initState();
    _initAccelerometer();
  }

  Future<void> _initAccelerometer() async {
    // Enable 3D accelerometer sensor at 50Hz
    debugPrint('📊 Enabling 3D accelerometer at 50Hz...');
    try {
      await widget.service.set3DEnabled(true);
      await Future.delayed(const Duration(milliseconds: 500));
      await widget.service.set3DFrequency(Sensor3DFrequency.hz50.value);
      await Future.delayed(const Duration(milliseconds: 500));
      debugPrint('✅ 3D accelerometer enabled');
    } catch (e) {
      debugPrint('❌ Error enabling accelerometer: $e');
    }
    
    // Setup stream listener
    _setupAccelerometerStream();
  }

  void _setupAccelerometerStream() {
    _accelSubscription = widget.service.accelerometer3DStream.listen(
      (samples) {
        if (samples.isNotEmpty) {
          final sample = samples.last;
          setState(() {
            _ax = sample.x;
            _ay = sample.y;
            _az = sample.z;
            _sampleCount += samples.length;
          });
        }
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
        title: const Text('3D Accelerometer'),
        backgroundColor: Colors.blue.shade700,
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // Sample count
              Text(
                'Samples: $_sampleCount',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.grey.shade600,
                ),
              ),
              const SizedBox(height: 40),
              
              // AX
              _buildAxisCard('AX', _ax, Colors.red),
              const SizedBox(height: 20),
              
              // AY
              _buildAxisCard('AY', _ay, Colors.green),
              const SizedBox(height: 20),
              
              // AZ
              _buildAxisCard('AZ', _az, Colors.blue),
              
              const SizedBox(height: 40),
              
              // Info
              Container(
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.amber.shade50,
                  borderRadius: BorderRadius.circular(12),
                  border: Border.all(color: Colors.amber.shade200),
                ),
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Icon(Icons.info_outline, size: 20, color: Colors.amber.shade700),
                    const SizedBox(width: 12),
                    Flexible(
                      child: Text(
                        'Device stationary: AZ ≈ 1.0g, AX/AY ≈ 0g',
                        style: TextStyle(
                          fontSize: 13,
                          color: Colors.amber.shade900,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildAxisCard(String label, double value, Color color) {
    return Card(
      elevation: 4,
      child: Container(
        width: double.infinity,
        padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 24),
        child: Column(
          children: [
            // Label
            Text(
              label,
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
            const SizedBox(height: 12),
            
            // Value in g
            Text(
              '${value.toStringAsFixed(3)} g',
              style: const TextStyle(
                fontSize: 48,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 8),
            
            // Value in m/s²
            Text(
              '${(value * 9.81).toStringAsFixed(2)} m/s²',
              style: TextStyle(
                fontSize: 16,
                color: Colors.grey.shade600,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
