import 'package:dio/dio.dart';

import 'package:astra/src/util/base_response.dart';

class _DioResponseInterceptor extends Interceptor {
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    handler.resolve(err.response!);
  }
}

class DioInstance<T> {
  late Dio dio;

  DioInstance() {
    dio = Dio(BaseOptions(baseUrl: "http://localhost:8080"));
    dio.interceptors.add(_DioResponseInterceptor());
  }

  /// Allow users to post without any JWT token
  Future<T> post(String endpoint, Map<String, Object?> payload) async {
    final Response response = await dio.post(endpoint, data: payload);

    BaseResponse<T> data = BaseResponse<T>.fromJson(response.data);

    if (!data.success) {
      throw Exception(data.message);
    }

    return response.data;
  }

  Future<T?> get(String endpoint, String jwt) async {
    final Response response = await dio.get(
      endpoint,
      options: Options(headers: {"authorization": jwt}),
    );
    BaseResponse<T> data = BaseResponse<T>.fromJson(response.data);
    if (!data.success) {
      throw Exception(data.message);
    }
    return data.data;
  }
}
