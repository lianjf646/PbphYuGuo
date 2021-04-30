package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:
 */

public class StoreOrderListRequest extends BaseRequest {

    private int customerId;
    private int pageNum;
    private int pageSize;

    public StoreOrderListRequest(int customerId, int pageNum, int pageSize) {
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
