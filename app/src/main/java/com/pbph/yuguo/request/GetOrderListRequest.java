package com.pbph.yuguo.request;

import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.base.BaseRequest;
import com.pbph.yuguo.base.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/3 0003.
 * class note:
 */

public class GetOrderListRequest extends BaseRequest {
    private int customerId;
    private int pageNum;
    private int pageSize;
    private int orderStatus;

    public GetOrderListRequest(int customerId, int pageNum, int pageSize, int orderStatus) {
        this.customerId = customerId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderStatus = orderStatus;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
            if (orderStatus != 0) {
                object.put("orderStatus", orderStatus);
            }
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
