package com.pbph.yuguo.observer;

import android.content.Context;
import android.content.Intent;

import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseResponse;
import com.pbph.yuguo.callback.MyCallBack;
import com.pbph.yuguo.callback.MyErrorCallBack;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.util.ErrorUtils;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

public class BaseObserver<T extends BaseResponse> implements FlowableSubscriber<T> {
    private final String TAG = BaseObserver.class.getSimpleName();
    private Context mContext;
    MyCallBack callBack = null;
    MyErrorCallBack errorCallBack = null;
    Subscription s;

    //    此字段只有一项功能，就是当接口返回时，如果出现1010错误时，是否走onNext中关于此错误码的验证处理 需要创建BaseObserver时，传入。
    //现在只在欢迎界面中使用了此字段。不希望其他地方出现此字段的使用。
    private boolean dontGoLogin = false;

    //    此字段只标识是否已经出现过1010错误了 此字段只有两处地方可用和可修改。1本类中，2 loginactivity中。不希望其他地方出现此字段的使用。
    public static volatile boolean has1010Error = false;

    public BaseObserver(Context context, MyCallBack<T> myCallBack) {
        this(context, myCallBack, null);

    }

    public BaseObserver(Context context, MyCallBack<T> myCallBack, boolean dontGoLogin) {
        this(context, myCallBack, null, dontGoLogin);

    }

    public BaseObserver(Context context, MyCallBack<T> myCallBack, MyErrorCallBack errorCallBack) {
        this(context, myCallBack, errorCallBack, false);
    }

    public BaseObserver(Context context, MyCallBack<T> myCallBack, MyErrorCallBack errorCallBack, boolean dontGoLogin) {
        this.callBack = myCallBack;
        this.mContext = context;
        setErrorCallBack(errorCallBack);
        this.dontGoLogin = dontGoLogin;
    }


    public void setErrorCallBack(MyErrorCallBack errorCallBack) {
        this.errorCallBack = errorCallBack;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.s = s;
        s.request(Integer.MAX_VALUE);
    }

//    @Override
//    public void onSubscribe(Disposable d) {
//
//    }

    @Override
    public void onNext(T t) {
//        onSuccess(t);
        try {
//            如果第二次出现1010那么什么也不要做 希望第一次出现时已经处理
//            可能出現的情況是一個界面多接口在做请求，此时出现1010那么第一个返回数据的接口会进入第一次逻辑中（80行的逻辑）跳入登录界面
//            其他后续的接口返回就让他们走此逻辑，当他们自动消失了就好 and i hope so
//            这之后，用户走登录逻辑时，会把has1010Error 字段改回false，且登陆成功后，程序会走入正常流程
//            if (has1010Error) {
//                WaitUI.Cancel();
//                return;
//            }

//            如果第一次出现1010错误则进入此逻辑 跳入登錄界面。
            if (!dontGoLogin && !has1010Error && (t.getCode() == 1010 || t.getCode() == 1011)) {// 令牌错误
                has1010Error = true;
                WaitUI.Cancel();
                YuGuoApplication.userInfo.setToken("");
                YuGuoApplication.userInfo.setCustomerId(-1);

                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }
            callBack.onNext(t);
//            if (t.getCode() == 200) {
//                callBack.onNext(t);
//            } else {
//                Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
//                if (null != errorCallBack) {
//                    errorCallBack.onError(t.getCode(), t.getMsg());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.cancel();
    }

    /**
     * 通用异常错误的处理，不能弹出一样的东西出来
     *
     * @param t
     */
    @Override
    public void onError(Throwable t) {
        s.cancel();
        t.printStackTrace();
        ErrorUtils.paserError(mContext, t, errorCallBack);
    }


    /**
     * 简单的把Dialog 处理掉
     */
    @Override
    public final void onComplete() {

    }

}
