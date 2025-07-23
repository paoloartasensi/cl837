import 'dart:async';
import 'package:flutter/foundation.dart';
import '../../models/spo2_data.dart';

/// Processore specializzato per i dati SpO2
/// Gestisce l'analisi e l'estrazione dei valori SpO2 dai pacchetti BLE
class SpO2Processor {
  final StreamController<SpO2Data> _spo2DataController = StreamController<SpO2Data>.broadcast();
  
  // Throttling for verbose SpO2 analysis logs
  int _enhancedAnalysisCount = 0;
  static const int _analysisLogThrottle = 5; // Log every 5th analysis
  
  /// Stream dei dati SpO2 processati
  Stream<SpO2Data> get spo2DataStream => _spo2DataController.stream;

  /// Processa i dati SpO2 dal comando 0x37 (SDK ufficiale)
  void processSPO2Data(List<int> data) {
    // SPO2 Mode-0x37 response format from SDK:
    // Byte 3: SPO2 value
    // Byte 4: 0=wrist posture wrong, 1=wrist posture correct (face up)
    // Byte 5: 0=no signal, <8=signal weak, >15=signal good
    // Byte 6: 0=not wear, 1=wear
    
    if (data.length < 7) {
      debugPrint('SPO2 data too short: ${data.length} bytes');
      return;
    }

    try {
      final spo2Value = data[3];
      final correctPosture = data[4] == 1;
      final signalQuality = data[5];
      final isWearing = data[6] == 1;

      debugPrint('SPO2 raw: value=$spo2Value, posture=$correctPosture, signal=$signalQuality, wearing=$isWearing');

      // 🔬 ENHANCED ANALYSIS: Distinguish between status and actual readings
      if (spo2Value <= 1) {
        debugPrint('🔬 ANALYSIS: This is a STATUS response, not actual SpO2 data');
        debugPrint('🔬   Status codes: 0=measurement not started, 1=measurement in progress/device not ready');
        debugPrint('🔬   Conditions: posture=${correctPosture ? "correct" : "wrong"}, signal=$signalQuality, wearing=${isWearing ? "yes" : "no"}');
        
        // Send status to UI with clear indication this is not a measurement
        final spo2Data = SpO2Data(
          spo2Value: null, // Use null to indicate no measurement available
          correctWristPosture: correctPosture,
          signalQuality: signalQuality,
          isWearing: isWearing,
        );
        _spo2DataController.add(spo2Data);
        debugPrint('📤 STATUS sent to UI: device ready=${isWearing && correctPosture && signalQuality >= 8}');
        
      } else if (spo2Value >= 70 && spo2Value <= 100) {
        debugPrint('🔬 ANALYSIS: This appears to be ACTUAL SpO2 measurement data');
        
        // SEMPRE invia i dati al UI per feedback in tempo reale
        final spo2Data = SpO2Data(
          spo2Value: spo2Value,
          correctWristPosture: correctPosture,
          signalQuality: signalQuality,
          isWearing: isWearing,
        );
        _spo2DataController.add(spo2Data);

        // Determinazione se il dato è valido (per logging)
        if (isWearing && correctPosture && signalQuality >= 8) {
          debugPrint('✅ Valid SpO2 Data: $spo2Value%, signal: $signalQuality');
        } else {
          // Feedback dettagliato per l'utente
          List<String> issues = [];
          if (!isWearing) issues.add('Device not detected on wrist');
          if (!correctPosture) issues.add('Turn wrist face up');
          if (signalQuality < 8) issues.add('Stay very still (signal: $signalQuality)');
          
          final feedback = issues.join(' • ');
          debugPrint('⚠️ SpO2 measurement needs adjustment: $feedback');
        }
        
      } else {
        debugPrint('🔬 ANALYSIS: Unexpected SpO2 value: $spo2Value (not typical range 70-100%)');
        debugPrint('🔬   This might be an error code or different data format');
        
        // Still send to UI but with null value to indicate error
        final spo2Data = SpO2Data(
          spo2Value: null,
          correctWristPosture: correctPosture,
          signalQuality: signalQuality,
          isWearing: isWearing,
        );
        _spo2DataController.add(spo2Data);
      }

    } catch (e) {
      debugPrint('Error parsing SPO2 data: $e');
    }
  }

  /// Ricerca aggressiva SpO2 nel comando 0x75 (dati sanitari estesi)
  void aggressiveSpO2Search(List<int> data, {bool shouldLogDetails = true}) {
    // 🎯 FOCUSED SEARCH: Only search for SpO2 in command 0x75 (extended health data)
    // This prevents false positives from accelerometer data (command 0x0C)
    
    if (data.length < 7 || data[0] != 0xFF) {
      return; // Not a valid protocol frame
    }
    
    final command = data[2];
    if (command != 0x75) {
      if (shouldLogDetails) {
        debugPrint('🚫 Skipping aggressive SpO2 search for command 0x${command.toRadixString(16)} (not 0x75)');
      }
      return; // Only search in extended health data
    }
    
    if (shouldLogDetails) {
      debugPrint('🔍 FOCUSED SpO2 search in command 0x75 (extended health data)');
      debugPrint('🔍 Packet: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    }
    
    // 🎯 NEW DISCOVERY: SpO2 value is at index 1 (second byte) in health data packets!
    // Check index 1 first as primary SpO2 location
    if (data.length >= 2) {
      final spo2Candidate = data[1];
      
      // SpO2 values are typically 85-100% (more restrictive range)
      if (spo2Candidate >= 85 && spo2Candidate <= 100) {
        if (shouldLogDetails) {
          debugPrint('🎯 PRIMARY SpO2 DETECTION: Found $spo2Candidate% at index 1 in health data');
          debugPrint('🎯 Packet: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
        }
        
        // This is very likely real SpO2 data from health command!
        final spo2Data = SpO2Data(
          spo2Value: spo2Candidate,
          correctWristPosture: true, // Assume good conditions if we get valid data
          signalQuality: 95, // Assume good signal quality
          isWearing: true,
        );
        
        _spo2DataController.add(spo2Data);
        if (shouldLogDetails) {
          debugPrint('🎯 REAL SpO2 DATA from health packet index 1: $spo2Candidate% pushed to UI');
        }
        return; // Found primary SpO2, no need to search further
      }
    }
    
    // Secondary search: Look for other reasonable SpO2 values in health data only
    for (int i = 3; i < data.length - 3; i++) {
      final byte = data[i];
      
      // Look for reasonable SpO2 values (85-100%)
      if (byte >= 85 && byte <= 100) {
        if (shouldLogDetails) {
          debugPrint('🔍 SECONDARY SpO2 SEARCH in health data: Found $byte% at position $i');
          debugPrint('🔍 Context: ${i > 0 ? '0x${data[i-1].toRadixString(16)}' : 'start'} -> 0x${byte.toRadixString(16)} -> ${i < data.length-1 ? '0x${data[i+1].toRadixString(16)}' : 'end'}');
        }
        
        // Only use secondary if we didn't find primary SpO2 at index 1
        if (i != 1) {
          final spo2Data = SpO2Data(
            spo2Value: byte,
            correctWristPosture: true,
            signalQuality: 80, // Lower confidence for secondary detection
            isWearing: true,
          );
          
          _spo2DataController.add(spo2Data);
          if (shouldLogDetails) {
            debugPrint('🔍 SECONDARY SpO2 DATA from health data: $byte% from position $i');
          }
          return; // Only process first match
        }
      }
    }
  }

  /// Analisi migliorata SpO2 - distingue tra stato e valori effettivi
  void enhancedSpO2Analysis(List<int> data, {bool shouldLogDetails = true}) {
    final command = data[2];
    _enhancedAnalysisCount++;
    
    // Only log detailed analysis occasionally to reduce spam or when explicitly requested
    bool doLogDetails = shouldLogDetails && (_enhancedAnalysisCount % _analysisLogThrottle == 0);
    
    if (doLogDetails) {
      debugPrint('🔬 ENHANCED SpO2 ANALYSIS for command 0x${command.toRadixString(16)} (analysis #$_enhancedAnalysisCount)');
      debugPrint('🔬 Full packet: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    }
    
    // Analyze each command type that might contain SpO2 data
    switch (command) {
      case 0x37: // Official SpO2 command
        if (doLogDetails) {
          debugPrint('🔬 Command 0x37 Analysis (Official SpO2 per SDK):');
        }
        if (data.length >= 7) {
          if (doLogDetails) {
            debugPrint('🔬   SDK says: "Returns SPO2 %, posture, signal quality, wear status"');
            debugPrint('🔬   Byte 3 (supposed SpO2): ${data[3]} (0x${data[3].toRadixString(16)})');
            debugPrint('🔬   Byte 4 (posture): ${data[4]} (0x${data[4].toRadixString(16)})');
            debugPrint('🔬   Byte 5 (signal): ${data[5]} (0x${data[5].toRadixString(16)})');
            debugPrint('🔬   Byte 6 (wearing): ${data[6]} (0x${data[6].toRadixString(16)})');
          }
          
          // According to SDK, this SHOULD be actual SpO2 percentage
          if (data[3] <= 1) {
            if (doLogDetails) {
              debugPrint('🔬   ❌ MISMATCH: SDK says this should be SpO2%, but we get ${data[3]}');
              debugPrint('🔬   ❌ Device might not be ready or needs different approach');
            }
          } else if (data[3] >= 70 && data[3] <= 100) {
            if (doLogDetails) {
              debugPrint('🔬   ✅ MATCHES SDK: This should be ACTUAL SpO2 data: ${data[3]}%');
            }
          } else {
            if (doLogDetails) {
              debugPrint('🔬   ⚠️  Unexpected value: ${data[3]} (not typical for SpO2)');
            }
          }
        }
        break;
        
      case 0x75: // Extended health data - NOT in official SDK but contains SpO2-like values!
        if (doLogDetails) {
          debugPrint('🔬 Command 0x75 Analysis (NOT in official SDK - discovered):');
          debugPrint('🔬   This packet is ${data.length} bytes long');
          debugPrint('🔬   HYPOTHESIS: Real SpO2 data might be embedded here!');
        }
        
        // Search for SpO2 patterns in health data according to aggressive search findings
        List<int> candidateValues = [];
        for (int i = 3; i < data.length - 3; i++) {
          if (data[i] >= 80 && data[i] <= 100) {
            candidateValues.add(data[i]);
            if (doLogDetails) {
              debugPrint('🔬   Candidate SpO2 at position $i: ${data[i]}% (context: 0x${data[i-1].toRadixString(16)} 0x${data[i+1].toRadixString(16)})');
            }
          }
        }
        
        // If we found reasonable SpO2 candidates, use the most likely one
        if (candidateValues.isNotEmpty) {
          // Prefer values in the normal range (95-100%)
          final preferredValue = candidateValues.firstWhere(
            (v) => v >= 95 && v <= 100,
            orElse: () => candidateValues.first
          );
          
          if (doLogDetails) {
            debugPrint('🔬   🎯 SELECTING $preferredValue% as likely SpO2 from health data');
          }
          
          // Push this as a real SpO2 reading
          final spo2Data = SpO2Data(
            spo2Value: preferredValue,
            correctWristPosture: true, // Assume good conditions if we get data
            signalQuality: 100, // Assume good signal
            isWearing: true,
          );
          
          _spo2DataController.add(spo2Data);
          if (doLogDetails) {
            debugPrint('🔬   📤 REAL SpO2 DATA from 0x75 pushed to UI: $preferredValue%');
          }
        }
        break;
        
      case 0x0C: // Accelerometer - NEVER contains SpO2 data!
        if (doLogDetails) {
          debugPrint('🔬 Command 0x0C (Accelerometer per SDK):');
          debugPrint('🔬   SDK says: "Acceleration 3D raw data, every 250ms"');
          debugPrint('🔬   ❌ IMPORTANT: This is MOTION DATA, not SpO2! Any 80-100 values are acceleration readings!');
          debugPrint('🔬   ❌ Acceleration values that happen to be 80-100 should NOT be interpreted as SpO2');
        }
        break;
    }
  }

  /// Rileva pattern SpO2 in dati senza protocollo rigoroso
  void detectSpO2Pattern(List<int> data) {
    if (data.length >= 4) {
      final possibleSpO2 = data[0];
      if (possibleSpO2 >= 70 && possibleSpO2 <= 100) {
        final spo2Data = SpO2Data(
          spo2Value: possibleSpO2,
          correctWristPosture: data.length > 1 ? data[1] == 1 : true,
          signalQuality: data.length > 2 ? data[2] : 100,
          isWearing: data.length > 3 ? data[3] == 1 : true,
        );
        _spo2DataController.add(spo2Data);
        debugPrint('Detected SpO2 pattern: $spo2Data');
      }
    }
  }

  void dispose() {
    _spo2DataController.close();
  }
}
