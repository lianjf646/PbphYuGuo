package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2018/9/7 0007.
 * class note:评价
 */

public class GetEvaluateOrderRequest extends BaseRequest {
    private int evaluateId;
    private int orderId;
    private int customerId;
    private int anonymousFlag;
    private int goodsId;
    private int storeGoodsId;
    private String evaluateContent;
    private String shareImgUrls;

    public GetEvaluateOrderRequest(int orderId, int evaluateId, int customerId, int anonymousFlag, int goodsId, int storeGoodsId, String evaluateContent, String shareImgUrls) {
        this.evaluateId = evaluateId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.anonymousFlag = anonymousFlag;
        this.goodsId = goodsId;
        this.storeGoodsId = storeGoodsId;
        this.evaluateContent = evaluateContent;
        this.shareImgUrls = shareImgUrls;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            if (evaluateId != -1) {
                object.put("evaluateId", evaluateId);
            }
            if (orderId != -1) {
                object.put("orderId", orderId);
            }
            if (customerId != -1) {
                object.put("customerId", customerId);
            }
            if (goodsId != -1) {
                object.put("goodsId", goodsId);
            }
            if (storeGoodsId != -1) {
                object.put("storeGoodsId", storeGoodsId);
            }
            object.put("anonymousFlag", anonymousFlag);
            object.put("evaluateContent", evaluateContent);
            object.put("shareImgUrls", shareImgUrls);

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
