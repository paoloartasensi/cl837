# CL837 Medical Sensors API - Guida Completa

Questa documentazione spiega come utilizzare l'API per i sensori medici del dispositivo CL837 in applicazioni esterne.

## 📋 Indice

1. [Panoramica](#panoramica)
2. [Installazione](#installazione)
3. [Setup BLE](#setup-ble)
4. [Utilizzo API](#utilizzo-api)
5. [Sensori Disponibili](#sensori-disponibili)
6. [Esempi Pratici](#esempi-pratici)
7. [Troubleshooting](#troubleshooting)

## 🔍 Panoramica

L'API `CL837MedicalSensorsAPI` fornisce accesso completo ai sensori medici del dispositivo CL837:

- **SpO2**: Saturazione ossigeno nel sangue con terminazione intelligente
- **HRV**: Variabilità heart rate con analisi RR-intervals
- **Temperatura**: Monitoraggio temperatura polso e corporea
- **Heart Rate**: Frequenza cardiaca per analisi HRV

### Caratteristiche Principali

- ✅ **Stream-based**: Dati in tempo reale tramite Dart Streams
- ✅ **Terminazione intelligente**: SpO2 con stop automatico dopo 2 letture valide
- ✅ **Calcoli HRV avanzati**: RMSSD, SDNN, pNN50, indice stress
- ✅ **Gestione errori robusta**: Retry automatici e cleanup sicuro
- ✅ **API pulita**: Metodi semplici e intuitivi

## 📦 Installazione

### 1. Aggiungi i file al tuo progetto Flutter:

```
lib/
  services/
    cl837_medical_sensors_api.dart    # API principale
  examples/
    cl837_sensors_example.dart        # Esempio completo
```

### 2. Aggiungi dipendenze in `pubspec.yaml`:

```yaml
dependencies:
  flutter_blue_plus: ^1.14.6
  # Altre dipendenze del tuo progetto
```

### 3. Importa l'API:

```dart
import 'package:your_app/services/cl837_medical_sensors_api.dart';
```

## 🔗 Setup BLE

### 1. Connessione al dispositivo CL837:

```dart
// Trova e connetti al dispositivo CL837
final device = await _findCL837Device();
await device.connect();

// Scopri servizi BLE
final services = await device.discoverServices();

// Trova caratteristiche Chileaf (UUID: aae28f00-71b5-42a1-8c3c-f9cf6ac969d0)
BluetoothCharacteristic? rxChar; // aae28f02 (Write to device)
BluetoothCharacteristic? txChar; // aae28f01 (Read from device, NOTIFY)

for (final service in services) {
  if (service.uuid.toString().contains('aae28f00')) {
    for (final char in service.characteristics) {
      if (char.uuid.toString().contains('aae28f02')) {
        rxChar = char;
      } else if (char.uuid.toString().contains('aae28f01')) {
        txChar = char;
        await char.setNotifyValue(true); // IMPORTANTE!
      }
    }
  }
}
```

### 2. Inizializza l'API:

```dart
final sensorsAPI = CL837MedicalSensorsAPI(
  rxCharacteristic: rxChar,
  txCharacteristic: txChar,
);

// Setup listener per notifiche BLE
txChar.onValueReceived.listen((data) {
  sensorsAPI.processIncomingData(data);
});
```

## 🎯 Utilizzo API

### Avvio/Stop Sensori

```dart
// SpO2 - 50 secondi max, terminazione anticipata con 2 letture valide
await sensorsAPI.startSpO2Measurement(
  maxDuration: Duration(seconds: 50),
  enableEarlyTermination: true,
);

// HRV - Sessione 5 minuti standard
await sensorsAPI.startHRVSession(
  sessionDuration: Duration(minutes: 5),
);

// Temperatura - Monitoraggio continuo ogni 5 secondi
await sensorsAPI.startTemperatureMonitoring(
  interval: Duration(seconds: 5),
  includeBodyTemperature: true,
  includeWristTemperature: true,
);

// Stop tutti i sensori
await sensorsAPI.stopAllMeasurements();
```

### Ascolto Dati

```dart
// Stream SpO2
sensorsAPI.spO2Stream.listen((SpO2Reading reading) {
  print('SpO2: ${reading.spo2Percentage}%');
  print('Segnale: ${reading.signalQuality}/15');
  print('Valido: ${reading.isValid}');
  print('Indossato: ${reading.isWearing}');
  print('Postura corretta: ${reading.correctWristPosture}');
});

// Stream HRV
sensorsAPI.hrvStream.listen((HRVReading reading) {
  print('RMSSD: ${reading.rmssd.toStringAsFixed(1)} ms');
  print('SDNN: ${reading.sdnn.toStringAsFixed(1)} ms');
  print('pNN50: ${reading.pnn50.toStringAsFixed(1)}%');
  print('Stress Index: ${reading.stressIndex.toStringAsFixed(1)}%');
  print('RR-Intervals: ${reading.rrIntervals.length} campioni');
});

// Stream Temperatura
sensorsAPI.temperatureStream.listen((TemperatureReading reading) {
  print('Polso: ${reading.wristTemperature.toStringAsFixed(1)}°C');
  if (reading.bodyTemperature != null) {
    print('Corporea: ${reading.bodyTemperature!.toStringAsFixed(1)}°C');
  }
  print('Valido: ${reading.isValid}');
});

// Stream Heart Rate (per HRV)
sensorsAPI.heartRateStream.listen((HeartRateReading reading) {
  print('HR: ${reading.heartRate} bpm');
  print('RR-Interval: ${reading.rrInterval} ms');
  print('Valido: ${reading.isValid}');
});
```

## 🔬 Sensori Disponibili

### 🫁 SpO2 (Saturazione Ossigeno)

**Caratteristiche:**
- Durata massima: 50 secondi (configurabile)
- Terminazione automatica: 2 letture consecutive valide
- LED rosso attivo durante misurazione
- Validazione qualità segnale (>8/15 per letture affidabili)

**Criteri validità:**
- SpO2: 70-100%
- Segnale: >8/15
- Dispositivo indossato
- Postura corretta

```dart
class SpO2Reading {
  final DateTime timestamp;
  final int? spo2Percentage;      // 70-100% o null
  final int signalQuality;        // 0-15 (>8 = buono)
  final int? heartRate;           // bpm durante misurazione
  final bool isWearing;           // Dispositivo indossato
  final bool correctWristPosture; // Postura corretta
  final bool isValid;             // Lettura valida complessiva
}
```

### 💓 HRV (Variabilità Heart Rate)

**Metriche calcolate:**
- **RMSSD**: Root Mean Square of Successive Differences
- **SDNN**: Standard Deviation of NN intervals  
- **pNN50**: Percentage of NN intervals >50ms different
- **Stress Index**: Indice stress calcolato (0-100%)

**Durata consigliata:** 5+ minuti per analisi accurata

```dart
class HRVReading {
  final DateTime timestamp;
  final double rmssd;           // ms
  final double sdnn;            // ms  
  final double pnn50;           // %
  final List<int> rrIntervals;  // ms
  final double stressIndex;     // % (0-100)
}
```

### 🌡️ Temperatura

**Tipi supportati:**
- **Temperatura polso**: Sempre disponibile
- **Temperatura corporea**: Se supportata dal firmware

**Range valido:** 20-50°C

```dart
class TemperatureReading {
  final DateTime timestamp;
  final double wristTemperature;  // °C
  final double? bodyTemperature;  // °C (opzionale)
  final bool isValid;             // Range 20-50°C
}
```

### 💗 Heart Rate

**Per analisi HRV:**
- Frequenza cardiaca instantanea
- RR-intervals in millisecondi
- Validazione range: 40-200 bpm

```dart
class HeartRateReading {
  final DateTime timestamp;
  final int heartRate;    // bpm
  final int rrInterval;   // ms
  final bool isValid;     // 40-200 bpm, RR 300-2000ms
}
```

## 📝 Esempi Pratici

### Esempio 1: Misurazione SpO2 Completa

```dart
Future<void> measureSpO2() async {
  final completer = Completer<SpO2Reading>();
  
  // Ascolta per la prima lettura valida
  final subscription = sensorsAPI.spO2Stream.listen((reading) {
    if (reading.isValid && !completer.isCompleted) {
      completer.complete(reading);
    }
  });
  
  try {
    // Avvia misurazione
    await sensorsAPI.startSpO2Measurement();
    
    // Aspetta prima lettura valida (max 60 secondi)
    final result = await completer.future.timeout(Duration(seconds: 60));
    
    print('SpO2 misurato: ${result.spo2Percentage}%');
    print('Qualità segnale: ${result.signalQuality}/15');
    
  } catch (e) {
    print('Errore misurazione SpO2: $e');
  } finally {
    subscription.cancel();
    await sensorsAPI.stopSpO2Measurement();
  }
}
```

### Esempio 2: Sessione HRV con Analisi

```dart
Future<Map<String, double>> analyzeHRV() async {
  final hrvData = <HRVReading>[];
  
  // Raccogli dati per 5 minuti
  final subscription = sensorsAPI.hrvStream.listen((reading) {
    hrvData.add(reading);
    print('HRV update: RMSSD=${reading.rmssd.toStringAsFixed(1)}ms');
  });
  
  try {
    await sensorsAPI.startHRVSession(
      sessionDuration: Duration(minutes: 5),
    );
    
    // Aspetta completamento
    await Future.delayed(Duration(minutes: 5, seconds: 10));
    
    // Calcola medie
    if (hrvData.isNotEmpty) {
      final avgRMSSD = hrvData.map((r) => r.rmssd).reduce((a, b) => a + b) / hrvData.length;
      final avgSDNN = hrvData.map((r) => r.sdnn).reduce((a, b) => a + b) / hrvData.length;
      final avgStress = hrvData.map((r) => r.stressIndex).reduce((a, b) => a + b) / hrvData.length;
      
      return {
        'rmssd': avgRMSSD,
        'sdnn': avgSDNN,
        'stress': avgStress,
        'samples': hrvData.length.toDouble(),
      };
    }
    
  } finally {
    subscription.cancel();
    await sensorsAPI.stopHRVSession();
  }
  
  return {};
}
```

### Esempio 3: Monitoraggio Temperatura Continuo

```dart
StreamSubscription<TemperatureReading>? monitorTemperature() {
  sensorsAPI.startTemperatureMonitoring(interval: Duration(seconds: 30));
  
  return sensorsAPI.temperatureStream.listen((reading) {
    final temp = reading.wristTemperature;
    
    if (temp > 37.5) {
      print('⚠️ Temperatura elevata: ${temp.toStringAsFixed(1)}°C');
      // Invia allarme
    } else if (temp < 35.0) {
      print('❄️ Temperatura bassa: ${temp.toStringAsFixed(1)}°C');
    } else {
      print('🌡️ Temperatura normale: ${temp.toStringAsFixed(1)}°C');
    }
  });
}
```

### Esempio 4: Widget UI Completo

Vedi il file `lib/examples/cl837_sensors_example.dart` per un esempio completo di widget Flutter che implementa tutte le funzionalità.

## 🔧 Troubleshooting

### Problemi Comuni

**1. Dispositivo non trovato**
```dart
// Assicurati che il Bluetooth sia acceso
if (await FlutterBluePlus.isOn == false) {
  await FlutterBluePlus.turnOn();
}

// Scansiona per dispositivi CL837
final devices = await FlutterBluePlus.scanResults.first;
final cl837 = devices.firstWhere(
  (result) => result.device.name.contains('CL837') || 
              result.device.name.contains('Chileaf'),
);
```

**2. Caratteristiche non trovate**
```dart
// Verifica UUID servizio corretto
const serviceUuid = 'aae28f00-71b5-42a1-8c3c-f9cf6ac969d0';
const rxUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0';
const txUuid = 'aae28f01-71b5-42a1-8c3c-f9cf6ac969d0';

// Debug servizi disponibili
for (final service in services) {
  print('Service: ${service.uuid}');
  for (final char in service.characteristics) {
    print('  Char: ${char.uuid}');
  }
}
```

**3. Nessun dato ricevuto**
```dart
// Verifica notifiche attivate
await txCharacteristic.setNotifyValue(true);

// Verifica listener setup
txCharacteristic.onValueReceived.listen((data) {
  print('Data received: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
  sensorsAPI.processIncomingData(data);
});
```

**4. Letture SpO2 non valide**
```dart
// Controlla condizioni:
// - Dispositivo ben posizionato sul polso
// - Polso fermo durante misurazione
// - Buon contatto con la pelle
// - Attendi stabilizzazione (3-4 secondi)

sensorsAPI.spO2Stream.listen((reading) {
  if (!reading.isValid) {
    if (!reading.isWearing) print('❌ Dispositivo non indossato');
    if (!reading.correctWristPosture) print('❌ Postura polso incorretta');
    if (reading.signalQuality <= 8) print('❌ Segnale debole: ${reading.signalQuality}/15');
  }
});
```

**5. HRV con pochi campioni**
```dart
// Aumenta durata sessione per più campioni
await sensorsAPI.startHRVSession(
  sessionDuration: Duration(minutes: 10), // Invece di 5
);

// Verifica heart rate stabile
sensorsAPI.heartRateStream.listen((reading) {
  if (reading.heartRate < 40 || reading.heartRate > 200) {
    print('⚠️ Heart rate fuori range: ${reading.heartRate}');
  }
});
```

### Debug e Logging

```dart
// Attiva debug per vedere tutti i comandi BLE
sensorsAPI.debugMode = true;

// Log stream dati per troubleshooting
sensorsAPI.spO2Stream.listen((reading) {
  print('SpO2 Debug: ${reading}');
});

sensorsAPI.hrvStream.listen((reading) {
  print('HRV Debug: ${reading}');
});

sensorsAPI.temperatureStream.listen((reading) {
  print('Temp Debug: ${reading}');
});
```

## 🏆 Best Practices

1. **Gestione lifecycle**: Sempre chiamare `dispose()` quando non più necessario
2. **Error handling**: Usa try-catch per tutte le operazioni async
3. **Stream management**: Cancella subscription quando non più necessarie
4. **UI responsiveness**: Usa FutureBuilder/StreamBuilder per UI reactive
5. **Battery optimization**: Ferma sensori non utilizzati
6. **Data validation**: Controlla sempre `isValid` prima di usare i dati

## 📞 Supporto

Per problemi o domande:
1. Controlla questa documentazione
2. Verifica gli esempi in `lib/examples/`
3. Abilita debug mode per logging dettagliato
4. Verifica specifiche hardware CL837

---

**Versione API**: 1.0  
**Compatibilità**: CL837, Flutter 3.0+  
**Licenza**: Da specificare nel progetto
