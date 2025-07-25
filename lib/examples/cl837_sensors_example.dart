/// Esempio pratico di utilizzo dell'API CL837 Medical Sensors
/// Questo file mostra come integrare l'API in un'applicazione esterna
/// 
/// IMPORTANTE: Questo è un esempio completo funzionante che può essere
/// copiato e utilizzato in altre applicazioni Flutter

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../services/cl837_medical_sensors_api.dart';

/// Widget esempio che dimostra l'utilizzo completo dell'API
class CL837MedicalSensorsExample extends StatefulWidget {
  final BluetoothDevice device;
  
  const CL837MedicalSensorsExample({
    Key? key,
    required this.device,
  }) : super(key: key);

  @override
  State<CL837MedicalSensorsExample> createState() => _CL837MedicalSensorsExampleState();
}

class _CL837MedicalSensorsExampleState extends State<CL837MedicalSensorsExample> {
  CL837MedicalSensorsAPI? _sensorsAPI;
  
  // Dati correnti
  SpO2Reading? _currentSpO2;
  HRVReading? _currentHRV;
  TemperatureReading? _currentTemperature;
  HeartRateReading? _currentHeartRate;
  
  // Stati
  bool _isConnected = false;
  bool _isSpO2Running = false;
  bool _isHRVRunning = false;
  bool _isTemperatureRunning = false;
  
  // Lista storica per grafici (esempio)
  final List<SpO2Reading> _spO2History = [];
  final List<TemperatureReading> _temperatureHistory = [];

  @override
  void initState() {
    super.initState();
    _connectAndInitialize();
  }

  /// Connessione e inizializzazione dell'API
  Future<void> _connectAndInitialize() async {
    try {
      setState(() => _isConnected = false);
      
      // Step 1: Connetti al dispositivo
      await widget.device.connect();
      
      // Step 2: Scopri i servizi
      final services = await widget.device.discoverServices();
      
      // Step 3: Trova le caratteristiche Chileaf
      BluetoothCharacteristic? rxChar;
      BluetoothCharacteristic? txChar;
      
      for (final service in services) {
        if (service.uuid.toString().contains('aae28f00')) {
          for (final char in service.characteristics) {
            if (char.uuid.toString().contains('aae28f02')) {
              rxChar = char; // Write to device
            } else if (char.uuid.toString().contains('aae28f01')) {
              txChar = char; // Read from device
              await char.setNotifyValue(true);
            }
          }
        }
      }
      
      if (rxChar == null || txChar == null) {
        throw Exception('Chileaf characteristics not found');
      }
      
      // Step 4: Inizializza API
      _sensorsAPI = CL837MedicalSensorsAPI(
        rxCharacteristic: rxChar,
        txCharacteristic: txChar,
      );
      
      // Step 5: Setup listeners per notifiche BLE
      txChar.onValueReceived.listen((data) {
        _sensorsAPI?.processIncomingData(data);
      });
      
      // Step 6: Setup listeners per i dati dei sensori
      _setupDataListeners();
      
      setState(() => _isConnected = true);
      
      // Mostra snackbar di successo
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('✅ Connesso al CL837!'),
            backgroundColor: Colors.green,
          ),
        );
      }
      
    } catch (e) {
      setState(() => _isConnected = false);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('❌ Errore connessione: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  /// Setup dei listener per i dati dei sensori
  void _setupDataListeners() {
    if (_sensorsAPI == null) return;
    
    // Listener SpO2
    _sensorsAPI!.spO2Stream.listen((reading) {
      setState(() {
        _currentSpO2 = reading;
        _spO2History.add(reading);
        
        // Mantieni solo ultimi 100 valori per performance
        if (_spO2History.length > 100) {
          _spO2History.removeAt(0);
        }
      });
      
      print('🫁 SpO2 ricevuto: $reading');
    });
    
    // Listener HRV
    _sensorsAPI!.hrvStream.listen((reading) {
      setState(() => _currentHRV = reading);
      print('💓 HRV ricevuto: $reading');
    });
    
    // Listener Temperatura
    _sensorsAPI!.temperatureStream.listen((reading) {
      setState(() {
        _currentTemperature = reading;
        _temperatureHistory.add(reading);
        
        if (_temperatureHistory.length > 100) {
          _temperatureHistory.removeAt(0);
        }
      });
      
      print('🌡️ Temperatura ricevuta: $reading');
    });
    
    // Listener Heart Rate (per HRV)
    _sensorsAPI!.heartRateStream.listen((reading) {
      setState(() => _currentHeartRate = reading);
      print('💗 Heart Rate ricevuto: $reading');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CL837 Medical Sensors'),
        backgroundColor: _isConnected ? Colors.green : Colors.red,
        foregroundColor: Colors.white,
      ),
      body: _isConnected ? _buildConnectedView() : _buildDisconnectedView(),
    );
  }

  /// Vista quando disconnesso
  Widget _buildDisconnectedView() {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          CircularProgressIndicator(),
          SizedBox(height: 16),
          Text('Connessione al CL837...'),
        ],
      ),
    );
  }

  /// Vista principale quando connesso
  Widget _buildConnectedView() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Controlli principali
          _buildControlsSection(),
          const SizedBox(height: 24),
          
          // Dati SpO2
          _buildSpO2Section(),
          const SizedBox(height: 24),
          
          // Dati HRV
          _buildHRVSection(),
          const SizedBox(height: 24),
          
          // Dati Temperatura
          _buildTemperatureSection(),
          const SizedBox(height: 24),
          
          // Statistiche
          _buildStatisticsSection(),
        ],
      ),
    );
  }

  /// Sezione controlli
  Widget _buildControlsSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '🎛️ Controlli Sensori',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: _isSpO2Running ? _stopSpO2 : _startSpO2,
                    icon: Icon(_isSpO2Running ? Icons.stop : Icons.play_arrow),
                    label: Text(_isSpO2Running ? 'Stop SpO2' : 'Start SpO2'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _isSpO2Running ? Colors.red : Colors.blue,
                      foregroundColor: Colors.white,
                    ),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: _isHRVRunning ? _stopHRV : _startHRV,
                    icon: Icon(_isHRVRunning ? Icons.stop : Icons.favorite),
                    label: Text(_isHRVRunning ? 'Stop HRV' : 'Start HRV'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _isHRVRunning ? Colors.red : Colors.green,
                      foregroundColor: Colors.white,
                    ),
                  ),
                ),
              ],
            ),
            
            const SizedBox(height: 8),
            
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: _isTemperatureRunning ? _stopTemperature : _startTemperature,
                icon: Icon(_isTemperatureRunning ? Icons.stop : Icons.thermostat),
                label: Text(_isTemperatureRunning ? 'Stop Temperatura' : 'Start Temperatura'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: _isTemperatureRunning ? Colors.red : Colors.orange,
                  foregroundColor: Colors.white,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  /// Sezione SpO2
  Widget _buildSpO2Section() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '🫁 Saturazione Ossigeno (SpO2)',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            if (_currentSpO2 != null) ...[
              Row(
                children: [
                  Expanded(
                    child: _buildDataTile(
                      'SpO2',
                      '${_currentSpO2!.spo2Percentage ?? "N/A"}%',
                      _currentSpO2!.isValid ? Colors.green : Colors.red,
                    ),
                  ),
                  Expanded(
                    child: _buildDataTile(
                      'Segnale',
                      '${_currentSpO2!.signalQuality}/15',
                      _currentSpO2!.signalQuality > 8 ? Colors.green : Colors.orange,
                    ),
                  ),
                ],
              ),
              
              const SizedBox(height: 8),
              
              Row(
                children: [
                  Expanded(
                    child: _buildDataTile(
                      'Heart Rate',
                      '${_currentSpO2!.heartRate ?? "N/A"} bpm',
                      Colors.blue,
                    ),
                  ),
                  Expanded(
                    child: _buildDataTile(
                      'Stato',
                      _currentSpO2!.isWearing ? 'Indossato' : 'Non indossato',
                      _currentSpO2!.isWearing ? Colors.green : Colors.red,
                    ),
                  ),
                ],
              ),
              
              if (_spO2History.isNotEmpty) ...[
                const SizedBox(height: 16),
                Text(
                  'Ultime ${_spO2History.length} letture',
                  style: const TextStyle(fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                SizedBox(
                  height: 50,
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: _spO2History.length,
                    itemBuilder: (context, index) {
                      final reading = _spO2History[index];
                      return Container(
                        width: 60,
                        margin: const EdgeInsets.only(right: 4),
                        decoration: BoxDecoration(
                          color: reading.isValid ? Colors.green.shade100 : Colors.red.shade100,
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Center(
                          child: Text(
                            '${reading.spo2Percentage ?? "N/A"}%',
                            style: TextStyle(
                              fontSize: 12,
                              color: reading.isValid ? Colors.green.shade800 : Colors.red.shade800,
                            ),
                          ),
                        ),
                      );
                    },
                  ),
                ),
              ],
            ] else ...[
              const Text('Nessun dato SpO2 disponibile'),
            ],
          ],
        ),
      ),
    );
  }

  /// Sezione HRV
  Widget _buildHRVSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '💓 Variabilità Heart Rate (HRV)',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            if (_currentHRV != null) ...[
              Row(
                children: [
                  Expanded(
                    child: _buildDataTile(
                      'RMSSD',
                      '${_currentHRV!.rmssd.toStringAsFixed(1)} ms',
                      Colors.purple,
                    ),
                  ),
                  Expanded(
                    child: _buildDataTile(
                      'SDNN',
                      '${_currentHRV!.sdnn.toStringAsFixed(1)} ms',
                      Colors.indigo,
                    ),
                  ),
                ],
              ),
              
              const SizedBox(height: 8),
              
              Row(
                children: [
                  Expanded(
                    child: _buildDataTile(
                      'pNN50',
                      '${_currentHRV!.pnn50.toStringAsFixed(1)}%',
                      Colors.teal,
                    ),
                  ),
                  Expanded(
                    child: _buildDataTile(
                      'Stress',
                      '${_currentHRV!.stressIndex.toStringAsFixed(0)}%',
                      _currentHRV!.stressIndex < 50 ? Colors.green : Colors.red,
                    ),
                  ),
                ],
              ),
              
              const SizedBox(height: 16),
              Text(
                'RR-Intervals: ${_currentHRV!.rrIntervals.length} campioni',
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
            ] else ...[
              const Text('Nessun dato HRV disponibile'),
            ],
          ],
        ),
      ),
    );
  }

  /// Sezione Temperatura
  Widget _buildTemperatureSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '🌡️ Temperatura',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            if (_currentTemperature != null) ...[
              Row(
                children: [
                  Expanded(
                    child: _buildDataTile(
                      'Polso',
                      '${_currentTemperature!.wristTemperature.toStringAsFixed(1)}°C',
                      _currentTemperature!.isValid ? Colors.green : Colors.red,
                    ),
                  ),
                  Expanded(
                    child: _buildDataTile(
                      'Corporea',
                      _currentTemperature!.bodyTemperature != null 
                        ? '${_currentTemperature!.bodyTemperature!.toStringAsFixed(1)}°C'
                        : 'N/A',
                      Colors.orange,
                    ),
                  ),
                ],
              ),
              
              if (_temperatureHistory.isNotEmpty) ...[
                const SizedBox(height: 16),
                Text(
                  'Trend temperatura (ultime ${_temperatureHistory.length} letture)',
                  style: const TextStyle(fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                SizedBox(
                  height: 50,
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: _temperatureHistory.length,
                    itemBuilder: (context, index) {
                      final reading = _temperatureHistory[index];
                      return Container(
                        width: 80,
                        margin: const EdgeInsets.only(right: 4),
                        decoration: BoxDecoration(
                          color: Colors.orange.shade100,
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Center(
                          child: Text(
                            '${reading.wristTemperature.toStringAsFixed(1)}°C',
                            style: TextStyle(
                              fontSize: 12,
                              color: Colors.orange.shade800,
                            ),
                          ),
                        ),
                      );
                    },
                  ),
                ),
              ],
            ] else ...[
              const Text('Nessun dato temperatura disponibile'),
            ],
          ],
        ),
      ),
    );
  }

  /// Sezione statistiche
  Widget _buildStatisticsSection() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '📊 Statistiche Sessione',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            Row(
              children: [
                Expanded(
                  child: _buildStatTile(
                    'SpO2 Validi',
                    '${_spO2History.where((r) => r.isValid).length}/${_spO2History.length}',
                    Colors.blue,
                  ),
                ),
                Expanded(
                  child: _buildStatTile(
                    'Temp. Media',
                    _temperatureHistory.isNotEmpty
                      ? '${(_temperatureHistory.map((r) => r.wristTemperature).reduce((a, b) => a + b) / _temperatureHistory.length).toStringAsFixed(1)}°C'
                      : 'N/A',
                    Colors.orange,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  /// Widget per dati individuali
  Widget _buildDataTile(String label, String value, Color color) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Column(
        children: [
          Text(
            label,
            style: TextStyle(
              fontSize: 12,
              color: color.darken(20),
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            value,
            style: TextStyle(
              fontSize: 16,
              color: color.darken(40),
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  /// Widget per statistiche
  Widget _buildStatTile(String label, String value, Color color) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Column(
        children: [
          Text(
            label,
            style: const TextStyle(fontSize: 12, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 4),
          Text(
            value,
            style: TextStyle(
              fontSize: 14,
              color: color,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  /// Metodi di controllo sensori
  Future<void> _startSpO2() async {
    if (_sensorsAPI == null) return;
    
    setState(() => _isSpO2Running = true);
    
    try {
      await _sensorsAPI!.startSpO2Measurement(
        maxDuration: const Duration(seconds: 60),
      );
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('🫁 SpO2 avviato')),
        );
      }
    } catch (e) {
      setState(() => _isSpO2Running = false);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Errore SpO2: $e')),
        );
      }
    }
  }

  Future<void> _stopSpO2() async {
    if (_sensorsAPI == null) return;
    
    await _sensorsAPI!.stopSpO2Measurement();
    setState(() => _isSpO2Running = false);
    
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('🛑 SpO2 fermato')),
      );
    }
  }

  Future<void> _startHRV() async {
    if (_sensorsAPI == null) return;
    
    setState(() => _isHRVRunning = true);
    
    try {
      await _sensorsAPI!.startHRVSession(
        sessionDuration: const Duration(minutes: 3),
      );
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('💓 HRV avviato')),
        );
      }
    } catch (e) {
      setState(() => _isHRVRunning = false);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Errore HRV: $e')),
        );
      }
    }
  }

  Future<void> _stopHRV() async {
    if (_sensorsAPI == null) return;
    
    await _sensorsAPI!.stopHRVSession();
    setState(() => _isHRVRunning = false);
    
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('🛑 HRV fermato')),
      );
    }
  }

  Future<void> _startTemperature() async {
    if (_sensorsAPI == null) return;
    
    setState(() => _isTemperatureRunning = true);
    
    try {
      await _sensorsAPI!.startTemperatureMonitoring(
        interval: const Duration(seconds: 10),
      );
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('🌡️ Temperatura avviata')),
        );
      }
    } catch (e) {
      setState(() => _isTemperatureRunning = false);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Errore temperatura: $e')),
        );
      }
    }
  }

  Future<void> _stopTemperature() async {
    if (_sensorsAPI == null) return;
    
    await _sensorsAPI!.stopTemperatureMonitoring();
    setState(() => _isTemperatureRunning = false);
    
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('🛑 Temperatura fermata')),
      );
    }
  }

  @override
  void dispose() {
    _sensorsAPI?.stopAllMeasurements();
    _sensorsAPI?.dispose();
    widget.device.disconnect();
    super.dispose();
  }
}

/// Estensione per scurire i colori
extension ColorExtension on Color {
  Color darken(int percent) {
    assert(1 <= percent && percent <= 100, 'percent must be between 1 and 100');
    final factor = 1 - percent / 100;
    return Color.fromARGB(
      alpha,
      (red * factor).round(),
      (green * factor).round(),
      (blue * factor).round(),
    );
  }
}
