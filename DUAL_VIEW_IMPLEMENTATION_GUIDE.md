# 🔧 Implementazione Modalità Doppie Vista (iOS + Android Style)

## ✅ Cosa È Già Stato Fatto

1. **Modello** (`historical_data.dart`):
   - ✅ Aggiunto `DailySleepSession` class
   - ✅ Metodo `SleepHistoryEntry.groupByDay()` per raggruppare con gap di 3 ore
   - ✅ Calcolo automatico statistiche giornaliere

2. **Screen** (`sleep_analysis_screen.dart`):
   - ✅ Aggiunto `ViewMode` enum (individual, daily)
   - ✅ Aggiunta lista `_dailySessions`
   - ✅ Aggiunto `_selectedDailySession`
   - ✅ Toggle nell'AppBar per cambiare vista
   - ✅ Listener aggiornato per raggruppare sessioni

## 🔨 Cosa Manca Da Completare

### 1. Rimuovere Codice Duplicato nel Selector

Il file `sleep_analysis_screen.dart` ha codice duplicato che va pulito.

**Cerca la riga ~195-228** e RIMUOVI tutto il blocco che inizia con:
```dart
            ),
            items: _sleepData.asMap().entries.map((entry) {
...fino a...
              });
            }),
```

### 2. Aggiungere i Nuovi Metodi Selector

**Dopo la funzione `_buildSleepSessionSelector()`**, aggiungi:

```dart
  Widget _buildDailySessionsSelector() {
    return DropdownButtonFormField<DailySleepSession>(
      value: _selectedDailySession,
      decoration: InputDecoration(
        contentPadding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(8),
        ),
        filled: true,
        fillColor: Colors.white,
      ),
      items: _dailySessions.asMap().entries.map((entry) {
        final index = entry.key;
        final dailySession = entry.value;
        final phases = dailySession.calculateTotalPhases();
        final duration = Duration(minutes: phases.totalSleep);
        
        return DropdownMenuItem(
          value: dailySession,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                'Giorno ${index + 1} - ${DateFormat('dd/MM/yyyy HH:mm').format(dailySession.startTime)}',
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
              Text(
                '${dailySession.sessions.length} sessioni - ${duration.inHours}h ${duration.inMinutes % 60}m - Efficienza: ${dailySession.sleepEfficiency.toStringAsFixed(1)}%',
                style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
              ),
            ],
          ),
        );
      }).toList(),
      onChanged: (DailySleepSession? newValue) {
        setState(() {
          _selectedDailySession = newValue;
        });
      },
    );
  }

  Widget _buildIndividualSessionsSelector() {
    return DropdownButtonFormField<SleepHistoryEntry>(
      value: _selectedSession,
      decoration: InputDecoration(
        contentPadding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(8),
        ),
        filled: true,
        fillColor: Colors.white,
      ),
      items: _sleepData.asMap().entries.map((entry) {
        final index = entry.key;
        final session = entry.value;
        final phases = session.calculateSleepPhases();
        final duration = Duration(minutes: phases.totalSleep);
        
        return DropdownMenuItem(
          value: session,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                'Sessione ${index + 1} - ${DateFormat('dd/MM/yyyy HH:mm').format(session.timestamp)}',
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
              Text(
                'Durata: ${duration.inHours}h ${duration.inMinutes % 60}m - Efficienza: ${phases.sleepEfficiency.toStringAsFixed(1)}%',
                style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
              ),
            ],
          ),
        );
      }).toList(),
      onChanged: (SleepHistoryEntry? newValue) {
        setState(() {
          _selectedSession = newValue;
        });
      },
    );
  }
```

### 3. Aggiungere Metodi per Vista Giornaliera

**Dopo `_buildSleepSummaryCard()`**, aggiungi:

```dart
  Widget _buildDailySleepSummaryCard() {
    if (_selectedDailySession == null) return const SizedBox.shrink();
    
    final phases = _selectedDailySession!.calculateTotalPhases();
    final lightDuration = Duration(minutes: phases.lightSleep);
    final deepDuration = Duration(minutes: phases.deepSleep);
    final awakeDuration = Duration(minutes: phases.awake);
    final totalDuration = Duration(minutes: phases.totalMinutes);
    final sleepDuration = Duration(minutes: _selectedDailySession!.totalSleepMinutes);

    return Container(
      margin: const EdgeInsets.all(16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        gradient: LinearGradient(
          colors: [Colors.indigo.shade50, Colors.blue.shade50],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 8,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        children: [
          Row(
            children: [
              const Icon(Icons.calendar_today, color: Colors.indigo, size: 24),
              const SizedBox(width: 8),
              Expanded(
                child: Text(
                  'Sonno Giornaliero - ${DateFormat('dd/MM/yyyy').format(_selectedDailySession!.startTime)}',
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ),
            ],
          ),
          const SizedBox(height: 8),
          Text(
            '${_selectedDailySession!.sessions.length} sessioni raggruppate',
            style: TextStyle(fontSize: 14, color: Colors.grey.shade700),
          ),
          const SizedBox(height: 16),
          Row(
            children: [
              Expanded(
                child: _buildSummaryItem(
                  'Durata Totale',
                  '${totalDuration.inHours}h ${totalDuration.inMinutes % 60}m',
                  Colors.blue,
                  Icons.schedule,
                ),
              ),
              Expanded(
                child: _buildSummaryItem(
                  'Sonno Effettivo',
                  '${sleepDuration.inHours}h ${sleepDuration.inMinutes % 60}m',
                  Colors.purple,
                  Icons.night light_outlined,
                ),
              ),
            ],
          ),
          const SizedBox(height: 12),
          Row(
            children: [
              Expanded(
                child: _buildSummaryItem(
                  'Efficienza',
                  '${_selectedDailySession!.sleepEfficiency.toStringAsFixed(1)}%',
                  Colors.green,
                  Icons.trending_up,
                ),
              ),
              Expanded(
                child: _buildSummaryItem(
                  'Tempo Sveglio',
                  '${awakeDuration.inHours}h ${awakeDuration.inMinutes % 60}m',
                  Colors.orange,
                  Icons.visibility,
                ),
              ),
            ],
          ),
          const SizedBox(height: 12),
          Row(
            children: [
              Expanded(
                child: _buildSummaryItem(
                  'Sonno Leggero',
                  '${lightDuration.inHours}h ${lightDuration.inMinutes % 60}m',
                  Colors.cyan,
                  Icons.wb_sunny_outlined,
                ),
              ),
              Expanded(
                child: _buildSummaryItem(
                  'Sonno Profondo',
                  '${deepDuration.inHours}h ${deepDuration.inMinutes % 60}m',
                  Colors.indigo,
                  Icons.bedtime,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildDailySleepChart() {
    if (_selectedDailySession == null) return const SizedBox.shrink();
    
    // Usa tutti gli actions concatenati della giornata
    final allActions = _selectedDailySession!.allActions;
    
    return Container(
      margin: const EdgeInsets.all(16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 8,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        children: [
          const Row(
            children: [
              Icon(Icons.analytics, color: Colors.indigo),
              SizedBox(width: 8),
              Text(
                'Fasi del Sonno Giornaliero',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
            ],
          ),
          const SizedBox(height: 16),
          Expanded(
            child: LineChart(
              LineChartData(
                gridData: FlGridData(
                  show: true,
                  drawVerticalLine: false,
                  horizontalInterval: 1,
                  getDrawingHorizontalLine: (value) {
                    return FlLine(
                      color: Colors.grey.shade300,
                      strokeWidth: 1,
                    );
                  },
                ),
                titlesData: FlTitlesData(
                  leftTitles: AxisTitles(
                    sideTitles: SideTitles(
                      showTitles: true,
                      reservedSize: 60,
                      getTitlesWidget: (value, meta) {
                        switch (value.toInt()) {
                          case 0:
                            return const Text('Sonno\nProfondo', style: TextStyle(fontSize: 10), textAlign: TextAlign.center);
                          case 1:
                            return const Text('Sonno\nLeggero', style: TextStyle(fontSize: 10), textAlign: TextAlign.center);
                          case 2:
                            return const Text('Sveglio', style: TextStyle(fontSize: 10), textAlign: TextAlign.center);
                          default:
                            return const SizedBox.shrink();
                        }
                      },
                    ),
                  ),
                  bottomTitles: AxisTitles(
                    sideTitles: SideTitles(
                      showTitles: true,
                      reservedSize: 40,
                      interval: (allActions.length / 6).ceil().toDouble(),
                      getTitlesWidget: (value, meta) {
                        final index = value.toInt();
                        final totalMinutes = index * 5;
                        final hours = totalMinutes ~/ 60;
                        final mins = totalMinutes % 60;
                        return Padding(
                          padding: const EdgeInsets.only(top: 8),
                          child: Text(
                            '$hours:${mins.toString().padLeft(2, '0')}',
                            style: const TextStyle(fontSize: 10),
                          ),
                        );
                      },
                    ),
                  ),
                  topTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                  rightTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                ),
                borderData: FlBorderData(
                  show: true,
                  border: Border.all(color: Colors.grey.shade300),
                ),
                minX: 0,
                maxX: allActions.length.toDouble() - 1,
                minY: -0.5,
                maxY: 2.5,
                lineBarsData: [
                  LineChartBarData(
                    spots: _generateSleepPhaseSpotsFromActions(allActions),
                    isCurved: false,
                    color: Colors.indigo,
                    barWidth: 2,
                    isStrokeCapRound: true,
                    dotData: const FlDotData(show: false),
                    belowBarData: BarAreaData(
                      show: true,
                      gradient: LinearGradient(
                        colors: [
                          Colors.indigo.withOpacity(0.3),
                          Colors.cyan.withOpacity(0.3),
                          Colors.orange.withOpacity(0.3),
                        ],
                        stops: const [0.0, 0.5, 1.0],
                        begin: Alignment.bottomCenter,
                        end: Alignment.topCenter,
                      ),
                    ),
                  ),
                ],
                lineTouchData: LineTouchData(
                  enabled: true,
                  touchTooltipData: LineTouchTooltipData(
                    getTooltipItems: (touchedSpots) {
                      return touchedSpots.map((LineBarSpot touchedSpot) {
                        final index = touchedSpot.x.toInt();
                        final totalMinutes = index * 5;
                        final hours = totalMinutes ~/ 60;
                        final mins = totalMinutes % 60;
                        final phase = _getSleepPhaseText(touchedSpot.y);
                        
                        return LineTooltipItem(
                          'Tempo: $hours:${mins.toString().padLeft(2, '0')}\nFase: $phase',
                          const TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                          ),
                        );
                      }).toList();
                    },
                  ),
                ),
              ),
            ),
          ),
          const SizedBox(height: 16),
          _buildSleepPhaseLegend(),
        ],
      ),
    );
  }

  List<FlSpot> _generateSleepPhaseSpotsFromActions(List<int> actions) {
    final spots = <FlSpot>[];
    int zeroIndex = 0;
    List<int> pendingZeroIndices = [];
    
    for (int i = 0; i < actions.length; i++) {
      final action = actions[i];
      
      if (action > 20) {
        if (zeroIndex >= 3) {
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 0)); // Deep sleep
          }
        } else if (zeroIndex > 0) {
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 1)); // Light sleep
          }
        }
        zeroIndex = 0;
        pendingZeroIndices.clear();
        spots.add(FlSpot(i.toDouble(), 2)); // Awake
        
      } else if (action <= 20 && action > 0) {
        if (zeroIndex >= 3) {
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 0)); // Deep sleep
          }
        } else if (zeroIndex > 0) {
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 1)); // Light sleep
          }
        }
        zeroIndex = 0;
        pendingZeroIndices.clear();
        spots.add(FlSpot(i.toDouble(), 1)); // Light sleep
        
      } else {
        zeroIndex++;
        pendingZeroIndices.add(i);
      }
    }
    
    // Processa zeri finali
    if (zeroIndex >= 3) {
      for (int idx in pendingZeroIndices) {
        spots.add(FlSpot(idx.toDouble(), 0)); // Deep sleep
      }
    } else if (zeroIndex > 0) {
      for (int idx in pendingZeroIndices) {
        spots.add(FlSpot(idx.toDouble(), 1)); // Light sleep
      }
    }
    
    spots.sort((a, b) => a.x.compareTo(b.x));
    return spots;
  }
```

### 4. Aggiornare _showDetailedLog()

Cerca il metodo `_showDetailedLog()` e SOSTITUISCI l'inizio con:

```dart
  void _showDetailedLog() {
    final buffer = StringBuffer();
    List<int> actions;
    DateTime baseTimestamp;
    
    if (_viewMode == ViewMode.daily && _selectedDailySession != null) {
      actions = _selectedDailySession!.allActions;
      baseTimestamp = _selectedDailySession!.startTime;
    } else if (_viewMode == ViewMode.individual && _selectedSession != null) {
      actions = _selectedSession!.actions;
      baseTimestamp = _selectedSession!.timestamp;
    } else {
      return;
    }
    
    // ... resto del codice rimane uguale
```

## 🎯 Risultato Atteso

Dopo queste modifiche avrai:

1. **Toggle nell'AppBar** per switchare tra:
   - 📅 **Vista Giornaliera** (iOS-style): Sessioni raggruppate per gap di 3 ore
   - 📋 **Vista Individuale** (Android-style): Ogni mini-sessione separata

2. **Vista Giornaliera mostra**:
   - Numero di sessioni raggruppate
   - Statistiche aggregate (totale sonno, efficienza, ecc.)
   - Grafico continuo di tutta la giornata

3. **Vista Individuale mostra**:
   - Singole sessioni separate
   - Statistiche per ogni sessione
   - Grafico della singola sessione

## 🚀 Test

Dopo aver completato:
1. Scarica i dati del sonno
2. Prova a switchare tra le due modalità con il pulsante nell'AppBar
3. Verifica che la vista giornaliera raggruppi correttamente le sessioni vicine
