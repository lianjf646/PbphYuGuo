package com.pbph.yuguo.request;

import android.text.TextUtils;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:修改支付密码request
 */

public class GetAppChangeCustomerInfoRequest extends BaseRequest {

    private int customerId;
    private String customerImgUrl;
    private String customerNickName;
    private String customerRealName;
    private String customerBirthday;
    private String alipayAccount;
    private String wxNickName;
    private String publicOpenId;
    private int customerSex;


    public GetAppChangeCustomerInfoRequest(int customerId,
                                           String customerImgUrl,
                                           String customerNickName,
                                           String customerRealName,
                                           String customerBirthday,
                                           String alipayAccount,
                                           String wxNickName,
                                           String publicOpenId,
                                           int customerSex) {
        this.customerId = customerId;
        this.customerImgUrl = customerImgUrl;
        this.customerNickName = customerNickName;
        this.customerRealName = customerRealName;
        this.customerBirthday = customerBirthday;
        this.wxNickName = wxNickName;
        this.alipayAccount = alipayAccount;
        this.publicOpenId = publicOpenId;
        this.customerSex = customerSex;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            if (!TextUtils.isEmpty(customerImgUrl)) {
                object.put("customerImgUrl", customerImgUrl);
            }
            if (!TextUtils.isEmpty(customerNickName)) {
                object.put("customerNickName", customerNickName);
            }
            if (!TextUtils.isEmpty(customerRealName)) {
                object.put("customerRealName", customerRealName);
            }
            if (!TextUtils.isEmpty(customerBirthday)) {
                object.put("customerBirthday", customerBirthday);
            }
            if (!TextUtils.isEmpty(alipayAccount)) {
                object.put("alipayAccount", alipayAccount);
            }
            if (!TextUtils.isEmpty(wxNickName)) {
                object.put("wxNickName", wxNickName);
            }
            if (!TextUtils.isEmpty(publicOpenId)) {
                object.put("publicOpenId", publicOpenId);
            }
            if (customerSex != -1) {
                object.put("customerSex", customerSex);
            }
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
