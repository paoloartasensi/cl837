// ignore_for_file: unused_element

import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'dart:math';
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:intl/intl.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:path_provider/path_provider.dart';
import 'package:share_plus/share_plus.dart';
import '../chileaf_extended_service.dart';
import '../models/historical_data.dart';

class GrokHrScreen extends StatefulWidget {
  const GrokHrScreen({super.key});

  @override
  State<GrokHrScreen> createState() => _GrokHrScreenState();
}

class _GrokHrScreenState extends State<GrokHrScreen> {
  final ChileafExtendedService _service = ChileafExtendedService();
  
  // BLE Connection variables
  BluetoothDevice? connectedDevice;
  List<BluetoothDevice> foundDevices = [];
  bool isScanning = false;
  bool isConnecting = false;
  String connectionStatus = 'Disconnected';
  
  // HR Data variables
  List<HeartRateHistoryData> _hrHistoryData = [];
  List<DateTime> _hrRecordList = [];
  List<int> _hrRawTimestamps = []; // Store raw timestamps for HR data requests
  int? _selectedTimestamp; // Currently selected HR session timestamp
  
  // Sleep and Steps data variables
  final List<SleepHistoryEntry> _sleepHistoryData = [];
  final List<StepIntervalEntry> _stepsHistoryData = [];
  
  bool _isDownloading = false;
  String _statusMessage = 'Ready to connect';
  StreamSubscription? _hrDataSubscription;
  StreamSubscription? _hrListSubscription;
  StreamSubscription? _sleepDataSubscription;
  StreamSubscription? _stepsDataSubscription;
  StreamSubscription<BluetoothConnectionState>? _connectionSubscription;

  @override
  void initState() {
    super.initState();
    _initializeBluetooth();
    _setupStreams();
  }

  @override
  void dispose() {
    _hrDataSubscription?.cancel();
    _hrListSubscription?.cancel();
    _sleepDataSubscription?.cancel();
    _stepsDataSubscription?.cancel();
    _connectionSubscription?.cancel();
    super.dispose();
  }

  Future<void> _initializeBluetooth() async {
    await _requestPermissions();
    await FlutterBluePlus.turnOn();
  }

  Future<void> _requestPermissions() async {
    await Future.wait([
      Permission.bluetooth.request(),
      Permission.bluetoothScan.request(),
      Permission.bluetoothConnect.request(),
      Permission.location.request(),
    ]);
  }

  void _setupStreams() {
    _hrDataSubscription = _service.hrHistoryDataStream.listen((data) {
      setState(() {
        _hrHistoryData = [data]; // Wrap single data in list for compatibility
        _isDownloading = false;
        _statusMessage = 'HR data downloaded successfully - ${data.entries.length} measurements';
      });
    });
    
    _hrListSubscription = _service.hrHistoryListStream.listen((list) {
      setState(() {
        _hrRecordList = list.timestamps;
        _hrRawTimestamps = list.rawTimestamps; // Store raw timestamps for HR data requests
        _statusMessage = 'HR record list received - ${list.timestamps.length} timestamps';
      });
    });
    
    _sleepDataSubscription = _service.sleepHistoryStream.listen((sleepData) {
      setState(() {
        _sleepHistoryData.clear();
        _sleepHistoryData.addAll(sleepData);
        _isDownloading = false;
        _statusMessage = 'Sleep data received - ${sleepData.length} sessions';
      });
      
      // Debug: print received data
      debugPrint('🌙 Received ${sleepData.length} sleep sessions:');
      for (int i = 0; i < sleepData.length; i++) {
        final session = sleepData[i];
        debugPrint('  Session $i: ${session.timestamp} - ${session.actions.length} actions');
        if (session.actions.isNotEmpty) {
          debugPrint('    First 10 actions: ${session.actions.take(10).toList()}');
        }
      }
    });
    
    _stepsDataSubscription = _service.stepsHistoryStream.listen((stepsData) {
      setState(() {
        _stepsHistoryData.clear();
        _stepsHistoryData.addAll(stepsData);
        _isDownloading = false;
        _statusMessage = 'Steps data received - ${stepsData.length} intervals';
      });
    });
  }

  Future<void> _startScan() async {
    if (isScanning) return;
    
    setState(() {
      isScanning = true;
      foundDevices.clear();
      _statusMessage = 'Scanning for devices...';
    });

    try {
      await FlutterBluePlus.startScan(timeout: const Duration(seconds: 10));
      
      FlutterBluePlus.scanResults.listen((results) {
        setState(() {
          foundDevices = results
              .where((r) => r.device.platformName.isNotEmpty)
              .map((r) => r.device)
              .toSet()
              .toList();
        });
      });

      await Future.delayed(const Duration(seconds: 10));
      await FlutterBluePlus.stopScan();
      
      if (mounted) {
        setState(() {
          isScanning = false;
          _statusMessage = foundDevices.isEmpty 
              ? 'No devices found' 
              : 'Found ${foundDevices.length} devices';
        });
      }
    } catch (e) {
      setState(() {
        isScanning = false;
        _statusMessage = 'Scan error: $e';
      });
    }
  }

  Future<void> _connectToDevice(BluetoothDevice device) async {
    if (isConnecting) return;

    setState(() {
      isConnecting = true;
      _statusMessage = 'Connecting to ${device.platformName}...';
    });

    try {
      // Disconnect any existing connection
      if (connectedDevice != null) {
        await connectedDevice!.disconnect();
      }

      // Connect to new device  
      await device.connect(mtu: null, license: License.free);
      
      // Listen to connection state
      _connectionSubscription = device.connectionState.listen((state) {
        setState(() {
          connectionStatus = state.toString().split('.').last;
          if (state == BluetoothConnectionState.connected) {
            connectedDevice = device;
            _statusMessage = 'Connected to ${device.platformName}';
          } else if (state == BluetoothConnectionState.disconnected) {
            connectedDevice = null;
            _statusMessage = 'Disconnected from device';
          }
        });
      });

      // Initialize the service with the connected device
      await _service.start(device);
      
      setState(() {
        isConnecting = false;
        connectedDevice = device;
        _statusMessage = 'Connected and services initialized';
      });

    } catch (e) {
      setState(() {
        isConnecting = false;
        _statusMessage = 'Connection failed: $e';
        connectionStatus = 'Failed';
      });
    }
  }

  Future<void> _disconnectDevice() async {
    if (connectedDevice != null) {
      try {
        await connectedDevice!.disconnect();
        setState(() {
          connectedDevice = null;
          connectionStatus = 'Disconnected';
          _statusMessage = 'Device disconnected successfully';
          _hrHistoryData.clear();
          _sleepHistoryData.clear();
        });
      } catch (e) {
        setState(() {
          _statusMessage = 'Error disconnecting: $e';
        });
      }
    } else {
      setState(() {
        _statusMessage = 'No device connected';
      });
    }
  }

  Future<void> _downloadCompleteHRHistory() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading COMPLETE HR history automatically...';
    });

    try {
      debugPrint('🚀 AUTOMATIC HR HISTORY DOWNLOAD STARTED');
      
      // Step 1: Get HR record timestamps first
      setState(() {
        _statusMessage = 'Step 1/3: Getting HR record timestamps...';
      });
      
      debugPrint('📋 Step 1: Getting HR record list...');
      await _service.getHistoryOfHRRecord();
      
      // Wait for timestamps to arrive
      await Future.delayed(const Duration(milliseconds: 2000));
      
      // Step 2: Check if we have timestamps to download
      if (_hrRawTimestamps.isEmpty) {
        setState(() {
          _isDownloading = false;
          _statusMessage = 'No HR timestamps found on device';
        });
        debugPrint('❌ No HR timestamps available for download');
        return;
      }
      
      setState(() {
        _statusMessage = 'Step 2/3: Found ${_hrRawTimestamps.length} HR sessions. Downloading latest...';
      });
      
      // Step 3: Download HR data for the LATEST timestamp (most recent session)
      int latestTimestamp = _hrRawTimestamps.last; // Get most recent timestamp
      setState(() {
        _selectedTimestamp = latestTimestamp; // Auto-select the latest session
      });
      
      debugPrint('💓 Step 3: Getting HR data for latest timestamp: $latestTimestamp');
      await _service.getHistoryOfHRData(latestTimestamp);
      
      // Wait for HR data to arrive
      await Future.delayed(const Duration(milliseconds: 3000));
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Latest HR session downloaded! Check chart below. ${_hrRawTimestamps.length} sessions available for selection.';
      });
      
      debugPrint('✅ AUTOMATIC HR HISTORY DOWNLOAD COMPLETED!');
      debugPrint('📊 HR Chart should now be populated with latest session data');
      
    } catch (e) {
      debugPrint('❌ Automatic HR history download failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Automatic HR download failed: $e';
      });
    }
  }

  Future<void> _downloadSleepData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading sleep data...';
    });

    try {
      debugPrint('🌙 SLEEP DATA DOWNLOAD STARTED');
      
      // Request sleep history data (force=true bypasses throttling when user explicitly clicks)
      await _service.requestOptimizedSleepHistory(force: true);
      
      // Wait for sleep data to arrive
      await Future.delayed(const Duration(milliseconds: 3000));
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data downloaded! Check sleep chart below.';
      });
      
      debugPrint('✅ SLEEP DATA DOWNLOAD COMPLETED!');
      
    } catch (e) {
      debugPrint('❌ Sleep data download failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data download failed: $e';
      });
    }
  }

  /// Download sleep data using OFFICIAL 0x31 command (ALL historical data)
  Future<void> _downloadSleepData0x31() async {
    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading FULL sleep history (0x31)...';
    });

    try {
      debugPrint('🌙📅 SLEEP HISTORY DOWNLOAD 0x31 STARTED');
      
      // Request ALL sleep history data with official 0x31 command
      await _service.requestSleepData31(force: true);
      
      // Wait longer for complete history (may be multiple days)
      await Future.delayed(const Duration(milliseconds: 5000));
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Full sleep history downloaded! Check data below.';
      });
      
      debugPrint('✅ SLEEP HISTORY DOWNLOAD 0x31 COMPLETED!');
      
    } catch (e) {
      debugPrint('❌ Sleep history 0x31 download failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep history 0x31 download failed: $e';
      });
    }
  }


  /// Genera il contenuto CSV per l'esportazione
  String _generateCSVContent() {
    StringBuffer csvContent = StringBuffer();
    
    // Header CSV
    csvContent.writeln('DateTime,Heart_Rate_BPM,Activity_Index,Session_Info');
    
    // Informazioni sessione
    String sessionInfo = _selectedTimestamp != null 
        ? 'Session_${DateFormat('yyyyMMdd_HHmm').format(DateTime.fromMillisecondsSinceEpoch(_selectedTimestamp! * 1000))}'
        : 'HR_Data';
        
    // Dati CSV
    for (var data in _hrHistoryData) {
      for (var entry in data.entries) {
        String dateTime = DateFormat('yyyy-MM-dd HH:mm:ss').format(entry.time);
        csvContent.writeln('$dateTime,${entry.heartRate},${entry.activityIndex},$sessionInfo');
      }
    }
    
    return csvContent.toString();
  }

  /// Genera il contenuto JSON per l'esportazione
  String _generateJSONContent() {
    // Informazioni sessione
    String sessionInfo = _selectedTimestamp != null 
        ? 'Session_${DateFormat('yyyyMMdd_HHmm').format(DateTime.fromMillisecondsSinceEpoch(_selectedTimestamp! * 1000))}'
        : 'HR_Data';
    
    // Conta il numero totale di misurazioni
    int totalMeasurements = 0;
    for (var data in _hrHistoryData) {
      totalMeasurements += data.entries.length;
    }
    
    // Crea la struttura JSON
    Map<String, dynamic> jsonData = {
      'export_info': {
        'exported_at': DateTime.now().toIso8601String(),
        'total_entries': totalMeasurements,
        'device': 'CL837',
        'data_type': 'heart_rate_history',
        'session_info': sessionInfo,
        'selected_timestamp': _selectedTimestamp
      },
      'data': []
    };
    
    // Aggiungi i dati HR
    for (var data in _hrHistoryData) {
      for (var entry in data.entries) {
        jsonData['data'].add({
          'timestamp': entry.time.millisecondsSinceEpoch ~/ 1000,
          'datetime': entry.time.toIso8601String(),
          'heart_rate_bpm': entry.heartRate,
          'activity_index': entry.activityIndex
        });
      }
    }
    
    // Ordina i dati per timestamp
    (jsonData['data'] as List).sort((a, b) => a['timestamp'].compareTo(b['timestamp']));
    
    // Codifica con formattazione indentata
    const encoder = JsonEncoder.withIndent('  ');
    return encoder.convert(jsonData);
  }

  /// Genera il contenuto CSV per i dati del sonno
  String _generateSleepCSVContent() {
    StringBuffer csvContent = StringBuffer();
    
    // Header CSV
    csvContent.writeln('Session_DateTime,Duration_Minutes,Total_Sleep_Minutes,Deep_Sleep_Minutes,Light_Sleep_Minutes,Awake_Minutes,Sleep_Efficiency_%,Sleep_Quality,Action_Index,Action_Timestamp');
    csvContent.writeln('# NOTE: Each Action Index = 5-MINUTE block (SDK 0x31 specification)');
    
    // Dati per ogni sessione
    for (var sleep in _sleepHistoryData) {
      final phases = sleep.calculateSleepPhases();
      final totalSleep = phases.lightSleep + phases.deepSleep;
      final totalMinutes = phases.totalMinutes;
      final efficiency = totalMinutes > 0 ? ((totalSleep / totalMinutes) * 100).toStringAsFixed(1) : '0';
      
      // Determina qualità del sonno
      String quality = 'Poor';
      if (totalSleep > 360 && phases.deepSleep > totalSleep * 0.2) {
        quality = 'Excellent';
      } else if (totalSleep > 300 && phases.deepSleep > totalSleep * 0.15) {
        quality = 'Good';
      } else if (totalSleep > 240) {
        quality = 'Fair';
      }
      
      String sessionDateTime = DateFormat('yyyy-MM-dd HH:mm:ss').format(sleep.timestamp);
      
      // Riga sommaria della sessione
      csvContent.writeln('$sessionDateTime,$totalMinutes,$totalSleep,${phases.deepSleep},${phases.lightSleep},${phases.awake},$efficiency,$quality,,');
      
      // Dettaglio azioni (ogni azione = 5 MINUTI secondo SDK)
      for (int i = 0; i < sleep.actions.length; i++) {
        int action = sleep.actions[i];
        DateTime actionTime = sleep.timestamp.add(Duration(minutes: i * 5)); // ✅ 5 minuti per action!
        String actionTimestamp = DateFormat('yyyy-MM-dd HH:mm:ss').format(actionTime);
        
        // Determina fase del sonno per questa azione (5-minute block)
        String phase = 'Unknown';
        if (action == 0) {
          phase = 'Very Still (0)';
        } else if (action > 20) {
          phase = 'Active/Awake';
        } else {
          phase = 'Light Activity';
        }
        
        csvContent.writeln(',,,,,,,,$action ($phase),$actionTimestamp');
      }
      
      // Riga vuota tra sessioni
      csvContent.writeln();
    }
    
    return csvContent.toString();
  }

  /// Esporta i dati del sonno in CSV
  Future<void> _exportSleepDataToCSV() async {
    if (_sleepHistoryData.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('No sleep data to export'),
          backgroundColor: Colors.orange,
        ),
      );
      return;
    }
    
    try {
      // Calcola statistiche per mostrare nell'alert
      final uniqueDates = _sleepHistoryData
          .map((s) => DateFormat('yyyy-MM-dd').format(s.timestamp))
          .toSet()
          .length;
      
      final csvContent = _generateSleepCSVContent();
      final timestamp = DateFormat('yyyyMMdd_HHmmss').format(DateTime.now());
      final filename = 'CL837_Sleep_Data_$timestamp.csv';
      
      // Usa share_plus per condividere il file
      final directory = await getTemporaryDirectory();
      final path = '${directory.path}/$filename';
      final file = File(path);
      await file.writeAsString(csvContent);
      
      await Share.shareXFiles(
        [XFile(path)],
        text: 'CL837 Sleep Data Export - ${_sleepHistoryData.length} sessions from $uniqueDates days',
        subject: filename,
      );
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('✅ Exported ${_sleepHistoryData.length} sessions from $uniqueDates days'),
            backgroundColor: Colors.green,
            duration: const Duration(seconds: 3),
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Export failed: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }


  // ===== HR SESSION SELECTOR =====
  Widget _buildHRSessionSelector() {
    if (_hrRecordList.isEmpty) {
      return const SizedBox.shrink(); // Hide if no sessions available
    }

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '📅 HR Session Selector',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                color: Colors.blue,
              ),
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                const Icon(Icons.access_time, size: 20, color: Colors.grey),
                const SizedBox(width: 8),
                Text(
                  'Found ${_hrRecordList.length} HR sessions',
                  style: const TextStyle(
                    fontSize: 14,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            DropdownButtonFormField<int>(
              decoration: const InputDecoration(
                labelText: 'Select HR Session',
                border: OutlineInputBorder(),
                prefixIcon: Icon(Icons.favorite),
              ),
              value: _selectedTimestamp,
              hint: const Text('Choose a session to view HR chart'),
              items: () {
                // Create a list of (timestamp, dateTime) pairs for sorting
                List<MapEntry<int, DateTime>> timestampPairs = _hrRawTimestamps.map((timestamp) {
                  return MapEntry(timestamp, DateTime.fromMillisecondsSinceEpoch(timestamp * 1000));
                }).toList();
                
                // Sort by date/time descending (most recent first)
                timestampPairs.sort((a, b) => b.value.compareTo(a.value));
                
                // Generate dropdown items from sorted list
                return timestampPairs.asMap().entries.map((entry) {
                  int sortedIndex = entry.key;
                  int timestamp = entry.value.key;
                  DateTime dateTime = entry.value.value;
                  
                  return DropdownMenuItem<int>(
                    value: timestamp,
                    child: Text(
                      '${DateFormat('MMM dd, yyyy - HH:mm').format(dateTime)} (Session ${sortedIndex + 1})',
                      style: const TextStyle(fontSize: 14),
                    ),
                  );
                }).toList();
              }(),
              onChanged: (int? newTimestamp) async {
                if (newTimestamp != null) {
                  setState(() {
                    _selectedTimestamp = newTimestamp;
                    _isDownloading = true;
                    _statusMessage = 'Loading HR data for selected session...';
                  });
                  
                  try {
                    debugPrint('📊 Loading HR data for timestamp: $newTimestamp');
                    await _service.getHistoryOfHRData(newTimestamp);
                    
                    // Wait for data to arrive
                    await Future.delayed(const Duration(milliseconds: 2000));
                    
                    setState(() {
                      _isDownloading = false;
                      _statusMessage = 'HR session loaded successfully!';
                    });
                  } catch (e) {
                    setState(() {
                      _isDownloading = false;
                      _statusMessage = 'Failed to load HR session: $e';
                    });
                  }
                }
              },
            ),
          ],
        ),
      ),
    );
  }

  // ===== HR CHART WIDGET =====
  Widget _buildHRChart() {
    final screenWidth = MediaQuery.of(context).size.width;
    final chartHeight = screenWidth > 600 ? 400.0 : 300.0;

    if (_hrHistoryData.isEmpty) {
      return Card(
        child: Container(
          height: chartHeight,
          padding: const EdgeInsets.all(16.0),
          child: const Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  Icons.show_chart,
                  size: 64,
                  color: Colors.grey,
                ),
                SizedBox(height: 16),
                Text(
                  'No HR data available for chart',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                  ),
                ),
                SizedBox(height: 8),
                Text(
                  'Connect to device and click "HR History Data"\nto retrieve HR measurements for visualization',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 14,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
    }

    // Prepare data for chart with enhanced features
    List<FlSpot> hrSpots = [];
    List<String> timeLabels = [];
    List<String> fullTimeLabels = [];
    List<int> activityIndices = [];

    for (int i = 0; i < _hrHistoryData.length; i++) {
      final hrData = _hrHistoryData[i];
      for (int j = 0; j < hrData.entries.length; j++) {
        final entry = hrData.entries[j];
        final timeIndex = (i * hrData.entries.length + j).toDouble();
        hrSpots.add(FlSpot(timeIndex, entry.heartRate.toDouble()));
        activityIndices.add(entry.activityIndex);

        // Full time label for tooltips
        fullTimeLabels.add(DateFormat('HH:mm:ss').format(entry.time));

        // Sparse time labels for axis (every 10th point or key points)
        if (j % 10 == 0 || j == hrData.entries.length - 1) {
          timeLabels.add(DateFormat('HH:mm').format(entry.time));
        } else {
          timeLabels.add('');
        }
      }
    }

    if (hrSpots.isEmpty) {
      return Card(
        child: Container(
          height: chartHeight,
          padding: const EdgeInsets.all(16.0),
          child: const Center(
            child: Text(
              'No HR measurements in data',
              style: TextStyle(fontSize: 16, color: Colors.grey),
            ),
          ),
        ),
      );
    }

    // Calculate Y-axis range with better padding
    double minHR = hrSpots.map((spot) => spot.y).reduce((a, b) => a < b ? a : b);
    double maxHR = hrSpots.map((spot) => spot.y).reduce((a, b) => a > b ? a : b);
    double range = maxHR - minHR;
    double padding = range * 0.15; // Increased padding for better visualization

    // Calculate HR zones
    double restingZone = 60; // Resting HR
    double fatBurnZone = 70; // Fat burn zone start
    double cardioZone = 85; // Cardio zone start
    double peakZone = 100; // Peak zone start

    // Calculate statistics
    double avgHR = hrSpots.map((s) => s.y).reduce((a, b) => a + b) / hrSpots.length;
    double stdDev = _calculateStandardDeviation(hrSpots.map((s) => s.y).toList());

    return Card(
      elevation: 4,
      margin: const EdgeInsets.symmetric(vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  '📈 Advanced Heart Rate Analysis',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.red,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            // HR Zone indicators
            _buildHRZoneIndicators(),
            const SizedBox(height: 16),
            SizedBox(
              height: chartHeight,
              child: LineChart(
                LineChartData(
                  gridData: FlGridData(
                    show: true,
                    drawVerticalLine: true,
                    horizontalInterval: 5, // More frequent horizontal lines
                    verticalInterval: hrSpots.length > 20 ? hrSpots.length / 10 : 2,
                    getDrawingHorizontalLine: (value) {
                      Color lineColor = Colors.grey.shade200;
                      // Highlight HR zone lines
                      if ((value - restingZone).abs() < 1) lineColor = Colors.blue.shade200;
                      if ((value - fatBurnZone).abs() < 1) lineColor = Colors.green.shade200;
                      if ((value - cardioZone).abs() < 1) lineColor = Colors.orange.shade200;
                      if ((value - peakZone).abs() < 1) lineColor = Colors.red.shade200;

                      return FlLine(
                        color: lineColor,
                        strokeWidth: (value - restingZone).abs() < 1 || (value - fatBurnZone).abs() < 1 ||
                                    (value - cardioZone).abs() < 1 || (value - peakZone).abs() < 1 ? 2 : 1,
                      );
                    },
                    getDrawingVerticalLine: (value) {
                      return FlLine(
                        color: Colors.grey.shade200,
                        strokeWidth: 1,
                      );
                    },
                  ),
                  titlesData: FlTitlesData(
                    show: true,
                    rightTitles: const AxisTitles(
                      sideTitles: SideTitles(showTitles: false),
                    ),
                    topTitles: const AxisTitles(
                      sideTitles: SideTitles(showTitles: false),
                    ),
                    bottomTitles: AxisTitles(
                      sideTitles: SideTitles(
                        showTitles: true,
                        reservedSize: 35,
                        interval: hrSpots.length > 20 ? hrSpots.length / 10 : 2,
                        getTitlesWidget: (value, meta) {
                          final index = value.toInt();
                          if (index >= 0 && index < timeLabels.length && timeLabels[index].isNotEmpty) {
                            return SideTitleWidget(
                              meta: meta,
                              child: Text(
                                timeLabels[index],
                                style: const TextStyle(
                                  color: Colors.grey,
                                  fontWeight: FontWeight.bold,
                                  fontSize: 11,
                                ),
                              ),
                            );
                          }
                          return const Text('');
                        },
                      ),
                    ),
                    leftTitles: AxisTitles(
                      sideTitles: SideTitles(
                        showTitles: true,
                        interval: 10,
                        reservedSize: 45,
                        getTitlesWidget: (value, meta) {
                          return Text(
                            '${value.toInt()}',
                            style: const TextStyle(
                              color: Colors.grey,
                              fontWeight: FontWeight.bold,
                              fontSize: 11,
                            ),
                          );
                        },
                      ),
                    ),
                  ),
                  borderData: FlBorderData(
                    show: true,
                    border: Border.all(color: Colors.grey.shade400, width: 1.5),
                  ),
                  minX: 0,
                  maxX: hrSpots.length.toDouble() - 1,
                  minY: (minHR - padding).clamp(40, 200), // Reasonable HR range
                  maxY: (maxHR + padding).clamp(60, 220),
                  lineBarsData: [
                    // Main HR line
                    LineChartBarData(
                      spots: hrSpots,
                      isCurved: true,
                      gradient: LinearGradient(
                        colors: [
                          Colors.red.shade400,
                          Colors.red.shade600,
                          Colors.red.shade800,
                        ],
                      ),
                      barWidth: 2.5,
                      isStrokeCapRound: true,
                      dotData: FlDotData(
                        show: hrSpots.length <= 50, // Show dots only for smaller datasets
                        getDotPainter: (spot, percent, barData, index) {
                          return FlDotCirclePainter(
                            radius: 3,
                            color: _getHRColor(spot.y.toInt()),
                            strokeWidth: 1.5,
                            strokeColor: Colors.white,
                          );
                        },
                      ),
                      belowBarData: BarAreaData(
                        show: true,
                        gradient: LinearGradient(
                          colors: [
                            Colors.red.shade200.withOpacity(0.4),
                            Colors.red.shade100.withOpacity(0.1),
                          ],
                          begin: Alignment.topCenter,
                          end: Alignment.bottomCenter,
                        ),
                      ),
                    ),
                    // Average line
                    LineChartBarData(
                      spots: List.generate(hrSpots.length, (index) => FlSpot(index.toDouble(), avgHR)),
                      isCurved: false,
                      color: Colors.blue.shade600,
                      barWidth: 1.5,
                      dashArray: [5, 5], // Dashed line
                      dotData: const FlDotData(show: false),
                    ),
                  ],
                  lineTouchData: LineTouchData(
                    handleBuiltInTouches: true,
                    touchTooltipData: LineTouchTooltipData(
                      getTooltipItems: (List<LineBarSpot> touchedBarSpots) {
                        return touchedBarSpots.map((barSpot) {
                          final index = barSpot.spotIndex;
                          final hr = barSpot.y.toInt();
                          final time = index < fullTimeLabels.length ? fullTimeLabels[index] : 'N/A';
                          final activity = index < activityIndices.length ? activityIndices[index] : 0;

                          return LineTooltipItem(
                            'HR: $hr BPM\nTime: $time\nActivity: $activity',
                            const TextStyle(
                              color: Colors.white,
                              fontSize: 12,
                              fontWeight: FontWeight.bold,
                            ),
                          );
                        }).toList();
                      },
                    ),
                  ),
                ),
              ),
            ),
            const SizedBox(height: 12),
            // Enhanced statistics row
            _buildEnhancedStatsRow(avgHR, stdDev, hrSpots.length),
          ],
        ),
      ),
    );
  }

  Widget _buildStatChip(String label, String value, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 6),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            label,
            style: TextStyle(
              fontSize: 11,
              fontWeight: FontWeight.bold,
              color: color,
            ),
            overflow: TextOverflow.ellipsis,
            textAlign: TextAlign.center,
          ),
          Text(
            value,
            style: TextStyle(
              fontSize: 12,
              fontWeight: FontWeight.bold,
              color: color,
            ),
            overflow: TextOverflow.ellipsis,
            textAlign: TextAlign.center,
          ),
        ],
      ),
    );
  }

  Widget _buildHRZoneIndicators() {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: Colors.grey.shade50,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.grey.shade300),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Flexible(child: _buildZoneIndicator('Resting', '< 60', Colors.blue)),
          Flexible(child: _buildZoneIndicator('Fat Burn', '60-70', Colors.green)),
          Flexible(child: _buildZoneIndicator('Cardio', '70-85', Colors.orange)),
          Flexible(child: _buildZoneIndicator('Peak', '> 85', Colors.red)),
        ],
      ),
    );
  }

  Widget _buildZoneIndicator(String zone, String range, Color color) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Container(
          width: 10,
          height: 10,
          decoration: BoxDecoration(
            color: color,
            shape: BoxShape.circle,
          ),
        ),
        const SizedBox(width: 3),
        Flexible(
          child: Text(
            '$zone: $range',
            style: TextStyle(
              fontSize: 10,
              color: color,
              fontWeight: FontWeight.w500,
            ),
            overflow: TextOverflow.ellipsis,
          ),
        ),
      ],
    );
  }

  Widget _buildEnhancedStatsRow(double avgHR, double stdDev, int count) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.grey.shade50,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: Colors.grey.shade200),
      ),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Flexible(child: _buildStatChip('Average', '${avgHR.toInt()} BPM', Colors.blue)),
              Flexible(child: _buildStatChip('Std Dev', stdDev.toStringAsFixed(1), Colors.purple)),
              Flexible(child: _buildStatChip('Samples', '$count', Colors.teal)),
              Flexible(child: _buildStatChip('Duration', _calculateDuration(), Colors.indigo)),
            ],
          ),
          const SizedBox(height: 8),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _buildTrendIndicator(),
              const SizedBox(width: 16),
              _buildHRVariabilityIndicator(stdDev),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildTrendIndicator() {
    // Simple trend analysis - compare first half vs second half
    if (_hrHistoryData.isEmpty) return const SizedBox.shrink();

    List<double> allHR = [];
    for (var data in _hrHistoryData) {
      allHR.addAll(data.entries.map((e) => e.heartRate.toDouble()));
    }

    if (allHR.length < 4) return const SizedBox.shrink();

    int mid = allHR.length ~/ 2;
    double firstHalf = allHR.sublist(0, mid).reduce((a, b) => a + b) / mid;
    double secondHalf = allHR.sublist(mid).reduce((a, b) => a + b) / (allHR.length - mid);
    double trend = secondHalf - firstHalf;

    IconData icon;
    Color color;
    String text;

    if (trend.abs() < 2) {
      icon = Icons.trending_flat;
      color = Colors.grey;
      text = 'Stable';
    } else if (trend > 0) {
      icon = Icons.trending_up;
      color = Colors.red;
      text = 'Increasing';
    } else {
      icon = Icons.trending_down;
      color = Colors.green;
      text = 'Decreasing';
    }

    return Row(
      children: [
        Icon(icon, size: 16, color: color),
        const SizedBox(width: 4),
        Text(
          text,
          style: TextStyle(
            fontSize: 12,
            color: color,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  Widget _buildHRVariabilityIndicator(double stdDev) {
    String variability;
    Color color;

    if (stdDev < 5) {
      variability = 'Low';
      color = Colors.green;
    } else if (stdDev < 15) {
      variability = 'Moderate';
      color = Colors.orange;
    } else {
      variability = 'High';
      color = Colors.red;
    }

    return Row(
      children: [
        Icon(Icons.vibration, size: 16, color: color),
        const SizedBox(width: 4),
        Text(
          'HRV: $variability',
          style: TextStyle(
            fontSize: 12,
            color: color,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  String _calculateDuration() {
    if (_hrHistoryData.isEmpty) return '0m';

    DateTime? startTime;
    DateTime? endTime;

    for (var data in _hrHistoryData) {
      for (var entry in data.entries) {
        if (startTime == null || entry.time.isBefore(startTime)) {
          startTime = entry.time;
        }
        if (endTime == null || entry.time.isAfter(endTime)) {
          endTime = entry.time;
        }
      }
    }

    if (startTime == null || endTime == null) return '0m';

    Duration duration = endTime.difference(startTime);
    int minutes = duration.inMinutes;
    int seconds = duration.inSeconds % 60;

    if (minutes > 0) {
      return '${minutes}m ${seconds}s';
    } else {
      return '${seconds}s';
    }
  }

  double _calculateStandardDeviation(List<double> values) {
    if (values.isEmpty) return 0.0;

    double mean = values.reduce((a, b) => a + b) / values.length;
    double sumSquaredDiffs = values.map((value) => (value - mean) * (value - mean)).reduce((a, b) => a + b);
    return sqrt(sumSquaredDiffs / values.length);
  }

  // ===== SLEEP CHART WIDGET =====
  Widget _buildSleepChart() {
    debugPrint('📊 Building sleep chart - ${_sleepHistoryData.length} sessions available');
    
    if (_sleepHistoryData.isEmpty) {
      debugPrint('📊 No sleep data - showing empty state');
      return Card(
        child: Container(
          height: 300,
          padding: const EdgeInsets.all(16.0),
          child: const Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  Icons.nightlight_round,
                  size: 64,
                  color: Colors.grey,
                ),
                SizedBox(height: 16),
                Text(
                  'No sleep data available for chart',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                  ),
                ),
                SizedBox(height: 8),
                Text(
                  'Connect to device and click "Download Sleep Data"\nto retrieve sleep measurements for visualization',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 14,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
    }

    // Get the most recent sleep session for charting
    final sleepEntry = _sleepHistoryData.first; // Most recent session
    debugPrint('📊 Using first session: ${sleepEntry.timestamp} with ${sleepEntry.actions.length} actions');
    debugPrint('📊 First 10 actions: ${sleepEntry.actions.take(10).toList()}');
    
    // Check if the session has valid actions
    if (sleepEntry.actions.isEmpty) {
      debugPrint('📊 Session has no actions - showing empty state');
      return Card(
        child: Container(
          height: 300,
          padding: const EdgeInsets.all(16.0),
          child: const Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  Icons.nightlight_round,
                  size: 64,
                  color: Colors.grey,
                ),
                SizedBox(height: 16),
                Text(
                  'Sleep session has no action data',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
    }
    
    final phases = sleepEntry.calculateSleepPhases();

    // Prepare data for sleep phases chart
    List<FlSpot> awakeSpots = [];
    List<FlSpot> lightSleepSpots = [];
    List<FlSpot> deepSleepSpots = [];

    // Analyze each action in the sleep data
    for (int i = 0; i < sleepEntry.actions.length; i++) {
      int action = sleepEntry.actions[i];
      double timeIndex = i.toDouble();

      if (action == 0) {
        // Check for deep sleep (3+ consecutive zeros)
        int consecutiveZeros = 1;
        for (int j = i + 1; j < sleepEntry.actions.length && sleepEntry.actions[j] == 0; j++) {
          consecutiveZeros++;
        }

        if (consecutiveZeros >= 3) {
          // Deep sleep
          deepSleepSpots.add(FlSpot(timeIndex, 3));
          i += consecutiveZeros - 1; // Skip the consecutive zeros
        } else {
          // Single or double zeros - treat as deep sleep for better visualization
          deepSleepSpots.add(FlSpot(timeIndex, 3));
        }
      } else if (action > 20) {
        // Awake
        awakeSpots.add(FlSpot(timeIndex, 1));
      } else {
        // Light sleep (action 1-20)
        lightSleepSpots.add(FlSpot(timeIndex, 2));
      }
    }

    // Debug: print data counts
    debugPrint('🌙 Sleep chart data - Actions: ${sleepEntry.actions.length}, Deep: ${deepSleepSpots.length}, Light: ${lightSleepSpots.length}, Awake: ${awakeSpots.length}');

    // Check if we have any data to display
    if (deepSleepSpots.isEmpty && lightSleepSpots.isEmpty && awakeSpots.isEmpty) {
      return Card(
        child: Container(
          height: 300,
          padding: const EdgeInsets.all(16.0),
          child: const Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  Icons.nightlight_round,
                  size: 64,
                  color: Colors.grey,
                ),
                SizedBox(height: 16),
                Text(
                  'No chartable sleep data found',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                  ),
                ),
                SizedBox(height: 8),
                Text(
                  'Sleep actions may be all zeros or invalid',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 14,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
    }

    return Card(
      elevation: 4,
      margin: const EdgeInsets.symmetric(vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  '🌙 Sleep Phases Analysis',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.purple,
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.info_outline),
                  onPressed: () => _showSleepChartInfo(context),
                  tooltip: 'Sleep Chart Information',
                ),
              ],
            ),
            const SizedBox(height: 8),
            // Sleep phase indicators
            _buildSleepPhaseIndicators(),
            const SizedBox(height: 16),
            SizedBox(
              height: 250,
              child: LineChart(
                LineChartData(
                  gridData: FlGridData(
                    show: true,
                    drawVerticalLine: true,
                    horizontalInterval: 1,
                    verticalInterval: sleepEntry.actions.length > 60 ? sleepEntry.actions.length / 10 : 10,
                    getDrawingHorizontalLine: (value) {
                      Color lineColor = Colors.grey.shade200;
                      if (value == 1) lineColor = Colors.orange.shade200;
                      if (value == 2) lineColor = Colors.blue.shade200;
                      if (value == 3) lineColor = Colors.purple.shade200;

                      return FlLine(
                        color: lineColor,
                        strokeWidth: value == 1 || value == 2 || value == 3 ? 2 : 1,
                      );
                    },
                    getDrawingVerticalLine: (value) {
                      return FlLine(
                        color: Colors.grey.shade200,
                        strokeWidth: 1,
                      );
                    },
                  ),
                  titlesData: FlTitlesData(
                    show: true,
                    rightTitles: const AxisTitles(
                      sideTitles: SideTitles(showTitles: false),
                    ),
                    topTitles: const AxisTitles(
                      sideTitles: SideTitles(showTitles: false),
                    ),
                    bottomTitles: AxisTitles(
                      sideTitles: SideTitles(
                        showTitles: true,
                        reservedSize: 35,
                        interval: sleepEntry.actions.length > 60 ? sleepEntry.actions.length / 10 : 10,
                        getTitlesWidget: (value, meta) {
                          final index = value.toInt();
                          if (index >= 0 && index < sleepEntry.actions.length && index % 30 == 0) {
                            final time = sleepEntry.timestamp.add(Duration(minutes: index));
                            return SideTitleWidget(
                              meta: meta,
                              child: Text(
                                DateFormat('HH:mm').format(time),
                                style: const TextStyle(
                                  color: Colors.grey,
                                  fontWeight: FontWeight.bold,
                                  fontSize: 11,
                                ),
                              ),
                            );
                          }
                          return const Text('');
                        },
                      ),
                    ),
                    leftTitles: AxisTitles(
                      sideTitles: SideTitles(
                        showTitles: true,
                        interval: 1,
                        reservedSize: 60,
                        getTitlesWidget: (value, meta) {
                          String label;
                          Color color;
                          switch (value.toInt()) {
                            case 1:
                              label = 'Awake';
                              color = Colors.orange;
                              break;
                            case 2:
                              label = 'Light';
                              color = Colors.blue;
                              break;
                            case 3:
                              label = 'Deep';
                              color = Colors.purple;
                              break;
                            default:
                              return const Text('');
                          }

                          return SideTitleWidget(
                            meta: meta,
                            child: Text(
                              label,
                              style: TextStyle(
                                color: color,
                                fontWeight: FontWeight.bold,
                                fontSize: 10,
                              ),
                            ),
                          );
                        },
                      ),
                    ),
                  ),
                  borderData: FlBorderData(
                    show: true,
                    border: Border.all(color: Colors.grey.shade400, width: 1.5),
                  ),
                  minX: 0,
                  maxX: sleepEntry.actions.length.toDouble() - 1,
                  minY: 0.5,
                  maxY: 3.5,
                  lineBarsData: [
                    // Deep sleep line
                    LineChartBarData(
                      spots: deepSleepSpots,
                      isCurved: false,
                      color: Colors.purple.shade700,
                      barWidth: 3,
                      dotData: const FlDotData(
                        show: false,
                      ),
                    ),
                    // Light sleep line
                    LineChartBarData(
                      spots: lightSleepSpots,
                      isCurved: false,
                      color: Colors.blue.shade600,
                      barWidth: 3,
                      dotData: const FlDotData(
                        show: false,
                      ),
                    ),
                    // Awake line
                    LineChartBarData(
                      spots: awakeSpots,
                      isCurved: false,
                      color: Colors.orange.shade600,
                      barWidth: 3,
                      dotData: const FlDotData(
                        show: false,
                      ),
                    ),
                  ],
                  lineTouchData: LineTouchData(
                    handleBuiltInTouches: true,
                    touchTooltipData: LineTouchTooltipData(
                      getTooltipItems: (List<LineBarSpot> touchedBarSpots) {
                        return touchedBarSpots.map((barSpot) {
                          final index = barSpot.spotIndex;
                          final phaseValue = barSpot.y.toInt();
                          final time = sleepEntry.timestamp.add(Duration(minutes: index));

                          String phaseName;
                          Color color;
                          switch (phaseValue) {
                            case 1:
                              phaseName = 'Awake';
                              color = Colors.orange;
                              break;
                            case 2:
                              phaseName = 'Light Sleep';
                              color = Colors.blue;
                              break;
                            case 3:
                              phaseName = 'Deep Sleep';
                              color = Colors.purple;
                              break;
                            default:
                              phaseName = 'Unknown';
                              color = Colors.grey;
                          }

                          return LineTooltipItem(
                            '$phaseName\n${DateFormat('HH:mm').format(time)}',
                            TextStyle(
                              color: color,
                              fontSize: 12,
                              fontWeight: FontWeight.bold,
                            ),
                          );
                        }).toList();
                      },
                    ),
                  ),
                ),
              ),
            ),
            const SizedBox(height: 12),
            // Sleep statistics
            _buildSleepStatsRow(phases),
          ],
        ),
      ),
    );
  }

  Widget _buildSleepPhaseIndicators() {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: Colors.grey.shade50,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.grey.shade300),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          _buildZoneIndicator('Awake', 'Action > 20', Colors.orange),
          _buildZoneIndicator('Light Sleep', 'Action 1-20', Colors.blue),
          _buildZoneIndicator('Deep Sleep', '3+ zeros', Colors.purple),
        ],
      ),
    );
  }

  Widget _buildSleepStatsRow(SleepPhases phases) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.grey.shade50,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: Colors.grey.shade200),
      ),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Flexible(child: _buildStatChip('Total Sleep', '${phases.totalSleep}m', Colors.blue)),
              Flexible(child: _buildStatChip('Deep Sleep', '${phases.deepSleep}m', Colors.purple)),
              Flexible(child: _buildStatChip('Light Sleep', '${phases.lightSleep}m', Colors.cyan)),
              Flexible(child: _buildStatChip('Awake', '${phases.awake}m', Colors.orange)),
            ],
          ),
          const SizedBox(height: 8),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _buildSleepQualityIndicator(phases),
              const SizedBox(width: 16),
              _buildSleepEfficiencyIndicator(phases),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildSleepQualityIndicator(SleepPhases phases) {
    final totalSleep = phases.lightSleep + phases.deepSleep;
    final deepSleepRatio = phases.deepSleep / totalSleep;

    String quality;
    Color color;
    IconData icon;

    if (deepSleepRatio > 0.25 && totalSleep > 360) {
      quality = 'Excellent';
      color = Colors.green;
      icon = Icons.star;
    } else if (deepSleepRatio > 0.15 && totalSleep > 240) {
      quality = 'Good';
      color = Colors.blue;
      icon = Icons.thumb_up;
    } else if (totalSleep > 180) {
      quality = 'Fair';
      color = Colors.orange;
      icon = Icons.thumbs_up_down;
    } else {
      quality = 'Poor';
      color = Colors.red;
      icon = Icons.thumb_down;
    }

    return Row(
      children: [
        Icon(icon, size: 16, color: color),
        const SizedBox(width: 4),
        Text(
          'Quality: $quality',
          style: TextStyle(
            fontSize: 12,
            color: color,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  Widget _buildSleepEfficiencyIndicator(SleepPhases phases) {
    final totalTime = phases.totalMinutes;
    final sleepTime = phases.lightSleep + phases.deepSleep;
    final efficiency = totalTime > 0 ? (sleepTime / totalTime * 100).round() : 0;

    Color color;
    if (efficiency >= 85) {
      color = Colors.green;
    } else if (efficiency >= 75) {
      color = Colors.blue;
    } else if (efficiency >= 65) {
      color = Colors.orange;
    } else {
      color = Colors.red;
    }

    return Row(
      children: [
        Icon(Icons.trending_up, size: 16, color: color),
        const SizedBox(width: 4),
        Text(
          'Efficiency: $efficiency%',
          style: TextStyle(
            fontSize: 12,
            color: color,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  void _showSleepChartInfo(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Sleep Phases Chart Information'),
        content: const SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text('🌙 Sleep Phase Analysis:', style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('• Purple line: Deep sleep phases'),
              Text('• Blue line: Light sleep phases'),
              Text('• Orange line: Awake periods'),
              Text('• Touch points for detailed timing'),
              SizedBox(height: 12),
              Text('🛌 Sleep Phases:', style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('• Deep Sleep: 3+ consecutive zero actions'),
              Text('• Light Sleep: Action values 1-20'),
              Text('• Awake: Action values > 20'),
              SizedBox(height: 12),
              Text('📊 Statistics:', style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('• Total Sleep: Light + Deep sleep time'),
              Text('• Quality: Based on deep sleep ratio'),
              Text('• Efficiency: Sleep time vs total time'),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Close'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 WearManager HR Test'),
        backgroundColor: Colors.blue.shade700.withOpacity(0.9),
        foregroundColor: Colors.white,
        elevation: 0,
      ),
      body: Container(
        decoration: const BoxDecoration(
          image: DecorationImage(
            image: AssetImage('lib/assets/background.jpg'),
            fit: BoxFit.cover,
          ),
        ),
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // Connection Status Card with Blur Effect
            _buildBlurCard(
              backgroundColor: _getConnectionStatusColor().withOpacity(0.3),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    Text(
                      'Connection Status: $connectionStatus',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                        shadows: [
                          Shadow(
                            offset: Offset(1, 1),
                            blurRadius: 2,
                            color: Colors.black54,
                          ),
                        ],
                      ),
                    ),
                    if (connectedDevice != null)
                      Text(
                        'Device: ${connectedDevice!.platformName}',
                        style: const TextStyle(
                          color: Colors.white,
                          shadows: [
                            Shadow(
                              offset: Offset(1, 1),
                              blurRadius: 2,
                              color: Colors.black54,
                            ),
                          ],
                        ),
                      ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Control Buttons
            if (connectedDevice == null) ...[
              ElevatedButton.icon(
                onPressed: isScanning ? null : _startScan,
                icon: isScanning 
                    ? const SizedBox(
                        width: 16, 
                        height: 16, 
                        child: CircularProgressIndicator(strokeWidth: 2)
                      )
                    : const Icon(Icons.search),
                label: Text(isScanning ? 'Scanning...' : 'Scan for Devices'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),
              
              const SizedBox(height: 12),
              
              // Device List
              if (foundDevices.isNotEmpty) ...[
                const Text(
                  'Found Devices:',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                ...foundDevices.map((device) => Card(
                  child: ListTile(
                    title: Text(device.platformName),
                    subtitle: Text(device.remoteId.toString()),
                    trailing: ElevatedButton(
                      onPressed: isConnecting ? null : () => _connectToDevice(device),
                      child: isConnecting 
                          ? const SizedBox(
                              width: 16, 
                              height: 16, 
                              child: CircularProgressIndicator(strokeWidth: 2)
                            )
                          : const Text('Connect'),
                    ),
                  ),
                )),
              ],
            ] else ...[
              // Connected Device Controls
              // HR Complete History - AUTOMATIC DOWNLOAD
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadCompleteHRHistory,
                icon: const Icon(Icons.favorite_border),
                label: const Text('💓 Download HR History'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 12),

              // Sleep Data Download (0x05 Legacy - Recent data)
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadSleepData,
                icon: const Icon(Icons.nightlight_round),
                label: const Text('🌙 Sleep Data (0x05 Recent)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 8),

              // Sleep Data Download (0x31 Official - Full history)
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadSleepData0x31,
                icon: const Icon(Icons.history),
                label: const Text('📅 Sleep History (0x31 ALL)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple.shade900,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 12),

              // Disconnect Device
              ElevatedButton.icon(
                onPressed: _disconnectDevice,
                icon: const Icon(Icons.bluetooth_disabled),
                label: const Text('🔌 Disconnect Device'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade600,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),
            ],
            
            const SizedBox(height: 16),
            
            // Status Message
            Card(
              color: Colors.grey.shade100,
              child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Text(
                  _statusMessage,
                  style: const TextStyle(fontSize: 14),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // HR Data Display with Blur Effect
            _buildBlurCard(
              backgroundColor: Colors.blue.withOpacity(0.2),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'HR History Data (${_hrHistoryData.length} entries)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                        shadows: [
                          Shadow(
                            offset: Offset(1, 1),
                            blurRadius: 2,
                            color: Colors.black54,
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 300, // Fixed height instead of Expanded
                      child: _hrHistoryData.isEmpty
                          ? const Center(
                              child: Text(
                                'No HR data downloaded yet.\nConnect to device and test WearManager protocol.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _hrHistoryData.length,
                              itemBuilder: (context, index) {
                                final data = _hrHistoryData[index];
                                return Card(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  child: ExpansionTile(
                                    title: Text(
                                      'HR History - ${DateFormat('yyyy-MM-dd HH:mm:ss').format(data.timestamp)}',
                                      style: const TextStyle(fontWeight: FontWeight.bold),
                                    ),
                                    subtitle: Text('${data.entries.length} entries'),
                                    children: data.entries.map((entry) => ListTile(
                                      leading: CircleAvatar(
                                        backgroundColor: _getHRColor(entry.heartRate),
                                        child: Text(
                                          '${entry.heartRate}',
                                          style: const TextStyle(
                                            color: Colors.white,
                                            fontWeight: FontWeight.bold,
                                            fontSize: 12,
                                          ),
                                        ),
                                      ),
                                      title: Text(
                                        'HR: ${entry.heartRate} bpm',
                                        style: const TextStyle(fontWeight: FontWeight.bold),
                                      ),
                                      subtitle: Text(
                                        'Time: ${DateFormat('HH:mm:ss').format(entry.time)}\nActivity: ${entry.activityIndex}',
                                      ),
                                    )).toList(),
                                  ),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),

            // HR Session Selector (only visible when sessions are available)
            _buildHRSessionSelector(),
            
            // HR Chart
            _buildHRChart(),
            
            const SizedBox(height: 16),
            
            // HR Record List Display with Blur Effect
            _buildBlurCard(
              backgroundColor: Colors.green.withOpacity(0.2),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'HR Record Timestamps (${_hrRecordList.length} entries)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                        shadows: [
                          Shadow(
                            offset: Offset(1, 1),
                            blurRadius: 2,
                            color: Colors.black54,
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 200, // Fixed height for record list
                      child: _hrRecordList.isEmpty
                          ? const Center(
                              child: Text(
                                'No HR record list downloaded yet.\nUse "Get HR Record List" button first.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _hrRecordList.length,
                              itemBuilder: (context, index) {
                                final timestamp = _hrRecordList[index];
                                return ListTile(
                                  leading: CircleAvatar(
                                    backgroundColor: Colors.blue,
                                    child: Text(
                                      '${index + 1}',
                                      style: const TextStyle(
                                        color: Colors.white,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 12,
                                      ),
                                    ),
                                  ),
                                  title: Text(
                                    DateFormat('yyyy-MM-dd HH:mm:ss').format(timestamp),
                                    style: const TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                  subtitle: Text(
                                    'HR session recorded at this timestamp',
                                    style: TextStyle(color: Colors.grey[600]),
                                  ),
                                  trailing: const Icon(Icons.schedule, color: Colors.blue),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Sleep Data Display with Blur Effect
            _buildBlurCard(
              backgroundColor: Colors.purple.withOpacity(0.2),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          '🌙 Sleep Data (${_sleepHistoryData.length} sessions)',
                          style: const TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                            shadows: [
                              Shadow(
                                offset: Offset(1, 1),
                                blurRadius: 2,
                                color: Colors.black54,
                              ),
                            ],
                          ),
                        ),
                        // Always show Export CSV button (will show message if no data)
                        ElevatedButton.icon(
                          onPressed: _sleepHistoryData.isNotEmpty 
                              ? _exportSleepDataToCSV 
                              : () {
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    const SnackBar(
                                      content: Text('⚠️ No sleep data to export. Download sleep data first!'),
                                      backgroundColor: Colors.orange,
                                    ),
                                  );
                                },
                          icon: const Icon(Icons.download, size: 18),
                          label: const Text('Export CSV'),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: _sleepHistoryData.isNotEmpty 
                                ? Colors.green.shade700 
                                : Colors.grey.shade600,
                            foregroundColor: Colors.white,
                            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                            textStyle: const TextStyle(fontSize: 12),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 250, // Fixed height for sleep list
                      child: _sleepHistoryData.isEmpty
                          ? const Center(
                              child: Text(
                                'No sleep data downloaded yet.\nUse "Get Sleep Data" button to download.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _sleepHistoryData.length,
                              itemBuilder: (context, index) {
                                final sleep = _sleepHistoryData[index];
                                final phases = sleep.calculateSleepPhases();
                                final totalSleepMinutes = phases.lightSleep + phases.deepSleep;
                                
                                return Card(
                                  color: Colors.white.withOpacity(0.1),
                                  child: ListTile(
                                    leading: CircleAvatar(
                                      backgroundColor: _getSleepQualityColor(phases),
                                      child: Icon(
                                        _getSleepIcon(phases),
                                        color: Colors.white,
                                        size: 20,
                                      ),
                                    ),
                                    title: Text(
                                      '🌙 Sleep Session ${index + 1}',
                                      style: const TextStyle(
                                        fontWeight: FontWeight.bold,
                                        color: Colors.white,
                                      ),
                                    ),
                                    subtitle: Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Text(
                                          'Time: ${DateFormat('MMM dd, HH:mm').format(sleep.timestamp)}',
                                          style: const TextStyle(color: Colors.white70),
                                        ),
                                        Text(
                                          'Duration: ${totalSleepMinutes ~/ 60}h ${totalSleepMinutes % 60}m',
                                          style: const TextStyle(color: Colors.white70),
                                        ),
                                        Row(
                                          children: [
                                            Flexible(
                                              child: Text(
                                                'Light: ${phases.lightSleep}m',
                                                style: const TextStyle(
                                                  color: Colors.blue,
                                                  fontSize: 11,
                                                ),
                                                overflow: TextOverflow.ellipsis,
                                              ),
                                            ),
                                            const SizedBox(width: 4),
                                            Flexible(
                                              child: Text(
                                                'Deep: ${phases.deepSleep}m',
                                                style: const TextStyle(
                                                  color: Colors.purple,
                                                  fontSize: 11,
                                                ),
                                                overflow: TextOverflow.ellipsis,
                                              ),
                                            ),
                                            const SizedBox(width: 4),
                                            Flexible(
                                              child: Text(
                                                'Awake: ${phases.awake}m',
                                                style: const TextStyle(
                                                  color: Colors.orange,
                                                  fontSize: 11,
                                                ),
                                                overflow: TextOverflow.ellipsis,
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                    trailing: IconButton(
                                      icon: const Icon(Icons.bar_chart, color: Colors.white),
                                      onPressed: () => _showSleepDetails(sleep, phases),
                                      tooltip: 'View sleep details',
                                    ),
                                  ),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Sleep Chart
            _buildSleepChart(),
            
            const SizedBox(height: 16),
            
            // Steps Data Display with Blur Effect
            _buildBlurCard(
              backgroundColor: Colors.orange.withOpacity(0.2),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Steps History (${_stepsHistoryData.length} intervals)',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                        shadows: [
                          Shadow(
                            offset: Offset(1, 1),
                            blurRadius: 2,
                            color: Colors.black54,
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      height: 250, // Fixed height for steps list
                      child: _stepsHistoryData.isEmpty
                          ? const Center(
                              child: Text(
                                'No steps data downloaded yet.\nUse "Get Steps Data" button to download.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.grey,
                                ),
                              ),
                            )
                          : ListView.builder(
                              itemCount: _stepsHistoryData.length,
                              itemBuilder: (context, index) {
                                final steps = _stepsHistoryData[index];
                                return ListTile(
                                  leading: CircleAvatar(
                                    backgroundColor: _getStepsColor(steps.steps),
                                    child: Text(
                                      '${steps.steps}',
                                      style: const TextStyle(
                                        color: Colors.white,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 10,
                                      ),
                                    ),
                                  ),
                                  title: Text(
                                    '👟 ${steps.steps} steps',
                                    style: const TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                  subtitle: Text(
                                    'Time: ${DateFormat('MMM dd, HH:mm').format(steps.timestamp)}',
                                  ),
                                  trailing: Icon(
                                    steps.steps > 500 ? Icons.directions_run : Icons.directions_walk,
                                    color: _getStepsColor(steps.steps),
                                  ),
                                );
                              },
                            ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
        ),
      ),
    );
  }

  /// Widget helper per creare card con effetto blur
  Widget _buildBlurCard({
    required Widget child,
    Color? backgroundColor,
    double borderRadius = 25,
    double sigmaX = 15,
    double sigmaY = 15,
  }) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(borderRadius),
      child: BackdropFilter(
        filter: ImageFilter.blur(sigmaX: sigmaX, sigmaY: sigmaY),
        child: Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(borderRadius),
            color: backgroundColor ?? Colors.black45,
          ),
          child: child,
        ),
      ),
    );
  }

  Color _getConnectionStatusColor() {
    switch (connectionStatus) {
      case 'connected':
        return Colors.green;
      case 'connecting':
        return Colors.orange;
      case 'disconnected':
        return Colors.red;
      default:
        return Colors.grey;
    }
  }

  Color _getHRColor(int heartRate) {
    if (heartRate < 60) return Colors.blue;
    if (heartRate < 100) return Colors.green;
    if (heartRate < 150) return Colors.orange;
    return Colors.red;
  }

  Color _getStepsColor(int steps) {
    if (steps < 100) return Colors.red;
    if (steps < 300) return Colors.orange;
    if (steps < 500) return Colors.blue;
    if (steps < 1000) return Colors.green;
    return Colors.purple;
  }

  Color _getSleepQualityColor(SleepPhases phases) {
    final totalSleep = phases.lightSleep + phases.deepSleep;
    final deepSleepRatio = phases.deepSleep / totalSleep;
    
    if (deepSleepRatio > 0.25 && totalSleep > 360) return Colors.green; // Good sleep
    if (deepSleepRatio > 0.15 && totalSleep > 240) return Colors.blue; // Decent sleep
    if (totalSleep > 180) return Colors.orange; // Poor sleep
    return Colors.red; // Very poor sleep
  }

  IconData _getSleepIcon(SleepPhases phases) {
    final totalSleep = phases.lightSleep + phases.deepSleep;
    final deepSleepRatio = phases.deepSleep / totalSleep;
    
    if (deepSleepRatio > 0.25 && totalSleep > 360) return Icons.nightlight; // Excellent
    if (deepSleepRatio > 0.15 && totalSleep > 240) return Icons.bedtime; // Good
    if (totalSleep > 180) return Icons.hotel; // Fair
    return Icons.snooze; // Poor
  }

  void _showSleepDetails(SleepHistoryEntry sleep, SleepPhases phases) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('🌙 Sleep Session Details'),
        content: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                'Session Time: ${DateFormat('MMM dd, yyyy - HH:mm').format(sleep.timestamp)}',
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 16),
              const Text('Sleep Phases:', style: TextStyle(fontWeight: FontWeight.bold)),
              const SizedBox(height: 8),
              _buildSleepPhaseRow('Light Sleep', phases.lightSleep, Colors.blue),
              _buildSleepPhaseRow('Deep Sleep', phases.deepSleep, Colors.purple),
              _buildSleepPhaseRow('Awake', phases.awake, Colors.orange),
              const SizedBox(height: 16),
              Text('Total Measurements: ${sleep.actions.length}'),
              Text('Sleep Efficiency: ${((phases.lightSleep + phases.deepSleep) / sleep.actions.length * 100).toStringAsFixed(1)}%'),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Close'),
          ),
        ],
      ),
    );
  }

  Widget _buildSleepPhaseRow(String phase, int minutes, Color color) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        children: [
          Container(
            width: 12,
            height: 12,
            decoration: BoxDecoration(
              color: color,
              shape: BoxShape.circle,
            ),
          ),
          const SizedBox(width: 8),
          Expanded(
            child: Text(
              '$phase: ${minutes ~/ 60}h ${minutes % 60}m',
              style: TextStyle(color: color, fontWeight: FontWeight.w500),
            ),
          ),
        ],
      ),
    );
  }
}