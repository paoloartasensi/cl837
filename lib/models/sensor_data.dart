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

  /// Magnitude of acceleration (Euclidean norm)
  /// magnitude = √(x² + y² + z²)
  double get magnitude => (x * x + y * y + z * z);

  /// Parse CL837 accelerometer packet format
  /// Format: [0xFF, length, 0x0C, X_low, X_high, Y_low, Y_high, Z_low, Z_high, ...]
  /// Each sample is 6 bytes: X (2 bytes), Y (2 bytes), Z (2 bytes) as signed int16
  /// Scale factor: ±8g range (CL837 specification)
  factory AccelerometerData.fromRawDataCl837(List<int> data) {
    // Validation to avoid reading incorrect packets
    if (data.length < 9) {
      throw ArgumentError('Invalid data length for CL837: ${data.length}');
    }

    // Validate packet header
    if (data[0] != 0xFF) {
      throw ArgumentError('Invalid CL837 packet header: 0x${data[0].toRadixString(16)}');
    }

    if (data[2] != 0x0C) {
      throw ArgumentError('Invalid CL837 packet type: 0x${data[2].toRadixString(16)}');
    }

    // Robust conversion function for signed int16
    int convertToSigned16(int lowByte, int highByte) {
      int value = (highByte << 8) | lowByte;
      // Correctly convert signed 16-bit values
      return (value & 0x8000) != 0 ? value - 0x10000 : value;
    }

    // Read raw values with offset 3 as per CL837 documentation
    final rawX = convertToSigned16(data[3], data[4]);
    final rawY = convertToSigned16(data[5], data[6]);
    final rawZ = convertToSigned16(data[7], data[8]);

    // Protection against anomalous values that cause spikes
    // Define a maximum plausible value in raw units
    const int maxRawValue = 25000; // Approximately 6g at 32768 = 8g

    int clampRawValue(int value) {
      if (value > maxRawValue) return maxRawValue;
      if (value < -maxRawValue) return -maxRawValue;
      return value;
    }

    // Apply clamp to avoid spikes
    final clampedX = clampRawValue(rawX);
    final clampedY = clampRawValue(rawY);
    final clampedZ = clampRawValue(rawZ);

    // Scale factor for CL837 (±8g range)
    // 32768 raw units = 8g → 1g = 4096 raw units
    const scaleFactor = 8.0 / 32768.0;

    return AccelerometerData(
      x: clampedX * scaleFactor,
      y: clampedY * scaleFactor,
      z: clampedZ * scaleFactor,
    );
  }

  @override
  String toString() {
    return 'AccelerometerData(x: ${x.toStringAsFixed(3)}g, y: ${y.toStringAsFixed(3)}g, z: ${z.toStringAsFixed(3)}g)';
  }
}

/// 3D Accelerometer Frequency Settings
enum Sensor3DFrequency {
  hz25(0, '25 Hz'),
  hz50(1, '50 Hz'),
  hz100(2, '100 Hz'),
  hz200(3, '200 Hz'),
  hz400(4, '400 Hz');

  final int value;
  final String label;

  const Sensor3DFrequency(this.value, this.label);

  static Sensor3DFrequency fromValue(int value) {
    return Sensor3DFrequency.values.firstWhere(
      (e) => e.value == value,
      orElse: () => Sensor3DFrequency.hz25,
    );
  }
}

/// 6D Sensor Frequency Settings (Accelerometer + Gyroscope)
enum Sensor6DFrequency {
  hz26(0, '26 Hz'),
  hz52(1, '52 Hz'),
  hz104(2, '104 Hz'),
  hz208(3, '208 Hz');

  final int value;
  final String label;

  const Sensor6DFrequency(this.value, this.label);

  static Sensor6DFrequency fromValue(int value) {
    return Sensor6DFrequency.values.firstWhere(
      (e) => e.value == value,
      orElse: () => Sensor6DFrequency.hz26,
    );
  }
}

/// 3D Accelerometer Status
class Sensor3DStatus {
  final bool enabled;

  const Sensor3DStatus({required this.enabled});

  @override
  String toString() => 'Sensor3DStatus(enabled: $enabled)';
}

/// 6D Sensor Raw Data (Gyroscope + Accelerometer)
class Sensor6DRawData {
  /// UTC timestamp (0xFF if not supported)
  final int? utc;

  /// Sequence number for data ordering
  final int sequence;

  /// Gyroscope X-axis (degrees/second)
  final int gyroscopeX;

  /// Gyroscope Y-axis (degrees/second)
  final int gyroscopeY;

  /// Gyroscope Z-axis (degrees/second)
  final int gyroscopeZ;

  /// Accelerometer X-axis (mg - milligravity)
  final int accelerometerX;

  /// Accelerometer Y-axis (mg)
  final int accelerometerY;

  /// Accelerometer Z-axis (mg)
  final int accelerometerZ;

  /// Timestamp when data was received
  final DateTime timestamp;

  Sensor6DRawData({
    this.utc,
    required this.sequence,
    required this.gyroscopeX,
    required this.gyroscopeY,
    required this.gyroscopeZ,
    required this.accelerometerX,
    required this.accelerometerY,
    required this.accelerometerZ,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  /// Check if device supports UTC timestamp
  bool get hasUTC => utc != null && utc != 0xFF;

  /// Get DateTime from UTC if available
  DateTime? get utcDateTime {
    if (hasUTC) {
      return DateTime.fromMillisecondsSinceEpoch(utc! * 1000);
    }
    return null;
  }

  /// Calculate magnitude of gyroscope vector
  double get gyroscopeMagnitude {
    return _calculateMagnitude(gyroscopeX, gyroscopeY, gyroscopeZ);
  }

  /// Calculate magnitude of accelerometer vector
  double get accelerometerMagnitude {
    return _calculateMagnitude(accelerometerX, accelerometerY, accelerometerZ);
  }

  double _calculateMagnitude(int x, int y, int z) {
    final magnitude = x * x + y * y + z * z;
    return magnitude.toDouble();
  }

  @override
  String toString() {
    String utcStr = hasUTC ? 'UTC: $utcDateTime' : 'No UTC';
    return 'Sensor6DRawData($utcStr, seq: $sequence, '
        'gyro: [$gyroscopeX, $gyroscopeY, $gyroscopeZ], '
        'accel: [$accelerometerX, $accelerometerY, $accelerometerZ])';
  }
}

/// RR Interval Data (for advanced HRV analysis)
class RRIntervalData {
  /// RR interval value (milliseconds between heartbeats)
  final int interval;

  /// Timestamp of the measurement
  final DateTime timestamp;

  /// UTC timestamp from device (if available)
  final int? utc;

  RRIntervalData({
    required this.interval,
    required this.timestamp,
    this.utc,
  });

  /// Get heart rate from RR interval (60000 / RR interval in ms)
  int get heartRate {
    if (interval > 0) {
      return (60000 / interval).round();
    }
    return 0;
  }

  @override
  String toString() {
    return 'RRInterval($interval ms, HR: $heartRate BPM, $timestamp)';
  }
}

/// Single Button Press Event
class SingleButtonPress {
  /// Timestamp when button was pressed
  final DateTime timestamp;

  /// UTC timestamp from device
  final int utc;

  SingleButtonPress({
    required this.timestamp,
    required this.utc,
  });

  @override
  String toString() {
    return 'ButtonPress($timestamp, UTC: $utc)';
  }
}