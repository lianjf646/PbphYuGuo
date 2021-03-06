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
            // Log.e("WebFragmet??????", "OK");
        }
        return true;
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {
            }
        }

        // ??????javascript??????alert
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // ????????????builder??????????????????????????????
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("????????????");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ?????????????????????????????????????????????????????????
                            result.confirm();
                        }
                    });

            // ??????keycode??????84???????????????
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

        // ??????javascript??????confirm
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("????????????");
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
            // ??????keycode??????84???????????????
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
         * ???????????????window.prompt
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    view.getContext());
            builder.setTitle("????????????").setMessage(message);
            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et);
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm(et.getText().toString());
                }
            }).setNeutralButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });

            // ??????keycode??????84???????????????
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
        public void onPageStarted(WebView view, String url, Bitmap favicon) {// ?????????????????????????????????
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {// ???????????????????????????
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
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // ?????????????????????????????????
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
//            // Log.e("?????????????????????1111:", view.getUrl());
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
            Toast.makeText(getActivity(), "???????????????????????????????????????", Toast.LENGTH_LONG).show();
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
            showToast("??????????????????");
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
