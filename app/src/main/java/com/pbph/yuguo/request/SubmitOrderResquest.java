package com.pbph.yuguo.request;

import android.text.TextUtils;

import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SubmitOrderResquest extends BaseRequest {

    public int orderFillType;                       //	是	订单填写类型(1:商品进入;2:购物车进入)
    public Integer storeGoodsInfoId;                       //	是	门店商品id (orderFillType=1时,必填)
    public Integer goodsInfoNum;                   //	是	商品数量 (orderFillType=1时,必填)

    public String shoppingCartIds;


    public String receiverLng;                          //		地址经度
    public String receiverLat;                          //		地址纬度

    public int couponId;                   //		优惠券id(首次进入时，传空，会返回最优的优惠券;非首次，不选择优惠券，传 -1)

    public long memberPrice;                    //		会员开通价格(没选时，传0即可)

    public int receiverAddressId;                           //	是	收货地址id

    public int deliveryTypeFlag;                           //	是	配送方式（1 立即配送 0预约）
    public int storeId;                       //	是	门店id
    public long orderDealPrice;                            //	是	交易金额


    public int fullPresentGoodsCount = 0;
    public int fullXGoodsCount = 0;
    public int pinkageActivityId;


    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("customerId", YuGuoApplication.userInfo.getCustomerId());


            object.put("orderFillType", orderFillType);

            if (storeGoodsInfoId != null) object.put("storeGoodsInfoId", storeGoodsInfoId);
            if (goodsInfoNum != null) object.put("goodsInfoNum", goodsInfoNum);

            object.put("receiverLng", receiverLng);
            object.put("receiverLat", receiverLat);
            object.put("couponId", couponId);

            object.put("memberPrice", memberPrice);
            object.put("receiverAddressId", receiverAddressId);
            object.put("deliveryTypeFlag", deliveryTypeFlag);
            object.put("storeId", storeId);

            object.put("orderDealPrice", orderDealPrice);

            if (!TextUtils.isEmpty(shoppingCartIds))
                object.put("shoppingCartIds", shoppingCartIds);

            object.put("fullPresentGoodsCount", fullPresentGoodsCount);
            object.put("fullXGoodsCount", fullXGoodsCount);
            object.put("pinkageActivityId", pinkageActivityId);

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
