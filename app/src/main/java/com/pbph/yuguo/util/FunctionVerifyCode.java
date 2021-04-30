package com.pbph.yuguo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.SmallDialog;
import com.sobot.chat.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取验证码
 * Created by zyp on 2018/8/20 0020.
 */

public class FunctionVerifyCode {
    public static final String TAG = "FunctionVerifyCode";
    private int FAILED = 0;// 失败
    private int WAITE = 201402;// 等待
    private int SUCCESS = 1;// 请求中

    private int GET_CODE = 201404;// 标记请求返回类型：请求验证码返回
    private int OAUTH_CODE = 201405;// 请求验证验证码返回
    private Context context;
    private Timer timer;
    private int time = 60;
    private UIHandler uihandler;
    private TextView tvGet;
    private EditText etPhone;
    private SmallDialog smallDialog;

    private boolean isSuccess = false;

    public FunctionVerifyCode(Context context, EditText etPhone, TextView tvGet) {
        this.context = context;
        this.etPhone = etPhone;
        this.tvGet = tvGet;
        uihandler = new UIHandler(this);
        smallDialog = new SmallDialog(context, "请稍后...");
        smallDialog.setCanceledOnTouchOutside(false);
    }


    static class UIHandler extends Handler {
        FunctionVerifyCode activity;
        WeakReference<FunctionVerifyCode> verifyCodeWeakReference;

        UIHandler(FunctionVerifyCode activity) {
            this.activity = activity;
            verifyCodeWeakReference = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            FunctionVerifyCode code = verifyCodeWeakReference.get();

            String alter = "秒后重新获取";
            if (msg.arg1 == code.WAITE) {
                code.tvGet.setText(String.format("%s%s", String.valueOf(code.time), alter));
                if (code.time == 0) {
                    code.timer.cancel();
                    code.time = 60;
                    code.tvGet.setText("获取验证码");
                    code.tvGet.setClickable(true);
                }
            } else if (msg.arg1 == code.GET_CODE) {
                if (msg.what == code.SUCCESS) {// 发送成功
                    ToastUtil.showToast(code.context, "验证码发送成功");
                    code.timer.schedule(new RemindTask(activity), 100, 1000);
                    code.tvGet.setText(String.format("%s%s", String.valueOf(code.time), alter));
                    code.tvGet.setTextColor(ContextCompat.getColor(code.context, R.color.white));
                    code.tvGet.setClickable(false);
                    code.setSuccess(true);
                } else {
                    // 发送失败则显示失败原因
                    if (code.timer != null)
                        code.timer.cancel();
                    code.tvGet.setClickable(true);
                    code.tvGet.setText("验证码发送成功");
                    ToastUtil.showToast(code.context, "验证码发送失败");
                    code.setSuccess(false);
                }

                code.smallDialog.dismiss();
            } else if (msg.arg1 == code.OAUTH_CODE) {
                code.setSuccess(true);
            } else {
                ToastUtil.showToast(code.context, msg.obj.toString());
                code.setSuccess(false);
            }
        }
    }

    // 根据手机号码获取找回密码验证码
    public void getVerifyCode() {
        timer = new Timer();
        if (!smallDialog.isShowing()) {
            smallDialog.setContent("请稍后...");
            smallDialog.show();
        }

        Message msg = uihandler.obtainMessage();
        msg.arg1 = GET_CODE;

        msg.what = 1; // //成功：1 失败：0
        msg.obj = "成功"; // 邮箱不合格：2
        uihandler.sendMessage(msg);

    }

    public boolean checkPhoneNumber() {
        String phoneNumber = etPhone.getText().toString();
        if (phoneNumber.trim().length() == 0 || phoneNumber.trim().length() != 11) {
            ToastUtil.showToast(context, "手机号格式不正确");
            return false;
        }

        String regExp = "^(13|15|18|14|17)\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        if (!m.find()) {
            ToastUtil.showToast(context, "手机号格式不正确");
            return false;
        }
        return true;
    }

    static class RemindTask extends TimerTask {

        WeakReference<FunctionVerifyCode> verifyCodeWeakReference;

        RemindTask(FunctionVerifyCode activity) {
            verifyCodeWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {

            FunctionVerifyCode code = verifyCodeWeakReference.get();

            --code.time;
            Message msg = code.uihandler.obtainMessage();
            msg.arg1 = code.WAITE;
            msg.sendToTarget();
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    private void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
