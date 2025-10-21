import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:intl/intl.dart';
import '../models/historical_data.dart';
import '../chileaf_extended_service.dart';

/// Schermo dedicato per la visualizzazione dei dati del sonno
/// Basato su HistorySleep.java con grafici interattivi delle fasi del sonno
class SleepAnalysisScreen extends StatefulWidget {
  final ChileafExtendedService chileafService;

  const SleepAnalysisScreen({
    super.key,
    required this.chileafService,
  });

  @override
  State<SleepAnalysisScreen> createState() => _SleepAnalysisScreenState();
}

class _SleepAnalysisScreenState extends State<SleepAnalysisScreen> {
  final List<SleepHistoryEntry> _sleepData = [];
  bool _isLoading = false;
  SleepHistoryEntry? _selectedSession;

  @override
  void initState() {
    super.initState();
    _loadSleepData();
    _setupSleepDataListener();
  }

  void _setupSleepDataListener() {
    widget.chileafService.sleepHistoryStream.listen((sleepEntries) {
      if (mounted) {
        setState(() {
          _sleepData.clear();
          _sleepData.addAll(sleepEntries);
          
          // Ordina per data più recente
          _sleepData.sort((a, b) => b.timestamp.compareTo(a.timestamp));
          
          // Seleziona automaticamente la sessione più recente
          if (_sleepData.isNotEmpty && _selectedSession == null) {
            _selectedSession = _sleepData.first;
          }
        });
      }
    });
  }

  Future<void> _loadSleepData() async {
    setState(() => _isLoading = true);
    
    try {
      // Richiedi dati del sonno dal dispositivo
      await widget.chileafService.requestOptimizedSleepHistory();
      
      // Aggiungi un delay per permettere la ricezione dei dati
      await Future.delayed(const Duration(seconds: 3));
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore nel caricamento dati sonno: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
    
    if (mounted) {
      setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Analisi del Sonno'),
        backgroundColor: Colors.indigo,
        foregroundColor: Colors.white,
        actions: [
          if (_selectedSession != null)
            IconButton(
              icon: const Icon(Icons.bug_report),
              onPressed: _showDetailedLog,
              tooltip: 'Mostra log dettagliato (formato Android)',
            ),
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _loadSleepData,
            tooltip: 'Aggiorna dati',
          ),
        ],
      ),
      body: Column(
        children: [
          _buildSleepSessionSelector(),
          if (_selectedSession != null) ...[
            _buildSleepSummaryCard(),
            Expanded(child: _buildSleepChart()),
          ] else
            const Expanded(
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(Icons.bedtime, size: 64, color: Colors.grey),
                    SizedBox(height: 16),
                    Text(
                      'Nessun dato del sonno disponibile',
                      style: TextStyle(fontSize: 18, color: Colors.grey),
                    ),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }

  Widget _buildSleepSessionSelector() {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.indigo.shade50,
        borderRadius: const BorderRadius.vertical(bottom: Radius.circular(16)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Seleziona Sessione di Sonno',
            style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          if (_isLoading)
            const Center(child: CircularProgressIndicator())
          else if (_sleepData.isEmpty)
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.orange.shade100,
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.orange.shade300),
              ),
              child: const Row(
                children: [
                  Icon(Icons.info, color: Colors.orange),
                  SizedBox(width: 8),
                  Expanded(
                    child: Text('Premi "Aggiorna dati" per scaricare i dati del sonno dal dispositivo'),
                  ),
                ],
              ),
            )
          else
            DropdownButtonFormField<SleepHistoryEntry>(
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
            ),
        ],
      ),
    );
  }

  Widget _buildSleepSummaryCard() {
    if (_selectedSession == null) return const SizedBox.shrink();
    
    final phases = _selectedSession!.calculateSleepPhases();
    final lightDuration = Duration(minutes: phases.lightSleep);
    final deepDuration = Duration(minutes: phases.deepSleep);
    final awakeDuration = Duration(minutes: phases.awake);
    final totalDuration = Duration(minutes: phases.totalMinutes);

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
              const Icon(Icons.bedtime, color: Colors.indigo, size: 24),
              const SizedBox(width: 8),
              Expanded(
                child: Text(
                  'Riepilogo Sonno - ${DateFormat('dd/MM/yyyy').format(_selectedSession!.timestamp)}',
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ),
            ],
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
                  'Efficienza',
                  '${phases.sleepEfficiency.toStringAsFixed(1)}%',
                  Colors.green,
                  Icons.trending_up,
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
          const SizedBox(height: 12),
          _buildSummaryItem(
            'Tempo Sveglio',
            '${awakeDuration.inHours}h ${awakeDuration.inMinutes % 60}m',
            Colors.orange,
            Icons.visibility,
          ),
        ],
      ),
    );
  }

  Widget _buildSummaryItem(String label, String value, Color color, IconData icon) {
    return Container(
      padding: const EdgeInsets.all(12),
      margin: const EdgeInsets.symmetric(horizontal: 4),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Column(
        children: [
          Icon(icon, color: color, size: 20),
          const SizedBox(height: 4),
          Text(
            label,
            style: TextStyle(
              fontSize: 12,
              color: Colors.grey.shade600,
              fontWeight: FontWeight.w500,
            ),
          ),
          const SizedBox(height: 2),
          Text(
            value,
            style: TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSleepChart() {
    if (_selectedSession == null) return const SizedBox.shrink();
    
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
                'Fasi del Sonno nel Tempo',
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
                      interval: (_selectedSession!.actions.length / 6).ceil().toDouble(),
                      getTitlesWidget: (value, meta) {
                        // Ogni indice = 5 minuti (300 secondi)
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
                maxX: _selectedSession!.actions.length.toDouble() - 1,
                minY: -0.5,
                maxY: 2.5,
                lineBarsData: [
                  LineChartBarData(
                    spots: _generateSleepPhaseSpots(),
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
                        // Ogni indice = 5 minuti
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

  List<FlSpot> _generateSleepPhaseSpots() {
    if (_selectedSession == null) return [];
    
    final actions = _selectedSession!.actions;
    final spots = <FlSpot>[];
    
    int zeroIndex = 0;
    List<int> pendingZeroIndices = []; // Tiene traccia degli indici degli zeri accumulati
    
    for (int i = 0; i < actions.length; i++) {
      final action = actions[i];
      
      if (action > 20) {
        // Wide awake - prima processa gli zeri accumulati
        if (zeroIndex >= 3) {
          // Gli zeri accumulati erano deep sleep
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 0)); // Deep sleep
          }
        } else if (zeroIndex > 0) {
          // Gli zeri accumulati erano light sleep
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 1)); // Light sleep
          }
        }
        zeroIndex = 0;
        pendingZeroIndices.clear();
        // Segna questo punto come sveglio
        spots.add(FlSpot(i.toDouble(), 2)); // Awake
        
      } else if (action <= 20 && action > 0) {
        // Light sleep - prima processa gli zeri accumulati
        if (zeroIndex >= 3) {
          // Gli zeri accumulati erano deep sleep
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 0)); // Deep sleep
          }
        } else if (zeroIndex > 0) {
          // Gli zeri accumulati erano light sleep
          for (int idx in pendingZeroIndices) {
            spots.add(FlSpot(idx.toDouble(), 1)); // Light sleep
          }
        }
        zeroIndex = 0;
        pendingZeroIndices.clear();
        // Segna questo punto come light sleep
        spots.add(FlSpot(i.toDouble(), 1)); // Light sleep
        
      } else {
        // action == 0: accumula
        zeroIndex++;
        pendingZeroIndices.add(i);
      }
    }
    
    // Processa gli eventuali zeri finali
    if (zeroIndex >= 3) {
      for (int idx in pendingZeroIndices) {
        spots.add(FlSpot(idx.toDouble(), 0)); // Deep sleep
      }
    } else if (zeroIndex > 0) {
      for (int idx in pendingZeroIndices) {
        spots.add(FlSpot(idx.toDouble(), 1)); // Light sleep
      }
    }
    
    // Ordina i punti per indice X (dovrebbero essere già ordinati, ma per sicurezza)
    spots.sort((a, b) => a.x.compareTo(b.x));
    
    return spots;
  }

  String _getSleepPhaseText(double value) {
    switch (value.round()) {
      case 0:
        return 'Sonno Profondo';
      case 1:
        return 'Sonno Leggero';
      case 2:
        return 'Sveglio';
      default:
        return 'Sconosciuto';
    }
  }

  Widget _buildSleepPhaseLegend() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        _buildLegendItem('Sonno Profondo', Colors.indigo, Icons.bedtime),
        _buildLegendItem('Sonno Leggero', Colors.cyan, Icons.wb_sunny_outlined),
        _buildLegendItem('Sveglio', Colors.orange, Icons.visibility),
      ],
    );
  }

  Widget _buildLegendItem(String label, Color color, IconData icon) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Icon(icon, color: color, size: 16),
        const SizedBox(width: 4),
        Text(
          label,
          style: TextStyle(
            fontSize: 12,
            color: color,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  /// Mostra un dialog con il log dettagliato formato Android per confronto
  void _showDetailedLog() {
    if (_selectedSession == null) return;
    
    final buffer = StringBuffer();
    final actions = _selectedSession!.actions;
    final baseTimestamp = _selectedSession!.timestamp;
    
    int zeroIndex = 0;
    List<DateTime> pendingZeroTimes = [];
    
    for (int i = 0; i < actions.length; i++) {
      int action = actions[i];
      DateTime utc = baseTimestamp.add(Duration(minutes: i * 5));
      String utcStr = utc.toLocal().toString().substring(0, 19);
      
      if (action > 20) {
        // Processa zeri accumulati
        if (zeroIndex >= 3) {
          for (var time in pendingZeroTimes) {
            buffer.writeln('utc:${time.toLocal().toString().substring(0, 19)}');
            buffer.writeln('action Index: deep Sleep');
          }
        } else if (zeroIndex > 0) {
          for (var time in pendingZeroTimes) {
            buffer.writeln('utc:${time.toLocal().toString().substring(0, 19)}');
            buffer.writeln('action Index: light sleep');
          }
        }
        zeroIndex = 0;
        pendingZeroTimes.clear();
        buffer.writeln('utc:$utcStr');
        buffer.writeln('action Index: not Sleep');
        
      } else if (action <= 20 && action > 0) {
        // Processa zeri accumulati
        if (zeroIndex >= 3) {
          for (var time in pendingZeroTimes) {
            buffer.writeln('utc:${time.toLocal().toString().substring(0, 19)}');
            buffer.writeln('action Index: deep Sleep');
          }
        } else if (zeroIndex > 0) {
          for (var time in pendingZeroTimes) {
            buffer.writeln('utc:${time.toLocal().toString().substring(0, 19)}');
            buffer.writeln('action Index: light sleep');
          }
        }
        zeroIndex = 0;
        pendingZeroTimes.clear();
        buffer.writeln('utc:$utcStr');
        buffer.writeln('action Index: light sleep');
        
      } else {
        // Accumula
        zeroIndex++;
        pendingZeroTimes.add(utc);
      }
    }
    
    // Processa zeri finali
    if (zeroIndex >= 3) {
      for (var time in pendingZeroTimes) {
        buffer.writeln('utc:${time.toLocal().toString().substring(0, 19)}');
        buffer.writeln('action Index: deep Sleep');
      }
    } else if (zeroIndex > 0) {
      for (var time in pendingZeroTimes) {
        buffer.writeln('utc:${time.toLocal().toString().substring(0, 19)}');
        buffer.writeln('action Index: light sleep');
      }
    }
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Row(
          children: [
            Icon(Icons.android, color: Colors.green),
            SizedBox(width: 8),
            Text('Log Formato Android'),
          ],
        ),
        content: SizedBox(
          width: double.maxFinite,
          child: SingleChildScrollView(
            child: SelectableText(
              buffer.toString(),
              style: const TextStyle(fontFamily: 'monospace', fontSize: 12),
            ),
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Chiudi'),
          ),
        ],
      ),
    );
  }
}