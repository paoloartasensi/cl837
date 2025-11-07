# 📚 MY_SLEEP - Complete Index

**Quick navigation guide for all files in this knowledge base**

---

## 🎯 Start Here

| File | Purpose | Read First? |
|------|---------|-------------|
| **README.md** | Master guide with overview | ✅ YES |
| **QUICK_REFERENCE.md** | One-page cheat sheet | ✅ YES |
| **KNOWN_ISSUES.md** | Current problems and fixes | ⚠️ IMPORTANT |

---

## 📖 Documentation Files

Located in: `documentation/`

| File | Topic | When to Read |
|------|-------|--------------|
| **SLEEP_DATA_EXTRACTION_GUIDE.md** | Complete implementation guide (900+ lines) | Main reference |
| **SLEEP_DATA_0x31_IMPLEMENTATION.md** | Alternative protocol (NEW format) | Advanced usage |
| **SLEEP_PARSING_OFFICIAL_FIX.md** | Parsing details and fixes | Troubleshooting |
| **SLEEP_TRACKING_COMPARISON.md** | 0x05 vs 0x31 comparison | Choosing protocol |
| **SLEEP_PROTOCOL_0x05_VS_0x31.md** | Technical comparison | Protocol details |
| **SLEEP_ONSET_DETECTION_GUIDE.md** | Advanced sleep detection | Optional features |
| **SLEEP_DATA_TESTING_GUIDE.md** | Testing procedures | Before deployment |
| **SLEEP_CLASSIFIER_IMPLEMENTATION.md** | Classification logic | Understanding types |
| **SLEEP_DOWNLOAD_BUG_FIX.md** | Historical bug fixes | Reference |
| **UTC_TIMEZONE_FIX.md** | UTC conversion explained | CRITICAL - Read! |

---

## 💻 Code Examples

Located in: `code_examples/`

All files are standalone, runnable Dart code with examples.

| File | What It Does | Lines | Key Features |
|------|--------------|-------|--------------|
| **sleep_parser_0x05.dart** | Parse BLE response packets | 170 | UTC conversion, big-endian, end detection |
| **session_merger.dart** | Merge consecutive sessions | 180 | Gap analysis, night period check |
| **sleep_classifier.dart** | Classify sleep types | 170 | Night/nap detection, duration rules |
| **sleep_phases_calculator.dart** | Calculate sleep phases | 280 | Deep/light/awake, quality score |
| **utc_converter.dart** | UTC timestamp utilities | 270 | CRITICAL - timestamp conversion |
| **official_commands.dart** | BLE command builders | 320 | All device commands, checksum |

### How to Run Examples

```bash
# From MY_SLEEP/code_examples/ directory
dart run sleep_parser_0x05.dart
dart run session_merger.dart
dart run sleep_classifier.dart
dart run sleep_phases_calculator.dart
dart run utc_converter.dart
dart run official_commands.dart
```

Each file includes:
- ✅ Complete implementation
- ✅ Detailed comments
- ✅ Example usage
- ✅ Test cases
- ✅ Important notes

---

## 🔍 SDK References

Located in: `sdk_references/`

Official implementation snippets from iOS and Android SDKs.

| File | Language | Source | Purpose |
|------|----------|--------|---------|
| **android_sleep_parser.java** | Java | WearManager.java lines 671-724 | Android SDK implementation |
| **ios_sleep_parser.m** | Objective-C | HeartBLEDevice.m lines 870-910 | iOS SDK implementation |
| **ios_utc_converter.m** | Objective-C | SleepDataController.m line 245 | UTC conversion (CRITICAL) |
| **checksum_algorithm.md** | Markdown | SDK documentation | Checksum calculation |

### Why These Are Important

- ✅ Show OFFICIAL implementations
- ✅ Verify our code matches SDK behavior
- ✅ Reference for edge cases
- ✅ Cross-platform compatibility proof

---

## 🗂️ By Use Case

### I want to implement sleep tracking

**Read:**
1. README.md (overview)
2. QUICK_REFERENCE.md (cheat sheet)
3. documentation/SLEEP_DATA_EXTRACTION_GUIDE.md (full guide)

**Use:**
- code_examples/sleep_parser_0x05.dart
- code_examples/session_merger.dart
- code_examples/sleep_classifier.dart

**Reference:**
- sdk_references/ios_utc_converter.m (UTC conversion)
- KNOWN_ISSUES.md (avoid pitfalls)

---

### I'm debugging timestamp issues

**Read:**
1. KNOWN_ISSUES.md (check if known issue)
2. documentation/UTC_TIMEZONE_FIX.md (UTC explanation)
3. QUICK_REFERENCE.md (common pitfalls)

**Use:**
- code_examples/utc_converter.dart (test conversion)

**Reference:**
- sdk_references/ios_utc_converter.m (official implementation)
- sdk_references/android_sleep_parser.java (millsToDate method)

---

### I'm getting wrong sleep classifications

**Read:**
1. documentation/SLEEP_CLASSIFIER_IMPLEMENTATION.md
2. QUICK_REFERENCE.md (classification rules)

**Use:**
- code_examples/session_merger.dart (check if merging first)
- code_examples/sleep_classifier.dart (test classification)

**Important:** Always merge BEFORE classifying!

---

### I'm calculating sleep phases

**Read:**
1. documentation/SLEEP_DATA_EXTRACTION_GUIDE.md (action indices)
2. QUICK_REFERENCE.md (phase meanings)

**Use:**
- code_examples/sleep_phases_calculator.dart

**Note:** Action index 0 = deep, 1-2 = light, 3+ = awake

---

### I'm building BLE commands

**Read:**
1. QUICK_REFERENCE.md (command list)
2. documentation/SLEEP_DATA_EXTRACTION_GUIDE.md (protocol details)

**Use:**
- code_examples/official_commands.dart

**Reference:**
- sdk_references/checksum_algorithm.md (checksum formula)
- sdk_references/ios_sleep_parser.m (command examples)

---

### I'm comparing protocols (0x05 vs 0x31)

**Read:**
1. documentation/SLEEP_TRACKING_COMPARISON.md
2. documentation/SLEEP_PROTOCOL_0x05_VS_0x31.md
3. documentation/SLEEP_DATA_0x31_IMPLEMENTATION.md

**Recommendation:** Use 0x05 (SDK compatible, proven)

---

### I'm testing my implementation

**Read:**
1. documentation/SLEEP_DATA_TESTING_GUIDE.md
2. QUICK_REFERENCE.md (testing checklist)

**Run:**
- All code examples to verify understanding
- Compare output with SDK implementations

**Check:**
- KNOWN_ISSUES.md (avoid known problems)

---

## 📊 File Statistics

| Category | Files | Total Lines | Avg Length |
|----------|-------|-------------|------------|
| Documentation | 10 | ~5,000+ | 500 lines |
| Code Examples | 6 | ~1,400 | 230 lines |
| SDK References | 4 | ~800 | 200 lines |
| Root Files | 3 | ~500 | 170 lines |
| **TOTAL** | **23** | **~7,700+** | **335 lines** |

---

## 🎓 Learning Path

### Beginner (Never used CL837 before)

1. README.md (overview)
2. QUICK_REFERENCE.md (basics)
3. Run: utc_converter.dart (understand timestamps)
4. Run: sleep_parser_0x05.dart (see parsing)
5. documentation/SLEEP_DATA_EXTRACTION_GUIDE.md (complete guide)

### Intermediate (Implementing in new app)

1. QUICK_REFERENCE.md (cheat sheet)
2. Copy: sleep_parser_0x05.dart → your project
3. Copy: session_merger.dart → your project
4. Copy: sleep_classifier.dart → your project
5. Read: KNOWN_ISSUES.md (avoid mistakes)

### Advanced (Debugging or optimizing)

1. Compare with sdk_references/ (verify correctness)
2. Read: documentation/SLEEP_PROTOCOL_0x05_VS_0x31.md (alternatives)
3. Read: documentation/SLEEP_ONSET_DETECTION_GUIDE.md (advanced features)
4. Review: checksum_algorithm.md (deep dive)

---

## 🔗 Quick Links by Topic

### UTC Timestamp Conversion
- Code: `code_examples/utc_converter.dart`
- Docs: `documentation/UTC_TIMEZONE_FIX.md`
- SDK: `sdk_references/ios_utc_converter.m`
- Quick: `QUICK_REFERENCE.md` (UTC section)

### Session Merging
- Code: `code_examples/session_merger.dart`
- Docs: `documentation/SLEEP_DATA_EXTRACTION_GUIDE.md` (merge section)
- Quick: `QUICK_REFERENCE.md` (merge rules)

### Sleep Classification
- Code: `code_examples/sleep_classifier.dart`
- Docs: `documentation/SLEEP_CLASSIFIER_IMPLEMENTATION.md`
- Quick: `QUICK_REFERENCE.md` (classification rules)

### BLE Commands
- Code: `code_examples/official_commands.dart`
- Docs: `documentation/SLEEP_DATA_EXTRACTION_GUIDE.md` (commands)
- SDK: `sdk_references/ios_sleep_parser.m`
- Quick: `QUICK_REFERENCE.md` (command list)

### Checksum
- Docs: `sdk_references/checksum_algorithm.md`
- Code: `code_examples/official_commands.dart` (calculateChecksum)
- Quick: `QUICK_REFERENCE.md` (checksum section)

### Sleep Phases
- Code: `code_examples/sleep_phases_calculator.dart`
- Docs: `documentation/SLEEP_DATA_EXTRACTION_GUIDE.md` (phases)
- Quick: `QUICK_REFERENCE.md` (action indices)

---

## 📞 Getting Help

### Finding Information

1. **Quick question?** → `QUICK_REFERENCE.md`
2. **Implementation details?** → `documentation/SLEEP_DATA_EXTRACTION_GUIDE.md`
3. **Code examples?** → `code_examples/` directory
4. **SDK comparison?** → `sdk_references/` directory
5. **Known problems?** → `KNOWN_ISSUES.md`

### Search Tips

Search for these terms to find relevant sections:

- "UTC" → timestamp conversion
- "merge" → session merging
- "checksum" → command validation
- "action index" → sleep phases
- "0x05" → legacy protocol
- "0x31" → new protocol
- "night sleep" → classification
- "deep sleep" → phase detection

---

## ✅ Verification Checklist

Before using code in production:

- [ ] Read README.md
- [ ] Read QUICK_REFERENCE.md
- [ ] Read KNOWN_ISSUES.md
- [ ] Run all code examples
- [ ] Verify UTC conversion works
- [ ] Test session merging
- [ ] Check classification logic
- [ ] Validate checksum calculation
- [ ] Compare with SDK references
- [ ] Test with real device

---

## 📅 Last Updated

**Date:** November 5, 2025

**Recent Changes:**
- ✅ Fixed UTC timestamp conversion
- ✅ Created complete MY_SLEEP knowledge base
- ✅ Added 6 code examples (all runnable)
- ✅ Added 4 SDK reference files
- ✅ Documented known device issue
- ✅ Created quick reference guide

**Total Knowledge Base Size:** ~7,700 lines of documentation and code

---

## 🚀 Next Steps

1. **Read** QUICK_REFERENCE.md (5 minutes)
2. **Run** code_examples/utc_converter.dart (verify understanding)
3. **Study** documentation/SLEEP_DATA_EXTRACTION_GUIDE.md (complete guide)
4. **Copy** code examples to your project
5. **Test** with real device
6. **Reference** SDK files when debugging

---

**Happy Coding! 🌙😴**

All code is tested, documented, and ready to transfer to other apps.
