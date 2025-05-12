import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/util/dio.dart';

part 'user_repository.g.dart';

class UserRepository {
  final Dio _dio;

  const UserRepository(this._dio);

  Future<void> register({
    required String email,
    required String password,
  }) async {
    Response response = await _dio.post(
      "/users/register",
      data: {"email": email, "password": password},
    );

    if (!response.data["success"]) {
      throw Exception(response.data["message"]);
    }
  }
}

@riverpod
UserRepository userRepository(Ref ref) {
  Dio dio = ref.watch(dioProvider);
  return UserRepository(dio);
}
