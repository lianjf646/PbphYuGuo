package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class UpdateShoppingCartResquest extends BaseRequest {

    public Integer shoppingCartId;
    public Integer goodsNum;
    public int isChecked;

    public UpdateShoppingCartResquest(Integer shoppingCartId, Integer goodsNum, int isChecked) {
        this.shoppingCartId = shoppingCartId;
        this.goodsNum = goodsNum;
        this.isChecked = isChecked;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("shoppingCartId", shoppingCartId);
            object.put("goodsNum", goodsNum);
            object.put("isChecked", isChecked);
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
