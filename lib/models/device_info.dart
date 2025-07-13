class DeviceInfo {
  final String? deviceName;
  final String? firmwareVersion;
  final String? hardwareVersion;
  final String? macAddress;
  final int? memoryTotal;
  final int? memoryUsed;
  final int? memoryFree;
  final Map<String, dynamic>? rawData;
  final DateTime timestamp;

  DeviceInfo({
    this.deviceName,
    this.firmwareVersion,
    this.hardwareVersion,
    this.macAddress,
    this.memoryTotal,
    this.memoryUsed,
    this.memoryFree,
    this.rawData,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  double get memoryUsagePercentage {
    if (memoryTotal == null || memoryUsed == null || memoryTotal == 0) return 0.0;
    return (memoryUsed! / memoryTotal!) * 100;
  }

  String get formattedMemoryUsage {
    if (memoryTotal == null || memoryUsed == null) return 'Unknown';
    return '${memoryUsed}KB / ${memoryTotal}KB (${memoryUsagePercentage.toStringAsFixed(1)}%)';
  }

  @override
  String toString() {
    return 'DeviceInfo(name: $deviceName, fw: $firmwareVersion, hw: $hardwareVersion, '
           'memory: $formattedMemoryUsage)';
  }
}

class BatteryInfo {
  final int level; // 0-100
  final bool isCharging;
  final int? voltage; // in mV
  final DateTime timestamp;

  BatteryInfo({
    required this.level,
    required this.isCharging,
    this.voltage,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  @override
  String toString() {
    return 'BatteryInfo(level: $level%, charging: $isCharging, voltage: ${voltage}mV)';
  }
}
