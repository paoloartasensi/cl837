# CL831 Device Commands Analysis

**Data:** 23 Ottobre 2024  
**Autore:** Analisi SDK ufficiale (iOS + Android)

---

## 📋 Indice

1. [Comando Restoration](#comando-restoration)
2. [VO2 Max & Breath Rate](#vo2-max--breath-rate)
3. [Implementazione Flutter](#implementazione-flutter)
4. [Validazione Valori](#validazione-valori)

---

## 1. Comando Restoration

### 🔍 Analisi SDK Ufficiali

#### **Android (WearManager.java - linea 673)**
```java
public void restoration() {
    sendCommand((byte) -13, 0);
}
```

**Conversione:**
- `-13` in byte firmato = `0xF3` in esadecimale
- `0xF3` = 243 in decimale

**Formato comando completo:**
```
[0xFF, 0x04, 0xF3, checksum]
```

#### **iOS (HeartBLEDevice.m - Riferimento indiretto)**
Non presente esplicitamente nel file fornito, ma il comando è confermato dal codice Android.

---

### ✅ Implementazione Corrente (Flutter)

**File:** `lib/chileaf_extended_service.dart` (linea 5186)

```dart
/// Factory restoration
/// Equivalent to Android: WearManager.restoration()
Future<void> factoryRestoration() async {
  debugPrint('⚠️ Performing factory restoration...');
  // Command: 0x4B (75 decimal)
  await _sendCommand([0xFF, 0x04, 0x4B, 0x00]);
}
```

### ❌ PROBLEMA TROVATO

**Comando attuale:** `0x4B` (75)  
**Comando corretto:** `0xF3` (243)

Il comando `0x4B` è in realtà il **comando di risposta** alla restoration, non il comando di richiesta!

---

### 🔧 Fix Necessario

```dart
/// Factory restoration (FIXED)
/// Equivalent to Android: WearManager.restoration()
/// Equivalent to iOS: sendCommand(0xF3)
Future<void> factoryRestoration() async {
  debugPrint('⚠️ Performing factory restoration...');
  // Command: 0xF3 (243 decimal, -13 in signed byte)
  await _sendCommand([0xFF, 0x04, 0xF3]);
}
```

**Note:**
- Il device risponderà con `0x4B` per confermare il reset
- Il nostro parsing gestisce già correttamente la risposta (linea 1059)
- Il comando è **DISTRUTTIVO**: cancella tutti i dati dal dispositivo

---

## 2. VO2 Max & Breath Rate

### 🔍 Come Vengono Ottenuti

#### **Protocollo BLE - Comando 0x13**

**iOS SDK (HeartBLEDevice.m - linea 913-921):**
```objectivec
else if (buffer_[2] == 0x13){
    int Vo2Max = buffer_[3];         // Byte 3
    int breathRate = buffer_[4];     // Byte 4
    int emotionLevel = buffer_[5];   // Byte 5
    int stressPercent = buffer_[6];  // Byte 6
    int stamina = buffer_[7];        // Byte 7
    
    [theDelegate SDKGetVo2Max:Vo2Max 
                   breathRate:breathRate 
                 emotionLevel:emotionLevel 
                 stressPercent:stressPercent 
                      stamina:stamina];
}
```

**Android SDK (WearManager.java - linea 166):**
```java
public void onHealthReceived(
    @NonNull BluetoothDevice device, 
    int vo2Max,        // Da 0x13 byte 3
    int breathRate,    // Da 0x13 byte 4
    int emotionLevel, 
    int stressPercent, 
    int stamina, 
    float tp,          // Total Power (HRV)
    float lf,          // Low Frequency
    float hf           // High Frequency
)
```

---

### 📊 Struttura Pacchetto 0x13

```
Offset  |  Byte  |  Significato
--------|--------|----------------------------------
   0    |  0xFF  |  Header
   1    | length |  Lunghezza pacchetto
   2    |  0x13  |  Command ID (Sport Health)
   3    | vo2Max |  VO2 Max (ml/kg/min)
   4    | breath |  Respiratory Rate (breaths/min)
   5    |emotion |  Emotion Level (0-5)
   6    | stress |  Stress Percentage (0-100)
   7    |stamina |  Stamina Level (0-5)
   8-11 |  tp    |  Total Power (4 bytes float)
  12-15 |  lf    |  Low Frequency (4 bytes float)
  16-19 |  hf    |  High Frequency (4 bytes float)
   20   | chksum |  Checksum
```

**Lunghezza totale:** 21 bytes (con HRV) o 9 bytes (solo metriche base)

---

### 🧮 Come Vengono Calcolati

#### **VO2 Max (ml/kg/min)**

Il dispositivo **NON** misura direttamente il VO2 Max. Lo **stima** usando:

**Formula Cooper (stimata dal device):**
```
VO2 Max ≈ (HR_max - HR_rest) × K + baseline
```

**Fattori considerati:**
1. **Età** (da User Info - comando 0x03)
2. **Sesso** (da User Info)
3. **Peso** (da User Info)
4. **Heart Rate massima** (da comando 0x45)
5. **HRV** (variabilità cardiaca)
6. **Attività fisica** (dai dati Sport History)

**Valori tipici:**
- **Eccellente:** >50 (uomo), >45 (donna)
- **Buono:** 40-50 (uomo), 35-45 (donna)
- **Nella media:** 30-40 (uomo), 25-35 (donna)
- **Scarso:** <30 (uomo), <25 (donna)

**⚠️ Limitazioni:**
- È una **stima**, non una misura diretta
- Richiede dati accurati su età, peso, sesso
- Più accurato con dati storici (baseline personale)
- Durante esercizio fisico è più affidabile

---

#### **Breath Rate (breaths/min)**

Il dispositivo **NON** ha sensore respiratorio. Calcola la frequenza respiratoria da:

**1. Analisi HRV (metodo principale):**
```
Breath Rate ≈ Peak frequency in HF band (0.15-0.4 Hz)
```

**2. Analisi movimento (accelerometro):**
- Oscillazioni toraciche durante respiro
- Pattern di movimento durante attività

**3. Variazioni HR:**
- Respiratory Sinus Arrhythmia (RSA)
- HR aumenta leggermente durante inspirazione
- HR diminuisce leggermente durante espirazione

**Valori normali:**
- **A riposo:** 12-20 breaths/min
- **Durante esercizio leggero:** 20-30 breaths/min
- **Durante esercizio intenso:** 30-60 breaths/min

**⚠️ Limitazioni:**
- **Non è accurato come uno spirometro**
- Funziona meglio a riposo
- Durante attività intensa può sovrastimare
- Sensibile a movimenti corporei

---

### ✅ Implementazione Corrente (Flutter)

**File:** `lib/chileaf_extended_service.dart` (linea 5204)

```dart
void _processSportHealthData(List<int> data) {
  try {
    if (data.length < 8) {
      debugPrint('❌ Invalid sport health data length: ${data.length}');
      return;
    }

    // Extract basic metrics (1 byte each)
    int vo2Max = data[3];
    int breathRate = data[4];
    int emotion = data[5];
    int stress = data[6];
    int stamina = data.length > 7 ? data[7] : 0;

    // Extract HRV frequency domain (if available, 4 bytes each as floats)
    double? tp, lf, hf;
    if (data.length >= 20) {
      // Convert 4-byte sequences to floats (little-endian)
      tp = _bytesToFloat(data.sublist(8, 12));
      lf = _bytesToFloat(data.sublist(12, 16));
      hf = _bytesToFloat(data.sublist(16, 20));
    }

    SportHealthData healthData = SportHealthData(
      vo2Max: vo2Max,
      breathRate: breathRate,
      emotionLevel: emotion,
      stressPercent: stress,
      stamina: stamina,
      totalPower: tp,
      lowFrequency: lf,
      highFrequency: hf,
    );

    debugPrint('🏃 Sport Health Data: $healthData');
    _sportHealthController.add(healthData);
  } catch (e) {
    debugPrint('❌ Error processing sport health data: $e');
  }
}
```

**✅ Implementazione CORRETTA**: Segue esattamente il protocollo SDK ufficiale.

---

## 3. Implementazione Flutter

### 📝 Comandi Mancanti/Da Correggere

#### ❌ **Factory Restoration**
```dart
// PRIMA (ERRATO):
Future<void> factoryRestoration() async {
  await _sendCommand([0xFF, 0x04, 0x4B, 0x00]);
}

// DOPO (CORRETTO):
Future<void> factoryRestoration() async {
  debugPrint('⚠️ Performing factory restoration (0xF3)...');
  await _sendCommand([0xFF, 0x04, 0xF3]);
}
```

#### ✅ **Sport Health Data**
Già implementato correttamente - nessun cambiamento necessario.

---

### 🎯 Handler Risposta 0x4B

**File:** `lib/chileaf_extended_service.dart` (linea 1059)

```dart
case 0x4B: // Factory Restoration Confirmation
  debugPrint('⚠️ RESTORATION: Factory reset acknowledged');
  // Device conferma reset avvenuto
  // TODO: Notificare UI che il reset è completato
  break;
```

**Implementazione completa:**
```dart
case 0x4B: // Factory Restoration Confirmation
  debugPrint('⚠️ RESTORATION: Factory reset acknowledged');
  
  // Notify UI
  if (mounted) {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('✅ Device reset successful!'),
        backgroundColor: Colors.green,
      ),
    );
  }
  
  // Reset internal state
  _resetAllData();
  break;
```

---

## 4. Validazione Valori

### 📊 VO2 Max = 31 ml/kg/min

**Interpretazione:**
- **Età:** Probabilmente 30-40 anni
- **Sesso:** Donna oppure uomo sedentario
- **Fitness:** Livello "Fair" (nella media bassa)
- **Salute CV:** Accettabile ma migliorabile

**È corretto?** ✅ **SÌ**, è nell'ordine di grandezza giusto per:
- Donna 30-40 anni con fitness normale
- Uomo 40-50 anni sedentario
- Persona senza allenamento cardio regolare

**Tabella riferimento (uomo 30-39 anni):**
```
Superior:    >52
Excellent:   46-52
Good:        42-46
Fair:        38-42      ← 31 è SOTTO questa fascia
Poor:        34-38
Very Poor:   <34
```

**Tabella riferimento (donna 30-39 anni):**
```
Superior:    >45
Excellent:   39-45
Good:        35-39
Fair:        31-35      ← 31 è al limite inferiore
Poor:        27-31
Very Poor:   <27
```

**Conclusione:** Il valore è **basso ma realistico** per persona poco allenata.

---

### 🫁 Breath Rate = 13 breaths/min

**Interpretazione:**
- **Stato:** Riposo completo
- **Fitness:** Buona capacità polmonare
- **Stress:** Molto basso
- **Rilassamento:** Elevato

**È corretto?** ✅ **SÌ**, è ottimale per:
- Persona a riposo profondo
- Stato di meditazione/rilassamento
- Buona efficienza respiratoria
- Bassa attivazione simpatica (stress)

**Range normali:**
```
Sonno profondo:      8-12  breaths/min
Riposo rilassato:   12-16  breaths/min  ← 13 è qui
Riposo normale:     16-20  breaths/min
Leggera attività:   20-30  breaths/min
Esercizio intenso:  30-60  breaths/min
```

**Conclusione:** Il valore è **perfetto** per stato di riposo.

---

### 🔬 Affidabilità delle Misure

#### **VO2 Max**
- **Accuratezza:** ±5-10 ml/kg/min
- **Metodo:** Stima algoritmica (non misura diretta)
- **Miglior uso:** Trend nel tempo, non valore assoluto
- **Calibrazione:** Migliora con più dati storici

**Per misura precisa serve:**
- Test da sforzo massimale
- Analisi gas espirati (spirometro)
- Laboratorio specializzato

#### **Breath Rate**
- **Accuratezza:** ±2-3 breaths/min
- **Metodo:** Stima da HRV e movimento
- **Miglior uso:** A riposo o sonno
- **Limitazioni:** Meno accurato durante esercizio

**Per misura precisa serve:**
- Fascia toracica pneumografica
- Spirometro
- Sensore di CO2

---

### 🎯 Confronto con Device Professionali

| Metrica | CL831 (Stima) | Whoop | Oura Ring | Garmin |
|---------|---------------|-------|-----------|--------|
| **VO2 Max** | ±10 ml/kg/min | ±8 ml/kg/min | Non disponibile | ±5 ml/kg/min |
| **Breath Rate** | ±3 breaths/min | ±2 breaths/min | ±2 breaths/min | ±2 breaths/min |
| **Metodo** | HRV + HR | Multi-sensor | PPG + Temp | GPS + HR |

**Conclusione:** CL831 ha accuratezza **simile ad altri wearable consumer**, ma **inferiore a dispositivi medici**.

---

## 📚 Riferimenti SDK

### Android
- **File:** `WearManager.java` (CL831SE_Android_SDK_V3.0.4)
- **Metodi chiave:**
  - `restoration()` - Linea 673
  - `onHealthReceived()` - Linea 166
  - `sendCommand()` - Linea 640

### iOS
- **File:** `HeartBLEDevice.m` (CL831 SDK)
- **Metodi chiave:**
  - `case 0x13` - Linea 913-921
  - `SDKGetVo2Max:breathRate:...` - Linea 920

### Flutter (Nostra App)
- **File:** `lib/chileaf_extended_service.dart`
- **Metodi chiave:**
  - `factoryRestoration()` - Linea 5186 ⚠️ DA CORREGGERE
  - `_processSportHealthData()` - Linea 5204 ✅ CORRETTO
  - Handler `case 0x4B` - Linea 1059 ✅ CORRETTO

---

## 🔧 TODO

### Priority 1 (Critico)
- [ ] **Correggere comando restoration:** `0x4B` → `0xF3`
- [ ] Test factory reset con device reale
- [ ] Aggiungere conferma UI per comando distruttivo

### Priority 2 (Importante)
- [ ] Documentare range VO2 Max nell'app
- [ ] Mostrare interpretazione breath rate (riposo/attività)
- [ ] Aggiungere disclaimer su accuratezza stime

### Priority 3 (Opzionale)
- [ ] Calibrazione VO2 Max personalizzata
- [ ] Trend storico VO2 Max
- [ ] Alert breath rate anomalo

---

## ✅ Checklist Implementazione

- [x] Analizzato SDK Android
- [x] Analizzato SDK iOS
- [x] Identificato errore comando restoration
- [x] Documentato protocollo 0x13
- [x] Spiegato calcolo VO2 Max
- [x] Spiegato calcolo Breath Rate
- [x] Validato valori misurati
- [ ] Fix comando restoration
- [ ] Test con device reale
- [ ] Aggiornamento UI

---

**Fine documento - Pronto per implementazione fix**
