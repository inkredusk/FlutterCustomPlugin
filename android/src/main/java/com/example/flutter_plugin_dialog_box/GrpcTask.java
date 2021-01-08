package com.example.flutter_plugin_dialog_box;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

//import anton.antonenv.RemotePluginServiceGrpc;
//import anton.events.Events;
//import anton.rpc.CallStatusOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ManagedChannel channel;

    GrpcTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("GrpcTask", "doInBackground");
        String host = params[0];
        String message = params[1];
        String portStr = params[2];
        int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
        try {

            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

            /*RemotePluginServiceGrpc.RemotePluginServiceBlockingStub stub = RemotePluginServiceGrpc.newBlockingStub(channel);

            Events.GenericEvent genericEvent = Events.GenericEvent.newBuilder()
                    .setDeviceId(message).setDiscovery(Events.DeviceDiscoveryEvent.getDefaultInstance()).build();
            InputStream stream = RemotePluginServiceGrpc.getSendEventMethod().streamRequest(genericEvent);
            CallStatusOuterClass.CallStatus callStatus = RemotePluginServiceGrpc.getSendEventMethod().parseResponse(stream);

            String responseMessage = callStatus.getMsg();
            int responseCode = callStatus.getCodeValue();

            Log.i("GrpcTask", "Server response message ==>" +responseMessage);
            Log.i("GrpcTask", "Server response code ==>" +responseCode);*/

            GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
            GreetingServiceOuterClass.HelloRequest request =
                    GreetingServiceOuterClass.HelloRequest.newBuilder()
                            .setName(message)
                            .build();

            GreetingServiceOuterClass.HelloResponse response =
                    stub.greeting(request);
            Log.i("GrpcTask", "Server response ==>" + response);
            String responseMessage = response.getGreeting();

            return responseMessage;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            return String.format("Failed... : %n%s", sw);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("GrpcTask", "onPostExecute");
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}