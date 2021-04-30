package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetHotSaleGoodsListResponse extends BaseResponse {

    /**
     * data : {"goodsList":[{"goodsDetailPicUrl":"","goodsName":"葡萄","goodsNameSub":"V发的","goodsPack":"",
     * "goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":12,
     * "labelNameList":["测试标签2","测试标签","产地直达"],"manyGoodsInfoFlag":1,"memberPrice":10,"storeGoodsId":84,"storeGoodsInfoId":129,
     * "storeId":124},{"goodsDetailPicUrl":"","goodsName":"大鸭梨","goodsNameSub":"好甜","goodsPack":"","goodsPicUrl":"http://image
     * .pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsSalePrice":120,"labelNameList":[],
     * "manyGoodsInfoFlag":1,"memberPrice":110,"storeGoodsId":66,"storeGoodsInfoId":88,"storeId":124}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<GoodsListBean> goodsList;

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * goodsDetailPicUrl :
             * goodsName : 葡萄
             * goodsNameSub : V发的
             * goodsPack :
             * goodsPicUrl : http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png
             * goodsSalePrice : 12
             * labelNameList : ["测试标签2","测试标签","产地直达"]
             * manyGoodsInfoFlag : 1
             * memberPrice : 10
             * storeGoodsId : 84
             * storeGoodsInfoId : 129
             * storeId : 124
             */

            private String goodsDetailPicUrl;
            private String goodsName;
            private String goodsNameSub;
            private String goodsPack;
            private String goodsPicUrl;
            private int goodsSalePrice;
            private int manyGoodsInfoFlag;
            private int memberPrice;
            private int storeGoodsId;
            private int storeGoodsInfoId;
            private int storeId;
            private List<String> labelNameList;

            public String getGoodsDetailPicUrl() {
                return goodsDetailPicUrl;
            }

            public void setGoodsDetailPicUrl(String goodsDetailPicUrl) {
                this.goodsDetailPicUrl = goodsDetailPicUrl;
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
        }
    }
}
