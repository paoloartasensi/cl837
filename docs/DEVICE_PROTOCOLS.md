# 📡 Device Protocols & BLE Commands

## 📋 Overview

L'applicazione CL837 comunica con il dispositivo wearable attraverso protocolli BLE personalizzati. L'implementazione è basata sull'analisi degli SDK ufficiali Android e iOS, garantendo compatibilità completa con il firmware del dispositivo.

---

## 🔧 BLE Protocol Structure

### Base Command Format

```
[0xFF] [length] [command] [parameters...] [checksum]
```

- **0xFF**: Header byte (costante)
- **length**: Lunghezza totale del pacchetto
- **command**: ID del comando (1 byte)
- **parameters**: Parametri specifici del comando (variabile)
- **checksum**: Byte di checksum per validazione

### Official Commands Class

```dart
class OfficialChileafCommands {
  static List<int> buildOfficialCommand(int command, List<int> params) {
    List<int> packet = [0xFF, 0x00, command]; // Header + placeholder length + command
    packet.addAll(params); // Add parameters

    // Calculate length (total - 2 for header/checksum)
    packet[1] = packet.length - 2;

    // Add checksum
    packet.add(_calculateChecksum(packet));

    return packet;
  }

  static int _calculateChecksum(List<int> data) {
    int sum = 0;
    for (int i = 0; i < data.length; i++) {
      sum += data[i];
    }
    return sum & 0xFF; // Modulo 256
  }
}
```

---

## 📊 Device Commands Reference

### 0x01 - Device Info Request
**Descrizione**: Richiede informazioni di base del dispositivo

**SDK Reference**:
```java
// Android WearManager.java
public void getDeviceInfo() {
    sendCommand((byte) 1, 0);
}
```

**Implementazione**:
```dart
static List<int> getDeviceInfo() {
  return buildOfficialCommand(0x01, []);
}
// Risposta attesa: [0xFF, len, 0x01, info_data..., checksum]
```

### 0x03 - User Info Setting
**Descrizione**: Imposta informazioni utente (età, sesso, peso, altezza)

**SDK Reference**:
```java
// Android WearManager.java
public void setUserInfo(int age, int gender, int height, int weight) {
    sendCommand((byte) 3, age, gender, height, weight);
}
```

**Implementazione**:
```dart
static List<int> setUserInfo(int age, int gender, int height, int weight) {
  return buildOfficialCommand(0x03, [age, gender, height, weight]);
}
// gender: 0=male, 1=female
// height: cm, weight: kg
```

### 0x05 - Sleep History Request
**Descrizione**: Richiede dati storici del sonno (protocollo legacy)

**SDK Reference**:
```java
// Android WearManager.java linea 724
public void getHistoryOfSleep() {
    sendCommand((byte) 5, 2);
}
```

```objectivec
// iOS HeartBLEDevice.m linea 1259
- (void)getSleepData {
    NSString *command = @"ff050502";
}
```

**Implementazione**:
```dart
static List<int> getHistoryOfSleep() {
  return buildOfficialCommand(0x05, [0x02]);
}
// Comando critico: [0xFF, 0x04, 0x05, 0x02, checksum]
```

### 0x08 - UTC Time Sync
**Descrizione**: Sincronizza l'ora UTC sul dispositivo

**SDK Reference**:
```java
// Android WearManager.java
public void setUTCTime() {
    int utc = (int)(System.currentTimeMillis() / 1000);
    sendCommand((byte) 8, utc >> 24, utc >> 16, utc >> 8, utc);
}
```

**Implementazione**:
```dart
static List<int> setUTCTime(int timestamp) {
  List<int> utcBytes = _utcToBytes(timestamp);
  return buildOfficialCommand(0x08, utcBytes);
}

static List<int> _utcToBytes(int timestamp) {
  return [
    (timestamp >> 24) & 0xFF,
    (timestamp >> 16) & 0xFF,
    (timestamp >> 8) & 0xFF,
    timestamp & 0xFF,
  ];
}
```

### 0x13 - Sport Health Data
**Descrizione**: Richiede metriche di salute sportive (VO2 Max, respiratory rate, etc.)

**Struttura Risposta**:
```
Offset | Byte | Significato
-------|------|----------------------------------
   0   | 0xFF | Header
   1   | len  | Lunghezza
   2   | 0x13 | Command ID
   3   | vo2  | VO2 Max (ml/kg/min)
   4   | resp | Respiratory Rate (breaths/min)
   5   | emot | Emotion Level (0-5)
   6   | str  | Stress % (0-100)
   7   | stam | Stamina (0-5)
   8-11| tp   | Total Power (HRV float)
 12-15| lf   | Low Frequency (HRV float)
 16-19| hf   | High Frequency (HRV float)
  20  | chk  | Checksum
```

**SDK Integration**:
```java
// Android callback
public void onHealthReceived(BluetoothDevice device, int vo2Max, int breathRate,
                           int emotionLevel, int stressPercent, int stamina,
                           float tp, float lf, float hf)
```

### 0x21 - HR Record List
**Descrizione**: Richiede lista timestamp dei record HR storici

**SDK Reference**:
```java
// Android WearManager.java
public void getHistoryOfHRRecord() {
    sendCommand((byte) 33, 0);
}
```

**Implementazione**:
```dart
static List<int> getHistoryOfHRRecord() {
  return buildOfficialCommand(0x21, [0x00]);
}
```

### 0x22 - HR History Data
**Descrizione**: Richiede dati HR dettagliati per timestamp specifico

**Implementazione**:
```dart
static List<int> getHistoryOfHRData(int utcTimestamp) {
  List<int> utcBytes = _utcToBytes(utcTimestamp);
  return buildOfficialCommand(0x22, [0x01] + utcBytes);
}

static List<int> getHistoryOfHRDataAll() {
  return buildOfficialCommand(0x22, [0x00]);
}
```

### 0x31 - Sleep Data (Official Protocol)
**Descrizione**: Richiede dati sleep in formato ufficiale (5-min granularity)

**Implementazione**:
```dart
static List<int> getSleepData31() {
  return buildOfficialCommand(0x31, []);
}

static List<int> getSleepDataForTimestamp(int utcTimestamp) {
  List<int> utcBytes = _utcToBytes(utcTimestamp);
  return buildOfficialCommand(0x31, utcBytes);
}
```

### 0x37 - Blood Oxygen Measurement
**Descrizione**: Avvia misurazione SpO2

**Pipeline Completa**:
1. **UI Trigger** → `startBloodOxygenMeasurement()`
2. **Callback Setup** → `_setupBloodOxygenDataMonitoring()`
3. **BLE Command** → Send 0x37
4. **Device Response** → SpO2 data stream
5. **Parse & Validate** → `_handleBloodOxygenReceived()`
6. **UI Update** → Display results

**Implementazione**:
```dart
static List<int> startBloodOxygen() {
  return buildOfficialCommand(0x37, [0x01]); // 0x01 = start
}

static List<int> stopBloodOxygen() {
  return buildOfficialCommand(0x37, [0x00]); // 0x00 = stop
}
```

### 0x40 - Steps Interval Data
**Descrizione**: Richiede dati contapassi per intervalli

**SDK Reference**:
```java
// Android WearManager.java
public void getIntervalSteps() {
    sendCommand((byte) 64, 0);
}
```

**Implementazione**:
```dart
static List<int> getIntervalSteps() {
  return buildOfficialCommand(0x40, [0x00]);
}
```

### 0x45 - Heart Rate Max Request
**Descrizione**: Richiede frequenza cardiaca massima rilevata

**Implementazione**:
```dart
static List<int> getHeartRateMax() {
  return buildOfficialCommand(0x45, []);
}
```

### 0xF3 - Factory Restoration
**Descrizione**: Ripristino alle impostazioni di fabbrica

**SDK Reference**:
```java
// Android WearManager.java linea 673
public void restoration() {
    sendCommand((byte) -13, 0); // -13 = 0xF3 in signed byte
}
```

**Implementazione**:
```dart
static List<int> factoryRestoration() {
  return buildOfficialCommand(0xF3, [0x00]);
}
// Comando critico: [0xFF, 0x04, 0xF3, 0x00, checksum]
```

---

## 🏗️ BLE Service Architecture

### Connection Management

```dart
class ChileafExtendedService {
  BluetoothCharacteristic? _rxCharacteristic;
  BluetoothCharacteristic? _txCharacteristic;

  Future<void> connectToDevice(BluetoothDevice device) async {
    // Discover services
    List<BluetoothService> services = await device.discoverServices();

    // Find CL837 service (custom UUID)
    BluetoothService cl837Service = services.firstWhere(
      (s) => s.uuid.toString() == CL837_SERVICE_UUID,
    );

    // Get characteristics
    _rxCharacteristic = cl837Service.characteristics.firstWhere(
      (c) => c.uuid.toString() == RX_CHARACTERISTIC_UUID,
    );
    _txCharacteristic = cl837Service.characteristics.firstWhere(
      (c) => c.uuid.toString() == TX_CHARACTERISTIC_UUID,
    );

    // Enable notifications
    await _rxCharacteristic?.setNotifyValue(true);

    // Setup data listener
    _rxCharacteristic?.value.listen(_handleIncomingData);
  }
}
```

### Command Transmission

```dart
Future<void> _sendCommand(List<int> command) async {
  if (_txCharacteristic == null) {
    throw Exception('TX characteristic not available');
  }

  debugPrint('📤 Sending command: ${command.map((e) => e.toRadixString(16).padLeft(2, '0')).join(' ')}');

  await _txCharacteristic!.write(command, withoutResponse: false);

  // Wait for acknowledgment (optional)
  await Future.delayed(Duration(milliseconds: 100));
}
```

### Data Reception & Routing

```dart
void _handleIncomingData(List<int> data) {
  if (data.isEmpty || data[0] != 0xFF) {
    debugPrint('❌ Invalid packet header');
    return;
  }

  int command = data[2];
  debugPrint('📥 Received command 0x${command.toRadixString(16)}');

  // Route to appropriate processor
  switch (command) {
    case 0x05:
      _processSleepData05(data);
      break;
    case 0x13:
      _processSportHealthData(data);
      break;
    case 0x21:
    case 0x22:
    case 0x23:
      _processHRHistoryData(data, command);
      break;
    case 0x31:
    case 0x32:
      _processSleepData31(data);
      break;
    case 0x37:
      _processBloodOxygenData(data);
      break;
    case 0x40:
      _processStepsIntervalData(data);
      break;
    default:
      debugPrint('⚠️ Unhandled command: 0x${command.toRadixString(16)}');
  }
}
```

---

## 📊 Data Processing Pipeline

### General Packet Validation

```dart
bool _validatePacket(List<int> data) {
  if (data.length < 5) return false; // Minimum packet size
  if (data[0] != 0xFF) return false; // Invalid header

  int length = data[1];
  if (data.length != length + 2) return false; // Length mismatch

  int checksum = data.last;
  int calculated = _calculateChecksum(data.sublist(0, data.length - 1));

  return checksum == calculated;
}
```

### Big-Endian Multi-Byte Parsing

```dart
int _getLongParse(List<int> bytes, int pos, int len) {
  int val = 0;
  int end = pos + len;
  for (int i = pos; i < end && i < bytes.length; i++) {
    val = (val << 8) | bytes[i];
  }
  return val;
}
```

### UTC Timestamp Handling

```dart
int _restoreZoneUTC(int stamp) {
  // Convert from UTC seconds to local milliseconds
  DateTime utcTime = DateTime.fromMillisecondsSinceEpoch(stamp * 1000, isUtc: true);
  DateTime localTime = utcTime.toLocal();
  return localTime.millisecondsSinceEpoch;
}
```

---

## 🔄 Real-time Data Streaming

### Stream Controllers Setup

```dart
final StreamController<SleepData31> _sleepData31Controller = StreamController.broadcast();
final StreamController<HRData> _hrDataController = StreamController.broadcast();
final StreamController<SpO2Data> _spo2DataController = StreamController.broadcast();
final StreamController<SportHealthData> _sportHealthController = StreamController.broadcast();

Stream<SleepData31> get sleepData31Stream => _sleepData31Controller.stream;
Stream<HRData> get hrDataStream => _hrDataController.stream;
Stream<SpO2Data> get spo2DataStream => _spo2DataController.stream;
Stream<SportHealthData> get sportHealthStream => _sportHealthController.stream;
```

### Stream Broadcasting

```dart
void _broadcastSportHealthData(List<int> data) {
  if (data.length < 9) return;

  SportHealthData healthData = SportHealthData(
    vo2Max: data[3],
    respiratoryRate: data[4],
    emotionLevel: data[5],
    stressPercent: data[6],
    stamina: data[7],
    // Parse HRV floats if available
    totalPower: data.length > 11 ? _parseFloat(data.sublist(8, 12)) : 0.0,
    lowFrequency: data.length > 15 ? _parseFloat(data.sublist(12, 16)) : 0.0,
    highFrequency: data.length > 19 ? _parseFloat(data.sublist(16, 20)) : 0.0,
  );

  _sportHealthController.add(healthData);
}
```

---

## 🧪 Testing & Validation

### Command Testing

```dart
void testDeviceCommands() {
  // Test checksum calculation
  List<int> command = OfficialChileafCommands.getHistoryOfSleep();
  assert(command.last == _calculateChecksum(command.sublist(0, command.length - 1)));

  // Test UTC conversion
  int timestamp = 1729728000; // Test timestamp
  List<int> utcBytes = OfficialChileafCommands._utcToBytes(timestamp);
  int reconstructed = _getLongParse(utcBytes, 0, 4);
  assert(reconstructed == timestamp);
}
```

### BLE Connection Testing

```dart
void testBLEConnection() async {
  // Mock device connection
  BluetoothDevice mockDevice = MockBluetoothDevice();

  // Test service discovery
  List<BluetoothService> services = await mockDevice.discoverServices();
  assert(services.any((s) => s.uuid.toString() == CL837_SERVICE_UUID));

  // Test characteristic access
  BluetoothService cl837Service = services.firstWhere(
    (s) => s.uuid.toString() == CL837_SERVICE_UUID,
  );
  assert(cl837Service.characteristics.length >= 2);

  // Test notification setup
  BluetoothCharacteristic rxChar = cl837Service.characteristics.firstWhere(
    (c) => c.properties.notify,
  );
  await rxChar.setNotifyValue(true);
  assert(rxChar.isNotifying);
}
```

---

## 🔧 Troubleshooting

### Common Issues

#### Command Not Responding
```dart
// Check device connection
if (_txCharacteristic == null) {
  debugPrint('❌ TX characteristic null');
  return;
}

// Check command format
List<int> command = OfficialChileafCommands.getHistoryOfSleep();
debugPrint('Command: ${command.map((e) => e.toRadixString(16)).join(' ')}');

// Verify checksum
int checksum = command.last;
int calculated = _calculateChecksum(command.sublist(0, command.length - 1));
assert(checksum == calculated);
```

#### Invalid Packet Reception
```dart
void debugPacket(List<int> data) {
  debugPrint('Raw data: ${data.map((e) => e.toRadixString(16).padLeft(2, '0')).join(' ')}');
  debugPrint('Length: ${data.length}');
  if (data.isNotEmpty) {
    debugPrint('Header: 0x${data[0].toRadixString(16)}');
    if (data.length > 1) debugPrint('Declared length: ${data[1]}');
    if (data.length > 2) debugPrint('Command: 0x${data[2].toRadixString(16)}');
  }
}
```

#### Checksum Errors
```dart
int calculateChecksum(List<int> data) {
  int sum = 0;
  for (int byte in data) {
    sum = (sum + byte) & 0xFF;
  }
  return sum;
}

// Validate received packet
bool validateChecksum(List<int> packet) {
  if (packet.length < 2) return false;
  List<int> data = packet.sublist(0, packet.length - 1);
  int receivedChecksum = packet.last;
  int calculatedChecksum = calculateChecksum(data);
  return receivedChecksum == calculatedChecksum;
}
```

---

## 📈 Performance Optimization

### Connection Pooling
```dart
class BLEConnectionPool {
  static const int MAX_CONNECTIONS = 3;
  final Queue<BluetoothDevice> _connectedDevices = Queue();

  Future<BluetoothDevice> getConnection(String deviceId) async {
    // Reuse existing connection if available
    BluetoothDevice? existing = _connectedDevices.firstWhereOrNull(
      (d) => d.id == deviceId,
    );

    if (existing != null) return existing;

    // Create new connection
    BluetoothDevice newDevice = await BluetoothDevice.fromId(deviceId);
    await newDevice.connect();

    // Manage pool size
    if (_connectedDevices.length >= MAX_CONNECTIONS) {
      BluetoothDevice oldest = _connectedDevices.removeFirst();
      await oldest.disconnect();
    }

    _connectedDevices.add(newDevice);
    return newDevice;
  }
}
```

### Command Batching
```dart
class CommandBatch {
  final List<List<int>> _commands = [];
  Timer? _batchTimer;

  void addCommand(List<int> command) {
    _commands.add(command);

    // Delay execution to allow batching
    _batchTimer?.cancel();
    _batchTimer = Timer(Duration(milliseconds: 50), _executeBatch);
  }

  void _executeBatch() async {
    for (List<int> command in _commands) {
      await _sendCommand(command);
      await Future.delayed(Duration(milliseconds: 10)); // Inter-command delay
    }
    _commands.clear();
  }
}
```

---

## 🚀 Future Protocol Extensions

### Enhanced Commands
1. **0x50-0x5F**: Advanced HRV metrics
2. **0x60-0x6F**: Environmental sensors
3. **0x70-0x7F**: Multi-sport modes
4. **0x80-0x8F**: Firmware updates

### Protocol Improvements
1. **Compression**: LZ4 for large data packets
2. **Encryption**: AES-128 for sensitive health data
3. **Acknowledgment**: Mandatory ACK for critical commands
4. **Flow Control**: Prevent device buffer overflow

---

**Created:** November 2025  
**Compatibility:** CL837 firmware v3.0+  
**SDK Reference:** Android/iOS official SDKs</content>
<parameter name="filePath">c:\Users\Admin\Documents\visualstudiocode\cl837\docs\DEVICE_PROTOCOLS.md