package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:取消订单
 */

public class GetCancelOrderRequest extends BaseRequest {
    private int orderId;
    private String backOrderCause;

    public GetCancelOrderRequest(int orderId, String backOrderCause) {
        this.orderId = orderId;
        this.backOrderCause = backOrderCause;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("orderId", orderId);
            object.put("backOrderCause", backOrderCause);
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
