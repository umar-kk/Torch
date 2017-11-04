package com.example.umarkk.torch;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.GestureDetector.OnGestureListener;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;


public class ScreenBulbLight extends AppCompatActivity implements OnGestureListener {

    private GestureDetector gestureDetector;
    private ImageView bulb;
    private ImageView light;
    private ImageButton ledActivityButton;
    private ImageButton bulbActivityButton;
    private float minLightOpacity = 0.0f;
    private float maxLightOpacity = 1.0f;
    private float lightOpacity = 0.4f;
    ArrayList<Integer> lights = new ArrayList<>();
    RelativeLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_bulb_light);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);
            }
        }

        // Receiving Intent of LED_Light Activity
        Intent ledActivityintent = getIntent();

        //Initialization
        light = (ImageView) findViewById(R.id.light_image);
        bulb = (ImageView) findViewById(R.id.bulb_image);
        ledActivityButton = (ImageButton) findViewById(R.id.led_light_activity);
        bulbActivityButton = (ImageButton) findViewById(R.id.screen_bulb_activity);
        container = (RelativeLayout) findViewById(R.id.mainContainer);

        // Making Object of GestureDetector
        gestureDetector = new GestureDetector(ScreenBulbLight.this, ScreenBulbLight.this);

        // Setting bulbActivityButton on bulb_lite when Activity is start
        bulbActivityButton.setBackgroundResource(R.drawable.bulb_lite);

        // Setting Default Opacity of light
        light.setAlpha(lightOpacity);


        //Adding Addresses of light images in ArrayList of lights
        lights.add(R.drawable.blue_light);
        lights.add(R.drawable.green_light);
        lights.add(R.drawable.purple_light);
        lights.add(R.drawable.red_light);
        lights.add(R.drawable.yellow_light);
        lights.add(R.drawable.white_light);

        // Making Intent for Opening LED_Light Activity
        ledActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ledIntent = new Intent(ScreenBulbLight.this, LED_Light.class);
                startActivity(ledIntent);
                finish();
            }
        });

    }

    // Method to Set Brightness of the Screen
    private void setBrightness(int brightness) {

        //constrain the value of brightness
//        if(brightness < 25)
//            brightness = 25;
//        else if(brightness > 255)
//            brightness = 255;

        ContentResolver cResolver = this.getApplicationContext().getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

    }

    // Methot to get the total Width of Screen
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    // Methot to get the total Height of Screen
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

                    // Setting Screen Brightness Values
                    if (lightOpacity == 0) {

                        setBrightness(0);

                    }else if (lightOpacity > 0 && lightOpacity <= 0.1) {

                        setBrightness(25);

                    } else if (lightOpacity > 0.1 && lightOpacity <= 0.2) {

                        setBrightness(55);

                    } else if (lightOpacity > 0.2 && lightOpacity <= 0.3) {

                        setBrightness(80);

                    } else if (lightOpacity > 0.3 && lightOpacity <= 0.4) {

                        setBrightness(110);

                    } else if (lightOpacity > 0.4 && lightOpacity <= 0.5) {

                        setBrightness(145);

                    } else if (lightOpacity > 0.5 && lightOpacity <= 0.6) {

                        setBrightness(180);

                    } else if (lightOpacity > 0.6 && lightOpacity <= 0.7) {

                        setBrightness(210);

                    } else if (lightOpacity > 0.7 && lightOpacity <= 0.8) {

                        setBrightness(235);

                    } else if (lightOpacity > 0.8 && lightOpacity <= 1) {

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

                    // Setting Screen Brightness Values
                    if (lightOpacity == 0) {

                        setBrightness(0);

                    }else if (lightOpacity > 0 && lightOpacity <= 0.1) {

                        setBrightness(25);

                    } else if (lightOpacity > 0.1 && lightOpacity <= 0.2) {

                        setBrightness(55);

                    } else if (lightOpacity > 0.2 && lightOpacity <= 0.3) {

                        setBrightness(80);

                    } else if (lightOpacity > 0.3 && lightOpacity <= 0.4) {

                        setBrightness(110);

                    } else if (lightOpacity > 0.4 && lightOpacity <= 0.5) {

                        setBrightness(145);

                    } else if (lightOpacity > 0.5 && lightOpacity <= 0.6) {

                        setBrightness(180);

                    } else if (lightOpacity > 0.6 && lightOpacity <= 0.7) {

                        setBrightness(210);

                    } else if (lightOpacity > 0.7 && lightOpacity <= 0.8) {

                        setBrightness(235);

                    } else if (lightOpacity > 0.8 && lightOpacity <= 1) {

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


    // When Activity in Resume State

    @Override
    protected void onResume() {
        super.onResume();

        //Setting Full Screen View for App
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
}
