package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.BothBtnCenterPopWin;
import com.pbph.yuguo.dialog.CancelOrderPopWin;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetConfirmReceiptRequest;
import com.pbph.yuguo.request.GetOrderDetailRequest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.request.OrderAgainRequest;
import com.pbph.yuguo.request.Riderh5ViewResquest;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.response.OrderDetailResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class OrderDetailActivity extends BaseActivity {
    private final Context context = OrderDetailActivity.this;

    private View viewMain;

    private TextView tvOrderStatus;
    private TextView tvOrderTips;

    private TextView tvUserName;
    private TextView tvPhone;
    private TextView tvAddress;

    private LinearLayout llGoodsView;
    private View layoutSingleGoods;
    private ImageView ivGoodsImage;
    private TextView tvGoodsName;
    private TextView tvGoodsIntro;
    private TextView tvGoodsPrice;
    private TextView tvGoodsNum;

    private View layoutMultiGoods;
    private LinearLayout llGoodsList;
    private TextView tvMore;

    private TextView tvTotalPrice;
    private TextView tvDistributionMoney;
    private TextView tvDiscounts;
    private TextView tvRealPay;
    private TextView tvDistributionTips;
    private RelativeLayout rlDistributionMain;
    private RelativeLayout rlDistributionDis;
    private TextView tvPinkagePrice;
    private TextView tvRealDis;
    private RelativeLayout rlVipPrice;
    private RelativeLayout rlPackingExpense;
    private TextView tvPackingExpense;
    private TextView tvVipPrice;
    private View viewVipLine;

    private TextView tvOrderTime;
    private TextView tvOrderNumber;
    private LinearLayout llPayType;
    private TextView tvPayType;

    private View layoutDistributionInfo;
    private TextView tvDistributionStatus;
    private TextView tvDistributionTime;

    private LinearLayout llDeliveryTime;
    private TextView tvDeliveryTime;

    private View rlBottom;
    private TextView tvBtn1;
    private TextView tvBtn2;
    private TextView tvBtn3;

    private boolean isApplyAfter = false;

    private int orderId;
    private String payTypeStr;
    private OrderDetailResponse.DataBean.OrderDetailBean orderDetail;
    private List<GoodsInfoBean> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
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
            isApplyAfter = intent.getBooleanExtra("isApplyAfter", false);
        }
    }

    private void initView() {
        viewMain = findViewById(R.id.view_main);

        View layoutOrderStatus = findViewById(R.id.layout_order_status);
        tvOrderStatus = layoutOrderStatus.findViewById(R.id.tv_order_status);
        tvOrderTips = layoutOrderStatus.findViewById(R.id.tv_order_tips);

        View layoutCheckAddress = findViewById(R.id.layout_check_address);
        tvUserName = layoutCheckAddress.findViewById(R.id.tv_user_name);
        tvPhone = layoutCheckAddress.findViewById(R.id.tv_phone);
        tvAddress = layoutCheckAddress.findViewById(R.id.tv_address);

        llGoodsView = findViewById(R.id.ll_goods_view);
        layoutSingleGoods = findViewById(R.id.layout_single_goods);
        ivGoodsImage = layoutSingleGoods.findViewById(R.id.iv_goods_image);
        tvGoodsName = layoutSingleGoods.findViewById(R.id.tv_goods_name);
        tvGoodsIntro = layoutSingleGoods.findViewById(R.id.tv_goods_intro);
        tvGoodsPrice = layoutSingleGoods.findViewById(R.id.tv_goods_price);
        tvGoodsNum = layoutSingleGoods.findViewById(R.id.tv_goods_num);

        layoutMultiGoods = findViewById(R.id.layout_multi_goods);
        llGoodsList = layoutMultiGoods.findViewById(R.id.ll_goods_list);
        tvMore = layoutMultiGoods.findViewById(R.id.tv_more);

        View layoutOrderMoney = findViewById(R.id.layout_order_money);
        tvTotalPrice = layoutOrderMoney.findViewById(R.id.tv_total_price);
        tvDistributionMoney = layoutOrderMoney.findViewById(R.id.tv_distribution_money);
        tvDiscounts = layoutOrderMoney.findViewById(R.id.tv_discounts);
        tvRealPay = layoutOrderMoney.findViewById(R.id.tv_real_pay);
        tvDistributionTips = layoutOrderMoney.findViewById(R.id.tv_distribution_tips);
        rlDistributionMain = layoutOrderMoney.findViewById(R.id.rl_distribution_main);
        rlDistributionDis = layoutOrderMoney.findViewById(R.id.rl_distribution_dis);
        tvPinkagePrice = layoutOrderMoney.findViewById(R.id.tv_pinkage_price);
        tvRealDis = layoutOrderMoney.findViewById(R.id.tv_real_dis);
        rlVipPrice = layoutOrderMoney.findViewById(R.id.rl_vip_price);
        rlPackingExpense = layoutOrderMoney.findViewById(R.id.rl_packing_expense);
        tvPackingExpense = layoutOrderMoney.findViewById(R.id.tv_packing_expense);
        tvVipPrice = layoutOrderMoney.findViewById(R.id.tv_vip_price);
        viewVipLine = layoutOrderMoney.findViewById(R.id.view_vip_line);

        View layoutOrderInfo = findViewById(R.id.layout_order_info);
        tvOrderTime = layoutOrderInfo.findViewById(R.id.tv_order_time);
        tvOrderNumber = layoutOrderInfo.findViewById(R.id.tv_order_number);
        llPayType = layoutOrderInfo.findViewById(R.id.ll_pay_type);
        tvPayType = layoutOrderInfo.findViewById(R.id.tv_pay_type);

        layoutDistributionInfo = findViewById(R.id.layout_distribution_info);
        tvDistributionStatus = findViewById(R.id.tv_distribution_status);
        tvDistributionTime = findViewById(R.id.tv_distribution_time);

        llDeliveryTime = findViewById(R.id.ll_delivery_time);
        tvDeliveryTime = findViewById(R.id.tv_delivery_time);

        rlBottom = findViewById(R.id.rl_bottom);
        tvBtn1 = findViewById(R.id.tv_btn1);
        tvBtn2 = findViewById(R.id.tv_btn2);
        tvBtn3 = findViewById(R.id.tv_btn3);
    }

    private void initData() {
        viewMain.setVisibility(View.VISIBLE);
        WaitUI.Show(context);
        //获取网络数据
        GetOrderDetailRequest request = new GetOrderDetailRequest(orderId);
        HttpAction.getInstance().getOrderDetail(request).subscribe(new BaseObserver<>(context, response -> {
            viewMain.setVisibility(View.GONE);
            WaitUI.Cancel();
            int code = response.getCode();
            String msg = response.getMsg();

            if (code == 200) {
                orderDetail = response.getData().getOrderDetail();
                setOrderStatus();
            } else {
                showToast(TextUtils.isEmpty(msg) ? "获取订单详情失败" : msg);
            }
        }));
    }

    private void setOrderStatus() {
        setAddress();
        setGoodsInfo();
        setOrderPrice();
        setOrderInfo();
        setDeliveryTime();

        switch (orderDetail.getOrderPayWay()) {
            case 1:
            case 2:
                payTypeStr = "微信支付";
                break;
            case 3:
                payTypeStr = "支付宝";
                break;
            case 4:
                payTypeStr = "余额支付";
                break;
            default:
                break;
        }

        double openMemberPrice = orderDetail.getOpenMembersDiscountPrice();
        if (openMemberPrice == 0) {
            rlVipPrice.setVisibility(View.GONE);
            viewVipLine.setVisibility(View.GONE);
        } else {
            rlVipPrice.setVisibility(View.VISIBLE);
            viewVipLine.setVisibility(View.VISIBLE);
            tvVipPrice.setText(String.format("￥%s", MoneyHelper.getInstance4Yuan(openMemberPrice / 100).change2Yuan().getString()));
        }
        int backOrderFlag = orderDetail.getBackOrderFlag();
        int anewDeliverFlag = orderDetail.getAnewDeliverFlag();
        int orderStatus = orderDetail.getOrderStatus();
        switch (orderStatus) {
            case 1:
                //待付款
                int timeRemaining = orderDetail.getTimeDifference();
                new MyCount(timeRemaining * 1000, 1000).start();

                tvOrderStatus.setText("待付款");
                tvBtn1.setText("去支付");
                tvBtn2.setText("取消订单");
                tvBtn3.setVisibility(View.GONE);
                llPayType.setVisibility(View.GONE);

                tvBtn2.setOnClickListener(new OnSPClickListener() {
                    @Override
                    public void onClickSucc(View v) {
                        CancelOrderPopWin popWin = new CancelOrderPopWin(mContext, orderId, 1);
                        popWin.show(v);
                    }
                });

                tvBtn1.setOnClickListener(v -> {
                    Intent intent = new Intent(context, PayCoreActivity.class);
                    intent.putExtra(PayCoreActivity.PAY_TYPE, "1");
                    intent.putExtra(PayCoreActivity.ORDER_ID, String.valueOf(orderId));
                    intent.putExtra(PayCoreActivity.ORDER_MONEY, tvRealPay.getText().toString().trim());
                    intent.putExtra(PayCoreActivity.SURPLUS_TIME, timeRemaining);
                    startActivity(intent);
                    finish();
                });
                break;
            case 2:
                //待发货
                llPayType.setVisibility(View.VISIBLE);
                tvBtn2.setVisibility(View.GONE);
                tvBtn3.setVisibility(View.GONE);
                if (backOrderFlag == 0) {
                    rlBottom.setVisibility(View.GONE);
                } else {
                    if (anewDeliverFlag == 0) {
                        rlBottom.setVisibility(View.VISIBLE);
                    } else {
                        rlBottom.setVisibility(View.GONE);
                    }
                }
                tvOrderStatus.setText("待发货");
                tvOrderTips.setText("等待打包发货");
                tvPayType.setText(payTypeStr);
                tvBtn1.setText("申请退款");
                tvBtn1.setOnClickListener(new OnSPClickListener() {
                    @Override
                    public void onClickSucc(View v) {
                        CancelOrderPopWin popWin = new CancelOrderPopWin(mContext, orderId, 2);
                        popWin.show(v);
                    }
                });
                break;
            case 3:
                //已发货  显示配送信息
                int orderType = orderDetail.getOrderType();
                switch (orderType) {
                    case 1:
                        //普通订单
                        int distributionTime = orderDetail.getDistributionTime();
                        switch (distributionTime) {
                            case 0:
                                layoutDistributionInfo.setVisibility(View.GONE);
                                break;
                            case 1:
                                layoutDistributionInfo.setVisibility(View.VISIBLE);
                                tvDistributionStatus.setText(orderDetail.getStatusDescribe());
                                tvDistributionTime.setText(orderDetail.getExpressStatusTime());
                                break;
                        }
                        break;
                    case 2:
                        //团购订单
                        layoutDistributionInfo.setVisibility(View.GONE);
                        rlDistributionMain.setEnabled(false);
                        break;
                }
                llPayType.setVisibility(View.VISIBLE);
                tvBtn2.setVisibility(View.GONE);
                tvOrderStatus.setText("待收货");
                tvOrderTips.setText("订单配送中,等待收货");
                tvPayType.setText(payTypeStr);
                tvBtn1.setText("确认收货");
                tvBtn1.setOnClickListener(v -> {
                    BothBtnCenterPopWin popWin = new BothBtnCenterPopWin(mContext, v, "确认收货", "确认是本人收货?", "取消", "确定", true);
                    popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                        @Override
                        public void onFirstBtnClick() {
                            popWin.dismiss();
                        }

                        @Override
                        public void onSecondBtnClick() {
                            confirmReceipt();
                            popWin.dismiss();
                        }
                    });
                });
                break;
            case 4:
                //已签收
                if (isApplyAfter) {
                    //申请售后的订单详情  不展示底部按钮
                    rlBottom.setVisibility(View.GONE);
                } else {
                    tvBtn1.setVisibility(View.GONE);
                    tvBtn2.setVisibility(View.GONE);
                }
                tvOrderStatus.setText("已签收");
                tvOrderTips.setText("订单已签收");
                tvPayType.setText(payTypeStr);
                break;
            case 5:
                //已完成
                llPayType.setVisibility(View.VISIBLE);
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setVisibility(View.GONE);
                tvOrderStatus.setText("已完成");
                tvOrderTips.setText("订单已完成");
                tvPayType.setText(payTypeStr);
                break;
            case 6:
                //已取消
                llPayType.setVisibility(View.GONE);
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setVisibility(View.GONE);
                tvOrderStatus.setText("已取消");
                tvOrderTips.setText("订单已取消");
                break;
        }
    }

    //确认收货
    private void confirmReceipt() {
        UserInfo userInfo = YuGuoApplication.userInfo;
        if (userInfo == null || userInfo.getCustomerId() == -1) {
            showToast("登陆后才能确认收货");
            return;
        }
        WaitUI.Show(context);
        GetConfirmReceiptRequest request = new GetConfirmReceiptRequest(orderId, userInfo.getCustomerId());
        HttpAction.getInstance().confirmReceipt(request).subscribe(new BaseObserver<>(context, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            String msg = response.getMsg();
            if (200 == code) {
                showToast("收货成功");
                OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
                EventBus.getDefault().post(orderDetailResponse);
                refreshOrderStatus();
            } else {
                showToast(TextUtils.isEmpty(msg) ? "收货失败" : msg);
            }
        }));
    }

    private void refreshOrderStatus() {
        initData();
    }

    public void cancelOrder() {
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        EventBus.getDefault().post(orderDetailResponse);
        finish();
    }

    private void setAddress() {
        tvUserName.setText(orderDetail.getReceiverName());
        tvPhone.setText(orderDetail.getReceiverPhone());
        tvAddress.setText(orderDetail.getReceiverAddress());
    }

    private void setGoodsInfo() {
        /*
        * 判断商品数量
        * 单个的展示 layoutSingleGoods
        * 多个的展示 layoutMultiGoods
        * */
        goodsList = orderDetail.getOrderGoodsInfoList();
        List<GoodsInfoBean> goodsSonList = orderDetail.getOrderGoodsInfoSonList();

        if (goodsSonList != null && goodsSonList.size() > 0) {
            for (GoodsInfoBean goodsSon : goodsSonList) {
                goodsSon.gifts = true;
            }
            goodsList.addAll(goodsSonList);
        }

        if (goodsList == null || goodsList.size() == 0) {
            llGoodsView.setVisibility(View.GONE);
            return;
        }

        if (goodsList.size() == 1) {
            GoodsInfoBean goods = goodsList.get(0);
            layoutSingleGoods.setVisibility(View.VISIBLE);
            layoutMultiGoods.setVisibility(View.GONE);
            GlideUtil.displayImage(mContext, goods.getGoodsInfoPicUrl(), ivGoodsImage);
            tvGoodsName.setText(goods.getGoodsInfoName());
            tvGoodsIntro.setText(goods.getGoodsInfoSpecValue());
            tvGoodsPrice.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(goods.getGoodsInfoMemberPrice()).change2Yuan().getString()));
            tvGoodsNum.setText(String.format("x%s", goods.getGoodsInfoNum()));
        } else {
            int totalSizeGoods = 0;

            layoutSingleGoods.setVisibility(View.GONE);
            layoutMultiGoods.setVisibility(View.VISIBLE);

            int measure = getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(measure, measure);

            for (int i = 0; i < goodsList.size(); i++) {
                String imageUrl = goodsList.get(i).getGoodsInfoPicUrl();
                RelativeLayout relativeLayout = new RelativeLayout(context);
                ImageView imageView = new ImageView(context);
                ImageView imageViewZP = new ImageView(context);
                imageViewZP.setBackgroundResource(R.drawable.complimentary);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GlideUtil.displayImage(mContext, imageUrl, imageView);
                relativeLayout.setPadding(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_20), 0, 0, 0);
                relativeLayout.addView(imageView);
                if (goodsList.get(i).gifts) {
                    relativeLayout.addView(imageViewZP);
                }
                llGoodsList.addView(relativeLayout);
                totalSizeGoods += goodsList.get(i).getGoodsInfoNum();
            }
            tvMore.setText(String.format("共%s件", totalSizeGoods));
        }
    }

    private void setOrderPrice() {
        tvTotalPrice.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderOldPrice()).change2Yuan().getString()));
        tvDistributionMoney.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderFreight()).change2Yuan().getString()));
        tvDiscounts.setText(String.format("-￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderDiscountsPrice()).change2Yuan().getString()));
        tvRealPay.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getOrderDealPrice()).change2Yuan().getString()));
        int realPayFullXPrice = orderDetail.getRealPayFullXPrice();
        int pinkagePrice = orderDetail.getPinkagePrice();
        /*int distributionTime = orderDetail.getDistributionTime();
        if(distributionTime != 0){
            tvDistributionTips.setCompoundDrawables(null, null, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.pay_attention_to);
            //这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvDistributionTips.setCompoundDrawables(null, null, drawable, null);
        }*/
        if (pinkagePrice == 0) {
            rlDistributionDis.setVisibility(View.GONE);
        } else {
            rlDistributionDis.setVisibility(View.VISIBLE);
            tvPinkagePrice.setText(String.format("满%s元减运费", MoneyHelper.getInstance4Fen(realPayFullXPrice).change2Yuan().getString()));
            tvRealDis.setText(String.format("-￥%s", MoneyHelper.getInstance4Fen(pinkagePrice).change2Yuan().getString()));
        }
        tvPackingExpense.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(orderDetail.getPackingExpense()).change2Yuan().getString()));
    }

    private void setOrderInfo() {
        tvOrderTime.setText(orderDetail.getCreateTime());
        tvOrderNumber.setText(orderDetail.getOrderCode());
    }

    private void setDeliveryTime() {
        int orderType = orderDetail.getOrderType();
        switch (orderType) {
            case 1:
                //普通订单
                llDeliveryTime.setVisibility(View.VISIBLE);
                int distributionTime = orderDetail.getDistributionTime();
                String distributionTimeStr = "";
                switch (distributionTime) {
                    case 0:
                        distributionTimeStr = "配送时间：明日配送";
                        rlDistributionMain.setEnabled(false);
//                        Drawable drawable = getResources().getDrawable(R.color.transparent);
//                        //这一步必须要做,否则不会显示.
//                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                        tvDistributionTips.setCompoundDrawables(null, null, drawable, null);
                        break;
                    case 1:
                        distributionTimeStr = "配送时间：立即配送";
                        break;
                }
                tvDeliveryTime.setText(distributionTimeStr);
                tvDistributionTips.setEnabled(false);
                break;
            case 2:
                //团购订单
                llDeliveryTime.setVisibility(View.GONE);
                rlDistributionMain.setEnabled(false);
                break;
        }
    }

    private void initClick() {
        tvMore.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                YuGuoApplication application = (YuGuoApplication) getApplication();
                application.object = goodsList;
                startActivity(new Intent(mContext, MyGoodsListActivity.class));
            }
        });

        rlPackingExpense.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                YuGuoApplication application = (YuGuoApplication) getApplication();
                application.object = goodsList;
                startActivity(new Intent(mContext, MyGoodsPeiSongFeiListActivity.class));
            }
        });

        /**
         * 配送费不可以点击 并且隐藏配送费图标
         */
//        rlDistributionMain.setOnClickListener(new OnSPClickListener() {
//            @Override
//            public void onClickSucc(View v) {
//                PeiSongFeiDialog.show(mContext,
//                        orderDetail.getBasicsPrice(),
//                        orderDetail.getOverhangPrice(),
//                        orderDetail.getSuperheavyPrice(),
//                        orderDetail.getOverWeightExplain(),
//                        orderDetail.getOverDistanceExplain(),
//                        orderDetail.getOrderFreight());
//            }
//        });

        layoutDistributionInfo.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                getAliToken();
            }
        });

        tvBtn3.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                orderAgain(orderId);
            }
        });
    }

    private void orderAgain(int orderId) {
        int orderType = orderDetail.getOrderType();
        if (orderType == 2) {
            //团购订单  提示不可再来一单
            ToastUtil.showToast(context, "团购订单不支持再来一单哦~");
            return;
        }
        WaitUI.Show(context);
        OrderAgainRequest request = new OrderAgainRequest(YuGuoApplication.userInfo.getCustomerId(), orderId);
        HttpAction.getInstance().orderAgain(request).subscribe(new BaseObserver<>(context, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            String msg = response.getMsg();
            if (200 == code) {
                startActivity(new Intent(context, ShoppingCarActivity.class));
            } else {
                ToastUtil.showToast(context, msg);
            }
        }));
    }

    private void getAliToken() {
        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest()).subscribe(new BaseObserver<>(mContext, response -> {
            if (response.getCode() == 200) {
                String token = response.getData().getToken();
                String projectCode = response.getData().getProjectCode();
                getH5Url(token, projectCode);
            }
        }));
    }

    private void getH5Url(String token, String projectCode) {

        Riderh5ViewResquest request = new Riderh5ViewResquest();
        request.orderId = orderDetail.getThirdOrderCode();
        request.orderType = 1;
        request.token = token;
        request.projectCode = projectCode;

        HttpAction.getInstance().riderh5View(request).subscribe(new BaseObserver<>(context, response -> {
            String url = response.getData().getUrl();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }));
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
            tvBtn2.setEnabled(false);
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            EventBus.getDefault().post(orderDetailResponse);
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
