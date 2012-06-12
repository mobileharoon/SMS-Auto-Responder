package com.rabblesoft.smsresponder;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class SmsresponderActivity extends Activity {
    /** Called when the activity is first created. */
    final BroadcastReceiver mybroadcast = new SMSGET();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final SharedPreferences gameSettings = SmsresponderActivity.this.getSharedPreferences("away_message_settings", SmsresponderActivity.this.MODE_PRIVATE);
        final ComponentName component = new ComponentName(getBaseContext(), ServiceReceiver.class);
        String value = gameSettings.getString("started", null);
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        int icon = R.drawable.notification_icon;
        CharSequence tickerText = "Folio3 Auto Responder - Enabled!";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        Context context = getApplicationContext();
        CharSequence contentTitle = "Folio3 Auto Responder";
        CharSequence contentText = "This service is now running!";
        //Intent notificationIntent = new Intent(this, SMSGET.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, null, 0);
        long[] vibrate = {
            0, 100, 100, 500, 100, 100
        };
        notification.vibrate = vibrate;
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        final int HELLO_ID = 1;
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(HELLO_ID, notification);
        if (value == null) {
            SharedPreferences.Editor prefEditor = gameSettings.edit();
            prefEditor.putString("message", "I am currently away from the phone");
            prefEditor.commit();
        } else {}
        String away_message = gameSettings.getString("message", "im busy!");
        final TextView text = (TextView) findViewById(R.id.current_msg);
        text.setText(away_message);
        final Button disableSMSButton = (Button) findViewById(R.id.disable_sms_button);
        final Button enableSMSButton = (Button) findViewById(R.id.enable_sms_button);
        final Button disablePhoneButton = (Button) findViewById(R.id.disable_phone_button);
        final Button enablePhoneButton = (Button) findViewById(R.id.enable_phone_button);
        final Button saveButton = (Button) findViewById(R.id.save_button);
        disableSMSButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        disablePhoneButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        enableSMSButton.getBackground().setColorFilter(0xFF00CC00, PorterDuff.Mode.MULTIPLY);
        enablePhoneButton.getBackground().setColorFilter(0xFF00CC00, PorterDuff.Mode.MULTIPLY);
        saveButton.getBackground().setColorFilter(0xFF666666, PorterDuff.Mode.MULTIPLY);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.edittext);
                String u_input = e.getText().toString();
                Toast toast = Toast.makeText(SmsresponderActivity.this, u_input, 1000);
                // toast.show(); 
                SharedPreferences.Editor prefEditor = gameSettings.edit();
                prefEditor.putString("message", u_input);
                prefEditor.putString("started", "yes");
                prefEditor.commit();
                String away_message = gameSettings.getString("message", "im busy!");
                final TextView text = (TextView) findViewById(R.id.current_msg);
                text.setText(away_message);
            }
        });
        disableSMSButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComponentName receiver = new ComponentName(getBaseContext(), SMSGET.class);
                PackageManager pm = getBaseContext().getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                final TextView text = (TextView) findViewById(R.id.sms_status);
                text.setText("SMS replying: disabled");
            }
        });
        enableSMSButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComponentName receiver = new ComponentName(getBaseContext(), SMSGET.class);
                PackageManager pm = getBaseContext().getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                final TextView text = (TextView) findViewById(R.id.sms_status);
                text.setText("SMS replying: enabled");
            }
        });
        disablePhoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComponentName receiver = new ComponentName(getBaseContext(), ServiceReceiver.class);
                PackageManager pm = getBaseContext().getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                final TextView text = (TextView) findViewById(R.id.phone_status);
                text.setText("Phone call replying: disabled");
            }
        });
        enablePhoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComponentName receiver = new ComponentName(getBaseContext(), ServiceReceiver.class);
                PackageManager pm = getBaseContext().getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                final TextView text = (TextView) findViewById(R.id.phone_status);
                text.setText("Phone call replying: enabled");
            }
        });
    }@Override public void onBackPressed() {}
}