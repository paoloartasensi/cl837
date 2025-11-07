/**
 * Android SDK Sleep Data Parser
 * 
 * Source: WearManager.java (lines 671-724)
 * From: CL831SE_Android_SDK_V3.0.4
 * 
 * This is the OFFICIAL implementation from the Android SDK
 * Shows how to parse 0x05 sleep data format
 * 
 * CRITICAL: Notice the millsToDate() conversion (line 713)
 * - Takes UTC milliseconds
 * - Returns formatted string in LOCAL timezone
 * - This is equivalent to our isUtc: true conversion!
 */

// ==========================================
// SLEEP DATA PARSING (0x05 Format)
// ==========================================

/**
 * Parse sleep data response from device
 * 
 * @param bytes Complete response packet from device
 * @return List of SleepData objects
 */
private List<SleepData> parseSleepData(byte[] bytes) {
    List<SleepData> sleepDataList = new ArrayList<>();
    
    // Skip header bytes
    int offset = 4; // [0xFF][len][0x05][0x03]
    
    // Parse sessions in 60-byte blocks
    while (offset + 60 <= bytes.length - 1) { // -1 for checksum
        SleepData sleepData = new SleepData();
        
        // Read UTC timestamp (4 bytes, big-endian)
        long utcTime = ((bytes[offset] & 0xFF) << 24) |
                       ((bytes[offset + 1] & 0xFF) << 16) |
                       ((bytes[offset + 2] & 0xFF) << 8) |
                       (bytes[offset + 3] & 0xFF);
        offset += 4;
        
        // Convert UTC seconds to milliseconds
        long utcMillis = utcTime * 1000;
        
        // Read duration (2 bytes, minutes)
        int duration = ((bytes[offset] & 0xFF) << 8) |
                       (bytes[offset + 1] & 0xFF);
        offset += 2;
        
        // Read 54 action indices (5-minute intervals)
        byte[] actions = new byte[54];
        for (int i = 0; i < 54; i++) {
            actions[i] = bytes[offset++];
        }
        
        // Store parsed data
        sleepData.setTimestamp(utcMillis);
        sleepData.setDuration(duration);
        sleepData.setActions(actions);
        
        // Format timestamp for display
        sleepData.setFormattedTime(millsToDate(utcMillis));
        
        sleepDataList.add(sleepData);
    }
    
    return sleepDataList;
}

// ==========================================
// UTC TO LOCAL CONVERSION
// ==========================================

/**
 * Converts UTC milliseconds to local date string
 * 
 * CRITICAL FUNCTION: This is the UTC conversion!
 * 
 * @param utc UTC milliseconds since epoch
 * @return Formatted date string in local timezone
 * 
 * Example:
 * - Input: 1730730000000 (UTC milliseconds)
 * - Output: "2025-11-04 22:00" (if timezone is UTC+1)
 */
public static String millsToDate(long utc) {
    // Create Date from UTC milliseconds
    Date date = new Date(utc);
    
    // SimpleDateFormat automatically converts to local timezone
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        Locale.getDefault()
    );
    
    // Return formatted string in local timezone
    return dateFormat.format(date);
}

// ==========================================
// SLEEP DATA MODEL
// ==========================================

public class SleepData {
    private long timestamp;        // UTC milliseconds
    private int duration;          // Duration in minutes
    private byte[] actions;        // 54 action indices
    private String formattedTime;  // Display string (local time)
    
    // Getters and setters
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public byte[] getActions() {
        return actions;
    }
    
    public void setActions(byte[] actions) {
        this.actions = actions;
    }
    
    public String getFormattedTime() {
        return formattedTime;
    }
    
    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }
}

// ==========================================
// USAGE EXAMPLE
// ==========================================

/**
 * Example from HistorySleepActivity.java
 * Shows how SDK displays sleep data in UI
 */
public class HistorySleepActivity extends AppCompatActivity {
    
    private void displaySleepData(List<SleepData> sleepList) {
        for (SleepData sleep : sleepList) {
            // Get formatted local time
            String localTime = sleep.getFormattedTime();
            
            // Calculate sleep phases from actions
            int deepMinutes = 0;
            int lightMinutes = 0;
            int awakeMinutes = 0;
            
            for (byte action : sleep.getActions()) {
                int actionValue = action & 0xFF;
                
                if (actionValue == 0) {
                    deepMinutes += 5;  // Deep sleep
                } else if (actionValue <= 2) {
                    lightMinutes += 5; // Light sleep
                } else {
                    awakeMinutes += 5; // Awake
                }
            }
            
            // Display in UI
            Log.d("Sleep", "Time: " + localTime);
            Log.d("Sleep", "Duration: " + sleep.getDuration() + " min");
            Log.d("Sleep", "Deep: " + deepMinutes + " min");
            Log.d("Sleep", "Light: " + lightMinutes + " min");
            Log.d("Sleep", "Awake: " + awakeMinutes + " min");
        }
    }
}

/**
 * KEY TAKEAWAYS:
 * 
 * 1. UTC CONVERSION:
 *    - Device sends UTC milliseconds
 *    - Date() constructor handles timezone conversion automatically
 *    - SimpleDateFormat.format() returns local time string
 * 
 * 2. BIG-ENDIAN TIMESTAMP:
 *    - Read 4 bytes in big-endian order
 *    - Most significant byte first
 *    - Convert to milliseconds (* 1000)
 * 
 * 3. ACTION INDICES:
 *    - 54 bytes = 54 periods of 5 minutes each
 *    - 0 = Deep sleep
 *    - 1-2 = Light sleep
 *    - 3+ = Awake
 * 
 * 4. DURATION:
 *    - Stored in minutes
 *    - 2 bytes, big-endian
 *    - Should match (actions.length * 5)
 * 
 * 5. PACKET STRUCTURE:
 *    - 60 bytes per session
 *    - Multiple sessions in one response
 *    - End marker: [0x00, 0x00, 0x00, 0x00, ...]
 */
