# CL837 Vibration Hunt System - Discovery Mode

## 🎯 **IMPORTANTE SCOPERTA**

Il dispositivo CL837 **HA VIBRAZIONE** confermata dall'utente:
- ✅ Vibra per **Heart Rate alti**
- ✅ Vibra per **Power off signals**
- 🔍 **Obiettivo**: Trovare i comandi BLE per controllarla direttamente

## 🧪 **Sistema di Ricerca Vibrazione**

### Strategia di Ricerca

1. **Range Comandi Esteso**: 0x60-0x8F
   - Molti dispositivi IoT usano 0x60+ per funzioni avanzate
   - Test multipli parametri per comando (0x01, 0x02, 0x03, 0xFF)
   - Delay 500ms tra comandi per sentire vibrazioni

2. **HR Alarm Trigger**
   - Usa il sistema HR Alarm noto per triggare vibrazione automatica
   - Test duration: 10 secondi
   - Conferma che la vibrazione funziona via software

3. **Feedback Diretto**
   - L'utente deve confermare manualmente se sente vibrazioni
   - Progress tracking in tempo reale
   - Log dettagliato di tutti i comandi testati

### Comandi di Test

#### Range Vibrazione Candidati
```
0x60-0x6F: Potenziali comandi vibrazione base
0x70-0x7F: Comandi vibrazione avanzati  
0x80-0x8F: Comandi sistema/feedback
```

#### Parametri Testati
```
0x01: Vibrazione breve/bassa intensità
0x02: Vibrazione media
0x03: Vibrazione forte  
0xFF: Vibrazione continua/massima
```

## 🎮 **Come Usare**

### Preparazione
1. **Connetti** il dispositivo CL837
2. **Tieni** il dispositivo in mano per sentire vibrazioni
3. Vai al tab **"Vibration Hunt"**
4. Leggi le informazioni aggiornate

### Test Vibrazione Diretta
1. Clicca **"Test Comandi Vibrazione"**
2. Conferma di voler procedere con la scansione
3. **ATTENZIONE**: Il dispositivo potrebbe vibrare!
4. Monitora progress e **segnala** se senti vibrazioni

### Test HR Vibrazione  
1. Clicca **"Trigger HR Alarm per Vibrazione"**
2. Il dispositivo attiverà HR monitoring intensivo
3. Dovrebbe vibrare se rileva anomalie HR
4. Test duration: 10 secondi automatici

## 📊 **Risultati Attesi**

### Test Comandi Diretti
```
🔍 Test completati: 45/48 comandi. Hai sentito vibrazioni?
Progresso: 40/48 - Testati: 42
```

### Test HR Vibrazione
```
✅ HR Alarm attivato - Controlla se il dispositivo vibra → HR Alarm disabilitato dopo 10sec
```

## 🔧 **Implementazione Tecnica**

### Algoritmo Scansione
```dart
// Range esteso per vibrazione
List<int> vibrationCommands = [0x60...0x8F];

// Multi-parametro per comando
for (int param in [0x01, 0x02, 0x03, 0xFF]) {
  List<int> frame = [0xFF, 0x05, cmd, param, checksum];
  await sendRawCommand(frame);
  await Future.delayed(Duration(milliseconds: 500)); // Tempo per sentire vibrazione
}
```

### Checksum Validation
Usa sempre l'algoritmo Java verificato:
```dart
int sum = 0xFF + 0x05 + cmd + param;
int checksum = ((-sum) & 0xFF) ^ 0x3A;
```

## 🎯 **Obiettivi di Ricerca**

### Comando Vibrazione Target
Una volta trovato, dovremmo avere:
```
Comando: 0xXX (da scoprire)
Parametri: 
  - 0x00: Stop vibrazione
  - 0x01: Vibrazione breve  
  - 0x02: Vibrazione media
  - 0x03: Vibrazione lunga
  - 0xFF: Vibrazione continua
```

### Pattern Tipici
Candidati probabili basati su pattern comuni:
- **0x66**: Spesso usato per vibrazione
- **0x77**: Comando "buzz" comune  
- **0x88**: Feedback tattile
- **0x6A**: Actuator control

## 🚨 **Avvertenze**

1. **Volume Scansione**: 48 comandi × 4 parametri = 192 test
2. **Durata**: ~96 secondi total (500ms × 192)
3. **Batteria**: Consumo intensivo durante test
4. **Disconnessione**: Alcuni comandi potrebbero disconnettere device

## 📈 **Prossimi Passi**

1. **Esecuzione Test**: Utente testa scansione completa
2. **Identificazione Comando**: Nota quale comando causa vibrazione  
3. **Validazione**: Conferma riproducibilità
4. **Integrazione**: Aggiungi comando alla libreria ufficiale
5. **Documentazione**: Aggiorna protocollo con comando vibrazione

---

**Status**: 🔍 **DISCOVERY MODE ATTIVO**  
**Obiettivo**: Trovare comando BLE per vibrazione diretta  
**Metodo**: Scansione sistematica range 0x60-0x8F  
**Conferma Hardware**: ✅ Vibrazione presente (HR alarms + power off)  

---

*Aggiornato dopo conferma utente della presenza di vibrazione hardware*
