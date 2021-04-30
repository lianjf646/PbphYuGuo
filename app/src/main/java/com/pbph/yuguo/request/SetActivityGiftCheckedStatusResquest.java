package com.pbph.yuguo.request;

import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SetActivityGiftCheckedStatusResquest extends BaseRequest {


    public int acticeId;
    public int storeId;
    public int goodsInfoId;
    public int checked;


    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", YuGuoApplication.userInfo.getCustomerId());

            object.put("acticeId", acticeId);
            object.put("storeId", storeId);
            object.put("goodsInfoId", goodsInfoId);
            object.put("checked", checked);
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
