package com.example.dwitm.safestreets3;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;



public class MyActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double latitude;
    double longitude;
    String googleLink;
    private static final int REQUEST_ENABLE_BT = 5;


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

        assert receive_data != null;
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
                        mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
                        mBluetoothSocket.connect();
                        System.out.printf("%d", mBluetoothSocket.TYPE_RFCOMM);
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

        /*receive_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    System.out.println(mBluetoothInputStream.read());

                    if (mBluetoothInputStream.read() == 49) {*/
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
                   /* }
                else
                    {
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
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/

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
