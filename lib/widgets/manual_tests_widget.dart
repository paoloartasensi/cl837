import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../hrv_session_service.dart';
import '../models/spo2_data.dart';
import '../models/temperature_data.dart';
import '../models/hrv_data.dart';
import '../models/manual_test_result.dart';
import '../services/manual_test_storage.dart';

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
  // Stati dei test
  bool _isSpo2Testing = false;
  bool _isHRVTesting = false;
  bool _isTempTesting = false;
  bool _isResetting = false;
  bool _isTestingLED = false;

  // Risultati dei test
  SpO2Data? _latestSpO2Result;
  TemperatureData? _latestTempResult;
  HRVData? _latestHRVResult;
  
  // Risultati congelati dai test manuali
  SpO2Data? _manualSpO2Result;
  TemperatureData? _manualTempResult;
  HRVData? _manualHRVResult;

  // Subscriptions per i stream
  StreamSubscription<SpO2Data>? _spo2Subscription;
  StreamSubscription<TemperatureData>? _tempSubscription;
  StreamSubscription<HRVData>? _hrvSubscription;

  @override
  void initState() {
    super.initState();
    _setupDataStreams();
    
    // Imposta il callback per il completamento automatico SpO2
    widget.extendedService.setSpO2AutoCompleteCallback(() {
      if (mounted) {
        _onSpO2AutoCompleted();
      }
    });
  }

  @override
  void dispose() {
    _spo2Subscription?.cancel();
    _tempSubscription?.cancel();
    _hrvSubscription?.cancel();
    super.dispose();
  }

  void _setupDataStreams() {
    // Ascolta i risultati SpO2
    _spo2Subscription = widget.extendedService.spo2DataStream.listen((data) {
      if (mounted) {
        setState(() {
          _latestSpO2Result = data;
          // Aggiorna il risultato manuale solo se il test è attivo
          if (_isSpo2Testing) {
            _manualSpO2Result = data;
          }
        });
        // Salva automaticamente se il valore SpO2 è valido E il test è attivo
        if (_isSpo2Testing && data.spo2Value != null && data.isValidMeasurement) {
          _saveTestResult(ManualTestResult.fromSpO2(data, notes: 'Test manuale SpO2'));
        }
      }
    });

    // Ascolta i risultati temperatura
    _tempSubscription = widget.extendedService.temperatureDataStream.listen((data) {
      if (mounted) {
        setState(() {
          _latestTempResult = data;
          // Aggiorna il risultato manuale solo se il test è attivo
          if (_isTempTesting) {
            _manualTempResult = data;
          }
        });
        // Salva automaticamente i dati temperatura solo se il test è attivo
        if (_isTempTesting) {
          _saveTestResult(ManualTestResult.fromTemperature(data, notes: 'Test manuale temperatura'));
        }
      }
    });

    // Ascolta i risultati HRV
    _hrvSubscription = widget.extendedService.hrvDataStream.listen((data) {
      if (mounted) {
        debugPrint('📊 HRV Data received: RMSSD=${data.rmssd}, SDNN=${data.sdnn}, HR=${data.estimatedHR}');
        setState(() {
          _latestHRVResult = data;
          // Aggiorna il risultato manuale solo se il test è attivo
          if (_isHRVTesting) {
            _manualHRVResult = data;
          }
        });
        // Salva automaticamente i dati HRV solo se il test è attivo
        if (_isHRVTesting) {
          _saveTestResult(ManualTestResult.fromHRV(data, notes: 'Test manuale HRV'));
        }
      }
    });
  }

  // Salva il risultato del test
  Future<void> _saveTestResult(ManualTestResult result) async {
    try {
      await ManualTestStorage.saveTestResult(result);
      debugPrint('✅ Test result saved: ${result.testTypeDisplayName}');
    } catch (e) {
      debugPrint('❌ Error saving test result: $e');
    }
  }

  // Cancella i risultati congelati dei test manuali
  void _clearTestResults() {
    setState(() {
      _manualSpO2Result = null;
      _manualTempResult = null;
      _manualHRVResult = null;
    });
    
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('Risultati test manuali cancellati'),
        backgroundColor: Colors.grey,
        duration: Duration(seconds: 2),
      ),
    );
  }

  // Gestisce il completamento automatico del test SpO2
  void _onSpO2AutoCompleted() {
    setState(() => _isSpo2Testing = false);
    
    if (mounted) {
      // Usa il risultato congelato del test manuale
      final spo2Value = _manualSpO2Result?.spo2Value;
      final quality = _manualSpO2Result?.signalQuality ?? 0;
      
      String message;
      if (spo2Value != null && spo2Value > 0) {
        message = 'SpO2 WatchFit completato! Risultato: $spo2Value% (qualità segnale: $quality/15)';
      } else {
        message = 'SpO2 WatchFit completato dopo 50 sec o con 2 letture valide. Controlla i risultati sopra.';
      }
      
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(message),
          backgroundColor: Colors.green,
          duration: const Duration(seconds: 5),
          action: _manualSpO2Result != null ? SnackBarAction(
            label: 'Dettagli',
            textColor: Colors.white,
            onPressed: () {
              // Mostra dettagli del risultato
              _showSpO2Details();
            },
          ) : null,
        ),
      );
    }
  }

  // Mostra i dettagli del risultato SpO2
  void _showSpO2Details() {
    final resultToShow = _manualSpO2Result ?? _latestSpO2Result;
    if (resultToShow == null) return;
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Risultato SpO2'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('SpO2: ${resultToShow.spo2Value ?? "N/A"}%'),
            Text('Qualità Segnale: ${resultToShow.signalQuality}/15'),
            Text('Descrizione: ${resultToShow.signalQualityDescription}'),
            Text('Timestamp: ${resultToShow.timestamp.toString().substring(0, 19)}'),
          ],
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
    return Scaffold(
      appBar: AppBar(
        title: const Text('Test Manuali'),
        backgroundColor: Colors.blue.shade700,
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            // SpO2 Test Section
            _buildTestSection(
              title: 'Test SpO2 WatchFit',
              subtitle: 'Saturazione ossigeno (50 sec max, early stop)',
              icon: Icons.opacity,
              color: Colors.blue,
              isRunning: _isSpo2Testing,
              onStart: _startSpO2Test,
              onStop: _stopSpO2Test,
            ),
            
            const SizedBox(height: 12),
            
            // HRV Test Section
            _buildTestSection(
              title: 'Test HRV',
              subtitle: 'Variabilità frequenza cardiaca (15 sec)',
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
              subtitle: 'Temperatura corporea (10 sec)',
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
                  Row(
                    children: [
                      Expanded(
                        child: ElevatedButton.icon(
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
                      ),
                      const SizedBox(width: 12),
                      Expanded(
                        child: ElevatedButton.icon(
                          onPressed: _isTestingLED ? null : _testLED,
                          icon: _isTestingLED 
                              ? const SizedBox(
                                  width: 16,
                                  height: 16,
                                  child: CircularProgressIndicator(strokeWidth: 2),
                                )
                              : const Icon(Icons.lightbulb),
                          label: Text(_isTestingLED ? 'Testing...' : 'Test LED SpO2'),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.blue.shade400,
                            foregroundColor: Colors.white,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Sezione Risultati Test
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.green.shade300),
                borderRadius: BorderRadius.circular(8),
                color: Colors.green.shade50,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Icon(Icons.analytics, color: Colors.green.shade700),
                      const SizedBox(width: 8),
                      Text(
                        'Risultati Test',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                          color: Colors.green.shade700,
                        ),
                      ),
                      const Spacer(),
                      if (_manualSpO2Result != null || _manualTempResult != null || _manualHRVResult != null)
                        TextButton.icon(
                          onPressed: _clearTestResults,
                          icon: Icon(Icons.clear, size: 16, color: Colors.grey.shade600),
                          label: Text(
                            'Clear',
                            style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
                          ),
                          style: TextButton.styleFrom(
                            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                            minimumSize: Size.zero,
                            tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                          ),
                        ),
                    ],
                  ),
                  const SizedBox(height: 12),
                  
                  // Risultati SpO2 (priorità ai risultati del test manuale)
                  if (_manualSpO2Result != null || _latestSpO2Result != null) ...[
                    Builder(
                      builder: (context) {
                        final spo2Result = _manualSpO2Result ?? _latestSpO2Result!;
                        final isManualResult = _manualSpO2Result != null;
                        return Column(
                          children: [
                            _buildResultRowWithBadge(
                              'SpO2:', 
                              spo2Result.spo2Value != null 
                                  ? '${spo2Result.spo2Value}%' 
                                  : (_isSpo2Testing ? 'Misurazione in corso...' : 'Non disponibile'),
                              Icons.opacity,
                              Colors.blue,
                              showTestBadge: isManualResult,
                            ),
                            _buildResultRow(
                              'Segnale:', 
                              spo2Result.signalQualityDescription,
                              Icons.signal_cellular_alt,
                              spo2Result.signalQuality >= 8 ? Colors.green : Colors.orange,
                            ),
                          ],
                        );
                      },
                    ),
                  ],
                  
                  // Risultati Temperatura (priorità ai risultati del test manuale)
                  if (_manualTempResult != null || _latestTempResult != null) ...[
                    Builder(
                      builder: (context) {
                        final tempResult = _manualTempResult ?? _latestTempResult!;
                        final isManualResult = _manualTempResult != null;
                        return Column(
                          children: [
                            _buildResultRowWithBadge(
                              'Temperatura Ambiente:', 
                              '${tempResult.ambientTempC.toStringAsFixed(1)}°C',
                              Icons.thermostat,
                              Colors.orange,
                              showTestBadge: isManualResult,
                            ),
                            _buildResultRow(
                              'Temperatura Polso:', 
                              '${tempResult.wristTempC.toStringAsFixed(1)}°C',
                              Icons.watch,
                              Colors.orange,
                            ),
                            _buildResultRow(
                              'Temperatura Corporea:', 
                              '${tempResult.bodyTempC.toStringAsFixed(1)}°C',
                              Icons.person,
                              Colors.red,
                            ),
                          ],
                        );
                      },
                    ),
                  ],
                  
                  // Risultati HRV (priorità ai risultati del test manuale)
                  if (_manualHRVResult != null || _latestHRVResult != null) ...[
                    Builder(
                      builder: (context) {
                        final hrvResult = _manualHRVResult ?? _latestHRVResult!;
                        final isManualResult = _manualHRVResult != null;
                        return Column(
                          children: [
                            _buildResultRowWithBadge(
                              'HRV RMSSD:', 
                              '${hrvResult.rmssd.toStringAsFixed(1)} ms (${hrvResult.hrvQuality})',
                              Icons.favorite,
                              Colors.red,
                              showTestBadge: isManualResult,
                            ),
                            _buildResultRow(
                              'HRV SDNN:', 
                              '${hrvResult.sdnn.toStringAsFixed(1)} ms',
                              Icons.show_chart,
                              Colors.red,
                            ),
                            _buildResultRow(
                              'FC Stimata:', 
                              '${hrvResult.estimatedHR.toStringAsFixed(0)} BPM (${hrvResult.hrCategory})',
                              Icons.monitor_heart,
                              Colors.red,
                            ),
                            _buildResultRow(
                              'RR Intervals:', 
                              '${hrvResult.rrIntervals.length} campioni',
                              Icons.timeline,
                              hrvResult.isDataValid ? Colors.green : Colors.orange,
                            ),
                          ],
                        );
                      },
                    ),
                  ],
                  
                  // Messaggio se nessun risultato
                  if (_manualSpO2Result == null && _latestSpO2Result == null && 
                      _manualTempResult == null && _latestTempResult == null && 
                      _manualHRVResult == null && _latestHRVResult == null)
                    const Padding(
                      padding: EdgeInsets.symmetric(vertical: 8.0),
                      child: Text(
                        'Nessun risultato disponibile. Avvia un test per vedere i dati.',
                        style: TextStyle(
                          color: Colors.grey,
                          fontStyle: FontStyle.italic,
                        ),
                      ),
                    ),
                ],
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Sezione Export
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.purple.shade300),
                borderRadius: BorderRadius.circular(8),
                color: Colors.purple.shade50,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Icon(Icons.download, color: Colors.purple.shade700),
                      const SizedBox(width: 8),
                      Text(
                        'Export Dati',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                          color: Colors.purple.shade700,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 12),
                  
                  Row(
                    children: [
                      Expanded(
                        child: ElevatedButton.icon(
                          onPressed: _exportTodayData,
                          icon: const Icon(Icons.today),
                          label: const Text('Export Oggi'),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.purple.shade400,
                            foregroundColor: Colors.white,
                          ),
                        ),
                      ),
                      const SizedBox(width: 12),
                      Expanded(
                        child: ElevatedButton.icon(
                          onPressed: _exportAllData,
                          icon: const Icon(Icons.archive),
                          label: const Text('Export Tutto'),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.purple.shade600,
                            foregroundColor: Colors.white,
                          ),
                        ),
                      ),
                    ],
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
        border: Border.all(color: color.withOpacity(0.5)),
        borderRadius: BorderRadius.circular(8),
        color: color.withOpacity(0.1),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Icon(icon, color: color),
              const SizedBox(width: 8),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      color: color,
                    ),
                  ),
                  Text(
                    subtitle,
                    style: TextStyle(
                      fontSize: 12,
                      color: color.withOpacity(0.8),
                    ),
                  ),
                ],
              ),
            ],
          ),
          const SizedBox(height: 8),
          Row(
            children: [
              ElevatedButton.icon(
                onPressed: isRunning ? null : onStart,
                icon: isRunning 
                    ? const SizedBox(
                        width: 16,
                        height: 16,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.play_arrow),
                label: Text(isRunning ? 'Running...' : 'Start'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: color,
                  foregroundColor: Colors.white,
                ),
              ),
              const SizedBox(width: 8),
              ElevatedButton.icon(
                onPressed: isRunning ? onStop : null,
                icon: const Icon(Icons.stop),
                label: const Text('Stop'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.grey,
                  foregroundColor: Colors.white,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildResultRow(String label, String value, IconData icon, Color color) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Row(
        children: [
          Icon(icon, size: 16, color: color),
          const SizedBox(width: 8),
          Text(
            label,
            style: const TextStyle(fontWeight: FontWeight.w500),
          ),
          const Spacer(),
          Text(
            value,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildResultRowWithBadge(String label, String value, IconData icon, Color color, {bool showTestBadge = false}) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Row(
        children: [
          Icon(icon, size: 16, color: color),
          const SizedBox(width: 8),
          Text(
            label,
            style: const TextStyle(fontWeight: FontWeight.w500),
          ),
          const Spacer(),
          Text(
            value,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
          if (showTestBadge) ...[
            const SizedBox(width: 8),
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
              decoration: BoxDecoration(
                color: Colors.green,
                borderRadius: BorderRadius.circular(10),
              ),
              child: const Text(
                'TEST',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 10,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ],
        ],
      ),
    );
  }

  // Test Methods
  Future<void> _startSpO2Test() async {
    setState(() {
      _isSpo2Testing = true;
      // Reset del risultato congelato per nuovo test
      _manualSpO2Result = null;
    });
    
    try {
      // Usa il comando che funziona per il LED rosso (come nel test LED)
      await widget.extendedService.measureSpO2();
      debugPrint('🩸 SpO2 measurement started - LED rosso dovrebbe accendersi');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test SpO2 WatchFit avviato - LED ROSSO acceso (50 sec max, stop anticipato con 2 letture valide)'),
            backgroundColor: Colors.blue,
            duration: Duration(seconds: 5),
          ),
        );
      }
    } catch (e) {
      debugPrint('Error starting SpO2 test: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore avvio SpO2: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      setState(() => _isSpo2Testing = false);
    }
  }

  Future<void> _stopSpO2Test() async {
    setState(() => _isSpo2Testing = true);
    
    try {
      // Usa il comando ufficiale 0x37 per fermare la misurazione SpO2
      await widget.extendedService.stopSpO2Measurement();
      debugPrint('🛑 SpO2 measurement stopped with official command 0x37');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test SpO2 fermato - LED rosso spento, ritorna verde'),
            backgroundColor: Colors.orange,
          ),
        );
      }
    } catch (e) {
      debugPrint('Error stopping SpO2 test: $e');
    } finally {
      setState(() => _isSpo2Testing = false);
    }
  }

  Future<void> _startHRVTest() async {
    setState(() {
      _isHRVTesting = true;
      // Reset del risultato congelato per nuovo test
      _manualHRVResult = null;
    });
    
    try {
      // Sottoscrivi al flusso HRV in tempo reale invece di richiedere dati storici
      _hrvSubscription?.cancel();
      List<HRVData> hrvSamples = [];
      
      _hrvSubscription = widget.extendedService.hrvDataStream.listen((hrvData) {
        if (_isHRVTesting && hrvData.isDataValid) {
          hrvSamples.add(hrvData);
          // Aggiorna l'ultimo risultato valido durante il test
          _manualHRVResult = hrvData;
          setState(() {});
        }
      });
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test HRV avviato - Monitoraggio variabilità cardiaca in tempo reale per 15 secondi'),
            backgroundColor: Colors.red,
            duration: Duration(seconds: 4),
          ),
        );
      }
      
      // Timer per auto-completamento HRV dopo 15 secondi
      Timer(const Duration(seconds: 15), () {
        if (mounted && _isHRVTesting) {
          _hrvSubscription?.cancel();
          setState(() => _isHRVTesting = false);
          
          final hrvResult = _manualHRVResult;
          String message;
          if (hrvResult != null && hrvSamples.isNotEmpty) {
            message = 'Test HRV completato! RMSSD: ${hrvResult.rmssd.toStringAsFixed(1)}ms (${hrvResult.hrvQuality}) - ${hrvSamples.length} campioni';
          } else {
            message = 'Test HRV completato ma nessun dato valido ricevuto - verifica il posizionamento del dispositivo';
          }
          
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(message),
              backgroundColor: hrvResult != null ? Colors.green : Colors.orange,
              duration: const Duration(seconds: 4),
            ),
          );
        }
      });
      
    } catch (e) {
      debugPrint('Error starting HRV test: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore avvio HRV: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  Future<void> _stopHRVTest() async {
    setState(() => _isHRVTesting = false);
  }

  Future<void> _startTemperatureTest() async {
    setState(() {
      _isTempTesting = true;
      // Reset del risultato congelato per nuovo test
      _manualTempResult = null;
    });
    
    try {
      await widget.extendedService.requestTemperature();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test temperatura avviato - Lettura sensori termici in corso (10 sec)'),
            backgroundColor: Colors.orange,
            duration: Duration(seconds: 3),
          ),
        );
      }
      
      // Timer per auto-completamento temperatura dopo 10 secondi
      Timer(const Duration(seconds: 10), () {
        if (mounted && _isTempTesting) {
          setState(() => _isTempTesting = false);
          
          final tempResult = _manualTempResult;
          String message;
          if (tempResult != null) {
            message = 'Test temperatura completato! Corporea: ${tempResult.bodyTempC.toStringAsFixed(1)}°C';
          } else {
            message = 'Test temperatura completato - verifica i risultati sopra';
          }
          
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(message),
              backgroundColor: Colors.green,
              duration: const Duration(seconds: 4),
            ),
          );
        }
      });
      
    } catch (e) {
      debugPrint('Error starting temperature test: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore avvio temperatura: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  Future<void> _stopTemperatureTest() async {
    setState(() => _isTempTesting = false);
  }

  Future<void> _resetDevice() async {
    setState(() => _isResetting = true);
    
    try {
      await widget.extendedService.deviceReset();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Device reset completato'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      debugPrint('Error resetting device: $e');
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

  Future<void> _testLED() async {
    setState(() => _isTestingLED = true);
    
    try {
      await widget.extendedService.testLEDFunctionality();
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test LED SpO2 completato - controlla il LED rosso sul dispositivo'),
            backgroundColor: Colors.blue,
          ),
        );
      }
    } catch (e) {
      debugPrint('Error testing LED: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore test LED: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      setState(() => _isTestingLED = false);
    }
  }

  // Export dei dati di oggi
  Future<void> _exportTodayData() async {
    try {
      final today = DateTime.now();
      final todayResults = await ManualTestStorage.getResultsByDate(today);
      
      if (todayResults.isEmpty) {
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Nessun dato disponibile per oggi'),
              backgroundColor: Colors.orange,
            ),
          );
        }
        return;
      }
      
      final dateStr = '${today.year}-${today.month.toString().padLeft(2, '0')}-${today.day.toString().padLeft(2, '0')}';
      final dailyData = DailyTestResults(
        date: dateStr,
        results: todayResults,
      );
      
      final jsonString = const JsonEncoder.withIndent('  ').convert(dailyData.toJson());
      debugPrint('📤 Export oggi (${todayResults.length} risultati):\n$jsonString');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Export completato: ${todayResults.length} risultati'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      debugPrint('❌ Error exporting today data: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore export: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  // Export di tutti i dati
  Future<void> _exportAllData() async {
    try {
      final allResults = await ManualTestStorage.getAllResults();
      
      if (allResults.isEmpty) {
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Nessun dato disponibile'),
              backgroundColor: Colors.orange,
            ),
          );
        }
        return;
      }
      
      // Raggruppa per giorno
      final dailyGroups = DailyTestResults.groupByDay(allResults);
      final exportData = {
        'exportDate': DateTime.now().toIso8601String(),
        'totalResults': allResults.length,
        'dailyData': dailyGroups.map((daily) => daily.toJson()).toList(),
      };
      
      final jsonString = const JsonEncoder.withIndent('  ').convert(exportData);
      debugPrint('📤 Export completo (${allResults.length} risultati):\n$jsonString');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Export completato: ${allResults.length} risultati'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      debugPrint('❌ Error exporting all data: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore export: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }
}
