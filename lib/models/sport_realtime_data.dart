/// Dati sportivi real-time (comando 0x15)
/// Ricevuti automaticamente come notifica dal dispositivo
class SportRealtimeData {
  final int steps;
  final double distanceMeters; // Distance in meters
  final double caloriesKcal;   // Calories in kcal
  final DateTime timestamp;

  const SportRealtimeData({
    required this.steps,
    required this.distanceMeters,
    required this.caloriesKcal,
    required this.timestamp,
  });

  /// Distance in kilometers
  double get distanceKm => distanceMeters / 1000;

  factory SportRealtimeData.fromBytes(List<int> data) {
    // Format from iOS SDK (HeartBLEDevice.m line 417-439):
    // buffer_[2] == 0x15 (Sport Real-time Data)
    // Hex format: FF LL 15 SSSSSS DDDDDD CCCCCC [XX]
    // - SSSSSS: Steps (6 hex chars = 3 bytes BIG-ENDIAN)
    // - DDDDDD: Distance in cm (6 hex chars = 3 bytes BIG-ENDIAN) / 100 = meters  
    // - CCCCCC: Calories * 10 (6 hex chars = 3 bytes BIG-ENDIAN) / 10 = kcal
    // - [XX]: Optional checksum byte
    // 
    // IMPORTANT: iOS SDK reads as hex string and parses as integers,
    // which means the bytes are in BIG-ENDIAN order (most significant byte first)
    // 
    // Example: 0x00 0x02 0x94 = 0x000294 = 660 (not 9699840 if read as little-endian!)
    // 
    // Actual device sends 13 bytes: FF 0D 15 + 9 data bytes + checksum
    // Minimum required: FF LL 15 + 9 data bytes = 12 bytes (without checksum)
    
    if (data.length < 12) {
      throw ArgumentError('Invalid sport data length: ${data.length}, expected >= 12 (got: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')})');
    }

    if (data[0] != 0xFF) {
      throw ArgumentError('Invalid start byte: 0x${data[0].toRadixString(16)}');
    }

    if (data[2] != 0x15) {
      throw ArgumentError('Invalid command byte: 0x${data[2].toRadixString(16)}, expected 0x15');
    }

    // Parse steps (bytes 3-5, 3 bytes BIG-ENDIAN)
    // iOS SDK reads as hex string, which is naturally big-endian
    int steps = (data[3] << 16) | (data[4] << 8) | data[5];

    // Parse distance in cm (bytes 6-8, 3 bytes BIG-ENDIAN)
    int distanceCm = (data[6] << 16) | (data[7] << 8) | data[8];
    double distanceMeters = distanceCm / 100.0;

    // Parse calories * 10 (bytes 9-11, 3 bytes BIG-ENDIAN)
    int caloriesTimes10 = (data[9] << 16) | (data[10] << 8) | data[11];
    double caloriesKcal = caloriesTimes10 / 10.0;

    return SportRealtimeData(
      steps: steps,
      distanceMeters: distanceMeters,
      caloriesKcal: caloriesKcal,
      timestamp: DateTime.now(),
    );
  }

  @override
  String toString() {
    return 'SportRealtimeData{steps: $steps, distance: ${distanceKm.toStringAsFixed(2)}km, calories: ${caloriesKcal.toStringAsFixed(1)}kcal}';
  }

  SportRealtimeData copyWith({
    int? steps,
    double? distanceMeters,
    double? caloriesKcal,
    DateTime? timestamp,
  }) {
    return SportRealtimeData(
      steps: steps ?? this.steps,
      distanceMeters: distanceMeters ?? this.distanceMeters,
      caloriesKcal: caloriesKcal ?? this.caloriesKcal,
      timestamp: timestamp ?? this.timestamp,
    );
  }
}
