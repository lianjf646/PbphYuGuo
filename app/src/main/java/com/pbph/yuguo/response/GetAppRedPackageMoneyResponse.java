package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetAppRedPackageMoneyResponse extends BaseResponse {

    /**
     * data : {"redPackageMoney":0}
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
         * redPackageMoney : 0
         */

        private int redPackageMoney;

        public int getRedPackageMoney() {
            return redPackageMoney;
        }

        public void setRedPackageMoney(int redPackageMoney) {
            this.redPackageMoney = redPackageMoney;
        }
    }
}
