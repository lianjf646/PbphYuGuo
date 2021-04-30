package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SendRedResquest extends BaseRequest {
    //	用户id
    Integer customerId ;
    String dealPrice;
    Integer dealWay;


    public SendRedResquest(Integer customerId , String dealPrice, Integer dealWay) {
        this.customerId  = customerId ;
        this.dealPrice = dealPrice;
        this.dealWay = dealWay;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("dealPrice", Long.valueOf(dealPrice));
            object.put("dealWay", dealWay);

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
