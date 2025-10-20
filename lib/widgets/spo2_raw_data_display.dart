import 'package:flutter/material.dart';

/// Widget per visualizzare i dati raw SpO2 nell'UI
/// Mostra tutti i dettagli tecnici del frame BLE ricevuto dal dispositivo
class SpO2RawDataDisplay extends StatelessWidget {
  final Map<String, dynamic>? rawData;
  
  const SpO2RawDataDisplay({
    super.key,
    this.rawData,
  });

  @override
  Widget build(BuildContext context) {
    if (rawData == null) {
      return const Card(
        child: Padding(
          padding: EdgeInsets.all(16.0),
          child: Text(
            '📡 In attesa di dati SpO2...',
            style: TextStyle(fontSize: 16),
          ),
        ),
      );
    }

    return Card(
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header con valore principale
            Container(
              padding: const EdgeInsets.all(12.0),
              decoration: BoxDecoration(
                color: _getValueColor(rawData!['spo2Value']),
                borderRadius: BorderRadius.circular(8.0),
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    '🩸 SpO2: ${rawData!['spo2Value']}%',
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  Text(
                    rawData!['spo2ValueHex'] ?? '',
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
            ),
            
            const SizedBox(height: 16),
            
            // Dati del frame raw
            _buildSectionHeader('📦 Frame Raw BLE'),
            _buildDataRow('Raw Frame', rawData!['rawFrame'] ?? 'N/A'),
            _buildDataRow('Frame Decimal', rawData!['rawFrameDecimal'] ?? 'N/A'),
            _buildDataRow('Frame Length', '${rawData!['frameLength'] ?? 0} bytes'),
            
            const SizedBox(height: 16),
            
            // Byte decodificati
            _buildSectionHeader('🔍 Byte Decodificati'),
            _buildByteRow(
              'Byte 4 (SpO2)', 
              rawData!['byte4_spo2Value'] ?? 0, 
              rawData!['byte4_spo2ValueHex'] ?? '',
              '⬅️ VALORE PRINCIPALE'
            ),
            _buildByteRow(
              'Byte 3 (Status)', 
              rawData!['byte3_status'] ?? 0, 
              rawData!['byte3_statusHex'] ?? '',
              rawData!['statusText'] ?? ''
            ),
            _buildByteRow(
              'Byte 5 (Postura)', 
              rawData!['byte5_posture'] ?? 0, 
              rawData!['byte5_postureHex'] ?? '',
              rawData!['postureText'] ?? ''
            ),
            _buildByteRow(
              'Byte 6 (PI Signal)', 
              rawData!['byte6_piSignal'] ?? 0, 
              rawData!['byte6_piSignalHex'] ?? '',
              rawData!['piSignalText'] ?? ''
            ),
            _buildByteRow(
              'Byte 7 (On Wrist)', 
              rawData!['byte7_onWrist'] ?? 0, 
              rawData!['byte7_onWristHex'] ?? '',
              rawData!['onWristText'] ?? ''
            ),
            
            const SizedBox(height: 16),
            
            // Interpretazione clinica
            _buildSectionHeader('🏥 Interpretazione Clinica'),
            _buildDataRow('Tipo Frame', rawData!['frameType'] ?? 'N/A'),
            _buildDataRow('Interpretazione', rawData!['clinicalInterpretation'] ?? 'N/A'),
            _buildDataRow('Livello Allerta', rawData!['alertLevel'] ?? 'N/A'),
            
            const SizedBox(height: 16),
            
            // Timestamp
            _buildSectionHeader('⏰ Timestamp'),
            _buildDataRow('Timestamp', rawData!['timestamp'] ?? 'N/A'),
          ],
        ),
      ),
    );
  }
  
  Widget _buildSectionHeader(String title) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8.0),
      child: Text(
        title,
        style: const TextStyle(
          fontSize: 18,
          fontWeight: FontWeight.bold,
          color: Colors.blue,
        ),
      ),
    );
  }
  
  Widget _buildDataRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 120,
            child: Text(
              label,
              style: const TextStyle(
                fontWeight: FontWeight.w500,
              ),
            ),
          ),
          Expanded(
            child: Text(
              value,
              style: const TextStyle(
                fontFamily: 'monospace',
              ),
            ),
          ),
        ],
      ),
    );
  }
  
  Widget _buildByteRow(String label, int value, String hex, String description) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              SizedBox(
                width: 120,
                child: Text(
                  label,
                  style: const TextStyle(
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
              Text(
                '$value',
                style: const TextStyle(
                  fontFamily: 'monospace',
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(width: 8),
              Text(
                '($hex)',
                style: const TextStyle(
                  fontFamily: 'monospace',
                  color: Colors.grey,
                ),
              ),
            ],
          ),
          if (description.isNotEmpty)
            Padding(
              padding: const EdgeInsets.only(left: 120, top: 2),
              child: Text(
                description,
                style: const TextStyle(
                  fontSize: 12,
                  color: Colors.green,
                  fontStyle: FontStyle.italic,
                ),
              ),
            ),
        ],
      ),
    );
  }
  
  Color _getValueColor(int? value) {
    if (value == null || value == 0) return Colors.grey;
    if (value >= 95) return Colors.green;
    if (value >= 90) return Colors.orange;
    return Colors.red;
  }
}
