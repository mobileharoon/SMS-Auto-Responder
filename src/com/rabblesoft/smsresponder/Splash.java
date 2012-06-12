package com.rabblesoft.smsresponder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(Splash.this, SmsresponderActivity.class);
                startActivityForResult(myIntent, 0);
            }
        }, 2500);

    }
    @Override public void onBackPressed() { }


	
}