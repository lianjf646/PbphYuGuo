package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 *获取会员返利比例

 */

public class GetMemberProfitPercentResponse extends BaseResponse {


    /**
     * data : {"returnRate":5}
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
         * returnRate : 5
         */

        private int returnRate;

        public int getReturnRate() {
            return returnRate;
        }

        public void setReturnRate(int returnRate) {
            this.returnRate = returnRate;
        }
    }
}
