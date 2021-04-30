package com.pbph.yuguo.util;

import android.app.Dialog;
import android.content.Context;
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

public class YesNoDialog extends Dialog {

    String text;

    public YesNoDialog(Context context, String text, OnClickRateDialog onClickRateListener,
                       boolean b) {

        this(context, R.style.Dialog, text, onClickRateListener,b);
    }

    public YesNoDialog(Context context, int themeResId, String text, OnClickRateDialog
            onClickRateListener, boolean b) {
        super(context, themeResId);
        //        super(context, R.style.MyRateDialog);
        this.text = text;
        this.onClickRateListener = onClickRateListener;
        this.setCancelable(b); // false 点击阴影不可关闭 否则为 true
        setCustomDialog();
    }


    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_yesno, null);

        TextView textView = mView.findViewById(R.id.textView);
        textView.setText(text);

        Button positiveButton = mView.findViewById(R.id.button2);
        Button negativeButton = mView.findViewById(R.id.button1);
        if (positiveButton != null) positiveButton.setOnClickListener(v -> {
            if (onClickRateListener != null)
                onClickRateListener.onClickRight();
            dismiss();
        });
        if (negativeButton != null) negativeButton.setOnClickListener(v -> {
            if (onClickRateListener != null)
                onClickRateListener.onClickLeft();
            dismiss();
        });
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(mView);
    }

    OnClickRateDialog onClickRateListener;

    public interface OnClickRateDialog {
        void onClickLeft();

        void onClickRight();
    }

    public YesNoDialog showAndReturn() {
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
        lp.height = (int) (lp.width * 0.60);     // 高度
        dialogWindow.setAttributes(lp);
    }

    public static YesNoDialog show(Context context, String text, OnClickRateDialog
            onClickRateListener ,boolean b) {
        return new YesNoDialog(context, text, onClickRateListener,b).showAndReturn();
    }
}
