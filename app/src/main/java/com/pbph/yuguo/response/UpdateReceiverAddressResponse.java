package com.pbph.yuguo.response;


import com.pbph.yuguo.base.BaseResponse;


public class UpdateReceiverAddressResponse extends BaseResponse {

    /**
     * data : {}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

    }
}
