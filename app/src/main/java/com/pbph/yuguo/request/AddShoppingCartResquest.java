package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class AddShoppingCartResquest extends BaseRequest {

    public int customerId;
    public int storeGoodsId;
    public int storeId;
    public int goodsNum;


    public int storeGoodsInfoId;//25,
    public int activeId;//6,
    public int activeType = -1;//1

    public AddShoppingCartResquest(int customerId,
                                   int storeGoodsId,
                                   int storeId,
                                   int goodsNum,
                                   int storeGoodsInfoId,
                                   int activeId,
                                   int activeType) {
        this.customerId = customerId;
        this.storeGoodsId = storeGoodsId;
        this.storeId = storeId;
        this.goodsNum = goodsNum;

        this.storeGoodsInfoId = storeGoodsInfoId;//25,
        this.activeId = activeId;//6,
        this.activeType = activeType;//1

    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
//            object.put("storeGoodsId", storeGoodsId);
            object.put("storeId", storeId);
            object.put("goodsNum", goodsNum);

            object.put("storeGoodsInfoId", storeGoodsInfoId);
            if (activeId != 0)
                object.put("activeId", activeId);
            if (activeType != -1)
                object.put("activeType", activeType);
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
