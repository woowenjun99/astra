import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/features/authentication/data/auth_repository.dart';

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
    final AuthRepository authRepository = ref.watch(authRepositoryProvider);
    if (isLogin) {
      await authRepository.signInWithEmailAndPassword(
        email: email,
        password: password,
      );
      return;
    }
    await authRepository.createUserWithEmailAndPassword(
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
