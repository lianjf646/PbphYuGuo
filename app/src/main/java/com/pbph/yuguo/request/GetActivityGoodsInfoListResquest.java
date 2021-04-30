package com.pbph.yuguo.request;

import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetActivityGoodsInfoListResquest extends BaseRequest {

    public Integer acticeId;
    public Integer acticeType;
    public int isGift;
    public int storeId;

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("acticeId", acticeId);
            object.put("acticeType", acticeType);
            object.put("isGift", isGift);
            object.put("storeId", storeId);
            object.put("customerId", YuGuoApplication.userInfo.getCustomerId());
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
