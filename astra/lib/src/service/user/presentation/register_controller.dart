import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/service/user/data/user_repository.dart';

part 'register_controller.g.dart';

@Riverpod(keepAlive: false)
class RegisterController extends _$RegisterController {
  @override
  FutureOr<void> build() {}

  Future<bool> register({
    required String email,
    required String password,
  }) async {
    final UserRepository userRepository = ref.watch(userRepositoryProvider);
    state = const AsyncLoading();
    state = await AsyncValue.guard(
      () => userRepository.register(email: email, password: password),
    );
    return !state.hasError;
  }
}
