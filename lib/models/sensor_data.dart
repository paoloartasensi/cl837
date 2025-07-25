class SensorData {
  final DateTime timestamp;
  final AccelerometerData? accelerometer;
  final int? heartRate;
  final int? batteryLevel;

  const SensorData({
    required this.timestamp,
    this.accelerometer,
    this.heartRate,
    this.batteryLevel,
  });

  SensorData copyWith({
    DateTime? timestamp,
    AccelerometerData? accelerometer,
    int? heartRate,
    int? batteryLevel,
  }) {
    return SensorData(
      timestamp: timestamp ?? this.timestamp,
      accelerometer: accelerometer ?? this.accelerometer,
      heartRate: heartRate ?? this.heartRate,
      batteryLevel: batteryLevel ?? this.batteryLevel,
    );
  }
}

class AccelerometerData {
  final double x;
  final double y;
  final double z;

  const AccelerometerData({
    required this.x,
    required this.y,
    required this.z,
  });
}