package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetGoodsGroupActivityListResquest extends BaseRequest {

    public Integer clientType=1;

    public Integer pageNum;
    public Integer pageSize;

    public GetGoodsGroupActivityListResquest(Integer pageNum, Integer pageSize) {

        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("clientType", clientType);
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
