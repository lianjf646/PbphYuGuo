package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetGoodsFirstCategoryResponse extends BaseResponse {

    /**
     * data : {"firstCategory":[{"cateName":"团购","categoryGoodsList":[],"categoryImgUrl":"","goodsCateId":2},{"cateName":"水果","categoryGoodsList":[],"categoryImgUrl":"","goodsCateId":1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<FirstCategoryBean> firstCategory;

        public List<FirstCategoryBean> getFirstCategory() {
            return firstCategory;
        }

        public void setFirstCategory(List<FirstCategoryBean> firstCategory) {
            this.firstCategory = firstCategory;
        }

        public static class FirstCategoryBean {
            /**
             * cateName : 团购
             * categoryGoodsList : []
             * categoryImgUrl :
             * goodsCateId : 2
             */

            private String cateName;
            private String categoryImgUrl;
            private int goodsCateId;
            private List<?> categoryGoodsList;

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

            public List<?> getCategoryGoodsList() {
                return categoryGoodsList;
            }

            public void setCategoryGoodsList(List<?> categoryGoodsList) {
                this.categoryGoodsList = categoryGoodsList;
            }
        }
    }
}
