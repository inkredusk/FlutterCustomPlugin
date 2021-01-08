package com.example.flutter_plugin_dialog_box;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

//import com.example.grpc.GreetingServiceGrpc;
//import com.example.grpc.GreetingServiceOuterClass;

import java.io.InputStream;

//import anton.antone/nv.RemotePluginServiceGrpc;
//import anton.events.Events;
//import anton.rpc.CallStatusOuterClass;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.Stream;
//import model.Hello;
//import model.HelloServiceGrpc;

/** FlutterPluginDialogBoxPlugin */
public class FlutterPluginDialogBoxPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  Context context;
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_plugin_dialog_box");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if(call.method.equalsIgnoreCase("showAlertDialog")) {
      Dialog dialog=new Dialog(context);
      dialog.setTitle("Hi, My Name is Flutter");
      dialog.show();
    }else if(call.method.equalsIgnoreCase("callRPCService")) {
      //call RPC service
      Log.i("DialogBoxPlugin", "inside call rpc service");
      //initialize channel
      /*final ManagedChannel channel;
      channel = ManagedChannelBuilder.forTarget("192.168.0.110:8080") //RPC server IP : Port
              .usePlaintext()
              .build();*/

      //for hello proto
      /*HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
      Hello.HelloRequest request =
              Hello.HelloRequest.newBuilder()
                      .setName("Karan")
                      .build();
      Hello.ResponseHello response =
              stub.sayHello(request);
       Log.i("FlutterPlugin", "Server response ==>" +response);
      */

      //for greeting service proto - working
      //server implementation written in maven java project. need to start server before running app
      /*GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
      GreetingServiceOuterClass.HelloRequest request =
              GreetingServiceOuterClass.HelloRequest.newBuilder()
                      .setName("Karan")
                      .build();

      GreetingServiceOuterClass.HelloResponse response =
              stub.greeting(request);
       Log.i("FlutterPlugin", "Server response ==>" +response);
     */

      /*for home weave - events proto*/
      //RemotePluginServiceGrpc.RemotePluginServiceBlockingStub stub = RemotePluginServiceGrpc.newBlockingStub(channel);

      /*Events.GenericEvent genericEvent = Events.GenericEvent.newBuilder()
                                    .setDeviceId("123456").setDiscovery(Events.DeviceDiscoveryEvent.getDefaultInstance()).build();
      InputStream stream = RemotePluginServiceGrpc.getSendEventMethod().streamRequest(genericEvent);
      CallStatusOuterClass.CallStatus callStatus = RemotePluginServiceGrpc.getSendEventMethod().parseResponse(stream);
      String responseMessage = callStatus.getMsg();
      int responseCode = callStatus.getCodeValue();

      Log.i("FlutterPlugin", "Server response message ==>" +responseMessage);
      Log.i("FlutterPlugin", "Server response code ==>" +responseCode);*/

      //channel.shutdownNow();

      //using async task in background
      new GrpcTask(context)
              .execute(
                     "192.168.0.110",
                      "123456 karan",
                      "8080");

    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
