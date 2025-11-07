/**
 * iOS SDK Sleep Data Parser
 * 
 * Source: HeartBLEDevice.m (lines 870-910)
 * From: CL831SDK iOS Framework
 * 
 * This is the OFFICIAL implementation from the iOS SDK
 * Shows how to parse 0x05 sleep data format in Objective-C
 * 
 * CRITICAL: Notice the big-endian byte conversion
 * - Uses bit shifting to combine bytes
 * - Converts 4 bytes to uint32_t timestamp
 */

#import <Foundation/Foundation.h>

// ==========================================
// SLEEP DATA PARSING (0x05 Format)
// ==========================================

/**
 * Parse sleep data response from device
 * 
 * @param data Complete response NSData from device
 * @return Array of SleepData objects
 */
- (NSArray<SleepData *> *)parseSleepData:(NSData *)data {
    NSMutableArray<SleepData *> *sleepArray = [NSMutableArray array];
    
    const uint8_t *bytes = (const uint8_t *)[data bytes];
    NSUInteger length = [data length];
    
    // Skip header: [0xFF][len][0x05][0x03]
    NSUInteger offset = 4;
    
    // Parse 60-byte sleep sessions
    while (offset + 60 <= length - 1) { // -1 for checksum
        SleepData *sleepData = [[SleepData alloc] init];
        
        // Read UTC timestamp (4 bytes, big-endian)
        uint32_t utcTime = (bytes[offset] << 24) |
                           (bytes[offset + 1] << 16) |
                           (bytes[offset + 2] << 8) |
                           bytes[offset + 3];
        offset += 4;
        
        // Read duration (2 bytes, big-endian)
        uint16_t duration = (bytes[offset] << 8) |
                            bytes[offset + 1];
        offset += 2;
        
        // Read 54 action indices
        NSMutableArray<NSNumber *> *actions = [NSMutableArray array];
        for (int i = 0; i < 54; i++) {
            [actions addObject:@(bytes[offset++])];
        }
        
        // Store data
        sleepData.mTimeStamp = utcTime;
        sleepData.mDuration = duration;
        sleepData.mActions = [actions copy];
        
        [sleepArray addObject:sleepData];
    }
    
    return [sleepArray copy];
}

// ==========================================
// SLEEP DATA MODEL
// ==========================================

@interface SleepData : NSObject

@property (nonatomic, assign) uint32_t mTimeStamp;  // UTC seconds
@property (nonatomic, assign) uint16_t mDuration;   // Duration in minutes
@property (nonatomic, strong) NSArray<NSNumber *> *mActions;  // Action indices

@end

@implementation SleepData
@end

// ==========================================
// CHECKSUM CALCULATION
// ==========================================

/**
 * Calculate CL837 checksum
 * 
 * Source: HeartBLEDevice.m line 156
 * 
 * @param bytes Command bytes (excluding checksum)
 * @param length Number of bytes
 * @return Checksum byte
 */
- (uint8_t)calculateChecksum:(const uint8_t *)bytes length:(NSUInteger)length {
    int sum = 0;
    
    // Sum all bytes except 0xFF header
    for (NSUInteger i = 1; i < length; i++) {
        sum += bytes[i];
    }
    
    // Apply SDK formula: ((-sum) & 0xFF) ^ 0x3A
    uint8_t checksum = ((-sum) & 0xFF) ^ 0x3A;
    
    return checksum;
}

// ==========================================
// BLE COMMAND BUILDERS
// ==========================================

/**
 * Build sleep data read command (0x05)
 * 
 * Source: HeartBLEDevice.m line 870
 * 
 * @return NSData with complete command
 */
- (NSData *)buildSleepDataCommand {
    uint8_t cmd[] = {
        0xFF,  // Header
        0x05,  // Length
        0x05,  // Command ID
        0x02,  // Subcommand (read)
        0x00   // Checksum (placeholder)
    };
    
    // Calculate checksum
    cmd[4] = [self calculateChecksum:cmd length:4];
    
    return [NSData dataWithBytes:cmd length:5];
}

/**
 * Build set time command
 * 
 * Source: HeartBLEDevice.m line 245
 * 
 * @param date Local time to set
 * @return NSData with complete command
 */
- (NSData *)buildSetTimeCommand:(NSDate *)date {
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [calendar components:
        (NSCalendarUnitYear | NSCalendarUnitMonth | NSCalendarUnitDay |
         NSCalendarUnitHour | NSCalendarUnitMinute | NSCalendarUnitSecond)
        fromDate:date];
    
    uint8_t cmd[] = {
        0xFF,                           // Header
        0x0C,                           // Length
        0x01,                           // Command ID
        0x01,                           // Subcommand
        (uint8_t)(components.year - 2000),  // Year since 2000
        (uint8_t)components.month,
        (uint8_t)components.day,
        (uint8_t)components.hour,
        (uint8_t)components.minute,
        (uint8_t)components.second,
        0x00,                           // Reserved
        0x00                            // Checksum (placeholder)
    };
    
    // Calculate checksum
    cmd[11] = [self calculateChecksum:cmd length:11];
    
    return [NSData dataWithBytes:cmd length:12];
}

/**
 * KEY TAKEAWAYS:
 * 
 * 1. BIG-ENDIAN CONVERSION:
 *    - Combine 4 bytes using bit shifts
 *    - Most significant byte first
 *    - uint32_t for 4 bytes, uint16_t for 2 bytes
 * 
 * 2. CHECKSUM FORMULA:
 *    - Sum bytes (skip 0xFF header)
 *    - Apply: ((-sum) & 0xFF) ^ 0x3A
 *    - Same algorithm as Android SDK
 * 
 * 3. COMMAND STRUCTURE:
 *    - Header: 0xFF
 *    - Length: bytes after header (excluding header and checksum)
 *    - Command ID + Subcommand
 *    - Checksum at end
 * 
 * 4. ACTION INDICES:
 *    - 54 bytes per session
 *    - 0 = Deep sleep
 *    - 1-2 = Light sleep
 *    - 3+ = Awake
 * 
 * 5. TIMESTAMP:
 *    - Device sends UTC seconds
 *    - Must convert to local timezone for display
 *    - See SleepDataController.m for conversion
 */
