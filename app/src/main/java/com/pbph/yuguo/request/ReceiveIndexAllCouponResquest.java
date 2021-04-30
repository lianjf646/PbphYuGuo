package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ReceiveIndexAllCouponResquest extends BaseRequest {

    public int customerId;

    public int memberType = 1;

    public ReceiveIndexAllCouponResquest(int customerId, int memberType) {
        this.customerId = customerId;
        this.memberType = memberType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("memberType", memberType);
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
