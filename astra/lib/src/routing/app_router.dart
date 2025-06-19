import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/features/authentication/data/auth_repository.dart';
import 'package:astra/src/routing/go_router_refresh_stream.dart';
import 'package:astra/src/screens/auth_screen.dart';
import 'package:astra/src/screens/fitness_dashboard.dart';
import 'package:astra/src/screens/fitness_goal_screen.dart';

part 'app_router.g.dart';

enum AppRoute { fitnessDashboard, fitnessGoal, auth }

@riverpod
GoRouter appRouter(Ref ref) {
  final AuthRepository authRepository = ref.watch(authRepositoryProvider);

  return GoRouter(
    redirect: (context, state) {
      bool isLoggedIn = authRepository.currentUser != null;
      if (isLoggedIn && state.fullPath == "/auth") {
        return "/";
      } else if (!isLoggedIn && state.fullPath != "/auth") {
        return "/auth";
      }
    },
    refreshListenable: GoRouterRefreshStream(authRepository.authStateChanges),
    routes: [
      GoRoute(
        path: "/auth",
        name: AppRoute.auth.name,
        builder: (context, state) => AuthScreen(),
      ),
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
    initialLocation: "/auth",
  );
}
