import 'package:json_annotation/json_annotation.dart';

part 'workout_log.g.dart';

@JsonSerializable()
class WorkoutLog {
  final int id;
  final String name;
  final int duration;
  final int caloriesBurnt;
  final String intensity;

  WorkoutLog({
    required this.id,
    required this.name,
    required this.duration,
    required this.caloriesBurnt,
    required this.intensity,
  });

  factory WorkoutLog.fromJson(Map<String, dynamic> json) {
    return _$WorkoutLogFromJson(json);
  }
}
