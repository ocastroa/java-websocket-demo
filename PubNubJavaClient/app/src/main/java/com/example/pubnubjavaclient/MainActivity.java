package com.example.pubnubjavaclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;

public class MainActivity extends AppCompatActivity {
    PubNub pubnub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_sounds);

        initPubNub();
    }

    public void initPubNub(){
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("ENTER_YOUR_PUB_KEY");
        pnConfiguration.setPublishKey("ENTER_YOUR_SUB_KEY");
        pnConfiguration.setSecure(true);
        pubnub = new PubNub(pnConfiguration);
    }
}
