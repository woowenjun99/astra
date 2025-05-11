import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'auth_repository.g.dart';

class AuthRepository {
  final FirebaseAuth _auth;

  const AuthRepository(this._auth);

  Stream<User?> get authStateChanges => _auth.authStateChanges();

  User? get currentUser => _auth.currentUser;
}

@riverpod
AuthRepository authRepository(Ref ref) {
  return AuthRepository(FirebaseAuth.instance);
}
