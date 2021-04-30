package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetReceiverAddressListResquest extends BaseRequest {
    //	用户id
    Integer customerId;
    int type; // 1所有地址2默认
    int pageNum;
    int pageSize;


    public GetReceiverAddressListResquest(Integer customerId, int type, int pageNum, int pageSize) {
        this.customerId = customerId;
        this.type = type;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);

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
