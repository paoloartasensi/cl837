# CL837 Device Command Protocol - Flutter/Dart Implementation Guide

## Estratto completo dal codice Java decompilato dell'app originale

### Tipi di dati supportati (TYPE constants)

```dart
// lib/models/device_protocol_constants.dart
class DeviceProtocolConstants {
  static const int TYPE_HEART = 4;         // Heart rate data
  static const int TYPE_SPORT = 2;         // Sport/Exercise data  
  static const int TYPE_SLEEP = 22;        // Sleep data
  static const int TYPE_RESPIRATORY = 8;   // Respiratory rate records
  static const int TYPE_INTERVALS = 18;    // Interval steps
  static const int TYPE_SINGLE_TAP = 20;   // Single tap records
  static const int TYPE_HEARTS = 6;        // Heart rate history
  static const int TYPE_HEART_RR = 8;      // RR intervals
  static const int TYPE_HEART_RRS = 16;    // RR data streams
  static const int TYPE_HISTORY_3D = 24;   // 3D sensor history
  
  static const int END_TAG = 0xFFFFFFFF;   // End of multi-packet data
}
```

## Comandi BLE per ricevere dati (intValue dal byte[2])

### 🔴 Dati Real-time

#### **Comando 4 - Heart Rate**
- **Formato BLE**: `[length, 0x??, 0x04, heart_rate, type]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/heart_rate_service.dart
class HeartRateService {
  void parseHeartRateData(List<int> data) {
    if (data.length >= 5 && data[2] == 0x04) {
      int heartRate = data[3];
      int type = data[4];
      onHeartRateReceived(heartRate, type);
    }
  }
  
  void onHeartRateReceived(int heartRate, int type) {
    // Implementa callback per UI
    print('Heart Rate: $heartRate BPM, Type: $type');
  }
}
```

#### **Comando 12 - Accelerometro Real-time**
- **Formato BLE**: `[length, 0x??, 0x0C, x1, y1, z1, x2, y2, z2, ...]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/accelerometer_service.dart
class AccelerometerService {
  void parseAccelerometerData(List<int> data) {
    if (data.length >= 3 && data[2] == 0x0C) {
      List<int> payload = data.sublist(3);
      
      // Ogni gruppo di 6 byte = 1 campione (x,y,z)
      for (int i = 0; i < payload.length ~/ 6; i++) {
        int offset = i * 6;
        int x = _getIntValue(payload, offset, 2);     // 2 bytes per asse
        int y = _getIntValue(payload, offset + 2, 2);
        int z = _getIntValue(payload, offset + 4, 2);
        onAccelerometerReceived(x, y, z);
      }
    }
  }
  
  void onAccelerometerReceived(int x, int y, int z) {
    print('Accelerometer - X: $x, Y: $y, Z: $z');
  }
  
  int _getIntValue(List<int> data, int offset, int length) {
    int value = 0;
    for (int i = 0; i < length; i++) {
      value = (value << 8) | (data[offset + i] & 0xFF);
    }
    return value;
  }
}
```

#### **Comando 19 - Sport Health Real-time**
- **Formato BLE**: `[length, 0x??, 0x13, param1, param2, param3, param4, param5]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/sport_health_service.dart
class SportHealthService {
  void parseSportHealthData(List<int> data) {
    if (data.length >= 8 && data[2] == 0x13) {
      int param1 = data[3];
      int param2 = data[4];
      int param3 = data[5];
      int param4 = data[6];
      int param5 = data[7];
      onSportHealthReceived(param1, param2, param3, param4, param5);
    }
  }
  
  void onSportHealthReceived(int p1, int p2, int p3, int p4, int p5) {
    print('Sport Health: $p1, $p2, $p3, $p4, $p5');
  }
}
```

#### **Comando 21 - Sport Data Real-time**
- **Formato BLE**: `[length, 0x??, 0x15, steps[3], distance[3], calories[3]]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/sport_data_service.dart
class SportDataService {
  void parseSportData(List<int> data) {
    if (data.length >= 12 && data[2] == 0x15) {
      int steps = _getIntParse(data, 3, 3);      // 3 bytes
      int distance = _getIntParse(data, 6, 3);   // 3 bytes  
      int calories = _getIntParse(data, 9, 3);   // 3 bytes
      onSportReceived(steps, distance, calories);
    }
  }
  
  void onSportReceived(int steps, int distance, int calories) {
    print('Sport Data - Steps: $steps, Distance: $distance, Calories: $calories');
  }
  
  int _getIntParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
}
```

#### **Comando 55 - SpO2 (Blood Oxygen)**
- **Formato BLE**: `[length, len, 0x37, spo2, param1, gesture, pi, onwrist]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/spo2_service.dart
class SpO2Service {
  void parseSpO2Data(List<int> data) {
    if (data.length > 6 && data[2] == 0x37) {
      int spo2 = data[3];
      if (data[1] <= 8) return; // Length check
      
      String param1 = data[4].toString();
      int gesture = data[5];
      int piValue = data[6];
      int onWrist = data[7];
      
      onBloodOxygenReceived(spo2, param1, gesture, piValue, onWrist);
    }
  }
  
  void onBloodOxygenReceived(int spo2, String param1, int gesture, int piValue, int onWrist) {
    print('SpO2: $spo2%, Gesture: $gesture, PI: $piValue, On Wrist: ${onWrist == 1}');
  }
}
```

#### **Comando 56 - Temperature**
- **Formato BLE**: `[length, 0x??, 0x38, env_temp[2], wrist_temp[2], body_temp[2]]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/temperature_service.dart
class TemperatureService {
  void parseTemperatureData(List<int> data) {
    if (data.length >= 9 && data[2] == 0x38) {
      double envTemp = _getIntParse(data, 3, 2) / 10.0;    // Environment temp
      double wristTemp = _getIntParse(data, 5, 2) / 10.0;  // Wrist temp  
      double bodyTemp = _getIntParse(data, 7, 2) / 10.0;   // Body temp
      
      onTemperatureReceived(envTemp, wristTemp, bodyTemp);
    }
  }
  
  void onTemperatureReceived(double environment, double wrist, double body) {
    print('Temperature - Env: ${environment}°C, Wrist: ${wrist}°C, Body: ${body}°C');
  }
  
  int _getIntParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
}
```

#### **Comando 63 - Bluetooth Status**
- **Formato BLE**: `[length, 0x??, 0x3F, status]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/bluetooth_status_service.dart
class BluetoothStatusService {
  void parseBluetoothStatus(List<int> data) {
    if (data.length >= 4 && data[2] == 0x3F) {
      bool isConnected = data[3] == 1;
      onBluetoothStatusReceived(isConnected);
    }
  }
  
  void onBluetoothStatusReceived(bool isConnected) {
    print('Bluetooth Status: ${isConnected ? "Connected" : "Disconnected"}');
  }
}
```

#### **Comando 70 - Heart Rate Status**
- **Formato BLE**: `[length, 0x??, 0x46, ??, param1, param2, param3]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/hr_status_service.dart
class HeartRateStatusService {
  void parseHeartRateStatus(List<int> data) {
    if (data.length >= 7 && data[2] == 0x46) {
      int param1 = data[4];
      int param2 = data[5];
      int param3 = data[6];
      onHeartRateStatusReceived(param1, param2, param3);
    }
  }
  
  void onHeartRateStatusReceived(int p1, int p2, int p3) {
    print('HR Status: $p1, $p2, $p3');
  }
}
```

### 🟡 Dati Storici Multi-Pacchetto

#### **Comando 22 - Sport History**
- **Formato multi-pacchetto**: Riceve più pacchetti, termina con `END_TAG`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/sport_history_service.dart
class SportHistoryService {
  List<List<int>> _packages = [];
  List<HistoryOfSport> _historyOfSports = [];
  bool _isCL833 = false;
  
  void parseSportHistory(List<int> data) {
    if (data.length >= 7 && data[2] == 0x16) { // 0x16 = 22
      int timestamp = _getLongParse(data, 3, 4);
      
      if (_isCL833) {
        // CL833: parsing immediato
        _parseSportHistoryData(data.sublist(3));
        _historyOfSports = _historyOfSports.reversed.toList();
        onHistoryOfSportReceived(_historyOfSports);
        _historyOfSports.clear();
      } else if (timestamp != DeviceProtocolConstants.END_TAG) {
        // Accumula pacchetti
        _packages.add(data);
      } else {
        // Processo finale quando ricevi END_TAG
        for (int i = 0; i < _packages.length; i++) {
          List<int> payload = _packages[i].sublist(3);
          _parseSportHistoryData(payload);
        }
        onHistoryOfSportReceived(_historyOfSports);
        _historyOfSports.clear();
        _packages.clear();
      }
    }
  }
  
  void _parseSportHistoryData(List<int> value) {
    // Parsing: 10 bytes per record (timestamp[4] + steps[3] + calories[3])
    for (int i = 0; i < value.length ~/ 10; i++) {
      int offset = i * 10;
      int stamp = _getLongParse(value, offset, 4);
      int step = _getLongParse(value, offset + 4, 3);
      int calorie = _getLongParse(value, offset + 7, 3);
      
      DateTime dateTime = _restoreZoneUTC(stamp);
      _historyOfSports.add(HistoryOfSport(
        timestamp: dateTime,
        steps: step,
        calories: calorie,
      ));
    }
  }
  
  void onHistoryOfSportReceived(List<HistoryOfSport> sports) {
    print('Sport History received: ${sports.length} records');
    for (var sport in sports) {
      print('${sport.timestamp}: ${sport.steps} steps, ${sport.calories} cal');
    }
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

#### **Comando 5 - Sleep History**
- **Formato BLE**: `[length, 0x??, 0x05, subtype, timestamp[4], data_array...]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/sleep_history_service.dart
class SleepHistoryService {
  List<HistorySleep> _historyOfSleeps = [];
  
  void parseSleepHistory(List<int> data) {
    if (data.length >= 4 && data[2] == 0x05) {
      int subtype = data[3];
      
      if (subtype == 3) { // Solo subtype = 3 è valido
        int i = 4; // start position
        
        while (i < data.length) {
          int numDataPoints = data[i];
          if (numDataPoints >= 1) {
            int timestamp = _getLongParse(data, i + 1, 4);
            int adjustedTime = (timestamp * 1000) - 28800000; // time adjustment
            
            List<int> sleepData = [];
            for (int j = 0; j < numDataPoints; j++) {
              sleepData.add(data[i + 5 + j]);
            }
            
            _historyOfSleeps.add(HistorySleep(
              timestamp: DateTime.fromMillisecondsSinceEpoch(adjustedTime),
              sleepData: sleepData,
            ));
            
            i += 5 + numDataPoints;
            if (i == data.length - 2) break;
          } else {
            i++;
          }
        }
        
        onHistoryOfSleepReceived(_historyOfSleeps);
        _historyOfSleeps.clear();
      }
    }
  }
  
  void onHistoryOfSleepReceived(List<HistorySleep> sleeps) {
    print('Sleep History received: ${sleeps.length} records');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
}
```

#### **Comando 33 - Heart Rate Records**
- **Formato multi-pacchetto**: Accumula pacchetti fino a `END_TAG`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/hr_records_service.dart
class HeartRateRecordsService {
  List<List<int>> _packages = [];
  List<HistoryOfRecord> _historyOfRecords = [];
  
  void parseHRRecords(List<int> data) {
    if (data.length >= 7 && data[2] == 0x21) { // 0x21 = 33
      int timestamp = _getLongParse(data, 3, 4);
      
      if (timestamp != DeviceProtocolConstants.END_TAG) {
        _packages.add(data);
      } else {
        // Processo finale
        for (int i = 0; i < _packages.length; i++) {
          List<int> payload = _packages[i].sublist(3);
          
          // 4 bytes per timestamp
          for (int j = 0; j < payload.length ~/ 4; j++) {
            int recordTimestamp = _getLongParse(payload, j * 4, 4);
            DateTime utcTime = _restoreZoneUTC(recordTimestamp);
            
            _historyOfRecords.add(HistoryOfRecord(
              originalTimestamp: recordTimestamp,
              utcTimestamp: utcTime,
            ));
          }
        }
        
        onHistoryOfHRRecordReceived(_historyOfRecords);
        _historyOfRecords.clear();
        _packages.clear();
      }
    }
  }
  
  void onHistoryOfHRRecordReceived(List<HistoryOfRecord> records) {
    print('HR Records received: ${records.length} records');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

#### **Comando 34+35 - Heart Rate History Data**
- **Comando 34**: Riceve timestamp + inizio dati
- **Comando 35**: Riceve dati HR effettivi
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/hr_history_service.dart
class HeartRateHistoryService {
  List<List<int>> _packages = [];
  List<HistoryOfHeartRate> _historyOfHeartRates = [];
  int _stamp = 0;
  bool _isStamp = false;
  
  void parseHRHistory(List<int> data) {
    if (data.length >= 3) {
      int command = data[2];
      
      if (command == 0x22) { // 0x22 = 34 - Comando timestamp
        if (!_isStamp) {
          _stamp = _getLongParse(data, 3, 4);
          _isStamp = true;
        }
        _packages.add(data);
        
      } else if (command == 0x23) { // 0x23 = 35 - Comando dati
        // Processo dati HR
        for (int i = 0; i < _packages.length; i++) {
          List<int> payload = _packages[i].sublist(3);
          
          // 1 byte per valore HR, timestamp incrementale
          for (int j = 4; j < payload.length; j++) {
            int heartRate = payload[j];
            DateTime timestamp = _restoreZoneUTC(_stamp);
            
            _historyOfHeartRates.add(HistoryOfHeartRate(
              timestamp: timestamp,
              heartRate: heartRate,
            ));
            _stamp++;
          }
        }
        
        onHistoryOfHRDataReceived(_historyOfHeartRates);
        _historyOfHeartRates.clear();
        _packages.clear();
        _isStamp = false;
        _stamp = 0;
      }
    }
  }
  
  void onHistoryOfHRDataReceived(List<HistoryOfHeartRate> heartRates) {
    print('HR History Data received: ${heartRates.length} readings');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

#### **Comando 37+38 - Respiratory Rate History**
- **Comando 37**: Timestamp
- **Comando 38**: Dati RR (2 bytes per valore)
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/rr_history_service.dart
class RespiratoryRateHistoryService {
  List<List<int>> _packages = [];
  List<HistoryOfRespiratoryRate> _historyOfRespiratoryRates = [];
  int _stamp = 0;
  bool _isStamp = false;
  
  void parseRRHistory(List<int> data) {
    if (data.length >= 3) {
      int command = data[2];
      
      if (command == 0x25) { // 0x25 = 37 - Comando timestamp
        if (!_isStamp) {
          _stamp = _getLongParse(data, 3, 4);
          _isStamp = true;
        }
        _packages.add(data);
        
      } else if (command == 0x26) { // 0x26 = 38 - Comando dati
        // Processo dati RR
        for (int i = 0; i < _packages.length; i++) {
          List<int> payload = _packages[i].sublist(7); // subSlice(7, ...)
          
          // 2 bytes per valore RR
          for (int j = 0; j < payload.length ~/ 2; j++) {
            int respiratoryRate = _getIntParse(payload, j * 2, 2);
            DateTime timestamp = _restoreZoneUTC(_stamp);
            
            _historyOfRespiratoryRates.add(HistoryOfRespiratoryRate(
              timestamp: timestamp,
              respiratoryRate: respiratoryRate,
            ));
            _stamp++;
          }
        }
        
        onHistoryOfRRDataReceived(_historyOfRespiratoryRates);
        _historyOfRespiratoryRates.clear();
        _packages.clear();
        _isStamp = false;
        _stamp = 0;
      }
    }
  }
  
  void onHistoryOfRRDataReceived(List<HistoryOfRespiratoryRate> respiratoryRates) {
    print('RR History Data received: ${respiratoryRates.length} readings');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  int _getIntParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

#### **Comando 64+65 - Interval Steps**
- **Comando 64**: Accumula pacchetti
- **Comando 65**: Processo finale
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/interval_steps_service.dart
class IntervalStepsService {
  List<List<int>> _packages = [];
  List<IntervalStep> _intervalSteps = [];
  
  void parseIntervalSteps(List<int> data) {
    if (data.length >= 3) {
      int command = data[2];
      
      if (command == 0x40) { // 0x40 = 64 - Accumula
        _packages.add(data);
        
      } else if (command == 0x41) { // 0x41 = 65 - Processo finale
        // Formato: 8 bytes per record (timestamp[4] + steps[4])
        for (int i = 0; i < _packages.length; i++) {
          List<int> payload = _packages[i].sublist(3);
          
          for (int j = 0; j < payload.length ~/ 8; j++) {
            int offset = j * 8;
            int timestamp = _getLongParse(payload, offset, 4);
            int steps = _getIntParse(payload, offset + 4, 4);
            
            _intervalSteps.add(IntervalStep(
              timestamp: _restoreZoneUTC(timestamp),
              steps: steps,
            ));
          }
        }
        
        onIntervalStepReceived(_intervalSteps);
        _intervalSteps.clear();
        _packages.clear();
      }
    }
  }
  
  void onIntervalStepReceived(List<IntervalStep> steps) {
    print('Interval Steps received: ${steps.length} records');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  int _getIntParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

### 🔵 Sensori Avanzati

#### **Comando 73 - History Single Record**
- **Formato BLE**: `[length, 0x??, 0x49, timestamp[4], val1[3], val2[3], val3[3]]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/single_record_service.dart
class SingleRecordService {
  void parseSingleRecord(List<int> data) {
    if (data.length >= 16 && data[2] == 0x49) { // 0x49 = 73
      int timestamp = _getLongParse(data, 3, 4);
      int val1 = _getLongParse(data, 7, 3);
      int val2 = _getLongParse(data, 10, 3);
      int val3 = _getLongParse(data, 13, 3);
      
      DateTime utc = _restoreZoneUTC(timestamp);
      onHistorySingleRecordReceived(utc, val1, val2, val3);
    }
  }
  
  void onHistorySingleRecordReceived(DateTime utc, int val1, int val2, int val3) {
    print('Single Record - Time: $utc, Val1: $val1, Val2: $val2, Val3: $val3');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

#### **Comando 91 - Heart Rate Alarm**
- **Formato BLE**: `[length, 0x??, 0x5B, timestamp[4], alarm_flag]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/hr_alarm_service.dart
class HeartRateAlarmService {
  void parseHeartRateAlarm(List<int> data) {
    if (data.length >= 8 && data[2] == 0x5B) { // 0x5B = 91
      int timestamp = _getLongParse(data, 3, 4);
      bool isAlarm = data[7] == 1;
      
      DateTime utc = _restoreZoneUTC(timestamp);
      onHeartRateAlarmReceived(utc, isAlarm);
    }
  }
  
  void onHeartRateAlarmReceived(DateTime utc, bool isAlarm) {
    print('HR Alarm - Time: $utc, Alarm: $isAlarm');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  DateTime _restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
}
```

#### **Comando 96 - Sensor 6D Raw Data (Sequence)**
- **Formato BLE**: `[length, 0x??, 0x60, sequence, raw_data...]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/sensor_6d_service.dart
class Sensor6DService {
  void parseSensor6DRawData(List<int> data) {
    if (data.length >= 4 && data[2] == 0x60) { // 0x60 = 96
      int sequence = data[3];
      List<int> rawData = data.sublist(4);
      
      _parseSensorRawList(sequence, rawData);
    }
  }
  
  void _parseSensorRawList(int sequence, List<int> data) {
    // Parsing 6D sensor data: 12 bytes per campione (gyro xyz + accel xyz)
    for (int i = 0; i < data.length ~/ 12; i++) {
      int offset = i * 12;
      
      // Gyroscope data (primi 6 bytes)
      int gyroscopeX = _getIntValue(data, offset, 2);
      int gyroscopeY = _getIntValue(data, offset + 2, 2);
      int gyroscopeZ = _getIntValue(data, offset + 4, 2);
      
      // Accelerometer data (successivi 6 bytes)
      int accelerometerX = _getIntValue(data, offset + 6, 2);
      int accelerometerY = _getIntValue(data, offset + 8, 2);
      int accelerometerZ = _getIntValue(data, offset + 10, 2);
      
      onSensor6DRawDataReceived(
        255, // utc placeholder
        sequence, 
        gyroscopeX, gyroscopeY, gyroscopeZ,
        accelerometerX, accelerometerY, accelerometerZ
      );
    }
  }
  
  void onSensor6DRawDataReceived(int utc, int sequence, 
      int gyroX, int gyroY, int gyroZ,
      int accelX, int accelY, int accelZ) {
    print('6D Sensor[$sequence] - Gyro: ($gyroX,$gyroY,$gyroZ), Accel: ($accelX,$accelY,$accelZ)');
  }
  
  int _getIntValue(List<int> data, int offset, int length) {
    int value = 0;
    for (int i = 0; i < length; i++) {
      value = (value << 8) | (data[offset + i] & 0xFF);
    }
    return value;
  }
}
```

#### **Comando 97 - Sensor 6D Frequency**
- **Formato BLE**: `[length, 0x??, 0x61, frequency]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/sensor_frequency_service.dart
class SensorFrequencyService {
  void parseSensor6DFrequency(List<int> data) {
    if (data.length >= 4 && data[2] == 0x61) { // 0x61 = 97
      int frequency = data[3];
      onSensor6DFrequencyReceived(frequency);
    }
  }
  
  void onSensor6DFrequencyReceived(int frequency) {
    print('6D Sensor Frequency: ${frequency}Hz');
  }
}
```

#### **Comando 100 - Sensor Raw Data (Timestamped)**
- **Formato BLE**: `[length, 0x??, 0x64, timestamp[4], millis[2], sequence, raw_data...]`
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/timestamped_sensor_service.dart
class TimestampedSensorService {
  void parseTimestampedSensorData(List<int> data) {
    if (data.length >= 10 && data[2] == 0x64) { // 0x64 = 100
      int timestamp = _getLongParse(data, 3, 4);
      int millis = _getIntParse(data, 7, 2);
      int sequence = data[9];
      List<int> rawData = data.sublist(10);
      
      // Timestamp: (timestamp * 1000) + millis
      int fullTimestamp = (timestamp * 1000) + millis;
      DateTime utc = DateTime.fromMillisecondsSinceEpoch(fullTimestamp, isUtc: true);
      
      _parseSensorRawListWithTimestamp(utc, sequence, rawData);
    }
  }
  
  void _parseSensorRawListWithTimestamp(DateTime utc, int sequence, List<int> data) {
    // Parsing con offsets diversi per timestamped data
    for (int i = 0; i < data.length ~/ 12; i++) {
      int offset = i * 12;
      
      // Nota: offsets diversi rispetto al comando 96
      int gyroscopeX = _getIntValue(data, offset + 10, 2);
      int gyroscopeY = _getIntValue(data, offset + 12, 2);
      int gyroscopeZ = _getIntValue(data, offset + 14, 2);
      int accelerometerX = _getIntValue(data, offset + 16, 2);
      int accelerometerY = _getIntValue(data, offset + 18, 2);
      int accelerometerZ = _getIntValue(data, offset + 20, 2);
      
      onSensor6DRawDataReceived(
        utc.millisecondsSinceEpoch,
        sequence, 
        gyroscopeX, gyroscopeY, gyroscopeZ,
        accelerometerX, accelerometerY, accelerometerZ
      );
    }
  }
  
  void onSensor6DRawDataReceived(int utc, int sequence, 
      int gyroX, int gyroY, int gyroZ,
      int accelX, int accelY, int accelZ) {
    DateTime time = DateTime.fromMillisecondsSinceEpoch(utc);
    print('Timestamped 6D[$sequence] at $time - Gyro: ($gyroX,$gyroY,$gyroZ), Accel: ($accelX,$accelY,$accelZ)');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  int _getIntParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
  
  int _getIntValue(List<int> data, int offset, int length) {
    int value = 0;
    for (int i = 0; i < length; i++) {
      value = (value << 8) | (data[offset + i] & 0xFF);
    }
    return value;
  }
}
```

#### **Comando 117 - Multi-Function Command**
- **Sub-comando nel byte 4**:
  - `6`: Heart Rate Max
  - `11`: Sensor 3D Frequency
  - `12`: Sensor 3D Status
  - `15`: Health Data
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/multi_function_service.dart
class MultiFunctionService {
  void parseMultiFunction(List<int> data) {
    if (data.length >= 5 && data[2] == 0x75) { // 0x75 = 117
      int subCommand = data[4];
      
      switch (subCommand) {
        case 6: // Heart Rate Max
          if (data.length >= 6) {
            int maxHR = data[5];
            onHeartRateMaxReceived(maxHR);
          }
          break;
          
        case 11: // Sensor 3D Frequency
          if (data.length >= 6) {
            int freq = data[5];
            onSensor3DFrequencyReceived(freq);
          }
          break;
          
        case 12: // Sensor 3D Status
          if (data.length >= 6) {
            bool status = data[5] == 1;
            onSensor3DStatusReceived(status);
          }
          break;
          
        case 15: // Health Data
          if (data.length >= 22) {
            int p1 = data[5];
            int p2 = data[6];
            int p3 = data[7];
            int p4 = data[8];
            int p5 = data[9];
            
            double f1 = _getLongParse(data, 10, 4) / 1000.0;
            double f2 = _getLongParse(data, 14, 4) / 1000.0;
            double f3 = _getLongParse(data, 18, 4) / 1000.0;
            
            onHealthReceived(p1, p2, p3, p4, p5, f1, f2, f3);
          }
          break;
      }
    }
  }
  
  void onHeartRateMaxReceived(int maxHR) {
    print('Max Heart Rate: $maxHR BPM');
  }
  
  void onSensor3DFrequencyReceived(int frequency) {
    print('3D Sensor Frequency: ${frequency}Hz');
  }
  
  void onSensor3DStatusReceived(bool status) {
    print('3D Sensor Status: ${status ? "Active" : "Inactive"}');
  }
  
  void onHealthReceived(int p1, int p2, int p3, int p4, int p5, 
                       double f1, double f2, double f3) {
    print('Health Data: [$p1,$p2,$p3,$p4,$p5] Floats: [$f1,$f2,$f3]');
  }
  
  int _getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      val = (val << 8) | (bytes[i] & 0xFF);
    }
    return val;
  }
}
```

#### **Comando 119+120 - History 3D Data**
- **Formato BLE**: 6 bytes per campione (x[2] + y[2] + z[2])
- **Comando 119**: Accelerometro 3D
- **Comando 120**: Giroscopio 3D
- **Flutter Implementation**:
```dart
// lib/services/ble_protocol/history_3d_service.dart
class History3DService {
  void parseHistory3DData(List<int> data) {
    if (data.length >= 3) {
      int command = data[2];
      
      if (command == 0x77 || command == 0x78) { // 119 o 120
        bool isGyroscope = (command == 0x78); // 120 = gyroscope
        List<int> payload = data.sublist(3);
        
        // 6 bytes per campione (x[2] + y[2] + z[2])
        for (int i = 0; i < payload.length ~/ 6; i++) {
          int offset = i * 6;
          
          int x = _getSInt16(payload, offset);
          int y = _getSInt16(payload, offset + 2);
          int z = _getSInt16(payload, offset + 4);
          
          onHistoryOf3DDataReceived(x, y, z, isGyroscope);
        }
      }
    }
  }
  
  void onHistoryOf3DDataReceived(int x, int y, int z, bool isGyroscope) {
    String type = isGyroscope ? "Gyroscope" : "Accelerometer";
    print('3D History [$type]: X=$x, Y=$y, Z=$z');
  }
  
  int _getSInt16(List<int> data, int offset) {
    int unsigned = _unsignedBytesToInt(data[offset], data[offset + 1]);
    return _unsignedToSigned(unsigned, 16);
  }
  
  int _unsignedByteToInt(int b) {
    return b & 0xFF;
  }
  
  int _unsignedBytesToInt(int b0, int b1) {
    return _unsignedByteToInt(b0) + (_unsignedByteToInt(b1) << 8);
  }
  
  int _unsignedToSigned(int unsigned, int size) {
    int signBit = 1 << (size - 1);
    if ((signBit & unsigned) != 0) {
      return ((signBit - (unsigned & (signBit - 1))) * -1);
    }
    return unsigned;
  }
}
```

## Costanti e Tag Speciali

```dart
// lib/models/device_protocol_constants.dart
class DeviceProtocolConstants {
  static const int END_TAG = 0xFFFFFFFF;  // Indica fine dei pacchetti multi-parte
  
  // BLE Command Codes (hex values)
  static const int CMD_USER_INFO = 0x03;
  static const int CMD_HEART_RATE = 0x04;
  static const int CMD_SLEEP_HISTORY = 0x05;
  static const int CMD_ACCELEROMETER = 0x0C;
  static const int CMD_SPORT_HEALTH = 0x13;
  static const int CMD_SPORT_DATA = 0x15;
  static const int CMD_SPORT_HISTORY = 0x16;
  static const int CMD_HR_RECORDS = 0x21;
  static const int CMD_HR_HISTORY_START = 0x22;
  static const int CMD_HR_HISTORY_DATA = 0x23;
  static const int CMD_RR_RECORDS = 0x24;
  static const int CMD_RR_HISTORY_START = 0x25;
  static const int CMD_RR_HISTORY_DATA = 0x26;
  static const int CMD_SPO2 = 0x37;
  static const int CMD_TEMPERATURE = 0x38;
  static const int CMD_BLUETOOTH_STATUS = 0x3F;
  static const int CMD_INTERVAL_STEPS_START = 0x40;
  static const int CMD_INTERVAL_STEPS_END = 0x41;
  static const int CMD_SINGLE_TAP_START = 0x42;
  static const int CMD_SINGLE_TAP_END = 0x43;
  static const int CMD_HR_STATUS = 0x46;
  static const int CMD_SINGLE_RECORD = 0x49;
  static const int CMD_HR_ALARM = 0x5B;
  static const int CMD_SENSOR_6D_RAW = 0x60;
  static const int CMD_SENSOR_6D_FREQ = 0x61;
  static const int CMD_SENSOR_TIMESTAMPED = 0x64;
  static const int CMD_MULTI_FUNCTION = 0x75;
  static const int CMD_HISTORY_3D_ACCEL = 0x77;
  static const int CMD_HISTORY_3D_GYRO = 0x78;
}
```

## Utility Functions per Flutter

```dart
// lib/utils/ble_data_parser.dart
class BLEDataParser {
  
  /// Parse integer from byte array (Big Endian)
  static int getIntParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      if (i < bytes.length) {
        val = (val << 8) | (bytes[i] & 0xFF);
      }
    }
    return val;
  }

  /// Parse long from byte array (Big Endian)
  static int getLongParse(List<int> bytes, int pos, int len) {
    int val = 0;
    for (int i = pos; i < pos + len; i++) {
      if (i < bytes.length) {
        val = (val << 8) | (bytes[i] & 0xFF);
      }
    }
    return val;
  }

  /// Parse signed 16-bit integer (Little Endian)
  static int getSInt16(List<int> data, int offset) {
    if (offset + 1 >= data.length) return 0;
    int unsigned = unsignedBytesToInt(data[offset], data[offset + 1]);
    return unsignedToSigned(unsigned, 16);
  }
  
  /// Convert unsigned byte to int
  static int unsignedByteToInt(int b) {
    return b & 0xFF;
  }

  /// Convert two bytes to int (Little Endian)
  static int unsignedBytesToInt(int b0, int b1) {
    return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
  }

  /// Convert unsigned to signed
  static int unsignedToSigned(int unsigned, int size) {
    int signBit = 1 << (size - 1);
    if ((signBit & unsigned) != 0) {
      return ((signBit - (unsigned & (signBit - 1))) * -1);
    }
    return unsigned;
  }

  /// Restore UTC timestamp from device time
  static DateTime restoreZoneUTC(int timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(timestamp * 1000, isUtc: true);
  }
  
  /// Create sublist (equivalent to Java subSlice)
  static List<int> subSlice(int start, List<int> value) {
    if (start >= value.length) return [];
    return value.sublist(start);
  }
  
  /// Validate packet length and command
  static bool validatePacket(List<int> data, int expectedCommand, int minLength) {
    return data.length >= minLength && 
           data.length >= 3 && 
           data[2] == expectedCommand &&
           (data.length >= 2 ? data[0] == data.length - 1 : true); // length check
  }
}
```

## Data Structure Classes per Flutter

```dart
// lib/models/device_data_models.dart

class HistoryOfSport {
  final DateTime timestamp;
  final int steps;
  final int calories;
  
  HistoryOfSport({
    required this.timestamp,
    required this.steps,
    required this.calories,
  });
  
  @override
  String toString() => 'HistoryOfSport(timestamp: $timestamp, steps: $steps, calories: $calories)';
}

class HistorySleep {
  final DateTime timestamp;
  final List<int> sleepData;
  
  HistorySleep({
    required this.timestamp,
    required this.sleepData,
  });
  
  @override
  String toString() => 'HistorySleep(timestamp: $timestamp, dataPoints: ${sleepData.length})';
}

class HistoryOfHeartRate {
  final DateTime timestamp;
  final int heartRate;
  
  HistoryOfHeartRate({
    required this.timestamp,
    required this.heartRate,
  });
  
  @override
  String toString() => 'HistoryOfHeartRate(timestamp: $timestamp, heartRate: $heartRate)';
}

class HistoryOfRespiratoryRate {
  final DateTime timestamp;
  final int respiratoryRate;
  
  HistoryOfRespiratoryRate({
    required this.timestamp,
    required this.respiratoryRate,
  });
  
  @override
  String toString() => 'HistoryOfRespiratoryRate(timestamp: $timestamp, respiratoryRate: $respiratoryRate)';
}

class IntervalStep {
  final DateTime timestamp;
  final int steps;
  
  IntervalStep({
    required this.timestamp,
    required this.steps,
  });
  
  @override
  String toString() => 'IntervalStep(timestamp: $timestamp, steps: $steps)';
}

class HistoryOfRecord {
  final int originalTimestamp;
  final DateTime utcTimestamp;
  
  HistoryOfRecord({
    required this.originalTimestamp,
    required this.utcTimestamp,
  });
  
  @override
  String toString() => 'HistoryOfRecord(originalTimestamp: $originalTimestamp, utcTimestamp: $utcTimestamp)';
}

class HistoryOf3D {
  final int x;
  final int y;
  final int z;
  
  HistoryOf3D({
    required this.x,
    required this.y,
    required this.z,
  });
  
  @override
  String toString() => 'HistoryOf3D(x: $x, y: $y, z: $z)';
}

// Sensor Data Classes
class AccelerometerData {
  final int x;
  final int y;
  final int z;
  final DateTime timestamp;
  
  AccelerometerData({
    required this.x,
    required this.y,
    required this.z,
    required this.timestamp,
  });
}

class GyroscopeData {
  final int x;
  final int y;
  final int z;
  final DateTime timestamp;
  
  GyroscopeData({
    required this.x,
    required this.y,
    required this.z,
    required this.timestamp,
  });
}

class TemperatureData {
  final double environment;
  final double wrist;
  final double body;
  final DateTime timestamp;
  
  TemperatureData({
    required this.environment,
    required this.wrist,
    required this.body,
    required this.timestamp,
  });
}

class SpO2Data {
  final int spo2;
  final String param1;
  final int gesture;
  final int piValue;
  final bool onWrist;
  final DateTime timestamp;
  
  SpO2Data({
    required this.spo2,
    required this.param1,
    required this.gesture,
    required this.piValue,
    required this.onWrist,
    required this.timestamp,
  });
}
```

## Esempio di Dispatcher Principale

```dart
// lib/services/ble_protocol/device_data_dispatcher.dart
class DeviceDataDispatcher {
  final HeartRateService _heartRateService = HeartRateService();
  final AccelerometerService _accelerometerService = AccelerometerService();
  final SportDataService _sportDataService = SportDataService();
  final SpO2Service _spo2Service = SpO2Service();
  final TemperatureService _temperatureService = TemperatureService();
  final SportHistoryService _sportHistoryService = SportHistoryService();
  // ... altri servizi
  
  /// Main entry point per tutti i dati BLE dal device
  void processDeviceData(List<int> data) {
    if (data.length < 3) {
      print('Invalid data length: ${data.length}');
      return;
    }
    
    int command = data[2];
    
    try {
      switch (command) {
        case DeviceProtocolConstants.CMD_HEART_RATE:
          _heartRateService.parseHeartRateData(data);
          break;
          
        case DeviceProtocolConstants.CMD_ACCELEROMETER:
          _accelerometerService.parseAccelerometerData(data);
          break;
          
        case DeviceProtocolConstants.CMD_SPORT_DATA:
          _sportDataService.parseSportData(data);
          break;
          
        case DeviceProtocolConstants.CMD_SPO2:
          _spo2Service.parseSpO2Data(data);
          break;
          
        case DeviceProtocolConstants.CMD_TEMPERATURE:
          _temperatureService.parseTemperatureData(data);
          break;
          
        case DeviceProtocolConstants.CMD_SPORT_HISTORY:
          _sportHistoryService.parseSportHistory(data);
          break;
          
        // ... aggiungi tutti gli altri comandi
          
        default:
          print('Unknown command: 0x${command.toRadixString(16).toUpperCase()}');
      }
    } catch (e) {
      print('Error processing command 0x${command.toRadixString(16)}: $e');
    }
  }
  
  /// Clear data buffers per tipo specifico
  void clearType(int type) {
    switch (type) {
      case DeviceProtocolConstants.TYPE_SPORT:
        _sportHistoryService.clearData();
        break;
      case DeviceProtocolConstants.TYPE_HEART:
        _heartRateService.clearData();
        break;
      // ... altri tipi
    }
  }
}
```

## Note Implementative per Flutter

1. **Multi-packet Protocol**: Usa `List<List<int>>` per accumulare pacchetti fino a `END_TAG`.

2. **Timestamp Handling**: Tutti i timestamp vengono convertiti con `DateTime.fromMillisecondsSinceEpoch()`.

3. **Device Types**: Distingui tra CL833 e altri modelli con flag booleano `isCL833`.

4. **Error Handling**: Tutti i parsing sono protetti da try-catch per gestire errori di formato.

5. **Data Streams**: Usa `Stream<T>` o `StreamController<T>` per esporre i dati in tempo reale all'UI.

6. **BLE Integration**: Integra con `flutter_blue_plus` per gestire le notifiche BLE:

```dart
// Esempio di integrazione BLE
characteristic.value.listen((value) {
  dispatcher.processDeviceData(value);
});
```

Questo protocollo rappresenta l'implementazione completa dell'app originale per interagire con il device CL837 in Flutter/Dart.
