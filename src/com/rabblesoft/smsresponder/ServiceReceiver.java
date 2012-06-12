package com.rabblesoft.smsresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class ServiceReceiver extends BroadcastReceiver {
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		
		SharedPreferences gameSettings = context.getSharedPreferences("away_message_settings", Context.MODE_PRIVATE);

		MyPhoneStateListener phoneListener=new MyPhoneStateListener(context,gameSettings);
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
    }	
}