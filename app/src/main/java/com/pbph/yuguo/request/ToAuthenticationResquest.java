package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ToAuthenticationResquest extends BaseRequest {

    public Integer customerId;
    public String imageUrl;
    public String realName;
    public String card;


    public ToAuthenticationResquest(Integer customerId,String imageUrl,String realName,String card) {
        this.customerId = customerId;
        this.imageUrl = imageUrl;
        this.realName = realName;
        this.card = card;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("realName", realName);
            object.put("idCardNo", card);
            object.put("idCardUrl", imageUrl);

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
