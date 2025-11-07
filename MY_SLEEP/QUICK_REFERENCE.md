# 🌙 CL837 Sleep Tracking - Quick Reference

**One-page cheat sheet for implementing sleep tracking in any app**

## 📋 Quick Start (5 Steps)

1. **Send Command**: `[0xFF, 0x05, 0x05, 0x02, 0xCF]`
2. **Receive Data**: 60-byte session blocks
3. **Parse Timestamp**: 4 bytes big-endian, UTC seconds
4. **Convert UTC**: `isUtc: true` + `toLocal()`
5. **Merge Sessions**: Gap < 30min + night hours → merge

## 🔧 Essential Commands

```
Get Sleep Data:   FF 05 05 02 CF
Get Heart Rate:   FF 05 06 02 CD
Get Battery:      FF 04 02 02 C2
Get Device Info:  FF 04 00 02 C4
```

## 📦 Session Format (60 bytes)

```
Offset  Bytes  Type        Description
------  -----  ----------  ---------------------
0-3     4      uint32_be   UTC timestamp (seconds)
4-5     2      uint16_be   Duration (minutes)
6-59    54     uint8[]     Action indices (5min each)
```

## 🌍 UTC Conversion (CRITICAL!)

```dart
// ✅ CORRECT
DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
  utcSeconds * 1000,
  isUtc: true,      // REQUIRED!
).toLocal();

// ❌ WRONG (missing isUtc: true)
DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
  utcSeconds * 1000  // Wrong timezone!
);
```

**Impact**: Wrong by timezone offset (e.g., 1 hour for UTC+1)

## 🔢 Action Index Meaning

```
0       = Deep Sleep   (no movement)
1-2     = Light Sleep  (minimal movement)
3+      = Awake        (significant movement)
```

Each index = 5 minutes

## 🔗 Session Merging Rules

```dart
bool shouldMerge = 
  (gap < 30 minutes) &&
  (startHour >= 18 || startHour <= 10);  // Night time
```

**Example**: 7 sessions of 60min each → 1 merged session of 420min

## 🏷️ Classification Rules

```
Night Sleep:  >= 180min AND hour in [18:00-10:00]
Long Nap:     >= 180min AND hour outside night
Short Nap:    30-179min (any time)
Brief Rest:   15-29min
Insufficient: < 15min
```

## 📊 Phase Calculation

```dart
for (int action in actions) {
  if (action == 0) deepMinutes += 5;
  else if (action <= 2) lightMinutes += 5;
  else awakeMinutes += 5;
}
```

**Deep Sleep Confirmation**: 3+ consecutive zeros (15+ min)

## ✔️ Checksum Algorithm

```dart
int sum = 0;
for (int i = 1; i < bytes.length; i++) {
  sum += bytes[i];  // Skip 0xFF header
}
checksum = ((-sum) & 0xFF) ^ 0x3A;
```

## 🎯 Common Pitfalls

| Pitfall | Symptom | Fix |
|---------|---------|-----|
| Missing `isUtc: true` | Timestamps off by timezone | Add `isUtc: true` parameter |
| Not merging | Many 60min sessions | Implement merge algorithm |
| Wrong checksum | Command fails | Use SDK formula: `((-sum) & 0xFF) ^ 0x3A` |
| Not checking end signal | Parsing error | Check for `[0x00, 0x00, 0x00, 0x00]` |
| Classifying before merge | All sessions = naps | Merge first, then classify |

## 📱 SDK References

### iOS (SleepDataController.m line 245)
```objc
NSTimeZone *tz = [NSTimeZone localTimeZone];
NSInteger offset = [tz secondsFromGMT];
NSDate *date = [NSDate dateWithTimeIntervalSince1970:
                (utcTime - offset)];
```

### Android (HistorySleepActivity.java)
```java
public static String millsToDate(long utc) {
    Date date = new Date(utc);  // UTC milliseconds
    return dateFormat.format(date);  // Local time
}
```

## 🧪 Testing Checklist

- [ ] Send command, receive response
- [ ] Parse timestamp (big-endian)
- [ ] UTC → Local conversion matches timezone
- [ ] Multiple sessions parsed correctly
- [ ] End signal detected
- [ ] Sessions merged when < 30min gap
- [ ] Night sleep classified correctly
- [ ] Deep sleep detected (3+ consecutive 0s)
- [ ] Phases calculated from actions
- [ ] CSV export works

## 🚀 Implementation Steps

### Minimal Working Example (Dart)

```dart
// 1. Send command
List<int> cmd = [0xFF, 0x05, 0x05, 0x02, 0xCF];
await characteristic.write(cmd);

// 2. Receive response
List<int> response = await characteristic.read();

// 3. Parse session
int offset = 4;  // Skip header
int utcSeconds = (response[offset] << 24) |
                 (response[offset + 1] << 16) |
                 (response[offset + 2] << 8) |
                 response[offset + 3];

// 4. Convert UTC
DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
  utcSeconds * 1000,
  isUtc: true,
).toLocal();

// 5. Read duration
int duration = (response[offset + 4] << 8) |
               response[offset + 5];

// 6. Read actions
List<int> actions = response.sublist(offset + 6, offset + 60);

// 7. Create session
Map<String, dynamic> session = {
  'timestamp': timestamp,
  'durationMinutes': duration,
  'actions': actions,
};

print('Sleep: $timestamp, ${duration}min');
```

## 📚 File Reference

```
MY_SLEEP/
├── README.md                              ← Master guide (start here)
├── QUICK_REFERENCE.md                     ← This file
├── documentation/
│   └── SLEEP_DATA_EXTRACTION_GUIDE.md    ← Complete documentation
├── code_examples/
│   ├── sleep_parser_0x05.dart            ← Parse 0x05 format
│   ├── session_merger.dart                ← Merge algorithm
│   ├── sleep_classifier.dart              ← Type classification
│   ├── sleep_phases_calculator.dart       ← Deep/light/awake
│   ├── utc_converter.dart                 ← UTC utilities
│   └── official_commands.dart             ← BLE commands
└── sdk_references/
    ├── android_sleep_parser.java          ← Android SDK code
    ├── ios_sleep_parser.m                 ← iOS SDK code
    ├── ios_utc_converter.m                ← iOS UTC conversion
    └── checksum_algorithm.md              ← Checksum explained
```

## 🔍 Debugging Commands

```dart
// Print hex bytes
print(bytes.map((b) => 
  '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}'
).join(' '));

// Verify checksum
bool valid = verifyChecksum(response);
print('Checksum valid: $valid');

// Show timezone offset
int offset = DateTime.now().timeZoneOffset.inHours;
print('Timezone: UTC${offset >= 0 ? '+' : ''}$offset');

// Compare UTC vs Local
DateTime utc = DateTime.fromMillisecondsSinceEpoch(ts * 1000, isUtc: true);
DateTime local = utc.toLocal();
print('UTC:   $utc');
print('Local: $local');
```

## ⚡ Performance Tips

1. **Buffer sessions**: Parse all sessions, then merge once
2. **Cache merged results**: Don't re-merge on every display
3. **Limit history**: Keep last 30 days only
4. **Use indexes**: Index by date for fast lookup
5. **Batch CSV export**: Write all rows at once

## 🎓 Key Concepts

1. **UTC Timestamp**: Device always sends UTC, must convert to local
2. **Big-Endian**: Multi-byte values are most significant byte first
3. **Session Merge**: Required because device sends 60min chunks
4. **Action Index**: Movement indicator, not sleep stage directly
5. **Deep Sleep**: Only confirmed with 3+ consecutive zeros
6. **Night vs Nap**: Based on start time and duration
7. **Checksum**: Error detection, not encryption

## 💡 Pro Tips

- **Test with emulator first**: Use sample packets before real device
- **Log everything**: Save raw bytes for debugging
- **Compare with SDK**: iOS/Android SDKs are reference implementations
- **Handle timezones**: Test across different timezones
- **Validate data**: Check duration = actions.length * 5
- **Error recovery**: Retry command if checksum fails

## 🆘 Common Errors

**"Timestamps are wrong by 1 hour"**
→ Missing `isUtc: true` in DateTime conversion

**"All sessions are 60 minutes"**
→ Not merging consecutive sessions

**"No deep sleep detected"**
→ Check for 3+ consecutive action index = 0

**"Device returns empty data"**
→ No sleep recorded, or time not synchronized

**"Checksum verification fails"**
→ Check formula: `((-sum) & 0xFF) ^ 0x3A`

**"Classification shows all naps"**
→ Classify AFTER merging, not before

---

## 🔗 Quick Links

- Full Guide: `documentation/SLEEP_DATA_EXTRACTION_GUIDE.md`
- Parser Code: `code_examples/sleep_parser_0x05.dart`
- UTC Conversion: `code_examples/utc_converter.dart`
- SDK Reference: `sdk_references/ios_utc_converter.m`
- Checksum: `sdk_references/checksum_algorithm.md`

---

**Last Updated**: November 5, 2025
**Protocol Version**: CL837 SDK v3.0.4 (0x05 format)
**Compatibility**: iOS SDK, Android SDK, Flutter

**Need help?** Check the full documentation or SDK reference files.
