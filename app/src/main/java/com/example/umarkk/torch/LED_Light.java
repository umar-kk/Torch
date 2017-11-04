package com.example.umarkk.torch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LED_Light extends AppCompatActivity {

    private Camera camera;
    private Camera.Parameters camParamsOn, camParamsOff;
    private MediaPlayer mediaPlayer;
    private ImageView slider;
    private ToggleButton powerButton;
    private TextView frequencyView;
    private ImageButton ledActivityButton;
    private ImageButton bulbActivityButton;
    private int frequency;
    private Thread thread;
    private StrobeController strobeController2;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    //private static final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_led);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LED_Light.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(LED_Light.this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            // MY_PERMISSIONS_REQUEST_CAMERA is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

//        checkPermission();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (Settings.System.canWrite(ScreenBulbLight)
//            {
//                // Do stuff here
//            }
//            else {
//                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        }

        // Here, thisActivity is the current activity
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
//
//                // No explanation needed, we can request the permission.
////
////                ActivityCompat.requestPermissions(LED_Light.this,
////                        new String[]{Manifest.permission.WRITE_SETTINGS}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
//
//                requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//
//            }
//        }


        // Receiving Intent from ScreenBulbLight Activity
        Intent bulbActivityIntent = getIntent();

        // Initialize Variables
        slider = (ImageView) findViewById(R.id.slider);
        frequencyView = (TextView) findViewById(R.id.frequency_view);
        powerButton = (ToggleButton) findViewById(R.id.power_button);
        ledActivityButton = (ImageButton) findViewById(R.id.led_light_activity);
        bulbActivityButton = (ImageButton) findViewById(R.id.screen_bulb_activity);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_toggle);

        strobeController2 = StrobeController.getInstance();
        strobeController2.controller2 = this;

        // Set Power Button Off when starting the activity
        powerButton.setBackgroundResource(R.drawable.power_button_off);
        ledActivityButton.setBackgroundResource(R.drawable.led_light);

//        if (!strobeController.isRunning) {
//            try {
//                camera = Camera.open();
//                if (camera == null) {
//                    powerButton.setEnabled(false);
//                    return;
//                }
//
//                camera.release();
//            } catch (RuntimeException ex) {
//                powerButton.setEnabled(false);
//                Toast.makeText(getApplicationContext(), "Error connecting to camera flash.", Toast.LENGTH_LONG).show();
//                return;
//            }
//        }


//        ////////////////////
//        // frequency
//        seekBar.setMax(maxSeekFreq);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                  frequency = seekBar.getProgress();
//
//
//
//
//            }
//        });


        frequency = strobeController2.getFreq();
        frequencyView.setText(String.valueOf(frequency));

        if (powerButton.isChecked()) {
            powerButton.setBackgroundResource(R.drawable.power_button_on);
            thread = new Thread(strobeController2);
            thread.start();
        } else {
            strobeController2.requestStop = true;
            powerButton.setBackgroundResource(R.drawable.power_button_off);
        }

        // When Click on Power Button
        powerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                mediaPlayer.start();
                if (powerButton.isChecked()) {
                    powerButton.setBackgroundResource(R.drawable.power_button_on);
                    thread = new Thread(strobeController2);
                    thread.start();
                } else {
                    strobeController2.requestStop = true;
                    powerButton.setBackgroundResource(R.drawable.power_button_off);
                }
            }
        });

        // When Click on BULB Button
        bulbActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bulbIntent = new Intent(LED_Light.this, ScreenBulbLight.class);
                startActivity(bulbIntent);
                finish();
            }
        });

    }


//    @TargetApi(Build.VERSION_CODES.M)
//    private void checkPermission() {
//        // give whatever permission you want. for example i am taking--Manifest.permission.READ_PHONE_STATE
//
//        if ((Build.VERSION.SDK_INT >= 23) &&(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_SETTINGS)
//                != PackageManager.PERMISSION_GRANTED ) ){
//
//            requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
//        }else {
//            //write your code here. if permission already granted
//
//
//        }
//    }


    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

//
//        if ((Build.VERSION.SDK_INT >= 23) &&(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_SETTINGS)
//                != PackageManager.PERMISSION_GRANTED ) ){
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
//            }
//            onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }else {
//            //write your code here. if permission already granted
//            Toast toast=Toast.makeText(getApplicationContext(),"Permision GrantedWRITE" ,Toast.LENGTH_LONG);
//            toast.show();
//
//        }

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast toast = Toast.makeText(getApplicationContext(), "Permision Granted1", Toast.LENGTH_LONG);
                    toast.show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast toast = Toast.makeText(getApplicationContext(), "Permision Denied1", Toast.LENGTH_LONG);
                    toast.show();
                }

                break;
            }
//            case MY_PERMISSIONS_REQUEST_WRITE_SETTINGS:
//            {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
//                    Toast toast=Toast.makeText(getApplicationContext(),"Permision Granted2" ,Toast.LENGTH_LONG);
//                    toast.show();
//                }
//
//                else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast toast=Toast.makeText(getApplicationContext(),"Permision Denied2" ,Toast.LENGTH_LONG);
//                    toast.show();
//                    //requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
//                }
//                break;
//            }

            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }


            // other 'case' lines to check for other
            // permissions this app might request

//            case MY_PERMISSIONS_REQUEST_WRITE_SETTINGS:  {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                    Toast toast=Toast.makeText(getApplicationContext(),"Permision Granted " ,Toast.LENGTH_LONG);
//                    toast.show();
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast toast=Toast.makeText(getApplicationContext(),"Permision Denied " ,Toast.LENGTH_LONG);
//                    toast.show();
//                }
//
//
//                return;
//            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        strobeController2.requestStop = true;
        powerButton.setChecked(false);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}