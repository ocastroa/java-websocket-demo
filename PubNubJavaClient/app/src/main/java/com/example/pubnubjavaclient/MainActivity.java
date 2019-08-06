package com.example.pubnubjavaclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  PubNub pubnub;
  TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.animal_sounds);

    initPubNub();
  }

  // Initialize PubNub
  public void initPubNub(){
    PNConfiguration pnConfiguration = new PNConfiguration();
    pnConfiguration.setPublishKey("ENTER_YOUR_PUB_KEY");
    pnConfiguration.setSubscribeKey("ENTER_YOUR_SUB_KEY");
    pnConfiguration.setSecure(true);
    pubnub = new PubNub(pnConfiguration);

    // Listen to messages that arrive in the channel
    pubnub.addListener(new SubscribeCallback() {
      @Override
      public void status(PubNub pub, PNStatus status) {
      }

      @Override
      public void message(PubNub pub, final PNMessageResult message) {
        final String msg = message.getMessage().toString().replace("\"", "");
        textView = findViewById(R.id.animalSound);

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            try{
              // Display the message on the app
              textView.setText(msg);
            } catch (Exception e){
                System.out.println("Error");
                e.printStackTrace();
            }
          }
        });
      }

      @Override
      public void presence(PubNub pub, PNPresenceEventResult presence) {

      }
    });

    // Subscribe to the global channel
    pubnub.subscribe()
      .channels(Arrays.asList("global_channel"))
      .execute();
  }

  public void publishMessage(String animal_sound){
    // Publish message to the global chanel
    pubnub.publish()
      .message(animal_sound)
      .channel("global_channel")
      .async(new PNCallback<PNPublishResult>() {
        @Override
        public void onResponse(PNPublishResult result, PNStatus status) {
          // status.isError() to see if error happened and print status code if error
          if(status.isError()) {
            System.out.println("pub status code: " + status.getStatusCode());
          }
        }
      });
  }

  // This method is called when a button is pressed
  public void sendMessage(View view) {
    // Get button ID
    switch(view.getId()){
      case(R.id.dogButton):
        publishMessage("Woooof");
        break;

      case(R.id.catButton):
        publishMessage("Meooow");
        break;

      case(R.id.pigButton):
        publishMessage("Bork Bork");
        break;

      case(R.id.foxButton):
        publishMessage("Fraka-kaka-kaka");
        break;
    }
  }
}
