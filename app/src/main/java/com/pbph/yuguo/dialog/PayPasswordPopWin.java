package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.myview.PasswordInputEdt;
import com.pbph.yuguo.myview.PwdKeyboardView;
import com.pbph.yuguo.util.PublicViewUtil;

/**
 * 支付密码PopupWindow
 * Created by zyp on 2018/8/13 0013.
 */

public class PayPasswordPopWin extends PopupWindow {
    private Context mContext;
    private PwdKeyboardView keyBoard;
    private PasswordInputEdt piePassword;
    private OnPayPasswordInputOverListener pwdInputListener;

    public PayPasswordPopWin(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.pop_win_pay_password, null);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        update();
        initView(view);
        initClick();
    }

    public void show(View view) {
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        PublicViewUtil.backgroundAlpha((Activity) mContext, 0.6f);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void initView(View view) {
        keyBoard = view.findViewById(R.id.key_board);
        piePassword = view.findViewById(R.id.pie_password);
    }

    private void initClick() {
        keyBoard.setOnKeyListener(new PwdKeyboardView.OnKeyListener() {
            @Override
            public void onInput(String text) {
                String content = piePassword.getText().toString();
                if (content.length() < 6) {
                    piePassword.append(text);
                }
            }

            @Override
            public void onDelete() {
                String content = piePassword.getText().toString();
                if (content.length() > 0) {
                    piePassword.setText(content.substring(0, content.length() - 1));
                }
            }
        });

        piePassword.setOnInputOverListener(text -> {
            if (text.length() == 6) {
                pwdInputListener.onInput(text);
            }
        });
    }

    public void setOnPayPasswordInputOverListener(OnPayPasswordInputOverListener pwdInputListener) {
        this.pwdInputListener = pwdInputListener;
    }

    public interface OnPayPasswordInputOverListener {
        void onInput(String payPassword);
    }
}
