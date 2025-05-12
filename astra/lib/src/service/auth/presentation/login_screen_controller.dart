import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/service/auth/data/auth_repository.dart';

part 'login_screen_controller.g.dart';

@Riverpod(keepAlive: false)
class LoginScreenController extends _$LoginScreenController {
  @override
  FutureOr<void> build() {}

  Future<bool> signIn({required String email, required String password}) async {
    state = AsyncLoading();
    AuthRepository authRepository = ref.watch(authRepositoryProvider);
    state = await AsyncValue.guard(
      () => authRepository.signInWithEmailAndPassword(
        email: email,
        password: password,
      ),
    );
    return !state.hasError;
  }
}
