package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetShoppingCartResponse extends BaseResponse {

    /**
     * data : {"totalPrice":72,"noActivityGoodsCartList":[],"shoppingActivityList":[{"activeId":8,"activeType":1,"alreadyCheckedNum":2,"alreadyXMoney":0,"freeNumber":0,"fullXDiscountPrice":0,"fullXmoney":20000,"isActivityOk":0,"satisfactionMoney":0,"satisfactionNum":1,"storeGoodsInfoCartList":[{"avaliableFlag":1,"goodsInfoId":23,"goodsInfoPackCharges":1,"goodsMemberPrice":36,"goodsName":"大枣(红色XL),而提供人","goodsNo":"201812290948567580","goodsNum":2,"goodsPack":"包装","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":36,"goodsSpec":"颜色:红色,大小:XL","goodsSub":"副标题","goodsVipFlag":0,"goodsWeight":12,"isChecked":1,"saleFlag":1,"shoppingCartId":19,"storageNum":64,"storeGoodsInfoId":61,"storeId":124}],"totalMoney":72,"yNumber":3}],"avaliableStoreGoodsInfoList":[{"avaliableFlag":0,"goodsInfoId":59,"goodsInfoPackCharges":1,"goodsMemberPrice":123,"goodsName":"苹果(红色),而额头","goodsNo":"201901072107129950","goodsNum":1,"goodsPack":"","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":123,"goodsSpec":"颜色:红色","goodsSub":"","goodsVipFlag":0,"goodsWeight":12,"isChecked":1,"saleFlag":1,"shoppingCartId":20,"storageNum":0,"storeGoodsInfoId":144,"storeId":124}]}
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
         * totalPrice : 72
         * noActivityGoodsCartList : []
         * shoppingActivityList : [{"activeId":8,"activeType":1,"alreadyCheckedNum":2,"alreadyXMoney":0,"freeNumber":0,"fullXDiscountPrice":0,"fullXmoney":20000,"isActivityOk":0,"satisfactionMoney":0,"satisfactionNum":1,"storeGoodsInfoCartList":[{"avaliableFlag":1,"goodsInfoId":23,"goodsInfoPackCharges":1,"goodsMemberPrice":36,"goodsName":"大枣(红色XL),而提供人","goodsNo":"201812290948567580","goodsNum":2,"goodsPack":"包装","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":36,"goodsSpec":"颜色:红色,大小:XL","goodsSub":"副标题","goodsVipFlag":0,"goodsWeight":12,"isChecked":1,"saleFlag":1,"shoppingCartId":19,"storageNum":64,"storeGoodsInfoId":61,"storeId":124}],"totalMoney":72,"yNumber":3}]
         * avaliableStoreGoodsInfoList : [{"avaliableFlag":0,"goodsInfoId":59,"goodsInfoPackCharges":1,"goodsMemberPrice":123,"goodsName":"苹果(红色),而额头","goodsNo":"201901072107129950","goodsNum":1,"goodsPack":"","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":123,"goodsSpec":"颜色:红色","goodsSub":"","goodsVipFlag":0,"goodsWeight":12,"isChecked":1,"saleFlag":1,"shoppingCartId":20,"storageNum":0,"storeGoodsInfoId":144,"storeId":124}]
         */

        private int preferencePrice;
        private int weigthFlag;
        private int totalPrice;
        private int totalWeight;
        private int limitNumFlag;
        private List<StoreGoodsInfoListBean> noActivityGoodsCartList;
        private List<ShoppingActivityListBean> shoppingActivityList;
        private List<StoreGoodsInfoListBean> avaliableStoreGoodsInfoList;
        private MemberDayActivityBean memberDayActivity;

        public MemberDayActivityBean getMemberDayActivity() {
            return memberDayActivity;
        }

        public void setMemberDayActivity(MemberDayActivityBean memberDayActivity) {
            this.memberDayActivity = memberDayActivity;
        }

        public int getPreferencePrice() {
            return preferencePrice;
        }

        public void setPreferencePrice(int preferencePrice) {
            this.preferencePrice = preferencePrice;
        }

        public int getLimitNumFlag() {
            return limitNumFlag;
        }

        public void setLimitNumFlag(int limitNumFlag) {
            this.limitNumFlag = limitNumFlag;
        }

        public int getWeigthFlag() {
            return weigthFlag;
        }

        public void setWeigthFlag(int weigthFlag) {
            this.weigthFlag = weigthFlag;
        }

        public int getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(int totalWeight) {
            this.totalWeight = totalWeight;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public List<StoreGoodsInfoListBean> getNoActivityGoodsCartList() {
            return noActivityGoodsCartList;
        }

        public void setNoActivityGoodsCartList(List<StoreGoodsInfoListBean> noActivityGoodsCartList) {
            this.noActivityGoodsCartList = noActivityGoodsCartList;
        }

        public List<ShoppingActivityListBean> getShoppingActivityList() {
            return shoppingActivityList;
        }

        public void setShoppingActivityList(List<ShoppingActivityListBean> shoppingActivityList) {
            this.shoppingActivityList = shoppingActivityList;
        }

        public List<StoreGoodsInfoListBean> getAvaliableStoreGoodsInfoList() {
            return avaliableStoreGoodsInfoList;
        }

        public void setAvaliableStoreGoodsInfoList(List<StoreGoodsInfoListBean> avaliableStoreGoodsInfoList) {
            this.avaliableStoreGoodsInfoList = avaliableStoreGoodsInfoList;
        }

        public static class MemberDayActivityBean {
            private int memberDayActivityId;
            private String activeName;
            private String startTime;
            private String endTime;
            private String discount;
            private List<StoreGoodsInfoListBean> storeGoodsInfoMemberDayList;

            public int getMemberDayActivityId() {
                return memberDayActivityId;
            }

            public void setMemberDayActivityId(int memberDayActivityId) {
                this.memberDayActivityId = memberDayActivityId;
            }

            public String getActiveName() {
                return activeName;
            }

            public void setActiveName(String activeName) {
                this.activeName = activeName;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public List<StoreGoodsInfoListBean> getStoreGoodsInfoMemberDayList() {
                return storeGoodsInfoMemberDayList;
            }

            public void setStoreGoodsInfoMemberDayList(List<StoreGoodsInfoListBean> storeGoodsInfoMemberDayList) {
                this.storeGoodsInfoMemberDayList = storeGoodsInfoMemberDayList;
            }
        }

        public static class ShoppingActivityListBean {
            /**
             * activeId : 8
             * activeType : 1
             * alreadyCheckedNum : 2
             * alreadyXMoney : 0
             * freeNumber : 0
             * fullXDiscountPrice : 0
             * fullXmoney : 20000
             * isActivityOk : 0
             * satisfactionMoney : 0
             * satisfactionNum : 1
             * storeGoodsInfoCartList : [{"avaliableFlag":1,"goodsInfoId":23,"goodsInfoPackCharges":1,"goodsMemberPrice":36,"goodsName":"大枣(红色XL),而提供人","goodsNo":"201812290948567580","goodsNum":2,"goodsPack":"包装","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":36,"goodsSpec":"颜色:红色,大小:XL","goodsSub":"副标题","goodsVipFlag":0,"goodsWeight":12,"isChecked":1,"saleFlag":1,"shoppingCartId":19,"storageNum":64,"storeGoodsInfoId":61,"storeId":124}]
             * totalMoney : 72
             * yNumber : 3
             */


            private int totalWeight;


            private int activeId;
            private int activeType;
            private int alreadyCheckedNum;
            private int alreadyXMoney;
            private int freeNumber;
            private int fullXDiscountPrice;
            private int fullXmoney;
            private int isActivityOk;
            private int satisfactionMoney;
            private int satisfactionNum;
            private int totalMoney;
            private int yNumber;
            private List<StoreGoodsInfoListBean> storeGoodsInfoCartList;

            public int getTotalWeight() {
                return totalWeight;
            }

            public void setTotalWeight(int totalWeight) {
                this.totalWeight = totalWeight;
            }

            public int getActiveId() {
                return activeId;
            }

            public void setActiveId(int activeId) {
                this.activeId = activeId;
            }

            public int getActiveType() {
                return activeType;
            }

            public void setActiveType(int activeType) {
                this.activeType = activeType;
            }

            public int getAlreadyCheckedNum() {
                return alreadyCheckedNum;
            }

            public void setAlreadyCheckedNum(int alreadyCheckedNum) {
                this.alreadyCheckedNum = alreadyCheckedNum;
            }

            public int getAlreadyXMoney() {
                return alreadyXMoney;
            }

            public void setAlreadyXMoney(int alreadyXMoney) {
                this.alreadyXMoney = alreadyXMoney;
            }

            public int getFreeNumber() {
                return freeNumber;
            }

            public void setFreeNumber(int freeNumber) {
                this.freeNumber = freeNumber;
            }

            public int getFullXDiscountPrice() {
                return fullXDiscountPrice;
            }

            public void setFullXDiscountPrice(int fullXDiscountPrice) {
                this.fullXDiscountPrice = fullXDiscountPrice;
            }

            public int getFullXmoney() {
                return fullXmoney;
            }

            public void setFullXmoney(int fullXmoney) {
                this.fullXmoney = fullXmoney;
            }

            public int getIsActivityOk() {
                return isActivityOk;
            }

            public void setIsActivityOk(int isActivityOk) {
                this.isActivityOk = isActivityOk;
            }

            public int getSatisfactionMoney() {
                return satisfactionMoney;
            }

            public void setSatisfactionMoney(int satisfactionMoney) {
                this.satisfactionMoney = satisfactionMoney;
            }

            public int getSatisfactionNum() {
                return satisfactionNum;
            }

            public void setSatisfactionNum(int satisfactionNum) {
                this.satisfactionNum = satisfactionNum;
            }

            public int getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(int totalMoney) {
                this.totalMoney = totalMoney;
            }

            public int getYNumber() {
                return yNumber;
            }

            public void setYNumber(int yNumber) {
                this.yNumber = yNumber;
            }

            public List<StoreGoodsInfoListBean> getStoreGoodsInfoCartList() {
                return storeGoodsInfoCartList;
            }

            public void setStoreGoodsInfoCartList(List<StoreGoodsInfoListBean> storeGoodsInfoCartList) {
                this.storeGoodsInfoCartList = storeGoodsInfoCartList;
            }


        }

        public static class StoreGoodsInfoListBean {
            /**
             * avaliableFlag : 0
             * goodsInfoId : 59
             * goodsInfoPackCharges : 1
             * goodsMemberPrice : 123
             * goodsName : 苹果(红色),而额头
             * goodsNo : 201901072107129950
             * goodsNum : 1
             * goodsPack :
             * goodsPicUrl : http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png
             * goodsSalePrice : 123
             * goodsSpec : 颜色:红色
             * goodsSub :
             * goodsVipFlag : 0
             * goodsWeight : 12
             * isChecked : 1
             * saleFlag : 1
             * shoppingCartId : 20
             * storageNum : 0
             * storeGoodsInfoId : 144
             * storeId : 124
             */

            private int avaliableFlag;
            private int goodsInfoId;
            private int goodsInfoPackCharges;
            private int goodsMemberPrice;
            private String goodsName;
            private String goodsNo;
            private int goodsNum;
            private String goodsPack;
            private String goodsPicUrl;
            private int goodsSalePrice;
            private String goodsSpec;
            private String goodsSub;
            private int goodsVipFlag;
            private int goodsWeight;
            private int isChecked;
            private int purchaseLimitationNum;
            private int saleFlag;
            private int shoppingCartId;
            private int storageNum;
            private int storeGoodsInfoId;
            private int storeId;

            public int getPurchaseLimitationNum() {
                return purchaseLimitationNum;
            }

            public void setPurchaseLimitationNum(int purchaseLimitationNum) {
                this.purchaseLimitationNum = purchaseLimitationNum;
            }

            public int getAvaliableFlag() {
                return avaliableFlag;
            }

            public void setAvaliableFlag(int avaliableFlag) {
                this.avaliableFlag = avaliableFlag;
            }

            public int getGoodsInfoId() {
                return goodsInfoId;
            }

            public void setGoodsInfoId(int goodsInfoId) {
                this.goodsInfoId = goodsInfoId;
            }

            public int getGoodsInfoPackCharges() {
                return goodsInfoPackCharges;
            }

            public void setGoodsInfoPackCharges(int goodsInfoPackCharges) {
                this.goodsInfoPackCharges = goodsInfoPackCharges;
            }

            public int getGoodsMemberPrice() {
                return goodsMemberPrice;
            }

            public void setGoodsMemberPrice(int goodsMemberPrice) {
                this.goodsMemberPrice = goodsMemberPrice;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsNo() {
                return goodsNo;
            }

            public void setGoodsNo(String goodsNo) {
                this.goodsNo = goodsNo;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getGoodsPack() {
                return goodsPack;
            }

            public void setGoodsPack(String goodsPack) {
                this.goodsPack = goodsPack;
            }

            public String getGoodsPicUrl() {
                return goodsPicUrl;
            }

            public void setGoodsPicUrl(String goodsPicUrl) {
                this.goodsPicUrl = goodsPicUrl;
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

            public String getGoodsSub() {
                return goodsSub;
            }

            public void setGoodsSub(String goodsSub) {
                this.goodsSub = goodsSub;
            }

            public int getGoodsVipFlag() {
                return goodsVipFlag;
            }

            public void setGoodsVipFlag(int goodsVipFlag) {
                this.goodsVipFlag = goodsVipFlag;
            }

            public int getGoodsWeight() {
                return goodsWeight;
            }

            public void setGoodsWeight(int goodsWeight) {
                this.goodsWeight = goodsWeight;
            }

            public int getIsChecked() {
                return isChecked;
            }

            public void setIsChecked(int isChecked) {
                this.isChecked = isChecked;
            }

            public int getSaleFlag() {
                return saleFlag;
            }

            public void setSaleFlag(int saleFlag) {
                this.saleFlag = saleFlag;
            }

            public int getShoppingCartId() {
                return shoppingCartId;
            }

            public void setShoppingCartId(int shoppingCartId) {
                this.shoppingCartId = shoppingCartId;
            }

            public int getStorageNum() {
                return storageNum;
            }

            public void setStorageNum(int storageNum) {
                this.storageNum = storageNum;
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
        }
    }
}
