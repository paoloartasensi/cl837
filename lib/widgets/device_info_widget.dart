import 'package:flutter/material.dart';
import '../models/device_info.dart';
import '../chileaf_extended_service.dart';

class DeviceInfoWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const DeviceInfoWidget({Key? key, required this.service}) : super(key: key);

  @override
  State<DeviceInfoWidget> createState() => _DeviceInfoWidgetState();
}

class _DeviceInfoWidgetState extends State<DeviceInfoWidget> {
  DeviceInfo? _deviceInfo;
  BatteryInfo? _batteryInfo;
  String? _firmwareVersion;
  String? _hardwareVersion;
  String? _deviceName;
  String? _macAddress;

  @override
  void initState() {
    super.initState();
    _setupStreams();
  }

  void _setupStreams() {
    widget.service.deviceInfoStream.listen((info) {
      if (mounted) {
        setState(() {
          _deviceInfo = info;
        });
      }
    });

    widget.service.batteryInfoStream.listen((battery) {
      if (mounted) {
        setState(() {
          _batteryInfo = battery;
        });
      }
    });

    widget.service.firmwareVersionStream.listen((version) {
      if (mounted) {
        setState(() {
          _firmwareVersion = version;
        });
      }
    });

    widget.service.hardwareVersionStream.listen((version) {
      if (mounted) {
        setState(() {
          _hardwareVersion = version;
        });
      }
    });

    widget.service.deviceNameStream.listen((name) {
      if (mounted) {
        setState(() {
          _deviceName = name;
        });
      }
    });

    widget.service.macAddressStream.listen((mac) {
      if (mounted) {
        setState(() {
          _macAddress = mac;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Icon(Icons.info_outline, color: Colors.blue),
                const SizedBox(width: 8),
                const Text(
                  'Device Information',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.blue,
                  ),
                ),
                const Spacer(),
                ElevatedButton.icon(
                  onPressed: () => widget.service.requestAllDeviceInfo(),
                  icon: const Icon(Icons.refresh, size: 16),
                  label: const Text('Refresh'),
                ),
              ],
            ),
            const Divider(),

            // Device Identity
            if (_deviceName != null || _macAddress != null) ...[
              _buildInfoSection(
                'Device Identity',
                Icons.perm_device_information,
                Colors.blue,
                [
                  if (_deviceName != null)
                    _buildInfoRow('Name', _deviceName!),
                  if (_macAddress != null)
                    _buildInfoRow('MAC Address', _macAddress!),
                ],
              ),
              const SizedBox(height: 16),
            ],

            // Versions
            if (_firmwareVersion != null || _hardwareVersion != null) ...[
              _buildInfoSection(
                'Versions',
                Icons.code,
                Colors.purple,
                [
                  if (_firmwareVersion != null)
                    _buildInfoRow('Firmware', _firmwareVersion!),
                  if (_hardwareVersion != null)
                    _buildInfoRow('Hardware', _hardwareVersion!),
                ],
              ),
              const SizedBox(height: 16),
            ],

            // Battery Info
            if (_batteryInfo != null) ...[
              _buildInfoSection(
                'Battery',
                Icons.battery_std,
                _getBatteryColor(_batteryInfo!.level),
                [
                  _buildInfoRow('Level', '${_batteryInfo!.level}%'),
                  _buildInfoRow('Status', _batteryInfo!.isCharging ? 'Charging ⚡' : 'Not Charging'),
                  if (_batteryInfo!.voltage != null)
                    _buildInfoRow('Voltage', '${_batteryInfo!.voltage}mV'),
                ],
              ),
              const SizedBox(height: 8),
              LinearProgressIndicator(
                value: _batteryInfo!.level / 100,
                backgroundColor: Colors.grey[300],
                valueColor: AlwaysStoppedAnimation<Color>(_getBatteryColor(_batteryInfo!.level)),
              ),
              const SizedBox(height: 16),
            ],

            // Memory Info
            if (_deviceInfo != null && _deviceInfo!.memoryTotal != null) ...[
              _buildInfoSection(
                'Memory',
                Icons.memory,
                Colors.orange,
                [
                  _buildInfoRow('Total', '${_deviceInfo!.memoryTotal}KB'),
                  _buildInfoRow('Used', '${_deviceInfo!.memoryUsed}KB'),
                  _buildInfoRow('Free', '${_deviceInfo!.memoryFree}KB'),
                  _buildInfoRow('Usage', '${_deviceInfo!.memoryUsagePercentage.toStringAsFixed(1)}%'),
                ],
              ),
              const SizedBox(height: 8),
              LinearProgressIndicator(
                value: _deviceInfo!.memoryUsagePercentage / 100,
                backgroundColor: Colors.grey[300],
                valueColor: AlwaysStoppedAnimation<Color>(
                  _deviceInfo!.memoryUsagePercentage > 80 ? Colors.red : Colors.orange,
                ),
              ),
              const SizedBox(height: 16),
            ],

            // Storage Information
            _buildInfoSection(
              'Data Storage Info',
              Icons.storage,
              Colors.indigo,
              [
                _buildInfoRow('Exercise History', '7 days retained'),
                _buildInfoRow('HR Sessions', 'Variable (depends on memory)'),
                _buildInfoRow('Sports Data', 'Latest session'),
                _buildInfoRow('Rope Data', 'Current session + day total'),
                _buildInfoRow('Temperature', 'Real-time only'),
                _buildInfoRow('SpO2', 'During measurement only'),
              ],
            ),

            // Raw Data (for debugging)
            if (_deviceInfo?.rawData != null && _deviceInfo!.rawData!.isNotEmpty) ...[
              const SizedBox(height: 16),
              ExpansionTile(
                title: const Text('Raw Data (Debug)'),
                leading: const Icon(Icons.bug_report),
                children: [
                  Container(
                    width: double.infinity,
                    padding: const EdgeInsets.all(8),
                    decoration: BoxDecoration(
                      color: Colors.grey[100],
                      borderRadius: BorderRadius.circular(4),
                    ),
                    child: Text(
                      _deviceInfo!.rawData.toString(),
                      style: const TextStyle(fontFamily: 'monospace', fontSize: 12),
                    ),
                  ),
                ],
              ),
            ],

            // Info Note
            const SizedBox(height: 16),
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.blue[50],
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.blue[200]!),
              ),
              child: Row(
                children: [
                  Icon(Icons.info, color: Colors.blue[600], size: 20),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      'Device info commands may not be supported by all firmware versions. Some fields might remain empty.',
                      style: TextStyle(
                        color: Colors.blue[800],
                        fontSize: 13,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoSection(String title, IconData icon, Color color, List<Widget> children) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            Icon(icon, color: color, size: 20),
            const SizedBox(width: 8),
            Text(
              title,
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
          ],
        ),
        const SizedBox(height: 8),
        Container(
          width: double.infinity,
          padding: const EdgeInsets.all(12),
          decoration: BoxDecoration(
            color: color.withOpacity(0.1),
            borderRadius: BorderRadius.circular(8),
            border: Border.all(color: color.withOpacity(0.3)),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: children,
          ),
        ),
      ],
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: const TextStyle(fontWeight: FontWeight.w500)),
          Flexible(
            child: Text(
              value, 
              style: const TextStyle(color: Colors.grey),
              textAlign: TextAlign.right,
            ),
          ),
        ],
      ),
    );
  }

  Color _getBatteryColor(int level) {
    if (level > 60) return Colors.green;
    if (level > 30) return Colors.orange;
    return Colors.red;
  }
}
