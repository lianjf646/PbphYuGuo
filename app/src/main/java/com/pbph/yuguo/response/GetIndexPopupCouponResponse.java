package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetIndexPopupCouponResponse extends BaseResponse {

    /**
     * data : {"list":[{"couponActivityEndTime":"","couponActivityId":7,"couponActivityStartTime":"","couponEndTime":"","couponName":"测试","couponPrice":20,"couponRuleType":3,"couponStartTime":"","couponXPrice":0,"receiveFlag":0}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * couponActivityEndTime :
             * couponActivityId : 7
             * couponActivityStartTime :
             * couponEndTime :
             * couponName : 测试
             * couponPrice : 20
             * couponRuleType : 3
             * couponStartTime :
             * couponXPrice : 0
             * receiveFlag : 0
             */

            private String couponActivityEndTime;
            private int couponActivityId;
            private String couponActivityStartTime;
            private String couponEndTime;
            private String couponName;
            private int couponPrice;
            private int couponRuleType;
            private String couponStartTime;
            private int couponXPrice;
            private int receiveFlag;

            public String getCouponActivityEndTime() {
                return couponActivityEndTime;
            }

            public void setCouponActivityEndTime(String couponActivityEndTime) {
                this.couponActivityEndTime = couponActivityEndTime;
            }

            public int getCouponActivityId() {
                return couponActivityId;
            }

            public void setCouponActivityId(int couponActivityId) {
                this.couponActivityId = couponActivityId;
            }

            public String getCouponActivityStartTime() {
                return couponActivityStartTime;
            }

            public void setCouponActivityStartTime(String couponActivityStartTime) {
                this.couponActivityStartTime = couponActivityStartTime;
            }

            public String getCouponEndTime() {
                return couponEndTime;
            }

            public void setCouponEndTime(String couponEndTime) {
                this.couponEndTime = couponEndTime;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
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

            public String getCouponStartTime() {
                return couponStartTime;
            }

            public void setCouponStartTime(String couponStartTime) {
                this.couponStartTime = couponStartTime;
            }

            public int getCouponXPrice() {
                return couponXPrice;
            }

            public void setCouponXPrice(int couponXPrice) {
                this.couponXPrice = couponXPrice;
            }

            public int getReceiveFlag() {
                return receiveFlag;
            }

            public void setReceiveFlag(int receiveFlag) {
                this.receiveFlag = receiveFlag;
            }
        }
    }
}
