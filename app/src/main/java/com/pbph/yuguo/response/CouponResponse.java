package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class CouponResponse extends BaseResponse{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int totleSize;
        private List<ListBean> list;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {

            private int couponActivityId;
            private String couponEndTime;
            private int couponId;
            private String couponName;
            private double couponPrice;
            private int couponRuleType;
            private String couponStartTime;
            private int couponStatus;
            private double couponXPrice;
            private int goodsId;
            private int usableFlag;
            private int receiveFlag;
            private int activeSiteType;

            public int getCouponActivityId() {
                return couponActivityId;
            }

            public void setCouponActivityId(int couponActivityId) {
                this.couponActivityId = couponActivityId;
            }

            public String getCouponEndTime() {
                return couponEndTime;
            }

            public void setCouponEndTime(String couponEndTime) {
                this.couponEndTime = couponEndTime;
            }

            public int getCouponId() {
                return couponId;
            }

            public void setCouponId(int couponId) {
                this.couponId = couponId;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public double getCouponPrice() {
                return couponPrice;
            }

            public void setCouponPrice(double couponPrice) {
                this.couponPrice = couponPrice;
            }

            public int getCouponRuleType() {
                return couponRuleType;
            }

            public void setCouponRuleType(int couponRuleType) {
                this.couponRuleType = couponRuleType;
            }

            public String getCouponStartTime() {
                return couponStartTime;
            }

            public void setCouponStartTime(String couponStartTime) {
                this.couponStartTime = couponStartTime;
            }

            public int getCouponStatus() {
                return couponStatus;
            }

            public void setCouponStatus(int couponStatus) {
                this.couponStatus = couponStatus;
            }

            public double getCouponXPrice() {
                return couponXPrice;
            }

            public void setCouponXPrice(double couponXPrice) {
                this.couponXPrice = couponXPrice;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getUsableFlag() {
                return usableFlag;
            }

            public void setUsableFlag(int usableFlag) {
                this.usableFlag = usableFlag;
            }

            public int getReceiveFlag() {
                return receiveFlag;
            }

            public void setReceiveFlag(int receiveFlag) {
                this.receiveFlag = receiveFlag;
            }

            public int getActiveSiteType() {
                return activeSiteType;
            }

            public void setActiveSiteType(int activeSiteType) {
                this.activeSiteType = activeSiteType;
            }
        }
    }
}
