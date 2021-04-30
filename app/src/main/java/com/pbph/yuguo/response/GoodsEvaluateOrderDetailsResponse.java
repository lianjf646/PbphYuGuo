package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:
 */

public class GoodsEvaluateOrderDetailsResponse extends BaseResponse {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int anonymousFlag;
        private String evaluateContent;
        private int evaluateId;

        public int getAnonymousFlag() {
            return anonymousFlag;
        }

        public void setAnonymousFlag(int anonymousFlag) {
            this.anonymousFlag = anonymousFlag;
        }

        public String getEvaluateContent() {
            return evaluateContent;
        }

        public void setEvaluateContent(String evaluateContent) {
            this.evaluateContent = evaluateContent;
        }

        public int getEvaluateId() {
            return evaluateId;
        }

        public void setEvaluateId(int evaluateId) {
            this.evaluateId = evaluateId;
        }
    }
}
