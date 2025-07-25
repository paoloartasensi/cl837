import 'dart:math' as math;
import 'package:flutter/foundation.dart';
import '../../models/device_info.dart';

class DeviceInfoProcessor {
  
  /// Processa informazioni generali del dispositivo (comando 0x01)
  static DeviceInfo? processDeviceInfo(List<int> data) {
    debugPrint('📱 Processing Device Info (0x01) - ${data.length} bytes');
    
    try {
      if (data.length < 3) return null;
      
      // Analisi esplorativa dei dati
      debugPrint('📱 Raw device info: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // Cerchiamo pattern noti
      Map<String, dynamic> rawData = {};
      
      // Possibili posizioni per info memoria (ipotesi basate su SDK)
      if (data.length >= 10) {
        // Bytes 3-6 potrebbero essere memoria totale (little endian)
        int? memoryTotal = _tryParseUint32(data, 3);
        // Bytes 7-10 potrebbero essere memoria usata
        int? memoryUsed = _tryParseUint32(data, 7);
        
        rawData['memoryTotal'] = memoryTotal;
        rawData['memoryUsed'] = memoryUsed;
        
        debugPrint('📱 Potential memory info: Total=${memoryTotal}KB, Used=${memoryUsed}KB');
      }
      
      return DeviceInfo(
        memoryTotal: rawData['memoryTotal'],
        memoryUsed: rawData['memoryUsed'],
        memoryFree: rawData['memoryTotal'] != null && rawData['memoryUsed'] != null 
            ? rawData['memoryTotal'] - rawData['memoryUsed'] 
            : null,
        rawData: rawData,
      );
      
    } catch (e) {
      debugPrint('❌ Error parsing device info: $e');
      return null;
    }
  }
  
  /// Processa livello batteria esteso (comando 0x02)
  static BatteryInfo? processBatteryLevel(List<int> data) {
    debugPrint('🔋 Processing Battery Level (0x02) - ${data.length} bytes');
    
    try {
      if (data.length < 4) return null;
      
      debugPrint('🔋 Raw battery data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // Byte 3 dovrebbe essere il livello batteria (0-100)
      int level = data[3];
      
      // Byte 4 potrebbe essere status charging (1 = charging, 0 = not charging)
      bool isCharging = data.length > 4 ? data[4] == 1 : false;
      
      // Bytes 5-6 potrebbero essere voltage in mV (little endian)
      int? voltage;
      if (data.length >= 7) {
        voltage = (data[5] | (data[6] << 8)); // little endian
      }
      
      debugPrint('🔋 Battery parsed: $level%, charging: $isCharging, voltage: ${voltage}mV');
      
      return BatteryInfo(
        level: level,
        isCharging: isCharging,
        voltage: voltage,
      );
      
    } catch (e) {
      debugPrint('❌ Error parsing battery level: $e');
      return null;
    }
  }
  
  /// Processa versione firmware (comando 0x03)
  static String? processFirmwareVersion(List<int> data) {
    debugPrint('💾 Processing Firmware Version (0x03) - ${data.length} bytes');
    
    try {
      if (data.length < 4) return null;
      
      debugPrint('💾 Raw firmware data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // Prova formato numerico: major.minor.patch
      if (data.length >= 6) {
        int major = data[3];
        int minor = data[4];
        int patch = data[5];
        
        if (major > 0 && major < 100) { // Valida se sembra una versione
          String version = '$major.$minor.$patch';
          debugPrint('💾 Firmware version (numeric): $version');
          return version;
        }
      }
      
      // Prova formato stringa ASCII
      try {
        List<int> versionBytes = data.sublist(3).where((b) => b != 0 && b < 128).toList();
        if (versionBytes.isNotEmpty) {
          String version = String.fromCharCodes(versionBytes);
          debugPrint('💾 Firmware version (string): $version');
          return version;
        }
      } catch (e) {
        debugPrint('💾 String parsing failed: $e');
      }
      
      // Fallback: versione esadecimale
      String hexVersion = data.sublist(3, math.min(data.length, 8))
          .map((b) => b.toRadixString(16).padLeft(2, '0'))
          .join('.');
      debugPrint('💾 Firmware version (hex): $hexVersion');
      return hexVersion;
      
    } catch (e) {
      debugPrint('❌ Error parsing firmware version: $e');
      return null;
    }
  }
  
  /// Processa versione hardware (comando 0x04)  
  static String? processHardwareVersion(List<int> data) {
    debugPrint('🔧 Processing Hardware Version (0x04) - ${data.length} bytes');
    
    try {
      if (data.length < 4) return null;
      
      debugPrint('🔧 Raw hardware data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // Simile al firmware
      if (data.length >= 6) {
        int major = data[3];
        int minor = data[4];
        
        if (major > 0 && major < 100) {
          String version = '$major.$minor';
          debugPrint('🔧 Hardware version: $version');
          return version;
        }
      }
      
      // Fallback: stringa o hex
      try {
        String version = String.fromCharCodes(data.sublist(3).where((b) => b != 0 && b < 128));
        if (version.isNotEmpty) {
          debugPrint('🔧 Hardware version (string): $version');
          return version;
        }
      } catch (e) {
        // Ignore
      }
      
      String hexVersion = data.sublist(3, math.min(data.length, 6))
          .map((b) => b.toRadixString(16).padLeft(2, '0'))
          .join('.');
      debugPrint('🔧 Hardware version (hex): $hexVersion');
      return hexVersion;
      
    } catch (e) {
      debugPrint('❌ Error parsing hardware version: $e');
      return null;
    }
  }
  
  /// Processa nome dispositivo (comando 0x05)
  static String? processDeviceName(List<int> data) {
    debugPrint('📱 Processing Device Name (0x05) - ${data.length} bytes');
    
    try {
      if (data.length < 4) return null;
      
      debugPrint('📱 Raw device name data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      // Estrai bytes ASCII validi (escludi null terminator)
      List<int> nameBytes = data.sublist(3).where((b) => b != 0 && b >= 32 && b < 127).toList();
      
      if (nameBytes.isEmpty) return null;
      
      String name = String.fromCharCodes(nameBytes).trim();
      debugPrint('📱 Device name: "$name"');
      return name.isNotEmpty ? name : null;
      
    } catch (e) {
      debugPrint('❌ Error parsing device name: $e');
      return null;
    }
  }
  
  /// Processa MAC address (comando 0x06)
  static String? processMacAddress(List<int> data) {
    debugPrint('🔗 Processing MAC Address (0x06) - ${data.length} bytes');
    
    try {
      if (data.length < 9) return null; // 3 header + 6 MAC bytes
      
      debugPrint('🔗 Raw MAC data: ${data.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
      
      String mac = data.sublist(3, 9)
          .map((b) => b.toRadixString(16).padLeft(2, '0'))
          .join(':')
          .toUpperCase();
      
      debugPrint('🔗 MAC Address: $mac');
      return mac;
      
    } catch (e) {
      debugPrint('❌ Error parsing MAC address: $e');
      return null;
    }
  }
  
  /// Helper per parsare uint32 little endian
  static int? _tryParseUint32(List<int> data, int offset) {
    if (offset + 4 > data.length) return null;
    
    return data[offset] |
           (data[offset + 1] << 8) |
           (data[offset + 2] << 16) |
           (data[offset + 3] << 24);
  }
  
  /// Verifica se il comando è relativo alle info dispositivo
  static bool canHandle(int command) {
    return command >= 0x01 && command <= 0x06;
  }
}
