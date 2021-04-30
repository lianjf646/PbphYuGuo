package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/5/23.
 * class note:再来一单
 */

public class OrderAgainRequest extends BaseRequest {

    private int customerId;//	是	用户id
    private int orderId;


    public OrderAgainRequest(int customerId, int orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("orderId", orderId);
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
