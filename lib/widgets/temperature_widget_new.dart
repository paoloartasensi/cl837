import 'package:flutter/material.dart';
import '../models/temperature_data.dart';

class TemperatureWidget extends StatelessWidget {
  final TemperatureData? temperatureData;
  final bool isConnected;

  const TemperatureWidget({
    super.key,
    this.temperatureData,
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
                  Icons.thermostat,
                  color: isConnected ? Colors.blue : Colors.grey,
                  size: 18,
                ),
                const SizedBox(width: 6),
                Expanded(
                  child: Text(
                    'Temperature',
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
            if (temperatureData != null) ...[
              _buildTemperatureContent(context),
            ] else ...[
              _buildEmptyState(context),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildTemperatureContent(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        // Main body temperature
        Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                '${temperatureData!.bodyTempC.toStringAsFixed(1)}°C',
                style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                  fontWeight: FontWeight.bold,
                  color: _getBodyTempColor(),
                  fontSize: 18,
                ),
              ),
              Text(
                'Body',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: _getBodyTempColor(),
                  fontSize: 10,
                ),
              ),
            ],
          ),
        ),
        
        const SizedBox(height: 8),
        
        // Other temperatures
        Row(
          children: [
            Expanded(
              child: _buildTempItem(
                'Wrist',
                temperatureData!.wristTempC,
                Colors.orange,
              ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildTempItem(
                'Ambient',
                temperatureData!.ambientTempC,
                Colors.blue,
              ),
            ),
          ],
        ),
        
        const SizedBox(height: 6),
        
        // Timestamp
        Text(
          _formatTime(temperatureData!.timestamp),
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
            fontSize: 10,
          ),
        ),
      ],
    );
  }

  Widget _buildTempItem(String label, double temp, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 4, horizontal: 6),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(6),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            '${temp.toStringAsFixed(1)}°C',
            style: TextStyle(
              fontSize: 12,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
          Text(
            label,
            style: TextStyle(
              fontSize: 10,
              color: color.withOpacity(0.8),
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
            Text(
              '--°C',
              style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                fontWeight: FontWeight.bold,
                color: Colors.grey,
                fontSize: 18,
              ),
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

  Color _getBodyTempColor() {
    if (temperatureData == null) return Colors.grey;
    
    final temp = temperatureData!.bodyTempC;
    if (temp >= 37.5) return Colors.red; // Fever
    if (temp >= 37.0) return Colors.orange; // Elevated
    if (temp >= 36.0) return Colors.green; // Normal
    return Colors.blue; // Low
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:'
        '${time.minute.toString().padLeft(2, '0')}:'
        '${time.second.toString().padLeft(2, '0')}';
  }
}
