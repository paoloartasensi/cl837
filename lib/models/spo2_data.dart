class SpO2Data {
  final int? spo2Value; // Now nullable to handle status vs measurement
  final bool correctWristPosture;
  final int signalQuality; // 0=no signal, <8=weak, >15=good
  final bool isWearing;
  final DateTime timestamp;

  SpO2Data({
    this.spo2Value, // Made optional
    required this.correctWristPosture,
    required this.signalQuality,
    required this.isWearing,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  String get signalQualityDescription {
    if (signalQuality == 0) return 'No Signal';
    if (signalQuality < 8) return 'Weak Signal';
    return 'Good Signal';
  }

  bool get isValidMeasurement => spo2Value != null && spo2Value! >= 70 && spo2Value! <= 100;
  bool get isDeviceReady => isWearing && correctWristPosture && signalQuality >= 8;

  @override
  String toString() {
    final valueStr = spo2Value?.toString() ?? 'N/A';
    return 'SpO2: $valueStr%, ${isWearing ? 'Wearing' : 'Not Wearing'}, $signalQualityDescription, ${correctWristPosture ? 'Correct' : 'Incorrect'} Position';
  }
}
