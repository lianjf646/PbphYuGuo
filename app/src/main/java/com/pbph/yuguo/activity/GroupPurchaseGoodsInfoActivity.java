package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.MySharePopRedBagWindow;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.FlowLayout;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetGoodsGroupDetailResquest;
import com.pbph.yuguo.response.GetGoodsGroupDetailResponse;
import com.pbph.yuguo.util.DensityUtils;
import com.pbph.yuguo.util.LongTime2HMS;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.SecTimer;
import com.pbph.yuguo.wxutil.WechatUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GroupPurchaseGoodsInfoActivity extends BaseActivity {

    LayoutInflater inflater;

    private Banner mBanner;
    public TextView tvGroupPurchaseGoodsInfoPrice;
    public TextView tvGroupPurchaseGoodsInfoDiscountPrice;
    public TextView tvAdapterGroupGoodsTimeHour;
    public TextView tvAdapterGroupGoodsTimeMin;
    public TextView tvAdapterGroupGoodsTimeSec;
    public TextView tvGroupPurchaseGoodsInfoTitle;
    public TextView tvGroupPurchaseGoodsInfoDesc;
    public ProgressBar pbGroupPurchaseGoodsInfoCountProgress;
    public TextView tvGroupPurchaseGoodsInfoCount;
    public TextView tvGroupPurchaseGoodsInfoSurplusCount;
    public LinearLayout llGroupPerInfo;
    public FlowLayout tvGroupPurchaseGoodsInfoExpress;
    //    public TextView tvVipCouponPrice;
    public TextView tvGoodsInfoDesc;
    private WebView webView;
    public Button btnGroupPurchaseGroupPurchaseGoodsInfoSubmit;

    private String goodimage = "";

    private GetGoodsGroupDetailResponse.DataBean dataBean;

    private Integer goodsGroupActivityId;

    SecTimer secTimer;

    protected MySharePopRedBagWindow sharePopWindow;//分享

    int type;
    long passTimebu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        goodsGroupActivityId = intent.getIntExtra("goodsGroupActivityId", -1);
        type = intent.getIntExtra("type", 1);


        inflater = LayoutInflater.from(this);

        setContentView(R.layout.activity_group_purchase_goods_info);
        initTitle(TITLE_STYLE_WHITE, "商品详情", true, false);

        initView();

        if (type == 1) {
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("立即参团");
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(true);
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(true);
        } else {
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("已参团");
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(false);
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(false);
        }

    }


    private void initView() {

        mBanner = findViewById(R.id.group_purchase_goods_info_banner);
        int w = getScreenWidth();
        int h = (int) (w / 1.44f);
        mBanner.getLayoutParams().height = h;


        ArrayList<String> imgList = new ArrayList<>();
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImages(imgList);
        mBanner.start();
        mBanner.setOnBannerListener((position) -> {
        });
//        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        this.tvGroupPurchaseGoodsInfoPrice = (TextView) findViewById(R.id.tv_group_purchase_goods_info_price);
        this.tvGroupPurchaseGoodsInfoDiscountPrice = (TextView) findViewById(R.id.tv_group_purchase_goods_info_discount_price);
//        tvGroupPurchaseGoodsInfoDiscountPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        findViewById(R.id.iv_share).setOnClickListener(listener);

        this.tvAdapterGroupGoodsTimeHour = (TextView) findViewById(R.id.tv_adapter_group_goods_time_hour);
        this.tvAdapterGroupGoodsTimeMin = (TextView) findViewById(R.id.tv_adapter_group_goods_time_min);
        this.tvAdapterGroupGoodsTimeSec = (TextView) findViewById(R.id.tv_adapter_group_goods_time_sec);
        this.tvGroupPurchaseGoodsInfoTitle = (TextView) findViewById(R.id.tv_group_purchase_goods_info_title);
        this.tvGroupPurchaseGoodsInfoDesc = (TextView) findViewById(R.id.tv_group_purchase_goods_info_desc);
        this.pbGroupPurchaseGoodsInfoCountProgress = (ProgressBar) findViewById(R.id.pb_group_purchase_goods_info_count_progress);
        this.tvGroupPurchaseGoodsInfoCount = (TextView) findViewById(R.id.tv_group_purchase_goods_info_count);
        this.tvGroupPurchaseGoodsInfoSurplusCount = (TextView) findViewById(R.id.tv_group_purchase_goods_info_surplus_count);
        this.llGroupPerInfo = (LinearLayout) findViewById(R.id.ll_group_per_info);
        this.tvGroupPurchaseGoodsInfoExpress = findViewById(R.id.tv_group_purchase_goods_info_express);
        tvGroupPurchaseGoodsInfoExpress.setVisibility(View.GONE);
//        this.tvVipCouponPrice = (TextView) findViewById(R.id.tv_vip_coupon_price);
        this.tvGoodsInfoDesc = (TextView) findViewById(R.id.tv_goods_info_desc);
        this.btnGroupPurchaseGroupPurchaseGoodsInfoSubmit = (Button) findViewById(R.id.btn_group_purchase_group_purchase_goods_info_submit);
        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setOnClickListener(listener);

        webView = findViewById(R.id.nvwv_group_purchase_goods_info_details);

        initWebView();


    }

    @Override
    public void onStart() {
        super.onStart();
        getGoodsGroupDetail();

    }

    @Override
    public void onStop() {
        if (secTimer != null) {
            secTimer.cancel();
        }
        super.onStop();
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

    private void getGoodsGroupDetail() {

        HttpAction.getInstance().getGoodsGroupDetail(new GetGoodsGroupDetailResquest(goodsGroupActivityId))
                .subscribe(new BaseObserver<>(this, (response -> {
                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        finish();
                        return;
                    }
                    this.dataBean = response.getData();

                    setBannerImage();

                    showGoodsInfo();
                })));
    }

    private void setBannerImage() {
        ArrayList<String> imgList = new ArrayList<>();
        List<GetGoodsGroupDetailResponse.DataBean.GoodsDetailBean.GoodsSlideListBean> imgListBeans = dataBean.getGoodsDetail().getGoodsSlideList();
        if (null == imgListBeans || imgListBeans.isEmpty()) {
            return;
        }
        for (int i = 0; i < imgListBeans.size(); i++) {
            GetGoodsGroupDetailResponse.DataBean.GoodsDetailBean.GoodsSlideListBean bean = imgListBeans.get(i);
            if (0 == bean.getGoodsType() || 2 == bean.getGoodsType()) {
                imgList.add(bean.getGoodsImgUrl());
            } else if (1 == bean.getGoodsType() || 3 == bean.getGoodsType()) {
                goodimage = bean.getGoodsImgUrl();
            }
        }
        mBanner.update(imgList);
    }


    private void showGoodsInfo() {

        GetGoodsGroupDetailResponse.DataBean.GoodsDetailBean goodsDetail = dataBean.getGoodsDetail();
        tvGroupPurchaseGoodsInfoTitle.setText(goodsDetail.getGoodsGroupName());
        tvGroupPurchaseGoodsInfoDesc.setText(goodsDetail.getGoodsGroupNameSub());

        pbGroupPurchaseGoodsInfoCountProgress.setMax(goodsDetail.getGroupNumber());
        pbGroupPurchaseGoodsInfoCountProgress.setProgress(goodsDetail.getAlreadyGroupNumber());


//        tvGroupPurchaseGoodsInfoCount.setText("参团");
//        tvGroupPurchaseGoodsInfoCount.append(String.valueOf(goodsDetail.getAlreadyGroupNumber()));
//        tvGroupPurchaseGoodsInfoCount.append("人");
//
//        tvGroupPurchaseGoodsInfoSurplusCount.setText("剩余");
//        tvGroupPurchaseGoodsInfoSurplusCount.append(String.valueOf(goodsDetail.getOfferedCount()));
//        tvGroupPurchaseGoodsInfoSurplusCount.append("人");

        if (goodsDetail.getGroupNumber() > goodsDetail.getAlreadyGroupNumber()) {
            tvGroupPurchaseGoodsInfoCount.setText("参团");
            tvGroupPurchaseGoodsInfoCount.append(String.valueOf(goodsDetail.getAlreadyGroupNumber()));
            tvGroupPurchaseGoodsInfoCount.append("人");

            tvGroupPurchaseGoodsInfoSurplusCount.setText("剩余");
            tvGroupPurchaseGoodsInfoSurplusCount.append(String.valueOf(goodsDetail.getOfferedCount()));
            tvGroupPurchaseGoodsInfoSurplusCount.append("人");
        } else {
            tvGroupPurchaseGoodsInfoCount.setText("本团已满");
            tvGroupPurchaseGoodsInfoCount.append(String.valueOf(goodsDetail.getAlreadyGroupNumber()));
            tvGroupPurchaseGoodsInfoCount.append("人");

            tvGroupPurchaseGoodsInfoSurplusCount.setText("");
        }

        tvGroupPurchaseGoodsInfoPrice.setText(MoneyHelper.getInstance4Fen(goodsDetail.getGroupPrice()).change2Yuan().getString());

        tvGroupPurchaseGoodsInfoDiscountPrice.setText("市场价 ￥");
        tvGroupPurchaseGoodsInfoDiscountPrice.append(MoneyHelper.getInstance4Fen(goodsDetail.getGoodsGroupMarketPrice()).change2Yuan().getString());

////TODO//        快递
//        List<String> temps = goodsDetail.getSupportNameList();
//        if (temps == null || temps.isEmpty()) {
//            tvGroupPurchaseGoodsInfoExpress.setVisibility(View.GONE);
//        } else {
//            tvGroupPurchaseGoodsInfoExpress.setVisibility(View.VISIBLE);
//            setDatas4FlowLayout(temps);
//        }


////会员抵用券金额
//        tvVipCouponPrice.setText(MoneyHelper.getInstance4Fen(dataBean.getProfitMoney()).change2Yuan().getString());


//        GetGoodsGroupDetailResponse.DataBean.CouponActivityBean couponVo = dataBean.getCouponActivity();


        tvGoodsInfoDesc.setText(getGoodsInfoSpec());

        if (!TextUtils.isEmpty(goodimage)) {
            webView.loadData(getHtml(goodimage), "text/html", "utf-8");
        }


        //  灰色
        if (type == 2) {
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("已参团"); //绿色
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setBackgroundColor(getResources().getColor(R.color.main_color));
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(false);
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(false);
        } else if (goodsDetail.getEndDatetime() - goodsDetail.getCurrentTime() - passTimebu <= 0) {
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("已结束"); //  灰色
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(false);
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(false);
        } else if (goodsDetail.getGroupNumber() - goodsDetail.getAlreadyGroupNumber() <= 0) {
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("已团满"); //  灰色
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(false);
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(false);
        } else {
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("立即参团"); //绿色
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setBackgroundColor(getResources().getColor(R.color.main_color));
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(true);
            btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(true);
        }

        secTimer = new SecTimer() {
            @Override
            public void onTick(long passTime) throws Exception {
                if (dataBean == null) return;
                passTimebu = passTime;
                GetGoodsGroupDetailResponse.DataBean.GoodsDetailBean detail = dataBean.getGoodsDetail();
                long stime = detail.getEndDatetime() - detail.getCurrentTime() - passTime;
                if (stime <= 0) {
                    tvAdapterGroupGoodsTimeHour.setText("00");
                    tvAdapterGroupGoodsTimeMin.setText("00");
                    tvAdapterGroupGoodsTimeSec.setText("00");

                    if (type == 2) {
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("已参团"); //绿色
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setBackgroundColor(getResources().getColor(R.color.main_color));
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(false);
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(false);
                    } else {
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setText("已结束"); //  灰色
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setClickable(false);
                        btnGroupPurchaseGroupPurchaseGoodsInfoSubmit.setEnabled(false);
                    }
                    return;
                }


                LongTime2HMS time = LongTime2HMS.longTime2HMS(stime);

                tvAdapterGroupGoodsTimeHour.setText(time.getH());
                tvAdapterGroupGoodsTimeMin.setText(time.getM());
                tvAdapterGroupGoodsTimeSec.setText(time.getS());
            }
        };
        secTimer.start();
    }

    private String getGoodsInfoSpec() {
        StringBuilder builder = new StringBuilder();

        List<GetGoodsGroupDetailResponse.DataBean.ParamListBean> detailsListBeans = dataBean.getParamList();
        if (detailsListBeans != null) {
            for (int i = 0, size = detailsListBeans.size(); i < size; i++) {
                GetGoodsGroupDetailResponse.DataBean.ParamListBean bean = detailsListBeans.get(i);
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

    OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            switch (v.getId()) {
                case R.id.iv_share:
                    if (!YuGuoApplication.isLogin()) {
                        startActivity(new Intent(mContext, LoginActivity.class));
                        return;
                    }
                    if (sharePopWindow == null)
                        sharePopWindow = new MySharePopRedBagWindow(mContext, onShareClickListener);
                    sharePopWindow.showDialog(v);
                    break;
                case R.id.btn_group_purchase_group_purchase_goods_info_submit:

                    if (!YuGuoApplication.isLogin()) {
                        startActivity(new Intent(mContext, LoginActivity.class));
                        return;
                    }
                    if (dataBean == null) return;
                    goBuy();
                    break;
            }
        }
    };

    MySharePopRedBagWindow.ShareOnClickListener onShareClickListener = type -> {
        switch (type) {
            case SHARELIFEWX: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPage(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
            }
            break;
            case SHARETIMELINE: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPageTimeLine(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
            }
            break;
        }
    };

    void goBuy() {
        YuGuoApplication application = (YuGuoApplication) getApplication();
        application.object = dataBean;
        startActivity(new Intent(this, GroupOrderFillActivity.class));
    }


    private void setDatas4FlowLayout(List<String> list) {
        tvGroupPurchaseGoodsInfoExpress.removeAllViews();
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
            tvGroupPurchaseGoodsInfoExpress.addViewByLayoutParams(cb);

            cb.setId(i);
            cb.setTag(vo);
            cb.setMaxLines(1);
            cb.setText(vo);

            cb.setVisibility(View.VISIBLE);
        }
    }
}
