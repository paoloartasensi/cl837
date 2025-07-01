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
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(
                  Icons.directions_run,
                  color: isConnected ? Colors.green : Colors.grey,
                  size: 24,
                ),
                const SizedBox(width: 8),
                Text(
                  'Sports & Activity',
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            if (sportsData != null) ...[
              // Main metrics row
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  _buildMetricCard(
                    context,
                    'Steps',
                    sportsData!.steps.toString(),
                    Icons.directions_walk,
                    Colors.blue,
                  ),
                  _buildMetricCard(
                    context,
                    'Distance',
                    '${(sportsData!.distanceCm / 100).toStringAsFixed(1)}m',
                    Icons.straighten,
                    Colors.green,
                  ),
                  _buildMetricCard(
                    context,
                    'Calories',
                    (sportsData!.caloriesKcal / 10).toStringAsFixed(1),
                    Icons.local_fire_department,
                    Colors.orange,
                  ),
                ],
              ),
              const SizedBox(height: 16),
              // Detailed data
              _buildDataRow('Total Steps', sportsData!.steps.toString()),
              _buildDataRow('Distance', '${(sportsData!.distanceCm / 100).toStringAsFixed(2)} meters'),
              _buildDataRow('Calories', '${(sportsData!.caloriesKcal / 10).toStringAsFixed(1)} kcal'),
              const SizedBox(height: 8),
              Text(
                'Updated: ${_formatTime(sportsData!.timestamp)}',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: Colors.grey[600],
                ),
              ),
            ] else ...[
              Center(
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        _buildMetricCard(
                          context,
                          'Steps',
                          '--',
                          Icons.directions_walk,
                          Colors.grey,
                        ),
                        _buildMetricCard(
                          context,
                          'Distance',
                          '--',
                          Icons.straighten,
                          Colors.grey,
                        ),
                        _buildMetricCard(
                          context,
                          'Calories',
                          '--',
                          Icons.local_fire_department,
                          Colors.grey,
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    Text(
                      isConnected ? 'Waiting for sports data...' : 'Not connected',
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

  Widget _buildMetricCard(
    BuildContext context,
    String title,
    String value,
    IconData icon,
    Color color,
  ) {
    return Expanded(
      child: Card(
        elevation: 2,
        child: Padding(
          padding: const EdgeInsets.all(12.0),
          child: Column(
            children: [
              Icon(
                icon,
                color: color,
                size: 20,
              ),
              const SizedBox(height: 4),
              Text(
                value,
                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                  fontWeight: FontWeight.bold,
                  color: color,
                ),
              ),
              Text(
                title,
                style: Theme.of(context).textTheme.bodySmall,
                textAlign: TextAlign.center,
              ),
            ],
          ),
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
