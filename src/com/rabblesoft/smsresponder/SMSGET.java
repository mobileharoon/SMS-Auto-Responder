package com.rabblesoft.smsresponder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;
public class SMSGET extends BroadcastReceiver {@Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += "SMS from " + msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n";
                final String sms_from = msgs[i].getOriginatingAddress().toString();
                SharedPreferences gameSettings = context.getSharedPreferences("away_message_settings", Context.MODE_PRIVATE);
                final String away_message = gameSettings.getString("message", "im busy!");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(sms_from, null, away_message, null, null);
                    }
                }, 30000);
                //Toast.makeText(context, sms_from, Toast.LENGTH_SHORT).show();
            }
        }
    }
}