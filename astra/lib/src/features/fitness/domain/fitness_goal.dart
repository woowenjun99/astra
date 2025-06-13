class FitnessGoal {
  final double startWeight;
  final double currentWeight;
  final double goalWeight;
  final int currentWeeklyWorkout;
  final int goalWeeklyWorkout;
  final double currentRunningDistance;
  final double goalRunningDistance;

  const FitnessGoal({
    required this.startWeight,
    required this.goalWeight,
    required this.currentWeight,
    required this.currentWeeklyWorkout,
    required this.goalWeeklyWorkout,
    required this.currentRunningDistance,
    required this.goalRunningDistance,
  });
}
