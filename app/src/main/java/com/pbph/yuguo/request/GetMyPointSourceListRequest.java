package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取积分来源列表
 * Created by zyp on 2018/9/3 0003.
 * class note:
 */

public class GetMyPointSourceListRequest extends BaseRequest {
    private int customerId;
    private int pageNum;
    private int pageSize;


    public GetMyPointSourceListRequest(int customerId, int pageNum, int pageSize) {
        this.customerId = customerId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
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
