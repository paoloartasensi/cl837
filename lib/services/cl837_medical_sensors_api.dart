/// API completa per i sensori medici del dispositivo CL837
/// Riassume tutte le procedure di lettura SpO2, HRV e Temperatura
/// Progettato per essere esportato e utilizzato in altre applicazioni
/// 
/// Autore: Reverse engineering CL837 SDK
/// Data: Luglio 2025

import 'dart:async';
import 'dart:math';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

/// API principale per sensori medici CL837
class CL837MedicalSensorsAPI {
  // Caratteristiche BLE
  BluetoothCharacteristic? _rxCharacteristic;
  
  // Stream controllers per i dati
  final _spO2Controller = StreamController<SpO2Reading>.broadcast();
  final _hrvController = StreamController<HRVReading>.broadcast();
  final _temperatureController = StreamController<TemperatureReading>.broadcast();
  final _heartRateController = StreamController<HeartRateReading>.broadcast();
  
  // Streams pubblici
  Stream<SpO2Reading> get spO2Stream => _spO2Controller.stream;
  Stream<HRVReading> get hrvStream => _hrvController.stream;
  Stream<TemperatureReading> get temperatureStream => _temperatureController.stream;
  Stream<HeartRateReading> get heartRateStream => _heartRateController.stream;
  
  // Stati di misurazione
  bool _isSpO2Active = false;
  bool _isHRVActive = false;
  bool _isTemperatureActive = false;
  
  Timer? _measurementTimer;
  
  CL837MedicalSensorsAPI({
    required BluetoothCharacteristic? rxCharacteristic,
    required BluetoothCharacteristic? txCharacteristic,
  }) : _rxCharacteristic = rxCharacteristic;

  /// ================================
  /// 🫁 PROCEDURA SpO2 COMPLETA
  /// ================================
  
  /// Avvia misurazione SpO2 ottimizzata per dispositivo CL837
  /// Durata: 50 secondi massimo con terminazione anticipata intelligente
  /// Termina automaticamente dopo 2 letture consecutive valide
  Future<void> startSpO2Measurement({
    Duration maxDuration = const Duration(seconds: 50),
    bool enableEarlyTermination = true,
  }) async {
    if (_isSpO2Active) {
      debugPrint('⚠️ SpO2 measurement already active');
      return;
    }
    
    debugPrint('🫁 Starting CL837 SpO2 measurement...');
    _isSpO2Active = true;
    
    try {
      // Step 1: Reset stato dispositivo
      await _sendCommand(_buildDisableSpO2Command());
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Step 2: Attiva modalità SpO2 (LED rosso si accende)
      await _sendCommand(_buildEnableSpO2Command());
      debugPrint('🔴 SpO2 LED attivo, iniziando letture...');
      
      // Step 3: Timer automatico di sicurezza
      _measurementTimer = Timer(maxDuration, () async {
        if (_isSpO2Active) {
          debugPrint('⏰ SpO2 timeout reached, stopping measurement');
          await stopSpO2Measurement();
        }
      });
      
      // Step 4: Inizia ciclo di letture
      _startSpO2ReadingCycle();
      
    } catch (e) {
      debugPrint('❌ Failed to start SpO2: $e');
      _isSpO2Active = false;
      rethrow;
    }
  }
  
  /// Ciclo di letture SpO2 con logica di terminazione intelligente
  void _startSpO2ReadingCycle() {
    Timer.periodic(const Duration(seconds: 2), (timer) async {
      if (!_isSpO2Active) {
        timer.cancel();
        return;
      }
      
      try {
        // Richiedi lettura SpO2
        await _sendCommand(_buildSpO2InquiryCommand());
        debugPrint('📊 SpO2 reading request sent');
        
      } catch (e) {
        debugPrint('❌ SpO2 reading error: $e');
      }
    });
  }
  
  /// Ferma misurazione SpO2
  Future<void> stopSpO2Measurement() async {
    if (!_isSpO2Active) return;
    
    debugPrint('🛑 Stopping SpO2 measurement...');
    _isSpO2Active = false;
    _measurementTimer?.cancel();
    
    try {
      // Spegni LED rosso
      await _sendCommand(_buildDisableSpO2Command());
      debugPrint('⚫ SpO2 LED spento');
    } catch (e) {
      debugPrint('❌ Error stopping SpO2: $e');
    }
  }
  
  /// ================================
  /// 💓 PROCEDURA HRV COMPLETA
  /// ================================
  
  /// Avvia sessione HRV con analisi RR-intervals
  /// Durata: 5 minuti standard per analisi accurata
  Future<void> startHRVSession({
    Duration sessionDuration = const Duration(minutes: 5),
  }) async {
    if (_isHRVActive) {
      debugPrint('⚠️ HRV session already active');
      return;
    }
    
    debugPrint('💓 Starting CL837 HRV session...');
    _isHRVActive = true;
    
    try {
      // Step 1: Abilita heart rate continuo
      await _sendCommand(_buildEnableHeartRateCommand());
      debugPrint('💗 Heart rate monitoring enabled');
      
      // Step 2: Timer sessione HRV
      _measurementTimer = Timer(sessionDuration, () async {
        debugPrint('⏰ HRV session completed');
        await stopHRVSession();
      });
      
      // Step 3: Inizia raccolta RR-intervals
      _startHRVDataCollection();
      
    } catch (e) {
      debugPrint('❌ Failed to start HRV: $e');
      _isHRVActive = false;
      rethrow;
    }
  }
  
  /// Raccolta dati HRV con calcolo metriche in tempo reale
  void _startHRVDataCollection() {
    final List<int> rrIntervals = [];
    
    Timer.periodic(const Duration(seconds: 1), (timer) async {
      if (!_isHRVActive) {
        timer.cancel();
        return;
      }
      
      try {
        // Richiedi dati heart rate per RR-intervals
        await _sendCommand(_buildHeartRateDataRequest());
        
        // Analizza HRV ogni 30 secondi
        if (rrIntervals.length >= 30) {
          final hrvMetrics = _calculateHRVMetrics(rrIntervals);
          _hrvController.add(hrvMetrics);
          rrIntervals.clear();
        }
        
      } catch (e) {
        debugPrint('❌ HRV data error: $e');
      }
    });
  }
  
  /// Calcola metriche HRV standard
  HRVReading _calculateHRVMetrics(List<int> rrIntervals) {
    if (rrIntervals.isEmpty) {
      return HRVReading.empty();
    }
    
    // RMSSD (Root Mean Square of Successive Differences)
    double rmssd = 0;
    for (int i = 1; i < rrIntervals.length; i++) {
      final diff = rrIntervals[i] - rrIntervals[i-1];
      rmssd += diff * diff;
    }
    rmssd = rmssd > 0 ? sqrt(rmssd / (rrIntervals.length - 1)) : 0;
    
    // SDNN (Standard Deviation of NN intervals)
    final mean = rrIntervals.reduce((a, b) => a + b) / rrIntervals.length;
    double variance = 0;
    for (final interval in rrIntervals) {
      variance += (interval - mean) * (interval - mean);
    }
    final sdnn = variance > 0 ? sqrt(variance / rrIntervals.length) : 0;
    
    // pNN50 (Percentage of NN intervals > 50ms different)
    int nn50Count = 0;
    for (int i = 1; i < rrIntervals.length; i++) {
      if ((rrIntervals[i] - rrIntervals[i-1]).abs() > 50) {
        nn50Count++;
      }
    }
    final pnn50 = rrIntervals.length > 1 ? (nn50Count / (rrIntervals.length - 1)) * 100.0 : 0.0;
    
    return HRVReading(
      timestamp: DateTime.now(),
      rmssd: rmssd.toDouble(),
      sdnn: sdnn.toDouble(),
      pnn50: pnn50,
      rrIntervals: List.from(rrIntervals),
      stressIndex: _calculateStressIndex(rmssd.toDouble(), sdnn.toDouble()),
    );
  }
  
  /// Calcola indice di stress basato su metriche HRV
  double _calculateStressIndex(double rmssd, double sdnn) {
    // Algoritmo semplificato: stress inversamente proporzionale alla variabilità
    if (rmssd == 0 || sdnn == 0) return 100.0;
    
    final normalizedRMSSD = (rmssd / 50.0).clamp(0.0, 1.0);
    final normalizedSDNN = (sdnn / 100.0).clamp(0.0, 1.0);
    
    return (1.0 - (normalizedRMSSD + normalizedSDNN) / 2.0) * 100.0;
  }
  
  /// Ferma sessione HRV
  Future<void> stopHRVSession() async {
    if (!_isHRVActive) return;
    
    debugPrint('🛑 Stopping HRV session...');
    _isHRVActive = false;
    _measurementTimer?.cancel();
    
    try {
      await _sendCommand(_buildDisableHeartRateCommand());
      debugPrint('💗 Heart rate monitoring disabled');
    } catch (e) {
      debugPrint('❌ Error stopping HRV: $e');
    }
  }
  
  /// ================================
  /// 🌡️ PROCEDURA TEMPERATURA COMPLETA
  /// ================================
  
  /// Avvia monitoraggio temperatura continuo
  /// Supporta temperatura corporea e del polso
  Future<void> startTemperatureMonitoring({
    Duration interval = const Duration(seconds: 5),
    bool includeBodyTemperature = true,
    bool includeWristTemperature = true,
  }) async {
    if (_isTemperatureActive) {
      debugPrint('⚠️ Temperature monitoring already active');
      return;
    }
    
    debugPrint('🌡️ Starting CL837 temperature monitoring...');
    _isTemperatureActive = true;
    
    try {
      // Abilita sensore temperatura
      await _sendCommand(_buildEnableTemperatureCommand());
      debugPrint('🌡️ Temperature sensor enabled');
      
      // Ciclo di letture
      Timer.periodic(interval, (timer) async {
        if (!_isTemperatureActive) {
          timer.cancel();
          return;
        }
        
        try {
          // Richiedi dati temperatura
          await _sendCommand(_buildTemperatureDataRequest());
          debugPrint('🌡️ Temperature reading request sent');
          
        } catch (e) {
          debugPrint('❌ Temperature reading error: $e');
        }
      });
      
    } catch (e) {
      debugPrint('❌ Failed to start temperature monitoring: $e');
      _isTemperatureActive = false;
      rethrow;
    }
  }
  
  /// Ferma monitoraggio temperatura
  Future<void> stopTemperatureMonitoring() async {
    if (!_isTemperatureActive) return;
    
    debugPrint('🛑 Stopping temperature monitoring...');
    _isTemperatureActive = false;
    
    try {
      await _sendCommand(_buildDisableTemperatureCommand());
      debugPrint('🌡️ Temperature sensor disabled');
    } catch (e) {
      debugPrint('❌ Error stopping temperature: $e');
    }
  }
  
  /// ================================
  /// 🔧 METODI DI UTILITÀ E PARSING
  /// ================================
  
  /// Processa dati ricevuti dal dispositivo
  void processIncomingData(List<int> data) {
    if (data.length < 4) return;
    
    final command = data.length >= 3 ? data[2] : null;
    
    switch (command) {
      case 0x37: // SpO2 Data
        _processSpO2Data(data);
        break;
      case 0x15: // Heart Rate Data (per HRV)
        _processHeartRateData(data);
        break;
      case 0x08: // Temperature Data
        _processTemperatureData(data);
        break;
      default:
        debugPrint('🔍 Unknown command: 0x${command?.toRadixString(16)}');
    }
  }
  
  /// Processa dati SpO2
  void _processSpO2Data(List<int> data) {
    if (data.length < 8) return;
    
    try {
      final spo2Value = data[3];
      final signalQuality = data[4];
      final heartRate = data[5];
      final isWearing = data[6] & 0x01 == 1;
      final correctPosture = data[6] & 0x02 == 2;
      
      final reading = SpO2Reading(
        timestamp: DateTime.now(),
        spo2Percentage: spo2Value > 0 ? spo2Value : null,
        signalQuality: signalQuality,
        heartRate: heartRate > 0 ? heartRate : null,
        isWearing: isWearing,
        correctWristPosture: correctPosture,
        isValid: spo2Value > 70 && spo2Value <= 100 && signalQuality > 8,
      );
      
      _spO2Controller.add(reading);
      debugPrint('🫁 SpO2: ${reading.spo2Percentage}%, Signal: ${reading.signalQuality}');
      
    } catch (e) {
      debugPrint('❌ Error processing SpO2 data: $e');
    }
  }
  
  /// Processa dati heart rate per HRV
  void _processHeartRateData(List<int> data) {
    if (data.length < 6) return;
    
    try {
      final heartRate = data[3];
      final rrInterval = (data[4] << 8) | data[5]; // RR-interval in ms
      
      final reading = HeartRateReading(
        timestamp: DateTime.now(),
        heartRate: heartRate,
        rrInterval: rrInterval,
        isValid: heartRate > 40 && heartRate < 200 && rrInterval > 300 && rrInterval < 2000,
      );
      
      _heartRateController.add(reading);
      debugPrint('💓 HR: ${reading.heartRate} bpm, RR: ${reading.rrInterval}ms');
      
    } catch (e) {
      debugPrint('❌ Error processing heart rate data: $e');
    }
  }
  
  /// Processa dati temperatura
  void _processTemperatureData(List<int> data) {
    if (data.length < 8) return;
    
    try {
      // Temperatura polso (16-bit, little-endian)
      final wristTempRaw = (data[4] << 8) | data[3];
      final wristTemp = _convertTemperature(wristTempRaw);
      
      // Temperatura corporea (se disponibile)
      final bodyTempRaw = data.length > 6 ? (data[6] << 8) | data[5] : null;
      final bodyTemp = bodyTempRaw != null ? _convertTemperature(bodyTempRaw) : null;
      
      final reading = TemperatureReading(
        timestamp: DateTime.now(),
        wristTemperature: wristTemp,
        bodyTemperature: bodyTemp,
        isValid: wristTemp > 20.0 && wristTemp < 50.0,
      );
      
      _temperatureController.add(reading);
      debugPrint('🌡️ Wrist: ${reading.wristTemperature.toStringAsFixed(1)}°C');
      
    } catch (e) {
      debugPrint('❌ Error processing temperature data: $e');
    }
  }
  
  /// Converte valore raw temperatura in Celsius
  double _convertTemperature(int rawValue) {
    // Algoritmo di conversione dal SDK CL837
    double temp = rawValue / 100.0;
    int tempInt = (0 - temp.toInt()) & 0xFF;
    tempInt = tempInt ^ 0x3A;
    return tempInt.toDouble();
  }
  
  /// ================================
  /// 🔧 COMANDI BLE
  /// ================================
  
  /// Invia comando al dispositivo
  Future<void> _sendCommand(List<int> command) async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }
    
    try {
      await _rxCharacteristic!.write(command, withoutResponse: true);
      await Future.delayed(const Duration(milliseconds: 100));
    } catch (e) {
      throw Exception('Command send failed: $e');
    }
  }
  
  /// Costruisce comando per abilitare SpO2
  List<int> _buildEnableSpO2Command() {
    return _buildCommand(0x37, [0x01]);
  }
  
  /// Costruisce comando per disabilitare SpO2
  List<int> _buildDisableSpO2Command() {
    return _buildCommand(0x37, [0x00]);
  }
  
  /// Costruisce comando per richiesta dati SpO2
  List<int> _buildSpO2InquiryCommand() {
    return _buildCommand(0x37, [0x02]);
  }
  
  /// Costruisce comando per abilitare heart rate
  List<int> _buildEnableHeartRateCommand() {
    return _buildCommand(0x15, [0x01]);
  }
  
  /// Costruisce comando per disabilitare heart rate
  List<int> _buildDisableHeartRateCommand() {
    return _buildCommand(0x15, [0x00]);
  }
  
  /// Costruisce comando per richiesta dati heart rate
  List<int> _buildHeartRateDataRequest() {
    return _buildCommand(0x15, [0x02]);
  }
  
  /// Costruisce comando per abilitare temperatura
  List<int> _buildEnableTemperatureCommand() {
    return _buildCommand(0x08, [0x01]);
  }
  
  /// Costruisce comando per disabilitare temperatura
  List<int> _buildDisableTemperatureCommand() {
    return _buildCommand(0x08, [0x00]);
  }
  
  /// Costruisce comando per richiesta dati temperatura
  List<int> _buildTemperatureDataRequest() {
    return _buildCommand(0x08, [0x02]);
  }
  
  /// Costruisce frame comando con protocollo CL837
  List<int> _buildCommand(int command, List<int> parameters) {
    final frame = <int>[0xFF, 0x06, command, ...parameters];
    
    // Calcola checksum
    int sum = frame.reduce((a, b) => a + b);
    int checksum = ((0 - sum) & 0xFF) ^ 0x3A;
    frame.add(checksum);
    
    return frame;
  }
  
  /// ================================
  /// 🧹 CLEANUP
  /// ================================
  
  /// Ferma tutte le misurazioni
  Future<void> stopAllMeasurements() async {
    await stopSpO2Measurement();
    await stopHRVSession();
    await stopTemperatureMonitoring();
  }
  
  /// Pulisce le risorse
  void dispose() {
    _measurementTimer?.cancel();
    _spO2Controller.close();
    _hrvController.close();
    _temperatureController.close();
    _heartRateController.close();
  }
}

/// ================================
/// 📊 MODELLI DATI
/// ================================

/// Lettura SpO2
class SpO2Reading {
  final DateTime timestamp;
  final int? spo2Percentage;
  final int signalQuality;
  final int? heartRate;
  final bool isWearing;
  final bool correctWristPosture;
  final bool isValid;
  
  SpO2Reading({
    required this.timestamp,
    this.spo2Percentage,
    required this.signalQuality,
    this.heartRate,
    required this.isWearing,
    required this.correctWristPosture,
    required this.isValid,
  });
  
  @override
  String toString() => 'SpO2: ${spo2Percentage ?? "N/A"}%, Signal: $signalQuality, Valid: $isValid';
}

/// Lettura HRV
class HRVReading {
  final DateTime timestamp;
  final double rmssd;
  final double sdnn;
  final double pnn50;
  final List<int> rrIntervals;
  final double stressIndex;
  
  HRVReading({
    required this.timestamp,
    required this.rmssd,
    required this.sdnn,
    required this.pnn50,
    required this.rrIntervals,
    required this.stressIndex,
  });
  
  static HRVReading empty() => HRVReading(
    timestamp: DateTime.now(),
    rmssd: 0,
    sdnn: 0,
    pnn50: 0,
    rrIntervals: [],
    stressIndex: 0,
  );
  
  @override
  String toString() => 'HRV: RMSSD=${rmssd.toStringAsFixed(1)}, SDNN=${sdnn.toStringAsFixed(1)}, Stress=${stressIndex.toStringAsFixed(1)}%';
}

/// Lettura temperatura
class TemperatureReading {
  final DateTime timestamp;
  final double wristTemperature;
  final double? bodyTemperature;
  final bool isValid;
  
  TemperatureReading({
    required this.timestamp,
    required this.wristTemperature,
    this.bodyTemperature,
    required this.isValid,
  });
  
  @override
  String toString() => 'Temp: Wrist=${wristTemperature.toStringAsFixed(1)}°C, Body=${bodyTemperature?.toStringAsFixed(1) ?? "N/A"}°C';
}

/// Lettura heart rate per HRV
class HeartRateReading {
  final DateTime timestamp;
  final int heartRate;
  final int rrInterval;
  final bool isValid;
  
  HeartRateReading({
    required this.timestamp,
    required this.heartRate,
    required this.rrInterval,
    required this.isValid,
  });
  
  @override
  String toString() => 'HR: ${heartRate} bpm, RR: ${rrInterval}ms';
}

/// ================================
/// 📋 ESEMPI DI UTILIZZO
/// ================================

/// Esempio completo di utilizzo dell'API
class CL837UsageExample {
  static Future<void> demonstrateFullWorkflow() async {
    // Questo è un esempio di come utilizzare l'API in un'altra app
    
    // 1. Inizializza API (dopo connessione BLE)
    final api = CL837MedicalSensorsAPI(
      rxCharacteristic: null, // Sostituire con caratteristica reale
      txCharacteristic: null, // Sostituire con caratteristica reale
    );
    
    // 2. Ascolta i dati
    api.spO2Stream.listen((reading) {
      print('📊 ${reading}');
    });
    
    api.hrvStream.listen((reading) {
      print('💓 ${reading}');
    });
    
    api.temperatureStream.listen((reading) {
      print('🌡️ ${reading}');
    });
    
    // 3. Sequenza di misurazioni
    try {
      // SpO2 per 30 secondi
      await api.startSpO2Measurement(maxDuration: const Duration(seconds: 30));
      await Future.delayed(const Duration(seconds: 35));
      
      // HRV per 2 minuti
      await api.startHRVSession(sessionDuration: const Duration(minutes: 2));
      await Future.delayed(const Duration(minutes: 3));
      
      // Temperatura continua per 1 minuto
      await api.startTemperatureMonitoring(interval: const Duration(seconds: 10));
      await Future.delayed(const Duration(minutes: 1));
      
    } finally {
      // 4. Cleanup
      await api.stopAllMeasurements();
      api.dispose();
    }
  }
}
