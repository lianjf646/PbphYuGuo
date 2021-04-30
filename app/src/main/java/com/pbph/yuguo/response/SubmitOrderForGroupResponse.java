package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;


public class SubmitOrderForGroupResponse extends BaseResponse {

    /**
     * data : {"customerGroupId":1}
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
         * customerGroupId : 1
         */

        private int customerGroupId;

        public int getCustomerGroupId() {
            return customerGroupId;
        }

        public void setCustomerGroupId(int customerGroupId) {
            this.customerGroupId = customerGroupId;
        }
    }
}
