// ignore_for_file: deprecated_member_use

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
      margin: const EdgeInsets.all(4.0),
      elevation: 2,
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            // Header
            Row(
              children: [
                Icon(
                  Icons.favorite_border,
                  color: isConnected ? Colors.red : Colors.grey,
                  size: 18,
                ),
                const SizedBox(width: 6),
                Expanded(
                  child: Text(
                    'HRV',
                    style: Theme.of(context).textTheme.titleSmall?.copyWith(
                      fontWeight: FontWeight.bold,
                      fontSize: 14,
                    ),
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            
            // Content
            if (hrvData != null) ...[
              _buildHRVMetrics(context),
            ] else ...[
              _buildEmptyState(context),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildHRVMetrics(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        // Main HRV metrics
        Row(
          children: [
            Expanded(
              child: _buildMetricItem(
                'RMSSD',
                '${hrvData!.rmssd.toStringAsFixed(1)}ms',
                Colors.red,
              ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildMetricItem(
                'SDNN', 
                '${hrvData!.sdnn.toStringAsFixed(1)}ms',
                Colors.purple,
              ),
            ),
          ],
        ),
        const SizedBox(height: 6),
        
        // RR intervals count
        _buildMetricItem(
          'RR Intervals',
          '${hrvData!.rrIntervals.length}',
          Colors.blue,
        ),
        
        const SizedBox(height: 8),
        
        // Timestamp
        Text(
          _formatTime(hrvData!.timestamp),
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
            fontSize: 10,
          ),
        ),
      ],
    );
  }

  Widget _buildMetricItem(String label, String value, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 6, horizontal: 8),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.1),
        borderRadius: BorderRadius.circular(6),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            value,
            style: TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
              color: color,
            ),
            overflow: TextOverflow.ellipsis,
          ),
          Text(
            label,
            style: TextStyle(
              fontSize: 10,
              color: color.withValues(alpha: 0.8),
            ),
            overflow: TextOverflow.ellipsis,
            textAlign: TextAlign.center,
          ),
        ],
      ),
    );
  }

  Widget _buildEmptyState(BuildContext context) {
    return SizedBox(
      height: 60,
      child: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              Icons.favorite_border,
              size: 24,
              color: Colors.grey[400],
            ),
            const SizedBox(height: 4),
            Text(
              isConnected ? 'Waiting...' : 'Not connected',
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: Colors.grey[600],
                fontSize: 11,
              ),
            ),
          ],
        ),
      ),
    );
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:'
        '${time.minute.toString().padLeft(2, '0')}:'
        '${time.second.toString().padLeft(2, '0')}';
  }
}
