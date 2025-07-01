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
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(
                  Icons.thermostat,
                  color: isConnected ? Colors.blue : Colors.grey,
                  size: 24,
                ),
                const SizedBox(width: 8),
                Text(
                  'Temperature',
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            if (temperatureData != null) ...[
              _buildTemperatureSection('Body Temperature', 
                  temperatureData!.bodyTempC, 
                  _getBodyTempColor()),
              const SizedBox(height: 12),
              _buildTemperatureSection('Wrist Temperature', 
                  temperatureData!.wristTempC, 
                  Colors.orange),
              const SizedBox(height: 12),
              _buildTemperatureSection('Ambient Temperature', 
                  temperatureData!.ambientTempC, 
                  Colors.blue),
              const SizedBox(height: 8),
              Text(
                'Updated: ${_formatTime(temperatureData!.timestamp)}',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: Colors.grey[600],
                ),
              ),
            ] else ...[
              Center(
                child: Column(
                  children: [
                    Text(
                      '--°C',
                      style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                        fontWeight: FontWeight.bold,
                        color: Colors.grey,
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text(
                      isConnected ? 'Waiting for temperature data...' : 'Not connected',
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

  Widget _buildTemperatureSection(String title, double temperature, Color color) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.w500,
          ),
        ),
        Row(
          children: [
            Text(
              '${temperature.toStringAsFixed(1)}°C',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
            const SizedBox(width: 8),
            Text(
              '(${_celsiusToFahrenheit(temperature).toStringAsFixed(1)}°F)',
              style: TextStyle(
                fontSize: 12,
                color: Colors.grey[600],
              ),
            ),
          ],
        ),
      ],
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

  double _celsiusToFahrenheit(double celsius) {
    return (celsius * 9 / 5) + 32;
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:'
        '${time.minute.toString().padLeft(2, '0')}:'
        '${time.second.toString().padLeft(2, '0')}';
  }
}
