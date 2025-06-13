import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/features/fitness/domain/fitness_goal.dart';
import 'package:astra/src/features/fitness/domain/workout_log.dart';

part 'fitness_repository.g.dart';

class FitnessRepository {
  /// Gets the workout log on the dashboard screen
  Future<List<WorkoutLog>> getWorkoutLogs() async {
    // TODO Replace with actual invocation of API
    await Future.delayed(Duration(milliseconds: 1000));
    return [
      WorkoutLog(
        id: 1,
        name: "Lower Body Strength",
        duration: 45,
        caloriesBurnt: 320,
        intensity: "High",
      ),
      WorkoutLog(
        id: 2,
        name: "Morning Run",
        duration: 32,
        caloriesBurnt: 286,
        intensity: "Medium",
      ),
      WorkoutLog(
        id: 3,
        name: "Full Body HIIT",
        duration: 28,
        caloriesBurnt: 345,
        intensity: "High",
      ),
    ];
  }

  Future<List<FitnessGoal>> getFitnessGoals() async {
    await Future.delayed(Duration(milliseconds: 1000));
    return [];
  }
}

@riverpod
FitnessRepository fitnessRepository(Ref ref) {
  return FitnessRepository();
}

@Riverpod(keepAlive: false)
Future<List<WorkoutLog>> workoutLogs(Ref ref) async {
  FitnessRepository fitnessRepository = ref.watch(fitnessRepositoryProvider);
  return fitnessRepository.getWorkoutLogs();
}

@Riverpod(keepAlive: false)
Future<List<FitnessGoal>> fitnessGoals(Ref ref) async {
  FitnessRepository fitnessRepository = ref.watch(fitnessRepositoryProvider);
  return fitnessRepository.getFitnessGoals();
}
