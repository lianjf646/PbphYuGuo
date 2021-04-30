package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetAppStoredMoneyResponse extends BaseResponse {

    /**
     * data : {"storedMoney":0}
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
         * storedMoney : 0
         */

        private int storedMoney;

        public int getStoredMoney() {
            return storedMoney;
        }

        public void setStoredMoney(int storedMoney) {
            this.storedMoney = storedMoney;
        }
    }
}
