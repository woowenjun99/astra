import 'package:envied/envied.dart';

part 'env.g.dart';

@Envied(path: '.env', obfuscate: true, useConstantCase: true)
abstract class Env {
  @EnviedField(varName: "BASE_URL")
  static String baseUrl = _Env.baseUrl;
}
