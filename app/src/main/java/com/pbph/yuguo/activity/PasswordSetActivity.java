package com.pbph.yuguo.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.PasswordInputEdt;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppChangeCustomerPayPasswordRequest;
import com.pbph.yuguo.request.GetAppChangeCustomerPhoneRequest;
import com.pbph.yuguo.request.GetAppSmsCodeResquest;
import com.pbph.yuguo.util.DownTimer;
import com.sobot.chat.utils.ToastUtil;

public class PasswordSetActivity extends BaseActivity implements DownTimer.DownTimerListener {

    private final Context context = PasswordSetActivity.this;
    private DownTimer downTimer = new DownTimer();
    /*验证手机号布局 start*/
    private View viewPhoneLayout;
    private EditText etPhone;
    private EditText etVerificationCode;
    private TextView tvGetVerificationCode;
    private TextView tvNextStep;
    /*验证手机号布局 end*/

    /*设置密码布局 start*/
    private View viewPasswordLayout;
    private PasswordInputEdt piePassword1;
    private PasswordInputEdt piePassword2;
    private TextView tvSubmit;
    /*设置密码布局 start*/

    private boolean isFirstStep = true;
    //第一次设置的密码
    private String firstPassword;
    //第二次设置的密码
    private String secedePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);
        initTitle(TITLE_STYLE_WHITE, "设置密码", true, false);
        initView();
        initData();
        initClick();
    }

    private void initView() {
        //验证手机号布局
        downTimer.setListener(this);
        viewPhoneLayout = findViewById(R.id.view_phone_layout);
        etPhone = viewPhoneLayout.findViewById(R.id.et_phone);
        etVerificationCode = viewPhoneLayout.findViewById(R.id.et_verification_code);
        tvGetVerificationCode = viewPhoneLayout.findViewById(R.id.tv_get_verification_code);
        tvNextStep = viewPhoneLayout.findViewById(R.id.tv_next_step);

        String oldPhone = YuGuoApplication.userInfo.getCustomerPhone();
        etPhone.setEnabled(false);
        etPhone.setText(oldPhone);

        //设置密码布局
        viewPasswordLayout = findViewById(R.id.view_password_layout);
        piePassword1 = viewPasswordLayout.findViewById(R.id.pie_password1);
        piePassword2 = viewPasswordLayout.findViewById(R.id.pie_password2);
        tvSubmit = viewPasswordLayout.findViewById(R.id.tv_submit);
    }

    private void initData() {

    }

    private void initClick() {
        tvNextStep.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etVerificationCode.getText().toString())) {
                showToast("请填写验证码");
                return;
            }
            WaitUI.Show(context);
            GetAppChangeCustomerPhoneRequest request = new GetAppChangeCustomerPhoneRequest(-1, etPhone.getText().toString(), etVerificationCode.getText().toString());
            HttpAction.getInstance().checkSms(request).subscribe(new BaseObserver<>(context, response -> {
                WaitUI.Cancel();
                int code = response.getCode();
                String message = response.getMsg();
                if (200 == code) {
                    nextStep();
                } else {
                    ToastUtil.showToast(context, TextUtils.isEmpty(message) ? "操作失败" : message);
                }
            }, (code, message) -> ToastUtil.showToast(context, TextUtils.isEmpty(message) ? "操作失败" : message)));
        });

        piePassword1.setOnInputOverListener(text -> firstPassword = text);

        piePassword2.setOnInputOverListener(text -> secedePassword = text);

        tvGetVerificationCode.setOnClickListener(v -> {
            WaitUI.Show(context);

            HttpAction.getInstance().getAppSmsCode(new GetAppSmsCodeResquest(etPhone.getText().toString())).subscribe(new BaseObserver<>(context, (response -> {
                WaitUI.Cancel();
                if (200 != response.getCode()) {
                    showToast(response.getMsg());
                }
                downTimer.startDown(60 * 1000);
            })));
        });

        tvSubmit.setOnClickListener(v -> {
            submit();
        });
    }

    private void nextStep() {
        if (isFirstStep) {
            downTimer.stopDown();
            viewPhoneLayout.setVisibility(View.GONE);
            viewPasswordLayout.setVisibility(View.VISIBLE);
            isFirstStep = false;
        }
    }

    private void submit() {
        //第二步 设置密码
        if (TextUtils.isEmpty(firstPassword) || TextUtils.isEmpty(secedePassword)) {
            ToastUtil.showToast(context, "密码不能为空");
            return;
        }
        if (firstPassword.length() < 6 || secedePassword.length() < 6) {
            ToastUtil.showToast(context, "请补全密码");
            return;
        }
        if (!firstPassword.equals(secedePassword)) {
            ToastUtil.showToast(context, "两次密码不一致");
            return;
        }
        //设置成功
        WaitUI.Show(context);
        GetAppChangeCustomerPayPasswordRequest request = new GetAppChangeCustomerPayPasswordRequest(YuGuoApplication.userInfo.getCustomerId(), secedePassword);
        HttpAction.getInstance().appChangeCustomerPayPassword(request).subscribe(new BaseObserver<>(context, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            if (200 == code) {
                showToast("密码设置成功");
                finish();
            }
        }));
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvGetVerificationCode.setText(String.format("%s秒后重发", String.valueOf(millisUntilFinished / 1000)));
        tvGetVerificationCode.setClickable(false);
    }

    @Override
    public void onFinish() {
        tvGetVerificationCode.setText(R.string.btn_login_getcode_text);
        tvGetVerificationCode.setClickable(true);
    }
}
