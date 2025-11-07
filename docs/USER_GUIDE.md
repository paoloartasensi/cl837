# 👤 CL837 User Guide

## 📱 Getting Started

Benvenuto nell'app CL837! Questa guida ti aiuterà a configurare e utilizzare al meglio il tuo dispositivo wearable CL837 per monitoraggio avanzato del sonno e della recovery.

---

## 🔌 Device Setup

### 1. Connessione Iniziale

1. **Accendi l'app** e vai alla schermata principale
2. **Attiva Bluetooth** sul tuo telefono
3. **Tocca "Scan for Devices"**
4. **Seleziona CL837** dalla lista dei dispositivi
5. **Attendi la connessione** (il LED del dispositivo lampeggerà)

### 2. Sincronizzazione Ora

L'app sincronizza automaticamente l'ora con il dispositivo. Se noti discrepanze nei timestamp:

1. Vai a **"🌍 Timezone Test"** (solo per sviluppatori)
2. Verifica che l'ora locale sia corretta
3. L'app gestisce automaticamente tutti i fusi orari

### 3. Configurazione Utente

Imposta i tuoi dati personali per calcoli più accurati:

```dart
// Dati richiesti per algoritmi ottimali:
- Età (per VO2 Max e HRV baseline)
- Sesso (per calcoli metabolici)
- Altezza e peso (per stime VO2 Max)
```

---

## 💤 Sleep Tracking

### Come Funziona

Il dispositivo CL837 traccia automaticamente il tuo sonno usando:
- **Accelerometro 3D** per movimenti
- **Frequenza cardiaca** per fasi del sonno
- **HRV (Heart Rate Variability)** per qualità del recupero

### Avvio Monitoraggio

1. **Indossa il dispositivo** prima di dormire
2. **Assicurati connessione** BLE attiva
3. **L'app rileva automaticamente** l'inizio del sonno
4. **Ricevi notifiche** di onset/wake

### Visualizzazione Dati

#### Sleep Score (0-100)
- **90-100**: Eccellente recupero
- **80-89**: Buono
- **70-79**: Nella media
- **60-69**: Sufficiente
- **<60**: Necessario miglioramento

#### Componenti Score
- **Duration** (35 punti): 7-9 ore ottimali
- **Efficiency** (30 punti): Tempo sonno vs tempo a letto
- **Quality** (25 punti): % deep sleep (15-25% ottimale)
- **Consistency** (10 punti): Numero di risvegli

#### Fasi del Sonno
- **🟦 Deep Sleep**: Recupero fisico (15-25% del totale)
- **🟩 Light Sleep**: Consolidamento memoria
- **⬜ Awake**: Risvegli e movimenti

---

## 🏃 Activity & Recovery

### Dashboard Recovery

L'app calcola un **Recovery Score** basato su:

#### Componenti (Punteggi)
- **HRV Score** (50%): Variabilità cardiaca
- **Sleep Quality** (35%): Qualità del sonno recente
- **Resting Heart Rate** (15%): Frequenza cardiaca a riposo

#### Zone Recovery
- **🟢 Verde (67-100%)**: Pronto per allenamenti intensi
- **🟡 Giallo (34-66%)**: Attività moderata raccomandata
- **🔴 Rosso (0-33%)**: Priorità al riposo

### Monitoraggio Real-time

#### Metriche Disponibili
- **VO2 Max**: Capacità cardiovascolare (stimata)
- **Respiratory Rate**: Frequenza respiratoria
- **Stress Level**: Livello di stress (0-100)
- **Stamina**: Energia residua (0-5)

---

## ⏰ Smart Alarm

### Configurazione

1. **Vai a "Alarm Config"**
2. **Imposta orario** desiderato
3. **Scegli finestra** (15-60 minuti)
4. **Seleziona giorni** della settimana
5. **Attiva opzioni**:
   - Vibrazione dispositivo
   - Notifica telefono

### Come Funziona

L'algoritmo trova il momento ottimale nella tua finestra:
1. **Analizza fasi sonno** negli ultimi 10 minuti
2. **Priorità**: Light sleep (ideale per svegliarsi)
3. **Fallback**: Awake periods
4. **Ultima opzione**: Lowest activity

### Esempio
```
Finestra: 7:00 - 7:30
Sonno rilevato: Light sleep alle 7:15
→ Sveglia alle 7:15 (in light sleep)
```

---

## 📊 Trends & Analytics

### Visualizzazioni Disponibili

#### Sleep Trends (7/30 giorni)
- **Grafico a barre** colorato per qualità
- **Linea media** score
- **Tooltips interattivi** con dettagli

#### Statistics Cards
- **Average Score**: Media periodo selezionato
- **Total Sleep**: Ore totali sonno
- **Efficiency**: % tempo sonno vs letto
- **Deep Sleep**: % fasi deep

#### Best/Worst Nights
- **Migliori notti**: Score più alto
- **Peggiori notti**: Score più basso
- **Pattern analysis**: Tendenze settimanali

---

## 🩸 Blood Oxygen (SpO2)

### Misurazione

1. **Tocca "Start SpO2 Measurement"**
2. **Tieni il dispositivo fermo** sul dito
3. **Attendi completamento** automatico (30-60 secondi)
4. **Visualizza risultati**:
   - Valore SpO2 (%)
   - Signal quality (PI)
   - Trend rispetto baseline

### Valori Normali
- **95-100%**: Normale
- **90-94%**: Leggermente basso
- **<90%**: Richiede attenzione medica

### Quando Misurare
- **Riposo**: Baseline personale
- **Dopo attività**: Recupero cardiovascolare
- **Se sintomi**: Affaticamento, mancanza fiato

---

## ⚙️ Device Configuration

### Sensore 3D Accelerometer

**Frequenze Disponibili:**
- **25Hz**: Risparmio batteria, attività basic
- **50Hz**: Attività moderate
- **100Hz**: Attività intense
- **200Hz**: Sport professionistici
- **400Hz**: Ricerca/massima precisione

**Impatto Batteria:**
- 25Hz: ~7 giorni autonomia
- 100Hz: ~5 giorni autonomia
- 400Hz: ~2 giorni autonomia

### Ripristino Fabbrica

**⚠️ ATTENZIONE**: Cancella tutti i dati del dispositivo

1. Vai a **"Advanced Settings"**
2. Tocca **"Factory Restoration"**
3. **Conferma** l'operazione
4. **Riconnetti** il dispositivo

---

## 🔧 Troubleshooting

### Problemi Comuni

#### Dispositivo Non si Connette
```
Possibili cause:
- Bluetooth spento
- Batteria dispositivo scarica
- Troppi dispositivi BLE vicini
- App senza permessi location

Soluzioni:
1. Riavvia Bluetooth telefono
2. Ricarica dispositivo
3. Allontanati da altri dispositivi BLE
4. Concedi permessi location all'app
```

#### Dati Sonno Non Scaricati
```
Possibili cause:
- Dispositivo non indossato durante sonno
- Connessione BLE interrotta
- Comando protocollo errato

Soluzioni:
1. Verifica di aver dormito con dispositivo
2. Controlla connessione BLE
3. Riprova download (tieni device vicino)
```

#### Score Incoerenti
```
Possibili cause:
- Dati utente incompleti (età/peso)
- Dispositivo non calibrato
- Attività fisica irregolare

Soluzioni:
1. Completa profilo utente
2. Lascia dispositivo acceso 24h per calibrazione
3. Mantieni routine regolare
```

#### Batteria si Scarica Veloce
```
Possibili cause:
- Frequenza sensore alta
- Monitoraggio continuo
- Temperatura ambiente fredda

Soluzioni:
1. Riduci frequenza sensore (25-50Hz)
2. Disattiva notifiche non necessarie
3. Conserva a temperatura ambiente
```

---

## 📱 Notifications

### Tipi di Notifica

#### Sleep Tracking
- **💤 Sleep Onset**: Rilevato inizio sonno
- **☀️ Wake Detected**: Rilevato risveglio
- **🌟 Sleep Score Ready**: Score calcolato

#### Recovery & Activity
- **🔄 Phase Change**: Cambio fase sonno
- **💡 Insight Available**: Nuovo consiglio disponibile
- **⏰ Smart Alarm**: Sveglia ottimale

#### Device Status
- **🔋 Low Battery**: Batteria sotto 20%
- **📡 Connection Lost**: BLE disconnesso
- **⚙️ Sync Complete**: Dati sincronizzati

### Gestione Notifiche

1. **Impostazioni telefono** → Notifiche → CL837
2. **Scegli canali** da attivare/disattivare
3. **Priorità** per notifiche critiche

---

## 📊 Data Export

### CSV Export

L'app permette esportazione dati in formato CSV:

```csv
Date,Timestamp,SleepScore,Duration,DeepSleep,Efficiency,Awakenings,HRV
2025-11-07,1730937600,85,8.5,1.8,92,2,45.2
2025-11-06,1730851200,78,7.2,1.2,88,4,38.1
```

### Come Esportare

1. **Menu** → **Export Data**
2. **Seleziona periodo** (7/30/90 giorni)
3. **Scegli formato** (CSV/JSON)
4. **Condividi** via email/app

---

## 🔐 Privacy & Security

### Dati Raccolti
- **Frequenza cardiaca** e HRV
- **Movimenti** (accelerometro)
- **Fasi sonno** calcolate
- **Metriche ambientali** (temperatura se disponibile)

### Sicurezza
- **Crittografia BLE** standard
- **Dati locali** (non inviati a server)
- **Permessi minimi** richiesti
- **Conformità** GDPR/privacy laws

### Backup & Restore
- **Auto-backup** dati locali
- **Restore** da backup precedente
- **Esportazione** per archivio personale

---

## 📞 Supporto

### Contatti
- **Email**: support@cl837.com
- **Documentazione**: [GitHub Repository](https://github.com/paoloartasensi/cl837)
- **Forum**: Community CL837

### Debug Information

Per segnalare problemi, includi:

```dart
// Debug info da console app:
- Versione app
- Modello telefono
- Versione Android/iOS
- Firmware dispositivo CL837
- Log errori (se presenti)
```

### Log Collection

1. **Impostazioni** → **Debug Mode**
2. **Abilita logging** dettagliato
3. **Riproduci problema**
4. **Esporta log** per supporto

---

## 🎯 Best Practices

### Per Score Sonno Ottimale
1. **Routine regolare**: Stessa ora letto/sveglia
2. **Ambiente buio**: Temperatura 18-20°C
3. **No caffeina dopo 15:00**
4. **Dispositivo indossato** correttamente
5. **Connessione BLE** stabile durante notte

### Per Recovery Tracking
1. **Baseline iniziale**: 3-7 giorni senza attività intensa
2. **Monitoraggio regolare**: Controlla daily score
3. **Bilancia carico**: Adatta allenamenti a recovery zone
4. **Qualità sonno**: Priorità rispetto quantità

### Per Lunga Autonomia
1. **Frequenza sensore**: 25-50Hz per uso daily
2. **Aggiornamenti firmware**: Mantiene efficienza
3. **Carica regolare**: Non aspettare batteria scarica
4. **Conservazione**: Temperatura ambiente, umidità normale

---

## 📈 Aggiornamenti Futuri

### Features Pianificate
- **Sleep Insights AI**: Pattern recognition personalizzata
- **Training Load**: Integrazione con app fitness
- **Multi-device**: Sincronizzazione tra dispositivi
- **Health Correlations**: Integrazione salute generale

### Compatibilità
- **iOS 12+** e **Android 8.0+**
- **BLE 4.0+** richiesto
- **CL837 firmware v3.0+** ottimale

---

**Versione Guida:** 1.0  
**Data:** Novembre 2025  
**Compatibilità:** CL837 app v1.0+</content>
<parameter name="filePath">c:\Users\Admin\Documents\visualstudiocode\cl837\docs\USER_GUIDE.md