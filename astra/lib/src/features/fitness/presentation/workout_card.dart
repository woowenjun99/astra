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
      return Color.fromRGBO(231, 0, 11, 1);
    } else if (intensity == "Medium") {
      return Color.fromRGBO(245, 74, 0, 1);
    }
    return Color.fromRGBO(0, 166, 62, 1);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(12),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey),
        borderRadius: BorderRadius.circular(8),
        color: Colors.white,
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
