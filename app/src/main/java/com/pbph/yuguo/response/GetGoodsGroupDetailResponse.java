package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;


public class GetGoodsGroupDetailResponse extends BaseResponse {

    /**
     * data : {"paramList":[{"info":"规格：g"},{"info":"包装：1"},{"info":"产地：哈尔滨"},{"info":"营养价值：肉"},{"info":"存储过程：吃"},{"info":"重量：10"}],"goodsDetail":{"alreadyGroupNumber":5,"currentTime":1547105082413,"endDatetime":1549698300000,"goodsGroupActivityId":1,"goodsGroupId":4,"goodsGroupMarketPrice":100,"goodsGroupName":"大包子","goodsGroupNameSub":"来一笼","goodsGroupNutritiveValue":"","goodsGroupOrigin":"","goodsGroupPack":"","goodsGroupPicUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsGroupPrice":20,"goodsGroupSalePrice":500,"goodsGroupSavingMode":"","goodsGroupSpec":"","goodsGroupWeight":0,"goodsSlideList":[{"goodsImgUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsType":2},{"goodsImgUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsType":3}],"groupImg":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","groupNumber":10,"groupPrice":20,"offeredCount":5,"promotionName":"大橙子","startDatetime":1545810000000}}
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
         * paramList : [{"info":"规格：g"},{"info":"包装：1"},{"info":"产地：哈尔滨"},{"info":"营养价值：肉"},{"info":"存储过程：吃"},{"info":"重量：10"}]
         * goodsDetail : {"alreadyGroupNumber":5,"currentTime":1547105082413,"endDatetime":1549698300000,"goodsGroupActivityId":1,"goodsGroupId":4,"goodsGroupMarketPrice":100,"goodsGroupName":"大包子","goodsGroupNameSub":"来一笼","goodsGroupNutritiveValue":"","goodsGroupOrigin":"","goodsGroupPack":"","goodsGroupPicUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsGroupPrice":20,"goodsGroupSalePrice":500,"goodsGroupSavingMode":"","goodsGroupSpec":"","goodsGroupWeight":0,"goodsSlideList":[{"goodsImgUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsType":2},{"goodsImgUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsType":3}],"groupImg":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","groupNumber":10,"groupPrice":20,"offeredCount":5,"promotionName":"大橙子","startDatetime":1545810000000}
         */

        private GoodsDetailBean goodsDetail;
        private List<ParamListBean> paramList;

        public GoodsDetailBean getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(GoodsDetailBean goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public List<ParamListBean> getParamList() {
            return paramList;
        }

        public void setParamList(List<ParamListBean> paramList) {
            this.paramList = paramList;
        }

        public static class GoodsDetailBean {
            /**
             * alreadyGroupNumber : 5
             * currentTime : 1547105082413
             * endDatetime : 1549698300000
             * goodsGroupActivityId : 1
             * goodsGroupId : 4
             * goodsGroupMarketPrice : 100
             * goodsGroupName : 大包子
             * goodsGroupNameSub : 来一笼
             * goodsGroupNutritiveValue :
             * goodsGroupOrigin :
             * goodsGroupPack :
             * goodsGroupPicUrl : http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg
             * goodsGroupPrice : 20
             * goodsGroupSalePrice : 500
             * goodsGroupSavingMode :
             * goodsGroupSpec :
             * goodsGroupWeight : 0
             * goodsSlideList : [{"goodsImgUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsType":2},{"goodsImgUrl":"http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg","goodsType":3}]
             * groupImg : http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg
             * groupNumber : 10
             * groupPrice : 20
             * offeredCount : 5
             * promotionName : 大橙子
             * startDatetime : 1545810000000
             */

            private int alreadyGroupNumber;
            private long currentTime;
            private long endDatetime;
            private int goodsGroupActivityId;
            private int goodsGroupId;
            private int goodsGroupMarketPrice;
            private String goodsGroupName;
            private String goodsGroupNameSub;
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
            private List<GoodsSlideListBean> goodsSlideList;

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

            public String getGoodsGroupNameSub() {
                return goodsGroupNameSub;
            }

            public void setGoodsGroupNameSub(String goodsGroupNameSub) {
                this.goodsGroupNameSub = goodsGroupNameSub;
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

            public List<GoodsSlideListBean> getGoodsSlideList() {
                return goodsSlideList;
            }

            public void setGoodsSlideList(List<GoodsSlideListBean> goodsSlideList) {
                this.goodsSlideList = goodsSlideList;
            }

            public static class GoodsSlideListBean {
                /**
                 * goodsImgUrl : http://image.pbdsh.com/ygonlinefold/3/82B4A657A540FE5D98163531338140E1.jpg
                 * goodsType : 2
                 */

                private String goodsImgUrl;
                private int goodsType;

                public String getGoodsImgUrl() {
                    return goodsImgUrl;
                }

                public void setGoodsImgUrl(String goodsImgUrl) {
                    this.goodsImgUrl = goodsImgUrl;
                }

                public int getGoodsType() {
                    return goodsType;
                }

                public void setGoodsType(int goodsType) {
                    this.goodsType = goodsType;
                }
            }
        }

        public static class ParamListBean {
            /**
             * info : 规格：g
             */

            private String info;

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }
        }
    }
}
