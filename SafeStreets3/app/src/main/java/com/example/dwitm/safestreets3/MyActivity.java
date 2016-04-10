package com.example.dwitm.safestreets3;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;


public class MyActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double latitude;
    double longitude;
    String googleLink;

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

        assert receive_data != null;
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
                if (mLastLocation != null) {
                    Toast.makeText(getApplicationContext(),"Go Go Gadget!",Toast.LENGTH_SHORT).show();
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    try {
                        googleLink = "Hi, would you be able to drive me home? My location is: "+"http://maps.google.com/maps?f=q&q=("+Double.toString(latitude)+","+Double.toString(longitude)+")";


                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("+17172035062", null,googleLink, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS failed try again", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
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

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Connection Failed",Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();
    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                Toast.makeText(getApplicationContext(),"permission denied",Toast.LENGTH_SHORT).show();

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(),"Connection Suspended",Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();
    }

//    protected void createLocationRequest() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
}
