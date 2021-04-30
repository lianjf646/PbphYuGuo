package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.util.PublicViewUtil;

/**
 * 底部弹出的两个按钮PopupWindow
 * Created by zyp on 2018/8/13 0013.
 */

public class BothBtnBottomPopWin extends PopupWindow {
    private Context mContext;
    private View mView;
    private RelativeLayout rlContainer;
    private TextView tvTitle;
    private TextView tvBtn1;
    private TextView tvBtn2;
    private String mTitle;
    private String mBtn1;
    private String mBtn2;

    private OnPopWinBothBtnClickListener mListener;

    public BothBtnBottomPopWin(Context context, View view, String title, String btn1, String btn2) {
        this.mContext = context;
        this.mView = view;
        this.mTitle = title;
        this.mBtn1 = btn1;
        this.mBtn2 = btn2;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.pop_win_both_btn_bottom, null);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        update();
        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) mContext, 1f));
        initView(view);
        initClick();
    }

    public void show(View view){
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        PublicViewUtil.backgroundAlpha((Activity) mContext, 0.6f);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void initView(View view) {
        rlContainer = view.findViewById(R.id.rl_container);
        tvTitle = view.findViewById(R.id.tv_title);
        tvBtn1 = view.findViewById(R.id.tv_btn1);
        tvBtn2 = view.findViewById(R.id.tv_btn2);

        tvTitle.setText(mTitle);
        tvBtn1.setText(mBtn1);
        tvBtn2.setText(mBtn2);
    }

    private void initClick() {
        rlContainer.setOnClickListener(v -> dismiss());
        tvBtn1.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onFirstBtnClick();
            }
        });

        tvBtn2.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSecondBtnClick();
            }
        });
    }

    public void setOnPopWinBothBtnClickListener(OnPopWinBothBtnClickListener listener) {
        this.mListener = listener;
    }
}
