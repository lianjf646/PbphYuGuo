package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/5/23.
 * class note:获取二维码
 */

public class QrOrBarCodeRequest extends BaseRequest {

    private int customerId;//	是	用户id
    private String payPassword;


    public QrOrBarCodeRequest(int customerId, String payPassword) {
        this.customerId = customerId;
        this.payPassword = payPassword;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("payPassword", payPassword);
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
