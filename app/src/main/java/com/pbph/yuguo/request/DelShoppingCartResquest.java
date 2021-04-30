package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class DelShoppingCartResquest extends BaseRequest {

    public String shoppingCartId;


    public DelShoppingCartResquest(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("shoppingCartId", shoppingCartId);
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
