package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:
 */

public class GetUsableCouponListForGoodsRequest extends BaseRequest {
    private int goodsId;
    private int customerId;
    private int clientType;

    public GetUsableCouponListForGoodsRequest(int goodsId, int customerId, int clientType) {
        this.goodsId = goodsId;
        this.customerId = customerId;
        this.clientType = clientType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("clientType", clientType);
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
