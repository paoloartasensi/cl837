import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../services/sleep_onset_detector.dart';

/// Esempio completo di come rilevare l'inizio del sonno e altri eventi
/// Mostra tutti i metodi disponibili per capire quando inizia/finisce il sonno
class SleepOnsetDetectionExample extends StatefulWidget {
  final ChileafExtendedService chileafService;

  const SleepOnsetDetectionExample({
    super.key,
    required this.chileafService,
  });

  @override
  State<SleepOnsetDetectionExample> createState() => _SleepOnsetDetectionExampleState();
}

class _SleepOnsetDetectionExampleState extends State<SleepOnsetDetectionExample> {
  // Eventi rilevati
  final List<SleepOnsetEvent> _onsetEvents = [];
  final List<SleepWakeEvent> _wakeEvents = [];
  final List<SleepPhaseChange> _phaseChanges = [];
  
  bool _isMonitoring = false;

  @override
  void initState() {
    super.initState();
    _setupEventListeners();
  }

  /// Configura i listener per tutti gli eventi sleep
  void _setupEventListeners() {
    // 1. EVENTO: Inizio del sonno rilevato
    widget.chileafService.sleepOnsetStream.listen((onsetEvent) {
      if (!mounted) return;
      
      setState(() {
        _onsetEvents.add(onsetEvent);
      });
      
      // Mostra notifica quando inizia il sonno
      _showSleepNotification(
        '🌙 Sonno Iniziato!',
        'Rilevato alle ${_formatTime(onsetEvent.timestamp)}\n'
        'Fase iniziale: ${onsetEvent.initialPhase.displayName}\n'
        'Confidence: ${onsetEvent.confidence.toStringAsFixed(1)}%',
        Colors.blue,
      );
    });
    
    // 2. EVENTO: Risveglio rilevato
    widget.chileafService.sleepWakeStream.listen((wakeEvent) {
      if (!mounted) return;
      
      setState(() {
        _wakeEvents.add(wakeEvent);
      });
      
      // Mostra notifica quando si sveglia
      _showSleepNotification(
        '😴 Risveglio!',
        'Risveglio alle ${_formatTime(wakeEvent.timestamp)}\n'
        'Durata sonno: ${wakeEvent.duration.inHours}h ${wakeEvent.duration.inMinutes % 60}m',
        Colors.orange,
      );
    });
    
    // 3. EVENTO: Cambio di fase del sonno
    widget.chileafService.sleepPhaseChangeStream.listen((phaseChange) {
      if (!mounted) return;
      
      setState(() {
        _phaseChanges.add(phaseChange);
      });
      
      // Log cambio fase (meno invasivo)
      debugPrint('🔄 Cambio fase: ${phaseChange.fromPhase.displayName} → ${phaseChange.toPhase.displayName}');
    });
  }

  void _showSleepNotification(String title, String message, Color color) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              title,
              style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            ),
            const SizedBox(height: 4),
            Text(message),
          ],
        ),
        backgroundColor: color,
        duration: const Duration(seconds: 5),
        action: SnackBarAction(
          label: 'OK',
          textColor: Colors.white,
          onPressed: () {},
        ),
      ),
    );
  }

  String _formatTime(DateTime dt) {
    return '${dt.hour.toString().padLeft(2, '0')}:${dt.minute.toString().padLeft(2, '0')}';
  }

  Future<void> _requestSleepData() async {
    setState(() => _isMonitoring = true);
    
    try {
      await widget.chileafService.requestSleepData31();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('📊 Richiesta dati sleep inviata! Attendi i risultati...'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
      setState(() => _isMonitoring = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sleep Onset Detection'),
        backgroundColor: Colors.indigo,
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          // Header con spiegazione
          Container(
            padding: const EdgeInsets.all(16),
            color: Colors.indigo.shade50,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Rilevamento Automatico Eventi Sonno',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                const Text(
                  'Questo esempio mostra come rilevare automaticamente:',
                  style: TextStyle(fontSize: 14),
                ),
                const SizedBox(height: 4),
                _buildInfoRow(Icons.bedtime, 'Quando inizia il sonno'),
                _buildInfoRow(Icons.wb_sunny, 'Quando ti svegli'),
                _buildInfoRow(Icons.swap_horiz, 'Cambi di fase (deep ↔ light)'),
              ],
            ),
          ),
          
          // Pulsante richiesta dati
          Padding(
            padding: const EdgeInsets.all(16),
            child: ElevatedButton.icon(
              onPressed: _isMonitoring ? null : _requestSleepData,
              icon: Icon(_isMonitoring ? Icons.hourglass_empty : Icons.download),
              label: Text(_isMonitoring ? 'Monitoring...' : 'Richiedi Dati Sleep'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.indigo,
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
              ),
            ),
          ),
          
          // Tabs per i diversi eventi
          Expanded(
            child: DefaultTabController(
              length: 3,
              child: Column(
                children: [
                  const TabBar(
                    tabs: [
                      Tab(icon: Icon(Icons.bedtime), text: 'Onset'),
                      Tab(icon: Icon(Icons.wb_sunny), text: 'Wake'),
                      Tab(icon: Icon(Icons.swap_horiz), text: 'Phases'),
                    ],
                  ),
                  Expanded(
                    child: TabBarView(
                      children: [
                        _buildOnsetList(),
                        _buildWakeList(),
                        _buildPhaseChangeList(),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildInfoRow(IconData icon, String text) {
    return Padding(
      padding: const EdgeInsets.only(left: 8, top: 2),
      child: Row(
        children: [
          Icon(icon, size: 16, color: Colors.indigo),
          const SizedBox(width: 8),
          Text(text, style: const TextStyle(fontSize: 12)),
        ],
      ),
    );
  }

  Widget _buildOnsetList() {
    if (_onsetEvents.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.bedtime, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text(
              'Nessun evento di inizio sonno rilevato',
              style: TextStyle(color: Colors.grey),
            ),
            SizedBox(height: 8),
            Text(
              'Premi "Richiedi Dati Sleep" per iniziare',
              style: TextStyle(color: Colors.grey, fontSize: 12),
            ),
          ],
        ),
      );
    }
    
    return ListView.builder(
      itemCount: _onsetEvents.length,
      itemBuilder: (context, index) {
        final event = _onsetEvents[index];
        return Card(
          margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          child: ListTile(
            leading: CircleAvatar(
              backgroundColor: Colors.blue,
              child: Text(event.initialPhase.emoji),
            ),
            title: Text('Sonno iniziato: ${_formatTime(event.timestamp)}'),
            subtitle: Text(
              'Fase: ${event.initialPhase.displayName}\n'
              'Activity Index: ${event.activityIndex}\n'
              'Confidence: ${event.confidence.toStringAsFixed(1)}%',
            ),
            isThreeLine: true,
          ),
        );
      },
    );
  }

  Widget _buildWakeList() {
    if (_wakeEvents.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.wb_sunny, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text(
              'Nessun evento di risveglio rilevato',
              style: TextStyle(color: Colors.grey),
            ),
          ],
        ),
      );
    }
    
    return ListView.builder(
      itemCount: _wakeEvents.length,
      itemBuilder: (context, index) {
        final event = _wakeEvents[index];
        return Card(
          margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          child: ListTile(
            leading: const CircleAvatar(
              backgroundColor: Colors.orange,
              child: Icon(Icons.wb_sunny, color: Colors.white),
            ),
            title: Text('Risveglio: ${_formatTime(event.timestamp)}'),
            subtitle: Text(
              'Inizio sonno: ${_formatTime(event.sessionStartTime)}\n'
              'Durata: ${event.duration.inHours}h ${event.duration.inMinutes % 60}m\n'
              'Activity Index: ${event.activityIndex}',
            ),
            isThreeLine: true,
          ),
        );
      },
    );
  }

  Widget _buildPhaseChangeList() {
    if (_phaseChanges.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.swap_horiz, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text(
              'Nessun cambio di fase rilevato',
              style: TextStyle(color: Colors.grey),
            ),
          ],
        ),
      );
    }
    
    return ListView.builder(
      itemCount: _phaseChanges.length,
      itemBuilder: (context, index) {
        final event = _phaseChanges[index];
        return Card(
          margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          child: ListTile(
            leading: CircleAvatar(
              backgroundColor: Colors.purple,
              child: Text('${event.fromPhase.emoji}→${event.toPhase.emoji}'),
            ),
            title: Text('Cambio fase: ${_formatTime(event.timestamp)}'),
            subtitle: Text(
              '${event.fromPhase.displayName} → ${event.toPhase.displayName}\n'
              'Activity Index: ${event.activityIndex}',
            ),
            isThreeLine: true,
          ),
        );
      },
    );
  }
}
