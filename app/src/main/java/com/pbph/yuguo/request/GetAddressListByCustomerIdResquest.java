package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetAddressListByCustomerIdResquest extends BaseRequest {

    private String customerId;//	是	用户id
    private String type;//	是	1所有地址2默认
    private String pageSize = "0";//	是	每页数量
    private String pageNum = "50";//	是	当前页码


    public GetAddressListByCustomerIdResquest(String customerId, String type) {
        this.customerId = customerId;
        this.type = type;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);

            object.put("type", type);

            object.put("pageSize", pageSize);
            object.put("pageNum", pageNum);
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
