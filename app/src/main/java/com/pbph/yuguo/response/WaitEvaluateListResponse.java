package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:待评价商品实体类
 */

public class WaitEvaluateListResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseResponse{

        private int totleSize;
        private List<WaitEvaluateListBean> waitEvaluateList;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<WaitEvaluateListBean> getWaitEvaluateList() {
            return waitEvaluateList;
        }

        public void setWaitEvaluateList(List<WaitEvaluateListBean> waitEvaluateList) {
            this.waitEvaluateList = waitEvaluateList;
        }

        public static class WaitEvaluateListBean extends BaseResponse{

            private int evaluateFlag;
            private int goodsId;
            private String goodsImg;
            private String goodsName;
            private String goodsNameSub;
            private int goodsNum;
            private String goodsPack;
            private double goodsSalePrice;
            private double memberPrice;
            private String goodsSpec;
            private int goodsWeight;
            private int orderId;
            private int shareFlag;
            private int storeGoodsId;
            private double subtotalPrice;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getEvaluateFlag() {
                return evaluateFlag;
            }

            public void setEvaluateFlag(int evaluateFlag) {
                this.evaluateFlag = evaluateFlag;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsNameSub() {
                return goodsNameSub;
            }

            public void setGoodsNameSub(String goodsNameSub) {
                this.goodsNameSub = goodsNameSub;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getGoodsPack() {
                return goodsPack;
            }

            public void setGoodsPack(String goodsPack) {
                this.goodsPack = goodsPack;
            }

            public double getGoodsSalePrice() {
                return goodsSalePrice;
            }

            public void setGoodsSalePrice(double goodsSalePrice) {
                this.goodsSalePrice = goodsSalePrice;
            }

            public String getGoodsSpec() {
                return goodsSpec;
            }

            public void setGoodsSpec(String goodsSpec) {
                this.goodsSpec = goodsSpec;
            }

            public int getGoodsWeight() {
                return goodsWeight;
            }

            public void setGoodsWeight(int goodsWeight) {
                this.goodsWeight = goodsWeight;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getShareFlag() {
                return shareFlag;
            }

            public void setShareFlag(int shareFlag) {
                this.shareFlag = shareFlag;
            }

            public int getStoreGoodsId() {
                return storeGoodsId;
            }

            public void setStoreGoodsId(int storeGoodsId) {
                this.storeGoodsId = storeGoodsId;
            }

            public double getSubtotalPrice() {
                return subtotalPrice;
            }

            public void setSubtotalPrice(double subtotalPrice) {
                this.subtotalPrice = subtotalPrice;
            }

            public double getMemberPrice() {
                return memberPrice;
            }

            public void setMemberPrice(double memberPrice) {
                this.memberPrice = memberPrice;
            }
        }
    }
}
