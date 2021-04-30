package com.pbph.yuguo.myview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

import com.pbph.yuguo.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TextSwitchView extends TextSwitcher implements ViewFactory {
    private int index = -1;
    private Context context;
    ArrayList<String> textArray = new ArrayList<>();
    LayoutInflater inflater;
    private Handler mHandler = new Handler((Message msg) -> {
        switch (msg.what) {
            case 1:
                index = next(); //取得下标值
                updateText();  //更新TextSwitcherd显示内容;
                break;
        }
        return false;
    });
    //    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    index = next(); //取得下标值
//                    updateText();  //更新TextSwitcherd显示内容;
//                    break;
//            }
//        }
//    };
    private Timer timer; //

    public TextSwitchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(context);
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.in_animation));
        this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.out_animation));
    }

    public void setResources(ArrayList<String> textArray) {
        this.textArray = textArray;
    }

    public void setTextStillTime(long time) {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new MyTask(), 1, time);//每3秒更新
        } else {
        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }

    private int next() {
        int flag = index + 1;
        int size = textArray.size();
        if (flag > size - 1) {
            flag = flag - size;
        }
        return flag;
    }

    private void updateText() {
        if (textArray == null || textArray.isEmpty()) return;
        if (textArray.size() <= index) index = 0;
        this.setText(textArray.get(index));
    }

    @Override
    public View makeView() {

        return inflater.inflate(R.layout.include_text_switch_content, null);
//        TextView textView = new TextView(context);
//        textView.setSingleLine(true);
//        textView.setTextSize(13f);
//        return textView;
    }
}