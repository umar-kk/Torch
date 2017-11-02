package com.example.umarkk.torch;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by King Kmaboh on 10/31/2017.
 */

public class ObservableHorizontalScrollView extends HorizontalScrollView {

//    public static StrobeController getInstance() {
//        return instance == null ? instance = new ObservableHorizontalScrollView() : instance;
//    }
//
//    private static StrobeController instance;

//    public interface OnScrollListener {
//        public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldX, int oldY);
//        public void onEndScroll(ObservableHorizontalScrollView scrollView);
//    }

    private int frequency;
//    private boolean mIsScrolling;
//    private boolean mIsTouching;
//    private Runnable mScrollingRunnable;
//    private OnScrollListener mOnScrollListener;
    private StrobeController strobeController;


    public ObservableHorizontalScrollView(Context context) {
        this(context, null, 0);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//
//
//        //Log.d("TOUCH", "onTouch");
//        if (action == MotionEvent.ACTION_MOVE) {
//            mIsTouching = true;
//            mIsScrolling = true;
//        } else if (action == MotionEvent.ACTION_UP) {
//            if (mIsTouching && !mIsScrolling) {
//                if (mOnScrollListener != null) {
//                    mOnScrollListener.onEndScroll(this);
//                }
//            }
//
//            mIsTouching = false;
//        }
//
//        return super.onTouchEvent(ev);
//    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);

        strobeController = StrobeController.getInstance();
        strobeController.controller = this;
        ObservableHorizontalScrollView scrollView = (ObservableHorizontalScrollView) findViewById(R.id.scroll_view);



        int totalWidth = scrollView.getChildAt(0).getWidth();
        float p = ((float)x/(float)totalWidth)*100;

//        if (p>40) {
//            Toast toast = Toast.makeText(getContext(), "Value of p: " + p, Toast.LENGTH_SHORT);
//            toast.show();
//        }

            if(p == 0){
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 0;

                //textView 0
            }

            if (p > 0 && p <= 5) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 1;
                //textView 1
               // strobeController.freq = frequency;
            }
             else if (p > 5 && p <= 10) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 2;
                //textView 2
            } else if (p > 10 && p <= 15) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 3;
                //textView 3
            } else if (p > 15 && p <= 20) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 4;
                //textView 4
            } else if (p > 20 && p <= 25) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 5;
                //textView 5
            } else if (p > 25 && p <= 30) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 6;
                //textView 6
            } else if (p > 30 && p <= 35) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 7;
                //textView 7
            } else if (p > 35 && p <= 40) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 8;
                //textView 9
            } else if (p > 40) {
//                Toast toast=Toast.makeText(getContext(),"Value of p: " + p,Toast.LENGTH_SHORT);
//                toast.show();
                frequency = 9;
                //textView 9
            }


            strobeController.freq = frequency;


//        String px = String.valueOf(p);
//        Log.d("SCROLL", px);
//        if (Math.abs(oldX - x) > 0) {
//            if (mScrollingRunnable != null) {
//                removeCallbacks(mScrollingRunnable);
//            }
//
//            mScrollingRunnable = new Runnable() {
//                public void run() {
//                    if (mIsScrolling && !mIsTouching) {
//                        if (mOnScrollListener != null) {
//                            mOnScrollListener.onEndScroll(ObservableHorizontalScrollView.this);
//                        }
//                    }
//
//                    mIsScrolling = false;
//                    mScrollingRunnable = null;
//                }
//            };
//
//            postDelayed(mScrollingRunnable, 200);
//        }
//
//        if (mOnScrollListener != null) {
//            mOnScrollListener.onScrollChanged(this, x, y, oldX, oldY);
//        }
//    }
//
//    public OnScrollListener getOnScrollListener() {
//        return mOnScrollListener;
//    }
//
//    public void setOnScrollListener(OnScrollListener mOnEndScrollListener) {
//        this.mOnScrollListener = mOnEndScrollListener;
    }

    }

