package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetInviteVipCouponListResquest extends BaseRequest {

    public Integer parentCustomerId;


    public GetInviteVipCouponListResquest(Integer parentCustomerId) {
        this.parentCustomerId = parentCustomerId;

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("parentCustomerId", parentCustomerId);
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
