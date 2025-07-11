import 'dart:async';
import 'package:flutter/foundation.dart';
import '../../models/hrv_data.dart';
import '../../models/heart_rate_data.dart';

/// Processore specializzato per i dati sanitari estesi
/// Gestisce l'analisi dei dati sanitari complessi e HRV
class HealthProcessor {
  final StreamController<HRVData> _hrvDataController = StreamController<HRVData>.broadcast();
  
  // RR intervals buffer for HRV calculation
  List<double> _rrIntervalsBuffer = [];
  static const int _maxRRIntervals = 30; // Store last 30 intervals for HRV
  
  /// Stream dei dati HRV processati
  Stream<HRVData> get hrvDataStream => _hrvDataController.stream;

  /// Processa i dati sanitari estesi dal comando 0x75
  void processHealthData(List<int> data) {
    // Extended health data from command 0x75 (discovered from logs)
    // 23-byte packets with health metrics
    if (data.length < 10) {
      debugPrint('Health data too short: ${data.length} bytes');
      return;
    }

    try {
      debugPrint('🏥 Extended health data (${data.length} bytes): ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // This could contain additional metrics like:
      // - Detailed heart rate variability
      // - Sleep analysis data
      // - Stress levels
      // - Additional sensor readings
      // - REAL SpO2 DATA (discovered)
      
      // For now, just log for analysis
      // Future: parse specific health metrics based on protocol documentation
    } catch (e) {
      debugPrint('Error parsing health data: $e');
    }
  }

  /// Processa gli intervalli RR per il calcolo HRV
  void processRRIntervalsForHRV(HeartRateData heartRateData) {
    if (heartRateData.rrIntervals != null && heartRateData.rrIntervals!.isNotEmpty) {
      _rrIntervalsBuffer.addAll(heartRateData.rrIntervals!);
      
      // Keep only the most recent intervals
      if (_rrIntervalsBuffer.length > _maxRRIntervals) {
        _rrIntervalsBuffer = _rrIntervalsBuffer.sublist(_rrIntervalsBuffer.length - _maxRRIntervals);
      }

      // Calculate HRV if we have enough data points (at least 10 intervals)
      if (_rrIntervalsBuffer.length >= 10) {
        final hrvData = HRVData(rrIntervals: List.from(_rrIntervalsBuffer));
        _hrvDataController.add(hrvData);
        debugPrint('HRV calculated from ${_rrIntervalsBuffer.length} RR intervals');
      }
    }
  }

  /// Ottiene statistiche del buffer RR
  Map<String, dynamic> getRRBufferStats() {
    return {
      'bufferSize': _rrIntervalsBuffer.length,
      'maxSize': _maxRRIntervals,
      'canCalculateHRV': _rrIntervalsBuffer.length >= 10,
    };
  }

  /// Svuota il buffer RR
  void clearRRBuffer() {
    _rrIntervalsBuffer.clear();
    debugPrint('RR intervals buffer cleared');
  }

  void dispose() {
    _hrvDataController.close();
    _rrIntervalsBuffer.clear();
  }
}
