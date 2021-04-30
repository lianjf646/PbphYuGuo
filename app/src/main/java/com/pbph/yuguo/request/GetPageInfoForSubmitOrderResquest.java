package com.pbph.yuguo.request;

import android.text.TextUtils;

import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetPageInfoForSubmitOrderResquest extends BaseRequest {


    private int orderFillType = 0;

    private int storeGoodsInfoId;
    private int goodsInfoNum = 0;

    private String receiverLng = "";
    private String receiverLat = "";

    private Integer couponId;
    private int memberPrice = 0;

    private String receiverAddress = "";
    private String deliveryTypeFlag = "1";

    private String shoppingCartIds;

    public int getOrderFillType() {
        return orderFillType;
    }

    public void setOrderFillType(int orderFillType) {
        this.orderFillType = orderFillType;
    }

    public int getStoreGoodsInfoId() {
        return storeGoodsInfoId;
    }

    public void setStoreGoodsInfoId(int storeGoodsInfoId) {
        this.storeGoodsInfoId = storeGoodsInfoId;
    }

    public int getGoodsInfoNum() {
        return goodsInfoNum;
    }

    public void setGoodsInfoNum(int goodsInfoNum) {
        this.goodsInfoNum = goodsInfoNum;
    }

    public String getReceiverLng() {
        return receiverLng;
    }

    public void setReceiverLng(String receiverLng) {
        this.receiverLng = receiverLng;
    }

    public String getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(String receiverLat) {
        this.receiverLat = receiverLat;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }


    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public int getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(int memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getDeliveryTypeFlag() {
        return deliveryTypeFlag;
    }

    public void setDeliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
    }

    public String getShoppingCartIds() {
        return shoppingCartIds;
    }

    public void setShoppingCartIds(String shoppingCartIds) {
        this.shoppingCartIds = shoppingCartIds;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("customerId", YuGuoApplication.userInfo.getCustomerId());

            object.put("orderFillType", orderFillType);
            object.put("storeGoodsInfoId", storeGoodsInfoId);
            object.put("goodsInfoNum", goodsInfoNum);


            object.put("clientType", 1);
            object.put("receiverLng", receiverLng);
            object.put("receiverLat", receiverLat);
            if (couponId != null) object.put("couponId", couponId);

            object.put("memberPrice", memberPrice);

            object.put("receiverAddress", receiverAddress);
            object.put("deliveryTypeFlag", deliveryTypeFlag);

            if (!TextUtils.isEmpty(shoppingCartIds))
                object.put("shoppingCartIds", shoppingCartIds);

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
