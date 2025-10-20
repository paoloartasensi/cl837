import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../widgets/hr_control_widget.dart';

/// Schermata per accedere ai test di feedback del device CL837
/// Include spiegazione su vibrazione non supportata e alternative disponibili
class FeedbackTestScreen extends StatelessWidget {
  final ChileafExtendedService service;

  const FeedbackTestScreen({
    super.key,
    required this.service,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Feedback Tests'),
        backgroundColor: Colors.deepPurple,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // HEADER INFO
            Card(
              color: Colors.blue[50],
              child: const Padding(
                padding: EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    Icon(Icons.info, color: Colors.blue, size: 32),
                    SizedBox(height: 8),
                    Text(
                      'Test Funzionalità Feedback CL837',
                      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
                    ),
                    SizedBox(height: 8),
                    Text(
                      'Questo tool testa tutte le funzionalità di feedback disponibili '
                      'sul dispositivo CL837, inclusi LED control e Heart Rate Alarms.',
                      textAlign: TextAlign.center,
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 24),
            
            // FUNZIONALITÀ DISPONIBILI
            const Text(
              '✅ Funzionalità Supportate:',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.green),
            ),
            const SizedBox(height: 12),
            
            _buildFeatureItem(
              Icons.lightbulb,
              'LED Control',
              'LED rosso durante misurazione SpO2 (comando 0x37)',
              Colors.red,
            ),
            
            _buildFeatureItem(
              Icons.favorite,
              'Heart Rate Alarms',
              'Allarmi configurabili per frequenza cardiaca (0x57/0x5B)',
              Colors.pink,
            ),
            
            _buildFeatureItem(
              Icons.bluetooth,
              'BLE Feedback',
              'Gestione stato Bluetooth e notifiche (0x3F)',
              Colors.blue,
            ),
            
            const SizedBox(height: 24),
            
            // LIMITAZIONI
            const Text(
              '❌ Non Supportate:',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.red),
            ),
            const SizedBox(height: 12),
            
            _buildFeatureItem(
              Icons.vibration,
              'Vibrazione',
              'CL837 NON ha motore di vibrazione (confermato da analisi REVERSE)',
              Colors.grey,
            ),
            
            const SizedBox(height: 32),
            
            // BOTTONE ACCESSO TEST
            Center(
              child: ElevatedButton.icon(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => HRControlWidget(service: service),
                    ),
                  );
                },
                icon: const Icon(Icons.favorite),
                label: const Text('Apri HR Control'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepPurple,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 16),
                  textStyle: const TextStyle(fontSize: 16),
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // DISCLAIMER SICUREZZA
            Card(
              color: Colors.orange[50],
              child: const Padding(
                padding: EdgeInsets.all(12.0),
                child: Row(
                  children: [
                    Icon(Icons.warning, color: Colors.orange),
                    SizedBox(width: 12),
                    Expanded(
                      child: Text(
                        '⚠️ Alcuni test potrebbero disconnettere il device. '
                        'Assicurati di essere pronti a riconnettere.',
                        style: TextStyle(fontSize: 12),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
  
  Widget _buildFeatureItem(IconData icon, String title, String description, Color color) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Row(
        children: [
          Icon(icon, color: color, size: 20),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  title,
                  style: const TextStyle(fontWeight: FontWeight.w500),
                ),
                Text(
                  description,
                  style: TextStyle(fontSize: 12, color: Colors.grey[600]),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
