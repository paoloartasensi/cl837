# 🎛️ DeviceControlWidget - Guida Utente

## 📋 Panoramica

Il `DeviceControlWidget` è un centro di controllo completo per il dispositivo CL837 che permette di eseguire tutti i 34 comandi ufficiali del protocollo Chileaf BLE v0.6.

## 🚀 Funzionalità Principali

### 🔧 Core Device Commands
- **Device Reset** (0xF3): Reset completo del dispositivo
- **Sync Time** (0x08): Sincronizzazione orario con il telefono
- **DFU Mode** (0x27): Modalità aggiornamento firmware

### 🩺 Health Monitoring
- **SpO2 Measurement** (0x37): Attivazione LED rosso per misurazione saturazione ossigeno
- **Temperature** (0x38): Richiesta dati temperatura corporea
- **HR Settings** (0x46): Configurazione soglie frequenza cardiaca

### 📡 Sensors Control
- **3D Sensor Toggle** (0x74/0x75): Abilitazione/disabilitazione accelerometro 3D
- **3D Frequency** (0x74): Impostazione frequenza sensore 3D (25-400 HZ)
- **6D Frequency** (0x62): Impostazione frequenza sensore 6D (26-208 HZ)

### 📚 Historical Data
- **Exercise History** (0x16): Storico esercizi ultimi 7 giorni
- **HR History List** (0x21): Lista record frequenza cardiaca
- **Sleep History** (0x05): Dati analisi del sonno

### 🪢 Rope Skipping
- **Mode Selection** (0x42): Selezione modalità saltare la corda
- **Free Mode** (0x41): Avvio modalità libera
- **Statistics** (0x45): Richiesta statistiche correnti

### 🔋 Power Management
- **Shutdown** (0xF1): Spegnimento dispositivo
- **Disable Bluetooth** (0x3F): Disabilitazione radio Bluetooth

## 🎯 Utilizzo

### Integrazione nel Main App
```dart
// Aggiunto come quarto tab nell'app principale
TabBarView(
  children: [
    _buildSensorTab(),           // Tab 1: Sensori
    ManualTestsWidget(...),      // Tab 2: Test Manuali  
    DeviceControlWidget(         // Tab 3: Device Control (NUOVO)
      extendedService: _extendedService,
      isConnected: connectedDevice != null,
      deviceName: connectedDevice?.platformName ?? 'Unknown Device',
    ),
    _buildInfoTab(),             // Tab 4: Info dispositivo
  ],
)
```

### Feedback Real-time
- **Status Bar**: Mostra stato ultimo comando eseguito
- **Pulse Animation**: Animazione durante esecuzione comandi
- **Command History**: Cronologia ultimi 20 comandi con timestamp
- **Success/Error Messages**: Notifiche SnackBar per feedback immediato

### Stati LED SpO2
- **LED OFF**: LED rosso spento, nessuna misurazione attiva
- **LED ON**: LED rosso acceso, misurazione SpO2 in corso
- **Auto-Stop**: Timer automatico 50 secondi per spegnimento LED

## 🔄 Gestione Errori

### Validazioni
- **Connection Check**: Verifica connessione prima di inviare comandi
- **Command Queue**: Prevenzione sovrascrittura comandi multipli
- **Parameter Validation**: Controllo parametri input (frequenze, soglie HR)

### Retry Logic
- **Automatic Retry**: Tentativo ripetizione comandi falliti
- **Error Logging**: Registrazione errori nella command history
- **User Feedback**: Messaggi di errore chiari per l'utente

## 📊 Monitoraggio Performance

### Command History
Ogni comando eseguito viene tracciato con:
- **Timestamp**: Ora esatta di esecuzione
- **Success/Failure**: Stato completamento comando
- **Error Details**: Dettagli errore in caso di fallimento
- **Command Name**: Nome comando per debugging

### Visual Indicators
- **Connection Status**: Badge verde/rosso per stato connessione
- **LED Status**: Badge indicatore stato LED SpO2
- **Sensor Status**: Badge ON/OFF per sensori 3D/6D
- **Command Progress**: Animazione pulse durante esecuzione

## 🛠️ Configurazioni Avanzate

### Heart Rate Settings Dialog
- **Min HR**: Soglia minima (40-200 BPM)
- **Max HR**: Soglia massima (40-220 BPM)  
- **Goal HR**: Obiettivo frequenza (40-200 BPM)
- **Alarm**: Enable/disable allarme superamento soglie

### Sensor Frequency Settings
- **3D Frequencies**: 25HZ, 50HZ, 100HZ, 200HZ, 400HZ
- **6D Frequencies**: 26HZ, 52HZ, 104HZ, 208HZ

### Rope Skipping Modes
- **Free Mode**: Modalità libera senza vincoli
- **Counter Mode**: Modalità contatore salti
- **Timer Mode**: Modalità timer cronometrato

## 🚨 Note Importanti

### Limitazioni Device
- **No Vibration**: Il CL837 NON supporta vibrazione
- **LED Contact Only**: Solo LED rosso per contatto pelle (SpO2)
- **Single Command**: Eseguire un comando alla volta per evitare conflitti

### Best Practices
- **Wait for Completion**: Attendere completamento comando prima del successivo
- **Check Connection**: Verificare connessione stabile prima di comandi critici
- **Monitor Battery**: Controllare livello batteria per operazioni intensive
- **DFU Caution**: Comando DFU disconnette il dispositivo per aggiornamento

## 📱 Accessibilità

### Responsive Design
- **Expandable Sections**: Sezioni espandibili per categorie comandi
- **Touch Targets**: Target touch ottimizzati per facilità uso
- **Visual Feedback**: Feedback visivo immediato per ogni azione

### Error Prevention
- **Disabled States**: Comandi disabilitati quando device disconnesso
- **Parameter Limits**: Controlli input per prevenire valori invalidi
- **Confirmation Dialogs**: Dialog conferma per comandi critici (reset, shutdown)

---

## 🔗 Riferimenti Tecnici

- **Protocollo**: Chileaf BLE Protocol v0.6
- **Comandi**: 34 comandi ufficiali da reverse engineering
- **SDK Source**: CL831_INFO + XFITNESS2
- **Device**: CL837 armband ottico

*Widget implementato il 25 Luglio 2025 basato su analisi completa protocollo dispositivo.*
