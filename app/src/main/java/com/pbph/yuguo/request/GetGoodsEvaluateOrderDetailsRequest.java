package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:
 */

public class GetGoodsEvaluateOrderDetailsRequest extends BaseRequest {
    private int orderId;
    private int customerId;
    private int goodsId;

    public GetGoodsEvaluateOrderDetailsRequest(int orderId, int customerId, int goodsId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.goodsId = goodsId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("orderId", orderId);
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
