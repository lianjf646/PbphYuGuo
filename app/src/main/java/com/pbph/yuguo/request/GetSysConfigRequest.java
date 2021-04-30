package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:售后规则
 */

public class GetSysConfigRequest extends BaseRequest {

    @Override
    public JSONObject toJson() {
        return new JSONObject();
    }


    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
