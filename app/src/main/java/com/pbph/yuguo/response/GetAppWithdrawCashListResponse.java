package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetAppWithdrawCashListResponse extends BaseResponse {

    /**
     * data : {"dealsInfoDtoList":[{"createTime":"2018-08-21 21:30:33","dealPrice":100,"dealWay":0},{"createTime":"2018-08-21 21:30:54","dealPrice":100,"dealWay":0}]}
     * totleSize : 2
     */

    private DataBean data;
    private int totleSize;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotleSize() {
        return totleSize;
    }

    public void setTotleSize(int totleSize) {
        this.totleSize = totleSize;
    }

    public static class DataBean {
        private List<DealsInfoDtoListBean> dealsInfoDtoList;

        public List<DealsInfoDtoListBean> getDealsInfoDtoList() {
            return dealsInfoDtoList;
        }

        public void setDealsInfoDtoList(List<DealsInfoDtoListBean> dealsInfoDtoList) {
            this.dealsInfoDtoList = dealsInfoDtoList;
        }

        public static class DealsInfoDtoListBean {
            /**
             * createTime : 2018-08-21 21:30:33
             * dealPrice : 100
             * dealWay : 0
             */

            private String createTime;
            private int dealPrice;
            private int dealWay;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getDealPrice() {
                return dealPrice;
            }

            public void setDealPrice(int dealPrice) {
                this.dealPrice = dealPrice;
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
