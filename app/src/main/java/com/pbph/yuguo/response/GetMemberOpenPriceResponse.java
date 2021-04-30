package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 获取会员充值价格

 */

public class GetMemberOpenPriceResponse extends BaseResponse {


    /**
     * data : {"discountPrice":16600,"openPrice":19800,"timeUnit":4}
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
         * discountPrice : 16600
         * openPrice : 19800
         * timeUnit : 4
         */

        private int discountPrice;
        private int openPrice;
        private int timeUnit;

        public int getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getOpenPrice() {
            return openPrice;
        }

        public void setOpenPrice(int openPrice) {
            this.openPrice = openPrice;
        }

        public int getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(int timeUnit) {
            this.timeUnit = timeUnit;
        }
    }
}
