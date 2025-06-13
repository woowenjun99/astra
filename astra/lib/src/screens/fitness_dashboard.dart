import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

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
            child: Column(children: [RecentWorkoutCard()]),
          ),
        ),
      ),
    );
  }
}
