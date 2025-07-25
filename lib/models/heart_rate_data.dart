class HeartRateData {
  final int heartRate;
  final bool? contactDetected;
  final bool contactSupported;
  final int? energyExpanded;
  final List<double>? rrIntervals;
  final DateTime timestamp;

  HeartRateData({
    required this.heartRate,
    this.contactDetected,
    this.contactSupported = false,
    this.energyExpanded,
    this.rrIntervals,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  @override
  String toString() {
    final buffer = StringBuffer();
    buffer.write('Heart Rate: $heartRate bpm');
    
    if (contactSupported) {
      if (contactDetected == true) {
        buffer.write(', Contact Detected');
      } else if (contactDetected == false) {
        buffer.write(', Contact NOT Detected');
      }
    } else {
      buffer.write(', Contact Not Supported');
    }
    
    if (energyExpanded != null) {
      buffer.write(', Energy Expanded: $energyExpanded kJ');
    }
    
    if (rrIntervals != null && rrIntervals!.isNotEmpty) {
      buffer.write(', RR Intervals: ');
      for (int i = 0; i < rrIntervals!.length; i++) {
        if (i > 0) buffer.write(', ');
        buffer.write('${rrIntervals![i].toStringAsFixed(2)} ms');
      }
    }
    
    return buffer.toString();
  }
}
