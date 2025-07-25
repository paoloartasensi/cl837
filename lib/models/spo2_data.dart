class SpO2Data {
  final int value; // Valore SpO2 0-100%
  final int piValue; // Perfusion Index 0-255
  final int gesture; // Postura polso (0=errata, 1=corretta)
  final int onWrist; // Contatto polso (0=non indossato, 1=indossato)
  final bool isReliable; // Se la lettura è affidabile per analisi salute
  final String quality; // Descrizione qualità del segnale
  final DateTime timestamp;

  SpO2Data({
    required this.value,
    required this.piValue,
    required this.gesture,
    required this.onWrist,
    required this.isReliable,
    required this.quality,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  // Getter di compatibilità con l'implementazione precedente
  int? get spo2Value => value;
  bool get correctWristPosture => gesture == 1;
  int get signalQuality => piValue;
  bool get isWearing => onWrist == 1;

  String get signalQualityDescription {
    if (piValue == 0) return 'Nessun Segnale';
    if (piValue < 8) return 'Segnale Debole';
    if (piValue < 15) return 'Segnale Buono';
    return 'Segnale Eccellente';
  }

  bool get isValidMeasurement => value >= 70 && value <= 100 && isReliable;
  bool get isDeviceReady => isWearing && correctWristPosture && piValue >= 8;

  /// Determina se questa lettura dovrebbe triggerare un allarme di salute
  bool get shouldTriggerHealthAlert => isReliable && value < 95;

  /// Determina se questa lettura è critica (richiede attenzione immediata)
  bool get isCritical => isReliable && value < 90;

  @override
  String toString() {
    String reliabilityFlag = isReliable ? '✓' : '⚠';
    return 'SpO2: $value% $reliabilityFlag, PI:$piValue, ${isWearing ? 'Indossato' : 'Non Indossato'}, $quality';
  }

  /// Conversione a mappa per serializzazione
  Map<String, dynamic> toMap() {
    return {
      'value': value,
      'piValue': piValue,
      'gesture': gesture,
      'onWrist': onWrist,
      'isReliable': isReliable,
      'quality': quality,
      'timestamp': timestamp.millisecondsSinceEpoch,
    };
  }

  /// Creazione da mappa per deserializzazione
  factory SpO2Data.fromMap(Map<String, dynamic> map) {
    return SpO2Data(
      value: map['value'] ?? 0,
      piValue: map['piValue'] ?? 0,
      gesture: map['gesture'] ?? 0,
      onWrist: map['onWrist'] ?? 0,
      isReliable: map['isReliable'] ?? false,
      quality: map['quality'] ?? '',
      timestamp: DateTime.fromMillisecondsSinceEpoch(map['timestamp'] ?? 0),
    );
  }
}
