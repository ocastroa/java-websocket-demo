package com.example.javawebsocketclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;


public class MainActivity extends AppCompatActivity {
    private WebSocketClient webSocketClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_noise);
        createWebSocketClient();
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            uri = new URI("ws://10.0.2.2:8080/socket");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                System.out.println("onOpen");
                Log.i("Websocket", "Session is starting");
                webSocketClient.send("Hello, World!");
            }

            @Override
            public void onTextReceived(String s) {
                System.out.println("onTextReceived");
                Log.i("Websocket", "Received message");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.animalSound);
                        textView.setText(message);
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                System.out.println("onBinaryReceived");
            }

            @Override
            public void onPingReceived(byte[] data) {
                System.out.println("onPingReceived");
            }

            @Override
            public void onPongReceived(byte[] data) {
                System.out.println("onPongReceived");
            }

            @Override
            public void onException(Exception e) {
                Log.i("Websocket", "Exception " + e.getMessage());
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("Websocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public void sendMessage(View view) {
        Log.i("Websocket", "Button was clicked");

        switch(view.getId()){
            case(R.id.dogButton):
                webSocketClient.send("1");
                break;

            case(R.id.catButton):
                webSocketClient.send("2");
                break;

            case(R.id.pigButton):
                webSocketClient.send("3");
                break;

            case(R.id.foxButton):
                webSocketClient.send("4");
                break;
        }
    }
}
