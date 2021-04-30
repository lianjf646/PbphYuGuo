package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;


public class GetGoodsStorageNumResponse extends BaseResponse {

    /**
     * data : {"storageNum":15}
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
         * storageNum : 15
         */

        private int storageNum;

        public int getStorageNum() {
            return storageNum;
        }

        public void setStorageNum(int storageNum) {
            this.storageNum = storageNum;
        }
    }
}
