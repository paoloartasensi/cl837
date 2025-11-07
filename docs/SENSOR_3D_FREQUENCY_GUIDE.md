# Guida Configurazione Sensore 3D (Accelerometro)

## 📱 File Creati

### 1. **Schermata Principale Configurazione 3D**
`lib/screens/sensor_3d_settings_screen.dart`

Schermata completa per configurare il sensore 3D con:
- Switch ON/OFF del sensore
- Slider per selezione frequenza
- 5 pulsanti rapidi per frequenze (25Hz, 50Hz, 100Hz, 200Hz, 400Hz)
- Indicatore consumo batteria
- Avvisi per frequenze alte
- Informazioni dettagliate

### 2. **Schermata Impostazioni Avanzate**
`lib/screens/advanced_settings_screen.dart`

Menu principale con accesso a tutte le funzionalità avanzate:
- Sensore 3D
- Sensore 6D (TODO)
- Frequenza cardiaca (TODO)
- SpO2 (TODO)
- Temperatura (TODO)
- Info dispositivo (TODO)
- Spegnimento
- Ripristino

### 3. **Widget Azioni Rapide**
`lib/widgets/sensor_3d_quick_actions.dart`

Widget compatto per azioni rapide sul sensore 3D:
- Pulsanti ON/OFF
- Pulsanti frequenza 25Hz, 100Hz, 400Hz
- Può essere inserito in qualsiasi schermata

---

## 🎯 Come Usare

### Metodo 1: Schermata Completa

```dart
import 'package:cl837/screens/sensor_3d_settings_screen.dart';

// Nel tuo widget
ElevatedButton(
  onPressed: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => Sensor3DSettingsScreen(service: _service),
      ),
    );
  },
  child: const Text('Configura Sensore 3D'),
)
```

### Metodo 2: Menu Impostazioni Avanzate

```dart
import 'package:cl837/screens/advanced_settings_screen.dart';

// Nel tuo widget
IconButton(
  icon: const Icon(Icons.settings),
  onPressed: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => AdvancedSettingsScreen(service: _service),
      ),
    );
  },
)
```

### Metodo 3: Widget Azioni Rapide

```dart
import 'package:cl837/widgets/sensor_3d_quick_actions.dart';

// Nel tuo layout
Column(
  children: [
    // ... altri widget ...
    Sensor3DQuickActionsWidget(service: _service),
    // ... altri widget ...
  ],
)
```

### Metodo 4: Uso Diretto API

```dart
// Abilita sensore
await _service.set3DEnabled(true);

// Imposta frequenza
await _service.set3DFrequency(2); // 100Hz

// Leggi stato
await _service.get3DStatus();

// Leggi frequenza
await _service.get3DFrequency();

// Ascolta cambiamenti
_service.sensor3DStatusStream.listen((status) {
  print('Sensore 3D: ${status.enabled ? "ON" : "OFF"}');
});

_service.sensor3DFrequencyStream.listen((freq) {
  print('Frequenza: ${freq.label}');
});
```

---

## ⚙️ Frequenze Disponibili

| Codice | Frequenza | Uso Consigliato | Consumo Batteria |
|--------|-----------|-----------------|------------------|
| **0** | 25Hz | Monitoraggio continuo 24/7, conteggio passi | 🟢 Minimo |
| **1** | 50Hz | Attività quotidiana, walking | 🟢 Basso |
| **2** | 100Hz | **CONSIGLIATO** - Sport moderato, running | 🟡 Medio |
| **3** | 200Hz | Sport intenso, analisi biomeccanica | 🟠 Alto |
| **4** | 400Hz | Test brevi, ricerca scientifica | 🔴 MASSIMO |

---

## ⚠️ AVVERTENZE IMPORTANTI

### Frequenza 400Hz
- ❌ **NON usare per monitoraggio continuo**
- ⚡ Batteria si scarica in poche ore
- 📡 Genera MOLTI pacchetti BLE/secondo
- 💻 Può causare lag dell'app
- ✅ **Solo per test brevi** (< 10 minuti)

### Frequenza 200Hz
- ⚠️ Consumo alto
- 🔋 Batteria dura circa 6-8 ore
- ✅ OK per sessioni sportive brevi (1-2 ore)

### Frequenza 100Hz (CONSIGLIATO)
- ✅ Ottimo equilibrio precisione/batteria
- 🔋 Batteria dura tutto il giorno
- ✅ Ideale per uso quotidiano + sport

### Frequenze 25Hz / 50Hz
- ✅ Massima durata batteria
- ✅ OK per monitoraggio 24/7
- ⚠️ Meno preciso per movimenti rapidi

---

## 📊 Esempi d'Uso Pratico

### Scenario 1: Monitoraggio 24/7
```dart
// Imposta frequenza bassa per durata batteria massima
await service.set3DFrequency(0); // 25Hz
await service.set3DEnabled(true);
```

### Scenario 2: Sessione Running/Cycling
```dart
// Frequenza media per buon equilibrio
await service.set3DFrequency(2); // 100Hz
await service.set3DEnabled(true);

// Al termine
await service.set3DFrequency(0); // Torna a 25Hz
```

### Scenario 3: Analisi Biomeccanica (Breve)
```dart
// Frequenza alta solo per test brevi
await service.set3DFrequency(4); // 400Hz
await service.set3DEnabled(true);

// IMPORTANTE: Disabilitare dopo max 10 minuti!
await Future.delayed(Duration(minutes: 10));
await service.set3DEnabled(false);
```

### Scenario 4: Risparmio Batteria Massimo
```dart
// Disabilita sensore quando non serve
await service.set3DEnabled(false);
```

---

## 🔧 Funzionalità della Schermata

### `Sensor3DSettingsScreen`

#### Features:
1. **Status Card**
   - Visualizza stato (Abilitato/Disabilitato)
   - Switch per abilitare/disabilitare
   - Mostra frequenza corrente

2. **Frequency Card**
   - Slider interattivo (0-4)
   - 5 pulsanti per selezione rapida
   - Indicatore consumo batteria colorato
   - Avvisi automatici per freq alte

3. **Info Card**
   - Spiegazione funzionalità
   - Consigli d'uso
   - Quando usare frequenze alte/basse

#### Sicurezza:
- ⚠️ Dialog di conferma per frequenze ≥ 200Hz
- 🔴 Warning speciale per 400Hz
- 📊 Indicatori visivi consumo batteria
- 💡 Suggerimenti contestuali

---

## 🎨 UI/UX Features

### Colori Indicatori Batteria
- **Verde** → 25Hz, 50Hz (consumo minimo/basso)
- **Arancione** → 100Hz (consumo medio)
- **Arancione Scuro** → 200Hz (consumo alto)
- **Rosso** → 400Hz (consumo massimo)

### Feedback Visivo
- ✅ Snackbar verde per successo
- ❌ Snackbar rosso per errori
- ⏳ Loading indicator durante operazioni
- 🔄 Pulsante refresh per aggiornare stato

---

## 🧪 Test delle Funzionalità

### Test Base
```dart
// 1. Verifica connessione
if (!service.isConnected) {
  print('❌ Dispositivo non connesso');
  return;
}

// 2. Leggi stato iniziale
await service.get3DStatus();
await service.get3DFrequency();

// 3. Abilita sensore
await service.set3DEnabled(true);
await Future.delayed(Duration(seconds: 1));

// 4. Cambia frequenza
for (int freq = 0; freq <= 4; freq++) {
  await service.set3DFrequency(freq);
  await Future.delayed(Duration(seconds: 2));
  print('Frequenza $freq impostata');
}

// 5. Disabilita
await service.set3DEnabled(false);
```

### Test Streams
```dart
// Ascolta cambiamenti stato
service.sensor3DStatusStream.listen((status) {
  print('📊 Sensore 3D Status: ${status.enabled}');
});

// Ascolta cambiamenti frequenza
service.sensor3DFrequencyStream.listen((freq) {
  print('📊 Frequenza: ${freq.value} (${freq.label})');
});
```

---

## 📝 Note Tecniche

### Protocollo BLE
- **Comando Get Status**: `0x74 0x00 0x0C`
- **Comando Set Status**: `0x74 0x00 0x0C [0/1]`
- **Comando Get Frequency**: `0x74 0x00 0x0B`
- **Comando Set Frequency**: `0x74 0x00 0x0B [0-4]`

### Stream Events
- `Sensor3DStatus` → stato ON/OFF
- `Sensor3DFrequency` → enum con value e label

### Compatibilità
- ✅ Compatibile SDK Android ufficiale
- ✅ Stessi comandi dell'app di debug cinese
- ✅ Protocollo Chileaf v0.6

---

## 🚀 Prossimi Passi

### TODO - Features da Implementare:
- [ ] Sensore 6D (Accelerometro + Giroscopio)
- [ ] Configurazione Heart Rate (min/max/goal)
- [ ] SpO2 Settings
- [ ] Temperature Settings
- [ ] Device Info Screen
- [ ] Storico dati 3D/6D

### TODO - Miglioramenti:
- [ ] Grafico real-time dati 3D
- [ ] Log storico cambiamenti frequenza
- [ ] Presets personalizzati (es. "Running Mode", "Sleep Mode")
- [ ] Auto-switch frequenza in base ad attività

---

## 📞 Supporto

Per domande o problemi:
1. Controlla i log di debug (`debugPrint`)
2. Verifica connessione dispositivo
3. Prova comandi base via API prima dell'UI
4. Consulta `DEVICE_CONTROL_COMMANDS.md` per dettagli protocollo

---

## 🎯 Riepilogo Rapido

**Cosa puoi fare ORA:**
✅ Abilitare/Disabilitare sensore 3D  
✅ Impostare frequenza 25/50/100/200/400 Hz  
✅ Vedere stato e frequenza corrente  
✅ Ricevere avvisi per frequenze alte  
✅ Monitorare consumo batteria  

**Raccomandazione:**
💡 **Usa 100Hz** per la maggior parte dei casi → ottimo equilibrio!
