# 🔍 ANALISI REVERSE ENGINEERING COMPLETA - CHILEAF SDK UFFICIALI

## 📋 SOMMARIO ESECUTIVO

Attraverso l'analisi dei due SDK ufficiali Android di Chileaf abbiamo estratto informazioni complete su:
- **CL831SE_Android_SDK_V3.0.4**: SDK ufficiale per dispositivi CL831
- **XFITNESS2**: App ufficiale Chileaf decompilata (X-Fitness)

Entrambe le fonti confermano la stessa implementazione del protocollo BLE.

---

## 🎯 UUID UFFICIALI CONFERMATI

### Service & Characteristics (IDENTICI in entrambi gli SDK)

```java
// Service principale Chileaf
SERVICE_UUID = "AAE28F00-71B5-42A1-8C3C-F9CF6AC969D0"

// Characteristics per comunicazione BLE
RX_CHAR_UUID = "AAE28F01-71B5-42A1-8C3C-F9CF6AC969D0"  // Read from device (NOTIFY)
TX_CHAR_UUID = "AAE28F02-71B5-42A1-8C3C-F9CF6AC969D0"  // Write to device (WRITE)

// Characteristics aggiuntive (XFITNESS2)
SPEC_CHAR_UUID = "AAE21541-71B5-42A1-8C3C-F9CF6AC969D0"
CUSTOM_CHAR_UUID = "AAE21542-71B5-42A1-8C3C-F9CF6AC969D0"

// Standard BLE Services
HR_SERVICE_UUID = "0000180D-0000-1000-8000-00805f9b34fb"
BATTERY_SERVICE_UUID = "0000180F-0000-1000-8000-00805f9b34fb"
DEVICE_INFO_SERVICE_UUID = "0000180A-0000-1000-8000-00805f9b34fb"
```

**✅ VERIFICA**: Il nostro codice Flutter usa già gli UUID corretti!

---

## 📡 PROTOCOLLO COMANDI UFFICIALE

### Struttura Frame (CONFERMATA da entrambi gli SDK)

```java
// Formato standard:
[0xFF] [length] [command] [parameters...] [checksum]

// Implementazione sendCommand():
private void sendCommand(final byte cmd, final int... values) {
    if (values != null) {
        int len = values.length + 4;
        command = HexUtil.compose(255, len, cmd);  // 0xFF + length + cmd
        byte[] bytes = HexUtil.compose(values);
        result = HexUtil.append(command, bytes);
    } else {
        result = HexUtil.compose(255, 4, cmd);     // 0xFF + 4 + cmd (no params)
    }
    byte check = this.checkSum(result);
    command = HexUtil.append(result, check);
    this.writeTxCharacteristic(command);
}
```

---

## 🎛️ MAPPATURA COMANDI COMPLETA

| Comando | Hex | Metodo Ufficiale | Parametri | Descrizione |
|---------|-----|------------------|-----------|-------------|
| **Timestamp** | `0x08` | `setUTCTime(long stamp)` | 4 bytes UTC | Sincronizza orario |
| **Shutdown** | `0xF1` (-15) | `shutdown()` | 0 | Spegne dispositivo |
| **🔄 RESET** | `0xF3` (-13) | `restoration()` | 0 | **COMANDO RESET UFFICIALE** |
| **Bluetooth Off** | `0x3F` (63) | `setBluetoothDisabled()` | 2 | Disabilita BT |
| **User Info Get** | `0x03` | `getUserInfo()` | 0 | Richiede info utente |
| **User Info Set** | `0x04` | `setUserInfo(...)` | 9 bytes | Imposta profilo utente |
| **Sleep History** | `0x05` | `getHistoryOfSleep()` | 2 | Storia del sonno |
| **🩸 SpO2 Control** | `0x37` (55) | `setBloodOxygen(int mode)` | mode,0 | **COMANDO SPO2 UFFICIALE** |
| **Exercise History** | `0x16` (22) | `getHistoryOfSport()` | 0 | Storia esercizi |
| **HR Record List** | `0x21` (33) | `getHistoryOfHRRecord()` | 0 | Lista record HR |
| **HR Data Detail** | `0x22` (34) | `getHistoryOfHRData(stamp)` | 1 + 4 bytes | Dati HR dettagliati |
| **RR Record List** | `0x24` (36) | `getHistoryOfRRRecord()` | - | Lista record RR |
| **RR Data Detail** | `0x25` (37) | `getHistoryOfRRData(stamp)` | 1 + 4 bytes | Dati RR dettagliati |
| **Interval Steps** | `0x40` (64) | `getIntervalSteps()` | 0 | Passi intervallari |
| **Single Tap** | `0x42` (66) | `getSingleTapRecords()` | 0 | Record singoli tap |
| **HR Status Get** | `0x46` (70) | `getHeartRateStatus()` | 0 | Status HR |
| **HR Status Set** | `0x46` (70) | `setHeartRateStatus(...)` | 1,min,max,goal | Imposta limiti HR |
| **Single Record** | `0x49` (73) | `getHistoryOfSingleRecord(stamp)` | 4 bytes | Record singolo |
| **HR Alarm Set** | `0x57` (87) | `setHeartRateAlarm(bool)` | 1/0 | Allarme HR |
| **HR Alarm Get** | `0x5B` (91) | `getHeartRateAlarm()` | 0 | Legge allarme HR |
| **HR Max Set** | `0x74` (116) | `setHeartRateMax(max)` | 0,6,max | Frequenza max |
| **HR Max Get** | `0x75` (117) | `getHeartRateMax()` | 0,6 | Legge freq max |
| **3D Frequency Set** | `0x74` (116) | `set3DFrequency(freq)` | 0,11,freq | Freq sensore 3D |
| **3D Frequency Get** | `0x75` (117) | `get3DFrequency()` | 0,11 | Legge freq 3D |
| **3D Status Set** | `0x74` (116) | `set3DEnabled(bool)` | 0,12,1/0 | Abilita 3D |
| **3D Status Get** | `0x75` (117) | `get3DStatus()` | 0,12 | Status 3D |
| **6D Frequency Get** | `0x61` (97) | `get6DFrequency()` | 0 | Frequenza 6D |
| **6D Frequency Set** | `0x62` (98) | `set6DFrequency(freq)` | freq | Imposta freq 6D |
| **3D History** | `0x77` (119) | `getHistoryOf3D()` | 0 | Storia dati 3D |
| **DFU Mode** | `0x27` (39) | `dfuMode()` | 0 | Modalità aggiornamento |

---

## 🔴 DISCREPANZE IDENTIFICATE NEL NOSTRO CODICE

### 1. **COMANDO RESET SBAGLIATO**
- ❌ **Nostro codice**: Usa `0x45` (non documentato)
- ✅ **SDK Ufficiale**: Usa `0xF3` (-13) con metodo `restoration()`

### 2. **COMANDO SPO2 CONFERMATO**
- ✅ **SDK Ufficiale**: Conferma `0x37` (55) con `setBloodOxygen(mode)`
- ✅ **Nostro codice**: Già corretto!

### 3. **COMANDO EXERCISE HISTORY CONFERMATO**
- ✅ **SDK Ufficiale**: Conferma `0x16` (22) con `getHistoryOfSport()`
- ✅ **Nostro codice**: Già corretto!

---

## 💡 HELPER FUNCTIONS UFFICIALI

### Conversione Timestamp UTC
```java
protected int[] utc2Bytes(final long stamp) {
    return new int[]{
        (int)(stamp >> 24), 
        (int)(stamp >> 16), 
        (int)(stamp >> 8), 
        (int)stamp
    };
}
```

### Checksum Calculation
```java
private byte checkSum(byte[] data) {
    // Implementazione checksum per verifica integrità
}
```

### Utility HexUtil
```java
// Metodi per composizione e concatenazione byte arrays
HexUtil.compose(int... values)
HexUtil.append(byte[] array1, byte[] array2)
```

---

## 🎭 CALLBACK SYSTEM UFFICIALE

### Callbacks Principali (XFITNESS2)
```java
// Dati biologici
BloodOxygenCallback
TemperatureCallback  
HeartRateStatusCallback
BodyHealthCallback

// Dati storici
HistoryOfSportCallback
HistoryOfHRDataCallback
HistoryOfSleepCallback

// Sensori movimento
AccelerometerCallback
Sensor3DFrequencyCallback
Sensor6DRawDataCallback

// Sistema
UserInfoCallback
BluetoothStatusCallback
CustomDataReceivedCallback
```

---

## 🛠️ MODIFICHE CONSIGLIATE AL NOSTRO CODICE

### 1. **Aggiornare Comando Reset**
```dart
// VECCHIO (sbagliato)
var command = [0x45];

// NUOVO (ufficiale)
var officialCommand = OfficialChileafCommands.deviceReset(); // 0xF3
```

### 2. **Verificare Struttura Frame**
Il nostro `ChileafProtocol.buildProtocolFrame()` dovrebbe seguire esattamente:
```
[0xFF] [length] [command] [parameters...] [checksum]
```

### 3. **Implementare Timestamp UTC Corretto**
```dart
List<int> utc2Bytes(int stamp) {
  return [
    (stamp >> 24) & 0xFF,
    (stamp >> 16) & 0xFF, 
    (stamp >> 8) & 0xFF,
    stamp & 0xFF
  ];
}
```

---

## 📊 COMPATIBILITÀ CON DISPOSITIVI

### Dispositivi Supportati (da XFITNESS)
- **CL800**: Dispositivo base
- **CL820**: Con sensori avanzati
- **CL830**: Con funzioni sport
- **CL831**: Con SpO2 e sensori 6D ✅
- **CL837**: Versione extended ✅
- **CL880**: Versione premium con allarmi

---

## 🎯 CONCLUSIONI

1. **✅ UUID**: Il nostro codice usa già gli UUID corretti
2. **❌ Reset Command**: Dobbiamo cambiare da `0x45` a `0xF3`
3. **✅ SpO2**: Il comando `0x37` è confermato ufficiale
4. **✅ Protocol Structure**: La struttura dei frame è corretta
5. **📈 Opportunities**: Possiamo aggiungere molti comandi nuovi

### Priorità Implementazione:
1. **URGENTE**: Sostituire comando reset con `0xF3`
2. **MEDIO**: Aggiungere comandi 3D/6D sensors
3. **BASSO**: Implementare callbacks più specifici

---

## 📝 AGGIORNAMENTI IMPLEMENTATI

✅ **OfficialChileafCommands.dart**: Creato con tutti i comandi ufficiali  
✅ **deviceReset()**: Implementato con comando `0xF3`  
✅ **setBloodOxygen()**: Confermato comando `0x37`  
✅ **getHistoryOfSport()**: Confermato comando `0x16`  
🔄 **ChileafExtendedService**: Aggiornato per usare comandi ufficiali  

**Data Analisi**: 23 Luglio 2025  
**SDK Analizzati**: CL831SE_Android_SDK_V3.0.4 + XFITNESS2 App  
**Metodo**: Reverse Engineering + Decompilazione DEX
