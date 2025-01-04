import 'dart:async';
import 'dart:collection';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:intl/intl.dart';
import 'accelerometer_service.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  FlutterBluePlus.setLogLevel(LogLevel.verbose, color: true);
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'CL837 Accelerometer',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const BluetoothAccelerometerPage(),
    );
  }
}

class OscilloscopePainter extends CustomPainter {
  final Queue<AccelerometerData> data;
  static const int maxPoints = 100;

  OscilloscopePainter(this.data);

  @override
  void paint(Canvas canvas, Size size) {
    final xPath = Path();
    final yPath = Path();
    final zPath = Path();

    final xPaint = Paint()
      ..color = Colors.red
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2;

    final yPaint = Paint()
      ..color = Colors.green
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2;

    final zPaint = Paint()
      ..color = Colors.blue
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2;

    // Draw grid
    final gridPaint = Paint()
      ..color = Colors.grey.withOpacity(0.2)
      ..strokeWidth = 1;

    // Horizontal grid lines
    for (int i = 0; i <= 4; i++) {
      double y = size.height * i / 4;
      canvas.drawLine(Offset(0, y), Offset(size.width, y), gridPaint);
    }

    // Vertical grid lines
    for (int i = 0; i <= 8; i++) {
      double x = size.width * i / 8;
      canvas.drawLine(Offset(x, 0), Offset(x, size.height), gridPaint);
    }

    // Zero line
    final zeroPaint = Paint()
      ..color = Colors.grey.withOpacity(0.5)
      ..strokeWidth = 1;
    canvas.drawLine(
      Offset(0, size.height / 2),
      Offset(size.width, size.height / 2),
      zeroPaint
    );

    if (data.isEmpty) return;

    var points = data.toList();
    for (var i = 0; i < points.length; i++) {
      var point = points[i];
      double x = size.width * i / maxPoints;
      
      // Normalize values from ±8g to 0..1
      double yX = size.height * (1 - (point.x + 8) / 16);
      double yY = size.height * (1 - (point.y + 8) / 16);
      double yZ = size.height * (1 - (point.z + 8) / 16);

      if (i == 0) {
        xPath.moveTo(x, yX);
        yPath.moveTo(x, yY);
        zPath.moveTo(x, yZ);
      } else {
        xPath.lineTo(x, yX);
        yPath.lineTo(x, yY);
        zPath.lineTo(x, yZ);
      }
    }

    canvas.drawPath(xPath, xPaint);
    canvas.drawPath(yPath, yPaint);
    canvas.drawPath(zPath, zPaint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}

class BluetoothAccelerometerPage extends StatefulWidget {
  const BluetoothAccelerometerPage({Key? key}) : super(key: key);

  @override
  State<BluetoothAccelerometerPage> createState() => _BluetoothAccelerometerPageState();
}

class _BluetoothAccelerometerPageState extends State<BluetoothAccelerometerPage> {
  final AccelerometerService _accelerometerService = AccelerometerService();
  static const String targetDeviceName = 'CL837-0753644';
  
  BluetoothDevice? connectedDevice;
  AccelerometerData? latestData;
  bool isScanning = false;
  bool isConnecting = false;
  
  final Queue<AccelerometerData> plotData = Queue();
  static const int maxPlotPoints = 100;
  
  StreamSubscription? _dataSubscription;
  DataRateMode _selectedDataRate = DataRateMode.normal;

  @override
  void initState() {
    super.initState();
    _initializeBluetooth();
  }

  Future<void> _initializeBluetooth() async {
    await _requestPermissions();
    await FlutterBluePlus.turnOn();
  }

  Future<void> _requestPermissions() async {
    final permissions = [
      Permission.bluetooth,
      Permission.bluetoothScan,
      Permission.bluetoothConnect,
      Permission.location,
    ];

    for (var permission in permissions) {
      await permission.request();
    }
  }

  Future<void> startScan() async {
    if (isScanning) return;

    setState(() {
      isScanning = true;
    });

    try {
      await FlutterBluePlus.startScan(timeout: const Duration(seconds: 5));
      
      FlutterBluePlus.scanResults.listen((results) {
        for (ScanResult r in results) {
          debugPrint('Found device: ${r.device.platformName}');
          if (r.device.platformName == targetDeviceName) {
            connectToDevice(r.device);
            FlutterBluePlus.stopScan();
            break;
          }
        }
      });

      await Future.delayed(const Duration(seconds: 5));
      if (mounted) {
        setState(() {
          isScanning = false;
        });
      }
    } catch (e) {
      debugPrint('Error during scan: $e');
      if (mounted) {
        setState(() {
          isScanning = false;
        });
      }
      showError('Error during scan: $e');
    }
  }

  Future<void> connectToDevice(BluetoothDevice device) async {
    if (isConnecting) return;

    setState(() {
      isConnecting = true;
    });

    try {
      await device.connect(timeout: const Duration(seconds: 10));
      await _accelerometerService.start(device);

      _dataSubscription = _accelerometerService.dataStream.listen((data) {
        setState(() {
          latestData = data;
          plotData.add(data);
          if (plotData.length > maxPlotPoints) {
            plotData.removeFirst();
          }
        });
      });
      
      if (mounted) {
        setState(() {
          connectedDevice = device;
          isConnecting = false;
        });
      }

    } catch (e) {
      debugPrint('Error during connection: $e');
      if (mounted) {
        setState(() {
          isConnecting = false;
        });
      }
      showError('Error during connection: $e');
    }
  }

  Future<void> disconnectDevice() async {
    try {
      await _dataSubscription?.cancel();
      await _accelerometerService.stop();
      await connectedDevice?.disconnect();
    } catch (e) {
      debugPrint('Error during disconnect: $e');
    }
    
    if (mounted) {
      setState(() {
        connectedDevice = null;
        latestData = null;
        plotData.clear();
      });
    }
  }

  void showError(String message) {
    if (!mounted) return;
    
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: Colors.red,
        duration: const Duration(seconds: 3),
      ),
    );
  }

  Widget _buildDataRateDropdown() {
    return Card(
      elevation: 4,
      margin: const EdgeInsets.symmetric(horizontal: 16.0),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16.0),
        child: DropdownButton<DataRateMode>(
          value: _selectedDataRate,
          isExpanded: true,
          underline: Container(),
          items: DataRateMode.values.map((mode) {
            return DropdownMenuItem<DataRateMode>(
              value: mode,
              child: Text(mode.label),
            );
          }).toList(),
          onChanged: (DataRateMode? newMode) {
            if (newMode != null) {
              setState(() {
                _selectedDataRate = newMode;
                _accelerometerService.setDataRateMode(newMode);
              });
            }
          },
        ),
      ),
    );
  }

  Widget _buildCurrentValuesCard() {
    return Card(
      elevation: 4,
      margin: const EdgeInsets.all(16.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  'Accelerometer Data',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                if (latestData != null)
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      Text(
                        DateFormat('HH:mm:ss.SSS').format(latestData!.timestamp),
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                      Text(
                        'Rate: ${latestData!.frequency.toStringAsFixed(1)} Hz',
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                      Text(
                        'Interval: ${latestData!.interval.toStringAsFixed(1)} ms',
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                    ],
                  ),
              ],
            ),
            const SizedBox(height: 16),
            if (latestData != null) ...[
              _buildAxisRow('X', latestData!.x, Colors.red),
              const SizedBox(height: 8),
              _buildAxisRow('Y', latestData!.y, Colors.green),
              const SizedBox(height: 8),
              _buildAxisRow('Z', latestData!.z, Colors.blue),
              const SizedBox(height: 16),
              Container(
                height: 200,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.withOpacity(0.2)),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(8),
                  child: CustomPaint(
                    painter: OscilloscopePainter(plotData),
                    size: const Size(double.infinity, 200),
                  ),
                ),
              ),
            ] else
              const Center(
                child: Text(
                  'Waiting for data...',
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.grey,
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }

  Widget _buildAxisRow(String axis, double value, Color color) {
    return Row(
      children: [
        SizedBox(
          width: 30,
          child: Text(
            axis,
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
        ),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              LinearProgressIndicator(
                value: (value + 8.0) / 16.0, // Normalize value from -8g to +8g
                backgroundColor: color.withOpacity(0.1),
                valueColor: AlwaysStoppedAnimation<Color>(color),
              ),
              const SizedBox(height: 4),
              Text(
                '${value.toStringAsFixed(3)} g',
                style: const TextStyle(
                  fontFamily: 'Monospace',
                  fontSize: 14,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  @override
  void dispose() {
    _dataSubscription?.cancel();
    _accelerometerService.dispose();
    disconnectDevice();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Accelerometer'),
        actions: [
          if (connectedDevice != null)
            IconButton(
              icon: const Icon(Icons.bluetooth_connected),
              tooltip: 'Disconnect',
              onPressed: disconnectDevice,
            ),
        ],
      ),
      body: Column(
        children: [
          Container(
            color: Colors.grey[100],
            padding: const EdgeInsets.all(16.0),
            child: Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    icon: const Icon(Icons.search),
                    label: Text(
                      isScanning ? 'Scanning...' : 
                      isConnecting ? 'Connecting...' : 
                      'Scan for Device'
                    ),
                    onPressed: (isScanning || isConnecting) ? null : startScan,
                  ),
                ),
              ],
            ),
          ),
          if (connectedDevice != null) ...[
            _buildDataRateDropdown(),
            Expanded(
              child: SingleChildScrollView(
                child: _buildCurrentValuesCard(),
              ),
            ),
          ],
        ],
      ),
    );
  }
}