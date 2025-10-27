import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';

/// Esempio di come integrare il controllo della frequenza 3D nella tua app
/// 
/// USO RAPIDO:
/// ```dart
/// // Nel tuo widget principale
/// FloatingActionButton(
///   onPressed: () {
///     Navigator.push(
///       context,
///       MaterialPageRoute(
///         builder: (context) => AdvancedSettingsScreen(service: _service),
///       ),
///     );
///   },
///   child: Icon(Icons.settings),
/// )
/// ```
/// 
/// OPPURE per accesso diretto alla schermata 3D:
/// ```dart
/// import 'screens/sensor_3d_settings_screen.dart';
/// 
/// Navigator.push(
///   context,
///   MaterialPageRoute(
///     builder: (context) => Sensor3DSettingsScreen(service: _service),
///   ),
/// );
/// ```

class Sensor3DQuickActionsWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const Sensor3DQuickActionsWidget({
    super.key,
    required this.service,
  });

  @override
  State<Sensor3DQuickActionsWidget> createState() => _Sensor3DQuickActionsWidgetState();
}

class _Sensor3DQuickActionsWidgetState extends State<Sensor3DQuickActionsWidget> {
  bool? _isEnabled;
  int? _currentFrequency;
  String? _frequencyLabel;
  bool _isLoading = false;

  static const Map<int, String> _frequencyMap = {
    0: '25Hz',
    1: '50Hz',
    2: '100Hz',
    3: '200Hz',
    4: '400Hz',
  };

  @override
  void initState() {
    super.initState();
    _setupListeners();
  }

  void _setupListeners() {
    // Ascolta lo stato del sensore 3D
    widget.service.sensor3DStatusStream.listen((status) {
      if (mounted) {
        setState(() {
          _isEnabled = status.enabled;
        });
      }
    });

    // Ascolta la frequenza del sensore 3D
    widget.service.sensor3DFrequencyStream.listen((frequencyData) {
      if (mounted) {
        setState(() {
          _currentFrequency = frequencyData.value;
          _frequencyLabel = _frequencyMap[frequencyData.value];
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(16),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Row(
                  children: [
                    Icon(Icons.threed_rotation, color: Colors.blue),
                    SizedBox(width: 8),
                    Text(
                      'Sensore 3D',
                      style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
                // Pulsante Refresh
                IconButton(
                  icon: _isLoading 
                    ? const SizedBox(
                        width: 20,
                        height: 20,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.refresh),
                  onPressed: _isLoading ? null : _refreshStatus,
                  tooltip: 'Aggiorna stato dal bracciale',
                ),
              ],
            ),
            const SizedBox(height: 12),
            
            // Stato corrente del bracciale
            if (_isEnabled != null || _currentFrequency != null)
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.blue.withOpacity(0.1),
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.blue.withOpacity(0.3)),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      '📡 Stato Attuale Bracciale:',
                      style: TextStyle(fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                    if (_isEnabled != null)
                      Row(
                        children: [
                          Icon(
                            _isEnabled! ? Icons.check_circle : Icons.cancel,
                            color: _isEnabled! ? Colors.green : Colors.red,
                            size: 20,
                          ),
                          const SizedBox(width: 8),
                          Text(
                            _isEnabled! ? 'ABILITATO' : 'DISABILITATO',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: _isEnabled! ? Colors.green : Colors.red,
                            ),
                          ),
                        ],
                      ),
                    if (_currentFrequency != null) ...[
                      const SizedBox(height: 4),
                      Row(
                        children: [
                          const Icon(Icons.speed, color: Colors.blue, size: 20),
                          const SizedBox(width: 8),
                          Text(
                            'Frequenza: $_frequencyLabel',
                            style: const TextStyle(fontWeight: FontWeight.bold),
                          ),
                          const SizedBox(width: 8),
                          Container(
                            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                            decoration: BoxDecoration(
                              color: _getFrequencyColor(_currentFrequency!),
                              borderRadius: BorderRadius.circular(12),
                            ),
                            child: Text(
                              _getBatteryImpact(_currentFrequency!),
                              style: const TextStyle(
                                color: Colors.white,
                                fontSize: 11,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ],
                ),
              ),
            
            const SizedBox(height: 16),
            const Text(
              'Comandi:',
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            
            // Pulsanti GET
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                _buildQuickButton(
                  context,
                  label: 'GET Status',
                  icon: Icons.download,
                  color: Colors.blue,
                  onPressed: () => _getStatus(context),
                ),
                _buildQuickButton(
                  context,
                  label: 'GET Frequenza',
                  icon: Icons.download,
                  color: Colors.blue,
                  onPressed: () => _getFrequency(context),
                ),
              ],
            ),
            
            const SizedBox(height: 12),
            const Text(
              'Azioni Rapide:',
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                _buildQuickButton(
                  context,
                  label: 'OFF',
                  icon: Icons.power_settings_new,
                  color: Colors.red,
                  onPressed: () => _toggle3D(context, false),
                ),
                _buildQuickButton(
                  context,
                  label: 'ON',
                  icon: Icons.power_settings_new,
                  color: Colors.green,
                  onPressed: () => _toggle3D(context, true),
                ),
                const SizedBox(width: 8),
                _buildQuickButton(
                  context,
                  label: '25Hz',
                  icon: Icons.speed,
                  color: Colors.green,
                  onPressed: () => _setFreq(context, 0, '25Hz'),
                ),
                _buildQuickButton(
                  context,
                  label: '100Hz',
                  icon: Icons.speed,
                  color: Colors.orange,
                  onPressed: () => _setFreq(context, 2, '100Hz'),
                ),
                _buildQuickButton(
                  context,
                  label: '400Hz',
                  icon: Icons.speed,
                  color: Colors.red,
                  onPressed: () => _setFreq(context, 4, '400Hz', showWarning: true),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _refreshStatus() async {
    setState(() => _isLoading = true);
    try {
      await widget.service.get3DStatus();
      await Future.delayed(const Duration(milliseconds: 200));
      await widget.service.get3DFrequency();
    } catch (e) {
      debugPrint('❌ Errore refresh: $e');
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  Future<void> _getStatus(BuildContext context) async {
    try {
      await widget.service.get3DStatus();
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('📡 Richiesta stato inviata...'),
            backgroundColor: Colors.blue,
            duration: Duration(seconds: 1),
          ),
        );
      }
    } catch (e) {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  Future<void> _getFrequency(BuildContext context) async {
    try {
      await widget.service.get3DFrequency();
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('📡 Richiesta frequenza inviata...'),
            backgroundColor: Colors.blue,
            duration: Duration(seconds: 1),
          ),
        );
      }
    } catch (e) {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  Color _getFrequencyColor(int frequency) {
    switch (frequency) {
      case 0:
      case 1:
        return Colors.green;
      case 2:
        return Colors.orange;
      case 3:
        return Colors.deepOrange;
      case 4:
        return Colors.red;
      default:
        return Colors.grey;
    }
  }

  String _getBatteryImpact(int frequency) {
    switch (frequency) {
      case 0: return 'Minimo';
      case 1: return 'Basso';
      case 2: return 'Medio';
      case 3: return 'Alto';
      case 4: return 'MASSIMO';
      default: return 'Sconosciuto';
    }
  }

  Widget _buildQuickButton(
    BuildContext context, {
    required String label,
    required IconData icon,
    required Color color,
    required VoidCallback onPressed,
  }) {
    return ElevatedButton.icon(
      onPressed: onPressed,
      icon: Icon(icon, size: 16),
      label: Text(label),
      style: ElevatedButton.styleFrom(
        backgroundColor: color,
        foregroundColor: Colors.white,
      ),
    );
  }

  Future<void> _toggle3D(BuildContext context, bool enable) async {
    try {
      await widget.service.set3DEnabled(enable);
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(enable ? '✅ Sensore 3D Abilitato' : '⏹️ Sensore 3D Disabilitato'),
            backgroundColor: enable ? Colors.green : Colors.orange,
            duration: const Duration(seconds: 1),
          ),
        );
      }
    } catch (e) {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  Future<void> _setFreq(
    BuildContext context,
    int frequency,
    String label, {
    bool showWarning = false,
  }) async {
    if (showWarning) {
      final confirmed = await showDialog<bool>(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('⚠️ Attenzione'),
          content: const Text(
            '400Hz consuma MOLTA batteria!\n\n'
            'Usare solo per test brevi.',
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context, false),
              child: const Text('Annulla'),
            ),
            ElevatedButton(
              onPressed: () => Navigator.pop(context, true),
              style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              child: const Text('Continua'),
            ),
          ],
        ),
      );
      if (confirmed != true) return;
    }

    try {
      await widget.service.set3DFrequency(frequency);
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('✅ Frequenza: $label'),
            backgroundColor: Colors.green,
            duration: const Duration(seconds: 1),
          ),
        );
      }
    } catch (e) {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }
}
