package com.example.umarkk.torch;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ObservableHorizontalScrollView extends HorizontalScrollView {

    private int frequency;
    private StrobeController strobeController;
    private MediaPlayer mediaPlayer;
    private Context context;


    public ObservableHorizontalScrollView(Context context) {
        this(context, null, 0);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);

        strobeController = StrobeController.getInstance();
        strobeController.controller = this;
        ObservableHorizontalScrollView scrollView = (ObservableHorizontalScrollView) findViewById(R.id.scroll_view);


        int totalWidth = scrollView.getChildAt(0).getWidth();
        float p = ((float) x / (float) totalWidth) * 100;

        if (p == 0.0f) {

            frequency = 0;
        }

        if (p > 0.0f && p <= 5.0f) {

            frequency = 1;

        } else if (p > 5.0f && p <= 10.0f) {

            frequency = 2;

        } else if (p > 10.0f && p <= 15.0f) {

            frequency = 3;

        } else if (p > 15.0f && p <= 20.0f) {

            frequency = 4;

        } else if (p > 20.0f && p <= 25.0f) {

            frequency = 5;

        } else if (p > 25.0f && p <= 30.0f) {

            frequency = 6;

        } else if (p > 30.0f && p <= 35.0f) {

            frequency = 7;

        } else if (p > 35.0f && p <= 40.0f) {

            frequency = 8;

        } else if (p > 40.0f) {

            frequency = 9;

        }

        strobeController.freq = frequency;
    }
}
