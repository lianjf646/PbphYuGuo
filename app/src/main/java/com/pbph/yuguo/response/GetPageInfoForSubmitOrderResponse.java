package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GetPageInfoForSubmitOrderResponse extends BaseResponse {

    /**
     * data : {"resultMap":{"actualPrice":37,"basedFreight":0,"basic":0,"coupon":null,"deliveryType":0,"discountPrice":0,"freight":0,"goodsInfoList":[{"activeType":0,"avaliableFlag":1,"goodsId":290,"goodsInfoId":23,"goodsInfoMemberPrice":36,"goodsInfoName":"大枣(红色XL),而提供人","goodsInfoNameSub":"副标题","goodsInfoNum":1,"goodsInfoPack":"包装","goodsInfoPackCharges":1,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsInfoSalePrice":36,"goodsInfoSpecValue":"颜色:红色;大小:XL","goodsInfoWeight":12,"storeGoodsInfoId":61,"storeId":124}],"goodsInfoSonList":[],"goodsInfoSumCount":1,"goodsInfoSumWeight":12,"menberFlag":2,"menberOpenDiscountPrice":16600,"menberOpenPrice":16600,"overDistance":0,"overDistanceExplain":"超距运费：每超过1公里收取2元超距运费","overWeight":0,"overWeightExplain":"超重运费：每超过1kg收取2元超重运费","packingChargesTotal":1,"pinkageActivityId":0,"realPayFullXPrice":0,"sumPrice":36,"timeUnit":4}}
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
         * resultMap : {"actualPrice":37,"basedFreight":0,"basic":0,"coupon":null,"deliveryType":0,"discountPrice":0,"freight":0,"goodsInfoList":[{"activeType":0,"avaliableFlag":1,"goodsId":290,"goodsInfoId":23,"goodsInfoMemberPrice":36,"goodsInfoName":"大枣(红色XL),而提供人","goodsInfoNameSub":"副标题","goodsInfoNum":1,"goodsInfoPack":"包装","goodsInfoPackCharges":1,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsInfoSalePrice":36,"goodsInfoSpecValue":"颜色:红色;大小:XL","goodsInfoWeight":12,"storeGoodsInfoId":61,"storeId":124}],"goodsInfoSonList":[],"goodsInfoSumCount":1,"goodsInfoSumWeight":12,"menberFlag":2,"menberOpenDiscountPrice":16600,"menberOpenPrice":16600,"overDistance":0,"overDistanceExplain":"超距运费：每超过1公里收取2元超距运费","overWeight":0,"overWeightExplain":"超重运费：每超过1kg收取2元超重运费","packingChargesTotal":1,"pinkageActivityId":0,"realPayFullXPrice":0,"sumPrice":36,"timeUnit":4}
         */

        private ResultMapBean resultMap;

        public ResultMapBean getResultMap() {
            return resultMap;
        }

        public void setResultMap(ResultMapBean resultMap) {
            this.resultMap = resultMap;
        }

        public static class ResultMapBean {
            /**
             * actualPrice : 37
             * basedFreight : 0
             * basic : 0
             * coupon : null
             * deliveryType : 0
             * discountPrice : 0
             * freight : 0
             * goodsInfoList : [{"activeType":0,"avaliableFlag":1,"goodsId":290,"goodsInfoId":23,"goodsInfoMemberPrice":36,"goodsInfoName":"大枣(红色XL),而提供人","goodsInfoNameSub":"副标题","goodsInfoNum":1,"goodsInfoPack":"包装","goodsInfoPackCharges":1,"goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsInfoSalePrice":36,"goodsInfoSpecValue":"颜色:红色;大小:XL","goodsInfoWeight":12,"storeGoodsInfoId":61,"storeId":124}]
             * goodsInfoSonList : []
             * goodsInfoSumCount : 1
             * goodsInfoSumWeight : 12
             * menberFlag : 2
             * menberOpenDiscountPrice : 16600
             * menberOpenPrice : 16600
             * overDistance : 0
             * overDistanceExplain : 超距运费：每超过1公里收取2元超距运费
             * overWeight : 0
             * overWeightExplain : 超重运费：每超过1kg收取2元超重运费
             * packingChargesTotal : 1
             * pinkageActivityId : 0
             * realPayFullXPrice : 0
             * sumPrice : 36
             * timeUnit : 4
             */

            private int actualPrice;
            private int basedFreight;
            private int basic;
            private CouponBean coupon;
            private int couponCount;
            private int deliveryType;
            private int discountPrice;
            private int freight;
            private int goodsInfoSumCount;
            private int goodsInfoSumWeight;
            private int memberFlag;

            private int memberOpenDiscountPrice;
            private int memberOpenPrice;
            private int overDistance;
            private String overDistanceExplain;
            private int overWeight;
            private String overWeightExplain;
            private int packingChargesTotal;
            private int pinkageActivityId;
            private int realPayFullXPrice;
            private int sumPrice;
            private int timeUnit;
            private List<GoodsInfoBean> goodsInfoList;
            private List<GoodsInfoBean> goodsInfoSonList;


            private int fullPresentGoodsCount = 0;
            private int fullXGoodsCount = 0;

            public int getFullPresentGoodsCount() {
                return fullPresentGoodsCount;
            }

            public void setFullPresentGoodsCount(int fullPresentGoodsCount) {
                this.fullPresentGoodsCount = fullPresentGoodsCount;
            }

            public int getFullXGoodsCount() {
                return fullXGoodsCount;
            }

            public void setFullXGoodsCount(int fullXGoodsCount) {
                this.fullXGoodsCount = fullXGoodsCount;
            }

            public int getCouponCount() {
                return couponCount;
            }

            public void setCouponCount(int couponCount) {
                this.couponCount = couponCount;
            }

            public int getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(int actualPrice) {
                this.actualPrice = actualPrice;
            }

            public int getBasedFreight() {
                return basedFreight;
            }

            public void setBasedFreight(int basedFreight) {
                this.basedFreight = basedFreight;
            }

            public int getBasic() {
                return basic;
            }

            public void setBasic(int basic) {
                this.basic = basic;
            }

            public CouponBean getCoupon() {
                return coupon;
            }

            public void setCoupon(CouponBean coupon) {
                this.coupon = coupon;
            }

            public int getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(int deliveryType) {
                this.deliveryType = deliveryType;
            }

            public int getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(int discountPrice) {
                this.discountPrice = discountPrice;
            }

            public int getFreight() {
                return freight;
            }

            public void setFreight(int freight) {
                this.freight = freight;
            }

            public int getGoodsInfoSumCount() {
                return goodsInfoSumCount;
            }

            public void setGoodsInfoSumCount(int goodsInfoSumCount) {
                this.goodsInfoSumCount = goodsInfoSumCount;
            }

            public int getGoodsInfoSumWeight() {
                return goodsInfoSumWeight;
            }

            public void setGoodsInfoSumWeight(int goodsInfoSumWeight) {
                this.goodsInfoSumWeight = goodsInfoSumWeight;
            }

            public int getMemberFlag() {
                return memberFlag;
            }

            public void setMemberFlag(int memberFlag) {
                this.memberFlag = memberFlag;
            }

            public int getMemberOpenDiscountPrice() {
                return memberOpenDiscountPrice;
            }

            public void setMemberOpenDiscountPrice(int menberOpenDiscountPrice) {
                this.memberOpenDiscountPrice = menberOpenDiscountPrice;
            }

            public int getMemberOpenPrice() {
                return memberOpenPrice;
            }

            public void setMemberOpenPrice(int memberOpenPrice) {
                this.memberOpenPrice = memberOpenPrice;
            }

            public int getOverDistance() {
                return overDistance;
            }

            public void setOverDistance(int overDistance) {
                this.overDistance = overDistance;
            }

            public String getOverDistanceExplain() {
                return overDistanceExplain;
            }

            public void setOverDistanceExplain(String overDistanceExplain) {
                this.overDistanceExplain = overDistanceExplain;
            }

            public int getOverWeight() {
                return overWeight;
            }

            public void setOverWeight(int overWeight) {
                this.overWeight = overWeight;
            }

            public String getOverWeightExplain() {
                return overWeightExplain;
            }

            public void setOverWeightExplain(String overWeightExplain) {
                this.overWeightExplain = overWeightExplain;
            }

            public int getPackingChargesTotal() {
                return packingChargesTotal;
            }

            public void setPackingChargesTotal(int packingChargesTotal) {
                this.packingChargesTotal = packingChargesTotal;
            }

            public int getPinkageActivityId() {
                return pinkageActivityId;
            }

            public void setPinkageActivityId(int pinkageActivityId) {
                this.pinkageActivityId = pinkageActivityId;
            }

            public int getRealPayFullXPrice() {
                return realPayFullXPrice;
            }

            public void setRealPayFullXPrice(int realPayFullXPrice) {
                this.realPayFullXPrice = realPayFullXPrice;
            }

            public int getSumPrice() {
                return sumPrice;
            }

            public void setSumPrice(int sumPrice) {
                this.sumPrice = sumPrice;
            }

            public int getTimeUnit() {
                return timeUnit;
            }

            public void setTimeUnit(int timeUnit) {
                this.timeUnit = timeUnit;
            }

            public List<GoodsInfoBean> getGoodsInfoList() {
                return goodsInfoList;
            }

            public void setGoodsInfoList(List<GoodsInfoBean> goodsInfoList) {
                this.goodsInfoList = goodsInfoList;
            }

            public List<GoodsInfoBean> getGoodsInfoSonList() {
                return goodsInfoSonList;
            }

            public void setGoodsInfoSonList(List<GoodsInfoBean> goodsInfoSonList) {
                this.goodsInfoSonList = goodsInfoSonList;
            }

            public static class CouponBean {
                private int couponId;
                private String couponName;
                private long couponPrice;
                private int couponRuleType;
                private long couponXPrice;
                //                private int goodsId;
                private int customerId;

                public int getCouponId() {
                    return couponId;
                }

                public void setCouponId(int couponId) {
                    this.couponId = couponId;
                }

                public String getCouponName() {
                    return couponName;
                }

                public void setCouponName(String couponName) {
                    this.couponName = couponName;
                }

                public long getCouponPrice() {
                    return couponPrice;
                }

                public void setCouponPrice(long couponPrice) {
                    this.couponPrice = couponPrice;
                }

                public int getCouponRuleType() {
                    return couponRuleType;
                }

                public void setCouponRuleType(int couponRuleType) {
                    this.couponRuleType = couponRuleType;
                }

                public long getCouponXPrice() {
                    return couponXPrice;
                }

                public void setCouponXPrice(long couponXPrice) {
                    this.couponXPrice = couponXPrice;
                }

                /*public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }*/

                public int getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(int customerId) {
                    this.customerId = customerId;
                }
            }

        }
    }
}
