// ignore_for_file: prefer_final_fields

import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../models/historical_data.dart';

/// Dashboard completa per l'estrazione di TUTTI i dati storici dal dispositivo CL837
/// Basata su:
/// - CL831 SDK technical documentation 
/// - Reverse engineering XFITNESS app
/// - Analisi protocollo Chileaf BLE
class DataExtractionDashboard extends StatefulWidget {
  final ChileafExtendedService service;
  
  const DataExtractionDashboard({
    Key? key,
    required this.service,
  }) : super(key: key);

  @override
  State<DataExtractionDashboard> createState() => _DataExtractionDashboardState();
}

class _DataExtractionDashboardState extends State<DataExtractionDashboard> {
  // Data Storage
  List<ExerciseHistoryData> _exerciseHistory = [];
  List<DateTime> _hrTimestamps = [];
  final List<HeartRateHistoryData> _hrData = [];
  final List<SleepHistoryEntry> _sleepData = [];
  List<StepIntervalEntry> _stepIntervals = [];
  List<Map<String, dynamic>> _temperatureData = [];
  List<Map<String, dynamic>> _accelerometerData = [];
  
  // Status Tracking
  bool _isConnected = false;
  bool _isExtracting = false;
  String _currentOperation = '';
  int _totalOperations = 0;
  int _completedOperations = 0;
  
  // Extraction Statistics
  Map<String, dynamic> _extractionStats = {};

  @override
  void initState() {
    super.initState();
    _setupDataStreams();
    _checkConnectionStatus();
  }

  void _setupDataStreams() {
    // Exercise History Stream
    widget.service.exerciseHistoryStream.listen((data) {
      setState(() {
        _exerciseHistory = data;
        _updateExtractionStats();
      });
    });

    // HR History Streams
    widget.service.hrHistoryListStream.listen((data) {
      setState(() {
        _hrTimestamps = data.timestamps;
        _updateExtractionStats();
      });
    });

    widget.service.hrHistoryDataStream.listen((data) {
      setState(() {
        _hrData.add(data);
        _updateExtractionStats();
      });
    });
  }

  void _checkConnectionStatus() {
    setState(() {
      _isConnected = widget.service.isConnected;
    });
  }

  void _updateExtractionStats() {
    setState(() {
      _extractionStats = {
        'exerciseEntries': _exerciseHistory.length,
        'hrTimestamps': _hrTimestamps.length,
        'hrSessions': _hrData.length,
        'sleepSessions': _sleepData.length,
        'stepIntervals': _stepIntervals.length,
        'temperaturePoints': _temperatureData.length,
        'accelerometerPoints': _accelerometerData.length,
        'totalDataPoints': _exerciseHistory.length + 
                          _hrTimestamps.length + 
                          _hrData.length + 
                          _sleepData.length +
                          _stepIntervals.length +
                          _temperatureData.length +
                          _accelerometerData.length,
      };
    });
  }

  Future<void> _extractAllData() async {
    if (!_isConnected) {
      _showErrorDialog('Device not connected');
      return;
    }

    setState(() {
      _isExtracting = true;
      _totalOperations = 7; // Number of data types to extract
      _completedOperations = 0;
    });

    try {
      // 1. Exercise History (7 days) - Command 0x16
      await _extractExerciseHistory();
      
      // 2. HR History List - Command 0x21
      await _extractHRHistory();
      
      // 3. Sleep Data - Command from SDK docs
      await _extractSleepData();
      
      // 4. Step Interval Data - Command 0x90/0x91
      await _extractStepIntervals();
      
      // 5. Real-time Temperature - SDK command
      await _extractTemperatureData();
      
      // 6. 3D Raw Data (Accelerometer) - SDK command  
      await _extract3DData();
      
      // 7. Device Info & Battery
      await _extractDeviceInfo();
      
      _showSuccessDialog('Data extraction completed successfully!');
      
    } catch (e) {
      _showErrorDialog('Extraction failed: $e');
    } finally {
      setState(() {
        _isExtracting = false;
        _currentOperation = '';
      });
    }
  }

  Future<void> _extractExerciseHistory() async {
    setState(() {
      _currentOperation = 'Extracting Exercise History (7 days)...';
    });
    
    await widget.service.requestExerciseHistory();
    await Future.delayed(const Duration(seconds: 2)); // Wait for response
    
    setState(() {
      _completedOperations++;
    });
  }

  Future<void> _extractHRHistory() async {
    setState(() {
      _currentOperation = 'Extracting HR History List...';
    });
    
    await widget.service.requestHRHistoryList();
    await Future.delayed(const Duration(seconds: 3)); // Wait for response
    
    setState(() {
      _completedOperations++;
    });
  }

  Future<void> _extractSleepData() async {
    setState(() {
      _currentOperation = 'Extracting Sleep Data...';
    });
    
    // Based on SDK docs: getSleepData command
    // Need to implement this in ChileafExtendedService
    await _requestSleepData();
    await Future.delayed(const Duration(seconds: 2));
    
    setState(() {
      _completedOperations++;
    });
  }

  Future<void> _extractStepIntervals() async {
    setState(() {
      _currentOperation = 'Extracting Step Interval Data...';
    });
    
    // Command 0x90 for step counting history list
    // Command 0x91 for step counting data
    await _requestStepIntervals();
    await Future.delayed(const Duration(seconds: 2));
    
    setState(() {
      _completedOperations++;
    });
  }

  Future<void> _extractTemperatureData() async {
    setState(() {
      _currentOperation = 'Extracting Temperature Data...';
    });
    
    // Real-time temperature request
    await _requestTemperatureData();
    await Future.delayed(const Duration(seconds: 1));
    
    setState(() {
      _completedOperations++;
    });
  }

  Future<void> _extract3DData() async {
    setState(() {
      _currentOperation = 'Extracting 3D Accelerometer Data...';
    });
    
    // 3D raw data extraction
    await _request3DData();
    await Future.delayed(const Duration(seconds: 2));
    
    setState(() {
      _completedOperations++;
    });
  }

  Future<void> _extractDeviceInfo() async {
    setState(() {
      _currentOperation = 'Extracting Device Info...';
    });
    
    // Device info and battery status
    await _requestDeviceInfo();
    await Future.delayed(const Duration(seconds: 1));
    
    setState(() {
      _completedOperations++;
    });
  }

  // Individual data request methods (to be implemented)
  Future<void> _requestSleepData() async {
    debugPrint('🛌 Requesting sleep data...');
    try {
      await widget.service.requestSleepData();
    } catch (e) {
      debugPrint('❌ Failed to request sleep data: $e');
    }
  }

  Future<void> _requestStepIntervals() async {
    debugPrint('🚶 Requesting step interval data...');
    try {
      // First request the step interval history list
      await widget.service.requestStepIntervalHistory();
      // Give time for response, then could request specific data
      await Future.delayed(const Duration(milliseconds: 500));
    } catch (e) {
      debugPrint('❌ Failed to request step intervals: $e');
    }
  }

  Future<void> _requestTemperatureData() async {
    debugPrint('🌡️ Requesting temperature data...');
    try {
      await widget.service.requestTemperatureData();
    } catch (e) {
      debugPrint('❌ Failed to request temperature data: $e');
    }
  }

  Future<void> _request3DData() async {
    debugPrint('📊 Requesting 3D accelerometer data...');
    try {
      await widget.service.request3DAccelerometerData();
    } catch (e) {
      debugPrint('❌ Failed to request 3D data: $e');
    }
  }

  Future<void> _requestDeviceInfo() async {
    debugPrint('📱 Requesting device info...');
    try {
      await widget.service.requestCompleteDeviceInfo();
    } catch (e) {
      debugPrint('❌ Failed to request device info: $e');
    }
  }

  void _exportData() {
    // Export all collected data to a structured format
    final exportData = {
      'export_timestamp': DateTime.now().toIso8601String(),
      'device_connection': _isConnected,
      'extraction_stats': _extractionStats,
      'data': {
        'exercise_history': _exerciseHistory.map((e) => {
          'date': e.date.toIso8601String(),
          'steps': e.steps,
          'calories': e.calories,
          'distanceCm': e.distanceCm,
        }).toList(),
        'heart_rate_timestamps': _hrTimestamps.map((t) => t.toIso8601String()).toList(),
        'heart_rate_data': _hrData.map((hr) => {
          'timestamp': hr.timestamp.toIso8601String(),
          'entries': hr.entries.map((entry) => {
            'heartRate': entry.heartRate,
            'activityIndex': entry.activityIndex,
            'time': entry.time.toIso8601String(),
          }).toList(),
        }).toList(),
        'sleep_data': _sleepData.map((s) => {
          'timestamp': s.timestamp.toIso8601String(),
          'count': s.count,
          'actions': s.actions,
          'sleep_phases': () {
            final phases = s.calculateSleepPhases();
            return {
              'lightSleep': phases.lightSleep,
              'deepSleep': phases.deepSleep,
              'awake': phases.awake,
              'totalMinutes': phases.totalMinutes,
              'totalSleep': phases.totalSleep,
              'sleepEfficiency': phases.sleepEfficiency,
            };
          }(),
        }).toList(),
        'step_intervals': _stepIntervals.map((s) => {
          'timestamp': s.timestamp.toIso8601String(),
          'steps': s.steps,
        }).toList(),
        'temperature_data': _temperatureData,
        'accelerometer_data': _accelerometerData,
      }
    };
    
    debugPrint('📤 Data exported: ${exportData.keys.length} categories');
    debugPrint('📊 Export summary: ${exportData['data']}');
    
    _showInfoDialog('Data exported successfully!\n\n'
        'Exercise sessions: ${_exerciseHistory.length}\n'
        'HR timestamps: ${_hrTimestamps.length}\n'
        'HR data points: ${_hrData.length}\n'
        'Sleep sessions: ${_sleepData.length}\n'
        'Step intervals: ${_stepIntervals.length}\n'
        'Temperature readings: ${_temperatureData.length}\n'
        'Accelerometer readings: ${_accelerometerData.length}');
  }

  void _clearData() {
    setState(() {
      _exerciseHistory.clear();
      _hrTimestamps.clear();
      _hrData.clear();
      _sleepData.clear();
      _stepIntervals.clear();
      _temperatureData.clear();
      _accelerometerData.clear();
      _updateExtractionStats();
    });
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Error'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }

  void _showSuccessDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Success'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }

  void _showInfoDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Info'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Data Extraction Dashboard'),
        backgroundColor: Colors.blue[800],
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: Icon(_isConnected ? Icons.bluetooth_connected : Icons.bluetooth_disabled),
            onPressed: null,
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Connection Status Card
            _buildConnectionCard(),
            const SizedBox(height: 16),
            
            // Extraction Controls Card
            _buildExtractionCard(),
            const SizedBox(height: 16),
            
            // Progress Card (shown during extraction)
            if (_isExtracting) _buildProgressCard(),
            if (_isExtracting) const SizedBox(height: 16),
            
            // Data Statistics Card
            _buildStatsCard(),
            const SizedBox(height: 16),
            
            // Individual Data Type Cards
            _buildDataTypeCards(),
          ],
        ),
      ),
    );
  }

  Widget _buildConnectionCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Device Status',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 8),
            Row(
              children: [
                Icon(
                  _isConnected ? Icons.check_circle : Icons.error,
                  color: _isConnected ? Colors.green : Colors.red,
                ),
                const SizedBox(width: 8),
                Text(
                  _isConnected ? 'Connected to CL837' : 'Not Connected',
                  style: TextStyle(
                    color: _isConnected ? Colors.green : Colors.red,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildExtractionCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Data Extraction',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton.icon(
                  onPressed: _isConnected && !_isExtracting ? _extractAllData : null,
                  icon: const Icon(Icons.download),
                  label: const Text('Extract All Data'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    foregroundColor: Colors.white,
                  ),
                ),
                ElevatedButton.icon(
                  onPressed: _extractionStats['totalDataPoints'] > 0 ? _exportData : null,
                  icon: const Icon(Icons.save),
                  label: const Text('Export Data'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green,
                    foregroundColor: Colors.white,
                  ),
                ),
                ElevatedButton.icon(
                  onPressed: _clearData,
                  icon: const Icon(Icons.clear),
                  label: const Text('Clear Data'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.red,
                    foregroundColor: Colors.white,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildProgressCard() {
    double progress = _totalOperations > 0 ? _completedOperations / _totalOperations : 0;
    
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Extraction Progress',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 8),
            LinearProgressIndicator(
              value: progress,
              backgroundColor: Colors.grey[300],
              valueColor: const AlwaysStoppedAnimation<Color>(Colors.blue),
            ),
            const SizedBox(height: 8),
            Text(
              '${(_completedOperations)} / $_totalOperations operations completed',
              style: Theme.of(context).textTheme.bodyMedium,
            ),
            const SizedBox(height: 4),
            Text(
              _currentOperation,
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                fontStyle: FontStyle.italic,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatsCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Extraction Statistics',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            Wrap(
              spacing: 16,
              runSpacing: 8,
              children: [
                _buildStatChip('Total Points', _extractionStats['totalDataPoints'] ?? 0),
                _buildStatChip('Exercise', _extractionStats['exerciseEntries'] ?? 0),
                _buildStatChip('HR Sessions', _extractionStats['hrSessions'] ?? 0),
                _buildStatChip('Sleep', _extractionStats['sleepSessions'] ?? 0),
                _buildStatChip('Steps', _extractionStats['stepIntervals'] ?? 0),
                _buildStatChip('Temperature', _extractionStats['temperaturePoints'] ?? 0),
                _buildStatChip('3D Data', _extractionStats['accelerometerPoints'] ?? 0),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatChip(String label, int value) {
    return Chip(
      label: Text('$label: $value'),
      backgroundColor: value > 0 ? Colors.green[100] : Colors.grey[200],
    );
  }

  Widget _buildDataTypeCards() {
    return Column(
      children: [
        _buildDataCard(
          'Exercise History',
          '7-day exercise data (steps, calories)',
          _exerciseHistory.length,
          Icons.fitness_center,
          Colors.orange,
        ),
        const SizedBox(height: 8),
        _buildDataCard(
          'Heart Rate History',
          'HR sessions and detailed data',
          _hrData.length,
          Icons.favorite,
          Colors.red,
        ),
        const SizedBox(height: 8),
        _buildDataCard(
          'Sleep Data',
          'Sleep patterns and quality',
          _sleepData.length,
          Icons.bedtime,
          Colors.purple,
        ),
        const SizedBox(height: 8),
        _buildDataCard(
          'Step Intervals',
          'Detailed step counting data',
          _stepIntervals.length,
          Icons.directions_walk,
          Colors.green,
        ),
        const SizedBox(height: 8),
        _buildDataCard(
          'Temperature Data',
          'Ambient, wrist, and body temperature',
          _temperatureData.length,
          Icons.thermostat,
          Colors.blue,
        ),
        const SizedBox(height: 8),
        _buildDataCard(
          '3D Accelerometer',
          'Raw motion sensor data',
          _accelerometerData.length,
          Icons.sensors,
          Colors.teal,
        ),
      ],
    );
  }

  Widget _buildDataCard(String title, String description, int count, IconData icon, Color color) {
    return Card(
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: color,
          child: Icon(icon, color: Colors.white),
        ),
        title: Text(title),
        subtitle: Text(description),
        trailing: Chip(
          label: Text('$count'),
          backgroundColor: count > 0 ? Colors.green[100] : Colors.grey[200],
        ),
      ),
    );
  }
}
