package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetMemberDayActivityGoodsRequest extends BaseRequest {

    private int memberDayActivityId;
    private int storeId;
    private int pageNum;
    private int pageSize;

    public GetMemberDayActivityGoodsRequest(int memberDayActivityId, int storeId, int pageNum, int pageSize) {
        this.memberDayActivityId = memberDayActivityId;
        this.storeId = storeId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("memberDayActivityId", memberDayActivityId);
            object.put("storeId",storeId);
            object.put("pageNum", pageNum);
            object.put("pageSize",pageSize);
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
