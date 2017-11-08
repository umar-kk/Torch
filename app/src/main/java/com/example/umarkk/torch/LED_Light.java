package com.example.umarkk.torch;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class LED_Light extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private ImageButton bulbActivityButton;
    private Camera camera;
    private ImageButton ledActivityButton;
    private MediaPlayer mediaPlayer;
    private ToggleButton powerButton;
    private ImageView slider;
    private ImageView sosView;
    private StrobeController strobeController2;
    private Thread thread;
    private AdView mAdView;
    private InterstitialAd mInterstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_led);


        // Here, this Activity is the current activity. Checking the Permission of
        // CAMERA is Granted or Not.
        if (ContextCompat.checkSelfPermission(LED_Light.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(LED_Light.this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            // MY_PERMISSIONS_REQUEST_CAMERA is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }


        // Receiving Intent from ScreenBulbLight Activity
        Intent bulbActivityIntent = getIntent();

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);


        // Initialization
        slider = (ImageView) findViewById(R.id.slider);
        sosView = (ImageView) findViewById(R.id.sos_view);
        powerButton = (ToggleButton) findViewById(R.id.power_button);
        ledActivityButton = (ImageButton) findViewById(R.id.led_light_activity);
        bulbActivityButton = (ImageButton) findViewById(R.id.screen_bulb_activity);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_toggle);

        strobeController2 = StrobeController.getInstance();
        strobeController2.controller2 = this;

        ObservableHorizontalScrollView obj = new ObservableHorizontalScrollView(this);

        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mediaPlayer.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        mediaPlayer.pause();
                        break;
                }

                return true;
            }
        });

        // Setting PowerButton, ledActivityButton and sos_view when starting the activity
        powerButton.setBackgroundResource(R.drawable.power_button_off);
        ledActivityButton.setBackgroundResource(R.drawable.led_light);
        sosView.setBackgroundResource(R.drawable.sos_dark);


        // Checking the Status of Power Button
        if (powerButton.isChecked()) {

            powerButton.setBackgroundResource(R.drawable.power_button_on);
            sosView.setBackgroundResource(R.drawable.sos_lite);
            thread = new Thread(strobeController2);
            thread.start();

        } else {

            strobeController2.requestStop = true;
            powerButton.setBackgroundResource(R.drawable.power_button_off);
            sosView.setBackgroundResource(R.drawable.sos_dark);
        }


        // When Click on Power Button
        powerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Perform action on clicks
                mediaPlayer.start();
                if (powerButton.isChecked()) {

                    powerButton.setBackgroundResource(R.drawable.power_button_on);
                    sosView.setBackgroundResource(R.drawable.sos_lite);
                    thread = new Thread(strobeController2);
                    thread.start();

                } else {

                    strobeController2.requestStop = true;
                    powerButton.setBackgroundResource(R.drawable.power_button_off);
                    sosView.setBackgroundResource(R.drawable.sos_dark);

                    if (mInterstitial.isLoaded()){
                        mInterstitial.show();
                    }
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

                if (mInterstitial.isLoaded()){
                    mInterstitial.show();
                }
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
//                    Toast toast = Toast.makeText(getApplicationContext(), "Permision Granted1", Toast.LENGTH_LONG);
//                    toast.show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    Toast toast = Toast.makeText(getApplicationContext(), "Permision Denied1", Toast.LENGTH_LONG);
//                    toast.show();
                    finish();
                }

                break;
            }

            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(LED_Light.this);
        alert.setTitle("Rate Us:");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + getPackageName())));
                }

                dialog.dismiss();
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.create();
        alert.show();

    }


    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitial.setAdListener(new ToastAdListener(this){
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }
        });

        AdRequest aq = new AdRequest.Builder().build();
        mInterstitial.loadAd(aq);

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
        powerButton.setBackgroundResource(R.drawable.power_button_off);
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