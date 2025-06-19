import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/features/authentication/data/auth_repository.dart';
import 'package:astra/src/util/base_response.dart';

part 'dio_instance.g.dart';

class _DioResponseInterceptor extends Interceptor {
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    handler.resolve(err.response!);
  }
}

class DioInstance<T> {
  final Dio _dio;
  final AuthRepository _authRepository;

  const DioInstance(this._dio, this._authRepository);

  /// Allow users to post without any JWT token
  Future<T> post(String endpoint, Map<String, Object?> payload) async {
    final Response response = await _dio.post(endpoint, data: payload);

    BaseResponse<T> data = BaseResponse<T>.fromJson(response.data);

    if (!data.success) {
      throw Exception(data.message);
    }

    return response.data;
  }
}

// TODO Replace the base url with environment variable
@Riverpod(keepAlive: true)
DioInstance dioInstance(Ref ref) {
  final AuthRepository authRepository = ref.watch(authRepositoryProvider);

  final Dio dio = Dio(BaseOptions(baseUrl: "http://localhost:8080"));
  dio.interceptors.add(_DioResponseInterceptor());

  return DioInstance(dio, authRepository);
}
