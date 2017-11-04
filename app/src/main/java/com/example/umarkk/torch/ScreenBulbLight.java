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
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    //    private static final int SWIPE_THRESHOLD = 1;
//    private static final int SWIPE_VELOCITY_THRESHOLD = 1;
    ArrayList<Integer> lights = new ArrayList<>();
    RelativeLayout container;
    private int brightness = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_bulb_light);

        Intent ledActivityintent = getIntent();
        light = (ImageView) findViewById(R.id.light_image);
        bulb = (ImageView) findViewById(R.id.bulb_image);
        ledActivityButton = (ImageButton) findViewById(R.id.led_light_activity);
        bulbActivityButton = (ImageButton) findViewById(R.id.screen_bulb_activity);
        container = (RelativeLayout) findViewById(R.id.mainContainer);

        gestureDetector = new GestureDetector(ScreenBulbLight.this, ScreenBulbLight.this);
        bulbActivityButton.setBackgroundResource(R.drawable.bulb_lite);

        light.setAlpha(lightOpacity);

        //Adding adresses of light images in ArrayList of lights
        lights.add(R.drawable.blue_light);
        lights.add(R.drawable.green_light);
        lights.add(R.drawable.purple_light);
        lights.add(R.drawable.red_light);
        lights.add(R.drawable.yellow_light);
        lights.add(R.drawable.white_light);

        ledActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ledIntent = new Intent(ScreenBulbLight.this, LED_Light.class);
                startActivity(ledIntent);
                finish();
            }
        });

    }

    public void setBrightness(int brightness) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);
            } else
                setBrightnessTask();
        } else
            setBrightnessTask();
    }

    private void setBrightnessTask() {
        //constrain the value of brightness
        if (brightness < 25)
            brightness = 25;
        else if (brightness > 255)
            brightness = 255;

        ContentResolver cResolver = this.getApplicationContext().getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int width = getScreenWidth();
        int height = getScreenHeight();
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
                    diffY = diffY / 7000;
                    lightOpacity = lightOpacity - diffY;
                    //  brightness = brightness - Math.round(diffY);
                    if (lightOpacity < 0) {
                        lightOpacity = minLightOpacity;
                    }
                    light.setAlpha(lightOpacity);
                    setBrightness((int) lightOpacity * 255);

                    return true;
                } else {
                    //On Swipe Up
                    diffY = diffY / 7000;
                    lightOpacity = lightOpacity + Math.abs(diffY);
                    //  brightness = brightness + Math.round(diffY);
                    if (lightOpacity > 1) {
                        lightOpacity = maxLightOpacity;
                    }
                    light.setAlpha(lightOpacity);
                    setBrightness((int) lightOpacity * 255);

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


