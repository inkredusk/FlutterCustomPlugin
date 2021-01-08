import Flutter
import UIKit

public class SwiftFlutterPluginDialogBoxPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_plugin_dialog_box", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterPluginDialogBoxPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
