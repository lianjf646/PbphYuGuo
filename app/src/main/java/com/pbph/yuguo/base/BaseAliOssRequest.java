package com.pbph.yuguo.base;


import android.support.annotation.NonNull;

import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.util.MD5;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseAliOssRequest {

    public abstract JSONObject toJson();

    public abstract String toJsonString();

    protected JSONObject getJson(@NonNull JSONObject object) {
        return getPublicRequestData(object);
    }

    public JSONObject getJsonData() {
        return getPublicRequestData(toJson());
    }
//    {
//        "data": {
//        "customerPhone": "18346170106",
//                "smsCode":"123456",
//                "customerId": 1
//    },
//        "timeStamp": "1533560372",
//            "sign": "734a2ec48d0d7c669a0ad64a6eaa3b6e",
//            "clientType": 2,
//            "token": "123456",
//            "version": "1.0",
//            "customerId": 1,
//            "tradeNo": "20180810145400"
//    }

    /**
     * @param data
     * @return
     */
    private JSONObject getPublicRequestData(JSONObject data) {
        JSONObject object = new JSONObject();
        try {
            object.put("timestamp", System.currentTimeMillis());
            object.put("sign", MD5.encrypt(data.toString()));
            object.put("clientType", "1");
            object.put("token", YuGuoApplication.userInfo.getToken());
            object.put("version", "1.0");
            object.put("customerId", YuGuoApplication.userInfo.getCustomerId());
            object.put("tradeNo", makeTradeNo());
            object.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    //    生成tradeNo 规则 10位秒值 5位随机数
    String makeTradeNo() {
        return (System.currentTimeMillis() / 1000) + "" + ((int) ((Math.random() * 9 + 1) * 10000));
    }
}
