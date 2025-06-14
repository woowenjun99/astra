import 'package:flutter/material.dart';

import 'package:astra/src/widgets/gf_outlined_button.dart';

class FitnessGoalForm extends StatefulWidget {
  const FitnessGoalForm({super.key});

  @override
  State<FitnessGoalForm> createState() => _FitnessGoalFormState();
}

class _FitnessGoalFormState extends State<FitnessGoalForm> {
  final TextEditingController _titleController = TextEditingController();

  Future<void> _showDialog() {
    return showDialog<void>(
      context: context,
      builder: (context) => AlertDialog(content: Form(child: Column())),
    );
  }

  @override
  Widget build(BuildContext context) {
    return GFOutlinedButton(onPressed: _showDialog, buttonText: "Add New Goal");
  }
}
