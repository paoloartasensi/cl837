/// Modello per i dati real-time ricevuti automaticamente alla connessione
/// Simile ai dati mostrati nell'SDK iOS (MainViewController)
class RealtimeDeviceData {
  final String? deviceName;
  final String? sdkVersion;
  final String? softwareVersion;
  final String? rssi;
  final int? battery;
  final int? heartRate;
  final int? steps;
  final double? distanceMeters;
  final double? calories;
  final int? vo2Max;
  final int? breathRate;
  final String? emotionLevel;
  final int? stressPercent;
  final String? stamina;
  
  const RealtimeDeviceData({
    this.deviceName,
    this.sdkVersion,
    this.softwareVersion,
    this.rssi,
    this.battery,
    this.heartRate,
    this.steps,
    this.distanceMeters,
    this.calories,
    this.vo2Max,
    this.breathRate,
    this.emotionLevel,
    this.stressPercent,
    this.stamina,
  });
  
  RealtimeDeviceData copyWith({
    String? deviceName,
    String? sdkVersion,
    String? softwareVersion,
    String? rssi,
    int? battery,
    int? heartRate,
    int? steps,
    double? distanceMeters,
    double? calories,
    int? vo2Max,
    int? breathRate,
    String? emotionLevel,
    int? stressPercent,
    String? stamina,
  }) {
    return RealtimeDeviceData(
      deviceName: deviceName ?? this.deviceName,
      sdkVersion: sdkVersion ?? this.sdkVersion,
      softwareVersion: softwareVersion ?? this.softwareVersion,
      rssi: rssi ?? this.rssi,
      battery: battery ?? this.battery,
      heartRate: heartRate ?? this.heartRate,
      steps: steps ?? this.steps,
      distanceMeters: distanceMeters ?? this.distanceMeters,
      calories: calories ?? this.calories,
      vo2Max: vo2Max ?? this.vo2Max,
      breathRate: breathRate ?? this.breathRate,
      emotionLevel: emotionLevel ?? this.emotionLevel,
      stressPercent: stressPercent ?? this.stressPercent,
      stamina: stamina ?? this.stamina,
    );
  }
  
  @override
  String toString() {
    return 'RealtimeDeviceData{deviceName: $deviceName, HR: $heartRate bpm, steps: $steps, distance: ${distanceMeters}m, calories: ${calories}kcal}';
  }
}

/// Converti EmotionLevel a stringa leggibile
String getEmotionLevelString(int? level) {
  if (level == null) return 'Unknown';
  switch (level) {
    case 1:
      return 'Relaxed';
    case 2:
      return 'Calm';
    case 3:
      return 'Normal';
    case 4:
      return 'Excited';
    case 5:
      return 'Stressed';
    default:
      return 'Unknown';
  }
}

/// Converti Stamina a stringa leggibile
String getStaminaString(int? stamina) {
  if (stamina == null) return 'Unknown';
  if (stamina < 20) return 'Excellent';
  if (stamina < 40) return 'Good';
  if (stamina < 60) return 'Normal';
  if (stamina < 80) return 'Tired';
  return 'Exhausted';
}
