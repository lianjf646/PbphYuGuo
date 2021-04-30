package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetJuniorUserMoneyListResponse extends BaseResponse {

    /**
     * data : {"userInviteYieldList":[{"customerNickName":"测试3昵称","customerPhone":"18845451212","distributionMoney":10000},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":60000},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":85200},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试5昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试2昵称333333","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测4昵称","customerPhone":"18845451212","distributionMoney":100},{"customerNickName":"","customerPhone":"18346170179","distributionMoney":0},{"customerNickName":"测试2昵称333333","customerPhone":"18845451212","distributionMoney":0}],"totleSize":11}
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
         * userInviteYieldList : [{"customerNickName":"测试3昵称","customerPhone":"18845451212","distributionMoney":10000},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":60000},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":85200},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试2昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试5昵称","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测试2昵称333333","customerPhone":"18845451212","distributionMoney":0},{"customerNickName":"测4昵称","customerPhone":"18845451212","distributionMoney":100},{"customerNickName":"","customerPhone":"18346170179","distributionMoney":0},{"customerNickName":"测试2昵称333333","customerPhone":"18845451212","distributionMoney":0}]
         * totleSize : 11
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
             * customerNickName : 测试3昵称
             * customerPhone : 18845451212
             * distributionMoney : 10000
             */

            private String customerNickName;
            private String customerPhone;
            private int distributionMoney;

            public String getCustomerNickName() {
                return customerNickName;
            }

            public void setCustomerNickName(String customerNickName) {
                this.customerNickName = customerNickName;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public int getDistributionMoney() {
                return distributionMoney;
            }

            public void setDistributionMoney(int distributionMoney) {
                this.distributionMoney = distributionMoney;
            }
        }
    }
}
