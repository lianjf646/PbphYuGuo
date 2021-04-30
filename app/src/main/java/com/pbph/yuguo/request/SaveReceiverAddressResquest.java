package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SaveReceiverAddressResquest extends BaseRequest {
    //	用户id
    String customerId;
    //	收货人姓名
    String receiverName;
    //  收货人手机号
    String receiverPhone;
    String receiverCity;
    //  详细地址
    String receiverAddressDetail;
    //	是否默认（0:非默认;1:默认）
    String defaultFlag;
    //   小区
    String receiverVillage;

    String receiverLat;
    String receiverLng ;

    public SaveReceiverAddressResquest(String customerId, String receiverName, String
            receiverPhone, String receiverCity,String receiverAddressDetail, String defaultFlag,
                                       String
            receiverLat, String receiverLng ,String receiverVillage) {
        this.customerId = customerId;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverCity = receiverCity;
        this.receiverAddressDetail = receiverAddressDetail;
        this.defaultFlag = defaultFlag;
        this.receiverLat = receiverLat;
        this.receiverLng = receiverLng;
        this.receiverVillage = receiverVillage;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("receiverName", receiverName);
            object.put("receiverPhone", receiverPhone);
//            object.put("receiverCity",receiverCity);
            object.put("receiverAddressDetail", receiverAddressDetail);
            object.put("defaultFlag", defaultFlag);
            object.put("receiverLat", receiverLat);
            object.put("receiverLng", receiverLng);
            object.put("receiverVillage", receiverVillage);
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
