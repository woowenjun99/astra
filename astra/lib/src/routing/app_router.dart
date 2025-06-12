import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/screens/fitness_dashboard.dart';

part 'app_router.g.dart';

@riverpod
GoRouter appRouter(Ref ref) {
  return GoRouter(
    routes: [
      GoRoute(builder: (context, state) => FitnessDashboard(), path: "/"),
    ],
    initialLocation: "/",
  );
}
