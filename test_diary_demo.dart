import 'package:flutter/material.dart';
import 'lib/widgets/test_diary_widget.dart';
import 'lib/services/test_diary_service.dart';
import 'lib/models/test_record.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Aggiungi alcuni dati di test
  await _addSampleData();
  
  runApp(const TestDiaryApp());
}

Future<void> _addSampleData() async {
  final diaryService = TestDiaryService.instance;
  
  // Aggiungi alcuni record di test se il diario è vuoto
  final existingRecords = await diaryService.getAllRecords();
  if (existingRecords.isEmpty) {
    // Heart Rate test
    final hrRecord = TestRecord.fromHeartRate(75, [800, 820, 810, 790]);
    await diaryService.saveTestRecord(hrRecord);
    
    // SpO2 test
    final spo2Record = TestRecord.fromSpO2(98, 85, 'Good');
    await diaryService.saveTestRecord(spo2Record);
    
    // Temperature test
    final tempRecord = TestRecord.fromTemperature(25.5, 32.1, 36.7);
    await diaryService.saveTestRecord(tempRecord);
    
    // Sports test
    final sportsRecord = TestRecord.fromSports(1250, 85000, 42.5);
    await diaryService.saveTestRecord(sportsRecord);
    
    // Rope skipping test
    final ropeRecord = TestRecord.fromRopeSkipping('Counter', 150, 120, 8.3);
    await diaryService.saveTestRecord(ropeRecord);
    
    // Battery test
    final batteryRecord = TestRecord.fromBattery(85, false, 3850);
    await diaryService.saveTestRecord(batteryRecord);
  }
}

class TestDiaryApp extends StatelessWidget {
  const TestDiaryApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'CL837 Test Diary',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: const Text('📝 CL837 Test Diary Demo'),
          centerTitle: true,
        ),
        body: const TestDiaryWidget(),
      ),
    );
  }
}
