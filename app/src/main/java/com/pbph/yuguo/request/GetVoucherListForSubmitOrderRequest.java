package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:
 */

public class GetVoucherListForSubmitOrderRequest extends BaseRequest {

    private int customerId;
    private int clientTyp;

    public GetVoucherListForSubmitOrderRequest(int customerId, int clientTyp) {
        this.customerId = customerId;
        this.clientTyp = clientTyp;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("clientType", clientTyp);
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
