import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../hrv_session_service.dart';

class ManualTestsWidget extends StatefulWidget {
  final ChileafExtendedService extendedService;
  final HRVSessionService? hrvService;

  const ManualTestsWidget({
    Key? key,
    required this.extendedService,
    this.hrvService,
  }) : super(key: key);

  @override
  State<ManualTestsWidget> createState() => _ManualTestsWidgetState();
}

class _ManualTestsWidgetState extends State<ManualTestsWidget> {
  bool _isSpO2Testing = false;
  bool _isHRVTesting = false;
  bool _isTempTesting = false;
  bool _isResetting = false;

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Test Manuali',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            
            // SpO2 Test Section
            _buildTestSection(
              title: 'Test SpO2',
              subtitle: 'Saturazione ossigeno',
              icon: Icons.opacity,
              color: Colors.blue,
              isRunning: _isSpO2Testing,
              onStart: _startSpO2Test,
              onStop: _stopSpO2Test,
            ),
            
            const SizedBox(height: 12),
            
            // HRV Test Section
            _buildTestSection(
              title: 'Test HRV',
              subtitle: 'Variabilità frequenza cardiaca',
              icon: Icons.favorite,
              color: Colors.red,
              isRunning: _isHRVTesting,
              onStart: _startHRVTest,
              onStop: _stopHRVTest,
            ),
            
            const SizedBox(height: 12),
            
            // Temperature Test Section
            _buildTestSection(
              title: 'Test Temperatura',
              subtitle: 'Temperatura corporea',
              icon: Icons.thermostat,
              color: Colors.orange,
              isRunning: _isTempTesting,
              onStart: _startTemperatureTest,
              onStop: _stopTemperatureTest,
            ),
            
            const SizedBox(height: 16),
            
            // Device Control Section
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey.shade300),
                borderRadius: BorderRadius.circular(8),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'Controllo Device',
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 8),
                  ElevatedButton.icon(
                    onPressed: _isResetting ? null : _resetDevice,
                    icon: _isResetting 
                        ? const SizedBox(
                            width: 16,
                            height: 16,
                            child: CircularProgressIndicator(strokeWidth: 2),
                          )
                        : const Icon(Icons.restart_alt),
                    label: Text(_isResetting ? 'Resetting...' : 'Reset Device'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.red.shade400,
                      foregroundColor: Colors.white,
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

  Widget _buildTestSection({
    required String title,
    required String subtitle,
    required IconData icon,
    required Color color,
    required bool isRunning,
    required VoidCallback onStart,
    required VoidCallback onStop,
  }) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        border: Border.all(color: color.withOpacity(0.3)),
        borderRadius: BorderRadius.circular(8),
        color: color.withOpacity(0.05),
      ),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: color.withOpacity(0.1),
              borderRadius: BorderRadius.circular(8),
            ),
            child: Icon(icon, color: color, size: 24),
          ),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  title,
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w600,
                  ),
                ),
                Text(
                  subtitle,
                  style: TextStyle(
                    fontSize: 12,
                    color: Colors.grey.shade600,
                  ),
                ),
              ],
            ),
          ),
          const SizedBox(width: 8),
          isRunning
              ? Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const SizedBox(
                      width: 16,
                      height: 16,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    ),
                    const SizedBox(width: 8),
                    TextButton(
                      onPressed: onStop,
                      style: TextButton.styleFrom(
                        foregroundColor: Colors.red,
                        padding: const EdgeInsets.symmetric(horizontal: 8),
                      ),
                      child: const Text('STOP'),
                    ),
                  ],
                )
              : ElevatedButton(
                  onPressed: onStart,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: color,
                    foregroundColor: Colors.white,
                    padding: const EdgeInsets.symmetric(horizontal: 16),
                  ),
                  child: const Text('START'),
                ),
        ],
      ),
    );
  }

  Future<void> _startSpO2Test() async {
    setState(() => _isSpO2Testing = true);
    
    try {
      await widget.extendedService.measureSpO2();
      
      // Mostra notifica di successo
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test SpO2 avviato - Controlla i dati in tempo reale'),
            backgroundColor: Colors.blue,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore test SpO2: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      setState(() => _isSpO2Testing = false);
    }
  }

  Future<void> _stopSpO2Test() async {
    try {
      await widget.extendedService.forceExitSpO2Mode();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test SpO2 interrotto'),
            backgroundColor: Colors.orange,
          ),
        );
      }
    } finally {
      setState(() => _isSpO2Testing = false);
    }
  }

  Future<void> _startHRVTest() async {
    setState(() => _isHRVTesting = true);
    
    try {
      
      if (widget.hrvService != null) {
        // Usa il servizio HRV se disponibile
        await widget.extendedService.requestHRVData();
      } else {
        // Fallback usando extended service
        await widget.extendedService.requestHRVData();
      }
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test HRV avviato - Controlla i dati HR estesi'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore test HRV: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      setState(() => _isHRVTesting = false);
    }
  }

  Future<void> _stopHRVTest() async {
    try {
      // Non c'è un comando specifico per fermare HRV, quindi solo feedback
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test HRV interrotto'),
            backgroundColor: Colors.orange,
          ),
        );
      }
    } finally {
      setState(() => _isHRVTesting = false);
    }
  }

  Future<void> _startTemperatureTest() async {
    setState(() => _isTempTesting = true);
    
    try {
      await widget.extendedService.requestTemperature();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test temperatura avviato'),
            backgroundColor: Colors.orange,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore test temperatura: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      setState(() => _isTempTesting = false);
    }
  }

  Future<void> _stopTemperatureTest() async {
    setState(() => _isTempTesting = false);
    
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Test temperatura interrotto'),
          backgroundColor: Colors.orange,
        ),
      );
    }
  }

  Future<void> _resetDevice() async {
    setState(() => _isResetting = true);
    
    try {
      await widget.extendedService.deviceReset();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Device resettato con successo'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore reset: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      setState(() => _isResetting = false);
    }
  }
}
