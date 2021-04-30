package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetIndexRotationChartRequest extends BaseRequest {

    public GetIndexRotationChartRequest() {

    }

    @Override
    public JSONObject toJson() {

        return  new JSONObject();
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
