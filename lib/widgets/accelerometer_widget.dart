// ignore_for_file: deprecated_member_use

import 'package:flutter/material.dart';
import '../models/sensor_data.dart';

class AccelerometerWidget extends StatelessWidget {
  final AccelerometerData? latestData;

  const AccelerometerWidget({
    super.key,
    required this.latestData,
  });

    @override
    Widget build(BuildContext context) {
        return Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
                const Text(
                    'Accelerometer',
                    style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                    ),
                ),
                const SizedBox(height: 8),
                if (latestData != null) ...[
                    _buildAxisRow('X', latestData!.x, Colors.red),
                    const SizedBox(height: 8),
                    _buildAxisRow('Y', latestData!.y, Colors.green),
                    const SizedBox(height: 8),
                    _buildAxisRow('Z', latestData!.z, Colors.blue),
                ] else
                    const Center(
                        child: Text(
                            'Waiting for data...',
                            style: TextStyle(
                                fontSize: 16,
                                color: Colors.grey,
                            ),
                        ),
                    ),
            ],
        );
    }

    Widget _buildAxisRow(String axis, double value, Color color) {
        return Card(
            elevation: 2,
            child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                        Row(
                            children: [
                                Container(
                                    width: 24,
                                    height: 24,
                                    decoration: BoxDecoration(                                // Usa withOpacity invece di .value (deprecato)
                                // ignore: duplicate_ignore
                                // ignore: deprecated_member_use
                                color: color.withValues(alpha: 0.2),
                                        borderRadius: BorderRadius.circular(4),
                                    ),
                                    child: Center(
                                        child: Text(
                                            axis,
                                            style: TextStyle(
                                                color: color,
                                                fontWeight: FontWeight.bold,
                                            ),
                                        ),
                                    ),
                                ),
                                const SizedBox(width: 12),
                                Expanded(
                                    child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                            LinearProgressIndicator(
                                                value: (value + 8.0) / 16.0, // Normalize from -8g to 8g
                                                // Usa withOpacity invece di .value (deprecato)
                                                backgroundColor: color.withValues(alpha: 0.1),
                                                valueColor: AlwaysStoppedAnimation<Color>(color),
                                            ),
                                            const SizedBox(height: 4),
                                            Text(
                                                '${value.toStringAsFixed(3)} g',
                                                style: TextStyle(
                                                    color: color,
                                                    fontWeight: FontWeight.w500,
                                                ),
                                            ),
                                        ],
                                    ),
                                ),
                            ],
                        ),
                    ],
                ),
            ),
        );
    }
}
