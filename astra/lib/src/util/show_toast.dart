import 'package:flutter/material.dart';
import 'package:toastification/toastification.dart';

ToastificationItem showToast({
  Color? backgroundColor,
  required BuildContext context,
  required String title,
  required String description,
}) {
  return toastification.show(
    autoCloseDuration: const Duration(seconds: 2),
    alignment: Alignment.bottomCenter,
    backgroundColor: backgroundColor,
    context: context,
    description: Text(description),
    showProgressBar: true,
    title: Text(title),
    type: ToastificationType.error,
  );
}
