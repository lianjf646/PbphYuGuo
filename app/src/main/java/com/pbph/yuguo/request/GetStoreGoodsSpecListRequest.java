package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 挡风的纱窗 on 2019/1/8.
 */
public class GetStoreGoodsSpecListRequest extends BaseRequest {

    Integer storeGoodsInfoId;
    Integer storeGoodsId;
    String specDetailIdStr;

    public GetStoreGoodsSpecListRequest(Integer storeGoodsInfoId, Integer storeGoodsId, String specDetailIdStr) {
        this.storeGoodsInfoId = storeGoodsInfoId;
        this.storeGoodsId = storeGoodsId;
        this.specDetailIdStr = specDetailIdStr;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("storeGoodsId", storeGoodsId);
            object.put("storeGoodsInfoId", storeGoodsInfoId);
            object.put("specDetailIdStr", specDetailIdStr);
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
