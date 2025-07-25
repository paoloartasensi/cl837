class RopeSkippingData {
  final RopeMode mode;
  final int jumps;
  final int timeSeconds;
  final double calories;
  final int dayTotalJumps;
  final int? countdown; // For timer mode
  final bool? restartFlag; // For realtime notifications
  final DateTime timestamp;

  RopeSkippingData({
    required this.mode,
    required this.jumps,
    required this.timeSeconds,
    required this.calories,
    required this.dayTotalJumps,
    this.countdown,
    this.restartFlag,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  @override
  String toString() {
    return 'RopeSkippingData(mode: $mode, jumps: $jumps, time: ${timeSeconds}s, '
           'calories: ${calories.toStringAsFixed(1)}, dayTotal: $dayTotalJumps)';
  }
}

enum RopeMode {
  free(0, 'Free'),
  counter(1, 'Counter'),
  timer(2, 'Timer');

  const RopeMode(this.value, this.name);
  
  final int value;
  final String name;

  static RopeMode fromValue(int value) {
    return RopeMode.values.firstWhere(
      (mode) => mode.value == value,
      orElse: () => RopeMode.free,
    );
  }
}

class RopeRealtimeData {
  final RopeMode mode;
  final int jumps;
  final int timeSeconds;
  final double calories;
  final int? countdown;
  final bool restartFlag;
  final DateTime timestamp;

  RopeRealtimeData({
    required this.mode,
    required this.jumps,
    required this.timeSeconds,
    required this.calories,
    this.countdown,
    required this.restartFlag,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  @override
  String toString() {
    return 'RopeRealtimeData(mode: $mode, jumps: $jumps, time: ${timeSeconds}s, '
           'calories: ${calories.toStringAsFixed(1)}, restart: $restartFlag)';
  }
}
