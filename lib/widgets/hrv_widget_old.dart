import 'package:flutter/material.dart';
import '../models/hrv_data.dart';

class HRVWidget extends StatelessWidget {
  final HRVData? hrvData;
  final bool isConnected;

  const HRVWidget({
    super.key,
    this.hrvData,
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
                  Icons.favorite_border,
                  color: isConnected ? Colors.green : Colors.grey,
                  size: 24,
                ),
                const SizedBox(width: 8),
                Text(
                  'HRV (Heart Rate Variability)',
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            if (hrvData != null) ...[
              _buildDataRow('RMSSD', '${hrvData!.rmssd.toStringAsFixed(1)} ms'),
              _buildDataRow('SDNN', '${hrvData!.sdnn.toStringAsFixed(1)} ms'),
              _buildDataRow('Mean RR', '${hrvData!.meanRR.toStringAsFixed(1)} ms'),
              _buildDataRow('Median RR', '${hrvData!.medianRR.toStringAsFixed(1)} ms'),
              _buildDataRow('RR Intervals', '${hrvData!.rrIntervals.length}'),
              if (hrvData!.rrIntervals.isNotEmpty) ...[
                const SizedBox(height: 8),
                Text(
                  'Latest RR Intervals (ms):',
                  style: Theme.of(context).textTheme.bodySmall,
                ),
                const SizedBox(height: 4),
                Wrap(
                  spacing: 4,
                  children: hrvData!.rrIntervals
                      .take(5)
                      .map((rr) => Chip(
                            label: Text(rr.toStringAsFixed(0)),
                            materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                          ))
                      .toList(),
                ),
              ],
              const SizedBox(height: 8),
              Text(
                'Updated: ${_formatTime(hrvData!.timestamp)}',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: Colors.grey[600],
                ),
              ),
            ] else ...[
              Text(
                isConnected ? 'Waiting for HRV data...' : 'Not connected',
                style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                  color: Colors.grey[600],
                ),
              ),
            ],
          ],
        ),
      ),
    );
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
