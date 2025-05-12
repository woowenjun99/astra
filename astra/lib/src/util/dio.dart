import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'package:astra/src/env.dart';

part 'dio.g.dart';

@riverpod
Dio dio(Ref ref) {
  return Dio(BaseOptions(baseUrl: Env.baseUrl));
}
