import 'dart:async';
import 'package:flutter/foundation.dart';
import '../../models/sports_data.dart';

/// Processore specializzato per i dati sportivi
/// Gestisce l'analisi e l'estrazione dei dati di attività sportiva dai pacchetti BLE
class SportsProcessor {
  final StreamController<SportsData> _sportsDataController = StreamController<SportsData>.broadcast();
  
  // Debouncing per ridurre log ripetitivi
  SportsData? _lastSportsData;
  DateTime? _lastLogTime;
  static const Duration _logInterval = Duration(seconds: 15); // Log ogni 15 secondi max
  
  /// Stream dei dati sportivi processati
  Stream<SportsData> get sportsDataStream => _sportsDataController.stream;

  /// Processa i dati sportivi dal comando 0x15
  void processSportsData(List<int> data) {
    // Real-time sports data notification-0x15 from SDK:
    // Bytes 3-5: Steps (3-byte value)
    // Bytes 6-8: Distance in cm (3-byte value)  
    // Bytes 9-11: Calories in 0.1 kcal units (3-byte value)
    
    if (data.length < 12) {
      debugPrint('Sports data too short: ${data.length} bytes');
      return;
    }

    try {
      final steps = (data[3] << 16) | (data[4] << 8) | data[5];
      final distanceCm = ((data[6] << 16) | (data[7] << 8) | data[8]).toDouble();
      final caloriesRaw = (data[9] << 16) | (data[10] << 8) | data[11];
      final caloriesKcal = caloriesRaw * 0.1; // Convert from 0.1 kcal units

      final sportsData = SportsData(
        steps: steps,
        distanceCm: distanceCm,
        caloriesKcal: caloriesKcal,
      );

      _sportsDataController.add(sportsData);
      
      // Log solo se i valori sono cambiati significativamente o è passato tempo
      final now = DateTime.now();
      final shouldLog = _lastLogTime == null || now.difference(_lastLogTime!) > _logInterval;
      
      final hasSignificantChange = _lastSportsData == null ||
          (steps - _lastSportsData!.steps).abs() > 5 || // Almeno 5 passi di differenza
          (caloriesKcal - _lastSportsData!.caloriesKcal).abs() > 1.0; // Almeno 1 kcal di differenza
          
      if (hasSignificantChange || shouldLog) {
        debugPrint('Sports data: steps=$steps, distance=${distanceCm}cm, calories=${caloriesKcal}kcal');
        _lastSportsData = sportsData;
        _lastLogTime = now;
      }
    } catch (e) {
      debugPrint('Error parsing sports data: $e');
    }
  }

  void dispose() {
    _sportsDataController.close();
  }
}
