import '../models/spo2_data.dart';
import '../models/temperature_data.dart';
import '../models/hrv_data.dart';

enum TestType { spo2, temperature, hrv }

class ManualTestResult {
  final String id;
  final TestType testType;
  final DateTime timestamp;
  final Map<String, dynamic> data;
  final String? notes;

  ManualTestResult({
    required this.id,
    required this.testType,
    required this.timestamp,
    required this.data,
    this.notes,
  });

  // Factory constructors per ogni tipo di test
  factory ManualTestResult.fromSpO2(SpO2Data spo2Data, {String? notes}) {
    return ManualTestResult(
      id: '${DateTime.now().millisecondsSinceEpoch}_spo2',
      testType: TestType.spo2,
      timestamp: spo2Data.timestamp,
      data: {
        'spo2Value': spo2Data.spo2Value,
        'signalQuality': spo2Data.signalQuality,
        'signalQualityDescription': spo2Data.signalQualityDescription,
        'correctWristPosture': spo2Data.correctWristPosture,
        'isWearing': spo2Data.isWearing,
        'isValidMeasurement': spo2Data.isValidMeasurement,
        'isDeviceReady': spo2Data.isDeviceReady,
      },
      notes: notes,
    );
  }

  factory ManualTestResult.fromTemperature(TemperatureData tempData, {String? notes}) {
    return ManualTestResult(
      id: '${DateTime.now().millisecondsSinceEpoch}_temp',
      testType: TestType.temperature,
      timestamp: tempData.timestamp,
      data: {
        'ambientTempC': tempData.ambientTempC,
        'wristTempC': tempData.wristTempC,
        'bodyTempC': tempData.bodyTempC,
        'ambientTempF': tempData.ambientTempF,
        'wristTempF': tempData.wristTempF,
        'bodyTempF': tempData.bodyTempF,
      },
      notes: notes,
    );
  }

  factory ManualTestResult.fromHRV(HRVData hrvData, {String? notes}) {
    return ManualTestResult(
      id: '${DateTime.now().millisecondsSinceEpoch}_hrv',
      testType: TestType.hrv,
      timestamp: hrvData.timestamp,
      data: {
        'rmssd': hrvData.rmssd.isFinite ? hrvData.rmssd : 0.0,
        'sdnn': hrvData.sdnn.isFinite ? hrvData.sdnn : 0.0,
        'meanRR': hrvData.meanRR.isFinite ? hrvData.meanRR : 0.0,
        'medianRR': hrvData.medianRR.isFinite ? hrvData.medianRR : 0.0,
        'estimatedHR': hrvData.estimatedHR.isFinite ? hrvData.estimatedHR : 0.0,
        'rrIntervalsCount': hrvData.rrIntervals.length,
        'rrIntervals': hrvData.rrIntervals.where((interval) => interval.isFinite).toList(), // Filtra valori non finiti
        'hrvQuality': hrvData.hrvQuality,
        'hrCategory': hrvData.hrCategory,
        'isDataValid': hrvData.isDataValid,
      },
      notes: notes,
    );
  }

  // Serializzazione JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'testType': testType.toString().split('.').last,
      'timestamp': timestamp.toIso8601String(),
      'data': data,
      'notes': notes,
    };
  }

  factory ManualTestResult.fromJson(Map<String, dynamic> json) {
    return ManualTestResult(
      id: json['id'],
      testType: TestType.values.firstWhere(
        (type) => type.toString().split('.').last == json['testType'],
      ),
      timestamp: DateTime.parse(json['timestamp']),
      data: Map<String, dynamic>.from(json['data']),
      notes: json['notes'],
    );
  }

  // Getters per accesso facile ai dati
  String get testTypeDisplayName {
    switch (testType) {
      case TestType.spo2:
        return 'SpO2';
      case TestType.temperature:
        return 'Temperatura';
      case TestType.hrv:
        return 'HRV';
    }
  }

  String get formattedTimestamp {
    return '${timestamp.day.toString().padLeft(2, '0')}/'
           '${timestamp.month.toString().padLeft(2, '0')}/'
           '${timestamp.year} '
           '${timestamp.hour.toString().padLeft(2, '0')}:'
           '${timestamp.minute.toString().padLeft(2, '0')}:'
           '${timestamp.second.toString().padLeft(2, '0')}';
  }

  String get dayKey {
    return '${timestamp.year}-${timestamp.month.toString().padLeft(2, '0')}-${timestamp.day.toString().padLeft(2, '0')}';
  }

  @override
  String toString() {
    return 'ManualTestResult($testTypeDisplayName, $formattedTimestamp)';
  }
}

// Classe per raggruppare i risultati per giorno
class DailyTestResults {
  final String date; // YYYY-MM-DD
  final List<ManualTestResult> results;

  DailyTestResults({
    required this.date,
    required this.results,
  });

  Map<String, dynamic> toJson() {
    return {
      'date': date,
      'results': results.map((r) => r.toJson()).toList(),
    };
  }

  factory DailyTestResults.fromJson(Map<String, dynamic> json) {
    return DailyTestResults(
      date: json['date'],
      results: (json['results'] as List)
          .map((r) => ManualTestResult.fromJson(r))
          .toList(),
    );
  }

  int get totalTests => results.length;
  
  int get spo2Tests => results.where((r) => r.testType == TestType.spo2).length;
  int get temperatureTests => results.where((r) => r.testType == TestType.temperature).length;
  int get hrvTests => results.where((r) => r.testType == TestType.hrv).length;
  
  // Raggruppa i risultati per giorno
  static List<DailyTestResults> groupByDay(List<ManualTestResult> results) {
    final Map<String, List<ManualTestResult>> dayGroups = {};
    
    for (final result in results) {
      final dateKey = '${result.timestamp.year}-${result.timestamp.month.toString().padLeft(2, '0')}-${result.timestamp.day.toString().padLeft(2, '0')}';
      
      if (!dayGroups.containsKey(dateKey)) {
        dayGroups[dateKey] = [];
      }
      dayGroups[dateKey]!.add(result);
    }
    
    final dailyResults = <DailyTestResults>[];
    for (final entry in dayGroups.entries) {
      dailyResults.add(DailyTestResults(
        date: entry.key,
        results: entry.value,
      ));
    }
    
    // Ordina per data (più recenti prima)
    dailyResults.sort((a, b) => b.date.compareTo(a.date));
    return dailyResults;
  }
}
