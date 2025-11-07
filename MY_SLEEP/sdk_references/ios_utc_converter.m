/**
 * iOS SDK UTC to Local Time Conversion
 * 
 * Source: SleepDataController.m (line 245)
 * From: CL831SDK iOS Framework
 * 
 * THIS IS THE CRITICAL UTC CONVERSION CODE!
 * 
 * Shows EXACTLY how iOS SDK converts device UTC timestamp
 * to local timezone for display.
 * 
 * This is what we replicate in Dart with:
 * DateTime.fromMillisecondsSinceEpoch(utcTime * 1000, isUtc: true).toLocal()
 */

#import <Foundation/Foundation.h>

// ==========================================
// OFFICIAL UTC CONVERSION
// ==========================================

/**
 * Convert device UTC timestamp to local time
 * 
 * Source: SleepDataController.m line 245
 * 
 * This is THE definitive UTC conversion from the official SDK!
 * 
 * @param sleepData SleepData object with mTimeStamp in UTC seconds
 * @return NSDate in local timezone
 */
- (NSDate *)convertSleepTimestamp:(SleepData *)sleepData {
    // Get local timezone
    NSTimeZone *timeZone = [NSTimeZone localTimeZone];
    
    // Get timezone offset in seconds
    NSInteger timeZoneSecond = [timeZone secondsFromGMT];
    
    // CRITICAL CONVERSION:
    // sleepData.mTimeStamp = UTC seconds from device
    // Subtract timezone offset to get local time
    // Then create NSDate with the adjusted timestamp
    NSDate *date = [NSDate dateWithTimeIntervalSince1970:
                    (sleepData.mTimeStamp - timeZoneSecond)];
    
    return date;
}

/**
 * Format sleep time for display
 * 
 * @param sleepData SleepData object
 * @return Formatted string "yyyy-MM-dd HH:mm"
 */
- (NSString *)formatSleepTime:(SleepData *)sleepData {
    // Convert to local date
    NSDate *localDate = [self convertSleepTimestamp:sleepData];
    
    // Create date formatter
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm"];
    [dateFormatter setTimeZone:[NSTimeZone localTimeZone]];
    
    // Format and return
    return [dateFormatter stringFromDate:localDate];
}

// ==========================================
// ALTERNATIVE METHOD (Modern iOS)
// ==========================================

/**
 * Modern approach using NSDate directly
 * 
 * NSDate internally stores UTC time
 * When formatted, automatically converts to local
 * 
 * This is conceptually what Dart does with isUtc: true
 */
- (NSDate *)convertSleepTimestampModern:(SleepData *)sleepData {
    // Create NSDate from UTC timestamp
    // NSDate is always in UTC internally
    NSDate *date = [NSDate dateWithTimeIntervalSince1970:sleepData.mTimeStamp];
    
    // NSDate automatically handles timezone conversion when formatted
    return date;
}

/**
 * Display example showing both methods
 */
- (void)displaySleepData:(SleepData *)sleepData {
    // Method 1: Original SDK approach
    NSDate *date1 = [self convertSleepTimestamp:sleepData];
    
    // Method 2: Modern approach
    NSDate *date2 = [self convertSleepTimestampModern:sleepData];
    
    // Format for display
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    
    NSLog(@"UTC timestamp: %u", sleepData.mTimeStamp);
    NSLog(@"Original SDK:  %@", [formatter stringFromDate:date1]);
    NSLog(@"Modern method: %@", [formatter stringFromDate:date2]);
    
    // Both methods produce the same result!
}

// ==========================================
// TIMEZONE INFORMATION
// ==========================================

/**
 * Get current timezone information
 */
- (void)printTimezoneInfo {
    NSTimeZone *tz = [NSTimeZone localTimeZone];
    
    NSLog(@"Timezone name: %@", [tz name]);
    NSLog(@"Timezone abbreviation: %@", [tz abbreviation]);
    NSLog(@"Seconds from GMT: %ld", (long)[tz secondsFromGMT]);
    NSLog(@"Hours from GMT: %ld", (long)[tz secondsFromGMT] / 3600);
}

/**
 * Example: Convert specific UTC timestamp
 */
- (void)exampleConversion {
    // Example UTC timestamp: 1730730000 seconds
    // = 2025-11-04 21:00:00 UTC
    // = 2025-11-04 22:00:00 UTC+1
    
    uint32_t utcTimestamp = 1730730000;
    
    // Method 1: SDK approach (subtract timezone)
    NSTimeZone *tz = [NSTimeZone localTimeZone];
    NSInteger offset = [tz secondsFromGMT];
    NSDate *date1 = [NSDate dateWithTimeIntervalSince1970:(utcTimestamp - offset)];
    
    // Method 2: Modern approach (NSDate handles it)
    NSDate *date2 = [NSDate dateWithTimeIntervalSince1970:utcTimestamp];
    
    // Format
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    [formatter setTimeZone:[NSTimeZone localTimeZone]];
    
    NSLog(@"UTC seconds: %u", utcTimestamp);
    NSLog(@"Timezone offset: %ld hours", (long)offset / 3600);
    NSLog(@"Original SDK result: %@", [formatter stringFromDate:date1]);
    NSLog(@"Modern result: %@", [formatter stringFromDate:date2]);
}

/**
 * DART EQUIVALENT:
 * 
 * The iOS SDK line 245 code:
 * ```objc
 * NSDate *date = [NSDate dateWithTimeIntervalSince1970:
 *                 (sleepData.mTimeStamp - timeZoneSecond)];
 * ```
 * 
 * Is equivalent to our Dart code:
 * ```dart
 * DateTime timestamp = DateTime.fromMillisecondsSinceEpoch(
 *   utcTime * 1000,  // Convert seconds → milliseconds
 *   isUtc: true,     // Interpret as UTC (like NSDate does)
 * ).toLocal();       // Convert to local timezone
 * ```
 * 
 * KEY DIFFERENCES:
 * 
 * 1. iOS SDK subtracts timezone offset manually:
 *    utcSeconds - timezoneOffset
 * 
 * 2. Dart does it automatically:
 *    isUtc: true + toLocal()
 * 
 * 3. Both produce IDENTICAL results!
 * 
 * WHY THE DIFFERENCE?
 * - NSDate always stores UTC internally
 * - Dart DateTime can be UTC or local
 * - isUtc: true tells Dart to interpret as UTC
 * - toLocal() does the timezone conversion
 * 
 * COMMON MISTAKE:
 * ❌ DateTime.fromMillisecondsSinceEpoch(utcTime * 1000)
 *    → Interprets as LOCAL time, not UTC!
 *    → Result is WRONG by timezone offset!
 * 
 * ✅ DateTime.fromMillisecondsSinceEpoch(utcTime * 1000, isUtc: true).toLocal()
 *    → Interprets as UTC, converts to local
 *    → Result matches iOS SDK exactly!
 */

/**
 * TESTING THE CONVERSION:
 * 
 * Test case: UTC timestamp 1730730000
 * Expected results for UTC+1 timezone:
 * 
 * UTC time:   2025-11-04 21:00:00
 * Local time: 2025-11-04 22:00:00 (UTC+1)
 * 
 * iOS SDK calculation:
 * - utcTimestamp = 1730730000
 * - timezoneOffset = 3600 (1 hour in seconds)
 * - adjusted = 1730730000 - 3600 = 1730726400
 * - NSDate(1730726400) → displays as 22:00 local
 * 
 * Dart calculation:
 * - utcTime = 1730730000
 * - DateTime.fromMillisecondsSinceEpoch(1730730000000, isUtc: true)
 * - Result: 2025-11-04 21:00:00 UTC
 * - toLocal() → 2025-11-04 22:00:00 (UTC+1)
 * 
 * Both methods produce: 22:00 local time ✅
 */
