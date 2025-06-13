import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:astra/src/features/fitness/data/fitness_repository.dart';
import 'package:astra/src/features/fitness/domain/fitness_goal.dart';

class FitnessGoalProgress extends ConsumerWidget {
  const FitnessGoalProgress({super.key});

  @override
  Widget build(BuildContext contex, WidgetRef ref) {
    AsyncValue<List<FitnessGoal>> fitnessGoals = ref.watch(
      fitnessGoalsProvider,
    );

    return fitnessGoals.when(
      data: (fitnessGoals) {
        return Column(children: []);
      },
      error: (err, stackTrace) => Center(child: Text(err.toString())),
      loading: () => Center(child: CircularProgressIndicator()),
    );
  }
}
