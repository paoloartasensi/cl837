# Blood Oxygen (SpO2) Measurement System
## Implementazione Pipeline Completa seguendo l'App Android Ufficiale

### 🏗️ Architettura del Sistema

Il nuovo sistema SpO2 è stato completamente riscritto seguendo la pipeline dell'app Android ufficiale, replicando esattamente il flusso di `BloodOxygenSearchActivity.java` e `CL880WearManager.java`.

### 📊 Pipeline Completa (Step by Step)

#### 1. **Inizializzazione e Setup**
```dart
// Equivalente a BloodOxygenSearchActivity.onCreate()
final service = ChileafExtendedService();

// Registrazione callback (equivalente a addBloodOxygenCallback(this))
service.setSpO2Callbacks(
  onValueReceived: (value) => updateUI(value),
  onComplete: () => handleCompletion(),
  onError: (error) => showError(error),
);
```

#### 2. **Avvio Misurazione**
```dart
// Equivalente al click del pulsante (BloodOxygenSearchActivity.onClick)
await service.startBloodOxygenMeasurement();

// Internamente esegue:
// 1. Reset dello stato (_spo2MeasurementActive = true)
// 2. Comando BLE: OfficialChileafCommands.setBloodOxygen(1)
// 3. Setup callback reception
// 4. Timer di 60 secondi
```

#### 3. **Comunicazione BLE**
```
SEND: [255, length+4, 55, 1, checksum]  // setBloodOxygen(1)
      ↓
DEVICE: Inizia misurazione SpO2 con LED rosso
      ↓
RECEIVE: [255, length+4, 55, data..., checksum]  // Dati SpO2
```

#### 4. **Elaborazione Dati**
```dart
void _handleBloodOxygenReceived(SpO2Data spo2Data) {
  // Equivalente a onBloodOxygenReceived(int i, String str, ...)
  
  if (spo2Data.value > 0 && spo2Data.isValidMeasurement) {
    String valueStr = spo2Data.value.toString();
    
    // Update UI (equivalente a runOnUiThread + mTxtBloodOxygenValue.setText)
    _onSpO2ValueReceived?.call(valueStr);
    
    // Pause measurement (equivalente al this.pause = true)
    _spo2MeasurementPaused = true;
    
    // Auto-complete dopo 2 secondi
    Future.delayed(Duration(seconds: 2), () => stopMeasurement());
  }
}
```

#### 5. **Stop e Cleanup**
```dart
// Equivalente a setBloodOxygen(0) + cleanup
await service.stopBloodOxygenMeasurement();

// Internamente esegue:
// 1. Comando STOP: OfficialChileafCommands.setBloodOxygen(0)
// 2. Cancel timer
// 3. Cancel data subscription
// 4. Reset stato
```

### 🔧 API Principali

#### Metodi di Controllo
```dart
// Start measurement (comando 0x37 con mode=1)
Future<void> startBloodOxygenMeasurement()

// Stop measurement (comando 0x37 con mode=0)  
Future<void> stopBloodOxygenMeasurement()

// Force stop in caso di emergenza
Future<void> forceExitSpO2Mode()
```

#### Callback Setup
```dart
void setSpO2Callbacks({
  void Function(String spo2Value)? onValueReceived,  // Update UI
  void Function()? onComplete,                       // Measurement done
  void Function(String error)? onError,              // Error handling
})
```

#### Getters di Stato
```dart
bool get isBloodOxygenMeasurementActive    // Misurazione in corso
bool get isBloodOxygenMeasurementPaused    // Misurazione in pausa
String? get lastBloodOxygenValue           // Ultimo valore misurato
```

### 📱 Utilizzo nell'UI (Flutter Widget)

```dart
class MySpO2Widget extends StatefulWidget {
  @override
  _MySpO2WidgetState createState() => _MySpO2WidgetState();
}

class _MySpO2WidgetState extends State<MySpO2Widget> {
  String _currentValue = '--';
  bool _isMeasuring = false;

  @override
  void initState() {
    super.initState();
    
    // Setup callbacks
    service.setSpO2Callbacks(
      onValueReceived: (value) {
        setState(() {
          _currentValue = value;
        });
      },
      onComplete: () {
        setState(() {
          _isMeasuring = false;
        });
        // Qui potresti navigare alla schermata di dettagli
        // Navigator.push(context, MaterialPageRoute(...));
      },
      onError: (error) {
        showDialog(context: context, builder: (_) => AlertDialog(
          title: Text('Error'),
          content: Text(error),
        ));
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        // Display valore SpO2
        Text('$_currentValue%', style: TextStyle(fontSize: 48)),
        
        // Pulsante start/stop
        ElevatedButton(
          onPressed: _isMeasuring ? _stopMeasurement : _startMeasurement,
          child: Text(_isMeasuring ? 'Stop' : 'Start'),
        ),
      ],
    );
  }

  Future<void> _startMeasurement() async {
    setState(() => _isMeasuring = true);
    await service.startBloodOxygenMeasurement();
  }

  Future<void> _stopMeasurement() async {
    await service.stopBloodOxygenMeasurement();
    setState(() => _isMeasuring = false);
  }
}
```

### 🔍 Comando BLE Dettagliato

#### Comando Start (setBloodOxygen(1))
```
Frame: [0xFF, 0x06, 0x37, 0x01, 0x00, checksum]
       ↓     ↓     ↓     ↓     ↓     ↓
    Header  Len  Cmd   Mode  Pad  Check
```

#### Comando Stop (setBloodOxygen(0))
```
Frame: [0xFF, 0x06, 0x37, 0x00, 0x00, checksum]
       ↓     ↓     ↓     ↓     ↓     ↓
    Header  Len  Cmd   Mode  Pad  Check
```

#### Risposta Dispositivo (dati SpO2)
```
Frame: [0xFF, length+4, 0x37, spo2_value, pi_value, gesture, on_wrist, ..., checksum]
```

### 📊 Modello Dati SpO2

```dart
class SpO2Data {
  final int value;              // 0-100% (valore SpO2)
  final int piValue;            // 0-255 (Perfusion Index)
  final int gesture;            // 0/1 (postura polso)
  final int onWrist;            // 0/1 (contatto polso)
  final bool isReliable;        // Lettura affidabile
  final String quality;         // Descrizione qualità
  final DateTime timestamp;     // Timestamp misurazione
  
  // Getters di compatibilità
  bool get correctWristPosture => gesture == 1;
  bool get isWearing => onWrist == 1;
  bool get isValidMeasurement => value >= 70 && value <= 100 && isReliable;
}
```

### ⚡ Vantaggi del Nuovo Sistema

1. **Compatibilità Totale**: Utilizza esattamente gli stessi comandi dell'app Android ufficiale
2. **Pipeline Completa**: Replica fedelmente il flusso dall'UI al dispositivo
3. **Gestione Errori**: Robusto error handling e recovery
4. **Stato Controllato**: Tracking preciso dello stato della misurazione
5. **Callback Strutturati**: API pulita per l'integrazione UI
6. **Auto-Cleanup**: Gestione automatica delle risorse
7. **Timeout Gestito**: Timer automatico di 60 secondi
8. **Dati Validati**: Controlli di qualità sui dati ricevuti

### 🚨 Gestione Errori

```dart
try {
  await service.startBloodOxygenMeasurement();
} catch (e) {
  // Gestione errori di avvio
  print('Failed to start: $e');
}

// Callback per errori durante la misurazione
service.setSpO2Callbacks(
  onError: (error) {
    // Gestione errori real-time
    print('Measurement error: $error');
  }
);

// Force stop in caso di problemi
await service.forceExitSpO2Mode();  // Emergency cleanup
```

### 📝 Compatibilità Legacy

Il nuovo sistema mantiene compatibilità con il codice esistente:

```dart
// Metodi legacy (ancora funzionanti)
await service.measureSpO2();           // → startBloodOxygenMeasurement()
await service.stopSpO2Measurement();   // → stopBloodOxygenMeasurement()
await service.exitSPO2Mode();         // → stopBloodOxygenMeasurement()

// Stream SpO2 (invariato)
service.spo2DataStream.listen((data) => {
  // Continua a funzionare normalmente
});
```

### 🎯 Best Practices

1. **Setup sempre i callback** prima di iniziare la misurazione
2. **Gestisci lo stato nell'UI** tramite i callback
3. **Non dimenticare il cleanup** quando esci dalla schermata
4. **Usa force stop** solo in caso di emergenza
5. **Valida sempre i dati** prima di mostrarli all'utente
6. **Implementa timeout UI** per feedback utente
7. **Salva i risultati** solo se la misurazione è valida

### 🔗 Collegamenti con Altri Sistemi

- **Data Persistence**: Integrazione con sistema di storage locale
- **Historical Data**: Collegamento con dati storici SpO2
- **Health Analysis**: Pipeline per analisi trend salute
- **Notifications**: Sistema di notifiche per valori critici
- **Export**: Esportazione dati per analisi esterne

Questo nuovo sistema rappresenta una implementazione completa e fedele della pipeline SpO2 dell'app Android ufficiale, garantendo massima compatibilità e affidabilità.
