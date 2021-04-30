package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;


public class GetAuthenticationResponse extends BaseResponse {

    /**
     * data : {"authentication":{"adminId":2,"adminName":"","applyTime":1547695200000,"authId":17,"authReuslt":0,"authStatus":1,"customerId":43,"customerName":"","customerPhone":"","customerRealName":"宁蝶","idCardNo":"371482199011130313","idCardUrl":"","modifyTime":1547696260000,"rejectReason":"asdasdasdqweqwe112233"}}
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
         * authentication : {"adminId":2,"adminName":"","applyTime":1547695200000,"authId":17,"authReuslt":0,"authStatus":1,"customerId":43,"customerName":"","customerPhone":"","customerRealName":"宁蝶","idCardNo":"371482199011130313","idCardUrl":"","modifyTime":1547696260000,"rejectReason":"asdasdasdqweqwe112233"}
         */

        private AuthenticationBean authentication;

        public AuthenticationBean getAuthentication() {
            return authentication;
        }

        public void setAuthentication(AuthenticationBean authentication) {
            this.authentication = authentication;
        }

        public static class AuthenticationBean {
            /**
             * adminId : 2
             * adminName :
             * applyTime : 1547695200000
             * authId : 17
             * authReuslt : 0
             * authStatus : 1
             * customerId : 43
             * customerName :
             * customerPhone :
             * customerRealName : 宁蝶
             * idCardNo : 371482199011130313
             * idCardUrl :
             * modifyTime : 1547696260000
             * rejectReason : asdasdasdqweqwe112233
             */

            private int adminId;
            private String adminName;
            private long applyTime;
            private int authId;
            private int authReuslt;
            private int authStatus;
            private int customerId;
            private String customerName;
            private String customerPhone;
            private String customerRealName;
            private String idCardNo;
            private String idCardUrl;
            private long modifyTime;
            private String rejectReason;

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public String getAdminName() {
                return adminName;
            }

            public void setAdminName(String adminName) {
                this.adminName = adminName;
            }

            public long getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(long applyTime) {
                this.applyTime = applyTime;
            }

            public int getAuthId() {
                return authId;
            }

            public void setAuthId(int authId) {
                this.authId = authId;
            }

            public int getAuthReuslt() {
                return authReuslt;
            }

            public void setAuthReuslt(int authReuslt) {
                this.authReuslt = authReuslt;
            }

            public int getAuthStatus() {
                return authStatus;
            }

            public void setAuthStatus(int authStatus) {
                this.authStatus = authStatus;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public String getCustomerRealName() {
                return customerRealName;
            }

            public void setCustomerRealName(String customerRealName) {
                this.customerRealName = customerRealName;
            }

            public String getIdCardNo() {
                return idCardNo;
            }

            public void setIdCardNo(String idCardNo) {
                this.idCardNo = idCardNo;
            }

            public String getIdCardUrl() {
                return idCardUrl;
            }

            public void setIdCardUrl(String idCardUrl) {
                this.idCardUrl = idCardUrl;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getRejectReason() {
                return rejectReason;
            }

            public void setRejectReason(String rejectReason) {
                this.rejectReason = rejectReason;
            }
        }
    }
}
