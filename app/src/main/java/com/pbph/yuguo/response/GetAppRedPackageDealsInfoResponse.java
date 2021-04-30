package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 我的红包-获取红包收支明细
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetAppRedPackageDealsInfoResponse extends BaseResponse {


    /**
     * data : {"dealsInfoDtoList":[{"createTime":"2018-08-23 16:21:33",
     * "dealCode":"230040913545953280","dealPrice":500044,"dealType":5,"dealWay":0}],"totleSize":3}
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
         * dealsInfoDtoList : [{"createTime":"2018-08-23 16:21:33",
         * "dealCode":"230040913545953280","dealPrice":500044,"dealType":5,"dealWay":0}]
         * totleSize : 3
         */

        private int totleSize;
        private List<DealsInfoDtoListBean> dealsInfoDtoList;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<DealsInfoDtoListBean> getDealsInfoDtoList() {
            return dealsInfoDtoList;
        }

        public void setDealsInfoDtoList(List<DealsInfoDtoListBean> dealsInfoDtoList) {
            this.dealsInfoDtoList = dealsInfoDtoList;
        }

        public static class DealsInfoDtoListBean {
            /**
             * createTime : 2018-08-23 16:21:33
             * dealCode : 230040913545953280
             * dealPrice : 500044
             * dealType : 5
             * dealWay : 0
             */

            private String createTime;
            private String dealCode;
            private int dealPrice;
            private int dealType;
            private int dealWay;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDealCode() {
                return dealCode;
            }

            public void setDealCode(String dealCode) {
                this.dealCode = dealCode;
            }

            public int getDealPrice() {
                return dealPrice;
            }

            public void setDealPrice(int dealPrice) {
                this.dealPrice = dealPrice;
            }

            public int getDealType() {
                return dealType;
            }

            public void setDealType(int dealType) {
                this.dealType = dealType;
            }

            public int getDealWay() {
                return dealWay;
            }

            public void setDealWay(int dealWay) {
                this.dealWay = dealWay;
            }
        }
    }
}
