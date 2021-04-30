package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetStoredConfigResponse extends BaseResponse {

    /**
     * data : {"storedConfigDtoList":[{"couponActivityDtoList":[{"couponActivityId":28,"couponPrice":500,"couponRuleType":1},{"couponActivityId":29,"couponPrice":10,"couponRuleType":1}],"discountFlag":1,"memberFlag":1,"memberTime":44,"storedMoney":4400,"usedFlag":0},{"couponActivityDtoList":[{"couponActivityId":20,"couponPrice":1000,"couponRuleType":1},{"couponActivityId":21,"couponPrice":2000,"couponRuleType":3}],"discountFlag":2,"memberFlag":2,"memberTime":20,"storedMoney":5000,"usedFlag":1},{"couponActivityDtoList":[],"discountFlag":2,"memberFlag":2,"memberTime":0,"storedMoney":10000,"usedFlag":0},{"couponActivityDtoList":[{"couponActivityId":20,"couponPrice":1000,"couponRuleType":1},{"couponActivityId":21,"couponPrice":2000,"couponRuleType":3}],"discountFlag":1,"memberFlag":2,"memberTime":20,"storedMoney":10000,"usedFlag":1},{"couponActivityDtoList":[],"discountFlag":2,"memberFlag":2,"memberTime":30,"storedMoney":15000,"usedFlag":1},{"couponActivityDtoList":[{"couponActivityId":30,"couponPrice":0,"couponRuleType":1}],"discountFlag":1,"memberFlag":1,"memberTime":4444,"storedMoney":44400,"usedFlag":1},{"couponActivityDtoList":[],"discountFlag":2,"memberFlag":1,"memberTime":88,"storedMoney":888800,"usedFlag":1},{"couponActivityDtoList":[],"discountFlag":1,"memberFlag":1,"memberTime":0,"storedMoney":3333300,"usedFlag":1},{"couponActivityDtoList":[],"discountFlag":2,"memberFlag":2,"memberTime":7,"storedMoney":7777700,"usedFlag":0},{"couponActivityDtoList":[],"discountFlag":1,"memberFlag":1,"memberTime":333333,"storedMoney":12321300,"usedFlag":1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<StoredConfigDtoListBean> storedConfigDtoList;

        public List<StoredConfigDtoListBean> getStoredConfigDtoList() {
            return storedConfigDtoList;
        }

        public void setStoredConfigDtoList(List<StoredConfigDtoListBean> storedConfigDtoList) {
            this.storedConfigDtoList = storedConfigDtoList;
        }


        public static class StoredConfigDtoListBean {
            /**
             * couponActivityDtoList : [{"couponActivityId":28,"couponPrice":500,"couponRuleType":1},{"couponActivityId":29,"couponPrice":10,"couponRuleType":1}]
             * discountFlag : 1
             * memberFlag : 1
             * memberTime : 44
             * storedMoney : 4400
             * usedFlag : 0
             */

            private int discountFlag;
            private int memberFlag;
            private int memberTime;
            private int storedMoney;
            private int usedFlag;
            private List<CouponActivityDtoListBean> couponActivityDtoList;

            private VipBean vipBean;

            public VipBean getVipBean() {
                return vipBean;
            }

            public void setVipBean(VipBean vipBean) {
                this.vipBean = vipBean;
            }

            public static class VipBean {
                private int memberFlag;
                private int memberTime;

                public int getMemberFlag() {
                    return memberFlag;
                }

                public void setMemberFlag(int memberFlag) {
                    this.memberFlag = memberFlag;
                }

                public int getMemberTime() {
                    return memberTime;
                }

                public void setMemberTime(int memberTime) {
                    this.memberTime = memberTime;
                }
            }

            public int getDiscountFlag() {
                return discountFlag;
            }

            public void setDiscountFlag(int discountFlag) {
                this.discountFlag = discountFlag;
            }

            public int getMemberFlag() {
                return memberFlag;
            }

            public void setMemberFlag(int memberFlag) {
                this.memberFlag = memberFlag;
            }

            public int getMemberTime() {
                return memberTime;
            }

            public void setMemberTime(int memberTime) {
                this.memberTime = memberTime;
            }

            public int getStoredMoney() {
                return storedMoney;
            }

            public void setStoredMoney(int storedMoney) {
                this.storedMoney = storedMoney;
            }

            public int getUsedFlag() {
                return usedFlag;
            }

            public void setUsedFlag(int usedFlag) {
                this.usedFlag = usedFlag;
            }

            public List<CouponActivityDtoListBean> getCouponActivityDtoList() {
                return couponActivityDtoList;
            }

            public void setCouponActivityDtoList(List<CouponActivityDtoListBean> couponActivityDtoList) {
                this.couponActivityDtoList = couponActivityDtoList;
            }

            public static class CouponActivityDtoListBean {
                /**
                 * couponActivityId : 28
                 * couponPrice : 500
                 * couponRuleType : 1
                 */

                private int couponActivityId;
                private int couponPrice;
                private int couponRuleType;

                private int discountFlag;

                public int getDiscountFlag() {
                    return discountFlag;
                }

                public void setDiscountFlag(int discountFlag) {
                    this.discountFlag = discountFlag;
                }

                public int getCouponActivityId() {
                    return couponActivityId;
                }

                public void setCouponActivityId(int couponActivityId) {
                    this.couponActivityId = couponActivityId;
                }

                public int getCouponPrice() {
                    return couponPrice;
                }

                public void setCouponPrice(int couponPrice) {
                    this.couponPrice = couponPrice;
                }

                public int getCouponRuleType() {
                    return couponRuleType;
                }

                public void setCouponRuleType(int couponRuleType) {
                    this.couponRuleType = couponRuleType;
                }
            }
        }

    }
}
