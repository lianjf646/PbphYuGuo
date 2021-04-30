package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/4 0004.
 * class note:订单详情实体类
 */

public class OrderDetailResponse extends BaseResponse {


    /**
     * data : {"orderDetail":{"backOrder":null,"backOrderFlag":0,"basicsPrice":0,"createTime":"2019-01-10 15:12:19","deliverymanName":"","deliverymanPhone":"","distributionTime":0,"expressStatusTime":"2019-01-10 15:30:55","openMembersPrice":16600,"orderCode":"A280757789177954304","orderDealPrice":124,"orderDiscountsPrice":0,"orderFreight":0,"orderGoodsInfoList":[{"activeType":0,"evaluateFlag":0,"goodsId":0,"goodsInfoId":133,"goodsInfoMemberPrice":123,"goodsInfoName":"","goodsInfoNameSub":"","goodsInfoNum":1,"goodsInfoPack":"","goodsInfoPackCharges":0,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"goodsInfoSpecValue":"颜色:红色;大小:XXL;重量:g","goodsInfoWeight":12,"memberPrice":0,"shareFlag":0}],"orderId":65,"orderOldPrice":1243,"orderPayWay":0,"orderStatus":1,"orderType":1,"overDistanceExplain":"超距运费：每超过1公里收取2元超距运费","overWeightExplain":"超重运费：每超过1kg收取2元超重运费","overhangPrice":0,"packingExpense":1,"pinkagePrice":0,"realPayFullXPrice":0,"receiverAddress":"哈尔滨泰海花园小区603","receiverName":"张毓鹏","receiverPhone":"18249799856","statusDescribe":"","superheavyPrice":0,"thirdOrderCode":"","timeDifference":2244}}
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
         * orderDetail : {"backOrder":null,"backOrderFlag":0,"basicsPrice":0,"createTime":"2019-01-10 15:12:19","deliverymanName":"","deliverymanPhone":"","distributionTime":0,"expressStatusTime":"2019-01-10 15:30:55","openMembersPrice":16600,"orderCode":"A280757789177954304","orderDealPrice":124,"orderDiscountsPrice":0,"orderFreight":0,"orderGoodsInfoList":[{"activeType":0,"evaluateFlag":0,"goodsId":0,"goodsInfoId":133,"goodsInfoMemberPrice":123,"goodsInfoName":"","goodsInfoNameSub":"","goodsInfoNum":1,"goodsInfoPack":"","goodsInfoPackCharges":0,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"goodsInfoSpecValue":"颜色:红色;大小:XXL;重量:g","goodsInfoWeight":12,"memberPrice":0,"shareFlag":0}],"orderId":65,"orderOldPrice":1243,"orderPayWay":0,"orderStatus":1,"orderType":1,"overDistanceExplain":"超距运费：每超过1公里收取2元超距运费","overWeightExplain":"超重运费：每超过1kg收取2元超重运费","overhangPrice":0,"packingExpense":1,"pinkagePrice":0,"realPayFullXPrice":0,"receiverAddress":"哈尔滨泰海花园小区603","receiverName":"张毓鹏","receiverPhone":"18249799856","statusDescribe":"","superheavyPrice":0,"thirdOrderCode":"","timeDifference":2244}
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
             * backOrder : null
             * backOrderFlag : 0
             * basicsPrice : 0
             * createTime : 2019-01-10 15:12:19
             * deliverymanName :
             * deliverymanPhone :
             * distributionTime : 0
             * expressStatusTime : 2019-01-10 15:30:55
             * openMembersPrice : 16600
             * orderCode : A280757789177954304
             * orderDealPrice : 124
             * orderDiscountsPrice : 0
             * orderFreight : 0
             * orderGoodsInfoList : [{"activeType":0,"evaluateFlag":0,"goodsId":0,"goodsInfoId":133,"goodsInfoMemberPrice":123,"goodsInfoName":"","goodsInfoNameSub":"","goodsInfoNum":1,"goodsInfoPack":"","goodsInfoPackCharges":0,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"goodsInfoSpecValue":"颜色:红色;大小:XXL;重量:g","goodsInfoWeight":12,"memberPrice":0,"shareFlag":0}]
             * orderId : 65
             * orderOldPrice : 1243
             * orderPayWay : 0
             * orderStatus : 1
             * orderType : 1
             * overDistanceExplain : 超距运费：每超过1公里收取2元超距运费
             * overWeightExplain : 超重运费：每超过1kg收取2元超重运费
             * overhangPrice : 0
             * packingExpense : 1
             * pinkagePrice : 0
             * realPayFullXPrice : 0
             * receiverAddress : 哈尔滨泰海花园小区603
             * receiverName : 张毓鹏
             * receiverPhone : 18249799856
             * statusDescribe :
             * superheavyPrice : 0
             * thirdOrderCode :
             * timeDifference : 2244
             */

            private BackOrderBean backOrder;
            private int backOrderFlag;
            private int anewDeliverFlag;
            private int basicsPrice;
            private String createTime;
            private String deliverymanName;
            private String deliverymanPhone;
            private int distributionTime;
            private String expressStatusTime;
            private int openMembersPrice;
            private int openMembersDiscountPrice;
            private int memberOpenDiscountPrice;
            private String orderCode;
            private int orderDealPrice;
            private int orderDiscountsPrice;
            private int orderFreight;
            private int orderId;
            private int orderOldPrice;
            private int orderPayWay;
            private int orderStatus;
            private int orderType;
            private String overDistanceExplain;
            private String overWeightExplain;
            private int overhangPrice;
            private int packingExpense;
            private int pinkagePrice;
            private int realPayFullXPrice;
            private String receiverAddress;
            private String receiverName;
            private String receiverPhone;
            private String statusDescribe;
            private int superheavyPrice;
            private String thirdOrderCode;
            private int timeDifference;
            private List<GoodsInfoBean> orderGoodsInfoList;
            private List<GoodsInfoBean> orderGoodsInfoSonList;


            public BackOrderBean getBackOrder() {
                return backOrder;
            }

            public void setBackOrder(BackOrderBean backOrder) {
                this.backOrder = backOrder;
            }

            public int getBackOrderFlag() {
                return backOrderFlag;
            }

            public void setBackOrderFlag(int backOrderFlag) {
                this.backOrderFlag = backOrderFlag;
            }

            public int getAnewDeliverFlag() {
                return anewDeliverFlag;
            }

            public void setAnewDeliverFlag(int anewDeliverFlag) {
                this.anewDeliverFlag = anewDeliverFlag;
            }

            public int getBasicsPrice() {
                return basicsPrice;
            }

            public void setBasicsPrice(int basicsPrice) {
                this.basicsPrice = basicsPrice;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDeliverymanName() {
                return deliverymanName;
            }

            public void setDeliverymanName(String deliverymanName) {
                this.deliverymanName = deliverymanName;
            }

            public String getDeliverymanPhone() {
                return deliverymanPhone;
            }

            public void setDeliverymanPhone(String deliverymanPhone) {
                this.deliverymanPhone = deliverymanPhone;
            }

            public int getDistributionTime() {
                return distributionTime;
            }

            public void setDistributionTime(int distributionTime) {
                this.distributionTime = distributionTime;
            }

            public String getExpressStatusTime() {
                return expressStatusTime;
            }

            public void setExpressStatusTime(String expressStatusTime) {
                this.expressStatusTime = expressStatusTime;
            }

            public int getOpenMembersPrice() {
                return openMembersPrice;
            }

            public void setOpenMembersPrice(int openMembersPrice) {
                this.openMembersPrice = openMembersPrice;
            }

            public int getOpenMembersDiscountPrice() {
                return openMembersDiscountPrice;
            }

            public void setOpenMembersDiscountPrice(int openMembersDiscountPrice) {
                this.openMembersDiscountPrice = openMembersDiscountPrice;
            }

            public int getMemberOpenDiscountPrice() {
                return memberOpenDiscountPrice;
            }

            public void setMemberOpenDiscountPrice(int memberOpenDiscountPrice) {
                this.memberOpenDiscountPrice = memberOpenDiscountPrice;
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

            public int getOrderDiscountsPrice() {
                return orderDiscountsPrice;
            }

            public void setOrderDiscountsPrice(int orderDiscountsPrice) {
                this.orderDiscountsPrice = orderDiscountsPrice;
            }

            public int getOrderFreight() {
                return orderFreight;
            }

            public void setOrderFreight(int orderFreight) {
                this.orderFreight = orderFreight;
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

            public void setOrderOldPrice(int orderOldPrice) {
                this.orderOldPrice = orderOldPrice;
            }

            public int getOrderPayWay() {
                return orderPayWay;
            }

            public void setOrderPayWay(int orderPayWay) {
                this.orderPayWay = orderPayWay;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public String getOverDistanceExplain() {
                return overDistanceExplain;
            }

            public void setOverDistanceExplain(String overDistanceExplain) {
                this.overDistanceExplain = overDistanceExplain;
            }

            public String getOverWeightExplain() {
                return overWeightExplain;
            }

            public void setOverWeightExplain(String overWeightExplain) {
                this.overWeightExplain = overWeightExplain;
            }

            public int getOverhangPrice() {
                return overhangPrice;
            }

            public void setOverhangPrice(int overhangPrice) {
                this.overhangPrice = overhangPrice;
            }

            public int getPackingExpense() {
                return packingExpense;
            }

            public void setPackingExpense(int packingExpense) {
                this.packingExpense = packingExpense;
            }

            public int getPinkagePrice() {
                return pinkagePrice;
            }

            public void setPinkagePrice(int pinkagePrice) {
                this.pinkagePrice = pinkagePrice;
            }

            public int getRealPayFullXPrice() {
                return realPayFullXPrice;
            }

            public void setRealPayFullXPrice(int realPayFullXPrice) {
                this.realPayFullXPrice = realPayFullXPrice;
            }

            public String getReceiverAddress() {
                return receiverAddress;
            }

            public void setReceiverAddress(String receiverAddress) {
                this.receiverAddress = receiverAddress;
            }

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
            }

            public String getReceiverPhone() {
                return receiverPhone;
            }

            public void setReceiverPhone(String receiverPhone) {
                this.receiverPhone = receiverPhone;
            }

            public String getStatusDescribe() {
                return statusDescribe;
            }

            public void setStatusDescribe(String statusDescribe) {
                this.statusDescribe = statusDescribe;
            }

            public int getSuperheavyPrice() {
                return superheavyPrice;
            }

            public void setSuperheavyPrice(int superheavyPrice) {
                this.superheavyPrice = superheavyPrice;
            }

            public String getThirdOrderCode() {
                return thirdOrderCode;
            }

            public void setThirdOrderCode(String thirdOrderCode) {
                this.thirdOrderCode = thirdOrderCode;
            }

            public int getTimeDifference() {
                return timeDifference;
            }

            public void setTimeDifference(int timeDifference) {
                this.timeDifference = timeDifference;
            }

            public List<GoodsInfoBean> getOrderGoodsInfoList() {
                return orderGoodsInfoList;
            }

            public void setOrderGoodsInfoList(List<GoodsInfoBean> orderGoodsInfoList) {
                this.orderGoodsInfoList = orderGoodsInfoList;
            }

            public List<GoodsInfoBean> getOrderGoodsInfoSonList() {
                return orderGoodsInfoSonList;
            }

            public void setOrderGoodsInfoSonList(List<GoodsInfoBean> orderGoodsInfoSonList) {
                this.orderGoodsInfoSonList = orderGoodsInfoSonList;
            }

            public static class BackOrderBean {

                private String backOrderCode;
                private String backOrderCause;
                private double backOrderPrice;
                private int backOrderStatus;

                public String getBackOrderCode() {
                    return backOrderCode;
                }

                public void setBackOrderCode(String backOrderCode) {
                    this.backOrderCode = backOrderCode;
                }

                public String getBackOrderCause() {
                    return backOrderCause;
                }

                public void setBackOrderCause(String backOrderCause) {
                    this.backOrderCause = backOrderCause;
                }

                public double getBackOrderPrice() {
                    return backOrderPrice;
                }

                public void setBackOrderPrice(double backOrderPrice) {
                    this.backOrderPrice = backOrderPrice;
                }

                public int getBackOrderStatus() {
                    return backOrderStatus;
                }

                public void setBackOrderStatus(int backOrderStatus) {
                    this.backOrderStatus = backOrderStatus;
                }
            }
        }
    }
}
