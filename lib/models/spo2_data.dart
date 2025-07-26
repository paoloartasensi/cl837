class SpO2Data {
  final int status; // Se la lettura è affidabile per analisi salute
  final int value; // Valore SpO2 0-100%
  final int gesture; // Postura polso (0=errata, 1=corretta)
  final int piValue; // Perfusion Index 0-255
  final int onWrist; // Contatto polso (0=non indossato, 1=indossato)
  final DateTime timestamp;

  SpO2Data({
    required this.status,
    required this.value,
    required this.gesture,
    required this.piValue,
    required this.onWrist,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  // Getter di compatibilità con l'implementazione precedente
  bool get isReliable => status == 1;
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
    return 'SpO2: $value% $reliabilityFlag, PI:$piValue, ${isWearing ? 'Indossato' : 'Non Indossato'}, Postura: ${correctWristPosture ? 'Corretta' : 'Errata'}, Timestamp: ${timestamp.toIso8601String()}';
  }

  /// Visualizzazione dettagliata del valore SpO2 per debugging
  String toDetailedString() {
    String valueInterpretation = '';
    String alertColor = '';
    
    if (value == 0) {
      valueInterpretation = 'Misurazione in corso';
      alertColor = '⚪';
    } else if (value >= 98) {
      valueInterpretation = 'ECCELLENTE - Ossigenazione ottimale';
      alertColor = '💚';
    } else if (value >= 95) {
      valueInterpretation = 'NORMALE - Ossigenazione buona';
      alertColor = '💚';
    } else if (value >= 90) {
      valueInterpretation = 'BASSA - Possibile ipossiemia lieve';
      alertColor = '🟡';
    } else if (value > 0) {
      valueInterpretation = 'CRITICA - Ipossiemia severa';
      alertColor = '🔴';
    }
    
    return '''
🩸 ===== DETTAGLI SATURAZIONE OSSIGENO =====
$alertColor SpO2 VALUE: $value%
🏥 INTERPRETAZIONE: $valueInterpretation
📊 STATUS: ${isReliable ? "AFFIDABILE" : "NON AFFIDABILE"}
⌚ INDOSSATO: ${isWearing ? "SÌ" : "NO"}
🤚 POSTURA: ${correctWristPosture ? "CORRETTA" : "ERRATA"}
📶 SEGNALE PI: $piValue ($signalQualityDescription)
⏰ TIMESTAMP: ${timestamp.toIso8601String()}
✅ VALIDO: ${isValidMeasurement ? "SÌ" : "NO"}
🚨 CRITICO: ${isCritical ? "SÌ" : "NO"}
🏃 DEVICE READY: ${isDeviceReady ? "SÌ" : "NO"}
=============================================''';
  }

  /// Conversione a mappa per serializzazione
  Map<String, dynamic> toMap() {
    return {
      'value': value,
      'piValue': piValue,
      'gesture': gesture,
      'onWrist': onWrist,
      'status': status,
      'timestamp': timestamp.millisecondsSinceEpoch,
    };
  }

  /// Creazione da mappa per deserializzazione
  factory SpO2Data.fromMap(Map<String, dynamic> map) {
    return SpO2Data(
      status: map['status'] ?? 0,
      value: map['value'] ?? 0,
      piValue: map['piValue'] ?? 0,
      gesture: map['gesture'] ?? 0,
      onWrist: map['onWrist'] ?? 0,
      timestamp: DateTime.fromMillisecondsSinceEpoch(map['timestamp'] ?? 0),
    );
  }
}
