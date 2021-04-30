package com.pbph.yuguo.request;

import com.pbph.yuguo.base.SCMRequest;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class AlipayauthsignResquest extends SCMRequest {
 
    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
