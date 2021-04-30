package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 订单列表实体类
 * Created by zyp on 2018/8/10 0010.
 */

public class OrderListResponse extends BaseResponse {
    /**
     * data : {"orderList":[{"backOrderId":0,"backOrderStatus":0,"createTime":"2019-01-10 15:12:19","evaluateStatus":0,"goodsNumTotal":1,"orderDealPrice":124,"orderGoodsInfoList":[{"activeType":0,"evaluateFlag":0,"goodsId":85,"goodsInfoId":0,"goodsInfoMemberPrice":0,"goodsInfoName":"橙子(红色XXLg),而纷纷退","goodsInfoNameSub":"","goodsInfoNum":1,"goodsInfoPack":"","goodsInfoPackCharges":0,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"goodsInfoSpecValue":"颜色:红色;大小:XXL;重量:g","goodsInfoWeight":12,"memberPrice":123,"shareFlag":0}],"orderId":65,"orderStatus":1,"shareStatus":0}],"totleSize":1}
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
         * orderList : [{"backOrderId":0,"backOrderStatus":0,"createTime":"2019-01-10 15:12:19","evaluateStatus":0,"goodsNumTotal":1,"orderDealPrice":124,"orderGoodsInfoList":[{"activeType":0,"evaluateFlag":0,"goodsId":85,"goodsInfoId":0,"goodsInfoMemberPrice":0,"goodsInfoName":"橙子(红色XXLg),而纷纷退","goodsInfoNameSub":"","goodsInfoNum":1,"goodsInfoPack":"","goodsInfoPackCharges":0,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"goodsInfoSpecValue":"颜色:红色;大小:XXL;重量:g","goodsInfoWeight":12,"memberPrice":123,"shareFlag":0}],"orderId":65,"orderStatus":1,"shareStatus":0}]
         * totleSize : 1
         */

        private int totleSize;
        private List<OrderListBean> orderList;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * backOrderId : 0
             * backOrderStatus : 0
             * createTime : 2019-01-10 15:12:19
             * evaluateStatus : 0
             * goodsNumTotal : 1
             * orderDealPrice : 124
             * orderGoodsInfoList : [{"activeType":0,"evaluateFlag":0,"goodsId":85,"goodsInfoId":0,"goodsInfoMemberPrice":0,"goodsInfoName":"橙子(红色XXLg),而纷纷退","goodsInfoNameSub":"","goodsInfoNum":1,"goodsInfoPack":"","goodsInfoPackCharges":0,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"goodsInfoSpecValue":"颜色:红色;大小:XXL;重量:g","goodsInfoWeight":12,"memberPrice":123,"shareFlag":0}]
             * orderId : 65
             * orderStatus : 1
             * shareStatus : 0
             * orderType : 0
             * anewDeliverFlag: 0
             * backOrderFlag: 0
             */

            private int backOrderId;
            private int backOrderStatus;
            private String createTime;
            private int evaluateStatus;
            private int goodsNumTotal;
            private int orderDealPrice;
            private int orderId;
            private int orderStatus;
            private int shareStatus;
            private int orderType;
            private int anewDeliverFlag;
            private int backOrderFlag;
            private List<OrderGoodsInfoListBean> orderGoodsInfoList;

            public int getBackOrderId() {
                return backOrderId;
            }

            public void setBackOrderId(int backOrderId) {
                this.backOrderId = backOrderId;
            }

            public int getBackOrderStatus() {
                return backOrderStatus;
            }

            public void setBackOrderStatus(int backOrderStatus) {
                this.backOrderStatus = backOrderStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getEvaluateStatus() {
                return evaluateStatus;
            }

            public void setEvaluateStatus(int evaluateStatus) {
                this.evaluateStatus = evaluateStatus;
            }

            public int getGoodsNumTotal() {
                return goodsNumTotal;
            }

            public void setGoodsNumTotal(int goodsNumTotal) {
                this.goodsNumTotal = goodsNumTotal;
            }

            public int getOrderDealPrice() {
                return orderDealPrice;
            }

            public void setOrderDealPrice(int orderDealPrice) {
                this.orderDealPrice = orderDealPrice;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getShareStatus() {
                return shareStatus;
            }

            public void setShareStatus(int shareStatus) {
                this.shareStatus = shareStatus;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public List<OrderGoodsInfoListBean> getOrderGoodsInfoList() {
                return orderGoodsInfoList;
            }

            public void setOrderGoodsInfoList(List<OrderGoodsInfoListBean> orderGoodsInfoList) {
                this.orderGoodsInfoList = orderGoodsInfoList;
            }

            public int getAnewDeliverFlag() {
                return anewDeliverFlag;
            }

            public void setAnewDeliverFlag(int anewDeliverFlag) {
                this.anewDeliverFlag = anewDeliverFlag;
            }

            public int getBackOrderFlag() {
                return backOrderFlag;
            }

            public void setBackOrderFlag(int backOrderFlag) {
                this.backOrderFlag = backOrderFlag;
            }

            public static class OrderGoodsInfoListBean {
                /**
                 * activeType : 0
                 * evaluateFlag : 0
                 * goodsId : 85
                 * goodsInfoId : 0
                 * goodsInfoMemberPrice : 0
                 * goodsInfoName : 橙子(红色XXLg),而纷纷退
                 * goodsInfoNameSub :
                 * goodsInfoNum : 1
                 * goodsInfoPack :
                 * goodsInfoPackCharges : 0
                 * goodsInfoPicUrl : http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png
                 * goodsInfoSalePrice : 1243
                 * goodsInfoSpecValue : 颜色:红色;大小:XXL;重量:g
                 * goodsInfoWeight : 12
                 * memberPrice : 123
                 * shareFlag : 0
                 */

                private int activeType;
                private int evaluateFlag;
                private int goodsId;
                private int goodsInfoId;
                private int goodsInfoMemberPrice;
                private String goodsInfoName;
                private String goodsInfoNameSub;
                private int goodsInfoNum;
                private String goodsInfoPack;
                private int goodsInfoPackCharges;
                private String goodsInfoPicUrl;
                private int goodsInfoSalePrice;
                private String goodsInfoSpecValue;
                private int goodsInfoWeight;
                private int memberPrice;
                private int shareFlag;

                public int getActiveType() {
                    return activeType;
                }

                public void setActiveType(int activeType) {
                    this.activeType = activeType;
                }

                public int getEvaluateFlag() {
                    return evaluateFlag;
                }

                public void setEvaluateFlag(int evaluateFlag) {
                    this.evaluateFlag = evaluateFlag;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public int getGoodsInfoId() {
                    return goodsInfoId;
                }

                public void setGoodsInfoId(int goodsInfoId) {
                    this.goodsInfoId = goodsInfoId;
                }

                public int getGoodsInfoMemberPrice() {
                    return goodsInfoMemberPrice;
                }

                public void setGoodsInfoMemberPrice(int goodsInfoMemberPrice) {
                    this.goodsInfoMemberPrice = goodsInfoMemberPrice;
                }

                public String getGoodsInfoName() {
                    return goodsInfoName;
                }

                public void setGoodsInfoName(String goodsInfoName) {
                    this.goodsInfoName = goodsInfoName;
                }

                public String getGoodsInfoNameSub() {
                    return goodsInfoNameSub;
                }

                public void setGoodsInfoNameSub(String goodsInfoNameSub) {
                    this.goodsInfoNameSub = goodsInfoNameSub;
                }

                public int getGoodsInfoNum() {
                    return goodsInfoNum;
                }

                public void setGoodsInfoNum(int goodsInfoNum) {
                    this.goodsInfoNum = goodsInfoNum;
                }

                public String getGoodsInfoPack() {
                    return goodsInfoPack;
                }

                public void setGoodsInfoPack(String goodsInfoPack) {
                    this.goodsInfoPack = goodsInfoPack;
                }

                public int getGoodsInfoPackCharges() {
                    return goodsInfoPackCharges;
                }

                public void setGoodsInfoPackCharges(int goodsInfoPackCharges) {
                    this.goodsInfoPackCharges = goodsInfoPackCharges;
                }

                public String getGoodsInfoPicUrl() {
                    return goodsInfoPicUrl;
                }

                public void setGoodsInfoPicUrl(String goodsInfoPicUrl) {
                    this.goodsInfoPicUrl = goodsInfoPicUrl;
                }

                public int getGoodsInfoSalePrice() {
                    return goodsInfoSalePrice;
                }

                public void setGoodsInfoSalePrice(int goodsInfoSalePrice) {
                    this.goodsInfoSalePrice = goodsInfoSalePrice;
                }

                public String getGoodsInfoSpecValue() {
                    return goodsInfoSpecValue;
                }

                public void setGoodsInfoSpecValue(String goodsInfoSpecValue) {
                    this.goodsInfoSpecValue = goodsInfoSpecValue;
                }

                public int getGoodsInfoWeight() {
                    return goodsInfoWeight;
                }

                public void setGoodsInfoWeight(int goodsInfoWeight) {
                    this.goodsInfoWeight = goodsInfoWeight;
                }

                public int getMemberPrice() {
                    return memberPrice;
                }

                public void setMemberPrice(int memberPrice) {
                    this.memberPrice = memberPrice;
                }

                public int getShareFlag() {
                    return shareFlag;
                }

                public void setShareFlag(int shareFlag) {
                    this.shareFlag = shareFlag;
                }
            }
        }
    }
}
