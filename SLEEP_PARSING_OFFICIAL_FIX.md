# 🛠️ Correzione Parsing Dati Sonno - Logica Ufficiale SDK

## 📋 Data: 21 Ottobre 2025

## ✅ Modifiche Implementate

### **Problema Identificato**
Il parsing dei dati del sonno **NON** seguiva la logica ufficiale del SDK Android fornito da Chileaf.

### **Logica Precedente (ERRATA)**
```dart
// ❌ Classificazione IMMEDIATA degli zeri
if (action == 0) {
  consecutiveZeros++;
  if (consecutiveZeros >= 3) {
    yValue = 0; // Deep sleep SUBITO
  } else {
    yValue = 1; // Light sleep SUBITO
  }
}
```

### **Logica Corretta (da HistorySleepActivity.java)**
```java
// ✅ Gli zeri vengono ACCUMULATI
if (action == 0) {
  zeroIndex++; // Accumula, NON classifica ancora
} else {
  // Solo QUI classifichiamo gli zeri accumulati
  if (zeroIndex >= 3) {
    // Erano deep sleep
  } else if (zeroIndex > 0) {
    // Erano light sleep
  }
  zeroIndex = 0;
  // POI classifica il valore corrente
}
```

## 🔧 File Modificati

### 1. **`lib/models/historical_data.dart`**

#### `SleepHistoryEntry.calculateSleepPhases()`
- ✅ Implementato algoritmo "accumula e decidi" per gli zeri
- ✅ Gestione corretta degli zeri finali
- ✅ Classificazione: `> 20` = sveglio, `1-20` = light sleep, `3+ zeri consecutivi` = deep sleep
- ✅ Conversione corretta: ogni action index = 5 minuti (300000 ms)

#### `SleepData31.calculateSleepPhases()`
- ✅ Stessa logica applicata per il comando 0x31
- ✅ Coerenza tra entrambi i metodi di parsing

### 2. **`lib/screens/sleep_analysis_screen.dart`**

#### `_generateSleepPhaseSpots()`
- ✅ Riscritta logica del grafico per seguire l'algoritmo ufficiale
- ✅ Utilizzo di `pendingZeroIndices` per tracciare gli zeri accumulati
- ✅ Classificazione posticipata fino al prossimo valore non-zero

#### Asse X del Grafico
- ✅ Corretta visualizzazione del tempo: `index * 5 minuti`
- ✅ Tooltip aggiornato per mostrare il tempo reale
- ✅ Etichette asse X calcolate correttamente

## 📊 Regole di Classificazione Ufficiali

Secondo `from_esther_sleep_parsing.md` e il codice SDK:

| Valore Activity Index | Classificazione | Note |
|----------------------|-----------------|------|
| `> 20` | **Sveglio** (Not Sleeping) | Alta attività fisica |
| `1 - 20` | **Sonno Leggero** (Light Sleep) | Bassa attività fisica |
| `0` | **Accumulato** | Deciso solo dopo aver visto il prossimo valore |
| `3+ zeri consecutivi` | **Sonno Profondo** (Deep Sleep) | Solo se seguiti da valore non-zero |

## ⏱️ Granularità Temporale

- **1 action index = 5 minuti** (300000 millisecondi)
- Confermato da: `utc + (i1 * 300000)` nel codice Java
- Confermato dalla documentazione: "Each 1 byte of activity index represents 5 minutes"

## 🧪 Esempio di Parsing

Input: `[0, 0, 0, 5, 10, 25, 0, 0, 15]`

**Vecchia logica (errata):**
- `[0,0,0]` → Profondo, Profondo, Profondo
- `[5]` → Leggero
- `[10]` → Leggero  
- `[25]` → Sveglio
- `[0,0]` → Leggero, Leggero
- `[15]` → Leggero

**Nuova logica (corretta):**
- `[0,0,0]` → accumulati (3 zeri)
- `[5]` → i 3 zeri erano **Profondo** (perché ≥3), poi questo è **Leggero**
- `[10]` → **Leggero**
- `[25]` → **Sveglio**
- `[0,0]` → accumulati (2 zeri)
- `[15]` → i 2 zeri erano **Leggero** (perché <3), poi questo è **Leggero**

Risultato finale:
- Deep Sleep: 3 blocchi = **15 minuti**
- Light Sleep: 4 blocchi = **20 minuti**  
- Awake: 1 blocco = **5 minuti**
- Totale: 8 blocchi = **40 minuti**

## ✨ Benefici

1. **Conformità al SDK Ufficiale**: Il parsing ora segue esattamente `HistorySleepActivity.java`
2. **Precisione Migliorata**: Classificazione più accurata delle fasi del sonno
3. **Coerenza**: Entrambi i metodi (`SleepHistoryEntry` e `SleepData31`) usano la stessa logica
4. **Visualizzazione Corretta**: Il grafico mostra i tempi reali (ogni punto = 5 minuti)
5. **Manutenibilità**: Codice più chiaro e commentato

## 📚 Riferimenti

- **Documentazione**: `docs/CL831SE_Android_SDK_V3.0.4/from_esther_sleep_parsing.md`
- **Codice Sorgente**: `CL831_Sample/app/src/main/java/com/chileaf/cl831/sample/HistorySleepActivity.java`
- **Comando**: `0x31` - Sleep Data Request
