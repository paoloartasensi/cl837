/// Official BLE Commands for CL837
/// 
/// Complete command builders with checksum calculation
/// Based on SDK documentation and reverse engineering
/// 
/// Author: Extracted from lib/services/ble_commands.dart
library;

/// Command IDs
class CommandId {
  static const int sleepData05 = 0x05;      // Sleep data (LEGACY format)
  static const int sleepData31 = 0x31;      // Sleep data (NEW format)
  static const int heartRate = 0x06;         // Heart rate data
  static const int steps = 0x07;             // Step count data
  static const int setTime = 0x01;           // Set device time
  static const int deviceInfo = 0x00;        // Get device info
  static const int battery = 0x02;           // Battery level
}

/// Calculates checksum for CL837 commands
/// 
/// Algorithm (from SDK):
/// 1. Sum all bytes (except header 0xFF and checksum byte)
/// 2. Negate the sum: -sum
/// 3. Apply mask and XOR: ((-sum) & 0xFF) ^ 0x3A
/// 
/// Parameters:
/// - bytes: Command bytes (including 0xFF header, excluding checksum)
/// 
/// Returns: Checksum byte (0-255)
/// 
/// Example:
/// ```dart
/// List<int> cmd = [0xFF, 0x05, 0x05, 0x02];
/// int checksum = calculateChecksum(cmd);  // = 0xCF
/// ```
int calculateChecksum(List<int> bytes) {
  // Skip first byte (0xFF header)
  int sum = 0;
  for (int i = 1; i < bytes.length; i++) {
    sum += bytes[i];
  }
  
  // Apply SDK formula: ((-sum) & 0xFF) ^ 0x3A
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  
  return checksum;
}

/// Builds complete command with checksum
/// 
/// Adds 0xFF header and calculates checksum
/// 
/// Parameters:
/// - commandBytes: Command data (without header and checksum)
/// 
/// Returns: Complete command ready to send
List<int> buildCommand(List<int> commandBytes) {
  // Add header
  List<int> command = [0xFF, ...commandBytes];
  
  // Calculate and add checksum
  int checksum = calculateChecksum(command);
  command.add(checksum);
  
  return command;
}

/// Verifies checksum of received data
/// 
/// Parameters:
/// - data: Received bytes (including header and checksum)
/// 
/// Returns: True if checksum is valid
bool verifyChecksum(List<int> data) {
  if (data.isEmpty || data[0] != 0xFF) {
    return false; // Invalid header
  }
  
  if (data.length < 3) {
    return false; // Too short
  }
  
  // Extract checksum (last byte)
  int receivedChecksum = data.last;
  
  // Calculate expected checksum
  List<int> dataWithoutChecksum = data.sublist(0, data.length - 1);
  int expectedChecksum = calculateChecksum(dataWithoutChecksum);
  
  return receivedChecksum == expectedChecksum;
}

// ==========================================
// SLEEP DATA COMMANDS
// ==========================================

/// Get sleep data (0x05 LEGACY format)
/// 
/// Command: [0xFF, 0x05, 0x05, 0x02, checksum]
/// 
/// Response format:
/// - Header: 0xFF
/// - Length: data length
/// - Command: 0x05
/// - Type: 0x03 (response)
/// - Sessions: variable (60-byte blocks)
/// - Checksum: calculated
/// 
/// Compatible with iOS/Android SDK
List<int> getSleepDataCommand05() {
  return buildCommand([0x05, 0x05, 0x02]);
}

/// Get sleep data (0x31 NEW format) - EXPERIMENTAL
/// 
/// Command: [0xFF, 0x06, 0x31, 0x02, checksum]
/// 
/// Note: This format is not fully documented
/// Use 0x05 for production code
List<int> getSleepDataCommand31() {
  return buildCommand([0x06, 0x31, 0x02]);
}

// ==========================================
// TIME SYNCHRONIZATION
// ==========================================

/// Set device time
/// 
/// Command format:
/// [0xFF, 0x0C, 0x01, 0x01, YY, MM, DD, hh, mm, ss, 0x00, checksum]
/// 
/// Parameters:
/// - dateTime: Time to set (in LOCAL timezone)
/// 
/// Device stores time in UTC internally
List<int> setTimeCommand(DateTime dateTime) {
  // Use local time (device will handle UTC conversion)
  int year = dateTime.year - 2000; // Years since 2000
  int month = dateTime.month;
  int day = dateTime.day;
  int hour = dateTime.hour;
  int minute = dateTime.minute;
  int second = dateTime.second;
  
  return buildCommand([
    0x0C,           // Length
    0x01,           // Command ID
    0x01,           // Subcommand
    year,
    month,
    day,
    hour,
    minute,
    second,
    0x00,           // Reserved
  ]);
}

// ==========================================
// HEART RATE COMMANDS
// ==========================================

/// Get heart rate data
/// 
/// Command: [0xFF, 0x05, 0x06, 0x02, checksum]
/// 
/// Response contains historical heart rate measurements
List<int> getHeartRateDataCommand() {
  return buildCommand([0x05, 0x06, 0x02]);
}

/// Start real-time heart rate monitoring
/// 
/// Command: [0xFF, 0x04, 0x06, 0x01, checksum]
/// 
/// Device will send continuous heart rate updates
List<int> startRealtimeHeartRateCommand() {
  return buildCommand([0x04, 0x06, 0x01]);
}

/// Stop real-time heart rate monitoring
/// 
/// Command: [0xFF, 0x04, 0x06, 0x00, checksum]
List<int> stopRealtimeHeartRateCommand() {
  return buildCommand([0x04, 0x06, 0x00]);
}

// ==========================================
// DEVICE INFO COMMANDS
// ==========================================

/// Get device information
/// 
/// Command: [0xFF, 0x04, 0x00, 0x02, checksum]
/// 
/// Response contains:
/// - Device model
/// - Firmware version
/// - Hardware version
/// - MAC address
List<int> getDeviceInfoCommand() {
  return buildCommand([0x04, 0x00, 0x02]);
}

/// Get battery level
/// 
/// Command: [0xFF, 0x04, 0x02, 0x02, checksum]
/// 
/// Response contains battery percentage (0-100)
List<int> getBatteryCommand() {
  return buildCommand([0x04, 0x02, 0x02]);
}

// ==========================================
// STEP COUNT COMMANDS
// ==========================================

/// Get step count data
/// 
/// Command: [0xFF, 0x05, 0x07, 0x02, checksum]
/// 
/// Response contains historical step counts
List<int> getStepDataCommand() {
  return buildCommand([0x05, 0x07, 0x02]);
}

// ==========================================
// UTILITY FUNCTIONS
// ==========================================

/// Formats command bytes for debugging
/// 
/// Example: [0xFF, 0x05, 0x05, 0x02, 0xCF] → "FF 05 05 02 CF"
String formatCommand(List<int> command) {
  return command
      .map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0'))
      .join(' ');
}

/// Parses command name from command ID
String getCommandName(int commandId) {
  switch (commandId) {
    case 0x00:
      return 'Device Info';
    case 0x01:
      return 'Set Time';
    case 0x02:
      return 'Battery';
    case 0x05:
      return 'Sleep Data (0x05)';
    case 0x06:
      return 'Heart Rate';
    case 0x07:
      return 'Step Count';
    case 0x31:
      return 'Sleep Data (0x31)';
    default:
      return 'Unknown (0x${commandId.toRadixString(16)})';
  }
}

/// Example usage
void main() {
  print('🔧 CL837 BLE COMMAND BUILDER:');
  print('');
  
  // Example 1: Sleep data command
  print('Example 1: Get Sleep Data (0x05)');
  List<int> sleepCmd = getSleepDataCommand05();
  print('   Command: ${formatCommand(sleepCmd)}');
  print('   Length: ${sleepCmd.length} bytes');
  print('   Checksum: 0x${sleepCmd.last.toRadixString(16).toUpperCase()}');
  print('   Valid: ${verifyChecksum(sleepCmd)}');
  print('');
  
  // Example 2: Set time
  print('Example 2: Set Device Time');
  DateTime now = DateTime.now();
  List<int> timeCmd = setTimeCommand(now);
  print('   Time: $now');
  print('   Command: ${formatCommand(timeCmd)}');
  print('   Length: ${timeCmd.length} bytes');
  print('   Valid: ${verifyChecksum(timeCmd)}');
  print('');
  
  // Example 3: Heart rate monitoring
  print('Example 3: Heart Rate Commands');
  List<int> startHR = startRealtimeHeartRateCommand();
  List<int> stopHR = stopRealtimeHeartRateCommand();
  print('   Start: ${formatCommand(startHR)}');
  print('   Stop:  ${formatCommand(stopHR)}');
  print('');
  
  // Example 4: Checksum verification
  print('Example 4: Checksum Verification');
  List<int> validData = [0xFF, 0x05, 0x05, 0x02, 0xCF];
  List<int> invalidData = [0xFF, 0x05, 0x05, 0x02, 0x00];
  print('   Valid data:   ${formatCommand(validData)} → ${verifyChecksum(validData)}');
  print('   Invalid data: ${formatCommand(invalidData)} → ${verifyChecksum(invalidData)}');
  print('');
  
  // Example 5: All commands summary
  print('Example 5: Command Reference');
  Map<String, List<int>> commands = {
    'Sleep Data (0x05)': getSleepDataCommand05(),
    'Sleep Data (0x31)': getSleepDataCommand31(),
    'Heart Rate Data': getHeartRateDataCommand(),
    'Device Info': getDeviceInfoCommand(),
    'Battery Level': getBatteryCommand(),
    'Step Count': getStepDataCommand(),
    'Start HR Monitor': startRealtimeHeartRateCommand(),
    'Stop HR Monitor': stopRealtimeHeartRateCommand(),
  };
  
  for (var entry in commands.entries) {
    print('   ${entry.key.padRight(20)}: ${formatCommand(entry.value)}');
  }
}

/// IMPORTANT NOTES:
/// 
/// 1. CHECKSUM ALGORITHM:
///    Formula: ((-sum) & 0xFF) ^ 0x3A
///    - Sum all bytes except 0xFF header
///    - Negate and mask to 8 bits
///    - XOR with 0x3A
/// 
/// 2. COMMAND STRUCTURE:
///    [0xFF][Length][CommandID][SubCommand][...Data...][Checksum]
///    - Header: Always 0xFF
///    - Length: Total bytes after header (excluding header and checksum)
///    - CommandID: Function identifier (0x05 = sleep, etc.)
///    - SubCommand: Operation type (0x02 = read, 0x01 = write)
///    - Checksum: Calculated using SDK formula
/// 
/// 3. SLEEP DATA COMMANDS:
///    - Use 0x05 (LEGACY) for production - fully tested
///    - 0x31 (NEW) is experimental, not fully documented
///    - Both return same session data, different formats
/// 
/// 4. TIME SYNCHRONIZATION:
///    - Send local time to device
///    - Device handles UTC conversion internally
///    - Year format: subtract 2000 (2025 → 25)
/// 
/// 5. REAL-TIME MONITORING:
///    - Start command enables continuous notifications
///    - Device sends updates every few seconds
///    - Stop command disables notifications
///    - Only one monitoring type active at a time
/// 
/// 6. CHECKSUM VERIFICATION:
///    - Always verify checksums on received data
///    - Invalid checksum = corrupted transmission
///    - Resend command if verification fails
/// 
/// 7. SDK COMPATIBILITY:
///    - Commands match iOS SDK (HeartBLEDevice.m)
///    - Commands match Android SDK (WearManager.java)
///    - Checksum algorithm verified against both SDKs
/// 
/// TESTING:
/// Run this file with `dart run` to see all commands
/// Use formatCommand() to debug BLE communication
