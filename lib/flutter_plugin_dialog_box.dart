import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginDialogBox {
  static const MethodChannel _channel =
      const MethodChannel('flutter_plugin_dialog_box');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static get showAlertDialog async {
    await _channel.invokeMethod('showAlertDialog');
  }

  static Future<String> get fetchRpcServiceData async {
    final String result = await _channel.invokeMethod('callRPCService');
    return result;
  }
}
