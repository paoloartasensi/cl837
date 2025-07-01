import 'package:flutter/material.dart';

class SensorInfoWidget extends StatelessWidget {
  const SensorInfoWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      elevation: 4,
      child: ExpansionTile(
        leading: const Icon(Icons.info_outline, color: Colors.blue),
        title: const Text(
          'Sensor Information',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        children: [
          const Padding(
            padding: EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _InfoSection(
                  title: '🌡️ Temperature',
                  content: 'Measures ambient, wrist, and body temperature using high-precision sensors. '
                      'Body temperature is calculated from wrist contact temperature. '
                      'Readings update every 5 seconds and are immediately available.',
                ),
                SizedBox(height: 12),
                _InfoSection(
                  title: '🫁 SpO2 (Blood Oxygen)',
                  content: 'Uses photoplethysmography (PPG) like medical pulse oximeters. '
                      'Requires 10-30 seconds to stabilize for accurate readings. '
                      'Keep wrist face-up and stay still for best results. '
                      'Similar to Elite HRV: only shows readings with good signal quality.',
                ),
                SizedBox(height: 12),
                _InfoSection(
                  title: '❤️ Heart Rate Variability',
                  content: 'Professional HRV analysis from RR intervals. '
                      'Sessions require minimum 1 minute (like Elite HRV standard). '
                      'Provides RMSSD, SDNN, pNN50 metrics for stress and recovery monitoring.',
                ),
                SizedBox(height: 12),
                _InfoSection(
                  title: '🏃 Activity Tracking',
                  content: 'Real-time step counting, distance estimation, and calorie calculation. '
                      'Uses advanced accelerometer algorithms with ±5% accuracy. '
                      'Updates continuously during movement.',
                ),
                SizedBox(height: 12),
                _InfoSection(
                  title: '🔋 Battery & Extended Data',
                  content: 'Battery level from standard BLE Battery Service (UUID: 180F). '
                      'Extended health data (0x75 command) includes sleep analysis, '
                      'stress levels, and detailed fitness metrics - currently under development.',
                ),
              ],
            ),
          ),
          ButtonBar(
            alignment: MainAxisAlignment.center,
            children: [
              TextButton.icon(
                icon: const Icon(Icons.description),
                label: const Text('Full Documentation'),
                onPressed: () {
                  showDialog(
                    context: context,
                    builder: (context) => const _FullDocumentationDialog(),
                  );
                },
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class _InfoSection extends StatelessWidget {
  final String title;
  final String content;

  const _InfoSection({
    required this.title,
    required this.content,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
        const SizedBox(height: 4),
        Text(
          content,
          style: TextStyle(
            color: Colors.grey[700],
            height: 1.4,
          ),
        ),
      ],
    );
  }
}

class _FullDocumentationDialog extends StatelessWidget {
  const _FullDocumentationDialog();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('CL837 Sensor Technology'),
      content: const SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              'Data Acquisition Methods:',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            ),
            SizedBox(height: 8),
            Text(
              '• Temperature: Triple-sensor system (ambient/wrist/body)\n'
              '• SpO2: PPG optical sensors with quality validation\n'
              '• HRV: ECG-derived RR intervals with professional metrics\n'
              '• Activity: 3-axis accelerometer + gyroscope fusion\n'
              '• Battery: Standard BLE Battery Service protocol',
              style: TextStyle(height: 1.4),
            ),
            SizedBox(height: 16),
            Text(
              'Quality Standards:',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            ),
            SizedBox(height: 8),
            Text(
              '• Medical-grade accuracy (±0.1°C temp, ±2% SpO2)\n'
              '• Elite HRV compatible session protocols\n'
              '• Real-time signal quality monitoring\n'
              '• Automatic artifact rejection\n'
              '• Professional health app standards',
              style: TextStyle(height: 1.4),
            ),
            SizedBox(height: 16),
            Text(
              'Technical Implementation:',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            ),
            SizedBox(height: 8),
            Text(
              '• Chileaf BLE Protocol SDK v0.6\n'
              '• 16-bit data precision with validation\n'
              '• Adaptive timing for sensor stabilization\n'
              '• Flutter BLE+ for reliable connectivity\n'
              '• Optimized for minimal battery impact',
              style: TextStyle(height: 1.4),
            ),
          ],
        ),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: const Text('Close'),
        ),
      ],
    );
  }
}
