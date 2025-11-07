# 🌙 Come Rilevare l'Inizio del Sonno - Guida Completa

## 📋 Risposta Rapida

**Sì!** Ci sono **4 modi** per capire quando il device rileva l'inizio del sonno.

---

## 🎯 Metodo 1: Eventi Automatici (CONSIGLIATO) ⭐

### Usa gli Stream dedicati

```dart
// Ascolta quando inizia il sonno
chileafService.sleepOnsetStream.listen((onsetEvent) {
  print('🌙 SONNO INIZIATO!');
  print('   Timestamp: ${onsetEvent.timestamp}');
  print('   Fase iniziale: ${onsetEvent.initialPhase.displayName}');
  print('   Confidence: ${onsetEvent.confidence}%');
});

// Ascolta quando si sveglia
chileafService.sleepWakeStream.listen((wakeEvent) {
  print('😴 RISVEGLIO!');
  print('   Durata sonno: ${wakeEvent.duration.inMinutes} minuti');
});

// Ascolta cambi di fase
chileafService.sleepPhaseChangeStream.listen((phaseChange) {
  print('🔄 Cambio fase: ${phaseChange.fromPhase} → ${phaseChange.toPhase}');
});
```

### Esempio Completo UI

Vedi: **`lib/examples/sleep_onset_detection_example.dart`**

---

## 📊 Metodo 2: Analisi Activity Index Manuale

### Regole di Interpretazione

| Activity Index | Stato | Significato |
|---------------|-------|-------------|
| **> 20** | Sveglio 😴 | Movimento/attività |
| **1-20** | Sonno Leggero 🌙 | **INIZIO SONNO** ✅ |
| **0 (3+ consecutivi)** | Sonno Profondo 💤 | Deep sleep |

### Codice di Rilevamento

```dart
void detectSleepOnset(List<int> activityIndices, DateTime baseTime) {
  for (int i = 0; i < activityIndices.length - 1; i++) {
    int current = activityIndices[i];
    int next = activityIndices[i + 1];
    
    // Rileva transizione da sveglio a sonno
    if (current > 20 && next <= 20) {
      DateTime sleepStart = baseTime.add(Duration(minutes: (i + 1) * 5));
      print('🌙 SLEEP ONSET DETECTED at $sleepStart');
      print('   Transition: $current → $next');
      break;
    }
  }
}
```

---

## 📅 Metodo 3: Timestamp UTC (Prima Entry)

### Il Primo Timestamp = Inizio Tracciamento

Quando ricevi i dati 0x31, il **primo UTC timestamp** indica quando il device ha **iniziato a tracciare** il sonno:

```dart
// Nel parsing 0x31
if (isFirstPacket) {
  int utcTimestamp = _getLongParse(data, 3, 4);
  DateTime sleepTrackingStart = DateTime.fromMillisecondsSinceEpoch(
    utcTimestamp * 1000, 
    isUtc: true
  );
  
  print('🌙 Device started tracking sleep at: $sleepTrackingStart');
}
```

**Esempio dal tuo screenshot:**
```
utc:2025-10-19 03:24:05  ← Device rilevò sonno QUI!
action Index: light sleep
```

---

## 🔍 Metodo 4: Pattern Recognition Avanzato

### Rileva Pattern Multi-Indice

Cerca una sequenza di almeno 3 indici consecutivi ≤20:

```dart
bool detectSleepOnsetPattern(List<int> indices, int startIndex) {
  const int patternLength = 3;
  const int sleepThreshold = 20;
  
  if (startIndex + patternLength >= indices.length) return false;
  
  // Verifica transizione
  bool wasAwake = indices[startIndex] > sleepThreshold;
  
  // Verifica pattern sleep
  bool sleepPattern = true;
  for (int i = 1; i <= patternLength; i++) {
    if (indices[startIndex + i] > sleepThreshold) {
      sleepPattern = false;
      break;
    }
  }
  
  return wasAwake && sleepPattern;
}
```

---

## 🎨 Output Atteso nei Log

### Quando funziona correttamente:

```
🔄🌙💤 Requesting Sleep Data with OFFICIAL command 0x31...
📡 Command: ff 04 31 cb

🌙💤 Processing Sleep Data 0x31...
📅 First packet: UTC 1729313045 → 2025-10-19 03:24:05  ← INIZIO TRACKING

📊 Activity indices: [25, 22, 18, 15, 10, 8, 5, 0, 0, 0...]
                     ↑ awake    ↑ light sleep  ↑ deep

🔔 Detected 3 sleep events in this packet:

🌙 SLEEP ONSET at 2025-10-19 03:29:05  ← EVENTO RILEVATO!
   Initial Phase: Light Sleep 🌙
   Activity Index: 18
   Confidence: 87.5%

🔄 PHASE CHANGE at 2025-10-19 03:44:05
   From: Light Sleep 🌙
   To: Deep Sleep 💤
   Activity Index: 0

😴 WAKE UP at 2025-10-19 07:45:32
   Session Start: 2025-10-19 03:29:05
   Sleep Duration: 4h 16m
   Activity Index: 25
```

---

## 📱 Come Usare nell'App

### 1. Aggiungi il Widget

```dart
// In navigation_screen.dart o main.dart
import 'examples/sleep_onset_detection_example.dart';

// Aggiungi tab o route
SleepOnsetDetectionExample(
  chileafService: _chileafService,
)
```

### 2. Richiedi Dati

```dart
// Tap pulsante "Richiedi Dati Sleep"
await chileafService.requestSleepData31();
```

### 3. Ricevi Eventi Automatici

Gli stream emetteranno automaticamente eventi quando:
- 🌙 Rileva inizio sonno
- 😴 Rileva risveglio  
- 🔄 Rileva cambio fase

---

## 🧪 Test Rapido

### Verifica che tutto funzioni:

```dart
void testSleepOnsetDetection() {
  // 1. Setup listeners
  chileafService.sleepOnsetStream.listen((event) {
    print('✅ Sleep onset detected: $event');
  });
  
  // 2. Request data
  await chileafService.requestSleepData31();
  
  // 3. Wait for events
  // Events will be emitted automatically!
}
```

---

## 📊 Confronto Metodi

| Metodo | Precisione | Facilità | Real-time | Consigliato |
|--------|-----------|----------|-----------|-------------|
| **Eventi Auto** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ✅ | **SÌ** |
| Activity Index | ⭐⭐⭐⭐ | ⭐⭐⭐ | ✅ | Per debug |
| UTC Timestamp | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ❌ | Info base |
| Pattern Recog. | ⭐⭐⭐⭐⭐ | ⭐⭐ | ✅ | Avanzato |

---

## 🔍 Debug & Troubleshooting

### Non ricevi eventi?

1. **Verifica stream attivi:**
   ```dart
   print('Onset stream active: ${chileafService.sleepOnsetStream != null}');
   ```

2. **Controlla log per emoji:**
   ```
   🌙 SLEEP ONSET    ← Cerca questo!
   😴 WAKE UP
   🔄 PHASE CHANGE
   ```

3. **Verifica dati ricevuti:**
   ```dart
   chileafService.sleepData31Stream.listen((data) {
     print('Received ${data.length} sleep packets');
   });
   ```

---

## 💡 Tips & Best Practices

### ✅ DO:
- Usa `sleepOnsetStream` per rilevamento automatico
- Implementa confidence threshold (>70% = affidabile)
- Mostra notifiche all'utente per eventi importanti
- Log tutti gli eventi per debugging

### ❌ DON'T:
- Non fare polling manuale dei dati
- Non ignorare la confidence score
- Non assumere che primo indice = inizio sonno
- Non confondere tracking start con onset reale

---

## 📚 Riferimenti

### File Creati:
- **`lib/services/sleep_onset_detector.dart`** - Detector automatico
- **`lib/examples/sleep_onset_detection_example.dart`** - Esempio UI completo

### Classi Principali:
- `SleepOnsetDetector` - Analisi automatica eventi
- `SleepOnsetEvent` - Evento inizio sonno
- `SleepWakeEvent` - Evento risveglio
- `SleepPhaseChange` - Evento cambio fase

### Stream Disponibili:
- `sleepOnsetStream` - Inizio sonno
- `sleepWakeStream` - Risveglio
- `sleepPhaseChangeStream` - Cambi fase
- `sleepData31Stream` - Dati grezzi

---

## ✅ Checklist Implementazione

- [x] Sleep onset detector creato
- [x] Stream eventi aggiunti
- [x] Integrazione in ChileafExtendedService
- [x] Esempio UI completo
- [x] Documentazione completa
- [x] Memory leak fix (dispose streams)
- [x] Callback automatici configurati
- [x] Log dettagliati per debug

---

**Implementato da**: GitHub Copilot  
**Data**: 20 Ottobre 2025  
**Versione**: 2.0.0
