# CL837 Device LED Status System

## 📍 LED Status Overview

The CL837 device has multiple LED indicators that provide visual feedback about device status, operations, and connectivity.

## 🔋 Device Status LED (Top LED)

### **When NOT Connected to App:**

#### **🔄 Boot/Pairing Sequence (Cycling Pattern)**
- **🟢 Green → 🔴 Red → ⚪ White** (repeating cycle)
- **No Vibration** during this phase
- **Duration**: Continuous until connected
- **Cycle Time**: ~2-3 seconds per color

**What this means:**
1. **🟢 Green**: System initialization complete, hardware ready
2. **🔴 Red**: Hardware self-test (sensors, memory, BLE radio)
3. **⚪ White**: Discoverable/pairing mode, waiting for connection

### **When Connected to App:**

#### **Manual Mode (Recommended):**
- **🟢 Stable Green LED**: Normal operation, heart rate monitoring active
- **Vibration**: Only during manual data requests (3 quick pulses)
- **LED Behavior**: Solid, stable, no cycling

#### **Automatic Mode (Legacy):**
- **🔴 Red LED + Vibration**: Device overloaded by frequent requests
- **LED Behavior**: Often unstable, cycling, stress indicators
- **Not Recommended**: Causes device stress and battery drain

#### **SpO2 Measurement Mode:**
- **🔴 Red LED + Vibration**: SpO2 measurement active
- **Behavior**: Solid red light with periodic vibration
- **Note**: This is the SpO2 sensor LED (660nm), not status LED

#### **⚫ LED Off State:**
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

## 📱 App Connection States vs LED Behavior

| Device State | Connection | Mode | Top LED | Vibration | Description |
|-------------|-----------|------|---------|-----------|-------------|
| **Boot/Unpaired** | ❌ | N/A | 🟢→🔴→⚪ (cycling) | None | Ready for pairing |
| **App Scanning** | ❌ | N/A | 🟢→🔴→⚪ (cycling) | None | Still discoverable |
| **Connected** | ✅ | Manual | 🟢 (solid) | 3 pulses on request | **Recommended** |
| **Connected** | ✅ | Auto | 🔴 (stressed) | Frequent | **Not Recommended** |
| **SpO2 Active** | ✅ | Either | 🔴 (solid) | Periodic | Measurement mode |
| **Service Paused** | ✅ | Either | ⚫ (off) | None | Power saving |

## 🎮 Mode Comparison

### **🎯 Manual Mode (RECOMMENDED)**
- **LED**: Stable 🟢 green after connection
- **Before Connection**: Natural 🟢→🔴→⚪ cycling (this is NORMAL!)
- **Requests**: Only when you press buttons
- **Battery Life**: Excellent
- **Device Stress**: None
- **Accuracy**: High (no interference)

### **🔄 Automatic Mode (LEGACY)**
- **LED**: Often 🔴 red due to overload
- **Before Connection**: Rapid cycling or stuck patterns
- **Requests**: Every 10 seconds automatically
- **Battery Life**: Poor
- **Device Stress**: High
- **Accuracy**: Lower (interference from frequent requests)

## 🔧 Implementation Notes

### **LED Control Commands (BLE Protocol v0.6)**

```dart
// Enable SpO2 mode (red LED on)
await sendCommand([0x37, 0x01]);

// Disable SpO2 mode (red LED off)
await sendCommand([0x37, 0x00]);

// Pause heart rate service (green LED off)
_heartRateService.pause();

// Switch between modes
_extendedService.pausePeriodicRequests();  // Manual mode
_extendedService.resumePeriodicRequests(); // Auto mode
```

### **Expected Behavior After App Connection:**
1. **During Scanning**: Device continues 🟢→🔴→⚪ cycle (NORMAL!)
2. **Upon Connection (Manual Mode)**: LED stabilizes to 🟢 (heart rate mode)
3. **Manual Requests**: LED stays 🟢, device gives 3-pulse vibration feedback
4. **SpO2 Measurement**: LED changes to 🔴, periodic vibration

## ⚠️ Troubleshooting

### **LED Cycling Won't Stop**
- **Cause**: Device not properly connected to app
- **Solution**: Check BLE connection, restart scan/connect process

### **Red LED + Vibration After Connection**
- **Cause**: App in automatic mode (device overloaded)
- **Solution**: Switch to manual mode in app control panel

### **LED Cycling Before Connection is NORMAL**
- **This is NOT an error**: 🟢→🔴→⚪ cycling means device is working correctly
- **When it happens**: Before pairing, during scanning, when app is not connected
- **What to do**: Nothing! This is the natural boot/pairing sequence

### **No LED Activity**
- **Cause**: Device battery low or hardware fault
- **Solution**: Charge device, check battery level via BLE

## 📊 LED Status Real-Time Monitoring

The app now includes a **LED Status Widget** that shows:
- Current LED color and state
- Connection status
- Mode (Manual/Auto)
- Real-time sync with actual device behavior

## 📚 Protocol Reference

Based on **Chileaf BLE Protocol v0.6** specification:
- Command 0x37: SpO2 mode control
- Heart Rate Service (0x180D): Controls green status LED  
- Custom Service (0x8f00): General device communication

**Note**: LED behavior documented through empirical testing, real-world observation, and protocol analysis. The cycling pattern 🟢→🔴→⚪ before connection is the device's natural and correct behavior.