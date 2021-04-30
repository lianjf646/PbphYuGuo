package com.pbph.yuguo.myview;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pbph.yuguo.R;

public abstract class CustomerDialogNoTitle extends Dialog {
    public interface OnCloseListener {
        public boolean onClose(CustomerDialogNoTitle dialog);
    }

    protected OnCloseListener onCloseListener;
    protected View closeButton;
    private View content;
    protected TextView contentsView;

    public void setContent(String text) {
        contentsView.setText(text);
    }

    public CustomerDialogNoTitle(Context context, int layoutId, float margin,
                                 String content) {
        super(context);
        this.setNotTitle(margin);
        init(context, layoutId, content);
    }

    public CustomerDialogNoTitle(Context context, int theme, int layoutId,
                                 float margin, String content) {
        super(context, theme);
        this.setNotTitle(margin);
        init(context, layoutId, content);
    }

    public CustomerDialogNoTitle(Context context, int layoutId, String content) {
        super(context);
        this.setNotTitle();
        init(context, layoutId, content);
    }

    public CustomerDialogNoTitle(Context context, int theme, int layoutId,
                                 String content) {
        super(context, theme);
        this.setNotTitle();
        init(context, layoutId, content);
    }

    private void setNotTitle() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
    }

    private void setNotTitle(float x) {
        this.setNotTitle();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.verticalMargin = x;
        getWindow().setAttributes(lp);
    }

    private void init(Context context, int layoutId, String contents) {
        content = View.inflate(context, layoutId, null);
        setContentView(content);
        contentsView = content.findViewById(R.id.dialog_content);
        if (contentsView != null) {
            contentsView.setText(contents);
        }

    }

    public void setBackGroudColor(int color) {
        content.setBackgroundColor(color);
    }

    public void setBackgroundResource(int res) {
        content.setBackgroundResource(res);
    }
}
