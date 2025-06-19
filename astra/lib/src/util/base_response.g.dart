// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'base_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BaseResponse<T> _$BaseResponseFromJson<T>(Map<String, dynamic> json) =>
    BaseResponse<T>(
      message: json['message'] as String,
      success: json['success'] as bool,
      data: _Converter<T?>().fromJson(json['data']),
    );

Map<String, dynamic> _$BaseResponseToJson<T>(BaseResponse<T> instance) =>
    <String, dynamic>{
      'data': _Converter<T?>().toJson(instance.data),
      'success': instance.success,
      'message': instance.message,
    };
