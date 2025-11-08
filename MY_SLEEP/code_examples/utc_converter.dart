/// UTC Timestamp Converter
/// 
/// Utilities for converting CL837 UTC timestamps to local time
/// CRITICAL: Device sends UTC seconds, must be converted to local timezone
/// 
/// Based on iOS SDK: SleepDataController.m line 245
/// Based on Android SDK: HistorySleepActivity.java millsToDate()
/// 
/// Author: Extracted from lib/services/utc_converter.dart
library;

/// Converts CL837 UTC seconds to local DateTime
/// 
/// CRITICAL IMPLEMENTATION:
/// 1. Device sends UTC seconds since epoch
/// 2. Must interpret as UTC (isUtc: true)
/// 3. Convert to local timezone (toLocal())
/// 
/// Parameters:
/// - utcSeconds: Timestamp from device (seconds since epoch, UTC)
/// 
/// Returns: DateTime in local timezone
/// 
/// Example:
/// ```dart
/// // Device sends: 1730730000 (UTC seconds)
/// DateTime local = convertUtcSecondsToLocal(1730730000);
/// // Result: 2025-11-04 22:00:00 (if timezone is UTC+1)
/// ```
DateTime convertUtcSecondsToLocal(int utcSeconds) {
  // Convert seconds to milliseconds
  int utcMilliseconds = utcSeconds * 1000;
  
  // CRITICAL: Must use isUtc: true!
  // This tells DateTime that the timestamp is in UTC
  DateTime utcDateTime = DateTime.fromMillisecondsSinceEpoch(
    utcMilliseconds,
    isUtc: true,  // ✅ REQUIRED! Interprets as UTC
  );
  
  // Convert to local timezone
  DateTime localDateTime = utcDateTime.toLocal();
  
  return localDateTime;
}

/// Converts local DateTime back to UTC seconds for device
/// 
/// Use when sending timestamps TO the device
/// 
/// Parameters:
/// - localDateTime: DateTime in local timezone
/// 
/// Returns: UTC seconds since epoch
DateTime localDateTimeToUtcSeconds(DateTime localDateTime) {
  // Convert to UTC
  DateTime utcDateTime = localDateTime.toUtc();
  
  // Get milliseconds since epoch
  int utcMilliseconds = utcDateTime.millisecondsSinceEpoch;
  
  // Convert to seconds
  int utcSeconds = utcMilliseconds ~/ 1000;
  
  return DateTime.fromMillisecondsSinceEpoch(utcSeconds);
}

/// Reads 4-byte big-endian UTC timestamp from bytes
/// 
/// CL837 Protocol:
/// - Bytes are big-endian (most significant byte first)
/// - Represents UTC seconds since epoch
/// 
/// Parameters:
/// - bytes: 4-byte array containing timestamp
/// - offset: Starting position (default: 0)
/// 
/// Returns: DateTime in local timezone
/// 
/// Example:
/// ```dart
/// // Bytes: [0x67, 0x29, 0xF8, 0x50] = 1730730000 seconds
/// List<int> bytes = [0x67, 0x29, 0xF8, 0x50];
/// DateTime timestamp = readBigEndianUtcTimestamp(bytes);
/// ```
DateTime readBigEndianUtcTimestamp(List<int> bytes, {int offset = 0}) {
  if (bytes.length < offset + 4) {
    throw ArgumentError('Need at least 4 bytes for timestamp');
  }
  
  // Read 4 bytes in big-endian order
  int utcSeconds = (bytes[offset] << 24) |
                   (bytes[offset + 1] << 16) |
                   (bytes[offset + 2] << 8) |
                   bytes[offset + 3];
  
  return convertUtcSecondsToLocal(utcSeconds);
}

/// Writes UTC timestamp to 4-byte big-endian array
/// 
/// Parameters:
/// - dateTime: DateTime to convert (any timezone)
/// 
/// Returns: 4-byte array in big-endian format
List<int> writeBigEndianUtcTimestamp(DateTime dateTime) {
  // Convert to UTC
  DateTime utcDateTime = dateTime.toUtc();
  
  // Get seconds since epoch
  int utcSeconds = utcDateTime.millisecondsSinceEpoch ~/ 1000;
  
  // Write as big-endian
  return [
    (utcSeconds >> 24) & 0xFF,
    (utcSeconds >> 16) & 0xFF,
    (utcSeconds >> 8) & 0xFF,
    utcSeconds & 0xFF,
  ];
}

/// Gets current UTC timestamp (seconds)
/// 
/// Use when device expects current time in UTC
int getCurrentUtcSeconds() {
  DateTime now = DateTime.now().toUtc();
  return now.millisecondsSinceEpoch ~/ 1000;
}

/// Formats UTC seconds to human-readable string
/// 
/// Parameters:
/// - utcSeconds: UTC timestamp from device
/// - includeSeconds: Include seconds in output (default: false)
/// 
/// Returns: Formatted string in local timezone
String formatUtcSecondsToLocal(int utcSeconds, {bool includeSeconds = false}) {
  DateTime local = convertUtcSecondsToLocal(utcSeconds);
  
  String year = local.year.toString();
  String month = local.month.toString().padLeft(2, '0');
  String day = local.day.toString().padLeft(2, '0');
  String hour = local.hour.toString().padLeft(2, '0');
  String minute = local.minute.toString().padLeft(2, '0');
  
  String formatted = '$year-$month-$day $hour:$minute';
  
  if (includeSeconds) {
    String second = local.second.toString().padLeft(2, '0');
    formatted += ':$second';
  }
  
  return formatted;
}

/// Calculates timezone offset in hours
/// 
/// Returns: Offset from UTC (e.g., +1, -5)
int getTimezoneOffsetHours() {
  DateTime now = DateTime.now();
  Duration offset = now.timeZoneOffset;
  return offset.inHours;
}

/// Example usage and testing
void main() {
  print('🌍 UTC TIMESTAMP CONVERSION EXAMPLES:');
  print('');
  
  // Example 1: Convert device timestamp
  print('Example 1: Device Timestamp → Local Time');
  int deviceUtc = 1730730000; // From device
  DateTime local = convertUtcSecondsToLocal(deviceUtc);
  print('   Device sent (UTC seconds): $deviceUtc');
  print('   Local time: $local');
  print('   Formatted: ${formatUtcSecondsToLocal(deviceUtc)}');
  print('');
  
  // Example 2: Big-endian reading
  print('Example 2: Read Big-Endian Bytes');
  List<int> bytes = [0x67, 0x29, 0xF8, 0x50]; // = 1730730000
  DateTime fromBytes = readBigEndianUtcTimestamp(bytes);
  print('   Bytes: ${bytes.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ')}');
  print('   Parsed time: $fromBytes');
  print('');
  
  // Example 3: Show timezone offset
  print('Example 3: Timezone Information');
  int offsetHours = getTimezoneOffsetHours();
  print('   Current timezone offset: UTC${offsetHours >= 0 ? '+' : ''}$offsetHours');
  print('   Current UTC time: ${DateTime.now().toUtc()}');
  print('   Current local time: ${DateTime.now()}');
  print('');
  
  // Example 4: Demonstrate the bug (without isUtc: true)
  print('Example 4: WRONG vs CORRECT Conversion');
  int testUtc = 1730730000;
  
  // ❌ WRONG: Interprets as local time
  DateTime wrong = DateTime.fromMillisecondsSinceEpoch(testUtc * 1000);
  print('   ❌ Without isUtc:true: $wrong');
  
  // ✅ CORRECT: Interprets as UTC
  DateTime correct = DateTime.fromMillisecondsSinceEpoch(testUtc * 1000, isUtc: true).toLocal();
  print('   ✅ With isUtc:true: $correct');
  
  Duration difference = correct.difference(wrong);
  print('   Difference: ${difference.inHours} hours (= timezone offset)');
  print('');
  
  // Example 5: Write timestamp back to device
  print('Example 5: Local Time → Device Bytes');
  DateTime now = DateTime.now();
  List<int> timestampBytes = writeBigEndianUtcTimestamp(now);
  print('   Local time: $now');
  print('   Big-endian bytes: ${timestampBytes.map((b) => '0x${b.toRadixString(16).toUpperCase().padLeft(2, '0')}').join(' ')}');
  print('   UTC seconds: ${getCurrentUtcSeconds()}');
}

/// COMMON PITFALLS - MUST READ!
/// 
/// ❌ WRONG - Missing isUtc parameter:
/// ```dart
/// DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(utcTime * 1000);
/// // This interprets utcTime as LOCAL timezone!
/// // Result will be OFF by your timezone offset!
/// ```
/// 
/// ✅ CORRECT - With isUtc parameter:
/// ```dart
/// DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
///   utcTime * 1000,
///   isUtc: true,  // Interprets as UTC
/// ).toLocal();    // Convert to local
/// ```
/// 
/// TIMEZONE IMPACT:
/// - Without isUtc: true, timestamps are WRONG by timezone offset
/// - Example: UTC+1 timezone → 1 hour difference
/// - Device at 22:00 UTC shows as 23:00 local (correct)
/// - But WITHOUT isUtc: true → shows as 23:00 UTC → 00:00 local (WRONG!)
/// 
/// SDK REFERENCES:
/// 
/// iOS (SleepDataController.m line 245):
/// ```objc
/// NSTimeZone *timeZone = [NSTimeZone localTimeZone];
/// NSInteger timeZoneSecond = [timeZone secondsFromGMT];
/// NSDate *date = [NSDate dateWithTimeIntervalSince1970:
///                 (sleepData.mTimeStamp - timeZoneSecond)];
/// ```
/// 
/// Android (HistorySleepActivity.java):
/// ```java
/// public static String millsToDate(long utc) {
///     Date date = new Date(utc);  // UTC milliseconds
///     return dateFormat.format(date);  // Formats in local timezone
/// }
/// ```
/// 
/// TESTING:
/// Run this file with `dart run` to see conversion examples
/// Compare results with iOS/Android SDK implementations
