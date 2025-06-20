// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'workout_log.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

WorkoutLog _$WorkoutLogFromJson(Map<String, dynamic> json) => WorkoutLog(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String,
  duration: (json['duration'] as num).toInt(),
  caloriesBurnt: (json['caloriesBurnt'] as num).toInt(),
  intensity: json['intensity'] as String,
);

Map<String, dynamic> _$WorkoutLogToJson(WorkoutLog instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'duration': instance.duration,
      'caloriesBurnt': instance.caloriesBurnt,
      'intensity': instance.intensity,
    };
