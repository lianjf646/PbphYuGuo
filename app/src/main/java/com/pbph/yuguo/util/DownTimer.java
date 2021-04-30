/*
    ShengDao Android Client, DownTimer
    Copyright (c) 2014 ShengDao Tech Company Limited
 */
package com.pbph.yuguo.util;

import android.os.CountDownTimer;

/**
 * @version 1.0
 **/
public class DownTimer {

    private final String TAG = DownTimer.class.getSimpleName();
    private CountDownTimer mCountDownTimer;
    private DownTimerListener listener;


    /**
     * @param time
     */
    public void startDown(long time) {
        startDown(time, 1000);
    }

    /**
     * @param time
     * @param mills
     */
    public void startDown(long time, long mills) {
        mCountDownTimer = new CountDownTimer(time, mills) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                } else {
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onFinish();
                } else {
                }
                if (mCountDownTimer != null) mCountDownTimer.cancel();
            }

        }.start();
    }

    public void stopDown() {
        if (mCountDownTimer != null) mCountDownTimer.cancel();

        mCountDownTimer = null;
    }

    /**
     * @param listener
     */
    public void setListener(DownTimerListener listener) {
        this.listener = listener;
    }

    public interface DownTimerListener {

        /**
         * @param millisUntilFinished
         */
        public void onTick(long millisUntilFinished);

        /**
         */
        public void onFinish();
    }
}

