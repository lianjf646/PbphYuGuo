package com.pbph.yuguo.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JsClass {

    private Activity context;

    private WebView webView;

    public JsClass(Activity context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @JavascriptInterface
    public void playVideo(String url) {
        if (url != null) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/mp4");
            context.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void shareToWeChat(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        context.startActivity(intent);
    }

    @JavascriptInterface
    public void shareClick() {
//        context.startActivity(new Intent(context, MyShareActivity.class));
    }
}
