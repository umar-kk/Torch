package com.example.umarkk.torch;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;


public class LED_Light extends AppCompatActivity {

    private Camera camera;
    private Camera.Parameters camParamsOn, camParamsOff;
    private SeekBar seekBar;
    private LinearLayout scrollView_layout;
    private ToggleButton powerButton;
    private ImageButton sosView;
    private ImageButton ledActivityButton;
    private ImageButton bulbActivityButton;
    private int frequency;
    private Thread thread;
    private StrobeController strobeController;
    private int maxSeekFreq = 9;
    private  static  final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    public final Handler mHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_led);


        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(LED_Light.this,
                Manifest.permission.CAMERA);
//        Toast toast=Toast.makeText(getApplicationContext(),"heloooo " + permissionCheck,Toast.LENGTH_SHORT);
//        toast.show();


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LED_Light.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LED_Light.this,
                        new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        }


        Intent bulbActivityIntent = getIntent();

        //Initialize Variables
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        scrollView_layout = (LinearLayout) findViewById(R.id.scroll_view_layout);
        sosView = (ImageButton) findViewById(R.id.sos_view);
        powerButton = (ToggleButton) findViewById(R.id.power_button);
        ledActivityButton = (ImageButton) findViewById(R.id.led_light_activity);
        bulbActivityButton = (ImageButton) findViewById(R.id.screen_bulb_activity);
        strobeController = StrobeController.getInstance();
        strobeController.controller = this;

        powerButton.setBackgroundResource(R.drawable.powerbutton_off);
        ledActivityButton.setBackgroundResource(R.drawable.led_button_on);
        sosView.setBackgroundResource(R.drawable.sos_off);

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


        ////////////////////
        // frequency
        seekBar.setMax(maxSeekFreq);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                  frequency = seekBar.getProgress();

                if (powerButton.isChecked()) {
                    powerButton.setBackgroundResource(R.drawable.powerbutton_on);
                    strobeController.freq = frequency;
                    thread = new Thread(strobeController);
                    thread.start();
                    if(strobeController.freq == 0){
                        sosView.setBackgroundResource(R.drawable.sos_off);
                    }else{
                        sosView.setBackgroundResource(R.drawable.sos_on);
                    }
                } else {
                    strobeController.requestStop = true;
                    powerButton.setBackgroundResource(R.drawable.powerbutton_off);
                    sosView.setBackgroundResource(R.drawable.sos_off);
                }


            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                  seekBar.getProgress();
                    seekBar.setProgress(seekBar.getProgress());
                    return true;
                }

                return true;
            }
        });



        powerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                if (powerButton.isChecked()) {
                    powerButton.setBackgroundResource(R.drawable.powerbutton_on);
                    strobeController.freq = frequency;
                    thread = new Thread(strobeController);
                    thread.start();
                    if(strobeController.freq == 0){
                        sosView.setBackgroundResource(R.drawable.sos_off);
                    }else{
                        sosView.setBackgroundResource(R.drawable.sos_on);
                    }
                } else {
                    strobeController.requestStop = true;
                    powerButton.setBackgroundResource(R.drawable.powerbutton_off);
                    sosView.setBackgroundResource(R.drawable.sos_off);
                }
            }
        });

        bulbActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bulbIntent = new Intent(LED_Light.this, ScreenBulbLight.class);
                startActivity(bulbIntent);
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast toast=Toast.makeText(getApplicationContext(),"Permision Granted " ,Toast.LENGTH_LONG);
                    toast.show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast toast=Toast.makeText(getApplicationContext(),"Permision Denied " ,Toast.LENGTH_LONG);
                    toast.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
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
        strobeController.requestStop = true;
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
