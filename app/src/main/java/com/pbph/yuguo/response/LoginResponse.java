package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;


public class LoginResponse extends BaseResponse{


    /**
     * data : {"customerId":30,"token":"72FF7580612BA3559BFBF5452450B17F63E773310AD6053BB12A37A5"}
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
         * userId : 30
         * token : 72FF7580612BA3559BFBF5452450B17F63E773310AD6053BB12A37A5
         */

        private int customerId;
        private String token;

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
