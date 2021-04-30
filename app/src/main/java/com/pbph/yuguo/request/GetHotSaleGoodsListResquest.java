package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetHotSaleGoodsListResquest extends BaseRequest {

    public int clientType = 1;

    String receiverLat;
    String receiverLng;

    public GetHotSaleGoodsListResquest(String receiverLat, String receiverLng) {
        this.receiverLat = receiverLat;
        this.receiverLng = receiverLng;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("receiverLat", receiverLat);
            object.put("receiverLng", receiverLng);
            object.put("clientType", clientType);
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
