package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.SubmitOrderForGroupResquest;
import com.pbph.yuguo.response.GetGoodsGroupDetailResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.ToastDialog;

public class GroupOrderFillActivity extends BaseActivity {

    private final Context context = GroupOrderFillActivity.this;

    //未选择收货地址
    private TextView tvChooseAddress;
    //    收货地址
    private View addressView;
    private TextView tvPhone, tvUserName, tvAddress;

    //单个商品
    private LinearLayout goodsLayout;
    private ImageView ivGoodsInfoImage;
    View iv_order_fill_goods_image_state;
    TextView tv_adapter_goods_list_state;

    private TextView tvGoodsName;
    private TextView tv_goods_intro;
    private TextView tvGoodsPrice;

    private ImageView ivStepperReduce;
    private ImageView ivStepperIncrease;
    private TextView tvGoodsNum;


    private TextView tvTotalPrice;


    //优惠 实际付款
    private TextView tvFavorablePrice, tvRealPay;

    //提交订单
    private TextView tvSubmitOrder;

    //////////

    GetGoodsGroupDetailResponse.DataBean dataBean;

    String addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YuGuoApplication application = (YuGuoApplication) getApplication();
        dataBean = (GetGoodsGroupDetailResponse.DataBean) application.object;
        application.object = null;

        setContentView(R.layout.activity_group_order_fill);
        initTitle(TITLE_STYLE_WHITE, "订单填写", true, false);

        initView();

        goodsLayout.setVisibility(View.VISIBLE);
        iv_order_fill_goods_image_state.setVisibility(View.GONE);
        tv_adapter_goods_list_state.setVisibility(View.GONE);


        defAddress();

        initClick();

        showPlaceOrder();

    }

    private void defAddress() {
        Integer id = YuGuoApplication.userInfo.getRecAddId();
        if (id == null || id == -1) {
            //选择好地址后 将选择地址的按钮隐藏
            tvChooseAddress.setVisibility(View.VISIBLE);
            //展示所选的地址
            addressView.setVisibility(View.GONE);
            return;
        }

        //选择好地址后 将选择地址的按钮隐藏
        tvChooseAddress.setVisibility(View.GONE);
        //展示所选的地址
        addressView.setVisibility(View.VISIBLE);

        addressId = String.valueOf(id);

        String userName = YuGuoApplication.userInfo.getRecUserName();
        String phone = YuGuoApplication.userInfo.getRecPhone();
        String address = YuGuoApplication.userInfo.getReDistrict();

        String receiverLat = String.valueOf(YuGuoApplication.userInfo.getRecLatitude());
        String receiverLng = String.valueOf(YuGuoApplication.userInfo.getRecLongitude());

        tvUserName.setText(userName);
        tvPhone.setText(phone);
        tvAddress.setText(address);

//        placeOrderResquest.setReceiverAddress(address);
//
//        placeOrderResquest.setReceiverLat(receiverLat);
//        placeOrderResquest.setReceiverLng(receiverLng);

    }


    @Override
    public void onLeftClick() {
        finish();
    }

    private void initView() {

        TextView tv = findViewById(R.id.tv_goods_price_pressssss);
        tv.setText("￥");

        iv_order_fill_goods_image_state = findViewById(R.id.iv_order_fill_goods_image_state);
        iv_order_fill_goods_image_state.setVisibility(View.GONE);

        tv_adapter_goods_list_state = findViewById(R.id.tv_adapter_goods_list_state);
        tv_adapter_goods_list_state.setVisibility(View.GONE);


        tv_goods_intro = findViewById(R.id.tv_goods_intro);

        goodsLayout = findViewById(R.id.ll_order_fill_buy_now_layout);
        tvChooseAddress = findViewById(R.id.tv_choose_address);
        ivStepperReduce = findViewById(R.id.iv_stepper_reduce);
        tvGoodsNum = findViewById(R.id.tv_goods_num);
        ivStepperIncrease = findViewById(R.id.iv_stepper_increase);


        ivGoodsInfoImage = findViewById(R.id.iv_order_fill_goods_image);
        tvGoodsPrice = findViewById(R.id.tv_goods_price);
        tvGoodsName = findViewById(R.id.tv_goods_name);
        tvSubmitOrder = findViewById(R.id.tv_submit_order);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        addressView = findViewById(R.id.layout_check_address);
        tvUserName = addressView.findViewById(R.id.tv_user_name);
        tvPhone = addressView.findViewById(R.id.tv_phone);
        tvAddress = addressView.findViewById(R.id.tv_address);

        tvFavorablePrice = findViewById(R.id.tv_favorable_price);
        tvRealPay = findViewById(R.id.tv_real_pay);


    }

    private void initClick() {

        //选择地址
        tvChooseAddress.setOnClickListener(v -> {
            Intent intent = new Intent(context, MyAddressListActivity.class);
            startActivityForResult(intent, 1);
        });
        //选择地址
        addressView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MyAddressListActivity.class);
            startActivityForResult(intent, 1);
        });


//        //-商品数量
//        ivStepperReduce.setOnClickListener(v -> {
//            goodsNum = Integer.parseInt(tvGoodsNum.getText().toString().trim());
//            if (goodsNum <= 1) {
////                Toast.makeText(context, "已达到最小值", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            goodsNum = goodsNum - 1;
//            tvGoodsNum.setText(goodsNum + "");
//            placeOrderResquest.setGoodsNum(goodsNum);
//            getPageInfoForPlaceOrder();
//        });
//        //+商品数量
//        ivStepperIncrease.setOnClickListener(v -> {
//            goodsNum = Integer.parseInt(tvGoodsNum.getText().toString().trim());
//            goodsNum = goodsNum + 1;
//            tvGoodsNum.setText(goodsNum + "");
//            placeOrderResquest.setGoodsNum(goodsNum);
//            getPageInfoForPlaceOrder();
//        });

        //提交订单
        tvSubmitOrder.setOnClickListener(v -> {
            if (TextUtils.isEmpty(addressId)) {      //提示还未选择地址
                ToastDialog.show(mContext, "提示", "请选择收货地址", () -> {
                    Intent intent = new Intent(context, MyAddressListActivity.class);
                    startActivityForResult(intent, 1);
                });
                return;
            }
            submitOrderForGroup();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //选择好地址后 将选择地址的按钮隐藏
            tvChooseAddress.setVisibility(View.GONE);
            //展示所选的地址
            addressView.setVisibility(View.VISIBLE);

            addressId = data.getStringExtra("addressId");

            String userName = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String address = data.getStringExtra("address");

            String receiverLat = data.getStringExtra("receiverLat");
            String receiverLng = data.getStringExtra("receiverLng");

            tvUserName.setText(userName);
            tvPhone.setText(phone);
            tvAddress.setText(address);

        }
    }


    private void showPlaceOrder() {

        GetGoodsGroupDetailResponse.DataBean.GoodsDetailBean goodsListBean = dataBean.getGoodsDetail();

//        GlideUtil.displayCircleBitmap(this, goodsListBean.getGoodsPicUrl(), ivGoodsInfoImage);
        Glide.with(mContext).load(goodsListBean.getGoodsGroupPicUrl()).into(ivGoodsInfoImage);
        tvGoodsName.setText(goodsListBean.getGoodsGroupName());
        tv_goods_intro.setText(goodsListBean.getGoodsGroupNameSub());
        tvGoodsPrice.setText(MoneyHelper.getInstance4Fen(goodsListBean.getGroupPrice()).change2Yuan().getString());

        tvGoodsNum.setText(String.valueOf(1));


        tvTotalPrice.setText("￥");
        tvTotalPrice.append(MoneyHelper.getInstance4Fen(goodsListBean.getGroupPrice()).change2Yuan().getString());


        tvFavorablePrice.setText("￥");
        tvFavorablePrice.append(MoneyHelper.getInstance4Fen(0).change2Yuan().getString());

        tvRealPay.setText("￥");
        tvRealPay.append(MoneyHelper.getInstance4Fen(goodsListBean.getGroupPrice()).change2Yuan().getString());


    }


    private void submitOrderForGroup() {
        SubmitOrderForGroupResquest resquest = new SubmitOrderForGroupResquest();

        resquest.goodsGroupActivityId = dataBean.getGoodsDetail().getGoodsGroupActivityId();
        resquest.receiverAddressId = addressId;
        resquest.goodsGroupNum = 1;
        resquest.goodsGroupPrice = dataBean.getGoodsDetail().getGroupPrice();

        showLoadingDialog();
        HttpAction.getInstance().submitOrderForGroup(resquest).subscribe(new BaseObserver<>(this, response -> {
            hideLoadingDialog();
            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }

            Intent intent = new Intent(GroupOrderFillActivity.this, PayCoreActivity.class);
            intent.putExtra(PayCoreActivity.PAY_TYPE, "2");
            intent.putExtra(PayCoreActivity.ORDER_ID, String.valueOf(response.getData().getCustomerGroupId()));
            intent.putExtra(PayCoreActivity.ORDER_MONEY, tvRealPay.getText().toString().trim());
            startActivity(intent);
            finish();
        }));
    }


}
