import 'package:flutter/material.dart';
import 'package:getwidget/getwidget.dart';

class GFOutlinedButton extends StatelessWidget {
  final void Function()? onPressed;
  final String buttonText;

  const GFOutlinedButton({
    super.key,
    required this.onPressed,
    required this.buttonText,
  });

  @override
  Widget build(BuildContext context) {
    return GFButton(
      fullWidthButton: true,
      color: Colors.grey,
      onPressed: onPressed,
      type: GFButtonType.outline,
      textStyle: TextStyle(color: Colors.black, fontWeight: FontWeight.w500),
      child: Text(buttonText),
    );
  }
}
