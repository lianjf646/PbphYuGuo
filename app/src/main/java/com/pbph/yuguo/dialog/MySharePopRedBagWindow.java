package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pbph.yuguo.R;

/**
 * Created by Administrator on 2018/7/6.
 */


public class MySharePopRedBagWindow implements View.OnClickListener {
    Context context;
    private PopupWindow popupWindow;
    private View contentView;
    public TextView mTvShareQq;
    public TextView mTvShareLife;
    public TextView mTvShareTimeline;
    public Button mBtnShareSubmit;

    private ShareOnClickListener shareOnClickListener;

    public MySharePopRedBagWindow(Context context, ShareOnClickListener shareOnClickListener) {
        this.context = context;
        this.shareOnClickListener = shareOnClickListener;
    }


//    public MyReportPopWindow(Context context, int type) {
//        this.type = type;
//        this.context = context;
//
//    }

    private void windowTransparent(float alpha) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha = alpha;   //这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
        window.setAttributes(wl);
    }

    public void showDialog(View button) {
        if (null != popupWindow && contentView != null) {
            popupWindow.showAtLocation(button, Gravity.BOTTOM, 0, 0);
            windowTransparent(0.6f);
            return;
        }
        contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_share, null);
        popupWindow = new PopupWindow(context);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);


        this.mTvShareQq = (TextView) contentView.findViewById(R.id.tv_share_qq);
        this.mTvShareLife = (TextView) contentView.findViewById(R.id.tv_share_life);
        this.mTvShareTimeline = (TextView) contentView.findViewById(R.id.tv_share_timeline);
        this.mBtnShareSubmit = (Button) contentView.findViewById(R.id.btn_share_submit);
        mTvShareQq.setOnClickListener(this);
        mTvShareLife.setOnClickListener(this);
        mTvShareTimeline.setOnClickListener(this);
        mBtnShareSubmit.setOnClickListener(this);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        popupWindow.showAtLocation(button, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        windowTransparent(0.6f);
        popupWindow.setOnDismissListener(() -> {
            windowTransparent(1);
        });
    }

    @Override
    public void onClick(View v) {
        popupWindow.dismiss();
        switch (v.getId()) {
            case R.id.tv_share_qq:
                shareOnClickListener.onShareClick(ShareType.SHAREQQ);
                break;
            case R.id.tv_share_life:
                shareOnClickListener.onShareClick(ShareType.SHARELIFEWX);
                break;
            case R.id.tv_share_timeline:
                shareOnClickListener.onShareClick(ShareType.SHARETIMELINE);
                break;
            case R.id.btn_share_submit:
                break;
        }
    }

    public void closeDialog() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }
    }

    public interface ShareOnClickListener {
        void onShareClick(ShareType type);
    }

    public enum ShareType {
        SHAREQQ, SHARELIFEWX, SHARETIMELINE
    }
}
