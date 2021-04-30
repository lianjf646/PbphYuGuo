package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/7 0007.
 * class note:评价
 */

public class GetGoodsStorageNumRequest extends BaseRequest {
    private int storeGoodsId;

    public GetGoodsStorageNumRequest(int storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("storeGoodsId", storeGoodsId);
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
