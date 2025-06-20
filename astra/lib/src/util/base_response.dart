import 'package:astra/src/features/fitness/domain/workout_log.dart';
import 'package:json_annotation/json_annotation.dart';

part 'base_response.g.dart';

class _Converter<T> implements JsonConverter<T?, Object?> {
  const _Converter();

  @override
  T? fromJson(Object? json) {
    if (json == null || T == Null) return null;
    switch (T) {
      case List<WorkoutLog> _:
      case WorkoutLog _:
        return WorkoutLog.fromJson(json as Map<String, dynamic>) as T;
      default:
        throw UnsupportedError('Unsupported type: $T');
    }
  }

  @override
  Object? toJson(T? object) => object;
}

@JsonSerializable()
class BaseResponse<T> {
  @_Converter()
  final T? data;
  final bool success;
  final String message;

  const BaseResponse({required this.message, required this.success, this.data});

  factory BaseResponse.fromJson(Map<String, dynamic> json) =>
      _$BaseResponseFromJson<T>(json);

  Map<String, dynamic> toJson() => _$BaseResponseToJson(this);
}
