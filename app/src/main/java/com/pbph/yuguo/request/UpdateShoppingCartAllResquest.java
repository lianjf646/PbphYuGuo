package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class UpdateShoppingCartAllResquest extends BaseRequest {


    public int isChecked;//是否勾选(0未勾选 1:勾选)
    public String shoppingCartId;


    public UpdateShoppingCartAllResquest(int isChecked, String shoppingCartId) {
        this.isChecked = isChecked;
        this.shoppingCartId = shoppingCartId;

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("isChecked", isChecked);
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
