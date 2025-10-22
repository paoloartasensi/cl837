# Advanced Health Dashboard - Troubleshooting Guide

## 🔍 Debug Logging Added

### Log Patterns to Watch

When opening the Advanced Health Dashboard, you should see these logs in sequence:

```
📊 DASHBOARD: Setting up listeners...
📊 DASHBOARD: Listeners setup complete
📊 DASHBOARD: Loading initial data...
📊 DASHBOARD: Initial data load complete
```

### Data Reception Logs

#### Heart Rate (Real-time)
```
📊 DASHBOARD: Received HR: 70 BPM
```
- Appears when device sends HR data
- Should update every 1-2 seconds when connected

#### Sport Health Data (HRV, VO2Max, etc.)
```
📊 DASHBOARD: Received Sport Health data: SportHealthData(...)
```
- Appears when device sends 0x13 command response
- Contains: VO2Max, Respiratory Rate, Stress, HRV (TP, LF, HF)
- Frequency: Varies (device-dependent, typically every 10-60 seconds)

#### Sleep History
```
📊 DASHBOARD: Received X sleep sessions
📊 DASHBOARD: Calculating sleep quality from X sessions...
📊 DASHBOARD: Found X main sleep sessions
📊 DASHBOARD: Sleep quality calculated: SleepQuality(score: X%, grade: Y, ...)
```
- Appears after downloading sleep data
- "Main sleep" = sessions ≥3h during night hours (18:00-10:00)

#### Recovery Score
```
📊 DASHBOARD: Calculating recovery score...
📊 DASHBOARD: Recovery score: 75.0% (Ready)
```
- Calculated when HRV + Sleep + RHR data available
- Triggered by new Sport Health or Sleep data

---

## 🐛 Common Issues & Solutions

### Issue 1: Dashboard Shows "Calculating recovery..."
**Symptoms**: Loading screen never disappears

**Possible Causes**:
1. No sleep data downloaded
2. No Sport Health data received
3. Sleep sessions don't qualify as "main sleep"

**Check Logs For**:
```
📊 DASHBOARD: Received 0 sleep sessions
// OR
📊 DASHBOARD: No sleep history available
// OR
📊 DASHBOARD: No qualifying main sleep sessions found
```

**Solutions**:
1. Go back to home screen
2. Tap "Sleep History ⭐" button
3. Wait for download to complete
4. Return to dashboard

**Why**: Dashboard needs historical data to calculate recovery

---

### Issue 2: Recovery Tab Shows "Download Sleep Data"
**Symptoms**: Button prompt instead of recovery score

**Cause**: `_recoveryScore` is null

**Check Logs For**:
```
📊 DASHBOARD: Calculating recovery score...
// But no follow-up "Recovery score: X%"
```

**Possible Reasons**:
- No sleep data: Check for "Received 0 sleep sessions"
- No HRV data: Check for "Received Sport Health data"
- No qualifying sleep: Check for "No qualifying main sleep sessions"

**Solutions**:
1. Download sleep data first
2. Wait for Sport Health data (device sends automatically)
3. Check sleep session times (must be night hours)

---

### Issue 3: Sleep Tab Shows "No data available"
**Symptoms**: Sleep tab empty

**Cause**: `_lastSleepQuality` is null

**Check Logs For**:
```
📊 DASHBOARD: Found 0 main sleep sessions
```

**Reasons**:
1. **No sleep data downloaded**: Total sessions = 0
2. **Sessions too short**: All sessions <3 hours (36 x 5min blocks)
3. **Wrong time of day**: All sessions during daytime (10:00-18:00)

**Check Session Details**:
```dart
// In logs from unified_home_screen.dart:
🏠 HOME SCREEN: Received X sleep sessions from stream
```

**Solutions**:
1. Download sleep data if X = 0
2. If X > 0 but no main sleep:
   - Sessions might be naps (too short)
   - Check timestamp hours in CSV export
   - Verify UTC timezone conversion is correct

---

### Issue 4: HRV Tab Shows "Waiting for HRV data..."
**Symptoms**: No HRV metrics displayed

**Cause**: Device hasn't sent Sport Health data yet

**Check Logs For**:
```
// No log matching:
📊 DASHBOARD: Received Sport Health data: ...
```

**Reasons**:
1. Device calculates HRV only during rest/sleep
2. Data not sent yet (can take 10-60 seconds)
3. Device in active mode (no HRV calculation during activity)

**Solutions**:
1. **Wait**: Device sends automatically when ready
2. **Rest mode**: Keep device still for 1-2 minutes
3. **Check connection**: Ensure device is still connected
4. **Restart**: Close and reopen dashboard

**Note**: HRV requires stable conditions. Walking/moving prevents measurement.

---

### Issue 5: Respiratory Tab Empty
**Symptoms**: Shows "Waiting for respiratory data..."

**Same as Issue 4** - Respiratory rate is part of Sport Health data (0x13 command)

---

### Issue 6: Dashboard Shows Old Data
**Symptoms**: Metrics don't update

**Check**:
1. Device still connected? (Check home screen connection status)
2. Streams active? Look for new logs with timestamps

**Solutions**:
1. Go back to home screen
2. Check "Connected" status
3. If disconnected, reconnect device
4. Reopen dashboard

---

## 📊 Expected Data Flow

### First Time Opening Dashboard

```
User Opens Dashboard
   ↓
📊 Setting up listeners (3 streams)
   ↓
📊 Loading initial data...
   ↓
Request Sleep History (force=false, uses cache if available)
   ↓
📊 Initial data load complete
   ↓
Wait for streams to receive data:
   ├─ HR Stream (immediate if connected)
   ├─ Sport Health Stream (10-60s delay)
   └─ Sleep History Stream (immediate if downloaded)
   ↓
Calculate Sleep Quality (if sessions available)
   ↓
Calculate Recovery Score (if HRV + Sleep + RHR available)
   ↓
Display Dashboard Tabs
```

### Timeline Expectations

| Event | Expected Time | Log Indicator |
|-------|--------------|---------------|
| Dashboard opens | Immediate | "Setting up listeners" |
| HR data appears | 1-2 seconds | "Received HR: X BPM" |
| Sleep data loads | 0-5 seconds | "Received X sleep sessions" |
| Sleep quality calculated | <1 second | "Sleep quality calculated" |
| Sport Health arrives | 10-60 seconds | "Received Sport Health data" |
| Recovery score calculated | <1 second | "Recovery score: X%" |

---

## 🔧 Manual Testing Commands

### Test Sleep Classification
```dart
// Check which sessions qualify as "main sleep"
for (var session in _sleepHistory) {
  bool isNight = session.timestamp.hour >= 18 || session.timestamp.hour <= 10;
  bool isLongEnough = session.count >= 36;
  bool qualifies = isNight && isLongEnough;
  
  print('Session: ${session.timestamp}');
  print('  Hour: ${session.timestamp.hour}');
  print('  Duration: ${session.count * 5}min (${session.count} blocks)');
  print('  Night time: $isNight');
  print('  Long enough: $isLongEnough');
  print('  Qualifies: $qualifies');
}
```

### Test Recovery Calculation
```dart
// Manually trigger calculation
_calculateLastSleepQuality();
_calculateRecoveryScore();

print('Sleep Quality: $_lastSleepQuality');
print('Recovery Score: $_recoveryScore');
```

---

## 🎯 Reduced Logging Configuration

### Current Settings (Less Spam)

```dart
// unified_home_screen.dart
_hrLogCounter % 10 == 0  // Log every 10th HR update

// chileaf_extended_service.dart
_logThrottleInterval = 2000  // Log every 2000 packets
_healthDataThrottleInterval = 500  // Every 500 health packets
_sportsThrottleInterval = 1000  // Every 1000 sports packets
```

### Before (Too Much Spam)
```
💓 HOME SCREEN: HR from service: 69 BPM  <- Every second!
💓 HOME SCREEN: HR from service: 69 BPM
💓 HOME SCREEN: HR from service: 69 BPM
🔄 Processed 4500 packets (batch update)  <- Every 500 packets!
```

### After (Reasonable)
```
💓 HOME SCREEN: HR from service: 69 BPM  <- Every 10 seconds
🔄 Processed 4000 packets (batch update)  <- Every 2000 packets
```

---

## 📱 UI States

### State 1: Loading
```
Screen: Center spinner
Logs: "Loading initial data..."
Condition: _isLoading = true
```

### State 2: No Data (Recovery Tab)
```
Screen: "Calculating recovery..." + Download button
Logs: "_recoveryScore == null"
Condition: No recovery score calculated yet
```

### State 3: Data Available (Recovery Tab)
```
Screen: Large recovery score display with zone
Logs: "Recovery score: X% (Zone)"
Condition: _recoveryScore != null
```

### State 4: No Data (Sleep Tab)
```
Screen: "No sleep data available" + Download button
Logs: "_lastSleepQuality == null"
Condition: No qualifying sleep sessions
```

### State 5: Data Available (Sleep Tab)
```
Screen: Sleep grade + quality breakdown
Logs: "Sleep quality calculated: ..."
Condition: _lastSleepQuality != null
```

---

## 🧪 Testing Checklist

### Basic Flow
- [ ] Dashboard opens without errors
- [ ] Loading indicator appears briefly
- [ ] Tabs are visible (4 tabs)
- [ ] Can switch between tabs

### With Sleep Data
- [ ] Download sleep data from home
- [ ] Open dashboard
- [ ] Check logs: "Received X sleep sessions" (X > 0)
- [ ] Check logs: "Sleep quality calculated"
- [ ] Sleep tab shows grade (A+ to F)
- [ ] Sleep stages chart displays

### With HRV Data
- [ ] Keep device still/resting
- [ ] Wait 10-60 seconds
- [ ] Check logs: "Received Sport Health data"
- [ ] HRV tab shows LF/HF ratio
- [ ] Recovery tab shows score

### Full Recovery Score
- [ ] Have sleep data downloaded
- [ ] Have HRV data received
- [ ] Recovery tab shows percentage
- [ ] Color zone matches score (🟢🟡🔴)
- [ ] Recommendation text displays
- [ ] Component bars show (HRV, Sleep, RHR)

---

## 🚨 Critical Logs to Monitor

### Success Pattern
```
📊 DASHBOARD: Setting up listeners...
📊 DASHBOARD: Listeners setup complete
📊 DASHBOARD: Loading initial data...
📊 DASHBOARD: Received 5 sleep sessions
📊 DASHBOARD: Calculating sleep quality from 5 sessions...
📊 DASHBOARD: Found 2 main sleep sessions
📊 DASHBOARD: Sleep quality calculated: SleepQuality(score: 82.0%, grade: B+, ...)
📊 DASHBOARD: Received Sport Health data: SportHealthData(lfHfRatio: 1.23, ...)
📊 DASHBOARD: Calculating recovery score...
📊 DASHBOARD: Recovery score: 75.5% (Ready)
```

### Failure Pattern (No Sleep)
```
📊 DASHBOARD: Setting up listeners...
📊 DASHBOARD: Listeners setup complete
📊 DASHBOARD: Loading initial data...
📊 DASHBOARD: Received 0 sleep sessions  <- PROBLEM!
📊 DASHBOARD: No sleep history available
// Recovery never calculated
```

### Failure Pattern (No HRV)
```
📊 DASHBOARD: Setting up listeners...
📊 DASHBOARD: Listeners setup complete
📊 DASHBOARD: Loading initial data...
📊 DASHBOARD: Received 3 sleep sessions
📊 DASHBOARD: Calculating sleep quality from 3 sessions...
📊 DASHBOARD: Sleep quality calculated: ...
// Sport Health data never arrives <- PROBLEM!
// Recovery calculated without HRV (lower score)
```

---

## 💡 Tips for Developers

### Enable Full Logging
```dart
// In chileaf_extended_service.dart
final bool _enableVerboseLogging = true;  // Change to true

// Restart app to see ALL data packets
```

### Force Recalculation
```dart
// Add button in dashboard UI
ElevatedButton(
  onPressed: () {
    _calculateLastSleepQuality();
    _calculateRecoveryScore();
    setState(() {});
  },
  child: Text('Recalculate'),
)
```

### Check Stream Activity
```dart
// In _setupListeners(), add counters
int hrCount = 0, sportCount = 0, sleepCount = 0;

widget.service.realtimeHRStream.listen((hr) {
  hrCount++;
  debugPrint('HR stream update #$hrCount');
});
```

---

**Version**: 1.0  
**Updated**: 2024-10-23  
**Status**: Production Debugging Guide
