package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GroupListResponse extends BaseResponse {

    /**
     * data : {"totleSize":1,"list":[{"alreadyGroupNumber":5,"currentTime":1547023966005,"endDatetime":1549698300000,"goodsGroupActivityId":1,"goodsGroupId":4,"goodsGroupMarketPrice":100,"goodsGroupName":"大包子","goodsGroupNutritiveValue":"","goodsGroupOrigin":"","goodsGroupPack":"1","goodsGroupPicUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsGroupPrice":20,"goodsGroupSalePrice":500,"goodsGroupSavingMode":"","goodsGroupSpec":"","goodsGroupWeight":0,"groupImg":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","groupNumber":10,"groupPrice":20,"offeredCount":5,"promotionName":"大橙子","startDatetime":1545810000000}]}
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
         * totleSize : 1
         * list : [{"alreadyGroupNumber":5,"currentTime":1547023966005,"endDatetime":1549698300000,"goodsGroupActivityId":1,"goodsGroupId":4,"goodsGroupMarketPrice":100,"goodsGroupName":"大包子","goodsGroupNutritiveValue":"","goodsGroupOrigin":"","goodsGroupPack":"1","goodsGroupPicUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsGroupPrice":20,"goodsGroupSalePrice":500,"goodsGroupSavingMode":"","goodsGroupSpec":"","goodsGroupWeight":0,"groupImg":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","groupNumber":10,"groupPrice":20,"offeredCount":5,"promotionName":"大橙子","startDatetime":1545810000000}]
         */

        private int totleSize;
        private List<ListBean> list;

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * alreadyGroupNumber : 5
             * currentTime : 1547023966005
             * endDatetime : 1549698300000
             * goodsGroupActivityId : 1
             * goodsGroupId : 4
             * goodsGroupMarketPrice : 100
             * goodsGroupName : 大包子
             * goodsGroupNutritiveValue :
             * goodsGroupOrigin :
             * goodsGroupPack : 1
             * goodsGroupPicUrl : http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg
             * goodsGroupPrice : 20
             * goodsGroupSalePrice : 500
             * goodsGroupSavingMode :
             * goodsGroupSpec :
             * goodsGroupWeight : 0
             * groupImg : http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg
             * groupNumber : 10
             * groupPrice : 20
             * offeredCount : 5
             * promotionName : 大橙子
             * startDatetime : 1545810000000
             */

            public long passTime;
            public int type;
            private int alreadyGroupNumber;
            private long currentTime;
            private long endDatetime;
            private int goodsGroupActivityId;
            private int goodsGroupId;
            private int goodsGroupMarketPrice;
            private String goodsGroupName;
            private String goodsGroupNutritiveValue;
            private String goodsGroupOrigin;
            private String goodsGroupPack;
            private String goodsGroupPicUrl;
            private int goodsGroupPrice;
            private int goodsGroupSalePrice;
            private String goodsGroupSavingMode;
            private String goodsGroupSpec;
            private int goodsGroupWeight;
            private String groupImg;
            private int groupNumber;
            private int groupPrice;
            private int offeredCount;
            private String promotionName;
            private long startDatetime;

            public int getAlreadyGroupNumber() {
                return alreadyGroupNumber;
            }

            public void setAlreadyGroupNumber(int alreadyGroupNumber) {
                this.alreadyGroupNumber = alreadyGroupNumber;
            }

            public long getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(long currentTime) {
                this.currentTime = currentTime;
            }

            public long getEndDatetime() {
                return endDatetime;
            }

            public void setEndDatetime(long endDatetime) {
                this.endDatetime = endDatetime;
            }

            public int getGoodsGroupActivityId() {
                return goodsGroupActivityId;
            }

            public void setGoodsGroupActivityId(int goodsGroupActivityId) {
                this.goodsGroupActivityId = goodsGroupActivityId;
            }

            public int getGoodsGroupId() {
                return goodsGroupId;
            }

            public void setGoodsGroupId(int goodsGroupId) {
                this.goodsGroupId = goodsGroupId;
            }

            public int getGoodsGroupMarketPrice() {
                return goodsGroupMarketPrice;
            }

            public void setGoodsGroupMarketPrice(int goodsGroupMarketPrice) {
                this.goodsGroupMarketPrice = goodsGroupMarketPrice;
            }

            public String getGoodsGroupName() {
                return goodsGroupName;
            }

            public void setGoodsGroupName(String goodsGroupName) {
                this.goodsGroupName = goodsGroupName;
            }

            public String getGoodsGroupNutritiveValue() {
                return goodsGroupNutritiveValue;
            }

            public void setGoodsGroupNutritiveValue(String goodsGroupNutritiveValue) {
                this.goodsGroupNutritiveValue = goodsGroupNutritiveValue;
            }

            public String getGoodsGroupOrigin() {
                return goodsGroupOrigin;
            }

            public void setGoodsGroupOrigin(String goodsGroupOrigin) {
                this.goodsGroupOrigin = goodsGroupOrigin;
            }

            public String getGoodsGroupPack() {
                return goodsGroupPack;
            }

            public void setGoodsGroupPack(String goodsGroupPack) {
                this.goodsGroupPack = goodsGroupPack;
            }

            public String getGoodsGroupPicUrl() {
                return goodsGroupPicUrl;
            }

            public void setGoodsGroupPicUrl(String goodsGroupPicUrl) {
                this.goodsGroupPicUrl = goodsGroupPicUrl;
            }

            public int getGoodsGroupPrice() {
                return goodsGroupPrice;
            }

            public void setGoodsGroupPrice(int goodsGroupPrice) {
                this.goodsGroupPrice = goodsGroupPrice;
            }

            public int getGoodsGroupSalePrice() {
                return goodsGroupSalePrice;
            }

            public void setGoodsGroupSalePrice(int goodsGroupSalePrice) {
                this.goodsGroupSalePrice = goodsGroupSalePrice;
            }

            public String getGoodsGroupSavingMode() {
                return goodsGroupSavingMode;
            }

            public void setGoodsGroupSavingMode(String goodsGroupSavingMode) {
                this.goodsGroupSavingMode = goodsGroupSavingMode;
            }

            public String getGoodsGroupSpec() {
                return goodsGroupSpec;
            }

            public void setGoodsGroupSpec(String goodsGroupSpec) {
                this.goodsGroupSpec = goodsGroupSpec;
            }

            public int getGoodsGroupWeight() {
                return goodsGroupWeight;
            }

            public void setGoodsGroupWeight(int goodsGroupWeight) {
                this.goodsGroupWeight = goodsGroupWeight;
            }

            public String getGroupImg() {
                return groupImg;
            }

            public void setGroupImg(String groupImg) {
                this.groupImg = groupImg;
            }

            public int getGroupNumber() {
                return groupNumber;
            }

            public void setGroupNumber(int groupNumber) {
                this.groupNumber = groupNumber;
            }

            public int getGroupPrice() {
                return groupPrice;
            }

            public void setGroupPrice(int groupPrice) {
                this.groupPrice = groupPrice;
            }

            public int getOfferedCount() {
                return offeredCount;
            }

            public void setOfferedCount(int offeredCount) {
                this.offeredCount = offeredCount;
            }

            public String getPromotionName() {
                return promotionName;
            }

            public void setPromotionName(String promotionName) {
                this.promotionName = promotionName;
            }

            public long getStartDatetime() {
                return startDatetime;
            }

            public void setStartDatetime(long startDatetime) {
                this.startDatetime = startDatetime;
            }
        }
    }
}
