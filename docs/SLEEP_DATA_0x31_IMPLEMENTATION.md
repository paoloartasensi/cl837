# Implementazione Comando Sleep Data 0x31 (UFFICIALE)

## 📋 Problema Risolto

L'applicazione stava utilizzando il **comando 0x05** (legacy) per recuperare i dati del sonno, mentre l'app ufficiale del produttore utilizza il **comando 0x31** come documentato nella sezione 2.14 del SDK.

### Differenze Chiave

| Aspetto | Comando 0x05 (Vecchio) | Comando 0x31 (Nuovo/Ufficiale) |
|---------|------------------------|--------------------------------|
| **Granularità** | 1 minuto per byte | **5 minuti per byte** |
| **Risposta** | 0x05 con cmd 0x03 | **0x31 (dati) / 0x32 (fine)** |
| **Formato** | len + UTC + actions | **UTC + activity indices** |
| **Multi-packet** | END_TAG (0xFFFFFFFF) | **Sequence number** |
| **Stato** | Legacy/non documentato | **Ufficiale da SDK** |

---

## 🔧 Modifiche Implementate

### 1. **Nuovi Comandi BLE** (`lib/services/ble_protocol/official_commands.dart`)

```dart
/// Richiede dati sleep con comando 0x31 (UFFICIALE)
static List<int> getSleepData31() {
  return buildOfficialCommand(0x31, []);
}

/// Richiede dati sleep per timestamp specifico
static List<int> getSleepDataForTimestamp(int utcTimestamp) {
  return buildOfficialCommand(0x31, utcToBytes(utcTimestamp));
}
```

### 2. **Nuovi Modelli Dati** (`lib/models/historical_data.dart`)

#### `SleepData31`
- Rappresenta un pacchetto di dati sleep con granularità 5 minuti
- Ogni activity index copre 5 minuti di sonno
- Supporta sequence number per multi-packet

#### `SleepPhases31`
- Calcola fasi del sonno in minuti (non intervalli)
- Deep sleep: 3+ indici consecutivi = 0
- Light sleep: 1 ≤ indice ≤ 20
- Awake: indice > 20
- **Efficienza del sonno**: percentuale tempo dormito

### 3. **Parser 0x31** (`lib/chileaf_extended_service.dart`)

#### `_processSleepData31(List<int> data)`
- Distingue primo pacchetto (UTC) da successivi (sequence number)
- Estrae activity indices da byte 7 fino a checksum
- Accumula dati in buffer `_sleepData31Buffer`
- Log dettagliato per debugging

#### `_finalizeSleepData31()`
- Chiamato quando arriva segnale 0x32 (fine dati)
- Calcola statistiche totali da tutti i pacchetti
- Invia al stream `sleepData31Stream`
- Reset buffer e stato

### 4. **Routing Comandi**

Aggiunti case 0x31 e 0x32 nel routing:

```dart
case 0x31: // Sleep Data
  _processSleepData31(data);
  break;
case 0x32: // Sleep Data End
  _finalizeSleepData31();
  break;
```

### 5. **Stream Pubblico**

```dart
Stream<List<SleepData31>> get sleepData31Stream => 
    _sleepData31Controller.stream;
```

### 6. **Metodo Pubblico**

```dart
Future<void> requestSleepData31() async {
  // Reset buffer
  _sleepData31Buffer.clear();
  _isSleepData31Active = false;
  
  // Invia comando 0x31
  await _sendCommand(OfficialChileafCommands.getSleepData31());
}
```

### 7. **UI Widget** (`lib/widgets/device_control_widget.dart`)

Aggiunto pulsante dedicato per comando 0x31 con icona stella ⭐

---

## 📊 Formato Dati 0x31

### Richiesta APP → Device
```
[0xFF] [length] [0x31] [checksum]
```

### Risposta Device → APP

#### Primo Pacchetto (contiene UTC)
```
[0xFF] [length] [0x31] [UTC 4 bytes] [ACT-0] [ACT-1] ... [ACT-n] [checksum]
```

#### Pacchetti Successivi (sequence number)
```
[0xFF] [length] [0x31] [SEQ 4 bytes] [ACT-0] [ACT-1] ... [ACT-n] [checksum]
```

#### Segnale Fine
```
[0xFF] [length] [0x32] [checksum]
```

### Interpretazione Activity Index

- **>20**: Non dormendo (awake)
- **1-20**: Sonno leggero (light sleep)
- **0 (3+ consecutivi)**: Sonno profondo (deep sleep)

---

## 🎯 Come Usare

### Codice Base

```dart
// 1. Sottoscrivi lo stream
chileafService.sleepData31Stream.listen((sleepSessions) {
  for (var session in sleepSessions) {
    print('Session: ${session.timestamp}');
    print('Intervals: ${session.activityIndices.length}');
    print('Duration: ${session.activityIndices.length * 5} minutes');
    
    // Calcola fasi
    SleepPhases31 phases = session.calculateSleepPhases();
    print('Deep: ${phases.deepSleepMinutes} min');
    print('Light: ${phases.lightSleepMinutes} min');
    print('Awake: ${phases.awakeMinutes} min');
    print('Efficiency: ${phases.sleepEfficiency}%');
  }
});

// 2. Richiedi dati
await chileafService.requestSleepData31();
```

### Dall'UI

1. Apri "Device Control" tab
2. Scorri fino a "Sleep Data 0x31 (OFFICIAL) ⭐"
3. Tap sul pulsante
4. Osserva i log nella console
5. Dati arriveranno tramite stream

---

## 🔍 Debug & Logging

### Emoji di Log

- 🌙💤 = Sleep Data 0x31 processing
- 📅 = UTC timestamp parsing
- 📦 = Continuation packet
- 📊 = Activity indices extraction
- 💾 = Buffer operations
- 📈 = Sleep phases calculation
- ✅ = Finalization and stream emission
- ⚠️ = Warnings
- ❌ = Errors

### Esempio Log Atteso

```
🔄🌙💤 Requesting Sleep Data with OFFICIAL command 0x31...
📖 Protocol: SDK 2.14 - Sleep Data Request
📊 Granularity: 1 byte = 5 minutes
📡 Command: ff 04 31 cb
✅ Sleep data 0x31 request sent successfully

🌙💤 Processing Sleep Data 0x31 (OFFICIAL FORMAT)...
📅 First packet detected - parsing UTC timestamp
🕐 UTC timestamp: 1729313045 → 2025-10-19 03:24:05.000Z
📊 Activity indices extracted: 144 intervals (720 minutes)
📊 Sample indices: 0, 0, 0, 0, 0, 0, 10, 12, 8, 15...
📊 Activity breakdown:
   😴 Awake intervals (>20): 12 = 60 minutes
   🌙 Light sleep intervals (1-20): 80 = 400 minutes
   💤 Deep sleep indicators (0): 52 = 260 minutes

🌙✅ SLEEP DATA 0x32: End signal received
📊 Total sleep packets collected: 3
⏱️ Total sleep duration: 2160 minutes (432 x 5-minute intervals)
📈 TOTAL SLEEP PHASES:
   💤 Deep sleep: 780 minutes
   🌙 Light sleep: 1200 minutes
   😴 Awake: 180 minutes
   ✅ Sleep efficiency: 91.7%
📤 Sent 3 sleep packets to UI stream
```

---

## 🐛 Problemi Risolti

### Memory Leak Fix

Aggiunto `_sleepData31Controller.close()` nel metodo `dispose()`:

```dart
void dispose() {
  // ... altri controller
  _sleepData31Controller.close();
  _sleepHistoryController.close();
  _batteryInfoController.close();
  // ...
}
```

### Comando Legacy Deprecato

Il vecchio metodo è ora marcato come LEGACY:

```dart
/// ⚠️ DEPRECATO: Usa requestSleepData31() per il protocollo ufficiale
Future<void> requestOptimizedSleepHistory() async {
  debugPrint('Requesting Sleep History (LEGACY 0x05)...');
  // ...
}
```

---

## 📝 TODO Future

- [ ] Creare UI dedicata per visualizzare dati 0x31
- [ ] Grafici con granularità 5 minuti
- [ ] Comparazione sleep efficiency tra sessioni
- [ ] Export dati in formato CSV/JSON
- [ ] Integrazione con sleep_analysis_screen.dart
- [ ] Notifiche per sleep quality bassa

---

## 🔗 Riferimenti

- **SDK Documentation**: Sezione 2.14 - Sleep Data Request
- **Activity Index**: >20 awake, 1-20 light, 3×0 deep
- **Granularity**: 1 byte = 5 minutes
- **Response**: 0x31 (data) / 0x32 (end)

---

## ✅ Testing

Per verificare che l'implementazione funzioni:

1. **Connetti dispositivo**
2. **Tap "Sleep Data 0x31"** button
3. **Osserva console** per log dettagliati
4. **Verifica stream** riceve dati
5. **Confronta** con app ufficiale

### Output Atteso

- Risposta 0x31 con activity indices
- Segnale 0x32 alla fine
- Stream emette `List<SleepData31>`
- Fasi del sonno calcolate correttamente

---

**Implementato da**: GitHub Copilot
**Data**: 20 Ottobre 2025
**Versione**: 1.0.0
