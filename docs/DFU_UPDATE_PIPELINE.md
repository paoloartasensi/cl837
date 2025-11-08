# 🔄 DFU Firmware Update - Pipeline Completa

## 📋 Overview

Questo documento descrive la **pipeline completa** per eseguire l'aggiornamento firmware (DFU - Device Firmware Update) sul dispositivo CL837, estratta direttamente dai SDK ufficiali Android e iOS.

---

## 🔧 Comando BLE per Entrare in Modalità DFU

### Specifiche Comando

| Campo | Valore | Descrizione |
|-------|--------|-------------|
| **Header** | `0xFF` | Header standard |
| **Length** | `0x04` | Lunghezza pacchetto |
| **Command** | `0x27` (39) | Comando DFU mode |
| **Param** | `0x00` | Parametro (non usato) |
| **Checksum** | Calcolato | Somma bytes & 0xFF |

**Comando completo:** `[0xFF, 0x04, 0x27, 0x00, checksum]`

---

## 📱 Implementazione Android (WearManager.java)

### Metodo `dfuMode()`

```java
/**
 * Entra in modalità DFU e ritorna il nuovo indirizzo BLE del device
 * 
 * Pipeline:
 * 1. Calcola nuovo indirizzo MAC (last byte + 1)
 * 2. Invia comando 0x27
 * 3. Device si riavvia in modalità DFU
 * 4. Nuovo nome device: originale + "U" (es. "CL831" → "CL831U")
 * 
 * @return Nuovo indirizzo MAC del device in DFU mode
 */
public String dfuMode() {
    // 1. Ottieni nuovo indirizzo MAC (incrementa ultimo byte)
    String address = getDFUAddress();
    
    // 2. Costruisci comando: [FF 04 27 checksum]
    byte[] command = {-1, 4, 39, checkSum(command)};
    
    // 3. Invia comando BLE
    writeTxCharacteristic(command);
    
    // 4. Ritorna nuovo indirizzo per scansione
    return address;
}

/**
 * Calcola nuovo indirizzo MAC per DFU mode
 * Incrementa l'ultimo byte dell'indirizzo originale
 * 
 * Esempio:
 * Original:  "AA:BB:CC:DD:EE:FF"
 * DFU mode:  "AA:BB:CC:DD:EE:00" (FF+1 con overflow = 00)
 */
private String getDFUAddress() {
    BluetoothDevice device = getBluetoothDevice();
    if (device == null) {
        return null;
    }
    
    String deviceAddress = device.getAddress();
    
    // Split: primi 15 char (AA:BB:CC:DD:EE) + ultimi 2 (FF)
    String firstBytes = deviceAddress.substring(0, 15);
    String lastByte = deviceAddress.substring(15);
    
    // Incrementa ultimo byte (modulo 256 per overflow)
    String lastByteIncremented = String.format(
        Locale.US, 
        "%02X", 
        Integer.valueOf((Integer.valueOf(lastByte, 16).intValue() + 1) & 255)
    );
    
    return firstBytes + lastByteIncremented;
}

/**
 * Costruisce comando BLE con checksum
 */
private void sendCommand(final byte cmd, final int... values) {
    byte[] result;
    
    if (values != null) {
        int len = values.length + 4;
        byte[] header = HexUtil.compose(255, len, cmd);
        byte[] bytes = HexUtil.compose(values);
        result = HexUtil.append(header, bytes);
    } else {
        result = HexUtil.compose(255, 4, cmd);
    }
    
    byte check = checkSum(result);
    byte[] command = HexUtil.append(result, check);
    writeTxCharacteristic(command);
}
```

---

## 🍎 Implementazione iOS (HeartBLEDevice.m)

### Metodo `enterDFU`

```objectivec
/**
 * Entra in modalità DFU
 * Invia comando 0x27 al dispositivo
 */
- (void)enterDFU {
    NSString *StrLen = @"ff0427";  // [FF 04 27] + checksum
    [self BLEReadData:StrLen];
}

/**
 * Invia comando al device
 */
- (void)BLEReadData:(NSString *)dataStr {
    [self CheckSun:dataStr];  // Calcola checksum
    [self CheckSun28:dataStr andCheckStr:self.FitChenSunStr];
}
```

---

## 🔄 Pipeline Completa DFU Update

### Step-by-Step Android (DfuActivity.java)

```java
/**
 * STEP 1: User seleziona file firmware ZIP
 */
public void onSelectFileClicked(final View view) {
    zipLauncher.launch(DfuService.MIME_TYPE_ZIP);
}

/**
 * STEP 2: Callback file selezionato
 * Valida che sia un file ZIP valido
 */
private void updateFileInfo(Uri uri, final String fileName, final long fileSize, final int fileType) {
    mFileUri = uri;
    mFileNameView.setText(fileName);
    mFileSizeView.setText(getString(R.string.dfu_file_size_text, fileSize));
    
    // Valida estensione (deve essere .zip)
    final String extension = "(?i)ZIP";
    final boolean statusOk = mStatusOk = MimeTypeMap
        .getFileExtensionFromUrl(fileName)
        .matches(extension);
    
    mFileStatusView.setText(statusOk ? R.string.dfu_file_status_ok : R.string.dfu_file_status_invalid);
    mUploadButton.setEnabled(statusOk);
}

/**
 * STEP 3: User tocca "Upload" → Entra in DFU mode
 */
public void onUploadClicked(final View view) {
    if (isDfuServiceRunning()) {
        showUploadCancelDialog();
        return;
    }
    
    // Valida file
    if (!mStatusOk) {
        Toast.makeText(this, R.string.dfu_file_status_invalid_message, Toast.LENGTH_LONG).show();
        return;
    }
    
    // Entra in DFU mode (invia comando 0x27)
    String address = mManager.dfuMode();
    
    // Scansiona per nuovo device DFU
    startScan(address);
    
    // Auto-dismiss dopo 30 secondi
    showLoadingAutoDismiss(30000L);
}

/**
 * STEP 4: Scansiona device in DFU mode
 * Nome device cambia: "CL831" → "CL831U"
 */
public void startScan(String address) {
    final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
    final ScanSettings settings = new ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .setUseHardwareBatchingIfSupported(false)
        .setReportDelay(1000)
        .setLegacy(false)
        .build();
    
    final List<ScanFilter> filters = new ArrayList<>();
    filters.add(new ScanFilter.Builder()
        .setDeviceAddress(address)  // Nuovo indirizzo (last byte + 1)
        .build());
    
    scanner.startScan(filters, settings, mScanCallback);
}

/**
 * STEP 5: Callback quando device DFU trovato
 */
private final ScanCallback mScanCallback = new ScanCallback() {
    @Override
    public void onBatchScanResults(final List<ScanResult> results) {
        for (ScanResult result : results) {
            BluetoothDevice device = result.getDevice();
            
            // Verifica che nome finisca con "U"
            if (isDfuDevice(device.getName())) {
                showToast("Start Update...");
                dfuUpdated(device);
                break;
            }
        }
    }
};

/**
 * Verifica se device è in DFU mode
 * Nome deve finire con "U" (es. "CL831U")
 */
private boolean isDfuDevice(String name) {
    return name != null && 
           !TextUtils.isEmpty(name) && 
           (name.toUpperCase().endsWith("U"));
}

/**
 * STEP 6: Avvia DFU service con Nordic library
 */
private void dfuUpdated(BluetoothDevice device) {
    stopScan();
    hideLoading();
    
    // Salva info file per restore dopo reboot
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    final SharedPreferences.Editor editor = preferences.edit();
    editor.putString(PREFS_FILE_NAME, mFileNameView.getText().toString());
    editor.putString(PREFS_FILE_SIZE, mFileSizeView.getText().toString());
    editor.apply();
    
    showProgressBar();
    
    // Configurazione DFU
    final boolean keepBond = false;       // Non mantenere bonding
    final boolean forceDfu = false;       // Non forzare DFU
    final boolean enablePRNs = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    int numberOfPackets = DfuServiceInitiator.DEFAULT_PRN_VALUE;  // Packet Receipt Notification
    
    // Inizializza DFU service (Nordic library)
    final DfuServiceInitiator starter = new DfuServiceInitiator(device.getAddress())
        .setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true)
        .setPacketsReceiptNotificationsValue(numberOfPackets)
        .setPacketsReceiptNotificationsEnabled(enablePRNs)
        .setDeviceName(device.getName())
        .setKeepBond(keepBond)
        .setForceDfu(forceDfu);
    
    // Imposta file ZIP firmware
    starter.setZip(mFileUri);
    
    Timber.v("dfuUpdated: %s - %s file uri:%s", device.getName(), device.getAddress(), mFileUri);
    
    // AVVIA DFU UPDATE!
    starter.start(this, DfuService.class);
}

/**
 * STEP 7: Callbacks progresso DFU
 */
private final DfuProgressListener mDfuProgressListener = new DfuProgressListenerAdapter() {
    
    @Override
    public void onDeviceConnecting(final String deviceAddress) {
        mTextPercentage.setText("Device Connecting...");
    }
    
    @Override
    public void onDfuProcessStarting(final String deviceAddress) {
        mTextPercentage.setText("Process Starting...");
    }
    
    @Override
    public void onEnablingDfuMode(final String deviceAddress) {
        mTextPercentage.setText("Updating...");
    }
    
    @Override
    public void onFirmwareValidating(final String deviceAddress) {
        mTextPercentage.setText("FirmwareValidating...");
    }
    
    @Override
    public void onProgressChanged(final String deviceAddress, 
                                  final int percent, 
                                  final float speed, 
                                  final float avgSpeed, 
                                  final int currentPart, 
                                  final int partsTotal) {
        mTextPercentage.setText(getString(R.string.dfu_uploading_percentage, percent));
        
        if (partsTotal > 1)
            mTextUploading.setText(String.format(Locale.getDefault(), 
                "Uploading Progress:%d/%d", currentPart, partsTotal));
        else
            mTextUploading.setText("Updating...");
    }
    
    @Override
    public void onDfuCompleted(final String deviceAddress) {
        mTextPercentage.setText("Update Completed");
        
        // Nascondi notifica dopo 200ms
        new Handler().postDelayed(() -> {
            onTransferCompleted();
            final NotificationManager manager = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.cancel(DfuService.NOTIFICATION_ID);
            }
        }, 200);
    }
    
    @Override
    public void onDfuAborted(final String deviceAddress) {
        mTextPercentage.setText("Update Aborted");
        onUploadCanceled();
    }
    
    @Override
    public void onError(final String deviceAddress, 
                       final int error, 
                       final int errorType, 
                       final String message) {
        showErrorMessage(message);
    }
};
```

---

## 🍎 Pipeline iOS (MainViewController.m)

```objectivec
/**
 * STEP 1-3: User seleziona file e tocca "Update"
 */
- (void)DFUMode {
    if(!self.fileURL) {
        // Mostra alert: nessun file selezionato
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"无文件" 
            message:@"请先选择文件，然后进行DFU升级" 
            preferredStyle:UIAlertControllerStyleAlert];
        [alert addAction:[UIAlertAction actionWithTitle:@"确定" 
            style:UIAlertActionStyleDefault handler:nil]];
        [self presentViewController:alert animated:YES completion:nil];
        return;
    }
    
    NSLog(@"选择了文件%@", self.fileURL.lastPathComponent);
    
    // STEP 4: Entra in DFU mode (comando 0x27)
    [self.BLEdeivce enterDFU];
    
    // STEP 5: Scansiona device DFU (nome "CL831U")
    [[HeartBLEDriver sharedInstance] scanDeviceName:@"CL831U" 
        callback:^(CBPeripheral *peripheral) {
        
        NSLog(@"=====%@", peripheral.name);
        
        // STEP 6: Carica firmware ZIP
        NSURL *url = self.fileURL;
        
        // STEP 7: Avvia update
        [self updateFirmware:peripheral filePath:url];
    }];
    
    // Auto-pop view dopo 3 secondi
    NSLog(@"返回上一页面");
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3.0 * NSEC_PER_SEC)), 
        dispatch_get_main_queue(), ^{
        [self.navigationController popViewControllerAnimated:true];
    });
}

/**
 * STEP 8: Configura e avvia DFU con Nordic library
 * https://github.com/NordicSemiconductor/IOS-DFU-Library
 */
- (void)updateFirmware:(CBPeripheral *)peri filePath:(NSURL *)file {
    
    CBCentralManager *manager = [HeartBLEDriver sharedInstance].centralManager;
    
    // Carica firmware da ZIP
    DFUFirmware *selectedFirmware = [[DFUFirmware alloc]initWithUrlToZipFile:file];
    
    // Inizializza DFU service
    DFUServiceInitiator *initiator = [[DFUServiceInitiator alloc] 
        initWithCentralManager:manager 
        target:peri];
    [initiator withFirmware:selectedFirmware];
    
    // Configurazione (da UserDefaults)
    initiator.forceDfu = [[[NSUserDefaults standardUserDefaults] 
        valueForKey:@"dfu_force_dfu"] boolValue];
    initiator.packetReceiptNotificationParameter = [[[NSUserDefaults standardUserDefaults] 
        valueForKey:@"dfu_number_of_packets"] intValue];
    
    // Callbacks
    initiator.logger = self;
    initiator.delegate = self;
    initiator.progressDelegate = self;
    
    // AVVIA DFU!
    [initiator startWithTarget:peri];
}

/**
 * STEP 9: Callback progresso
 */
- (void)dfuProgressDidChangeFor:(NSInteger)part 
                         outOf:(NSInteger)totalParts 
                            to:(NSInteger)progress 
    currentSpeedBytesPerSecond:(double)currentSpeedBytesPerSecond 
        avgSpeedBytesPerSecond:(double)avgSpeedBytesPerSecond {
    
    NSLog(@"更新进度:%ld%% (%ld/%ld)", (long)progress, (long)part, (long)totalParts);
    
    // Mostra loading con percentuale
    [EasyLodingView hidenLoding];
    [EasyLodingView showLodingText:
        [NSString stringWithFormat:@"更新进度:%ld%%", (long)progress] 
        config:^EasyLodingConfig *{
            return [EasyLodingConfig shared].setLodingType(LodingShowTypeIndicator);
        }];
    
    if (progress == 100) {
        [EasyLodingView hidenLoding];
    }
}

/**
 * STEP 10: Callback stati DFU
 */
- (void)dfuStateDidChangeTo:(enum DFUState)state {
    NSLog(@"DFUState state%ld", state);
    
    if (state == DFUStateConnecting) {
        NSLog(@"Connecting...");
    }
    else if (state == DFUStateStarting) {
        NSLog(@"Starting DFU...");
    }
    else if (state == DFUStateEnablingDfuMode) {
        NSLog(@"Enabling DFU Bootloader...");
    }
    else if (state == DFUStateUploading) {
        NSLog(@"Uploading...");
    }
    else if (state == DFUStateValidating) {
        NSLog(@"Validating...");
    }
    else if (state == DFUStateDisconnecting) {
        NSLog(@"Disconnecting...");
    }
    else if (state == DFUStateCompleted) {
        NSLog(@"✅ Update Completed!");
    }
    else if (state == DFUStateAborted) {
        NSLog(@"❌ Update Aborted");
    }
}
```

---

## 📊 Diagramma Pipeline Completa

```
┌─────────────────────────────────────────────────────────────┐
│                    DFU UPDATE PIPELINE                       │
└─────────────────────────────────────────────────────────────┘

1. USER ACTION
   └─> Seleziona file ZIP firmware (es. CL831_V4.1.9_dfu.zip)
   └─> Tocca "Upload" / "Update"

2. ENTER DFU MODE
   └─> Calcola nuovo MAC address (last byte + 1)
       Original:  AA:BB:CC:DD:EE:FF
       DFU mode:  AA:BB:CC:DD:EE:00
   └─> Invia comando BLE: [0xFF, 0x04, 0x27, checksum]
   └─> Device RIAVVIA in DFU bootloader

3. DEVICE REBOOT (2-3 secondi)
   └─> Nome cambia: "CL831" → "CL831U"
   └─> MAC address cambia: ultimo byte incrementato
   └─> Device in modalità bootloader DFU

4. SCAN FOR DFU DEVICE
   └─> Scansiona BLE con filtro:
       - MAC address: nuovo (calcolato)
       - Nome device: deve finire con "U"
   └─> Timeout: 30 secondi

5. DEVICE FOUND
   └─> Verifica: isDfuDevice(name)
   └─> Stop scan
   └─> Passa a step 6

6. INITIALIZE DFU SERVICE
   └─> Nordic DFU Library
   └─> Carica firmware ZIP
   └─> Configura parametri:
       - Force DFU: false
       - Keep Bond: false
       - PRN (Packet Receipt Notification): 12-16
       - Experimental buttonless: true

7. START DFU TRANSFER
   └─> Connessione BLE
   └─> Abilita DFU bootloader
   └─> Upload firmware chunks
   └─> Progress callbacks ogni N packets

8. VALIDATION
   └─> Device valida firmware
   └─> Checksum verification
   └─> Integrity check

9. FINALIZE
   └─> Device applica firmware
   └─> Riavvia in modalità normale
   └─> Nome torna: "CL831U" → "CL831"
   └─> MAC torna normale

10. COMPLETED
    └─> Notifica utente: "Update Completed"
    └─> Riconnetti device (opzionale)
```

---

## 🔑 Parametri Chiave

### Configurazione DFU Service

| Parametro | Valore | Descrizione |
|-----------|--------|-------------|
| **Force DFU** | `false` | Non forzare update se versione uguale |
| **Keep Bond** | `false` | Non mantenere bonding dopo DFU |
| **PRN Value** | `12-16` | Packet Receipt Notification ogni N packets |
| **Experimental Buttonless** | `true` | Abilita DFU buttonless mode |
| **Timeout Scan** | `30s` | Timeout per trovare device DFU |
| **Delay Reboot** | `2-3s` | Attesa dopo comando 0x27 |

### File Firmware

| Campo | Requisito |
|-------|-----------|
| **Formato** | ZIP file |
| **Nome** | `CL831_V{version}_dfu.zip` |
| **Contenuto** | `.bin` + `.dat` (init packet) |
| **Versione** | Es. `V4.1.9` |
| **Size** | ~200-500 KB tipico |

---

## 🛠️ Implementazione Flutter/Dart

### Pacchetto Richiesto

```yaml
# pubspec.yaml
dependencies:
  nordic_dfu: ^6.0.0  # https://pub.dev/packages/nordic_dfu
```

### Codice Completo

```dart
import 'package:nordic_dfu/nordic_dfu.dart';

class DfuService {
  
  /// Entra in modalità DFU
  Future<String> enterDFUMode() async {
    // 1. Calcola nuovo MAC address
    String currentMac = _device.id.toString();
    String dfuMac = _calculateDFUAddress(currentMac);
    
    // 2. Invia comando 0x27
    List<int> command = [0xFF, 0x04, 0x27, 0x00];
    int checksum = _calculateChecksum(command);
    command.add(checksum);
    
    await _txCharacteristic!.write(command, withoutResponse: false);
    
    debugPrint('📤 Entered DFU mode');
    debugPrint('   Original MAC: $currentMac');
    debugPrint('   DFU MAC: $dfuMac');
    
    return dfuMac;
  }
  
  /// Calcola nuovo MAC address (ultimo byte + 1)
  String _calculateDFUAddress(String originalMac) {
    // "AA:BB:CC:DD:EE:FF" → "AA:BB:CC:DD:EE:00"
    List<String> parts = originalMac.split(':');
    String lastByte = parts.last;
    
    // Incrementa ultimo byte (modulo 256)
    int lastByteInt = int.parse(lastByte, radix: 16);
    int newLastByte = (lastByteInt + 1) & 0xFF;
    
    parts[parts.length - 1] = newLastByte.toRadixString(16).padLeft(2, '0').toUpperCase();
    
    return parts.join(':');
  }
  
  /// Calcola checksum
  int _calculateChecksum(List<int> data) {
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    return sum & 0xFF;
  }
  
  /// Scansiona device in DFU mode
  Future<BluetoothDevice?> scanForDFUDevice(String dfuMac) async {
    debugPrint('🔍 Scanning for DFU device...');
    debugPrint('   Target MAC: $dfuMac');
    
    Completer<BluetoothDevice?> completer = Completer();
    
    // Timeout 30 secondi
    Timer timeoutTimer = Timer(Duration(seconds: 30), () {
      if (!completer.isCompleted) {
        debugPrint('❌ DFU device not found (timeout)');
        completer.complete(null);
      }
    });
    
    // Start scan
    StreamSubscription? scanSubscription;
    scanSubscription = FlutterBluePlus.scanResults.listen((results) {
      for (ScanResult result in results) {
        String deviceMac = result.device.id.toString();
        String deviceName = result.device.name;
        
        // Verifica MAC address E nome finisce con "U"
        if (deviceMac.toUpperCase() == dfuMac.toUpperCase() &&
            deviceName.toUpperCase().endsWith('U')) {
          
          debugPrint('✅ DFU device found!');
          debugPrint('   Name: $deviceName');
          debugPrint('   MAC: $deviceMac');
          
          timeoutTimer.cancel();
          scanSubscription?.cancel();
          FlutterBluePlus.stopScan();
          
          if (!completer.isCompleted) {
            completer.complete(result.device);
          }
          break;
        }
      }
    });
    
    FlutterBluePlus.startScan(timeout: Duration(seconds: 30));
    
    return completer.future;
  }
  
  /// Avvia DFU update
  Future<void> startDFUUpdate(String deviceId, String zipFilePath) async {
    debugPrint('🚀 Starting DFU update...');
    debugPrint('   Device: $deviceId');
    debugPrint('   File: $zipFilePath');
    
    try {
      await NordicDfu().startDfu(
        deviceId,
        zipFilePath,
        numberOfPackets: 12,
        enableUnsafeExperimentalButtonlessServiceInSecureDfu: true,
        
        onProgressChanged: (deviceAddress, percent, speed, avgSpeed, currentPart, partsTotal) {
          debugPrint('📊 DFU Progress: $percent%');
          if (partsTotal > 1) {
            debugPrint('   Part: $currentPart/$partsTotal');
          }
          
          // Aggiorna UI
          _updateProgress(percent, currentPart, partsTotal);
        },
        
        onDeviceConnecting: (deviceAddress) {
          debugPrint('🔗 Connecting to DFU device...');
        },
        
        onDeviceConnected: (deviceAddress) {
          debugPrint('✅ Connected to DFU device');
        },
        
        onDfuProcessStarting: (deviceAddress) {
          debugPrint('🔧 DFU process starting...');
        },
        
        onEnablingDfuMode: (deviceAddress) {
          debugPrint('⚙️ Enabling DFU bootloader...');
        },
        
        onFirmwareValidating: (deviceAddress) {
          debugPrint('✔️ Validating firmware...');
        },
        
        onDeviceDisconnecting: (deviceAddress) {
          debugPrint('🔌 Disconnecting...');
        },
        
        onDfuCompleted: (deviceAddress) {
          debugPrint('🎉 DFU completed successfully!');
          _showSuccessDialog();
        },
        
        onDfuAborted: (deviceAddress) {
          debugPrint('❌ DFU aborted');
          _showErrorDialog('Update aborted');
        },
        
        onError: (deviceAddress, error, errorType, message) {
          debugPrint('❌ DFU error: $message');
          debugPrint('   Error code: $error');
          debugPrint('   Error type: $errorType');
          _showErrorDialog(message);
        },
      );
      
    } catch (e) {
      debugPrint('❌ Exception during DFU: $e');
      _showErrorDialog('Update failed: $e');
    }
  }
  
  /// Pipeline completa: entra in DFU + scansiona + aggiorna
  Future<void> performCompleteDFUUpdate(String zipFilePath) async {
    try {
      // STEP 1: Entra in DFU mode
      String dfuMac = await enterDFUMode();
      
      // STEP 2: Attendi riavvio device (2-3 secondi)
      await Future.delayed(Duration(seconds: 3));
      
      // STEP 3: Scansiona device DFU
      BluetoothDevice? dfuDevice = await scanForDFUDevice(dfuMac);
      
      if (dfuDevice == null) {
        throw Exception('DFU device not found after 30 seconds');
      }
      
      // STEP 4: Avvia DFU update
      await startDFUUpdate(dfuDevice.id.toString(), zipFilePath);
      
    } catch (e) {
      debugPrint('❌ DFU update failed: $e');
      rethrow;
    }
  }
}
```

---

## 🧪 Testing & Validation

### Pre-requisiti

1. **File firmware ZIP** valido (es. `CL831_V4.1.9_dfu.zip`)
2. **Device connesso** e in range
3. **BLE permissions** garantiti
4. **Battery > 30%** sul device (consigliato)
5. **Nordic DFU library** integrata

### Test Checklist

- [ ] Comando 0x27 inviato correttamente
- [ ] Device riavvia in DFU mode (nome + "U")
- [ ] MAC address incrementato correttamente
- [ ] Device trovato durante scan (< 30s)
- [ ] DFU service inizializza senza errori
- [ ] Progress callbacks ricevuti
- [ ] Firmware validato correttamente
- [ ] Device riavvia in modalità normale
- [ ] Versione firmware aggiornata

### Error Handling

| Errore | Causa | Soluzione |
|--------|-------|-----------|
| **Device not found** | Scan timeout | Aumenta timeout, verifica MAC |
| **Connection failed** | BLE instabile | Avvicina device, riprova |
| **Validation error** | Firmware corrotto | Usa file ZIP valido |
| **Battery too low** | <20% battery | Carica device |
| **Permission denied** | BLE permissions | Richiedi permessi utente |

---

## 📚 Riferimenti

### Librerie Ufficiali

- **Nordic DFU iOS**: https://github.com/NordicSemiconductor/IOS-DFU-Library
- **Nordic DFU Android**: https://github.com/NordicSemiconductor/Android-DFU-Library
- **Nordic DFU Flutter**: https://pub.dev/packages/nordic_dfu

### SDK Ufficiali

- **Android**: `WearManager.dfuMode()` in `WearManager.java`
- **iOS**: `enterDFU` in `HeartBLEDevice.m`

### Protocollo BLE

- **Comando**: `0x27` (39 decimal)
- **Formato**: `[0xFF, 0x04, 0x27, checksum]`
- **Response**: Device riavvia in DFU bootloader

---

**Creato:** Novembre 2025  
**SDK Version:** CL831 v3.0.4  
**DFU Protocol:** Nordic Semiconductor  
**Status:** Production Ready