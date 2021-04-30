package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetGoodsDetailResquest extends BaseRequest {

    private Integer customerId;

    private Integer storeGoodsId;

    private Integer storeGoodsInfoId;


    public GetGoodsDetailResquest(Integer customerId, Integer storeGoodsId, Integer storeGoodsInfoId) {
        this.customerId = customerId;
        this.storeGoodsId = storeGoodsId;
        this.storeGoodsInfoId = storeGoodsInfoId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            if (customerId != -1)
                object.put("customerId", customerId);
            if (storeGoodsId != null)
                object.put("storeGoodsId", storeGoodsId);

            if (storeGoodsInfoId != null)
                object.put("storeGoodsInfoId", storeGoodsInfoId);

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
