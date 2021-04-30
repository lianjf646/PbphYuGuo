package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/10 0010.
 * class note:更改手机号
 */

public class GetAppChangeCustomerPhoneRequest extends BaseRequest {
    private int customerId;
    private String customerPhone;
    private String smsCode;

    public GetAppChangeCustomerPhoneRequest(int customerId, String customerPhone, String smsCode) {
        this.customerId = customerId;
        this.customerPhone = customerPhone;
        this.smsCode = smsCode;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            if (customerId != -1) {
                object.put("customerId", customerId);
            }
            object.put("customerPhone", customerPhone);
            object.put("smsCode", smsCode);
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
