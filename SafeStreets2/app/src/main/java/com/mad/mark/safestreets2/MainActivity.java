package com.mad.mark.safestreets2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendSMSMessage(){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+14843189828", null, "location", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS failed try again", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }
}
