package com.pbph.yuguo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.myview.WaitUI;

public class VipEquityWebActivity extends BaseActivity {

    private int tab;
    private int levelType;
    private int userType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(TITLE_STYLE_WHITE, "会员权益说明", true, false);
        setContentView(R.layout.activity_vip_equity_web);
        initIntent();
        initView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        tab = intent.getIntExtra("tab", -1);
        levelType = intent.getIntExtra("levelType", 0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        WebView webView = findViewById(R.id.web_view);
        webView.setWebChromeClient(new WebChromeClient());
        if (levelType == 2) {
            userType = 1;
        } else {
            userType = 0;
        }
        webView.loadUrl(ConstantData.VIP_EQUITY_URL+"?tab=" + tab + "&userType=" + userType);

        WebSettings webSettings = webView.getSettings();
        //不使用缓存，只从网络获取数据.
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "vip");
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        webView.setWebViewClient(webViewClient);
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            //页面加载完成
            WaitUI.Cancel();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //页面开始加载
            WaitUI.Show(mContext);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//     注意：super句话一定要删除，或者注释掉，否则又走handler.cancel() 默认的不支持https的了。
//     super.onReceivedSslError(view, handler, error);
            handler.proceed();// 接受所有网站的证书
        }
    };

    @Override
    public void onLeftClick() {
        finish();
    }

    @JavascriptInterface
    public void openVip() {
        startActivity(new Intent(this, OpenVipActivity.class));
    }

    @JavascriptInterface
    public void goNameAuth() {
        startActivity(new Intent(this, RealNameStateActivity.class));
    }
}
