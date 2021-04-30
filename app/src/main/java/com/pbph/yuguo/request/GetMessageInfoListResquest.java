package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetMessageInfoListResquest extends BaseRequest {

    public int customerId;
    public int pageNum;
    public int pageSize;
    public int messageType;


    public GetMessageInfoListResquest(int customerId,
                                      int pageNum,
                                      int pageSize,
                                      int messageType) {
        this.customerId = customerId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.messageType = messageType;


    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
            object.put("messageType",messageType);

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
