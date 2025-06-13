import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/screens/fitness_dashboard.dart';
import 'package:astra/src/screens/fitness_goal_screen.dart';

part 'app_router.g.dart';

enum AppRoute { fitnessDashboard, fitnessGoal }

@riverpod
GoRouter appRouter(Ref ref) {
  return GoRouter(
    routes: [
      GoRoute(
        builder: (context, state) => FitnessDashboard(),
        path: "/",
        name: AppRoute.fitnessDashboard.name,
      ),
      GoRoute(
        builder: (context, state) => FitnessGoalScreen(),
        path: "/fitness-goal",
        name: AppRoute.fitnessGoal.name,
      ),
    ],
    initialLocation: "/",
  );
}
