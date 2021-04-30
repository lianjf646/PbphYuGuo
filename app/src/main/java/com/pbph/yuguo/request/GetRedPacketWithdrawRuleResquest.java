package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetRedPacketWithdrawRuleResquest extends BaseRequest {




    public GetRedPacketWithdrawRuleResquest( ) {

    }

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
