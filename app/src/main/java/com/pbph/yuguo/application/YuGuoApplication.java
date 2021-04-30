package com.pbph.yuguo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.util.CrashHandler;
import com.pbph.yuguo.util.SpHelper;
import com.pbph.yuguo.util.SystemUtils;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

public class YuGuoApplication extends Application {

    public Activity rootActivity;
    public Object object;
    public boolean scrollView = false;

    public static UserInfo userInfo;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(getApplicationContext()))) {
            SpHelper.getInstance().init(this);
            userInfo = UserInfo.getInstance();
//            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            SDKInitializer.initialize(getApplicationContext());
            createFolder();
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    public void createFolder() {
        File file = new File(ConstantData.PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        File down = new File(ConstantData.DOWN_LOAD_PATH);
        if (!down.exists()) {
            down.mkdirs();
        }
    }

    public static boolean isLogin() {
        if (userInfo == null) return false;
        if (userInfo.getCustomerId() == null) return false;
        if (userInfo.getCustomerId() == -1) return false;
        if (TextUtils.isEmpty(userInfo.getToken())) return false;
        return true;
    }
    public static Context getContext(){
        return mContext;
    }
}
