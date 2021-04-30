package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.RechargeViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.MyGridView;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.adapter.choicehelper.abslistview.SingleChoiceHelper;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AppChangestoredMoneyResquest;
import com.pbph.yuguo.request.GetStoredConfigResquest;
import com.pbph.yuguo.request.PayOrderResquest;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.response.WxPayOrderResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.alipayUntil.AliPayAndShouQuan;
import com.pbph.yuguo.wxutil.WechatUtils;

import java.util.Calendar;

public class RechargeActivity extends BaseActivity implements View.OnClickListener {


    private MyGridView gridView;
    private DataAdapter adapter;

    private RadioButton rbWx;
    private RadioButton rbZfb;

    private Button btnPayRecharge;
    private long dealPrice = 0;
    private int dealWay = 2;//支付类型 0 微信 1支付宝
    private int customerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initTitle(TITLE_STYLE_WHITE, "余额充值", true, false);
        initView();
        getStoredConfig();
    }

    private void initView() {

        customerId = YuGuoApplication.userInfo.getCustomerId();
        rbWx = (RadioButton) findViewById(R.id.rb_wx);
        rbWx.setOnClickListener(this);
        rbZfb = (RadioButton) findViewById(R.id.rb_zfb);
        rbZfb.setOnClickListener(this);
        btnPayRecharge = findViewById(R.id.btn_pay_recharge);
        btnPayRecharge.setOnClickListener(this);

        gridView = (MyGridView) findViewById(R.id.gv_money);
        adapter = new DataAdapter(mContext, gridView, RechargeViewHolder.class,
                SingleChoiceHelper.class);
        gridView.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
                GetStoredConfigResponse.DataBean.StoredConfigDtoListBean storedConfigDtoListBean
                        = (GetStoredConfigResponse.DataBean.StoredConfigDtoListBean) adapter
                        .choiceHelper.putChoiceNotify(position);
                dealPrice = storedConfigDtoListBean.getStoredMoney();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("需支付");
                stringBuffer.append(String.valueOf(MoneyHelper.getInstance4Fen(dealPrice)
                        .change2Yuan().getInt()));
                stringBuffer.append("元");
                btnPayRecharge.setText(stringBuffer.toString());

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ConstantData.PayTypeSuccessOrFail == 1) {
            ConstantData.PayTypeSuccessOrFail = 0;
            finish();

        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay_recharge:
                if (dealPrice == 0) return;
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;
                    appChangestoredMoney();
                }
                break;
            case R.id.rb_wx:
                dealWay = 2;
                break;
            case R.id.rb_zfb:
                dealWay = 3;
                break;

        }
    }


    private void getStoredConfig() {
        HttpAction.getInstance().getStoredConfig(new GetStoredConfigResquest()).subscribe(new
                BaseObserver<>(this, (response -> {
            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            adapter.setDatas(response.getData().getStoredConfigDtoList());
            if (adapter.getCount() == 0) return;
            GetStoredConfigResponse.DataBean.StoredConfigDtoListBean storedConfigDtoListBean
                    = (GetStoredConfigResponse.DataBean.StoredConfigDtoListBean) adapter
                    .choiceHelper.putChoiceNotify(0);
            dealPrice = storedConfigDtoListBean.getStoredMoney();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("需支付");
            stringBuffer.append(String.valueOf(MoneyHelper.getInstance4Fen(dealPrice).change2Yuan()
                    .getInt()));
            stringBuffer.append("元");
            btnPayRecharge.setText(stringBuffer.toString());


        })));
    }

    private void appChangestoredMoney() {
        WaitUI.Show(this);
        HttpAction.getInstance().appChangestoredMoney(new AppChangestoredMoneyResquest
                (customerId, dealPrice, dealWay)).subscribe(new BaseObserver<>
                (this, response -> {
                    if (response.getCode() != 200) {
                        Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (dealWay == 2) {
                        wxPayOrder(dealWay, response.getData().getRechargeId());
                    } else if (dealWay == 3) {
                        aLiPayOrder(dealWay, response.getData().getRechargeId());

                    }
                }, (code, message) -> {

                }));

    }

    // 支付订单(待付款订单) 微信
    private void wxPayOrder(int orderPayWay, String orderId) {
        HttpAction.getInstance().wxPayOrder(new PayOrderResquest(orderId,
                orderPayWay, "4", "")).subscribe(new BaseObserver<WxPayOrderResponse>(this,
                response -> {
                    WaitUI.Cancel();
                    if (response.getCode() != 200) {
                        Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    WxPayOrderResponse.DataBean.PayInfoBean payInfoBean = response.getData()
                            .getPayInfo();
                    if (payInfoBean == null) return;
                    WechatUtils.getInstance().initWechatUtils(this).wechatPay(payInfoBean);
                }, ((code, message) -> {

        })));
    }

    // 支付订单(待付款订单) Ali
    private void aLiPayOrder(int orderPayWay, String orderId) {
        HttpAction.getInstance().aLiPayOrder(new PayOrderResquest(orderId,
                orderPayWay, "4", "")).subscribe(new BaseObserver<>(this,
                response -> {
                    WaitUI.Cancel();
                    if (response.getCode() != 200) {
                        Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AliPayAndShouQuan.getInstance().payV2(this, response.getData().getPayInfo(),
                            str -> {
                                finish();
                            });
                }, ((code, message) -> {

        })));
    }
}
