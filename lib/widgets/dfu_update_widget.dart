/// 🔄 DFU Update Widget
/// 
/// Widget per aggiornamento firmware CL837/CL831
/// Mostra versione corrente, target e progress DFU
library;

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../services/dfu_service.dart';
import '../chileaf_extended_service.dart';

class DfuUpdateWidget extends StatefulWidget {
  final ChileafExtendedService service;
  final BluetoothDevice device;
  
  /// Path del firmware ZIP in assets
  final String firmwareAssetPath;
  
  /// Versione target firmware
  final String targetVersion;
  
  const DfuUpdateWidget({
    super.key,
    required this.service,
    required this.device,
    this.firmwareAssetPath = 'lib/assets/fw/FW_V419.zip',
    this.targetVersion = '4.1.9',
  });

  @override
  State<DfuUpdateWidget> createState() => _DfuUpdateWidgetState();
}

class _DfuUpdateWidgetState extends State<DfuUpdateWidget> {
  late DfuService _dfuService;
  
  String? _currentVersion;
  bool _isLoadingVersion = false;
  bool _isUpdating = false;
  
  DfuProgress _progress = DfuProgress(state: DfuState.idle);
  
  @override
  void initState() {
    super.initState();
    _dfuService = DfuService(
      chileafService: widget.service,
      device: widget.device,
    );
    
    _dfuService.progressStream.listen((progress) {
      setState(() {
        _progress = progress;
        
        // Update terminato
        if (progress.state == DfuState.completed) {
          _isUpdating = false;
          _showSuccessDialog();
        } else if (progress.state == DfuState.error || progress.state == DfuState.aborted) {
          _isUpdating = false;
        }
      });
    });
    
    // Carica versione corrente
    _loadCurrentVersion();
  }
  
  @override
  void dispose() {
    _dfuService.dispose();
    super.dispose();
  }
  
  Future<void> _loadCurrentVersion() async {
    setState(() {
      _isLoadingVersion = true;
    });
    
    try {
      String? version = await _dfuService.getCurrentFirmwareVersion();
      setState(() {
        _currentVersion = version;
        _isLoadingVersion = false;
      });
    } catch (e) {
      setState(() {
        _isLoadingVersion = false;
      });
    }
  }
  
  Future<void> _startDfuUpdate() async {
    // Conferma utente
    bool? confirm = await _showConfirmDialog();
    if (confirm != true) return;
    
    setState(() {
      _isUpdating = true;
    });
    
    // Avvia update
    DfuResult result = await _dfuService.performDfuUpdate(
      assetPath: widget.firmwareAssetPath,
      targetVersion: widget.targetVersion,
    );
    
    if (!result.success) {
      _showErrorDialog(result.errorMessage ?? 'Unknown error');
    }
  }
  
  Future<bool?> _showConfirmDialog() {
    return showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            Icon(Icons.system_update, color: Colors.orange),
            SizedBox(width: 12),
            Text('Firmware Update'),
          ],
        ),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Do you want to update the firmware?'),
            SizedBox(height: 16),
            _buildInfoRow('Current Version:', _currentVersion ?? 'Unknown'),
            _buildInfoRow('Target Version:', widget.targetVersion),
            SizedBox(height: 16),
            Container(
              padding: EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.orange.shade50,
                borderRadius: BorderRadius.circular(8),
              ),
              child: Row(
                children: [
                  Icon(Icons.warning_amber, color: Colors.orange, size: 20),
                  SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      'Keep device close during update\n(~2-5 minutes)',
                      style: TextStyle(fontSize: 12, color: Colors.orange.shade900),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.orange,
            ),
            child: Text('Update'),
          ),
        ],
      ),
    );
  }
  
  void _showSuccessDialog() {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            Icon(Icons.check_circle, color: Colors.green),
            SizedBox(width: 12),
            Text('Update Completed'),
          ],
        ),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text('Firmware updated successfully!'),
            SizedBox(height: 16),
            Text(
              'Device will restart in normal mode.',
              style: TextStyle(fontSize: 12, color: Colors.grey[600]),
            ),
          ],
        ),
        actions: [
          ElevatedButton(
            onPressed: () {
              Navigator.pop(context);
              // Ricarica versione
              _loadCurrentVersion();
            },
            child: Text('OK'),
          ),
        ],
      ),
    );
  }
  
  void _showErrorDialog(String message) {
    // Determina se è un timeout di scan DFU
    bool isDfuScanTimeout = message.contains('DFU device not found') || 
                            message.contains('timeout');
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            Icon(Icons.error, color: Colors.red),
            SizedBox(width: 12),
            Expanded(child: Text('Update Failed')),
          ],
        ),
        content: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(message),
              if (isDfuScanTimeout) ...[
                SizedBox(height: 16),
                Container(
                  padding: EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: Colors.blue.shade50,
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(Icons.info_outline, color: Colors.blue, size: 20),
                          SizedBox(width: 8),
                          Text(
                            'Troubleshooting',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Colors.blue.shade900,
                            ),
                          ),
                        ],
                      ),
                      SizedBox(height: 8),
                      Text(
                        '• Keep device within 1 meter\n'
                        '• Wait 5 seconds and retry\n'
                        '• Check device battery > 30%\n'
                        '• Try restarting the device\n'
                        '• Ensure device is not connected to other apps',
                        style: TextStyle(fontSize: 12, color: Colors.blue.shade900),
                      ),
                    ],
                  ),
                ),
              ],
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('OK'),
          ),
        ],
      ),
    );
  }
  
  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 4),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: TextStyle(fontWeight: FontWeight.w500),
          ),
          Text(
            value,
            style: TextStyle(color: Colors.blue),
          ),
        ],
      ),
    );
  }
  
  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.all(16),
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header
            Row(
              children: [
                Container(
                  padding: EdgeInsets.all(8),
                  decoration: BoxDecoration(
                    color: Colors.orange.shade100,
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Icon(Icons.system_update, color: Colors.orange),
                ),
                SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Firmware Update',
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      Text(
                        'CL837/CL831 Device',
                        style: TextStyle(
                          fontSize: 12,
                          color: Colors.grey[600],
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            
            SizedBox(height: 20),
            
            // Version info
            Container(
              padding: EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.grey.shade100,
                borderRadius: BorderRadius.circular(8),
              ),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Current Version:',
                        style: TextStyle(fontWeight: FontWeight.w500),
                      ),
                      _isLoadingVersion
                          ? SizedBox(
                              width: 16,
                              height: 16,
                              child: CircularProgressIndicator(strokeWidth: 2),
                            )
                          : Text(
                              _currentVersion ?? 'Unknown',
                              style: TextStyle(
                                color: Colors.blue,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                    ],
                  ),
                  SizedBox(height: 8),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Available Version:',
                        style: TextStyle(fontWeight: FontWeight.w500),
                      ),
                      Text(
                        widget.targetVersion,
                        style: TextStyle(
                          color: Colors.green,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            
            SizedBox(height: 16),
            
            // Progress section (mostrato solo durante update)
            if (_isUpdating) ...[
              Container(
                padding: EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.blue.shade50,
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Stato
                    Row(
                      children: [
                        SizedBox(
                          width: 16,
                          height: 16,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        ),
                        SizedBox(width: 12),
                        Expanded(
                          child: Text(
                            _getStateMessage(_progress.state),
                            style: TextStyle(
                              fontWeight: FontWeight.w500,
                              color: Colors.blue.shade900,
                            ),
                          ),
                        ),
                      ],
                    ),
                    
                    if (_progress.message != null) ...[
                      SizedBox(height: 8),
                      Text(
                        _progress.message!,
                        style: TextStyle(
                          fontSize: 12,
                          color: Colors.grey[700],
                        ),
                      ),
                    ],
                    
                    // Progress bar (solo durante upload)
                    if (_progress.state == DfuState.uploading) ...[
                      SizedBox(height: 12),
                      ClipRRect(
                        borderRadius: BorderRadius.circular(4),
                        child: LinearProgressIndicator(
                          value: _progress.percent / 100,
                          minHeight: 8,
                          backgroundColor: Colors.grey.shade300,
                          valueColor: AlwaysStoppedAnimation(Colors.blue),
                        ),
                      ),
                      SizedBox(height: 8),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '${_progress.percent}%',
                            style: TextStyle(
                              fontSize: 12,
                              fontWeight: FontWeight.bold,
                              color: Colors.blue.shade900,
                            ),
                          ),
                          if (_progress.totalParts > 1)
                            Text(
                              'Part ${_progress.currentPart}/${_progress.totalParts}',
                              style: TextStyle(
                                fontSize: 12,
                                color: Colors.grey[700],
                              ),
                            ),
                        ],
                      ),
                      if (_progress.avgSpeed > 0) ...[
                        SizedBox(height: 4),
                        Text(
                          'Speed: ${_progress.avgSpeed.toStringAsFixed(1)} KB/s',
                          style: TextStyle(
                            fontSize: 10,
                            color: Colors.grey[600],
                          ),
                        ),
                      ],
                    ],
                  ],
                ),
              ),
              SizedBox(height: 16),
            ],
            
            // Error message
            if (_progress.state == DfuState.error || _progress.state == DfuState.aborted) ...[
              Container(
                padding: EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.red.shade50,
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Row(
                  children: [
                    Icon(Icons.error, color: Colors.red, size: 20),
                    SizedBox(width: 12),
                    Expanded(
                      child: Text(
                        _progress.message ?? 'Update failed',
                        style: TextStyle(
                          fontSize: 12,
                          color: Colors.red.shade900,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              SizedBox(height: 16),
            ],
            
            // Update button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: _isUpdating || _isLoadingVersion
                    ? null
                    : _startDfuUpdate,
                icon: Icon(Icons.system_update),
                label: Text(
                  _isUpdating ? 'Updating...' : 'Start Firmware Update',
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.orange,
                  foregroundColor: Colors.white,
                  padding: EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
            ),
            
            SizedBox(height: 8),
            
            // Refresh version button
            SizedBox(
              width: double.infinity,
              child: TextButton.icon(
                onPressed: _isUpdating || _isLoadingVersion
                    ? null
                    : _loadCurrentVersion,
                icon: Icon(Icons.refresh, size: 18),
                label: Text('Refresh Version'),
              ),
            ),
          ],
        ),
      ),
    );
  }
  
  String _getStateMessage(DfuState state) {
    switch (state) {
      case DfuState.idle:
        return 'Ready';
      case DfuState.preparingFile:
        return 'Preparing firmware file...';
      case DfuState.enteringDfuMode:
        return 'Entering DFU mode...';
      case DfuState.scanningDfuDevice:
        return 'Scanning for DFU device...';
      case DfuState.connecting:
        return 'Connecting...';
      case DfuState.uploading:
        return 'Uploading firmware...';
      case DfuState.validating:
        return 'Validating firmware...';
      case DfuState.completed:
        return 'Update completed!';
      case DfuState.error:
        return 'Update failed';
      case DfuState.aborted:
        return 'Update aborted';
    }
  }
}
