package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class LoginResquest extends BaseRequest {

    String customerPhone = "";
    String smsCode = "";

    public LoginResquest(String customerPhone, String smsCode) {
        this.customerPhone = customerPhone;
        this.smsCode = smsCode;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerPhone", customerPhone);
            object.put("smsCode", smsCode);
            object.put("customerId", "");
            object.put("clientType", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
