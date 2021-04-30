package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:领取优惠券request
 */

public class GetReceiveCouponRequest extends BaseRequest {

    private int customerId;
    private int couponActivityId;
    private int goodsId;


    public GetReceiveCouponRequest(int customerId,
                                   int couponActivityId,
                                   int goodsId) {
        this.customerId = customerId;
        this.couponActivityId = couponActivityId;
        this.goodsId = goodsId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("couponActivityId", couponActivityId);
            object.put("goodsId", goodsId);
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
