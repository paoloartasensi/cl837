import 'package:flutter/material.dart';
import '../models/sports_data.dart';

class SportsWidget extends StatelessWidget {
  final SportsData? sportsData;
  final bool isConnected;

  const SportsWidget({
    super.key,
    this.sportsData,
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
                  Icons.directions_run,
                  color: isConnected ? Colors.green : Colors.grey,
                  size: 18,
                ),
                const SizedBox(width: 6),
                Expanded(
                  child: Text(
                    'Sports',
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
            if (sportsData != null) ...[
              _buildCompactMetrics(context),
            ] else ...[
              _buildEmptyState(context),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildCompactMetrics(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        // First row: Steps and Calories
        Row(
          children: [
            Expanded(
              child: _buildMetricItem(
                'Steps',
                sportsData!.steps.toString(),
                Icons.directions_walk,
                Colors.blue,
              ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildMetricItem(
                'Cal',
                (sportsData!.caloriesKcal / 10).toStringAsFixed(0),
                Icons.local_fire_department,
                Colors.orange,
              ),
            ),
          ],
        ),
        const SizedBox(height: 6),
        
        // Second row: Distance (centered)
        _buildMetricItem(
          'Distance',
          '${(sportsData!.distanceCm / 100).toStringAsFixed(1)}m',
          Icons.straighten,
          Colors.green,
        ),
        
        const SizedBox(height: 8),
        
        // Timestamp
        Text(
          _formatTime(sportsData!.timestamp),
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
            fontSize: 10,
          ),
        ),
      ],
    );
  }

  Widget _buildMetricItem(String label, String value, IconData icon, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 4, horizontal: 8),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(6),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon, size: 16, color: color),
          const SizedBox(width: 4),
          Flexible(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                Text(
                  value,
                  style: TextStyle(
                    fontSize: 12,
                    fontWeight: FontWeight.bold,
                    color: color,
                  ),
                  overflow: TextOverflow.ellipsis,
                ),
                Text(
                  label,
                  style: TextStyle(
                    fontSize: 10,
                    color: color.withOpacity(0.8),
                  ),
                  overflow: TextOverflow.ellipsis,
                ),
              ],
            ),
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
              Icons.directions_run,
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
