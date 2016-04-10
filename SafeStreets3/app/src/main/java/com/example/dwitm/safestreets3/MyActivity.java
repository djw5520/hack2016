package com.example.dwitm.safestreets3;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final Button location = (Button) findViewById(R.id.location);
        final Button phone = (Button) findViewById(R.id.phone);
        final Button uber = (Button) findViewById(R.id.uber);
        final Button receive_data = (Button) findViewById(R.id.receive);
        final TextView helping_hand = (TextView) findViewById(R.id.helping_hand);
        final TextView let_us = (TextView) findViewById(R.id.let_us);
        final TextView uber_sub = (TextView) findViewById(R.id.uber_sub);
        final TextView phone_sub = (TextView) findViewById(R.id.phone_sub);
        final TextView location_sub = (TextView) findViewById(R.id.location_sub);
        final TextView all_clear = (TextView) findViewById(R.id.all_clear);

        assert location != null;
        location.setVisibility(View.GONE);
        assert phone != null;
        phone.setVisibility(View.GONE);
        assert uber != null;
        uber.setVisibility(View.GONE);
        assert helping_hand != null;
        helping_hand.setVisibility(View.GONE);
        assert let_us != null;
        let_us.setVisibility(View.GONE);
        assert uber_sub != null;
        uber_sub.setVisibility(View.GONE);
        assert phone_sub != null;
        phone_sub.setVisibility(View.GONE);
        assert location_sub != null;
        location_sub.setVisibility(View.GONE);
        assert all_clear != null;
        all_clear.setVisibility(View.VISIBLE);

        receive_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                        assert location != null;
                        location.setVisibility(View.VISIBLE);
                        assert phone != null;
                        phone.setVisibility(View.VISIBLE);
                        assert uber != null;
                        uber.setVisibility(View.VISIBLE);
                        assert helping_hand != null;
                        helping_hand.setVisibility(View.VISIBLE);
                        assert let_us != null;
                        let_us.setVisibility(View.VISIBLE);
                        assert uber_sub != null;
                        uber_sub.setVisibility(View.VISIBLE);
                        assert phone_sub != null;
                        phone_sub.setVisibility(View.VISIBLE);
                        assert location_sub != null;
                        location_sub.setVisibility(View.VISIBLE);
                        assert all_clear != null;
                        all_clear.setVisibility(View.GONE);

            }
        });

        assert location != null;
        location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+14843189828", null, "Hello, I'm in need of a ride.", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS failed try again", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        });

        assert phone != null;
        phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:4843189828"));
                try{
                    startActivity(callIntent);
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
