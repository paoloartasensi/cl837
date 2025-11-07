# UTC/Timezone Management Fix

## 🐛 Problem Discovered

### Current Behavior (WRONG)
```dart
// In chileaf_extended_service.dart line ~1845
int utc = _getLongParse(value, j, 4);  // Device sends UTC timestamp
int utcMillis = utc * 1000;
utcMillis -= 28800000;  // ❌ HARDCODED 8 hours (China timezone!)
timestamp: DateTime.fromMillisecondsSinceEpoch(utcMillis),  // Creates LOCAL DateTime
```

### Issues
1. **Hardcoded offset**: -8 hours (28800000 ms) is China Standard Time (UTC+8)
2. **Wrong for other timezones**: 
   - Italy (UTC+1/+2): Shows sleep 6-7 hours OFF
   - USA EST (UTC-5): Shows sleep 13 hours OFF
3. **Inconsistent with system**: User's phone timezone ignored
4. **Sleep classification fails**: Night detection uses wrong hours

## 📊 Example Impact

### Sleep Session at 23:00 UTC (Device Time)
| User Location | Expected Local Time | Current (Wrong) | Offset Error |
|--------------|---------------------|-----------------|--------------|
| **Italy (UTC+2 DST)** | 01:00 (next day) | 17:00 (8h earlier) | -8 hours |
| **China (UTC+8)** | 07:00 (next day) | 23:00 | ✅ Correct |
| **USA EST (UTC-5)** | 18:00 (prev day) | 10:00 | -8 hours |
| **UK (UTC+0)** | 23:00 | 15:00 | -8 hours |

### Impact on Features
- ❌ **CSV Export**: Wrong timestamps
- ❌ **Sleep Classification**: "Night sleep" at 17:00 detected as nap
- ❌ **Recovery Score**: Uses wrong sleep sessions
- ❌ **Dashboard Display**: Shows sleep at incorrect times

## 🔧 Solution

### Understanding DateTime in Dart

```dart
// 1. Device sends: UTC timestamp (seconds since 1970-01-01 00:00:00 UTC)
int utcTimestamp = 1729728000; // Example: 2024-10-23 22:00:00 UTC

// 2. Convert to milliseconds
int utcMillis = utcTimestamp * 1000;

// 3. Create UTC DateTime
DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);
// Result: 2024-10-23 22:00:00.000Z (UTC)

// 4. Convert to local timezone
DateTime localDateTime = utcDateTime.toLocal();
// Italy (UTC+2): 2024-10-24 00:00:00.000 (correct!)
// China (UTC+8): 2024-10-24 06:00:00.000 (correct!)
// USA EST (UTC-5): 2024-10-23 17:00:00.000 (correct!)
```

### Implementation Fix

#### Option 1: Automatic Local Conversion (RECOMMENDED)
```dart
// Parse UTC timestamp from device
int utc = _getLongParse(value, j, 4);
int utcMillis = utc * 1000;

// Create UTC DateTime and convert to local
DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);
DateTime localDateTime = utcDateTime.toLocal();

SleepHistoryEntry sleepEntry = SleepHistoryEntry(
  timestamp: localDateTime,  // ✅ Now in user's local timezone
  count: actions.length,
  actions: actions,
);
```

**Pros**:
- ✅ Works for all users worldwide
- ✅ Automatically adapts to system timezone
- ✅ DST (daylight saving) handled by Dart
- ✅ No configuration needed

**Cons**:
- None (this is the correct approach)

#### Option 2: Keep UTC (for advanced users)
```dart
// Store as UTC, convert only for display
DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);

SleepHistoryEntry sleepEntry = SleepHistoryEntry(
  timestamp: utcDateTime,  // Store UTC
  count: actions.length,
  actions: actions,
);

// In UI: convert to local for display
String displayTime = sleepEntry.timestamp.toLocal().toString();
```

**Pros**:
- ✅ Data stored in absolute time (UTC)
- ✅ Can convert to any timezone later

**Cons**:
- ❌ Must remember to convert in ALL UI code
- ❌ Easy to forget and show UTC to users
- ❌ More complex to maintain

### Recommended: Option 1 (Auto Local)

## 🔨 Implementation Plan

### Files to Modify

#### 1. `chileaf_extended_service.dart`

**Location**: Sleep data parsing (line ~1845)
```dart
// BEFORE:
int utcMillis = utc * 1000;
utcMillis -= 28800000; // ❌ Hardcoded China timezone
timestamp: DateTime.fromMillisecondsSinceEpoch(utcMillis),

// AFTER:
int utcMillis = utc * 1000;
DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);
timestamp: utcDateTime.toLocal(),  // ✅ Convert to local timezone
```

**Also check**: 
- HR history parsing (similar issue?)
- Exercise data parsing
- Any other timestamp conversions

#### 2. `timestamp_decoder.dart`

Add helper method:
```dart
/// Convert device UTC timestamp to local DateTime
static DateTime utcToLocal(int utcSeconds) {
  int utcMillis = utcSeconds * 1000;
  DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(utcMillis, isUtc: true);
  return utcDateTime.toLocal();
}
```

#### 3. CSV Export Fix

Update `unified_home_screen.dart` CSV export:
```dart
// Ensure timestamps are in local time
final dateStr = session.timestamp.toLocal().toString();
// Or use DateFormat from intl package:
// final dateStr = DateFormat('yyyy-MM-dd HH:mm:ss').format(session.timestamp);
```

#### 4. Sleep Classification Fix

Update `recovery_score.dart`:
```dart
// Detect night sleep using LOCAL time
final mainSleeps = _sleepHistory.where((s) => 
  s.count >= 36 && // At least 3 hours
  (s.timestamp.hour >= 18 || s.timestamp.hour <= 10) // ✅ Now uses local hour
).toList();
```

## 🧪 Testing Plan

### Test Case 1: Verify UTC to Local Conversion
```dart
void testTimezoneConversion() {
  // Example UTC timestamp: 2024-10-23 22:00:00 UTC
  int utcTimestamp = 1729728000;
  
  // Old method (wrong)
  int oldMillis = utcTimestamp * 1000 - 28800000;
  DateTime oldTime = DateTime.fromMillisecondsSinceEpoch(oldMillis);
  print('❌ Old (wrong): $oldTime');
  
  // New method (correct)
  DateTime utcTime = DateTime.fromMillisecondsSinceEpoch(
    utcTimestamp * 1000, 
    isUtc: true
  );
  DateTime localTime = utcTime.toLocal();
  print('✅ New (correct): $localTime');
  print('   Timezone offset: ${localTime.timeZoneOffset}');
  print('   Is UTC: ${localTime.isUtc}');
}
```

**Expected Output (Italy UTC+2)**:
```
❌ Old (wrong): 2024-10-23 14:00:00.000 (8 hours too early)
✅ New (correct): 2024-10-24 00:00:00.000 (midnight local time)
   Timezone offset: 2:00:00.000000
   Is UTC: false
```

### Test Case 2: Sleep Time Detection
```dart
void testSleepDetection() {
  // Sleep at 23:00 UTC
  DateTime utcSleep = DateTime.utc(2024, 10, 23, 23, 0);
  DateTime localSleep = utcSleep.toLocal();
  
  print('UTC sleep time: ${utcSleep.hour}:00');
  print('Local sleep time: ${localSleep.hour}:00');
  
  // Night detection (18:00-10:00)
  bool isNight = localSleep.hour >= 18 || localSleep.hour <= 10;
  print('Detected as night sleep: $isNight');
}
```

**Expected Output (Italy UTC+2)**:
```
UTC sleep time: 23:00
Local sleep time: 1:00  (next day)
Detected as night sleep: true ✅
```

### Test Case 3: CSV Export
1. Download sleep data
2. Export to CSV
3. Verify timestamps match local time shown in app
4. Check: Times should be in user's timezone, not China time

### Test Case 4: Dashboard Display
1. Open Advanced Health Dashboard
2. Check sleep tab
3. Verify: Sleep times make sense for local timezone
4. Verify: Night sleep correctly classified

## 📝 Code Changes Summary

### Critical Changes (Required)
1. ✅ **Sleep parsing** in `chileaf_extended_service.dart` (~line 1845)
2. ✅ **Sleep classification** in `advanced_health_dashboard.dart` (uses timestamp.hour)
3. ⚠️ **CSV export** in `unified_home_screen.dart` (verify format)

### Optional Improvements
4. ⚠️ **HR history parsing** - check if same issue exists
5. ⚠️ **Exercise data** - check timestamps
6. ⚠️ Add `isUtc` flag to model classes for clarity
7. ⚠️ Add timezone display in UI ("Europe/Rome", "America/New_York")

## 🌍 Timezone Resources

### Common Timezones
```dart
// Get current timezone
String timezone = DateTime.now().timeZoneName;  // "CEST", "PST", etc.
Duration offset = DateTime.now().timeZoneOffset;  // Duration(hours: 2)

// Format with timezone
import 'package:intl/intl.dart';
String formatted = DateFormat('yyyy-MM-dd HH:mm:ss z').format(dateTime);
// "2024-10-24 00:00:00 CEST"
```

### DST (Daylight Saving Time)
Dart automatically handles DST transitions:
```dart
// Summer (DST)
DateTime summer = DateTime(2024, 7, 1, 12, 0);
print(summer.timeZoneOffset);  // 2:00:00 (Italy UTC+2)

// Winter (no DST)
DateTime winter = DateTime(2024, 1, 1, 12, 0);
print(winter.timeZoneOffset);  // 1:00:00 (Italy UTC+1)
```

## 🔍 Debugging Tips

### Add Logging
```dart
debugPrint('🕒 UTC timestamp from device: $utc');
debugPrint('🕒 UTC DateTime: ${utcDateTime.toIso8601String()}');
debugPrint('🕒 Local DateTime: ${localDateTime.toIso8601String()}');
debugPrint('🕒 Timezone offset: ${localDateTime.timeZoneOffset}');
debugPrint('🕒 Timezone name: ${localDateTime.timeZoneName}');
```

### Verify Conversion
```dart
assert(utcDateTime.isUtc == true, 'Should be UTC');
assert(localDateTime.isUtc == false, 'Should be local');
assert(localDateTime.difference(utcDateTime) == localDateTime.timeZoneOffset);
```

## ⚠️ Breaking Change Warning

**This is a breaking change** for existing users in China:
- Before: Times displayed "correctly" (by accident, hardcoded to UTC+8)
- After: Times will shift by offset from UTC+8

**Migration Strategy**:
1. Add version check in app
2. Show one-time notice: "Timezone handling improved"
3. Suggest re-downloading sleep data
4. Old data will have wrong times (acceptable - one-time issue)

## 📚 References

- Dart DateTime: https://api.dart.dev/stable/dart-core/DateTime-class.html
- UTC vs Local: https://dart.dev/guides/language/effective-dart/usage#do-use-utc-for-absolute-time
- Chileaf Protocol: SDK documentation (timestamps are UTC)
- ISO 8601: International standard for date/time (includes timezone)

---

**Status**: 🔴 Critical Issue - Fix Required  
**Priority**: High  
**Estimated Impact**: All non-China users affected  
**Fix Complexity**: Low (2-3 line changes)  
**Testing Required**: Yes (different timezones)
