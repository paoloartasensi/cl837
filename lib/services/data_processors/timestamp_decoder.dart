/// Decoded timestamp result with multiple interpretations
class TimestampResult {
  final int rawValue;
  final List<int> rawBytes;
  final Map<String, DateTime?> interpretations;
  final String? bestGuess;
  final String? reasoning;
  
  const TimestampResult({
    required this.rawValue,
    required this.rawBytes,
    required this.interpretations,
    this.bestGuess,
    this.reasoning,
  });
}

/// Advanced timestamp decoder for CL837 device following official Chileaf BLE Protocol v0.6
/// Handles multiple timestamp interpretations to find the correct format
class TimestampDecoder {
  /// Base dates for different interpretation methods
  static final DateTime epoch1970 = DateTime(1970, 1, 1);
  static final DateTime epoch2000 = DateTime(2000, 1, 1);
  static final DateTime epoch2020 = DateTime(2020, 1, 1);
  static final DateTime epoch2024 = DateTime(2024, 1, 1);
  
  /// Current date for validation
  static final DateTime now = DateTime.now();
  
  /// Decode 4-byte timestamp from raw data using multiple methods
  static TimestampResult decodeTimestamp(List<int> bytes) {
    if (bytes.length != 4) {
      throw ArgumentError('Timestamp must be exactly 4 bytes');
    }
    
    // Little-endian conversion (as per Chileaf protocol)
    int rawValue = bytes[0] | (bytes[1] << 8) | (bytes[2] << 16) | (bytes[3] << 24);
    
    Map<String, DateTime?> interpretations = {};
    
    // Method 1: Standard Unix timestamp (seconds since 1970-01-01)
    try {
      DateTime unixTime = DateTime.fromMillisecondsSinceEpoch(rawValue * 1000);
      if (_isReasonableDate(unixTime)) {
        interpretations['Unix (1970)'] = unixTime;
      }
    } catch (e) {
      interpretations['Unix (1970)'] = null;
    }
    
    // Method 2: Milliseconds since 1970-01-01
    try {
      DateTime unixMillis = DateTime.fromMillisecondsSinceEpoch(rawValue);
      if (_isReasonableDate(unixMillis)) {
        interpretations['Unix Millis (1970)'] = unixMillis;
      }
    } catch (e) {
      interpretations['Unix Millis (1970)'] = null;
    }
    
    // Method 3: Seconds since 2000-01-01
    try {
      DateTime epoch2000Time = epoch2000.add(Duration(seconds: rawValue));
      if (_isReasonableDate(epoch2000Time)) {
        interpretations['Epoch 2000'] = epoch2000Time;
      }
    } catch (e) {
      interpretations['Epoch 2000'] = null;
    }
    
    // Method 4: Seconds since 2020-01-01 (common for fitness devices)
    try {
      DateTime epoch2020Time = epoch2020.add(Duration(seconds: rawValue));
      if (_isReasonableDate(epoch2020Time)) {
        interpretations['Epoch 2020'] = epoch2020Time;
      }
    } catch (e) {
      interpretations['Epoch 2020'] = null;
    }
    
    // Method 5: Minutes since 2020-01-01
    try {
      DateTime minutes2020Time = epoch2020.add(Duration(minutes: rawValue));
      if (_isReasonableDate(minutes2020Time)) {
        interpretations['Minutes 2020'] = minutes2020Time;
      }
    } catch (e) {
      interpretations['Minutes 2020'] = null;
    }
    
    // Method 6: Only use lower 16 bits as offset from 2020-01-01
    try {
      int lowerBits = rawValue & 0xFFFF;
      DateTime lower16Time = epoch2020.add(Duration(seconds: lowerBits));
      if (_isReasonableDate(lower16Time)) {
        interpretations['Lower 16-bit (2020)'] = lower16Time;
      }
    } catch (e) {
      interpretations['Lower 16-bit (2020)'] = null;
    }
    
    // Method 7: Use only bytes 2-3 as timestamp
    try {
      int byte23Value = bytes[2] | (bytes[3] << 8);
      DateTime byte23Time = epoch2020.add(Duration(seconds: byte23Value));
      if (_isReasonableDate(byte23Time)) {
        interpretations['Bytes 2-3 (2020)'] = byte23Time;
      }
    } catch (e) {
      interpretations['Bytes 2-3 (2020)'] = null;
    }
    
    // Method 8: Custom Chileaf format analysis
    // Pattern observed: 0x68B7xxxx or 0x68B8xxxx
    if ((rawValue & 0xFFFF0000) == 0x68B70000 || (rawValue & 0xFFFF0000) == 0x68B80000) {
      try {
        // Extract variable part (lower 16 bits)
        int variablePart = rawValue & 0xFFFF;
        
        // Try as offset from different base dates
        DateTime chileafTime1 = epoch2020.add(Duration(minutes: variablePart));
        if (_isReasonableDate(chileafTime1)) {
          interpretations['Chileaf Format (min)'] = chileafTime1;
        }
        
        DateTime chileafTime2 = epoch2020.add(Duration(seconds: variablePart));
        if (_isReasonableDate(chileafTime2)) {
          interpretations['Chileaf Format (sec)'] = chileafTime2;
        }
        
        DateTime chileafTime3 = epoch2020.add(Duration(hours: variablePart));
        if (_isReasonableDate(chileafTime3)) {
          interpretations['Chileaf Format (hr)'] = chileafTime3;
        }
      } catch (e) {
        // Ignore errors
      }
    }
    
    // Determine best guess
    String? bestGuess;
    String? reasoning;
    
    // Filter valid interpretations
    Map<String, DateTime> validInterpretations = {};
    for (var entry in interpretations.entries) {
      if (entry.value != null) {
        validInterpretations[entry.key] = entry.value!;
      }
    }
    
    if (validInterpretations.isNotEmpty) {
      // Prefer interpretations closer to current time
      var sortedByCloseness = validInterpretations.entries.toList()
        ..sort((a, b) {
          int diffA = (a.value.millisecondsSinceEpoch - now.millisecondsSinceEpoch).abs();
          int diffB = (b.value.millisecondsSinceEpoch - now.millisecondsSinceEpoch).abs();
          return diffA.compareTo(diffB);
        });
      
      bestGuess = sortedByCloseness.first.key;
      reasoning = 'Closest to current time (${_formatTimeDifference(sortedByCloseness.first.value)})';
    }
    
    return TimestampResult(
      rawValue: rawValue,
      rawBytes: bytes,
      interpretations: interpretations,
      bestGuess: bestGuess,
      reasoning: reasoning,
    );
  }
  
  /// Check if a date is reasonable (between 2010 and 2035)
  static bool _isReasonableDate(DateTime date) {
    return date.year >= 2010 && date.year <= 2035;
  }
  
  /// Format time difference from now
  static String _formatTimeDifference(DateTime date) {
    Duration diff = now.difference(date);
    int days = diff.inDays.abs();
    
    if (days == 0) {
      return 'today';
    } else if (days < 30) {
      return '$days days ${diff.isNegative ? 'in future' : 'ago'}';
    } else if (days < 365) {
      int months = (days / 30).round();
      return '$months months ${diff.isNegative ? 'in future' : 'ago'}';
    } else {
      int years = (days / 365).round();
      return '$years years ${diff.isNegative ? 'in future' : 'ago'}';
    }
  }
  
  /// Analyze multiple timestamps to find patterns
  static Map<String, dynamic> analyzeTimestampPattern(List<List<int>> timestampBytes) {
    if (timestampBytes.isEmpty) return {};
    
    List<TimestampResult> results = timestampBytes.map(decodeTimestamp).toList();
    
    // Count which interpretation method works for most timestamps
    Map<String, int> methodCounts = {};
    Map<String, List<DateTime>> methodDates = {};
    
    for (var result in results) {
      for (var entry in result.interpretations.entries) {
        if (entry.value != null) {
          methodCounts[entry.key] = (methodCounts[entry.key] ?? 0) + 1;
          methodDates.putIfAbsent(entry.key, () => []).add(entry.value!);
        }
      }
    }
    
    // Find most successful method
    String? bestMethod;
    int maxCount = 0;
    for (var entry in methodCounts.entries) {
      if (entry.value > maxCount) {
        maxCount = entry.value;
        bestMethod = entry.key;
      }
    }
    
    // Analyze chronological order for best method
    bool isChronological = false;
    if (bestMethod != null && methodDates[bestMethod] != null) {
      var dates = methodDates[bestMethod]!;
      if (dates.length > 1) {
        isChronological = true;
        for (int i = 1; i < dates.length; i++) {
          if (dates[i].isBefore(dates[i-1])) {
            isChronological = false;
            break;
          }
        }
      }
    }
    
    return {
      'totalTimestamps': timestampBytes.length,
      'methodSuccessRates': methodCounts,
      'bestMethod': bestMethod,
      'bestMethodCount': maxCount,
      'isChronological': isChronological,
      'methodDates': methodDates,
      'rawPattern': _analyzeRawPattern(timestampBytes),
    };
  }
  
  /// Analyze raw byte patterns
  static Map<String, dynamic> _analyzeRawPattern(List<List<int>> timestampBytes) {
    if (timestampBytes.isEmpty) return {};
    
    // Check for common prefixes
    Map<int, int> byte0Counts = {};
    Map<int, int> byte1Counts = {};
    Map<String, int> prefixCounts = {};
    
    for (var bytes in timestampBytes) {
      byte0Counts[bytes[0]] = (byte0Counts[bytes[0]] ?? 0) + 1;
      byte1Counts[bytes[1]] = (byte1Counts[bytes[1]] ?? 0) + 1;
      
      String prefix = '0x${bytes[0].toRadixString(16).padLeft(2, '0')}${bytes[1].toRadixString(16).padLeft(2, '0')}';
      prefixCounts[prefix] = (prefixCounts[prefix] ?? 0) + 1;
    }
    
    return {
      'byte0Distribution': byte0Counts,
      'byte1Distribution': byte1Counts,
      'prefixDistribution': prefixCounts,
      'hasConstantPrefix': prefixCounts.length == 1,
      'mostCommonPrefix': prefixCounts.entries.reduce((a, b) => a.value > b.value ? a : b).key,
    };
  }
}
