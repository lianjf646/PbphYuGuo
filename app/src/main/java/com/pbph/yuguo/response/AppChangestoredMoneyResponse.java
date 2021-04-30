package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 *
 * Created by Administrator on 2018/8/6 0006.
 */

public class AppChangestoredMoneyResponse extends BaseResponse {


    /**
     * data : {"orderCode":"D235458949864828928","payFlag":0,"rechargeId":124}
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
         * orderCode : D235458949864828928
         * payFlag : 0
         * rechargeId : 124
         */

        private String orderCode;
        private int payFlag;
        private String rechargeId;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getPayFlag() {
            return payFlag;
        }

        public void setPayFlag(int payFlag) {
            this.payFlag = payFlag;
        }

        public String getRechargeId() {
            return rechargeId;
        }

        public void setRechargeId(String rechargeId) {
            this.rechargeId = rechargeId;
        }
    }
}
