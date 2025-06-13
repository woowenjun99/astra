import 'package:flutter/material.dart';
import 'package:getwidget/getwidget.dart';

import 'package:astra/src/features/fitness/presentation/workout_cards.dart';

class RecentWorkoutCard extends StatelessWidget {
  const RecentWorkoutCard({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(8),
        border: BoxBorder.all(color: Colors.grey),
      ),
      child: Container(
        margin: EdgeInsets.symmetric(vertical: 12, horizontal: 12),
        width: double.infinity,
        child: Column(
          spacing: 6,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "Recent Workouts",
              style: TextStyle(fontWeight: FontWeight.w600, fontSize: 24),
            ),
            Text(
              "Your latest training sessions",
              style: TextStyle(fontWeight: FontWeight.w300),
            ),
            WorkoutCards(),
            GFButton(
              onPressed: () {},
              fullWidthButton: true,
              type: GFButtonType.outline,
              color: Colors.grey,
              textStyle: TextStyle(
                color: Colors.black,
                fontWeight: FontWeight.w500,
              ),
              child: Text("View Workout History"),
            ),
          ],
        ),
      ),
    );
  }
}
