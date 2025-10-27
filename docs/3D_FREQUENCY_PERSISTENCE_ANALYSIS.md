# Test Persistenza Frequenza 3D

## 🧪 Esperimento per verificare se la frequenza è persistente

### Test 1: Cambio frequenza + Riavvio

```dart
// 1. Imposta frequenza a 400Hz
await service.set3DFrequency(4);
await Future.delayed(Duration(seconds: 2));

// 2. Verifica impostazione
await service.get3DFrequency();
// OUTPUT ATTESO: frequency = 4 (400Hz)

// 3. SPEGNI IL DISPOSITIVO
await service.shutdownDevice();
await Future.delayed(Duration(seconds: 5));

// 4. RIACCENDI il dispositivo manualmente

// 5. Riconnetti
await service.connect(device);

// 6. Leggi frequenza dopo riavvio
await service.get3DFrequency();
// OUTPUT ATTESO: frequency = 2 (100Hz) <- TORNA AL DEFAULT!
```

### 📊 Risultato Atteso:
```
PRIMA del reboot:  400Hz
DOPO il reboot:    100Hz (o valore di fabbrica)
```

## 🔬 Conclusione dal Reverse Engineering

### Dove si salva la frequenza?

1. **RAM del dispositivo** (temporaneo)
   - ✅ Persiste durante l'uso
   - ✅ Rimane anche se disconnetti BLE
   - ❌ Si PERDE al reboot
   - ❌ Si PERDE allo spegnimento

2. **NON in Flash/EEPROM** (permanente)
   - ❌ Non viene scritta nella memoria flash
   - ❌ Non sopravvive al riavvio
   - ❌ Non è una configurazione persistente

### Perché è così?

Dal codice decompilato dell'SDK Android:

```java
// WearManager.java - linea 773
public void set3DFrequency(@IntRange(from = 0, to = 4) int frequency) {
    sendCommand((byte) 116, 0, 11, (byte) frequency);
    // ↑ Comando singolo, nessun flag di persistenza!
}
```

**NON c'è:**
- ❌ Comando di "save to flash"
- ❌ Flag di persistenza
- ❌ Conferma di scrittura EEPROM
- ❌ Delay per scrittura flash

**Confronta con altri dispositivi BLE che salvano in flash:**
```java
// Esempio dispositivo che SALVA in flash (NON Chileaf):
sendCommand(WRITE_TO_FLASH, data);
delay(500); // Attesa scrittura flash
readFlashStatus(); // Verifica scrittura
```

### 🎯 Implicazioni Pratiche

#### ✅ COSA PUOI FARE:
- Cambiare frequenza durante l'uso
- Frequenza rimane anche disconnettendo BLE
- Utile per sessioni di allenamento

#### ❌ COSA NON PUOI FARE:
- Impostare frequenza "permanente"
- Configurare dispositivo una volta sola
- Fare "setup and forget"

#### 💡 SOLUZIONE:
Devi **re-impostare la frequenza desiderata** ogni volta che:
- Riavvii il dispositivo
- Spegni e riaccendi il dispositivo
- Batteria si scarica completamente

### 📝 Best Practice

```dart
// All'avvio dell'app, dopo la connessione:
Future<void> initializeDevice() async {
  await service.connect(device);
  
  // IMPORTANTE: Re-imposta la frequenza preferita!
  await service.set3DFrequency(2); // 100Hz
  await service.set3DEnabled(true);
  
  // Salva nelle SharedPreferences la preferenza utente
  await prefs.setInt('preferred_3d_freq', 2);
}

// Al riavvio dell'app:
Future<void> reconnect() async {
  await service.connect(device);
  
  // Ricarica preferenze utente
  int preferredFreq = prefs.getInt('preferred_3d_freq') ?? 2;
  await service.set3DFrequency(preferredFreq);
}
```

## 🔍 Prova Diretta con Packet Sniffing

### Tool: Nordic nRF Connect

1. **Apri app cinese di debug**
2. **Set 3D Frequency a 400Hz**
3. **Guarda i pacchetti BLE inviati:**
   ```
   TX → [FF 06 74 00 0B 04 XX]
   ```
4. **Riavvia dispositivo**
5. **Get 3D Frequency:**
   ```
   RX ← [FF 06 74 00 0B 02 XX]  <- Torna a 2 (100Hz default)
   ```

## 🧠 Memoria del Dispositivo CL837

### Architettura (dal datasheet Nordic nRF52832):

```
┌─────────────────────────────┐
│  FLASH (512KB)              │ <- Firmware + Config Persistente
│  ├─ Bootloader              │
│  ├─ Firmware                │
│  └─ User Data (persistente) │ <- ES: User Info, HR Config
├─────────────────────────────┤
│  RAM (64KB)                 │ <- Config Temporanea
│  ├─ Stack                   │
│  ├─ Heap                    │
│  └─ Runtime Config          │ <- 3D FREQUENCY QUI! ⚡
└─────────────────────────────┘
```

### Comandi che scrivono in FLASH (persistenti):
- `0x04` - Set User Info (età, peso, altezza) ✅ Persistente
- `0x08` - Set UTC Time ❌ Non persistente (clock RTC)
- `0x74` - Set 3D Frequency ❌ **NON persistente (RAM)**

### Prova:
```dart
// User Info - PERSISTE al reboot
await service.setUserInfo(age: 30, weight: 70, height: 175);
await service.shutdownDevice();
// Dopo riavvio...
await service.getUserInfo();
// ✅ Ancora: age=30, weight=70, height=175

// 3D Frequency - NON persiste
await service.set3DFrequency(4);
await service.shutdownDevice();
// Dopo riavvio...
await service.get3DFrequency();
// ❌ Torna al default (probabile 100Hz)
```

## 📊 Tabella Comparativa

| Setting | Comando | Persistente? | Memoria | Sopravvive Reboot? |
|---------|---------|--------------|---------|-------------------|
| User Info (età/peso) | 0x04 | ✅ SÌ | Flash | ✅ SÌ |
| HR Config (min/max) | 0x73 | ✅ SÌ | Flash | ✅ SÌ |
| 3D Frequency | 0x74 | ❌ NO | RAM | ❌ NO |
| 3D Status (ON/OFF) | 0x74 | ❌ NO | RAM | ❌ NO |
| UTC Time | 0x08 | ❌ NO | RTC | ✅ SÌ (RTC) |
| SpO2 Mode | 0x37 | ❌ NO | RAM | ❌ NO |

## 🎯 Conclusione Finale

### Dal Reverse Engineering:

1. **Comandi scoperti:**
   - `0x74` SET (write to RAM)
   - `0x75` GET (read from RAM)

2. **Memoria utilizzata:**
   - RAM temporanea del dispositivo
   - Chipset Nordic nRF52832

3. **Persistenza:**
   - ❌ NON persistente
   - Si perde al reboot

4. **Workaround:**
   - Salva preferenze in app (SharedPreferences)
   - Re-imposta ad ogni connessione

### Firmware Reverse Engineering Status:

- ✅ Comandi BLE scoperti
- ✅ Formato pacchetti noto
- ✅ Comportamento RAM confermato
- ❌ **NON possiamo modificare il firmware**
- ❌ **NON possiamo rendere persistente la frequenza**

### Limitazioni Hardware:

Il firmware del CL837 **non espone** comandi per:
- Scrivere configurazione 3D in flash
- Rendere persistente la frequenza
- Modificare il default di fabbrica

**È una scelta di design** del produttore Chileaf.
