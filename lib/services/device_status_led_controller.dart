/// Controller per il LED di status esterno del dispositivo CL837
/// Questo LED (non quello SpO2) cambia colore in base agli stati del dispositivo
/// Basato sul reverse engineering degli SDK CL831_INFO + XFITNESS2

import 'dart:async';
import 'package:flutter/foundation.dart';
import '../services/ble_protocol/official_commands_complete.dart';

class DeviceStatusLedController {
  final Future<void> Function(List<int>) _sendCommand;
  
  Timer? _blinkTimer;
  bool _isBlinking = false;
  
  DeviceStatusLedController({
    required Future<void> Function(List<int>) sendCommand,
  }) : _sendCommand = sendCommand;

  /// 🟢 LED Verde Lampeggiante - Attiva sensore 3D con frequenza alta
  /// Questo fa lampeggiare il LED di status verde sul dispositivo
  Future<void> setGreenBlinkingLED() async {
    debugPrint('🟢 Attivazione LED Verde Lampeggiante...');
    
    // Step 1: Abilita il sensore 3D
    await _sendCommand(OfficialChileafCommands.set3DEnabled(true));
    await Future.delayed(const Duration(milliseconds: 200));
    
    // Step 2: Imposta frequenza alta per farlo lampeggiare
    await _sendCommand(OfficialChileafCommands.set3DFrequency(4)); // 400HZ = max freq
    
    debugPrint('✅ LED Verde Lampeggiante attivo! (3D Sensor abilitato a 400HZ)');
  }

  /// 🟡 LED Giallo Lampeggiante - Stato intermedio con frequenza media
  /// Questo fa lampeggiare il LED di status giallo sul dispositivo
  Future<void> setYellowBlinkingLED() async {
    debugPrint('🟡 Attivazione LED Giallo Lampeggiante...');
    
    // Step 1: Abilita il sensore 3D
    await _sendCommand(OfficialChileafCommands.set3DEnabled(true));
    await Future.delayed(const Duration(milliseconds: 200));
    
    // Step 2: Imposta frequenza media per colore giallo
    await _sendCommand(OfficialChileafCommands.set3DFrequency(2)); // 100HZ = freq media
    
    debugPrint('✅ LED Giallo Lampeggiante attivo! (3D Sensor abilitato a 100HZ)');
  }

  /// 🔴 LED Rosso Fisso - Attiva HR alarm per stato di allarme
  /// Questo accende il LED di status rosso fisso sul dispositivo
  Future<void> setRedSolidLED() async {
    debugPrint('🔴 Attivazione LED Rosso Fisso...');
    
    // Attiva l'allarme HR per stato rosso
    await _sendCommand(OfficialChileafCommands.setHeartRateAlarm(true));
    
    debugPrint('✅ LED Rosso Fisso attivo! (HR Alarm abilitato)');
  }

  /// 🔵 LED Blu Lampeggiante - Combina sensori per stato speciale
  /// Questo fa lampeggiare il LED di status blu sul dispositivo
  Future<void> setBlueBlinkingLED() async {
    debugPrint('🔵 Attivazione LED Blu Lampeggiante...');
    
    // Step 1: Abilita sensore 3D
    await _sendCommand(OfficialChileafCommands.set3DEnabled(true));
    await Future.delayed(const Duration(milliseconds: 200));
    
    // Step 2: Imposta frequenza bassa per blu
    await _sendCommand(OfficialChileafCommands.set3DFrequency(0)); // 25HZ = freq bassa
    await Future.delayed(const Duration(milliseconds: 200));
    
    // Step 3: Abilita anche allarme HR per effetto combinato
    await _sendCommand(OfficialChileafCommands.setHeartRateAlarm(true));
    
    debugPrint('✅ LED Blu Lampeggiante attivo! (3D + HR Alarm combinati)');
  }

  /// ⚪ Spegni tutti i LED di status - Ripristina stato normale
  Future<void> turnOffAllStatusLEDs() async {
    debugPrint('⚪ Spegnimento tutti i LED di status...');
    
    // Step 1: Disabilita sensore 3D
    await _sendCommand(OfficialChileafCommands.set3DEnabled(false));
    await Future.delayed(const Duration(milliseconds: 200));
    
    // Step 2: Disabilita allarme HR
    await _sendCommand(OfficialChileafCommands.setHeartRateAlarm(false));
    await Future.delayed(const Duration(milliseconds: 200));
    
    // Step 3: Reset frequenza a default
    await _sendCommand(OfficialChileafCommands.set3DFrequency(2)); // 100HZ default
    
    _stopBlinkTimer();
    
    debugPrint('✅ Tutti i LED di status spenti! Dispositivo in stato normale.');
  }

  /// 🟢🔴 LED Alternato Verde-Rosso - Pattern speciale per test
  Future<void> setAlternatingGreenRedLED({Duration interval = const Duration(seconds: 2)}) async {
    debugPrint('🟢🔴 Attivazione LED Alternato Verde-Rosso...');
    
    _stopBlinkTimer();
    _isBlinking = true;
    
    bool isGreen = true;
    _blinkTimer = Timer.periodic(interval, (_) async {
      if (!_isBlinking) return;
      
      try {
        if (isGreen) {
          await setGreenBlinkingLED();
          debugPrint('🟢 Fase Verde');
        } else {
          await setRedSolidLED();
          debugPrint('🔴 Fase Rossa');
        }
        isGreen = !isGreen;
      } catch (e) {
        debugPrint('❌ Errore LED alternato: $e');
      }
    });
    
    debugPrint('✅ LED Alternato attivo! Intervallo: ${interval.inSeconds}s');
  }

  /// 🌈 Pattern LED Rainbow - Cicla tutti i colori
  Future<void> setRainbowLEDPattern({Duration interval = const Duration(seconds: 3)}) async {
    debugPrint('🌈 Attivazione Pattern Rainbow LED...');
    
    _stopBlinkTimer();
    _isBlinking = true;
    
    final patterns = [
      () => setGreenBlinkingLED(),
      () => setYellowBlinkingLED(), 
      () => setRedSolidLED(),
      () => setBlueBlinkingLED(),
    ];
    
    int patternIndex = 0;
    _blinkTimer = Timer.periodic(interval, (_) async {
      if (!_isBlinking) return;
      
      try {
        await patterns[patternIndex]();
        debugPrint('🌈 Pattern ${patternIndex + 1}/4');
        patternIndex = (patternIndex + 1) % patterns.length;
      } catch (e) {
        debugPrint('❌ Errore pattern rainbow: $e');
      }
    });
    
    debugPrint('✅ Pattern Rainbow attivo! Intervallo: ${interval.inSeconds}s');
  }

  /// 🛑 Ferma tutti i pattern di lampeggio
  void stopAllPatterns() {
    debugPrint('🛑 Fermando tutti i pattern LED...');
    _stopBlinkTimer();
  }

  void _stopBlinkTimer() {
    _blinkTimer?.cancel();
    _blinkTimer = null;
    _isBlinking = false;
  }

  /// Pulisci le risorse
  void dispose() {
    _stopBlinkTimer();
  }
}

/// Tipi di LED status disponibili
enum DeviceStatusLEDType {
  off,           // ⚪ Spento
  greenBlink,    // 🟢 Verde lampeggiante
  yellowBlink,   // 🟡 Giallo lampeggiante  
  redSolid,      // 🔴 Rosso fisso
  blueBlink,     // 🔵 Blu lampeggiante
  alternating,   // 🟢🔴 Alternato
  rainbow,       // 🌈 Pattern arcobaleno
}

/// Informazioni su stato LED
class StatusLEDInfo {
  final DeviceStatusLEDType type;
  final bool isActive;
  final String description;
  final Duration? interval;
  
  StatusLEDInfo({
    required this.type,
    required this.isActive,
    required this.description,
    this.interval,
  });
  
  @override
  String toString() => 'StatusLED($type, active: $isActive, desc: $description)';
}
