# 🧪 Guida al Test e Confronto dei Dati del Sonno

## 📅 Data: 21 Ottobre 2025

## ✅ Modifiche Completate

### **1. Parsing Corretto Implementato**
- ✅ Algoritmo "accumula e decidi" conforme al SDK ufficiale
- ✅ Ogni action index = 5 minuti (300000 ms)
- ✅ Classificazione posticipata degli zeri consecutivi

### **2. Log Dettagliato Aggiunto**
- ✅ Output nel console log formato Android (nel service)
- ✅ Button debug nell'UI per visualizzare log dettagliato
- ✅ Formato identico all'app di esempio del SDK

## 🔬 Come Testare

### **Passo 1: Connetti il Dispositivo**
1. Apri l'app Flutter
2. Connetti il braccialetto CL831
3. Vai su "Analisi del Sonno"

### **Passo 2: Scarica i Dati**
1. Premi il pulsante "Aggiorna dati" (icona refresh)
2. Aspetta 3-5 secondi per il download completo
3. Osserva il console log (debug output)

### **Passo 3: Visualizza Log Dettagliato**
1. Una volta caricati i dati, premi l'icona 🐛 (bug report) nell'AppBar
2. Si aprirà un dialog con il log in formato Android
3. Confronta con gli screenshot dell'app Android forniti

### **Passo 4: Confronta i Risultati**

#### **Dal Console Log:**
```
═══════════════════════════════════════════════════════
📋 DETAILED SLEEP DATA (Android App Format)
═══════════════════════════════════════════════════════
utc:2025-10-17 20:33:24
action Index: deep Sleep
utc:2025-10-17 20:38:24
action Index: deep Sleep
utc:2025-10-17 20:43:24
action Index: deep Sleep
...
═══════════════════════════════════════════════════════
```

#### **Dall'App Android (Screenshot forniti):**
```
utc:2025-10-17 20:33:24
action Index: deep Sleep
utc:2025-10-17 20:38:24
action Index: deep Sleep
utc:2025-10-17 20:43:24
action Index: deep Sleep
...
```

## ✅ Cosa Verificare

### **1. Timestamp Corretti**
- ✅ Ogni entry deve essere distanziata di **5 minuti**
- ✅ Es: 20:33 → 20:38 → 20:43 → 20:48 → 20:53...

### **2. Classificazione Corretta**
Confronta che le classificazioni siano identiche:
- **"deep Sleep"** = sonno profondo (3+ zeri consecutivi)
- **"light sleep"** = sonno leggero (valori 1-20 o <3 zeri)
- **"not Sleep"** = sveglio (valori >20)

### **3. Statistiche Complessive**
Nel riepilogo dovresti vedere:
- **Durata Totale**: es. 2h 30m
- **Sonno Profondo**: es. 1h 15m
- **Sonno Leggero**: es. 45m
- **Tempo Sveglio**: es. 30m
- **Efficienza**: es. 80.0%

## 🔍 Dati di Test Attesi (dagli Screenshot)

### **Sessione 17 Ottobre 2025 (20:33-22:28)**
```
Inizio: 2025-10-17 20:33:24
Fine: circa 2025-10-17 22:28:24
Durata: ~115 minuti (~23 blocchi da 5 min)

Pattern atteso:
- 20:33-20:43: 3 deep sleep
- 20:53: 1 light sleep
- 20:58: 1 not sleep
- 21:03-21:28: mix deep/light sleep
- ...
```

### **Sessione 20 Ottobre 2025 (16:46-18:11)**
```
Inizio: 2025-10-20 16:46:21
Fine: circa 2025-10-20 18:11:21
Durata: ~85 minuti (~17 blocchi da 5 min)

Pattern atteso:
- Tutto "light sleep" (riposo pomeridiano)
- Ogni entry distanziata di 5 minuti
```

## 🐛 Debug Avanzato

### **Console Log Completo**
Cerca nel console output questi messaggi:
```
🌙💤 Processing Sleep Data 0x31 (OFFICIAL SDK FORMAT)...
📊 Activity indices: X blocks (Y minutes)
📊 Breakdown: Awake=N×5min, Light=M×5min, Still=K×5min
═══════════════════════════════════════════════════════
📋 DETAILED SLEEP DATA (Android App Format)
═══════════════════════════════════════════════════════
[output dettagliato qui]
```

### **Verifica Calcolo Fasi**
```
📈 SESSION TOTAL PHASES:
   💤 Deep sleep: X minutes
   🌙 Light sleep: Y minutes
   😴 Awake: Z minutes
   ✅ Sleep efficiency: W%
```

## 📊 Grafico Interattivo

### **Asse X (Temporale)**
- Ogni punto sull'asse X rappresenta un indice (0, 1, 2, 3...)
- Il tempo mostrato è: `indice × 5 minuti`
- Es: indice 6 = 30 minuti dall'inizio = 0:30

### **Asse Y (Fasi)**
- 0 = Sonno Profondo (Deep Sleep)
- 1 = Sonno Leggero (Light Sleep)
- 2 = Sveglio (Awake)

### **Tooltip**
Quando tocchi il grafico, dovresti vedere:
```
Tempo: 0:35
Fase: Sonno Profondo
```

## ✨ Risultati Attesi

Se tutto funziona correttamente:
1. ✅ Il log formato Android è **identico** agli screenshot
2. ✅ Le statistiche totali corrispondono ai dati raw
3. ✅ Il grafico mostra le transizioni di fase corrette
4. ✅ I timestamp sono distanziati esattamente di 5 minuti
5. ✅ La classificazione segue la logica: zeri accumulati → decidi dopo

## 🎯 Esempio di Verifica Manuale

Prendi uno screenshot dell'app Android e confronta riga per riga:

**Android:**
```
utc:2025-10-17 20:33:24
action Index: deep Sleep
utc:2025-10-17 20:38:24
action Index: deep Sleep
```

**Flutter (nel dialog debug):**
```
utc:2025-10-17 20:33:24
action Index: deep Sleep
utc:2025-10-17 20:38:24
action Index: deep Sleep
```

Dovrebbero essere **IDENTICI**! ✅

## 🚀 Prossimi Passi

Una volta verificato che funziona:
1. Testa con diversi giorni di dati
2. Verifica che l'accumulo di sessioni multiple funzioni
3. Controlla che la cache persista tra riavvii
4. Valida l'efficienza del sonno calcolata

## 📞 In Caso di Discrepanze

Se noti differenze:
1. Copia il log completo dal console
2. Fai screenshot dell'app Android
3. Confronta i raw activity indices (array di numeri)
4. Verifica che il dispositivo sia sincronizzato (stessa timezone)
