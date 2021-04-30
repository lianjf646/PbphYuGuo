package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class PayOrderResquest extends BaseRequest {

    //    orderId 复制	[int] 	是	订单id	展开
//    orderPayWay 	[int] 	是	支付方式(0:微信;1:支付宝;2余额支付)	展开
//    payType 	[string] 	是	支付类型(1:普通订单支付;2:团购支付;3会员支付;4储值支付)
    private String orderId;
    private int orderPayWay;
    private String payType;
    private String payPassword;


    public PayOrderResquest(String orderId, int orderPayWay, String payType, String payPassword) {
        this.orderId = orderId;
        this.orderPayWay = orderPayWay;
        this.payType = payType;
        this.payPassword = payPassword;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("orderId", orderId);
            object.put("orderPayWay", orderPayWay);
            object.put("payType", payType);
            object.put("payPassword", payPassword);
            object.put("orderScene", 0);//订单场景(0线上，1线下) 支付方式为1时 可不填
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
