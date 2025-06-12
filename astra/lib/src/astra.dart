import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import 'package:astra/src/routing/app_router.dart';

class Astra extends ConsumerWidget {
  const Astra({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    GoRouter appRouter = ref.watch(appRouterProvider);

    return MaterialApp.router(
      debugShowCheckedModeBanner: false,
      title: 'Astra',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      routerConfig: appRouter,
    );
  }
}
