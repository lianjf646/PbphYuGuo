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
 * 中间弹出的PopWin
 * Created by zyp on 2018/8/16 0016.
 */

public class BothBtnCenterPopWin extends PopupWindow {
    private Context mContext;
    private View mView;
    private RelativeLayout rlContainer;
    private TextView tvTitle;
    private TextView tvTips;
    private TextView tvBtn1;
    private TextView tvBtn2;
    private String mTitle;
    private String mTips;
    private String mBtn1;
    private String mBtn2;
    private boolean isDismiss;  //true 点旁边取消  false 点旁边不取消
    private OnPopWinBothBtnClickListener mListener;

    public BothBtnCenterPopWin(Context context, View view, String title, String tips, String btn1, String btn2, boolean isDismiss) {
        this.mContext = context;
        this.mView = view;
        this.mTitle = title;
        this.mTips = tips;
        this.mBtn1 = btn1;
        this.mBtn2 = btn2;
        this.isDismiss = isDismiss;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.pop_win_both_btn_center, null);
        setAnimationStyle(R.style.CustomPopupAnimation);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        PublicViewUtil.backgroundAlpha((Activity) mContext, 0.6f);
        update();
        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) mContext, 1f));
        initView(view);
        initClick();
    }

    private void initView(View view) {
        rlContainer = view.findViewById(R.id.rl_container);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTips = view.findViewById(R.id.tv_tips);
        tvBtn1 = view.findViewById(R.id.tv_btn1);
        tvBtn2 = view.findViewById(R.id.tv_btn2);

        tvTitle.setText(mTitle);
        tvTips.setText(mTips);
        tvBtn1.setText(mBtn1);
        tvBtn2.setText(mBtn2);
    }

    private void initClick() {
        rlContainer.setOnClickListener(v -> {
            if (isDismiss) {
                dismiss();
            }
        });
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
