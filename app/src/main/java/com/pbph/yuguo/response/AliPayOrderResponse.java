package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 *
 * Created by Administrator on 2018/8/6 0006.
 */

public class AliPayOrderResponse extends BaseResponse {


    /**
     * data : {"orderStatus":2,"payInfo":""}
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
         * orderStatus : 2
         * payInfo :
         */

        private int orderStatus;
        private String payInfo;

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(String payInfo) {
            this.payInfo = payInfo;
        }
    }
}
