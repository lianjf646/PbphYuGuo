package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetGoodsListResquest extends BaseRequest {

    public int goodsCateId = 1;
    String receiverLat;
    String receiverLng;

    String goodsName;


    public GetGoodsListResquest(String receiverLat,
                                String receiverLng) {
        this.receiverLat = receiverLat;
        this.receiverLng = receiverLng;
    }

    public GetGoodsListResquest(String receiverLat,
                                String receiverLng, String goodsName) {
        this.receiverLat = receiverLat;
        this.receiverLng = receiverLng;
        this.goodsName = goodsName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
//            object.put("clientType", "1");
            object.put("receiverLat", receiverLat);
            object.put("receiverLng", receiverLng);
            object.put("goodsCateId", goodsCateId);

            object.put("goodsName", goodsName);


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
