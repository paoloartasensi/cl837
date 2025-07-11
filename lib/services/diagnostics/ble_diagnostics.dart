import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../ble_protocol/command_builder.dart';

/// Diagnostica e test per connessione BLE
/// Fornisce metodi per verificare stato connessione e caratteristiche
class BLEDiagnostics {
  final BluetoothCharacteristic? _rxCharacteristic;
  final BluetoothCharacteristic? _txCharacteristic;

  BLEDiagnostics(this._rxCharacteristic, this._txCharacteristic);

  /// Verifica stato della connessione BLE
  Future<bool> checkBLEConnection() async {
    try {
      if (_rxCharacteristic == null || _txCharacteristic == null) {
        debugPrint('❌ Caratteristiche BLE non inizializzate');
        return false;
      }

      final device = _rxCharacteristic!.device;
      final connectionState = await device.connectionState.first;
      
      debugPrint('🔍 Stato connessione dispositivo: $connectionState');
      
      if (connectionState != BluetoothConnectionState.connected) {
        debugPrint('❌ Dispositivo non connesso');
        return false;
      }
      
      debugPrint('✅ Dispositivo connesso correttamente');
      return true;
    } catch (e) {
      debugPrint('❌ Errore controllo connessione BLE: $e');
      return false;
    }
  }

  /// Diagnostica completa problemi di comunicazione BLE
  Future<void> diagnoseBLEIssues() async {
    debugPrint('🔧 Avvio diagnostica BLE...');
    
    try {
      // Check 1: Connessione
      final isConnected = await checkBLEConnection();
      if (!isConnected) {
        debugPrint('❌ PROBLEMA: Dispositivo non connesso');
        return;
      }
      
      // Check 2: Caratteristiche
      if (_rxCharacteristic == null || _txCharacteristic == null) {
        debugPrint('❌ PROBLEMA: Caratteristiche BLE non trovate');
        return;
      }
      
      // Check 3: Proprietà delle caratteristiche
      debugPrint('📊 RX Char Properties: ${_rxCharacteristic!.properties}');
      debugPrint('📊 TX Char Properties: ${_txCharacteristic!.properties}');
      
      // Check 4: Notifiche abilitate
      final isNotifying = _txCharacteristic!.isNotifying;
      debugPrint('📊 TX Notifications enabled: $isNotifying');
      
      if (!isNotifying) {
        debugPrint('⚠️ Tentativo di riabilitare notifiche...');
        try {
          await _txCharacteristic!.setNotifyValue(true);
          debugPrint('✅ Notifiche riabilitate');
        } catch (e) {
          debugPrint('❌ Impossibile riabilitare notifiche: $e');
        }
      }
      
      // Check 5: Test invio comando semplice
      debugPrint('🧪 Test invio comando base...');
      try {
        await _sendTestCommand();
        debugPrint('✅ Comando base inviato con successo');
      } catch (e) {
        debugPrint('❌ Errore invio comando base: $e');
      }
      
      debugPrint('🔧 Diagnostica BLE completata');
      
    } catch (e) {
      debugPrint('❌ Errore durante diagnostica BLE: $e');
    }
  }

  /// Test caratteristiche BLE
  Future<Map<String, dynamic>> testCharacteristics() async {
    final results = <String, dynamic>{};
    
    try {
      // Test RX characteristic
      if (_rxCharacteristic != null) {
        results['rxCharacteristic'] = {
          'uuid': _rxCharacteristic!.uuid.toString(),
          'canWrite': _rxCharacteristic!.properties.write,
          'canWriteWithoutResponse': _rxCharacteristic!.properties.writeWithoutResponse,
          'canRead': _rxCharacteristic!.properties.read,
        };
      } else {
        results['rxCharacteristic'] = 'NOT_FOUND';
      }

      // Test TX characteristic
      if (_txCharacteristic != null) {
        results['txCharacteristic'] = {
          'uuid': _txCharacteristic!.uuid.toString(),
          'canNotify': _txCharacteristic!.properties.notify,
          'isNotifying': _txCharacteristic!.isNotifying,
          'canRead': _txCharacteristic!.properties.read,
        };
      } else {
        results['txCharacteristic'] = 'NOT_FOUND';
      }

      // Test device connection
      if (_rxCharacteristic != null) {
        final device = _rxCharacteristic!.device;
        final connectionState = await device.connectionState.first;
        results['deviceConnection'] = {
          'state': connectionState.toString(),
          'deviceId': device.remoteId.toString(),
          'deviceName': device.platformName,
        };
      }

    } catch (e) {
      results['error'] = e.toString();
    }

    return results;
  }

  /// Ottiene informazioni dettagliate sulle caratteristiche
  Map<String, dynamic> getCharacteristicsInfo() {
    final info = <String, dynamic>{};

    if (_rxCharacteristic != null) {
      info['rxCharacteristic'] = {
        'uuid': _rxCharacteristic!.uuid.toString(),
        'properties': {
          'broadcast': _rxCharacteristic!.properties.broadcast,
          'read': _rxCharacteristic!.properties.read,
          'writeWithoutResponse': _rxCharacteristic!.properties.writeWithoutResponse,
          'write': _rxCharacteristic!.properties.write,
          'notify': _rxCharacteristic!.properties.notify,
          'indicate': _rxCharacteristic!.properties.indicate,
        },
      };
    }

    if (_txCharacteristic != null) {
      info['txCharacteristic'] = {
        'uuid': _txCharacteristic!.uuid.toString(),
        'isNotifying': _txCharacteristic!.isNotifying,
        'properties': {
          'broadcast': _txCharacteristic!.properties.broadcast,
          'read': _txCharacteristic!.properties.read,
          'writeWithoutResponse': _txCharacteristic!.properties.writeWithoutResponse,
          'write': _txCharacteristic!.properties.write,
          'notify': _txCharacteristic!.properties.notify,
          'indicate': _txCharacteristic!.properties.indicate,
        },
      };
    }

    return info;
  }

  /// Invia comando di test per verificare comunicazione
  Future<void> _sendTestCommand() async {
    if (_rxCharacteristic == null) {
      throw Exception('RX characteristic not available');
    }

    final testCommand = CommandBuilder.buildTemperatureDataRequest();
    
    debugPrint('📡 Sending test command: ${testCommand.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ')}');
    
    try {
      if (_rxCharacteristic!.properties.writeWithoutResponse) {
        await _rxCharacteristic!.write(testCommand, withoutResponse: true);
      } else {
        await _rxCharacteristic!.write(testCommand, withoutResponse: false);
      }
    } catch (e) {
      throw Exception('Test command failed: $e');
    }
  }
}
