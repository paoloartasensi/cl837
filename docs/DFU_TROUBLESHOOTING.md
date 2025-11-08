# 🔧 DFU Update Troubleshooting Guide

## ❌ Problema: "DFU device not found (timeout)"

### Causa
Il dispositivo non è stato trovato in modalità DFU dopo l'invio del comando 0x27.

### Possibili Motivi

#### 1. **Device non è entrato in DFU mode**
- Il comando 0x27 non è stato ricevuto
- Device non supporta DFU
- Firmware device già in DFU mode

**Soluzione:**
- Riavvia manualmente il device
- Verifica che sia connesso prima di update
- Controlla battery > 30%

#### 2. **Device fuori range durante scan**
- Distanza > 1 metro
- Ostacoli tra device e telefono
- Interferenze BLE

**Soluzione:**
- Avvicina device a telefono (< 50cm)
- Rimuovi ostacoli metallici
- Spegni altri dispositivi BLE vicini

#### 3. **Device riavviato ma nome non cambiato**
- Nome device non finisce con "U"
- MAC address non incrementato
- Firmware diverso da aspettative

**Soluzione:**
- Attendi 5-10 secondi extra
- Verifica manualmente nome device (scanner BLE)
- Controlla MAC address atteso vs ricevuto

#### 4. **Scan timeout troppo breve**
- Device lento a riavviarsi
- BLE stack lento
- Molti dispositivi nelle vicinanze

**Soluzione:**
- Aumenta timeout a 60 secondi
- Riduci dispositivi BLE vicini
- Riprova update

---

## 🔍 Debug Steps

### Step 1: Verifica Comando Inviato

Cerca nei log:
```
📤 Sending DFU command: 0xFF 0x04 0x27 0xXX
✅ DFU mode command sent successfully
```

Se **NON** vedi questo:
- TX characteristic non trovata
- Device disconnesso prima del comando
- Permessi BLE insufficienti

### Step 2: Verifica Disconnessione Device

Cerca nei log:
```
🔌 Current connection state: ...
🔌 Connection state after 500ms: ...
```

Il device **DEVE** disconnettersi dopo comando 0x27.

Se rimane connesso:
- Comando non accettato
- Device non supporta DFU
- Firmware incompatibile

### Step 3: Verifica Scan DFU

Cerca nei log:
```
🔍 Starting DFU device scan...
   Looking for MAC: XX:XX:XX:XX:XX:XX
   Looking for name ending with: U
```

Poi verifica i candidate trovati:
```
🔎 Candidate: NOME (MAC)
🔎 Candidate: NOME (MAC)
...
```

### Step 4: Analizza Risultati

#### ✅ Match Perfetto:
```
✅ DFU device found (exact match)!
   Name: CL831U
   MAC: AA:BB:CC:DD:EE:01  (incrementato)
```

#### ⚠️ Match Parziale:
```
⚠️ Potential DFU device found (MAC match, name fallback)
   Name: CL831 (expected to end with U)
   MAC: AA:BB:CC:DD:EE:01 ✓
```

#### ❌ No Match:
```
❌ DFU device not found (timeout after 45s)
❌ DFU scan completed. Candidates found:
   - TY (62:CB:C0:8D:1F:39)
   - Device2 (XX:XX:XX:XX:XX:XX)
   ...
```

---

## 🛠️ Soluzioni per Scenario

### Scenario A: Device trovato ma nome sbagliato

**Log:**
```
⚠️ Device with U suffix but wrong MAC: CL831U (AA:BB:CC:DD:EE:FF)
   Expected MAC: AA:BB:CC:DD:EE:01
```

**Causa:** Altro device CL831 in DFU mode nelle vicinanze

**Soluzione:**
1. Allontana altri CL831/CL837
2. Spegni altri device CL831
3. Riprova update

### Scenario B: MAC corretto ma nome sbagliato

**Log:**
```
⚠️ Potential DFU device found (MAC match, name fallback)
   Name: CL831 (expected to end with U)
   MAC: AA:BB:CC:DD:EE:01 ✓
```

**Causa:** Firmware non aggiunge "U" al nome in DFU mode

**Soluzione:**
1. Questo è accettabile (alcune versioni firmware)
2. Modifica codice per accettare match solo su MAC
3. Contatta produttore per conferma comportamento

### Scenario C: Nessun device trovato

**Log:**
```
❌ DFU scan completed. Candidates found:
   - TY (62:CB:C0:8D:1F:39)
   - Other (XX:XX:XX:XX:XX:XX)
```

**Causa:** Device non entrato in DFU mode

**Soluzione:**
1. Verifica comando 0x27 inviato correttamente
2. Riavvia device manualmente
3. Prova con app ufficiale per confermare DFU funzionante
4. Verifica versione firmware supporta DFU

---

## 🔧 Modifiche Codice per Debug

### Aumenta Timeout Scan

```dart
// In dfu_service.dart, modifica parametro default
Future<BluetoothDevice?> scanForDfuDevice(
  String dfuMac, 
  {int timeoutSeconds = 60}  // Era 45, ora 60
) async {
  // ...
}
```

### Accetta Match Solo su MAC (ignora nome)

```dart
// In scanForDfuDevice, commenta check nome
if (macMatch) {  // Ignora nameMatch
  debugPrint('✅ DFU device found (MAC match only)!');
  // ... completa
}
```

### Aggiungi Retry Automatico

```dart
// In performDfuUpdate
for (int attempt = 1; attempt <= 3; attempt++) {
  debugPrint('🔄 Scan attempt $attempt/3');
  
  BluetoothDevice? dfuDevice = await scanForDfuDevice(dfuMac);
  
  if (dfuDevice != null) {
    break;  // Trovato!
  }
  
  if (attempt < 3) {
    debugPrint('⏳ Waiting 5s before retry...');
    await Future.delayed(Duration(seconds: 5));
  }
}
```

---

## 📊 Statistiche Tipiche

| Scenario | Tempo | Successo |
|----------|-------|----------|
| Device close (< 50cm) | 5-15s | 95% |
| Device medium (50cm-1m) | 15-30s | 80% |
| Device far (> 1m) | 30-45s | 40% |
| Device very far (> 2m) | Timeout | 10% |

---

## 🔬 Test Manuale

### Test 1: Verifica DFU Mode Entry

```dart
// Test standalone
void testDfuModeEntry() async {
  String currentMac = "AA:BB:CC:DD:EE:FF";
  String expectedDfuMac = "AA:BB:CC:DD:EE:00";  // FF+1 con overflow
  
  print('Original MAC: $currentMac');
  print('Expected DFU MAC: $expectedDfuMac');
  
  // Invia comando 0x27
  List<int> cmd = [0xFF, 0x04, 0x27, 0x00];
  int checksum = (0xFF + 0x04 + 0x27 + 0x00) & 0xFF;
  cmd.add(checksum);
  
  print('Command: ${cmd.map((b) => '0x${b.toRadixString(16)}').join(' ')}');
  
  // Attendi e scansiona manualmente
  await Future.delayed(Duration(seconds: 5));
  
  // Scansiona tutti i device e stampa
  FlutterBluePlus.startScan();
  await Future.delayed(Duration(seconds: 10));
  FlutterBluePlus.stopScan();
}
```

### Test 2: Scan Tutti Device BLE

```dart
void scanAllDevices() async {
  print('🔍 Scanning all BLE devices...');
  
  FlutterBluePlus.scanResults.listen((results) {
    for (var result in results) {
      String name = result.device.platformName;
      String mac = result.device.remoteId.toString();
      int rssi = result.rssi;
      
      print('📱 $name ($mac) RSSI: $rssi dBm');
    }
  });
  
  await FlutterBluePlus.startScan(timeout: Duration(seconds: 30));
}
```

### Test 3: Verifica MAC Calculation

```dart
void testMacCalculation() {
  // Test vari MAC address
  Map<String, String> tests = {
    "AA:BB:CC:DD:EE:FF": "AA:BB:CC:DD:EE:00",  // Overflow
    "AA:BB:CC:DD:EE:FE": "AA:BB:CC:DD:EE:FF",  // Normal
    "AA:BB:CC:DD:EE:00": "AA:BB:CC:DD:EE:01",  // Normal
  };
  
  for (var entry in tests.entries) {
    String original = entry.key;
    String expected = entry.value;
    
    // Calcola
    String calculated = _calculateDfuAddress(original);
    
    bool match = calculated == expected;
    String status = match ? '✅' : '❌';
    
    print('$status $original → $calculated (expected: $expected)');
  }
}
```

---

## 📝 Checklist Pre-Update

Prima di avviare DFU update, verifica:

- [ ] Device connesso e funzionante
- [ ] Battery > 30% (meglio > 50%)
- [ ] Distanza device < 50cm
- [ ] Nessun ostacolo metallico
- [ ] Nessun altro CL831/CL837 vicino
- [ ] Nessun'altra app BLE attiva
- [ ] File firmware ZIP valido in assets
- [ ] Permessi BLE garantiti
- [ ] Timeout scan >= 45 secondi

---

## 🆘 Quando Contattare Supporto

Se dopo tutti i tentativi il DFU fallisce ancora:

1. **Raccogli log completi**
   - Da comando 0x27 fino a timeout
   - Lista tutti candidate device trovati
   - Connection state changes

2. **Verifica con app ufficiale**
   - Prova DFU update con app Android/iOS ufficiale
   - Se fallisce anche lì → problema hardware/firmware

3. **Informazioni da fornire**
   - Modello device (CL831/CL837)
   - Versione firmware attuale
   - Versione firmware target
   - Log completi
   - Screenshot errore

---

**Aggiornato:** Novembre 8, 2025  
**Versione doc:** 1.0  
**Status:** Troubleshooting attivo
