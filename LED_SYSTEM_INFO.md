# 🚨 Sistema LED CL837 - Guida Completa

## 💡 **Come Funzionano i LED del CL837**

### **🔴 LED ROSSO (SpO2 Mode)**
- **Quando si accende**: Durante misurazione SpO2
- **Funzione**: Illumina la pelle per misurare saturazione ossigeno
- **Comando di attivazione**: `0x37 0x01` (Enter SpO2 mode)
- **Comando di spegnimento**: `0x37 0x00` (Exit SpO2 mode)

### **🟢 LED VERDE (Heart Rate Mode)**  
- **Quando si accende**: Durante misurazione frequenza cardiaca
- **Funzione**: Rileva variazioni di flusso sanguigno (PPG)
- **Stato**: Automatico quando device connesso

## ⚠️ **PROBLEMA COMUNE: LED ROSSO BLOCCATO**

### **Sintomi:**
- LED rosso lampeggia continuamente
- Device non risponde ad altri comandi  
- Consumo batteria elevato
- Impossibile fare altre misurazioni

### **Cause:**
1. App chiusa durante misurazione SpO2
2. Disconnessione BLE durante misurazione
3. Mancato invio comando Exit SpO2 mode
4. Crash dell'app durante misurazione

## 🔧 **SOLUZIONI IMPLEMENTATE**

### **1. Auto-Exit Dopo Misurazione**
```dart
// Ora automaticamente esce dalla modalità SpO2
await measureSpO2() {
    await _enableSPO2Mode();    // LED ROSSO ON
    // ... misurazione ...
    await exitSPO2Mode();       // LED ROSSO OFF ✅
}
```

### **2. Gestione Errori**
```dart
try {
    // misurazione SpO2
} catch (error) {
    await exitSPO2Mode();  // Sempre esce anche in caso di errore ✅
}
```

### **3. Bottone di Emergenza**
- **Dove**: Nel widget SpO2
- **Funzione**: "Force Exit SpO₂ Mode"  
- **Cosa fa**: Invia 3 comandi di exit per assicurare spegnimento

### **4. Exit Durante Disconnessione**
```dart
await stop() {
    await exitSPO2Mode();  // Sempre esce quando app si disconnette ✅
}
```

## 🛡️ **Come Prevenire il Blocco**

### **✅ SEMPRE FARE:**
1. **Usa il bottone "Measure SpO₂"** (non chiudere app durante misurazione)
2. **Aspetta che finisca** prima di disconnettere
3. **Usa "Force Exit"** se LED rimane acceso

### **❌ MAI FARE:**
1. Chiudere app durante misurazione SpO2
2. Disconnettere device con LED rosso acceso
3. Fare altre operazioni BLE durante SpO2

## 🚨 **Procedura di Emergenza**

### **Se LED Rosso Rimane Acceso:**

1. **Riapri l'app** CL837
2. **Riconnetti** al device  
3. **Premi "Force Exit SpO₂ Mode"** nel widget SpO2
4. **Verifica** che LED si spegne

### **Se Non Funziona:**
1. **Disconnetti** device dall'app
2. **Spegni/riaccendi** il device fisicamente  
3. **Riconnetti** tramite app

## 📊 **Consumo Batteria per Modalità**

| Modalità | LED | Consumo | Durata |
|----------|-----|---------|--------|
| **Idle** | OFF | Basso | ~7 giorni |
| **Heart Rate** | Verde | Medio | ~3 giorni |
| **SpO2** | Rosso | **ALTO** | ~8 ore ⚠️ |

**⚠️ IMPORTANTE**: LED rosso consuma 10x più del normale - mai lasciare acceso!

## 🔬 **Dettagli Tecnici**

### **Protocollo SpO2:**
- **Enter**: `FF 03 37 01 [checksum]`
- **Inquire**: `FF 03 37 02 [checksum]`  
- **Exit**: `FF 03 37 00 [checksum]` ← **CRUCIALE**

### **Verifica Stato LED:**
- **LED OFF**: Device in idle/HR mode ✅
- **LED Verde**: Heart rate attivo ✅  
- **LED Rosso**: SpO2 attivo - deve spegnersi dopo ~10 secondi ⚠️

---

**💡 REMEMBER**: Il LED rosso è potente ma consuma molto - sempre spegnerlo dopo l'uso!
