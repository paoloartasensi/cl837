# Device Control Commands - Implementation Guide

## Overview
This guide documents the implementation of device control commands for the CL837/CL831 fitness tracker, specifically **Shutdown** and **Factory Restoration** commands.

## SDK References

### iOS SDK
- **File**: `docs/CL831SDK/CL831/MainVC/HeartParamVC/MainViewController.m`
- **Shutdown**: Line 312 (command not found in provided code)
- **Restoration**: Line 350 - `@"ff05f300"` (without checksum)

### Android SDK  
- **File**: `CL831_Sample/app/src/main/java/com/chileaf/cl831/sample/MainActivity.java`
- **Shutdown**: Line 115 - `mManager.shutdown()`
- **Restoration**: Line 117 - `mManager.restoration()`

## Command Protocol

### Frame Structure
```
[Header] [Length] [Command] [Parameters...] [Checksum]
```

### Checksum Calculation
Both iOS and Android SDKs use the same checksum algorithm:

```dart
int sum = frame.reduce((a, b) => a + b);  // Sum all bytes
int checksum = ((-sum) & 0xFF) ^ 0x3A;    // Negate, mask, XOR with 0x3A
```

**iOS SDK** (HeartBLEDevice.m:1289-1296):
```objectivec
unsigned long num1 = strtoul([CheckStr UTF8String], 0, 16);
uint8_t sss = 0x00 - num1;      // Negation
uint8_t SSS = sss ^ 0x3a;       // XOR with 0x3A
```

## Implemented Commands

### 1. Shutdown Device (0xF1)

**Purpose**: Immediately power off the device

**Command Structure**:
- Header: `0xFF`
- Length: `0x04` (4 bytes before checksum)
- Command: `0xF1`
- Checksum: Calculated

**Final Command**: `FF 04 F1 36`

**Calculation**:
```
Sum = 0xFF + 0x04 + 0xF1 = 0x1F4
Checksum = ((-0x1F4) & 0xFF) ^ 0x3A
         = 0x0C ^ 0x3A  
         = 0x36 ✅
```

**Implementation**:
```dart
Future<void> shutdownDevice() async {
  List<int> frame = [0xFF, 4, 0xF1];
  
  int sum = frame.reduce((a, b) => a + b);
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  frame.add(checksum);
  
  await _sendCommand(frame);
  // Device powers off immediately
}
```

**Status**: ✅ Working (confirmed by user)

---

### 2. Factory Restoration (0xF3)

**Purpose**: Reset device to factory settings

**Command Structure**:
- Header: `0xFF`
- Length: `0x05` (5 bytes before checksum)
- Command: `0xF3`
- Parameter: `0x00`
- Checksum: Calculated

**Final Command**: `FF 05 F3 00 33`

**Calculation**:
```
Frame = [0xFF, 0x05, 0xF3, 0x00]
Sum = 0xFF + 0x05 + 0xF3 + 0x00 = 0x1F7
Checksum = ((-0x1F7) & 0xFF) ^ 0x3A
         = 0x09 ^ 0x3A
         = 0x33 ✅
```

**iOS SDK Raw Command**: `ff05f300` (checksum added by `CheckSun28` function)

**Implementation**:
```dart
Future<void> deviceReset() async {
  List<int> frame = [0xFF, 5, 0xF3, 0x00];
  
  int sum = frame.reduce((a, b) => a + b);
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  frame.add(checksum);
  
  await _sendCommand(frame);
  // Device resets to factory settings
}
```

**Status**: ✅ Implemented (to be tested)

---

## Testing

### Checksum Verification
Run the test file to verify checksum calculations:

```bash
dart test_restoration_checksum.dart
```

**Expected Output**:
```
Frame: 0xFF 0x05 0xF3 0x00 0x33
Sum: 0x1F7
Checksum: 0x33

✅ Final Restoration command: ff05f30033

=== Comparison with Shutdown ===
Shutdown command: 0xFF 0x04 0xF1 0x36
```

### Usage in App

Both commands are available in:
- **Manual Tests Widget**: Test panel for all commands
- **Device Control Widget**: User-friendly buttons with confirmations

**Example Usage**:
```dart
// Shutdown device
await extendedService.shutdownDevice();

// Factory reset device  
await extendedService.deviceReset();
```

---

## Key Insights

### Why Previous Implementation Failed

1. **Incorrect Length**: Using `buildOfficialCommand(0xF3, [0])` was correct, but the issue was elsewhere
2. **iOS SDK Format**: The iOS SDK passes commands **without checksum** as hex strings (e.g., `ff05f300`)
3. **Checksum Addition**: The `CheckSun28` function calculates and appends the checksum before sending

### iOS SDK Command Flow

```
MainViewController.m:350
   ↓
RestClick → BLEReadData("ff05f300")
   ↓  
CheckSun("ff05f300") → calculates checksum → "33"
   ↓
CheckSun28("ff05f300", "33") → appends checksum → "ff05f30033"
   ↓
BLESendYinDaBuff → converts hex string to bytes → sends to device
```

### Critical Details

1. **Parameter Required**: The restoration command requires parameter `0x00` (unlike some assumptions)
2. **Length Includes Parameters**: Length byte = header + length + command + parameters
3. **Checksum Excludes Itself**: Checksum is calculated on all bytes except the checksum byte itself

---

## Related Files

- **Service**: `lib/chileaf_extended_service.dart` (lines 3885-3953)
- **Protocol**: `lib/services/ble_protocol/official_commands.dart`
- **Test**: `test_restoration_checksum.dart`
- **UI**: 
  - `lib/widgets/device_control_widget.dart`
  - `lib/widgets/manual_tests_widget.dart`

---

## Next Steps

1. ✅ Implemented restoration command with correct checksum
2. ⏳ Test with real device to confirm functionality
3. ⏳ Monitor device response/acknowledgment (if any)
4. ⏳ Add UI feedback for successful reset

---

## Comparison Table

| Command | Code | Length | Parameters | Checksum | Final Bytes | Status |
|---------|------|--------|------------|----------|-------------|--------|
| Shutdown | 0xF1 | 4 | None | 0x36 | `FF 04 F1 36` | ✅ Working |
| Restoration | 0xF3 | 5 | 0x00 | 0x33 | `FF 05 F3 00 33` | ✅ Implemented |

---

*Last Updated*: Based on iOS SDK `MainViewController.m:350` and Android SDK `MainActivity.java:117`
