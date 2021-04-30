package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetUserInviteNumberListResponse extends BaseResponse {

    /**
     * data : {"userInviteNumberList":[{"customerNickName":"","customerNum":11},{"customerNickName":"测试2昵称333333","customerNum":2}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<UserInviteNumberListBean> userInviteNumberList;

        public List<UserInviteNumberListBean> getUserInviteNumberList() {
            return userInviteNumberList;
        }

        public void setUserInviteNumberList(List<UserInviteNumberListBean> userInviteNumberList) {
            this.userInviteNumberList = userInviteNumberList;
        }

        public static class UserInviteNumberListBean {
            /**
             * customerNickName :
             * customerNum : 11
             */

            private String customerNickName;
            private int customerNum;

            public String getCustomerNickName() {
                return customerNickName;
            }

            public void setCustomerNickName(String customerNickName) {
                this.customerNickName = customerNickName;
            }

            public int getCustomerNum() {
                return customerNum;
            }

            public void setCustomerNum(int customerNum) {
                this.customerNum = customerNum;
            }
        }
    }
}
