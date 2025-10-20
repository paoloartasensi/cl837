/// Sleep Notification Service
/// 
/// Handles local notifications for sleep tracking events:
/// - Sleep onset detected
/// - Wake detected
/// - Smart alarm trigger
/// - Sleep score available
/// 
/// Uses flutter_local_notifications for cross-platform support

import 'dart:typed_data';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import '../models/sleep_score.dart';
import '../models/smart_alarm.dart';
import '../services/sleep_onset_detector.dart';

class SleepNotificationService {
  static final SleepNotificationService _instance = SleepNotificationService._internal();
  factory SleepNotificationService() => _instance;
  SleepNotificationService._internal();

  final FlutterLocalNotificationsPlugin _notifications = FlutterLocalNotificationsPlugin();
  bool _initialized = false;

  /// Initialize notification service
  Future<void> initialize() async {
    if (_initialized) return;

    const androidSettings = AndroidInitializationSettings('@mipmap/ic_launcher');
    const iosSettings = DarwinInitializationSettings(
      requestAlertPermission: true,
      requestBadgePermission: true,
      requestSoundPermission: true,
    );

    const initSettings = InitializationSettings(
      android: androidSettings,
      iOS: iosSettings,
    );

    await _notifications.initialize(
      initSettings,
      onDidReceiveNotificationResponse: _onNotificationTapped,
    );

    _initialized = true;
  }

  /// Request notification permissions (iOS)
  Future<bool> requestPermissions() async {
    if (!_initialized) await initialize();

    final result = await _notifications
        .resolvePlatformSpecificImplementation<
            IOSFlutterLocalNotificationsPlugin>()
        ?.requestPermissions(
          alert: true,
          badge: true,
          sound: true,
        );

    return result ?? true; // Android doesn't need explicit permission
  }

  /// Show sleep onset notification
  Future<void> showSleepOnsetNotification(SleepOnsetEvent event) async {
    if (!_initialized) await initialize();

    final timeStr = _formatTime(event.timestamp);
    
    await _notifications.show(
      1, // Notification ID
      '💤 Sleep Detected',
      'You fell asleep at $timeStr in ${event.initialPhase.displayName}',
      _getNotificationDetails(priority: Priority.high),
      payload: 'sleep_onset:${event.timestamp.toIso8601String()}',
    );
  }

  /// Show wake notification
  Future<void> showWakeNotification(SleepWakeEvent event) async {
    if (!_initialized) await initialize();

    final timeStr = _formatTime(event.timestamp);
    final duration = _formatDuration(event.duration);
    
    await _notifications.show(
      2,
      '☀️ Good Morning!',
      'You woke up at $timeStr after $duration of sleep',
      _getNotificationDetails(priority: Priority.high),
      payload: 'wake:${event.timestamp.toIso8601String()}',
    );
  }

  /// Show sleep score notification
  Future<void> showSleepScoreNotification(SleepScore score) async {
    if (!_initialized) await initialize();

    await _notifications.show(
      3,
      '${score.emoji} Sleep Score: ${score.totalScore.toStringAsFixed(0)}/100',
      score.rating.description,
      _getNotificationDetails(priority: Priority.defaultPriority),
      payload: 'score:${score.calculatedAt.toIso8601String()}',
    );
  }

  /// Show smart alarm notification
  Future<void> showSmartAlarmNotification(SmartAlarm alarm) async {
    if (!_initialized) await initialize();


    String body = alarm.optimalWakeTime != null
        ? '⏰ Optimal wake time! You\'re in ${alarm.wakePhase?.displayName ?? "light sleep"}'
        : '⏰ Time to wake up!';

    await _notifications.show(
      4,
      alarm.label,
      body,
      _getNotificationDetails(
        priority: Priority.max,
        playSound: true,
        vibration: true,
      ),
      payload: 'alarm:${alarm.desiredWakeTime.toIso8601String()}',
    );
  }

  /// Schedule smart alarm for specific time
  Future<void> scheduleSmartAlarm(SmartAlarm alarm) async {
    if (!_initialized) await initialize();
    if (!alarm.isEnabled) return;

    // Cancel any existing alarm
    await _notifications.cancel(100);

    final scheduledTime = alarm.optimalWakeTime ?? alarm.desiredWakeTime;
    
    await _notifications.zonedSchedule(
      100, // Scheduled alarm ID
      alarm.label,
      '⏰ Time to wake up!',
      _convertToTZDateTime(scheduledTime),
      _getNotificationDetails(
        priority: Priority.max,
        playSound: true,
        vibration: true,
      ),
      androidScheduleMode: AndroidScheduleMode.exactAllowWhileIdle,
      uiLocalNotificationDateInterpretation:
          UILocalNotificationDateInterpretation.absoluteTime,
      payload: 'scheduled_alarm:${scheduledTime.toIso8601String()}',
    );
  }

  /// Cancel scheduled alarm
  Future<void> cancelAlarm() async {
    await _notifications.cancel(100);
  }

  /// Show phase change notification (optional)
  Future<void> showPhaseChangeNotification(SleepPhaseChange change) async {
    if (!_initialized) await initialize();

    // Only show for significant changes
    if (change.fromPhase == SleepPhase.awake && 
        change.toPhase != SleepPhase.awake) {
      // User fell asleep - already handled by onset notification
      return;
    }

    await _notifications.show(
      5,
      '🔄 Sleep Phase Change',
      'Entered ${change.toPhase.displayName}',
      _getNotificationDetails(priority: Priority.low),
      payload: 'phase_change:${change.timestamp.toIso8601String()}',
    );
  }

  /// Show insight notification
  Future<void> showInsightNotification(SleepInsight insight) async {
    if (!_initialized) await initialize();

    // Only show important insights
    if (insight.severity == InsightSeverity.positive ||
        insight.severity == InsightSeverity.warning) {
      
      await _notifications.show(
        6,
        '${insight.icon} Sleep Insight',
        insight.message,
        _getNotificationDetails(
          priority: insight.severity == InsightSeverity.warning
              ? Priority.high
              : Priority.defaultPriority,
        ),
        payload: 'insight:${insight.type.name}',
      );
    }
  }

  /// Cancel all notifications
  Future<void> cancelAll() async {
    await _notifications.cancelAll();
  }

  /// Get notification details based on platform
  NotificationDetails _getNotificationDetails({
    Priority priority = Priority.defaultPriority,
    bool playSound = false,
    bool vibration = false,
  }) {
    final androidDetails = AndroidNotificationDetails(
      'sleep_tracking', // Channel ID
      'Sleep Tracking', // Channel name
      channelDescription: 'Notifications for sleep tracking events',
      importance: _mapPriorityToImportance(priority),
      priority: priority,
      playSound: playSound,
      sound: playSound 
          ? const RawResourceAndroidNotificationSound('alarm_sound')
          : null,
      enableVibration: vibration,
      vibrationPattern: vibration ? Int64List.fromList([0, 1000, 500, 1000]) : null,
      styleInformation: const BigTextStyleInformation(''),
    );

    final iosDetails = DarwinNotificationDetails(
      presentAlert: true,
      presentBadge: true,
      presentSound: playSound,
      sound: playSound ? 'alarm_sound.aiff' : null,
    );

    return NotificationDetails(
      android: androidDetails,
      iOS: iosDetails,
    );
  }

  /// Map priority to importance
  Importance _mapPriorityToImportance(Priority priority) {
    switch (priority) {
      case Priority.max:
        return Importance.max;
      case Priority.high:
        return Importance.high;
      case Priority.defaultPriority:
        return Importance.defaultImportance;
      case Priority.low:
        return Importance.low;
      case Priority.min:
        return Importance.min;
    }
  }

  /// Convert DateTime to TZDateTime (for scheduling)
  /// Note: Requires timezone package for proper implementation
  dynamic _convertToTZDateTime(DateTime dateTime) {
    // For now, return the DateTime as-is
    // In production, use timezone package to convert properly
    return dateTime;
  }

  /// Handle notification tap
  void _onNotificationTapped(NotificationResponse response) {
    final payload = response.payload;
    if (payload == null) return;

    // Parse payload and handle navigation
    if (payload.startsWith('sleep_onset:')) {
      // Navigate to sleep analysis screen
      print('📱 Tapped sleep onset notification');
    } else if (payload.startsWith('wake:')) {
      // Navigate to sleep summary
      print('📱 Tapped wake notification');
    } else if (payload.startsWith('score:')) {
      // Navigate to sleep score dashboard
      print('📱 Tapped score notification');
    } else if (payload.startsWith('alarm:') || payload.startsWith('scheduled_alarm:')) {
      // Handle alarm tap
      print('📱 Tapped alarm notification');
    }
  }

  /// Format time for display
  String _formatTime(DateTime time) {
    final hour = time.hour.toString().padLeft(2, '0');
    final minute = time.minute.toString().padLeft(2, '0');
    return '$hour:$minute';
  }

  /// Format duration for display
  String _formatDuration(Duration duration) {
    final hours = duration.inHours;
    final minutes = duration.inMinutes.remainder(60);
    return '${hours}h ${minutes}m';
  }
}
