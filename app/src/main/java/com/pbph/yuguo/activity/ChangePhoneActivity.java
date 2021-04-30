package com.pbph.yuguo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppChangeCustomerPhoneRequest;
import com.pbph.yuguo.request.GetAppSmsCodeResquest;
import com.pbph.yuguo.response.EventBusNotificationResponse;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.DownTimer;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class ChangePhoneActivity extends BaseActivity implements DownTimer.DownTimerListener {
    private final Context context = ChangePhoneActivity.this;
    private DownTimer downTimer = new DownTimer();
    private ImageView ivIcon;
    private TextView tvVerifyPhoneLabel;
    private TextView tvTipsLabel;
    private EditText etPhone;
    private TextView tvGetVerificationCode;
    private EditText etVerificationCode;
    private TextView tvSubmit;
    private String oldPhone;

    //记录是否是第一步
    private boolean isFirstStep = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        initTitle(TITLE_STYLE_WHITE, "变更手机号", true, false);
        oldPhone = YuGuoApplication.userInfo.getCustomerPhone();
        if (TextUtils.isEmpty(oldPhone)) {
            ToastUtil.showToast(context, "手机号获取失败");
            finish();
        }
        initView();
        initClick();
    }

    public void initView() {
        downTimer.setListener(this);

        ivIcon = findViewById(R.id.iv_icon);
        tvVerifyPhoneLabel = findViewById(R.id.tv_verify_phone_label);
        tvTipsLabel = findViewById(R.id.tv_tips_label);
        etPhone = findViewById(R.id.et_phone);
        tvGetVerificationCode = findViewById(R.id.tv_get_verification_code);
        etVerificationCode = findViewById(R.id.et_verification_code);
        tvSubmit = findViewById(R.id.tv_submit);

        if (isFirstStep) {
            etPhone.setEnabled(false);
            etPhone.setText(oldPhone);
        }
    }

    public void initClick() {
        tvGetVerificationCode.setOnClickListener(v -> {
            WaitUI.Show(context);

            HttpAction.getInstance().getAppSmsCode(new GetAppSmsCodeResquest(etPhone.getText().toString())).subscribe(new BaseObserver<>(context, (response -> {
                WaitUI.Cancel();
                int code = response.getCode();
                String msg = response.getMsg();
                if (200 == code) {
                    downTimer.startDown(60 * 1000);
                } else {
                    showToast(TextUtils.isEmpty(msg) ? "发送失败" : msg);
                }

            })));
        });

        tvSubmit.setOnClickListener(v -> {
            changePhoneNum(etPhone.getText().toString(), etVerificationCode.getText().toString());
        });
    }

    public void changePhoneNum(String phone, String sms) {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请填写手机号");
            return;
        }
        if (!AMUtils.isMobile(phone)) {
            showToast("非法手机号");
            return;
        }
        if (TextUtils.isEmpty(sms)) {
            showToast("请填写验证码");
            return;
        }
        GetAppChangeCustomerPhoneRequest request = new GetAppChangeCustomerPhoneRequest(-1, phone, sms);
        HttpAction.getInstance().checkSms(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            String message = response.getMsg();
            if (200 == code) {
                if (isFirstStep) {
                    isFirstStep = false;
                    changePageInfo();
                } else {
                    changePhone(phone, sms);
                }
            } else {
                ToastUtil.showToast(context, TextUtils.isEmpty(message) ? "操作失败" : message);
            }
        }, (code, message) -> ToastUtil.showToast(context, TextUtils.isEmpty(message) ? "操作失败" : message)));
    }

    private void changePhone(String phone, String sms) {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请填写手机号");
            return;
        }
        if (!AMUtils.isMobile(phone)) {
            showToast("非法手机号");
            return;
        }
        if (TextUtils.isEmpty(sms)) {
            showToast("请填写验证码");
            return;
        }
        GetAppChangeCustomerPhoneRequest request = new GetAppChangeCustomerPhoneRequest(customerId, phone, sms);
        HttpAction.getInstance().appChangeCustomerPhone(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            String message = response.getMsg();
            if (200 == code) {
                ToastUtil.showToast(context, "手机号变更成功");
                //更新本地手机号缓存
                YuGuoApplication.userInfo.setCustomerPhone(phone);
                EventBusNotificationResponse notificationResponse = new EventBusNotificationResponse();
                notificationResponse.setNotificationType(1);
                EventBus.getDefault().post(notificationResponse);
                finish();
            } else {
                ToastUtil.showToast(context, TextUtils.isEmpty(message) ? "操作失败" : message);
            }
        }, (code, message) -> ToastUtil.showToast(context, TextUtils.isEmpty(message) ? "操作失败" : message)));
    }

    private void changePageInfo() {
        if (!isFirstStep) {
            downTimer.stopDown();
            ivIcon.setImageDrawable(null);
            ivIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.notice));
            etVerificationCode.setText("");
            tvVerifyPhoneLabel.setText("绑定新手机号");
            etPhone.setText("");
            etPhone.setHint("请输入新手机号");
            etPhone.setEnabled(true);
            tvTipsLabel.setText("请输入您的新手机号，完成绑定");
            tvGetVerificationCode.setText(R.string.btn_login_getcode_text);
            tvGetVerificationCode.setClickable(true);
            tvSubmit.setText("提交");
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvGetVerificationCode.setText(String.format("%s后重发", String.valueOf(millisUntilFinished / 1000)));
        tvGetVerificationCode.setClickable(false);
    }

    @Override
    public void onFinish() {
        tvGetVerificationCode.setText(R.string.btn_login_getcode_text);
        tvGetVerificationCode.setClickable(true);
    }
}
