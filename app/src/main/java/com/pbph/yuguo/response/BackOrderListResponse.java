package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/11 0011.
 * class note:退单实体类
 */

public class BackOrderListResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int totleSize;
        private List<OrderListBean> list;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<OrderListBean> getList() {
            return list;
        }

        public void setList(List<OrderListBean> list) {
            this.list = list;
        }

        public static class OrderListBean {

            private int backOrderStatus;
            private int backOrderId;
            private String createTime;
            private int evaluateStatus;
            private int goodsNum;
            private double orderDealPrice;
            private int orderId;
            private int orderStatus;
            private int shareStatus;
            private List<GoodsInfoBean> orderGoodsInfoList;

            public int getBackOrderStatus() {
                return backOrderStatus;
            }

            public void setBackOrderStatus(int backOrderStatus) {
                this.backOrderStatus = backOrderStatus;
            }

            public int getBackOrderId() {
                return backOrderId;
            }

            public void setBackOrderId(int backOrderId) {
                this.backOrderId = backOrderId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getEvaluateStatus() {
                return evaluateStatus;
            }

            public void setEvaluateStatus(int evaluateStatus) {
                this.evaluateStatus = evaluateStatus;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }

            public double getOrderDealPrice() {
                return orderDealPrice;
            }

            public void setOrderDealPrice(double orderDealPrice) {
                this.orderDealPrice = orderDealPrice;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getShareStatus() {
                return shareStatus;
            }

            public void setShareStatus(int shareStatus) {
                this.shareStatus = shareStatus;
            }

            public List<GoodsInfoBean> getOrderGoodsInfoList() {
                return orderGoodsInfoList;
            }

            public void setOrderGoodsInfoList(List<GoodsInfoBean> orderGoodsInfoList) {
                this.orderGoodsInfoList = orderGoodsInfoList;
            }
        }
    }
}
