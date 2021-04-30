package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;
import com.pbph.yuguo.response.GoodsInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zyp on 2018/9/5 0005.
 * class note:
 */

public class GetCouponListForSubmitOrderRequest extends BaseRequest {
    private int customerId;
    List<GoodsInfoBean> storeGoodsListBeans;
    private long goodsSumPrice;
    private int clientType;


    public GetCouponListForSubmitOrderRequest(int customerId, List<GoodsInfoBean> storeGoodsListBeans, long goodsSumPrice, int clientType) {
        this.customerId = customerId;
        this.storeGoodsListBeans = storeGoodsListBeans;
        this.goodsSumPrice = goodsSumPrice;
        this.clientType = clientType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (GoodsInfoBean vo : storeGoodsListBeans) {
                JSONObject jsonObjectItem = new JSONObject();

                jsonObjectItem.put("goodsId", vo.getGoodsId());
                jsonObjectItem.put("goodsInfoMemberPrice", vo.getGoodsInfoMemberPrice());
                jsonObjectItem.put("goodsInfoNum", vo.getGoodsInfoNum());
                if (vo.getAveragePrice() != 0) {
                    jsonObjectItem.put("averagePrice", vo.getAveragePrice());
                }

                jsonArray.put(jsonObjectItem);
            }
            object.put("customerId", customerId);
            object.put("goodsList", jsonArray);
            object.put("goodsSumPrice", goodsSumPrice);
            object.put("clientType", clientType);
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
