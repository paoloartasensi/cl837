import 'package:flutter/material.dart';
import '../models/historical_data.dart';

class HistoricalDataWidget extends StatelessWidget {
  final List<ExerciseHistoryData>? exerciseHistory;
  final HeartRateHistoryList? hrHistoryList;
  final List<HeartRateHistoryData>? hrHistoryData;
  final bool isConnected;
  final VoidCallback? onRequestExercise;
  final VoidCallback? onRequestHRHistory;
  final VoidCallback? onRequestAllHistory;

  const HistoricalDataWidget({
    super.key,
    this.exerciseHistory,
    this.hrHistoryList,
    this.hrHistoryData,
    required this.isConnected,
    this.onRequestExercise,
    this.onRequestHRHistory,
    this.onRequestAllHistory,
  });

  @override
  Widget build(BuildContext context) {
    // DEBUG: Log current data state every 5 seconds to avoid spam
    if (DateTime.now().second % 5 == 0) {
      debugPrint('🔍 HistoricalDataWidget: Exercise=${exerciseHistory?.length ?? 0}, HR List=${hrHistoryList?.timestamps.length ?? 0}, HR Data=${hrHistoryData?.length ?? 0}');
    }
    
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Icon(Icons.history, color: Colors.deepPurple),
                const SizedBox(width: 8),
                const Text(
                  'Historical Data',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.deepPurple,
                  ),
                ),
                const Spacer(),
                if (isConnected) ...[
                  IconButton(
                    icon: const Icon(Icons.refresh, size: 20),
                    onPressed: onRequestAllHistory,
                    tooltip: 'Request All Historical Data',
                  ),
                ],
              ],
            ),
            const SizedBox(height: 12),
            
            // Exercise History Section
            _buildExerciseHistorySection(),
            const SizedBox(height: 16),
            
            // HR History Section
            _buildHRHistorySection(),
            const SizedBox(height: 16),
            
            // Action Buttons
            if (isConnected) _buildActionButtons(),
          ],
        ),
      ),
    );
  }

  Widget _buildExerciseHistorySection() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            const Icon(Icons.fitness_center, size: 16, color: Colors.orange),
            const SizedBox(width: 4),
            const Text('Exercise History (7 days)',
                style: TextStyle(fontWeight: FontWeight.w600)),
            const Spacer(),
            if (isConnected)
              TextButton(
                onPressed: onRequestExercise,
                child: const Text('Request'),
              ),
          ],
        ),
        const SizedBox(height: 8),
        
        if (exerciseHistory == null || exerciseHistory!.isEmpty) ...[
          Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.orange.shade50,
              borderRadius: BorderRadius.circular(8),
              border: Border.all(color: Colors.orange.shade200),
            ),
            child: Row(
              children: [
                Icon(Icons.info_outline, color: Colors.orange.shade700, size: 16),
                const SizedBox(width: 8),
                Expanded(
                  child: Text(
                    isConnected 
                      ? 'No exercise data available. Device may be new or memory corrupted.'
                      : 'Connect to device to view exercise history',
                    style: TextStyle(color: Colors.orange.shade700, fontSize: 12),
                  ),
                ),
              ],
            ),
          ),
        ] else
          Column(
            children: exerciseHistory!.map((data) => Padding(
              padding: const EdgeInsets.only(bottom: 4.0),
              child: Row(
                children: [
                  Text(
                    '${data.date.day}/${data.date.month}',
                    style: const TextStyle(fontSize: 12),
                  ),
                  const SizedBox(width: 8),
                  Text('${data.steps} steps', 
                       style: const TextStyle(fontSize: 12)),
                  const SizedBox(width: 8),
                  Text('${data.calories.toStringAsFixed(1)}kcal', 
                       style: const TextStyle(fontSize: 12)),
                ],
              ),
            )).toList(),
          ),
      ],
    );
  }

  Widget _buildHRHistorySection() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            const Icon(Icons.favorite, size: 16, color: Colors.red),
            const SizedBox(width: 4),
            const Text('Heart Rate History',
                style: TextStyle(fontWeight: FontWeight.w600)),
            const Spacer(),
            if (isConnected)
              TextButton(
                onPressed: onRequestHRHistory,
                child: const Text('Request'),
              ),
          ],
        ),
        const SizedBox(height: 8),
        
        if (hrHistoryList == null) ...[
          Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.red.shade50,
              borderRadius: BorderRadius.circular(8),
              border: Border.all(color: Colors.red.shade200),
            ),
            child: Row(
              children: [
                Icon(Icons.info_outline, color: Colors.red.shade700, size: 16),
                const SizedBox(width: 8),
                Expanded(
                  child: Text(
                    isConnected 
                      ? 'No HR history available. Request data or check device memory.'
                      : 'Connect to device to view HR history',
                    style: TextStyle(color: Colors.red.shade700, fontSize: 12),
                  ),
                ),
              ],
            ),
          ),
        ] else if (hrHistoryList!.timestamps.isEmpty) ...[
          Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.amber.shade50,
              borderRadius: BorderRadius.circular(8),
              border: Border.all(color: Colors.amber.shade200),
            ),
            child: Row(
              children: [
                Icon(Icons.warning_outlined, color: Colors.amber.shade700, size: 16),
                const SizedBox(width: 8),
                Expanded(
                  child: Text(
                    'Device memory appears corrupted or in overflow state. All HR timestamps are invalid.',
                    style: TextStyle(color: Colors.amber.shade700, fontSize: 12),
                  ),
                ),
              ],
            ),
          ),
        ] else
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('${hrHistoryList!.timestamps.length} sessions found',
                   style: const TextStyle(fontSize: 12)),
              const SizedBox(height: 4),
              if (hrHistoryData != null && hrHistoryData!.isNotEmpty)
                ...hrHistoryData!.map((session) => Padding(
                  padding: const EdgeInsets.only(bottom: 4.0),
                  child: Text(
                    '${session.timestamp.day}/${session.timestamp.month} - ${session.entries.length} readings',
                    style: const TextStyle(fontSize: 12),
                  ),
                )).toList(),
            ],
          ),
      ],
    );
  }

  Widget _buildActionButtons() {
    return Row(
      children: [
        Expanded(
          child: ElevatedButton.icon(
            onPressed: onRequestExercise,
            icon: const Icon(Icons.fitness_center, size: 16),
            label: const Text('Exercise', style: TextStyle(fontSize: 12)),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.orange.shade100,
              foregroundColor: Colors.orange.shade800,
              padding: const EdgeInsets.symmetric(vertical: 8),
            ),
          ),
        ),
        const SizedBox(width: 8),
        Expanded(
          child: ElevatedButton.icon(
            onPressed: onRequestHRHistory,
            icon: const Icon(Icons.favorite, size: 16),
            label: const Text('HR History', style: TextStyle(fontSize: 12)),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.red.shade100,
              foregroundColor: Colors.red.shade800,
              padding: const EdgeInsets.symmetric(vertical: 8),
            ),
          ),
        ),
        const SizedBox(width: 8),
        Expanded(
          child: ElevatedButton.icon(
            onPressed: onRequestAllHistory,
            icon: const Icon(Icons.download, size: 16),
            label: const Text('All Data', style: TextStyle(fontSize: 12)),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.deepPurple.shade100,
              foregroundColor: Colors.deepPurple.shade800,
              padding: const EdgeInsets.symmetric(vertical: 8),
            ),
          ),
        ),
      ],
    );
  }
}
