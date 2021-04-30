package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class AddShoppingCartResponse extends BaseResponse {

    /**
     * data : {"shoppingActivityCart":{"activeId":6,"activeType":0,"alreadyCheckedNum":0,"alreadyXMoney":0,"freeNumber":3,"fullXDiscountPrice":0,"fullXmoney":30000,"isActivityOk":0,"satisfactionMoney":29868,"satisfactionNum":0,"storeGoodsInfoCartList":[],"totalMoney":0,"totalWeight":0,"yNumber":0}}
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
         * shoppingActivityCart : {"activeId":6,"activeType":0,"alreadyCheckedNum":0,"alreadyXMoney":0,"freeNumber":3,"fullXDiscountPrice":0,"fullXmoney":30000,"isActivityOk":0,"satisfactionMoney":29868,"satisfactionNum":0,"storeGoodsInfoCartList":[],"totalMoney":0,"totalWeight":0,"yNumber":0}
         */

        private ShoppingActivityCartBean shoppingActivityCart;

        public ShoppingActivityCartBean getShoppingActivityCart() {
            return shoppingActivityCart;
        }

        public void setShoppingActivityCart(ShoppingActivityCartBean shoppingActivityCart) {
            this.shoppingActivityCart = shoppingActivityCart;
        }

        public static class ShoppingActivityCartBean {
            /**
             * activeId : 6
             * activeType : 0
             * alreadyCheckedNum : 0
             * alreadyXMoney : 0
             * freeNumber : 3
             * fullXDiscountPrice : 0
             * fullXmoney : 30000
             * isActivityOk : 0
             * satisfactionMoney : 29868
             * satisfactionNum : 0
             * storeGoodsInfoCartList : []
             * totalMoney : 0
             * totalWeight : 0
             * yNumber : 0
             */

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
            private int totalWeight;
            private int yNumber;
            private List<?> storeGoodsInfoCartList;

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

            public int getTotalWeight() {
                return totalWeight;
            }

            public void setTotalWeight(int totalWeight) {
                this.totalWeight = totalWeight;
            }

            public int getYNumber() {
                return yNumber;
            }

            public void setYNumber(int yNumber) {
                this.yNumber = yNumber;
            }

            public List<?> getStoreGoodsInfoCartList() {
                return storeGoodsInfoCartList;
            }

            public void setStoreGoodsInfoCartList(List<?> storeGoodsInfoCartList) {
                this.storeGoodsInfoCartList = storeGoodsInfoCartList;
            }
        }
    }
}
