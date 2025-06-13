import 'package:flutter/material.dart';

class WorkoutCard extends StatelessWidget {
  final String name;
  final int duration;
  final int caloriesBurnt;
  final String intensity;

  const WorkoutCard({
    super.key,
    required this.name,
    required this.duration,
    required this.caloriesBurnt,
    required this.intensity,
  });

  Color _determineIntensityColor() {
    if (intensity == "High") {
      return Colors.red;
    } else if (intensity == "Medium") {
      return Colors.orangeAccent;
    }
    return Colors.green;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(12),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Column(
        spacing: 8,
        children: [
          Row(
            children: [
              Text(name, style: TextStyle(fontWeight: FontWeight.w600)),
            ],
          ),
          Row(
            spacing: 4,
            children: [
              Icon(Icons.access_time),
              Text("$duration min"),
              Icon(Icons.local_fire_department, color: Colors.orangeAccent),
              Text("$caloriesBurnt kcal"),
              Icon(Icons.speed),
              Text(
                intensity,
                style: TextStyle(color: _determineIntensityColor()),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
