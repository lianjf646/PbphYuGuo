package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;


public class GetMyInvitationListResponse extends BaseResponse {


    /**
     * data : {"userInviteYieldList":[{"customerImgUrl":"","customerNickName":"1111"},{"customerImgUrl":"http://rqbucket.scrqgs.com/rqtestfold/20/EE43609F21C0BBDB36E907678BDDF82B.jpg","customerNickName":"裴广庭"}],"totleSize":2}
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
         * userInviteYieldList : [{"customerImgUrl":"","customerNickName":"1111"},{"customerImgUrl":"http://rqbucket.scrqgs.com/rqtestfold/20/EE43609F21C0BBDB36E907678BDDF82B.jpg","customerNickName":"裴广庭"}]
         * totleSize : 2
         */

        private int totleSize;
        private List<UserInviteYieldListBean> userInviteYieldList;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<UserInviteYieldListBean> getUserInviteYieldList() {
            return userInviteYieldList;
        }

        public void setUserInviteYieldList(List<UserInviteYieldListBean> userInviteYieldList) {
            this.userInviteYieldList = userInviteYieldList;
        }

        public static class UserInviteYieldListBean {
            /**
             * customerImgUrl :
             * customerNickName : 1111
             */

            private String customerImgUrl;
            private String customerNickName;
            private String customerPhone;

            public String getCustomerImgUrl() {
                return customerImgUrl;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public void setCustomerImgUrl(String customerImgUrl) {
                this.customerImgUrl = customerImgUrl;
            }

            public String getCustomerNickName() {
                return customerNickName;
            }

            public void setCustomerNickName(String customerNickName) {
                this.customerNickName = customerNickName;
            }
        }
    }
}
