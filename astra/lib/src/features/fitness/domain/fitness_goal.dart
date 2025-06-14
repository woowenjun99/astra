class FitnessGoal {
  final String category;
  final double currentValue;
  final String description;
  final DateTime targetTime;
  final double targetValue;
  final String title;

  const FitnessGoal({
    required this.currentValue,
    required this.title,
    required this.description,
    required this.category,
    required this.targetTime,
    required this.targetValue,
  });
}
