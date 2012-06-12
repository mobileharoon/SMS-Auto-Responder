package com.rabblesoft.smsresponder;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
public class MyPhoneStateListener extends PhoneStateListener {
    public Context context;
    public SharedPreferences gameSettings;
    public MyPhoneStateListener(Context context, SharedPreferences gameSettings) {
        this.context = context;
        this.gameSettings = context.getApplicationContext().getSharedPreferences("away_message_settings", Context.MODE_PRIVATE);
    }
    public void onCallStateChanged(int state, final String incomingNumber) {
        try {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                final String away_message = gameSettings.getString("message", "im busy!");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(incomingNumber, null, away_message, null, null);
                    }
                }, 30000);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onCallStateChanged(state, incomingNumber);
        //
    }
}