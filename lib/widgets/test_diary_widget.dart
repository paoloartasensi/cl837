import 'package:flutter/material.dart';
import '../models/test_record.dart';
import '../services/test_diary_service.dart';
import 'diary_export_widget.dart';

class TestDiaryWidget extends StatefulWidget {
  const TestDiaryWidget({super.key});

  @override
  State<TestDiaryWidget> createState() => _TestDiaryWidgetState();
}

class _TestDiaryWidgetState extends State<TestDiaryWidget> {
  final TestDiaryService _diaryService = TestDiaryService.instance;
  List<TestRecord> _records = [];
  TestType? _filterType;
  bool _isLoading = true;
  String _searchQuery = '';
  Map<String, dynamic>? _statistics;

  @override
  void initState() {
    super.initState();
    _initializeDiary();
  }

  Future<void> _initializeDiary() async {
    // Inizializza dati di test se necessario
    await _diaryService.initializeSampleDataIfEmpty();
    // Carica i record
    await _loadRecords();
    await _loadStatistics();
  }

  Future<void> _loadRecords() async {
    setState(() => _isLoading = true);
    
    try {
      List<TestRecord> records;
      if (_filterType != null) {
        records = await _diaryService.getRecordsByType(_filterType!);
      } else {
        records = await _diaryService.getAllRecords();
      }
      
      // Applica filtro di ricerca
      if (_searchQuery.isNotEmpty) {
        records = records.where((record) {
          return record.summary.toLowerCase().contains(_searchQuery.toLowerCase()) ||
                 record.formattedTimestamp.contains(_searchQuery) ||
                 (record.notes?.toLowerCase().contains(_searchQuery.toLowerCase()) ?? false);
        }).toList();
      }
      
      setState(() {
        _records = records;
        _isLoading = false;
      });
    } catch (e) {
      setState(() => _isLoading = false);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Errore nel caricamento: $e')),
        );
      }
    }
  }

  Future<void> _loadStatistics() async {
    final stats = await _diaryService.getDiaryStatistics();
    setState(() => _statistics = stats);
  }

  Future<void> _deleteRecord(TestRecord record) async {
    final confirm = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Elimina Record'),
        content: Text('Sei sicuro di voler eliminare questo record?\n\n${record.summary}'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Annulla'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, true),
            style: TextButton.styleFrom(foregroundColor: Colors.red),
            child: const Text('Elimina'),
          ),
        ],
      ),
    );

    if (confirm == true) {
      final success = await _diaryService.deleteRecord(record.id);
      if (success) {
        _loadRecords();
        _loadStatistics();
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Record eliminato')),
          );
        }
      }
    }
  }

  Future<void> _deleteAllRecords() async {
    final confirm = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Elimina Tutti i Record'),
        content: Text('Sei sicuro di voler eliminare TUTTI i ${_records.length} record del diario?\n\nQuesta azione non può essere annullata.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Annulla'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, true),
            style: TextButton.styleFrom(foregroundColor: Colors.red),
            child: const Text('Elimina Tutto'),
          ),
        ],
      ),
    );

    if (confirm == true) {
      await _diaryService.deleteAllRecords();
      _loadRecords();
      _loadStatistics();
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Tutti i record sono stati eliminati')),
        );
      }
    }
  }

  Future<void> _editNotes(TestRecord record) async {
    final notesController = TextEditingController(text: record.notes ?? '');
    
    final newNotes = await showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Modifica Note'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(record.summary, style: const TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 12),
            TextField(
              controller: notesController,
              decoration: const InputDecoration(
                labelText: 'Note',
                border: OutlineInputBorder(),
              ),
              maxLines: 3,
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Annulla'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, notesController.text),
            child: const Text('Salva'),
          ),
        ],
      ),
    );

    if (newNotes != null) {
      final success = await _diaryService.updateRecordNotes(record.id, newNotes);
      if (success) {
        _loadRecords();
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Note aggiornate')),
          );
        }
      }
    }
  }

  Widget _buildFilterChips() {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child: Row(
        children: [
          FilterChip(
            label: const Text('Tutti'),
            selected: _filterType == null,
            onSelected: (selected) {
              setState(() => _filterType = null);
              _loadRecords();
            },
          ),
          const SizedBox(width: 8),
          ...TestType.values.map((type) => Padding(
            padding: const EdgeInsets.only(right: 8),
            child: FilterChip(
              label: Text('${type.icon} ${type.displayName}'),
              selected: _filterType == type,
              onSelected: (selected) {
                setState(() => _filterType = selected ? type : null);
                _loadRecords();
              },
            ),
          )),
        ],
      ),
    );
  }

  Widget _buildStatistics() {
    if (_statistics == null) return const SizedBox.shrink();
    
    return Card(
      margin: const EdgeInsets.all(16),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('📊 Statistiche Diario', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 12),
            Text('Totale record: ${_statistics!['totalRecords']}'),
            Text('Media giornaliera: ${(_statistics!['averagePerDay'] as double).toStringAsFixed(1)}'),
            if (_statistics!['oldestRecord'] != null)
              Text('Primo record: ${DateTime.parse(_statistics!['oldestRecord']).toString().substring(0, 10)}'),
            const SizedBox(height: 8),
            const Text('Per tipo:', style: TextStyle(fontWeight: FontWeight.bold)),
            ...(_statistics!['byType'] as Map<String, int>).entries.map(
              (entry) => Text('  ${entry.key}: ${entry.value}'),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Barra di ricerca
            Padding(
              padding: const EdgeInsets.all(16),
              child: TextField(
                decoration: const InputDecoration(
                  labelText: 'Cerca nei record...',
                  prefixIcon: Icon(Icons.search),
                  border: OutlineInputBorder(),
                ),
                onChanged: (value) {
                  setState(() => _searchQuery = value);
                  _loadRecords();
                },
              ),
            ),
            
            // Filtri per tipo
            _buildFilterChips(),
            
            // Statistiche
            _buildStatistics(),
            
            // Export/Import widget
            const DiaryExportWidget(),
            
            // Header con conteggio e azioni
            Padding(
              padding: const EdgeInsets.all(16),
              child: Row(
                children: [
                  Text(
                    '📝 Diario Test (${_records.length} record)',
                    style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  const Spacer(),
                  if (_records.isNotEmpty) ...[
                    IconButton(
                  icon: const Icon(Icons.delete_sweep, color: Colors.red),
                  tooltip: 'Elimina tutti',
                  onPressed: _deleteAllRecords,
                ),
                IconButton(
                  icon: const Icon(Icons.refresh),
                  tooltip: 'Aggiorna',
                  onPressed: () {
                    _loadRecords();
                    _loadStatistics();
                  },
                ),
              ],
            ],
          ),
        ),
        
            // Lista dei record
            _isLoading
                ? const SizedBox(
                    height: 200,
                    child: Center(child: CircularProgressIndicator()),
                  )
                : _records.isEmpty
                    ? SizedBox(
                        height: 200,
                        child: Center(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              const Icon(Icons.note_alt_outlined, size: 64, color: Colors.grey),
                              const SizedBox(height: 16),
                              Text(
                                _searchQuery.isNotEmpty 
                                    ? 'Nessun record trovato per "$_searchQuery"'
                                    : 'Nessun test salvato nel diario',
                                style: const TextStyle(fontSize: 16, color: Colors.grey),
                              ),
                              if (_searchQuery.isNotEmpty)
                                TextButton(
                                  onPressed: () {
                                    setState(() => _searchQuery = '');
                                    _loadRecords();
                                  },
                                  child: const Text('Mostra tutti'),
                                ),
                            ],
                          ),
                        ),
                      )
                    : ListView.builder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemCount: _records.length,
                        itemBuilder: (context, index) {
                          final record = _records[index];
                          return Card(
                            margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
                            child: ListTile(
                              leading: CircleAvatar(
                                backgroundColor: Colors.blue.shade100,
                                child: Text(record.type.icon),
                              ),
                              title: Text(record.summary),
                              subtitle: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(record.formattedTimestamp),
                                  if (record.notes != null) ...[
                                    const SizedBox(height: 4),
                                    Text(
                                      record.notes!,
                                      style: const TextStyle(
                                        fontStyle: FontStyle.italic,
                                        color: Colors.blue,
                                      ),
                                    ),
                                  ],
                                ],
                              ),
                              trailing: PopupMenuButton<String>(
                                onSelected: (value) {
                                  switch (value) {
                                    case 'edit':
                                      _editNotes(record);
                                      break;
                                    case 'delete':
                                      _deleteRecord(record);
                                      break;
                                  }
                                },
                                itemBuilder: (context) => [
                                  const PopupMenuItem(
                                    value: 'edit',
                                    child: Row(
                                      children: [
                                        Icon(Icons.edit, size: 20),
                                        SizedBox(width: 8),
                                        Text('Modifica note'),
                                      ],
                                    ),
                                  ),
                                  const PopupMenuItem(
                                    value: 'delete',
                                    child: Row(
                                      children: [
                                        Icon(Icons.delete, size: 20, color: Colors.red),
                                        SizedBox(width: 8),
                                        Text('Elimina'),
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          );
                        },
                      ),
          ],
        ),
      ),
    );
  }
}
