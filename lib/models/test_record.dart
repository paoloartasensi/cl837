/// Record di un test salvato nel diario
class TestRecord {
  final String id;
  final DateTime timestamp;
  final TestType type;
  final Map<String, dynamic> data;
  final String? notes;

  TestRecord({
    required this.id,
    required this.timestamp,
    required this.type,
    required this.data,
    this.notes,
  });

  factory TestRecord.fromJson(Map<String, dynamic> json) {
    return TestRecord(
      id: json['id'],
      timestamp: DateTime.parse(json['timestamp']),
      type: TestType.fromString(json['type']),
      data: Map<String, dynamic>.from(json['data']),
      notes: json['notes'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'timestamp': timestamp.toIso8601String(),
      'type': type.name,
      'data': data,
      'notes': notes,
    };
  }

  /// Crea un record dal dato di heart rate
  static TestRecord fromHeartRate(int heartRate, List<int>? rrIntervals) {
    return TestRecord(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      timestamp: DateTime.now(),
      type: TestType.heartRate,
      data: {
        'heartRate': heartRate,
        'rrIntervals': rrIntervals ?? [],
        'quality': rrIntervals?.isNotEmpty == true ? 'Good' : 'Basic',
      },
    );
  }

  /// Crea un record dal dato SpO2
  static TestRecord fromSpO2(int spO2, int signalQuality, String wearStatus) {
    return TestRecord(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      timestamp: DateTime.now(),
      type: TestType.spO2,
      data: {
        'spO2': spO2,
        'signalQuality': signalQuality,
        'wearStatus': wearStatus,
        'quality': signalQuality > 80 ? 'Good' : signalQuality > 50 ? 'Fair' : 'Poor',
      },
    );
  }

  /// Crea un record dal dato di temperatura
  static TestRecord fromTemperature(double ambient, double wrist, double body) {
    return TestRecord(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      timestamp: DateTime.now(),
      type: TestType.temperature,
      data: {
        'ambient': ambient,
        'wrist': wrist,
        'body': body,
        'unit': '°C',
      },
    );
  }

  /// Crea un record dai dati sportivi
  static TestRecord fromSports(int steps, double distance, double calories) {
    return TestRecord(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      timestamp: DateTime.now(),
      type: TestType.sports,
      data: {
        'steps': steps,
        'distance': distance,
        'calories': calories,
        'unit_distance': 'cm',
        'unit_calories': 'kcal',
      },
    );
  }

  /// Crea un record dal rope skipping
  static TestRecord fromRopeSkipping(String mode, int jumps, int timeSeconds, double calories) {
    return TestRecord(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      timestamp: DateTime.now(),
      type: TestType.ropeSkipping,
      data: {
        'mode': mode,
        'jumps': jumps,
        'timeSeconds': timeSeconds,
        'calories': calories,
        'avgJumpsPerMinute': timeSeconds > 0 ? (jumps * 60 / timeSeconds).round() : 0,
      },
    );
  }

  /// Crea un record dai dati della batteria
  static TestRecord fromBattery(int level, bool isCharging, int? voltage) {
    return TestRecord(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      timestamp: DateTime.now(),
      type: TestType.battery,
      data: {
        'level': level,
        'isCharging': isCharging,
        'voltage': voltage,
        'status': isCharging ? 'Charging' : 'Discharging',
      },
    );
  }

  String get formattedTimestamp => '${timestamp.day.toString().padLeft(2, '0')}/'
      '${timestamp.month.toString().padLeft(2, '0')}/${timestamp.year} '
      '${timestamp.hour.toString().padLeft(2, '0')}:'
      '${timestamp.minute.toString().padLeft(2, '0')}';

  String get summary {
    switch (type) {
      case TestType.heartRate:
        return '❤️ HR: ${data['heartRate']} bpm (${data['quality']})';
      case TestType.spO2:
        return '🫁 SpO2: ${data['spO2']}% (${data['quality']})';
      case TestType.temperature:
        return '🌡️ Body: ${data['body']}°C, Wrist: ${data['wrist']}°C';
      case TestType.sports:
        return '🏃 Steps: ${data['steps']}, Cal: ${data['calories']}kcal';
      case TestType.ropeSkipping:
        return '🪢 ${data['mode']}: ${data['jumps']} jumps in ${data['timeSeconds']}s';
      case TestType.battery:
        return '🔋 ${data['level']}% (${data['status']})';
      case TestType.deviceInfo:
        return '📱 Device: ${data['name'] ?? 'CL837'}';
    }
  }

  @override
  String toString() {
    return 'TestRecord(${type.name}, $formattedTimestamp, $summary)';
  }
}

enum TestType {
  heartRate('Heart Rate'),
  spO2('SpO2'),
  temperature('Temperature'),
  sports('Sports'),
  ropeSkipping('Rope Skipping'),
  battery('Battery'),
  deviceInfo('Device Info');

  const TestType(this.displayName);
  
  final String displayName;

  String get name => toString().split('.').last;

  static TestType fromString(String value) {
    return TestType.values.firstWhere(
      (type) => type.name == value,
      orElse: () => TestType.heartRate,
    );
  }

  String get icon {
    switch (this) {
      case TestType.heartRate:
        return '❤️';
      case TestType.spO2:
        return '🫁';
      case TestType.temperature:
        return '🌡️';
      case TestType.sports:
        return '🏃';
      case TestType.ropeSkipping:
        return '🪢';
      case TestType.battery:
        return '🔋';
      case TestType.deviceInfo:
        return '📱';
    }
  }
}
