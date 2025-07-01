class TemperatureData {
  final double ambientTempC;
  final double wristTempC;
  final double bodyTempC;
  final DateTime timestamp;

  TemperatureData({
    required this.ambientTempC,
    required this.wristTempC,
    required this.bodyTempC,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  double get bodyTempF => (bodyTempC * 9 / 5) + 32;
  double get wristTempF => (wristTempC * 9 / 5) + 32;
  double get ambientTempF => (ambientTempC * 9 / 5) + 32;

  @override
  String toString() {
    return 'Body: ${bodyTempC.toStringAsFixed(1)}°C, Wrist: ${wristTempC.toStringAsFixed(1)}°C, Ambient: ${ambientTempC.toStringAsFixed(1)}°C';
  }
}
