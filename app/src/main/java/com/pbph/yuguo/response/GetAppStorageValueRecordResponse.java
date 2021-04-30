package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetAppStorageValueRecordResponse extends BaseResponse {


    /**
     * data : {"dealsInfoDtoList":[{"createTime":"2018-09-26 14:51:23",
     * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
     * {"createTime":"2018-09-26 14:52:55","dealCode":"242339795360395264","dealPrice":3795,
     * "dealType":2,"dealWay":4},{"createTime":"2018-09-26 14:53:18",
     * "dealCode":"242339890403323904","dealPrice":3795,"dealType":2,"dealWay":4},
     * {"createTime":"2018-09-26 14:55:33","dealCode":"D242339377121177600",
     * "dealPrice":111111100,"dealType":5,"dealWay":3},{"createTime":"2018-09-26 14:57:46",
     * "dealCode":"242341014493573120","dealPrice":4554,"dealType":2,"dealWay":4},
     * {"createTime":"2018-09-26 14:58:37","dealCode":"242341228138835968","dealPrice":4554,
     * "dealType":2,"dealWay":4},{"createTime":"2018-09-26 15:05:15",
     * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
     * {"createTime":"2018-09-26 15:07:09","dealCode":"242343375173693440","dealPrice":4554,
     * "dealType":2,"dealWay":4},{"createTime":"2018-09-26 15:15:27",
     * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
     * {"createTime":"2018-09-26 16:15:38","dealCode":"D242339377121177600",
     * "dealPrice":111111100,"dealType":5,"dealWay":3},{"createTime":"2018-09-26 18:15:26",
     * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
     * {"createTime":"2018-09-27 00:15:37","dealCode":"D242339377121177600",
     * "dealPrice":111111100,"dealType":5,"dealWay":3}],"totleSize":12}
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
         * dealsInfoDtoList : [{"createTime":"2018-09-26 14:51:23",
         * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
         * {"createTime":"2018-09-26 14:52:55","dealCode":"242339795360395264","dealPrice":3795,
         * "dealType":2,"dealWay":4},{"createTime":"2018-09-26 14:53:18",
         * "dealCode":"242339890403323904","dealPrice":3795,"dealType":2,"dealWay":4},
         * {"createTime":"2018-09-26 14:55:33","dealCode":"D242339377121177600",
         * "dealPrice":111111100,"dealType":5,"dealWay":3},{"createTime":"2018-09-26 14:57:46",
         * "dealCode":"242341014493573120","dealPrice":4554,"dealType":2,"dealWay":4},
         * {"createTime":"2018-09-26 14:58:37","dealCode":"242341228138835968","dealPrice":4554,
         * "dealType":2,"dealWay":4},{"createTime":"2018-09-26 15:05:15",
         * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
         * {"createTime":"2018-09-26 15:07:09","dealCode":"242343375173693440","dealPrice":4554,
         * "dealType":2,"dealWay":4},{"createTime":"2018-09-26 15:15:27",
         * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
         * {"createTime":"2018-09-26 16:15:38","dealCode":"D242339377121177600",
         * "dealPrice":111111100,"dealType":5,"dealWay":3},{"createTime":"2018-09-26 18:15:26",
         * "dealCode":"D242339377121177600","dealPrice":111111100,"dealType":5,"dealWay":3},
         * {"createTime":"2018-09-27 00:15:37","dealCode":"D242339377121177600",
         * "dealPrice":111111100,"dealType":5,"dealWay":3}]
         * totleSize : 12
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
             * createTime : 2018-09-26 14:51:23
             * dealCode : D242339377121177600
             * dealPrice : 111111100
             * dealType : 5
             * dealWay : 3
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
