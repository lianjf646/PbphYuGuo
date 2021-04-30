package com.pbph.yuguo.wxutil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.pbph.yuguo.BuildConfig;
import com.pbph.yuguo.R;
import com.pbph.yuguo.response.WxPayOrderResponse;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class WechatUtils {
    //  支付 正式
    public static final String APP_ID = "wx9f3056f1af817963";
    public static final String APP_SECRET = "cf4d90511c252494057de0142668d121";

    /**
     * 测试
     */
//    public static final String APP_ID = "wxd10426a8e2ae7aa1";
//    public static final String APP_SECRET = "079a23a7df41a16d2633eb07c8621b96";

    //  授权正式 提现
    public static final String AUTH_APP_ID = "wxc66bbf41d0430eb6";
    public static final String AUTH_APP_SECRET = "0d5004af0c6500065c0844e04a03d1e1";

    /**
     * 测试
     */
//    public static final String AUTH_APP_ID = "wxd10426a8e2ae7aa1";
//    public static final String AUTH_APP_SECRET = "079a23a7df41a16d2633eb07c8621b96";

    private static final int THUMB_SIZE = 150;
    public static final String TAG = "WechatUtils"; // 日志输出类名。
    private Context context;
    private IWXAPI wxApi;

    private WechatUtils() {

    }

    public static WechatUtils getInstance() {
        return WechatUtilsHolder.mIntance;
    }

    private static class WechatUtilsHolder {
        private static WechatUtils mIntance = new WechatUtils();
    }

    /**
     * @param context
     * @return
     */
    public WechatUtils initWechatUtils(Context context) {
        this.context = context;
//        if (null == wxApi) {
        wxApi = WXAPIFactory.createWXAPI(context, APP_ID, false);
        wxApi.registerApp(APP_ID);
//        }
        return this;
    }

    /**
     * @param context
     * @return
     */
    public WechatUtils initAuthWechatUtils(Context context) {
        this.context = context;
//        if (null == wxApi) {
        wxApi = WXAPIFactory.createWXAPI(context, AUTH_APP_ID, false);
        wxApi.registerApp(AUTH_APP_ID);
//        }
        return this;
    }

    public void wechatPay(WxPayOrderResponse.DataBean.PayInfoBean data) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        PayReq req = new PayReq();
        req.appId = data.getAppid();
        req.partnerId = data.getPartnerid();
        req.prepayId = data.getPrepayid();
//        req.packageValue = data.get();
        req.packageValue = data.getPackages();
        req.nonceStr = data.getNoncestr();
        req.timeStamp = data.getTimestamp();
        req.sign = data.getSign();
        req.extData = "app data";
        wxApi.sendReq(req);
    }

    public void shareImages(Bitmap bmp, String title, int type) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = title;
        msg.description = "";
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = bitmap2Bytes(thumbBmp, 31);
        bmp.recycle();
        thumbBmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = type;
        wxApi.sendReq(req);
    }

    public void shareImages(Bitmap bmp, int type) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = "";
        msg.description = "";
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = bitmap2Bytes(thumbBmp, 31);
        bmp.recycle();
        thumbBmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = type;
        wxApi.sendReq(req);
    }

    public void shareText(String text, int type) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }

        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        //        msg.title = "Will be ignored";
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = type;

        wxApi.sendReq(req);
    }

    public void sharePublicNumberUrl(String url) {
        sendWebPage(url, "提现公众号", "请关注 “唯我首赚” 并进行授权");
    }

    public void weChatLogin() {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "toutoule";
        wxApi.sendReq(req);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    public void shareToFriend(File file, int type) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        Log.e("shareToFriend", file.getAbsolutePath());
        try {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", type == 0 ? "com.tencent.mm.ui.tools.ShareImgUI" : "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction("android.intent.action.SEND");
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendWebPage(String url, String title, String description) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.feng_xiang_ico);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        wxApi.sendReq(req);
    }


    public void sendWebPageTimeLine(String url, String title, String description) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.feng_xiang_ico);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        wxApi.sendReq(req);
    }


    public void sendWebPage(String url, byte[] bytes, String title, String description, int type) {
        if (!wxApi.isWXAppInstalled()) {
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;

        msg.thumbData = bytes;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = type;

        wxApi.sendReq(req);
    }

    public void shareVideo(String url, byte[] bytes, String title, int type) {

        if (!wxApi.isWXAppInstalled()) {
            return;
        }
//        1、创建WXVideoObject对象，用于指向视频URL
        WXVideoObject videoObject = new WXVideoObject();
//        2、设置视频url
        videoObject.videoUrl = url;
//        3、创建WXMediaMessage对象，
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = videoObject;
        msg.title = title;
//        msg.description = "很搞笑哦";
//        4、设置缩略图
        msg.thumbData = bytes;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = String.valueOf(System.currentTimeMillis()) + "vidio";
        req.scene = type;
        wxApi.sendReq(req);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 30, output);

        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
