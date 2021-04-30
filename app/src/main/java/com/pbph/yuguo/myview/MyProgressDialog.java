package com.pbph.yuguo.myview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pbph.yuguo.R;


public class MyProgressDialog {
    private Dialog dialog = null;
    private Context context = null;
    private ProgressBar progressBar = null;
    private int width, height = 0;

    public MyProgressDialog(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

//        ((Application) context).getWindowManager().getDefaultDisplay()
//                .getMetrics(displayMetrics);
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        this.context = context;
        initDialog(context);
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    public void showDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void cancel() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    private void initDialog(Context context) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_progress_layout, null);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        TextView title = view
                .findViewById(R.id.tv_dialog_progress_title);
        progressBar = view.findViewById(R.id.pb_dialog_progress);
        Button cancel = view
                .findViewById(R.id.btn_dialog_progresst_cancel);
        cancel.setVisibility(View.GONE);
        cancel.setOnClickListener((View v) -> cancel());
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = width / 10 * 9;
        // p.width = (int) (d.getWidth() * 0.65);
        dialogWindow.setAttributes(p);
//		view.getLayoutParams().width = width / 4 * 3;
    }


}
