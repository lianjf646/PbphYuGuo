package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.PayPasswordDialog;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetOrderConfigResquest;
import com.pbph.yuguo.request.PayOrderResquest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.GetOrderConfigResponse;
import com.pbph.yuguo.response.WxPayOrderResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.YesNoDialog;
import com.pbph.yuguo.util.alipayUntil.AliPayAndShouQuan;
import com.pbph.yuguo.wxutil.WechatUtils;

import org.jsoup.helper.StringUtil;

import java.util.Calendar;

/**
 * Created by 连嘉凡 on 2018/9/11.
 */

public class PayCoreActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton rbnBalancePay;
    private RadioButton rbnWxPay;
    private RadioButton rbnAliPay;
    private Button btnConfirmPay;
    private TextView tvMoney;
    private TextView tvSurplusTime;


    private String orderId;
    private String payType; //支付类型(1:普通订单支付;2:团购支付;3会员支付;4储值支付)
    private int orderPayWay = 2;//支付方式(1:H5微信;2:APP微信;3:支付宝;4余额支付)
    private String money;
    private Long surplusTime;
    private String payPassword = "";
    private int jump_type = 0; // 0线上支付 1线下支付 （跳转到线上详情，或者线下详情）

    public final static String PAY_TYPE = "payType";
    public final static String ORDER_ID = "orderId";
    public final static String ORDER_MONEY = "orderMoney";
    public final static String SURPLUS_TIME = "surplus_time";
    public final static String JUMP_TYPE = "jump_type";


    private CountDownTimer countDownTimer;
    GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;
    long ONECE_TIME = 1000;


    @Override
    public void onLeftClick() {
        if (!payType.equals("1")) {
            finish();
        } else {
            showDialog();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_core);
        initTitle(TITLE_STYLE_WHITE, "支付中心", true, false);
        initView();
        initPopup();

    }

    private void initPopup() {
    }


    private void initView() {

        payType = getIntent().getStringExtra(PAY_TYPE);
        orderId = getIntent().getStringExtra(ORDER_ID);
        money = getIntent().getStringExtra(ORDER_MONEY);
        jump_type = getIntent().getIntExtra(JUMP_TYPE, 0);
        surplusTime = Long.valueOf(getIntent().getIntExtra(SURPLUS_TIME, 0));

        rbnWxPay = findViewById(R.id.rbn_wx_pay);
        rbnWxPay.setOnClickListener(this);
        rbnAliPay = findViewById(R.id.rbn_ali_pay);
        rbnAliPay.setOnClickListener(this);
        rbnBalancePay = findViewById(R.id.rbn_balance_pay);
        rbnBalancePay.setOnClickListener(this);
        btnConfirmPay = (Button) findViewById(R.id.btn_confirm_pay);
        btnConfirmPay.setOnClickListener(this);
        tvMoney = findViewById(R.id.tv_money);
        tvSurplusTime = findViewById(R.id.tv_surplus_time);

        tvMoney.setText(money);
        money = money.replace("￥", "").replace("元","").trim();

        getCustomerInfoById();
        if (!payType.equals("1")) {
            tvSurplusTime.setVisibility(View.GONE);
        } else {
            tvSurplusTime.setVisibility(View.VISIBLE);
            if (surplusTime == 0) {
                getOrderConfig();
            } else {
                startSurplusTime(surplusTime * 1000);
            }
        }
    }

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_confirm_pay:
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;
                    submission();
                }

                break;
            case R.id.rbn_balance_pay:
                orderPayWay = 4;
                break;
            case R.id.rbn_ali_pay:
                orderPayWay = 3;
                break;
            case R.id.rbn_wx_pay:
                orderPayWay = 2;
                break;
        }
    }

    /**
     * 提交订单
     */
    private void submission() {

        if (orderPayWay == 2) {
            wxPay();//微信支付
        } else if (orderPayWay == 3) {
            aLiPay();// 阿里支付
        } else if (orderPayWay == 4) {
            balancePay();//余额支付
        }
    }

    private void wxPay() {
        WaitUI.Show(this);
        HttpAction.getInstance().wxPayOrder(new PayOrderResquest(orderId, orderPayWay, payType,
                "")).subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            WxPayOrderResponse.DataBean.PayInfoBean payInfoBean = response.getData().getPayInfo();
            if (payInfoBean == null) return;
            WechatUtils.getInstance().initWechatUtils(this).wechatPay(payInfoBean);
        }, ((code, message) -> {

        })));
    }

    private void aLiPay() {
        WaitUI.Show(this);
        HttpAction.getInstance().aLiPayOrder(new PayOrderResquest(orderId, orderPayWay, payType,
                "")).subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            String payInfo = response.getData().getPayInfo();
            if (payInfo == null) return;
            AliPayAndShouQuan.getInstance().payV2(this, payInfo, str -> {
                goToPaySuccessActivity();
            });

        }, ((code, message) -> {

        })));
    }


    private void balancePay() {

        if (StringUtil.isBlank(payPassword)) {
            Toast.makeText(mContext, "请点击我的-账户安全-储值支付密码，进行密码设置", Toast.LENGTH_SHORT).show();
            return;
        }

        new PayPasswordDialog(this, payPassword -> {
            WaitUI.Show(this);
            HttpAction.getInstance().aLiPayOrder(new PayOrderResquest(orderId, orderPayWay,
                    payType, payPassword)).subscribe(new BaseObserver<>(this, response -> {
                WaitUI.Cancel();
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                if (response.getCode() != 200) return;
                goToPaySuccessActivity();
            }, ((code, message) -> {

            })));
        });

    }

    private void showDialog() {
        YesNoDialog.show(this, "确认要取消支付?", new YesNoDialog.OnClickRateDialog() {
            @Override
            public void onClickLeft() {

            }

            @Override
            public void onClickRight() {
                Intent intentObligation = new Intent(mContext, MyOrderActivity.class);
                intentObligation.putExtra("orderStatus", 1);
                startActivity(intentObligation);
                finish();
            }
        }, true);
    }

    private void goToPaySuccessActivity() {
        if (payType.equals("1")) {
            Intent intent = new Intent(PayCoreActivity.this, PaySuccessActivity.class);
            intent.putExtra(JUMP_TYPE,jump_type);
            intent.putExtra("orderId", orderId);
            intent.putExtra("money", money);
            startActivity(intent);
            ConstantData.PayTypeSuccessOrFail = 0;
        } else {
            Intent intent = new Intent(PayCoreActivity.this, MainTabActivity.class).addFlags
                    (Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest
                (YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>
                (mContext, response -> {
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                rbnBalancePay.setClickable(false);
                return;
            }

            customerBean = response.getData().getCustomer();

            YuGuoApplication.userInfo.setCustomerLevelType(customerBean.getCustomerLevelType());

            payPassword = customerBean.getPayPassword();
            rbnBalancePay.setText("储值支付(余额￥");
            rbnBalancePay.append(MoneyHelper.getInstance4Fen(customerBean.getStoredMoney())
                    .change2Yuan().getString());
            rbnBalancePay.append(")");
            if (Double.valueOf(MoneyHelper.getInstance4Fen(customerBean.getStoredMoney())
                    .change2Yuan().getString()) < Double.valueOf(money)) {
                rbnBalancePay.setClickable(false);
            }

        }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!payType.equals("1")) {
                finish();
            } else {
                showDialog();
            }

            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ConstantData.PayTypeSuccessOrFail == 1) {
            goToPaySuccessActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
        super.onDestroy();


    }

    /**
     * 倒计时 处理TextView
     *
     * @param millisUntilFinished
     */
    private void surplusTimeTv(Long millisUntilFinished) {
        long hour = (millisUntilFinished / 1000) / 3600;
        long minute = ((millisUntilFinished / 1000) - hour * 3600) / 60;
        long second = millisUntilFinished / 1000 - hour * 3600 - minute * 60;

        String hourStr;
        String minuteStr;
        String secondStr;

        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }

        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }

        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }
//        LogUtils.e("DDDD", "onTick: " + String.format("剩余时间：%s:%s:%s", hourStr, minuteStr,
//                secondStr));
        tvSurplusTime.setText("剩余支付时间:  ");
        tvSurplusTime.append(hourStr);
        tvSurplusTime.append(":");
        tvSurplusTime.append(minuteStr);
        tvSurplusTime.append(":");
        tvSurplusTime.append(secondStr);
    }

    //获取订单配置信息
    private void getOrderConfig() {
        HttpAction.getInstance().getOrderConfig(new GetOrderConfigResquest()).subscribe(new
                BaseObserver<GetOrderConfigResponse>(this, response -> {
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            GetOrderConfigResponse.DataBean.OrderConfigDtoBean orderConfigDtoBean = response
                    .getData().getOrderConfigDto();
            if (orderConfigDtoBean.getOrderAutoCancelTime() == null) return;
            if (orderConfigDtoBean.getOrderAutoCancelTime() == 0) return;
            long orderAutoCancelTime = orderConfigDtoBean.getOrderAutoCancelTime() * 60 * 1000;
            startSurplusTime(orderAutoCancelTime);

        }, (code, message) -> {

        }));

    }

    /**
     * 开始到计时
     *
     * @param surplusTime
     */
    private void startSurplusTime(Long surplusTime) {
        countDownTimer = new CountDownTimer(surplusTime, ONECE_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                surplusTimeTv(millisUntilFinished);

            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

}
