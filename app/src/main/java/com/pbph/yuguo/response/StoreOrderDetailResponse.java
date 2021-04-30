package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:订单详情实体类
 */

public class StoreOrderDetailResponse extends BaseResponse {


    /**
     * data : {"orderDetail":{"createTime":"2019-05-14 16:25:55","orderCode":"A303879795297529856","orderDealPrice":1200,"orderGoodsList":[{"activeType":0,"activeTypeName":"","anewDeliverFlag":0,"evaluateFlag":1,"goodsCostPrice":900,"goodsGroupId":0,"goodsId":423,"goodsImg":"http://image.pbdsh.com/ygtestfold/28/9C3C84A13E6738A26126DEB0A4E532CC.png","goodsName":"测试(红色)","goodsNameSub":"副标题","goodsNumWeight":10,"goodsPack":"","goodsSalePrice":1100,"goodsSpec":"颜色:红色","goodsWeight":100,"memberPrice":100,"mxActivitySub":"","mzActivitySub":"","orderGoodsId":660,"orderId":455,"orderType":0,"packingCharges":100,"salesMethod":0,"shareFlag":1,"storeGoodsId":202,"storeGoodsInfoId":521,"storeId":154,"subtotalPrice":200}],"orderId":455,"orderOldPrice":1200,"orderPayWay":3,"orderPracticalPrice":0,"orderStatus":1,"storeName":"鹏博普华店","timeDifference":958}}
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
         * orderDetail : {"createTime":"2019-05-14 16:25:55","orderCode":"A303879795297529856","orderDealPrice":1200,"orderGoodsList":[{"activeType":0,"activeTypeName":"","anewDeliverFlag":0,"evaluateFlag":1,"goodsCostPrice":900,"goodsGroupId":0,"goodsId":423,"goodsImg":"http://image.pbdsh.com/ygtestfold/28/9C3C84A13E6738A26126DEB0A4E532CC.png","goodsName":"测试(红色)","goodsNameSub":"副标题","goodsNumWeight":10,"goodsPack":"","goodsSalePrice":1100,"goodsSpec":"颜色:红色","goodsWeight":100,"memberPrice":100,"mxActivitySub":"","mzActivitySub":"","orderGoodsId":660,"orderId":455,"orderType":0,"packingCharges":100,"salesMethod":0,"shareFlag":1,"storeGoodsId":202,"storeGoodsInfoId":521,"storeId":154,"subtotalPrice":200}],"orderId":455,"orderOldPrice":1200,"orderPayWay":3,"orderPracticalPrice":0,"orderStatus":1,"storeName":"鹏博普华店","timeDifference":958}
         */

        private OrderDetailBean orderDetail;

        public OrderDetailBean getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetailBean orderDetail) {
            this.orderDetail = orderDetail;
        }

        public static class OrderDetailBean {
            /**
             * createTime : 2019-05-14 16:25:55
             * orderCode : A303879795297529856
             * orderDealPrice : 1200
             * orderGoodsList : [{"activeType":0,"activeTypeName":"","anewDeliverFlag":0,"evaluateFlag":1,"goodsCostPrice":900,"goodsGroupId":0,"goodsId":423,"goodsImg":"http://image.pbdsh.com/ygtestfold/28/9C3C84A13E6738A26126DEB0A4E532CC.png","goodsName":"测试(红色)","goodsNameSub":"副标题","goodsNumWeight":10,"goodsPack":"","goodsSalePrice":1100,"goodsSpec":"颜色:红色","goodsWeight":100,"memberPrice":100,"mxActivitySub":"","mzActivitySub":"","orderGoodsId":660,"orderId":455,"orderType":0,"packingCharges":100,"salesMethod":0,"shareFlag":1,"storeGoodsId":202,"storeGoodsInfoId":521,"storeId":154,"subtotalPrice":200}]
             * orderId : 455
             * orderOldPrice : 1200
             * orderPayWay : 3
             * orderPracticalPrice : 0
             * orderStatus : 1
             * storeName : 鹏博普华店
             * timeDifference : 958
             */

            private String createTime;
            private String orderCode;
            private int orderDealPrice;
            private int orderId;
            private int orderOldPrice;
            private int orderDiscountsPrice;
            private int orderPayWay;
            private int orderPracticalPrice;
            private int orderStatus;
            private String storeName;
            private int timeDifference;
            private List<OrderGoodsListBean> orderGoodsList;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
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

            public int getOrderOldPrice() {
                return orderOldPrice;
            }

            public int getOrderDiscountsPrice() {
                return orderDiscountsPrice;
            }

            public void setOrderDiscountsPrice(int orderDiscountsPrice) {
                this.orderDiscountsPrice = orderDiscountsPrice;
            }

            public void setOrderOldPrice(int orderOldPrice) {
                this.orderOldPrice = orderOldPrice;
            }

            public int getOrderPayWay() {
                return orderPayWay;
            }

            public void setOrderPayWay(int orderPayWay) {
                this.orderPayWay = orderPayWay;
            }

            public int getOrderPracticalPrice() {
                return orderPracticalPrice;
            }

            public void setOrderPracticalPrice(int orderPracticalPrice) {
                this.orderPracticalPrice = orderPracticalPrice;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getTimeDifference() {
                return timeDifference;
            }

            public void setTimeDifference(int timeDifference) {
                this.timeDifference = timeDifference;
            }

            public List<OrderGoodsListBean> getOrderGoodsList() {
                return orderGoodsList;
            }

            public void setOrderGoodsList(List<OrderGoodsListBean> orderGoodsList) {
                this.orderGoodsList = orderGoodsList;
            }

            public static class OrderGoodsListBean {
                /**
                 * activeType : 0
                 * activeTypeName :
                 * anewDeliverFlag : 0
                 * evaluateFlag : 1
                 * goodsCostPrice : 900
                 * goodsGroupId : 0
                 * goodsId : 423
                 * goodsImg : http://image.pbdsh.com/ygtestfold/28/9C3C84A13E6738A26126DEB0A4E532CC.png
                 * goodsName : 测试(红色)
                 * goodsNameSub : 副标题
                 * goodsNumWeight : 10
                 * goodsPack :
                 * goodsSalePrice : 1100
                 * goodsSpec : 颜色:红色
                 * goodsWeight : 100
                 * memberPrice : 100
                 * mxActivitySub :
                 * mzActivitySub :
                 * orderGoodsId : 660
                 * orderId : 455
                 * orderType : 0
                 * packingCharges : 100
                 * salesMethod : 0
                 * shareFlag : 1
                 * storeGoodsId : 202
                 * storeGoodsInfoId : 521
                 * storeId : 154
                 * subtotalPrice : 200
                 */

                private int activeType;
                private String activeTypeName;
                private int anewDeliverFlag;
                private int evaluateFlag;
                private int goodsCostPrice;
                private int goodsGroupId;
                private int goodsId;
                private String goodsImg;
                private String goodsName;
                private String goodsNameSub;
                private int goodsNumWeight;
                private String goodsPack;
                private int goodsSalePrice;
                private String goodsSpec;
                private int goodsWeight;
                private int memberPrice;
                private String mxActivitySub;
                private String mzActivitySub;
                private int orderGoodsId;
                private int orderId;
                private int orderType;
                private int packingCharges;
                private int salesMethod;
                private int shareFlag;
                private int storeGoodsId;
                private int storeGoodsInfoId;
                private int storeId;
                private int subtotalPrice;

                public int getActiveType() {
                    return activeType;
                }

                public void setActiveType(int activeType) {
                    this.activeType = activeType;
                }

                public String getActiveTypeName() {
                    return activeTypeName;
                }

                public void setActiveTypeName(String activeTypeName) {
                    this.activeTypeName = activeTypeName;
                }

                public int getAnewDeliverFlag() {
                    return anewDeliverFlag;
                }

                public void setAnewDeliverFlag(int anewDeliverFlag) {
                    this.anewDeliverFlag = anewDeliverFlag;
                }

                public int getEvaluateFlag() {
                    return evaluateFlag;
                }

                public void setEvaluateFlag(int evaluateFlag) {
                    this.evaluateFlag = evaluateFlag;
                }

                public int getGoodsCostPrice() {
                    return goodsCostPrice;
                }

                public void setGoodsCostPrice(int goodsCostPrice) {
                    this.goodsCostPrice = goodsCostPrice;
                }

                public int getGoodsGroupId() {
                    return goodsGroupId;
                }

                public void setGoodsGroupId(int goodsGroupId) {
                    this.goodsGroupId = goodsGroupId;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getGoodsImg() {
                    return goodsImg;
                }

                public void setGoodsImg(String goodsImg) {
                    this.goodsImg = goodsImg;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getGoodsNameSub() {
                    return goodsNameSub;
                }

                public void setGoodsNameSub(String goodsNameSub) {
                    this.goodsNameSub = goodsNameSub;
                }

                public int getGoodsNumWeight() {
                    return goodsNumWeight;
                }

                public void setGoodsNumWeight(int goodsNumWeight) {
                    this.goodsNumWeight = goodsNumWeight;
                }

                public String getGoodsPack() {
                    return goodsPack;
                }

                public void setGoodsPack(String goodsPack) {
                    this.goodsPack = goodsPack;
                }

                public int getGoodsSalePrice() {
                    return goodsSalePrice;
                }

                public void setGoodsSalePrice(int goodsSalePrice) {
                    this.goodsSalePrice = goodsSalePrice;
                }

                public String getGoodsSpec() {
                    return goodsSpec;
                }

                public void setGoodsSpec(String goodsSpec) {
                    this.goodsSpec = goodsSpec;
                }

                public int getGoodsWeight() {
                    return goodsWeight;
                }

                public void setGoodsWeight(int goodsWeight) {
                    this.goodsWeight = goodsWeight;
                }

                public int getMemberPrice() {
                    return memberPrice;
                }

                public void setMemberPrice(int memberPrice) {
                    this.memberPrice = memberPrice;
                }

                public String getMxActivitySub() {
                    return mxActivitySub;
                }

                public void setMxActivitySub(String mxActivitySub) {
                    this.mxActivitySub = mxActivitySub;
                }

                public String getMzActivitySub() {
                    return mzActivitySub;
                }

                public void setMzActivitySub(String mzActivitySub) {
                    this.mzActivitySub = mzActivitySub;
                }

                public int getOrderGoodsId() {
                    return orderGoodsId;
                }

                public void setOrderGoodsId(int orderGoodsId) {
                    this.orderGoodsId = orderGoodsId;
                }

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public int getOrderType() {
                    return orderType;
                }

                public void setOrderType(int orderType) {
                    this.orderType = orderType;
                }

                public int getPackingCharges() {
                    return packingCharges;
                }

                public void setPackingCharges(int packingCharges) {
                    this.packingCharges = packingCharges;
                }

                public int getSalesMethod() {
                    return salesMethod;
                }

                public void setSalesMethod(int salesMethod) {
                    this.salesMethod = salesMethod;
                }

                public int getShareFlag() {
                    return shareFlag;
                }

                public void setShareFlag(int shareFlag) {
                    this.shareFlag = shareFlag;
                }

                public int getStoreGoodsId() {
                    return storeGoodsId;
                }

                public void setStoreGoodsId(int storeGoodsId) {
                    this.storeGoodsId = storeGoodsId;
                }

                public int getStoreGoodsInfoId() {
                    return storeGoodsInfoId;
                }

                public void setStoreGoodsInfoId(int storeGoodsInfoId) {
                    this.storeGoodsInfoId = storeGoodsInfoId;
                }

                public int getStoreId() {
                    return storeId;
                }

                public void setStoreId(int storeId) {
                    this.storeId = storeId;
                }

                public int getSubtotalPrice() {
                    return subtotalPrice;
                }

                public void setSubtotalPrice(int subtotalPrice) {
                    this.subtotalPrice = subtotalPrice;
                }
            }
        }
    }
}
