package com.example.dwitm.safestreets3;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MyActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final int REQUEST_ENABLE_BT = 5;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected static final String TAG = "MyActivity";
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    //Bluetooth
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private BluetoothSocket fallbackSocket;
    private InputStream mBluetoothInputStream;
    private Set<BluetoothDevice> pairedDevices;


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



        // Find Bluetooth adapter
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            System.out.println("NO BLUETOOTH SUPPORT");
        }

        // Enable Bluetooth adapter
        assert mBluetoothAdapter != null;
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            List<String> pairedDeviceList = new ArrayList<String>();
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in the alert dialog
                pairedDeviceList.add(device.getName() + "\n" + device.getAddress());
            }

            // Build an alert dialog to select the Bluetooth adapter from a list of paired devices
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the Bluetooth adapter");
            builder.setItems(pairedDeviceList.toArray(new
                    CharSequence[pairedDeviceList.size()]), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Get selected paired Bluetooth device
                    mBluetoothDevice = (BluetoothDevice) pairedDevices.toArray()[which];
                    System.out.println(mBluetoothDevice.getAddress());
                    try {
                        // Try to connect to Bluetooth device
                        mBluetoothSocket =
                                mBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                        mBluetoothSocket.connect();
                        mBluetoothInputStream = mBluetoothSocket.getInputStream();
                    } catch (IOException e) {
                        try {
                            // Try to connect to Bluetooth device again in case of error
                            Class<?> clazz = mBluetoothSocket.getRemoteDevice().getClass();
                            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};

                            Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                            Object[] params = new Object[]{Integer.valueOf(1)};

                            fallbackSocket = (BluetoothSocket)
                                    m.invoke(mBluetoothSocket.getRemoteDevice(), params);
                            fallbackSocket.connect();
                            mBluetoothInputStream = fallbackSocket.getInputStream();
                        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();

                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

// Handle button click to toggle LED
        // Output input into written thing
        //public class EventListenerActivity implements ActionListener {
        //}
        //}

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

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        assert location != null;
        location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+14843189828", null, "Fuck yeah MOTHERFUCKER", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS failed try again", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        }
);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "My Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dwitm.safestreets3/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "My Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dwitm.safestreets3/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();
    }

}
