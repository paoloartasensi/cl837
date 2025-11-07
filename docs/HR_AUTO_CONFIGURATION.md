# Heart Rate Auto-Configuration

## 🎯 Overview

Il sistema **Auto-Configuration** calcola automaticamente le soglie di frequenza cardiaca ottimali basate sull'età e sul profilo dell'utente, eliminando la necessità di configurazione manuale.

## 📊 Valori Calcolati

### Formula Base
- **Max HR**: `220 - età` (Formula standard per età)

### Soglie Raccomandate
| Parametro | Formula | % Max HR | Descrizione |
|-----------|---------|----------|-------------|
| **Min HR** | `max * 0.50` | 50% | Soglia minima (resting HR) |
| **Goal HR** | `max * 0.75` | 75% | Obiettivo fitness (zona aerobica) |
| **Max Safe** | `max * 0.90` | 90% | Limite massimo sicuro per allenamento |

## 💡 Esempio per Età 35 Anni

```dart
UserInfo user = UserInfo(age: 35, gender: 1, weight: 75, height: 175, userId: 12345);

print(user.maxHeartRate);              // 185 BPM (220 - 35)
print(user.recommendedMinHeartRate);   // 93 BPM  (50% max)
print(user.recommendedGoalHeartRate);  // 139 BPM (75% max)
print(user.recommendedMaxHeartRate);   // 167 BPM (90% max)
```

## 🔧 Utilizzo

### Metodo 1: Auto-Configuration Completa

```dart
final userInfo = UserInfo(
  age: 35,
  gender: 1,     // 0=female, 1=male
  weight: 75,    // kg
  height: 175,   // cm
  userId: 12345,
);

// Configura automaticamente tutto
await service.autoConfigureHeartRate(userInfo);
```

Questo metodo:
1. Calcola min/max/goal in base all'età
2. Invia comando `0x43` (setHeartRateStatus)
3. Invia comando `0x45` (setHeartRateMax)

### Metodo 2: Accesso ai Valori Raccomandati

```dart
final settings = userInfo.recommendedHeartRateSettings;
print(settings['min']);   // 93 BPM
print(settings['max']);   // 167 BPM
print(settings['goal']);  // 139 BPM

// Usa i valori manualmente
await service.setHeartRateStatus(
  settings['min']!,
  settings['max']!,
  settings['goal']!,
);
```

### Metodo 3: Integrazione con Set User Info

```dart
// Nel test screen, dopo setUserInfo:
await service.setUserInfo(age, gender, weight, height, userId);

// Auto-configura HR
final userInfo = UserInfo(age: age, gender: gender, weight: weight, height: height, userId: userId);
await service.autoConfigureHeartRate(userInfo);
```

## 🏃 Zone di Allenamento

Il modello `UserInfo` fornisce anche le zone di allenamento standard:

```dart
final zones = userInfo.heartRateZones;

// Zone disponibili:
zones['resting'];    // 50% max - Riposo
zones['warmUp'];     // 60% max - Riscaldamento
zones['fatBurn'];    // 70% max - Brucia grassi 🔥
zones['aerobic'];    // 80% max - Aerobico 💪
zones['anaerobic'];  // 90% max - Anaerobico ⚡
zones['maximum'];    // 100% max - Massimo 🚀
```

## 📈 Confronto per Età

| Età | Max HR | Min HR | Goal HR | Max Safe |
|-----|--------|--------|---------|----------|
| 20 | 200 BPM | 100 BPM | 150 BPM | 180 BPM |
| 30 | 190 BPM | 95 BPM | 143 BPM | 171 BPM |
| 40 | 180 BPM | 90 BPM | 135 BPM | 162 BPM |
| 50 | 170 BPM | 85 BPM | 128 BPM | 153 BPM |
| 60 | 160 BPM | 80 BPM | 120 BPM | 144 BPM |
| 70 | 150 BPM | 75 BPM | 113 BPM | 135 BPM |

## ✅ Vantaggi

1. **Sicurezza**: Soglie appropriate per età → riduce rischio sovrallenamento
2. **Precisione**: Calcoli basati su formule mediche standard
3. **Semplicità**: Un solo comando → configura tutto
4. **Personalizzazione**: Valori specifici per ogni utente
5. **Consistenza**: Stessi calcoli usati da dispositivi fitness professionali

## ⚠️ Validazione

Il sistema include validazione automatica:

```dart
// Valori troppo alti per l'età
if (maxHR > userInfo.maxHeartRate) {
  print('⚠️ Max HR supera il massimo per età');
}

// Valori non sicuri
if (maxHR > userInfo.recommendedMaxHeartRate) {
  print('⚠️ Max HR supera la soglia sicura (90% max)');
}

// Obiettivo non valido
if (goalHR > maxHR || goalHR < minHR) {
  print('❌ Goal HR deve essere tra Min e Max');
}
```

## 🎨 UI Integration

Nel test screen, i valori raccomandati sono mostrati automaticamente:

```dart
// Display in UI
_buildDataRow('Min HR', '${userInfo.recommendedMinHeartRate} BPM (50% max)');
_buildDataRow('Goal HR', '${userInfo.recommendedGoalHeartRate} BPM (75% max)');
_buildDataRow('Max HR', '${userInfo.recommendedMaxHeartRate} BPM (90% max)');
```

## 📚 Esempi Completi

Vedi `lib/examples/heart_rate_auto_config_example.dart` per:
- Example 1: Basic Auto-Configuration
- Example 2: Age-Based Comparison
- Example 3: Manual vs Auto Configuration
- Example 4: Training Zones
- Example 5: Listen and Auto-Configure
- Example 6: Validate Custom Settings

## 🔗 Comandi Dispositivo

| Comando | Hex | Descrizione | Parametri |
|---------|-----|-------------|-----------|
| setHeartRateStatus | 0x43 | Imposta min/max/goal | [min, max, goal] |
| setHeartRateMax | 0x45 | Imposta max per età | [max] |

L'auto-configuration invia entrambi i comandi in sequenza per configurazione completa.

## 🧮 Formule Mediche

### Max Heart Rate
- **Formula Universale**: `220 - età`
- Base per tutti i calcoli successivi

### Training Zones (% of Max HR)
- **Resting**: 50-60% - Recupero attivo
- **Fat Burn**: 60-70% - Metabolismo lipidi
- **Aerobic**: 70-80% - Fitness cardiovascolare
- **Anaerobic**: 80-90% - Performance atletica
- **Maximum**: 90-100% - Sforzo massimale (brevi periodi)

### Safe Limits
- **Min Safe**: 50% max (non scendere sotto durante attività)
- **Max Safe**: 90% max (non superare per allenamenti prolungati)
- **Goal**: 75% max (zona aerobica ottimale)

## 🚀 Best Practices

1. **Sempre auto-configure dopo setUserInfo**
   ```dart
   await service.setUserInfo(...);
   await service.autoConfigureHeartRate(userInfo);
   ```

2. **Aggiorna quando l'età cambia**
   - Ogni anno, le soglie devono essere ricalcolate
   - Max HR diminuisce di 1 BPM per anno

3. **Monitora zone durante workout**
   - Usa heartRateZones per feedback real-time
   - Mostra in UI la zona corrente

4. **Valida input personalizzati**
   - Prima di accettare valori custom dall'utente
   - Confronta con recommendedHeartRateSettings

## 📱 Test

Per testare:
1. Apri Advanced Features Test Screen
2. Vai a sezione "👤 User Info & Device Status"
3. Premi "Set User Info"
4. Inserisci età (es: 35 anni)
5. Premi "Set"
6. Verifica il SnackBar con valori calcolati
7. Check logs per vedere i comandi inviati

Il sistema mostrerà:
```
Min: 93 BPM (50% max)
Goal: 139 BPM (75% max)  
Max: 167 BPM (90% max)
```

Tutto configurato automaticamente! 🎉
