package com.mad.mark.safestreets2;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;



    protected static final String TAG = "MainActivity";


    protected Location mLastLocation;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build(); */

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendSMSMessage();
            }
        });


    }



    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    public void sendSMSMessage(){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+14843189828", null, "Fuck Yeah Mother Fucker", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS failed try again", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {


            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
}
