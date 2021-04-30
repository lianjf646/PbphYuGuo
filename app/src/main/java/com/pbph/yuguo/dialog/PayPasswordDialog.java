package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.PasswordInputEdt;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 连嘉凡 on 2018/9/17.
 */

public class PayPasswordDialog {

    private Activity context;
    private View view;
    Dialog dialog;
    private PasswordInputEdt passwordInputEdtPayPassword;

    public PayPasswordDialog(Activity context, OnPayPasswordInputOverListener
            onPayPasswordInputOverListener) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.popwindow_pay_password, null);
        passwordInputEdtPayPassword = view.findViewById(R.id.passwordinputedt_pay_password);
        passwordInputEdtPayPassword.setOnInputOverListener(text -> {
            if (text.length() == 6) {
                onPayPasswordInputOverListener.onInput(text);
                dialog.dismiss();
                passwordInputEdtPayPassword.closeKeyboard();

            }
        });

        initDialog();

    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.show();
        showInputMethod();

    }

    public boolean isShow() {
        if (dialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    private void showInputMethod() {
        Observable.timer(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (dialog.isShowing()) {
                        InputMethodManager inputManager = (InputMethodManager)
                                context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                });
    }

    public interface OnPayPasswordInputOverListener {
        void onInput(String payPassword);

    }
}
