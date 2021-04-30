package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class AppChangestoredMoneyResquest extends BaseRequest {

    //    customerId 复制	[int] 	是	用户id
//    rechargePrice 	[long] 	是	交易金额(会员金额)
//    dealWay 	[int] 	是	交易方式(0微信，1支付宝，2钱包支付)
    private int customerId;
    private long rechargePrice;
    private int dealWay;


    public AppChangestoredMoneyResquest(int customerId, long rechargePrice, int dealWay) {
        this.customerId = customerId;
        this.rechargePrice = rechargePrice;
        this.dealWay = dealWay;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("rechargePrice", rechargePrice);
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
