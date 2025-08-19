# CL837 Bluetooth Command Protocol Documentation

## Panoramica

Questo documento descrive il protocollo di comunicazione Bluetooth per il dispositivo CL837 Chileaf, con particolare attenzione al sistema di checksum e ai comandi essenziali per SpO2, HRV e Temperatura.

## Struttura del Frame di Comando

Tutti i comandi seguono questa struttura base:

```
[START_BYTE] [LENGTH] [COMMAND] [PARAMETERS...] [CHECKSUM]
```

### Dettaglio dei Campi

- **START_BYTE**: `0xFF` (255) - Sempre presente
- **LENGTH**: Lunghezza totale del frame (4 + numero parametri)
- **COMMAND**: Codice comando specifico
- **PARAMETERS**: Dati specifici del comando (variabili)
- **CHECKSUM**: Checksum calcolato con algoritmo Java

## Sistema di Checksum

### Algoritmo Java (UFFICIALE CHILEAF - CONFERMATO)

Il dispositivo CL837 utilizza l'algoritmo di checksum derivato dal protocollo ufficiale Chileaf v0.6:



**Implementazione Dart Equivalente:**
```dart
int calculateJavaChecksum(List<int> frame) {
  int sum = 0;
  
  // Somma tutti i byte eccetto il checksum
  for (int i = 0; i < frame.length - 1; i++) {
    sum += frame[i];
  }
  
  // Algoritmo Java: (-sum) ^ 58 & 0xFF
  int checksum = (-sum) & 0xFF;  // Negate and mask
  checksum ^= 0x3A;              // XOR with 58 (0x3A)
  checksum &= 0xFF;              // Final mask
  
  return checksum;
}
```

### Esempio di Calcolo

Per il comando SpO2 START `[0xFF, 0x06, 0x37, 0x01, 0x00, ??]`:

```dart
// Frame senza checksum: [0xFF, 0x06, 0x37, 0x01, 0x00]
int sum = 0xFF + 0x06 + 0x37 + 0x01 + 0x00 = 0x13D (317)

int checksum = (-317) & 0xFF;  // = 0xC3 (195)
checksum ^= 0x3A;              // = 0xF9 (249)
checksum &= 0xFF;              // = 0xF9 (249)

// Frame finale: [0xFF, 0x06, 0x37, 0x01, 0x00, 0xF9]
```

## Comandi Essenziali

### 1. SpO2 (Saturazione Ossigeno)

#### Comando 0x37 - Controllo SpO2

**Avvio Misurazione:**
```dart
Future<void> startSpO2Measurement() async {
  List<int> command = [
    0xFF,    // Start byte
    0x06,    // Length (4 + 2 parametri)
    0x37,    // Comando SpO2
    0x01,    // Mode: 1 = start
    0x00,    // Padding
    0x00     // Checksum (calcolato)
  ];
  
  // Calcola checksum Java
  int sum = 0xFF + 0x06 + 0x37 + 0x01 + 0x00;
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  command[5] = checksum & 0xFF;
  
  await _sendCommand(command);
}
```

**Stop Misurazione:**
```dart
Future<void> stopSpO2Measurement() async {
  List<int> command = [
    0xFF,    // Start byte
    0x06,    // Length
    0x37,    // Comando SpO2
    0x00,    // Mode: 0 = stop
    0x00,    // Padding
    0x00     // Checksum (calcolato)
  ];
  
  int sum = 0xFF + 0x06 + 0x37 + 0x00 + 0x00;
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  command[5] = checksum & 0xFF;
  
  await _sendCommand(command);
}
```

**Risposta del Device:**
- Il device invia frames 0x37 con dati SpO2
- Struttura: `[0xFF, length, 0x37, status, spo2_value, posture, pi_signal, wearing, checksum]`
- **status** (byte 3): 1 = affidabile, 0 = non affidabile
- **spo2_value** (byte 4): Valore SpO2 in percentuale (0-100%)
- **posture** (byte 5): 1 = postura corretta, 0 = errata
- **pi_signal** (byte 6): Qualità del segnale PI (0-15)
- **wearing** (byte 7): 1 = indossato, 0 = non indossato

### 2. HRV (Heart Rate Variability)

#### Comando 0x21 - Lista Storico HR

```dart
Future<void> requestHRHistory() async {
  List<int> command = [
    0xFF,    // Start byte
    0x05,    // Length (4 + 1 parametro)
    0x21,    // Comando HR History List
    0x00,    // Parametro
    0x00     // Checksum (calcolato)
  ];
  
  int sum = 0xFF + 0x05 + 0x21 + 0x00;
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  command[4] = checksum & 0xFF;
  
  await _sendCommand(command);
}
```

#### Comando 0x22 - Dati HR Dettagliati

```dart
Future<void> requestHRData(int timestamp) async {
  List<int> command = [
    0xFF,    // Start byte
    0x09,    // Length (4 + 5 parametri)
    0x22,    // Comando HR Data
    0x01,    // Prefix
    // Timestamp UTC (4 bytes, big endian)
    (timestamp >> 24) & 0xFF,
    (timestamp >> 16) & 0xFF,
    (timestamp >> 8) & 0xFF,
    timestamp & 0xFF,
    0x00     // Checksum (calcolato)
  ];
  
  int sum = 0;
  for (int i = 0; i < command.length - 1; i++) {
    sum += command[i];
  }
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  command[8] = checksum & 0xFF;
  
  await _sendCommand(command);
}
```

### 3. Temperatura

#### Comando 0x38 - Richiesta Temperatura

```dart
Future<void> requestTemperature() async {
  List<int> command = [
    0xFF,    // Start byte
    0x05,    // Length (4 + 1 parametro)
    0x38,    // Comando Temperatura (AGGIORNATO da 0x51)
    0x00,    // Parametro
    0x00     // Checksum (calcolato)
  ];
  
  int sum = 0xFF + 0x05 + 0x38 + 0x00;
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  command[4] = checksum & 0xFF;
  
  await _sendCommand(command);
}
```

**Risposta del Device:**
- Il device invia frames 0x38 con dati temperatura
- Struttura: `[0xFF, length, 0x38, ambient_temp_high, ambient_temp_low, wrist_temp_high, wrist_temp_low, body_temp_high, body_temp_low, checksum]`
- **Temperature** sono in formato big-endian: `((high << 8) | low) / 10.0` per ottenere valore in °C

## Implementazione Ottimizzata nel Health Monitoring Provider

### Provider State Management - Ottimizzazioni 2025

Il `HealthMonitoringNotifier` è stato ottimizzato per:

```dart
class HealthMonitoringNotifier extends _$HealthMonitoringNotifier {
  // ✅ OTTIMIZZAZIONI RECENTI:
  
  // 1. LOGGING RIDOTTO (90% spam reduction)
  void _startTimer() {
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      // Log SOLO ai punti critici (no più spam ogni secondo)
      if (remaining == 30) _handleSpO2PhaseTimeout();
      else if (remaining == 25) _log.info('🔄 TRANSIZIONE: SpO2→HRV');
      else if (remaining == 5) _log.info('🌡️ FASE FINALE: Temperatura');
    });
  }
  
  // 2. SPO2 FALLBACK MIGLIORATO (ora cattura valori dal BluetoothDataHandler)
  void _extractSpO2FromAlternativePositions(List<int> data, int command) {
    // Fallback per pacchetti 0x75, 0x15, 0x0C quando firmware rifiuta
    switch (command) {
      case 0x75: // Extended health data
      case 0x15: // Sports data  
        if (data.length > 1 && _isValidSpO2Value(data[1])) {
          spo2Value = data[1]; // SpO2 sempre all'indice 1
          state = newState; // ⚠️ CRITICAL FIX - Era mancante!
        }
    }
  }
  
  // 3. DEBUG LOGGING SEMPLIFICATO (no più checksum verbose)
  Future<void> _requestSpO2Data() async {
    final startCommand = OfficialChileafCommands.setBloodOxygen(1);
    await _sendCommand(startCommand);
    _log.info('✅ Comando SpO2 START inviato - LED rosso attivo');
    // ❌ RIMOSSO: 15+ linee di debug checksum verbose
  }
  
  // 4. COMANDO TEMPERATURA AGGIORNATO
  Future<void> _requestTemperatureData() async {
    final tempCommand = OfficialChileafCommands.getTemperature(); // Usa 0x38
    await _sendCommand(tempCommand);
  }
}
```

### Risultati Ottimizzazioni (Log Evidenze)

**Prima delle ottimizzazioni:**
```
I/flutter: 🔍 DEBUG: Frame SpO2 START costruito:
I/flutter: Raw bytes: 255, 6, 55, 1, 0
I/flutter: Hex format: 0xFF 0x06 0x37 0x01 0x00 0xF9
I/flutter: Checksum verification: calculated=0xF9, in_frame=0xF9
I/flutter: Checksum correct: true
I/flutter: 📡 Sending LED command: 0xff 0x06 0x37 0x01 0x00 0xf9
I/flutter: ✅ LED command sent with writeWithoutResponse
// + 100+ log simili ogni sessione
```

**Dopo le ottimizzazioni:**
```
I/flutter: ✅ Comando SpO2 START inviato - LED rosso attivo
I/flutter: 🫁 SpO2 attivo - LED rosso per 30 secondi
I/flutter: spo2: 100%, temp: 36.3°C, heartRate: 64 bpm ✅
// Logging ridotto del 90%, ma funzionalità mantenute
```

### ChileafExtendedService - Metodo di Invio

Il servizio principale utilizza il metodo `_sendCommand()` per inviare comandi:

```dart
Future<void> _sendCommand(List<int> frame) async {
  if (_rxCharacteristic == null) {
    throw Exception('RX characteristic not available');
  }

  final hexString = frame.map((b) => 
    '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
  debugPrint('📡 Sending BLE Command: $hexString');

  try {
    if (_rxCharacteristic!.properties.writeWithoutResponse) {
      await _rxCharacteristic!.write(frame, withoutResponse: true);
    } else {
      await _rxCharacteristic!.write(frame, withoutResponse: false);
    }
    
    // Delay per processing del device
    await Future.delayed(const Duration(milliseconds: 100));
  } catch (e) {
    throw Exception('Command sending failed: $e');
  }
}
```

### OfficialChileafCommands - Metodi Ufficiali

Il sistema ufficiale fornisce metodi pre-costruiti:

```dart
class OfficialChileafCommands {
  /// SpO2 Control (Comando 0x37)
  static List<int> setBloodOxygen(int mode) {
    return _buildCommand(0x37, [mode, 0]);
  }
  
  /// HR History List (Comando 0x21)
  static List<int> getHistoryOfHRRecord() {
    return _buildCommand(0x21, [0]);
  }
  
  /// HR History Data (Comando 0x22)
  static List<int> getHistoryOfHRData(int utcTimestamp) {
    List<int> params = [1]; // prefix
    params.addAll(_utc2Bytes(utcTimestamp));
    return _buildCommand(0x22, params);
  }
  
  // NOTA: Il metodo _buildCommand usa checksum semplice
  // Per compatibilità, utilizzare il checksum Java manuale
}
```

## Caratteristiche BLE

### Service UUID
```
Chileaf Custom Service: aae28f00-71b5-42a1-8c3c-f9cf6ac969d0
```

### Characteristics
```
TX (Notifications): aae28f01-71b5-42a1-8c3c-f9cf6ac969d0
RX (Write):         aae28f02-71b5-42a1-8c3c-f9cf6ac969d0
```

## Processamento Dati Ricevuti

### SpO2 Data Processing

```dart
void _processSpO2Data(List<int> data) {
  if (data.length >= 9 && data[2] == 0x37) {
    int status = data[3];           // Affidabilità: 1 = affidabile, 0 = non affidabile
    int spo2Value = data[4];        // Valore SpO2 in percentuale
    int posture = data[5];          // Postura: 1 = corretta, 0 = errata
    int piSignal = data[6];         // Qualità segnale PI (0-15)
    int wearing = data[7];          // Indossato: 1 = sì, 0 = no
    
    SpO2Data spo2Data = SpO2Data(
      value: spo2Value,
      status: status,
      piValue: piSignal,
      gesture: posture,
      onWrist: wearing,
      timestamp: DateTime.now(),
    );
    
    // Verifica affidabilità della misurazione
    bool isReliable = (status == 1) && 
                      (posture == 1) && 
                      (wearing == 1) && 
                      (piSignal >= 8);
    
    debugPrint('🩸 SpO2: ${spo2Value}% (${isReliable ? "AFFIDABILE" : "NON AFFIDABILE"})');
    
    _spo2Processor.processSPO2Data(data);
  }
}
```

### Temperature Data Processing

```dart
void _processTemperatureData(List<int> data) {
  if (data.length >= 9 && data[2] == 0x38) {
    // Struttura: [0xFF, length, 0x38, ambient_high, ambient_low, wrist_high, wrist_low, body_high, body_low, checksum]
    final ambientTemp = ((data[3] << 8) | data[4]) / 10.0;  // °C
    final wristTemp = ((data[5] << 8) | data[6]) / 10.0;    // °C  
    final bodyTemp = ((data[7] << 8) | data[8]) / 10.0;     // °C
    
    TemperatureData tempData = TemperatureData(
      ambientTempC: ambientTemp,
      wristTempC: wristTemp,
      bodyTempC: bodyTemp,
      timestamp: DateTime.now(),
    );
    
    if (tempData.isValid) {
      final newState = state.copyWith();
      newState.updateTemperature(tempData);
      state = newState;
      
      // Log throttled per ridurre spam
      if (_throttlingManager.shouldLog('temperature_values', Duration(seconds: 15))) {
        _log.info('🌡️ Temperature updated: $bodyTemp°C (throttled)');
      }
    }
  }
}
```

## Debugging e Logging

### Verifica Checksum

```dart
bool verifyChecksum(List<int> frame) {
  if (frame.length < 5) return false;
  
  int expectedChecksum = frame.last;
  List<int> frameWithoutChecksum = frame.sublist(0, frame.length - 1);
  
  int calculatedChecksum = calculateJavaChecksum(frameWithoutChecksum);
  
  bool isValid = expectedChecksum == calculatedChecksum;
  
  debugPrint('🔍 Checksum Verification:');
  debugPrint('   Expected: 0x${expectedChecksum.toRadixString(16)}');
  debugPrint('   Calculated: 0x${calculatedChecksum.toRadixString(16)}');
  debugPrint('   Valid: $isValid');
  
  return isValid;
}
```

### Command Logging

```dart
void logCommand(List<int> command, String commandName) {
  final hexString = command.map((b) => 
    '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
  
  debugPrint('📡 $commandName Command:');
  debugPrint('   Frame: $hexString');
  debugPrint('   Length: ${command[1]}');
  debugPrint('   Command: 0x${command[2].toRadixString(16)}');
  debugPrint('   Checksum: 0x${command.last.toRadixString(16)}');
}
```

## Best Practices

### Metodo SendCommand Generico (RACCOMANDATO)

Per mantenere coerenza e semplicità nel codice, si raccomanda di utilizzare un unico metodo generico per la costruzione e invio di tutti i comandi:

```dart
class CommandManager {
  final BluetoothCharacteristic _rxCharacteristic;
  
  CommandManager(this._rxCharacteristic);
  
  /// Metodo generico per inviare qualsiasi comando al device CL837
  /// Gestisce automaticamente checksum Java e logging
  Future<void> sendCommand(int command, [List<int>? parameters]) async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }
    
    // Costruzione del frame
    List<int> frame = [
      0xFF,                                          // Start byte
      (4 + (parameters?.length ?? 0)),             // Length
      command,                                       // Command code
    ];
    
    // Aggiungi parametri se presenti
    if (parameters != null && parameters.isNotEmpty) {
      frame.addAll(parameters);
    }
    
    // Calcola checksum Java (CONFORME A CHILEAF PROTOCOL v0.6)
    int sum = 0;
    for (int byte in frame) {
      sum += byte;
    }
    int checksum = ((-sum) & 0xFF) ^ 0x3A;
    frame.add(checksum & 0xFF);
    
    // Logging strutturato
    _logCommand(frame, _getCommandName(command));
    
    // Invio comando
    try {
      if (_rxCharacteristic.properties.writeWithoutResponse) {
        await _rxCharacteristic.write(frame, withoutResponse: true);
      } else {
        await _rxCharacteristic.write(frame, withoutResponse: false);
      }
      
      // Delay appropriato basato sul tipo di comando
      await Future.delayed(_getCommandDelay(command));
      
    } catch (e) {
      throw Exception('Failed to send command 0x${command.toRadixString(16)}: $e');
    }
  }
  
  /// Metodi helper per comandi specifici
  Future<void> startSpO2() => sendCommand(0x37, [1, 0]);
  Future<void> stopSpO2() => sendCommand(0x37, [0, 0]);
  Future<void> requestTemperature() => sendCommand(0x38, [0]);  // ✅ AGGIORNATO da 0x51
  Future<void> requestHRHistory() => sendCommand(0x21, [0]);
  Future<void> requestDeviceInfo() => sendCommand(0x01, [0]);
  Future<void> requestBattery() => sendCommand(0x02, [0]);
  Future<void> deviceReset() => sendCommand(0xF3, [0]);
  Future<void> deviceShutdown() => sendCommand(0xF1, [0]);
  
  /// Comandi con parametri complessi
  Future<void> requestHRData(int timestamp) {
    List<int> params = [
      1,                                    // Prefix
      (timestamp >> 24) & 0xFF,           // Timestamp big-endian
      (timestamp >> 16) & 0xFF,
      (timestamp >> 8) & 0xFF,
      timestamp & 0xFF,
    ];
    return sendCommand(0x22, params);
  }
  
  Future<void> setUserInfo(int age, int sex, int weight, int height, int userId) {
    List<int> params = [
      age & 0xFF,
      sex & 0xFF,
      weight & 0xFF,
      height & 0xFF,
      (userId >> 32) & 0xFF,
      (userId >> 24) & 0xFF,
      (userId >> 16) & 0xFF,
      (userId >> 8) & 0xFF,
      userId & 0xFF,
    ];
    return sendCommand(0x04, params);
  }
  
  Future<void> setUTCTime(int utcTimestamp) {
    List<int> params = [
      (utcTimestamp >> 24) & 0xFF,        // UTC big-endian
      (utcTimestamp >> 16) & 0xFF,
      (utcTimestamp >> 8) & 0xFF,
      utcTimestamp & 0xFF,
    ];
    return sendCommand(0x08, params);
  }
  
  /// Logging strutturato per debug
  void _logCommand(List<int> frame, String commandName) {
    final hexString = frame.map((b) => 
      '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
    
    debugPrint('📡 ===== $commandName COMMAND =====');
    debugPrint('   Frame: $hexString');
    debugPrint('   Length: ${frame[1]} bytes');
    debugPrint('   Command: 0x${frame[2].toRadixString(16).toUpperCase()}');
    debugPrint('   Parameters: ${frame.length > 4 ? frame.sublist(3, frame.length - 1) : 'None'}');
    debugPrint('   Checksum: 0x${frame.last.toRadixString(16).toUpperCase()} (Java algorithm)');
    debugPrint('📡 ================================');
  }
  
  /// Ottiene nome comando per logging
  String _getCommandName(int command) {
    switch (command) {
      case 0x37: return 'SpO2 Control';
      case 0x38: return 'Temperature Request';  // ✅ AGGIORNATO da 0x51
      case 0x21: return 'HR History List';
      case 0x22: return 'HR History Data';
      case 0x01: return 'Device Info';
      case 0x02: return 'Battery Level';
      case 0x03: return 'Get User Info';
      case 0x04: return 'Set User Info';
      case 0x08: return 'Set UTC Time';
      case 0x16: return 'Exercise History';
      case 0x57: return 'HR Alarm Set';
      case 0x5B: return 'HR Alarm Get';
      case 0xF1: return 'Device Shutdown';
      case 0xF3: return 'Device Reset';
      default: return 'Unknown Command';
    }
  }
  
  /// Ottiene delay appropriato per comando
  Duration _getCommandDelay(int command) {
    switch (command) {
      case 0x37: return Duration(milliseconds: 500);  // SpO2 needs more time
      case 0x38: return Duration(milliseconds: 200);  // Temperature (AGGIORNATO da 0x51)
      case 0x21:
      case 0x22: return Duration(milliseconds: 300);  // Historical data
      case 0xF1:
      case 0xF3: return Duration(milliseconds: 1000); // Reset commands
      default: return Duration(milliseconds: 100);    // Standard delay
    }
  }
}
```

### Vantaggi dell'Approccio Generico

1. **Coerenza**: Tutti i comandi usano lo stesso algoritmo di checksum
2. **Manutenibilità**: Un solo punto per modifiche al protocollo
3. **Debugging**: Logging uniforme per tutti i comandi
4. **Error Handling**: Gestione errori centralizzata
5. **Timing**: Delay appropriati per ogni tipo di comando
6. **Scalabilità**: Facile aggiunta di nuovi comandi

### Utilizzo Raccomandato

```dart
// Inizializzazione
final commandManager = CommandManager(_rxCharacteristic);

// Comandi semplici
await commandManager.startSpO2();
await commandManager.requestTemperature();
await commandManager.requestHRHistory();

// Comandi con parametri
await commandManager.setUserInfo(25, 1, 70, 175, 12345);
await commandManager.requestHRData(DateTime.now().millisecondsSinceEpoch ~/ 1000);

// Comando personalizzato
await commandManager.sendCommand(0x37, [2]); // SpO2 inquiry mode
```

### Migrazione da Implementazione Manuale

Se stai già utilizzando comandi manuali, puoi migrare gradualmente:

```dart
// ❌ VECCHIO APPROCCIO (manuale)
List<int> command = [0xFF, 0x06, 0x37, 0x01, 0x00, 0x00];
int sum = 0xFF + 0x06 + 0x37 + 0x01 + 0x00;
int checksum = ((-sum) & 0xFF) ^ 0x3A;
command[5] = checksum & 0xFF;
await _sendCommand(command);

// ✅ NUOVO APPROCCIO (generico)
await commandManager.startSpO2();
```

## Note Tecniche

### Differenze nei Sistemi di Checksum

1. **Checksum Semplice** (OfficialChileafCommands):
   ```dart
   int simpleChecksum = data.fold(0, (sum, byte) => sum + byte) & 0xFF;
   ```

2. **Checksum Java** (Device CL837):
   ```dart
   int javaChecksum = ((-sum) & 0xFF) ^ 0x3A;
   ```

### Timing e Sincronizzazione

- **Delay Command**: 100ms dopo ogni invio
- **SpO2 Timeout**: 3 minuti per safety
- **Response Timeout**: 10 secondi per risposte critiche

### Device Control

- **Device-Controlled Duration**: SpO2 si ferma automaticamente
- **Manual Stop**: Sempre possibile via comando 0x37 mode 0
- **Error Recovery**: Auto-cleanup su timeout o errori

## Esempi di Utilizzo

### Sessione SpO2 Completa

```dart
// 1. Setup callbacks
service.setSpO2Callbacks(
  onValueReceived: (value) => print('SpO2: $value%'),
  onComplete: () => print('Measurement complete'),
  onError: (error) => print('Error: $error'),
);

// 2. Start measurement
await service.startBloodOxygenMeasurement();

// 3. Device invia automaticamente dati
// 4. Stop automatico o manuale
await service.stopBloodOxygenMeasurement();
```

### Richiesta Dati Storici HRV

```dart
// 1. Richiedi lista timestamps
await service.requestHRHistoryList();

// 2. Per ogni timestamp, richiedi dati dettagliati
for (DateTime timestamp in timestamps) {
  await service.requestHRHistoryData(timestamp);
  await Future.delayed(Duration(milliseconds: 500));
}
```

## Conclusioni

Il protocollo CL837 richiede:
- **Checksum Java**: Algoritmo specifico `(-sum) ^ 0x3A`
- **Frame Structure**: Struttura fissa con START_BYTE 0xFF
- **Device Timing**: Rispetto dei timing del dispositivo
- **Error Handling**: Gestione robusta di timeout e errori

Questo sistema garantisce comunicazione affidabile e compatibilità completa con il firmware del dispositivo CL837.
