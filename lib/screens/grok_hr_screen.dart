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
        _isDownloading = false;
        _statusMessage = 'Sleep data received - ${sleepData.length} sessions';
      });
    });
    
    _stepsDataSubscription = _service.stepsHistoryStream.listen((stepsData) {
      setState(() {
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
      await device.connect(timeout: const Duration(seconds: 15));
      
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

  Future<void> _downloadHRData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing WearManager protocol...';
    });

    try {
      debugPrint('🫀 WEARMANAGER PROTOCOL TEST');
      
      // Test completo del protocollo WearManager
      setState(() {
        _statusMessage = 'Running complete WearManager sequence...';
      });
      
      await _service.performCompleteHRHistorySequence();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'WearManager protocol test completed! Check logs.';
      });
      
      debugPrint('✅ WearManager protocol test completed!');
    } catch (e) {
      debugPrint('❌ WearManager protocol test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'WearManager protocol test failed: $e';
      });
    }
  }

  // ===== WEARMANAGER TEST METHODS =====

  Future<void> _testUTCSync() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing UTC synchronization...';
    });

    try {
      debugPrint('⏰ Testing UTC sync (WearManager setUTCTime)');
      await _service.setUTCTime();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'UTC sync completed! Check logs for details.';
      });
      
      debugPrint('✅ UTC sync test completed!');
    } catch (e) {
      debugPrint('❌ UTC sync failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'UTC sync failed: $e';
      });
    }
  }

  Future<void> _testHRRecordList() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing HR record list (0x21)...';
    });

    try {
      debugPrint('📋 Testing HR record list (WearManager getHistoryOfHRRecord)');
      await _service.getHistoryOfHRRecord();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR record list test completed! Check logs for timestamps.';
      });
      
      debugPrint('✅ HR record list test completed!');
    } catch (e) {
      debugPrint('❌ HR record list test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR record list test failed: $e';
      });
    }
  }

  Future<void> _testHRData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing HR data retrieval (0x22)...';
    });

    try {
      debugPrint('💓 Testing HR data (WearManager getHistoryOfHRData)');
      
      // Use the most recent timestamp from the HR record list if available
      int targetTimestamp;
      if (_hrRawTimestamps.isNotEmpty) {
        // Use the most recent timestamp from the device
        targetTimestamp = _hrRawTimestamps.last;
        debugPrint('💓 Using most recent timestamp from device records: $targetTimestamp');
      } else {
        // Fallback to known good timestamp from your logs
        targetTimestamp = 1758127172; // 2025-09-17 18:39:32
        debugPrint('💓 Using fallback timestamp: $targetTimestamp');
      }
      
      debugPrint('💓 Requesting HR data for: ${DateTime.fromMillisecondsSinceEpoch(targetTimestamp * 1000)}');
      await _service.getHistoryOfHRData(targetTimestamp);
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR data test completed! Check logs for HR measurements.';
      });
      
      debugPrint('✅ HR data test completed!');
    } catch (e) {
      debugPrint('❌ HR data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'HR data test failed: $e';
      });
    }
  }

  Future<void> _testSleepData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing sleep data retrieval (0x05)...';
    });

    try {
      debugPrint('🌙 Testing sleep data (WearManager getHistoryOfSleep)');
      await _service.getHistoryOfSleep();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data test completed! Check logs for sleep sessions.';
      });
      
      debugPrint('✅ Sleep data test completed!');
    } catch (e) {
      debugPrint('❌ Sleep data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep data test failed: $e';
      });
    }
  }

  Future<void> _testAllSleepCommands() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing ALL sleep commands (comprehensive test)...';
    });

    try {
      debugPrint('🌙🧪 Starting comprehensive sleep command test');
      await _service.testAllSleepCommands();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'All sleep command tests completed! Check logs for responses.';
      });
      
      debugPrint('✅ All sleep command tests completed!');
    } catch (e) {
      debugPrint('❌ Sleep command test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Sleep command test failed: $e';
      });
    }
  }

  Future<void> _testStepsData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Testing steps data retrieval (0x40)...';
    });

    try {
      debugPrint('👟 Testing steps data (WearManager getIntervalSteps)');
      await _service.getIntervalSteps();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Steps data test completed! Check logs for step intervals.';
      });
      
      debugPrint('✅ Steps data test completed!');
    } catch (e) {
      debugPrint('❌ Steps data test failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Steps data test failed: $e';
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

  Future<void> _downloadAllData() async {
    if (connectedDevice == null) {
      setState(() {
        _statusMessage = 'No device connected';
      });
      return;
    }

    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading ALL data (HR + Sleep + Steps)...';
    });

    try {
      debugPrint('🔄 Starting complete data sequence (HR + Sleep + Steps)');
      await _service.performCompleteDataSequence();
      
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Complete data sequence finished! Check UI sections below.';
      });
      
      debugPrint('✅ Complete data sequence completed!');
    } catch (e) {
      debugPrint('❌ Complete data sequence failed: $e');
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Complete data sequence failed: $e';
      });
    }
  }

  /// Esporta i dati HR correnti in formato CSV o JSON
  Future<void> _exportHRData(String format) async {
    if (_hrHistoryData.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Nessun dato HR da esportare. Scarica prima i dati.'),
          backgroundColor: Colors.orange,
        ),
      );
      return;
    }

    try {
      setState(() {
        _statusMessage = 'Creazione file ${format.toUpperCase()}...';
      });

      String content;
      String fileName;
      
      if (format.toLowerCase() == 'csv') {
        content = _generateCSVContent();
        fileName = 'HR_Export_${DateFormat('yyyyMMdd_HHmmss').format(DateTime.now())}.csv';
      } else if (format.toLowerCase() == 'json') {
        content = _generateJSONContent();
        fileName = 'HR_Export_${DateFormat('yyyyMMdd_HHmmss').format(DateTime.now())}.json';
      } else {
        throw Exception('Formato non supportato: $format');
      }

      // Ottieni la directory per salvare il file
      final directory = await getApplicationDocumentsDirectory();
      final file = File('${directory.path}/$fileName');
      
      // Scrivi il file
      await file.writeAsString(content);
      
      // Conta il numero totale di misurazioni
      int totalMeasurements = 0;
      for (var data in _hrHistoryData) {
        totalMeasurements += data.entries.length;
      }
      
      setState(() {
        _statusMessage = '${format.toUpperCase()} creato con successo: $totalMeasurements misurazioni';
      });

      // Condividi il file
      await Share.shareXFiles(
        [XFile(file.path)],
        text: 'Dati HR CL837 - $totalMeasurements misurazioni',
        subject: 'Esportazione Dati Heart Rate (${format.toUpperCase()})',
      );

      // Mostra messaggio di successo
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('✅ File ${format.toUpperCase()} esportato: $fileName'),
            backgroundColor: Colors.green,
            action: SnackBarAction(
              label: 'OK',
              onPressed: () {},
            ),
          ),
        );
      }

    } catch (e) {
      debugPrint('❌ Errore esportazione ${format.toUpperCase()}: $e');
      setState(() {
        _statusMessage = 'Errore esportazione ${format.toUpperCase()}: $e';
      });
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore esportazione: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
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
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  '📈 Advanced Heart Rate Analysis',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.red,
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.info_outline),
                  onPressed: () => _showHRChartInfo(context),
                  tooltip: 'Chart Information',
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
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
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
              fontSize: 12,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
          Text(
            value,
            style: TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
              color: color,
            ),
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
          _buildZoneIndicator('Resting', '< 60', Colors.blue),
          _buildZoneIndicator('Fat Burn', '60-70', Colors.green),
          _buildZoneIndicator('Cardio', '70-85', Colors.orange),
          _buildZoneIndicator('Peak', '> 85', Colors.red),
        ],
      ),
    );
  }

  Widget _buildZoneIndicator(String zone, String range, Color color) {
    return Row(
      children: [
        Container(
          width: 12,
          height: 12,
          decoration: BoxDecoration(
            color: color,
            shape: BoxShape.circle,
          ),
        ),
        const SizedBox(width: 4),
        Text(
          '$zone: $range',
          style: TextStyle(
            fontSize: 11,
            color: color,
            fontWeight: FontWeight.w500,
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
              _buildStatChip('Average', '${avgHR.toInt()} BPM', Colors.blue),
              _buildStatChip('Std Dev', stdDev.toStringAsFixed(1), Colors.purple),
              _buildStatChip('Samples', '$count', Colors.teal),
              _buildStatChip('Duration', _calculateDuration(), Colors.indigo),
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

  void _showHRChartInfo(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Heart Rate Chart Information'),
        content: const SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text('📊 Chart Features:', style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('• Red line: Actual heart rate measurements'),
              Text('• Blue dashed line: Average heart rate'),
              Text('• Colored dots: HR zone indicators'),
              Text('• Touch points for detailed information'),
              SizedBox(height: 12),
              Text('💓 HR Zones:', style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('• Blue (< 60): Resting zone'),
              Text('• Green (60-70): Fat burn zone'),
              Text('• Orange (70-85): Cardio zone'),
              Text('• Red (> 85): Peak zone'),
              SizedBox(height: 12),
              Text('📈 Statistics:', style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('• Average: Mean heart rate'),
              Text('• Std Dev: Heart rate variability'),
              Text('• Trend: Session progression'),
              Text('• Duration: Total measurement time'),
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
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadHRData,
                icon: _isDownloading 
                    ? const SizedBox(
                        width: 16, 
                        height: 16, 
                        child: CircularProgressIndicator(strokeWidth: 2)
                      )
                    : const Icon(Icons.download),
                label: Text(_isDownloading ? 'Running...' : '🔄 Complete WearManager Test'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 16),

              // WearManager HR Test Section
              const Text(
                '🫀 HR History Test (WearManager Compatible):',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.blue,
                ),
              ),
              const SizedBox(height: 8),

              // UTC Sync Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testUTCSync,
                icon: const Icon(Icons.sync),
                label: const Text('⏰ Sync UTC Time (0x08)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.orange.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // HR Record List Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testHRRecordList,
                icon: const Icon(Icons.schedule),
                label: const Text('📋 Get HR Record List (0x21)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // HR Complete History - AUTOMATIC DOWNLOAD
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadCompleteHRHistory,
                icon: const Icon(Icons.favorite_border),
                label: const Text('💓 Get COMPLETE HR History (Auto)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
                ),
              ),

              const SizedBox(height: 12),

              // Export HR Data Buttons
              Row(
                children: [
                  Expanded(
                    child: ElevatedButton.icon(
                      onPressed: _hrHistoryData.isEmpty ? null : () => _exportHRData('csv'),
                      icon: const Icon(Icons.table_chart),
                      label: const Text('📊 Export CSV'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.orange.shade700,
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 12),
                      ),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: ElevatedButton.icon(
                      onPressed: _hrHistoryData.isEmpty ? null : () => _exportHRData('json'),
                      icon: const Icon(Icons.code),
                      label: const Text('🔗 Export JSON'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.deepOrange.shade700,
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 12),
                      ),
                    ),
                  ),
                ],
              ),

              const SizedBox(height: 12),

              // HR Data Test (manual)
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testHRData,
                icon: const Icon(Icons.data_usage),
                label: const Text('💓 Get HR Data (0x22) - Manual'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 20),

              // Sleep and Steps Test Section
              const Text(
                '🌙👟 Sleep & Steps Test (WearManager Compatible):',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.purple,
                ),
              ),
              const SizedBox(height: 8),

              // Sleep Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testSleepData,
                icon: const Icon(Icons.nightlight_round),
                label: const Text('🌙 Get Sleep Data (0x05)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 8),

              // Comprehensive Sleep Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testAllSleepCommands,
                icon: const Icon(Icons.science),
                label: const Text('🧪 Test ALL Sleep Commands'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.indigo.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // Steps Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _testStepsData,
                icon: const Icon(Icons.directions_walk),
                label: const Text('👟 Get Steps Data (0x40)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),

              const SizedBox(height: 12),

              // Complete Data Test
              ElevatedButton.icon(
                onPressed: _isDownloading ? null : _downloadAllData,
                icon: const Icon(Icons.download_for_offline),
                label: const Text('📊 Download ALL Data (HR+Sleep+Steps)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.indigo.shade700,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 14),
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
                    Text(
                      'Sleep History (${_sleepHistoryData.length} sessions)',
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
                                return Card(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  child: ExpansionTile(
                                    title: Text(
                                      'Sleep ${DateFormat('MMM dd, HH:mm').format(sleep.timestamp)}',
                                      style: const TextStyle(fontWeight: FontWeight.bold),
                                    ),
                                    subtitle: Text(
                                      'Total: ${phases.totalSleep}min (Light: ${phases.lightSleep}min, Deep: ${phases.deepSleep}min)',
                                    ),
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(16.0),
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            Text('💤 Sleep Efficiency: ${phases.sleepEfficiency.toStringAsFixed(1)}%'),
                                            Text('⏰ Total Duration: ${phases.totalMinutes} minutes'),
                                            Text('🌙 Light Sleep: ${phases.lightSleep} minutes'),
                                            Text('🌊 Deep Sleep: ${phases.deepSleep} minutes'),
                                            Text('😴 Awake Time: ${phases.awake} minutes'),
                                            Text('📊 Actions Recorded: ${sleep.actions.length}'),
                                          ],
                                        ),
                                      ),
                                    ],
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
}