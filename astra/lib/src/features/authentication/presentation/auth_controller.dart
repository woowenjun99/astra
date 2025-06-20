import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/features/authentication/data/auth_repository.dart';
import 'package:astra/src/util/dio_instance.dart';

part 'auth_controller.g.dart';

@Riverpod(keepAlive: false)
class AuthController extends _$AuthController {
  @override
  FutureOr<void> build() {}

  Future<void> _submit({
    required String email,
    required String password,
    required bool isLogin,
  }) async {
    if (!isLogin) {
      final DioInstance<void> dioInstance = DioInstance<void>();
      await dioInstance.post("/users/register", {
        "email": email,
        "password": password,
        "provider": "password",
      });
    }
    final AuthRepository authRepository = ref.watch(authRepositoryProvider);
    await authRepository.signInWithEmailAndPassword(
      email: email,
      password: password,
    );
  }

  Future<bool> submit({
    required String email,
    required String password,
    required bool isLogin,
  }) async {
    state = const AsyncLoading();
    state = await AsyncValue.guard(
      () => _submit(email: email, password: password, isLogin: isLogin),
    );
    return !state.hasError;
  }
}
