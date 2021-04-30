package com.pbph.yuguo.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pbph.yuguo.R;


/**
 * Created by Administrator on 2018/3/12.
 */

public class ToastDialog extends Dialog {

    String title;
    String text;

    public ToastDialog(Context context, String title, String text, OnClickRateDialog onClickRateListener) {
        this(context, R.style.Dialog, title, text, onClickRateListener);
    }

    public ToastDialog(Context context, int themeResId, String title, String text, OnClickRateDialog onClickRateListener) {
        super(context, themeResId);
        //        super(context, R.style.MyRateDialog);
        this.title = title;
        this.text = text;
        this.onClickRateListener = onClickRateListener;
        setCustomDialog();
    }


    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_toast, null);

        TextView textView = mView.findViewById(R.id.textView1);
        if (TextUtils.isEmpty(title)) textView.setVisibility(View.GONE);
        textView.setText(title);

        textView = mView.findViewById(R.id.textView2);
        textView.setText(text);

        Button positiveButton = mView.findViewById(R.id.button2);

        if (positiveButton != null) positiveButton.setOnClickListener(v -> {
            if (onClickRateListener != null)
                onClickRateListener.onClickRight();
            dismiss();
        });
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(mView);
    }

    OnClickRateDialog onClickRateListener;

    public interface OnClickRateDialog {
        //        void onClickLeft();
        void onClickRight();
    }

    public ToastDialog showAndReturn() {
        show();
        return this;
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /////////获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        /////////设置高宽
        lp.width = (int) (screenWidth * 0.75); // 宽度
        lp.height = (int) (lp.width * 0.70);     // 高度
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    public static ToastDialog show(Context context, String title, String text, OnClickRateDialog onClickRateListener) {
        return new ToastDialog(context, title, text, onClickRateListener).showAndReturn();
    }
}
