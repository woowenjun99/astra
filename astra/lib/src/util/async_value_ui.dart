import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:toastification/toastification.dart';

extension AsyncValueUI on AsyncValue {
  /// show an alert dialog if the current [AsyncValue] has an error and the
  /// state is not loading.
  void showAlertDialogOnError(BuildContext context) {
    if (!isLoading && hasError) {
      final message = _errorMessage(error);
      toastification.show(
        backgroundColor: Colors.red[200],
        context: context,
        description: Text(message),
        title: Text("Error"),
        showProgressBar: true,
        alignment: Alignment.bottomCenter,
        showIcon: false,
        autoCloseDuration: Duration(seconds: 3),
      );
    }
  }

  String _errorMessage(Object? error) {
    if (error is FirebaseAuthException) {
      return error.message ?? error.toString();
    }
    return error.toString();
  }
}
