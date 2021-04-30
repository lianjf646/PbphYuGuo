package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetInviteVipCouponListResponse extends BaseResponse {

    /**
     * data : {"invitationVipCouponVO":{"couponNum":2,"couponPrice":830000,"inviteNum":2}}
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
         * invitationVipCouponVO : {"couponNum":2,"couponPrice":830000,"inviteNum":2}
         */

        private InvitationVipCouponVOBean invitationVipCouponVO;

        public InvitationVipCouponVOBean getInvitationVipCouponVO() {
            return invitationVipCouponVO;
        }

        public void setInvitationVipCouponVO(InvitationVipCouponVOBean invitationVipCouponVO) {
            this.invitationVipCouponVO = invitationVipCouponVO;
        }

        public static class InvitationVipCouponVOBean {
            /**
             * couponNum : 2
             * couponPrice : 830000
             * inviteNum : 2
             */

            private int couponNum;
            private long couponPrice;
            private int inviteNum;

            public int getCouponNum() {
                return couponNum;
            }

            public void setCouponNum(int couponNum) {
                this.couponNum = couponNum;
            }

            public long getCouponPrice() {
                return couponPrice;
            }

            public void setCouponPrice(long couponPrice) {
                this.couponPrice = couponPrice;
            }

            public int getInviteNum() {
                return inviteNum;
            }

            public void setInviteNum(int inviteNum) {
                this.inviteNum = inviteNum;
            }
        }
    }
}
