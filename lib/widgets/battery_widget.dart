import 'package:flutter/material.dart';

class BatteryWidget extends StatelessWidget {
    final int? latestData;
    const BatteryWidget({Key? key, required this.latestData}) : super(key: key);

    @override
    Widget build(BuildContext context) {
        return _buildStatusRow(
            'Battery',
            latestData != null ? '$latestData%' : 'N/A',
            Icons.battery_full,
            color: _getBatteryColor(latestData),
        );
    }

    Widget _buildStatusRow(String label, dynamic value, IconData icon, {Color? color}) {
        return Card(
            elevation: 2,
            child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Row(
                    children: [
                        Icon(icon, size: 24, color: color),
                        const SizedBox(width: 12),
                        Text(
                            label,
                            style: const TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                            ),
                        ),
                        const Spacer(),
                        Text(
                            value?.toString() ?? 'N/A',
                            style: TextStyle(
                                fontSize: 16,
                                color: color,
                                fontWeight: FontWeight.bold,
                            ),
                        ),
                    ],
                ),
            ),
        );
    }

    Color _getBatteryColor(int? level) {
        if (level == null) return Colors.grey;
        if (level > 60) return Colors.green;
        if (level > 30) return Colors.orange;
        return Colors.red;
    }
}
