package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:
 */

public class GetWaitEvaluateListRequest extends BaseRequest {
    private int customerId;
    private int pageNum;
    private int pageSize;

    public GetWaitEvaluateListRequest(int customerId, int pageNum, int pageSize) {
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
