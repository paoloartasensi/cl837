# 📱 DFU Update - Anteprima UI

## Dashboard con Bottone Firmware Update

```
┌─────────────────────────────────────────────────┐
│  ←  Dashboard        [🔄] [↻]                   │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │  📱 CL831                                 │ │
│  │     Software Version: 4.0.1               │ │
│  │  ──────────────────────────────────────   │ │
│  │  SDK       RSSI      Battery              │ │
│  │  v3.0.4    -65 dB    85%                  │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ... (altri dati dashboard)                    │
│                                                 │
└─────────────────────────────────────────────────┘
      ↑
      Tocca [🔄] per aprire Firmware Update Screen
```

---

## Firmware Update Screen

```
┌─────────────────────────────────────────────────┐
│  ← Firmware Update                              │
├─────────────────────────────────────────────────┤
│  ⚠️  Important                                  │
│     Keep device close and charged during update │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │  🔄  Firmware Update                      │ │
│  │       CL837/CL831 Device                  │ │
│  │                                           │ │
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  Current Version:    4.0.1          │ │ │
│  │  │  Available Version:  4.1.9          │ │ │
│  │  └─────────────────────────────────────┘ │ │
│  │                                           │ │
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  [🔄 Start Firmware Update]         │ │ │
│  │  └─────────────────────────────────────┘ │ │
│  │                                           │ │
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  [↻ Refresh Version]                │ │ │
│  │  └─────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │  ℹ️  Update Instructions                  │ │
│  │                                           │ │
│  │  ① Keep device within 1 meter range      │ │
│  │  ② Ensure battery level > 30%            │ │
│  │  ③ Do not close app during update        │ │
│  │  ④ Update takes 2-5 minutes              │ │
│  │  ⑤ Device will restart automatically     │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │  💻 Technical Details                     │ │
│  │                                           │ │
│  │  Protocol:  Nordic DFU                    │ │
│  │  Package:   FW_V419.zip                   │ │
│  │  Device:    CL837/CL831                   │ │
│  │  Mode:      DFU Bootloader (0x27)         │ │
│  │                                           │ │
│  │  For more info: docs/DFU_UPDATE_PIPELINE  │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
└─────────────────────────────────────────────────┘
```

---

## Confirm Dialog

```
┌─────────────────────────────────────┐
│  🔄 Firmware Update                 │
├─────────────────────────────────────┤
│                                     │
│  Do you want to update the firmware?│
│                                     │
│  Current Version:    4.0.1          │
│  Target Version:     4.1.9          │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ ⚠️  Keep device close during  │ │
│  │     update (~2-5 minutes)     │ │
│  └───────────────────────────────┘ │
│                                     │
│     [Cancel]        [Update]        │
│                                     │
└─────────────────────────────────────┘
```

---

## Progress States

### 1. Preparing File
```
┌─────────────────────────────────────────────────┐
│  ┌───────────────────────────────────────────┐ │
│  │  🔄  Firmware Update                      │ │
│  │       CL837/CL831 Device                  │ │
│  │                                           │ │
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  Current Version:    4.0.1          │ │ │
│  │  │  Available Version:  4.1.9          │ │ │
│  │  └─────────────────────────────────────┘ │ │
│  │                                           │ │
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  ⏳ Preparing firmware file...      │ │ │
│  │  │     Copying from assets...          │ │ │
│  │  └─────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────┘ │
└─────────────────────────────────────────────────┘
```

### 2. Entering DFU Mode
```
┌─────────────────────────────────────────────────┐
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  ⏳ Entering DFU mode...            │ │ │
│  │  │     Sending command 0x27...         │ │ │
│  │  └─────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────┘
```

### 3. Scanning for DFU Device
```
┌─────────────────────────────────────────────────┐
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  ⏳ Scanning for DFU device...      │ │ │
│  │  │     Looking for "CL831U"...         │ │ │
│  │  └─────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────┘
```

### 4. Uploading Firmware
```
┌─────────────────────────────────────────────────┐
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  ⏳ Uploading firmware...           │ │ │
│  │  │     Uploading firmware: 47%         │ │ │
│  │  │                                     │ │ │
│  │  │  ▰▰▰▰▰▰▰▰▰▱▱▱▱▱▱▱▱▱▱▱  47%        │ │ │
│  │  │                                     │ │ │
│  │  │  Part 1/1                           │ │ │
│  │  │  Speed: 23.5 KB/s                   │ │ │
│  │  └─────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────┘
```

### 5. Validating Firmware
```
┌─────────────────────────────────────────────────┐
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  ⏳ Validating firmware...          │ │ │
│  │  │     Checking integrity...           │ │ │
│  │  │                                     │ │ │
│  │  │  ▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰  100%       │ │ │
│  │  └─────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────┘
```

---

## Success Dialog

```
┌─────────────────────────────────────┐
│  ✅ Update Completed                │
├─────────────────────────────────────┤
│                                     │
│  Firmware updated successfully!     │
│                                     │
│  Device will restart in normal mode.│
│                                     │
│              [OK]                   │
│                                     │
└─────────────────────────────────────┘
```

---

## Error States

### Scan Timeout Error
```
┌─────────────────────────────────────────────────┐
│  │  ┌─────────────────────────────────────┐ │ │
│  │  │  ❌ DFU device not found (timeout)  │ │ │
│  │  └─────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────┘
```

### Upload Failed Error
```
┌─────────────────────────────────────┐
│  ❌ Update Failed                   │
├─────────────────────────────────────┤
│                                     │
│  Connection lost during upload      │
│                                     │
│              [OK]                   │
│                                     │
└─────────────────────────────────────┘
```

---

## Esempio Codice Standalone

### Widget in qualsiasi schermata

```dart
import 'package:flutter/material.dart';
import 'widgets/dfu_update_widget.dart';

class MyCustomScreen extends StatelessWidget {
  final ChileafExtendedService service;
  final BluetoothDevice device;
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Device Settings')),
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Altri widget...
            
            // DFU Update Widget
            DfuUpdateWidget(
              service: service,
              device: device,
            ),
            
            // Altri widget...
          ],
        ),
      ),
    );
  }
}
```

### Navigazione a schermata dedicata

```dart
import 'package:flutter/material.dart';
import 'screens/firmware_update_screen.dart';

// Da qualsiasi punto dell'app
ElevatedButton(
  onPressed: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => FirmwareUpdateScreen(
          service: _chileafService,
          device: _device,
        ),
      ),
    );
  },
  child: Text('Firmware Update'),
)
```

### Utilizzo programmatico

```dart
import 'services/dfu_service.dart';

class MyDfuController {
  late DfuService _dfuService;
  
  void initDfu() {
    _dfuService = DfuService(
      chileafService: _chileafService,
      device: _device,
    );
    
    // Ascolta progresso
    _dfuService.progressStream.listen((progress) {
      print('DFU State: ${progress.state}');
      print('Progress: ${progress.percent}%');
      
      if (progress.state == DfuState.completed) {
        print('✅ Update completed!');
      } else if (progress.state == DfuState.error) {
        print('❌ Update failed: ${progress.message}');
      }
    });
  }
  
  Future<void> startUpdate() async {
    // Avvia update
    DfuResult result = await _dfuService.performDfuUpdate(
      assetPath: 'lib/assets/fw/FW_V419.zip',
      targetVersion: '4.1.9',
    );
    
    if (result.success) {
      // Success handling
      showSuccessNotification();
    } else {
      // Error handling
      showErrorDialog(result.errorMessage);
    }
  }
  
  void dispose() {
    _dfuService.dispose();
  }
}
```

---

## Colori UI

| Elemento | Colore | Uso |
|----------|--------|-----|
| **Header Icon** | Orange | Firmware update icon |
| **Primary Button** | Orange | "Start Firmware Update" |
| **Success** | Green | Versione target, completamento |
| **Current Version** | Blue | Versione attuale |
| **Progress Bar** | Blue | Upload progress |
| **Warning Banner** | Orange gradient | Important message |
| **Error** | Red | Error states e messaggi |
| **Info** | Blue | Istruzioni e dettagli tecnici |
| **Background** | Grey.shade50 | Technical details card |

---

## Stati Icone

| Stato | Icona | Colore |
|-------|-------|--------|
| **Idle** | `system_update` | Orange |
| **Loading** | `CircularProgressIndicator` | Blue |
| **Uploading** | `upload_file` | Blue |
| **Success** | `check_circle` | Green |
| **Error** | `error` | Red |
| **Warning** | `warning_amber` | Orange |
| **Info** | `info_outline` | Blue |
| **Refresh** | `refresh` | Grey |

---

**UI Design:** Material Design 3  
**Responsiveness:** Adaptive per tablet/phone  
**Accessibility:** Screen reader compatible  
**Dark Mode:** Support (colors.adaptive)
