package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by zyp on 2018/9/7 0007.
 * class note:评价
 */

public class EvaluateOrderResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int evaluateStatus;
        private int shareStatus;

        public int getEvaluateStatus() {
            return evaluateStatus;
        }

        public void setEvaluateStatus(int evaluateStatus) {
            this.evaluateStatus = evaluateStatus;
        }

        public int getShareStatus() {
            return shareStatus;
        }

        public void setShareStatus(int shareStatus) {
            this.shareStatus = shareStatus;
        }
    }
}
