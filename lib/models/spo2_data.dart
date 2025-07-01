class SpO2Data {
  final int spo2Value;
  final bool correctWristPosture;
  final int signalQuality; // 0=no signal, <8=weak, >15=good
  final bool isWearing;
  final DateTime timestamp;

  SpO2Data({
    required this.spo2Value,
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

  @override
  String toString() {
    return 'SpO2: $spo2Value%, ${isWearing ? 'Wearing' : 'Not Wearing'}, $signalQualityDescription, ${correctWristPosture ? 'Correct' : 'Incorrect'} Position';
  }
}
