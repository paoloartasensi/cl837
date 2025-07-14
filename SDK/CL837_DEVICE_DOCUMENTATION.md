# CL837 BLE Device - Complete Development Documentation

## 📋 Table of Contents
1. [Device Overview](#device-overview)
2. [BLE Connection & Services](#ble-connection--services)
3. [Data Parsing & Protocol](#data-parsing--protocol)
4. [Battery Monitoring](#battery-monitoring)
5. [Heart Rate & HRV](#heart-rate--hrv)
6. [SpO2 Measurement](#spo2-measurement)
7. [Temperature Monitoring](#temperature-monitoring)
8. [Sports Data](#sports-data)
9. [LED Control](#led-control)
10. [Command Reference](#command-reference)
11. [Implementation Tips](#implementation-tips)
12. [Troubleshooting](#troubleshooting)

## 🔌 Device Overview

The **CL837** is a Bluetooth Low Energy (BLE) health monitoring device based on the Chileaf SDK. It provides real-time monitoring of:
- Heart rate with RR intervals
- Blood oxygen saturation (SpO2)
- Body temperature (ambient, wrist, body)
- Sports data (steps, distance, calories)
- Battery level
- 3D accelerometer data

## 🔗 BLE Connection & Services

### Primary Services

#### 1. Heart Rate Service (Standard BLE)
```
Service UUID: 0x180D (Heart Rate Service)
Characteristic UUID: 0x2A37 (Heart Rate Measurement)
Properties: NOTIFY
```

#### 2. Battery Service (Standard BLE)
```
Service UUID: 0x180F (Battery Service)
Characteristic UUID: 0x2A19 (Battery Level)
Properties: READ, NOTIFY
```

#### 3. Chileaf Custom Service
```
Service UUID: aae28f00-71b5-42a1-8c3c-f9cf6ac969d0
TX Characteristic: aae28f01-71b5-42a1-8c3c-f9cf6ac969d0 (NOTIFY - Read from device)
RX Characteristic: aae28f02-71b5-42a1-8c3c-f9cf6ac969d0 (WRITE - Write to device)
```

### Connection Process
```dart
// 1. Scan for device
final scanResults = await FlutterBluePlus.scan();

// 2. Connect to device
await device.connect();

// 3. Discover services
final services = await device.discoverServices();

// 4. Find and configure characteristics
final heartRateService = services.firstWhere((s) => s.uuid.toString() == "0000180d-0000-1000-8000-00805f9b34fb");
final batteryService = services.firstWhere((s) => s.uuid.toString() == "0000180f-0000-1000-8000-00805f9b34fb");
final customService = services.firstWhere((s) => s.uuid.toString().toLowerCase() == "aae28f00-71b5-42a1-8c3c-f9cf6ac969d0");

// 5. Enable notifications
await characteristic.setNotifyValue(true);
```

## 📊 Data Parsing & Protocol

### Chileaf Protocol Frame Structure
```
[0xFF] [Length] [Command] [Data...] [Checksum]
```

- **Header**: Always `0xFF`
- **Length**: Data length + 4 (includes header, length, checksum, padding)
- **Command**: Command code (see Command Reference)
- **Data**: Payload data
- **Checksum**: XOR(0x3A, -sum(Header to Data))

### Checksum Calculation
```dart
int calculateChecksum(List<int> frameData) {
  int sum = 0;
  for (int byte in frameData) {
    sum += byte;
  }
  int temp = sum & 0xFF;
  temp = (0 - temp) & 0xFF;
  temp ^= 0x3A;
  return temp & 0xFF;
}
```

## 🔋 Battery Monitoring

### Standard BLE Battery Service
```dart
// Battery level is a single byte (0-100%)
void processBatteryData(List<int> data) {
  if (data.isNotEmpty) {
    final batteryLevel = data[0]; // Direct percentage
    print('Battery: ${batteryLevel}%');
  }
}
```

### Key Insights
- Battery data is sent as a single byte representing percentage
- No conversion needed - direct value
- Updates automatically when connected
- Reliable and follows BLE standard

## ❤️ Heart Rate & HRV

### Heart Rate Data Structure
```dart
class HeartRateData {
  final int heartRate;           // BPM
  final List<double>? rrIntervals; // RR intervals in milliseconds
  final int? energyExpended;     // Optional energy data
  final bool sensorContact;      // Sensor contact status
}
```

### Parsing Heart Rate Data
```dart
void processHeartRateData(List<int> data) {
  if (data.isEmpty) return;
  
  final flags = data[0];
  int heartRate;
  int offset = 1;
  
  // Check if heart rate is 16-bit
  if (flags & 0x01 != 0) {
    heartRate = (data[offset + 1] << 8) | data[offset];
    offset += 2;
  } else {
    heartRate = data[offset];
    offset += 1;
  }
  
  // Check for RR intervals
  List<double> rrIntervals = [];
  if (flags & 0x10 != 0) {
    while (offset < data.length - 1) {
      int rrValue = (data[offset + 1] << 8) | data[offset];
      rrIntervals.add(rrValue / 1024.0 * 1000); // Convert to milliseconds
      offset += 2;
    }
  }
  
  // Sensor contact status
  bool sensorContact = (flags & 0x06) == 0x06;
}
```

### HRV Calculation
```dart
class HRVData {
  final List<double> rrIntervals;
  final double rmssd;        // Root Mean Square of Successive Differences
  final double sdnn;         // Standard Deviation of NN intervals
  final double pnn50;        // Percentage of NN50 intervals
  
  HRVData({required this.rrIntervals}) 
    : rmssd = _calculateRMSSD(rrIntervals),
      sdnn = _calculateSDNN(rrIntervals),
      pnn50 = _calculatePNN50(rrIntervals);
      
  static double _calculateRMSSD(List<double> intervals) {
    if (intervals.length < 2) return 0.0;
    
    double sumSquaredDiffs = 0.0;
    for (int i = 1; i < intervals.length; i++) {
      double diff = intervals[i] - intervals[i - 1];
      sumSquaredDiffs += diff * diff;
    }
    
    return sqrt(sumSquaredDiffs / (intervals.length - 1));
  }
}
```

## 🫁 SpO2 Measurement

### **CRITICAL DISCOVERY: SpO2 at Index 1**
**The SpO2 value is directly encoded in the second byte (index 1) of many BLE packets as a percentage.**

```dart
void parseSpO2FromPacket(List<int> data) {
  if (data.length >= 2) {
    final spo2Value = data[1]; // Direct SpO2 percentage
    
    if (spo2Value >= 80 && spo2Value <= 100) {
      print('SpO2: ${spo2Value}%');
      // 0x64 = 100%, 0x61 = 97%, 0x5A = 90%
    }
  }
}
```

### SpO2 Commands & LED Control
```dart
// Command 0x37 - SpO2 Mode Control
Future<void> enableSpO2Mode() async {
  await sendCommand([0x37, 0x01]); // Enable SpO2 + LED ON (Red)
}

Future<void> exitSpO2Mode() async {
  await sendCommand([0x37, 0x00]); // Disable SpO2 + LED OFF
}

Future<void> inquireSpO2Status() async {
  await sendCommand([0x37, 0x02]); // Request SpO2 measurement
}
```

### SpO2 Measurement Procedure
```dart
Future<void> measureSpO2() async {
  try {
    // 1. Ensure SpO2 mode is off
    await exitSpO2Mode();
    await Future.delayed(Duration(milliseconds: 500));
    
    // 2. Enable SpO2 mode (LED turns RED)
    await enableSpO2Mode();
    print('LED should be RED - measuring...');
    
    // 3. Wait for stabilization
    await Future.delayed(Duration(seconds: 4));
    
    // 4. Request measurements (multiple times for accuracy)
    for (int i = 0; i < 3; i++) {
      await inquireSpO2Status();
      await Future.delayed(Duration(seconds: 2));
    }
    
    // 5. IMPORTANT: Turn off LED
    await exitSpO2Mode();
    print('LED should be OFF');
    
  } catch (e) {
    // Always ensure LED is turned off
    await exitSpO2Mode();
    rethrow;
  }
}
```

### SpO2 Data Structure (SDK Format)
```dart
class SpO2Data {
  final int? spo2Value;           // SpO2 percentage (null = no measurement)
  final bool correctWristPosture; // Wrist position correct
  final int signalQuality;        // Signal quality (0-100)
  final bool isWearing;          // Device worn correctly
  
  bool get isValidMeasurement => 
    spo2Value != null && 
    isWearing && 
    correctWristPosture && 
    signalQuality >= 8;
}
```

## 🌡️ Temperature Monitoring

### Command & Data Structure
```dart
// Command 0x38 - Temperature Request
Future<void> requestTemperature() async {
  await sendCommand([0x38]);
}

// Temperature Response Parsing
void processTemperatureData(List<int> data) {
  if (data.length < 9) return;
  
  // MSB first, divide by 10 for actual °C
  final ambientTemp = ((data[3] << 8) | data[4]) / 10.0;
  final wristTemp = ((data[5] << 8) | data[6]) / 10.0;
  final bodyTemp = ((data[7] << 8) | data[8]) / 10.0;
  
  print('Ambient: ${ambientTemp}°C, Wrist: ${wristTemp}°C, Body: ${bodyTemp}°C');
}
```

### Temperature Data Structure
```dart
class TemperatureData {
  final double ambientTempC;  // Ambient temperature
  final double wristTempC;    // Wrist temperature  
  final double bodyTempC;     // Body temperature
  
  bool get isValid => 
    ambientTempC >= 10 && ambientTempC <= 50 &&
    wristTempC >= 20 && wristTempC <= 45 &&
    bodyTempC >= 30 && bodyTempC <= 45;
}
```

## 🏃 Sports Data

### Command & Data Structure
```dart
// Command 0x15 - Sports Data Request
Future<void> requestSportsData() async {
  await sendCommand([0x15]);
}

// Sports Data Parsing
void processSportsData(List<int> data) {
  if (data.length < 12) return;
  
  final steps = (data[3] << 16) | (data[4] << 8) | data[5];
  final distanceCm = ((data[6] << 16) | (data[7] << 8) | data[8]).toDouble();
  final caloriesRaw = (data[9] << 16) | (data[10] << 8) | data[11];
  final caloriesKcal = caloriesRaw * 0.1; // Convert from 0.1 kcal units
  
  print('Steps: $steps, Distance: ${distanceCm}cm, Calories: ${caloriesKcal}kcal');
}
```

### Sports Data Structure
```dart
class SportsData {
  final int steps;           // Step count
  final double distanceCm;   // Distance in centimeters
  final double caloriesKcal; // Calories in kcal
  
  double get distanceKm => distanceCm / 100000.0;
  double get distanceM => distanceCm / 100.0;
}
```

## 💡 LED Control

### Device LED System Overview

The CL837 has **two separate LED systems**:
1. **Top Status LED**: Device state indicator
2. **Bottom SpO2 LED**: Skin-contact sensor for SpO2 measurement

### 🔋 Top Status LED (Device State)

#### **When NOT Connected:**
- **🟢→🔴→⚪ Cycling Pattern**: Boot/pairing sequence
  - 🟢 Green: System initialization complete
  - 🔴 Red: Hardware self-test
  - ⚪ White: Discoverable/waiting for connection
- **No Vibration** during boot sequence

#### **When Connected:**
- **🟢 Solid Green**: Heart rate monitoring active (normal operation)
- **🔴 Solid Red**: SpO2 measurement mode active
- **⚫ LED Off**: Heart rate service paused/disabled

### 🩸 Bottom SpO2 LED (Sensor)

#### **SpO2 Measurement Mode:**
- **Red LED (660nm)**: Visible red light for SpO2 measurement
- **Infrared (940nm)**: Invisible infrared light (not visible to eye)
- **Safety**: Auto-off after 5 minutes maximum
- **Vibration**: Periodic pulses during measurement

### LED Control Commands

```dart
// SpO2 Mode Control (affects both status LED and sensor LED)
await sendCommand([0x37, 0x01]); // Enter SpO2 mode (red LEDs on)
await sendCommand([0x37, 0x00]); // Exit SpO2 mode (red LEDs off)

// Heart Rate Service Control (affects top status LED)
_heartRateService.pause();  // Turn off green status LED
_heartRateService.resume(); // Turn on green status LED

// Force Exit SpO2 Mode (emergency LED control)
Future<void> forceExitSpO2Mode() async {
  for (int i = 0; i < 3; i++) {
    await sendCommand([0x37, 0x00]);
    await Future.delayed(Duration(milliseconds: 500));
  }
}
```

### Expected LED Behavior Sequence

| Device State | Top Status LED | Vibration | Description |
|-------------|---------------|-----------|-------------|
| **Boot/Unpaired** | 🟢→🔴→⚪ (cycling) | None | Ready for pairing |
| **App Scanning** | 🟢→🔴→⚪ (cycling) | None | Still discoverable |
| **Connected (Normal)** | 🟢 (solid) | 3 pulses on data | Heart rate mode |
| **SpO2 Measurement** | 🔴 (solid) | Periodic pulses | SpO2 active |
| **Service Paused** | ⚫ (off) | None | Power saving mode |

### Troubleshooting LED Issues

```dart
// If LED stuck cycling after connection
await device.connect();
await Future.delayed(Duration(seconds: 2)); // Wait for services
await forceExitSpO2Mode(); // Ensure clean state

// If red LED won't turn off
await sendCommand([0x37, 0x00]); // Exit SpO2 mode
_heartRateService.resume(); // Resume normal green LED

// Check connection state
if (device.connectionState == BluetoothConnectionState.connected) {
  print('Device connected - LED should be stable');
} else {
  print('Device not connected - LED will cycle');
}
```

**📖 Reference**: See `LED_SYSTEM_INFO.md` for detailed LED behavior documentation.

## 📋 Command Reference

### Chileaf Custom Commands
| Command | Description | Parameters | Response |
|---------|-------------|------------|----------|
| `0x15` | Sports data request | None | Steps, distance, calories |
| `0x37` | SpO2 mode control | `0x00`=Exit, `0x01`=Enter, `0x02`=Inquire | SpO2 data |
| `0x38` | Temperature request | None | Ambient, wrist, body temp |
| `0x0C` | Accelerometer data | None | 3D motion data (high freq) |
| `0x75` | Extended health data | None | Additional health metrics |

### Command Sending
```dart
Future<void> sendCommand(List<int> command) async {
  final frame = buildProtocolFrame(command);
  await rxCharacteristic.write(frame, withoutResponse: true);
}

List<int> buildProtocolFrame(List<int> data) {
  final length = data.length + 4;
  final frame = [0xFF, length, ...data];
  final checksum = calculateChecksum(frame);
  frame.add(checksum);
  return frame;
}
```

## 🔧 Implementation Tips

### 1. Connection Management
```dart
// Always check connection state
final connectionState = await device.connectionState.first;
if (connectionState != BluetoothConnectionState.connected) {
  throw Exception('Device not connected');
}

// Implement auto-reconnection
device.connectionState.listen((state) {
  if (state == BluetoothConnectionState.disconnected) {
    // Attempt reconnection
    reconnectDevice();
  }
});
```

### 2. Data Stream Management
```dart
// Use StreamControllers for real-time data
final heartRateController = StreamController<HeartRateData>.broadcast();
final spo2Controller = StreamController<SpO2Data>.broadcast();
final temperatureController = StreamController<TemperatureData>.broadcast();

// Proper disposal
@override
void dispose() {
  heartRateController.close();
  spo2Controller.close();
  temperatureController.close();
  super.dispose();
}
```

### 3. Error Handling
```dart
try {
  await sendCommand([0x37, 0x01]);
} catch (e) {
  print('Command failed: $e');
  // Always cleanup on error
  await sendCommand([0x37, 0x00]); // Turn off LED
}
```

### 4. Periodic Data Requests
```dart
Timer.periodic(Duration(seconds: 5), (timer) async {
  try {
    await requestTemperatureData();
    await Future.delayed(Duration(milliseconds: 300));
    await requestSportsData();
  } catch (e) {
    print('Periodic request error: $e');
  }
});
```

## 🐛 Troubleshooting

### Common Issues

#### 1. SpO2 Always Shows 1%
**Solution**: Use the index 1 discovery - SpO2 is in the second byte of packets.

#### 2. LED Stuck ON
**Solution**: Send multiple exit commands:
```dart
for (int i = 0; i < 3; i++) {
  await sendCommand([0x37, 0x00]);
  await Future.delayed(Duration(milliseconds: 500));
}
```

#### 3. No Data Received
**Solution**: Check notifications are enabled:
```dart
if (!characteristic.isNotifying) {
  await characteristic.setNotifyValue(true);
}
```

#### 4. Connection Drops
**Solution**: Implement connection monitoring:
```dart
final subscription = device.connectionState.listen((state) {
  if (state == BluetoothConnectionState.disconnected) {
    handleDisconnection();
  }
});
```

#### 5. Incorrect Checksum
**Solution**: Verify checksum calculation:
```dart
// Correct checksum: XOR(0x3A, -sum(frame))
int checksum = ((0 - sum) & 0xFF) ^ 0x3A;
```

### Debug Tips

#### 1. Log All BLE Data
```dart
void logBLEData(List<int> data) {
  print('BLE Data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
}
```

#### 2. Analyze Packet Structure
```dart
void analyzePacket(List<int> data) {
  if (data.length >= 3 && data[0] == 0xFF) {
    print('Protocol packet: Command=0x${data[2].toRadixString(16)}, Length=${data.length}');
  }
}
```

#### 3. Monitor SpO2 Detection
```dart
void monitorSpO2Detection(List<int> data) {
  if (data.length >= 2) {
    final byte1 = data[1];
    if (byte1 >= 80 && byte1 <= 100) {
      print('🎯 SpO2 detected at index 1: ${byte1}%');
    }
  }
}
```

## 📚 Additional Resources

### Chileaf SDK Documentation
- Protocol version: v0.6
- Official commands: 0x15 (Sports), 0x37 (SpO2), 0x38 (Temperature)
- Discovered commands: 0x0C (Accelerometer), 0x75 (Extended Health)

### BLE Standards
- Heart Rate Service: [Bluetooth SIG Specification](https://www.bluetooth.com/specifications/gatt/services/)
- Battery Service: [Bluetooth SIG Specification](https://www.bluetooth.com/specifications/gatt/services/)

### Flutter BLE Libraries
- `flutter_blue_plus`: Primary BLE library
- Supports scanning, connecting, reading/writing characteristics
- Handles notifications and connection state changes

---

**Note**: This documentation is based on reverse engineering and analysis of the CL837 device. Always refer to official Chileaf documentation when available, and test thoroughly in your specific use case.

**Last Updated**: July 2025
**Device Firmware**: CL837 with Chileaf SDK v0.6
**Flutter Version**: 3.x with flutter_blue_plus
