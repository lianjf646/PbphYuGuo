package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2019/1/7 0007.
 * class note:
 */

public class VipGoodsListResponse extends BaseResponse {


    /**
     * data : {"goodsList":[{"goodsId":261,"goodsName":"测试第九个商品","goodsNameSub":"","goodsNo":"","goodsPicUrl":"http://image.pbdsh.com/ygonlinefold/3/4D91448751CCF1B50DF70FFFBBB22785.png","goodsSalePrice":0,"labelNameList":[],"manyGoodsInfoFlag":0,"memberPrice":0,"storeGoodsId":18,"storeGoodsInfoId":0,"storeId":124},{"goodsId":262,"goodsName":"测试第十个商品","goodsNameSub":"","goodsNo":"","goodsPicUrl":"http://image.pbdsh.com/ygonlinefold/3/4D91448751CCF1B50DF70FFFBBB22785.png","goodsSalePrice":0,"labelNameList":[],"manyGoodsInfoFlag":0,"memberPrice":0,"storeGoodsId":23,"storeGoodsInfoId":0,"storeId":124},{"goodsId":263,"goodsName":"测试11","goodsNameSub":"","goodsNo":"","goodsPicUrl":"http://image.pbdsh.com/ygonlinefold/3/4D91448751CCF1B50DF70FFFBBB22785.png","goodsSalePrice":0,"labelNameList":[],"manyGoodsInfoFlag":0,"memberPrice":0,"storeGoodsId":25,"storeGoodsInfoId":12,"storeId":124}]}
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
             * goodsId : 261
             * goodsName : 测试第九个商品
             * goodsNameSub :
             * goodsNo :
             * goodsPicUrl : http://image.pbdsh.com/ygonlinefold/3/4D91448751CCF1B50DF70FFFBBB22785.png
             * goodsSalePrice : 0
             * labelNameList : []
             * manyGoodsInfoFlag : 0
             * memberPrice : 0
             * storeGoodsId : 18
             * storeGoodsInfoId : 0
             * storeId : 124
             */

            private int goodsId;
            private String goodsName;
            private String goodsNameSub;
            private String goodsNo;
            private String goodsPicUrl;
            private int goodsSalePrice;
            private int manyGoodsInfoFlag;
            private int memberPrice;
            private int storeGoodsId;
            private int storeGoodsInfoId;
            private int storeId;
            private List<String> labelNameList;

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

            public String getGoodsNo() {
                return goodsNo;
            }

            public void setGoodsNo(String goodsNo) {
                this.goodsNo = goodsNo;
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
