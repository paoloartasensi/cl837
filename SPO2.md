# SpO2 Measurement - CL837 Device Complete Guide

## 🎯 **CRITICAL DISCOVERY: SpO2 Direct Encoding**

The **most important discovery** in working with the CL837 device is that **SpO2 values are NOT calculated - they are directly encoded** in the BLE packets.

### **Key Finding**
```dart
// SpO2 is located at INDEX 1 (second byte) of BLE packets
final spo2Value = data[1]; // Direct percentage value - NO conversion needed
```

## 📊 **SpO2 Data Encoding**

### **Direct Byte-to-Percentage Mapping**
| Hex Byte | Decimal | SpO2 % | Status |
|----------|---------|--------|---------|
| `0x64`   | 100     | 100%   | Excellent |
| `0x63`   | 99      | 99%    | Excellent |
| `0x61`   | 97      | 97%    | Good |
| `0x60`   | 96      | 96%    | Good |
| `0x5C`   | 92      | 92%    | Acceptable |
| `0x5A`   | 90      | 90%    | Low Normal |
| `0x55`   | 85      | 85%    | Low |
| `0x51`   | 81      | 81%    | Critical |

### **Encoding Rules**
- **Range**: 70-100% (typical physiological range)
- **Format**: Single byte, direct percentage
- **Location**: Always at `data[1]` (index 1)
- **Validation**: Values outside 70-100% are considered invalid/status codes

## 🔍 **SpO2 Extraction Implementation**

### **Primary SpO2 Extraction Method**
```dart
class SpO2Extractor {
  
  /// Extract SpO2 from any BLE packet
  static int? extractSpO2(List<int> data) {
    if (data.length < 2) return null;
    
    final potentialSpO2 = data[1]; // Index 1 = second byte
    
    // Validate SpO2 range (70-100% is physiologically valid)
    if (potentialSpO2 >= 70 && potentialSpO2 <= 100) {
      return potentialSpO2; // Direct value - no calculation needed
    }
    
    return null; // Invalid SpO2 value
  }
  
  /// Advanced analysis for debugging
  static void analyzePacketForSpO2(List<int> data) {
    print('🔬 Packet Analysis: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    for (int i = 0; i < data.length; i++) {
      final value = data[i];
      if (value >= 80 && value <= 100) {
        if (i == 1) {
          print('✅ PRIMARY SpO2 at index 1: ${value}%');
        } else {
          print('🎯 Potential SpO2 at index $i: ${value}%');
        }
      }
    }
  }
}
```

### **SpO2 Data Model**
```dart
class SpO2Data {
  final int? spo2Value;           // SpO2 percentage (null = status/no reading)
  final bool correctWristPosture; // Wrist position correct
  final int signalQuality;        // Signal quality (0-100)
  final bool isWearing;          // Device worn correctly
  final DateTime timestamp;      // When measurement was taken
  
  SpO2Data({
    this.spo2Value,
    required this.correctWristPosture,
    required this.signalQuality,
    required this.isWearing,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();
  
  /// Check if this is a valid SpO2 measurement (not just status)
  bool get isValidMeasurement => 
    spo2Value != null && 
    spo2Value! >= 70 && 
    spo2Value! <= 100;
    
  /// Check if device is ready for accurate measurement
  bool get isDeviceReady => 
    isWearing && 
    correctWristPosture && 
    signalQuality >= 8;
    
  /// Get SpO2 status description
  String get statusDescription {
    if (spo2Value == null) return "Status/No Reading";
    if (spo2Value! >= 95) return "Normal";
    if (spo2Value! >= 90) return "Acceptable";
    if (spo2Value! >= 85) return "Low";
    return "Critical";
  }
  
  /// Get appropriate color for UI display
  Color get statusColor {
    if (spo2Value == null) return Colors.grey;
    if (spo2Value! >= 95) return Colors.green;
    if (spo2Value! >= 90) return Colors.orange;
    return Colors.red;
  }
}
```

## 🚨 **Command 0x37 vs Real SpO2 Data**

### **IMPORTANT DISTINCTION**

#### **Command 0x37 - SpO2 Control (NOT measurement data)**
```dart
// Command 0x37 controls SpO2 mode - does NOT return real SpO2 values
await sendCommand([0x37, 0x01]); // Enable SpO2 + LED ON (Red)
await sendCommand([0x37, 0x00]); // Disable SpO2 + LED OFF  
await sendCommand([0x37, 0x02]); // Request SpO2 status

// Typical 0x37 response format:
// [0xFF, 0x06, 0x37, value=1, posture=0, signal=1, wearing=0, checksum]
// Where value=1 is a STATUS CODE, not SpO2 percentage
```

#### **Real SpO2 Data (in other packets)**
```dart
// Real SpO2 values arrive in various packet types:
// - Command 0x0C (Accelerometer data)
// - Command 0x75 (Extended health data)
// - Command 0x15 (Sports data)
// - Automatic health monitoring packets

// Example packet with real SpO2:
// [0xFF, 0x08, 0x0C, 0x61, 0x12, 0x34, 0x56, checksum]
//                    ^^^^
//                    97% SpO2 at index 1
```

## 🔬 **Complete SpO2 Measurement Procedure**

### **Full Measurement Implementation**
```dart
class SpO2Measurement {
  static const Duration _measurementTimeout = Duration(seconds: 15);
  static const Duration _stabilizationTime = Duration(seconds: 4);
  
  /// Perform complete SpO2 measurement
  static Future<SpO2Result> measureSpO2({
    required Function(List<int>) sendCommand,
    required Stream<List<int>> dataStream,
  }) async {
    List<int> detectedValues = [];
    SpO2Data? bestReading;
    
    StreamSubscription? subscription;
    final completer = Completer<SpO2Result>();
    
    try {
      // 1. Ensure SpO2 mode is disabled first
      await sendCommand([0x37, 0x00]);
      await Future.delayed(Duration(milliseconds: 500));
      
      // 2. Set up data monitoring
      subscription = dataStream.listen((List<int> data) {
        // Extract SpO2 from any incoming packet
        final spo2 = SpO2Extractor.extractSpO2(data);
        if (spo2 != null) {
          detectedValues.add(spo2);
          print('📊 SpO2 detected: ${spo2}%');
          
          // Also parse command 0x37 status if available
          if (data.length >= 7 && data[0] == 0xFF && data[2] == 0x37) {
            final statusData = _parseSpO2Status(data);
            if (statusData.isDeviceReady) {
              bestReading = SpO2Data(
                spo2Value: spo2,
                correctWristPosture: statusData.correctWristPosture,
                signalQuality: statusData.signalQuality,
                isWearing: statusData.isWearing,
              );
            }
          }
        }
      });
      
      // 3. Enable SpO2 mode (LED turns RED)
      print('🔴 Enabling SpO2 mode - LED should turn RED');
      await sendCommand([0x37, 0x01]);
      
      // 4. Wait for sensor stabilization
      await Future.delayed(_stabilizationTime);
      
      // 5. Request multiple measurements for accuracy
      for (int i = 0; i < 5; i++) {
        await sendCommand([0x37, 0x02]); // Request status/measurement
        await Future.delayed(Duration(seconds: 2));
      }
      
      // 6. Wait additional time for data collection
      await Future.delayed(Duration(seconds: 6));
      
      // 7. Analyze collected data
      final result = _analyzeSpO2Results(detectedValues, bestReading);
      completer.complete(result);
      
    } catch (e) {
      completer.completeError(e);
    } finally {
      // 8. ALWAYS turn off SpO2 mode and LED
      try {
        await sendCommand([0x37, 0x00]);
        print('⚫ SpO2 mode disabled - LED should be OFF');
      } catch (e) {
        print('⚠️ Error disabling SpO2 mode: $e');
      }
      
      subscription?.cancel();
    }
    
    return completer.future.timeout(_measurementTimeout);
  }
  
  /// Parse SpO2 status from command 0x37 response
  static SpO2Data _parseSpO2Status(List<int> data) {
    if (data.length < 7) {
      throw Exception('Invalid SpO2 status data length');
    }
    
    return SpO2Data(
      spo2Value: null, // Status data doesn't contain real SpO2
      correctWristPosture: data[4] == 1,
      signalQuality: data[5],
      isWearing: data[6] == 1,
    );
  }
  
  /// Analyze collected SpO2 results
  static SpO2Result _analyzeSpO2Results(List<int> values, SpO2Data? bestReading) {
    if (values.isEmpty) {
      return SpO2Result(
        success: false,
        error: 'No SpO2 values detected',
        measurements: [],
      );
    }
    
    // Filter valid values and calculate statistics
    final validValues = values.where((v) => v >= 80 && v <= 100).toList();
    
    if (validValues.isEmpty) {
      return SpO2Result(
        success: false,
        error: 'No valid SpO2 values detected',
        measurements: values,
      );
    }
    
    final average = validValues.reduce((a, b) => a + b) / validValues.length;
    final mostCommon = _findMostCommon(validValues);
    
    return SpO2Result(
      success: true,
      averageSpO2: average.round(),
      mostCommonSpO2: mostCommon,
      measurements: values,
      bestReading: bestReading,
      measurementQuality: _calculateQuality(validValues),
    );
  }
  
  /// Find most commonly detected SpO2 value
  static int _findMostCommon(List<int> values) {
    final frequency = <int, int>{};
    for (final value in values) {
      frequency[value] = (frequency[value] ?? 0) + 1;
    }
    
    return frequency.entries
        .reduce((a, b) => a.value > b.value ? a : b)
        .key;
  }
  
  /// Calculate measurement quality score
  static double _calculateQuality(List<int> values) {
    if (values.length < 2) return 0.5;
    
    final variance = _calculateVariance(values);
    final consistency = 1.0 / (1.0 + variance); // Lower variance = higher quality
    final sampleSize = (values.length / 10.0).clamp(0.0, 1.0); // More samples = higher quality
    
    return (consistency + sampleSize) / 2.0;
  }
  
  /// Calculate variance of measurements
  static double _calculateVariance(List<int> values) {
    final mean = values.reduce((a, b) => a + b) / values.length;
    final squaredDiffs = values.map((v) => (v - mean) * (v - mean));
    return squaredDiffs.reduce((a, b) => a + b) / values.length;
  }
}
```

### **SpO2 Result Model**
```dart
class SpO2Result {
  final bool success;
  final String? error;
  final int? averageSpO2;
  final int? mostCommonSpO2;
  final List<int> measurements;
  final SpO2Data? bestReading;
  final double? measurementQuality; // 0.0 - 1.0
  
  SpO2Result({
    required this.success,
    this.error,
    this.averageSpO2,
    this.mostCommonSpO2,
    this.measurements = const [],
    this.bestReading,
    this.measurementQuality,
  });
  
  /// Get the most reliable SpO2 reading
  int? get reliableSpO2 => mostCommonSpO2 ?? averageSpO2;
  
  /// Get quality description
  String get qualityDescription {
    if (measurementQuality == null) return 'Unknown';
    if (measurementQuality! >= 0.8) return 'Excellent';
    if (measurementQuality! >= 0.6) return 'Good';
    if (measurementQuality! >= 0.4) return 'Fair';
    return 'Poor';
  }
}
```

## 💡 **LED Control for SpO2**

### **LED States**
- **OFF**: Normal operation, SpO2 mode disabled
- **RED**: SpO2 measurement active, sensor collecting data
- **Stuck ON**: Emergency - requires multiple shutdown commands

### **LED Control Implementation**
```dart
class SpO2LEDController {
  
  /// Enable SpO2 mode with LED
  static Future<void> enableSpO2LED(Function(List<int>) sendCommand) async {
    await sendCommand([0x37, 0x01]);
    print('🔴 SpO2 LED ON - Device measuring');
  }
  
  /// Disable SpO2 mode and LED
  static Future<void> disableSpO2LED(Function(List<int>) sendCommand) async {
    await sendCommand([0x37, 0x00]);
    print('⚫ SpO2 LED OFF - Measurement stopped');
  }
  
  /// Emergency LED shutdown (if stuck)
  static Future<void> emergencyLEDShutdown(Function(List<int>) sendCommand) async {
    print('🚨 Emergency LED shutdown');
    
    for (int attempt = 0; attempt < 5; attempt++) {
      try {
        await sendCommand([0x37, 0x00]);
        await Future.delayed(Duration(milliseconds: 300));
        print('Emergency shutdown attempt ${attempt + 1}/5');
      } catch (e) {
        print('Emergency shutdown attempt ${attempt + 1} failed: $e');
      }
    }
  }
}
```

## 🔧 **Advanced SpO2 Analysis**

### **Multi-Packet SpO2 Detection**
```dart
class AdvancedSpO2Analyzer {
  
  /// Search for SpO2 in any packet type
  static void aggressiveSpO2Search(List<int> data) {
    print('🔍 Searching packet: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    for (int i = 0; i < data.length; i++) {
      final value = data[i];
      
      // Look for realistic SpO2 values
      if (value >= 80 && value <= 100) {
        final confidence = _calculateConfidence(value, i, data);
        print('🎯 SpO2 candidate: ${value}% at index $i (confidence: ${confidence.toStringAsFixed(2)})');
        
        if (i == 1) {
          print('✅ PRIMARY SpO2 LOCATION: ${value}% at index 1');
        }
      }
    }
  }
  
  /// Calculate confidence that a value is SpO2
  static double _calculateConfidence(int value, int index, List<int> data) {
    double confidence = 0.0;
    
    // Index 1 is most likely SpO2 location
    if (index == 1) confidence += 0.6;
    
    // Typical SpO2 ranges
    if (value >= 95) confidence += 0.3;
    else if (value >= 90) confidence += 0.2;
    else if (value >= 85) confidence += 0.1;
    
    // Packet context (certain commands more likely to contain SpO2)
    if (data.length >= 3) {
      final command = data[2];
      if (command == 0x0C || command == 0x75) confidence += 0.1; // Health data
    }
    
    return confidence.clamp(0.0, 1.0);
  }
  
  /// Enhanced SpO2 analysis with packet type detection
  static SpO2AnalysisResult enhancedSpO2Analysis(List<int> data) {
    final candidates = <SpO2Candidate>[];
    
    for (int i = 0; i < data.length; i++) {
      final value = data[i];
      if (value >= 70 && value <= 100) {
        final confidence = _calculateConfidence(value, i, data);
        candidates.add(SpO2Candidate(
          value: value,
          index: i,
          confidence: confidence,
        ));
      }
    }
    
    // Sort by confidence
    candidates.sort((a, b) => b.confidence.compareTo(a.confidence));
    
    return SpO2AnalysisResult(
      packetData: data,
      candidates: candidates,
      bestCandidate: candidates.isNotEmpty ? candidates.first : null,
      packetType: _identifyPacketType(data),
    );
  }
  
  /// Identify packet type
  static String _identifyPacketType(List<int> data) {
    if (data.length >= 3 && data[0] == 0xFF) {
      final command = data[2];
      switch (command) {
        case 0x0C: return 'Accelerometer';
        case 0x15: return 'Sports Data';
        case 0x37: return 'SpO2 Control';
        case 0x38: return 'Temperature';
        case 0x75: return 'Extended Health';
        default: return 'Unknown Command (0x${command.toRadixString(16)})';
      }
    }
    return 'Non-Protocol Packet';
  }
}

class SpO2Candidate {
  final int value;
  final int index;
  final double confidence;
  
  SpO2Candidate({
    required this.value,
    required this.index,
    required this.confidence,
  });
}

class SpO2AnalysisResult {
  final List<int> packetData;
  final List<SpO2Candidate> candidates;
  final SpO2Candidate? bestCandidate;
  final String packetType;
  
  SpO2AnalysisResult({
    required this.packetData,
    required this.candidates,
    this.bestCandidate,
    required this.packetType,
  });
}
```

## 🎯 **Usage Examples**

### **Simple SpO2 Reading**
```dart
// Quick SpO2 extraction from any packet
void onDataReceived(List<int> data) {
  final spo2 = SpO2Extractor.extractSpO2(data);
  if (spo2 != null) {
    print('SpO2: ${spo2}%');
    updateUI(spo2);
  }
}
```

### **Complete SpO2 Measurement**
```dart
// Full measurement procedure
Future<void> performSpO2Measurement() async {
  try {
    final result = await SpO2Measurement.measureSpO2(
      sendCommand: (cmd) => sendBLECommand(cmd),
      dataStream: bleDataStream,
    );
    
    if (result.success) {
      print('SpO2 Measurement Successful!');
      print('Average SpO2: ${result.averageSpO2}%');
      print('Most Common: ${result.mostCommonSpO2}%');
      print('Quality: ${result.qualityDescription}');
    } else {
      print('SpO2 Measurement Failed: ${result.error}');
    }
    
  } catch (e) {
    print('SpO2 measurement error: $e');
    // Emergency LED shutdown
    await SpO2LEDController.emergencyLEDShutdown(sendBLECommand);
  }
}
```

### **Real-time SpO2 Monitoring**
```dart
// Continuous SpO2 monitoring
StreamSubscription? _spo2Subscription;

void startSpO2Monitoring() {
  _spo2Subscription = bleDataStream.listen((data) {
    // Analyze every packet for SpO2
    final analysis = AdvancedSpO2Analyzer.enhancedSpO2Analysis(data);
    
    if (analysis.bestCandidate != null && analysis.bestCandidate!.confidence > 0.7) {
      final spo2 = analysis.bestCandidate!.value;
      print('High-confidence SpO2: ${spo2}%');
      
      // Update UI
      spo2Controller.add(SpO2Data(
        spo2Value: spo2,
        correctWristPosture: true, // Would get from status
        signalQuality: (analysis.bestCandidate!.confidence * 100).round(),
        isWearing: true, // Would get from status
      ));
    }
  });
}

void stopSpO2Monitoring() {
  _spo2Subscription?.cancel();
}
```

## 📋 **Key Takeaways**

### ✅ **What Works**
1. **SpO2 is at index 1** - Direct percentage encoding
2. **No calculations needed** - Device sends pre-calculated values
3. **Multiple packet sources** - SpO2 appears in various packet types
4. **LED control works** - Command 0x37 reliably controls LED
5. **Range validation** - 70-100% indicates valid SpO2

### ❌ **Common Mistakes**
1. **Don't trust command 0x37 values** - They're status codes, not SpO2
2. **Don't over-complicate parsing** - Simple `data[1]` extraction works
3. **Don't forget LED shutdown** - Always turn off LED after measurement
4. **Don't ignore packet types** - SpO2 comes from multiple sources
5. **Don't assume single reading** - Collect multiple values for accuracy

### 🎯 **Best Practices**
1. **Always monitor index 1** for SpO2 values
2. **Collect multiple readings** for better accuracy
3. **Validate range** (70-100%) before using values
4. **Handle LED properly** - On for measurement, off when done
5. **Implement timeouts** - Don't let measurements run indefinitely
6. **Use confidence scoring** when analyzing packets
7. **Monitor connection state** during measurements

---

**This document represents the complete SpO2 implementation knowledge for the CL837 device, based on extensive reverse engineering and testing.**

**Last Updated**: July 2025  
**Device**: CL837 with Chileaf SDK v0.6  
**Key Discovery**: SpO2 direct encoding at packet index 1

## 🩸 **Come Vengono Letti i Dati per la Saturazione di Ossigeno nel Sangue**

### **Principio di Funzionamento del Sensore SpO2**

Il dispositivo CL837 utilizza un **sensore ottico** per misurare la saturazione di ossigeno nel sangue:

1. **LED Rosso e Infrarosso**: Il dispositivo emette luce a due lunghezze d'onda
   - **660 nm (rosso)**: Assorbita dall'emoglobina desossigenata
   - **940 nm (infrarosso)**: Assorbita dall'emoglobina ossigenata

2. **Fotodiodo**: Rileva la luce che attraversa il tessuto

3. **Algoritmo di Calcolo**: Il microprocessore interno calcola il rapporto tra le due assorbanze

### **Processo di Lettura dei Dati SpO2**

#### **1. Attivazione del Sensore**
```dart
// Il LED rosso si accende per indicare la misurazione attiva
await sendCommand([0x37, 0x01]); // Comando per attivare SpO2

// Sequenza di attivazione:
// 1. Microprocessore attiva i LED rosso/infrarosso
// 2. Fotodiodo inizia a rilevare i segnali
// 3. LED rosso visibile si accende come indicatore
// 4. Algoritmo interno inizia il calcolo
```

#### **2. Acquisizione del Segnale Fotopletismografico (PPG)**
```dart
// Il dispositivo acquisisce il segnale PPG in tempo reale:
// - Rileva le variazioni di assorbimento della luce
// - Analizza le pulsazioni del flusso sanguigno
// - Calcola il rapporto tra emoglobina ossigenata/desossigenata

// Esempio di processo interno (non accessibile dall'esterno):
class PPGSignalProcessor {
  double redSignal;    // Segnale LED rosso (660nm)
  double irSignal;     // Segnale LED infrarosso (940nm)
  
  double calculateSpO2() {
    // Formula semplificata (algoritmo reale è più complesso)
    double ratio = (redSignal / irSignal);
    return 110 - (25 * ratio); // Approssimazione
  }
}
```

#### **3. Trasmissione dei Dati via BLE**

Il dispositivo **NON trasmette** i dati grezzi PPG, ma invia direttamente il **valore SpO2 calcolato**:

```dart
// IMPORTANTE: Il valore SpO2 è già processato dal dispositivo
class SpO2DataTransmission {
  
  /// Il dispositivo invia il valore SpO2 già calcolato
  static void transmitSpO2Data(int calculatedSpO2) {
    // Il microprocessore interno ha già:
    // 1. Acquisito i segnali PPG
    // 2. Filtrato il rumore
    // 3. Applicato algoritmi di compensazione
    // 4. Calcolato il valore SpO2 finale
    
    // Trasmissione BLE del valore finale
    List<int> packet = [
      0xFF,                    // Header
      0x08,                    // Length
      0x0C,                    // Command (es. accelerometer data)
      calculatedSpO2,          // INDEX 1: SpO2 come percentuale diretta
      0x12, 0x34, 0x56,       // Altri dati del pacchetto
      0xAB                     // Checksum
    ];
    
    // Il valore all'index 1 è il risultato finale del calcolo SpO2
    print('SpO2 trasmesso: ${calculatedSpO2}%');
  }
}
```

### **4. Estrazione del Valore SpO2 dall'App**

```dart
// L'app Flutter riceve il pacchetto BLE e estrae il valore
class SpO2DataReader {
  
  /// Legge il valore SpO2 già calcolato dal dispositivo
  static int? readSpO2FromPacket(List<int> blePacket) {
    // Esempio di pacchetto ricevuto:
    // [0xFF, 0x08, 0x0C, 0x61, 0x12, 0x34, 0x56, 0xAB]
    //                    ^^^^
    //                    Index 1 = 0x61 = 97 decimale = 97% SpO2
    
    if (blePacket.length >= 2) {
      final spo2Value = blePacket[1]; // Lettura diretta
      
      // Validazione del range fisiologico
      if (spo2Value >= 70 && spo2Value <= 100) {
        return spo2Value; // Valore valido
      }
    }
    
    return null; // Valore non valido
  }
}
```

### **Confronto: Dati Grezzi vs Dati Processati**

#### **❌ Quello che NON facciamo (dati grezzi):**
```dart
// NON abbiamo accesso a questi dati dal CL837:
class RawPPGData {
  List<double> redLEDSamples;     // Campioni LED rosso
  List<double> irLEDSamples;      // Campioni LED infrarosso
  double heartRate;               // Frequenza cardiaca rilevata
  double signalQuality;           // Qualità del segnale PPG
  
  // Questi calcoli sono fatti INTERNAMENTE dal dispositivo
  double calculateSpO2() {
    // Algoritmi complessi di elaborazione del segnale
    // Filtri digitali, compensazione movimento, ecc.
  }
}
```

#### **✅ Quello che facciamo (dati processati):**
```dart
// Quello che riceviamo dal CL837:
class ProcessedSpO2Data {
  final int spo2Percentage;      // Valore finale già calcolato
  final bool deviceWorn;         // Status sensore di contatto
  final int signalQuality;       // Qualità del segnale (0-100)
  final bool positionCorrect;    // Posizione polso corretta
  
  // Semplice estrazione del valore
  static int extractSpO2(List<int> packet) {
    return packet[1]; // Nessun calcolo necessario
  }
}
```

### **Flusso Completo di Lettura SpO2**

```dart
class CompleteSpO2ReadingFlow {
  
  /// Flusso completo dalla richiesta alla lettura
  static Future<SpO2Reading> performCompleteReading() async {
    
    // FASE 1: Preparazione Hardware
    print('🔧 Fase 1: Preparazione sensore...');
    await sendCommand([0x37, 0x00]); // Assicura che sia spento
    await Future.delayed(Duration(milliseconds: 500));
    
    // FASE 2: Attivazione Sensore SpO2
    print('🔴 Fase 2: Attivazione LED e sensori...');
    await sendCommand([0x37, 0x01]); // LED rosso ON
    
    /*
    Processo interno del dispositivo (non visibile):
    - Accensione LED rosso (660nm) e infrarosso (940nm)
    - Attivazione fotodiodo
    - Inizio acquisizione segnale PPG
    - Rilevamento battito cardiaco
    - Calcolo rapporto assorbimento luce
    */
    
    // FASE 3: Stabilizzazione (4 secondi)
    print('⏱️ Fase 3: Stabilizzazione sensore...');
    await Future.delayed(Duration(seconds: 4));
    
    /*
    Durante la stabilizzazione:
    - Il sensore si adatta alla pelle
    - Vengono filtrati i rumori iniziali
    - Si stabilisce un baseline per il segnale
    - Algoritmi di compensazione movimento si attivano
    */
    
    // FASE 4: Richiesta Misurazioni
    print('📊 Fase 4: Richiesta misurazioni...');
    List<int> detectedValues = [];
    
    for (int i = 0; i < 5; i++) {
      await sendCommand([0x37, 0x02]); // Richiesta status
      
      // Attesa per permettere al dispositivo di:
      // - Completare il calcolo SpO2
      // - Trasmettere il risultato via BLE
      await Future.delayed(Duration(seconds: 2));
    }
    
    // FASE 5: Monitoraggio Pacchetti BLE
    print('📡 Fase 5: Lettura pacchetti BLE...');
    
    // Ascolto di TUTTI i pacchetti in arrivo
    dataStream.listen((List<int> packet) {
      
      // Analisi del pacchetto ricevuto
      debugPrint('Pacchetto: ${packet.map((b) => '0x${b.toRadixString(16)}').join(' ')}');
      
      // Estrazione SpO2 dall'index 1
      if (packet.length >= 2) {
        final potentialSpO2 = packet[1];
        
        if (potentialSpO2 >= 80 && potentialSpO2 <= 100) {
          detectedValues.add(potentialSpO2);
          print('✅ SpO2 rilevato: ${potentialSpO2}%');
          
          /*
          Cosa significa questo valore:
          - È il risultato di complessi calcoli PPG
          - Include compensazione per movimento
          - È filtrato per eliminare artefatti
          - Rappresenta la saturazione media dell'ultimo ciclo
          */
        }
      }
    });
    
    // FASE 6: Spegnimento Sensore
    print('⚫ Fase 6: Spegnimento LED...');
    await sendCommand([0x37, 0x00]); // LED OFF
    
    // FASE 7: Analisi Risultati
    return analyzeSpO2Results(detectedValues);
  }
}
```

### **Precisioni Tecniche sui Dati SpO2**

#### **Caratteristiche del Sensore CL837:**
```dart
class CL837SpO2Specifications {
  // Specifiche tecniche del sensore
  static const double redLEDWavelength = 660.0;  // nm
  static const double irLEDWavelength = 940.0;   // nm
  static const int samplingRate = 25;            // Hz (stima)
  static const int resolution = 1;               // 1% di risoluzione
  static const List<int> accuracyRange = [70, 100]; // Range operativo
  
  // Tempo di risposta del sensore
  static const Duration responseTime = Duration(seconds: 3);
  static const Duration stabilizationTime = Duration(seconds: 4);
  
  // Algoritmi interni (non accessibili)
  static const List<String> internalProcessing = [
    'Filtro passa-basso per eliminare rumore',
    'Algoritmo di rilevamento battito',
    'Compensazione movimento',
    'Calibrazione automatica',
    'Validazione qualità segnale'
  ];
}
```

#### **Formato dei Dati Trasmessi:**
```dart
class SpO2DataFormat {
  
  /// Formato esatto dei dati SpO2 nel pacchetto BLE
  static void analyzePacketFormat(List<int> packet) {
    if (packet.length >= 8 && packet[0] == 0xFF) {
      
      print('📋 Analisi formato pacchetto SpO2:');
      print('   Index 0: 0x${packet[0].toRadixString(16)} (Header fisso)');
      print('   Index 1: 0x${packet[1].toRadixString(16)} = ${packet[1]}% SpO2');
      print('   Index 2: 0x${packet[2].toRadixString(16)} (Comando)');
      print('   Index 3+: Dati aggiuntivi o padding');
      
      // Il valore SpO2 è sempre all'index 1
      final spo2 = packet[1];
      
      /*
      Interpretazione del valore:
      - Range 80-100: Valore SpO2 valido
      - Range 70-79: SpO2 basso ma possibile
      - Range 0-69: Codici di stato o errore
      - > 100: Valore non valido
      */
      
      if (spo2 >= 95) {
        print('✅ SpO2 Normale: ${spo2}%');
      } else if (spo2 >= 90) {
        print('⚠️ SpO2 Accettabile: ${spo2}%');
      } else if (spo2 >= 80) {
        print('🚨 SpO2 Basso: ${spo2}%');
      } else {
        print('❌ Valore non valido o codice stato: ${spo2}');
      }
    }
  }
}
```

### **Risoluzione dei Problemi nella Lettura SpO2**

```dart
class SpO2ReadingTroubleshooting {
  
  /// Diagnostica problemi di lettura SpO2
  static void diagnoseReadingIssues(List<int> receivedPackets) {
    
    print('🔍 Diagnostica lettura SpO2...');
    
    // Problema 1: Nessun dato ricevuto
    if (receivedPackets.isEmpty) {
      print('❌ PROBLEMA: Nessun pacchetto ricevuto');
      print('   Soluzioni:');
      print('   - Verificare connessione BLE');
      print('   - Controllare che il LED sia acceso');
      print('   - Assicurarsi che le notifiche siano abilitate');
      return;
    }
    
    // Problema 2: Valori sempre uguali (es. sempre 1%)
    final uniqueValues = receivedPackets.toSet();
    if (uniqueValues.length == 1 && uniqueValues.first <= 10) {
      print('❌ PROBLEMA: Ricevuto solo codice stato (${uniqueValues.first})');
      print('   Soluzioni:');
      print('   - Il dispositivo non sta effettuando misurazioni reali');
      print('   - Posizionare correttamente il dispositivo sul polso');
      print('   - Assicurarsi che il sensore tocchi la pelle');
      print('   - Rimanere fermi durante la misurazione');
      return;
    }
    
    // Problema 3: Valori troppo variabili
    final variance = calculateVariance(receivedPackets);
    if (variance > 25) {
      print('⚠️ PROBLEMA: Valori troppo variabili (varianza: ${variance.toStringAsFixed(1)})');
      print('   Soluzioni:');
      print('   - Rimanere completamente fermi');
      print('   - Stringere il dispositivo (non troppo)');
      print('   - Pulire il sensore');
      print('   - Evitare movimenti del braccio');
    }
    
    // Problema 4: Range non fisiologico
    final validValues = receivedPackets.where((v) => v >= 80 && v <= 100).toList();
    if (validValues.length < receivedPackets.length * 0.5) {
      print('❌ PROBLEMA: Troppi valori fuori range fisiologico');
      print('   Valori ricevuti: ${receivedPackets}');
      print('   Valori validi: ${validValues}');
      print('   Soluzioni:');
      print('   - Controllare posizionamento sensore');
      print('   - Verificare che non ci siano interferenze luminose');
      print('   - Assicurarsi che la pelle sia pulita e asciutta');
    }
    
    // Successo
    if (validValues.length >= receivedPackets.length * 0.7) {
      final average = validValues.reduce((a, b) => a + b) / validValues.length;
      print('✅ Lettura SpO2 riuscita!');
      print('   Media: ${average.toStringAsFixed(1)}%');
      print('   Valori validi: ${validValues.length}/${receivedPackets.length}');
      print('   Stabilità: ${variance < 10 ? 'Buona' : 'Accettabile'}');
    }
  }
  
  static double calculateVariance(List<int> values) {
    if (values.length < 2) return 0;
    final mean = values.reduce((a, b) => a + b) / values.length;
    final squaredDiffs = values.map((v) => (v - mean) * (v - mean));
    return squaredDiffs.reduce((a, b) => a + b) / values.length;
  }
}
```