package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:取消订单
 */

public class CancelOrderResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int orderStatus;

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
