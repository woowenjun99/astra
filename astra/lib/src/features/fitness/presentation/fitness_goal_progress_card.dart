import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import 'package:astra/src/features/fitness/presentation/fitness_goal_progress.dart';
import 'package:astra/src/routing/app_router.dart';
import 'package:astra/src/widgets/gf_outlined_button.dart';

class FitnessGoalProgressCard extends StatelessWidget {
  const FitnessGoalProgressCard({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Container(
        margin: EdgeInsets.all(12),
        width: double.infinity,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          spacing: 6,
          children: [
            Text(
              "Goals Progress",
              style: TextStyle(fontWeight: FontWeight.w600, fontSize: 24),
            ),
            Text(
              "Track your fitness goals",
              style: TextStyle(fontWeight: FontWeight.w300),
            ),
            FitnessGoalProgress(),
            GFOutlinedButton(
              onPressed: () => context.goNamed(AppRoute.fitnessGoal.name),
              buttonText: "Manage Goals",
            ),
          ],
        ),
      ),
    );
  }
}
