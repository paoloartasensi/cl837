import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../chileaf_extended_service.dart';
import 'sensor_3d_settings_screen.dart';
import 'firmware_update_screen.dart';

/// Schermata di debug/impostazioni avanzate
/// Replica le funzionalità dell'app di debug cinese
class AdvancedSettingsScreen extends StatelessWidget {
  final ChileafExtendedService service;
  final BluetoothDevice? device;

  const AdvancedSettingsScreen({
    super.key,
    required this.service,
    this.device,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Impostazioni Avanzate'),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          _buildSectionHeader('Sensori'),
          _buildSettingsTile(
            context,
            icon: Icons.threed_rotation,
            title: 'Sensore 3D (Accelerometro)',
            subtitle: 'Configura frequenza e stato del sensore',
            color: Colors.blue,
            onTap: () => Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => Sensor3DSettingsScreen(service: service),
              ),
            ),
          ),
          const SizedBox(height: 24),
          _buildSectionHeader('Sistema'),
          _buildSettingsTile(
            context,
            icon: Icons.system_update,
            title: 'Aggiornamento Firmware',
            subtitle: 'Update firmware dispositivo (DFU)',
            color: Colors.orange,
            onTap: () {
              if (device == null) {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                    content: Text('Dispositivo non connesso'),
                    backgroundColor: Colors.red,
                  ),
                );
                return;
              }
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => FirmwareUpdateScreen(
                    service: service,
                    device: device!,
                  ),
                ),
              );
            },
          ),
          const SizedBox(height: 24),
          _buildSectionHeader('Salute'),
          _buildSettingsTile(
            context,
            icon: Icons.favorite,
            title: 'Frequenza Cardiaca',
            subtitle: 'Configurazione HR min/max/goal',
            color: Colors.red,
            onTap: () {
              // TODO: Implementare schermata HR settings
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Funzionalità in arrivo')),
              );
            },
          ),
          const SizedBox(height: 8),
          _buildSettingsTile(
            context,
            icon: Icons.bloodtype,
            title: 'Ossigeno nel Sangue',
            subtitle: 'Modalità SpO2',
            color: Colors.orange,
            onTap: () {
              // TODO: Implementare schermata SpO2 settings
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Funzionalità in arrivo')),
              );
            },
          ),
          const SizedBox(height: 8),
          _buildSettingsTile(
            context,
            icon: Icons.thermostat,
            title: 'Temperatura',
            subtitle: 'Temperatura corporea in tempo reale',
            color: Colors.teal,
            onTap: () {
              // TODO: Implementare schermata temperatura
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Funzionalità in arrivo')),
              );
            },
          ),
          const SizedBox(height: 24),
          _buildSectionHeader('Dispositivo'),
          _buildSettingsTile(
            context,
            icon: Icons.info_outline,
            title: 'Informazioni Dispositivo',
            subtitle: 'Nome, versione firmware, batteria',
            color: Colors.grey[700]!,
            onTap: () {
              // TODO: Implementare schermata device info
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Funzionalità in arrivo')),
              );
            },
          ),
          const SizedBox(height: 8),
          _buildSettingsTile(
            context,
            icon: Icons.power_settings_new,
            title: 'Spegni Dispositivo',
            subtitle: 'Arresta il dispositivo',
            color: Colors.red[700]!,
            onTap: () => _showShutdownDialog(context),
          ),
          const SizedBox(height: 8),
          _buildSettingsTile(
            context,
            icon: Icons.restore,
            title: 'Ripristino',
            subtitle: 'Ripristina il dispositivo',
            color: Colors.orange[700]!,
            onTap: () => _showRestorationDialog(context),
          ),
        ],
      ),
    );
  }

  Widget _buildSectionHeader(String title) {
    return Padding(
      padding: const EdgeInsets.only(left: 8, bottom: 12, top: 8),
      child: Text(
        title,
        style: TextStyle(
          fontSize: 14,
          fontWeight: FontWeight.bold,
          color: Colors.grey[600],
        ),
      ),
    );
  }

  Widget _buildSettingsTile(
    BuildContext context, {
    required IconData icon,
    required String title,
    required String subtitle,
    required Color color,
    required VoidCallback onTap,
  }) {
    return Card(
      elevation: 2,
      child: ListTile(
        leading: Container(
          padding: const EdgeInsets.all(8),
          decoration: BoxDecoration(
            color: color.withOpacity(0.1),
            borderRadius: BorderRadius.circular(8),
          ),
          child: Icon(icon, color: color),
        ),
        title: Text(
          title,
          style: const TextStyle(fontWeight: FontWeight.bold),
        ),
        subtitle: Text(subtitle),
        trailing: const Icon(Icons.chevron_right),
        onTap: onTap,
      ),
    );
  }

  Future<void> _showShutdownDialog(BuildContext context) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Row(
          children: [
            Icon(Icons.warning_amber_rounded, color: Colors.red),
            SizedBox(width: 12),
            Text('Spegni Dispositivo'),
          ],
        ),
        content: const Text(
          'Sei sicuro di voler spegnere il dispositivo?\n\n'
          'Dovrai riaccenderlo manualmente.',
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Annulla'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
            child: const Text('Spegni'),
          ),
        ],
      ),
    );

    if (confirmed == true && context.mounted) {
      try {
        await service.shutdownDevice();
        if (context.mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('✅ Comando di spegnimento inviato'),
              backgroundColor: Colors.green,
            ),
          );
        }
      } catch (e) {
        if (context.mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('❌ Errore: $e'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    }
  }

  Future<void> _showRestorationDialog(BuildContext context) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Row(
          children: [
            Icon(Icons.warning_amber_rounded, color: Colors.orange),
            SizedBox(width: 12),
            Text('Ripristino Dispositivo'),
          ],
        ),
        content: const Text(
          'Sei sicuro di voler ripristinare il dispositivo?\n\n'
          'Questa operazione potrebbe resettare alcune impostazioni.',
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Annulla'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(backgroundColor: Colors.orange),
            child: const Text('Ripristina'),
          ),
        ],
      ),
    );

    if (confirmed == true && context.mounted) {
      try {
        await service.factoryRestoration();
        if (context.mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('✅ Comando di ripristino inviato'),
              backgroundColor: Colors.green,
            ),
          );
        }
      } catch (e) {
        if (context.mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('❌ Errore: $e'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    }
  }
}
