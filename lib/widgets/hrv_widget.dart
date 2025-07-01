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
                _getHRVColor(),
              ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildMetricItem(
                'Mean RR', 
                '${hrvData!.meanRR.toStringAsFixed(0)}ms',
                Colors.blue,
              ),
            ),
          ],
        ),
        const SizedBox(height: 6),
        
        // HR and quality row
        Row(
          children: [
            Expanded(
              child: _buildMetricItem(
                'Est. HR',
                '${hrvData!.estimatedHR.toStringAsFixed(0)} BPM',
                _getHRColor(),
              ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildQualityChip(),
            ),
          ],
        ),
        const SizedBox(height: 6),
        
        // Data validation status
        if (!hrvData!.isDataValid) ...[
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(6),
            decoration: BoxDecoration(
              color: Colors.orange.withValues(alpha: 0.1),
              borderRadius: BorderRadius.circular(4),
            ),
            child: Text(
              '⚠️ Data quality warning',
              style: TextStyle(
                fontSize: 10,
                color: Colors.orange[700],
              ),
              textAlign: TextAlign.center,
            ),
          ),
          const SizedBox(height: 4),
        ],
        
        // RR intervals count and sample count
        Text(
          '${hrvData!.rrIntervals.length} RR intervals • ${hrvData!.hrCategory}',
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
            fontSize: 10,
          ),
          textAlign: TextAlign.center,
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

  Color _getHRVColor() {
    if (hrvData == null) return Colors.grey;
    
    // Colore basato sulla qualità HRV
    switch (hrvData!.hrvQuality) {
      case 'Very Poor':
        return Colors.red[700]!;
      case 'Poor':
        return Colors.red;
      case 'Fair':
        return Colors.orange;
      case 'Good':
        return Colors.green;
      case 'Excellent':
        return Colors.green[700]!;
      default:
        return Colors.grey;
    }
  }

  Color _getHRColor() {
    if (hrvData == null) return Colors.grey;
    
    final hr = hrvData!.estimatedHR;
    if (hr < 50 || hr > 150) return Colors.red;      // Bradycardia/Tachycardia
    if (hr < 60 || hr > 100) return Colors.orange;   // Athletic/Elevated
    return Colors.green;                              // Normal
  }

  Widget _buildQualityChip() {
    if (hrvData == null) return const SizedBox();
    
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: _getHRVColor().withValues(alpha: 0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Text(
        hrvData!.hrvQuality,
        style: TextStyle(
          fontSize: 10,
          color: _getHRVColor(),
          fontWeight: FontWeight.bold,
        ),
        textAlign: TextAlign.center,
      ),
    );
  }
}
