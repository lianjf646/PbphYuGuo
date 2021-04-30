package com.pbph.yuguo.interfaces;


import android.view.View;
import android.widget.AdapterView;


public abstract class MyOnItemClickListener implements AdapterView.OnItemClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) return;
        lastClickTime = currentTime;
        myOnItemClick(parent, view, position, id);
    }

    public abstract void myOnItemClick(AdapterView<?> parent, View view, int position, long id);
}
