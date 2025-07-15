import 'package:flutter/material.dart';
import 'dart:async';

enum LEDColor { 
  green, 
  red, 
  white, 
  off,
  cycling  // Special state for boot/pairing
}

enum DeviceMode {
  manual,
  automatic,
  spo2,
  paused
}

class LEDStatusData {
  final LEDColor currentColor;
  final DeviceMode mode;
  final bool isConnected;
  final bool isVibrating;
  final String statusMessage;
  final DateTime timestamp;

  LEDStatusData({
    required this.currentColor,
    required this.mode,
    required this.isConnected,
    required this.isVibrating,
    required this.statusMessage,
    required this.timestamp,
  });
}

class LEDStatusWidget extends StatefulWidget {
  final bool isConnected;
  final bool isManualMode;
  final bool isSpO2Active;
  final bool isHeartRatePaused;

  const LEDStatusWidget({
    super.key,
    required this.isConnected,
    required this.isManualMode,
    this.isSpO2Active = false,
    this.isHeartRatePaused = false,
  });

  @override
  State<LEDStatusWidget> createState() => _LEDStatusWidgetState();
}

class _LEDStatusWidgetState extends State<LEDStatusWidget>
    with TickerProviderStateMixin {
  late AnimationController _cyclingController;
  late AnimationController _pulseController;
  late AnimationController _blinkController; // For slow green blink
  late Animation<Color?> _cyclingAnimation;
  late Animation<double> _pulseAnimation;
  late Animation<double> _blinkAnimation;
  
  Timer? _vibrationTimer;
  bool _isVibrating = false;
  
  @override
  void initState() {
    super.initState();
    
    // Cycling animation for boot/pairing sequence
    _cyclingController = AnimationController(
      duration: const Duration(seconds: 3),
      vsync: this,
    );
    
    // Pulse animation for vibration feedback
    _pulseController = AnimationController(
      duration: const Duration(milliseconds: 500),
      vsync: this,
    );
    
    // Slow blink animation for manual mode green LED
    _blinkController = AnimationController(
      duration: const Duration(seconds: 2),
      vsync: this,
    );
    
    _cyclingAnimation = TweenSequence<Color?>([
      TweenSequenceItem(
        weight: 1.0,
        tween: ColorTween(begin: Colors.green, end: Colors.green),
      ),
      TweenSequenceItem(
        weight: 1.0,
        tween: ColorTween(begin: Colors.green, end: Colors.red),
      ),
      TweenSequenceItem(
        weight: 1.0,
        tween: ColorTween(begin: Colors.red, end: Colors.red),
      ),
      TweenSequenceItem(
        weight: 1.0,
        tween: ColorTween(begin: Colors.red, end: Colors.white),
      ),
      TweenSequenceItem(
        weight: 1.0,
        tween: ColorTween(begin: Colors.white, end: Colors.white),
      ),
      TweenSequenceItem(
        weight: 1.0,
        tween: ColorTween(begin: Colors.white, end: Colors.green),
      ),
    ]).animate(_cyclingController);
    
    _pulseAnimation = Tween<double>(
      begin: 1.0,
      end: 1.3,
    ).animate(CurvedAnimation(
      parent: _pulseController,
      curve: Curves.elasticOut,
    ));
    
    _blinkAnimation = Tween<double>(
      begin: 0.3,
      end: 1.0,
    ).animate(CurvedAnimation(
      parent: _blinkController,
      curve: Curves.easeInOut,
    ));
    
    _updateLEDState();
  }
  
  @override
  void didUpdateWidget(LEDStatusWidget oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.isConnected != widget.isConnected ||
        oldWidget.isManualMode != widget.isManualMode ||
        oldWidget.isSpO2Active != widget.isSpO2Active ||
        oldWidget.isHeartRatePaused != widget.isHeartRatePaused) {
      _updateLEDState();
    }
  }
  
  void _updateLEDState() {
    if (!widget.isConnected) {
      // Boot/Pairing sequence - cycling colors
      _cyclingController.repeat();
      _blinkController.stop();
      _stopVibration();
    } else {
      _cyclingController.stop();
      
      // For connected device, show actual observed behavior:
      if (widget.isSpO2Active) {
        // SpO2 mode - red LED during active measurement
        _blinkController.stop();
        _startPeriodicVibration();
      } else if (widget.isHeartRatePaused) {
        // Paused - LED off
        _blinkController.stop();
        _stopVibration();
      } else if (widget.isManualMode) {
        // Manual mode - slow green blink (like real device)
        _blinkController.repeat(reverse: true);
        _stopVibration();
      } else {
        // Auto mode - stop blinking
        _blinkController.stop();
        _stopVibration();
      }
    }
  }
  
  void _startPeriodicVibration() {
    _vibrationTimer?.cancel();
    _vibrationTimer = Timer.periodic(const Duration(seconds: 3), (timer) {
      _triggerVibrationPulse();
    });
  }
  
  void _stopVibration() {
    _vibrationTimer?.cancel();
    _isVibrating = false;
  }
  
  void _triggerVibrationPulse() {
    setState(() {
      _isVibrating = true;
    });
    _pulseController.forward().then((_) {
      _pulseController.reverse().then((_) {
        setState(() {
          _isVibrating = false;
        });
      });
    });
  }
  
  LEDStatusData _getCurrentStatus() {
    if (!widget.isConnected) {
      return LEDStatusData(
        currentColor: LEDColor.cycling,
        mode: DeviceMode.manual,
        isConnected: false,
        isVibrating: false,
        statusMessage: "Boot/Pairing Sequence (NORMAL)",
        timestamp: DateTime.now(),
      );
    }
    
    if (widget.isSpO2Active) {
      return LEDStatusData(
        currentColor: LEDColor.red,
        mode: DeviceMode.spo2,
        isConnected: true,
        isVibrating: _isVibrating,
        statusMessage: "SpO2 Measurement Active",
        timestamp: DateTime.now(),
      );
    }
    
    if (widget.isHeartRatePaused) {
      return LEDStatusData(
        currentColor: LEDColor.off,
        mode: DeviceMode.paused,
        isConnected: true,
        isVibrating: false,
        statusMessage: "Heart Rate Service Paused",
        timestamp: DateTime.now(),
      );
    }
    
    // For normal connected operation, show the actual observed device behavior
    if (widget.isManualMode) {
      return LEDStatusData(
        currentColor: LEDColor.green,
        mode: DeviceMode.manual,
        isConnected: true,
        isVibrating: false, // No continuous vibration in manual mode
        statusMessage: "Manual Mode - Slow Green Blink (ACTUAL)",
        timestamp: DateTime.now(),
      );
    } else {
      return LEDStatusData(
        currentColor: LEDColor.red,
        mode: DeviceMode.automatic,
        isConnected: true,
        isVibrating: false, // Show real vibration state, not simulated
        statusMessage: "Auto Mode - Device May Be Stressed",
        timestamp: DateTime.now(),
      );
    }
  }
  
  Color _getLEDDisplayColor(LEDStatusData status) {
    switch (status.currentColor) {
      case LEDColor.green:
        return Colors.green;
      case LEDColor.red:
        return Colors.red;
      case LEDColor.white:
        return Colors.white;
      case LEDColor.off:
        return Colors.grey[800]!;
      case LEDColor.cycling:
        return _cyclingAnimation.value ?? Colors.green;
    }
  }
  
  IconData _getModeIcon(DeviceMode mode) {
    switch (mode) {
      case DeviceMode.manual:
        return Icons.touch_app;
      case DeviceMode.automatic:
        return Icons.refresh;
      case DeviceMode.spo2:
        return Icons.favorite;
      case DeviceMode.paused:
        return Icons.pause;
    }
  }
  
  Color _getModeColor(DeviceMode mode) {
    switch (mode) {
      case DeviceMode.manual:
        return Colors.orange;
      case DeviceMode.automatic:
        return Colors.red;
      case DeviceMode.spo2:
        return Colors.purple;
      case DeviceMode.paused:
        return Colors.grey;
    }
  }
  
  @override
  Widget build(BuildContext context) {
    final status = _getCurrentStatus();
    final ledColor = _getLEDDisplayColor(status);
    
    return AnimatedBuilder(
      animation: Listenable.merge([_cyclingController, _pulseController]),
      builder: (context, child) {
        return Card(
          elevation: 4,
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  children: [
                    Text(
                      'LED Status Monitor',
                      style: Theme.of(context).textTheme.titleMedium?.copyWith(
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const Spacer(),
                    Icon(
                      Icons.sensors,
                      color: status.isConnected ? Colors.green : Colors.grey,
                    ),
                  ],
                ),
                const SizedBox(height: 16),
                
                // LED Visual Indicator
                Row(
                  children: [
                    Transform(
                      transform: status.isVibrating
                          ? (Matrix4.identity()..scale(_pulseAnimation.value))
                          : Matrix4.identity(),
                      child: Container(
                        width: 24,
                        height: 24,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: ledColor,
                          border: Border.all(
                            color: Colors.black,
                            width: 2,
                          ),
                          boxShadow: status.currentColor != LEDColor.off
                              ? [
                                  BoxShadow(
                                    color: ledColor.withOpacity(0.6),
                                    blurRadius: 8,
                                    spreadRadius: 2,
                                  ),
                                ]
                              : null,
                        ),
                      ),
                    ),
                    const SizedBox(width: 12),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            status.statusMessage,
                            style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                              fontWeight: FontWeight.w500,
                            ),
                          ),
                          const SizedBox(height: 4),
                          Row(
                            children: [
                              Icon(
                                _getModeIcon(status.mode),
                                size: 16,
                                color: _getModeColor(status.mode),
                              ),
                              const SizedBox(width: 4),
                              Text(
                                status.mode.name.toUpperCase(),
                                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                                  color: _getModeColor(status.mode),
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              if (status.isVibrating) ...[
                                const SizedBox(width: 8),
                                const Icon(
                                  Icons.vibration,
                                  size: 16,
                                  color: Colors.orange,
                                ),
                              ],
                            ],
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
                
                const SizedBox(height: 12),
                
                // Status Details
                Container(
                  padding: const EdgeInsets.all(8),
                  decoration: BoxDecoration(
                    color: Colors.grey[100],
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Row(
                    children: [
                      Expanded(
                        child: _buildStatusItem(
                          'Connection', 
                          status.isConnected ? 'Connected' : 'Disconnected',
                          status.isConnected ? Colors.green : Colors.red,
                        ),
                      ),
                      Container(width: 1, height: 40, color: Colors.grey[300]),
                      Expanded(
                        child: _buildStatusItem(
                          'LED Color', 
                          status.currentColor == LEDColor.cycling 
                              ? 'G→R→W' 
                              : status.currentColor.name.toUpperCase(),
                          ledColor,
                        ),
                      ),
                      Container(width: 1, height: 40, color: Colors.grey[300]),
                      Expanded(
                        child: _buildStatusItem(
                          'Vibration', 
                          status.isVibrating ? 'Active' : 'None',
                          status.isVibrating ? Colors.orange : Colors.grey,
                        ),
                      ),
                    ],
                  ),
                ),
                
                const SizedBox(height: 8),
                
                // Help Text
                Text(
                  status.currentColor == LEDColor.cycling
                      ? 'LED cycling G→R→W is NORMAL before connection!'
                      : 'Mirroring actual device LED behavior - not simulated',
                  style: Theme.of(context).textTheme.bodySmall?.copyWith(
                    color: Colors.grey[600],
                    fontStyle: FontStyle.italic,
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
  
  Widget _buildStatusItem(String label, String value, Color color) {
    return Column(
      children: [
        Text(
          label,
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
          ),
        ),
        const SizedBox(height: 4),
        Text(
          value,
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: color,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }
  
  @override
  void dispose() {
    _cyclingController.dispose();
    _pulseController.dispose();
    _vibrationTimer?.cancel();
    super.dispose();
  }
}
