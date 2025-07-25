# 🔬 CL837 Technical Protocol Guide - Guida Tecnica Protocolli

## 📡 Panoramica Protocollo BLE Chileaf

Il dispositivo CL837 utilizza il protocollo Chileaf SDK v0.6 per comunicazioni BLE ottimizzate con comandi specializzati per sensori medici.

---

## 🩸 1. PROTOCOLLO SPO2 (SATURAZIONE OSSIGENO)

### 📊 **Comandi BLE SpO2**

#### **Avvio Misurazione SpO2**
```dart
// Comando principale: setBloodOxygen
static List<int> setBloodOxygen(int mode) => _buildCommand(0x37, [mode]);

// Parametri:
// mode = 1: Avvia misurazione SpO2
// mode = 0: Ferma misurazione SpO2
```

#### **Controllo LED Diagnostico**
```dart
// Comando LED: Test illuminazione sensore
static List<int> setLED(int mode) => _buildCommand(0x36, [mode]);

// Parametri:
// mode = 1: Accendi LED rosso (diagnostico)
// mode = 0: Spegni LED rosso
```

### 📊 **Formato Dati SpO2 Ricevuti**
```dart
// Pacchetto dati SpO2 (7 bytes):
// [header] [length] [command] [spo2_value] [posture] [signal_quality] [wearing_status]

// Byte 3: spo2_value
//   0-1: Status (device not ready/measuring)
//   70-100: Valore SpO2 effettivo in percentuale
//   >100: Errore o formato dati non valido

// Byte 4: posture (postura polso)
//   0: Postura scorretta (polso non rivolto verso l'alto)
//   1: Postura corretta (polso face-up)

// Byte 5: signal_quality (qualità segnale)
//   0-7: Segnale debole/insufficiente
//   8-15: Segnale buono/eccellente (ideale >15)

// Byte 6: wearing_status (rilevamento indossato)
//   0: Device non indossato
//   1: Device indossato correttamente
```

### 🎯 **Logica Terminazione Intelligente SpO2**
```dart
// Criteri per lettura valida WatchFit:
bool isValidSpO2Reading(SpO2Data data) {
  return data.spo2Value != null &&
         data.spo2Value! >= 70 && data.spo2Value! <= 100 &&
         data.correctWristPosture &&
         data.isWearing &&
         data.signalQuality >= 8;
}

// Auto-terminazione con 2 letture consecutive valide:
// - Riduce tempo test da 50s a ~10-20s
// - Migliora user experience
// - Mantiene accuratezza clinica
```

---

## ❤️‍🩹 2. PROTOCOLLO HRV (VARIABILITÀ CARDIACA)

### 📊 **Source Dati HRV**
```dart
// HRV utilizza il main heart rate stream (stesso dei SENSORI)
// Vantaggi: dati real-time, accuratezza massima, no duplicazione comandi

Stream<HeartRateData> mainHeartRateStream = heartRateService.dataStream;

// Collezione RR intervals durante test HRV:
heartRateData.rrIntervals?.forEach((interval) {
  if (interval >= 300.0 && interval <= 2000.0) { // Range fisiologico
    collectedRRIntervals.add(interval);
  }
});
```

### 📊 **Calcoli Metriche HRV**
```dart
// RMSSD (Root Mean Square of Successive Differences)
double calculateRMSSD(List<double> rrIntervals) {
  if (rrIntervals.length < 2) return 0.0;
  
  double sumSquaredDiffs = 0.0;
  for (int i = 1; i < rrIntervals.length; i++) {
    double diff = rrIntervals[i] - rrIntervals[i-1];
    sumSquaredDiffs += diff * diff;
  }
  
  return sqrt(sumSquaredDiffs / (rrIntervals.length - 1));
}

// SDNN (Standard Deviation of NN intervals)
double calculateSDNN(List<double> rrIntervals) {
  if (rrIntervals.isEmpty) return 0.0;
  
  double mean = rrIntervals.reduce((a, b) => a + b) / rrIntervals.length;
  double variance = rrIntervals
      .map((rr) => pow(rr - mean, 2))
      .reduce((a, b) => a + b) / rrIntervals.length;
  
  return sqrt(variance);
}

// pNN50 (Percentage of NN intervals > 50ms different)
double calculatePNN50(List<double> rrIntervals) {
  if (rrIntervals.length < 2) return 0.0;
  
  int count = 0;
  for (int i = 1; i < rrIntervals.length; i++) {
    if ((rrIntervals[i] - rrIntervals[i-1]).abs() > 50.0) {
      count++;
    }
  }
  
  return (count / (rrIntervals.length - 1)) * 100.0;
}
```

### 🏥 **Validazione Clinica HRV**
```dart
// Standard Elite HRV implementato:
class HRVValidation {
  static const Duration MIN_DURATION = Duration(seconds: 60);
  static const int MIN_RR_INTERVALS = 50;
  static const double MIN_RR_MS = 300.0;  // 200 BPM max
  static const double MAX_RR_MS = 2000.0; // 30 BPM min
  
  static bool isValidSession(List<double> rrIntervals, Duration duration) {
    return duration >= MIN_DURATION &&
           rrIntervals.length >= MIN_RR_INTERVALS &&
           rrIntervals.every((rr) => rr >= MIN_RR_MS && rr <= MAX_RR_MS);
  }
}
```

---

## 🫀 3. PROTOCOLLO HEART RATE (FREQUENZA CARDIACA)

### 📊 **Configurazione HR Monitoring**
```dart
// Attivazione allarme HR per monitoraggio continuo
static List<int> setHeartRateAlarm(bool enable) => 
    _buildCommand(0x38, [enable ? 1 : 0]);

// Configurazione soglie HR
static List<int> setHRRange(int minHR, int maxHR, int goalHR) => 
    _buildCommand(0x39, [minHR, maxHR, goalHR]);
```

### 📊 **Dati HR Storici**

#### **Fase 1: Lista Record HR (0x21)**
```dart
// Comando: getHistoryOfHRRecord()
static List<int> getHistoryOfHRRecord() => _buildCommand(0x21);

// Formato risposta:
// [0xFF] [len] [0x21] [record_count] [record1_timestamp(4bytes)] [record1_duration(2bytes)] ...

void parseHRRecordList(List<int> data) {
  if (data.length < 5) return;
  
  int recordCount = data[3];
  List<HRRecord> records = [];
  
  for (int i = 0; i < recordCount; i++) {
    int offset = 4 + (i * 6); // 4 bytes timestamp + 2 bytes duration
    if (offset + 6 <= data.length) {
      int timestamp = (data[offset] << 24) | (data[offset+1] << 16) | 
                     (data[offset+2] << 8) | data[offset+3];
      int duration = (data[offset+4] << 8) | data[offset+5];
      
      records.add(HRRecord(
        timestamp: timestamp,
        duration: duration,
        dateTime: DateTime.fromMillisecondsSinceEpoch(timestamp * 1000),
      ));
    }
  }
}
```

#### **Fase 2: Dati HR Dettagliati (0x22)**
```dart
// Comando: getHistoryOfHRData(timestamp)
static List<int> getHistoryOfHRData(int timestamp) => _buildCommand(0x22, [
  1, // Indicatore richiesta
  (timestamp >> 24) & 0xFF,
  (timestamp >> 16) & 0xFF,
  (timestamp >> 8) & 0xFF,
  timestamp & 0xFF
]);

// Formato risposta:
// [0xFF] [len] [0x22] [timestamp(4bytes)] [hr_count] [hr1] [hr2] ... [avg_hr] [max_hr] [min_hr]

void parseHRDetailData(List<int> data) {
  if (data.length < 8) return;
  
  int timestamp = (data[3] << 24) | (data[4] << 16) | (data[5] << 8) | data[6];
  int hrCount = data[7];
  
  List<int> hrValues = [];
  for (int i = 0; i < hrCount && i + 8 < data.length; i++) {
    hrValues.add(data[8 + i]);
  }
  
  int avgHR = data[8 + hrCount];
  int maxHR = data[9 + hrCount];
  int minHR = data[10 + hrCount];
}
```

#### **Fase 3: Dati HR Estesi con RR Intervals (0x23)**
```dart
// Comando: getHistoryOfHRDataExtended(timestamp)
static List<int> getHistoryOfHRDataExtended(int timestamp) => _buildCommand(0x23, [
  1, // Indicatore richiesta
  (timestamp >> 24) & 0xFF,
  (timestamp >> 16) & 0xFF,
  (timestamp >> 8) & 0xFF,
  timestamp & 0xFF
]);

// Formato risposta:
// [0xFF] [len] [0x23] [timestamp(4bytes)] [rr_count] [rr1(2bytes)] [rr2(2bytes)] ... [quality_score]

void parseHRExtendedData(List<int> data) {
  if (data.length < 8) return;
  
  int timestamp = (data[3] << 24) | (data[4] << 16) | (data[5] << 8) | data[6];
  int rrCount = data[7];
  
  List<int> rrIntervals = [];
  for (int i = 0; i < rrCount; i++) {
    int offset = 8 + (i * 2);
    if (offset + 1 < data.length) {
      int rrInterval = (data[offset] << 8) | data[offset + 1];
      rrIntervals.add(rrInterval);
    }
  }
  
  int qualityScore = data[8 + (rrCount * 2)];
  
  // Calcola metriche HRV da dati storici
  if (rrIntervals.length > 1) {
    double rmssd = calculateRMSSD(rrIntervals.map((e) => e.toDouble()).toList());
    double sdnn = calculateSDNN(rrIntervals.map((e) => e.toDouble()).toList());
  }
}
```

---

## 🌡️ 4. PROTOCOLLO TEMPERATURA

### 📊 **Comando Temperatura**
```dart
// Comando: getTemperature (polling automatico ogni 5s)
static List<int> getTemperature() => _buildCommand(0x31);

// Background polling implementato per monitoraggio continuo
Timer.periodic(Duration(seconds: 5), (timer) {
  if (isConnected) {
    sendCommand(OfficialChileafCommands.getTemperature());
  }
});
```

### 📊 **Formato Dati Temperatura**
```dart
// Pacchetto temperatura (múltiple sensori):
// [header] [length] [command] [ambient_temp(2bytes)] [wrist_temp(2bytes)] [body_temp(2bytes)]

void parseTemperatureData(List<int> data) {
  if (data.length < 9) return;
  
  // Temperatura ambiente (bytes 3-4)
  int ambientRaw = (data[3] << 8) | data[4];
  double ambientTemp = ambientRaw / 100.0; // Scala 0.01°C
  
  // Temperatura polso (bytes 5-6)
  int wristRaw = (data[5] << 8) | data[6];
  double wristTemp = wristRaw / 100.0;
  
  // Temperatura corporea stimata (bytes 7-8)
  int bodyRaw = (data[7] << 8) | data[8];
  double bodyTemp = bodyRaw / 100.0;
  
  // Validazione range fisiologici
  if (bodyTemp >= 35.0 && bodyTemp <= 42.0) {
    // Temperatura corporea nel range normale
  }
}
```

### 🎯 **Debouncing e Ottimizzazione**
```dart
// Debouncing per ridurre spam log temperatura:
class TemperatureDebouncer {
  DateTime? lastLogTime;
  String? lastData;
  static const Duration minInterval = Duration(seconds: 10);
  static const double significantChange = 0.5; // °C
  
  bool shouldLog(String newData, double newTemp, double lastTemp) {
    final now = DateTime.now();
    
    if (lastLogTime == null) return true; // Prima lettura
    if (now.difference(lastLogTime!) >= minInterval) return true; // Timeout
    if ((newTemp - lastTemp).abs() >= significantChange) return true; // Cambio significativo
    
    return false;
  }
}
```

---

## 🔋 5. PROTOCOLLO BATTERIA

### 📊 **Monitoraggio Batteria Standard BLE**
```dart
// UUID Standard BLE Battery Service
static const String BATTERY_SERVICE_UUID = "180F";
static const String BATTERY_LEVEL_CHAR_UUID = "2A19";

// Lettura livello batteria (0-100%)
Future<int?> readBatteryLevel() async {
  final characteristic = await findCharacteristic(BATTERY_LEVEL_CHAR_UUID);
  if (characteristic != null) {
    final value = await characteristic.read();
    return value.isNotEmpty ? value[0] : null; // Single byte 0-100%
  }
  return null;
}

// Notifiche automatiche per batteria scarica
void setupBatteryNotifications() {
  batteryStream.listen((level) {
    if (level <= 15) {
      showLowBatteryAlert(); // Alert utente
    }
  });
}
```

---

## 🏃‍♂️ 6. PROTOCOLLI DATI STORICI

### 📊 **Exercise History (0x16)**
```dart
// Comando: getHistoryOfSport
static List<int> getHistoryOfSport() => _buildCommand(0x16);

// Formato con parser enhanced (reverse-engineered dall'app originale):
List<ExerciseHistoryEntry> parseExerciseHistory(Uint8List data) {
  final entries = <ExerciseHistoryEntry>[];
  
  for (int i = 0; i < data.lengthInBytes / 10; i++) {
    int offset = i * 10;
    if (offset + 10 > data.lengthInBytes) break;
    
    int stamp = _getLongParse(data, offset, 4);     // Timestamp UTC
    int step = _getLongParse(data, offset + 4, 3);  // Passi (3 bytes)
    int calorie = _getLongParse(data, offset + 7, 3); // Calorie (3 bytes)
    
    entries.add(ExerciseHistoryEntry(
      timestamp: _restoreZoneUTC(stamp),
      steps: step,
      calories: calorie,
    ));
  }
  
  return entries;
}
```

### 📊 **Sleep History (0x05)**
```dart
// Comando: getHistoryOfSleep
static List<int> getHistoryOfSleep() => _buildCommand(0x05);

// Parser sleep con azioni dettagliate:
List<SleepHistoryEntry> parseSleepHistory(Uint8List data) {
  final entries = <SleepHistoryEntry>[];
  if (data.lengthInBytes < 4) return entries;

  if (_getLongParse(data, 3, 1) == 3) { // Sleep indicator
    int i = 4;
    while (i < data.lengthInBytes) {
      if (i + 5 > data.lengthInBytes) break;
      
      int actionCount = data[i++];
      int timestamp = _getLongParse(data, i, 4);
      i += 4;
      
      DateTime sleepTime = DateTime.fromMillisecondsSinceEpoch(
        (timestamp * 1000) - 28800000, isUtc: true); // Timezone correction
      
      List<int> actions = data.sublist(i, i + actionCount);
      i += actionCount;
      
      entries.add(SleepHistoryEntry(
        timestamp: sleepTime, 
        actions: actions
      ));
    }
  }
  
  return entries;
}
```

### 📊 **Interval Steps (0x40)**
```dart
// Comando: getIntervalSteps
static List<int> getIntervalSteps() => _buildCommand(0x40);

// Parser passi intervallari (ogni 8 bytes):
List<IntervalStepEntry> parseIntervalSteps(Uint8List data) {
  final entries = <IntervalStepEntry>[];
  
  for (int i = 0; i < data.lengthInBytes / 8; i++) {
    int offset = i * 8;
    if (offset + 8 > data.lengthInBytes) break;

    int stamp = _getLongParse(data, offset, 4);     // Timestamp
    int steps = _getLongParse(data, offset + 4, 4); // Steps count
    
    entries.add(IntervalStepEntry(
      timestamp: _restoreZoneUTC(stamp),
      steps: steps,
    ));
  }
  
  return entries;
}
```

---

## 🛠️ 7. UTILITY E HELPER FUNCTIONS

### 📊 **Parsing Utilities**
```dart
// Parsing little-endian multi-byte
int _getLongParse(List<int> data, int offset, int length) {
  int result = 0;
  for (int i = 0; i < length; i++) {
    result |= (data[offset + i] & 0xFF) << (i * 8);
  }
  return result;
}

// Conversione timestamp UTC
DateTime _restoreZoneUTC(int timestamp) {
  return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
}

// Build comando con checksum
List<int> _buildCommand(int command, [List<int>? params]) {
  List<int> cmd = [0xFF]; // Header
  
  int payloadLength = 1 + (params?.length ?? 0); // Command + params
  cmd.add(payloadLength);
  cmd.add(command);
  
  if (params != null) {
    cmd.addAll(params);
  }
  
  // Calcola checksum
  int checksum = 0;
  for (int i = 1; i < cmd.length; i++) { // Skip header
    checksum ^= cmd[i];
  }
  cmd.add(checksum);
  
  return cmd;
}
```

### 📊 **Validazione Dati**
```dart
// Validazione range fisiologici
class DataValidator {
  static bool isValidSpO2(int value) => value >= 70 && value <= 100;
  static bool isValidHR(int bpm) => bpm >= 30 && bpm <= 220;
  static bool isValidTemp(double celsius) => celsius >= 15.0 && celsius <= 45.0;
  static bool isValidRR(double ms) => ms >= 300.0 && ms <= 2000.0;
  
  // Validazione qualità segnale
  static bool isGoodSignalQuality(int quality) => quality >= 8;
  static bool isExcellentSignalQuality(int quality) => quality >= 15;
}
```

---

## 🔄 8. GESTIONE CONNESSIONE E STREAM

### 📊 **Setup Stream Dati**
```dart
// Configurazione stream per monitoraggio real-time
class DataStreamManager {
  final Map<String, StreamController> _controllers = {};
  
  void setupStreams() {
    // SpO2 stream
    _controllers['spo2'] = StreamController<SpO2Data>.broadcast();
    
    // Temperature stream (background polling)
    Timer.periodic(Duration(seconds: 5), (_) => requestTemperature());
    
    // HR stream (continuo)
    _controllers['hr'] = StreamController<HeartRateData>.broadcast();
    
    // HRV stream (calcolato da HR)
    _controllers['hrv'] = StreamController<HRVData>.broadcast();
  }
  
  // Cleanup risorse
  void dispose() {
    _controllers.values.forEach((controller) => controller.close());
    _controllers.clear();
  }
}
```

### 📊 **Error Handling e Retry**
```dart
// Gestione errori e retry automatico
class BLEErrorHandler {
  static const int MAX_RETRIES = 3;
  static const Duration RETRY_DELAY = Duration(seconds: 2);
  
  static Future<bool> executeWithRetry(Future<void> Function() command) async {
    for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
      try {
        await command();
        return true;
      } catch (e) {
        debugPrint('Command failed (attempt ${attempt + 1}/$MAX_RETRIES): $e');
        if (attempt < MAX_RETRIES - 1) {
          await Future.delayed(RETRY_DELAY);
        }
      }
    }
    return false;
  }
}
```

---

## 📈 9. PERFORMANCE E OTTIMIZZAZIONI

### 📊 **Ottimizzazioni Implementate**
```dart
// 1. Debouncing log temperatura (max 10s)
// 2. Terminazione anticipata SpO2 (2 letture valide)
// 3. Background polling ottimizzato (solo temperatura)
// 4. Stream unificati per ridurre overhead BLE
// 5. Validazione client-side per ridurre traffic

// Memory management per grandi dataset
class DataBuffer<T> {
  final int maxSize;
  final Queue<T> _buffer = Queue<T>();
  
  DataBuffer({this.maxSize = 1000});
  
  void add(T item) {
    _buffer.add(item);
    if (_buffer.length > maxSize) {
      _buffer.removeFirst(); // Rimuovi più vecchio
    }
  }
  
  List<T> getLatest(int count) => 
      _buffer.take(count).toList();
}
```

---

## 🔒 10. SICUREZZA E PRIVACY

### 📊 **Data Privacy**
```dart
// Tutti i dati rimangono locali sul device
// Export JSON manuale (user-controlled)
// No cloud storage automatico
// SharedPreferences per persistenza locale

class PrivacyManager {
  static Future<void> exportData() async {
    final data = await _gatherAllHealthData();
    final jsonString = jsonEncode(data);
    
    // Share locale (no upload automatico)
    await Share.share(jsonString, subject: 'CL837 Health Data Export');
  }
  
  static Future<void> clearAllData() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();
    // User-initiated data deletion
  }
}
```

### 🔒 **Validazione Input**
```dart
// Sanitizzazione input per prevenire injection
class InputValidator {
  static bool isValidTimestamp(int timestamp) {
    final year2020 = DateTime(2020).millisecondsSinceEpoch ~/ 1000;
    final year2050 = DateTime(2050).millisecondsSinceEpoch ~/ 1000;
    return timestamp >= year2020 && timestamp <= year2050;
  }
  
  static List<int> sanitizeCommand(List<int> cmd) {
    // Verifica header, lunghezza, checksum
    if (cmd.isEmpty || cmd[0] != 0xFF) return [];
    if (cmd.length < 3) return [];
    
    // Validazione checksum
    int expectedChecksum = 0;
    for (int i = 1; i < cmd.length - 1; i++) {
      expectedChecksum ^= cmd[i];
    }
    
    return expectedChecksum == cmd.last ? cmd : [];
  }
}
```

---

## 📝 **Note Implementazione**

### ✅ **Features Implementate**
- ✅ SpO2 con terminazione intelligente (WatchFit optimized)
- ✅ HRV con standard Elite HRV (60s minimum)
- ✅ Temperature background polling (5s interval)
- ✅ HR continuous monitoring con RR intervals
- ✅ Data persistence con SharedPreferences
- ✅ JSON export completo con share nativo
- ✅ Validazione clinica multi-livello
- ✅ Error handling e retry automatico

### 🔄 **In Sviluppo**
- 🔄 Sleep analysis avanzata
- 🔄 Stress level detection
- 🔄 Extended health metrics (comando 0x75)
- 🔄 Machine learning per pattern recognition

### 📱 **Compatibilità**
- **Flutter**: 3.0.0+
- **Dart**: 2.17.0+
- **iOS**: 12.0+
- **Android**: API 23+ (Android 6.0)
- **BLE**: 4.0+ required

---

**🔬 Disclaimer Tecnico**: Questa documentazione copre l'implementazione attuale del protocollo Chileaf v0.6. Per modifiche al protocollo, consultare la documentazione SDK ufficiale Chileaf.
