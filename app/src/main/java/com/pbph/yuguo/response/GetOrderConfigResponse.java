package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 *
 * Created by zyp on 2018/8/13 0013.
 */

public class GetOrderConfigResponse extends BaseResponse {


    /**
     * data : {"orderConfigDto":{"backOrderFlag":1,"backOrderRemark":"1223333","modifyAdminId":0,
     * "modifyTime":null,"orderAutoCancelTime":100,"orderAutoConfirmTime":1,
     * "orderAutoEvaluateTime":1,"orderConfigId":1,"refundRemark":"1233","totalWeight":1}}
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
         * orderConfigDto : {"backOrderFlag":1,"backOrderRemark":"1223333","modifyAdminId":0,
         * "modifyTime":null,"orderAutoCancelTime":100,"orderAutoConfirmTime":1,
         * "orderAutoEvaluateTime":1,"orderConfigId":1,"refundRemark":"1233","totalWeight":1}
         */

        private OrderConfigDtoBean orderConfigDto;

        public OrderConfigDtoBean getOrderConfigDto() {
            return orderConfigDto;
        }

        public void setOrderConfigDto(OrderConfigDtoBean orderConfigDto) {
            this.orderConfigDto = orderConfigDto;
        }

        public static class OrderConfigDtoBean {
            /**
             * backOrderFlag : 1
             * backOrderRemark : 1223333
             * modifyAdminId : 0
             * modifyTime : null
             * orderAutoCancelTime : 100
             * orderAutoConfirmTime : 1
             * orderAutoEvaluateTime : 1
             * orderConfigId : 1
             * refundRemark : 1233
             * totalWeight : 1
             */

            private int backOrderFlag;
            private String backOrderRemark;
            private int modifyAdminId;
            private Object modifyTime;
            private Integer orderAutoCancelTime;
            private int orderAutoConfirmTime;
            private int orderAutoEvaluateTime;
            private int orderConfigId;
            private String refundRemark;
            private int totalWeight;

            public int getBackOrderFlag() {
                return backOrderFlag;
            }

            public void setBackOrderFlag(int backOrderFlag) {
                this.backOrderFlag = backOrderFlag;
            }

            public String getBackOrderRemark() {
                return backOrderRemark;
            }

            public void setBackOrderRemark(String backOrderRemark) {
                this.backOrderRemark = backOrderRemark;
            }

            public int getModifyAdminId() {
                return modifyAdminId;
            }

            public void setModifyAdminId(int modifyAdminId) {
                this.modifyAdminId = modifyAdminId;
            }

            public Object getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(Object modifyTime) {
                this.modifyTime = modifyTime;
            }

            public Integer getOrderAutoCancelTime() {
                return orderAutoCancelTime;
            }

            public void setOrderAutoCancelTime(Integer orderAutoCancelTime) {
                this.orderAutoCancelTime = orderAutoCancelTime;
            }

            public int getOrderAutoConfirmTime() {
                return orderAutoConfirmTime;
            }

            public void setOrderAutoConfirmTime(int orderAutoConfirmTime) {
                this.orderAutoConfirmTime = orderAutoConfirmTime;
            }

            public int getOrderAutoEvaluateTime() {
                return orderAutoEvaluateTime;
            }

            public void setOrderAutoEvaluateTime(int orderAutoEvaluateTime) {
                this.orderAutoEvaluateTime = orderAutoEvaluateTime;
            }

            public int getOrderConfigId() {
                return orderConfigId;
            }

            public void setOrderConfigId(int orderConfigId) {
                this.orderConfigId = orderConfigId;
            }

            public String getRefundRemark() {
                return refundRemark;
            }

            public void setRefundRemark(String refundRemark) {
                this.refundRemark = refundRemark;
            }

            public int getTotalWeight() {
                return totalWeight;
            }

            public void setTotalWeight(int totalWeight) {
                this.totalWeight = totalWeight;
            }
        }
    }
}
