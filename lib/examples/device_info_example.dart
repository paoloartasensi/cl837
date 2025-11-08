/// 📋 Esempio: Lettura Device Information Service
/// 
/// Mostra come leggere le informazioni del device dal servizio standard BLE 0x180A
/// incluse versioni firmware e software
library;

import 'package:flutter/material.dart';
import '../services/dfu_service.dart';

class DeviceInfoScreen extends StatefulWidget {
  final DfuService dfuService;
  
  const DeviceInfoScreen({
    super.key,
    required this.dfuService,
  });

  @override
  State<DeviceInfoScreen> createState() => _DeviceInfoScreenState();
}

class _DeviceInfoScreenState extends State<DeviceInfoScreen> {
  Map<String, String>? _deviceInfo;
  bool _isLoading = false;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadDeviceInfo();
  }

  Future<void> _loadDeviceInfo() async {
    setState(() {
      _isLoading = true;
      _error = null;
    });

    try {
      Map<String, String> info = await widget.dfuService.readDeviceInformation();
      
      setState(() {
        _deviceInfo = info;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Device Information'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _isLoading ? null : () {
              // Forza refresh (bypass cache)
              _loadDeviceInfo();
            },
          ),
        ],
      ),
      body: _buildBody(),
    );
  }

  Widget _buildBody() {
    if (_isLoading) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 16),
            Text('Reading device information...'),
          ],
        ),
      );
    }

    if (_error != null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.error_outline, size: 64, color: Colors.red),
            const SizedBox(height: 16),
            Text('Error: $_error'),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _loadDeviceInfo,
              child: const Text('Retry'),
            ),
          ],
        ),
      );
    }

    if (_deviceInfo == null || _deviceInfo!.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.info_outline, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text('No device information available'),
          ],
        ),
      );
    }

    return ListView(
      padding: const EdgeInsets.all(16),
      children: [
        _buildInfoCard(),
        const SizedBox(height: 16),
        _buildVersionsCard(),
        const SizedBox(height: 16),
        _buildHardwareCard(),
      ],
    );
  }

  Widget _buildInfoCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              children: [
                Icon(Icons.devices, color: Colors.blue),
                SizedBox(width: 8),
                Text(
                  'Device Identity',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const Divider(),
            _buildInfoRow('Manufacturer', _deviceInfo!['manufacturer'], Icons.business),
            _buildInfoRow('Model', _deviceInfo!['model'], Icons.phone_android),
            _buildInfoRow('Serial Number', _deviceInfo!['serial'], Icons.tag),
          ],
        ),
      ),
    );
  }

  Widget _buildVersionsCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              children: [
                Icon(Icons.memory, color: Colors.green),
                SizedBox(width: 8),
                Text(
                  'Software Versions',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const Divider(),
            _buildInfoRow('Firmware', _deviceInfo!['firmware'], Icons.developer_board),
            _buildInfoRow('Software', _deviceInfo!['software'], Icons.code),
          ],
        ),
      ),
    );
  }

  Widget _buildHardwareCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(
              children: [
                Icon(Icons.hardware, color: Colors.orange),
                SizedBox(width: 8),
                Text(
                  'Hardware',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const Divider(),
            _buildInfoRow('Hardware Revision', _deviceInfo!['hardware'], Icons.settings),
            _buildInfoRow('System ID', _deviceInfo!['systemId'], Icons.fingerprint),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String? value, IconData icon) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        children: [
          Icon(icon, size: 20, color: Colors.grey[600]),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: TextStyle(
                    fontSize: 12,
                    color: Colors.grey[600],
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  value ?? 'N/A',
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

/// Widget più semplice per mostrare solo versioni firmware/software
class FirmwareVersionWidget extends StatelessWidget {
  final DfuService dfuService;
  
  const FirmwareVersionWidget({
    super.key,
    required this.dfuService,
  });

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Map<String, String>>(
      future: dfuService.readDeviceInformation(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const SizedBox(
            width: 20,
            height: 20,
            child: CircularProgressIndicator(strokeWidth: 2),
          );
        }

        if (snapshot.hasError || !snapshot.hasData) {
          return const Text('Version: Unknown');
        }

        final firmware = snapshot.data!['firmware'] ?? 'Unknown';
        final software = snapshot.data!['software'] ?? 'Unknown';

        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Firmware: $firmware'),
            Text('Software: $software'),
          ],
        );
      },
    );
  }
}
