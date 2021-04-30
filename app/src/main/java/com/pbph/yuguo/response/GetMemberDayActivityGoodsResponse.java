package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:
 */

public class GetMemberDayActivityGoodsResponse extends BaseResponse {


    /**
     * data : {"memberActivity":{"activeName":"会员日2","activityPrcture":"http://image.pbdsh
     * .com/ygtestfold/16/18CDAE92E64AEC71689727D498513CBC.jpg","adminId":2,"advertisingPicture":"http://image.pbdsh
     * .com/ygtestfold/16/8559348A9B22E28A659214643384F8C5.png","createTime":1557372210000,"delFlag":0,"discount":50,
     * "endTime":1557890400000,"goodsDetailActivityPrcture":"http://image.pbdsh
     * .com/ygtestfold/16/136B98443715932E1BA872204A46CAE0.png","memberDayActivityId":36,"modifyTime":1557381046000,
     * "openFlag":1,"startTime":1557372180000},"totleSize":1,"resultList":[{"goodsCateId":51,"goodsId":446,"goodsName":"苹果派",
     * "goodsNameSub":"苹果派","goodsPicUrl":"http://image.pbdsh.com/ygtestfold/16/3CD5117E937ED53F6238AD947ADB995A.png",
     * "goodsSalePrice":10000,"labelNameList":["品质保证"],"manyGoodsInfoFlag":0,"memberDayActivityName":"会员日2",
     * "memberDiscountPrice":4000,"memberPrice":8000,"serviceNameList":[],"storeGoodsId":225,"storeGoodsInfoId":571,
     * "storeId":154}]}
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
         * memberActivity : {"activeName":"会员日2","activityPrcture":"http://image.pbdsh
         * .com/ygtestfold/16/18CDAE92E64AEC71689727D498513CBC.jpg","adminId":2,"advertisingPicture":"http://image.pbdsh
         * .com/ygtestfold/16/8559348A9B22E28A659214643384F8C5.png","createTime":1557372210000,"delFlag":0,"discount":50,
         * "endTime":1557890400000,"goodsDetailActivityPrcture":"http://image.pbdsh
         * .com/ygtestfold/16/136B98443715932E1BA872204A46CAE0.png","memberDayActivityId":36,"modifyTime":1557381046000,
         * "openFlag":1,"startTime":1557372180000}
         * totleSize : 1
         * resultList : [{"goodsCateId":51,"goodsId":446,"goodsName":"苹果派","goodsNameSub":"苹果派","goodsPicUrl":"http://image
         * .pbdsh.com/ygtestfold/16/3CD5117E937ED53F6238AD947ADB995A.png","goodsSalePrice":10000,"labelNameList":["品质保证"],
         * "manyGoodsInfoFlag":0,"memberDayActivityName":"会员日2","memberDiscountPrice":4000,"memberPrice":8000,
         * "serviceNameList":[],"storeGoodsId":225,"storeGoodsInfoId":571,"storeId":154}]
         */

        private MemberActivityBean memberActivity;
        private int totleSize;
        private List<ResultListBean> resultList;

        public MemberActivityBean getMemberActivity() {
            return memberActivity;
        }

        public void setMemberActivity(MemberActivityBean memberActivity) {
            this.memberActivity = memberActivity;
        }

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class MemberActivityBean {
            /**
             * activeName : 会员日2
             * activityPrcture : http://image.pbdsh.com/ygtestfold/16/18CDAE92E64AEC71689727D498513CBC.jpg
             * adminId : 2
             * advertisingPicture : http://image.pbdsh.com/ygtestfold/16/8559348A9B22E28A659214643384F8C5.png
             * createTime : 1557372210000
             * delFlag : 0
             * discount : 50
             * endTime : 1557890400000
             * goodsDetailActivityPrcture : http://image.pbdsh.com/ygtestfold/16/136B98443715932E1BA872204A46CAE0.png
             * memberDayActivityId : 36
             * modifyTime : 1557381046000
             * openFlag : 1
             * startTime : 1557372180000
             */

            private String activeName;
            private String activityPrcture;
            private int adminId;
            private String advertisingPicture;
            private long createTime;
            private int delFlag;
            private int discount;
            private long endTime;
            private String goodsDetailActivityPrcture;
            private int memberDayActivityId;
            private long modifyTime;
            private int openFlag;
            private long startTime;

            public String getActiveName() {
                return activeName;
            }

            public void setActiveName(String activeName) {
                this.activeName = activeName;
            }

            public String getActivityPrcture() {
                return activityPrcture;
            }

            public void setActivityPrcture(String activityPrcture) {
                this.activityPrcture = activityPrcture;
            }

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public String getAdvertisingPicture() {
                return advertisingPicture;
            }

            public void setAdvertisingPicture(String advertisingPicture) {
                this.advertisingPicture = advertisingPicture;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(int delFlag) {
                this.delFlag = delFlag;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getGoodsDetailActivityPrcture() {
                return goodsDetailActivityPrcture;
            }

            public void setGoodsDetailActivityPrcture(String goodsDetailActivityPrcture) {
                this.goodsDetailActivityPrcture = goodsDetailActivityPrcture;
            }

            public int getMemberDayActivityId() {
                return memberDayActivityId;
            }

            public void setMemberDayActivityId(int memberDayActivityId) {
                this.memberDayActivityId = memberDayActivityId;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public int getOpenFlag() {
                return openFlag;
            }

            public void setOpenFlag(int openFlag) {
                this.openFlag = openFlag;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }
        }

        public static class ResultListBean {
            /**
             * goodsCateId : 51
             * goodsId : 446
             * goodsName : 苹果派
             * goodsNameSub : 苹果派
             * goodsPicUrl : http://image.pbdsh.com/ygtestfold/16/3CD5117E937ED53F6238AD947ADB995A.png
             * goodsSalePrice : 10000
             * labelNameList : ["品质保证"]
             * manyGoodsInfoFlag : 0
             * memberDayActivityName : 会员日2
             * memberDiscountPrice : 4000
             * memberPrice : 8000
             * serviceNameList : []
             * storeGoodsId : 225
             * storeGoodsInfoId : 571
             * storeId : 154
             */

            private int goodsCateId;
            private int goodsId;
            private String goodsName;
            private String goodsNameSub;
            private String goodsPicUrl;
            private int goodsSalePrice;
            private int manyGoodsInfoFlag;
            private String memberDayActivityName;
            private int memberDiscountPrice;
            private int memberPrice;
            private int storeGoodsId;
            private int storeGoodsInfoId;
            private int storeId;
            private List<String> labelNameList;
            private List<?> serviceNameList;

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

            public String getMemberDayActivityName() {
                return memberDayActivityName;
            }

            public void setMemberDayActivityName(String memberDayActivityName) {
                this.memberDayActivityName = memberDayActivityName;
            }

            public int getMemberDiscountPrice() {
                return memberDiscountPrice;
            }

            public void setMemberDiscountPrice(int memberDiscountPrice) {
                this.memberDiscountPrice = memberDiscountPrice;
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
