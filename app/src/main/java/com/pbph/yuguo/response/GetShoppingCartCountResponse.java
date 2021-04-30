package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetShoppingCartCountResponse extends BaseResponse {

    /**
     * data : {"shoppingCartCount":2}
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
         * shoppingCartCount : 2
         */

        private int shoppingCartCount;

        public int getShoppingCartCount() {
            return shoppingCartCount;
        }

        public void setShoppingCartCount(int shoppingCartCount) {
            this.shoppingCartCount = shoppingCartCount;
        }
    }
}
