import 'package:astra/src/screens/auth_screen.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'package:astra/src/common/scaffold_with_nested_navigation.dart';

import 'package:astra/src/router/go_router_refresh_stream.dart';
import 'package:astra/src/screens/home_screen.dart';
import 'package:astra/src/service/auth/data/auth_repository.dart';

part 'app_router.g.dart';

@riverpod
GoRouter goRouter(Ref ref) {
  AuthRepository authRepository = ref.watch(authRepositoryProvider);

  return GoRouter(
    redirect: (context, state) {
      final bool isSignedIn = authRepository.currentUser != null;

      // final List<String> public = [];

      // if (!isSignedIn && !public.contains(state.fullPath)) {
      //   return "/login";
      // } else if (isSignedIn && public.contains(state.fullPath)) {
      //   return "/";
      // }
      return null;
    },
    initialLocation: "/login",
    routes: [
      GoRoute(path: "/login", builder: (context, state) => AuthScreen()),
      StatefulShellRoute.indexedStack(
        branches: [
          StatefulShellBranch(
            routes: [
              GoRoute(path: "/", builder: (context, state) => HomeScreen()),
            ],
          ),
        ],
        builder: (context, state, navigationShell) =>
            ScaffoldWithNestedNavigation(navigationShell: navigationShell),
      ),
    ],
    refreshListenable: GoRouterRefreshStream(authRepository.authStateChanges),
  );
}
