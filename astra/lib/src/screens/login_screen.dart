import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:getwidget/getwidget.dart';

import 'package:astra/src/service/auth/presentation/login_screen_controller.dart';
import 'package:astra/src/util/async_value_ui.dart';

class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  bool isVisible = false;
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  @override
  void dispose() {
    super.dispose();
    _emailController.dispose();
    _passwordController.dispose();
  }

  void _login() async {
    await ref
        .watch(loginScreenControllerProvider.notifier)
        .signIn(
          email: _emailController.text.trim(),
          password: _passwordController.text.trim(),
        );
  }

  @override
  Widget build(BuildContext context) {
    final AsyncValue<void> state = ref.watch(loginScreenControllerProvider);
    ref.listen(loginScreenControllerProvider, (_, state) {
      state.showToastOnError(context);
    });

    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: EdgeInsetsGeometry.symmetric(vertical: 8, horizontal: 16),
          child: SizedBox(
            width: double.infinity,
            child: Column(
              children: [
                Text(
                  "Astra",
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                ),
                Text("Manage your life with ease"),

                const SizedBox(height: 16),
                Row(
                  children: [
                    Text(
                      "Email",
                      style: TextStyle(fontWeight: FontWeight.w600),
                    ),
                  ],
                ),
                const SizedBox(height: 4),
                TextFormField(
                  controller: _emailController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    hintText: "name@example.com",
                    prefixIcon: Icon(Icons.email),
                  ),
                  keyboardType: TextInputType.emailAddress,
                ),
                const SizedBox(height: 16),
                Row(
                  children: [
                    Text(
                      "Password",
                      style: TextStyle(fontWeight: FontWeight.w600),
                    ),
                  ],
                ),
                const SizedBox(height: 4),
                TextFormField(
                  controller: _passwordController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    hintText: "******",
                    prefixIcon: Icon(Icons.lock),
                    suffixIcon: IconButton(
                      onPressed: () => setState(() => isVisible = !isVisible),
                      icon: Icon(
                        isVisible ? Icons.visibility_off : Icons.visibility,
                      ),
                    ),
                  ),
                  obscureText: !isVisible,
                ),
                const SizedBox(height: 20),
                GFButton(
                  onPressed: _login,
                  fullWidthButton: true,
                  color: Theme.of(context).primaryColor,
                  size: GFSize.LARGE,
                  child: state.isLoading
                      ? CircularProgressIndicator.adaptive()
                      : Text("Login"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
