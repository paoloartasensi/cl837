import 'package:flutter/material.dart';
import '../models/heart_rate_data.dart';

class HeartRateWidget extends StatelessWidget {
    final HeartRateData? latestData;
    const HeartRateWidget({Key? key, required this.latestData, required bool isConnected}) : super(key: key);

    @override
    Widget build(BuildContext context) {
        return Card(
            elevation: 2,
            child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                        // Main heart rate display
                        Row(
                            children: [
                                const Icon(Icons.favorite, size: 24, color: Colors.red),
                                const SizedBox(width: 12),
                                const Text(
                                    'Heart Rate',
                                    style: TextStyle(
                                        fontSize: 18,
                                        fontWeight: FontWeight.bold,
                                    ),
                                ),
                                const Spacer(),
                                Text(
                                    latestData != null ? '${latestData!.heartRate} BPM' : 'N/A',
                                    style: const TextStyle(
                                        fontSize: 20,
                                        color: Colors.red,
                                        fontWeight: FontWeight.bold,
                                    ),
                                ),
                            ],
                        ),
                        
                        // Additional details if available
                        if (latestData != null) ...[
                            const SizedBox(height: 12),
                            const Divider(height: 1),
                            const SizedBox(height: 12),
                            
                            // Contact status
                            if (latestData!.contactSupported) ...[
                                Row(
                                    children: [
                                        Icon(
                                            latestData!.contactDetected == true 
                                                ? Icons.touch_app 
                                                : Icons.not_interested,
                                            size: 16,
                                            color: latestData!.contactDetected == true 
                                                ? Colors.green 
                                                : Colors.orange,
                                        ),
                                        const SizedBox(width: 8),
                                        Text(
                                            'Contact: ${latestData!.contactDetected == true ? "Detected" : "Not Detected"}',
                                            style: TextStyle(
                                                fontSize: 14,
                                                color: Colors.grey[600],
                                            ),
                                        ),
                                    ],
                                ),
                                const SizedBox(height: 8),
                            ],
                            
                            // Energy expended
                            if (latestData!.energyExpanded != null) ...[
                                Row(
                                    children: [
                                        const Icon(Icons.flash_on, size: 16, color: Colors.amber),
                                        const SizedBox(width: 8),
                                        Text(
                                            'Energy: ${latestData!.energyExpanded} kJ',
                                            style: TextStyle(
                                                fontSize: 14,
                                                color: Colors.grey[600],
                                            ),
                                        ),
                                    ],
                                ),
                                const SizedBox(height: 8),
                            ],
                            
                            // RR Intervals - sempre la stessa altezza per evitare scroll
                            const SizedBox(height: 8),
                            Row(
                                children: [
                                    const Icon(Icons.timeline, size: 16, color: Colors.blue),
                                    const SizedBox(width: 8),
                                    Text(
                                        latestData!.rrIntervals != null && latestData!.rrIntervals!.isNotEmpty
                                            ? 'RR Intervals (${latestData!.rrIntervals!.length}):'
                                            : 'RR Intervals: None',
                                        style: TextStyle(
                                            fontSize: 14,
                                            color: Colors.grey[600],
                                            fontWeight: FontWeight.bold,
                                        ),
                                    ),
                                ],
                            ),
                            const SizedBox(height: 4),
                            // Container sempre presente con altezza fissa per evitare jump
                            SizedBox(
                                height: 35, // Altezza fissa sempre
                                child: latestData!.rrIntervals != null && latestData!.rrIntervals!.isNotEmpty
                                    ? SingleChildScrollView(
                                        scrollDirection: Axis.horizontal,
                                        child: Row(
                                            children: latestData!.rrIntervals!.take(6).map((interval) =>
                                                Padding(
                                                    padding: const EdgeInsets.only(right: 4.0),
                                                    child: Chip(
                                                        label: Text(
                                                            '${interval.toStringAsFixed(0)}ms',
                                                            style: const TextStyle(fontSize: 10),
                                                        ),
                                                        backgroundColor: Colors.blue[50],
                                                        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                                                        visualDensity: VisualDensity.compact,
                                                    ),
                                                ),
                                            ).toList(),
                                        ),
                                    )
                                    : Container(
                                        alignment: Alignment.centerLeft,
                                        child: Text(
                                            'Waiting for RR data...',
                                            style: TextStyle(
                                                fontSize: 12,
                                                color: Colors.grey[400],
                                                fontStyle: FontStyle.italic,
                                            ),
                                        ),
                                    ),
                            ),
                            // Informazioni aggiuntive sempre con altezza fissa
                            SizedBox(
                                height: 16, // Altezza fissa per il testo
                                child: latestData!.rrIntervals != null && latestData!.rrIntervals!.length > 6
                                    ? Text(
                                        '... and ${latestData!.rrIntervals!.length - 6} more',
                                        style: TextStyle(
                                            fontSize: 12,
                                            color: Colors.grey[500],
                                            fontStyle: FontStyle.italic,
                                        ),
                                    )
                                    : const SizedBox.shrink(), // Mantiene lo spazio ma è invisibile
                            ),
                            
                            // Timestamp
                            const SizedBox(height: 8),
                            Row(
                                children: [
                                    const Icon(Icons.access_time, size: 14, color: Colors.grey),
                                    const SizedBox(width: 4),
                                    Text(
                                        'Last: ${_formatTime(latestData!.timestamp)}',
                                        style: TextStyle(
                                            fontSize: 12,
                                            color: Colors.grey[500],
                                        ),
                                    ),
                                ],
                            ),
                        ],
                    ],
                ),
            ),
        );
    }
    
    String _formatTime(DateTime dateTime) {
        return '${dateTime.hour.toString().padLeft(2, '0')}:'
               '${dateTime.minute.toString().padLeft(2, '0')}:'
               '${dateTime.second.toString().padLeft(2, '0')}';
    }
}
