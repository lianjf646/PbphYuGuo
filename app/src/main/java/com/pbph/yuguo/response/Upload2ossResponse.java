package com.pbph.yuguo.response;


import com.pbph.yuguo.base.BaseResponse;


public class Upload2ossResponse extends BaseResponse {

    /**
     * data : https://wangjcbkt.oss-cn-beijing.aliyuncs.com/productimage2/headImg/1528271074708.jpg
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
