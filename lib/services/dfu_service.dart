/// 🔄 DFU (Device Firmware Update) Service
/// 
/// Gestisce l'aggiornamento firmware del dispositivo CL837/CL831
/// seguendo il protocollo ufficiale SDK Android/iOS
/// 
/// Pipeline:
/// 1. Legge versione firmware attuale (comando 0x03)
/// 2. Entra in modalità DFU (comando 0x27)
/// 3. Calcola nuovo MAC address (ultimo byte + 1)
/// 4. Scansiona device DFU (nome con suffisso "U")
/// 5. Avvia Nordic DFU upload
/// 6. Monitora progresso e stato
/// 
/// Riferimenti: docs/DFU_UPDATE_PIPELINE.md
library;

import 'dart:async';
import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:nordic_dfu/nordic_dfu.dart';
import 'package:path_provider/path_provider.dart';
import '../chileaf_extended_service.dart';

/// Stati DFU
enum DfuState {
  idle,
  preparingFile,
  enteringDfuMode,
  scanningDfuDevice,
  connecting,
  uploading,
  validating,
  completed,
  error,
  aborted,
}

/// Risultato DFU
class DfuResult {
  final bool success;
  final String? errorMessage;
  final String? newVersion;
  
  DfuResult({
    required this.success,
    this.errorMessage,
    this.newVersion,
  });
}

/// Progress data
class DfuProgress {
  final DfuState state;
  final int percent;
  final double speed;
  final double avgSpeed;
  final int currentPart;
  final int totalParts;
  final String? message;
  
  DfuProgress({
    required this.state,
    this.percent = 0,
    this.speed = 0.0,
    this.avgSpeed = 0.0,
    this.currentPart = 1,
    this.totalParts = 1,
    this.message,
  });
  
  DfuProgress copyWith({
    DfuState? state,
    int? percent,
    double? speed,
    double? avgSpeed,
    int? currentPart,
    int? totalParts,
    String? message,
  }) {
    return DfuProgress(
      state: state ?? this.state,
      percent: percent ?? this.percent,
      speed: speed ?? this.speed,
      avgSpeed: avgSpeed ?? this.avgSpeed,
      currentPart: currentPart ?? this.currentPart,
      totalParts: totalParts ?? this.totalParts,
      message: message ?? this.message,
    );
  }
}

class DfuService {
  final ChileafExtendedService _chileafService;
  final BluetoothDevice _device;
  
  final StreamController<DfuProgress> _progressController = StreamController<DfuProgress>.broadcast();
  Stream<DfuProgress> get progressStream => _progressController.stream;
  
  DfuProgress _currentProgress = DfuProgress(state: DfuState.idle);
  
  String? _dfuMacAddress;
  bool _isInitialized = false;
  
  // Cache delle device info per evitare letture multiple
  Map<String, String>? _cachedDeviceInfo;
  
  DfuService({
    required ChileafExtendedService chileafService,
    required BluetoothDevice device,
  })  : _chileafService = chileafService,
        _device = device;
  
  /// Verifica e inizializza ChileafExtendedService se necessario
  Future<bool> _ensureServiceInitialized() async {
    if (_isInitialized && _chileafService.isConnected) {
      return true;
    }
    
    debugPrint('🔧 Initializing ChileafExtendedService...');
    
    try {
      // Verifica se device è connesso
      final connState = await _device.connectionState.first;
      debugPrint('   Device state: $connState');
      
      if (connState != BluetoothConnectionState.connected) {
        debugPrint('   ❌ Device not connected, connecting...');
        await _device.connect(mtu: null, license: License.free);
        await Future.delayed(Duration(seconds: 1)); // Attendi stabilizzazione
      }
      
      // Verifica se ChileafService è già avviato
      if (!_chileafService.isConnected) {
        debugPrint('   🔧 Starting ChileafExtendedService...');
        await _chileafService.start(_device);
        
        // Attendi che servizio sia pronto
        await Future.delayed(Duration(seconds: 2));
        
        // Verifica che il servizio sia veramente pronto
        debugPrint('   🔍 Verifying service readiness...');
        debugPrint('   - isConnected: ${_chileafService.isConnected}');
        
        // Verifica che gli stream siano attivi
        try {
          // Test rapido: prova a leggere device info (dovrebbe rispondere velocemente)
          debugPrint('   🔍 Testing service with device info request...');
          await _chileafService.requestDeviceInfo();
          await Future.delayed(Duration(milliseconds: 500));
        } catch (e) {
          debugPrint('   ⚠️ Service test failed (non-critical): $e');
        }
      }
      
      _isInitialized = true;
      debugPrint('   ✅ ChileafExtendedService initialized and ready');
      return true;
      
    } catch (e) {
      debugPrint('   ❌ Failed to initialize service: $e');
      return false;
    }
  }
  
  /// Legge informazioni dal Device Information Service standard BLE (0x180A)
  /// Usa cache se disponibile per evitare letture multiple
  Future<Map<String, String>> readDeviceInformation({bool forceRefresh = false}) async {
    // Usa cache se disponibile e non forzato refresh
    if (!forceRefresh && _cachedDeviceInfo != null) {
      debugPrint('📋 Using cached device information');
      return Map.from(_cachedDeviceInfo!);
    }
    
    Map<String, String> info = {};
    
    try {
      debugPrint('📋 Reading Device Information Service (0x180A)...');
      
      // UUID del Device Information Service (standard BLE)
      final Guid disServiceUuid = Guid('0000180a-0000-1000-8000-00805f9b34fb');
      
      // UUID delle caratteristiche
      final Map<String, String> characteristics = {
        'manufacturer': '00002a29-0000-1000-8000-00805f9b34fb',
        'model': '00002a24-0000-1000-8000-00805f9b34fb',
        'serial': '00002a25-0000-1000-8000-00805f9b34fb',
        'hardware': '00002a27-0000-1000-8000-00805f9b34fb',
        'firmware': '00002a26-0000-1000-8000-00805f9b34fb',
        'software': '00002a28-0000-1000-8000-00805f9b34fb',
        'systemId': '00002a23-0000-1000-8000-00805f9b34fb',
      };
      
      // Scopri i servizi
      List<BluetoothService> services = await _device.discoverServices();
      
      // Trova il Device Information Service
      BluetoothService? disService;
      try {
        disService = services.firstWhere(
          (s) => s.uuid == disServiceUuid
        );
      } catch (e) {
        debugPrint('   ⚠️ Device Information Service not found');
        return info;
      }
      
      debugPrint('   ✓ Device Information Service found');
      
      // Leggi ogni caratteristica
      for (var entry in characteristics.entries) {
        try {
          final charUuid = Guid(entry.value);
          
          // Trova la caratteristica
          BluetoothCharacteristic? char;
          try {
            char = disService.characteristics.firstWhere(
              (c) => c.uuid == charUuid
            );
          } catch (e) {
            // Caratteristica non trovata, continua
            continue;
          }
          
          List<int> value = await char.read();
          // Decodifica come stringa UTF-8
          String decoded = String.fromCharCodes(value)
              .replaceAll('\x00', '') // Rimuovi null terminators
              .trim();
          
          if (decoded.isNotEmpty) {
            info[entry.key] = decoded;
            debugPrint('   ${entry.key}: $decoded');
          }
          
        } catch (e) {
          debugPrint('   ${entry.key}: Not available');
        }
      }
      
      debugPrint('✓ Device information read complete');
      
      // Salva in cache
      _cachedDeviceInfo = Map.from(info);
      
    } catch (e) {
      debugPrint('❌ Error reading device information: $e');
    }
    
    return info;
  }
  
  /// Ottiene versione firmware dal Device Information Service (se disponibile)
  Future<String?> getCurrentFirmwareVersion() async {
    debugPrint('💾 Reading firmware version...');
    
    try {
      Map<String, String> info = await readDeviceInformation();
      
      if (info.containsKey('firmware') && info['firmware']!.isNotEmpty) {
        String version = info['firmware']!;
        debugPrint('✓ Firmware version: $version');
        return version;
      }
      
      debugPrint('⚠️ Firmware version not available in Device Information Service');
      return null;
      
    } catch (e) {
      debugPrint('❌ Error reading firmware version: $e');
      return null;
    }
  }
  
  /// Entra in modalità DFU
  /// Invia comando 0x27 e calcola nuovo MAC address
  Future<String?> enterDfuMode() async {
    debugPrint('🔧 Entering DFU mode...');
    
    _updateProgress(DfuProgress(
      state: DfuState.enteringDfuMode,
      message: 'Entering DFU mode...',
    ));
    
    // CRITICAL: Assicura che il servizio sia inizializzato
    if (!await _ensureServiceInitialized()) {
      debugPrint('❌ Cannot enter DFU mode: service not initialized');
      _updateProgress(DfuProgress(
        state: DfuState.error,
        message: 'Service not initialized',
      ));
      return null;
    }
    
    try {
      // Calcola nuovo MAC address (ultimo byte + 1)
      String currentMac = _device.remoteId.toString();
      _dfuMacAddress = _calculateDfuAddress(currentMac);
      
      debugPrint('📍 Current MAC: $currentMac');
      debugPrint('📍 DFU MAC: $_dfuMacAddress');
      
      // Invia comando 0x27 per entrare in DFU mode
      // IMPORTANTE: Il comando è [0xFF, 0x04, 0x27] + checksum (0xEC)
      // Checksum calcolato con algoritmo iOS: sum → two's complement → XOR 0x3A
      List<int> command = [0xFF, 0x04, 0x27];
      int checksum = _calculateChecksum(command);
      command.add(checksum);
      
      debugPrint('📤 Sending DFU command: ${command.map((b) => '0x${b.toRadixString(16).padLeft(2, '0').toUpperCase()}').join(' ')}');
      debugPrint('   ✅ Checksum: 0x${checksum.toRadixString(16).padLeft(2, '0').toUpperCase()} (verified from iOS SDK)');
      
      // Ottieni RX characteristic (per scrivere al device)
      var rxChar = await _getRxCharacteristic();
      if (rxChar == null) {
        throw Exception('RX characteristic not found');
      }
      
      debugPrint('📝 Using RX characteristic: ${rxChar.uuid}');
      debugPrint('   Properties: write=${rxChar.properties.write}, writeWithoutResponse=${rxChar.properties.writeWithoutResponse}');
      
      try {
        await rxChar.write(command, withoutResponse: false);
        debugPrint('✅ DFU command write completed');
      } catch (e) {
        // IMPORTANTE: GATT error 133 è NORMALE!
        // Significa che il device ha ricevuto il comando e si sta disconnettendo per entrare in DFU mode
        if (e.toString().contains('133') || e.toString().contains('GATT_ERROR')) {
          debugPrint('✅ Device disconnected after DFU command (expected behavior)');
          debugPrint('   GATT error 133 = device is rebooting into bootloader mode');
        } else {
          // Altri errori sono problematici
          rethrow;
        }
      }
      
      debugPrint('✅ DFU mode command sent successfully');
      debugPrint('⏳ Device will reboot in DFU mode (2-3 seconds)...');
      debugPrint('� LED should turn RED and device should disconnect');
      
      return _dfuMacAddress;
      
    } catch (e) {
      debugPrint('❌ Failed to enter DFU mode: $e');
      _updateProgress(DfuProgress(
        state: DfuState.error,
        message: 'Failed to enter DFU mode: $e',
      ));
      return null;
    }
  }
  
  /// Calcola nuovo MAC address per DFU mode
  /// Incrementa l'ultimo byte dell'indirizzo originale
  String _calculateDfuAddress(String originalMac) {
    List<String> parts = originalMac.split(':');
    String lastByte = parts.last;
    
    // Incrementa ultimo byte (modulo 256 per overflow)
    int lastByteInt = int.parse(lastByte, radix: 16);
    int newLastByte = (lastByteInt + 1) & 0xFF;
    
    parts[parts.length - 1] = newLastByte.toRadixString(16).padLeft(2, '0').toUpperCase();
    
    return parts.join(':');
  }
  
  /// Calcola checksum per comando DFU
  /// Algoritmo CORRETTO verificato da iOS HeartBLEDevice.m (November 8, 2025)
  /// 
  /// Steps:
  /// 1. Somma tutti i bytes: 0xFF + 0x04 + 0x27 = 0x12A
  /// 2. Prendi ultimo byte: 0x2A
  /// 3. Two's complement: 0x00 - 0x2A = 0xD6
  /// 4. XOR con 0x3A: 0xD6 ^ 0x3A = 0xEC
  /// 
  /// Test: [0xFF, 0x04, 0x27] → checksum = 0xEC ✅
  /// Result: LED turns RED, device enters bootloader mode
  int _calculateChecksum(List<int> data) {
    // Step 1: Somma tutti i bytes e prendi ultimo byte
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    int lastByte = sum & 0xFF;  // Prendi solo ultimo byte (0x2A per [0xFF, 0x04, 0x27])
    
    // Step 2: Two's complement (0x00 - lastByte)
    int twosComplement = (0x00 - lastByte) & 0xFF;  // 0xD6
    
    // Step 3: XOR con 0x3A
    int checksum = twosComplement ^ 0x3A;  // 0xEC
    
    return checksum;
  }
  
  /// Ottiene RX characteristic (per scrivere al device)
  /// RX = Receive dal punto di vista del device = Write dal nostro lato
  /// UUID: aae28f02-71b5-42a1-8c3c-f9cf6ac969d0
  Future<BluetoothCharacteristic?> _getRxCharacteristic() async {
    const String rxCharUuid = 'aae28f02-71b5-42a1-8c3c-f9cf6ac969d0';
    
    try {
      List<BluetoothService> services = await _device.discoverServices();
      
      for (var service in services) {
        for (var characteristic in service.characteristics) {
          // Cerca prima per UUID specifico
          if (characteristic.uuid.toString().toLowerCase() == rxCharUuid.toLowerCase()) {
            debugPrint('✅ Found RX characteristic (by UUID): ${characteristic.uuid}');
            return characteristic;
          }
        }
      }
      
      // Fallback: cerca per proprietà WRITE
      for (var service in services) {
        for (var characteristic in service.characteristics) {
          if (characteristic.properties.write || characteristic.properties.writeWithoutResponse) {
            debugPrint('⚠️ Found RX characteristic (by WRITE property): ${characteristic.uuid}');
            return characteristic;
          }
        }
      }
      
      debugPrint('❌ RX characteristic not found');
      return null;
      
    } catch (e) {
      debugPrint('❌ Error finding RX characteristic: $e');
      return null;
    }
  }
  
  /// Scansiona device in DFU mode
  /// Device avrà nome con suffisso "U" (es. "CL831" → "CL831U")
  Future<BluetoothDevice?> scanForDfuDevice(String dfuMac, {int timeoutSeconds = 45}) async {
    debugPrint('🔍 Scanning for DFU device...');
    debugPrint('   Target MAC: $dfuMac');
    debugPrint('   Timeout: ${timeoutSeconds}s');
    
    _updateProgress(DfuProgress(
      state: DfuState.scanningDfuDevice,
      message: 'Scanning for DFU device (0/${timeoutSeconds}s)...',
    ));
    
    Completer<BluetoothDevice?> completer = Completer();
    int elapsedSeconds = 0;
    
    // Progress timer ogni secondo
    Timer? progressTimer = Timer.periodic(Duration(seconds: 1), (timer) {
      elapsedSeconds++;
      if (!completer.isCompleted) {
        _updateProgress(DfuProgress(
          state: DfuState.scanningDfuDevice,
          percent: (elapsedSeconds * 100 / timeoutSeconds).round(),
          message: 'Scanning for DFU device ($elapsedSeconds/${timeoutSeconds}s)...',
        ));
      } else {
        timer.cancel();
      }
    });
    
    // Timeout
    Timer timeoutTimer = Timer(Duration(seconds: timeoutSeconds), () {
      if (!completer.isCompleted) {
        progressTimer.cancel();
        debugPrint('❌ DFU device not found (timeout after ${timeoutSeconds}s)');
        debugPrint('💡 Suggestions:');
        debugPrint('   1. Check device is within 1 meter range');
        debugPrint('   2. Ensure device rebooted (2-3 seconds after command)');
        debugPrint('   3. Try manually restarting device');
        debugPrint('   4. Check if device name changed to end with "U"');
        _updateProgress(DfuProgress(
          state: DfuState.error,
          message: 'DFU device not found after ${timeoutSeconds}s. Device may not have entered DFU mode.',
        ));
        completer.complete(null);
      }
    });
    
    try {
      debugPrint('🔍 Starting DFU device scan...');
      debugPrint('   Looking for MAC: $dfuMac');
      debugPrint('   Looking for name ending with: U');
      
      List<String> candidateDevices = [];
      
      // Start scan
      StreamSubscription? scanSubscription;
      scanSubscription = FlutterBluePlus.scanResults.listen((results) {
        for (ScanResult result in results) {
          String deviceMac = result.device.remoteId.toString();
          String deviceName = result.device.platformName;
          
          // Log tutti i device per debug
          if (deviceName.isNotEmpty) {
            String candidateInfo = '$deviceName ($deviceMac)';
            if (!candidateDevices.contains(candidateInfo)) {
              candidateDevices.add(candidateInfo);
              debugPrint('🔎 Candidate: $candidateInfo');
            }
          }
          
          // Verifica 1: MAC address match
          bool macMatch = deviceMac.toUpperCase() == dfuMac.toUpperCase();
          
          // Verifica 2: Nome finisce con "U" (case insensitive)
          bool nameMatch = deviceName.toUpperCase().endsWith('U');
          
          // Verifica 3: Nome contiene "CL831" o "CL837" (fallback)
          bool nameFallback = deviceName.toUpperCase().contains('CL831') || 
                             deviceName.toUpperCase().contains('CL837');
          
          if (macMatch && nameMatch) {
            debugPrint('✅ DFU device found (exact match)!');
            debugPrint('   Name: $deviceName');
            debugPrint('   MAC: $deviceMac');
            
            timeoutTimer.cancel();
            scanSubscription?.cancel();
            FlutterBluePlus.stopScan();
            
            if (!completer.isCompleted) {
              completer.complete(result.device);
            }
            break;
          } else if (macMatch && nameFallback) {
            debugPrint('⚠️ Potential DFU device found (MAC match, name fallback)');
            debugPrint('   Name: $deviceName (expected to end with U)');
            debugPrint('   MAC: $deviceMac ✓');
            // Non interrompiamo, continuiamo a cercare match perfetto
          } else if (nameMatch && !macMatch) {
            debugPrint('⚠️ Device with U suffix but wrong MAC: $deviceName ($deviceMac)');
            debugPrint('   Expected MAC: $dfuMac');
          }
        }
      });
      
      await FlutterBluePlus.startScan(timeout: Duration(seconds: 30));
      
      BluetoothDevice? result = await completer.future;
      
      if (result == null) {
        debugPrint('❌ DFU scan completed. Candidates found:');
        for (var candidate in candidateDevices) {
          debugPrint('   - $candidate');
        }
      }
      
      return result;
      
    } catch (e) {
      debugPrint('❌ Scan error: $e');
      timeoutTimer.cancel();
      FlutterBluePlus.stopScan();
      
      _updateProgress(DfuProgress(
        state: DfuState.error,
        message: 'Scan error: $e',
      ));
      
      if (!completer.isCompleted) {
        completer.complete(null);
      }
      
      return null;
    }
  }
  
  /// Copia file firmware da assets a directory temporanea
  Future<String?> _prepareFirmwareFile(String assetPath) async {
    debugPrint('📦 Preparing firmware file from assets...');
    
    _updateProgress(DfuProgress(
      state: DfuState.preparingFile,
      message: 'Preparing firmware file...',
    ));
    
    try {
      // Carica file da assets
      ByteData data = await rootBundle.load(assetPath);
      
      // Ottieni directory temporanea
      Directory tempDir = await getTemporaryDirectory();
      String fileName = assetPath.split('/').last;
      String tempPath = '${tempDir.path}/$fileName';
      
      // Scrivi file
      File tempFile = File(tempPath);
      await tempFile.writeAsBytes(data.buffer.asUint8List());
      
      debugPrint('✅ Firmware file ready: $tempPath');
      debugPrint('   Size: ${await tempFile.length()} bytes');
      
      return tempPath;
      
    } catch (e) {
      debugPrint('❌ Failed to prepare firmware file: $e');
      _updateProgress(DfuProgress(
        state: DfuState.error,
        message: 'Failed to prepare firmware file: $e',
      ));
      return null;
    }
  }
  
  /// Avvia DFU update con Nordic library
  Future<bool> _startDfuUpload(String deviceId, String zipFilePath) async {
    debugPrint('🚀 Starting DFU upload...');
    debugPrint('   Device: $deviceId');
    debugPrint('   File: $zipFilePath');
    
    _updateProgress(DfuProgress(
      state: DfuState.uploading,
      message: 'Starting DFU upload...',
    ));
    
    try {
      await NordicDfu().startDfu(
        deviceId,
        zipFilePath,
        numberOfPackets: 12,
        enableUnsafeExperimentalButtonlessServiceInSecureDfu: true,
        
        onProgressChanged: (deviceAddress, percent, speed, avgSpeed, currentPart, partsTotal) {
          debugPrint('📊 DFU Progress: $percent%');
          
          _updateProgress(DfuProgress(
            state: DfuState.uploading,
            percent: percent,
            speed: speed,
            avgSpeed: avgSpeed,
            currentPart: currentPart,
            totalParts: partsTotal,
            message: partsTotal > 1 
                ? 'Uploading part $currentPart/$partsTotal ($percent%)'
                : 'Uploading firmware: $percent%',
          ));
        },
        
        onDeviceConnecting: (deviceAddress) {
          debugPrint('🔗 Connecting to DFU device...');
          _updateProgress(DfuProgress(
            state: DfuState.connecting,
            message: 'Connecting to DFU device...',
          ));
        },
        
        onDeviceConnected: (deviceAddress) {
          debugPrint('✅ Connected to DFU device');
        },
        
        onDfuProcessStarting: (deviceAddress) {
          debugPrint('🔧 DFU process starting...');
          _updateProgress(DfuProgress(
            state: DfuState.uploading,
            message: 'DFU process starting...',
          ));
        },
        
        onEnablingDfuMode: (deviceAddress) {
          debugPrint('⚙️ Enabling DFU bootloader...');
          _updateProgress(DfuProgress(
            state: DfuState.uploading,
            message: 'Enabling DFU bootloader...',
          ));
        },
        
        onFirmwareValidating: (deviceAddress) {
          debugPrint('✔️ Validating firmware...');
          _updateProgress(DfuProgress(
            state: DfuState.validating,
            percent: 100,
            message: 'Validating firmware...',
          ));
        },
        
        onDeviceDisconnecting: (deviceAddress) {
          debugPrint('🔌 Disconnecting...');
        },
        
        onDfuCompleted: (deviceAddress) {
          debugPrint('🎉 DFU completed successfully!');
          _updateProgress(DfuProgress(
            state: DfuState.completed,
            percent: 100,
            message: 'Firmware update completed!',
          ));
        },
        
        onDfuAborted: (deviceAddress) {
          debugPrint('❌ DFU aborted');
          _updateProgress(DfuProgress(
            state: DfuState.aborted,
            message: 'Firmware update aborted',
          ));
        },
        
        onError: (deviceAddress, error, errorType, message) {
          debugPrint('❌ DFU error: $message');
          debugPrint('   Error code: $error');
          debugPrint('   Error type: $errorType');
          _updateProgress(DfuProgress(
            state: DfuState.error,
            message: message,
          ));
        },
      );
      
      return _currentProgress.state == DfuState.completed;
      
    } catch (e) {
      debugPrint('❌ Exception during DFU: $e');
      _updateProgress(DfuProgress(
        state: DfuState.error,
        message: 'Update failed: $e',
      ));
      return false;
    }
  }
  
  /// Pipeline completa: prepara file → entra DFU → scansiona → aggiorna
  Future<DfuResult> performDfuUpdate({
    required String assetPath,
    String? targetVersion,
  }) async {
    debugPrint('🔄 ========================================');
    debugPrint('🔄 STARTING DFU UPDATE PIPELINE');
    debugPrint('🔄 ========================================');
    
    try {
      // STEP 1: Leggi versione attuale (OPZIONALE - non blocca se fallisce)
      debugPrint('\n📋 STEP 1: Reading current firmware version (optional)...');
      String? currentVersion;
      try {
        currentVersion = await getCurrentFirmwareVersion();
        debugPrint('   ✅ Current: $currentVersion');
      } catch (e) {
        debugPrint('   ⚠️ Could not read current version (non-critical): $e');
        currentVersion = 'Unknown';
      }
      debugPrint('   🎯 Target: $targetVersion');
      
      // STEP 2: Prepara file firmware
      debugPrint('\n📦 STEP 2: Preparing firmware file...');
      String? firmwarePath = await _prepareFirmwareFile(assetPath);
      
      if (firmwarePath == null) {
        return DfuResult(
          success: false,
          errorMessage: 'Failed to prepare firmware file',
        );
      }
      
      // STEP 3: Entra in modalità DFU
      debugPrint('\n🔧 STEP 3: Entering DFU mode...');
      String? dfuMac = await enterDfuMode();
      
      if (dfuMac == null) {
        return DfuResult(
          success: false,
          errorMessage: 'Failed to enter DFU mode',
        );
      }
      
      // STEP 4: Attendi riavvio device (2-3 secondi)
      debugPrint('\n⏳ STEP 4: Waiting for device reboot...');
      await Future.delayed(Duration(seconds: 3));
      
      // STEP 5: Scansiona device DFU
      debugPrint('\n🔍 STEP 5: Scanning for DFU device...');
      BluetoothDevice? dfuDevice = await scanForDfuDevice(dfuMac);
      
      if (dfuDevice == null) {
        return DfuResult(
          success: false,
          errorMessage: 'DFU device not found after 30 seconds',
        );
      }
      
      // STEP 6: Avvia DFU upload
      debugPrint('\n🚀 STEP 6: Starting DFU upload...');
      bool success = await _startDfuUpload(
        dfuDevice.remoteId.toString(),
        firmwarePath,
      );
      
      if (!success) {
        return DfuResult(
          success: false,
          errorMessage: 'DFU upload failed',
        );
      }
      
      debugPrint('\n🎉 ========================================');
      debugPrint('🎉 DFU UPDATE COMPLETED SUCCESSFULLY!');
      debugPrint('🎉 ========================================');
      
      return DfuResult(
        success: true,
        newVersion: targetVersion,
      );
      
    } catch (e) {
      debugPrint('\n❌ ========================================');
      debugPrint('❌ DFU UPDATE FAILED: $e');
      debugPrint('❌ ========================================');
      
      _updateProgress(DfuProgress(
        state: DfuState.error,
        message: 'Update failed: $e',
      ));
      
      return DfuResult(
        success: false,
        errorMessage: e.toString(),
      );
    }
  }
  
  /// Aggiorna stato progresso
  void _updateProgress(DfuProgress progress) {
    _currentProgress = progress;
    _progressController.add(progress);
  }
  
  /// Cleanup
  void dispose() {
    _progressController.close();
  }
}
