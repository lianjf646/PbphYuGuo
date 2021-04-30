package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/10 0010.
 * class note:商品列表
 */

public class GetGoodsEvaluateListRequest extends BaseRequest {
    private int storeGoodsId;
    private int pageNum;
    private int pageSize;

    public GetGoodsEvaluateListRequest(int storeGoodsId, int pageNum, int pageSize) {
        this.storeGoodsId = storeGoodsId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("storeGoodsId", storeGoodsId);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
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
