package com.pbph.yuguo.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.util.JsClass;


public class WebViewFragment extends BaseFragment {
    private WebView webview = null;
    public static final String ARG_PARAM1 = "url";
    public static final String ARG_PARAM2 = "title";
    private String mainUrl = "";

    public WebViewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_my_browsers;
    }

    @Override
    public void initView() {

        webview = mContentView.findViewById(R.id.home_webView);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setBuiltInZoomControls(false);
        webview.setWebViewClient(new MyWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());
        mainUrl = getArguments().getString("url");
        webview.loadUrl(mainUrl);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings()
//                .setUserAgentString(
//                        "Mozilla/5.0 (Linux; U; Android 2.2; en-gb; Nexus One Build/FRF50) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
//        webview.getSettings()
//                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.addJavascriptInterface(new JsClass(getActivity(), webview), "callClass");
    }



    public void webViewGoBack() {
        if (null != webview) {
            if (webview.canGoBack()) {
                webview.goBack();
            } else {
                getActivity().finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        webview.loadUrl(mainUrl);
    }
//    private void backSetVisibility() {
//        if (webview.copyBackForwardList().getCurrentIndex() > 0) {
//            back.setVisibility(View.VISIBLE);
//        } else {
//            back.setVisibility(View.INVISIBLE);
//        }
//    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            webViewGoBack();
            // Log.e("WebFragmet事件", "OK");
        }
        return true;
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {
            }
        }

        // 处理javascript中的alert
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // 构架一个builder来显示网页中的对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示信息");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击确定按钮之后，继续执行网页中的操作
                            result.confirm();
                        }
                    });

            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    return true;
                }
            });

            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        // 处理javascript中的confirm
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示信息");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });
            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    return true;
                }
            });

            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        /**
         * 覆盖默认的window.prompt
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    view.getContext());
            builder.setTitle("提示信息").setMessage(message);
            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm(et.getText().toString());
                }
            }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    return true;
                }
            });

            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {// 网页加载结束的时候
            webview.setEnabled(true);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            view.loadUrl("file:///android_asset/404.html");
            Log.e("onReceivedError", errorCode + "  " + description + "    " + failingUrl);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // 网页加载时的连接的网址
            Log.e("url", url);
            if (url.startsWith("taobao") || url.startsWith("tbopen")) {
                openTaoBao(url);
                return true;
            } else if (url.startsWith("sinaweibo") || url.startsWith("snssdk")) {
                viewBrowser(url);
                return true;
            } else if (url.contains("reload")) {
                view.loadUrl(mainUrl);
                return true;
            } else if (url.startsWith(mainUrl) && url.contains("detail")) {

            } else if (url.contains("device")){
                return true;
            }
            view.loadUrl(url);
            return true;

        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            // Log.e("即将加载的页面1111:", view.getUrl());
//            return true;
//        }
    }

    private String closeDialogJS() {
        return "javascript:$(\".hd_close\").click();";
    }

    @TargetApi(19)
    private void closeDialog() {
//        webview.evaluateJavascript(closeDialogJS(), new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                Log.e("closeDialog", s);
//            }
//        });
    }

    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            getActivity().getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void openTaoBao(String url) {
        if (!checkPackage("com.taobao.taobao")) {
            Toast.makeText(getActivity(), "未安装手机淘宝，请下载安装", Toast.LENGTH_LONG).show();
            return;
        }
        viewBrowser(url);
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Log.e("toTaobao", url);
//        Uri uri = Uri.parse(TextUtils.isEmpty(url) ? "taobao://m.taobao.com" : url);
//        intent.setData(uri);
//        startActivity(intent);
    }

    private void viewBrowser(String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
//            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("请安装客户端");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if (null != webview) {
            try {
                ((RelativeLayout) mContentView).removeView(webview);
                webview.removeAllViews();
                webview.destroy();
                webview.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDetach();
    }

    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
