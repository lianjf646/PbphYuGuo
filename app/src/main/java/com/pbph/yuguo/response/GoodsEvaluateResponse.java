package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:评价response
 */

public class GoodsEvaluateResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int totleSize;
        private List<EvaluateListBean> evaluateList;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<EvaluateListBean> getEvaluateList() {
            return evaluateList;
        }

        public void setEvaluateList(List<EvaluateListBean> evaluateList) {
            this.evaluateList = evaluateList;
        }

        public static class EvaluateListBean {

            private int anonymousFlag;
            private String createTime;
            private String customerName;
            private String customerImgUrl;
            private String evaluateContent;
            private List<ShareImgListBean> shareImgList;

            public int getAnonymousFlag() {
                return anonymousFlag;
            }

            public void setAnonymousFlag(int anonymousFlag) {
                this.anonymousFlag = anonymousFlag;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerImgUrl() {
                return customerImgUrl;
            }

            public void setCustomerImgUrl(String customerImgUrl) {
                this.customerImgUrl = customerImgUrl;
            }

            public String getEvaluateContent() {
                return evaluateContent;
            }

            public void setEvaluateContent(String evaluateContent) {
                this.evaluateContent = evaluateContent;
            }

            public List<ShareImgListBean> getShareImgList() {
                return shareImgList;
            }

            public void setShareImgList(List<ShareImgListBean> shareImgList) {
                this.shareImgList = shareImgList;
            }

            public static class ShareImgListBean {

                private String imageUrl;

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }
            }
        }
    }
}
