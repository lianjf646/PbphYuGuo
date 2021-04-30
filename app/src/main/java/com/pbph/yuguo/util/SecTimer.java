package com.pbph.yuguo.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by Administrator on 2018/4/9.
 */

public abstract class SecTimer {


    private final long mCountdownInterval = 1000;

    private long startTime;

    private long timePass;

    private boolean mFinished = false;
    private boolean mCancelled = false;

    public SecTimer() {
        startTime = SystemClock.elapsedRealtime();
    }

    public synchronized final SecTimer reset() {

        startTime = SystemClock.elapsedRealtime();

        mFinished = false;
        mCancelled = false;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public synchronized final SecTimer start() {
        if (mFinished) return this;
        mCancelled = false;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    public synchronized final void finish() {
        mFinished = true;
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    public abstract void onTick(long passTime) throws Exception;


    private static final int MSG = 1;
    // handles counting down
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (SecTimer.this) {
                if (mCancelled) {
                    return;
                }
                long lastTickStart = SystemClock.elapsedRealtime();

                timePass = lastTickStart - startTime;

                try {
                    onTick(timePass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // take into account user's onTick taking time to execute
                long lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart;


                long delay = mCountdownInterval - lastTickDuration;

                // special case: user's onTick took more than interval to
                // complete, skip to next interval
                while (delay < 0) delay += mCountdownInterval;

                sendMessageDelayed(obtainMessage(MSG), delay);
            }
        }
    };
}
