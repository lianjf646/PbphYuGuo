package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetGoodsListResponse extends BaseResponse {

    /**
     * data : {"goodsCategoryList":[{"cateName":"菠萝","categoryGoodsList":[{"goodsCateId":3,"goodsId":290,"goodsName":"大枣","goodsNameSub":"而提供人","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":36,"labelNameList":["测试标签","产地直达"],"manyGoodsInfoFlag":1,"memberPrice":36,"serviceNameList":[],"storeGoodsId":56,"storeGoodsInfoId":61,"storeId":124}],"categoryImgUrl":"http://image.pbdsh.com/ygtestfold/16/C1229C62F385CEDD99AA4E5A991466B6.jpg","goodsCateId":3}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<GoodsCategoryListBean> goodsCategoryList;

        public List<GoodsCategoryListBean> getGoodsCategoryList() {
            return goodsCategoryList;
        }

        public void setGoodsCategoryList(List<GoodsCategoryListBean> goodsCategoryList) {
            this.goodsCategoryList = goodsCategoryList;
        }

        public static class GoodsCategoryListBean {
            /**
             * cateName : 菠萝
             * categoryGoodsList : [{"goodsCateId":3,"goodsId":290,"goodsName":"大枣","goodsNameSub":"而提供人","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":36,"labelNameList":["测试标签","产地直达"],"manyGoodsInfoFlag":1,"memberPrice":36,"serviceNameList":[],"storeGoodsId":56,"storeGoodsInfoId":61,"storeId":124}]
             * categoryImgUrl : http://image.pbdsh.com/ygtestfold/16/C1229C62F385CEDD99AA4E5A991466B6.jpg
             * goodsCateId : 3
             */

            public int headId;
            private String cateName;
            private String categoryImgUrl;
            private int goodsCateId;
            private List<CategoryGoodsListBean> categoryGoodsList;

            public String getCateName() {
                return cateName;
            }

            public void setCateName(String cateName) {
                this.cateName = cateName;
            }

            public String getCategoryImgUrl() {
                return categoryImgUrl;
            }

            public void setCategoryImgUrl(String categoryImgUrl) {
                this.categoryImgUrl = categoryImgUrl;
            }

            public int getGoodsCateId() {
                return goodsCateId;
            }

            public void setGoodsCateId(int goodsCateId) {
                this.goodsCateId = goodsCateId;
            }

            public List<CategoryGoodsListBean> getCategoryGoodsList() {
                return categoryGoodsList;
            }

            public void setCategoryGoodsList(List<CategoryGoodsListBean> categoryGoodsList) {
                this.categoryGoodsList = categoryGoodsList;
            }

            public static class CategoryGoodsListBean {
                /**
                 * goodsCateId : 3
                 * goodsId : 290
                 * goodsName : 大枣
                 * goodsNameSub : 而提供人
                 * goodsPicUrl : http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png
                 * goodsSalePrice : 36
                 * labelNameList : ["测试标签","产地直达"]
                 * manyGoodsInfoFlag : 1
                 * memberPrice : 36
                 * serviceNameList : []
                 * storeGoodsId : 56
                 * storeGoodsInfoId : 61
                 * storeId : 124
                 */

                private int goodsCateId;
                private int goodsId;
                private String goodsName;
                private String goodsNameSub;
                private String goodsPicUrl;
                private int goodsSalePrice;
                private int manyGoodsInfoFlag;
                private int memberPrice;
                private int storeGoodsId;
                private int storeGoodsInfoId;
                private int storeId;
                private List<String> labelNameList;
                private List<?> serviceNameList;
                public int headId;

                public int getGoodsCateId() {
                    return goodsCateId;
                }

                public void setGoodsCateId(int goodsCateId) {
                    this.goodsCateId = goodsCateId;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
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

                public int getManyGoodsInfoFlag() {
                    return manyGoodsInfoFlag;
                }

                public void setManyGoodsInfoFlag(int manyGoodsInfoFlag) {
                    this.manyGoodsInfoFlag = manyGoodsInfoFlag;
                }

                public int getMemberPrice() {
                    return memberPrice;
                }

                public void setMemberPrice(int memberPrice) {
                    this.memberPrice = memberPrice;
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

                public List<String> getLabelNameList() {
                    return labelNameList;
                }

                public void setLabelNameList(List<String> labelNameList) {
                    this.labelNameList = labelNameList;
                }

                public List<?> getServiceNameList() {
                    return serviceNameList;
                }

                public void setServiceNameList(List<?> serviceNameList) {
                    this.serviceNameList = serviceNameList;
                }
            }
        }
    }
}
