package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AppChangeVipInfoResquest;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetMemberOpenPriceRequest;
import com.pbph.yuguo.request.PayOrderResquest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.GetMemberOpenPriceResponse;
import com.pbph.yuguo.response.WxPayOrderResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.alipayUntil.AliPayAndShouQuan;
import com.pbph.yuguo.wxutil.WechatUtils;
import com.sobot.chat.utils.ToastUtil;

public class OpenVipActivity extends BaseActivity implements View.OnClickListener {

    private int orderPayWay = 0; // 0是微信 1是支付宝

    private View layout_vip_head;
    private ImageView iv_heard_icon;
    private TextView tv_name;
    private TextView tv_vip;
    private TextView tv_time;

    private TextView tv_preferential_price;
    private TextView tv_original_price;

    private View layout_pay_type;
    private RadioButton rbnWx, rbnZfb;
    private Button btn_confirm;

    GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;
    GetMemberOpenPriceResponse.DataBean priceBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(TITLE_STYLE_WHITE, "会员", true, false);
        setContentView(R.layout.activity_open_vip);
        initView();
        getMemberOpenPrice();
    }

    private void initView() {
        layout_vip_head = findViewById(R.id.layout_vip_head);
        iv_heard_icon = mContentView.findViewById(R.id.iv_heard_icon);
        tv_name = mContentView.findViewById(R.id.tv_name);
        tv_vip = mContentView.findViewById(R.id.tv_vip);
        tv_time = mContentView.findViewById(R.id.tv_time);

        tv_preferential_price = findViewById(R.id.tv_preferential_price);
        tv_original_price = findViewById(R.id.tv_original_price);
        tv_original_price.getPaint().setAntiAlias(true);//抗锯齿
//        tv_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        layout_pay_type = findViewById(R.id.layout_pay_type);
        rbnWx = findViewById(R.id.rbn_wx);
        rbnWx.setOnClickListener(this);
        rbnZfb = findViewById(R.id.rbn_zfb);
        rbnZfb.setOnClickListener(this);

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbn_wx:
                orderPayWay = 0;
                break;
            case R.id.rbn_zfb:
                orderPayWay = 1;
                break;
            case R.id.btn_confirm:
                if (priceBean == null) {
                    ToastUtil.showToast(mContext, "获取会员价格失败");
                    return;
                }
                appChangeVipInfo();
                break;
        }
    }

    private void appChangeVipInfo() {
        WaitUI.Show(this);
        HttpAction.getInstance().appChangeVipInfo(new AppChangeVipInfoResquest(YuGuoApplication
                .userInfo.getCustomerId(), priceBean.getDiscountPrice(),
                orderPayWay, priceBean.getTimeUnit()))
                .subscribe(new BaseObserver<>(this, response -> {
                    if (response.getCode() != 200) {
                        ToastUtil.showToast(mContext, response.getMsg());
                        return;
                    }
                    if (orderPayWay == 0) {
                        wxPayOrder(response.getData().getRechargeId());
                    } else if (orderPayWay == 1) {
                        aLiPayOrder(response.getData().getRechargeId());
                    }
                }));
    }

    //获取会员充值价格
    private void getMemberOpenPrice() {

        HttpAction.getInstance().getOpenMemberPrice(new GetMemberOpenPriceRequest()).subscribe(new BaseObserver<>(this, response -> {

            if (response.getCode() != 200) {
                ToastUtil.showToast(mContext, response.getMsg());
                return;
            }
            //时间单位(1年，2月，3季，4周，5日)
            priceBean = response.getData();
            String label = null;
            switch (priceBean.getTimeUnit()) {
                case 1:
                    label = "年";
                    break;
                case 2:
                    label = "月";
                    break;
                case 3:
                    label = "季";
                    break;
                case 4:
                    label = "周";
                    break;
                case 5:
                    label = "日";
                    break;
            }
            tv_preferential_price.setText(String.format("￥%s元/%s", priceBean.getDiscountPrice() / 100, label));
            tv_original_price.setText(String.format("￥%s元/%s", priceBean.getOpenPrice() / 100, label));
        }));
    }

    // 支付订单(待付款订单) 微信
    private void wxPayOrder(String orderId) {
        PayOrderResquest resquest = new PayOrderResquest(orderId, 2, "3", "");
        HttpAction.getInstance().wxPayOrder(resquest).subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            if (response.getCode() != 200) {
                ToastUtil.showToast(mContext, response.getMsg());
                return;
            }
            WxPayOrderResponse.DataBean.PayInfoBean payInfoBean = response.getData().getPayInfo();
            if (payInfoBean == null) {
                return;
            }
            WechatUtils.getInstance().initWechatUtils(this).wechatPay(payInfoBean);
        }));
    }

    // 支付订单(待付款订单) Ali
    private void aLiPayOrder(String orderId) {
        PayOrderResquest resquest = new PayOrderResquest(orderId, 3, "3", "");
        HttpAction.getInstance().aLiPayOrder(resquest).subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            if (response.getCode() != 200) {
                ToastUtil.showToast(mContext, response.getMsg());
                return;
            }
            AliPayAndShouQuan.getInstance().payV2(this, response.getData().getPayInfo(), str -> getCustomerInfoById());
        }));
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
        WaitUI.Show(mContext);
        GetCustomerInfoByIdRequest request = new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId());
        HttpAction.getInstance().getCustomerInfoById(request).subscribe(new BaseObserver<>(mContext, response -> {

            WaitUI.Cancel();
            if (response.getCode() != 200) {
                return;
            }

            YuGuoApplication.userInfo.setCustomerLevelType(response.getData().getCustomer().getCustomerLevelType());

            customerBean = response.getData().getCustomer();
            GlideUtil.displayCircleBitmap(mContext, customerBean.getCustomerImgUrl(), iv_heard_icon);
            tv_name.setText(customerBean.getCustomerName());
            if (customerBean.getCustomerLevelType() == 0) { //会员类型（0普通，1试用，2正式）
                tv_time.setVisibility(View.GONE);
                tv_vip.setBackgroundResource(R.drawable.bg_vip_logo_solid_corner);
                tv_vip.setText("未开通");
                btn_confirm.setText("立即开通VIP会员");
            } else if (customerBean.getCustomerLevelType() == 1) {
                tv_time.setVisibility(View.VISIBLE);
                tv_time.setText(TextUtils.isEmpty(customerBean.getCustomerVipExpireTime()) ? "" : customerBean.getCustomerVipExpireTimeString() + "会员到期");
                tv_vip.setBackgroundResource(R.drawable.bg_vip_logo_corner);
                tv_vip.setText("体验会员");
                btn_confirm.setText("立即开通VIP会员");
            } else {
                tv_time.setVisibility(View.VISIBLE);
                tv_time.setText(TextUtils.isEmpty(customerBean.getCustomerVipExpireTime()) ? "" : customerBean.getCustomerVipExpireTimeString() + "会员到期");
                tv_vip.setBackgroundResource(R.drawable.bg_vip_logo_corner);
                tv_vip.setText("VIP会员");
                btn_confirm.setText("立即续费");
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerInfoById();
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
