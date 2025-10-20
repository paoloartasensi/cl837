import 'package:flutter/material.dart';
import '../chileaf_extended_service.dart';
import '../services/ble_protocol/official_commands_complete.dart';
import 'heart_rate_test_widget.dart';

/// Widget per controllare e configurare Heart Rate e testare vibrazione
class HRControlWidget extends StatefulWidget {
  final ChileafExtendedService service;

  const HRControlWidget({
    super.key,
    required this.service,
  });

  @override
  State<HRControlWidget> createState() => _HRControlWidgetState();
}

class _HRControlWidgetState extends State<HRControlWidget> {
  // HR Configuration state
  final Map<String, dynamic> _hrConfig = {
    'min': 60,
    'max': 180,
    'goal': 120,
    'alarmEnabled': false,
  };

  @override
  void initState() {
    super.initState();
    _setupHRCallbacks();
  }

  void _setupHRCallbacks() {
    widget.service.setHRCallbacks(
      onConfigReceived: (min, max, goal, alarmEnabled) {
        if (mounted) {
          setState(() {
            _hrConfig['min'] = min;
            _hrConfig['max'] = max;
            _hrConfig['goal'] = goal;
            _hrConfig['alarmEnabled'] = alarmEnabled;
          });
          debugPrint('🔄 HR Config updated in UI: Min=$min, Max=$max, Goal=$goal, Alarm=$alarmEnabled');
        }
      },
      onError: (error) {
        debugPrint('❌ HR Config error: $error');
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('❤️ HR Control'),
        backgroundColor: Colors.red.shade100,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            // Current HR Configuration Display
            Card(
              elevation: 4,
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      '❤️ Current HR Configuration',
                      style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 12),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        Column(
                          children: [
                            const Text('Min HR', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text('${_hrConfig['min']} BPM', style: const TextStyle(color: Colors.blue)),
                          ],
                        ),
                        Column(
                          children: [
                            const Text('Max HR', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text('${_hrConfig['max']} BPM', style: const TextStyle(color: Colors.red)),
                          ],
                        ),
                        Column(
                          children: [
                            const Text('Goal HR', style: TextStyle(fontWeight: FontWeight.bold)),
                            Text('${_hrConfig['goal']} BPM', style: const TextStyle(color: Colors.green)),
                          ],
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Text('HR Alarm:', style: TextStyle(fontWeight: FontWeight.bold)),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
                          decoration: BoxDecoration(
                            color: _hrConfig['alarmEnabled'] ? Colors.green : Colors.grey,
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Text(
                            _hrConfig['alarmEnabled'] ? 'ENABLED' : 'DISABLED',
                            style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    Row(
                      children: [
                        Expanded(
                          child: ElevatedButton.icon(
                            icon: const Icon(Icons.refresh),
                            label: const Text('Read Config'),
                            onPressed: _readHRConfiguration,
                          ),
                        ),
                        const SizedBox(width: 12),
                        Expanded(
                          child: ElevatedButton.icon(
                            icon: const Icon(Icons.edit),
                            label: const Text('Set Config'),
                            onPressed: _showHRConfigDialog,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 16),
            
            // Essential Test Buttons
            Expanded(
              child: GridView.count(
                crossAxisCount: 2,
                crossAxisSpacing: 12,
                mainAxisSpacing: 12,
                childAspectRatio: 1.2,
                children: [
                  _buildQuickTestButton(
                    '🔥 SHUTDOWN TEST',
                    'CONFIRMED VIBRATION\n(Device shuts down)',
                    Colors.red.shade600,
                    _testShutdownVibration,
                  ),
                  _buildQuickTestButton(
                    '🔄 ALARM TOGGLE',
                    'Test ON/OFF\nFunctionality',
                    Colors.indigo.shade600,
                    _testAlarmToggle,
                  ),
                  _buildQuickTestButton(
                    '🔔 NOTIFICATIONS',
                    'Test Call/Message\nVibrations',
                    Colors.blue.shade600,
                    _testNotificationVibrations,
                  ),
                  _buildQuickTestButton(
                    '🚨 TRIGGER ALARM',
                    'Test HR Threshold\nVibration',
                    Colors.orange.shade600,
                    _testHRAlarmVibration,
                  ),
                  _buildQuickTestButton(
                    '🔍 Test Alternative Commands',
                    'Test Alternative\nCommand Set',
                    Colors.deepPurple.shade600,
                    _testDecodeResponses,
                  ),
                  _buildQuickTestButton(
                    '� HR SYSTEM TEST',
                    'Complete HR Test\nAge & Manual Modes',
                    Colors.green.shade600,
                    _openHRSystemTest,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
  
  // Quick test button builder
  Widget _buildQuickTestButton(String title, String description, Color color, VoidCallback onPressed) {
    return Card(
      elevation: 4,
      child: InkWell(
        onTap: onPressed,
        borderRadius: BorderRadius.circular(8),
        child: Container(
          padding: const EdgeInsets.all(12),
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [color.withOpacity(0.8), color],
            ),
            borderRadius: BorderRadius.circular(8),
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                title,
                style: const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 16,
                ),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 8),
              Text(
                description,
                style: const TextStyle(
                  color: Colors.white70,
                  fontSize: 12,
                ),
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _readHRConfiguration() async {
    try {
      debugPrint('🔍 Reading HR Configuration from device...');
      
      // Read HR configuration (min/max/goal)
      final getHRStatusCommand = OfficialChileafCommands.getHeartRateStatus();
      await widget.service.sendRawCommand(getHRStatusCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Read HR alarm status separately
      debugPrint('🔍 Reading HR Alarm Status from device...');
      final getHRAlarmCommand = OfficialChileafCommands.getHeartRateAlarm();
      await widget.service.sendRawCommand(getHRAlarmCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      final getHRMaxCommand = OfficialChileafCommands.getHeartRateMax();
      await widget.service.sendRawCommand(getHRMaxCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      debugPrint('🔍 HR Configuration commands sent. Check Flutter logs for decoded values.');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('HR Configuration read - check Flutter logs')),
        );
      }
      
    } catch (e) {
      debugPrint('❌ Error reading HR configuration: $e');
    }
  }
  
  // Test specifically the alarm toggle functionality
  Future<void> _testAlarmToggle() async {
    try {
      debugPrint('🔄 ALARM TOGGLE TEST: Testing ON/OFF functionality');
      
      // Step 1: Read current alarm status
      debugPrint('📖 Step 1: Reading current alarm status');
      final getAlarmCommand = OfficialChileafCommands.getHeartRateAlarm();
      await widget.service.sendRawCommand(getAlarmCommand);
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 2: Turn alarm ON
      debugPrint('🔛 Step 2: Setting alarm ON');
      final setAlarmOnCommand = OfficialChileafCommands.setHeartRateAlarm(true);
      await widget.service.sendRawCommand(setAlarmOnCommand);
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 3: Read alarm status again (should be ON)
      debugPrint('📖 Step 3: Verifying alarm is ON');
      await widget.service.sendRawCommand(getAlarmCommand);
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 4: Turn alarm OFF
      debugPrint('🔴 Step 4: Setting alarm OFF');
      final setAlarmOffCommand = OfficialChileafCommands.setHeartRateAlarm(false);
      await widget.service.sendRawCommand(setAlarmOffCommand);
      await Future.delayed(const Duration(seconds: 2));
      
      // Step 5: Read alarm status final time (should be OFF)
      debugPrint('📖 Step 5: Verifying alarm is OFF');
      await widget.service.sendRawCommand(getAlarmCommand);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('🔄 Alarm toggle test complete - check logs!')),
        );
      }
      
      debugPrint('✅ Alarm toggle test sequence completed');
      
    } catch (e) {
      debugPrint('❌ Error in alarm toggle test: $e');
    }
  }

  void _showHRConfigDialog() {
    int tempMin = _hrConfig['min'] ?? 60;
    int tempMax = _hrConfig['max'] ?? 180;
    int tempGoal = _hrConfig['goal'] ?? 120;
    bool tempAlarm = _hrConfig['alarmEnabled'] ?? false;
    
    showDialog(
      context: context,
      builder: (context) => StatefulBuilder(
        builder: (context, setDialogState) => AlertDialog(
          title: const Text('❤️ HR Configuration'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Row(
                children: [
                  const Expanded(child: Text('Min HR:')),
                  SizedBox(
                    width: 80,
                    child: TextFormField(
                      initialValue: tempMin.toString(),
                      keyboardType: TextInputType.number,
                      onChanged: (value) => tempMin = int.tryParse(value) ?? tempMin,
                    ),
                  ),
                  const Text(' BPM'),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Expanded(child: Text('Max HR:')),
                  SizedBox(
                    width: 80,
                    child: TextFormField(
                      initialValue: tempMax.toString(),
                      keyboardType: TextInputType.number,
                      onChanged: (value) => tempMax = int.tryParse(value) ?? tempMax,
                    ),
                  ),
                  const Text(' BPM'),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Expanded(child: Text('Goal HR:')),
                  SizedBox(
                    width: 80,
                    child: TextFormField(
                      initialValue: tempGoal.toString(),
                      keyboardType: TextInputType.number,
                      onChanged: (value) => tempGoal = int.tryParse(value) ?? tempGoal,
                    ),
                  ),
                  const Text(' BPM'),
                ],
              ),
              const SizedBox(height: 12),
              SwitchListTile(
                title: const Text('HR Alarm'),
                value: tempAlarm,
                onChanged: (value) => setDialogState(() => tempAlarm = value),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () async {
                await _setHRConfiguration(tempMin, tempMax, tempGoal, tempAlarm);
                if (context.mounted) Navigator.pop(context);
              },
              child: const Text('Apply'),
            ),
          ],
        ),
      ),
    );
  }
  
  Future<void> _setHRConfiguration(int min, int max, int goal, bool alarmEnabled) async {
    try {
      debugPrint('🔧 Setting HR Configuration: Min=$min, Max=$max, Goal=$goal, Alarm=$alarmEnabled');
      
      // Set heart rate status (min, max, goal) using official command
      final setHRCommand = OfficialChileafCommands.setHeartRateStatus(min, max, goal);
      await widget.service.sendRawCommand(setHRCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Set alarm switch using official command
      final setAlarmCommand = OfficialChileafCommands.setHeartRateAlarm(alarmEnabled);
      await widget.service.sendRawCommand(setAlarmCommand);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // IMPORTANT: Since device doesn't respond to HR commands,
      // we update the UI immediately with the values we sent
      setState(() {
        _hrConfig['min'] = min;
        _hrConfig['max'] = max;
        _hrConfig['goal'] = goal;
        _hrConfig['alarmEnabled'] = alarmEnabled;
      });
      
      debugPrint('✅ HR Configuration applied successfully (UI updated manually)');
      debugPrint('🔄 UI state: Min=$min, Max=$max, Goal=$goal, Alarm=$alarmEnabled');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('HR Config set: Min=$min, Max=$max, Goal=$goal, Alarm=${alarmEnabled ? "ON" : "OFF"}'),
            backgroundColor: alarmEnabled ? Colors.green : Colors.orange,
          ),
        );
      }
      
      // Still try to refresh, but don't rely on device response
      await Future.delayed(const Duration(milliseconds: 1000));
      debugPrint('🔍 Attempting to read back configuration (may not work)...');
      await _readHRConfiguration();
      
    } catch (e) {
      debugPrint('❌ Error setting HR configuration: $e');
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e')),
        );
      }
    }
  }

  // ===== TEST FUNCTIONS =====
  
  Future<void> _testShutdownVibration() async {
    bool? confirm = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('⚠️ Shutdown Test'),
        content: const Text('This will cause vibration but SHUTDOWN the device. Continue?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context, false), child: const Text('Cancel')),
          ElevatedButton(onPressed: () => Navigator.pop(context, true), child: const Text('YES')),
        ],
      ),
    );
    
    if (confirm == true) {
      try {
        final shutdownCommand = OfficialChileafCommands.deviceShutdown();
        await widget.service.sendRawCommand(shutdownCommand);
        debugPrint('🔥 SHUTDOWN command sent - device should vibrate and turn off');
      } catch (e) {
        debugPrint('❌ Shutdown test error: $e');
      }
    }
  }

  Future<void> _testHRAlarmVibration() async {
    try {
      debugPrint('🚨 HR ALARM TEST: Multi-strategy vibration approach');
      
      // STRATEGY 1: Set extreme low thresholds first (40-50 BPM)
      debugPrint('📊 Strategy 1: Extreme low thresholds (40-50 BPM)');
      final setLowMax = OfficialChileafCommands.setHeartRateStatus(40, 50, 45);
      await widget.service.sendRawCommand(setLowMax);
      await Future.delayed(const Duration(milliseconds: 500));
      
      // Enable alarm
      final enableAlarm = OfficialChileafCommands.setHeartRateAlarm(true);
      await widget.service.sendRawCommand(enableAlarm);
      await Future.delayed(const Duration(seconds: 1));
      
      // STRATEGY 2: Quick switch to high thresholds (150-200 BPM) to simulate crossing
      debugPrint('📊 Strategy 2: Quick switch to high thresholds (150-200 BPM)');
      final setHighMax = OfficialChileafCommands.setHeartRateStatus(150, 200, 175);
      await widget.service.sendRawCommand(setHighMax);
      await Future.delayed(const Duration(seconds: 1));
      
      // STRATEGY 3: Try notification commands (known to cause vibration)
      debugPrint('📊 Strategy 3: Notification vibration triggers');
      
      // Call notification (comando 0x74 sub-tipo 0x01)
      List<int> callNotification = [0xFF, 0x07, 0x74, 0x00, 0x01, 0x01, 0x00];
      int checksum1 = _calculateVibrationChecksum(callNotification.sublist(0, callNotification.length - 1));
      callNotification[callNotification.length - 1] = checksum1;
      await widget.service.sendRawCommand(callNotification);
      debugPrint('📞 Call notification sent');
      await Future.delayed(const Duration(milliseconds: 800));
      
      // Message notification (comando 0x74 sub-tipo 0x02)
      List<int> messageNotification = [0xFF, 0x07, 0x74, 0x00, 0x02, 0x01, 0x00];
      int checksum2 = _calculateVibrationChecksum(messageNotification.sublist(0, messageNotification.length - 1));
      messageNotification[messageNotification.length - 1] = checksum2;
      await widget.service.sendRawCommand(messageNotification);
      debugPrint('💬 Message notification sent');
      await Future.delayed(const Duration(milliseconds: 800));
      
      // Alarm notification (comando 0x74 sub-tipo 0x03)
      List<int> alarmNotification = [0xFF, 0x07, 0x74, 0x00, 0x03, 0x01, 0x00];
      int checksum3 = _calculateVibrationChecksum(alarmNotification.sublist(0, alarmNotification.length - 1));
      alarmNotification[alarmNotification.length - 1] = checksum3;
      await widget.service.sendRawCommand(alarmNotification);
      debugPrint('⏰ Alarm notification sent');
      await Future.delayed(const Duration(seconds: 1));
      
      // STRATEGY 4: Sedentary reminder (comando 0x75 sub-tipo vibrazione)
      List<int> sedentaryReminder = [0xFF, 0x06, 0x75, 0x00, 0x01, 0x00];
      int checksum4 = _calculateVibrationChecksum(sedentaryReminder.sublist(0, sedentaryReminder.length - 1));
      sedentaryReminder[sedentaryReminder.length - 1] = checksum4;
      await widget.service.sendRawCommand(sedentaryReminder);
      debugPrint('🪑 Sedentary reminder sent');
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Multi-strategy vibration test complete - watch device!')),
        );
      }
      
      // Auto-restore normal values after test
      await Future.delayed(const Duration(seconds: 8));
      final restoreNormal = OfficialChileafCommands.setHeartRateStatus(60, 180, 120);
      await widget.service.sendRawCommand(restoreNormal);
      debugPrint('🔧 Auto-restored normal HR thresholds');
      
    } catch (e) {
      debugPrint('❌ Error in HR alarm test: $e');
    }
  }
  
  // Helper method for vibration checksum calculation
  int _calculateVibrationChecksum(List<int> frame) {
    int sum = 0;
    for (int byte in frame) {
      sum += byte;
    }
    return ((-sum) & 0xFF) ^ 0x3A; // Java algorithm: ((-sum) & 0xFF) ^ 0x3A
  }
  
  // Test direct notification vibrations
  Future<void> _testNotificationVibrations() async {
    try {
      debugPrint('🔔 NOTIFICATION VIBRATION TEST: Testing direct triggers');
      
      // Test 1: Call notification
      debugPrint('📞 Test 1/5: Call notification');
      List<int> callCmd = [0xFF, 0x07, 0x74, 0x00, 0x01, 0x01, 0x00];
      callCmd[callCmd.length - 1] = _calculateVibrationChecksum(callCmd.sublist(0, callCmd.length - 1));
      await widget.service.sendRawCommand(callCmd);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 2: Message notification
      debugPrint('💬 Test 2/5: Message notification');
      List<int> msgCmd = [0xFF, 0x07, 0x74, 0x00, 0x02, 0x01, 0x00];
      msgCmd[msgCmd.length - 1] = _calculateVibrationChecksum(msgCmd.sublist(0, msgCmd.length - 1));
      await widget.service.sendRawCommand(msgCmd);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 3: Alarm notification  
      debugPrint('⏰ Test 3/5: Alarm notification');
      List<int> alarmCmd = [0xFF, 0x07, 0x74, 0x00, 0x03, 0x01, 0x00];
      alarmCmd[alarmCmd.length - 1] = _calculateVibrationChecksum(alarmCmd.sublist(0, alarmCmd.length - 1));
      await widget.service.sendRawCommand(alarmCmd);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 4: Sedentary reminder
      debugPrint('🪑 Test 4/5: Sedentary reminder');
      List<int> sedentaryCmd = [0xFF, 0x06, 0x75, 0x00, 0x01, 0x00];
      sedentaryCmd[sedentaryCmd.length - 1] = _calculateVibrationChecksum(sedentaryCmd.sublist(0, sedentaryCmd.length - 1));
      await widget.service.sendRawCommand(sedentaryCmd);
      await Future.delayed(const Duration(seconds: 2));
      
      // Test 5: Anti-lost alert
      debugPrint('🔍 Test 5/5: Anti-lost alert');
      List<int> antiLostCmd = [0xFF, 0x07, 0x74, 0x00, 0x04, 0x01, 0x00];
      antiLostCmd[antiLostCmd.length - 1] = _calculateVibrationChecksum(antiLostCmd.sublist(0, antiLostCmd.length - 1));
      await widget.service.sendRawCommand(antiLostCmd);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('🔔 Notification vibration tests complete!')),
        );
      }
      
      debugPrint('🔔 All notification vibration tests completed');
      
    } catch (e) {
      debugPrint('❌ Error in notification vibration test: $e');
    }
  }

  Future<void> _testDecodeResponses() async {
    try {
      debugPrint('🔍 DEBUG MODE: Testing commands for detailed responses');
      
      // Test various commands to trigger responses
      final commands = [
        OfficialChileafCommands.getHeartRateStatus(),
        OfficialChileafCommands.getHeartRateAlarm(), 
        OfficialChileafCommands.getHeartRateMax(),
        OfficialChileafCommands.getUserInfo(),
      ];
      
      for (int i = 0; i < commands.length; i++) {
        debugPrint('🔍 TEST ${i+1}/${commands.length}: Sending command');
        await widget.service.sendRawCommand(commands[i]);
        await Future.delayed(const Duration(milliseconds: 1500));
      }
      
      // TEST ALTERNATIVE COMMANDS that might work
      debugPrint('🔍 TESTING ALTERNATIVE COMMANDS:');
      
      // Test basic device commands that usually work
      debugPrint('🔍 Test 1: Basic device info');
      List<int> deviceInfoCmd = [0xFF, 0x05, 0x03, 0x00, 0x07]; // getUserInfo
      await widget.service.sendRawCommand(deviceInfoCmd);
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Test time sync (often works)
      debugPrint('🔍 Test 2: Time sync command');
      List<int> timeSyncCmd = [0xFF, 0x0A, 0x01, 0x00, 0x19, 0x08, 0x13, 0x0C, 0x00, 0x00, 0x5E]; // Date/time
      await widget.service.sendRawCommand(timeSyncCmd);
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Test vibration command directly
      debugPrint('🔍 Test 3: Direct vibration test');
      List<int> vibrateCmd = [0xFF, 0x06, 0x04, 0x00, 0x01, 0x09]; // Find device vibration
      await widget.service.sendRawCommand(vibrateCmd);
      await Future.delayed(const Duration(milliseconds: 1000));
      
      // Test factory reset command (CAREFUL!)
      debugPrint('🔍 Test 4: Factory reset command (response only)');
      List<int> factoryCmd = [0xFF, 0x05, 0x06, 0x00, 0x0A]; // Factory reset query
      await widget.service.sendRawCommand(factoryCmd);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('🔍 Alternative commands tested - check logs')),
        );
      }
      
    } catch (e) {
      debugPrint('❌ Error in decode test: $e');
    }
  }

  void _openHRSystemTest() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => HeartRateTestWidget(service: widget.service),
      ),
    );
  }

}
