package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;


public class SubmitOrderResponse extends BaseResponse {


    /**
     * data : {"orderCode":"20181234567897546","orderStatus":2,"orderId":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderCode : 20181234567897546
         * orderStatus : 2
         * orderId : 1
         */

        private String orderCode;
        private int orderStatus;
        private int orderId;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
