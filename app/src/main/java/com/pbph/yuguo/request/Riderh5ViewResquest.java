package com.pbph.yuguo.request;

import com.pbph.yuguo.base.SCMRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Riderh5ViewResquest extends SCMRequest {

    public String orderId;
    public int orderType;


    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", orderId);
            object.put("order_type", orderType);
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
