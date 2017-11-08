package com.example.umarkk.torch;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Random;


public class ScreenBulbLight extends AppCompatActivity implements OnGestureListener {

    private ImageView bulb;
    private ImageButton bulbActivityButton;
    private RelativeLayout container;
    private GestureDetector gestureDetector;
    private ImageButton ledActivityButton;
    private ImageView light;
    private float lightOpacity = 0.4f;
    private ArrayList<Integer> lights = new ArrayList();
    private float maxLightOpacity = 1.0f;
    private float minLightOpacity = 0.0f;
    private AdView mAdView;
    private InterstitialAd mInterstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_bulb_light);

        if (VERSION.SDK_INT >= 23 && !System.canWrite(getApplicationContext())) {
            startActivityForResult(new Intent("android.settings.action.MANAGE_WRITE_SETTINGS",
                    Uri.parse("package:" + getPackageName())), 200);
        }


        // Receiving Intent from LED_Light Activity
        Intent ledActivityintent = getIntent();


        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        // Initialization
        light = (ImageView) findViewById(R.id.light_image);
        bulb = (ImageView) findViewById(R.id.bulb_image);
        ledActivityButton = (ImageButton) findViewById(R.id.led_light_activity);
        bulbActivityButton = (ImageButton) findViewById(R.id.screen_bulb_activity);
        container = (RelativeLayout) findViewById(R.id.mainContainer);

        gestureDetector = new GestureDetector(ScreenBulbLight.this, ScreenBulbLight.this);
        bulbActivityButton.setBackgroundResource(R.drawable.bulb_lite);

        light.setAlpha(lightOpacity);


        //Adding Addresses of light images in ArrayList of lights
        lights.add(R.drawable.blue_light);
        lights.add(R.drawable.green_light);
        lights.add(R.drawable.purple_light);
        lights.add(R.drawable.red_light);
        lights.add(R.drawable.yellow_light);
        lights.add(R.drawable.white_light);


        // When Click on LED Button
        ledActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ledIntent = new Intent(ScreenBulbLight.this, LED_Light.class);
                startActivity(ledIntent);
                finish();

                if (mInterstitial.isLoaded()){
                    mInterstitial.show();
                }
            }
        });

    }


    // Method to Control the Brightness of Screen
    public void setBrightness(int brightness) {

//        //constrain the value of brightness
//        if(brightness < 25)
//            brightness = 25;
//        else if(brightness > 255)
//            brightness = 255;

        ContentResolver cResolver = this.getApplicationContext().getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

    }

    // Method to Get the total Width of Screen
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    // Method to Get the total Height of Screen
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


        int width = getScreenWidth();
        Random r = new Random();
        int index = r.nextInt(6);

        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {


                if (diffX > width * 0.35) {

                    //On Swipe Right
                    bulb.setImageResource(R.drawable.white_bulb);
                    light.setImageResource(lights.get(index));

                    return true;

                } else if (diffX < (-1) * width * 0.35) {

                    //On Swipe Left
                    bulb.setImageResource(R.drawable.white_bulb);
                    light.setImageResource(lights.get(index));

                    return true;

                }

            } else {


                if (diffY > 0) {

                    //On Swipe Down
                    diffY = diffY / 4000;
                    lightOpacity = lightOpacity - diffY;

                    if (lightOpacity < 0) {
                        lightOpacity = minLightOpacity;
                    }
                    light.setAlpha(lightOpacity);

                    // Setting Different Values of Brightness
                    if (this.lightOpacity == 0.0f) {

                        setBrightness(0);

                    } else if (this.lightOpacity > 0.0f && ((double) this.lightOpacity) <= 0.1d) {

                        setBrightness(25);

                    } else if (((double) this.lightOpacity) > 0.1d && ((double) this.lightOpacity) <= 0.2d) {

                        setBrightness(55);

                    } else if (((double) this.lightOpacity) > 0.2d && ((double) this.lightOpacity) <= 0.3d) {

                        setBrightness(80);

                    } else if (((double) this.lightOpacity) > 0.3d && ((double) this.lightOpacity) <= 0.4d) {

                        setBrightness(110);

                    } else if (((double) this.lightOpacity) > 0.4d && ((double) this.lightOpacity) <= 0.5d) {

                        setBrightness(145);

                    } else if (((double) this.lightOpacity) > 0.5d && ((double) this.lightOpacity) <= 0.6d) {

                        setBrightness(180);

                    } else if (((double) this.lightOpacity) > 0.6d && ((double) this.lightOpacity) <= 0.7d) {

                        setBrightness(210);

                    } else if (((double) this.lightOpacity) > 0.7d && ((double) this.lightOpacity) <= 0.8d) {

                        setBrightness(235);

                    } else if (((double) this.lightOpacity) > 0.8d && this.lightOpacity <= 1.0f) {

                        setBrightness(255);
                    }

                    return true;

                } else {

                    //On Swipe Up
                    diffY = diffY / 4000;
                    lightOpacity = lightOpacity + Math.abs(diffY);
                    if (lightOpacity > 1) {
                        lightOpacity = maxLightOpacity;
                    }
                    light.setAlpha(lightOpacity);

                    // Setting Different Values of Brightness
                    if (this.lightOpacity == 0.0f) {

                        setBrightness(0);

                    } else if (this.lightOpacity > 0.0f && ((double) this.lightOpacity) <= 0.1d) {

                        setBrightness(25);

                    } else if (((double) this.lightOpacity) > 0.1d && ((double) this.lightOpacity) <= 0.2d) {

                        setBrightness(55);

                    } else if (((double) this.lightOpacity) > 0.2d && ((double) this.lightOpacity) <= 0.3d) {

                        setBrightness(80);

                    } else if (((double) this.lightOpacity) > 0.3d && ((double) this.lightOpacity) <= 0.4d) {

                        setBrightness(110);

                    } else if (((double) this.lightOpacity) > 0.4d && ((double) this.lightOpacity) <= 0.5d) {

                        setBrightness(145);

                    } else if (((double) this.lightOpacity) > 0.5d && ((double) this.lightOpacity) <= 0.6d) {

                        setBrightness(180);

                    } else if (((double) this.lightOpacity) > 0.6d && ((double) this.lightOpacity) <= 0.7d) {

                        setBrightness(210);

                    } else if (((double) this.lightOpacity) > 0.7d && ((double) this.lightOpacity) <= 0.8d) {

                        setBrightness(235);

                    } else if (((double) this.lightOpacity) > 0.8d && this.lightOpacity <= 1.0f) {

                        setBrightness(255);
                    }
                    return true;
                }
            }

        } catch (Exception exception) {

            exception.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // TODO Auto-generated method stub

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public void onLongPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onShowPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public boolean onDown(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return true;
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

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(ScreenBulbLight.this);
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

}


