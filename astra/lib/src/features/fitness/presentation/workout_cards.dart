import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:astra/src/features/fitness/data/fitness_repository.dart';
import 'package:astra/src/features/fitness/domain/workout_log.dart';
import 'package:astra/src/features/fitness/presentation/workout_card.dart';

class WorkoutCards extends ConsumerWidget {
  const WorkoutCards({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    AsyncValue<List<WorkoutLog>> workoutLogs = ref.watch(workoutLogsProvider);

    return workoutLogs.when(
      data: (logs) {
        return Column(
          spacing: 12,
          children: logs
              .map(
                (log) => WorkoutCard(
                  name: log.name,
                  duration: log.duration,
                  caloriesBurnt: log.caloriesBurnt,
                  intensity: log.intensity,
                ),
              )
              .toList(),
        );
      },
      error: (error, stackTrace) => Center(child: Text(error.toString())),
      loading: () => Center(child: CircularProgressIndicator()),
    );
  }
}
