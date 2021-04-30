package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetMyInvitationListListResquest extends BaseRequest {

    public Integer parentCustomerId;

    public Integer pageNum;
    public Integer pageSize;


    public GetMyInvitationListListResquest(Integer parentCustomerId, Integer pageNum, Integer pageSize) {
        this.parentCustomerId = parentCustomerId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("parentCustomerId", parentCustomerId);
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
