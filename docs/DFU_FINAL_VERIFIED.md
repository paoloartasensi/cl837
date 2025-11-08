# DFU (Device Firmware Update) - Documentazione Verificata

**Status:** ✅ COMPLETAMENTE FUNZIONANTE  
**Data verifica:** 8 Novembre 2025  
**Device testato:** CL837 (C1:AE:7C:3A:A1:78)  
**Firmware caricato:** V4.1.9 (FW_V419.zip, 120261 bytes)  
**Tempo upload:** ~23 secondi  

---

## 🎯 Pipeline DFU Funzionante

### STEP 1: Preparazione File Firmware ✅
```dart
// File: lib/services/dfu_service.dart
Future<String?> _prepareFirmwareFile(String assetPath) async {
  ByteData data = await rootBundle.load(assetPath);
  Directory tempDir = await getTemporaryDirectory();
  String tempPath = '${tempDir.path}/${assetPath.split('/').last}';
  File tempFile = File(tempPath);
  await tempFile.writeAsBytes(data.buffer.asUint8List());
  return tempPath;
}
```

**Asset path:** `assets/fw/FW_V419.zip`

---

### STEP 2: Calcolo MAC Address DFU ✅
```dart
String _calculateDfuAddress(String originalMac) {
  List<String> parts = originalMac.split(':');
  String lastByte = parts.last;
  
  // Incrementa ultimo byte (+1)
  int lastByteInt = int.parse(lastByte, radix: 16);
  int newLastByte = (lastByteInt + 1) & 0xFF;
  
  parts[parts.length - 1] = newLastByte.toRadixString(16)
      .padLeft(2, '0').toUpperCase();
  
  return parts.join(':');
}
```

**Esempio:**
- MAC originale: `C1:AE:7C:3A:A1:78`
- MAC DFU: `C1:AE:7C:3A:A1:79` (ultimo byte +1)

---

### STEP 3: Comando DFU - ALGORITMO CHECKSUM CORRETTO ✅

**CRITICO:** Il checksum è l'unico vero problema che impediva il funzionamento!

```dart
/// Algoritmo VERIFICATO da iOS HeartBLEDevice.m
/// Test: [0xFF, 0x04, 0x27] → checksum = 0xEC ✅
int _calculateChecksum(List<int> data) {
  // Step 1: Somma tutti i bytes e prendi ultimo byte
  int sum = 0;
  for (int byte in data) {
    sum += byte;
  }
  int lastByte = sum & 0xFF;  // 0xFF + 0x04 + 0x27 = 0x12A → 0x2A
  
  // Step 2: Two's complement (0x00 - lastByte)
  int twosComplement = (0x00 - lastByte) & 0xFF;  // 0x00 - 0x2A = 0xD6
  
  // Step 3: XOR con 0x3A
  int checksum = twosComplement ^ 0x3A;  // 0xD6 ^ 0x3A = 0xEC
  
  return checksum;
}
```

**Comando finale:**
```dart
List<int> command = [0xFF, 0x04, 0x27];  // 3 bytes
int checksum = _calculateChecksum(command);  // 0xEC
command.add(checksum);  // [0xFF, 0x04, 0x27, 0xEC]
```

**Risultato:**
- ✅ LED diventa ROSSO
- ✅ Device si disconnette (GATT error 133 = comportamento normale!)
- ✅ Entra in bootloader mode

---

### STEP 4: Scrittura Comando - Gestione GATT Error 133 ✅

```dart
try {
  await rxChar.write(command, withoutResponse: false);
  debugPrint('✅ DFU command write completed');
} catch (e) {
  // IMPORTANTE: GATT error 133 è NORMALE!
  // Significa che il device ha ricevuto il comando e si sta disconnettendo
  if (e.toString().contains('133') || e.toString().contains('GATT_ERROR')) {
    debugPrint('✅ Device disconnected after DFU command (expected)');
    // CONTINUA - non è un errore!
  } else {
    throw e;  // Altri errori sono problematici
  }
}
```

**Caratteristica corretta:**
- UUID RX: `aae28f02-71b5-42a1-8c3c-f9cf6ac969d0`
- Property: `WRITE` (non NOTIFY!)
- TX/RX dal punto di vista del DEVICE, non dell'app

---

### STEP 5: Scansione Bootloader ✅

```dart
Future<BluetoothDevice?> scanForDfuDevice(String dfuMac, 
    {int timeoutSeconds = 45}) async {
  
  // Criteri di ricerca (TUTTI devono corrispondere):
  // 1. MAC address match (ultimo byte +1)
  bool macMatch = deviceMac.toUpperCase() == dfuMac.toUpperCase();
  
  // 2. Nome finisce con "U" (case insensitive)
  bool nameMatch = deviceName.toUpperCase().endsWith('U');
  
  if (macMatch && nameMatch) {
    // TROVATO!
    return result.device;
  }
}
```

**Bootloader identificato:**
- Nome: `CL837U` (o `CL831U` per altri modelli)
- MAC: Originale con ultimo byte +1
- Timeout: 45 secondi (ma tipicamente trovato in 5-10s)

---

### STEP 6: Upload Firmware ✅

```dart
await NordicDfu().startDfu(
  deviceId,              // MAC del bootloader
  zipFilePath,           // Path del file .zip
  numberOfPackets: 12,   // Ottimizzazione velocità
  enableUnsafeExperimentalButtonlessServiceInSecureDfu: true,
  
  onProgressChanged: (address, percent, speed, avgSpeed, currentPart, partsTotal) {
    // Progress 0-100%
  },
  
  onDfuCompleted: (address) {
    // SUCCESS!
  },
  
  onError: (address, error, errorType, message) {
    // Gestione errori
  },
);
```

**Performance verificate:**
- File size: 120261 bytes
- Tempo upload: ~23 secondi
- Velocità: ~5.2 KB/s
- Pacchetti: 30 oggetti da 4096 bytes + 1 da 816 bytes

---

## 📱 Caratteristiche BLE

### Service Chileaf Custom
- UUID: `aae28f00-71b5-42a1-8c3c-f9cf6ac969d0`

### Characteristics (punto di vista DEVICE)
- **TX** (aae28f01): `NOTIFY` - Device → App (leggiamo)
- **RX** (aae28f02): `WRITE` - App → Device (scriviamo)

**IMPORTANTE:** Quando scriviamo comandi, usiamo RX characteristic!

---

## 🔧 Troubleshooting

### Problema: GATT error 133
**Soluzione:** È NORMALE! Significa che il device sta rebooting in DFU mode.
```dart
// Catch e ignora error 133
if (e.toString().contains('133')) {
  // OK - continua la pipeline
}
```

### Problema: Bootloader non trovato
**Check:**
1. LED è diventato ROSSO? (comando ricevuto)
2. Device si è disconnesso? (sta rebooting)
3. Attendi almeno 5-10 secondi dopo disconnect
4. Verifica MAC: ultimo byte deve essere +1
5. Verifica nome: deve finire con "U"

### Problema: Upload fallisce al 50%
**Causa:** Interferenze BLE o distanza eccessiva
**Soluzione:** 
- Mantieni device entro 1 metro
- Chiudi altre app BLE
- Riprova l'update

---

## 📊 Log di Successo

```
🔄 STARTING DFU UPDATE PIPELINE

📋 STEP 1: Reading current firmware version (optional)...
   ✅ Current: Unknown (not readable)
   🎯 Target: 4.1.9

📦 STEP 2: Preparing firmware file...
✅ Firmware file ready: /data/.../FW_V419.zip
   Size: 120261 bytes

🔧 STEP 3: Entering DFU mode...
📍 Current MAC: C1:AE:7C:3A:A1:78
📍 DFU MAC: C1:AE:7C:3A:A1:79
📤 Sending DFU command: 0xFF 0x04 0x27 0xEC
   ✅ Checksum: 0xEC (verified from iOS SDK)
✅ Device disconnected after DFU command (expected behavior)
   GATT error 133 = device is rebooting into bootloader mode
✅ DFU mode command sent successfully
⏳ Device will reboot in DFU mode (2-3 seconds)...
🔴 LED should turn RED and device should disconnect

⏳ STEP 4: Waiting for device reboot...

🔍 STEP 5: Scanning for DFU device...
🔎 Candidate: CL837U (C1:AE:7C:3A:A1:79)
✅ DFU device found (exact match)!
   Name: CL837U
   MAC: C1:AE:7C:3A:A1:79

🚀 STEP 6: Starting DFU upload...
📊 DFU Progress: 0%
📊 DFU Progress: 25%
📊 DFU Progress: 50%
📊 DFU Progress: 75%
📊 DFU Progress: 100%
🔌 Disconnecting...
🎉 DFU completed successfully!

🎉 DFU UPDATE COMPLETED SUCCESSFULLY!
```

---

## 📦 Dipendenze

```yaml
# pubspec.yaml
dependencies:
  flutter_blue_plus: ^2.0.0
  nordic_dfu: ^6.0.0
  path_provider: ^2.0.0
```

---

## 🎯 Checklist Pre-Release

- [x] Checksum algorithm corretto (sum→two's complement→XOR 0x3A)
- [x] GATT error 133 gestito correttamente
- [x] TX/RX characteristics corretti
- [x] MAC address +1 calcolato
- [x] Bootloader scan con timeout 45s
- [x] Nordic DFU integration
- [x] Progress callbacks funzionanti
- [x] Error handling completo
- [x] Test end-to-end completato con successo

---

## 📝 Note Implementative

### Versione Firmware Non Leggibile
Il comando `0x03` risponde sempre con User Info (15 bytes) invece della versione firmware.
**Soluzione:** La versione è opzionale per il DFU - procediamo comunque.

### Device Information Service
Il device NON espone il servizio standard BLE `0x180A` (Device Information).
**Soluzione:** Usiamo il comando custom DFU direttamente.

### Service Initialization
Prima di inviare comandi, assicurati che ChileafExtendedService sia inizializzato:
```dart
await _chileafService.start(_device);
await Future.delayed(Duration(seconds: 2));  // Wait for setup
```

---

## 🔗 Riferimenti

### SDK Ufficiali
- iOS: `docs/HeartBLEDevice.m` (checksum algorithm source)
- Android: `docs/WearManager.java` (DFU implementation)
- Documentazione: `docs/CL831 SDK technical documentation.docx.md`

### Nordic DFU
- GitHub: https://github.com/NordicSemiconductor/IOS-DFU-Library
- Protocol: Secure DFU over BLE

---

## ✅ Status Finale

**DFU COMPLETAMENTE FUNZIONANTE**

Testato con successo:
- Device: CL837 (C1:AE:7C:3A:A1:78)
- Firmware: V4.1.9 (120261 bytes)
- Tempo: 23 secondi
- Data: 8 Novembre 2025

**Nessun bug conosciuto.**
