# 📚 CL837 Documentation

Questa cartella contiene la documentazione completa per il progetto CL837 - un'app Flutter per dispositivi wearable CL837 con funzionalità avanzate di monitoraggio del sonno, frequenza cardiaca, e analisi della recovery.

## 📖 Struttura Documentazione

### [🏗️ ARCHITECTURE.md](ARCHITECTURE.md)
- Overview architetturale del progetto
- Pattern di design utilizzati
- Struttura del codice e organizzazione
- Librerie e dipendenze principali

### [⚙️ IMPLEMENTATION.md](IMPLEMENTATION.md)
- Implementazioni completate e stato del progetto
- Feature parity con dispositivi premium (Whoop, Oura, Apple Watch)
- Componenti implementati (Sleep Score, Smart Alarm, Readiness, etc.)
- Statistiche del codice e metriche di qualità

### [💤 SLEEP_TRACKING.md](SLEEP_TRACKING.md)
- Protocolli di comunicazione per dati del sonno
- Implementazione comandi 0x05 (legacy) e 0x31 (ufficiale)
- Parsing e processamento dei dati sleep
- Algoritmi di classificazione fasi del sonno
- Sleep Score calculation e analytics

### [📡 DEVICE_PROTOCOLS.md](DEVICE_PROTOCOLS.md)
- Comandi BLE e protocolli di comunicazione
- Analisi SDK ufficiali (Android/iOS)
- Implementazione comandi device specifici
- Configurazione sensori (3D, HRV, SpO2, etc.)

### [🔧 TROUBLESHOOTING.md](TROUBLESHOOTING.md)
- Risoluzioni di bug e problemi incontrati
- Fix applicati e loro impatto
- TODO risolti e miglioramenti implementati
- Guide per debugging e testing

### [👤 USER_GUIDE.md](USER_GUIDE.md)
- Guida utente per l'applicazione
- Istruzioni per configurazione e utilizzo
- Spiegazione delle funzionalità principali
- Troubleshooting comune per utenti finali

## 📂 Struttura del Progetto

```
lib/
├── models/          # Modelli dati (SleepScore, RecoveryScore, etc.)
├── services/        # Business logic e comunicazione BLE
├── screens/         # Schermate UI principali
├── widgets/         # Componenti UI riutilizzabili
└── utils/           # Utility e helper functions

docs/                # Questa cartella
├── ARCHITECTURE.md
├── IMPLEMENTATION.md
├── SLEEP_TRACKING.md
├── DEVICE_PROTOCOLS.md
├── TROUBLESHOOTING.md
└── USER_GUIDE.md

test/                # Unit tests
tools/               # Script di sviluppo
```

## 🚀 Quick Start

1. **Installazione**: `flutter pub get`
2. **Configurazione**: Segui [USER_GUIDE.md](USER_GUIDE.md)
3. **BLE Setup**: Assicurati che il dispositivo CL837 sia connesso
4. **Testing**: `flutter test` per eseguire i test
5. **Build**: `flutter build apk` per Android

## 📊 Stato del Progetto

- ✅ **87% Feature Parity** con dispositivi premium
- ✅ **13/15 componenti** implementati
- ✅ **Production Ready** per le funzionalità core
- 🔄 **Integrazione finale** in corso (Sleep Insights + UI tabs)

## 📞 Supporto

Per problemi tecnici, consulta prima [TROUBLESHOOTING.md](TROUBLESHOOTING.md).
Per domande sull'utilizzo, vedi [USER_GUIDE.md](USER_GUIDE.md).

---

**Ultimo aggiornamento:** Novembre 2025
**Versione:** 1.0.0</content>
<parameter name="filePath">c:\Users\Admin\Documents\visualstudiocode\cl837\docs\README.md