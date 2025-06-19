import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:astra/src/features/fitness/presentation/fitness_goal_progress_card.dart';
import 'package:astra/src/features/fitness/presentation/recent_workout_card.dart';

class FitnessDashboard extends ConsumerWidget {
  const FitnessDashboard({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: EdgeInsetsGeometry.all(16),
            child: Column(
              spacing: 12,
              children: [
                RecentWorkoutCard(),
                FitnessGoalProgressCard(),
                ElevatedButton(
                  onPressed: () async {
                    await FirebaseAuth.instance.signOut();
                  },
                  child: Text("Sign out"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
