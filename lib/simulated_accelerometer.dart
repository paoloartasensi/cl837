import 'dart:async';
import 'dart:math' as math;
import 'package:flutter/material.dart';

class SimulatedAccelerometerData {
  final DateTime timestamp;
  final double x;
  final double y;
  final double z;
  final double frequency;
  final double interval;

  SimulatedAccelerometerData({
    required this.timestamp,
    required this.x,
    required this.y,
    required this.z,
    required this.frequency,
    required this.interval,
  });
}

class SimulatedAccelerometerWidget extends StatefulWidget {
  final double targetFrequency;
  final void Function(SimulatedAccelerometerData) onDataReceived;

  const SimulatedAccelerometerWidget({
    Key? key,
    required this.targetFrequency,
    required this.onDataReceived,
  }) : super(key: key);

  @override
  State<SimulatedAccelerometerWidget> createState() => _SimulatedAccelerometerWidgetState();
}

class _SimulatedAccelerometerWidgetState extends State<SimulatedAccelerometerWidget> {
  Timer? _timer;
  DateTime? _lastUpdateTime;
  List<SimulatedAccelerometerData> _dataBuffer = [];
  final math.Random _random = math.Random();

  // Generazione dati simulati con movimento sinusoidale
  double _time = 0;
  static const double _amplitude = 2.0; // ampiezza in g
  static const double _frequency = 0.5; // Hz per il movimento simulato

  @override
  void initState() {
    super.initState();
    _startSimulation();
  }

  @override
  void didUpdateWidget(SimulatedAccelerometerWidget oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.targetFrequency != widget.targetFrequency) {
      _stopSimulation();
      _startSimulation();
    }
  }

  void _startSimulation() {
    final int microsecondsInterval = (1000000 / widget.targetFrequency).round();
    
    _timer = Timer.periodic(Duration(microseconds: microsecondsInterval), (timer) {
      final now = DateTime.now();
      if (_lastUpdateTime != null) {
        final actualInterval = now.difference(_lastUpdateTime!).inMicroseconds / 1000.0;
        final actualFrequency = 1000.0 / actualInterval;

        // Genera dati simulati con movimento sinusoidale + rumore
        _time += 1.0 / widget.targetFrequency;
        final baseX = _amplitude * math.sin(2 * math.pi * _frequency * _time);
        final baseY = _amplitude * math.cos(2 * math.pi * _frequency * _time);
        final baseZ = _amplitude * math.sin(2 * math.pi * _frequency * _time + math.pi/4);

        // Aggiungi rumore casuale
        final data = SimulatedAccelerometerData(
          timestamp: now,
          x: baseX + _random.nextDouble() * 0.1 - 0.05,
          y: baseY + _random.nextDouble() * 0.1 - 0.05,
          z: baseZ + _random.nextDouble() * 0.1 - 0.05,
          frequency: actualFrequency,
          interval: actualInterval,
        );

        _dataBuffer.add(data);

        // Interpolazione se necessario
        if (_dataBuffer.length >= 2) {
          final first = _dataBuffer.first;
          final last = _dataBuffer.last;
          
          // Genera un punto interpolato
          final interpolatedTime = DateTime.fromMillisecondsSinceEpoch(
            (first.timestamp.millisecondsSinceEpoch + last.timestamp.millisecondsSinceEpoch) ~/ 2
          );
          
          final interpolatedData = SimulatedAccelerometerData(
            timestamp: interpolatedTime,
            x: (first.x + last.x) / 2,
            y: (first.y + last.y) / 2,
            z: (first.z + last.z) / 2,
            frequency: actualFrequency,
            interval: actualInterval / 2,
          );

          // Emetti i dati nell'ordine corretto
          widget.onDataReceived(first);
          widget.onDataReceived(interpolatedData);
          widget.onDataReceived(last);

          _dataBuffer.clear();
        }
      }
      _lastUpdateTime = now;
    });
  }

  void _stopSimulation() {
    _timer?.cancel();
    _timer = null;
    _lastUpdateTime = null;
    _dataBuffer.clear();
  }

  @override
  void dispose() {
    _stopSimulation();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // Widget vuoto, poiché la simulazione avviene in background
    return const SizedBox();
  }
}

// Widget di controllo della frequenza
class FrequencyControlPanel extends StatefulWidget {
  final double currentFrequency;
  final ValueChanged<double> onFrequencyChanged;

  const FrequencyControlPanel({
    Key? key,
    required this.currentFrequency,
    required this.onFrequencyChanged,
  }) : super(key: key);

  @override
  State<FrequencyControlPanel> createState() => _FrequencyControlPanelState();
}

class _FrequencyControlPanelState extends State<FrequencyControlPanel> {
  final List<double> _availableFrequencies = [25, 50, 100, 200, 400];

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Sampling Frequency',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 8),
            DropdownButton<double>(
              value: widget.currentFrequency,
              isExpanded: true,
              items: _availableFrequencies.map((freq) {
                return DropdownMenuItem<double>(
                  value: freq,
                  child: Text('$freq Hz'),
                );
              }).toList(),
              onChanged: (value) {
                if (value != null) {
                  widget.onFrequencyChanged(value);
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}