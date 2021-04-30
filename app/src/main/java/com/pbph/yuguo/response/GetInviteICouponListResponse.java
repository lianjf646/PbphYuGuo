package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetInviteICouponListResponse extends BaseResponse {

    /**
     * data : {"inviteICouponList":[{"couponList":[{"couponNum":2,"couponPrice":400},{"couponNum":2,"couponPrice":500}],"inviteNum":1},{"couponList":[{"couponNum":0,"couponPrice":1000},{"couponNum":0,"couponPrice":2000}],"inviteNum":10}],"customerNum":2}
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
         * inviteICouponList : [{"couponList":[{"couponNum":2,"couponPrice":400},{"couponNum":2,"couponPrice":500}],"inviteNum":1},{"couponList":[{"couponNum":0,"couponPrice":1000},{"couponNum":0,"couponPrice":2000}],"inviteNum":10}]
         * customerNum : 2
         */

        private int customerNum;
        private List<InviteICouponListBean> inviteICouponList;

        public int getCustomerNum() {
            return customerNum;
        }

        public void setCustomerNum(int customerNum) {
            this.customerNum = customerNum;
        }

        public List<InviteICouponListBean> getInviteICouponList() {
            return inviteICouponList;
        }

        public void setInviteICouponList(List<InviteICouponListBean> inviteICouponList) {
            this.inviteICouponList = inviteICouponList;
        }

        public static class InviteICouponListBean {
            /**
             * couponList : [{"couponNum":2,"couponPrice":400},{"couponNum":2,"couponPrice":500}]
             * inviteNum : 1
             */

            private int inviteNum;
            private List<CouponListBean> couponList;

            public int getInviteNum() {
                return inviteNum;
            }

            public void setInviteNum(int inviteNum) {
                this.inviteNum = inviteNum;
            }

            public List<CouponListBean> getCouponList() {
                return couponList;
            }

            public void setCouponList(List<CouponListBean> couponList) {
                this.couponList = couponList;
            }

            public static class CouponListBean {
                /**
                 * couponNum : 2
                 * couponPrice : 400
                 */

                private int couponNum;
                private int couponPrice;

                public int getCouponNum() {
                    return couponNum;
                }

                public void setCouponNum(int couponNum) {
                    this.couponNum = couponNum;
                }

                public int getCouponPrice() {
                    return couponPrice;
                }

                public void setCouponPrice(int couponPrice) {
                    this.couponPrice = couponPrice;
                }
            }
        }
    }
}
