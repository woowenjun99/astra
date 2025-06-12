import 'package:flutter/material.dart';

class FitnessDashboard extends StatelessWidget {
  const FitnessDashboard({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: EdgeInsetsGeometry.all(16),
            child: SizedBox(width: double.infinity, child: Container()),
          ),
        ),
      ),
    );
  }
}
