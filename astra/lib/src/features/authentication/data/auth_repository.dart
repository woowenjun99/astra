import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'auth_repository.g.dart';

class AuthRepository {
  final FirebaseAuth _auth;

  const AuthRepository(this._auth);

  Future<void> signInWithEmailAndPassword({
    required String email,
    required String password,
  }) async {
    await _auth.signInWithEmailAndPassword(email: email, password: password);
  }

  Stream<User?> get authStateChanges => _auth.authStateChanges();

  User? get currentUser => _auth.currentUser;

  Future<String> getJwtToken() async {
    String? idToken = await _auth.currentUser!.getIdToken();
    if (idToken == null) {
      throw Exception("User is not signed in");
    }
    return idToken;
  }
}

@Riverpod(keepAlive: false)
AuthRepository authRepository(Ref ref) {
  return AuthRepository(FirebaseAuth.instance);
}
