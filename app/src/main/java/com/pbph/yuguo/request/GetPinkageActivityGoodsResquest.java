package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetPinkageActivityGoodsResquest extends BaseRequest {

    private Integer pinkageActivityId;

    private Integer storeId;


    public GetPinkageActivityGoodsResquest(Integer pinkageActivityId, Integer storeId) {
        this.pinkageActivityId = pinkageActivityId;
        this.storeId = storeId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("pinkageActivityId", pinkageActivityId);
            object.put("storeId", storeId);
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
