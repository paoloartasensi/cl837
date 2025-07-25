# CL837 Accelerometer Project

Progetto Flutter per la gestione del dispositivo CL837 armband ottico BLE con 34 comandi ufficiali implementati.

## 🚀 Setup Rapido

### Prerequisiti
- **Anaconda/Miniconda** installato
- **Java JDK 8+** installato
- **Flutter SDK** installato
- **Git** installato

### Installazione Automatica

1. Clona il repository:
```bash
git clone <repository-url>
cd cl837
```

2. Attiva conda base e esegui setup:
```bash
conda activate base
setup.bat
```

Lo script automatico installerà:
- ✅ Dipendenze Flutter
- ✅ jadx decompiler per .dex
- ✅ Verifiche ambiente completo

## 📱 Uso dell'App

### Avvio dell'App
```bash
conda activate base
flutter run
```

### Controlli Dispositivo Disponibili

Il `DeviceControlWidget` implementa 34 comandi ufficiali Chileaf:

#### 🔧 Core Device Commands
- **Device Reset** (0xF3) - Reset dispositivo
- **Sync Time** (0x08) - Sincronizzazione tempo  
- **DFU Mode** (0x27) - Modalità aggiornamento firmware

#### 🩺 Health Monitoring
- **Start SpO2** (0x37) - Attiva LED rosso per SpO2
- **Stop SpO2** - Disattiva LED SpO2
- **Temperature** (0x38) - Lettura temperatura corporea
- **HR Settings** (0x46) - Configurazione soglie frequenza cardiaca

#### 📡 Sensors Control
- **3D Sensor Toggle** (0x74/0x75) - Accelerometro 3D
- **3D Frequency** - Frequenza sensore 3D (25-400HZ)
- **6D Frequency** (0x62) - Frequenza sensore 6D (26-208HZ)

#### 📚 Historical Data
- **Exercise History** (0x16) - Storico esercizi 7 giorni
- **HR History** (0x21) - Lista storico frequenza cardiaca
- **Sleep History** (0x05) - Dati analisi sonno

#### 🪢 Rope Skipping
- **Rope Mode** (0x42) - Modalità salto corda
- **Rope Free Mode** (0x41) - Modalità libera
- **Rope Data** (0x45) - Statistiche salto corda

#### 🔋 Power Management
- **Shutdown** (0xF1) - Spegnimento dispositivo
- **Disable Bluetooth** (0x3F) - Disattiva radio BT

## 🔧 Reverse Engineering

### Decompilazione SDK .dex

Per analizzare i file .dex dell'SDK CL831:

```bash
# Script batch (Windows)
decompile_dex.bat

# Script PowerShell (alternativo)
decompile_dex.ps1
```

Questo decompilerà tutti i file .dex in:
```
REVERSE/CL831_DECOMPILED/
```

### Documentazione Tecnica

- **CL837_DEVICE_ANALYSIS.md** - Analisi completa 34 comandi
- **SDK/BEST_PRACTICES_CL837.md** - Best practices sviluppo
- **SDK/MY_REVERSE_CL387.md** - Note reverse engineering

### Struttura Progetto

```
cl837/
├── lib/
│   ├── main.dart                 # Entry point
│   ├── chileaf_extended_service.dart  # Servizio BLE principale
│   ├── models/                   # Modelli dati
│   ├── services/                 # Servizi app
│   └── widgets/
│       └── device_control_widget.dart  # Widget controllo dispositivo
├── REVERSE/
│   ├── CL831_INFO/              # SDK decompilato 1
│   ├── XFITNESS2/               # SDK decompilato 2
│   ├── CL831_SDK/               # SDK originale .dex
│   └── CL831_DECOMPILED/        # Output decompilazione
├── SDK/                         # Documentazione tecnica
├── tools/                       # Strumenti (jadx)
├── setup.bat                    # Setup automatico
├── decompile_dex.bat           # Decompilazione .dex
└── decompile_dex.ps1           # Decompilazione PowerShell
```

## 🔍 Protocollo BLE Chileaf

### Comandi Implementati (34 totali)

| Comando | Hex | Descrizione |
|---------|-----|-------------|
| Device Info | 0x03 | Informazioni dispositivo |
| Sleep Data | 0x05 | Dati sonno |
| Sync Time | 0x08 | Sincronizzazione tempo |
| Exercise History | 0x16 | Storico esercizi |
| HR History | 0x21 | Storico frequenza cardiaca |
| DFU Mode | 0x27 | Modalità aggiornamento |
| SpO2 LED | 0x37 | Controllo LED SpO2 |
| Temperature | 0x38 | Temperatura corporea |
| Bluetooth Off | 0x3F | Spegni Bluetooth |
| Rope Free | 0x41 | Modalità corda libera |
| Rope Mode | 0x42 | Selezione modalità corda |
| Rope Data | 0x45 | Dati salto corda |
| HR Settings | 0x46 | Impostazioni frequenza |
| 6D Frequency | 0x62 | Frequenza sensore 6D |
| 3D Enable | 0x74 | Abilita sensore 3D |
| 3D Disable | 0x75 | Disabilita sensore 3D |
| Device Shutdown | 0xF1 | Spegnimento |
| Factory Reset | 0xF3 | Reset fabbrica |

### Caratteristiche BLE

- **Service UUID**: Specifico Chileaf
- **Write Characteristic**: Invio comandi
- **Notify Characteristic**: Ricezione dati
- **Protocol**: Pacchetti custom Chileaf v0.6

## 🛠️ Sviluppo

### Ambiente di Sviluppo
```bash
# Attiva sempre conda base
conda activate base

# Verifica ambiente
flutter doctor -v

# Dipendenze
flutter pub get

# Run in debug
flutter run

# Build release
flutter build apk
```

### Test
```bash
# Test unitari
flutter test

# Test widget specifici
flutter test test/widgets/device_control_widget_test.dart
```

### Debug BLE
Il progetto include logging dettagliato per:
- Connessioni BLE
- Invio/ricezione pacchetti
- Parsing dati dispositivo
- Gestione errori

## 📚 Risorse

- **Chileaf BLE Protocol v0.6** - Documentazione ufficiale
- **CL831 Android SDK** - Riferimento implementazione
- **Flutter Blue Plus** - Libreria BLE utilizzata

## 🤝 Contributi

1. Fork del repository
2. Crea feature branch
3. Commit modifiche
4. Push al branch
5. Crea Pull Request

## 📄 Licenza

Progetto per scopi di ricerca e reverse engineering.

---

**Nota**: Ricorda sempre di usare `conda activate base` prima di qualsiasi comando!
