package com.example.umarkk.torch;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Umar-KK on 10/31/2017.
 */

public class ObservableHorizontalScrollView extends HorizontalScrollView {

    private int frequency;
    private StrobeController strobeController;

    // Constructor Definitions
    public ObservableHorizontalScrollView(Context context) {

        this(context, null, 0);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }


    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);

        strobeController = StrobeController.getInstance();
        strobeController.controller = this;
        ObservableHorizontalScrollView scrollView = (ObservableHorizontalScrollView) findViewById(R.id.scroll_view);


        int totalWidth = scrollView.getChildAt(0).getWidth();
        float p = ((float) x / (float) totalWidth) * 100;

        if (p == 0) {

            frequency = 0;
        }

        if (p > 0 && p <= 5) {

            frequency = 1;
        } else if (p > 5 && p <= 10) {

            frequency = 2;

        } else if (p > 10 && p <= 15) {

            frequency = 3;

        } else if (p > 15 && p <= 20) {

            frequency = 4;

        } else if (p > 20 && p <= 25) {

            frequency = 5;

        } else if (p > 25 && p <= 30) {

            frequency = 6;

        } else if (p > 30 && p <= 35) {

            frequency = 7;

        } else if (p > 35 && p <= 40) {

            frequency = 8;

        } else if (p > 40) {

            frequency = 9;
        }

        strobeController.freq = frequency;
    }

}
