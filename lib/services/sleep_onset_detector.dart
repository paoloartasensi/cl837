import '../models/historical_data.dart';

/// Rileva automaticamente l'inizio e la fine delle sessioni di sonno
/// Analizza i dati Sleep 0x31 per identificare pattern di addormentamento
class SleepOnsetDetector {
  // Callbacks
  Function(SleepOnsetEvent)? onSleepOnset;
  Function(SleepWakeEvent)? onWakeUp;
  Function(SleepPhaseChange)? onPhaseChange;
  
  // Thresholds
  static const int awakeThreshold = 20;
  static const int lightSleepMax = 20;
  static const int deepSleepPattern = 3; // 3 consecutive 0s
  
  // State tracking
  bool _isCurrentlyAsleep = false;
  DateTime? _sessionStartTime;
  SleepPhase _currentPhase = SleepPhase.awake;
  
  /// Analizza un pacchetto di dati sleep per rilevare eventi
  List<SleepEvent> analyzeSleepData(SleepData31 sleepData) {
    List<SleepEvent> events = [];
    DateTime currentTime = sleepData.timestamp;
    
    int consecutiveZeros = 0;
    
    for (int i = 0; i < sleepData.activityIndices.length; i++) {
      int activityIndex = sleepData.activityIndices[i];
      DateTime indexTime = currentTime.add(Duration(minutes: i * 5));
      
      // Determina fase corrente basata su activity index
      SleepPhase newPhase = _determinePhase(activityIndex, consecutiveZeros);
      
      // Rileva inizio sonno (transizione da awake a sleep)
      if (!_isCurrentlyAsleep && newPhase != SleepPhase.awake) {
        _isCurrentlyAsleep = true;
        _sessionStartTime = indexTime;
        
        SleepOnsetEvent onsetEvent = SleepOnsetEvent(
          timestamp: indexTime,
          initialPhase: newPhase,
          activityIndex: activityIndex,
          confidence: _calculateOnsetConfidence(
            sleepData.activityIndices, 
            i
          ),
        );
        
        events.add(onsetEvent);
        onSleepOnset?.call(onsetEvent);
      }
      
      // Rileva risveglio (transizione da sleep a awake)
      if (_isCurrentlyAsleep && newPhase == SleepPhase.awake) {
        _isCurrentlyAsleep = false;
        
        if (_sessionStartTime != null) {
          Duration sleepDuration = indexTime.difference(_sessionStartTime!);
          
          SleepWakeEvent wakeEvent = SleepWakeEvent(
            timestamp: indexTime,
            sessionStartTime: _sessionStartTime!,
            duration: sleepDuration,
            activityIndex: activityIndex,
          );
          
          events.add(wakeEvent);
          onWakeUp?.call(wakeEvent);
          
          _sessionStartTime = null;
        }
      }
      
      // Rileva cambio di fase (deep ↔ light)
      if (_currentPhase != newPhase) {
        SleepPhaseChange phaseChange = SleepPhaseChange(
          timestamp: indexTime,
          fromPhase: _currentPhase,
          toPhase: newPhase,
          activityIndex: activityIndex,
        );
        
        events.add(phaseChange);
        onPhaseChange?.call(phaseChange);
        
        _currentPhase = newPhase;
      }
      
      // Traccia consecutive zeros per deep sleep
      if (activityIndex == 0) {
        consecutiveZeros++;
      } else {
        consecutiveZeros = 0;
      }
    }
    
    return events;
  }
  
  /// Determina la fase di sonno basata su activity index
  SleepPhase _determinePhase(int activityIndex, int consecutiveZeros) {
    if (activityIndex > awakeThreshold) {
      return SleepPhase.awake;
    } else if (activityIndex == 0 && consecutiveZeros >= deepSleepPattern - 1) {
      return SleepPhase.deepSleep;
    } else if (activityIndex <= lightSleepMax) {
      return SleepPhase.lightSleep;
    }
    return SleepPhase.awake;
  }
  
  /// Calcola confidence score per onset detection
  /// Più bassi sono gli activity indices dopo onset, più alta la confidence
  double _calculateOnsetConfidence(List<int> indices, int onsetIndex) {
    int lookAhead = 6; // Guarda 30 minuti avanti (6 x 5 min)
    int endIndex = (onsetIndex + lookAhead).clamp(0, indices.length);
    
    if (endIndex <= onsetIndex + 1) return 50.0;
    
    List<int> postOnset = indices.sublist(onsetIndex + 1, endIndex);
    double avgActivity = postOnset.reduce((a, b) => a + b) / postOnset.length;
    
    // Score più alto se l'attività media è bassa dopo onset
    double confidence = ((awakeThreshold - avgActivity) / awakeThreshold) * 100;
    return confidence.clamp(0, 100);
  }
  
  /// Resetta lo stato del detector
  void reset() {
    _isCurrentlyAsleep = false;
    _sessionStartTime = null;
    _currentPhase = SleepPhase.awake;
  }
}

/// Enum per le fasi del sonno
enum SleepPhase {
  awake,
  lightSleep,
  deepSleep,
}

extension SleepPhaseExtension on SleepPhase {
  String get displayName {
    switch (this) {
      case SleepPhase.awake:
        return 'Awake';
      case SleepPhase.lightSleep:
        return 'Light Sleep';
      case SleepPhase.deepSleep:
        return 'Deep Sleep';
    }
  }
  
  String get emoji {
    switch (this) {
      case SleepPhase.awake:
        return '😴';
      case SleepPhase.lightSleep:
        return '🌙';
      case SleepPhase.deepSleep:
        return '💤';
    }
  }
}

/// Evento base per sleep tracking
abstract class SleepEvent {
  final DateTime timestamp;
  final String eventType;
  
  SleepEvent({
    required this.timestamp,
    required this.eventType,
  });
  
  @override
  String toString();
}

/// Evento: Inizio del sonno rilevato
class SleepOnsetEvent extends SleepEvent {
  final SleepPhase initialPhase;
  final int activityIndex;
  final double confidence;
  
  SleepOnsetEvent({
    required super.timestamp,
    required this.initialPhase,
    required this.activityIndex,
    required this.confidence,
  }) : super(eventType: 'SLEEP_ONSET');
  
  @override
  String toString() {
    return '🌙 SLEEP ONSET at $timestamp\n'
           '   Initial Phase: ${initialPhase.displayName} ${initialPhase.emoji}\n'
           '   Activity Index: $activityIndex\n'
           '   Confidence: ${confidence.toStringAsFixed(1)}%';
  }
}

/// Evento: Risveglio rilevato
class SleepWakeEvent extends SleepEvent {
  final DateTime sessionStartTime;
  final Duration duration;
  final int activityIndex;
  
  SleepWakeEvent({
    required super.timestamp,
    required this.sessionStartTime,
    required this.duration,
    required this.activityIndex,
  }) : super(eventType: 'WAKE_UP');
  
  @override
  String toString() {
    return '😴 WAKE UP at $timestamp\n'
           '   Session Start: $sessionStartTime\n'
           '   Sleep Duration: ${duration.inHours}h ${duration.inMinutes % 60}m\n'
           '   Activity Index: $activityIndex';
  }
}

/// Evento: Cambio di fase del sonno
class SleepPhaseChange extends SleepEvent {
  final SleepPhase fromPhase;
  final SleepPhase toPhase;
  final int activityIndex;
  
  SleepPhaseChange({
    required super.timestamp,
    required this.fromPhase,
    required this.toPhase,
    required this.activityIndex,
  }) : super(eventType: 'PHASE_CHANGE');
  
  @override
  String toString() {
    return '🔄 PHASE CHANGE at $timestamp\n'
           '   From: ${fromPhase.displayName} ${fromPhase.emoji}\n'
           '   To: ${toPhase.displayName} ${toPhase.emoji}\n'
           '   Activity Index: $activityIndex';
  }
}
