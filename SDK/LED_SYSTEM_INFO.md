# CL837 Device LED Status System

## 📍 LED Status Overview

The CL837 device has multiple LED indicators that provide visual feedback about device status, operations, and connectivity.

## 🔋 Device Status LED (Top LED)

### **When NOT Connected to App:**

#### **🔄 Boot/Pairing Sequence (Cycling Pattern)**
- **🟢 Green → 🔴 Red → ⚪ White** (repeating cycle)
- **No Vibration** during this phase
- **Duration**: Continuous until connected

**What this means:**
1. **🟢 Green**: System initialization complete, hardware ready
2. **🔴 Red**: Hardware self-test (sensors, memory, BLE radio)
3. **⚪ White**: Discoverable/pairing mode, waiting for connection

### **When Connected to App:**

#### **🟢 Stable Green LED**
- **Meaning**: Heart rate monitoring mode active
- **Behavior**: Solid green light (no cycling)
- **Vibration**: Only during data transmission (3 quick pulses)

#### **🔴 Red LED + Vibration**
- **Meaning**: SpO2 measurement mode active
- **Behavior**: Solid red light with periodic vibration
- **Note**: This is the SpO2 sensor LED (660nm), not status LED

#### **⚫ LED Off**
- **Meaning**: Heart rate service paused/disabled
- **Achieved by**: Calling `_heartRateService.pause()` in app
- **Use case**: Power saving or testing mode

## 🩸 SpO2 Sensor LED (Bottom/Skin-Contact)

### **SpO2 Measurement LED**
- **Color**: Red (660nm) + Infrared (940nm, not visible)
- **Location**: Bottom of device, touches skin
- **Behavior**: Flashes during SpO2 measurement
- **Safety**: Auto-off after 5 minutes max

**Important**: This is a separate LED from the top status LED!

## 📱 App Connection States

| Device State | Top LED | Vibration | Description |
|-------------|---------|-----------|-------------|
| **Boot/Unpaired** | 🟢→🔴→⚪ (cycling) | None | Ready for pairing |
| **Connected (HR Mode)** | 🟢 (solid) | 3 pulses | Normal operation |
| **Connected (SpO2 Mode)** | 🔴 (solid) | Periodic | SpO2 measurement |
| **Connected (Paused)** | ⚫ (off) | None | Heart rate paused |

## 🔧 Implementation Notes

### **LED Control Commands (BLE Protocol v0.6)**

```dart
// Enable SpO2 mode (red LED on)
await sendCommand([0x37, 0x01]);

// Disable SpO2 mode (red LED off)
await sendCommand([0x37, 0x00]);

// Pause heart rate service (green LED off)
_heartRateService.pause();
```

### **Expected Behavior After App Connection:**
1. **During Scanning**: Device continues 🟢→🔴→⚪ cycle
2. **Upon Connection**: LED stabilizes to 🟢 (heart rate mode)
3. **Manual Mode**: LED remains 🟢, data requests cause 3-pulse vibration
4. **SpO2 Measurement**: LED changes to 🔴, periodic vibration

## ⚠️ Troubleshooting

### **LED Cycling Won't Stop**
- **Cause**: Device not properly connected to app
- **Solution**: Check BLE connection, restart scan/connect process

### **Red LED + Vibration After Connection**
- **Cause**: Device stuck in SpO2 mode from previous session
- **Solution**: Send force exit SpO2 command: `forceExitSpO2Mode()`

### **No LED Activity**
- **Cause**: Device battery low or hardware fault
- **Solution**: Charge device, check battery level via BLE

## 📚 Protocol Reference

Based on **Chileaf BLE Protocol v0.6** specification:
- Command 0x37: SpO2 mode control
- Heart Rate Service (0x180D): Controls green status LED
- Custom Service (0x8f00): General device communication

**Note**: LED behavior documented through empirical testing and protocol analysis. Official Chileaf documentation does not explicitly detail LED status meanings.