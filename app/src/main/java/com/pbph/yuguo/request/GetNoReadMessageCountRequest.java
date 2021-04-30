package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:取消订单
 */

public class GetNoReadMessageCountRequest extends BaseRequest {
    private int customerId;


    public GetNoReadMessageCountRequest(int customerId) {
        this.customerId = customerId;

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
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
