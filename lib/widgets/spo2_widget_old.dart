import 'package:flutter/material.dart';
import '../models/spo2_data.dart';

class SpO2Widget extends StatelessWidget {
  final SpO2Data? spo2Data;
  final bool isConnected;

  const SpO2Widget({
    super.key,
    this.spo2Data,
    required this.isConnected,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(
                  Icons.air,
                  color: isConnected ? _getSpO2Color() : Colors.grey,
                  size: 24,
                ),
                const SizedBox(width: 8),
                Text(
                  'SpO₂ (Blood Oxygen)',
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            if (spo2Data != null) ...[
              Center(
                child: Column(
                  children: [
                    Text(
                      '${spo2Data!.spo2Value}%',
                      style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                        fontWeight: FontWeight.bold,
                        color: _getSpO2Color(),
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text(
                      _getSpO2Status(),
                      style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                        color: _getSpO2Color(),
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 12),
              _buildDataRow('Wrist Position', spo2Data!.correctWristPosture ? 'Correct' : 'Incorrect'),
              _buildDataRow('Signal Quality', _getSignalQualityText()),
              _buildDataRow('Wearing Status', spo2Data!.isWearing ? 'Wearing' : 'Not Wearing'),
              const SizedBox(height: 8),
              Text(
                'Updated: ${_formatTime(spo2Data!.timestamp)}',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: Colors.grey[600],
                ),
              ),
            ] else ...[
              Center(
                child: Column(
                  children: [
                    Text(
                      '--',
                      style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                        fontWeight: FontWeight.bold,
                        color: Colors.grey,
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text(
                      isConnected ? 'Waiting for SpO₂ data...' : 'Not connected',
                      style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                        color: Colors.grey[600],
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ],
        ),
      ),
    );
  }

  Color _getSpO2Color() {
    if (spo2Data == null) return Colors.grey;
    
    final value = spo2Data!.spo2Value;
    if (value == null) return Colors.grey;
    if (value >= 95) return Colors.green;
    if (value >= 90) return Colors.orange;
    return Colors.red;
  }

  String _getSpO2Status() {
    if (spo2Data == null) return 'No data';
    
    final value = spo2Data!.spo2Value;
    if (value == null) return 'Waiting...';
    if (value >= 95) return 'Normal';
    if (value >= 90) return 'Low';
    return 'Very Low';
  }

  String _getSignalQualityText() {
    if (spo2Data == null) return 'Unknown';
    
    final quality = spo2Data!.signalQuality;
    if (quality == 0) return 'No Signal';
    if (quality < 8) return 'Weak';
    if (quality >= 15) return 'Good';
    return 'Fair';
  }

  Widget _buildDataRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: const TextStyle(fontSize: 14),
          ),
          Text(
            value,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:'
        '${time.minute.toString().padLeft(2, '0')}:'
        '${time.second.toString().padLeft(2, '0')}';
  }
}
