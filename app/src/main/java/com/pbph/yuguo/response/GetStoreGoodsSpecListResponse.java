package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/1/8.
 */
public class GetStoreGoodsSpecListResponse extends BaseResponse {


    /**
     * data : {"unvaliableSpecDetailIdList":[2,3,7],"storeGoodsInfo":{"goodsId":295,"goodsInfoId":46,
     * "goodsInfoMemberPrice":123,"goodsInfoName":"橙子(红色XXLg),而纷纷退","goodsInfoPicUrl":"http://image.pbdsh
     * .com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png","goodsInfoSalePrice":1243,"purchaseLimitationNum":1,
     * "specDetailIdList":[1,8,5],"storageNum":0,"storeGoodsInfoId":133,"storeId":124},
     * "allSpecDetailIdList":[{"specDetailList":[{"specDetailId":1,"specValueRemark":"红色"},{"specDetailId":2,
     * "specValueRemark":"蓝色"}],"specId":1,"specName":"颜色"},{"specDetailList":[{"specDetailId":8,"specValueRemark":"XXL"},
     * {"specDetailId":3,"specValueRemark":"XL"}],"specId":2,"specName":"大小"},{"specDetailList":[{"specDetailId":5,
     * "specValueRemark":"g"},{"specDetailId":6,"specValueRemark":"kg"},{"specDetailId":7,"specValueRemark":"kkg"}],"specId":3,
     * "specName":"重量"}]}
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
         * unvaliableSpecDetailIdList : [2,3,7]
         * storeGoodsInfo : {"goodsId":295,"goodsInfoId":46,"goodsInfoMemberPrice":123,"goodsInfoName":"橙子(红色XXLg),而纷纷退",
         * "goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png",
         * "goodsInfoSalePrice":1243,"purchaseLimitationNum":1,"specDetailIdList":[1,8,5],"storageNum":0,
         * "storeGoodsInfoId":133,"storeId":124}
         * allSpecDetailIdList : [{"specDetailList":[{"specDetailId":1,"specValueRemark":"红色"},{"specDetailId":2,
         * "specValueRemark":"蓝色"}],"specId":1,"specName":"颜色"},{"specDetailList":[{"specDetailId":8,"specValueRemark":"XXL"},
         * {"specDetailId":3,"specValueRemark":"XL"}],"specId":2,"specName":"大小"},{"specDetailList":[{"specDetailId":5,
         * "specValueRemark":"g"},{"specDetailId":6,"specValueRemark":"kg"},{"specDetailId":7,"specValueRemark":"kkg"}],
         * "specId":3,"specName":"重量"}]
         */

        private StoreGoodsInfoBean storeGoodsInfo;
        private List<Integer> unvaliableSpecDetailIdList;
        private List<AllSpecDetailIdListBean> allSpecDetailIdList;

        public StoreGoodsInfoBean getStoreGoodsInfo() {
            return storeGoodsInfo;
        }

        public void setStoreGoodsInfo(StoreGoodsInfoBean storeGoodsInfo) {
            this.storeGoodsInfo = storeGoodsInfo;
        }

        public List<Integer> getUnvaliableSpecDetailIdList() {
            return unvaliableSpecDetailIdList;
        }

        public void setUnvaliableSpecDetailIdList(List<Integer> unvaliableSpecDetailIdList) {
            this.unvaliableSpecDetailIdList = unvaliableSpecDetailIdList;
        }

        public List<AllSpecDetailIdListBean> getAllSpecDetailIdList() {
            return allSpecDetailIdList;
        }

        public void setAllSpecDetailIdList(List<AllSpecDetailIdListBean> allSpecDetailIdList) {
            this.allSpecDetailIdList = allSpecDetailIdList;
        }

        public static class StoreGoodsInfoBean {
            /**
             * goodsId : 295
             * goodsInfoId : 46
             * goodsInfoMemberPrice : 123
             * goodsInfoName : 橙子(红色XXLg),而纷纷退
             * goodsInfoPicUrl : http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png
             * goodsInfoSalePrice : 1243
             * purchaseLimitationNum : 1
             * specDetailIdList : [1,8,5]
             * storageNum : 0
             * storeGoodsInfoId : 133
             * storeId : 124
             */

            private int goodsId;
            private int goodsInfoId;
            private int goodsInfoMemberPrice;
            private String goodsInfoName;
            private String goodsInfoPicUrl;
            private int goodsInfoSalePrice;
            private int purchaseLimitationNum;//限购
            private int storageNum;  //库存
            private int storeGoodsInfoId;
            private int storeId;
            private List<Integer> specDetailIdList;

            private String goodsInfoWeight;

            public String getGoodsInfoWeight() {
                return goodsInfoWeight;
            }

            public void setGoodsInfoWeight(String goodsInfoWeight) {
                this.goodsInfoWeight = goodsInfoWeight;
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

            public int getPurchaseLimitationNum() {
                return purchaseLimitationNum;
            }

            public void setPurchaseLimitationNum(int purchaseLimitationNum) {
                this.purchaseLimitationNum = purchaseLimitationNum;
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

            public List<Integer> getSpecDetailIdList() {
                return specDetailIdList;
            }

            public void setSpecDetailIdList(List<Integer> specDetailIdList) {
                this.specDetailIdList = specDetailIdList;
            }
        }

        public static class AllSpecDetailIdListBean {
            /**
             * specDetailList : [{"specDetailId":1,"specValueRemark":"红色"},{"specDetailId":2,"specValueRemark":"蓝色"}]
             * specId : 1
             * specName : 颜色
             */

            private int specId;
            private String specName;
            private List<SpecDetailListBean> specDetailList;

            public int choose_child_id;
            public String choose_child_name;

            public int getChoose_child_id() {
                return choose_child_id;
            }

            public void setChoose_child_id(int choose_child_id) {
                this.choose_child_id = choose_child_id;
            }

            public String getChoose_child_name() {
                return choose_child_name;
            }

            public void setChoose_child_name(String choose_child_name) {
                this.choose_child_name = choose_child_name;
            }

            public int getSpecId() {
                return specId;
            }

            public void setSpecId(int specId) {
                this.specId = specId;
            }

            public String getSpecName() {
                return specName;
            }

            public void setSpecName(String specName) {
                this.specName = specName;
            }

            public List<SpecDetailListBean> getSpecDetailList() {
                return specDetailList;
            }

            public void setSpecDetailList(List<SpecDetailListBean> specDetailList) {
                this.specDetailList = specDetailList;
            }

            public static class SpecDetailListBean {
                /**
                 * specDetailId : 1
                 * specValueRemark : 红色
                 */

                private int specDetailId;
                private String specValueRemark;

                public int getSpecDetailId() {
                    return specDetailId;
                }

                public void setSpecDetailId(int specDetailId) {
                    this.specDetailId = specDetailId;
                }

                public String getSpecValueRemark() {
                    return specValueRemark;
                }

                public void setSpecValueRemark(String specValueRemark) {
                    this.specValueRemark = specValueRemark;
                }
            }
        }
    }
}
