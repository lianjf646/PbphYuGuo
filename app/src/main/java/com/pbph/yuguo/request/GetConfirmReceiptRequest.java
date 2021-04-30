package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/14 0014.
 * class note:确认收货
 */

public class GetConfirmReceiptRequest extends BaseRequest {
    private int orderId;
    private int customerId;

    public GetConfirmReceiptRequest(int orderId,
                                    int customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("orderId", orderId);
            object.put("customerId", customerId);
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
