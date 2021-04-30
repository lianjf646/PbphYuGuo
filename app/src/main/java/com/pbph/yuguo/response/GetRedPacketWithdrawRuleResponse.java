package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetRedPacketWithdrawRuleResponse extends BaseResponse {

    /**
     * data : {"withdrawConfigDto":{"alipayFlag":1,"defaultWay":1,"openFlag":1,"serviceCharge":1,"wechatFlag":1,"withdrawCount":1,"withdrawLimit":200,"withdrawType":"10,30,50,100,200"}}
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
         * withdrawConfigDto : {"alipayFlag":1,"defaultWay":1,"openFlag":1,"serviceCharge":1,"wechatFlag":1,"withdrawCount":1,"withdrawLimit":200,"withdrawType":"10,30,50,100,200"}
         */

        private WithdrawConfigDtoBean withdrawConfigDto;


        public WithdrawConfigDtoBean getWithdrawConfigDto() {
            return withdrawConfigDto;
        }

        public void setWithdrawConfigDto(WithdrawConfigDtoBean withdrawConfigDto) {
            this.withdrawConfigDto = withdrawConfigDto;
        }

        public static class WithdrawConfigDtoBean {
            /**
             * alipayFlag : 1
             * defaultWay : 1
             * openFlag : 1
             * serviceCharge : 1
             * wechatFlag : 1
             * withdrawCount : 1
             * withdrawLimit : 200
             * withdrawType : 10,30,50,100,200
             */

            private int alipayFlag;
            private int defaultWay;
            private int openFlag;
            private int serviceCharge;
            private int wechatFlag;
            private int withdrawCount;
            private int withdrawLimit;
            private String withdrawType;

            public int getAlipayFlag() {
                return alipayFlag;
            }

            public void setAlipayFlag(int alipayFlag) {
                this.alipayFlag = alipayFlag;
            }

            public int getDefaultWay() {
                return defaultWay;
            }

            public void setDefaultWay(int defaultWay) {
                this.defaultWay = defaultWay;
            }

            public int getOpenFlag() {
                return openFlag;
            }

            public void setOpenFlag(int openFlag) {
                this.openFlag = openFlag;
            }

            public int getServiceCharge() {
                return serviceCharge;
            }

            public void setServiceCharge(int serviceCharge) {
                this.serviceCharge = serviceCharge;
            }

            public int getWechatFlag() {
                return wechatFlag;
            }

            public void setWechatFlag(int wechatFlag) {
                this.wechatFlag = wechatFlag;
            }

            public int getWithdrawCount() {
                return withdrawCount;
            }

            public void setWithdrawCount(int withdrawCount) {
                this.withdrawCount = withdrawCount;
            }

            public int getWithdrawLimit() {
                return withdrawLimit;
            }

            public void setWithdrawLimit(int withdrawLimit) {
                this.withdrawLimit = withdrawLimit;
            }

            public String getWithdrawType() {
                return withdrawType;
            }

            public void setWithdrawType(String withdrawType) {
                this.withdrawType = withdrawType;
            }
        }

        public static class WithdrawBean {
            private int serviceCharge;
            private String money;

            public int getServiceCharge() {
                return serviceCharge;
            }

            public void setServiceCharge(int serviceCharge) {
                this.serviceCharge = serviceCharge;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
