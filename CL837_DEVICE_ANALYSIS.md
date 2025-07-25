# 📱 CL837 Device Analysis - DFU, Modalità, LED e Controlli

**Data Analisi**: 25 Luglio 2025  
**Fonte**: Reverse Engineering CL831_INFO + XFITNESS2 SDK  
**Dispositivo**: Chileaf CL837 (armband ottico)

---

## 🔍 **SOMMARIO ESECUTIVO**

Analisi completa del dispositivo CL837 basata sul reverse engineering degli SDK ufficiali Android (CL831_INFO + XFITNESS2). Il dispositivo supporta aggiornamenti firmware DFU, controllo LED per misurazioni biometriche, multiple modalità operative, ma **non include funzionalità di vibrazione**.

---

## 🔄 **DFU (Device Firmware Update)**

### Comando DFU Principale
```java
// Comando DFU ufficiale
public String dfuMode() {
    String address = this.getDFUAddress();
    byte[] command = new byte[]{-1, 4, 39, 0};  // 0xFF, 0x04, 0x27, 0x00
    command[3] = this.checkSum(command);
    this.writeTxCharacteristic(command);
    return address;
}
```

**Dettagli Tecnici:**
- **Comando Hex**: `0x27` (39 decimale)
- **Metodo SDK**: `dfuMode()`
- **Formato Frame**: `[0xFF, 0x04, 0x27, 0x00, checksum]`
- **Indirizzo DFU**: Calcolato incrementando l'ultimo byte del MAC address device

### Processo DFU Completo
Dal file `DfuActivity.java`:

1. **Device Connecting** - Connessione al dispositivo target
2. **Process Starting** - Inizializzazione processo aggiornamento  
3. **Enabling DFU Mode** - Attivazione modalità DFU
4. **Firmware Validating** - Validazione del firmware uploadato
5. **Device Disconnecting** - Disconnessione temporanea
6. **DFU Completed** - Completamento aggiornamento

### File Supportati
- **ZIP files** (auto-detection con `DfuService.TYPE_AUTO`)
- **HEX files** (formato Intel HEX)
- **BIN files** (formato binario)
- **Validazione MIME**: Controllo automatico estensione file

---

## 🚨 **LED DI STATO E CONTROLLO**

### LED SpO2 (Rosso - Contatto Pelle)

**Comando Attivazione SpO2:**
```java
public void setBloodOxygen(final int mode) {
    int[] command = new int[]{(byte)mode, 0};
    this.sendCommand((byte)55, command);  // 0x37
}
```

**Dettagli LED SpO2:**
- **Comando Hex**: `0x37` (55 decimale)
- **Metodo SDK**: `setBloodOxygen(int mode)`
- **Parametri**: `mode=1` per attivazione, `mode=0` per disattivazione
- **Colore**: **Rosso** (per rilevamento contatto pelle)
- **Durata Massima**: 50 secondi (timer automatico WatchFit)
- **Auto-spegnimento**: Integrato nel sistema di misurazione

**Implementazione Flutter:**
```dart
// Dal chileaf_extended_service.dart
await _sendCommand(OfficialChileafCommands.setBloodOxygen(1)); // LED ON
debugPrint('🚨 LED SpO2 activation - LED rosso acceso per 50 secondi max');

// Timer automatico di spegnimento
_spo2Timer = Timer(const Duration(seconds: 50), () async {
    await stopSpO2Measurement(); // LED OFF
});
```

### LED di Status Device

**Status Indicatori:**
- **3D Sensor Status**: `"Enabled 3D"` / `"Disabled 3D"`
- **HR Alarm Status**: `"Alarm By Age"` / `"Alarm By High-Low"`
- **Frequency Indicators**: Correlati alle frequenze sensori (25HZ-400HZ per 3D, 26HZ-208HZ per 6D)

### 🔍 Implementazione SDK Chileaf Decompilata

**Controllo LED attraverso comando SpO2 (Java SDK):**
```java
// Metodo principale in WearManager.java per controllo LED/SpO2
public void setBloodOxygen(final int mode) {
    int[] command = {(byte) mode};
    sendCommand((byte) 55, command);  // 55 = 0x37 hex
}

// Utilizzo nell'app BloodOxygenActivity.java
if (switchEnabled) {
    this.mManager.setBloodOxygen(1);  // LED acceso + SpO2 attivo
} else {
    this.mManager.setBloodOxygen(0);  // LED spento + SpO2 disabilitato
}

// Callback completo per feedback dal device
@Override
public void onBloodOxygenReceived(BluetoothDevice device, 
                                  int bSwitch,    // 0=spento, 1=acceso
                                  String value,   // valore SpO2 misurato
                                  int gesture,    // 0=postura sbagliata, 1=corretta
                                  int piValue,    // qualità segnale polso
                                  int onwrist) {  // 0=non indossato, 1=indossato
    
    // Interpretazione qualità segnale piValue:
    // 0 = "No pulse detected"
    // <8 = "Weak signal" 
    // <15 = "Good signal"
    // >=15 = "Excellent signal"
    
    // Interpretazione gesture:
    // 0 = "Wrong wrist posture"
    // 1 = "Wear the correct posture"
}
```

**Comando DFU Mode (da WearManager.java):**
```java
public String dfuMode() {
    String address = getDFUAddress();  // Calcola indirizzo DFU (+1 al MAC)
    byte[] command = {-1, 4, 39, checkSum(command)};  // 0xFF, 0x04, 0x27
    writeTxCharacteristic(command);
    return address;  // Nuovo indirizzo BLE per connessione DFU
}
```

---

## ⚙️ **MODALITÀ DEVICE (AUTOMATICHE vs MANUALI)**

### 🔄 Modalità Heart Rate (HR)

**Modalità Automatica (Background Monitoring):**
```java
// Configura monitoring continuo HR con soglie
mManager.setHeartRateStatus(60, 180, 120);  // min, max, goal
// Comando: 0x46, payload: [1, 60, 180, 120]

// Abilita allarme automatico per soglie
mManager.setHeartRateAlarm(true);  // Comando: 0x57, payload: [1]
```

**Modalità Manuale (On-Demand):**
```java
// Richiedi singola misurazione HR
mManager.requestHeartRate();  // Comando varia per tipo misurazione
```

### 📱 Modalità Sensori 3D/6D

**Controllo automatico frequenze:**
```java
// 3D Sensor: 25HZ, 50HZ, 100HZ, 200HZ, 400HZ
mManager.set3DFrequency(2);  // 100HZ - Comando: 0x74, payload: [0, 11, 2]

// 6D Sensor: 26HZ, 52HZ, 104HZ, 208HZ  
mManager.set6DFrequency(1);  // 52HZ - Comando: 0x62, payload: [1]

// Enable/Disable 3D monitoring
mManager.set3DEnabled(true);  // Comando: 0x74, payload: [0, 12, 1]
```

### 🏃 Modalità Esercizio (Exercise Modes)

Dal reverse engineering BaseActivity.java:
| Codice | Modalità | Tipo | Auto/Manuale |
|--------|----------|------|--------------|
| `0` | Indoor running | Cardio | **Automatico** |
| `1` | Outdoor running | Cardio | **Automatico** |
| `2` | Outdoor cycling | Cardio | **Automatico** |
| `3` | Spinning bike | Cardio | **Automatico** |
| `4` | Free training | Generic | **Manuale** |
| `5` | Skipping rope | Cardio | **Manuale** |

### 🩸 Modalità SpO2 (LED Control)

**Modalità Manuale (Solo su richiesta):**
```java
// Attiva LED rosso + misurazione SpO2
mManager.setBloodOxygen(1);  // Comando: 0x37, payload: [1]
// Timer automatico: 50 secondi max

// Disattiva LED + stop misurazione
mManager.setBloodOxygen(0);  // Comando: 0x37, payload: [0]
```

**Feedback callback ricevuto:**
- `bSwitch`: 0=LED spento, 1=LED acceso
- `value`: Valore SpO2 misurato (%)
- `gesture`: 0=postura sbagliata, 1=postura corretta
- `piValue`: Qualità segnale (0=nessuno, <8=debole, <15=buono, >=15=ottimo)
- `onwrist`: 0=non indossato, 1=indossato correttamente

### 👤 Configurazione Profilo Utente

**Info utente (influenza algoritmi automatici):**
```java
// Configura profilo per algoritmi personalizzati
mManager.setUserInfo(25, 1, 70, 175, 12345);  
// age, sex(1=M,0=F), weight(kg), height(cm), userId
// Comando: 0x04, payload: [age, sex, weight, height, userId_bytes]

// Richiedi profilo corrente
mManager.getUserInfo();  // Comando: 0x03, payload: [0]
```

**Callback ricevuto:**
```java
@Override
public void onUserInfoReceived(BluetoothDevice device, 
                              int age, int sex, int weight, 
                              int height, long userId) {
    // sex: 1=Male, 0=Female
    // age: 1-120 anni
    // weight: 30-200 kg  
    // height: 100-250 cm
    // userId: identificativo numerico
}
```

---

## ⚙️ **MODALITÀ DEVICE (AUTOMATICHE vs MANUALI)**

### Modalità Esercizio
Dal metodo `getMode(int mode)` in `BaseActivity.java`:

| Codice | Modalità | Tipo | Descrizione |
|--------|----------|------|-------------|
| `0` | Indoor running | **Automatico** | Corsa indoor con rilevamento automatico |
| `1` | Outdoor running | **Automatico** | Corsa outdoor con GPS |
| `2` | Outdoor cycling | **Automatico** | Ciclismo outdoor |
| `3` | Spinning bike | **Manuale** | Cyclette - controllo manuale |
| `4` | Free training | **Manuale** | Allenamento libero |
| `5` | Skipping rope | **Manuale** | Saltare la corda |

### Modalità Rope Skipping Specifiche

**Free Mode:**
```java
public void getRopeSkippingFree() {
    this.sendCommand((byte)65, 0);  // 0x41
}
```

**Counter Mode:**
```java
public void getRopeSkippingCounter() {
    this.sendCommand((byte)67, 0);  // 0x43  
}
```

**Timer Mode:**
```java
public void getRopeSkippingTimer() {
    this.sendCommand((byte)68, 0);  // 0x44
}
```

---

## 📊 **CONTROLLI SENSORI E STATUS**

### Sensore 3D (Accelerometro)

**Set Frequency:**
```java
public void set3DFrequency(@IntRange(from = 0L,to = 4L) int frequency) {
    this.sendCommand((byte)116, 0, 11, (byte)frequency);  // 0x74
}
```

**Get Status:**
```java
public void get3DStatus() {
    this.sendCommand((byte)117, 0, 12);  // 0x75
}
```

**Enable/Disable:**
```java
public void set3DEnabled(boolean enabled) {
    this.sendCommand((byte)116, 0, 12, enabled ? 1 : 0);  // 0x74
}
```

**Frequenze Supportate 3D:**
- `0`: 25HZ
- `1`: 50HZ  
- `2`: 100HZ
- `3`: 200HZ
- `4`: 400HZ

### Sensore 6D (Giroscopio + Accelerometro)

**Set Frequency:**
```java
public void set6DFrequency(@IntRange(from = 0L,to = 3L) int frequency) {
    this.sendCommand((byte)98, frequency);  // 0x62
}
```

**Get Frequency:**
```java
public void get6DFrequency() {
    this.sendCommand((byte)97, 0);  // 0x61
}
```

**Frequenze Supportate 6D:**
- `0`: 26HZ
- `1`: 52HZ
- `2`: 104HZ  
- `3`: 208HZ

### Heart Rate Status

**Get Status:**
```java
public void getHeartRateStatus() {
    this.sendCommand((byte)70, 0);  // 0x46
}
```

**Set Status:**
```java
public void setHeartRateStatus(int min, int max, int goal) {
    this.sendCommand((byte)70, 1, (byte)min, (byte)max, (byte)goal);  // 0x46
}
```

**Heart Rate Alarm:**
```java
public void setHeartRateAlarm(final boolean alarm) {
    this.sendCommand((byte)87, alarm ? 1 : 0);  // 0x57
}

public void getHeartRateAlarm() {
    this.sendCommand((byte)91, 0);  // 0x5B
}
```

---

## 🔧 **PROTOCOLLO DI COMUNICAZIONE**

### Formato Frame Standard
```java
private void sendCommand(final byte cmd, final int... values) {
    byte[] result;
    byte[] command;
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

**Struttura Frame:** `[0xFF] [length] [command] [parameters...] [checksum]`

### Algoritmo Checksum
```java
protected byte checkSum(final byte[] data) {
    int result = 0;
    for(byte item : data) {
        result += item;
    }
    result = -result;
    result ^= 58;  // XOR con 0x3A
    return (byte)(result & 255);
}
```

---

## 📋 **MAPPATURA COMANDI PRINCIPALI**

| Comando | Hex | Metodo | Parametri | Categoria | Descrizione |
|---------|-----|--------|-----------|-----------|-------------|
| **Reset** | `0xF3` | `restoration()` | 0 | Core | Reset dispositivo |
| **Timestamp** | `0x08` | `setUTCTime(stamp)` | 4 bytes UTC | Core | Sincronizza orario |
| **Shutdown** | `0xF1` | `shutdown()` | 0 | Core | Spegne dispositivo |
| **DFU Mode** | `0x27` | `dfuMode()` | 0 | Core | **Modalità aggiornamento** |
| **SpO2 Control** | `0x37` | `setBloodOxygen(mode)` | mode,0 | Health | **LED rosso SpO2** |
| **Temperature** | `0x38` | `requestTemperature()` | - | Health | Temperatura corporea |
| **Get User Info** | `0x03` | `getUserInfo()` | - | Core | **Profilo utente** |
| **Set User Info** | `0x04` | `setUserInfo(age,sex,weight,height,userId)` | age,sex,weight,height,userId | Core | **Configura profilo** |
| **HR Status Get** | `0x46` | `getHeartRateStatus()` | - | Health | **Modalità HR** |
| **HR Status Set** | `0x46` | `setHeartRateStatus(min,max,goal)` | 1,min,max,goal | Health | **Configura HR automatico** |
| **HR Max Set** | `0x74` | `setHeartRateMax(max)` | 0,6,max | Health | **Soglia massima HR** |
| **HR Max Get** | `0x75` | `getHeartRateMax()` | 0,6 | Health | **Richiedi soglia max** |
| **HR Alarm Set** | `0x57` | `setHeartRateAlarm(enabled)` | 1/0 | Health | **Abilita allarme HR** |
| **HR Alarm Get** | `0x5B` | `getHeartRateAlarm()` | - | Health | **Stato allarme HR** |
| **Temperature** | `0x38` | `getTemperature()` | 0 | Health | Temperatura corpo |
| **HR Status Get** | `0x46` | `getHeartRateStatus()` | 0 | HeartRate | Status frequenza cardiaca |
| **HR Status Set** | `0x46` | `setHeartRateStatus(...)` | 1,min,max,goal | HeartRate | Imposta limiti HR |
| **HR Alarm Set** | `0x57` | `setHeartRateAlarm(bool)` | 1/0 | HeartRate | Allarme frequenza |
| **HR Alarm Get** | `0x5B` | `getHeartRateAlarm()` | 0 | HeartRate | Legge stato allarme |
| **6D Freq Get** | `0x61` | `get6DFrequency()` | 0 | Sensors | Frequenza giroscopio |
| **6D Freq Set** | `0x62` | `set6DFrequency(freq)` | freq | Sensors | Imposta freq 6D |
| **3D Freq Set** | `0x74` | `set3DFrequency(freq)` | 0,11,freq | Sensors | Frequenza 3D |
| **3D Status Set** | `0x74` | `set3DEnabled(bool)` | 0,12,1/0 | Sensors | **Abilita/Disabilita 3D** |
| **3D Freq Get** | `0x75` | `get3DFrequency()` | 0,11 | Sensors | Legge frequenza 3D |
| **3D Status Get** | `0x75` | `get3DStatus()` | 0,12 | Sensors | **Status sensore 3D** |

---

## ❌ **VIBRAZIONE - NON SUPPORTATA**

⚠️ **Risultato Analisi**: Il dispositivo CL837 **NON supporta funzionalità di vibrazione**.

**Evidenze:**
- Nessun comando vibrazione trovato negli SDK CL831_INFO e XFITNESS2
- Nessun metodo `vibrate()`, `setVibration()` o simili
- Nessuna callback vibrazione nei gestori di eventi
- Nessun riferimento a vibrazione nei modelli device

**Conclusione**: Il CL837 è progettato come dispositivo di monitoraggio passivo senza feedback tattile.

---

## 🔋 **GESTIONE ENERGIA E STATI**

### Battery & Power Management
```java
// Shutdown device
public void shutdown() {
    this.sendCommand((byte)-15, 0);  // 0xF1
}

// Bluetooth disable  
public void setBluetoothDisabled() {
    this.sendCommand((byte)63, 2);  // 0x3F
}
```

### Connection Status Callbacks
- `onDeviceConnected(BluetoothDevice device)`
- `onDeviceDisconnected(BluetoothDevice device)` 
- `onLinkLossOccurred(BluetoothDevice device)`
- `onBatteryLevelChanged(BluetoothDevice device, int batteryLevel)`

---

## 📈 **DIFFERENZE TRA GLI SDK**

### CL831_INFO vs XFITNESS2

**Similitudini:**
- Stesso protocollo frame `[0xFF][length][command][params][checksum]`
- Stesso algoritmo checksum con XOR 0x3A
- Stessi comandi principali (DFU, SpO2, Sensori)

**Differenze:**
- **XFITNESS2**: Più metodi helper e utility
- **CL831_INFO**: Focus su UI e activity Android
- **XFITNESS2**: Meglio strutturato per integrazione SDK

---

## 🎯 **RACCOMANDAZIONI IMPLEMENTAZIONE**

### Per Sviluppatori Flutter
1. **LED SpO2**: Usare comando `0x37` con timer automatico 50s
2. **DFU**: Implementare comando `0x27` con gestione indirizzo MAC+1  
3. **Sensori**: Preferire modalità automatiche per precisione
4. **Checksum**: Implementare algoritmo ufficiale per affidabilità
5. **Vibrazione**: Non implementare - feature non supportata

### Best Practices
- Sempre validare checksum prima dell'invio
- Implementare timeout per comandi DFU (30s minimum)
- Usare throttling per comandi ripetitivi
- Monitorare battery level durante operazioni intensive

---

## 🎛️ **WIDGET DI CONTROLLO DISPOSITIVO**

### DeviceControlWidget - Centro di Comando Completo

Per facilitare l'interazione con tutti i comandi del dispositivo CL837, è stato implementato un widget centralizzato che permette di:

**Funzionalità Principali:**
- **Controllo DFU**: Avvio modalità aggiornamento firmware con feedback visivo
- **Gestione LED SpO2**: Start/Stop misurazione con controllo LED rosso
- **Controllo Sensori**: Enable/Disable sensori 3D/6D con regolazione frequenze
- **Gestione Power**: Shutdown, reset, disable Bluetooth
- **Modalità Rope**: Controllo modalità saltare la corda
- **Heart Rate**: Configurazione allarmi e soglie HR
- **Storico Dati**: Richiesta e visualizzazione dati storici

**Caratteristiche Tecniche:**
- **Feedback Real-time**: Status visivo per ogni comando inviato
- **Error Handling**: Gestione errori con retry automatico
- **Command Queue**: Coda comandi per evitare conflitti BLE
- **Progress Indicators**: Indicatori di progresso per operazioni lunghe
- **Command History**: Log comandi inviati con timestamp
- **Responsive UI**: Interfaccia adattiva per diversi dispositivi

**Implementazione:**
```dart
class DeviceControlWidget extends StatefulWidget {
  final ChileafExtendedService extendedService;
  final bool isConnected;
  
  // Gestisce tutti i 34 comandi ufficiali mappati
  // Fornisce feedback visivo per ogni operazione
  // Include validazione parametri e gestione errori
}
```

**Categorie Comandi Supportate:**
1. **Core Device** (Reset, Shutdown, DFU, Time Sync)
2. **Health Monitoring** (SpO2, Temperature, HR Status)
3. **Sensors Control** (3D/6D Frequency, Enable/Disable)
4. **Historical Data** (Exercise, HR, RR Data)
5. **Rope Skipping** (Mode Selection, Statistics)
6. **Power Management** (Battery, Bluetooth Control)

---

## 🎛️ **WIDGET DI CONTROLLO DISPOSITIVO**

### DeviceControlWidget - Centro di Comando Completo

Il progetto include un widget completo `DeviceControlWidget` che implementa tutti i 34 comandi ufficiali del protocollo Chileaf BLE v0.6 con feedback real-time e gestione errori avanzata.

#### **Funzionalità Principali:**

**🔧 Core Device Commands:**
- **Device Reset** (0xF3): Reset completo con cancellazione dati storici
- **Sync Time** (0x08): Sincronizzazione orario automatica
- **DFU Mode** (0x27): Modalità aggiornamento firmware con feedback visivo

**🩺 Health Monitoring:**
- **SpO2 Measurement** (0x37): Controllo LED rosso con timer 50s automatico
- **Temperature Reading** (0x38): Richiesta temperatura corporea
- **HR Settings** (0x46): Configurazione soglie frequenza cardiaca

**📡 Sensors Control:**
- **3D Sensor Toggle** (0x74/0x75): Enable/Disable accelerometro 3D
- **3D Frequency** (0x74): Controllo frequenza 25HZ-400HZ
- **6D Frequency** (0x62): Controllo frequenza giroscopio 26HZ-208HZ

**📚 Historical Data:**
- **Exercise History** (0x16): Storico esercizi 7 giorni
- **HR History** (0x21/0x22): Dati frequenza cardiaca dettagliati
- **Sleep History** (0x05): Analisi del sonno

**🪢 Rope Skipping:**
- **Mode Selection**: Free/Counter/Timer mode
- **Real-time Stats**: Statistiche saltare la corda

**🔋 Power Management:**
- **Shutdown** (0xF1): Spegnimento dispositivo
- **Disable Bluetooth** (0x3F): Disattivazione radio BT

#### **Caratteristiche Tecniche:**

**Real-time Feedback:**
```dart
Widget _buildCommandStatus() {
  return Container(
    // Status bar con animazione pulse durante esecuzione
    child: Row([
      Icon(_isExecutingCommand ? Icons.sync : Icons.check_circle_outline),
      Text(_commandStatus.isEmpty ? 'Ready to send commands' : _commandStatus),
      if (_lastCommandTime != null) Text('${timestamp}'),
    ]),
  );
}
```

**Error Handling Avanzato:**
- **Connection Check**: Validazione connessione prima di ogni comando
- **Command Queue**: Prevenzione conflitti BLE con mutex
- **Retry Logic**: Tentativo automatico ripetizione
- **User Feedback**: SnackBar success/error con dettagli

**Command History:**
```dart
class CommandHistoryEntry {
  final String command;
  final DateTime timestamp;
  final bool success;
  final String? error;
}
```

**SpO2 LED Management:**
```dart
// Gestione automatica LED rosso SpO2
Timer _spo2LEDTimer = Timer(Duration(seconds: 50), () {
  setState(() => _isSpO2LEDActive = false);
  _updateCommandStatus('⏰ SpO2 LED auto-stopped after 50s');
});
```

#### **Integrazione App Principale:**

Il widget è integrato come tab dedicato nell'app principale:

```dart
TabBarView(
  controller: _tabController,
  children: [
    _buildSensorTab(),           // Tab 1: Sensori live
    ManualTestsWidget(...),      // Tab 2: Test manuali
    DeviceControlWidget(         // Tab 3: Device Control
      extendedService: _extendedService,
      isConnected: connectedDevice != null,
      deviceName: connectedDevice?.platformName ?? 'CL837 Device',
    ),
    _buildInfoTab(),             // Tab 4: Info dispositivo
  ],
)
```

#### **UI/UX Features:**

**Sezioni Espandibili:**
- 6 categorie di comandi organizzate logicamente
- Icone colorate per identificazione rapida
- Badge status per stati attivi (LED ON, Sensor Enabled)

**Animazioni:**
- Pulse animation durante esecuzione comandi
- Color coding per success/error states
- Loading indicators per operazioni lunghe

**Accessibilità:**
- Touch targets ottimizzati
- Feedback tattile e visivo
- Stati disabled quando disconnesso

#### **Validazione e Sicurezza:**

**Parameter Validation:**
```dart
// Esempio validazione frequenze 3D
@IntRange(from = 0, to = 4) int frequency3D;
final frequencies = ['25HZ', '50HZ', '100HZ', '200HZ', '400HZ'];
```

**Command Throttling:**
```dart
Future<void> _executeCommand(String commandName, Future<void> Function() command) async {
  if (!widget.isConnected || _isExecutingCommand) return;
  
  setState(() => _isExecutingCommand = true);
  try {
    await command();
    _addToHistory(commandName, true);
  } catch (e) {
    _addToHistory(commandName, false, error: e.toString());
  } finally {
    setState(() => _isExecutingCommand = false);
  }
}
```

---

## 🔗 **RIFERIMENTI TECNICI**

- **SDK Source**: CL831_INFO + XFITNESS2 reverse engineering
- **Protocollo**: Chileaf BLE Protocol v0.6  
- **Device**: CL837 armband ottico
- **Data Analisi**: 25 Luglio 2025
- **Widget Implementato**: DeviceControlWidget con 34 comandi ufficiali

*Documento tecnico basato su reverse engineering completo degli SDK ufficiali Android Chileaf.*
