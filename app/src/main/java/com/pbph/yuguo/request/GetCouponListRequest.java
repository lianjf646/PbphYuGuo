package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:
 */

public class GetCouponListRequest extends BaseRequest {

    private int customerId;
    private int couponStatus;
    private int pageNum;
    private int pageSize;

    public GetCouponListRequest(int customerId, int couponStatus, int pageNum, int pageSize) {
        this.customerId = customerId;
        this.couponStatus = couponStatus;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("couponStatus", couponStatus);
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
