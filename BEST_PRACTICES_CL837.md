# 🏥 Best Practices CL837 - Monitoraggio Medico Professionale

## 📱 App CL837 - Guida alle Migliori Pratiche per Rilevazioni Accurate

Questa guida contiene le best practices implementate nell'app CL837 per ottenere misurazioni clinicamente accurate con il dispositivo Chileaf CL837.

---

## 🫀 1. FREQUENZA CARDIACA A RIPOSO (HR)

### 📋 **Preparazione del Test:**
- ⏰ **Timing ottimale**: Al mattino, subito dopo il risveglio
- 🛏️ **Posizione**: Seduto o sdraiato, completamente rilassato
- ⏱️ **Riposo pre-test**: Almeno 5 minuti in posizione di riposo
- 🚫 **Evitare**: Caffè, fumo, attività fisica nelle 2 ore precedenti

### 🎯 **Durante la Misurazione:**
- 📍 **Posizionamento**: Device ben aderente al polso, 2-3 cm sopra l'osso
- 🤫 **Comportamento**: Non parlare, non muoversi per 2-3 minuti
- 💨 **Respirazione**: Naturale e regolare, non forzata
- 🔄 **Durata**: Minimo 2 minuti per stabilizzazione

### 📊 **Interpretazione Risultati:**
- **50-60 BPM**: Atleti ben allenati o bradicardia
- **60-100 BPM**: Range normale per adulti a riposo
- **100-150 BPM**: Tachicardia lieve, possibile stress/ansia
- **>150 BPM**: Consultare medico, possibile aritmia

---

## 🩸 2. SATURAZIONE OSSIGENO (SpO2)

### 📋 **Preparazione del Test:**
- 🧼 **Pulizia**: Pulire polso e sensore da sudore/lozioni
- 🌡️ **Temperatura**: Mani calde (se fredde, riscaldare prima)
- 💅 **Unghie**: Rimuovere smalto scuro se presente
- 🚫 **Evitare**: Movimento durante test

### 🎯 **Durante la Misurazione (Sistema WatchFit Ottimizzato):**
- ⏱️ **Durata massima**: 50 secondi (ottimizzato per WatchFit)
- 🔴 **LED rosso**: Si accende automaticamente, indica misurazione attiva
- 🔚 **Terminazione intelligente**: Auto-stop con 2 letture valide consecutive
- 📊 **Criteri validità**: SpO2 70-100%, postura corretta, device indossato
- 📱 **Posizione**: Braccio fermo, device verso l'alto
- ✋ **Immobilità**: Assoluta durante misurazione
- � **Comando BLE**: 0x37 mode=1 (setBloodOxygen) + 0x36 per LED

### 📊 **Interpretazione Risultati (Soglie Cliniche Implementate):**
- **98-100%**: Saturazione ottimale (normale)
- **95-97%**: Normale, monitorare se persistente
- **90-94%**: Ipossiemia lieve, consultare medico
- **70-89%**: Range validazione device (consultare medico)
- **<70%**: Fuori range device, verifica posizionamento
- **Qualità segnale 12-15/15**: Misurazione clinicamente affidabile
- **Qualità segnale 8-11/15**: Accettabile, considerare ripetizione
- **Qualità segnale <8/15**: Inaffidabile, ripetere test obbligatorio

---

## ❤️‍🩹 3. VARIABILITÀ CARDIACA (HRV)

### 📋 **Preparazione del Test (Standard Elite HRV):**
- 🌅 **Timing**: Preferibilmente al mattino dopo il risveglio
- 😌 **Stato**: Completamente rilassato, no stress
- 🛏️ **Posizione**: Seduto comodamente o sdraiato
- ⏱️ **Pre-test**: 10 minuti di rilassamento mentale

### 🎯 **Durante la Misurazione (Standard Elite HRV):**
- ⏰ **Durata clinica obbligatoria**: Minimo 60 secondi continuativi
- 📊 **RR Intervals richiesti**: Minimo 50 battiti (soglia app: 50, validazione: 40)
- 💨 **Respirazione**: Naturale, profonda e regolare (no apnea)
- 🧘 **Concentrazione**: Focus sul respiro, mente rilassata
- 📱 **Immobilità**: Movimento minimo, braccio completamente rilassato
- 🔄 **Data Source**: Main heart rate stream (stesso dei SENSORI)
- ⚡ **Collezione**: Real-time da `heartRateData.rrIntervals`

### 📊 **Interpretazione Risultati (Metriche Complete):**

#### **RMSSD (Variabilità Parasimpatica):**
- **>70ms**: Eccellente forma fisica, recovery ottimo
- **50-70ms**: Buona forma fisica, sopra la media
- **30-50ms**: Nella media normale
- **15-30ms**: Sotto la media, possibile stress
- **<15ms**: Stress severo, recovery insufficiente

#### **SDNN (Variabilità Totale):**
- **>50ms**: Ottima variabilità complessiva
- **30-50ms**: Variabilità normale
- **<30ms**: Bassa variabilità, monitorare

#### **pNN50 (Attività Parasimpatica):**
- **>15%**: Ottima variabilità parasimpatica
- **5-15%**: Variabilità normale
- **<5%**: Bassa attività parasimpatica

### 🏥 **Validazione Clinica (Implementata):**
- ✅ **Durata minima**: >60 secondi per validità scientifica Elite HRV
- ✅ **RR Count**: >40 intervalli per analisi statistica affidabile
- ✅ **Range fisiologico**: 300-2000ms per intervallo (30-200 BPM)
- ✅ **Controllo variabilità**: <50% della media RR per coerenza dati
- ✅ **Qualità insufficiente**: "Insufficient Duration (X.Xs)" se <60s
- ✅ **Auto-calcolo**: RMSSD, SDNN, pNN50 in tempo reale

---

## 🌡️ 4. TEMPERATURA CORPOREA

### 📋 **Preparazione del Test:**
- 🏠 **Ambiente**: Temperatura ambiente stabile (18-24°C)
- ⏱️ **Acclimatazione**: 15 minuti per stabilizzazione termica
- 🧼 **Pulizia**: Polso asciutto e pulito
- 🚫 **Evitare**: Doccia calda, esercizio fisico nelle 2 ore precedenti

### 🎯 **Durante la Misurazione (Protocollo Multi-Sensor):**
- ⏱️ **Durata acquisizione**: 10 secondi per stabilizzazione termica
- 📍 **Posizionamento**: Sensore termico a pieno contatto con pelle
- 🤚 **Immobilità**: Braccio fermo per lettura accurata e stabile
- 🌡️ **Triple-sensor**: Ambiente, polso, corporea stimata simultanee
- 🔄 **Polling automatico**: Ogni 5 secondi in background
- 📊 **Range validazione**: Ambiente 10-50°C, Polso 20-45°C, Corpo 30-45°C

### 📊 **Interpretazione Risultati:**

#### **Temperatura Corporea (Stimata):**
- **36.1-37.2°C** (97-99°F): Range normale
- **35.5-36.0°C** (96-97°F): Ipotermia lieve
- **37.3-38.0°C** (99-100°F): Febbre lieve
- **38.1-39.0°C** (100-102°F): Febbre moderata
- **>39°C** (>102°F): Febbre alta, consultare medico

#### **Temperatura Polso (Misurazione Diretta):**
- Precisione: ±0.1°C (risoluzione device)
- Generalmente 1-2°C più bassa della corporea
- Utile per trend relativi e variazioni temporali
- Range normale polso: 32-38°C

#### **Temperatura Ambiente (Compensazione):**
- Misurazione simultanea per correzione termica
- Range ottimale misure: 20-25°C ambiente
- Usata per calibrazione automatica letture corporee

---

## 🔧 SPECIFICHE TECNICHE IMPLEMENTATE

### 📱 **Architettura Software Attuale:**
- **BLE Protocol**: Chileaf v0.6 con comandi ottimizzati WearManager.java
- **Data Polling**: Temperatura ogni 5 secondi automatico (0x31)
- **SpO2 Commands**: 0x37 mode=1 (setBloodOxygen) + 0x36 (LED control)  
- **HRV Source**: Main heart rate stream (`heartRateService.dataStream`)
- **Debouncing Log**: Temperatura max 10s, significativo >0.5°C change
- **JSON Export**: Dati completi con `share_plus` e `path_provider`
- **Sports Data**: Completamente rimosso (step counting inaffidabile)

### 🏥 **Standard Clinici Implementati:**
- **HRV**: Elite HRV standard rigoroso (60s minimo, 50+ RR intervals)
- **SpO2**: WatchFit protocol ottimizzato (50s max, 2 letture consecutive)
- **HR**: Analisi RR intervals da stream principale per accuratezza massima
- **Temperature**: Multi-point validation (3 sensori, range fisiologici)
- **Data Quality**: Range check, duration check, artifact removal automatici

### 📊 **Algoritmi di Validazione Attivi:**
- **Range Check**: Valori fisiologici automatici (HR: 30-200 BPM, SpO2: 70-100%)
- **Duration Check**: Durata minima clinica obbligatoria (HRV >60s)
- **Quality Check**: Segnale sufficiente per accuratezza (SpO2 quality ≥8/15)
- **Artifact Removal**: Filtri movimento per artefatti (HRV variazione <50% media)
- **Consecutive Validation**: SpO2 termina con 2 letture valide consecutive
- **Statistical Validation**: HRV con minimo 40-50 RR intervals per analisi

---

## 🎯 RACCOMANDAZIONI GENERALI

### ⏰ **Timing e Frequenze Implementate:**
1. **Background Polling**: Temperatura ogni 5 secondi continuativo
2. **HRV Collection**: Real-time da main HR stream (continuo durante test)
3. **SpO2 Measurement**: 50 secondi max, terminazione anticipata intelligente
4. **Log Debouncing**: Temperatura max 10s, SpO2 immediate, HRV per evento
5. **Data Persistence**: Salvataggio automatico SharedPreferences
6. **Export Timing**: On-demand con file sharing nativo

### 📈 **Protocolli di Monitoraggio Calibrati:**
- **Morning Routine**: HRV (1-2 min) + HR rest (2-3 min) pre-caffè
- **Daily Monitoring**: Temperatura continua background ogni 5s  
- **Spot Checks**: SpO2 on-demand (50s massimo per test)
- **Trend Analysis**: Export giornaliero/completo con timestamp precisi
- **Quality Assurance**: Validazione automatica multi-livello per ogni sensore

### 💾 **Data Management:**
- **Export**: JSON completi con timestamp
- **Backup**: Dati persistenti in local storage
- **Sharing**: Condivisione sicura con professionisti

### ⚠️ **Limitazioni e Disclaimer:**
- Device non è dispositivo medico certificato
- Risultati per monitoraggio fitness/wellness
- Consultare medico per valutazioni cliniche
- Non sostituisce strumentazione medica professionale

---

## 📞 SUPPORTO E TROUBLESHOOTING

### 🔧 **Troubleshooting Tecnico Specifico:**
- **"Insufficient Duration (X.Xs)"**: Test HRV fermato prima dei 60s obbligatori
- **SpO2 quality <8/15**: Sensore sporco, mani fredde, o movimento durante test
- **HR instabile**: Device mal posizionato, verificare stream RR intervals nei SENSORI
- **Temperatura out of range**: Attesa 15 min acclimatazione o ambiente <18/>24°C
- **LED rosso non si accende**: Reset BLE device, riconnettere, riprovare comando 0x36
- **Export JSON vuoto**: Nessun test salvato in SharedPreferences, verificare permissions

### 📱 **Reset Device:**
- Funzione integrata nell'app per reset BLE
- Utilizzare se connessione instabile
- Riconnessione automatica dopo reset

---

## 📚 RIFERIMENTI SCIENTIFICI

- **Task Force of ESC/NASPE**: Heart Rate Variability Standards
- **Elite HRV**: Professional HRV monitoring protocols
- **Pulse Oximetry**: WHO guidelines for SpO2 measurement
- **Body Temperature**: Clinical thermometry best practices

---

*Documento aggiornato al 23 Luglio 2025 - App CL837 v1.0*
*Per aggiornamenti e supporto: consultare documentazione tecnica*
