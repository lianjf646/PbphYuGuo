package com.pbph.yuguo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class SpHelper {
    private final static String USERID = "userid";
    private final static String TOKEN = "token";

    private static volatile SpHelper instance = null;

    public static SpHelper getInstance() {
        if (instance == null) {
            synchronized (SpHelper.class) {
                if (instance == null) {
                    instance = new SpHelper();
                }
            }
        }
        return instance;
    }

    //    private SpHelper() {


    private SharedPreferences sp;

    public void init(Context context) {
        String SP_NAME = context.getPackageName() + "_sp";
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public boolean putAccountPassword(String username, String password) {

        if (TextUtils.isEmpty(username)) username = "";
        if (TextUtils.isEmpty(password)) password = "";

        SharedPreferences.Editor editor = sp.edit();//
        editor.putString("username", username);//
        editor.putString("password", password);//
        return editor.commit();
    }

    public boolean putPassword(String password) {

        if (TextUtils.isEmpty(password)) password = "";

        SharedPreferences.Editor editor = sp.edit();//
        editor.putString("password", password);//
        return editor.commit();
    }

    public String getAccount() {
        return sp.getString("username", "");
    }

    public String getPassword() {
        return sp.getString("password", "");
    }

    //
    public boolean putRemember(boolean isRemember) {
        SharedPreferences.Editor editor = sp.edit();//
        editor.putBoolean("isRemember", isRemember);//
        return editor.commit();
    }

    public boolean isRemember() {
        return sp.getBoolean("isRemember", false);
    }

    public String getUserid() {
        return sp.getString(USERID, "");
    }

    public int getIntUserId() {
        String userId = sp.getString(USERID, "-1");
        if (TextUtils.isEmpty(userId)) {
            return -1;
        }
        if (userId.equals("null")) {
            return -1;
        }
        return Integer.valueOf(userId);
    }

    public void setUserID(String userid) {
        sp.edit().putString(USERID, userid).apply();
    }


    public String getToken() {
        return sp.getString(TOKEN, "");
//        return "123";
    }

    public void setToken(String token) {
        sp.edit().putString(TOKEN, token).apply();
    }

    public String getParentId() {
        return sp.getString("ParentId", "");
    }

    public void setParentId(String ParentId) {
        sp.edit().putString("ParentId", ParentId).apply();
    }

    public String getAlipayUserId() {
        return sp.getString("AlipayUserId", "");
    }

    public void setAlipayUserId(String AlipayUserId) {
        sp.edit().putString("AlipayUserId", AlipayUserId).apply();
    }
}
