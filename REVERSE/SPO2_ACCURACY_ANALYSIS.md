# Analisi Accuratezza SpO2 - CL837 Device

## Problema Identificato

Il dispositivo CL837 spesso fornisce letture SpO2 costantemente basse (es. 85-90%) causando falsi allarmi di salute, anche quando l'utente sta bene.

## Analisi Tecnica

### Struttura Dati SpO2 (Comando 0x37)

```java
// Dal WearReceivedDataCallback.java - Comando 55 (0x37)
onBloodOxygenReceived(bluetoothDevice, 
    bSwitch,        // byte 3: Stato misurazione (0/1)
    spO2Value,      // byte 4: Valore SpO2 (0-100%)
    gesture,        // byte 5: Postura polso (0=errata, 1=corretta)
    piValue,        // byte 6: Perfusion Index (0-255)
    onWrist         // byte 7: Contatto polso (0=non indossato, 1=indossato)
);
```

### Parametri di Qualità Critici

1. **Perfusion Index (PI)**: Indica la qualità del segnale
   - `0`: Nessun battito rilevato
   - `1-7`: Segnale debole (INAFFIDABILE)
   - `8-14`: Segnale buono
   - `15+`: Segnale eccellente

2. **Gesture**: Postura del polso
   - `0`: Postura errata (INAFFIDABILE)
   - `1`: Postura corretta

3. **OnWrist**: Contatto con il polso
   - `0`: Non indossato (INAFFIDABILE)
   - `1`: Indossato correttamente

## Causa del Problema

Il dispositivo **non filtra automaticamente** le letture SpO2 quando le condizioni di misurazione sono inadeguate. Anche con `piValue < 8`, `gesture = 0`, o `onWrist = 0`, il valore SpO2 viene comunque trasmesso e visualizzato.

## Soluzione Flutter

### 1. Implementazione Filtro Qualità

```dart
class SpO2Service {
  static const int MIN_PI_VALUE = 8;  // Soglia minima PI per affidabilità
  
  void parseSpO2Data(List<int> data) {
    if (data.length < 8) return;
    
    int bSwitch = data[3];
    int spO2Value = data[4];
    int gesture = data[5];
    int piValue = data[6];
    int onWrist = data[7];
    
    // VALIDAZIONE QUALITÀ SEGNALE
    bool isReliableReading = _validateSignalQuality(gesture, piValue, onWrist);
    
    if (isReliableReading) {
      // Solo letture affidabili
      _onSpO2Reading(spO2Value, piValue, true);
    } else {
      // Segnala lettura non affidabile
      _onSpO2Reading(spO2Value, piValue, false);
      print("SpO2 reading discarded: Poor signal quality");
    }
  }
  
  bool _validateSignalQuality(int gesture, int piValue, int onWrist) {
    // Controlla se tutte le condizioni per una lettura affidabile sono soddisfatte
    return gesture == 1 &&           // Postura corretta
           piValue >= MIN_PI_VALUE && // Segnale forte abbastanza
           onWrist == 1;              // Dispositivo indossato
  }
  
  void _onSpO2Reading(int value, int piValue, bool isReliable) {
    String qualityText = _getQualityText(piValue);
    
    SpO2Data spO2Data = SpO2Data(
      value: value,
      piValue: piValue,
      isReliable: isReliable,
      quality: qualityText,
      timestamp: DateTime.now(),
    );
    
    // Solo mostra/salva letture affidabili per analisi salute
    if (isReliable) {
      _healthMonitor.addSpO2Reading(spO2Data);
    }
    
    // UI può mostrare tutte le letture ma con indicatori di qualità
    _uiController.updateSpO2Display(spO2Data);
  }
  
  String _getQualityText(int piValue) {
    if (piValue == 0) return "Nessun battito rilevato";
    if (piValue < 8) return "Segnale debole";
    if (piValue < 15) return "Segnale buono";
    return "Segnale eccellente";
  }
}
```

### 2. Classe Dati SpO2

```dart
class SpO2Data {
  final int value;
  final int piValue;
  final bool isReliable;
  final String quality;
  final DateTime timestamp;
  
  const SpO2Data({
    required this.value,
    required this.piValue,
    required this.isReliable,
    required this.quality,
    required this.timestamp,
  });
  
  bool get shouldTriggerHealthAlert => isReliable && value < 95;
}
```

### 3. Monitoraggio Salute Intelligente

```dart
class HealthMonitor {
  static const int CRITICAL_SPO2_THRESHOLD = 90;
  static const int LOW_SPO2_THRESHOLD = 95;
  static const int MIN_RELIABLE_READINGS = 3;
  
  List<SpO2Data> _recentReadings = [];
  
  void addSpO2Reading(SpO2Data reading) {
    if (!reading.isReliable) return; // Ignora letture inaffidabili
    
    _recentReadings.add(reading);
    
    // Mantieni solo le ultime 10 letture affidabili
    if (_recentReadings.length > 10) {
      _recentReadings.removeAt(0);
    }
    
    _evaluateSpO2Trend();
  }
  
  void _evaluateSpO2Trend() {
    if (_recentReadings.length < MIN_RELIABLE_READINGS) return;
    
    // Calcola media delle ultime letture affidabili
    double avgSpO2 = _recentReadings
        .map((r) => r.value)
        .reduce((a, b) => a + b) / _recentReadings.length;
    
    // Controlla trend consistente di letture basse
    bool allReadingsLow = _recentReadings.every((r) => r.value < LOW_SPO2_THRESHOLD);
    
    if (avgSpO2 < CRITICAL_SPO2_THRESHOLD && allReadingsLow) {
      _triggerHealthAlert("SpO2 critico: ${avgSpO2.toStringAsFixed(1)}%");
    } else if (avgSpO2 < LOW_SPO2_THRESHOLD && allReadingsLow) {
      _triggerHealthWarning("SpO2 basso: ${avgSpO2.toStringAsFixed(1)}%");
    }
  }
}
```

### 4. Widget UI con Indicatori Qualità

```dart
class SpO2DisplayWidget extends StatelessWidget {
  final SpO2Data? currentReading;
  
  @override
  Widget build(BuildContext context) {
    if (currentReading == null) {
      return _buildNoDataWidget();
    }
    
    return Card(
      child: Column(
        children: [
          Text(
            '${currentReading!.value}%',
            style: TextStyle(
              fontSize: 32,
              fontWeight: FontWeight.bold,
              color: _getValueColor(),
            ),
          ),
          _buildQualityIndicator(),
          _buildInstructions(),
        ],
      ),
    );
  }
  
  Color _getValueColor() {
    if (!currentReading!.isReliable) return Colors.grey;
    if (currentReading!.value < 90) return Colors.red;
    if (currentReading!.value < 95) return Colors.orange;
    return Colors.green;
  }
  
  Widget _buildQualityIndicator() {
    return Row(
      children: [
        Icon(
          currentReading!.isReliable ? Icons.check_circle : Icons.warning,
          color: currentReading!.isReliable ? Colors.green : Colors.orange,
        ),
        Text(currentReading!.quality),
      ],
    );
  }
  
  Widget _buildInstructions() {
    if (currentReading!.isReliable) {
      return Text("Lettura affidabile");
    }
    
    return Text(
      "Per migliorare l'accuratezza:\n"
      "• Indossa il dispositivo saldamente\n"
      "• Mantieni il polso fermo\n"
      "• Posiziona il sensore correttamente",
      style: TextStyle(fontSize: 12, color: Colors.orange),
    );
  }
}
```

## Raccomandazioni Implementazione

### 1. **Filtro Primario**
- Scarta letture con PI < 8
- Scarta letture con gesture = 0 
- Scarta letture con onWrist = 0

### 2. **Aggregazione Intelligente**
- Usa media delle ultime 3-5 letture affidabili
- Allarmi solo su trend consistenti
- Timeout per nuove letture (30-60 secondi)

### 3. **Feedback Utente**
- Mostra indicatori qualità segnale in tempo reale
- Istruzioni per migliorare la misurazione
- Distingui visivamente letture affidabili/inaffidabili

### 4. **Soglie Conservative**
- SpO2 < 90%: Allarme critico (solo se letture affidabili)
- SpO2 < 95%: Avviso (solo se trend consistente)
- PI < 8: Nessun allarme (qualità insufficiente)

## Test Consigliati

1. **Test in Condizioni Ideali**: Polso fermo, dispositivo saldo
2. **Test di Movimento**: Verifica filtri durante attività
3. **Test di Posizionamento**: Sensore mal posizionato
4. **Test di Affidabilità**: Confronto con ossimetro medico certificato

Questa implementazione dovrebbe eliminare i falsi allarmi mantenendo la sicurezza dell'utente.
