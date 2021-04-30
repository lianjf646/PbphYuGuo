package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:修改支付密码request
 */

public class GetAppChangeCustomerPayPasswordRequest extends BaseRequest {

    private int customerId;
    private String payPassword;


    public GetAppChangeCustomerPayPasswordRequest(int customerId, String payPassword) {
        this.customerId = customerId;
        this.payPassword = payPassword;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("payPassword", payPassword);
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
