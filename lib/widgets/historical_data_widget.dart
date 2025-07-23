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
  final VoidCallback? onClearAllData;
  final VoidCallback? onFactoryReset;

  const HistoricalDataWidget({
    super.key,
    this.exerciseHistory,
    this.hrHistoryList,
    this.hrHistoryData,
    required this.isConnected,
    this.onRequestExercise,
    this.onRequestHRHistory,
    this.onRequestAllHistory,
    this.onClearAllData,
    this.onFactoryReset,
  });

  @override
  Widget build(BuildContext context) {
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
            const SizedBox(height: 12),
            
            // Reset Buttons (Dangerous Actions)
            if (isConnected) _buildResetButtons(),
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
        
        if (exerciseHistory == null || exerciseHistory!.isEmpty)
          const Text('No exercise data available', 
                    style: TextStyle(color: Colors.grey))
        else
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
        
        if (hrHistoryList == null)
          const Text('No HR history list available', 
                    style: TextStyle(color: Colors.grey))
        else if (hrHistoryList!.timestamps.isEmpty)
          const Text('No HR timestamps found', 
                    style: TextStyle(color: Colors.grey))
        else
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

  Widget _buildResetButtons() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          '⚠️ Device Reset Options',
          style: TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.bold,
            color: Colors.red,
          ),
        ),
        const SizedBox(height: 8),
        Row(
          children: [
            Expanded(
              child: ElevatedButton.icon(
                onPressed: onClearAllData != null 
                  ? () => _showResetConfirmation('Clear All Historical Data', 
                      'This will delete all Exercise History and HR History from the device. This cannot be undone.', 
                      onClearAllData!)
                  : null,
                icon: const Icon(Icons.delete_sweep, size: 16),
                label: const Text('Clear History', style: TextStyle(fontSize: 11)),
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
                onPressed: onFactoryReset != null 
                  ? () => _showResetConfirmation('Factory Reset', 
                      'This will restore the device to factory settings and delete ALL data. This cannot be undone.', 
                      onFactoryReset!)
                  : null,
                icon: const Icon(Icons.settings_backup_restore, size: 16),
                label: const Text('Factory Reset', style: TextStyle(fontSize: 11)),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade100,
                  foregroundColor: Colors.red.shade800,
                  padding: const EdgeInsets.symmetric(vertical: 8),
                ),
              ),
            ),
          ],
        ),
        const SizedBox(height: 4),
        const Text(
          '💡 Note: Old dates (2020-2021) indicate device needs reset',
          style: TextStyle(fontSize: 10, color: Colors.grey),
        ),
      ],
    );
  }

  void _showResetConfirmation(String title, String message, VoidCallback onConfirm) {
    // This would need to be implemented with a context, 
    // or the parent widget should handle the confirmation dialog
    onConfirm();
  }
}
