class SportsData {
  final int steps;
  final double distanceCm;
  final double caloriesKcal;
  final DateTime timestamp;

  SportsData({
    required this.steps,
    required this.distanceCm,
    required this.caloriesKcal,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  double get distanceKm => distanceCm / 100000.0; // Convert cm to km
  double get distanceM => distanceCm / 100.0; // Convert cm to meters

  @override
  String toString() {
    return 'Steps: $steps, Distance: ${distanceM.toStringAsFixed(1)}m, Calories: ${caloriesKcal.toStringAsFixed(1)} kcal';
  }
}
