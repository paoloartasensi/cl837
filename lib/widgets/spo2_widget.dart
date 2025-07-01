import 'package:flutter/material.dart';
import '../models/spo2_data.dart';

class SpO2Widget extends StatelessWidget {
  final SpO2Data? spo2Data;
  final bool isConnected;
  final VoidCallback? onMeasureSpO2;
  final bool isMeasuring;

  const SpO2Widget({
    super.key,
    this.spo2Data,
    required this.isConnected,
    this.onMeasureSpO2,
    this.isMeasuring = false,
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
                  Icons.air,
                  color: isConnected ? _getSpO2Color() : Colors.grey,
                  size: 18,
                ),
                const SizedBox(width: 6),
                Expanded(
                  child: Text(
                    'SpO₂',
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
            
            // Measure button
            if (isConnected && onMeasureSpO2 != null) ...[
              SizedBox(
                width: double.infinity,
                child: ElevatedButton.icon(
                  onPressed: isMeasuring ? null : onMeasureSpO2,
                  icon: isMeasuring 
                    ? const SizedBox(
                        width: 16,
                        height: 16,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.air, size: 16),
                  label: Text(
                    isMeasuring ? 'Measuring...' : 'Measure SpO₂',
                    style: const TextStyle(fontSize: 12),
                  ),
                  style: ElevatedButton.styleFrom(
                    padding: const EdgeInsets.symmetric(vertical: 8),
                    backgroundColor: isMeasuring ? Colors.grey : Colors.blue,
                    foregroundColor: Colors.white,
                  ),
                ),
              ),
              const SizedBox(height: 8),
            ],
            
            // Content
            if (spo2Data != null) ...[
              _buildSpO2Content(context),
            ] else ...[
              _buildEmptyState(context),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildSpO2Content(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        // Main SpO2 value
        Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                '${spo2Data!.spo2Value}%',
                style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                  fontWeight: FontWeight.bold,
                  color: _getSpO2Color(),
                  fontSize: 22,
                ),
              ),
              Text(
                _getSpO2Status(),
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: _getSpO2Color(),
                  fontWeight: FontWeight.w500,
                  fontSize: 10,
                ),
              ),
            ],
          ),
        ),
        
        const SizedBox(height: 8),
        
        // Status indicators
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            _buildStatusChip(
              spo2Data!.correctWristPosture ? 'Correct' : 'Incorrect',
              spo2Data!.correctWristPosture ? Colors.green : Colors.orange,
            ),
            _buildStatusChip(
              spo2Data!.isWearing ? 'Wearing' : 'Not Wearing',
              spo2Data!.isWearing ? Colors.green : Colors.red,
            ),
          ],
        ),
        
        const SizedBox(height: 6),
        
        // Timestamp
        Text(
          _formatTime(spo2Data!.timestamp),
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
            fontSize: 10,
          ),
        ),
      ],
    );
  }

  Widget _buildStatusChip(String text, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.2),
        borderRadius: BorderRadius.circular(10),
      ),
      child: Text(
        text,
        style: TextStyle(
          fontSize: 9,
          color: color,
          fontWeight: FontWeight.w500,
        ),
      ),
    );
  }

  Widget _buildEmptyState(BuildContext context) {
    return SizedBox(
      height: 80,
      child: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              '--%',
              style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                fontWeight: FontWeight.bold,
                color: Colors.grey,
                fontSize: 22,
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

  Color _getSpO2Color() {
    if (spo2Data == null) return Colors.grey;
    
    final value = spo2Data!.spo2Value;
    if (value >= 95) return Colors.green;
    if (value >= 90) return Colors.orange;
    return Colors.red;
  }

  String _getSpO2Status() {
    if (spo2Data == null) return 'No data';
    
    final value = spo2Data!.spo2Value;
    if (value >= 95) return 'Normal';
    if (value >= 90) return 'Low';
    return 'Very Low';
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:'
        '${time.minute.toString().padLeft(2, '0')}:'
        '${time.second.toString().padLeft(2, '0')}';
  }
}
