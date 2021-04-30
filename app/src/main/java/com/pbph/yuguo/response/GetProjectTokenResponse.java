package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by 连嘉凡 on 2018/9/10.
 */

public class GetProjectTokenResponse extends BaseResponse {


    /**
     * data : {"projectCode":"1000000522761709","token":"B350A50C3D8ECDB1E8BFE1366D8CEDBA"}
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
         * projectCode : 1000000522761709
         * token : B350A50C3D8ECDB1E8BFE1366D8CEDBA
         */

        private String projectCode;
        private String token;

        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
