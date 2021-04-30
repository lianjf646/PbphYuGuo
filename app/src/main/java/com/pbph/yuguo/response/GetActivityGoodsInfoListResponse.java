package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetActivityGoodsInfoListResponse extends BaseResponse {

    /**
     * data : {"activity":{"activeId":8,"activeType":1,"count":3,"endTime":"2019-01-31 23:45:00","isActivityOk":1,"price":20000,"satisfactionMoney":0,"satisfactionNum":0,"selectedCount":0,"startTime":"2019-01-03 13:50:00"},"goodsInfoList":[{"goodsInfoMemberPrice":23,"goodsInfoName":"大枣(红色XL),而提供人","goodsInfoNameSub":"副标题","goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsInfoSalePrice":36,"isChecked":0,"isNotEnough":0,"labelNameList":["产地直达","测试标签"],"number":0,"storeGoodsId":56,"storeGoodsInfoId":61}]}
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
         * activity : {"activeId":8,"activeType":1,"count":3,"endTime":"2019-01-31 23:45:00","isActivityOk":1,"price":20000,"satisfactionMoney":0,"satisfactionNum":0,"selectedCount":0,"startTime":"2019-01-03 13:50:00"}
         * goodsInfoList : [{"goodsInfoMemberPrice":23,"goodsInfoName":"大枣(红色XL),而提供人","goodsInfoNameSub":"副标题","goodsInfoPicUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsInfoSalePrice":36,"isChecked":0,"isNotEnough":0,"labelNameList":["产地直达","测试标签"],"number":0,"storeGoodsId":56,"storeGoodsInfoId":61}]
         */

        private ActivityBean activity;
        private List<GoodsInfoListBean> goodsInfoList;

        public ActivityBean getActivity() {
            return activity;
        }

        public void setActivity(ActivityBean activity) {
            this.activity = activity;
        }

        public List<GoodsInfoListBean> getGoodsInfoList() {
            return goodsInfoList;
        }

        public void setGoodsInfoList(List<GoodsInfoListBean> goodsInfoList) {
            this.goodsInfoList = goodsInfoList;
        }

        public static class ActivityBean {
            /**
             * activeId : 8
             * activeType : 1
             * count : 3
             * endTime : 2019-01-31 23:45:00
             * isActivityOk : 1
             * price : 20000
             * satisfactionMoney : 0
             * satisfactionNum : 0
             * selectedCount : 0
             * startTime : 2019-01-03 13:50:00
             */

            private int activeId;
            private int activeType;
            private int count;
            private String endTime;
            private int isActivityOk;
            private int price;
            private int satisfactionMoney;
            private int satisfactionNum;
            private int selectedCount;
            private String startTime;

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

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getIsActivityOk() {
                return isActivityOk;
            }

            public void setIsActivityOk(int isActivityOk) {
                this.isActivityOk = isActivityOk;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
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

            public int getSelectedCount() {
                return selectedCount;
            }

            public void setSelectedCount(int selectedCount) {
                this.selectedCount = selectedCount;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }

        public static class GoodsInfoListBean {
            /**
             * goodsInfoMemberPrice : 23
             * goodsInfoName : 大枣(红色XL),而提供人
             * goodsInfoNameSub : 副标题
             * goodsInfoPicUrl : http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png
             * goodsInfoSalePrice : 36
             * isChecked : 0
             * isNotEnough : 0
             * labelNameList : ["产地直达","测试标签"]
             * number : 0
             * storeGoodsId : 56
             * storeGoodsInfoId : 61
             */

            private int goodsInfoMemberPrice;
            private String goodsInfoName;
            private String goodsSpec;
            private String goodsInfoPicUrl;
            private int goodsInfoSalePrice;
            private int isChecked;
            private int isNotEnough;
            private int number;
            private int storeGoodsId;
            private int storeGoodsInfoId;
            private List<String> labelNameList;

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

            public String getGoodsSpec() {
                return goodsSpec;
            }

            public void setGoodsSpec(String goodsSpec) {
                this.goodsSpec = goodsSpec;
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

            public int getIsChecked() {
                return isChecked;
            }

            public void setIsChecked(int isChecked) {
                this.isChecked = isChecked;
            }

            public int getIsNotEnough() {
                return isNotEnough;
            }

            public void setIsNotEnough(int isNotEnough) {
                this.isNotEnough = isNotEnough;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
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

            public List<String> getLabelNameList() {
                return labelNameList;
            }

            public void setLabelNameList(List<String> labelNameList) {
                this.labelNameList = labelNameList;
            }
        }
    }
}
