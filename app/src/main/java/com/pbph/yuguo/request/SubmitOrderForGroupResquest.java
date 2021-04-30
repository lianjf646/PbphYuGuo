package com.pbph.yuguo.request;

import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SubmitOrderForGroupResquest extends BaseRequest {

    public String receiverAddressId;
    public int goodsGroupNum;
    public int goodsGroupPrice;
    public int goodsGroupActivityId;

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("customerId", YuGuoApplication.userInfo.getCustomerId());

            object.put("receiverAddressId", receiverAddressId);
            object.put("goodsGroupNum", goodsGroupNum);
            object.put("goodsGroupPrice", goodsGroupPrice);

            object.put("goodsGroupActivityId", goodsGroupActivityId);

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
