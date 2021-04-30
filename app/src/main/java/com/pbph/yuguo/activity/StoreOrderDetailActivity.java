package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetOrderDetailRequest;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.response.StoreOrderDetailResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.sobot.chat.utils.ToastUtil;

import java.util.List;

public class StoreOrderDetailActivity extends BaseActivity {

    private TextView tvOrderStatus;
    private TextView tvOrderTips;

    private TextView tvPayMoney;
    private TextView tvDiscountMoney;
    private TextView tvRealPayMoney;

    private TextView tvOrderTime;
    private TextView tvOrderNumber;
    private TextView tvStoreName;

    private LinearLayout llBottom;
    private TextView tvBtn1;

    private LinearLayout ll_goods_list;

    private int orderId;
    private String payTypeStr;
    private StoreOrderDetailResponse.DataBean.OrderDetailBean orderDetail;
    private List<GoodsInfoBean> goodsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order_detail);
        initTitle(TITLE_STYLE_WHITE, "订单详情", true, false);
        initIntent();
        initView();
        initData();
        initClick();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getIntExtra("orderId", -1);
        }
    }

    private void initView() {

        View layoutOrderStatus = findViewById(R.id.layout_order_status);
        tvOrderStatus = layoutOrderStatus.findViewById(R.id.tv_order_status);
        tvOrderTips = layoutOrderStatus.findViewById(R.id.tv_order_tips);

        tvPayMoney = findViewById(R.id.tv_pay_money);
        tvDiscountMoney = findViewById(R.id.tv_discount_money);
        tvRealPayMoney = findViewById(R.id.tv_real_pay_money);

        View layoutOrderInfo = findViewById(R.id.layout_order_info);
        tvOrderTime = layoutOrderInfo.findViewById(R.id.tv_order_time);
        tvOrderNumber = layoutOrderInfo.findViewById(R.id.tv_order_number);
        tvStoreName = layoutOrderInfo.findViewById(R.id.tv_store_name);

        llBottom = findViewById(R.id.ll_bottom);
        tvBtn1 = findViewById(R.id.tv_btn1);

        ll_goods_list = findViewById(R.id.ll_goods_list);
    }

    private void initData() {
        getStoreOrderDetail();
    }

    private void initClick() {

    }

    private void getStoreOrderDetail() {
        WaitUI.Show(mContext);
        GetOrderDetailRequest request = new GetOrderDetailRequest(orderId);
        HttpAction.getInstance().getStoreOrderDetail(request).subscribe(new BaseObserver<>(mContext, (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                orderDetail = orderDate.getData().getOrderDetail();
                setStoreOrderDetail();
            }
            WaitUI.Cancel();
        }), (code, message) -> {
            WaitUI.Cancel();
            ToastUtil.showToast(mContext, message);
        }));
    }

    private void setStoreOrderDetail() {
        setOrderStatus();
        setGoodsListView();
        tvPayMoney.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderOldPrice()).change2Yuan().getString()));
        tvDiscountMoney.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderDiscountsPrice()).change2Yuan().getString()));
        tvRealPayMoney.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderDealPrice()).change2Yuan().getString()));
        tvOrderTime.setText(orderDetail.getCreateTime());
        tvOrderNumber.setText(orderDetail.getOrderCode());
        tvStoreName.setText(orderDetail.getStoreName());
    }

    private void setOrderStatus() {
        int orderStatus = orderDetail.getOrderStatus();
        switch (orderStatus) {
            case 1:
                //待付款
                tvOrderStatus.setText("待付款");
                llBottom.setVisibility(View.VISIBLE);
                int timeRemaining = orderDetail.getTimeDifference();
                new MyCount(timeRemaining * 1000, 1000).start();

                tvBtn1.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, PayCoreActivity.class);
                    intent.putExtra(PayCoreActivity.PAY_TYPE, "1");
                    intent.putExtra(PayCoreActivity.JUMP_TYPE, 1);
                    intent.putExtra(PayCoreActivity.ORDER_ID, String.valueOf(orderId));
                    intent.putExtra(PayCoreActivity.ORDER_MONEY, MoneyHelper.getInstance4Fen(orderDetail.getOrderDealPrice()).change2Yuan().getString());
                    intent.putExtra(PayCoreActivity.SURPLUS_TIME, timeRemaining);
                    startActivity(intent);
                    finish();
                });
                break;
            case 4:
                //已付款
                tvOrderStatus.setText("已付款");
                tvOrderTips.setText("订单已付款");
                llBottom.setVisibility(View.GONE);
                break;
            case 5:
                //已完成
                tvOrderStatus.setText("已完成");
                tvOrderTips.setText("订单已完成");
                llBottom.setVisibility(View.GONE);
                break;
            case 6:
                //已取消
                tvOrderStatus.setText("已取消");
                tvOrderTips.setText("订单已取消");
                llBottom.setVisibility(View.GONE);
                break;
        }
    }

    private void setGoodsListView() {
        List<StoreOrderDetailResponse.DataBean.OrderDetailBean.OrderGoodsListBean> goodsList = orderDetail.getOrderGoodsList();
        if (goodsList == null || goodsList.size() == 0) {
            return;
        }
        for (int i = 0; i < goodsList.size(); i++) {
            StoreOrderDetailResponse.DataBean.OrderDetailBean.OrderGoodsListBean goodsDetail = goodsList.get(i);
            View goodsItem = LayoutInflater.from(this).inflate(R.layout.item_store_order_goods_list, null);
            TextView tv_goods_name = goodsItem.findViewById(R.id.tv_goods_name);
            TextView tv_weight = goodsItem.findViewById(R.id.tv_weight);
            tv_goods_name.setText(goodsDetail.getGoodsName());
            //售卖方式  0:称重  1:计件
            int salesMethod = goodsDetail.getSalesMethod();
            switch (salesMethod) {
                case 0:
                    tv_weight.setText(String.format("%s公斤", (float) goodsDetail.getGoodsNumWeight() / 1000));
                    break;
                case 1:
                    tv_weight.setText(String.format("%s件", goodsDetail.getGoodsNumWeight()));
                    break;
            }
            ll_goods_list.addView(goodsItem);
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    class MyCount extends CountDownTimer {
        MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            String timeRemaining = "剩余时间：00:00:00";
            tvOrderTips.setText(timeRemaining);
            tvBtn1.setEnabled(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
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
            tvOrderTips.setText(String.format("剩余时间：%s:%s:%s", hourStr, minuteStr, secondStr));
        }
    }
}
