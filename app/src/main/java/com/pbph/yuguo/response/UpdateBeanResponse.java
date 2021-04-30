package com.pbph.yuguo.response;


import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by Administrator on 2018/5/23.
 */

public class UpdateBeanResponse extends BaseResponse {


    /**
     * data : {"appVersion":{"appVersionId":8,"downLoadUrl":"2","appType":"0","updateTime":1529464447000,"version":"1.0.0","isUpdateFlag":"1"}}
     * isTip : Y
     */

    private DataBean data;
    private String isTip;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getIsTip() {
        return isTip;
    }

    public void setIsTip(String isTip) {
        this.isTip = isTip;
    }


    public static class DataBean {
        /**
         * versionId : 3
         * downloadUrl : http://bshop.pbphkj.com/ygweb/phph_hgs_release_v1.0.0.apk
         * versionType : 1
         * versionNum : 2
         * modifiedTime : 2018-10-11
         */

        private int versionId;
        private String downloadUrl;
        private String versionType;
        private String versionNum;
        private String modifiedTime;

        public int getVersionId() {
            return versionId;
        }

        public void setVersionId(int versionId) {
            this.versionId = versionId;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }

        public String getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(String modifiedTime) {
            this.modifiedTime = modifiedTime;
        }
    }
}
