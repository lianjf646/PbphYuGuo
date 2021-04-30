package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:会员权益
 */

public class GetVipGoodsListRequest extends BaseRequest {
    private String receiverLng;
    private String receiverLat;

    public GetVipGoodsListRequest(String receiverLng, String receiverLat) {
        this.receiverLng = receiverLng;
        this.receiverLat = receiverLat;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("receiverLng",receiverLng);
            object.put("receiverLat",receiverLat);
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
