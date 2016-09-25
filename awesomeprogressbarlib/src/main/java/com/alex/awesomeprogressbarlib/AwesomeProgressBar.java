package com.alex.awesomeprogressbarlib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by xuebo on 16/9/7.
 */
public class AwesomeProgressBar extends RelativeLayout {

    private static final int ANIM_TIME = 700;

    private int timespace_hanlder = 20;
    private int times_total;
    private int times_current;
    private float leftvalue, rightvalue;
    private float leftspeed, rightspeed;

    private Timer mTimer;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private TextView left_tv, right_tv;
    private ProgressBar left_bar, right_bar;

    public AwesomeProgressBar(Context context) {
        super(context);
        initView();
    }

    public AwesomeProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AwesomeProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.awesome_bar, null);
        addView(view);
        left_bar = (ProgressBar) findViewById(R.id.progress_left);
        left_tv = (TextView) findViewById(R.id.left_tv);
        right_tv = (TextView) findViewById(R.id.right_tv);
        right_bar = (ProgressBar) findViewById(R.id.progress_right);
    }

    /**
     * set left and right progress with anim
     *
     * @param left_value
     * @param right_value
     */
    public void setRateWithAnim(float left_value, float right_value) {
        if ((left_value + right_value) == 0) {
            setVisibility(GONE);
            return;
        }
        resetTimer();
        setVisibility(VISIBLE);
        left_bar.setProgress(0);
        right_bar.setProgress(0);

        leftvalue = left_value / (left_value + right_value) * 100;
        rightvalue = 100 - leftvalue;

        leftspeed = leftvalue / ANIM_TIME * timespace_hanlder;//
        rightspeed = rightvalue / ANIM_TIME * timespace_hanlder;

        times_total = ANIM_TIME / timespace_hanlder;
        mTimer = new Timer();
        mTimer.schedule(new BarTimerTask(), 0, timespace_hanlder);

        int leftshow = (int) leftvalue;
        int rightshow = 100 - leftshow;
        left_tv.setText(leftshow + "%");
        right_tv.setText(rightshow + "%");
    }

    /**
     * set left and right progress with no amin
     *
     * @param left_value
     * @param right_value
     */
    public void setRate(float left_value, float right_value) {
        if ((left_value + right_value) == 0) {
            setVisibility(GONE);
            return;
        }
        resetTimer();
        setVisibility(VISIBLE);
        left_bar.setProgress(0);
        right_bar.setProgress(0);

        leftvalue = left_value / (left_value + right_value) * 100;
        rightvalue = 100 - leftvalue;

        int leftshow = (int) leftvalue;
        int rightshow = 100 - leftshow;
        left_tv.setText(leftshow + "%");
        right_tv.setText(rightshow + "%");

        left_bar.setProgress((int) leftvalue);
        right_bar.setProgress((100 - left_bar.getProgress()));
    }

    /**
     * reset timer
     */
    private void resetTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
            times_current = 0;
        }
    }

    /**
     * timer task
     */
    private class BarTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    times_current++;
                    Log.e("tag", times_current + "   " + (int) (times_current * leftspeed));
                    left_bar.setProgress((int) (times_current * leftspeed));
                    right_bar.setProgress((int) (times_current * rightspeed));
                    if (times_current >= times_total) {
                        left_bar.setProgress((int) leftvalue);
                        right_bar.setProgress((100 - left_bar.getProgress()));
                        resetTimer();
                    }
                }
            });
        }
    }

}
