package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.ChoiceAddressListActivity;
import com.pbph.yuguo.activity.MessageActivity;
import com.pbph.yuguo.activity.PaymentActivity;
import com.pbph.yuguo.activity.SearchActivity;
import com.pbph.yuguo.adapter.MyFragmentPageAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.LocationMsg;
import com.pbph.yuguo.msg.LocationResultMsg;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetIndexPopupCouponResquest;
import com.pbph.yuguo.request.GetNoReadMessageCountRequest;
import com.pbph.yuguo.request.GetStoreInfoByLocationResquest;
import com.pbph.yuguo.request.ReceiveIndexAllCouponResquest;
import com.pbph.yuguo.response.GetIndexPopupCouponResponse;
import com.pbph.yuguo.response.GetStoreInfoByLocationResponse;
import com.pbph.yuguo.util.JsClass;
import com.pbph.yuguo.util.MainDialog;
import com.pbph.yuguo.util.RxBusF;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvPosition;

    private TextView tv_mian_msg_count;

    private TabLayout mTabLayout;

    private ViewPager mViewPager;
    private MyFragmentPageAdapter mAdapter;
    ArrayList<Fragment> fragments = new ArrayList<>();

    private View ll_no_datas;
    private TextView tvNodata;
    //    private Banner banner_nodatas;
//    private List<GetSlideResponse.DataBean.SlideDtoListBean> bannerDatas = new ArrayList<>();
    private WebView webview;

    boolean getIndexPopupCouponfirst = false;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_main_viewpager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIndexPopupCouponfirst = true;

    }

    @Override
    public void initView() {

        ll_no_datas = mContentView.findViewById(R.id.ll_no_datas);
        ll_no_datas.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {

            }
        });
        ll_no_datas.setVisibility(View.GONE);

        webview = mContentView.findViewById(R.id.home_webView);
        initWebView();

        tvPosition = (TextView) mContentView.findViewById(R.id.tv_position);
        tvPosition.setOnClickListener(this);

        mContentView.findViewById(R.id.iv_mian_search).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (UserInfo.getInstance().getLatitude() == null && UserInfo.getInstance().getRecLatitude() == null)
                    return;
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        mContentView.findViewById(R.id.iv_mian_code).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (!YuGuoApplication.isLogin()) {
                    return;
                }
                startActivity(new Intent(getContext(), PaymentActivity.class));
            }
        });

        mContentView.findViewById(R.id.iv_mian_msg).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (!YuGuoApplication.isLogin()) {
                    return;
                }
                startActivity(new Intent(getContext(), MessageActivity.class));
            }
        });

        tv_mian_msg_count = mContentView.findViewById(R.id.tv_mian_msg_count);
        tv_mian_msg_count.setVisibility(View.INVISIBLE);


        tvNodata = (TextView) mContentView.findViewById(R.id.tv_nodata);
        tvNodata.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(getContext(), ChoiceAddressListActivity.class));
            }
        });
//        banner_nodatas = mContentView.findViewById(R.id.banner_nodatas);
//        makeBanner();


        fragments.add(new BestSellersGoodsListFragment());
        fragments.add(new GroupPurchaseGoodsListFragment());
        fragments.add(new FruitGoodsListFragment());
//        fragments.add(new HighMissionListFragment());
        mAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        mViewPager = mContentView.findViewById(R.id.vp_main_list);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);

        mTabLayout = mContentView.findViewById(R.id.tl_main_tab);
//
        mTabLayout.addTab(mTabLayout.newTab());
        // 使用 TabLayout 和 ViewPager 相关联
        mTabLayout.setupWithViewPager(mViewPager);
        // TabLayout指示器添加文本
        mTabLayout.getTabAt(0).setText("热卖");
        mTabLayout.getTabAt(1).setText("团购");
        mTabLayout.getTabAt(2).setText("水果");


    }

    @Override
    public void onStart() {
        super.onStart();
        getNoReadMessageCount();
        getIndexPopupCoupon();

        location();
    }

    @Override
    public void onStop() {
        RxBusF.removeDisposable0(MainViewPagerFragment.this, LocationMsg.class);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_position: {
                startActivity(new Intent(getContext(), ChoiceAddressListActivity.class));
            }
            break;
        }
    }


    private void location() {

        if (YuGuoApplication.userInfo.getRecAddId() != null) {
            ll_no_datas.setVisibility(View.GONE);
            //先判断有没有本地收货地址
            tvPosition.setText(YuGuoApplication.userInfo.getRecStreet());

            getStoreInfoByLocation(String.valueOf(UserInfo.getInstance().getRecLatitude()), String.valueOf(UserInfo.getInstance
                    ().getRecLongitude()), YuGuoApplication.userInfo.getRecStreet());

            return;
        }

        if (!TextUtils.isEmpty(YuGuoApplication.userInfo.getStreet())) {
            ll_no_datas.setVisibility(View.GONE);

            tvPosition.setText(YuGuoApplication.userInfo.getStreet());

            getStoreInfoByLocation(String.valueOf(UserInfo.getInstance().getLatitude()), String.valueOf(UserInfo.getInstance()
                    .getLongitude()), YuGuoApplication.userInfo.getStreet());

            return;
        }

        RxBusF.register0(MainViewPagerFragment.this, LocationMsg.class, locationMsg -> {
            if (locationMsg.succ) {

                ll_no_datas.setVisibility(View.GONE);

                if (locationMsg.loc) {
                    tvPosition.setText(YuGuoApplication.userInfo.getStreet());

                    getStoreInfoByLocation(String.valueOf(UserInfo.getInstance().getLatitude()), String.valueOf(UserInfo
                            .getInstance().getLongitude()), YuGuoApplication.userInfo.getStreet());
                } else {
                    tvPosition.setText(YuGuoApplication.userInfo.getRecStreet());

                    getStoreInfoByLocation(String.valueOf(UserInfo.getInstance().getRecLatitude()), String.valueOf(UserInfo
                            .getInstance().getRecLongitude()), YuGuoApplication.userInfo.getRecStreet());
                }

            } else {
                ll_no_datas.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(locationMsg.street)) tvPosition.setText(locationMsg.street);
                setWebView();
            }
        });

    }


//    private void makeBanner() {
//        int height = mContext.getScreenWidth() / 2;
//        banner_nodatas.getLayoutParams().height = height;
//        banner_nodatas.setImageLoader(new GlideImageLoader());
//        banner_nodatas.setIndicatorGravity(BannerConfig.RIGHT);
//        banner_nodatas.setImages(bannerDatas);
//        banner_nodatas.start();
//        banner_nodatas.setOnBannerListener((position) -> {
////            startActivity(new Intent(getActivity(), AccountManagementActivity.class));
//        });
//    }

//    private void getSlide() {
//        HttpAction.getInstance().getSlide(new GetIndexRotationChartRequest()).subscribe(new BaseObserver<>
// (mContext, (response -> {
//
//            if (200 != response.getCode()) {
//                return;
//            }
//            bannerDatas = response.getData().getSlideDtoList();
//            ArrayList<String> list = new ArrayList<>(bannerDatas.size());
//
//            if (bannerDatas != null)
//                for (GetSlideResponse.DataBean.SlideDtoListBean vo : bannerDatas) {
//                    list.add(vo.getSlideUrl());
//                }
//
//            banner_nodatas.update(list);
//
//        })));
//    }

    private void setWebView() {
        webview.loadUrl(ConstantData.LOC_FAIL);
    }

    private void getIndexPopupCoupon() {
        if (!getIndexPopupCouponfirst) return;

        if (!YuGuoApplication.isLogin()) {
            return;
        }

        HttpAction.getInstance().getIndexPopupCoupon(new GetIndexPopupCouponResquest(YuGuoApplication.userInfo.getCustomerId(),
                YuGuoApplication.userInfo.getCustomerLevelType())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
//                Toast.makeText(getContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            getIndexPopupCouponfirst = false;

            List<GetIndexPopupCouponResponse.DataBean.ListBean> list = response.getData().getList();

            if (list == null || list.isEmpty()) return;

            MainDialog.show(getContext(), list, onClickRateDialogListener);

        })));
    }

    MainDialog.OnClickRateDialogListener onClickRateDialogListener = () -> receiveIndexAllCoupon();


    private void receiveIndexAllCoupon() {
        HttpAction.getInstance().receiveIndexAllCoupon(new ReceiveIndexAllCouponResquest(YuGuoApplication.userInfo
                .getCustomerId(), YuGuoApplication.userInfo.getCustomerLevelType())).subscribe(new BaseObserver<>(mContext,
                (response -> {
//            if (200 != response.getCode()) {
//                return;
//            }
                })));
    }


    private void initWebView() {
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setBuiltInZoomControls(false);
        webview.setWebViewClient(new MyWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings()
//                .setUserAgentString(
//                        "Mozilla/5.0 (Linux; U; Android 2.2; en-gb; Nexus One Build/FRF50)
// AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
//        webview.getSettings()
//                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.addJavascriptInterface(new JsClass(getActivity(), webview), "callClass");

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候
            super.onPageStarted(view, url, favicon);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

    }


    private void getNoReadMessageCount() {

        if (!YuGuoApplication.isLogin()) {
            tv_mian_msg_count.setVisibility(View.INVISIBLE);
            return;
        }

        if (TextUtils.isEmpty(tv_mian_msg_count.getText().toString())) {
            tv_mian_msg_count.setVisibility(View.INVISIBLE);
        } else {
            tv_mian_msg_count.setVisibility(View.VISIBLE);
        }

        HttpAction.getInstance().getNoReadMessageCount(new GetNoReadMessageCountRequest(YuGuoApplication.userInfo.getCustomerId
                ())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
//                Toast.makeText(getContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            int c = response.getData().getCount();

            if (c <= 0) {
                tv_mian_msg_count.setVisibility(View.INVISIBLE);
            } else {
                tv_mian_msg_count.setVisibility(View.VISIBLE);
            }
            tv_mian_msg_count.setText(String.valueOf(c));

        })));
    }

    private void getStoreInfoByLocation(String receiverLat, String receiverLng, String street) {


        HttpAction.getInstance().getStoreInfoByLocation(new GetStoreInfoByLocationResquest(receiverLat, receiverLng)).subscribe
                (new BaseObserver<>(mContext, (response -> {

                    if (200 != response.getCode()) {

                        ll_no_datas.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(street)) tvPosition.setText(street);
                        setWebView();

                        return;
                    }

                    GetStoreInfoByLocationResponse.DataBean data = response.getData();
                    if (data == null || data.getStoreId() <= 0) {
                        ll_no_datas.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(street)) tvPosition.setText(street);
                        setWebView();

                        return;
                    }

                    RxBusF.post0(new LocationResultMsg(receiverLng, receiverLat));

                })));
    }


}
