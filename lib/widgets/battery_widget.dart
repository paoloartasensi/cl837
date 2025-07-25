import 'package:flutter/material.dart';
import '../services/data_persistence_manager.dart';

class BatteryWidget extends StatefulWidget {
    final int? latestData;
    final bool isConnected;
    final bool isCharging;
    final VoidCallback? onTap;
    
    const BatteryWidget({
        Key? key, 
        required this.latestData,
        this.isConnected = false,
        this.isCharging = false,
        this.onTap,
    }) : super(key: key);

    @override
    State<BatteryWidget> createState() => _BatteryWidgetState();
}

class _BatteryWidgetState extends State<BatteryWidget> with TickerProviderStateMixin {
    late AnimationController _pulseController;
    late Animation<double> _pulseAnimation;
    List<BatteryReading> _history = [];
    bool _showHistory = false;

    @override
    void initState() {
        super.initState();
        _pulseController = AnimationController(
            duration: const Duration(seconds: 2),
            vsync: this,
        );
        _pulseAnimation = Tween<double>(
            begin: 0.8,
            end: 1.0,
        ).animate(CurvedAnimation(
            parent: _pulseController,
            curve: Curves.easeInOut,
        ));
        
        if (widget.isCharging) {
            _pulseController.repeat(reverse: true);
        }
        
        _loadBatteryHistory();
    }

    @override
    void didUpdateWidget(BatteryWidget oldWidget) {
        super.didUpdateWidget(oldWidget);
        
        if (widget.isCharging != oldWidget.isCharging) {
            if (widget.isCharging) {
                _pulseController.repeat(reverse: true);
            } else {
                _pulseController.stop();
            }
        }
        
        // Save new battery reading
        if (widget.latestData != null && widget.latestData != oldWidget.latestData) {
            _saveBatteryReading();
        }
    }

    @override
    void dispose() {
        _pulseController.dispose();
        super.dispose();
    }

    Future<void> _loadBatteryHistory() async {
        try {
            List<BatteryReading> history = await dataPersistenceManager.getRecentBatteryReadings(limit: 20);
            if (mounted) {
                setState(() {
                    _history = history;
                });
            }
        } catch (e) {
            debugPrint('❌ Error loading battery history: $e');
        }
    }

    Future<void> _saveBatteryReading() async {
        if (widget.latestData == null) return;
        
        try {
            BatteryReading reading = BatteryReading(
                timestamp: DateTime.now(),
                level: widget.latestData!,
                isCharging: widget.isCharging,
            );
            
            await dataPersistenceManager.saveBatteryReading(reading);
            await _loadBatteryHistory();
        } catch (e) {
            debugPrint('❌ Error saving battery reading: $e');
        }
    }

    @override
    Widget build(BuildContext context) {
        return GestureDetector(
            onTap: () {
                setState(() {
                    _showHistory = !_showHistory;
                });
                widget.onTap?.call();
            },
            child: AnimatedContainer(
                duration: const Duration(milliseconds: 300),
                child: Card(
                    elevation: _showHistory ? 8 : 2,
                    child: Column(
                        children: [
                            _buildBatteryHeader(),
                            if (_showHistory) _buildBatteryHistory(),
                        ],
                    ),
                ),
            ),
        );
    }

    Widget _buildBatteryHeader() {
        return Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
                children: [
                    _buildBatteryIcon(),
                    const SizedBox(width: 12),
                    Expanded(
                        child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                                Row(
                                    children: [
                                        const Text(
                                            'Battery',
                                            style: TextStyle(
                                                fontSize: 16,
                                                fontWeight: FontWeight.bold,
                                            ),
                                        ),
                                        const SizedBox(width: 8),
                                        _buildStatusIndicator(),
                                    ],
                                ),
                                const SizedBox(height: 4),
                                _buildBatteryLevel(),
                            ],
                        ),
                    ),
                    _buildBatteryPercentage(),
                ],
            ),
        );
    }

    Widget _buildBatteryIcon() {
        IconData icon = _getBatteryIcon(widget.latestData);
        Color color = _getBatteryColor(widget.latestData);
        
        Widget iconWidget = Icon(icon, size: 28, color: color);
        
        if (widget.isCharging) {
            return AnimatedBuilder(
                animation: _pulseAnimation,
                builder: (context, child) {
                    return Transform.scale(
                        scale: _pulseAnimation.value,
                        child: iconWidget,
                    );
                },
            );
        }
        
        return iconWidget;
    }

    Widget _buildStatusIndicator() {
        if (!widget.isConnected) {
            return Container(
                padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                decoration: BoxDecoration(
                    color: Colors.grey,
                    borderRadius: BorderRadius.circular(8),
                ),
                child: const Text(
                    'Disconnected',
                    style: TextStyle(
                        color: Colors.white,
                        fontSize: 10,
                        fontWeight: FontWeight.bold,
                    ),
                ),
            );
        }
        
        if (widget.isCharging) {
            return Container(
                padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                decoration: BoxDecoration(
                    color: Colors.green,
                    borderRadius: BorderRadius.circular(8),
                ),
                child: const Text(
                    'Charging',
                    style: TextStyle(
                        color: Colors.white,
                        fontSize: 10,
                        fontWeight: FontWeight.bold,
                    ),
                ),
            );
        }
        
        return const SizedBox.shrink();
    }

    Widget _buildBatteryLevel() {
        if (widget.latestData == null) {
            return Container(
                height: 6,
                decoration: BoxDecoration(
                    color: Colors.grey[300],
                    borderRadius: BorderRadius.circular(3),
                ),
            );
        }
        
        double percentage = widget.latestData! / 100.0;
        Color color = _getBatteryColor(widget.latestData);
        
        return Container(
            height: 6,
            decoration: BoxDecoration(
                color: Colors.grey[300],
                borderRadius: BorderRadius.circular(3),
            ),
            child: FractionallySizedBox(
                alignment: Alignment.centerLeft,
                widthFactor: percentage,
                child: Container(
                    decoration: BoxDecoration(
                        color: color,
                        borderRadius: BorderRadius.circular(3),
                    ),
                ),
            ),
        );
    }

    Widget _buildBatteryPercentage() {
        return Column(
            crossAxisAlignment: CrossAxisAlignment.end,
            children: [
                Text(
                    widget.latestData != null ? '${widget.latestData}%' : 'N/A',
                    style: TextStyle(
                        fontSize: 18,
                        color: _getBatteryColor(widget.latestData),
                        fontWeight: FontWeight.bold,
                    ),
                ),
                if (_history.isNotEmpty) ...[
                    const SizedBox(height: 2),
                    Text(
                        _getBatteryTrend(),
                        style: TextStyle(
                            fontSize: 12,
                            color: Colors.grey[600],
                        ),
                    ),
                ],
            ],
        );
    }

    Widget _buildBatteryHistory() {
        if (_history.isEmpty) {
            return const Padding(
                padding: EdgeInsets.all(16.0),
                child: Text(
                    'No battery history available',
                    style: TextStyle(color: Colors.grey),
                ),
            );
        }
        
        return Container(
            height: 120,
            padding: const EdgeInsets.all(16.0),
            child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                    const Text(
                        'Recent Readings',
                        style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                        ),
                    ),
                    const SizedBox(height: 8),
                    Expanded(
                        child: ListView.builder(
                            scrollDirection: Axis.horizontal,
                            itemCount: _history.length,
                            itemBuilder: (context, index) {
                                BatteryReading reading = _history[index];
                                return Container(
                                    width: 60,
                                    margin: const EdgeInsets.only(right: 8),
                                    child: Column(
                                        children: [
                                            Expanded(
                                                child: Container(
                                                    width: 4,
                                                    decoration: BoxDecoration(
                                                        color: Colors.grey[300],
                                                        borderRadius: BorderRadius.circular(2),
                                                    ),
                                                    child: FractionallySizedBox(
                                                        alignment: Alignment.bottomCenter,
                                                        heightFactor: reading.level / 100.0,
                                                        child: Container(
                                                            decoration: BoxDecoration(
                                                                color: _getBatteryColor(reading.level),
                                                                borderRadius: BorderRadius.circular(2),
                                                            ),
                                                        ),
                                                    ),
                                                ),
                                            ),
                                            const SizedBox(height: 4),
                                            Text(
                                                '${reading.level}%',
                                                style: const TextStyle(fontSize: 10),
                                            ),
                                            Text(
                                                '${reading.timestamp.hour.toString().padLeft(2, '0')}:${reading.timestamp.minute.toString().padLeft(2, '0')}',
                                                style: TextStyle(
                                                    fontSize: 8,
                                                    color: Colors.grey[600],
                                                ),
                                            ),
                                        ],
                                    ),
                                );
                            },
                        ),
                    ),
                ],
            ),
        );
    }

    String _getBatteryTrend() {
        if (_history.length < 2) return '';
        
        int current = _history.first.level;
        int previous = _history[1].level;
        
        if (current > previous) {
            return '↗ +${current - previous}%';
        } else if (current < previous) {
            return '↘ ${current - previous}%';
        } else {
            return '→ Stable';
        }
    }

    IconData _getBatteryIcon(int? level) {
        if (level == null) return Icons.battery_unknown;
        if (widget.isCharging) return Icons.battery_charging_full;
        if (level > 90) return Icons.battery_full;
        if (level > 60) return Icons.battery_5_bar;
        if (level > 30) return Icons.battery_3_bar;
        if (level > 15) return Icons.battery_2_bar;
        if (level > 5) return Icons.battery_1_bar;
        return Icons.battery_0_bar;
    }

    Color _getBatteryColor(int? level) {
        if (level == null) return Colors.grey;
        if (level > 60) return Colors.green;
        if (level > 30) return Colors.orange;
        if (level > 15) return Colors.red;
        return Colors.red[800]!;
    }
}
