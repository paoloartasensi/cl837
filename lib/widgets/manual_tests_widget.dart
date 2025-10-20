import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:share_plus/share_plus.dart';
import 'package:path_provider/path_provider.dart';
import '../chileaf_extended_service.dart';
import '../hrv_session_service.dart';
import '../models/spo2_data.dart';
import '../models/temperature_data.dart';
import '../models/hrv_data.dart';
import '../models/heart_rate_data.dart';
import '../models/manual_test_result.dart';
import '../services/manual_test_storage.dart';

class ManualTestsWidget extends StatefulWidget {
  final ChileafExtendedService extendedService;
  final HRVSessionService? hrvService;
  final Stream<HeartRateData?>? heartRateStream; // Stream principale per RR intervals

  const ManualTestsWidget({
    Key? key,
    required this.extendedService,
    this.hrvService,
    this.heartRateStream, // Accesso diretto al stream del heart rate
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
  // bool _isTestingLED = false;

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
  StreamSubscription<HeartRateData?>? _heartRateSubscription; // Subscription al main heart rate stream

  // Ultimi dati raccolti per HRV dal main heart rate stream
  final List<double> _collectedRRIntervals = [];

  @override
  void initState() {
    super.initState();
    _setupDataStreams();
    
    // Imposta i callback per il nuovo sistema SpO2 (logica Android)
    widget.extendedService.setSpO2Callbacks(
      onValueReceived: (value) {
        // Callback Android: str > "0" → pause = true
        debugPrint('📊 SpO2 callback received: str="$value" (Android equivalent)');
        
        // Update UI automatico (equivalente a mTxtBloodOxygenValue.setText(str + "%"))
        scheduleMicrotask(() {
          if (mounted && _isSpo2Testing) {
            // Il valore congelato viene aggiornato automaticamente dal stream
            debugPrint('✅ SpO2 value triggered pause in Android logic: $value%');
          }
        });
      },
      onComplete: () {
        // Usa scheduleMicrotask per evitare conflitti UI
        scheduleMicrotask(() {
          if (mounted) {
            _onSpO2AutoCompleted();
          }
        });
      },
      onError: (error) {
        debugPrint('SpO2 error: $error');
        // Usa scheduleMicrotask per gli errori
        scheduleMicrotask(() {
          if (mounted) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                content: Text('Errore SpO2: $error'), 
                backgroundColor: Colors.red,
                duration: const Duration(seconds: 3),
              ),
            );
          }
        });
      },
    );
  }

  @override
  void dispose() {
    _spo2Subscription?.cancel();
    _tempSubscription?.cancel();
    _hrvSubscription?.cancel();
    _heartRateSubscription?.cancel();
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

    // Ascolta i risultati HRV dal servizio esteso (legacy)
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

    // Ascolta il main heart rate stream per accesso diretto ai RR intervals
    if (widget.heartRateStream != null) {
      _heartRateSubscription = widget.heartRateStream!.listen((heartRateData) {
        if (mounted && _isHRVTesting && heartRateData != null && heartRateData.rrIntervals != null) {
          debugPrint('💓 Main HR Stream - RR intervals: ${heartRateData.rrIntervals!.length} found');
          
          // Raccogli RR intervals durante il test HRV
          _collectedRRIntervals.addAll(heartRateData.rrIntervals!);
          
          // Se abbiamo abbastanza dati per 1 minuto (almeno 50 RR intervals per essere sicuri)
          if (_collectedRRIntervals.length >= 50) {
            debugPrint('🎯 Processing ${_collectedRRIntervals.length} collected RR intervals for HRV calculation');
            
            // Crea HRVData dai RR intervals raccolti
            final hrvData = HRVData(
              rrIntervals: List.from(_collectedRRIntervals),
              timestamp: DateTime.now(),
            );
            
            setState(() {
              _latestHRVResult = hrvData;
              _manualHRVResult = hrvData;
            });
            
            // Salva il risultato
            _saveTestResult(ManualTestResult.fromHRV(hrvData, notes: 'Test manuale HRV da main HR stream'));
            
            debugPrint('✅ HRV calculated from main stream: RMSSD=${hrvData.rmssd.toStringAsFixed(1)}ms, SDNN=${hrvData.sdnn.toStringAsFixed(1)}ms');
          }
        }
      });
    }
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

  // Gestisce il completamento automatico del test SpO2 (Android logic)
  // Equivalente a: waveView.stop() + mBtnRetry.setVisibility(0)
  void _onSpO2AutoCompleted() {
    // Evita doppi aggiornamenti di stato
    if (!_isSpo2Testing) return;
    
    setState(() => _isSpo2Testing = false);
    
    // Usa Future.microtask per evitare conflitti con altri setState
    Future.microtask(() {
      if (!mounted) return;
      
      // Usa il risultato congelato del test manuale (Android: pause = true)
      final spo2Value = _manualSpO2Result?.spo2Value;
      final quality = _manualSpO2Result?.signalQuality ?? 0;
      
      String message;
      if (spo2Value != null && spo2Value > 0) {
        message = 'SpO2 completato! Risultato: $spo2Value% (qualità segnale: $quality/100)';
      } else {
        message = 'SpO2 completato dopo 60 sec o con lettura valida. Controlla i risultati sopra.';
      }
      
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(message),
          backgroundColor: Colors.green,
          duration: const Duration(seconds: 4),
          action: _manualSpO2Result != null ? SnackBarAction(
            label: 'Dettagli',
            textColor: Colors.white,
            onPressed: () {
              // Usa Future per evitare conflitti UI
              Future.delayed(const Duration(milliseconds: 100), () {
                if (mounted) _showSpO2Details();
              });
            },
          ) : null,
        ),
      );
    });
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

  // Mostra le istruzioni per il test HRV guidato
  Future<bool> _showHRVInstructions() async {
    final result = await showDialog<bool>(
      context: context,
      barrierDismissible: false,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            Icon(Icons.favorite, color: Colors.red.shade600),
            const SizedBox(width: 8),
            const Text('Test HRV Guidato'),
          ],
        ),
        content: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.blue.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.blue.shade200),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Condizioni Ottimali per HRV:',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.blue.shade700,
                      ),
                    ),
                    const SizedBox(height: 8),
                    const Text('• Preferibilmente al mattino dopo il risveglio'),
                    const Text('• Posizione seduta o sdraiata, rilassata'),
                    const Text('• Respirazione naturale e tranquilla'),
                    const Text('• Evitare movimenti bruschi'),
                    const Text('• Dispositivo ben posizionato al polso'),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.orange.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.orange.shade200),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Durante il Test (1 minuto minimo):',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.orange.shade700,
                      ),
                    ),
                    const SizedBox(height: 8),
                    const Text('• Rimani fermo e rilassato per almeno 1 minuto'),
                    const Text('• Respira normalmente e profondamente'),
                    const Text('• Non parlare o muoverti durante il test'),
                    const Text('• Concentrati su un respiro tranquillo'),
                    const Text('• Standard clinico: minimo 60 secondi di dati'),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.green.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.green.shade200),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Interpretazione Risultati:',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.green.shade700,
                      ),
                    ),
                    const SizedBox(height: 8),
                    const Text('• RMSSD >50ms: Buona forma fisica'),
                    const Text('• RMSSD 30-50ms: Nella media'),
                    const Text('• RMSSD <30ms: Possibile stress/fatica'),
                    const Text('• pNN50 >15%: Ottima variabilità'),
                    const Text('• pNN50 5-15%: Variabilità normale'),
                    const Text('• pNN50 <5%: Bassa variabilità'),
                    const Text('• Meglio al mattino per valutazione recovery'),
                  ],
                ),
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text('Annulla'),
          ),
          ElevatedButton.icon(
            onPressed: () => Navigator.of(context).pop(true),
            icon: const Icon(Icons.play_arrow),
            label: const Text('Inizia Test'),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.red,
              foregroundColor: Colors.white,
            ),
          ),
        ],
      ),
    );
    
    return result ?? false;
  }

  // Mostra l'interpretazione dettagliata dei risultati HRV
  void _showHRVInterpretation(HRVData hrvResult) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            Icon(Icons.analytics, color: Colors.red.shade600),
            const SizedBox(width: 8),
            const Text('Analisi HRV Completa'),
          ],
        ),
        content: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Risultati principali
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.blue.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.blue.shade200),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Metriche HRV:',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.blue.shade700,
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text('RMSSD: ${hrvResult.rmssd.toStringAsFixed(1)} ms (${hrvResult.hrvQuality})'),
                    Text('SDNN: ${hrvResult.sdnn.toStringAsFixed(1)} ms'),
                    Text('pNN50: ${hrvResult.pNN50.toStringAsFixed(1)}%'),
                    Text('FC Media: ${hrvResult.estimatedHR.toStringAsFixed(0)} BPM (${hrvResult.hrCategory})'),
                    Text('RR Intervals: ${hrvResult.rrIntervals.length} campioni'),
                    Text('Dati validi: ${hrvResult.isDataValid ? "Sì" : "No"}'),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              
              // Interpretazione clinica
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: _getHRVInterpretationColor(hrvResult.rmssd).withOpacity(0.1),
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: _getHRVInterpretationColor(hrvResult.rmssd).withOpacity(0.3)),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Interpretazione Clinica:',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: _getHRVInterpretationColor(hrvResult.rmssd),
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text(_getHRVDetailedInterpretation(hrvResult.rmssd)),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              
              // Raccomandazioni
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.green.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.green.shade200),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Raccomandazioni:',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.green.shade700,
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text(_getHRVRecommendations(hrvResult.rmssd)),
                  ],
                ),
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

  // Colore basato sul valore RMSSD
  Color _getHRVInterpretationColor(double rmssd) {
    if (rmssd < 15) return Colors.red;
    if (rmssd < 30) return Colors.orange;
    if (rmssd < 50) return Colors.yellow.shade700;
    if (rmssd < 70) return Colors.green;
    return Colors.blue;
  }

  // Interpretazione dettagliata basata su RMSSD
  String _getHRVDetailedInterpretation(double rmssd) {
    if (rmssd < 15) {
      return 'Variabilità cardiaca molto bassa. Potrebbe indicare stress severo, sovrallenamento, malattia o recupero insufficiente. Considera una valutazione medica.';
    } else if (rmssd < 30) {
      return 'Variabilità cardiaca sotto la media. Possibile presenza di stress, affaticamento o necessità di maggior recupero. Monitora nei prossimi giorni.';
    } else if (rmssd < 50) {
      return 'Variabilità cardiaca nella media normale. Buono stato generale, ma c\'è margine per miglioramenti attraverso tecniche di rilassamento.';
    } else if (rmssd < 70) {
      return 'Ottima variabilità cardiaca! Indica buona forma fisica, basso stress e buon recupero. Continua con le attuali abitudini.';
    } else {
      return 'Eccellente variabilità cardiaca! Indica forma fisica eccellente, sistema nervoso molto ben equilibrato. Ideale per atleti ben allenati.';
    }
  }

  // Raccomandazioni basate su RMSSD
  String _getHRVRecommendations(double rmssd) {
    if (rmssd < 15) {
      return '• Riposo e recupero prioritari\n• Evitare allenamenti intensi\n• Considerare tecniche di rilassamento\n• Valutazione medica consigliata';
    } else if (rmssd < 30) {
      return '• Aumentare il riposo\n• Allenamento leggero/moderato\n• Tecniche di respirazione\n• Monitoraggio quotidiano';
    } else if (rmssd < 50) {
      return '• Mantenere routine attuale\n• Aggiungere meditazione/yoga\n• Allenamento regolare\n• Test HRV mattutini';
    } else if (rmssd < 70) {
      return '• Ottimo stato: mantieni le abitudini\n• Allenamento intenso OK\n• Continua monitoraggio HRV\n• Ottimizza il sonno';
    } else {
      return '• Eccellente! Mantieni il livello\n• Allenamento ad alta intensità OK\n• Condividi la tua routine\n• Monitoraggio per performance';
    }
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
              subtitle: 'Saturazione ossigeno (comando 55 - alto livello)',
              icon: Icons.opacity,
              color: Colors.blue,
              isRunning: _isSpo2Testing,
              onStart: _startSpO2Test,
              onStop: _stopSpO2Test,
            ),
            
            const SizedBox(height: 12),
            
            // HRV Test Section
            _buildTestSection(
              title: 'Test HRV Guidato',
              subtitle: 'Variabilità cardiaca (1 min minimo)',
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
              subtitle: 'Temperatura corporea (10s)',
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
                      // Expanded(
                      //   child: ElevatedButton.icon(
                      //     onPressed: _isTestingLED ? null : _testLED,
                      //     icon: _isTestingLED 
                      //         ? const SizedBox(
                      //             width: 16,
                      //             height: 16,
                      //             child: CircularProgressIndicator(strokeWidth: 2),
                      //           )
                      //         : const Icon(Icons.lightbulb),
                      //     label: Text(_isTestingLED ? 'Testing...' : 'Test LED SpO2'),
                      //     style: ElevatedButton.styleFrom(
                      //       backgroundColor: Colors.blue.shade400,
                      //       foregroundColor: Colors.white,
                      //     ),
                      //   ),
                      // ),
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
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      title,
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w600,
                        color: color,
                      ),
                      overflow: TextOverflow.ellipsis,
                    ),
                    Text(
                      subtitle,
                      style: TextStyle(
                        fontSize: 12,
                        color: color.withOpacity(0.8),
                      ),
                      overflow: TextOverflow.ellipsis,
                      maxLines: 2,
                    ),
                  ],
                ),
              ),
            ],
          ),
          const SizedBox(height: 8),
          Row(
            children: [
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: isRunning ? null : onStart,
                  icon: isRunning 
                      ? const SizedBox(
                          width: 16,
                          height: 16,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        )
                      : const Icon(Icons.play_arrow, size: 16),
                  label: Text(
                    isRunning ? 'Running...' : 'Start',
                    style: const TextStyle(fontSize: 12),
                  ),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: color,
                    foregroundColor: Colors.white,
                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                  ),
                ),
              ),
              const SizedBox(width: 8),
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: isRunning ? onStop : null,
                  icon: const Icon(Icons.stop, size: 16),
                  label: const Text(
                    'Stop',
                    style: TextStyle(fontSize: 12),
                  ),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.grey,
                    foregroundColor: Colors.white,
                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                  ),
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

  // ===== TEST METHODS =====
  
  /// Avvia il test SpO2 usando comando di alto livello Android-compatibile
  Future<void> _startSpO2Test() async {
    // Evita avvii multipli
    if (_isSpo2Testing) return;
    
    setState(() {
      _isSpo2Testing = true;
      // Reset del risultato congelato per nuovo test
      _manualSpO2Result = null;
    });
    
    try {
      // CRITICAL: Imposta i callback PRIMA di avviare la misurazione (come Android app)
      widget.extendedService.setSpO2Callbacks(
        onValueReceived: (spo2Value) {
          debugPrint('🫁 SpO2 VALUE RECEIVED: $spo2Value%');
          if (mounted) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                content: Text('SpO2 misurato: $spo2Value%'),
                backgroundColor: Colors.green,
                duration: const Duration(seconds: 3),
              ),
            );
          }
        },
        onComplete: () {
          debugPrint('✅ SpO2 measurement completed');
          if (mounted) {
            setState(() => _isSpo2Testing = false);
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(
                content: Text('Misurazione SpO2 completata'),
                backgroundColor: Colors.blue,
                duration: Duration(seconds: 2),
              ),
            );
          }
        },
        onError: (error) {
          debugPrint('❌ SpO2 measurement error: $error');
          if (mounted) {
            setState(() => _isSpo2Testing = false);
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                content: Text('Errore SpO2: $error'),
                backgroundColor: Colors.red,
                duration: const Duration(seconds: 3),
              ),
            );
          }
        },
      );
      
      // Invia comando di alto livello 55 (0x37) - il dispositivo gestisce autonomamente i LED
      await widget.extendedService.startBloodOxygenMeasurement();
      debugPrint('🩸 Completed');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test SpO2 avviato - Comando 55 inviato (dispositivo controlla LED autonomamente)'),
            backgroundColor: Colors.blue,
            duration: Duration(seconds: 4),
          ),
        );
      }
    } catch (e) {
      debugPrint('Error starting SpO2 test: $e');
      setState(() => _isSpo2Testing = false);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Errore avvio SpO2: $e'),
            backgroundColor: Colors.red,
            duration: const Duration(seconds: 3),
          ),
        );
      }
    }
  }

  /// Ferma il test SpO2
  Future<void> _stopSpO2Test() async {
    // Evita chiamate multiple
    if (!_isSpo2Testing) return;
    
    setState(() => _isSpo2Testing = true); // Mostra loading durante stop
    
    try {
      // Usa il nuovo sistema SpO2 per fermare la misurazione
      await widget.extendedService.stopBloodOxygenMeasurement();
      debugPrint('🛑 SpO2 measurement stopped using Android-compatible system');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test SpO2 fermato manualmente'),
            backgroundColor: Colors.orange,
            duration: Duration(seconds: 3),
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
    // Mostra prima le istruzioni guidate per il test HRV
    final shouldProceed = await _showHRVInstructions();
    if (!shouldProceed) return;
    
    setState(() {
      _isHRVTesting = true;
      // Reset del risultato congelato per nuovo test
      _manualHRVResult = null;
    });
    
    // RESET della collezione RR intervals per nuovo test
    _collectedRRIntervals.clear();
    
    try {
      // Attiva l'allarme heart rate per il monitoraggio continuo (assicura che i dati fluiscano)
      await widget.extendedService.setHeartRateAlarm(true);
      
      // Attendi che il dispositivo inizi il monitoraggio
      await Future.delayed(const Duration(milliseconds: 1000));
      
      debugPrint('🔍 Starting HRV test - monitoring main HR stream for RR intervals...');
      debugPrint('� RR intervals saranno raccolti dal main stream (stesso usato in SENSORI)');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Test HRV avviato - Raccogliendo RR intervals dal main stream... Rimani rilassato'),
            backgroundColor: Colors.red,
            duration: Duration(seconds: 4),
          ),
        );
      }
      
      // Timer per auto-completamento HRV dopo 15 secondi
      Timer(const Duration(seconds: 15), () async {
        if (mounted && _isHRVTesting) {
          await widget.extendedService.setHeartRateAlarm(false);
          setState(() => _isHRVTesting = false);
          
          final hrvResult = _manualHRVResult;
          String message;
          if (hrvResult != null && _collectedRRIntervals.length >= 50) {
            message = 'Test HRV completato! RMSSD: ${hrvResult.rmssd.toStringAsFixed(1)}ms (${hrvResult.hrvQuality}) - ${_collectedRRIntervals.length} RR intervals (${hrvResult.samplingDurationFormatted})';
          } else if (_collectedRRIntervals.isNotEmpty) {
            message = 'Test HRV completato con ${_collectedRRIntervals.length} RR intervals (minimo 50 per 1 minuto clinico). Continua per risultati più accurati.';
          } else {
            message = 'Test HRV completato ma nessun RR interval ricevuto dal main stream. Verifica il posizionamento del dispositivo e riprova.';
          }
          
          if (mounted) {
            ScaffoldMessenger.of(context).clearSnackBars();
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                content: Text(message),
                backgroundColor: (hrvResult != null && _collectedRRIntervals.length >= 50) ? Colors.green : Colors.orange,
                duration: const Duration(seconds: 6),
                action: hrvResult != null ? SnackBarAction(
                  label: 'Dettagli',
                  textColor: Colors.white,
                  onPressed: () => _showHRVInterpretation(hrvResult),
                ) : null,
              ),
            );
          }
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


  // Export dei dati di oggi
  // Export dei dati di oggi con salvataggio e condivisione
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
      
      // Salva file JSON
      try {
        final directory = await getApplicationDocumentsDirectory();
        final fileName = 'CL837_Daily_Export_$dateStr.json';
        final file = File('${directory.path}/$fileName');
        await file.writeAsString(jsonString);
        
        debugPrint('� File salvato: ${file.path}');
        
        // Condividi il file
        await Share.shareXFiles(
          [XFile(file.path)],
          text: 'Export dati CL837 del $dateStr - ${todayResults.length} test',
          subject: 'CL837 Export Giornaliero $dateStr',
        );
        
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Export salvato e condiviso: ${todayResults.length} risultati'),
              backgroundColor: Colors.green,
              duration: const Duration(seconds: 4),
            ),
          );
        }
      } catch (e) {
        // Fallback: solo log se la condivisione fallisce
        debugPrint('�📤 Export oggi (${todayResults.length} risultati):\n$jsonString');
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Export nei log: ${todayResults.length} risultati'),
              backgroundColor: Colors.green,
            ),
          );
        }
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

  // Export di tutti i dati con salvataggio e condivisione
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
      final now = DateTime.now();
      final timestampStr = '${now.year}-${now.month.toString().padLeft(2, '0')}-${now.day.toString().padLeft(2, '0')}_${now.hour.toString().padLeft(2, '0')}-${now.minute.toString().padLeft(2, '0')}';
      
      final exportData = {
        'exportDate': now.toIso8601String(),
        'exportTimestamp': timestampStr,
        'totalResults': allResults.length,
        'totalDays': dailyGroups.length,
        'dailyData': dailyGroups.map((daily) => daily.toJson()).toList(),
      };
      
      final jsonString = const JsonEncoder.withIndent('  ').convert(exportData);
      
      // Salva file JSON
      try {
        final directory = await getApplicationDocumentsDirectory();
        final fileName = 'CL837_Complete_Export_$timestampStr.json';
        final file = File('${directory.path}/$fileName');
        await file.writeAsString(jsonString);
        
        debugPrint('� File completo salvato: ${file.path}');
        
        // Condividi il file
        await Share.shareXFiles(
          [XFile(file.path)],
          text: 'Export completo dati CL837 - ${allResults.length} test totali in ${dailyGroups.length} giorni',
          subject: 'CL837 Export Completo $timestampStr',
        );
        
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Export completo salvato e condiviso:\n${allResults.length} risultati, ${dailyGroups.length} giorni'),
              backgroundColor: Colors.green,
              duration: const Duration(seconds: 5),
            ),
          );
        }
      } catch (e) {
        // Fallback: solo log se la condivisione fallisce
        debugPrint('📤 Export completo (${allResults.length} risultati):\n$jsonString');
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Export nei log: ${allResults.length} risultati'),
              backgroundColor: Colors.green,
            ),
          );
        }
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
