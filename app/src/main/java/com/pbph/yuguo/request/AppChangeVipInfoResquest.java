package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class AppChangeVipInfoResquest extends BaseRequest {

    private int customerId;         //customerId 复制	[int] 	是	用户id
    private long rechargePrice;     //rechargePrice 	[long] 	是	交易金额(会员金额)
    private int dealWay;            //dealWay 	[int] 	是	交易方式(0微信，1支付宝，2钱包支付)
    private int timeUnit;           //timeUnit 	[number] 	是	充值时长（月）


    public AppChangeVipInfoResquest(int customerId, long rechargePrice, int dealWay, int timeUnit) {
        this.customerId = customerId;
        this.rechargePrice = rechargePrice;
        this.dealWay = dealWay;
        this.timeUnit = timeUnit;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("rechargePrice", rechargePrice);
            object.put("dealWay", dealWay);
            object.put("timeUnit", timeUnit);
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
