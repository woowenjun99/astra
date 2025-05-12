import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:astra/src/util/show_toast.dart';

/// A helper [AsyncValue] extension to show an alert dialog on error
extension AsyncValueUI on AsyncValue {
  /// show an alert dialog if the current [AsyncValue] has an error and the
  /// state is not loading.
  void showToastOnError(BuildContext context) {
    if (!isLoading && hasError) {
      final message = _errorMessage(error);
      showToast(
        context: context,
        title: "Error",
        description: message,
        backgroundColor: Colors.red[100],
      );
    }
  }

  String _errorMessage(Object? error) {
    if (error is FirebaseAuthException) {
      return error.message ?? error.toString();
    } else {
      return error.toString();
    }
  }
}
