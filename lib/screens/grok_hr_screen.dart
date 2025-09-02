import 'dart:async';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../chileaf_extended_service.dart';
import '../models/historical_data.dart';

class GrokHrScreen extends StatefulWidget {
  const GrokHrScreen({super.key});

  @override
  State<GrokHrScreen> createState() => _GrokHrScreenState();
}

class _GrokHrScreenState extends State<GrokHrScreen> {
  final ChileafExtendedService _service = ChileafExtendedService();
  List<HeartRateHistoryData> _hrHistoryData = [];
  bool _isDownloading = false;
  bool _isSyncing = false;
  String _statusMessage = '';
  StreamSubscription? _hrDataSubscription;

  @override
  void initState() {
    super.initState();
    _setupStreams();
  }

  @override
  void dispose() {
    _hrDataSubscription?.cancel();
    super.dispose();
  }

  void _setupStreams() {
    _hrDataSubscription = _service.hrHistoryDataStream.listen((data) {
      setState(() {
        _hrHistoryData = [data]; // Wrap single data in list for compatibility
        _isDownloading = false;
        _statusMessage = 'HR data downloaded successfully';
      });
    });
  }

  Future<void> _downloadHRData() async {
    setState(() {
      _isDownloading = true;
      _statusMessage = 'Downloading HR data...';
    });

    try {
      // Send command 0x21 to get HR list
      await _service.sendCommand([0x21]);

      // Wait a moment for response
      await Future.delayed(const Duration(seconds: 2));

      // Send command 0x22 to get detailed data
      await _service.sendCommand([0x22]);

      // Wait for data to be processed
      await Future.delayed(const Duration(seconds: 5));

      // Send command 0x23 to complete
      await _service.sendCommand([0x23]);

      setState(() {
        _statusMessage = 'Download completed';
      });
    } catch (e) {
      setState(() {
        _isDownloading = false;
        _statusMessage = 'Error downloading HR data: $e';
      });
    }
  }

  Future<void> _syncUTC() async {
    setState(() {
      _isSyncing = true;
      _statusMessage = 'Syncing UTC time...';
    });

    try {
      await _service.syncDeviceTime();
      setState(() {
        _isSyncing = false;
        _statusMessage = 'UTC time synced successfully';
      });
    } catch (e) {
      setState(() {
        _isSyncing = false;
        _statusMessage = 'Error syncing UTC time: $e';
      });
    }
  }

  double _calculateAverageHR() {
    if (_hrHistoryData.isEmpty) return 0.0;

    int totalHR = 0;
    int count = 0;

    for (final session in _hrHistoryData) {
      for (final entry in session.entries) {
        if (entry.heartRate > 0 && entry.heartRate < 200) {
          totalHR += entry.heartRate;
          count++;
        }
      }
    }

    return count > 0 ? totalHR / count : 0.0;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('GROK HR Monitor'),
        backgroundColor: Colors.red.shade700,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // Control buttons
            Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: _isDownloading ? null : _downloadHRData,
                    icon: _isDownloading
                        ? const SizedBox(
                            width: 20,
                            height: 20,
                            child: CircularProgressIndicator(strokeWidth: 2),
                          )
                        : const Icon(Icons.download),
                    label: const Text('Download HR Data'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue,
                      padding: const EdgeInsets.symmetric(vertical: 12),
                    ),
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: _isSyncing ? null : _syncUTC,
                    icon: _isSyncing
                        ? const SizedBox(
                            width: 20,
                            height: 20,
                            child: CircularProgressIndicator(strokeWidth: 2),
                          )
                        : const Icon(Icons.sync),
                    label: const Text('Sync UTC'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.green,
                      padding: const EdgeInsets.symmetric(vertical: 12),
                    ),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 16),

            // Status message
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.grey.shade100,
                borderRadius: BorderRadius.circular(8),
              ),
              child: Text(
                _statusMessage,
                style: const TextStyle(fontSize: 14),
                textAlign: TextAlign.center,
              ),
            ),

            const SizedBox(height: 16),

            // Stats cards
            Row(
              children: [
                Expanded(
                  child: _buildStatCard(
                    'Total Sessions',
                    _hrHistoryData.length.toString(),
                    Icons.favorite,
                    Colors.red,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: _buildStatCard(
                    'Average HR',
                    '${_calculateAverageHR().toStringAsFixed(1)} bpm',
                    Icons.monitor_heart,
                    Colors.purple,
                  ),
                ),
              ],
            ),

            const SizedBox(height: 16),

            // Chart
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(12),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.shade300,
                      blurRadius: 8,
                      offset: const Offset(0, 4),
                    ),
                  ],
                ),
                child: _buildSimpleChart(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatCard(String title, String value, IconData icon, Color color) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Column(
        children: [
          Icon(icon, color: color, size: 32),
          const SizedBox(height: 8),
          Text(
            value,
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            title,
            style: TextStyle(
              fontSize: 12,
              color: color.withOpacity(0.7),
            ),
            textAlign: TextAlign.center,
          ),
        ],
      ),
    );
  }

  Widget _buildSimpleChart() {
    if (_hrHistoryData.isEmpty) {
      return const Center(
        child: Text(
          'No HR data available\nTap "Download HR Data" to get started',
          textAlign: TextAlign.center,
          style: TextStyle(color: Colors.grey),
        ),
      );
    }

    return CustomPaint(
      painter: ChartLinePainter(_hrHistoryData),
      child: Container(),
    );
  }
}

class ChartLinePainter extends CustomPainter {
  final List<HeartRateHistoryData> data;

  ChartLinePainter(this.data);

  @override
  void paint(Canvas canvas, Size size) {
    if (data.isEmpty) return;

    final paint = Paint()
      ..color = Colors.red.shade400
      ..strokeWidth = 2
      ..style = PaintingStyle.stroke;

    final gridPaint = Paint()
      ..color = Colors.grey.shade300
      ..strokeWidth = 1;

    // Draw grid
    for (int i = 0; i <= 10; i++) {
      final y = size.height * i / 10;
      canvas.drawLine(Offset(0, y), Offset(size.width, y), gridPaint);
    }

    // Collect all HR entries with timestamps
    final List<HeartRateHistoryEntry> allEntries = [];
    for (final session in data) {
      allEntries.addAll(session.entries);
    }

    if (allEntries.isEmpty) return;

    // Sort by timestamp
    allEntries.sort((a, b) => a.time.compareTo(b.time));

    // Find min/max values
    final minHR = allEntries.map((e) => e.heartRate).reduce((a, b) => a < b ? a : b);
    final maxHR = allEntries.map((e) => e.heartRate).reduce((a, b) => a > b ? a : b);

    final hrRange = maxHR - minHR;
    final timeRange = allEntries.last.time.difference(allEntries.first.time).inMinutes;

    if (timeRange == 0 || hrRange == 0) return;

    final path = Path();
    bool firstPoint = true;

    for (final entry in allEntries) {
      final timeDiff = entry.time.difference(allEntries.first.time).inMinutes;
      final x = size.width * timeDiff / timeRange;
      final y = size.height - (size.height * (entry.heartRate - minHR) / hrRange);

      if (firstPoint) {
        path.moveTo(x, y);
        firstPoint = false;
      } else {
        path.lineTo(x, y);
      }
    }

    canvas.drawPath(path, paint);

    // Draw data points
    final pointPaint = Paint()
      ..color = Colors.red.shade600
      ..style = PaintingStyle.fill;

    for (final entry in allEntries) {
      final timeDiff = entry.time.difference(allEntries.first.time).inMinutes;
      final x = size.width * timeDiff / timeRange;
      final y = size.height - (size.height * (entry.heartRate - minHR) / hrRange);

      canvas.drawCircle(Offset(x, y), 3, pointPaint);
    }

    // Draw labels
    final textPainter = TextPainter(
      textAlign: TextAlign.center,
    );

    // Y-axis labels (HR values)
    for (int i = 0; i <= 5; i++) {
      final hr = minHR + (hrRange * i / 5);
      final y = size.height - (size.height * i / 5);

      textPainter.text = TextSpan(
        text: hr.toStringAsFixed(0),
        style: TextStyle(color: Colors.grey.shade600, fontSize: 10),
      );
      textPainter.layout();
      textPainter.paint(canvas, Offset(-25, y - 5));
    }

    // X-axis labels (time)
    if (allEntries.length >= 2) {
      final startTime = allEntries.first.time;

      for (int i = 0; i <= 5; i++) {
        final time = startTime.add(Duration(
          minutes: (timeRange * i / 5).toInt(),
        ));

        final x = size.width * i / 5;

        textPainter.text = TextSpan(
          text: DateFormat('HH:mm').format(time),
          style: TextStyle(color: Colors.grey.shade600, fontSize: 10),
        );
        textPainter.layout();
        textPainter.paint(canvas, Offset(x - 15, size.height + 5));
      }
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
