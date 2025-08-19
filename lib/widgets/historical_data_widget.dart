import 'package:flutter/material.dart';
import '../models/historical_data.dart';

class HistoricalDataWidget extends StatefulWidget {
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
  State<HistoricalDataWidget> createState() => _HistoricalDataWidgetState();
}

class _HistoricalDataWidgetState extends State<HistoricalDataWidget> {
  bool _showHeartRate = false; // false = Calorie/Steps, true = HeartRate

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header with refresh button
            Row(
              children: [
                const Icon(Icons.history, color: Colors.deepPurple),
                const SizedBox(width: 8),
                const Text(
                  'History data',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.deepPurple,
                  ),
                ),
                const Spacer(),
                if (widget.isConnected) ...[
                  IconButton(
                    icon: const Icon(Icons.refresh, size: 20),
                    onPressed: widget.onRequestAllHistory,
                    tooltip: 'Request All Historical Data',
                  ),
                ],
              ],
            ),
            const SizedBox(height: 12),
            
            // Toggle Switch like in original app
            _buildDataTypeToggle(),
            const SizedBox(height: 16),
            
            // Data display based on toggle
            if (_showHeartRate)
              _buildHeartRateSection()
            else
              _buildCalorieStepsSection(),
            
            const SizedBox(height: 16),
            
            // Action Buttons
            if (widget.isConnected) _buildActionButtons(),
          ],
        ),
      ),
    );
  }

  Widget _buildDataTypeToggle() {
    return Container(
      width: double.infinity,
      height: 35,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(17.5),
        color: Colors.grey.shade200,
      ),
      child: Stack(
        children: [
          // Sliding background
          AnimatedAlign(
            alignment: _showHeartRate ? Alignment.centerLeft : Alignment.centerRight,
            duration: const Duration(milliseconds: 200),
            child: Container(
              width: MediaQuery.of(context).size.width * 0.35,
              height: 35,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(17.5),
                color: _showHeartRate ? Colors.green.shade300 : Colors.green.shade300,
              ),
            ),
          ),
          // Buttons
          Row(
            children: [
              Expanded(
                child: GestureDetector(
                  onTap: () => setState(() => _showHeartRate = true),
                  child: Container(
                    height: 35,
                    alignment: Alignment.center,
                    child: Text(
                      'HeartRate',
                      style: TextStyle(
                        color: _showHeartRate ? Colors.white : Colors.black54,
                        fontWeight: FontWeight.w500,
                        fontSize: 13,
                      ),
                    ),
                  ),
                ),
              ),
              Expanded(
                child: GestureDetector(
                  onTap: () => setState(() => _showHeartRate = false),
                  child: Container(
                    height: 35,
                    alignment: Alignment.center,
                    child: Text(
                      'Calorie/Steps',
                      style: TextStyle(
                        color: !_showHeartRate ? Colors.white : Colors.black54,
                        fontWeight: FontWeight.w500,
                        fontSize: 13,
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildCalorieStepsSection() {
    if (widget.exerciseHistory == null || widget.exerciseHistory!.isEmpty) {
      return Center(
        child: Column(
          children: [
            Icon(Icons.fitness_center, size: 48, color: Colors.grey.shade400),
            const SizedBox(height: 8),
            Text(
              'No exercise data available',
              style: TextStyle(color: Colors.grey.shade600),
            ),
            if (widget.isConnected) ...[
              const SizedBox(height: 8),
              ElevatedButton(
                onPressed: widget.onRequestExercise,
                child: const Text('Request Exercise Data'),
              ),
            ],
          ],
        ),
      );
    }

    return Column(
      children: widget.exerciseHistory!.map((data) => Container(
        margin: const EdgeInsets.only(bottom: 8),
        padding: const EdgeInsets.all(12),
        decoration: BoxDecoration(
          color: Colors.grey.shade50,
          borderRadius: BorderRadius.circular(8),
          border: Border.all(color: Colors.grey.shade200),
        ),
        child: Row(
          children: [
            // Date column
            Expanded(
              flex: 2,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    '${data.date.year}-${data.date.month.toString().padLeft(2, '0')}-${data.date.day.toString().padLeft(2, '0')}',
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                  Text(
                    '${data.date.hour.toString().padLeft(2, '0')}:${data.date.minute.toString().padLeft(2, '0')}:${data.date.second.toString().padLeft(2, '0')}',
                    style: TextStyle(
                      fontSize: 12,
                      color: Colors.grey.shade600,
                    ),
                  ),
                ],
              ),
            ),
            // Calories
            Expanded(
              child: Column(
                children: [
                  Text(
                    '${data.calories.toStringAsFixed(0)}Kcal',
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const Icon(Icons.local_fire_department, 
                       color: Colors.orange, size: 16),
                ],
              ),
            ),
            // Steps
            Expanded(
              child: Column(
                children: [
                  Text(
                    '${data.steps}',
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const Icon(Icons.directions_walk, 
                       color: Colors.blue, size: 16),
                ],
              ),
            ),
          ],
        ),
      )).toList(),
    );
  }

  Widget _buildHeartRateSection() {
    if (widget.hrHistoryData == null || widget.hrHistoryData!.isEmpty) {
      return Center(
        child: Column(
          children: [
            Icon(Icons.favorite, size: 48, color: Colors.grey.shade400),
            const SizedBox(height: 8),
            Text(
              'No heart rate data available',
              style: TextStyle(color: Colors.grey.shade600),
            ),
            if (widget.isConnected) ...[
              const SizedBox(height: 8),
              ElevatedButton(
                onPressed: widget.onRequestHRHistory,
                child: const Text('Request HR History'),
              ),
            ],
          ],
        ),
      );
    }

    return Column(
      children: widget.hrHistoryData!.map((session) => Container(
        margin: const EdgeInsets.only(bottom: 8),
        padding: const EdgeInsets.all(12),
        decoration: BoxDecoration(
          color: Colors.red.shade50,
          borderRadius: BorderRadius.circular(8),
          border: Border.all(color: Colors.red.shade100),
        ),
        child: Row(
          children: [
            const Icon(Icons.favorite, color: Colors.red, size: 16),
            const SizedBox(width: 8),
            Text(
              '${session.timestamp.year}-${session.timestamp.month.toString().padLeft(2, '0')}-${session.timestamp.day.toString().padLeft(2, '0')} ${session.timestamp.hour.toString().padLeft(2, '0')}:${session.timestamp.minute.toString().padLeft(2, '0')}:${session.timestamp.second.toString().padLeft(2, '0')}',
              style: const TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.w500,
              ),
            ),
            const Spacer(),
            const Icon(Icons.chevron_right, color: Colors.grey),
          ],
        ),
      )).toList(),
    );
  }
  Widget _buildActionButtons() {
    return Row(
      children: [
        Expanded(
          child: ElevatedButton.icon(
            onPressed: widget.onRequestExercise,
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
            onPressed: widget.onRequestHRHistory,
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
            onPressed: widget.onRequestAllHistory,
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
