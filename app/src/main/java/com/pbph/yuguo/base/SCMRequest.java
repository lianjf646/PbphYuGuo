package com.pbph.yuguo.base;


import android.support.annotation.NonNull;

import com.pbph.yuguo.util.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class SCMRequest implements Serializable {


    public String projectCode;
    public String token;

    public abstract JSONObject toJson();

    public abstract String toJsonString();

    protected JSONObject getJson(@NonNull JSONObject object) {
        return getPublicRequestData(object);
    }

    public JSONObject getJsonData() {
        return getPublicRequestData(toJson());
    }

    /**
     * @param data
     * @return
     */
    private JSONObject getPublicRequestData(JSONObject data) {
        JSONObject object = new JSONObject();
        try {
            object.put("clientType", "1");
            object.put("sign", MD5.encrypt(data.toString()));
            object.put("version", "1.0");
            object.put("timestamp", System.currentTimeMillis());
            object.put("tradeNo", makeTradeNo());
            object.put("data", data);

            object.put("token", token);
            object.put("projectCode", projectCode);
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
