package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.GoodsEvaluateListAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.MySharePopRedBagWindow;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.dialog.UsableCouponPopwin;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.FlowLayout;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetGoodsDetailResquest;
import com.pbph.yuguo.request.GetPageInfoForSubmitOrderResquest;
import com.pbph.yuguo.request.GetShoppingCartCountResquest;
import com.pbph.yuguo.response.GetGoodsDetailResponse;
import com.pbph.yuguo.util.DensityUtils;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.wxutil.WechatUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {

    LayoutInflater inflater;

    private Banner mBanner;
    private ImageView iv_goodsinfo_bottom;
    private TextView tv_banner_count;


    private TextView mTvGoodsInfoTitle, mTvGoodsInfoSubTitle;

    private TextView mTvGoodsInfoPrice, mTvGoodsInfoSpec, mTvGoodsInfoDiscountPrice;
    private TextView tv_goods_info_score;
    private FlowLayout mTvGoodsInfoExpress; //快递
//    android:drawableLeft="@drawable/complete"
//    android:drawablePadding="@dimen/dp_3dp"


    View include_event_layout, include_event_layout_line;


    private TextView mTvVipCouponPrice;//会员抵用券 金额

    View include_yunfei;
    View view_freight_top_line; // 运费顶部线条
    TextView tv_event_label;
    TextView tv_yunfei_name;
    TextView tv_event_name;//活动名称
    TextView tv_coupon_name;//优惠券名称

    private TextView mTvStandardContent;//重量

    private TextView mTvGoodsEvaluteCount;//评价数量
    private TextView mTvGoodsEvaluteAll;//查看全部

    private ListView evaluateListView;//评价内容
    private GoodsEvaluateListAdapter evaluateListAdapter;
    List<GetGoodsDetailResponse.DataBean.EvaluateListBean> evaluateListBeans = new ArrayList<>();

    private TextView mTvGoodsInfoDesc;//商品详情

    private WebView webView;


    //购物车 及数量
    private TextView mTvGoodsInfoShoppingTrolley, mTvGoodsInfoShoppingTrolleyCount;

    private Button mTvGoodsInfoPutShoppingTrolley, mTvGoodsInfoBuyNow;


    //        private MyStandardPopWindow myStandardPopWindow;//商品数量选择
    private SpecChoicePop specChoicePop;
    UsableCouponPopwin popWin;
    private MySharePopRedBagWindow sharePopWindow;//分享

    private String goodimage = "";


    private Integer storeGoodsId, storeGoodsInfoId;
    private GetGoodsDetailResponse.DataBean dataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        storeGoodsId = intent.getIntExtra("storeGoodsId", -1);
        storeGoodsInfoId = intent.getIntExtra("storeGoodsInfoId", -1);


        inflater = LayoutInflater.from(this);

        setContentView(R.layout.activity_goods_info);
        initTitle(TITLE_STYLE_WHITE, "商品详情", true, false);

        initView();
        specChoicePop = new SpecChoicePop(mContext, (specChoicePop) -> {
            switch (specChoicePop.type) {
                case BUY: {
                    goBuy(specChoicePop.goodsNum);
                }
                break;
                case ADDSHOP: {
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), storeGoodsId, dataBean.getGoodsDetail().getStoreId(), specChoicePop.goodsNum, storeGoodsInfoId, 0, -1);
                }
                break;
                case CLOSE: {
                }
                break;
                case CHOICE: {
                    storeGoodsId = specChoicePop.storeGoodsId;
                    storeGoodsInfoId = specChoicePop.storeGoodsInfoId;
                    getGoodsDetail();
                }
                break;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        getGoodsStorageNum();
        getGoodsDetail();
    }

    private void initView() {

        mBanner = findViewById(R.id.goods_info_banner);
        int w = getScreenWidth();
        int h = (int) (w / 1.44f);
        mBanner.getLayoutParams().height = h;


        webView = findViewById(R.id.nvwv_goods_info_details);
        imgList = new ArrayList<>();
        evaluateListView = findViewById(R.id.mlv_goods_info_evaluate_list);
        evaluateListAdapter = new GoodsEvaluateListAdapter(this, evaluateListBeans);
        evaluateListView.setAdapter(evaluateListAdapter);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImages(imgList);
        mBanner.start();
        mBanner.setOnBannerListener((position) -> {

        });
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_banner_count.setText(String.valueOf(position));
                tv_banner_count.append("/");

                try {
                    tv_banner_count.append(String.valueOf(imgList.size()));
                } catch (Exception e) {
                    e.printStackTrace();
                    tv_banner_count.append("0");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        iv_goodsinfo_bottom = findViewById(R.id.iv_goodsinfo_bottom);
        iv_goodsinfo_bottom.setVisibility(View.INVISIBLE);

        tv_banner_count = findViewById(R.id.tv_banner_count);


        include_event_layout = findViewById(R.id.include_event_layout);
        include_event_layout.setOnClickListener(this);
        include_event_layout_line = findViewById(R.id.include_event_layout_line);

        tv_event_name = findViewById(R.id.tv_event_name);
        tv_coupon_name = findViewById(R.id.tv_coupon_name);


        mTvGoodsInfoTitle = (TextView) findViewById(R.id.tv_goods_info_title);
        mTvGoodsInfoSubTitle = (TextView) findViewById(R.id.tv_goods_info_sub_title);
        mTvGoodsInfoDesc = (TextView) findViewById(R.id.tv_goods_info_desc);
        mTvGoodsInfoSpec = (TextView) findViewById(R.id.tv_goods_info_spec);
        mTvGoodsInfoPrice = (TextView) findViewById(R.id.tv_goods_info_price);

        mTvGoodsInfoDiscountPrice = (TextView) findViewById(R.id.tv_goods_info_discount_price);
//        mTvGoodsInfoDiscountPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mTvGoodsInfoDiscountPrice.setVisibility(View.INVISIBLE);

        tv_goods_info_score = findViewById(R.id.tv_goods_info_score);

        mTvGoodsInfoExpress = findViewById(R.id.tv_goods_info_express);
        mTvGoodsInfoExpress.setVisibility(View.GONE);
        mTvVipCouponPrice = (TextView) findViewById(R.id.tv_vip_coupon_price);
        mTvStandardContent = (TextView) findViewById(R.id.tv_standard_content);
        mTvGoodsEvaluteCount = (TextView) findViewById(R.id.tv_evaluate_count);
        mTvGoodsEvaluteAll = (TextView) findViewById(R.id.tv_evaluate_all);
        mTvGoodsEvaluteAll.setOnClickListener(this);

        mTvGoodsInfoShoppingTrolley = (TextView) findViewById(R.id.tv_goods_info_shopping_trolley);
        mTvGoodsInfoShoppingTrolley.setOnClickListener(this);
        mTvGoodsInfoShoppingTrolleyCount = (TextView) findViewById(R.id.tv_goods_info_shopping_trolley_count);

        mTvGoodsInfoPutShoppingTrolley = (Button) findViewById(R.id.tv_goods_info_put_shopping_trolley);
        mTvGoodsInfoPutShoppingTrolley.setOnClickListener(this);
        mTvGoodsInfoBuyNow = (Button) findViewById(R.id.tv_goods_info_buy_now);
        mTvGoodsInfoBuyNow.setOnClickListener(this);

        tv_yunfei_name = findViewById(R.id.tv_yunfei_name);
        tv_event_label = findViewById(R.id.tv_event_label);

        view_freight_top_line = findViewById(R.id.view_freight_top_line);
        include_yunfei = findViewById(R.id.include_yunfei);
        include_yunfei.setOnClickListener(this);
        findViewById(R.id.include_coupon_layout).setOnClickListener(this);
        findViewById(R.id.include_share_coupon_layout).setOnClickListener(this);
        findViewById(R.id.include_standard_layout).setOnClickListener(this);


        findViewById(R.id.iv_share).setOnClickListener(this);


        findViewById(R.id.include_vip_coupon_layout).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext, OpenVipActivity.class));
            }
        });

        initWebView();
    }

    private void getGoodsDetail() {
        showLoadingDialog();
        HttpAction.getInstance().getGoodsDetail(new GetGoodsDetailResquest(YuGuoApplication.userInfo.getCustomerId(), storeGoodsId, storeGoodsInfoId))
                .subscribe(new BaseObserver<>(this, (response -> {
                    hideLoadingDialog();
                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        finish();
                        return;
                    }
                    this.dataBean = response.getData();

//                    myStandardPopWindow.setGoodsDetailBean(dataBean.getGoodsDetail());

                    setBannerImage();

                    showGoodsInfo();
                })));
    }

    private void showGoodsInfo() {

        GetGoodsDetailResponse.DataBean.GoodsDetailBean goodsDetail = dataBean.getGoodsDetail();
        mTvGoodsInfoTitle.setText(goodsDetail.getGoodsName());
        mTvGoodsInfoSubTitle.setText(goodsDetail.getGoodsNameSub());

        mTvGoodsInfoPrice.setText(MoneyHelper.getInstance4Fen(goodsDetail.getMemberPrice()).change2Yuan().getString());

//        mTvGoodsInfoSpec.setText(" /");
//        mTvGoodsInfoSpec.append(goodsDetail.getGoodsSpec());

//        if (goodsDetail.getHotFlag() == 1) {
        mTvGoodsInfoDiscountPrice.setVisibility(View.VISIBLE);
//        } else {
//            mTvGoodsInfoDiscountPrice.setVisibility(View.INVISIBLE);
//        }

        mTvGoodsInfoDiscountPrice.setText("￥");
        mTvGoodsInfoDiscountPrice.append(MoneyHelper.getInstance4Fen(goodsDetail.getGoodsSalePrice()).change2Yuan().getString());


        tv_goods_info_score.setText(String.valueOf(goodsDetail.getGetRulePointScore()));

//       快递
        List<String> temps = goodsDetail.getSupportNameList();
        if (temps == null || temps.isEmpty()) {
            mTvGoodsInfoExpress.setVisibility(View.GONE);
        } else {
            mTvGoodsInfoExpress.setVisibility(View.VISIBLE);
            setDatas4FlowLayout(temps);
        }

        GetGoodsDetailResponse.DataBean.StoreGoodsInfoActivityBean avo = dataBean.getStoreGoodsInfoActivity();
        if (avo != null) {
            include_event_layout.setVisibility(View.VISIBLE);
            include_event_layout_line.setVisibility(View.VISIBLE);

            iv_goodsinfo_bottom.setVisibility(View.INVISIBLE);
            if (avo.getActivityType() == 0) {
                //满赠
                tv_event_label.setText("满赠");
                tv_event_name.setText(avo.getActivityName());
            } else if (avo.getActivityType() == 1) {
                //超值
                tv_event_label.setText("超值");
                tv_event_name.setText(avo.getActivityName());
            } else {
                tv_event_label.setText(avo.getActivityName());

                tv_event_name.setText("尊享");
                tv_event_name.append(avo.getDiscount());
                tv_event_name.append("折优惠");

                iv_goodsinfo_bottom.setVisibility(View.VISIBLE);
            }
        } else {
            include_event_layout.setVisibility(View.GONE);
            include_event_layout_line.setVisibility(View.GONE);
        }


////会员抵用券金额
//        mTvVipCouponPrice.setText(MoneyHelper.getInstance4Fen(dataBean.getMemberLevel().getOpenPrice()).change2Yuan().getString());
        mTvVipCouponPrice.setText("");


        GetGoodsDetailResponse.DataBean.CouponActivityBean couponVo = dataBean.getCouponActivity();
////                优惠券名称
        tv_coupon_name.setText(couponVo.getCouponName());

        String xPrice = String.valueOf(MoneyHelper.getInstance4Fen(couponVo.getCouponXPrice()).change2Yuan().getInt());
        String price = String.valueOf(MoneyHelper.getInstance4Fen(couponVo.getCouponPrice()).change2Yuan().getInt());
        switch (couponVo.getCouponRuleType()) {
            case 1:  //满减
                tv_coupon_name.setText("实付满");
                tv_coupon_name.append(xPrice);
                tv_coupon_name.append("减");
                tv_coupon_name.append(price);
                tv_coupon_name.append("元");
                break;
            case 2:          //直降
                tv_coupon_name.setText("直降");
                tv_coupon_name.append(price);
                tv_coupon_name.append("元");
                break;
            case 3:          //无门槛
                tv_coupon_name.setText("无门槛");
                tv_coupon_name.append(price);
                tv_coupon_name.append("元");
                break;
        }


        mTvStandardContent.setText(goodsDetail.getSpecAndSpecDetailName());


        int num = dataBean.getEvaluateTotalNumber();
        if (num <= 0) {
            evaluateListView.setVisibility(View.GONE);
            mTvGoodsEvaluteCount.setText("商品评价(0)");
        } else {
            evaluateListView.setVisibility(View.VISIBLE);
            mTvGoodsEvaluteCount.setText("商品评价(");
            mTvGoodsEvaluteCount.append(String.valueOf(num));
            mTvGoodsEvaluteCount.append(")");
        }

        this.evaluateListBeans.clear();
        List<GetGoodsDetailResponse.DataBean.EvaluateListBean> list = dataBean.getEvaluateList();
        if (null != list && !list.isEmpty()) {
            this.evaluateListBeans.addAll(list);
            evaluateListAdapter.notifyDataSetChanged();
        }
        mTvGoodsInfoDesc.setText(getGoodsInfoSpec());

        if (!TextUtils.isEmpty(goodimage)) {
            webView.loadData(getHtml(goodimage), "text/html", "utf-8");
        }

//       pinkageFlag;//是否包邮（1是，0否）
//       pinkageActivityId;//包邮（减基础运费）
//       realPayFullXPrice;//包邮（实付满）
        if (goodsDetail.getPinkageFlag() == 0) {
            include_yunfei.setVisibility(View.GONE);
            view_freight_top_line.setVisibility(View.GONE);
        } else {
            include_yunfei.setVisibility(View.VISIBLE);
            view_freight_top_line.setVisibility(View.VISIBLE);
            tv_yunfei_name.setText("满");
            tv_yunfei_name.append(MoneyHelper.getInstance4Fen(goodsDetail.getRealPayFullXPrice()).change2Yuan().getStringDelZero());
            tv_yunfei_name.append("元减基础运费");
            tv_yunfei_name.append(MoneyHelper.getInstance4Fen(goodsDetail.getBasedFreight()).change2Yuan().getStringDelZero());
            tv_yunfei_name.append("元");

        }

    }

    ArrayList<String> imgList;

    private void setBannerImage() {
        imgList = new ArrayList<>();
        List<GetGoodsDetailResponse.DataBean.GoodsDetailBean.GoodsSlideListBean> imgListBeans = dataBean.getGoodsDetail().getGoodsSlideList();
        if (null == imgListBeans || imgListBeans.isEmpty()) {
            return;
        }
        for (int i = 0; i < imgListBeans.size(); i++) {
            GetGoodsDetailResponse.DataBean.GoodsDetailBean.GoodsSlideListBean bean = imgListBeans.get(i);
            if (0 == bean.getGoodsType()) {
                imgList.add(bean.getGoodsImgUrl());
            } else if (1 == bean.getGoodsType()) {
                goodimage = bean.getGoodsImgUrl();
            }
        }
        mBanner.update(imgList);
    }

    private String getGoodsInfoSpec() {
        StringBuilder builder = new StringBuilder();
        List<GetGoodsDetailResponse.DataBean.ParamListBean> detailsListBeans = dataBean.getParamList();
        if (detailsListBeans != null) {
            for (int i = 0, size = detailsListBeans.size(); i < size; i++) {
                GetGoodsDetailResponse.DataBean.ParamListBean bean = detailsListBeans.get(i);
                builder.append(bean.getInfo());
                if (i != (size - 1)) {
                    builder.append("\n");
                } else {
//                builder.append("g");
                }
            }
        }
        return builder.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvGoodsInfoShoppingTrolleyCount.setVisibility(View.GONE);
        getShoppingCartCount();
    }


    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:getsize()");
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void getNaturalSize(int imgWidth, int imgHeight) {
//                Log.e("image size", "width: " + imgWidth + " height: " + imgHeight);
            }
        }, "stub");
    }

    private String getHtml(String url) {
        int screenWidthDp = DensityUtils.px2dip(this, getScreenWidth());
        String html = "<html>" +
                "<body>" +
                "   <style type=\"text/css\">  " +
                "  body{  " +
                "   margin:0px;  " +
                "  }   " +
                " </style>  " +
                "<img id=\"img\" src=\"" + url + "\" width=\"" + screenWidthDp + "\"/>" +
                "<script>" +
                " function getsize(){" +
                "  var img = document.getElementById(\"img\");" +
                "  javascript:stub.getNaturalSize(img.naturalWidth,img.naturalHeight);" +
                " }" +
                "</script>" +
                "</body>" +
                "</html>";
//        Log.e("html", html);
        return html;
    }


    @Override
    public void onLeftClick() {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_goods_info_shopping_trolley://去購物車
                startActivity(new Intent(this, ShoppingCarActivity.class));
                break;
            case R.id.tv_goods_info_put_shopping_trolley://加入购物车
                if (dataBean == null) return;
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
//                myStandardPopWindow.showDialog(v, 1);
                specChoicePop.showPop(storeGoodsInfoId, storeGoodsId, "", 0, v);
                break;
            case R.id.tv_goods_info_buy_now://立即购买
                if (dataBean == null) return;
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
//                myStandardPopWindow.showDialog(v, 2);
                specChoicePop.showPop(storeGoodsInfoId, storeGoodsId, "", 1, v);
                break;
            case R.id.include_coupon_layout://选择优惠券

                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }

                if (popWin == null)
                    popWin = new UsableCouponPopwin(mContext, dataBean.getGoodsDetail().getGoodsId());
                popWin.show(v);
                break;
            case R.id.tv_evaluate_all://查看全部评价

                if (dataBean.getEvaluateTotalNumber() <= 0) {
                    return;
                }
                startActivity(new Intent(mContext, EvaluateListActivity.class)
                        .putExtra("storeGoodsId", storeGoodsId)
                );
                break;
            case R.id.iv_share:
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
                if (sharePopWindow == null)
                    sharePopWindow = new MySharePopRedBagWindow(mContext, onShareClickListener);
                sharePopWindow.showDialog(v);
                break;
            case R.id.include_standard_layout://选择规格
//                myStandardPopWindow.showDialog(v, 0);
                specChoicePop.showPop(storeGoodsInfoId, storeGoodsId, "", 2, v);
                break;
            case R.id.include_share_coupon_layout:
                break;

            case R.id.include_yunfei:
                startActivity(new Intent(mContext, SalesPromotionActivity.class)
                        .putExtra("pinkageActivityId", dataBean.getGoodsDetail().getPinkageActivityId())
                        .putExtra("storeId", dataBean.getGoodsDetail().getStoreId())
                );
                break;
            case R.id.include_event_layout:
                GetGoodsDetailResponse.DataBean.StoreGoodsInfoActivityBean act = dataBean.getStoreGoodsInfoActivity();
                if (act.getActivityType() == 0) {//活动类型(0:满赠; 1:满x元y件)
                    startActivity(new Intent(mContext, EventCoudanActvity.class)
                            .putExtra("acticeId", act.getActivityId())
                            .putExtra("storeId", dataBean.getGoodsDetail().getStoreId())
                    );
                } else if (act.getActivityType() == 1) {
                    startActivity(new Intent(mContext, EventManjianActvity.class)
                            .putExtra("acticeId", act.getActivityId())
                            .putExtra("storeId", dataBean.getGoodsDetail().getStoreId())
                    );
                } else {
                    startActivity(new Intent(mContext, VipDayActivity.class)
                            .putExtra("storeId", String.valueOf(dataBean.getGoodsDetail().getStoreId()))
                            .putExtra("memberDayActivityId", dataBean.getStoreGoodsInfoActivity().getActivityId())
                    );
                }


//                if (act.getActivityType() == 0) {//活动类型(0:满赠; 1:满x元y件)
//
//
//                    if (act.getIsActivityOk() == 0) {
//                        startActivity(new Intent(getContext(), EventCoudanActvity.class)
//                                .putExtra("acticeId", act.getActiveId())
//                                .putExtra("storeId", act.getStoreGoodsInfoCartList().get(0).getStoreId())
//                        );
//                    } else {
//                        startActivity(new Intent(getContext(), EventCoudanGiftActvity.class)
//                                .putExtra("acticeId", act.getActiveId())
//                                .putExtra("storeId", act.getStoreGoodsInfoCartList().get(0).getStoreId())
//                        );
//                    }
//                } else {
//
//                    startActivity(new Intent(getContext(), EventManjianActvity.class)
//                            .putExtra("acticeId", act.getActiveId())
//                            .putExtra("storeId", act.getStoreGoodsInfoCartList().get(0).getStoreId())
//                    );
//                }
                break;
        }
    }

    MySharePopRedBagWindow.ShareOnClickListener onShareClickListener = type -> {
        switch (type) {
            case SHARELIFEWX: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPage(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "哈市最受欢迎鲜果售卖终端");
            }
            break;
            case SHARETIMELINE: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPageTimeLine(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
            }
            break;
        }
    };

    private void getShoppingCartCount() {
        if (!YuGuoApplication.isLogin()) {
            mTvGoodsInfoShoppingTrolleyCount.setVisibility(View.GONE);
            return;
        }
        HttpAction.getInstance().getShoppingCartCount(new GetShoppingCartCountResquest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                mTvGoodsInfoShoppingTrolleyCount.setVisibility(View.GONE);
                return;
            }
            int num = response.getData().getShoppingCartCount();
            if (num <= 0) {
                mTvGoodsInfoShoppingTrolleyCount.setVisibility(View.GONE);
            } else {
                mTvGoodsInfoShoppingTrolleyCount.setVisibility(View.VISIBLE);
                mTvGoodsInfoShoppingTrolleyCount.setText(String.valueOf(num));
            }

        })));
    }


    void goBuy(int count) {
        if (specChoicePop.goodsNum <= 0) {
            showToast("暂无库存");
            return;
        }

        GetPageInfoForSubmitOrderResquest placeOrderResquest = new GetPageInfoForSubmitOrderResquest();
        placeOrderResquest.setOrderFillType(1);
        placeOrderResquest.setStoreGoodsInfoId(storeGoodsInfoId);
        placeOrderResquest.setGoodsInfoNum(count);

        if (YuGuoApplication.userInfo.getRecAddId() == null) {
            placeOrderResquest.setReceiverLat(String.valueOf(YuGuoApplication.userInfo.getLatitude()));
            placeOrderResquest.setReceiverLng(String.valueOf(YuGuoApplication.userInfo.getLongitude()));
        } else {
            placeOrderResquest.setReceiverLat(String.valueOf(YuGuoApplication.userInfo.getRecLatitude()));
            placeOrderResquest.setReceiverLng(String.valueOf(YuGuoApplication.userInfo.getRecLongitude()));
        }
        YuGuoApplication application = (YuGuoApplication) getApplication();
        application.object = placeOrderResquest;
        startActivity(new Intent(this, OrderFillActivity.class));
    }


    private void setDatas4FlowLayout(List<String> list) {
        mTvGoodsInfoExpress.removeAllViews();
//        for (int i = 0; i < flowLayout.getChildCount(); i++) {
//            CheckBox cb = (CheckBox) flowLayout.getChildAt(i);
//            cb.setChecked(false);
//            cb.setVisibility(View.GONE);
//        }
        if (list == null || list.size() <= 0) return;

        for (int i = 0, count = list.size(); i < count; i++) {

            String vo = list.get(i);

            TextView cb;
            cb = (TextView) inflater.inflate(R.layout.layout_support, null);
            mTvGoodsInfoExpress.addViewByLayoutParams(cb);

            cb.setId(i);
            cb.setTag(vo);
            cb.setMaxLines(1);
            cb.setText(vo);

            cb.setVisibility(View.VISIBLE);
        }
    }


    private void addShoppingCart(int customerId,
                                 int storeGoodsId,
                                 int storeId,
                                 int goodsNum,
                                 int storeGoodsInfoId,
                                 int activeId,
                                 int activeType) {

        if (goodsNum <= 0) {
            showToast("暂无库存");
            return;
        }

        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId,
                storeGoodsId, storeId, goodsNum, storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(mContext,
                (response -> {

                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        return;
                    }
                    Toast.makeText(mContext, "已加入购物车", Toast.LENGTH_SHORT).show();
                    getShoppingCartCount();
                })));
    }

}
