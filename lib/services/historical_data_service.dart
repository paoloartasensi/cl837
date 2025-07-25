// ignore_for_file: empty_catches

import 'dart:typed_data';

import 'ble_protocol/official_commands.dart';
import 'data_processors/enhanced_historical_data_processor.dart';

/// Servizio ottimizzato per il recupero dei dati storici dal dispositivo CL837
/// Utilizza l'algoritmo checksum Java corretto per massima affidabilità
/// Enhanced con parser reverse-engineered dall'app originale
class HistoricalDataService {
  final Function(List<int>) _sendCommand;
  
  // Cache per evitare richieste duplicate
  final Map<String, DateTime> _lastRequestTime = {};
  final Duration _minRequestInterval = const Duration(seconds: 30);
  
  HistoricalDataService(this._sendCommand);

  /// Recupera l'elenco completo dei record HR disponibili
  /// Utilizza comando 0x21 (getHistoryOfHRRecord)
  Future<void> requestHRRecordList() async {
    if (_shouldThrottleRequest('hr_record_list')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfHRRecord();
      
      
      await _sendCommand(command);
      _updateRequestTime('hr_record_list');
      
    } catch (e) {
    }
  }

  /// Recupera dati HR dettagliati per un timestamp specifico
  /// Utilizza comando 0x22 (getHistoryOfHRData)
  Future<void> requestHRDetailData(int timestamp) async {
    String key = 'hr_detail_$timestamp';
    if (_shouldThrottleRequest(key)) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfHRData(timestamp);
      
      
      await _sendCommand(command);
      _updateRequestTime(key);
      
    } catch (e) {
    }
  }

  /// Recupera dati HR estesi con intervalli RR per analisi HRV
  /// Utilizza comando 0x23 (getHistoryOfHRDataExtended)
  Future<void> requestHRExtendedData(int timestamp) async {
    String key = 'hr_extended_$timestamp';
    if (_shouldThrottleRequest(key)) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfHRDataExtended(timestamp);
      
      
      await _sendCommand(command);
      _updateRequestTime(key);
      
    } catch (e) {
    }
  }

  /// Recupera l'elenco dei record RR disponibili
  /// Utilizza comando 0x24 (getHistoryOfRRRecord)
  Future<void> requestRRRecordList() async {
    if (_shouldThrottleRequest('rr_record_list')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfRRRecord();
      
      
      await _sendCommand(command);
      _updateRequestTime('rr_record_list');
      
    } catch (e) {
    }
  }

  /// Recupera dati RR dettagliati per un timestamp specifico
  /// Utilizza comando 0x25 (getHistoryOfRRData)
  Future<void> requestRRDetailData(int timestamp) async {
    String key = 'rr_detail_$timestamp';
    if (_shouldThrottleRequest(key)) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfRRData(timestamp);
      
      
      await _sendCommand(command);
      _updateRequestTime(key);
      
    } catch (e) {
    }
  }

  /// Recupera dati di esercizio/sport storici con parser enhanced
  /// Utilizza comando 0x16 (getHistoryOfSport) con parsing dell'app originale
  Future<void> requestExerciseHistoryEnhanced() async {
    if (_shouldThrottleRequest('exercise_history_enhanced')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfSport();
      
      
      await _sendCommand(command);
      _updateRequestTime('exercise_history_enhanced');
      
    } catch (e) {
    }
  }

  /// Recupera dati di sonno storici con parser enhanced
  /// Utilizza comando 0x05 (getHistoryOfSleep) con parsing dell'app originale
  Future<void> requestSleepHistoryEnhanced() async {
    if (_shouldThrottleRequest('sleep_history_enhanced')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfSleep();
      
      
      await _sendCommand(command);
      _updateRequestTime('sleep_history_enhanced');
      
    } catch (e) {
    }
  }

  /// Recupera passi intervallari con parser enhanced
  /// Utilizza comando 0x40 (getIntervalSteps) con parsing dell'app originale
  Future<void> requestIntervalStepsEnhanced() async {
    if (_shouldThrottleRequest('interval_steps_enhanced')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getIntervalSteps();
      
      
      await _sendCommand(command);
      _updateRequestTime('interval_steps_enhanced');
      
    } catch (e) {
    }
  }

  /// Parse i dati ricevuti usando i parser enhanced (chiamato dai processori dati)
  static List<ExerciseHistoryEntry> parseReceivedExerciseData(Uint8List data) {
    return EnhancedHistoricalDataProcessor.parseSportHistory(data);
  }

  static List<SleepHistoryEntry> parseReceivedSleepData(Uint8List data) {
    return EnhancedHistoricalDataProcessor.parseSleepHistory(data);
  }

  static List<IntervalStepEntry> parseReceivedIntervalStepsData(Uint8List data) {
    return EnhancedHistoricalDataProcessor.parseIntervalSteps(data);
  }

  static List<HeartRateHistoryEntry> parseReceivedHRHistoryList(Uint8List data) {
    return EnhancedHistoricalDataProcessor.parseHeartRateHistory(data);
  }

  /// Recupera dati di sonno storici
  /// Utilizza comando 0x05 (getHistoryOfSleep)
  Future<void> requestSleepHistory() async {
    if (_shouldThrottleRequest('sleep_history')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfSleep();
      
      
      await _sendCommand(command);
      _updateRequestTime('sleep_history');
      
    } catch (e) {
    }
  }

  /// Recupera passi intervallari
  /// Utilizza comando 0x40 (getIntervalSteps)
  Future<void> requestIntervalSteps() async {
    if (_shouldThrottleRequest('interval_steps')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getIntervalSteps();
      
      
      await _sendCommand(command);
      _updateRequestTime('interval_steps');
      
    } catch (e) {
    }
  }

  /// Recupera record di singolo tap
  /// Utilizza comando 0x42 (getSingleTapRecords)
  Future<void> requestSingleTapRecords() async {
    if (_shouldThrottleRequest('single_tap_records')) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getSingleTapRecords();
      
      
      await _sendCommand(command);
      _updateRequestTime('single_tap_records');
      
    } catch (e) {
    }
  }

  /// Recupera record singolo per timestamp specifico
  /// Utilizza comando 0x49 (getHistoryOfSingleRecord)
  Future<void> requestSingleRecord(int timestamp) async {
    String key = 'single_record_$timestamp';
    if (_shouldThrottleRequest(key)) {
      return;
    }
    
    try {
      var command = OfficialChileafCommands.getHistoryOfSingleRecord(timestamp);
      
      
      await _sendCommand(command);
      _updateRequestTime(key);
      
    } catch (e) {
    }
  }

  /// Workflow completo per recuperare tutti i dati HR di un periodo
  Future<void> requestCompleteHRHistory() async {
    
    // 1. Prima richiedi la lista dei record disponibili
    await requestHRRecordList();
    
    // Attendi un po' prima delle richieste successive
    await Future.delayed(const Duration(seconds: 2));
    
    // 2. Le richieste di dettaglio verranno fatte quando riceveremo la lista
  }

  /// Workflow completo per recuperare tutti i dati RR/HRV di un periodo
  Future<void> requestCompleteRRHistory() async {
    
    // 1. Prima richiedi la lista dei record RR disponibili
    await requestRRRecordList();
    
    // Attendi un po' prima delle richieste successive
    await Future.delayed(const Duration(seconds: 2));
    
    // 2. Le richieste di dettaglio verranno fatte quando riceveremo la lista
  }

  /// Workflow per recuperare tutti i tipi di dati storici con parser enhanced
  Future<void> requestAllHistoricalDataEnhanced() async {
    
    try {
      // Recupera dati di base con parser enhanced
      await requestExerciseHistoryEnhanced();
      await Future.delayed(const Duration(seconds: 1));
      
      await requestSleepHistoryEnhanced();
      await Future.delayed(const Duration(seconds: 1));
      
      await requestIntervalStepsEnhanced();
      await Future.delayed(const Duration(seconds: 1));
      
      await requestSingleTapRecords();
      await Future.delayed(const Duration(seconds: 1));
      
      // Recupera dati HR completi
      await requestCompleteHRHistory();
      await Future.delayed(const Duration(seconds: 3));
      
      // Recupera dati RR/HRV completi
      await requestCompleteRRHistory();
      
    } catch (e) {
    }
  }

  /// Verifica se una richiesta dovrebbe essere throttled
  bool _shouldThrottleRequest(String key) {
    final lastTime = _lastRequestTime[key];
    if (lastTime == null) return false;
    
    return DateTime.now().difference(lastTime) < _minRequestInterval;
  }

  /// Aggiorna il timestamp dell'ultima richiesta
  void _updateRequestTime(String key) {
    _lastRequestTime[key] = DateTime.now();
  }

  /// Pulisce la cache delle richieste
  void clearRequestCache() {
    _lastRequestTime.clear();
  }

  /// Formatta timestamp in data leggibile
  static String formatTimestamp(int timestamp) {
    try {
      final date = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);
      return '${date.day}/${date.month}/${date.year} ${date.hour}:${date.minute.toString().padLeft(2, '0')}';
    } catch (e) {
      return 'Invalid timestamp: $timestamp';
    }
  }

  /// Verifica se un timestamp è valido (tra 2020 e 2050)
  static bool isValidTimestamp(int timestamp) {
    final year2020 = DateTime(2020).millisecondsSinceEpoch ~/ 1000;
    final year2050 = DateTime(2050).millisecondsSinceEpoch ~/ 1000;
    
    return timestamp >= year2020 && timestamp <= year2050;
  }
}
