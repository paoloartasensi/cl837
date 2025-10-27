import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';

/// Schermata per la configurazione del Sensore 3D (Accelerometro)
/// Replica la funzionalità dell'app di debug cinese
class Sensor3DSettingsScreen extends StatefulWidget {
  final ChileafExtendedService service;

  const Sensor3DSettingsScreen({
    super.key,
    required this.service,
  });

  @override
  State<Sensor3DSettingsScreen> createState() => _Sensor3DSettingsScreenState();
}

class _Sensor3DSettingsScreenState extends State<Sensor3DSettingsScreen> {
  bool _isEnabled = false;
  int _currentFrequency = 2; // Default 100Hz
  String _frequencyLabel = '100Hz';
  bool _isLoading = false;

  // Mappa delle frequenze (come nell'app cinese)
  static const Map<int, String> _frequencyMap = {
    0: '25Hz',
    1: '50Hz',
    2: '100Hz',
    3: '200Hz',
    4: '400Hz',
  };

  // Livello di consumo batteria per frequenza
  static const Map<int, String> _batteryImpact = {
    0: '🟢 Minimo',
    1: '🟢 Basso',
    2: '🟡 Medio',
    3: '🟠 Alto',
    4: '🔴 MASSIMO',
  };

  @override
  void initState() {
    super.initState();
    _setupListeners();
    _refreshStatus();
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
          _frequencyLabel = _frequencyMap[frequencyData.value] ?? 'Unknown';
        });
      }
    });
  }

  Future<void> _refreshStatus() async {
    setState(() => _isLoading = true);
    try {
      await widget.service.get3DStatus();
      await Future.delayed(const Duration(milliseconds: 200));
      await widget.service.get3DFrequency();
    } catch (e) {
      _showError('Errore lettura stato: $e');
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  Future<void> _toggleSensor(bool enable) async {
    setState(() => _isLoading = true);
    try {
      await widget.service.set3DEnabled(enable);
      await Future.delayed(const Duration(milliseconds: 200));
      await widget.service.get3DStatus();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(enable ? '✅ Sensore 3D Abilitato' : '⏹️ Sensore 3D Disabilitato'),
            backgroundColor: enable ? Colors.green : Colors.orange,
            duration: const Duration(seconds: 1),
          ),
        );
      }
    } catch (e) {
      _showError('Errore toggle sensore: $e');
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  Future<void> _setFrequency(int frequency) async {
    // Conferma per frequenze alte
    if (frequency >= 3) {
      final confirmed = await _showWarningDialog(frequency);
      if (!confirmed) return;
    }

    setState(() => _isLoading = true);
    try {
      await widget.service.set3DFrequency(frequency);
      await Future.delayed(const Duration(milliseconds: 200));
      await widget.service.get3DFrequency();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('✅ Frequenza impostata: ${_frequencyMap[frequency]}'),
            backgroundColor: Colors.green,
            duration: const Duration(seconds: 1),
          ),
        );
      }
    } catch (e) {
      _showError('Errore impostazione frequenza: $e');
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  Future<bool> _showWarningDialog(int frequency) async {
    return await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            const Icon(Icons.warning_amber_rounded, color: Colors.orange, size: 32),
            const SizedBox(width: 12),
            Text('Attenzione: ${_frequencyMap[frequency]}'),
          ],
        ),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Stai per impostare una frequenza ${frequency == 4 ? 'MOLTO ALTA' : 'alta'}.',
              style: const TextStyle(fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            const Text('⚠️ RISCHI:', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            _buildWarningItem('🔋 Consumo batteria ${_batteryImpact[frequency]}'),
            _buildWarningItem('📡 Molti pacchetti BLE/secondo'),
            _buildWarningItem('⚡ Possibile lag dell\'app'),
            if (frequency == 4) ...[
              const SizedBox(height: 8),
              const Text(
                '🔴 A 400Hz la batteria si scarica MOLTO velocemente!',
                style: TextStyle(color: Colors.red, fontWeight: FontWeight.bold),
              ),
            ],
            const SizedBox(height: 16),
            const Text('Vuoi continuare?'),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Annulla'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(
              backgroundColor: frequency == 4 ? Colors.red : Colors.orange,
            ),
            child: const Text('Continua'),
          ),
        ],
      ),
    ) ?? false;
  }

  Widget _buildWarningItem(String text) {
    return Padding(
      padding: const EdgeInsets.only(left: 8, bottom: 4),
      child: Row(
        children: [
          const Icon(Icons.circle, size: 6),
          const SizedBox(width: 8),
          Expanded(child: Text(text)),
        ],
      ),
    );
  }

  void _showError(String message) {
    if (!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: Colors.red,
        duration: const Duration(seconds: 3),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sensore 3D (Accelerometro)'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _isLoading ? null : _refreshStatus,
            tooltip: 'Aggiorna stato',
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  _buildStatusCard(),
                  const SizedBox(height: 16),
                  _buildFrequencyCard(),
                  const SizedBox(height: 16),
                  _buildInfoCard(),
                ],
              ),
            ),
    );
  }

  Widget _buildStatusCard() {
    return Card(
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Stato Sensore',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      _isEnabled ? '✅ ABILITATO' : '⏹️ DISABILITATO',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                        color: _isEnabled ? Colors.green : Colors.grey,
                      ),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Frequenza: $_frequencyLabel',
                      style: const TextStyle(color: Colors.grey),
                    ),
                  ],
                ),
                Switch(
                  value: _isEnabled,
                  onChanged: _isLoading ? null : _toggleSensor,
                  activeColor: Colors.green,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildFrequencyCard() {
    return Card(
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Impostazione Frequenza',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text(
              'Frequenza corrente: $_frequencyLabel',
              style: const TextStyle(color: Colors.grey),
            ),
            const SizedBox(height: 16),
            // Slider per frequenza
            Slider(
              value: _currentFrequency.toDouble(),
              min: 0,
              max: 4,
              divisions: 4,
              label: _frequencyMap[_currentFrequency],
              onChanged: _isLoading ? null : (value) {
                setState(() => _currentFrequency = value.toInt());
              },
              onChangeEnd: (value) => _setFrequency(value.toInt()),
            ),
            const SizedBox(height: 8),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text('25Hz', style: TextStyle(fontSize: 12, color: Colors.grey[600])),
                Text('400Hz', style: TextStyle(fontSize: 12, color: Colors.grey[600])),
              ],
            ),
            const SizedBox(height: 16),
            // Pulsanti come nell'app cinese
            const Text(
              'Selezione Rapida:',
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 12),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                for (var entry in _frequencyMap.entries)
                  _buildFrequencyButton(entry.key, entry.value),
              ],
            ),
            const SizedBox(height: 16),
            // Indicatore consumo batteria
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: _getBatteryColor(_currentFrequency).withOpacity(0.1),
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: _getBatteryColor(_currentFrequency)),
              ),
              child: Row(
                children: [
                  Icon(Icons.battery_alert, color: _getBatteryColor(_currentFrequency)),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      'Consumo batteria: ${_batteryImpact[_currentFrequency]}',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: _getBatteryColor(_currentFrequency),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildFrequencyButton(int frequency, String label) {
    final isSelected = _currentFrequency == frequency;
    final color = _getBatteryColor(frequency);

    return ElevatedButton(
      onPressed: _isLoading ? null : () => _setFrequency(frequency),
      style: ElevatedButton.styleFrom(
        backgroundColor: isSelected ? color : Colors.grey[200],
        foregroundColor: isSelected ? Colors.white : Colors.black87,
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            label,
            style: TextStyle(
              fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
            ),
          ),
          if (isSelected)
            const Icon(Icons.check_circle, size: 16),
        ],
      ),
    );
  }

  Widget _buildInfoCard() {
    return Card(
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              children: [
                Icon(Icons.info_outline, color: Colors.blue),
                SizedBox(width: 8),
                Text(
                  'Informazioni',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            const SizedBox(height: 12),
            _buildInfoItem(
              'Cosa fa il sensore 3D?',
              'Misura l\'accelerazione su 3 assi (X, Y, Z) per rilevare movimenti e attività fisica.',
            ),
            const Divider(height: 24),
            _buildInfoItem(
              'Quando usare frequenze alte?',
              '• Analisi dettagliate del movimento\n'
              '• Sport ad alta intensità\n'
              '• Rilevamento cadute\n'
              '• Analisi biomeccaniche',
            ),
            const Divider(height: 24),
            _buildInfoItem(
              'Quando usare frequenze basse?',
              '• Monitoraggio continuo 24/7\n'
              '• Risparmiare batteria\n'
              '• Conteggio passi standard\n'
              '• Uso quotidiano',
            ),
            const Divider(height: 24),
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.orange[50],
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.orange),
              ),
              child: const Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Icon(Icons.tips_and_updates, color: Colors.orange),
                  SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      'Consiglio: Usa 100Hz per un buon equilibrio tra precisione e durata batteria.',
                      style: TextStyle(color: Colors.orange),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoItem(String title, String description) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 14),
        ),
        const SizedBox(height: 4),
        Text(
          description,
          style: TextStyle(color: Colors.grey[700], fontSize: 13),
        ),
      ],
    );
  }

  Color _getBatteryColor(int frequency) {
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
}
