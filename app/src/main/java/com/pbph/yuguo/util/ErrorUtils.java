package com.pbph.yuguo.util;

import android.content.Context;
import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.pbph.yuguo.callback.MyErrorCallBack;
import com.pbph.yuguo.myview.WaitUI;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.HttpException;


public class ErrorUtils {

    private static final int RESPONSE_FATAL_EOR = -1;  //返回数据失败,严重的错误
    private static final String TAG = ErrorUtils.class.getSimpleName();

    public static void paserError(Context context, Throwable t, MyErrorCallBack errorCallBack) {
        WaitUI.Cancel();
        int errorCode;
        String errorMsg;
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
//            try {
//                Log.e("response",httpException.response().errorBody().string()+"  123123");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            Log.e(TAG, errorCode + "\n" + errorMsg + "\n");
            if (errorCode >= 500) {
                errorCode = RESPONSE_FATAL_EOR;
                errorMsg = "服务器错误";
            } else {
                try {
                    JSONObject object = new JSONObject(httpException.response().errorBody().string());
//                    Log.e("json", object.getInt("code") + " " + object.getString("message"));
//                    BaseError baseError = JsonMananger.jsonToBean(httpException.response().errorBody().string(), BaseError.class);
//                    errorMsg = baseError.getMessage();
//                    errorCode = object.getInt("code");
                    errorMsg = object.getString("message");
//                    Log.e("baseError", errorCode + "\n" + errorMsg + "\n" + object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMsg = "网络链接异常，请稍后尝试";
                }
            }
//            Log.e(TAG, errorCode + "\n" + errorMsg + "\n");
//            getErrorMsg(httpException);
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof UnknownHostException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "没有网络，请检查网络连接";
        } else if (t instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "主线程不能网络请求";
        } else if (t instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "运行时错误";
        } else {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "未知的服务器错误";
        }
        if (null == errorCallBack) {
            disposeEorCode(context, errorMsg, errorCode);
        } else {
            errorCallBack.onError(errorCode, errorMsg);
        }
    }

    private static void disposeEorCode(Context mContext, String message, int code) {
//        Toast.makeText(mContext, "bad http code result： " + code, Toast.LENGTH_SHORT).show();
        Log.e("======>", "bad http code result： " + code);
        switch (code) {
            case 101:
            case 112:
            case 123:
            case 401:
                Toast.makeText(mContext, "请重新登录401", Toast.LENGTH_SHORT).show();
                if (mContext != null) {  //Context 可以使Activity BroadCast Service !
                    Intent intent = new Intent();
                    //不要hard Code, 使用灵活的Intent 来做吧
                    intent.setAction("app.intent.action.LOGIN");
                    mContext.startActivity(intent);
                }
                break;
            default:
                if (null != mContext)
                    Toast.makeText(mContext, TextUtils.isEmpty(message) ? "网络链接异常，请稍后尝试" : message, Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
