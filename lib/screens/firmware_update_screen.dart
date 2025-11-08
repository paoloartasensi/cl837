/// 🔄 Firmware Update Screen
/// 
/// Schermata dedicata all'aggiornamento firmware CL837/CL831
library;

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../chileaf_extended_service.dart';
import '../widgets/dfu_update_widget.dart';

class FirmwareUpdateScreen extends StatelessWidget {
  final ChileafExtendedService service;
  final BluetoothDevice device;
  
  const FirmwareUpdateScreen({
    super.key,
    required this.service,
    required this.device,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Firmware Update'),
        backgroundColor: Colors.orange,
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Warning banner
            Container(
              width: double.infinity,
              padding: EdgeInsets.all(16),
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  colors: [Colors.orange.shade700, Colors.orange.shade500],
                ),
              ),
              child: Column(
                children: [
                  Icon(Icons.warning_amber, color: Colors.white, size: 40),
                  SizedBox(height: 8),
                  Text(
                    'Important',
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Keep device close and charged during update',
                    style: TextStyle(color: Colors.white),
                    textAlign: TextAlign.center,
                  ),
                ],
              ),
            ),
            
            // DFU Widget
            DfuUpdateWidget(
              service: service,
              device: device,
            ),
            
            // Instructions
            Padding(
              padding: EdgeInsets.all(16),
              child: Card(
                child: Padding(
                  padding: EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(Icons.info_outline, color: Colors.blue),
                          SizedBox(width: 8),
                          Text(
                            'Update Instructions',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                      SizedBox(height: 16),
                      _buildInstruction(
                        '1',
                        'Keep device within 1 meter range',
                      ),
                      _buildInstruction(
                        '2',
                        'Ensure battery level > 30%',
                      ),
                      _buildInstruction(
                        '3',
                        'Do not close app during update',
                      ),
                      _buildInstruction(
                        '4',
                        'Update takes 2-5 minutes',
                      ),
                      _buildInstruction(
                        '5',
                        'Device will restart automatically',
                      ),
                    ],
                  ),
                ),
              ),
            ),
            
            // Technical info
            Padding(
              padding: EdgeInsets.all(16),
              child: Card(
                color: Colors.grey.shade50,
                child: Padding(
                  padding: EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(Icons.code, color: Colors.grey[600], size: 18),
                          SizedBox(width: 8),
                          Text(
                            'Technical Details',
                            style: TextStyle(
                              fontSize: 14,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey[700],
                            ),
                          ),
                        ],
                      ),
                      SizedBox(height: 12),
                      _buildTechDetail('Protocol:', 'Nordic DFU'),
                      _buildTechDetail('Package:', 'FW_V419.zip'),
                      _buildTechDetail('Device:', 'CL837/CL831'),
                      _buildTechDetail('Mode:', 'DFU Bootloader (0x27)'),
                      SizedBox(height: 8),
                      Text(
                        'For more information, see docs/DFU_UPDATE_PIPELINE.md',
                        style: TextStyle(
                          fontSize: 10,
                          color: Colors.grey[600],
                          fontStyle: FontStyle.italic,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            
            SizedBox(height: 32),
          ],
        ),
      ),
    );
  }
  
  Widget _buildInstruction(String number, String text) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 8),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            width: 28,
            height: 28,
            decoration: BoxDecoration(
              color: Colors.blue,
              shape: BoxShape.circle,
            ),
            child: Center(
              child: Text(
                number,
                style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 14,
                ),
              ),
            ),
          ),
          SizedBox(width: 12),
          Expanded(
            child: Padding(
              padding: EdgeInsets.only(top: 4),
              child: Text(
                text,
                style: TextStyle(fontSize: 14),
              ),
            ),
          ),
        ],
      ),
    );
  }
  
  Widget _buildTechDetail(String label, String value) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 4),
      child: Row(
        children: [
          Text(
            label,
            style: TextStyle(
              fontSize: 12,
              color: Colors.grey[600],
              fontWeight: FontWeight.w500,
            ),
          ),
          SizedBox(width: 8),
          Text(
            value,
            style: TextStyle(
              fontSize: 12,
              color: Colors.grey[800],
              fontFamily: 'monospace',
            ),
          ),
        ],
      ),
    );
  }
}
