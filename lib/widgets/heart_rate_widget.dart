import 'package:flutter/material.dart';

class HeartRateWidget extends StatelessWidget {
    final int? latestData;
    const HeartRateWidget({Key? key, required this.latestData}) : super(key: key);

    @override
    Widget build(BuildContext context) {
        return _buildStatusRow(
            'Heart Rate',
            latestData != null ? '${latestData} BPM' : 'N/A',
            Icons.favorite,
            color: Colors.red,
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
}
