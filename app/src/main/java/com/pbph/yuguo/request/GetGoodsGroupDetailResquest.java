package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetGoodsGroupDetailResquest extends BaseRequest {

    private Integer goodsGroupActivityId;


    public GetGoodsGroupDetailResquest(Integer goodsGroupActivityId) {
        this.goodsGroupActivityId = goodsGroupActivityId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            if (goodsGroupActivityId != -1)
                object.put("goodsGroupActivityId", goodsGroupActivityId);
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
