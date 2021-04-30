package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:
 */

public class GetMemberOpenPriceRequest extends BaseRequest {

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        return object;
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
