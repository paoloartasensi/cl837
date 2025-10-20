// ignore_for_file: use_build_context_synchronously

import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../models/heart_rate_config.dart';

/// Widget di test per il sistema HR completo con entrambe le modalità di allarme
class HeartRateTestWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const HeartRateTestWidget({Key? key, required this.service}) : super(key: key);

  @override
  State<HeartRateTestWidget> createState() => _HeartRateTestWidgetState();
}

class _HeartRateTestWidgetState extends State<HeartRateTestWidget> {
  int? _realtimeHR;
  HeartRateConfig? _currentConfig;
  HeartRateStatus? _currentStatus;
  
  // Form controllers
  final _ageController = TextEditingController(text: '30');
  final _minController = TextEditingController(text: '60');
  final _maxController = TextEditingController(text: '180');
  final _goalController = TextEditingController(text: '120');
  
  @override
  void initState() {
    super.initState();
    _setupStreams();
  }
  
  void _setupStreams() {
    // Listen to real-time HR
    widget.service.realtimeHRStream.listen((hr) {
      if (mounted) {
        setState(() {
          _realtimeHR = hr;
        });
      }
    });
    
    // Listen to HR configuration changes
    widget.service.hrConfigStream.listen((config) {
      if (mounted) {
        setState(() {
          _currentConfig = config;
        });
      }
    });
    
    // Listen to HR status changes
    widget.service.hrStatusStream.listen((status) {
      if (mounted) {
        setState(() {
          _currentStatus = status;
        });
      }
    });
  }
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('HR System Test'),
        backgroundColor: Colors.red.shade400,
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Real-time HR Display
            _buildRealtimeHRCard(),
            const SizedBox(height: 16),
            
            // Current Configuration Display
            _buildCurrentConfigCard(),
            const SizedBox(height: 16),
            
            // Current Status Display
            _buildCurrentStatusCard(),
            const SizedBox(height: 16),
            
            // Age-Based Configuration
            _buildAgeBasedConfigCard(),
            const SizedBox(height: 16),
            
            // Manual Configuration
            _buildManualConfigCard(),
            const SizedBox(height: 16),
            
            // Control Buttons
            _buildControlButtons(),
          ],
        ),
      ),
    );
  }
  
  Widget _buildRealtimeHRCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('💓 Real-time Heart Rate', 
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Text(
              _realtimeHR != null ? '$_realtimeHR BPM' : 'No data',
              style: TextStyle(
                fontSize: 32,
                fontWeight: FontWeight.bold,
                color: _getHRColor(_realtimeHR),
              ),
            ),
            if (_currentStatus?.shouldTriggerAlarm == true)
              Container(
                margin: const EdgeInsets.only(top: 8),
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.red.shade100,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.red),
                ),
                child: Row(
                  children: [
                    const Icon(Icons.warning, color: Colors.red),
                    const SizedBox(width: 8),
                    Text(
                      'ALARM: ${_currentStatus!.alarmState.displayName}',
                      style: const TextStyle(
                        color: Colors.red,
                        fontWeight: FontWeight.bold,
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
  
  Widget _buildCurrentConfigCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('⚙️ Current Configuration', 
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            if (_currentConfig != null) ...[
              Text('Mode: ${_currentConfig!.alarmMode.displayName}'),
              Text('Range: ${_currentConfig!.minHeartRate} - ${_currentConfig!.maxHeartRate} BPM'),
              Text('Goal: ${_currentConfig!.goalHeartRate} BPM'),
              Text('Alarm: ${_currentConfig!.alarmEnabled ? "ON" : "OFF"}'),
              if (_currentConfig!.alarmMode == HeartRateAlarmMode.ageBased && _currentConfig!.userAge != null)
                Text('Age: ${_currentConfig!.userAge} years'),
            ] else
              const Text('No configuration loaded'),
          ],
        ),
      ),
    );
  }
  
  Widget _buildCurrentStatusCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('📊 Current Status', 
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            if (_currentStatus != null) ...[
              Text('Current HR: ${_currentStatus!.currentHeartRate} BPM'),
              Text('Status: ${_currentStatus!.alarmState.displayName}'),
              Text('Should Alarm: ${_currentStatus!.shouldTriggerAlarm ? "YES" : "NO"}'),
              Text('Last Update: ${_currentStatus!.timestamp.toString().substring(11, 19)}'),
            ] else
              const Text('No status data'),
          ],
        ),
      ),
    );
  }
  
  Widget _buildAgeBasedConfigCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('👤 Age-Based Configuration', 
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _ageController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(
                      labelText: 'Age',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                const SizedBox(width: 16),
                ElevatedButton(
                  onPressed: _setAgeBasedConfig,
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.blue),
                  child: const Text('Apply Age-Based', style: TextStyle(color: Colors.white)),
                ),
              ],
            ),
            const SizedBox(height: 8),
            const Text('Calculates limits using: Max HR = 220 - age', 
                style: TextStyle(fontSize: 12, color: Colors.grey)),
          ],
        ),
      ),
    );
  }
  
  Widget _buildManualConfigCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('🎯 Manual Configuration', 
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _minController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(
                      labelText: 'Min HR',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextField(
                    controller: _maxController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(
                      labelText: 'Max HR',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextField(
                    controller: _goalController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(
                      labelText: 'Goal HR',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            ElevatedButton(
              onPressed: _setManualConfig,
              style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
              child: const Text('Apply Manual Config', style: TextStyle(color: Colors.white)),
            ),
          ],
        ),
      ),
    );
  }
  
  Widget _buildControlButtons() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('🎮 Controls', 
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                ElevatedButton(
                  onPressed: () => widget.service.startHeartRateMonitoring(),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
                  child: const Text('Start HR Monitor', style: TextStyle(color: Colors.white)),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.getHeartRateConfiguration(),
                  child: const Text('Get Config'),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.setHeartRateAlarmEnabled(true),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.orange),
                  child: const Text('Enable Alarm', style: TextStyle(color: Colors.white)),
                ),
                ElevatedButton(
                  onPressed: () => widget.service.setHeartRateAlarmEnabled(false),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.grey),
                  child: const Text('Disable Alarm', style: TextStyle(color: Colors.white)),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
  
  Color _getHRColor(int? hr) {
    if (hr == null) return Colors.grey;
    if (_currentConfig == null) return Colors.black;
    
    if (_currentConfig!.isTooLow(hr)) return Colors.blue;
    if (_currentConfig!.isTooHigh(hr)) return Colors.red;
    return Colors.green;
  }
  
  void _setAgeBasedConfig() async {
    try {
      final age = int.parse(_ageController.text);
      await widget.service.setHeartRateConfigAgeBased(
        userAge: age,
        alarmEnabled: true,
      );
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Age-based config set for age $age')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }
  
  void _setManualConfig() async {
    try {
      final min = int.parse(_minController.text);
      final max = int.parse(_maxController.text);
      final goal = int.parse(_goalController.text);
      
      await widget.service.setHeartRateConfigManual(
        minHeartRate: min,
        maxHeartRate: max,
        goalHeartRate: goal,
        alarmEnabled: true,
      );
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Manual config set: $min-$max, goal: $goal')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }
  
  @override
  void dispose() {
    _ageController.dispose();
    _minController.dispose();
    _maxController.dispose();
    _goalController.dispose();
    super.dispose();
  }
}
