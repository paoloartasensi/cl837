/// Configurazione completa per il controllo della frequenza cardiaca
/// Supporta entrambe le modalità di allarme: basata sull'età e limiti manuali
class HeartRateConfig {
  // Valori di configurazione base
  final int minHeartRate;      // Limite minimo HR
  final int maxHeartRate;      // Limite massimo HR  
  final int goalHeartRate;     // Obiettivo HR target
  
  // Modalità di allarme
  final HeartRateAlarmMode alarmMode;
  final bool alarmEnabled;     // Allarme attivo/disattivo
  
  // Configurazione per allarme basato sull'età
  final int? userAge;          // Età utente per calcolo automatico
  final double? targetZoneMin; // % minima della zona target (es. 0.6 = 60%)
  final double? targetZoneMax; // % massima della zona target (es. 0.8 = 80%)
  
  // Timestamp dell'ultima configurazione
  final DateTime lastUpdated;
  
  HeartRateConfig({
    required this.minHeartRate,
    required this.maxHeartRate,
    required this.goalHeartRate,
    this.alarmMode = HeartRateAlarmMode.manual,
    this.alarmEnabled = false,
    this.userAge,
    this.targetZoneMin = 0.6,  // 60% default
    this.targetZoneMax = 0.8,  // 80% default  
    DateTime? lastUpdated,
  }) : lastUpdated = lastUpdated ?? DateTime.now();
  
  /// Calcola i limiti HR basati sull'età (formula: 220 - età)
  static HeartRateConfig fromAge(int age, {
    int? goalHeartRate,
    bool alarmEnabled = true,
    double targetZoneMin = 0.6,
    double targetZoneMax = 0.8,
  }) {
    final maxHR = 220 - age;
    final minHR = (maxHR * targetZoneMin).round();
    final maxTargetHR = (maxHR * targetZoneMax).round();
    final goal = goalHeartRate ?? ((minHR + maxTargetHR) / 2).round();
    
    return HeartRateConfig(
      minHeartRate: minHR,
      maxHeartRate: maxTargetHR,
      goalHeartRate: goal,
      alarmMode: HeartRateAlarmMode.ageBased,
      alarmEnabled: alarmEnabled,
      userAge: age,
      targetZoneMin: targetZoneMin,
      targetZoneMax: targetZoneMax,
    );
  }
  
  /// Crea configurazione con limiti manuali
  static HeartRateConfig manual({
    required int minHeartRate,
    required int maxHeartRate,
    required int goalHeartRate,
    bool alarmEnabled = true,
  }) {
    return HeartRateConfig(
      minHeartRate: minHeartRate,
      maxHeartRate: maxHeartRate,
      goalHeartRate: goalHeartRate,
      alarmMode: HeartRateAlarmMode.manual,
      alarmEnabled: alarmEnabled,
    );
  }
  
  /// Verifica se l'HR è nella zona target
  bool isInTargetZone(int currentHR) {
    return currentHR >= minHeartRate && currentHR <= maxHeartRate;
  }
  
  /// Verifica se l'HR è troppo basso
  bool isTooLow(int currentHR) {
    return currentHR < minHeartRate;
  }
  
  /// Verifica se l'HR è troppo alto
  bool isTooHigh(int currentHR) {
    return currentHR > maxHeartRate;
  }
  
  /// Copia la configurazione con modifiche
  HeartRateConfig copyWith({
    int? minHeartRate,
    int? maxHeartRate,
    int? goalHeartRate,
    HeartRateAlarmMode? alarmMode,
    bool? alarmEnabled,
    int? userAge,
    double? targetZoneMin,
    double? targetZoneMax,
  }) {
    return HeartRateConfig(
      minHeartRate: minHeartRate ?? this.minHeartRate,
      maxHeartRate: maxHeartRate ?? this.maxHeartRate,
      goalHeartRate: goalHeartRate ?? this.goalHeartRate,
      alarmMode: alarmMode ?? this.alarmMode,
      alarmEnabled: alarmEnabled ?? this.alarmEnabled,
      userAge: userAge ?? this.userAge,
      targetZoneMin: targetZoneMin ?? this.targetZoneMin,
      targetZoneMax: targetZoneMax ?? this.targetZoneMax,
    );
  }
  
  Map<String, dynamic> toMap() {
    return {
      'minHeartRate': minHeartRate,
      'maxHeartRate': maxHeartRate,
      'goalHeartRate': goalHeartRate,
      'alarmMode': alarmMode.name,
      'alarmEnabled': alarmEnabled,
      'userAge': userAge,
      'targetZoneMin': targetZoneMin,
      'targetZoneMax': targetZoneMax,
      'lastUpdated': lastUpdated.toIso8601String(),
    };
  }
  
  factory HeartRateConfig.fromMap(Map<String, dynamic> map) {
    return HeartRateConfig(
      minHeartRate: map['minHeartRate'],
      maxHeartRate: map['maxHeartRate'],
      goalHeartRate: map['goalHeartRate'],
      alarmMode: HeartRateAlarmMode.values.firstWhere(
        (mode) => mode.name == map['alarmMode'],
        orElse: () => HeartRateAlarmMode.manual,
      ),
      alarmEnabled: map['alarmEnabled'] ?? false,
      userAge: map['userAge'],
      targetZoneMin: map['targetZoneMin']?.toDouble(),
      targetZoneMax: map['targetZoneMax']?.toDouble(),
      lastUpdated: DateTime.parse(map['lastUpdated']),
    );
  }
  
  @override
  String toString() {
    final buffer = StringBuffer();
    buffer.write('HR Config: ');
    buffer.write('$minHeartRate-$maxHeartRate bpm, ');
    buffer.write('Goal: $goalHeartRate bpm, ');
    buffer.write('Mode: ${alarmMode.displayName}, ');
    buffer.write('Alarm: ${alarmEnabled ? "ON" : "OFF"}');
    
    if (alarmMode == HeartRateAlarmMode.ageBased && userAge != null) {
      buffer.write(', Age: $userAge');
    }
    
    return buffer.toString();
  }
}

/// Modalità di allarme per la frequenza cardiaca
enum HeartRateAlarmMode {
  /// Allarme basato sull'età dell'utente (calcolo automatico)
  ageBased,
  
  /// Allarme con limiti manuali impostati dall'utente
  manual;
  
  /// Nome visualizzabile per l'UI
  String get displayName {
    switch (this) {
      case HeartRateAlarmMode.ageBased:
        return 'Age-Based';
      case HeartRateAlarmMode.manual:
        return 'Manual Limits';
    }
  }
  
  /// Descrizione completa per l'UI
  String get description {
    switch (this) {
      case HeartRateAlarmMode.ageBased:
        return 'Automatic limits based on user age (220 - age formula)';
      case HeartRateAlarmMode.manual:
        return 'Custom min/max limits set manually';
    }
  }
}

/// Stato corrente dell'HR con informazioni di allarme
class HeartRateStatus {
  final int currentHeartRate;
  final HeartRateConfig config;
  final HeartRateAlarmState alarmState;
  final DateTime timestamp;
  
  HeartRateStatus({
    required this.currentHeartRate,
    required this.config,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now(),
       alarmState = _calculateAlarmState(currentHeartRate, config);
  
  static HeartRateAlarmState _calculateAlarmState(int hr, HeartRateConfig config) {
    if (!config.alarmEnabled) return HeartRateAlarmState.disabled;
    if (hr < config.minHeartRate) return HeartRateAlarmState.tooLow;
    if (hr > config.maxHeartRate) return HeartRateAlarmState.tooHigh;
    return HeartRateAlarmState.normal;
  }
  
  bool get shouldTriggerAlarm {
    return config.alarmEnabled && 
           (alarmState == HeartRateAlarmState.tooLow || 
            alarmState == HeartRateAlarmState.tooHigh);
  }
  
  @override
  String toString() {
    return 'HR: $currentHeartRate bpm, State: ${alarmState.displayName}, Config: $config';
  }
}

/// Stati possibili dell'allarme HR
enum HeartRateAlarmState {
  disabled,    // Allarme disabilitato
  normal,      // HR nella zona normale
  tooLow,      // HR troppo basso
  tooHigh;     // HR troppo alto
  
  String get displayName {
    switch (this) {
      case HeartRateAlarmState.disabled:
        return 'Disabled';
      case HeartRateAlarmState.normal:
        return 'Normal';
      case HeartRateAlarmState.tooLow:
        return 'Too Low';
      case HeartRateAlarmState.tooHigh:
        return 'Too High';
    }
  }
  
  /// Colore suggerito per l'UI
  String get colorHex {
    switch (this) {
      case HeartRateAlarmState.disabled:
        return '#9E9E9E';  // Grigio
      case HeartRateAlarmState.normal:
        return '#4CAF50';  // Verde
      case HeartRateAlarmState.tooLow:
        return '#2196F3';  // Blu
      case HeartRateAlarmState.tooHigh:
        return '#F44336';  // Rosso
    }
  }
}
