class FitnessGoal {
  final String category;
  final double currentProgress;
  final String description;
  final DateTime targetTime;
  final double targetValue;
  final String title;

  const FitnessGoal({
    required this.currentProgress,
    required this.title,
    required this.description,
    required this.category,
    required this.targetTime,
    required this.targetValue,
  });
}
