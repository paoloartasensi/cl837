import 'package:flutter/material.dart';
import '../models/spo2_data.dart';
import '../services/spo2_service.dart';

/// Widget per visualizzare le letture SpO2 con indicatori di qualità
class SpO2DisplayWidget extends StatefulWidget {
  final SpO2Service spO2Service;

  const SpO2DisplayWidget({
    Key? key,
    required this.spO2Service,
  }) : super(key: key);

  @override
  State<SpO2DisplayWidget> createState() => _SpO2DisplayWidgetState();
}

class _SpO2DisplayWidgetState extends State<SpO2DisplayWidget> {
  SpO2Data? _currentReading;
  SpO2Statistics? _statistics;
  String? _lastAlert;

  @override
  void initState() {
    super.initState();
    _setupStreams();
  }

  void _setupStreams() {
    // Ascolta le letture SpO2
    widget.spO2Service.spO2Stream.listen((spO2Data) {
      setState(() {
        _currentReading = spO2Data;
        _statistics = widget.spO2Service.getRecentStatistics();
      });
    });

    // Ascolta gli allarmi
    widget.spO2Service.alertStream.listen((alert) {
      setState(() {
        _lastAlert = alert;
      });
      _showAlertDialog(alert);
    });
  }

  void _showAlertDialog(String alert) {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => AlertDialog(
        title: Icon(
          alert.contains('CRITICO') ? Icons.error : Icons.warning,
          color: alert.contains('CRITICO') ? Colors.red : Colors.orange,
          size: 48,
        ),
        content: Text(
          alert,
          style: const TextStyle(fontSize: 16),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('OK'),
          ),
        ],
      ),
    );
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
            _buildHeader(),
            const SizedBox(height: 16),
            _buildMainDisplay(),
            const SizedBox(height: 16),
            _buildQualityIndicators(),
            if (_currentReading != null && !_currentReading!.isReliable) ...[
              const SizedBox(height: 16),
              _buildImprovementTips(),
            ],
            if (_statistics != null) ...[
              const SizedBox(height: 16),
              _buildStatistics(),
            ],
            if (_lastAlert != null) ...[
              const SizedBox(height: 16),
              _buildLastAlert(),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildHeader() {
    return Row(
      children: [
        const Icon(Icons.favorite, color: Colors.red, size: 24),
        const SizedBox(width: 8),
        const Text(
          'Saturazione Ossigeno (SpO2)',
          style: TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
        const Spacer(),
        IconButton(
          icon: const Icon(Icons.info_outline),
          onPressed: _showInfoDialog,
        ),
      ],
    );
  }

  Widget _buildMainDisplay() {
    if (_currentReading == null) {
      return const Center(
        child: Column(
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 16),
            Text('In attesa dei dati SpO2...'),
          ],
        ),
      );
    }

    return Row(
      children: [
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  Text(
                    '${_currentReading!.value}%',
                    style: TextStyle(
                      fontSize: 48,
                      fontWeight: FontWeight.bold,
                      color: _getValueColor(),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Icon(
                    _currentReading!.isReliable ? Icons.check_circle : Icons.warning,
                    color: _currentReading!.isReliable ? Colors.green : Colors.orange,
                    size: 24,
                  ),
                ],
              ),
              Text(
                _getValueDescription(),
                style: TextStyle(
                  fontSize: 14,
                  color: _getValueColor(),
                  fontWeight: FontWeight.w500,
                ),
              ),
            ],
          ),
        ),
        _buildQualityMeter(),
      ],
    );
  }

  Widget _buildQualityMeter() {
    double quality = _currentReading!.piValue / 30.0; // Normalizza 0-30 -> 0-1
    quality = quality.clamp(0.0, 1.0);

    return SizedBox(
      width: 60,
      height: 60,
      child: Stack(
        children: [
          CircularProgressIndicator(
            value: quality,
            strokeWidth: 6,
            backgroundColor: Colors.grey[300],
            valueColor: AlwaysStoppedAnimation<Color>(
              quality < 0.3 ? Colors.red : quality < 0.6 ? Colors.orange : Colors.green,
            ),
          ),
          Center(
            child: Text(
              'PI\n${_currentReading!.piValue}',
              textAlign: TextAlign.center,
              style: const TextStyle(fontSize: 10, fontWeight: FontWeight.bold),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildQualityIndicators() {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.grey[100],
        borderRadius: BorderRadius.circular(8),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Indicatori Qualità',
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          _buildQualityRow(
            'Postura Polso',
            _currentReading?.gesture == 1 ? 'Corretta' : 'Errata',
            _currentReading?.gesture == 1 ? Colors.green : Colors.red,
            _currentReading?.gesture == 1 ? Icons.check : Icons.close,
          ),
          _buildQualityRow(
            'Contatto Dispositivo',
            _currentReading?.onWrist == 1 ? 'Indossato' : 'Non Indossato',
            _currentReading?.onWrist == 1 ? Colors.green : Colors.red,
            _currentReading?.onWrist == 1 ? Icons.check : Icons.close,
          ),
        ],
      ),
    );
  }

  Widget _buildQualityRow(String label, String value, Color color, IconData icon) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        children: [
          Icon(icon, color: color, size: 16),
          const SizedBox(width: 8),
          Text('$label: ', style: const TextStyle(fontSize: 12)),
          Text(
            value,
            style: TextStyle(
              fontSize: 12,
              color: color,
              fontWeight: FontWeight.w500,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildImprovementTips() {
    List<String> tips = widget.spO2Service.getQualityImprovementTips(_currentReading);
    
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.orange[50],
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.orange[200]!),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              const Icon(Icons.lightbulb, color: Colors.orange, size: 16),
              const SizedBox(width: 8),
              Text(
                'Per migliorare la misurazione:',
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: Colors.orange[800],
                ),
              ),
            ],
          ),
          const SizedBox(height: 8),
          ...tips.map((tip) => Padding(
                padding: const EdgeInsets.symmetric(vertical: 2),
                child: Text(
                  tip,
                  style: TextStyle(fontSize: 12, color: Colors.orange[800]),
                ),
              )),
        ],
      ),
    );
  }

  Widget _buildStatistics() {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.blue[50],
        borderRadius: BorderRadius.circular(8),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Statistiche Letture Affidabili',
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              _buildStatItem('Media', '${_statistics!.average.toStringAsFixed(1)}%'),
              _buildStatItem('Min', '${_statistics!.minimum}%'),
              _buildStatItem('Max', '${_statistics!.maximum}%'),
              _buildStatItem('Letture', '${_statistics!.count}'),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildStatItem(String label, String value) {
    return Column(
      children: [
        Text(
          value,
          style: TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
            color: Colors.blue[800],
          ),
        ),
        Text(
          label,
          style: TextStyle(fontSize: 10, color: Colors.blue[600]),
        ),
      ],
    );
  }

  Widget _buildLastAlert() {
    bool isCritical = _lastAlert!.contains('CRITICO');
    
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: isCritical ? Colors.red[50] : Colors.orange[50],
        borderRadius: BorderRadius.circular(8),
        border: Border.all(
          color: isCritical ? Colors.red[200]! : Colors.orange[200]!,
        ),
      ),
      child: Row(
        children: [
          Icon(
            isCritical ? Icons.error : Icons.warning,
            color: isCritical ? Colors.red : Colors.orange,
          ),
          const SizedBox(width: 8),
          Expanded(
            child: Text(
              _lastAlert!,
              style: TextStyle(
                color: isCritical ? Colors.red[800] : Colors.orange[800],
                fontSize: 12,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Color _getValueColor() {
    if (_currentReading == null || !_currentReading!.isReliable) return Colors.grey;
    if (_currentReading!.value < 90) return Colors.red;
    if (_currentReading!.value < 95) return Colors.orange;
    return Colors.green;
  }

  String _getValueDescription() {
    if (_currentReading == null) return '';
    if (!_currentReading!.isReliable) return 'Lettura non affidabile';
    if (_currentReading!.value < 90) return 'Critico - Consultare medico';
    if (_currentReading!.value < 95) return 'Basso - Monitorare';
    if (_currentReading!.value < 98) return 'Normale';
    return 'Ottimale';
  }


  void _showInfoDialog() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Informazioni SpO2'),
        content: const SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                'Valori Normali:',
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              Text('• 95-100%: Normale\n• 90-94%: Lievemente basso\n• <90%: Critico'),
              SizedBox(height: 16),
              Text(
                'Qualità del Segnale (PI):',
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              Text('• 0: Nessun segnale\n• 1-7: Debole (inaffidabile)\n• 8-14: Buono\n• 15+: Eccellente'),
              SizedBox(height: 16),
              Text(
                'Nota:',
                style: TextStyle(fontWeight: FontWeight.bold, color: Colors.orange),
              ),
              Text(
                'Solo le letture con qualità del segnale ≥8, postura corretta e dispositivo indossato sono considerate affidabili per l\'analisi della salute.',
                style: TextStyle(fontSize: 12),
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }
}
