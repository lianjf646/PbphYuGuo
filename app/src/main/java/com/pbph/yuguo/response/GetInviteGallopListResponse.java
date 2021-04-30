package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetInviteGallopListResponse extends BaseResponse {

    /**
     * data : {"inviteGallopList":[{"customerNum":12,"customerPhone":"15846570232","sumMaidProfit":0},{"customerNum":11,"customerPhone":"18645027760","sumMaidProfit":10000},{"customerNum":2,"customerPhone":"18845451212","sumMaidProfit":1000}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<InviteGallopListBean> inviteGallopList;

        public List<InviteGallopListBean> getInviteGallopList() {
            return inviteGallopList;
        }

        public void setInviteGallopList(List<InviteGallopListBean> inviteGallopList) {
            this.inviteGallopList = inviteGallopList;
        }

        public static class InviteGallopListBean {
            /**
             * customerNum : 12
             * customerPhone : 15846570232
             * sumMaidProfit : 0
             */

            private int customerNum;
            private String customerPhone;
            private int sumMaidProfit;

            public int getCustomerNum() {
                return customerNum;
            }

            public void setCustomerNum(int customerNum) {
                this.customerNum = customerNum;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public int getSumMaidProfit() {
                return sumMaidProfit;
            }

            public void setSumMaidProfit(int sumMaidProfit) {
                this.sumMaidProfit = sumMaidProfit;
            }
        }
    }
}
