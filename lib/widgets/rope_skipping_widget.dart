import 'package:flutter/material.dart';
import '../models/rope_data.dart';
import '../chileaf_extended_service.dart';

class RopeSkippingWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const RopeSkippingWidget({super.key, required this.service});

  @override
  State<RopeSkippingWidget> createState() => _RopeSkippingWidgetState();
}

class _RopeSkippingWidgetState extends State<RopeSkippingWidget> {
  RopeSkippingData? _latestStatus;
  RopeRealtimeData? _latestRealtime;
  RopeMode _selectedMode = RopeMode.free;

  @override
  void initState() {
    super.initState();
    _setupStreams();
  }

  void _setupStreams() {
    // Listen to rope status updates
    widget.service.ropeStatusStream.listen((status) {
      if (mounted) {
        setState(() {
          _latestStatus = status;
        });
      }
    });

    // Listen to realtime rope updates
    widget.service.ropeRealtimeStream.listen((realtime) {
      if (mounted) {
        setState(() {
          _latestRealtime = realtime;
        });
      }
    });
  }

  String _formatTime(int seconds) {
    int minutes = seconds ~/ 60;
    int secs = seconds % 60;
    return '${minutes.toString().padLeft(2, '0')}:${secs.toString().padLeft(2, '0')}';
  }

  Color _getModeColor(RopeMode mode) {
    switch (mode) {
      case RopeMode.free:
        return Colors.green;
      case RopeMode.counter:
        return Colors.blue;
      case RopeMode.timer:
        return Colors.orange;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              children: [
                Icon(Icons.fitness_center, color: Colors.deepPurple),
                SizedBox(width: 8),
                Text(
                  'Rope Skipping',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.deepPurple,
                  ),
                ),
              ],
            ),
            const Divider(),

            // Mode Selection
            Row(
              children: [
                const Text('Mode: ', style: TextStyle(fontWeight: FontWeight.bold)),
                DropdownButton<RopeMode>(
                  value: _selectedMode,
                  onChanged: (RopeMode? newMode) {
                    if (newMode != null) {
                      setState(() {
                        _selectedMode = newMode;
                      });
                    }
                  },
                  items: RopeMode.values.map((mode) {
                    return DropdownMenuItem<RopeMode>(
                      value: mode,
                      child: Row(
                        children: [
                          Container(
                            width: 12,
                            height: 12,
                            decoration: BoxDecoration(
                              color: _getModeColor(mode),
                              shape: BoxShape.circle,
                            ),
                          ),
                          const SizedBox(width: 8),
                          Text(mode.name),
                        ],
                      ),
                    );
                  }).toList(),
                ),
                const SizedBox(width: 16),
                ElevatedButton(
                  onPressed: () => widget.service.setRopeMode(_selectedMode),
                  child: const Text('Set Mode'),
                ),
              ],
            ),
            const SizedBox(height: 16),

            // Current Status
            if (_latestStatus != null) ...[
              Text('Current Status', 
                   style: Theme.of(context).textTheme.titleMedium?.copyWith(fontWeight: FontWeight.bold)),
              const SizedBox(height: 8),
              Row(
                children: [
                  Expanded(
                    child: _buildStatusCard(
                      'Mode',
                      _latestStatus!.mode.name,
                      Icons.settings,
                      _getModeColor(_latestStatus!.mode),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: _buildStatusCard(
                      'Jumps',
                      '${_latestStatus!.jumps}',
                      Icons.vertical_align_center,
                      Colors.green,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 8),
              Row(
                children: [
                  Expanded(
                    child: _buildStatusCard(
                      'Time',
                      _formatTime(_latestStatus!.timeSeconds),
                      Icons.timer,
                      Colors.blue,
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: _buildStatusCard(
                      'Calories',
                      '${_latestStatus!.calories.toStringAsFixed(1)} kcal',
                      Icons.local_fire_department,
                      Colors.red,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 8),
              _buildStatusCard(
                'Day Total',
                '${_latestStatus!.dayTotalJumps} jumps',
                Icons.today,
                Colors.purple,
              ),
              const SizedBox(height: 16),
            ],

            // Realtime Data
            if (_latestRealtime != null) ...[
              Text('Live Session', 
                   style: Theme.of(context).textTheme.titleMedium?.copyWith(fontWeight: FontWeight.bold)),
              const SizedBox(height: 8),
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.grey[50],
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.grey[300]!),
                ),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        _buildRealtimeItem('Jumps', _latestRealtime!.jumps.toString(), Icons.fitness_center),
                        _buildRealtimeItem('Time', _formatTime(_latestRealtime!.timeSeconds), Icons.timer),
                        _buildRealtimeItem('Cal', _latestRealtime!.calories.toStringAsFixed(1), Icons.local_fire_department),
                      ],
                    ),
                    if (_latestRealtime!.countdown != null) ...[
                      const SizedBox(height: 8),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          const Icon(Icons.timer_outlined, color: Colors.orange, size: 20),
                          const SizedBox(width: 4),
                          Text(
                            'Countdown: ${_latestRealtime!.countdown}s',
                            style: const TextStyle(color: Colors.orange, fontWeight: FontWeight.bold),
                          ),
                        ],
                      ),
                    ],
                    if (_latestRealtime!.restartFlag) ...[
                      const SizedBox(height: 8),
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                        decoration: BoxDecoration(
                          color: Colors.green[100],
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: const Row(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Icon(Icons.refresh, color: Colors.green, size: 16),
                            SizedBox(width: 4),
                            Text('Session Restarted', style: TextStyle(color: Colors.green, fontSize: 12)),
                          ],
                        ),
                      ),
                    ],
                  ],
                ),
              ),
              const SizedBox(height: 16),
            ],

            // Actions
            Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: () => widget.service.clearRopeData(),
                    icon: const Icon(Icons.clear),
                    label: const Text('Clear Data'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.red[100],
                      foregroundColor: Colors.red[800],
                    ),
                  ),
                ),
              ],
            ),

            // Info
            const SizedBox(height: 8),
            Text(
              'Note: Rope skipping data is received automatically when the device is in rope mode',
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: Colors.grey[600],
                fontStyle: FontStyle.italic,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatusCard(String title, String value, IconData icon, Color color) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Column(
        children: [
          Icon(icon, color: color, size: 24),
          const SizedBox(height: 4),
          Text(
            title,
            style: TextStyle(
              fontSize: 12,
              color: color,
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(
            value,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildRealtimeItem(String label, String value, IconData icon) {
    return Column(
      children: [
        Icon(icon, size: 20, color: Colors.grey[600]),
        const SizedBox(height: 4),
        Text(
          label,
          style: TextStyle(fontSize: 10, color: Colors.grey[600]),
        ),
        Text(
          value,
          style: const TextStyle(fontSize: 14, fontWeight: FontWeight.bold),
        ),
      ],
    );
  }
}
