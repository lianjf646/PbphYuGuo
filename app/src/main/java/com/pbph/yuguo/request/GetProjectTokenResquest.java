package com.pbph.yuguo.request;

import com.pbph.yuguo.base.SCMRequest;
import com.pbph.yuguo.constant.ConstantData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetProjectTokenResquest extends SCMRequest {

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("projectAppId", ConstantData.ALIOSS_PROJECT_APP_ID);
            object.put("projectAppSecret", ConstantData.ALIOSS_PROJECT_APP_SECRET);

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
