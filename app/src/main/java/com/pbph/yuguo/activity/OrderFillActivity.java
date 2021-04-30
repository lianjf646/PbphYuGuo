package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.OrderFillShopingCarGoodsAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.VoucherPopwin;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetPageInfoForSubmitOrderResquest;
import com.pbph.yuguo.request.SubmitOrderResquest;
import com.pbph.yuguo.response.GetPageInfoForSubmitOrderResponse;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.ToastDialog;

import java.util.ArrayList;
import java.util.List;

public class OrderFillActivity extends BaseActivity {

    private final Context context = OrderFillActivity.this;

    //未选择收货地址
    private TextView tvChooseAddress;
    //    收货地址
    private View addressView;
    private ImageView ivRight;
    private TextView tvPhone, tvUserName, tvAddress;


    private RelativeLayout rl_order_fill_sendtime;
    private TextView tv_order_fill_sendtime;
    OptionsPickerView timeOptionsPickerView;
    List<String> timeOptionsPickerViewList = new ArrayList<>();
    int timeChooseDataPos = 0;


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


    //    多商品
    private RelativeLayout shoppingCarLayout;
    private GridView mGoodsGridView;
    private OrderFillShopingCarGoodsAdapter goodsAdapter;
    private List<GoodsInfoBean> allGoodsList = new ArrayList<>();
    private TextView tvShoppingCarCount;


    private TextView tvTotalPrice;

    private RelativeLayout rl_distribution_label;//配送费
    private TextView tvDistributionFee;
    TextView tv_distribution_manjian;
    TextView tv_distribution_fee_sub;

    private RelativeLayout rl_order_fill_baozhuagnfei;
    private TextView tv_order_fill_baozhuagnfei;

    //优惠券
    private RelativeLayout rlCoupon;
    private TextView tvCoupon;

//    //抵用券
//    private RelativeLayout rlVoucher;
//    private TextView tvVoucher;

    //会员
    private View ll_order_fill_open_vip;
    //    private TextView tvVipCoupon;
//    private TextView tvVipPrice;
    private CheckBox cbVipChoose;

    //优惠 实际付款
    private TextView tvFavorablePrice, tvRealPay;

    //提交订单
    private TextView tvSubmitOrder;

    ImageView iv_distribution_label;

    //////////

    private VoucherPopwin couponPop, vouchePop;

    private GetPageInfoForSubmitOrderResquest placeOrderResquest;
    private GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean placeOrderResponse;

    String addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YuGuoApplication application = (YuGuoApplication) getApplication();
        placeOrderResquest = (GetPageInfoForSubmitOrderResquest) application.object;
        application.object = null;
        if (placeOrderResquest == null) return;

        setContentView(R.layout.activity_order_fill);
        initTitle(TITLE_STYLE_WHITE, "订单填写", true, false);

        initView();

        if (placeOrderResquest.getOrderFillType() == 1) {
            goodsLayout.setVisibility(View.VISIBLE);
            shoppingCarLayout.setVisibility(View.GONE);
        } else {
            goodsLayout.setVisibility(View.GONE);
            shoppingCarLayout.setVisibility(View.VISIBLE);
        }

        initTimeOptionsPickerView();
        initClick();

        defAddress();

        getPageInfoForSubmitOrder();
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

        placeOrderResquest.setReceiverAddress(address);

        placeOrderResquest.setReceiverLat(receiverLat);
        placeOrderResquest.setReceiverLng(receiverLng);

    }

    boolean isFlush = true;

    @Override
    protected void onStart() {
        super.onStart();
        if (isFlush) {
//            getPageInfoForSubmitOrder();
        }
        isFlush = true;
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    private void initView() {

        TextView tv = findViewById(R.id.tv_goods_price_pressssss);
        tv.setText("￥");

        iv_distribution_label = findViewById(R.id.iv_distribution_label);
        rl_order_fill_sendtime = findViewById(R.id.rl_order_fill_sendtime);
        tv_order_fill_sendtime = findViewById(R.id.tv_order_fill_sendtime);

        iv_order_fill_goods_image_state = findViewById(R.id.iv_order_fill_goods_image_state);
        iv_order_fill_goods_image_state.setVisibility(View.GONE);

        tv_adapter_goods_list_state = findViewById(R.id.tv_adapter_goods_list_state);
        tv_adapter_goods_list_state.setVisibility(View.GONE);


        ll_order_fill_open_vip = findViewById(R.id.ll_order_fill_open_vip);

        rl_distribution_label = findViewById(R.id.rl_distribution_label);
        tv_goods_intro = findViewById(R.id.tv_goods_intro);

        mGoodsGridView = findViewById(R.id.gv_order_fill_grid);

        goodsAdapter = new OrderFillShopingCarGoodsAdapter(this, allGoodsList);
        mGoodsGridView.setAdapter(goodsAdapter);
        shoppingCarLayout = findViewById(R.id.ll_order_fill_shoppint_car_layout);
        goodsLayout = findViewById(R.id.ll_order_fill_buy_now_layout);
        tvChooseAddress = findViewById(R.id.tv_choose_address);
        ivStepperReduce = findViewById(R.id.iv_stepper_reduce);
        tvGoodsNum = findViewById(R.id.tv_goods_num);
        ivStepperIncrease = findViewById(R.id.iv_stepper_increase);
//        rlVoucher = findViewById(R.id.rl_order_fill_voucher);
        rlCoupon = findViewById(R.id.rl_order_fill_coupon);


        ivGoodsInfoImage = findViewById(R.id.iv_order_fill_goods_image);
        tvGoodsPrice = findViewById(R.id.tv_goods_price);
        tvDistributionFee = findViewById(R.id.tv_distribution_fee);
        tvShoppingCarCount = findViewById(R.id.tv_shopping_car_goods_count);
        tvGoodsName = findViewById(R.id.tv_goods_name);
        tvSubmitOrder = findViewById(R.id.tv_submit_order);
        tvCoupon = findViewById(R.id.tv_coupon);
//        tvVoucher = findViewById(R.id.tv_member_voucher);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        addressView = findViewById(R.id.layout_check_address);
        tvUserName = addressView.findViewById(R.id.tv_user_name);
        ivRight = addressView.findViewById(R.id.iv_right);
        ivRight.setVisibility(View.VISIBLE);
        tvPhone = addressView.findViewById(R.id.tv_phone);
        tvAddress = addressView.findViewById(R.id.tv_address);

        tvFavorablePrice = findViewById(R.id.tv_favorable_price);
        tvRealPay = findViewById(R.id.tv_real_pay);
//        tvVipPrice = findViewById(R.id.tv_vip_price);
//        tvVipCoupon = findViewById(R.id.tv_vip_coupon_price);
        cbVipChoose = findViewById(R.id.cb_vip_choose);

        tv_distribution_manjian = findViewById(R.id.tv_distribution_manjian);
        tv_distribution_fee_sub = findViewById(R.id.tv_distribution_fee_sub);

        rl_order_fill_baozhuagnfei = findViewById(R.id.rl_order_fill_baozhuagnfei);
        tv_order_fill_baozhuagnfei = findViewById(R.id.tv_order_fill_baozhuagnfei);

//        cbVipChoose.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            int realpay = placeOrderResponse.getActualPrice();
//            int vipPice = placeOrderResponse.getMemberOpenPrice();
//            if (isChecked) {
//                realpay = realpay + vipPice;
//            } else {
//                realpay = realpay - vipPice;
//            }
//            tvRealPay.setText("￥");
//            tvRealPay.append(MoneyHelper.getInstance4Fen(realpay).change2Yuan().getString());
//        });

        placeOrderResquest.setMemberPrice(0);

        cbVipChoose.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                placeOrderResquest.setMemberPrice(placeOrderResponse.getMemberOpenDiscountPrice());
            } else {
                placeOrderResquest.setMemberPrice(0);
            }
            getPageInfoForSubmitOrder();

        });
    }

    private void initClick() {

        //选择地址
        tvChooseAddress.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                isFlush = false;
                Intent intent = new Intent(context, MyAddressListActivity.class);
                intent.putExtra("order_address_id", addressId);
                startActivityForResult(intent, 1);
            }
        });
        //选择地址
        addressView.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                isFlush = false;
                Intent intent = new Intent(context, MyAddressListActivity.class);
                intent.putExtra("order_address_id", addressId);
                startActivityForResult(intent, 1);
            }
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
        //抵用券


        shoppingCarLayout.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                YuGuoApplication application = (YuGuoApplication) getApplication();
                application.object = allGoodsList;
                startActivity(new Intent(mContext, MyGoodsListActivity.class));
            }
        });
        mGoodsGridView.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
                YuGuoApplication application = (YuGuoApplication) getApplication();
                application.object = allGoodsList;
                startActivity(new Intent(mContext, MyGoodsListActivity.class));
            }
        });

        /**
         * 配送费不可点击 隐藏弹窗
         */
//        rl_distribution_label.setOnClickListener(new OnSPClickListener() {
//            @Override
//            public void onClickSucc(View v) {
//                if (placeOrderResponse == null) return;
//                if (timeChooseDataPos == 1) {
//                    iv_distribution_label.setVisibility(View.GONE);
//                    return;
//                }
//                PeiSongFeiDialog.show(mContext
//                        , placeOrderResponse.getBasic()
//                        , placeOrderResponse.getOverDistance()
//                        , placeOrderResponse.getOverWeight()
//                        , placeOrderResponse.getOverWeightExplain()
//                        , placeOrderResponse.getOverDistanceExplain()
//                        , placeOrderResponse.getFreight());
//            }
//        });

        rl_order_fill_sendtime.setOnClickListener(new OnSPClickListener() {

            @Override
            public void onClickSucc(View v) {
                if (placeOrderResponse.getDeliveryType() == 0) {
                    Toast.makeText(context, "超过营业时间，现在下单只能明日配送哦!", Toast.LENGTH_SHORT).show();
                    return;
                }
                timeOptionsPickerView.show();
            }
        });

        rlCoupon.setOnClickListener(new OnSPClickListener() {

            @Override
            public void onClickSucc(View v) {

                List<GoodsInfoBean> temps = new ArrayList<>(placeOrderResponse.getGoodsInfoList().size());
                for (GoodsInfoBean vo : placeOrderResponse.getGoodsInfoList()) {
                    if (vo.getAvaliableFlag() == 0) continue;
                    temps.add(vo);
                }

                if (temps.size() <= 0) {
                    Toast.makeText(context, "无有效优惠券", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (couponPop == null) {
                    couponPop = new VoucherPopwin(mContext, ConstantData.COUPON_TYPE, temps, placeOrderResponse.getSumPrice());
                    couponPop.setOnPopWinClickListener(id -> {
                        placeOrderResquest.setCouponId(id);
                        getPageInfoForSubmitOrder();
                    });
                } else {
                    couponPop.setDatas(temps, placeOrderResponse.getSumPrice());
                }

                GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.CouponBean coupon = placeOrderResponse.getCoupon();
                if (coupon != null) {
                    couponPop.adapter.pos_id = coupon.getCouponId();
                    couponPop.adapter.notifyDataSetChanged();
                }

                couponPop.show(v);
            }
        });
//        rlVoucher.setOnClickListener(new OnSPClickListener() {
//            @Override
//            public void onClickSucc(View v) {
//                if (vouchePop == null) {
//                    vouchePop = new VoucherPopwin(mContext, ConstantData.VOUCHER_TYPE, placeOrderResponse.getStoreGoodsList(), placeOrderResponse.getSumPrice());
//                    vouchePop.setOnPopWinClickListener(id -> {
////                        placeOrderResquest.setVoucherId(id);
//                        getPageInfoForSubmitOrder();
//                    });
//                }
//                vouchePop.show(v);
//            }
//        });

        //提交订单
        tvSubmitOrder.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (TextUtils.isEmpty(addressId)) {      //提示还未选择地址
                    ToastDialog.show(mContext, "提示", "请选择收货地址", () -> {
                        isFlush = false;
                        Intent intent = new Intent(context, MyAddressListActivity.class);
                        intent.putExtra("order_address_id", addressId);
                        startActivityForResult(intent, 1);
                    });
                    return;
                }
                submitOrder();
            }
        });

        rl_order_fill_baozhuagnfei.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                YuGuoApplication application = (YuGuoApplication) getApplication();
                application.object = allGoodsList;
                startActivity(new Intent(mContext, MyGoodsPeiSongFeiListActivity.class));
            }
        });
    }

//    String makeIds() {
//        StringBuilder sb = new StringBuilder();
//
//        List<GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.StoreGoodsListBean> storeGoodsListBeans = placeOrderResponse.getStoreGoodsList();
//        for (GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.StoreGoodsListBean vo : storeGoodsListBeans) {
//            sb.append(",");
//            sb.append(vo.getGoodsId());
//        }
//        if (sb.length() <= 0) return null;
//        return sb.substring(1).toString();
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String id = data.getStringExtra("addressId");
            if (TextUtils.isEmpty(id)) {
                tvChooseAddress.setVisibility(View.VISIBLE);
                addressView.setVisibility(View.GONE);
                return;
            }

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

            placeOrderResquest.setReceiverAddress(address);

            placeOrderResquest.setReceiverLat(receiverLat);
            placeOrderResquest.setReceiverLng(receiverLng);
            getPageInfoForSubmitOrder();

        }
    }


    private void getPageInfoForSubmitOrder(Boolean... bl) {
        if (null == placeOrderResquest) {
            return;
        }
        cbVipChoose.setEnabled(false);
        showLoadingDialog();
        HttpAction.getInstance().getPageInfoForSubmitOrder(placeOrderResquest)
                .subscribe(new BaseObserver<>(this, response -> {
                    hideLoadingDialog();
                    cbVipChoose.setEnabled(true);
                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        return;
                    }
                    this.placeOrderResponse = response.getData().getResultMap();

                    for (GoodsInfoBean vo : this.placeOrderResponse.getGoodsInfoSonList()) {
                        vo.gifts = true;
                    }
                    showPlaceOrder(bl);
                }));
    }

    private void showPlaceOrder(Boolean... bl) {
        List<GoodsInfoBean> storeGoodsListBeans = placeOrderResponse.getGoodsInfoList();
        if (bl == null || bl.length <= 0) {
            for (GoodsInfoBean vo : storeGoodsListBeans) {
                if (vo.getAvaliableFlag() == 1) continue;
                ToastDialog.show(mContext, "", "您的部分商品因库存原因，商品已失效，请确认您的订单", null);
                break;
            }
        }
        boolean b = false;
        for (GoodsInfoBean vo : storeGoodsListBeans) {
            if (vo.getAvaliableFlag() == 1) {//有效
                b = true;
            }
        }
        tvSubmitOrder.setEnabled(b);
        tvSubmitOrder.setBackgroundResource(b ? R.color.main_color : R.color.dark_gray);

        if (placeOrderResquest.getOrderFillType() == 1) {
            this.allGoodsList.clear();
            this.allGoodsList.addAll(storeGoodsListBeans);
            GoodsInfoBean goodsListBean = storeGoodsListBeans.get(0);

//            GlideUtil.displayCircleBitmap(this, goodsListBean.getGoodsPicUrl(), ivGoodsInfoImage);
            Glide.with(mContext).load(goodsListBean.getGoodsInfoPicUrl()).into(ivGoodsInfoImage);
            tvGoodsName.setText(goodsListBean.getGoodsInfoName());
            tv_goods_intro.setText(goodsListBean.getGoodsInfoNameSub());
            tvGoodsPrice.setText(MoneyHelper.getInstance4Fen(goodsListBean.getGoodsInfoMemberPrice()).change2Yuan().getString());

            tvGoodsNum.setText(String.valueOf(goodsListBean.getGoodsInfoNum()));

            iv_order_fill_goods_image_state.setVisibility(goodsListBean.getAvaliableFlag() == 1 ? View.GONE : View.VISIBLE);
            tv_adapter_goods_list_state.setVisibility(goodsListBean.getAvaliableFlag() == 1 ? View.GONE : View.VISIBLE);
        } else {
            this.allGoodsList.clear();


            this.allGoodsList.addAll(storeGoodsListBeans);
            this.allGoodsList.addAll(placeOrderResponse.getGoodsInfoSonList());

            int num = 0;
            for (GoodsInfoBean vo : this.allGoodsList) {
                num += vo.getGoodsInfoNum();
            }

            tvShoppingCarCount.setText("共");
            tvShoppingCarCount.append(String.valueOf(num));
            tvShoppingCarCount.append("件");

            setGoodsGridViewWidth(this.allGoodsList.size());
            goodsAdapter.notifyDataSetChanged();
        }


        tvTotalPrice.setText("￥");
        tvTotalPrice.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getSumPrice()).change2Yuan().getString());

        tvDistributionFee.setText("￥");
        tvDistributionFee.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getFreight()).change2Yuan().getString());

        MoneyHelper moneyHelper = MoneyHelper.getInstance4Fen(placeOrderResponse.getBasedFreight()).change2Yuan();
        String money = moneyHelper.getString();
        if (moneyHelper.getDouble() == 0) {
            tv_distribution_manjian.setVisibility(View.GONE);
            tv_distribution_fee_sub.setVisibility(View.GONE);
        } else {
            tv_distribution_manjian.setVisibility(View.VISIBLE);
            tv_distribution_fee_sub.setVisibility(View.VISIBLE);

            MoneyHelper mjMoneyHelper = MoneyHelper.getInstance4Fen(placeOrderResponse.getRealPayFullXPrice()).change2Yuan();
            if (mjMoneyHelper.getDouble() == 0) {
                tv_distribution_manjian.setText("免邮");
            } else {
                tv_distribution_manjian.setText("满");
                tv_distribution_manjian.append(mjMoneyHelper.getStringDelZero());
                tv_distribution_manjian.append("元减运费");
            }

            tv_distribution_fee_sub.setText("-￥");
            tv_distribution_fee_sub.append(money);
        }

        tv_order_fill_baozhuagnfei.setText("￥");
        tv_order_fill_baozhuagnfei.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getPackingChargesTotal()).change2Yuan().getString());


        if (placeOrderResponse.getDeliveryType() == 1) {
//            rl_order_fill_sendtime.setClickable(true);
        } else {
//            rl_order_fill_sendtime.setClickable(false);
            timeChooseDataPos = 1;
            timeOptionsPickerView.setSelectOptions(timeChooseDataPos);
            tv_order_fill_sendtime.setText(timeOptionsPickerViewList.get(timeChooseDataPos));

//            iv_distribution_label.setVisibility(View.GONE);
        }

        GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.CouponBean coupon = placeOrderResponse.getCoupon();
        if (null != coupon) {
//            placeOrderResquest.setCouponId(coupon.getCouponId());
            tvCoupon.setText(getCouponDesc(coupon));
        } else {
            if (placeOrderResponse.getCouponCount() > 0) {
                tvCoupon.setText(String.valueOf(placeOrderResponse.getCouponCount()));
                tvCoupon.append("张可用");
            } else {
                tvCoupon.setText("无可用优惠券");
            }
        }
//        0普通，1试用，2正式
        if (placeOrderResponse.getMemberFlag() == 2) {//是会员
//            rlVoucher.setVisibility(View.GONE);
            ll_order_fill_open_vip.setVisibility(View.GONE);

//            GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.CouponBean voucher = placeOrderResponse.getVoucher();
//            if (null != voucher) {
////                placeOrderResquest.setVoucherId(voucher.getCouponId());
//                tvVoucher.setText(getCouponDesc(voucher));
//            } else {
//                tvVoucher.setText("选择抵用券");
//            }

        } else {//不是会员 或体验会员
//            rlVoucher.setVisibility(View.GONE);
            ll_order_fill_open_vip.setVisibility(View.GONE);

//            tvVipCoupon.setText("开通会员本单返");
//            tvVipCoupon.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getReturnTotlePrice()).change2Yuan().getString());
//            tvVipCoupon.append("元");
//
//            tvVipPrice.setText("会员价格￥");
//            tvVipPrice.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getMenberPriceReality()).change2Yuan().getString());
//            tvVipPrice.append("/");
////            tvVipPrice.append(placeOrderResponse.getOpenMonth() + "");
//            tvVipPrice.append("月");

            cbVipChoose.setText("￥");
            cbVipChoose.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getMemberOpenDiscountPrice()).change2Yuan().getString());
            cbVipChoose.append("/");
//            tvVipPrice.append(placeOrderResponse.getOpenMonth() + "");
            switch (placeOrderResponse.getTimeUnit()) {//时间单位(1年，2月，3季，4周，5日)
                case 1:
                    cbVipChoose.append("年");
                    break;
                case 2:
                    cbVipChoose.append("月");
                    break;
                case 3:
                    cbVipChoose.append("季");
                    break;
                case 4:
                    cbVipChoose.append("周");
                    break;
                case 5:
                    cbVipChoose.append("日");
                    break;
            }

        }

        tvFavorablePrice.setText("￥");
        tvFavorablePrice.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getDiscountPrice()).change2Yuan().getString());
        tvFavorablePrice.append("元");

        tvRealPay.setText("￥");
        tvRealPay.append(MoneyHelper.getInstance4Fen(placeOrderResponse.getActualPrice()).change2Yuan().getString());
        tvRealPay.append("元");

    }

    private void setGoodsGridViewWidth(int size) {
        int horizontalSpacing = 20;
        int gridViewWidth = ((getScreenWidth() / 4) + horizontalSpacing) * size;
        mGoodsGridView.getLayoutParams().width = gridViewWidth;
        mGoodsGridView.setHorizontalSpacing(horizontalSpacing);
        mGoodsGridView.setNumColumns(size);
    }

    private String getCouponDesc(GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.CouponBean couponBean) {

        String xPrice = String.valueOf(MoneyHelper.getInstance4Fen(couponBean.getCouponXPrice()).change2Yuan().getString());
        String price = String.valueOf(MoneyHelper.getInstance4Fen(couponBean.getCouponPrice()).change2Yuan().getString());

        StringBuilder stringBuilder = new StringBuilder();
        switch (couponBean.getCouponRuleType()) {
            case 1:
                stringBuilder.append("满").append(xPrice).append("元减").append(price);
                break;
            case 2:
                stringBuilder.append("直降").append(price);
                break;
            case 3:
                stringBuilder.append("无门槛减").append(price);
                break;
            case 4:
                stringBuilder.append("抵用券").append(price);
                break;
        }
//        stringBuilder.append("元");
        return stringBuilder.toString();
    }

    private void submitOrder() {
        SubmitOrderResquest resquest = new SubmitOrderResquest();

        resquest.orderFillType = placeOrderResquest.getOrderFillType();
        GoodsInfoBean tVo = placeOrderResponse.getGoodsInfoList().get(0);
        if (resquest.orderFillType == 1) {

            resquest.storeGoodsInfoId = tVo.getStoreGoodsInfoId();
            resquest.goodsInfoNum = tVo.getGoodsInfoNum();
        } else {
            resquest.shoppingCartIds = placeOrderResquest.getShoppingCartIds();
        }
        resquest.storeId = tVo.getStoreId();

        resquest.receiverLng = placeOrderResquest.getReceiverLng();                         //		地址经度
        resquest.receiverLat = placeOrderResquest.getReceiverLat();                         //		地址纬度

        GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.CouponBean couponVo = placeOrderResponse.getCoupon();
        if (couponVo != null) {
            resquest.couponId = couponVo.getCouponId();
        } else {
            resquest.couponId = -1;
        }
//        GetPageInfoForSubmitOrderResponse.DataBean.ResultMapBean.CouponBean voucherVo = placeOrderResponse.getVoucher();
//        if (voucherVo != null) {
//            resquest.voucherId = voucherVo.getCouponId();
//        } else {
//            resquest.voucherId = 0;
//        }
        if (placeOrderResponse.getMemberFlag() == 2) {//是会员
            resquest.memberPrice = 0;
        } else {
            resquest.memberPrice = placeOrderResquest.getMemberPrice();
        }

        resquest.receiverAddressId = Integer.parseInt(addressId);

        resquest.deliveryTypeFlag = timeChooseDataPos == 0 ? 1 : 0;
        resquest.orderDealPrice = placeOrderResponse.getActualPrice();

        resquest.fullPresentGoodsCount = placeOrderResponse.getFullPresentGoodsCount();
        resquest.fullXGoodsCount = placeOrderResponse.getFullXGoodsCount();
        resquest.pinkageActivityId = placeOrderResponse.getPinkageActivityId();

        showLoadingDialog();
        HttpAction.getInstance().submitOrder(resquest).subscribe(new BaseObserver<>(this, response -> {
            hideLoadingDialog();
//            4010
            if (4033 == response.getCode() || 4028 == response.getCode()) {
                getPageInfoForSubmitOrder(true);
                ToastDialog.show(mContext, "", response.getMsg(), null);
                return;
            }

            if (4030 == response.getCode() || 4025 == response.getCode()) {

                tvSubmitOrder.setEnabled(false);
                tvSubmitOrder.setBackgroundResource(R.color.dark_gray);

                ToastDialog.show(mContext, "", response.getMsg(), null);
                return;
            }


            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }

            Intent intent = new Intent(OrderFillActivity.this, PayCoreActivity.class);
            intent.putExtra(PayCoreActivity.PAY_TYPE, "1");
            intent.putExtra(PayCoreActivity.ORDER_ID, String.valueOf(response.getData().getOrderId()));
            intent.putExtra(PayCoreActivity.ORDER_MONEY, tvRealPay.getText().toString().trim());
            startActivity(intent);
            finish();
        }));
    }

    private void initTimeOptionsPickerView() {// 弹出选择器

        Resources resource = context.getResources();

        timeOptionsPickerView = new OptionsPickerView.Builder(context, (options1, options2, options3, v) -> {

            if (timeChooseDataPos == options1) return;

            timeChooseDataPos = options1;
            tv_order_fill_sendtime.setText(timeOptionsPickerViewList.get(timeChooseDataPos));

            placeOrderResquest.setDeliveryTypeFlag(String.valueOf(timeChooseDataPos == 0 ? 1 : 0));

//            iv_distribution_label.setVisibility(timeChooseDataPos == 0 ? View.VISIBLE : View.GONE);

            getPageInfoForSubmitOrder();
        })
                .setTitleText("配送时间")//标题
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字

                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(0xFFDBDBDB)//滚轮背景颜色 Night mode

                .setTitleColor(resource.getColor(R.color.black))//标题文字颜色
                .setSubmitColor(resource.getColor(R.color.main_color))//确定按钮文字颜色
                .setCancelColor(resource.getColor(R.color.main_color))//取消按钮文字颜色
                .setDividerColor(resource.getColor(R.color.black))//分割线

                .setTitleSize(14)//标题文字大小
                .setSubCalSize(14)//确定和取消文字大小
                .setContentTextSize(18)//滚轮文字大小

                .setLineSpacingMultiplier(2.0F)

//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();

//        expressOptionsPickerView.setOnDismissListener(o -> tv_index_order_type.setChecked(false));

        loadTimes();
    }

    private void loadTimes() {
        timeOptionsPickerViewList.add("立即配送");
        timeOptionsPickerViewList.add("明日配送");

        timeOptionsPickerView.setPicker(timeOptionsPickerViewList);
        timeChooseDataPos = 0;
        timeOptionsPickerView.setSelectOptions(timeChooseDataPos);
        tv_order_fill_sendtime.setText(timeOptionsPickerViewList.get(timeChooseDataPos));


//        iv_distribution_label.setVisibility(View.VISIBLE);
    }

}
