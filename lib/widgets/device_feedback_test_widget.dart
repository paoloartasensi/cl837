import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../services/ble_protocol/official_commands_complete.dart';

/// Widget per testare le funzionalità di feedback del device CL837
/// Basato sulla documentazione REVERSE: NO VIBRAZIONE, ma supporta LED e Allarmi HR
class DeviceFeedbackTestWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const DeviceFeedbackTestWidget({
    Key? key,
    required this.service,
  }) : super(key: key);

  @override
  State<DeviceFeedbackTestWidget> createState() => _DeviceFeedbackTestWidgetState();
}

class _DeviceFeedbackTestWidgetState extends State<DeviceFeedbackTestWidget> {
  String _lastCommand = "";
  String _lastResponse = "";
  
  // Test results
  final Map<String, String> _testResults = {};
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Feedback Tests'),
        backgroundColor: Colors.deepPurple,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // DISCLAIMER VIBRAZIONE
            Card(
              color: Colors.blue[50],
              child: const Padding(
                padding: EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    Icon(Icons.info, color: Colors.blue, size: 32),
                    SizedBox(height: 8),
                    Text(
                      '🔍 RICERCA COMANDI VIBRAZIONE',
                      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                    ),
                    SizedBox(height: 8),
                    Text(
                      'CL837 HA vibrazione (confermato: vibra per HR alti e power off).\n'
                      'Cerchiamo i comandi BLE per controllarla direttamente.',
                      textAlign: TextAlign.center,
                    ),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 24),
            
            // LED CONTROL TESTS
            _buildSectionTitle('🔴 LED Control Tests'),
            _buildTestCard(
              'SpO2 LED (Rosso)',
              'Attiva LED rosso per misurazione SpO2',
              () => _testSpO2LED(),
              _testResults['spo2_led'] ?? '',
            ),
            
            const SizedBox(height: 16),
            
            // HEART RATE ALARM TESTS  
            _buildSectionTitle('💓 Heart Rate Alarm Tests'),
            _buildTestCard(
              'Abilita HR Alarm',
              'Comando 0x57 - Attiva allarme frequenza cardiaca',
              () => _testHRAlarmEnable(),
              _testResults['hr_alarm_enable'] ?? '',
            ),
            
            _buildTestCard(
              'Leggi HR Alarm Status',
              'Comando 0x5B - Legge stato allarme HR',
              () => _testHRAlarmStatus(),
              _testResults['hr_alarm_status'] ?? '',
            ),
            
            _buildTestCard(
              'Disabilita HR Alarm',
              'Comando 0x57 - Disattiva allarme frequenza cardiaca',
              () => _testHRAlarmDisable(),
              _testResults['hr_alarm_disable'] ?? '',
            ),
            
            const SizedBox(height: 16),
            
            // EXPERIMENTAL COMMANDS
            _buildSectionTitle('🧪 Experimental Feedback Tests'),
            
            _buildTestCard(
              '🔍 Test Comandi Vibrazione',
              'Cerca comandi BLE per vibrazione diretta (range 0x60-0x8F)',
              () => _testVibrationCommands(),
              _testResults['vibration_search'] ?? '',
            ),
            
            _buildTestCard(
              'Trigger HR Alarm per Vibrazione',
              'Usa HR Alarm per testare vibrazione automatica',
              () => _testHRAlarmVibration(),
              _testResults['hr_vibration'] ?? '',
            ),
            
            _buildTestCard(
              'Test Comando 0x3F (BLE Disable)',
              'Potenziale comando audio/beep feedback',
              () => _testBluetoothDisable(),
              _testResults['bluetooth_disable'] ?? '',
            ),
            
            _buildTestCard(
              'Test Comandi LED Sconosciuti',
              'Esplora comandi 0x50-0x60 per feedback audio/LED',
              () => _testUnknownLEDCommands(),
              _testResults['unknown_led'] ?? '',
            ),
            
            const SizedBox(height: 24),
            
            // COMMAND LOG
            _buildSectionTitle('📡 Command Log'),
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('Last Command: $_lastCommand', 
                         style: const TextStyle(fontFamily: 'monospace')),
                    const SizedBox(height: 8),
                    Text('Last Response: $_lastResponse',
                         style: const TextStyle(fontFamily: 'monospace')),
                  ],
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            // CLEAR RESULTS
            ElevatedButton(
              onPressed: _clearResults,
              style: ElevatedButton.styleFrom(backgroundColor: Colors.grey),
              child: const Text('Clear All Results'),
            ),
          ],
        ),
      ),
    );
  }
  
  Widget _buildSectionTitle(String title) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: Text(
        title,
        style: const TextStyle(
          fontSize: 18,
          fontWeight: FontWeight.bold,
          color: Colors.deepPurple,
        ),
      ),
    );
  }
  
  Widget _buildTestCard(String title, String description, VoidCallback onTest, String result) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 4),
            Text(description, style: TextStyle(color: Colors.grey[600])),
            const SizedBox(height: 12),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                ElevatedButton(
                  onPressed: onTest,
                  child: const Text('Test'),
                ),
                if (result.isNotEmpty)
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.only(left: 16.0),
                      child: Text(
                        result,
                        style: TextStyle(
                          color: result.contains('✅') ? Colors.green : 
                                 result.contains('❌') ? Colors.red : 
                                 Colors.orange,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ),
                  ),
              ],
            ),
          ],
        ),
      ),
    );
  }
  
  // ===== LED CONTROL TESTS =====
  
  Future<void> _testSpO2LED() async {
    try {
      setState(() {
        _lastCommand = "SpO2 LED Start (0x37)";
      });
      
      // Comando SpO2 START - attiva LED rosso
      final command = OfficialChileafCommands.setBloodOxygen(1);
      await widget.service.sendRawCommand(command);
      
      setState(() {
        _testResults['spo2_led'] = '✅ LED rosso attivato - Comando inviato';
        _lastResponse = 'LED SpO2 START inviato';
      });
      
      // Aspetta 5 secondi poi spegni
      await Future.delayed(const Duration(seconds: 5));
      
      final stopCommand = OfficialChileafCommands.setBloodOxygen(0);
      await widget.service.sendRawCommand(stopCommand);
      
      setState(() {
        final currentResult = _testResults['spo2_led'] ?? '';
        _testResults['spo2_led'] = '$currentResult → Spento dopo 5sec';
      });
      
    } catch (e) {
      setState(() {
        _testResults['spo2_led'] = '❌ Errore: $e';
      });
    }
  }
  
  // ===== HEART RATE ALARM TESTS =====
  
  Future<void> _testHRAlarmEnable() async {
    try {
      setState(() {
        _lastCommand = "HR Alarm Enable (0x57)";
      });
      
      // Comando 0x57 con parametro 1 (enable)
      final command = OfficialChileafCommands.setHeartRateAlarm(true);
      await widget.service.sendRawCommand(command);
      
      setState(() {
        _testResults['hr_alarm_enable'] = '✅ Comando HR Alarm ENABLE inviato';
        _lastResponse = 'HR Alarm abilitato';
      });
      
    } catch (e) {
      setState(() {
        _testResults['hr_alarm_enable'] = '❌ Errore: $e';
      });
    }
  }
  
  Future<void> _testHRAlarmStatus() async {
    try {
      setState(() {
        _lastCommand = "HR Alarm Status (0x5B)";
      });
      
      // Comando 0x5B per leggere stato allarme
      final command = OfficialChileafCommands.getHeartRateAlarm();
      await widget.service.sendRawCommand(command);
      
      setState(() {
        _testResults['hr_alarm_status'] = '✅ Richiesta stato HR Alarm inviata';
        _lastResponse = 'Attendi risposta device...';
      });
      
    } catch (e) {
      setState(() {
        _testResults['hr_alarm_status'] = '❌ Errore: $e';
      });
    }
  }
  
  Future<void> _testHRAlarmDisable() async {
    try {
      setState(() {
        _lastCommand = "HR Alarm Disable (0x57)";
      });
      
      // Comando 0x57 con parametro 0 (disable)
      final command = OfficialChileafCommands.setHeartRateAlarm(false);
      await widget.service.sendRawCommand(command);
      
      setState(() {
        _testResults['hr_alarm_disable'] = '✅ Comando HR Alarm DISABLE inviato';
        _lastResponse = 'HR Alarm disabilitato';
      });
      
    } catch (e) {
      setState(() {
        _testResults['hr_alarm_disable'] = '❌ Errore: $e';
      });
    }
  }
  
  // ===== EXPERIMENTAL TESTS =====
  
  Future<void> _testBluetoothDisable() async {
    try {
      setState(() {
        _lastCommand = "Bluetooth Disable Test (0x3F)";
      });
      
      // ATTENZIONE: Questo comando potrebbe disconnettere il device!
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('⚠️ Attenzione'),
          content: const Text(
            'Questo comando potrebbe disconnettere il device!\n'
            'Continuare con il test?'
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Annulla'),
            ),
            TextButton(
              onPressed: () async {
                Navigator.pop(context);
                await _executeBluetoothDisableTest();
              },
              child: const Text('Continua'),
            ),
          ],
        ),
      );
      
    } catch (e) {
      setState(() {
        _testResults['bluetooth_disable'] = '❌ Errore: $e';
      });
    }
  }
  
  Future<void> _executeBluetoothDisableTest() async {
    try {
      final command = OfficialChileafCommands.setBluetoothDisabled();
      await widget.service.sendRawCommand(command);
      
      setState(() {
        _testResults['bluetooth_disable'] = '⚠️ Comando BLE Disable inviato - Device potrebbe disconnettersi';
        _lastResponse = 'BLE Disable test eseguito';
      });
      
    } catch (e) {
      setState(() {
        _testResults['bluetooth_disable'] = '❌ Errore nell\'esecuzione: $e';
      });
    }
  }
  
  Future<void> _testUnknownLEDCommands() async {
    try {
      setState(() {
        _lastCommand = "Testing unknown LED commands (0x50-0x5F)";
      });
      
      // Test range di comandi potenziali per LED/Audio feedback
      List<int> testCommands = [0x50, 0x52, 0x53, 0x54, 0x55, 0x56, 0x58, 0x59, 0x5A];
      String results = '';
      
      for (int cmd in testCommands) {
        try {
          // Costruisci comando manualmente usando il checksum Java
          List<int> frame = [
            0xFF,           // Start byte
            0x05,           // Length (4 + 1 parametro)
            cmd,            // Command to test
            0x01,           // Parameter (try enable)
            0x00            // Checksum (calcolato)
          ];
          
          // Calcola checksum Java come nella documentazione
          int sum = 0xFF + 0x05 + cmd + 0x01;
          int checksum = ((-sum) & 0xFF) ^ 0x3A;
          frame[4] = checksum & 0xFF;
          
          await widget.service.sendRawCommand(frame);
          results += '0x${cmd.toRadixString(16).toUpperCase()} ✅, ';
          
          // Delay tra comandi per non sovraccaricare
          await Future.delayed(const Duration(milliseconds: 200));
          
        } catch (e) {
          results += '0x${cmd.toRadixString(16).toUpperCase()} ❌, ';
        }
      }
      
      setState(() {
        _testResults['unknown_led'] = 'Test completati: $results';
        _lastResponse = 'Scan LED commands completato';
      });
      
    } catch (e) {
      setState(() {
        _testResults['unknown_led'] = '❌ Errore nel test scan: $e';
      });
    }
  }
  
  void _clearResults() {
    setState(() {
      _testResults.clear();
      _lastCommand = "";
      _lastResponse = "";
    });
  }
}
