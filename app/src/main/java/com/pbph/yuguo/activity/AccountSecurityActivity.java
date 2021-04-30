package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AlipayauthsignResquest;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.response.EventBusNotificationResponse;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.util.alipayUntil.AliPayAndShouQuan;
import com.pbph.yuguo.wxutil.WechatUtils;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AccountSecurityActivity extends BaseActivity {
    private final Context context = AccountSecurityActivity.this;


    private RelativeLayout rlPayPassword;
    private TextView tvPhone;
    private RelativeLayout rlPhone;
    private TextView tvWechat;
    private RelativeLayout rlWechat;
    //    private TextView tvAlipay;
//    private RelativeLayout rlAlipay;
    private RelativeLayout rlNameAuth;

    AliPayAndShouQuan aliPayAndShouQuan = new AliPayAndShouQuan();
    private GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        initTitle(TITLE_STYLE_WHITE, "账户安全", true, false);
        initView();
        initData();
        initClick();
    }

    private void initView() {
        EventBus.getDefault().register(context);
        String phone = YuGuoApplication.userInfo.getCustomerPhone();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(context, "手机号获取失败");
            finish();
        }
        tvPhone = findViewById(R.id.tv_phone);
        rlPhone = findViewById(R.id.rl_phone);
        tvWechat = findViewById(R.id.tv_wechat);
        rlWechat = findViewById(R.id.rl_wechat);
//        tvAlipay = findViewById(R.id.tv_alipay);
//        rlAlipay = findViewById(R.id.rl_alipay);
        rlPayPassword = findViewById(R.id.rl_pay_password);
        rlNameAuth = findViewById(R.id.rl_name_auth);
        tvPhone.setText(phone);
    }

    private void initData() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        WaitUI.Show(context);
        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest(customerId)).subscribe(new BaseObserver<>(mContext, response -> {
            WaitUI.Cancel();
            if (200 == response.getCode()) {
                customerBean = response.getData().getCustomer();
                YuGuoApplication.userInfo.setCustomerLevelType(customerBean.getCustomerLevelType());
                setUserInfo();
            }
        }));
    }

    private void initClick() {
        rlPhone.setOnClickListener(v -> {
            startActivity(new Intent(context, ChangePhoneActivity.class));
        });

        rlPayPassword.setOnClickListener(v -> {
            startActivity(new Intent(context, PasswordSetActivity.class));
        });

        rlWechat.setOnClickListener(v -> {
            WechatUtils.getInstance().initAuthWechatUtils(this).weChatLogin();
        });

//        rlAlipay.setOnClickListener(v -> {
//            getAliToken();
//        });

        rlNameAuth.setOnClickListener(v -> {
            //跳转到实名认证
            startActivity(new Intent(this, RealNameStateActivity.class));
        });
    }

    private void setUserInfo() {
        String wxNickName = customerBean.getWxNickName();

        if (!TextUtils.isEmpty(wxNickName)) {
            tvWechat.setText(wxNickName);
            rlWechat.setEnabled(false);
        } else {
            tvWechat.setText("请绑定微信");
        }
//        String alipayAccount = customerBean.getAlipayAccount();
//        if (!TextUtils.isEmpty(alipayAccount)) {
//            tvAlipay.setText("已绑定");
//            rlAlipay.setEnabled(false);
//        } else {
//            tvAlipay.setText("请绑定支付宝账号");
//        }
    }

    private void getAliToken() {
        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest()).subscribe(new BaseObserver<>(this, response -> {
            if (response.getCode() == 200) {
                String token = response.getData().getToken();
                String projectCode = response.getData().getProjectCode();
                getAliSign(token, projectCode);
            } else {
                showToast("获取Token信息失败");
            }
        }));
    }

    private void getAliSign(String token, String projectCode) {
        AlipayauthsignResquest resquest = new AlipayauthsignResquest();
        resquest.token = token;
        resquest.projectCode = projectCode;

        HttpAction.getInstance().aliAuthSign(resquest).subscribe(new BaseObserver<>(this, response -> {
            if (response.getCode() == 200) {
                String sign = response.getData().getSign();
                aliPayAndShouQuan.onCreate(this);
                aliPayAndShouQuan.authV2(sign);
                aliPayAndShouQuan.setAliListener(this::saveAliCode);
            } else {
                showToast("获取用户信息失败");
            }
        }));
    }

    private void saveAliCode(String alipayAccount) {
//        int customerId = YuGuoApplication.userInfo.getCustomerId();
//        if (customerId == -1 || customerId == 0) {
//            showToast("用户Id获取失败");
//            return;
//        }
//        GetAppChangeCustomerInfoRequest request = new GetAppChangeCustomerInfoRequest(customerId, null, null, null, null, alipayAccount, null, null, -1);
//        HttpAction.getInstance().appChangeCustomerInfo(request).subscribe(new BaseObserver<>(context, response -> {
//            int code = response.getCode();
//            if (200 == code) {
//                tvAlipay.setText("已绑定");
//                rlAlipay.setEnabled(false);
//            } else {
//                showToast("绑定失败");
//            }
//        }));
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPage(EventBusNotificationResponse notificationResponse) {
        int notificationType = notificationResponse.getNotificationType();
        switch (notificationType) {
            case 1:
                //刷新电话号码
                tvPhone.setText(YuGuoApplication.userInfo.getCustomerPhone());
                break;
            case 2:
                //刷新微信号
                tvWechat.setText(notificationResponse.getWxNickName());
                rlWechat.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(context);
    }
}
