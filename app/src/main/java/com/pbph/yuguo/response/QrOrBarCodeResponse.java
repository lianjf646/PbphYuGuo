package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 支付二维码
 * Created by zyp on 2018/8/13 0013.
 */

public class QrOrBarCodeResponse extends BaseResponse {

    /**
     * data : {"qrUrl":"http:","barUrl":"http:"}
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
         * qrUrl : http:
         * barUrl : http:
         */

        private String qrUrl;
        private String barUrl;

        public String getQrUrl() {
            return qrUrl;
        }

        public void setQrUrl(String qrUrl) {
            this.qrUrl = qrUrl;
        }

        public String getBarUrl() {
            return barUrl;
        }

        public void setBarUrl(String barUrl) {
            this.barUrl = barUrl;
        }
    }
}
