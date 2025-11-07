# Sleep Data Extraction Guide - CL837/CL831 Device
## Complete Implementation Guide for Sleep History Download

**Version:** 1.0  
**Date:** November 5, 2025  
**Author:** Technical Documentation  
**Purpose:** Transfer sleep extraction logic to other applications

---

## 📋 Table of Contents

1. [Overview](#overview)
2. [BLE Protocol](#ble-protocol)
3. [Command Structure](#command-structure)
4. [Response Parsing](#response-parsing)
5. [Session Merging Logic](#session-merging-logic)
6. [Sleep Classification](#sleep-classification)
7. [Complete Implementation](#complete-implementation)
8. [Testing & Validation](#testing--validation)
9. [Common Pitfalls](#common-pitfalls)

---

## Overview

### Device Compatibility
- **Devices:** CL837, CL831, CL880 (Chileaf series)
- **Protocol:** BLE 4.0+ (GATT)
- **SDK Compatibility:** Android (JAVA) and iOS (Objective-C)

### Sleep Data Format
The device stores sleep sessions in **60-minute blocks** (12 × 5-minute intervals).  
Each session is transmitted separately and must be **merged** to form complete night sleep periods.

---

## BLE Protocol

### Required Characteristics

```
Service UUID: 6E400001-B5A3-F393-E0A9-E50E24DCCA9E

TX Characteristic (Device → App):
  UUID: 6E400003-B5A3-F393-E0A9-E50E24DCCA9E
  Properties: NOTIFY
  
RX Characteristic (App → Device):
  UUID: 6E400002-B5A3-F393-E0A9-E50E24DCCA9E
  Properties: WRITE, WRITE_WITHOUT_RESPONSE
```

### Connection Setup

```pseudocode
1. Connect to device
2. Discover services
3. Enable notifications on TX characteristic
4. Verify RX characteristic is ready
5. Send sleep history command
```

---

## Command Structure

### Sleep History Request (0x05 Legacy Format)

**Official SDK Command - RECOMMENDED:**

```
Command Bytes: [0xFF, 0x05, 0x05, 0x02, 0xCF]

Breakdown:
  0xFF      - Command header
  0x05      - Packet length (5 bytes)
  0x05      - Command ID (Sleep data)
  0x02      - Parameter (Request history)
  0xCF      - Checksum
```

**Checksum Calculation:**
```pseudocode
sum = 0xFF + 0x05 + 0x05 + 0x02 = 0x10B
checksum = (-sum) & 0xFF = 0xF5
checksum = checksum ^ 0x3A = 0xCF ✅
```

### Alternative Format (0x31 - Not Recommended)

```
Command: [0xFF, 0x09, 0x31, 0x00, 0x00, 0x00, 0x00, 0x00, checksum]

Note: This format has multi-packet complexity. 
Use 0x05 for SDK compatibility.
```

---

## Response Parsing

### Response Format (0x05)

**Multiple packets, each containing 1+ sessions:**

```
Packet Structure:
[0xFF][length][0x05][0x03][session_data...][checksum]

Session Data Format:
[count][utc_time_4bytes][action_indices...]

Example Packet:
FF 8D 05 03 0C 5E 0C C3 83 00 02 00 00 00 00 02 02 07 0C 08 17 ...
│  │  │  │  │  └─────┬─────┘ └──────┬──────────────────────────┘
│  │  │  │  │        │              │
│  │  │  │  │        │              └─ 12 action indices (60 min)
│  │  │  │  │        └─ UTC timestamp (1577836579 = 2020-01-01 00:56:19)
│  │  │  │  └─ Count (12 intervals = 60 minutes)
│  │  │  └─ Sub-command (0x03 = data)
│  │  └─ Command (0x05 = sleep)
│  └─ Length (141 bytes)
└─ Header
```

### Parsing Algorithm

```pseudocode
FUNCTION parseSleepData05(data):
    IF data.length < 10:
        RETURN error
    
    // Verify header
    IF data[0] != 0xFF OR data[2] != 0x05 OR data[3] != 0x03:
        RETURN error
    
    length = data[1] - 5  // Exclude header/checksum
    start = 4
    sessions = []
    
    WHILE start < length AND start < data.length - 1:
        count = data[start]                    // Number of 5-min blocks
        utcTime = readBigEndian32(data, start+1)  // 4 bytes UTC (in SECONDS!)
        
        // ⚠️ CRITICAL: UTC → Local Time Conversion
        // Device sends UTC timestamp in SECONDS (not milliseconds!)
        // iOS SDK: [NSDate dateWithTimeIntervalSince1970:stamp - timeZoneSecond]
        // Must convert: UTC seconds → Local DateTime
        timestamp = convertUTCSecondsToLocal(utcTime)
        
        actions = []
        FOR i = 0 TO count - 1:
            actions.append(data[start + 5 + i])
        END FOR
        
        sessions.append({
            timestamp: timestamp,  // Local time, not UTC!
            count: count,
            actions: actions
        })
        
        start = start + 5 + count  // Move to next session
    END WHILE
    
    RETURN sessions
END FUNCTION
```

### ⚠️ CRITICAL: UTC Timestamp Conversion

**The device sends timestamps in UTC (SECONDS), not local time!**

You **MUST** convert UTC → Local time, or all sleep timestamps will be wrong by your timezone offset!

**iOS SDK Reference (SleepDataController.m line 245):**
```objectivec
NSInteger timeZoneSecond = [[NSTimeZone localTimeZone] secondsFromGMT];
NSDate *detaildate = [NSDate dateWithTimeIntervalSince1970:stamp - timeZoneSecond];
```

**Conversion Algorithm:**
```pseudocode
FUNCTION convertUTCSecondsToLocal(utcSeconds):
    // Step 1: Convert seconds → milliseconds
    milliseconds = utcSeconds * 1000
    
    // Step 2: Create DateTime as UTC
    utcDateTime = DateTime.fromEpoch(milliseconds, timezone=UTC)
    
    // Step 3: Convert to local timezone
    localDateTime = utcDateTime.toLocalTimezone()
    
    RETURN localDateTime
END FUNCTION
```

**Example:**
```
Device sends: 1730847381 (UTC seconds)
  = 2024-11-05 14:30:41 UTC
  
❌ WRONG (interpret as local):
  DateTime.fromEpoch(1730847381 * 1000)
  = 2024-11-05 14:30:41 Local (WRONG if you're not in UTC+0!)
  
✅ CORRECT (convert UTC → Local):
  DateTime.fromEpoch(1730847381 * 1000, isUtc=true).toLocal()
  = 2024-11-05 15:30:41 CET (if timezone is UTC+1) ✅
```

**Why This Matters:**
- If you sleep at 22:00 local time (CET = UTC+1)
- Device saves: 21:00 UTC (22:00 - 1h)
- Without conversion: CSV shows 21:00 ❌
- With conversion: CSV shows 22:00 ✅

### End Signal

```
End Packet: [0xFF, 0x04, 0x05, 0xFF, checksum]

When received:
  - All data transmitted
  - Finalize and merge sessions
```

---

## Session Merging Logic

### Why Merge is Critical

❌ **Without Merge:**
```
Device sends:
  Session 1: 00:56-01:56 (60 min) → Classified as "Nap" ❌
  Session 2: 01:56-02:56 (60 min) → Classified as "Nap" ❌
  Session 3: 02:56-03:56 (60 min) → Classified as "Nap" ❌
  ...
Result: 7 separate naps instead of 1 night sleep!
```

✅ **With Merge:**
```
After merge:
  Session 1: 00:56-07:56 (420 min) → "Night Sleep" ✅
```

### Merge Algorithm

```pseudocode
FUNCTION mergeConsecutiveSessions(sessions):
    IF sessions.isEmpty:
        RETURN []
    
    // Sort by timestamp
    sessions.sortBy(timestamp)
    
    merged = []
    currentGroup = NULL
    
    FOR EACH session IN sessions:
        IF currentGroup == NULL:
            // First session - start new group
            currentGroup = copy(session)
            CONTINUE
        END IF
        
        // Calculate gap
        currentEnd = currentGroup.timestamp + (currentGroup.count × 5 minutes)
        nextStart = session.timestamp
        gap = nextStart - currentEnd
        
        // Check merge conditions
        sameNightPeriod = isNightTime(currentEnd.hour) AND isNightTime(nextStart.hour)
        
        IF gap < 30 minutes AND sameNightPeriod:
            // MERGE sessions
            currentGroup.actions.append(session.actions)
            currentGroup.count = currentGroup.actions.length
            DEBUG "✅ Merged: gap=" + gap + "min, duration=" + (currentGroup.count × 5) + "min"
        ELSE:
            // Save current group and start new one
            merged.append(currentGroup)
            currentGroup = copy(session)
            DEBUG "❌ Not merged: gap=" + gap + "min, sameNight=" + sameNightPeriod
        END IF
    END FOR
    
    // Add last group
    IF currentGroup != NULL:
        merged.append(currentGroup)
    END IF
    
    RETURN merged
END FUNCTION

FUNCTION isNightTime(hour):
    // Night period: 18:00 - 10:00
    RETURN hour >= 18 OR hour <= 10
END FUNCTION
```

### Merge Example

```
Input (13 raw sessions):
  1. 2020-01-01 00:56:19 (12 blocks = 60 min)
  2. 2020-01-01 01:56:19 (12 blocks = 60 min)  [gap: 0 min ✅]
  3. 2020-01-01 02:56:19 (12 blocks = 60 min)  [gap: 0 min ✅]
  4. 2020-01-01 03:56:19 (12 blocks = 60 min)  [gap: 0 min ✅]
  5. 2020-01-01 04:56:19 (12 blocks = 60 min)  [gap: 0 min ✅]
  6. 2020-01-01 05:56:19 (12 blocks = 60 min)  [gap: 0 min ✅]
  7. 2020-01-01 06:56:19 (7 blocks = 35 min)   [gap: 0 min ✅]
  8. 2025-11-04 11:10:13 (6 blocks = 30 min)   [gap: HUGE ❌]
  9. 2025-11-04 11:34:44 (3 blocks = 15 min)   [gap: -5 min ❌]
  ...

Output (7 merged sessions):
  1. 2020-01-01 00:56:19 (79 blocks = 395 min) ← 7 sessions merged!
  2. 2025-11-04 11:10:13 (6 blocks = 30 min)
  3. 2025-11-04 11:34:44 (3 blocks = 15 min)
  4. 2025-11-04 14:22:54 (7 blocks = 35 min)
  5. 2025-11-04 19:06:18 (6 blocks = 30 min)
  6. 2025-11-04 20:36:47 (3 blocks = 15 min)
  7. 2025-11-05 00:07:56 (9 blocks = 45 min)
```

---

## Sleep Classification

### Activity Index Interpretation

```
Action Index Values (per 5-minute block):
  > 20      → Awake (movement detected)
  1-20      → Light Sleep
  0         → Still (potential deep sleep)
  
Deep Sleep Detection:
  3+ consecutive zeros → Deep Sleep
  < 3 consecutive zeros → Light Sleep
```

### Classification Rules

```pseudocode
FUNCTION classifySleepType(session):
    duration = session.count × 5  // minutes
    hour = session.timestamp.hour
    
    // Too short
    IF duration < 30:
        RETURN "Insufficient" (⏱️)
    END IF
    
    // Night time check (18:00-10:00)
    isNightTime = hour >= 18 OR hour <= 10
    
    // Main night sleep: 3+ hours during night
    IF duration >= 180 AND isNightTime:
        RETURN "Night Sleep" (🌙)
    END IF
    
    // Long daytime nap
    IF duration >= 180:
        RETURN "Long Nap" (🛋️)
    END IF
    
    // Short nap
    RETURN "Short Nap" (😴)
END FUNCTION
```

### Sleep Phases Calculation

```pseudocode
FUNCTION calculateSleepPhases(actions):
    lightSleep = 0
    deepSleep = 0
    awake = 0
    zeroCount = 0
    
    FOR EACH action IN actions:
        IF action > 20:
            // Awake - process accumulated zeros first
            IF zeroCount >= 3:
                deepSleep += zeroCount
            ELSE IF zeroCount > 0:
                lightSleep += zeroCount
            END IF
            zeroCount = 0
            awake += 1
            
        ELSE IF action > 0 AND action <= 20:
            // Light sleep - process accumulated zeros first
            IF zeroCount >= 3:
                deepSleep += zeroCount
            ELSE IF zeroCount > 0:
                lightSleep += zeroCount
            END IF
            zeroCount = 0
            lightSleep += 1
            
        ELSE:
            // Zero - accumulate
            zeroCount += 1
        END IF
    END FOR
    
    // Process remaining zeros
    IF zeroCount >= 3:
        deepSleep += zeroCount
    ELSE IF zeroCount > 0:
        lightSleep += zeroCount
    END IF
    
    RETURN {
        deepSleepMinutes: deepSleep × 5,
        lightSleepMinutes: lightSleep × 5,
        awakeMinutes: awake × 5,
        totalMinutes: actions.length × 5
    }
END FUNCTION
```

---

## Complete Implementation

### Step-by-Step Process

```pseudocode
// ========================================
// STEP 1: SEND COMMAND
// ========================================
FUNCTION requestSleepHistory():
    command = [0xFF, 0x05, 0x05, 0x02, 0xCF]
    
    // Send via BLE RX characteristic
    bleDevice.write(rxCharacteristic, command, writeWithoutResponse=true)
    
    DEBUG "📡 Sleep history command sent"
END FUNCTION


// ========================================
// STEP 2: RECEIVE & BUFFER DATA
// ========================================
buffer = []  // Global buffer

FUNCTION onBLEDataReceived(data):
    IF data[2] == 0x05:  // Sleep command
        IF data[3] == 0x03:  // Data packet
            sessions = parseSleepData05(data)
            buffer.appendAll(sessions)
            
            DEBUG "📦 Buffered " + sessions.length + " sessions"
            DEBUG "💾 Total in buffer: " + buffer.length
            
            // Auto-finalize on small packets
            IF data.length <= 50:
                finalizeSleepData()
            END IF
            
        ELSE IF data[3] == 0xFF:  // End signal
            DEBUG "🏁 End signal received"
            finalizeSleepData()
        END IF
    END IF
END FUNCTION


// ========================================
// STEP 3: MERGE & FINALIZE
// ========================================
FUNCTION finalizeSleepData():
    IF buffer.isEmpty:
        DEBUG "⚠️ No data to finalize"
        RETURN
    END IF
    
    DEBUG "🔗 Starting merge: " + buffer.length + " sessions"
    
    // CRITICAL: Merge before classification!
    merged = mergeConsecutiveSessions(buffer)
    
    DEBUG "✅ Merge complete: " + buffer.length + " → " + merged.length
    
    // Process each merged session
    FOR EACH session IN merged:
        // Calculate phases
        phases = calculateSleepPhases(session.actions)
        
        // Classify type
        type = classifySleepType(session)
        
        // Save to database/storage
        saveSession({
            timestamp: session.timestamp,
            duration: session.count × 5,
            type: type,
            deepSleep: phases.deepSleepMinutes,
            lightSleep: phases.lightSleepMinutes,
            awake: phases.awakeMinutes,
            rawActions: session.actions
        })
        
        DEBUG "💾 Saved: " + session.timestamp + " (" + (session.count × 5) + "min) - " + type
    END FOR
    
    // Clear buffer
    buffer.clear()
    
    // Notify UI
    notifyUI(merged)
END FUNCTION
```

---

## Testing & Validation

### Test Case 1: Single Night Sleep

```
Input:
  7 sessions × 60 min each = 420 min total
  Timestamps: 00:56 → 06:56 (consecutive)
  
Expected Output:
  1 merged session
  Duration: 420 minutes (7 hours)
  Type: Night Sleep 🌙
  Classification: PASS (>= 180 min, night time)
```

### Test Case 2: Naps vs Night Sleep

```
Input:
  Session A: 14:00 (30 min) - Daytime
  Session B: 22:00 (180 min) - Night time
  
Expected Output:
  2 separate sessions (no merge - gap too large)
  A: Short Nap 😴 (< 180 min)
  B: Night Sleep 🌙 (>= 180 min, night time)
```

### Test Case 3: Gap Detection

```
Input:
  Session 1: 00:56 (60 min) → ends 01:56
  Session 2: 01:56 (60 min) → gap = 0 min ✅
  Session 3: 03:30 (60 min) → gap = 94 min ❌
  
Expected Output:
  2 merged sessions:
    Sessions 1+2: 120 min
    Session 3: 60 min (separate)
```

### Validation Logs

```
Expected Console Output:

🌙📦 Processing sleep data 0x05 format...
  📊 Session 1: 12 action indices
  🕐 Timestamp: 1577836579 → 2020-01-01 00:56:19
  ✅ Session 1 added to buffer

  📊 Session 2: 12 action indices
  🕐 Timestamp: 1577840179 → 2020-01-01 01:56:19
  ✅ Session 2 added to buffer
  
💾 Total sessions in buffer: 7

🔗 Starting session merge analysis...
  🔍 Gap between sessions: 0 minutes
  ✅ Merging consecutive sessions (gap: 0min)
  📊 Merged session now: 120 minutes
  
  🔍 Gap between sessions: 0 minutes
  ✅ Merging consecutive sessions (gap: 0min)
  📊 Merged session now: 180 minutes
  
🔗 Merge complete: 7 → 1 sessions

📅 2020-01-01 00:56:19: 420min total, Awake=0min, Light=125min, Deep=270min
📤 Sent 1 session to UI stream
```

---

## Common Pitfalls

### ❌ WRONG: Classify Before Merge

```pseudocode
// THIS WILL FAIL!
sessions = parseSleepData05(data)
FOR session IN sessions:
    type = classifySleepType(session)  // Each 60 min = "Nap" ❌
    save(session, type)
END FOR
```

**Result:** All sessions classified as naps!

### ✅ CORRECT: Merge Then Classify

```pseudocode
// THIS WORKS!
sessions = parseSleepData05(data)
merged = mergeConsecutiveSessions(sessions)  // Combine first!
FOR session IN merged:
    type = classifySleepType(session)  // 420 min = "Night Sleep" ✅
    save(session, type)
END FOR
```

### ❌ WRONG: Ignore Checksum

```pseudocode
// Command without checksum - DEVICE WILL IGNORE!
command = [0xFF, 0x05, 0x05, 0x02]  // Missing checksum ❌
```

### ✅ CORRECT: Always Calculate Checksum

```pseudocode
command = [0xFF, 0x05, 0x05, 0x02]
checksum = calculateChecksum(command)
command.append(checksum)  // [0xFF, 0x05, 0x05, 0x02, 0xCF] ✅
```

### ❌ WRONG: Use Wrong Protocol

```pseudocode
// Using 0x31 format - Complex multi-packet handling!
command = [0xFF, 0x09, 0x31, 0x00, 0x00, 0x00, 0x00, 0x00, checksum]
```

### ✅ CORRECT: Use 0x05 Legacy Format

```pseudocode
// SDK-compatible, simpler parsing
command = [0xFF, 0x05, 0x05, 0x02, 0xCF]
```

### ❌ WRONG: Ignore UTC Conversion

```pseudocode
// THIS WILL GIVE WRONG TIMESTAMPS!
timestamp = DateTime.fromEpoch(utcTime * 1000)  // Interprets as local ❌
```

**Result:** All timestamps wrong by timezone offset!

### ✅ CORRECT: Convert UTC → Local

```pseudocode
// Correct conversion (like iOS SDK)
timestamp = DateTime.fromEpoch(utcTime * 1000, isUtc=true).toLocal()  // ✅
```

### ❌ WRONG: Request in Loop

```pseudocode
// THIS CREATES INFINITE LOOP!
FUNCTION onDataReceived(data):
    save(data)
    requestSleepHistory()  // Request again! ❌ LOOP!
END FUNCTION
```

### ✅ CORRECT: Request Once, Load from Storage

```pseudocode
// Request only when user initiates
FUNCTION downloadSleepData():
    requestSleepHistory()  // Once!
END FUNCTION

// Stream listener just saves
FUNCTION onDataReceived(data):
    save(data)
    loadFromStorage()  // No new request ✅
END FUNCTION
```

---

## Code Examples

### Flutter/Dart Implementation

```dart
// Send command
Future<void> getHistoryOfSleep() async {
  final command = [0xFF, 0x05, 0x05, 0x02, 0xCF];
  await rxCharacteristic.write(command, withoutResponse: true);
  debugPrint('📡 Sleep command sent');
}

// Parse response
void _processSleepData05(List<int> data) {
  if (data.length < 10) return;
  if (data[0] != 0xFF || data[2] != 0x05 || data[3] != 0x03) return;
  
  int length = data[1] - 5;
  int start = 4;
  
  while (start < length && start < data.length - 1) {
    int count = data[start];
    
    // Read UTC timestamp (4 bytes, big-endian)
    int utcTime = (data[start + 1] << 24) + 
                  (data[start + 2] << 16) + 
                  (data[start + 3] << 8) + 
                  data[start + 4];
    
    // ✅ CRITICAL: Convert UTC → Local (like iOS SDK)
    DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
      utcTime * 1000,  // Convert seconds → milliseconds
      isUtc: true      // Interpret as UTC!
    ).toLocal();       // Convert to local timezone
    
    List<int> actions = [];
    for (int i = 0; i < count; i++) {
      actions.add(data[start + 5 + i]);
    }
    
    _buffer.add({
      'timestamp': timestamp,  // Local time, not UTC!
      'count': count,
      'sleep': actions,
    });
    
    start = start + 5 + count;
  }
}

// Merge sessions
List<Map<String, dynamic>> _mergeConsecutiveSessions(
    List<Map<String, dynamic>> sessions) {
  if (sessions.isEmpty) return [];
  
  sessions.sort((a, b) => 
    (a['timestamp'] as DateTime).compareTo(b['timestamp'] as DateTime));
  
  List<Map<String, dynamic>> merged = [];
  Map<String, dynamic>? currentGroup;
  
  for (var session in sessions) {
    if (currentGroup == null) {
      currentGroup = Map.from(session);
      continue;
    }
    
    DateTime currentEnd = (currentGroup['timestamp'] as DateTime)
        .add(Duration(minutes: (currentGroup['sleep'] as List<int>).length * 5));
    DateTime nextStart = session['timestamp'] as DateTime;
    
    Duration gap = nextStart.difference(currentEnd);
    bool sameNightPeriod = _isNightTime(currentEnd.hour) && 
                           _isNightTime(nextStart.hour);
    
    if (gap.inMinutes < 30 && sameNightPeriod) {
      // Merge
      List<int> mergedActions = List<int>.from(currentGroup['sleep'] as List<int>);
      mergedActions.addAll(session['sleep'] as List<int>);
      currentGroup['sleep'] = mergedActions;
      currentGroup['count'] = mergedActions.length;
    } else {
      merged.add(currentGroup);
      currentGroup = Map.from(session);
    }
  }
  
  if (currentGroup != null) merged.add(currentGroup);
  return merged;
}

bool _isNightTime(int hour) => hour >= 18 || hour <= 10;
```

### Java/Android Implementation

```java
// Send command
public void getHistoryOfSleep() {
    byte[] command = new byte[]{
        (byte) 0xFF, 0x05, 0x05, 0x02, (byte) 0xCF
    };
    writeCharacteristic.setValue(command);
    bluetoothGatt.writeCharacteristic(writeCharacteristic);
    Log.d(TAG, "📡 Sleep command sent");
}

// Parse response
private void processSleepData05(byte[] data) {
    if (data.length < 10) return;
    if (data[0] != (byte)0xFF || data[2] != 0x05 || data[3] != 0x03) return;
    
    int length = (data[1] & 0xFF) - 5;
    int start = 4;
    
    while (start < length && start < data.length - 1) {
        int count = data[start] & 0xFF;
        
        long utcTime = ((data[start+1] & 0xFF) << 24) |
                       ((data[start+2] & 0xFF) << 16) |
                       ((data[start+3] & 0xFF) << 8) |
                       (data[start+4] & 0xFF);
        
        int[] actions = new int[count];
        for (int i = 0; i < count; i++) {
            actions[i] = data[start + 5 + i] & 0xFF;
        }
        
        SleepSession session = new SleepSession(utcTime, count, actions);
        buffer.add(session);
        
        start = start + 5 + count;
    }
}
```

### iOS/Objective-C Implementation

```objectivec
// Send command
- (void)getHistoryOfSleep {
    unsigned char command[] = {0xFF, 0x05, 0x05, 0x02, 0xCF};
    NSData *data = [NSData dataWithBytes:command length:5];
    [peripheral writeValue:data 
         forCharacteristic:rxCharacteristic 
                      type:CBCharacteristicWriteWithoutResponse];
    NSLog(@"📡 Sleep command sent");
}

// Parse response
- (void)processSleepData05:(NSData *)data {
    const unsigned char *bytes = [data bytes];
    NSUInteger length = [data length];
    
    if (length < 10) return;
    if (bytes[0] != 0xFF || bytes[2] != 0x05 || bytes[3] != 0x03) return;
    
    int dataLength = bytes[1] - 5;
    int start = 4;
    
    while (start < dataLength && start < length - 1) {
        int count = bytes[start];
        
        uint32_t utcTime = (bytes[start+1] << 24) |
                           (bytes[start+2] << 16) |
                           (bytes[start+3] << 8) |
                           bytes[start+4];
        
        NSMutableArray *actions = [NSMutableArray array];
        for (int i = 0; i < count; i++) {
            [actions addObject:@(bytes[start + 5 + i])];
        }
        
        NSDictionary *session = @{
            @"timestamp": @(utcTime),
            @"count": @(count),
            @"actions": actions
        };
        [buffer addObject:session];
        
        start = start + 5 + count;
    }
}
```

---

## Summary Checklist

**Before implementing, ensure:**

- [ ] BLE notifications enabled on TX characteristic
- [ ] RX characteristic supports write without response
- [ ] Checksum calculation implemented correctly
- [ ] Response parser handles multiple sessions per packet
- [ ] **Session merging logic implemented (CRITICAL!)**
- [ ] Classification happens AFTER merging
- [ ] End signal (0xFF) triggers finalization
- [ ] No infinite request loops
- [ ] Timestamps converted to local timezone
- [ ] Deep sleep detection uses 3-zero rule
- [ ] Night time window: 18:00-10:00
- [ ] Minimum duration thresholds applied

---

## Reference Implementation

**Complete working implementation:** See `lib/chileaf_extended_service.dart`
- Method: `_processSleepData05()`
- Method: `_finalizeSleepData05()`
- Method: `_mergeConsecutiveSessions()`

**Sleep classification:** See `lib/services/sleep_classifier.dart`
- Class: `SleepClassifier`
- Method: `classifySession()`

**Official SDKs:**
- Android: `docs/CL831SE_Android_SDK_V3.0.4/WearManager.java` (line 671-724)
- iOS: `docs/CL831SDK/HeartBLEDevice.m` (line 870-910)

---

**End of Guide**  
For questions or issues, refer to official SDK documentation or test against reference implementation.
