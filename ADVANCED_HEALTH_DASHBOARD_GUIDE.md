# Advanced Health Dashboard - Testing Guide

## 🎯 Overview
New Whoop-style recovery analytics dashboard with HRV frequency domain analysis, sleep quality scoring, and recovery recommendations.

## ✨ Features Implemented

### 1. Recovery Score System
- **Algorithm**: Whoop-inspired weighted scoring:
  - HRV Score: 50% (LF/HF ratio based)
  - Sleep Quality: 35% (multi-factor analysis)
  - Resting Heart Rate: 15% (lower is better)
- **Recovery Zones**:
  - 🟢 Green (67-100%): Ready for high-intensity training
  - 🟡 Yellow (34-66%): Moderate activity recommended
  - 🔴 Red (0-33%): Rest and recovery needed
- **Personalized Recommendations**: Based on recovery zone

### 2. Sleep Quality Analysis
- **Grade System**: A+ to F based on 5 factors
- **Components**:
  - Duration Score (25%): Hours slept vs 7-9 optimal
  - Deep Sleep % (25%): Target 15-25% of total
  - Efficiency (20%): Time asleep / time in bed
  - Respiratory Rate (15%): 12-16 BPM optimal
  - HRV During Sleep (15%): LF/HF ratio
- **Sleep Classification**: Distinguishes main sleep from naps
  - Main sleep: ≥3 hours (36 x 5min), night hours (18:00-10:00)
  - Naps: <3 hours or daytime

### 3. HRV Frequency Domain
- **Total Power (TP)**: Overall heart rate variability
- **Low Frequency (LF)**: Sympathetic nervous system (0.04-0.15 Hz)
- **High Frequency (HF)**: Parasympathetic nervous system (0.15-0.4 Hz)
- **LF/HF Ratio**: Autonomic balance indicator
  - 0.5-1.0: 🟢 Excellent recovery (parasympathetic dominance)
  - 1.0-2.0: 🟡 Balanced (normal)
  - 2.0-3.0: 🟠 Elevated stress (sympathetic dominance)
  - >3.0: 🔴 High stress / overtraining risk

### 4. Respiratory Rate Monitoring
- **Current Rate**: Live breath rate from device
- **Context-Based Ranges**:
  - During Sleep: 12-16 BPM (optimal)
  - At Rest: 12-20 BPM (normal)
  - During Activity: 20-30 BPM (elevated)
- **Stress Correlation**: High respiratory rate with high LF/HF = stress

### 5. Four Dashboard Tabs

#### Tab 1: Recovery
- Large recovery score display with zone indicator
- Component breakdown (HRV, Sleep, RHR)
- Current metrics grid (VO2Max, Breath Rate, Emotion, Stress, Stamina, HR)
- Color-coded based on recovery zone

#### Tab 2: Sleep
- Sleep quality grade (A+ to F)
- Overall sleep score percentage
- Duration breakdown (Deep, Light, Awake)
- Sleep efficiency metric
- Visual sleep stages chart

#### Tab 3: HRV
- LF/HF ratio display with status
- HRV frequency components:
  - Total Power (TP)
  - Low Frequency (LF) - sympathetic
  - High Frequency (HF) - parasympathetic
- Interpretation guide with ranges

#### Tab 4: Respiratory
- Current breath rate
- Context-based ranges (sleep, rest, activity)
- Related metrics (Stress %, VO2Max)
- Color-coded status indicator

## 📱 UI Location
**Home Screen** → **⭐ Health Analytics (Recovery & HRV)** button (green)
- Appears when device is connected
- Old "Dashboard (Real-time)" now marked as "OLD" (will be removed)

## 🧪 Testing Steps

### Pre-Test Requirements
1. Device connected via Bluetooth
2. Download sleep history first: **"Sleep History ⭐"** button
3. Wait for device to provide Sport Health data (0x13 command)

### Test Scenario 1: Initial Load
```
1. Connect to CL831 device
2. Download sleep data (wait for completion)
3. Open "⭐ Health Analytics (Recovery & HRV)"
4. Verify: Should show "Calculating recovery..." if no data yet
5. Wait ~10 seconds for Sport Health data (HRV)
6. Check: Recovery score appears with color zone
```

**Expected Results**:
- Recovery tab shows score 0-100%
- Zone indicator (🟢/🟡/🔴) matches score
- Component scores displayed (HRV, Sleep, RHR)
- Current metrics grid populates

### Test Scenario 2: Sleep Quality Tab
```
1. Navigate to "Sleep" tab
2. Verify sleep grade (A+ to F)
3. Check duration breakdown
4. Verify sleep stages chart
```

**Expected Results**:
- Grade matches sleep quality score
- Deep/Light/Awake percentages add to 100%
- Sleep efficiency shows >85% for good sleep
- Chart displays proportional color bars

### Test Scenario 3: HRV Analysis
```
1. Navigate to "HRV" tab
2. Check LF/HF ratio value
3. Verify color coding matches ratio
4. Read component values (TP, LF, HF)
```

**Expected Results**:
- LF/HF ratio: 0.5-3.0 typical range
- Green if <1.5, Yellow if 1.5-2.5, Red if >2.5
- TP, LF, HF values in ms² units
- Interpretation guide matches displayed value

### Test Scenario 4: Respiratory Tab
```
1. Navigate to "Respiratory" tab
2. Check breath rate value
3. Verify related metrics (Stress, VO2Max)
```

**Expected Results**:
- Breath rate: 10-30 BPM typical
- Green if 12-18, Yellow if 10-22, Red otherwise
- Stress % correlates with LF/HF ratio
- VO2Max shows fitness level

### Test Scenario 5: Recovery Zones
```
Test different recovery scenarios:

A. High Recovery (Green Zone):
   - LF/HF < 1.5
   - Sleep quality >80%
   - RHR 50-60 BPM
   Expected: Recovery score >67%, green card, "ready for high-intensity"

B. Medium Recovery (Yellow Zone):
   - LF/HF 1.5-2.5
   - Sleep quality 65-80%
   - RHR 60-70 BPM
   Expected: Recovery score 34-66%, yellow card, "moderate activity"

C. Low Recovery (Red Zone):
   - LF/HF >2.5
   - Sleep quality <65%
   - RHR >70 BPM
   Expected: Recovery score <34%, red card, "rest recommended"
```

## 🐛 Known Issues & Limitations

### Data Availability
1. **HRV requires rest**: Device only calculates HRV during rest/sleep, not during activity
2. **Sleep classification**: Currently uses simple 3-hour threshold, not ML-based
3. **Historical trends**: Not yet implemented (shows only current/last data)

### Edge Cases
1. **No sleep data**: Shows download prompt, cannot calculate recovery
2. **No HRV data**: Waits for Sport Health data, may take 10-60 seconds
3. **Multiple sleep sessions**: Uses most recent night sleep (>3h, night hours)

### Device Limitations
1. **CL831 firmware**: Some users report inconsistent HRV readings
2. **Activity detection**: May misclassify sitting as sleep during day
3. **Respiratory accuracy**: Estimated from movement, not direct measurement

## 🔍 Debugging

### Check Logs for:
```dart
// Sleep data received
'🏠 HOME SCREEN: Received X sleep sessions from stream'

// Sport Health data (HRV)
'📊 Received Sport Health: VO2Max=X, LF/HF=Y'

// Recovery calculation
'💚 Recovery Score calculated: X% (Zone: Y)'
```

### Common Issues

**Issue**: Recovery score not appearing
- **Cause**: Missing sleep or HRV data
- **Fix**: Download sleep first, wait for Sport Health data

**Issue**: Sleep tab shows "No data"
- **Cause**: Sleep history not downloaded
- **Fix**: Go to home, tap "Sleep History ⭐" button

**Issue**: HRV shows "Waiting for data"
- **Cause**: Device hasn't sent Sport Health packet yet
- **Fix**: Wait 10-60 seconds, device sends periodically

**Issue**: LF/HF ratio seems high (>3.0)
- **Cause**: May be stressed, overtrained, or device error
- **Fix**: Verify with multiple readings, rest and retest

## 📊 Data Flow

```
1. Sleep Download (0x05 command)
   ↓
   SleepHistoryEntry list
   ↓
   SleepQuality.fromSleepSession()
   ↓
   Sleep component score (35%)

2. Sport Health (0x13 command - automatic)
   ↓
   SportHealthData with HRV (TP, LF, HF)
   ↓
   LF/HF ratio calculation
   ↓
   HRV component score (50%)

3. Heart Rate (realtime stream)
   ↓
   Current HR value
   ↓
   RHR component score (15%)

4. RecoveryScore.calculate()
   ↓
   Weighted average: HRV*0.5 + Sleep*0.35 + RHR*0.15
   ↓
   Recovery zone assignment (Green/Yellow/Red)
   ↓
   UI display with recommendations
```

## 🎨 UI Design Philosophy

### Color Coding
- **Green**: Optimal/Excellent (recovery, sleep, HRV)
- **Yellow**: Moderate/Warning (borderline values)
- **Red**: Poor/Critical (rest needed)
- **Grey**: Deprecated/Old features

### Metric Presentation
- **Large numbers**: Primary metrics (recovery score, LF/HF ratio)
- **Breakdown bars**: Component contributions (HRV 50%, Sleep 35%, RHR 15%)
- **Grid cards**: Related metrics (VO2Max, Stress, etc.)
- **Tabs**: Organized by category (Recovery, Sleep, HRV, Respiratory)

### Whoop-Inspired Elements
- Recovery zones with emojis (🟢🟡🔴)
- Personalized recommendations
- Grade system for sleep (A+ to F)
- Focus on autonomic balance (LF/HF ratio)
- Weighted scoring approach

## 📝 Next Steps (Future Enhancements)

1. **Historical Trends**
   - 7-day recovery trend chart
   - Sleep quality over time
   - HRV baseline establishment

2. **ML Sleep Classification**
   - Circadian rhythm learning
   - Multi-sensor fusion (HR, movement, respiratory)
   - Automatic main sleep vs nap detection

3. **Training Load Integration**
   - Combine recovery with training intensity
   - Adaptive training recommendations
   - Overtraining risk prediction

4. **Notifications**
   - Low recovery alerts
   - Optimal training window notifications
   - Sleep quality feedback

5. **Export & Sharing**
   - CSV export with all metrics
   - PDF recovery reports
   - Integration with fitness apps

## 🔗 Related Files

- `lib/screens/advanced_health_dashboard.dart` - Main UI
- `lib/models/recovery_score.dart` - Scoring algorithms
- `lib/models/sport_health_data.dart` - HRV data model
- `lib/models/historical_data.dart` - Sleep data model
- `lib/chileaf_extended_service.dart` - BLE communication
- `lib/screens/unified_home_screen.dart` - Entry point

## 📚 References

- Whoop Recovery Algorithm: HRV + Sleep + RHR weighted scoring
- Garmin Body Battery: Similar approach with different weights
- Fitbit Daily Readiness: Sleep + Activity + HRV
- Apple Watch Recovery: HRV + Sleep + Activity variance
- HRV Standards: Task Force (1996) HRV frequency domain analysis

---

**Version**: 1.0.0  
**Date**: 2024  
**Status**: ✅ Ready for testing  
**Branch**: sleep_scarica_bene
