#import "FlutterPluginDialogBoxPlugin.h"
#if __has_include(<flutter_plugin_dialog_box/flutter_plugin_dialog_box-Swift.h>)
#import <flutter_plugin_dialog_box/flutter_plugin_dialog_box-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_plugin_dialog_box-Swift.h"
#endif

@implementation FlutterPluginDialogBoxPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterPluginDialogBoxPlugin registerWithRegistrar:registrar];
}
@end
