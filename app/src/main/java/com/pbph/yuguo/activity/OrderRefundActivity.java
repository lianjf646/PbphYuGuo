package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetOrderDetailRequest;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.response.OrderDetailResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

public class OrderRefundActivity extends BaseActivity {

    private final Context context = OrderRefundActivity.this;

    private TextView tvOrderStatus;
    private TextView tvOrderTips;
    private TextView tvRefundNumber;
    private TextView tvOrderNumber;

    private View layoutSingleGoods;
    private ImageView ivGoodsImage;
    private TextView tvGoodsName;
    private TextView tvGoodsIntro;
    private TextView tvGoodsPrice;
    private TextView tvGoodsNum;

    private View layoutMultiGoods;
    private LinearLayout llGoodsList;
    private TextView tvMore;

    private TextView tvRealPay;
    private TextView tvRefundReason;
    private TextView tvOrderMoney;

    private OrderDetailResponse.DataBean.OrderDetailBean orderDetail;
    private List<GoodsInfoBean> goodsList;

    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refund);
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
        tvOrderStatus = findViewById(R.id.tv_order_status);
        tvOrderTips = findViewById(R.id.tv_order_tips);
        tvRefundNumber = findViewById(R.id.tv_refund_number);
        tvOrderNumber = findViewById(R.id.tv_order_number);

        layoutSingleGoods = findViewById(R.id.layout_single_goods);
        ivGoodsImage = layoutSingleGoods.findViewById(R.id.iv_goods_image);
        tvGoodsName = layoutSingleGoods.findViewById(R.id.tv_goods_name);
        tvGoodsIntro = layoutSingleGoods.findViewById(R.id.tv_goods_intro);
        tvGoodsPrice = layoutSingleGoods.findViewById(R.id.tv_goods_price);
        tvGoodsNum = layoutSingleGoods.findViewById(R.id.tv_goods_num);

        layoutMultiGoods = findViewById(R.id.layout_multi_goods);
        llGoodsList = layoutMultiGoods.findViewById(R.id.ll_goods_list);
        tvMore = layoutMultiGoods.findViewById(R.id.tv_more);

        tvRealPay = findViewById(R.id.tv_real_pay);
        tvRefundReason = findViewById(R.id.tv_refund_reason);
        tvOrderMoney = findViewById(R.id.tv_order_money);
    }

    private void initData() {
        WaitUI.Show(context);
        GetOrderDetailRequest request = new GetOrderDetailRequest(orderId);
        HttpAction.getInstance().getOrderDetail(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            if (code == 200) {
                orderDetail = response.getData().getOrderDetail();
                setPage();
            }
            WaitUI.Cancel();
        }));
    }

    private void setPage() {
        setGoodsInfo();
        //退款状态
        OrderDetailResponse.DataBean.OrderDetailBean.BackOrderBean backOrder = orderDetail.getBackOrder();
        int backOrderStatus = backOrder.getBackOrderStatus();
        String orderStatusStr = "";
        switch (backOrderStatus) {
            case 1:
                orderStatusStr = "退单申请";
                break;
            case 2:
                orderStatusStr = "重新发货";
                break;
            case 3:
                orderStatusStr = "退单结束";
                break;
        }
        tvOrderTips.setText(orderStatusStr);
        tvRefundNumber.setText(backOrder.getBackOrderCode());
        tvOrderNumber.setText(orderDetail.getOrderCode());
        tvRefundReason.setText(backOrder.getBackOrderCause());

        tvOrderMoney.setText(String.format("%s", MoneyHelper.getInstance4Fen(backOrder.getBackOrderPrice()).change2Yuan().getString()));

        tvRealPay.setText(String.format("%s元", MoneyHelper.getInstance4Fen(orderDetail.getOrderDealPrice()).change2Yuan().getString()));
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
            layoutSingleGoods.setVisibility(View.GONE);
            layoutMultiGoods.setVisibility(View.GONE);
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

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
            layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.dp_20);

            for (int i = 0; i < goodsList.size(); i++) {
                String imageUrl = goodsList.get(i).getGoodsInfoPicUrl();
                RelativeLayout relativeLayout = new RelativeLayout(context);
                ImageView imageView = new ImageView(context);
                ImageView imageViewZP = new ImageView(context);
                imageViewZP.setBackgroundResource(R.drawable.complimentary);
                relativeLayout.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GlideUtil.displayImage(mContext, imageUrl, imageView);
                relativeLayout.addView(imageView);
                if(goodsList.get(i).gifts){
                    relativeLayout.addView(imageViewZP);
                }
                llGoodsList.addView(relativeLayout);
                totalSizeGoods += goodsList.get(i).getGoodsInfoNum();
            }
            tvMore.setText(String.format("共%s件", totalSizeGoods));
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
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
