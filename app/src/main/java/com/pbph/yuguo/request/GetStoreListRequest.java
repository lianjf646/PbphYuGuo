package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetStoreListRequest extends BaseRequest {



    public GetStoreListRequest() {

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
//        try {
////            object.put("customerId", customerId);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return object;
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
