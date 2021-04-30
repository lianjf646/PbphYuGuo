package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetJpushIdResquest extends BaseRequest {

    private String customerId;//
    private String jpushId;//
    private String clientType = "0";


    public GetJpushIdResquest(String customerId, String jpushId) {
        this.customerId = customerId;
        this.jpushId = jpushId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);

            object.put("jpushId", jpushId);

            object.put("clientType", clientType);
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
