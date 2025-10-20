import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'grok_hr_screen.dart';
import 'sleep_premium_screen.dart';
import 'advanced_features_test_screen.dart';
import '../chileaf_extended_service.dart';

/// Unified Home Screen - Persistent BT connection with individual data download
class UnifiedHomeScreen extends StatefulWidget {
  const UnifiedHomeScreen({super.key});

  @override
  State<UnifiedHomeScreen> createState() => _UnifiedHomeScreenState();
}

class _UnifiedHomeScreenState extends State<UnifiedHomeScreen> {
  final ChileafExtendedService _service = ChileafExtendedService();
  
  // BLE Connection
  BluetoothDevice? _connectedDevice;
  List<BluetoothDevice> _foundDevices = [];
  bool _isScanning = false;
  bool _isConnecting = false;
  int _batteryLevel = 0;
  StreamSubscription<BluetoothConnectionState>? _connectionSubscription;
  StreamSubscription<List<ScanResult>>? _scanSubscription;
  
  // Stream controller to notify dialog of device updates
  final StreamController<void> _dialogUpdateController = StreamController<void>.broadcast();

  // Data download status
  final Map<String, bool> _downloading = {};
  final Map<String, String> _lastDownload = {};
  final Map<String, int> _dataCount = {};

  @override
  void initState() {
    super.initState();
    _initializeBluetooth();
    _setupStreams();
  }

  @override
  void dispose() {
    _dialogUpdateController.close();
    _scanSubscription?.cancel();
    _connectionSubscription?.cancel();
    _disconnect();
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
    // HR History List
    _service.hrHistoryListStream.listen((list) {
      if (mounted) {
        setState(() {
          _dataCount['hr'] = list.timestamps.length;
          _downloading['hr'] = false;
          _lastDownload['hr'] = 'Now';
        });
      }
    });

    // Sleep History - Use sleepHistoryStream
    _service.sleepHistoryStream.listen((list) {
      if (mounted) {
        setState(() {
          _dataCount['sleep'] = list.length;
          _downloading['sleep'] = false;
          _lastDownload['sleep'] = 'Now';
        });
      }
    });

    // Steps History - Use stepsHistoryStream
    _service.stepsHistoryStream.listen((list) {
      if (mounted) {
        setState(() {
          _dataCount['steps'] = list.length;
          _downloading['steps'] = false;
          _lastDownload['steps'] = 'Now';
        });
      }
    });

    // User Info
    _service.userInfoStream.listen((userInfo) {
      if (mounted) {
        setState(() {
          _downloading['user'] = false;
          _lastDownload['user'] = 'Age: ${userInfo.age}, BMI: ${userInfo.bmi.toStringAsFixed(1)}';
        });
      }
    });

    // Sport Health
    _service.sportHealthStream.listen((data) {
      if (mounted) {
        setState(() {
          _downloading['sport'] = false;
          _lastDownload['sport'] = 'VO2: ${data.vo2Max}, HRV: ${data.totalPower?.toStringAsFixed(0) ?? "N/A"}';
        });
      }
    });

    // Battery Level - use real-time HR stream as proxy or check manually
    _service.realTimeHeartRateStream.listen((hr) {
      // Battery updates might come through other channels
    });
  }

  // ===== BLE CONNECTION =====

  Future<void> _startScan() async {
    if (_isScanning) return;

    debugPrint('🔍 Starting BLE scan...');

    // Cancel previous scan subscription
    await _scanSubscription?.cancel();

    setState(() {
      _isScanning = true;
      _foundDevices = []; // Create new list instead of clearing
    });

    try {
      // Check if Bluetooth is available
      if (await FlutterBluePlus.isSupported == false) {
        debugPrint("❌ Bluetooth not supported by this device");
        return;
      }

      // Check Bluetooth adapter state
      final adapterState = await FlutterBluePlus.adapterState.first;
      debugPrint('📱 Bluetooth adapter state: $adapterState');
      
      if (adapterState != BluetoothAdapterState.on) {
        debugPrint("❌ Bluetooth is off, trying to turn on...");
        await FlutterBluePlus.turnOn();
        await Future.delayed(const Duration(seconds: 2));
      }

      // Start scan
      debugPrint('🔍 Starting scan with 10s timeout...');
      await FlutterBluePlus.startScan(
        timeout: const Duration(seconds: 10),
        androidUsesFineLocation: true,
      );

      // Listen to scan results
      _scanSubscription = FlutterBluePlus.scanResults.listen((results) {
        debugPrint('📡 Scan results received: ${results.length} devices');
        
        if (!mounted) return;
        
        final List<BluetoothDevice> newDevices = [];
        for (var result in results) {
          final name = result.device.platformName;
          final id = result.device.remoteId.toString();
          
          debugPrint('  Device: $name ($id)');
          
          // Accept ANY device for testing
          if (!_foundDevices.any((d) => d.remoteId == result.device.remoteId)) {
            newDevices.add(result.device);
            debugPrint('  ✅ Added: $name');
          }
        }
        
        if (newDevices.isNotEmpty && mounted) {
          setState(() {
            _foundDevices = [..._foundDevices, ...newDevices];
            debugPrint('📱 Total devices found: ${_foundDevices.length}');
          });
          // Notify dialog to update
          _dialogUpdateController.add(null);
        }
      });

      // Wait for scan to complete
      await Future.delayed(const Duration(seconds: 10));
      debugPrint('⏱️ Scan timeout reached');
      
    } catch (e) {
      debugPrint('❌ Scan error: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Scan error: $e'), backgroundColor: Colors.red),
        );
      }
    } finally {
      await FlutterBluePlus.stopScan();
      debugPrint('🛑 Scan stopped. Found ${_foundDevices.length} devices');
      if (mounted) {
        setState(() => _isScanning = false);
      }
    }
  }

  Future<void> _connectToDevice(BluetoothDevice device) async {
    if (_isConnecting) return;

    setState(() {
      _isConnecting = true;
    });

    try {
      // Connect with license parameter (required by flutter_blue_plus)
      await device.connect(mtu: null, license: License.free);
      
      // Wait a bit for connection to stabilize
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Discover services (this triggers ChileafExtendedService auto-configuration)
      await device.discoverServices();

      _connectionSubscription = device.connectionState.listen((state) {
        if (mounted) {
          setState(() {
            if (state == BluetoothConnectionState.connected) {
              _connectedDevice = device;
            } else {
              _connectedDevice = null;
            }
          });
        }
      });

      setState(() {
        _connectedDevice = device;
      });

      // Request initial data
      await _service.requestUserInfo();
      await _service.getBodyHealth();

      if (mounted) {
        Navigator.pop(context); // Close scan dialog
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('✅ Device connected successfully!')),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Connection failed: $e')),
        );
      }
    } finally {
      setState(() => _isConnecting = false);
    }
  }

  Future<void> _disconnect() async {
    if (_connectedDevice != null) {
      await _connectedDevice!.disconnect();
      setState(() {
        _connectedDevice = null;
        _batteryLevel = 0;
      });
    }
  }

  void _showScanDialog() {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => StreamBuilder<void>(
        stream: _dialogUpdateController.stream,
        builder: (context, snapshot) {
          // Auto-start scan when dialog first builds
          if (!_isScanning && _foundDevices.isEmpty) {
            Future.microtask(() => _startScan());
          }

          return AlertDialog(
            title: const Text('🔍 Scan for Devices'),
            content: SizedBox(
              width: double.maxFinite,
              height: 300,
              child: _isScanning && _foundDevices.isEmpty
                  ? const Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          CircularProgressIndicator(),
                          SizedBox(height: 16),
                          Text('Scanning for CL837 devices...'),
                        ],
                      ),
                    )
                  : _foundDevices.isEmpty && !_isScanning
                      ? Center(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Icon(Icons.bluetooth_disabled, size: 48, color: Colors.grey),
                              const SizedBox(height: 16),
                              const Text('No devices found'),
                              const SizedBox(height: 8),
                              const Text(
                                'Make sure your device is on and nearby',
                                style: TextStyle(fontSize: 12, color: Colors.grey),
                                textAlign: TextAlign.center,
                              ),
                            ],
                          ),
                        )
                      : ListView.builder(
                          itemCount: _foundDevices.length,
                          itemBuilder: (context, index) {
                            final device = _foundDevices[index];
                            return Card(
                              child: ListTile(
                                leading: const Icon(Icons.bluetooth, color: Colors.blue),
                                title: Text(device.platformName.isEmpty ? 'Unknown Device' : device.platformName),
                                subtitle: Text(device.remoteId.toString()),
                                trailing: _isConnecting
                                    ? const SizedBox(
                                        width: 20,
                                        height: 20,
                                        child: CircularProgressIndicator(strokeWidth: 2),
                                      )
                                    : const Icon(Icons.arrow_forward),
                                onTap: () => _connectToDevice(device),
                              ),
                            );
                          },
                        ),
            ),
            actions: [
              TextButton(
                onPressed: () {
                  _scanSubscription?.cancel();
                  FlutterBluePlus.stopScan();
                  Navigator.pop(context);
                },
                child: const Text('Cancel'),
              ),
              if (!_isScanning)
                ElevatedButton.icon(
                  onPressed: () async {
                    setState(() => _foundDevices = []);
                    _dialogUpdateController.add(null);
                    await _startScan();
                  },
                  icon: const Icon(Icons.refresh),
                  label: const Text('Rescan'),
                ),
            ],
          );
        },
      ),
    );
  }

  // ===== DATA DOWNLOAD =====

  Future<void> _downloadHRHistory() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['hr'] = true);
    await _service.requestHRHistoryList();
  }

  Future<void> _downloadSleepHistory() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['sleep'] = true);
    await _service.requestOptimizedSleepHistory(force: true);
  }

  Future<void> _downloadStepsHistory() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['steps'] = true);
    await _service.requestStepIntervalHistory();
  }

  Future<void> _downloadUserInfo() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['user'] = true);
    await _service.requestUserInfo();
  }

  Future<void> _downloadSportHealth() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    setState(() => _downloading['sport'] = true);
    await _service.getBodyHealth();
  }

  Future<void> _downloadRealTimeHR() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    // startHeartRateMonitoring is the correct method name
    await _service.startHeartRateMonitoring();
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('▶️ Real-time HR monitoring started')),
      );
    }
  }

  Future<void> _downloadRealTimeSteps() async {
    if (_connectedDevice == null) {
      _showNotConnectedSnackbar();
      return;
    }
    // Use getBodyHealth for current step count (real-time steps is part of sport health)
    await _service.getBodyHealth();
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('▶️ Body health refreshed (includes steps)')),
      );
    }
  }

  void _showNotConnectedSnackbar() {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('❌ Please connect to device first'),
        backgroundColor: Colors.red,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(),
      body: _buildBody(),
      floatingActionButton: _connectedDevice == null
          ? FloatingActionButton.extended(
              onPressed: _showScanDialog,
              icon: const Icon(Icons.bluetooth_searching),
              label: const Text('Connect Device'),
              backgroundColor: Colors.deepPurple,
            )
          : null,
    );
  }

  PreferredSizeWidget _buildAppBar() {
    return AppBar(
      title: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(
            _connectedDevice != null ? Icons.bluetooth_connected : Icons.bluetooth_disabled,
            color: Colors.white,
          ),
          const SizedBox(width: 8),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                _connectedDevice != null ? 'Connected' : 'Not Connected',
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
              if (_connectedDevice != null)
                Text(
                  _connectedDevice!.platformName,
                  style: const TextStyle(fontSize: 11),
                ),
            ],
          ),
        ],
      ),
      backgroundColor: _connectedDevice != null ? Colors.green.shade700 : Colors.grey.shade700,
      foregroundColor: Colors.white,
      actions: [
        if (_batteryLevel > 0)
          Center(
            child: Padding(
              padding: const EdgeInsets.only(right: 8),
              child: Row(
                children: [
                  Icon(
                    _batteryLevel > 20 ? Icons.battery_std : Icons.battery_alert,
                    size: 20,
                  ),
                  Text('$_batteryLevel%', style: const TextStyle(fontSize: 14)),
                ],
              ),
            ),
          ),
        if (_connectedDevice != null)
          IconButton(
            icon: const Icon(Icons.bluetooth_disabled),
            onPressed: _disconnect,
            tooltip: 'Disconnect',
          ),
        if (_connectedDevice == null)
          IconButton(
            icon: const Icon(Icons.bluetooth_searching),
            onPressed: _showScanDialog,
            tooltip: 'Connect',
          ),
      ],
    );
  }

  Widget _buildBody() {
    if (_connectedDevice == null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.bluetooth_disabled, size: 80, color: Colors.grey.shade400),
            const SizedBox(height: 24),
            Text(
              'No Device Connected',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Colors.grey.shade700),
            ),
            const SizedBox(height: 8),
            Text(
              'Tap the button below to scan for devices',
              style: TextStyle(fontSize: 16, color: Colors.grey.shade600),
            ),
          ],
        ),
      );
    }

    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            '📥 Download Data',
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          Text(
            'Download individual data from your CL837 device',
            style: TextStyle(fontSize: 14, color: Colors.grey.shade600),
          ),
          const SizedBox(height: 24),
          
          // Historical Data Section
          _buildSectionTitle('Historical Data'),
          _buildDownloadTile(
            title: 'Heart Rate History',
            subtitle: _lastDownload['hr'] ?? 'Not downloaded yet',
            icon: Icons.favorite,
            color: Colors.red,
            count: _dataCount['hr'],
            isDownloading: _downloading['hr'] ?? false,
            onDownload: _downloadHRHistory,
          ),
          _buildDownloadTile(
            title: 'Sleep History',
            subtitle: _lastDownload['sleep'] ?? 'Not downloaded yet',
            icon: Icons.nightlight_round,
            color: Colors.deepPurple,
            count: _dataCount['sleep'],
            isDownloading: _downloading['sleep'] ?? false,
            onDownload: _downloadSleepHistory,
          ),
          _buildDownloadTile(
            title: 'Steps History',
            subtitle: _lastDownload['steps'] ?? 'Not downloaded yet',
            icon: Icons.directions_walk,
            color: Colors.green,
            count: _dataCount['steps'],
            isDownloading: _downloading['steps'] ?? false,
            onDownload: _downloadStepsHistory,
          ),
          
          const SizedBox(height: 24),
          
          // Profile & Health Section
          _buildSectionTitle('Profile & Health'),
          _buildDownloadTile(
            title: 'User Info',
            subtitle: _lastDownload['user'] ?? 'Age, weight, height, BMI',
            icon: Icons.person,
            color: Colors.blue,
            isDownloading: _downloading['user'] ?? false,
            onDownload: _downloadUserInfo,
          ),
          _buildDownloadTile(
            title: 'Sport Health',
            subtitle: _lastDownload['sport'] ?? 'VO2 Max, HRV, Stress, Stamina',
            icon: Icons.fitness_center,
            color: Colors.orange,
            isDownloading: _downloading['sport'] ?? false,
            onDownload: _downloadSportHealth,
          ),
          
          const SizedBox(height: 24),
          
          // Real-time Monitoring Section
          _buildSectionTitle('Real-time Monitoring'),
          _buildDownloadTile(
            title: 'Real-time Heart Rate',
            subtitle: 'Start continuous HR monitoring',
            icon: Icons.monitor_heart,
            color: Colors.pink,
            isDownloading: false,
            onDownload: _downloadRealTimeHR,
          ),
          _buildDownloadTile(
            title: 'Real-time Steps',
            subtitle: 'Start continuous steps monitoring',
            icon: Icons.directions_run,
            color: Colors.teal,
            isDownloading: false,
            onDownload: _downloadRealTimeSteps,
          ),
          
          const SizedBox(height: 32),
          
          // Analysis Tools Section
          const Text(
            '📊 Analysis Tools',
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16),
          
          Row(
            children: [
              Expanded(
                child: _buildAnalysisButton(
                  title: 'HR Analysis',
                  icon: Icons.show_chart,
                  color: Colors.red,
                  onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const GrokHrScreen())),
                ),
              ),
              const SizedBox(width: 12),
              Expanded(
                child: _buildAnalysisButton(
                  title: 'Sleep Premium',
                  icon: Icons.bedtime,
                  color: Colors.deepPurple,
                  onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => SleepPremiumScreen(chileafService: _service))),
                ),
              ),
            ],
          ),
          const SizedBox(height: 12),
          SizedBox(
            width: double.infinity,
            child: _buildAnalysisButton(
              title: 'Advanced Features',
              icon: Icons.settings,
              color: Colors.blueGrey,
              onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => AdvancedFeaturesTestScreen(service: _service))),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSectionTitle(String title) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Text(
        title,
        style: TextStyle(
          fontSize: 18,
          fontWeight: FontWeight.w600,
          color: Colors.grey.shade700,
        ),
      ),
    );
  }

  Widget _buildDownloadTile({
    required String title,
    required String subtitle,
    required IconData icon,
    required Color color,
    int? count,
    required bool isDownloading,
    required VoidCallback onDownload,
  }) {
    return Card(
      margin: const EdgeInsets.only(bottom: 8),
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: color.withOpacity(0.1),
          child: Icon(icon, color: color, size: 22),
        ),
        title: Row(
          children: [
            Expanded(child: Text(title, style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 15))),
            if (count != null)
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                decoration: BoxDecoration(
                  color: color.withOpacity(0.1),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Text(
                  '$count',
                  style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: color),
                ),
              ),
          ],
        ),
        subtitle: Text(subtitle, style: const TextStyle(fontSize: 13)),
        trailing: isDownloading
            ? const SizedBox(
                width: 24,
                height: 24,
                child: CircularProgressIndicator(strokeWidth: 2),
              )
            : IconButton(
                icon: Icon(Icons.download, color: color),
                onPressed: onDownload,
                tooltip: 'Download',
              ),
      ),
    );
  }

  Widget _buildAnalysisButton({
    required String title,
    required IconData icon,
    required Color color,
    required VoidCallback onTap,
  }) {
    return ElevatedButton.icon(
      onPressed: onTap,
      icon: Icon(icon),
      label: Text(title),
      style: ElevatedButton.styleFrom(
        backgroundColor: color,
        foregroundColor: Colors.white,
        padding: const EdgeInsets.symmetric(vertical: 16),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      ),
    );
  }
}
