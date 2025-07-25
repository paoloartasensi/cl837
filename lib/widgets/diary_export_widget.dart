import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import '../services/test_diary_service.dart';

class DiaryExportWidget extends StatefulWidget {
  const DiaryExportWidget({super.key});

  @override
  State<DiaryExportWidget> createState() => _DiaryExportWidgetState();
}

class _DiaryExportWidgetState extends State<DiaryExportWidget> {
  final TestDiaryService _diaryService = TestDiaryService.instance;
  bool _isExporting = false;
  bool _isImporting = false;

  Future<void> _exportData() async {
    setState(() => _isExporting = true);

    try {
      final jsonData = await _diaryService.exportRecordsAsJson();
      
      // Copia negli appunti
      await Clipboard.setData(ClipboardData(text: jsonData));
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('📋 Dati esportati negli appunti! Puoi incollare il testo in un file.'),
            duration: Duration(seconds: 3),
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Errore esportazione: $e')),
        );
      }
    } finally {
      setState(() => _isExporting = false);
    }
  }

  Future<void> _showImportDialog() async {
    final controller = TextEditingController();
    
    final result = await showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Importa Dati'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text('Incolla qui i dati JSON esportati:'),
            const SizedBox(height: 12),
            TextField(
              controller: controller,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                hintText: 'Incolla JSON qui...',
              ),
              maxLines: 5,
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Annulla'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, controller.text),
            child: const Text('Importa'),
          ),
        ],
      ),
    );

    if (result != null && result.isNotEmpty) {
      await _importData(result);
    }
  }

  Future<void> _importData(String jsonData) async {
    setState(() => _isImporting = true);

    try {
      final importedCount = await _diaryService.importRecordsFromJson(jsonData);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('✅ Importati $importedCount nuovi record!'),
            duration: const Duration(seconds: 3),
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ Errore importazione: $e')),
        );
      }
    } finally {
      setState(() => _isImporting = false);
    }
  }

  Future<void> _cleanupOldData() async {
    final result = await showDialog<int>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Pulizia Dati Vecchi'),
        content: const Text('Elimina record più vecchi di quanti giorni?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Annulla'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, 7),
            child: const Text('7 giorni'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, 30),
            child: const Text('30 giorni'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, 90),
            child: const Text('90 giorni'),
          ),
        ],
      ),
    );

    if (result != null) {
      final deletedCount = await _diaryService.deleteOldRecords(result);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('🗑️ Eliminati $deletedCount record vecchi')),
        );
      }
    }
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
            const Text(
              '💾 Backup & Gestione Dati',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            // Export
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: _isExporting ? null : _exportData,
                icon: _isExporting 
                    ? const SizedBox(
                        width: 16, 
                        height: 16, 
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.download),
                label: Text(_isExporting ? 'Esportando...' : 'Esporta tutti i dati'),
              ),
            ),
            
            const SizedBox(height: 8),
            
            // Import
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: _isImporting ? null : _showImportDialog,
                icon: _isImporting 
                    ? const SizedBox(
                        width: 16, 
                        height: 16, 
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.upload),
                label: Text(_isImporting ? 'Importando...' : 'Importa dati'),
              ),
            ),
            
            const SizedBox(height: 8),
            
            // Cleanup
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: _cleanupOldData,
                icon: const Icon(Icons.cleaning_services, color: Colors.orange),
                label: const Text('Pulisci dati vecchi'),
                style: ElevatedButton.styleFrom(
                  foregroundColor: Colors.orange,
                ),
              ),
            ),
            
            const SizedBox(height: 16),
            
            FutureBuilder<DateTime?>(
              future: _diaryService.getLastBackupDate(),
              builder: (context, snapshot) {
                if (snapshot.hasData && snapshot.data != null) {
                  final lastBackup = snapshot.data!;
                  return Text(
                    'Ultimo backup: ${lastBackup.day.toString().padLeft(2, '0')}/'
                    '${lastBackup.month.toString().padLeft(2, '0')}/${lastBackup.year} '
                    '${lastBackup.hour.toString().padLeft(2, '0')}:'
                    '${lastBackup.minute.toString().padLeft(2, '0')}',
                    style: const TextStyle(color: Colors.grey),
                  );
                }
                return const Text(
                  'Nessun backup effettuato',
                  style: TextStyle(color: Colors.grey),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
