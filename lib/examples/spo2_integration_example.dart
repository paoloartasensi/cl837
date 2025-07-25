// NOTA: Questo è un esempio di codice per dimostrare l'integrazione del sistema SpO2
// Per un'implementazione completa, aggiungere i seguenti import:
// import 'package:flutter/material.dart';
// import 'package:flutter/foundation.dart';
// import '../widgets/spo2_display_widget.dart';

/*
Esempio di integrazione del nuovo sistema SpO2 in ChileafExtendedService

class ChileafExtendedService {
  late final SpO2Service _spO2Service;
  
  ChileafExtendedService() {
    _spO2Service = SpO2Service();
  }

  void _handleReceivedData(List<int> data) {
    if (data.length >= 8 && data[2] == 55) { // Comando 0x37
      _spO2Service.parseSpO2Data(data);
    }
    // ... altri comandi
  }
  
  Stream<SpO2Data> get spO2Stream => _spO2Service.spO2Stream;
  Stream<String> get spO2AlertStream => _spO2Service.alertStream;
  
  SpO2Statistics? getSpO2Statistics() => _spO2Service.getRecentStatistics();
  void clearSpO2History() => _spO2Service.clearHistory();
  
  void dispose() {
    _spO2Service.dispose();
    super.dispose();
  }
}

// Utilizzo nel widget:
// 
// SpO2DisplayWidget(
//   spO2Service: chileafService.spO2Service,
// )
//
// // Gestione allarmi:
// chileafService.spO2AlertStream.listen((alert) {
//   if (alert.contains('CRITICO')) {
//     showCriticalHealthAlert(alert);
//   } else {
//     showHealthWarning(alert);
//   }
// });

*/
