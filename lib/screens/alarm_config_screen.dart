/// Alarm Configuration Screen
/// 
/// Beautiful UI for setting up smart alarms
/// Features:
/// - Time picker for desired wake time
/// - Window duration slider
/// - Enable/disable toggle
/// - Repeat days selector
/// - Device vibration toggle
/// - Phone notification toggle
/// - Visual preview of wake window

import 'package:flutter/material.dart';
import '../models/smart_alarm.dart';
import '../services/smart_alarm_service.dart';
import '../services/sleep_notification_service.dart';

class AlarmConfigScreen extends StatefulWidget {
  final SmartAlarm? existingAlarm;

  const AlarmConfigScreen({Key? key, this.existingAlarm}) : super(key: key);

  @override
  State<AlarmConfigScreen> createState() => _AlarmConfigScreenState();
}

class _AlarmConfigScreenState extends State<AlarmConfigScreen> {
  late SmartAlarmService _alarmService;
  late SleepNotificationService _notificationService;

  // Alarm configuration
  late TimeOfDay _desiredWakeTime;
  late int _windowMinutes;
  late bool _isEnabled;
  late bool _useDeviceVibration;
  late bool _usePhoneNotification;
  late String _label;
  late List<int> _repeatDays;

  @override
  void initState() {
    super.initState();
    _alarmService = SmartAlarmService();
    _notificationService = SleepNotificationService();

    // Initialize from existing alarm or defaults
    if (widget.existingAlarm != null) {
      final alarm = widget.existingAlarm!;
      _desiredWakeTime = TimeOfDay(
        hour: alarm.desiredWakeTime.hour,
        minute: alarm.desiredWakeTime.minute,
      );
      _windowMinutes = alarm.windowMinutes;
      _isEnabled = alarm.isEnabled;
      _useDeviceVibration = alarm.useDeviceVibration;
      _usePhoneNotification = alarm.usePhoneNotification;
      _label = alarm.label;
      _repeatDays = List.from(alarm.repeatDays);
    } else {
      // Defaults
      _desiredWakeTime = const TimeOfDay(hour: 7, minute: 0);
      _windowMinutes = 30;
      _isEnabled = true;
      _useDeviceVibration = true;
      _usePhoneNotification = true;
      _label = 'Wake Up';
      _repeatDays = [];
    }

    // Initialize notification service
    _notificationService.initialize();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Smart Alarm'),
        actions: [
          IconButton(
            icon: const Icon(Icons.check),
            onPressed: _saveAlarm,
          ),
        ],
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          _buildHeader(),
          const SizedBox(height: 24),
          _buildTimePicker(),
          const SizedBox(height: 24),
          _buildWindowSlider(),
          const SizedBox(height: 24),
          _buildWindowPreview(),
          const SizedBox(height: 24),
          _buildRepeatSelector(),
          const SizedBox(height: 24),
          _buildOptionsSection(),
          const SizedBox(height: 32),
          _buildSaveButton(),
        ],
      ),
    );
  }

  Widget _buildHeader() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            Row(
              children: [
                Container(
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: Theme.of(context).primaryColor.withOpacity(0.1),
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Icon(
                    Icons.alarm,
                    size: 32,
                    color: Theme.of(context).primaryColor,
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Smart Wake Alarm',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Wake during light sleep for better energy',
                        style: TextStyle(
                          fontSize: 14,
                          color: Colors.grey[600],
                        ),
                      ),
                    ],
                  ),
                ),
                Switch(
                  value: _isEnabled,
                  onChanged: (value) {
                    setState(() {
                      _isEnabled = value;
                    });
                  },
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTimePicker() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: InkWell(
        onTap: _selectTime,
        borderRadius: BorderRadius.circular(16),
        child: Padding(
          padding: const EdgeInsets.all(20),
          child: Row(
            children: [
              Icon(Icons.schedule, color: Theme.of(context).primaryColor),
              const SizedBox(width: 16),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Desired Wake Time',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Latest time you want to wake up',
                      style: TextStyle(
                        fontSize: 12,
                        color: Colors.grey[600],
                      ),
                    ),
                  ],
                ),
              ),
              Text(
                _formatTimeOfDay(_desiredWakeTime),
                style: const TextStyle(
                  fontSize: 32,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildWindowSlider() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(Icons.access_time, color: Theme.of(context).primaryColor),
                const SizedBox(width: 16),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Smart Wake Window',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Time range to find optimal light sleep',
                        style: TextStyle(
                          fontSize: 12,
                          color: Colors.grey[600],
                        ),
                      ),
                    ],
                  ),
                ),
                Text(
                  '$_windowMinutes min',
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Slider(
              value: _windowMinutes.toDouble(),
              min: 15,
              max: 60,
              divisions: 9,
              label: '$_windowMinutes minutes',
              onChanged: (value) {
                setState(() {
                  _windowMinutes = value.toInt();
                });
              },
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text('15 min', style: TextStyle(color: Colors.grey[600], fontSize: 12)),
                Text('30 min', style: TextStyle(color: Colors.grey[600], fontSize: 12)),
                Text('60 min', style: TextStyle(color: Colors.grey[600], fontSize: 12)),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildWindowPreview() {
    final windowStart = _getWindowStart();
    final windowEnd = _desiredWakeTime;

    return Card(
      elevation: 2,
      color: Theme.of(context).primaryColor.withOpacity(0.05),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            Row(
              children: [
                const Icon(Icons.info_outline, size: 20),
                const SizedBox(width: 8),
                const Text(
                  'Wake Window Preview',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Column(
                  children: [
                    const Text(
                      'Earliest',
                      style: TextStyle(fontSize: 12, color: Colors.grey),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      _formatTimeOfDay(windowStart),
                      style: const TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ],
                ),
                const Icon(Icons.arrow_forward, size: 32),
                Column(
                  children: [
                    const Text(
                      'Latest',
                      style: TextStyle(fontSize: 12, color: Colors.grey),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      _formatTimeOfDay(windowEnd),
                      style: const TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildRepeatSelector() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(Icons.repeat, color: Theme.of(context).primaryColor),
                const SizedBox(width: 16),
                const Text(
                  'Repeat',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Wrap(
              spacing: 8,
              children: [
                _buildDayChip('Sun', 0),
                _buildDayChip('Mon', 1),
                _buildDayChip('Tue', 2),
                _buildDayChip('Wed', 3),
                _buildDayChip('Thu', 4),
                _buildDayChip('Fri', 5),
                _buildDayChip('Sat', 6),
              ],
            ),
            if (_repeatDays.isNotEmpty) ...[
              const SizedBox(height: 12),
              Text(
                _getRepeatDescription(),
                style: TextStyle(
                  fontSize: 12,
                  color: Colors.grey[600],
                ),
              ),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildDayChip(String label, int day) {
    final isSelected = _repeatDays.contains(day);

    return FilterChip(
      label: Text(label),
      selected: isSelected,
      onSelected: (selected) {
        setState(() {
          if (selected) {
            _repeatDays.add(day);
            _repeatDays.sort();
          } else {
            _repeatDays.remove(day);
          }
        });
      },
    );
  }

  Widget _buildOptionsSection() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          children: [
            SwitchListTile(
              title: const Text('Device Vibration'),
              subtitle: const Text('Vibrate device when alarm triggers'),
              secondary: const Icon(Icons.vibration),
              value: _useDeviceVibration,
              onChanged: (value) {
                setState(() {
                  _useDeviceVibration = value;
                });
              },
            ),
            const Divider(),
            SwitchListTile(
              title: const Text('Phone Notification'),
              subtitle: const Text('Show notification on phone'),
              secondary: const Icon(Icons.notifications_active),
              value: _usePhoneNotification,
              onChanged: (value) {
                setState(() {
                  _usePhoneNotification = value;
                });
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSaveButton() {
    return ElevatedButton(
      onPressed: _saveAlarm,
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(vertical: 16),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(12),
        ),
      ),
      child: const Text(
        'Save Alarm',
        style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
      ),
    );
  }

  Future<void> _selectTime() async {
    final time = await showTimePicker(
      context: context,
      initialTime: _desiredWakeTime,
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            timePickerTheme: TimePickerThemeData(
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(16),
              ),
            ),
          ),
          child: child!,
        );
      },
    );

    if (time != null) {
      setState(() {
        _desiredWakeTime = time;
      });
    }
  }

  TimeOfDay _getWindowStart() {
    final totalMinutes = _desiredWakeTime.hour * 60 + _desiredWakeTime.minute;
    final startMinutes = totalMinutes - _windowMinutes;
    return TimeOfDay(
      hour: (startMinutes ~/ 60) % 24,
      minute: startMinutes % 60,
    );
  }

  String _formatTimeOfDay(TimeOfDay time) {
    final hour = time.hour.toString().padLeft(2, '0');
    final minute = time.minute.toString().padLeft(2, '0');
    return '$hour:$minute';
  }

  String _getRepeatDescription() {
    if (_repeatDays.isEmpty) return 'One time alarm';
    if (_repeatDays.length == 7) return 'Every day';
    if (_repeatDays.length == 5 && 
        !_repeatDays.contains(0) && 
        !_repeatDays.contains(6)) {
      return 'Weekdays only';
    }
    if (_repeatDays.length == 2 && 
        _repeatDays.contains(0) && 
        _repeatDays.contains(6)) {
      return 'Weekends only';
    }

    final dayNames = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return 'Repeats ${_repeatDays.map((d) => dayNames[d]).join(', ')}';
  }

  void _saveAlarm() async {
    // Create alarm from current settings
    final now = DateTime.now();
    final desiredDateTime = DateTime(
      now.year,
      now.month,
      now.day,
      _desiredWakeTime.hour,
      _desiredWakeTime.minute,
    );

    // If time is in the past, move to tomorrow
    final adjustedDateTime = desiredDateTime.isBefore(now)
        ? desiredDateTime.add(const Duration(days: 1))
        : desiredDateTime;

    final alarm = SmartAlarm(
      desiredWakeTime: adjustedDateTime,
      windowMinutes: _windowMinutes,
      isEnabled: _isEnabled,
      useDeviceVibration: _useDeviceVibration,
      usePhoneNotification: _usePhoneNotification,
      label: _label,
      repeatDays: _repeatDays,
    );

    // Set alarm in service
    _alarmService.setAlarm(alarm);

    // Schedule notification
    if (_isEnabled && _usePhoneNotification) {
      await _notificationService.scheduleSmartAlarm(alarm);
    }

    // Show confirmation
    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            _isEnabled
                ? '⏰ Smart alarm set for ${_formatTimeOfDay(_desiredWakeTime)}'
                : '⏰ Alarm disabled',
          ),
          backgroundColor: _isEnabled ? Colors.green : Colors.grey,
          behavior: SnackBarBehavior.floating,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
        ),
      );

      Navigator.pop(context, alarm);
    }
  }

  @override
  void dispose() {
    super.dispose();
  }
}
