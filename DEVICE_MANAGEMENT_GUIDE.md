# 🏥 CL837 Device Management Guide - Guida Completa Gestione Dispositivo

## 📱 Panoramica dell'App CL837

L'app CL837 è un monitor sanitario professionale che gestisce il dispositivo Chileaf CL837 per rilevazioni mediche accurate. Supporta monitoraggio multi-sensore in tempo reale con validazione clinica dei dati.

---

## 🩸 1. SATURAZIONE OSSIGENO (SpO2)

### 📊 **Panoramica Tecnica**
- **Tecnologia**: Fotopletismografia (PPG) simile ai pulsossimetri medici
- **Durata test**: Massimo 50 secondi (ottimizzato WatchFit)
- **Terminazione intelligente**: Auto-stop con 2 letture valide consecutive
- **Comando BLE**: `0x37 mode=1` (setBloodOxygen) + `0x36` per LED

### 📋 **Preparazione del Test**
1. **Pulizia sensore**: Rimuovere sudore, lozioni o sporco dal polso
2. **Temperatura**: Riscaldare le mani se fredde (circolazione ottimale)
3. **Posizione**: Braccio rilassato, device rivolto verso l'alto
4. **Evitare**: Smalto scuro sulle unghie, movimenti durante il test

### 🎯 **Durante la Misurazione**
- **LED rosso**: Si accende automaticamente (indica misurazione attiva)
- **Postura**: Mantenere polso fermo e device verso l'alto
- **Immobilità**: Assoluta - anche piccoli movimenti compromettono la lettura
- **Criteri validità**: SpO2 70-100%, postura corretta, device indossato
- **Qualità segnale**: Minimo 8/15 (ideale >15 per precisione massima)

### 📊 **Interpretazione Risultati**
| Valore SpO2 | Stato | Azione |
|-------------|-------|--------|
| **98-100%** | 🟢 Ottimale | Normale |
| **95-97%** | 🟡 Normale | Monitorare se persistente |
| **90-94%** | 🟠 Basso | Consultare medico se sintomi |
| **<90%** | 🔴 Critico | **Attenzione medica immediata** |

### 🚨 **Troubleshooting SpO2**
- **"Adjust position"**: Girare device verso l'alto, polso fermo
- **Qualità <8/15**: Pulire sensore, riscaldare mani, ridurre movimenti
- **LED non si accende**: Reset BLE, riconnettere, riprovare comando

---

## ❤️‍🩹 2. VARIABILITÀ CARDIACA (HRV)

### 📊 **Panoramica Tecnica**
- **Standard**: Elite HRV compatibile (60 secondi minimi)
- **Metriche**: RMSSD, SDNN, pNN50, HR medio
- **Data source**: Main heart rate stream (stesso dei SENSORI)
- **Validazione**: Minimo 50 RR intervals per analisi affidabile

### 📋 **Preparazione del Test (Standard Elite HRV)**
1. **Timing ottimale**: Al mattino, prima del caffè
2. **Posizione**: Seduto comodo o sdraiato, completamente rilassato
3. **Riposo**: 5 minuti pre-test per stabilizzazione
4. **Evitare**: Caffè, alcol, stress, attività fisica 2 ore prima

### 🎯 **Durante la Misurazione**
- **Durata obbligatoria**: Minimo 60 secondi continuativi
- **Respirazione**: Naturale, profonda e regolare (no apnea)
- **Concentrazione**: Focus sul respiro, mente rilassata
- **Immobilità**: Movimento minimo, braccio completamente rilassato
- **Collezione dati**: Real-time da `heartRateData.rrIntervals`

### 📊 **Interpretazione Risultati HRV**
| RMSSD (ms) | Categoria | Interpretazione |
|------------|-----------|-----------------|
| **<15** | 🔴 Molto Bassa | Stress severo, overtraining, consultare medico |
| **15-30** | 🟠 Sotto Media | Possibile stress/affaticamento, maggior recupero |
| **30-50** | 🟡 Media | Stato normale, monitorare trend |
| **50-70** | 🟢 Buona | Buon recupero, forma fisica ottimale |
| **>70** | 🔵 Eccellente | HRV superiore, atleti élite |

### 🏥 **Validazione Clinica Implementata**
- ✅ Durata minima >60 secondi per validità scientifica
- ✅ RR Count >40 intervalli per analisi statistica affidabile
- ✅ Range fisiologico: 300-2000ms per intervallo (30-200 BPM)
- ✅ Controllo variabilità: <50% della media RR per coerenza dati
- ✅ Auto-calcolo: RMSSD, SDNN, pNN50 in tempo reale

### 🚨 **Troubleshooting HRV**
- **"Insufficient Duration"**: Test fermato prima dei 60s obbligatori
- **HR instabile**: Device mal posizionato, verificare RR intervals nei SENSORI
- **Pochi RR intervals**: Controllare connessione, riposizionare device

---

## 🫀 3. FREQUENZA CARDIACA (HR)

### 📊 **Panoramica Tecnica**
- **Monitoraggio**: Continuo in tempo reale con RR intervals
- **Precisione**: Supporta analisi HRV con intervalli millisecondo
- **Display**: BPM + RR intervals per monitoraggio avanzato
- **Comando**: Allarme HR per monitoraggio continuo

### 📋 **Configurazione HR**
- **Min HR**: Soglia minima (40-200 BPM)
- **Max HR**: Soglia massima (40-220 BPM)
- **Goal HR**: Obiettivo frequenza (40-200 BPM)
- **Allarme**: Enable/disable per superamento soglie

### 📊 **Interpretazione HR a Riposo**
| BPM | Categoria | Note |
|-----|-----------|------|
| **50-60** | 🔵 Atleti | Ben allenati o bradicardia fisiologica |
| **60-100** | 🟢 Normale | Range standard adulti a riposo |
| **100-150** | 🟡 Elevata | Possibile stress, ansia, tachicardia |
| **>150** | 🔴 Critica | **Consultare medico - possibile aritmia** |

### 🎯 **Best Practices HR**
- **Timing**: Al mattino dopo risveglio per HR basale
- **Posizione**: Seduto/sdraiato, rilassato 5 minuti pre-test
- **Durata**: Minimo 2-3 minuti per stabilizzazione
- **Posizionamento**: Device aderente 2-3 cm sopra l'osso del polso

---

## 🌡️ 4. TEMPERATURA CORPOREA

### 📊 **Panoramica Tecnica**
- **Multi-sensore**: Ambiente, polso, corpo (stimata)
- **Frequenza**: Automatica ogni 5 secondi in background
- **Precisione**: ±0.1°C accuratezza medica
- **Range**: 15-45°C (protezione sensore)

### 📊 **Tipi di Temperatura**
1. **Temperatura Ambientale**: Ambiente circostante
2. **Temperatura Polso**: Superficie skin device
3. **Temperatura Corporea**: Stima core temperature

### 📋 **Condizioni Ottimali**
- **Acclimatazione**: 15 minuti in ambiente stabile
- **Range ambiente**: 18-24°C per misure accurate
- **Evitare**: Bagni caldi, attività fisica intensa, esposizione diretta sole
- **Device**: Ben aderente al polso, non troppo stretto

### 📊 **Interpretazione Temperatura Corporea**
| Temperatura | Stato | Azione |
|-------------|-------|--------|
| **36.1-37.2°C** | 🟢 Normale | Range fisiologico |
| **37.3-38.0°C** | 🟡 Febbricola | Monitorare, possibile infezione lieve |
| **38.1-39.0°C** | 🟠 Febbre | Consultare medico se persistente |
| **>39.0°C** | 🔴 Febbre Alta | **Attenzione medica** |
| **<36.0°C** | 🔵 Ipotermia | Verificare condizioni ambiente |

### 🚨 **Troubleshooting Temperatura**
- **"Out of range"**: Attesa 15 min acclimatazione o ambiente <18/>24°C
- **Valori instabili**: Device non aderente, sudorazione eccessiva
- **Differenze eccessive**: Calibrazione sensore, condizioni ambientali

---

## 🚶‍♂️ 5. CONTAPASSI E ATTIVITÀ

### 📊 **Panoramica Tecnica**
- **Sensore**: Accelerometro 3D integrato
- **Calibrazione**: Automatica basata su pattern movimento
- **Metriche**: Passi, distanza stimata, calorie bruciate
- **Reset**: Automatico a mezzanotte

### 📋 **Ottimizzazione Contapassi**
- **Posizionamento**: Polso non dominante per accuratezza
- **Attività**: Normale camminata (non corsa estrema)
- **Calibrazione**: Prima settimana per adattamento algoritmo
- **Soglia**: Movimenti minimi per evitare falsi positivi

### 📊 **Interpretazione Attività Giornaliera**
| Passi/Giorno | Livello | Raccomandazione |
|--------------|---------|-----------------|
| **<5,000** | 🔴 Sedentario | Aumentare attività gradualmente |
| **5,000-7,500** | 🟠 Basso | Camminata quotidiana 30 min |
| **7,500-10,000** | 🟡 Moderato | Buon livello base |
| **10,000-15,000** | 🟢 Attivo | Ottimo per salute cardiovascolare |
| **>15,000** | 🔵 Molto Attivo | Atleti o lavori fisici |

---

## 🔋 6. GESTIONE BATTERIA

### 📊 **Monitoraggio Batteria**
- **Standard BLE**: Battery Service (UUID: 180F)
- **Range**: 0-100% con precisione 1%
- **Autonomia**: 3-7 giorni uso normale
- **Low battery**: Alert automatico <15%

### 📋 **Ottimizzazione Autonomia**
- **Display**: Ridurre luminosità se possibile
- **Connessioni**: Disconnettere quando non in uso
- **Background**: Temperatura polling ogni 5s (ottimizzato)
- **Ricarica**: Cavo magnetico originale, 2-3 ore full charge

---

## 🛠️ 7. CONFIGURAZIONI AVANZATE

### 📱 **Impostazioni Device**
- **Frequenze 3D**: 25Hz, 50Hz, 100Hz, 200Hz, 400Hz
- **Frequenze 6D**: 26Hz, 52Hz, 104Hz, 208Hz
- **LED Control**: Test diagnostico illuminazione
- **Factory Reset**: Ripristino impostazioni originali

### 🔧 **Modalità Rope Skipping**
- **Free Mode**: Modalità libera senza vincoli
- **Counter Mode**: Modalità contatore salti
- **Timer Mode**: Modalità timer cronometrato

### 📊 **Data Export**
- **Formato**: JSON completo con timestamp
- **Condivisione**: Share nativo iOS/Android
- **Backup**: Dati persistenti SharedPreferences
- **Sicurezza**: Export locale, privacy garantita

---

## 🚨 8. TROUBLESHOOTING GENERALE

### 🔧 **Problemi Connessione BLE**
1. **Reset Bluetooth**: Spegnere/riaccendere Bluetooth dispositivo
2. **Vicinanza**: Mantenere <1 metro durante connessione
3. **Interferenze**: Allontanare altri dispositivi BLE
4. **Riavvio app**: Force close e riapertura app
5. **Device reset**: Spegnere/riaccendere CL837

### 📱 **Problemi Specifici App**
- **Crash app**: Verificare permissions, aggiornare app
- **Dati mancanti**: Controllare connessione, riposizionare device
- **Export fallito**: Verificare storage permissions
- **Performance**: Chiudere app background, riavviare device

### 🏥 **Validazione Medica**
- **Dati anomali**: Ripetere test in condizioni ottimali
- **Trend preoccupanti**: Consultare medico con export dati
- **Emergenze**: SpO2 <90%, HR >150 a riposo, febbre >39°C
- **Disclaimer**: Device non sostituisce valutazione medica professionale

---

## 📞 9. SUPPORTO E RISORSE

### 🔧 **Codici Errore Comuni**
- **"Insufficient Duration (X.Xs)"**: Test HRV fermato prima dei 60s
- **"Signal quality <8/15"**: SpO2 necessita condizioni migliori
- **"Device not detected"**: Problemi connessione BLE
- **"Out of range"**: Temperatura/valori fuori soglie fisiologiche

### 📚 **Risorse Aggiuntive**
- **Elite HRV**: Standard compatibilità per professionisti
- **WatchFit Protocol**: Ottimizzazioni SpO2 implementate
- **Chileaf SDK v0.6**: Documentazione tecnica protocollo
- **Flutter BLE+**: Framework connettività affidabile

### 🏥 **Supporto Medico**
Per valutazioni cliniche professionali, esportare i dati e consultare il proprio medico. Il dispositivo CL837 è uno strumento di wellness, non un dispositivo medico diagnostico.

---

## 📝 **Note Versione**
- **Versione**: 1.0.0+1
- **SDK**: Chileaf v0.6
- **Flutter**: 3.0.0+
- **Compatibilità**: iOS 12+, Android 6.0+
- **Ultimo aggiornamento**: Luglio 2025

**⚠️ Disclaimer Medico**: Questo dispositivo è destinato al wellness e fitness. Non utilizzare per diagnosi mediche. In caso di sintomi gravi, consultare immediatamente un medico.
