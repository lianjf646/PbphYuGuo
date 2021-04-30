package com.pbph.yuguo.response;


import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by zyp on 2018/5/23.
 */

public class Riderh5ViewResponse extends BaseResponse {

    /**
     * data : {"sf_order_id":"3232928040248582147","shop_order_id":"E257587952211181568","url":"http://goappic.sf-express.com/rider/externalh5/ridertrack?clientCode=&shopOrderNo=3232928040248582147&sign=NWJiNjkzMmMwNDAwYjgzN2M2Nzg3ZWU4NGI0MTNhZjI="}
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
         * sf_order_id : 3232928040248582147
         * shop_order_id : E257587952211181568
         * url : http://goappic.sf-express.com/rider/externalh5/ridertrack?clientCode=&shopOrderNo=3232928040248582147&sign=NWJiNjkzMmMwNDAwYjgzN2M2Nzg3ZWU4NGI0MTNhZjI=
         */

        private String sf_order_id;
        private String shop_order_id;
        private String url;

        public String getSf_order_id() {
            return sf_order_id;
        }

        public void setSf_order_id(String sf_order_id) {
            this.sf_order_id = sf_order_id;
        }

        public String getShop_order_id() {
            return shop_order_id;
        }

        public void setShop_order_id(String shop_order_id) {
            this.shop_order_id = shop_order_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
