package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 查询消息信息以及未读消息数量
 */

public class GetNoReadMessageCountResponse extends BaseResponse {

    /**
     * data : {"count":0}
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
         * count : 0
         */

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
