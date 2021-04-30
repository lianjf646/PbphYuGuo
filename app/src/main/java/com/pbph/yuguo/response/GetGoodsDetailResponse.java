package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;


public class GetGoodsDetailResponse extends BaseResponse {

    /**
     * data : {"customerLevelFlag":0,"memberLevel":{"adminId":2,"createTime":1545455400000,"discountPrice":16600,"experienceShoppingTimes":0,"memberBenefitsIdList":[],"memberDay":0,"memberLevelId":2,"memberLevelName":"VIP一星会员","memberType":1,"modifyTime":1546852183000,"openPrice":19800,"timeUnit":4},"paramList":[{"info":"营养价值：维生素"},{"info":"产地：中国大陆"}],"storeGoodsInfoActivity":null,"evaluateList":[],"manyGoodsInfoFlag":1,"couponActivity":{"couponActivityId":5,"couponName":"测试自动优惠券","couponPrice":1000,"couponRuleType":3,"couponXPrice":0},"evaluateTotalNumber":0,"goodsDetail":{"basedFreight":0,"getRulePointScore":0,"goodsCateId":3,"goodsId":290,"goodsName":"大枣","goodsNameSub":"而提供人","goodsPicUrl":"","goodsSalePrice":36,"goodsSlideList":[{"goodsId":290,"goodsImgId":55,"goodsImgUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsType":0},{"goodsId":290,"goodsImgId":56,"goodsImgUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsType":1}],"memberPrice":36,"pinkageActivityId":0,"pinkageName":"","pointSetOpenFlag":1,"realPayFullXPrice":0,"specAndSpecDetailName":"颜色:红色;大小:XL","storeGoodsId":56,"storeGoodsInfoId":61,"storeId":124,"supportNameList":["呵呵1","限时包邮"]}}
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
         * customerLevelFlag : 0
         * memberLevel : {"adminId":2,"createTime":1545455400000,"discountPrice":16600,"experienceShoppingTimes":0,"memberBenefitsIdList":[],"memberDay":0,"memberLevelId":2,"memberLevelName":"VIP一星会员","memberType":1,"modifyTime":1546852183000,"openPrice":19800,"timeUnit":4}
         * paramList : [{"info":"营养价值：维生素"},{"info":"产地：中国大陆"}]
         * storeGoodsInfoActivity : null
         * evaluateList : []
         * manyGoodsInfoFlag : 1
         * couponActivity : {"couponActivityId":5,"couponName":"测试自动优惠券","couponPrice":1000,"couponRuleType":3,"couponXPrice":0}
         * evaluateTotalNumber : 0
         * goodsDetail : {"basedFreight":0,"getRulePointScore":0,"goodsCateId":3,"goodsId":290,"goodsName":"大枣","goodsNameSub":"而提供人","goodsPicUrl":"","goodsSalePrice":36,"goodsSlideList":[{"goodsId":290,"goodsImgId":55,"goodsImgUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsType":0},{"goodsId":290,"goodsImgId":56,"goodsImgUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsType":1}],"memberPrice":36,"pinkageActivityId":0,"pinkageName":"","pointSetOpenFlag":1,"realPayFullXPrice":0,"specAndSpecDetailName":"颜色:红色;大小:XL","storeGoodsId":56,"storeGoodsInfoId":61,"storeId":124,"supportNameList":["呵呵1","限时包邮"]}
         */

        private int customerLevelFlag;
        private MemberLevelBean memberLevel;
        private StoreGoodsInfoActivityBean storeGoodsInfoActivity;
        private int manyGoodsInfoFlag;
        private CouponActivityBean couponActivity;
        private int evaluateTotalNumber;
        private GoodsDetailBean goodsDetail;
        private List<ParamListBean> paramList;
        private List<EvaluateListBean> evaluateList;

        public int getCustomerLevelFlag() {
            return customerLevelFlag;
        }

        public void setCustomerLevelFlag(int customerLevelFlag) {
            this.customerLevelFlag = customerLevelFlag;
        }

        public MemberLevelBean getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(MemberLevelBean memberLevel) {
            this.memberLevel = memberLevel;
        }

        public StoreGoodsInfoActivityBean getStoreGoodsInfoActivity() {
            return storeGoodsInfoActivity;
        }

        public void setStoreGoodsInfoActivity(StoreGoodsInfoActivityBean storeGoodsInfoActivity) {
            this.storeGoodsInfoActivity = storeGoodsInfoActivity;
        }

        public int getManyGoodsInfoFlag() {
            return manyGoodsInfoFlag;
        }

        public void setManyGoodsInfoFlag(int manyGoodsInfoFlag) {
            this.manyGoodsInfoFlag = manyGoodsInfoFlag;
        }

        public CouponActivityBean getCouponActivity() {
            return couponActivity;
        }

        public void setCouponActivity(CouponActivityBean couponActivity) {
            this.couponActivity = couponActivity;
        }

        public int getEvaluateTotalNumber() {
            return evaluateTotalNumber;
        }

        public void setEvaluateTotalNumber(int evaluateTotalNumber) {
            this.evaluateTotalNumber = evaluateTotalNumber;
        }

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

        public List<EvaluateListBean> getEvaluateList() {
            return evaluateList;
        }

        public void setEvaluateList(List<EvaluateListBean> evaluateList) {
            this.evaluateList = evaluateList;
        }

        public static class MemberLevelBean {
            /**
             * adminId : 2
             * createTime : 1545455400000
             * discountPrice : 16600
             * experienceShoppingTimes : 0
             * memberBenefitsIdList : []
             * memberDay : 0
             * memberLevelId : 2
             * memberLevelName : VIP一星会员
             * memberType : 1
             * modifyTime : 1546852183000
             * openPrice : 19800
             * timeUnit : 4
             */

            private int adminId;
            private long createTime;
            private int discountPrice;
            private int experienceShoppingTimes;
            private int memberDay;
            private int memberLevelId;
            private String memberLevelName;
            private int memberType;
            private long modifyTime;
            private int openPrice;
            private int timeUnit;
            private List<?> memberBenefitsIdList;

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(int discountPrice) {
                this.discountPrice = discountPrice;
            }

            public int getExperienceShoppingTimes() {
                return experienceShoppingTimes;
            }

            public void setExperienceShoppingTimes(int experienceShoppingTimes) {
                this.experienceShoppingTimes = experienceShoppingTimes;
            }

            public int getMemberDay() {
                return memberDay;
            }

            public void setMemberDay(int memberDay) {
                this.memberDay = memberDay;
            }

            public int getMemberLevelId() {
                return memberLevelId;
            }

            public void setMemberLevelId(int memberLevelId) {
                this.memberLevelId = memberLevelId;
            }

            public String getMemberLevelName() {
                return memberLevelName;
            }

            public void setMemberLevelName(String memberLevelName) {
                this.memberLevelName = memberLevelName;
            }

            public int getMemberType() {
                return memberType;
            }

            public void setMemberType(int memberType) {
                this.memberType = memberType;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public int getOpenPrice() {
                return openPrice;
            }

            public void setOpenPrice(int openPrice) {
                this.openPrice = openPrice;
            }

            public int getTimeUnit() {
                return timeUnit;
            }

            public void setTimeUnit(int timeUnit) {
                this.timeUnit = timeUnit;
            }

            public List<?> getMemberBenefitsIdList() {
                return memberBenefitsIdList;
            }

            public void setMemberBenefitsIdList(List<?> memberBenefitsIdList) {
                this.memberBenefitsIdList = memberBenefitsIdList;
            }
        }

        public static class CouponActivityBean {
            /**
             * couponActivityId : 5
             * couponName : 测试自动优惠券
             * couponPrice : 1000
             * couponRuleType : 3
             * couponXPrice : 0
             */

            private int couponActivityId;
            private String couponName;
            private int couponPrice;
            private int couponRuleType;
            private int couponXPrice;

            public int getCouponActivityId() {
                return couponActivityId;
            }

            public void setCouponActivityId(int couponActivityId) {
                this.couponActivityId = couponActivityId;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public int getCouponPrice() {
                return couponPrice;
            }

            public void setCouponPrice(int couponPrice) {
                this.couponPrice = couponPrice;
            }

            public int getCouponRuleType() {
                return couponRuleType;
            }

            public void setCouponRuleType(int couponRuleType) {
                this.couponRuleType = couponRuleType;
            }

            public int getCouponXPrice() {
                return couponXPrice;
            }

            public void setCouponXPrice(int couponXPrice) {
                this.couponXPrice = couponXPrice;
            }
        }

        public static class GoodsDetailBean {
            /**
             * basedFreight : 0
             * getRulePointScore : 0
             * goodsCateId : 3
             * goodsId : 290
             * goodsName : 大枣
             * goodsNameSub : 而提供人
             * goodsPicUrl :
             * goodsSalePrice : 36
             * goodsSlideList : [{"goodsId":290,"goodsImgId":55,"goodsImgUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsType":0},{"goodsId":290,"goodsImgId":56,"goodsImgUrl":"http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png","goodsType":1}]
             * memberPrice : 36
             * pinkageActivityId : 0
             * pinkageName :
             * pointSetOpenFlag : 1
             * realPayFullXPrice : 0
             * specAndSpecDetailName : 颜色:红色;大小:XL
             * storeGoodsId : 56
             * storeGoodsInfoId : 61
             * storeId : 124
             * supportNameList : ["呵呵1","限时包邮"]
             */

            private int basedFreight;
            private int getRulePointScore;
            private int goodsCateId;
            private int goodsId;
            private String goodsName;
            private String goodsNameSub;
            private String goodsPicUrl;
            private int goodsSalePrice;
            private int memberPrice;
            private int pinkageActivityId;
            private String pinkageName;
            private int pointSetOpenFlag;
            private int realPayFullXPrice;
            private String specAndSpecDetailName;
            private int storeGoodsId;
            private int storeGoodsInfoId;
            private int storeId;
            private List<GoodsSlideListBean> goodsSlideList;
            private List<String> supportNameList;

            private int pinkageFlag;

            public int getPinkageFlag() {
                return pinkageFlag;
            }

            public void setPinkageFlag(int pinkageFlag) {
                this.pinkageFlag = pinkageFlag;
            }

            public int getBasedFreight() {
                return basedFreight;
            }

            public void setBasedFreight(int basedFreight) {
                this.basedFreight = basedFreight;
            }

            public int getGetRulePointScore() {
                return getRulePointScore;
            }

            public void setGetRulePointScore(int getRulePointScore) {
                this.getRulePointScore = getRulePointScore;
            }

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

            public int getMemberPrice() {
                return memberPrice;
            }

            public void setMemberPrice(int memberPrice) {
                this.memberPrice = memberPrice;
            }

            public int getPinkageActivityId() {
                return pinkageActivityId;
            }

            public void setPinkageActivityId(int pinkageActivityId) {
                this.pinkageActivityId = pinkageActivityId;
            }

            public String getPinkageName() {
                return pinkageName;
            }

            public void setPinkageName(String pinkageName) {
                this.pinkageName = pinkageName;
            }

            public int getPointSetOpenFlag() {
                return pointSetOpenFlag;
            }

            public void setPointSetOpenFlag(int pointSetOpenFlag) {
                this.pointSetOpenFlag = pointSetOpenFlag;
            }

            public int getRealPayFullXPrice() {
                return realPayFullXPrice;
            }

            public void setRealPayFullXPrice(int realPayFullXPrice) {
                this.realPayFullXPrice = realPayFullXPrice;
            }

            public String getSpecAndSpecDetailName() {
                return specAndSpecDetailName;
            }

            public void setSpecAndSpecDetailName(String specAndSpecDetailName) {
                this.specAndSpecDetailName = specAndSpecDetailName;
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

            public List<GoodsSlideListBean> getGoodsSlideList() {
                return goodsSlideList;
            }

            public void setGoodsSlideList(List<GoodsSlideListBean> goodsSlideList) {
                this.goodsSlideList = goodsSlideList;
            }

            public List<String> getSupportNameList() {
                return supportNameList;
            }

            public void setSupportNameList(List<String> supportNameList) {
                this.supportNameList = supportNameList;
            }

            public static class GoodsSlideListBean {
                /**
                 * goodsId : 290
                 * goodsImgId : 55
                 * goodsImgUrl : http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png
                 * goodsType : 0
                 */

                private int goodsId;
                private int goodsImgId;
                private String goodsImgUrl;
                private int goodsType;

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public int getGoodsImgId() {
                    return goodsImgId;
                }

                public void setGoodsImgId(int goodsImgId) {
                    this.goodsImgId = goodsImgId;
                }

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
             * info : 营养价值：维生素
             */

            private String info;

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }
        }

        public static class EvaluateListBean {
            /**
             * anonymousFlag : 1
             * createTime : 2018-08-22 19:42:22
             * customerImgUrl : https://ygshopbucket.oss-cn-beijing.aliyuncs.com/%7B3A83693A-F03A-9B94-C3DA-D9EB58FB515B%7D.png
             * customerName : 测试333333
             * evaluateContent : 这是个商品评价
             * shareImgList : [{"imageUrl":"https://ygshopbucket.oss-cn-beijing.aliyuncs.com/%7B81F6A6B2-31F3-2C87-49D2-F65348F37D8E%7D.png"},{"imageUrl":"https://ygshopbucket.oss-cn-beijing.aliyuncs.com/%7B81F6A6B2-31F3-2C87-49D2-F65348F37D8E%7D.png"}]
             */

            private int anonymousFlag;
            private String createTime;
            private String customerImgUrl;
            private String customerName;
            private String evaluateContent;
            private List<ShareImgListBean> shareImgList;

            public int getAnonymousFlag() {
                return anonymousFlag;
            }

            public void setAnonymousFlag(int anonymousFlag) {
                this.anonymousFlag = anonymousFlag;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCustomerImgUrl() {
                return customerImgUrl;
            }

            public void setCustomerImgUrl(String customerImgUrl) {
                this.customerImgUrl = customerImgUrl;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getEvaluateContent() {
                return evaluateContent;
            }

            public void setEvaluateContent(String evaluateContent) {
                this.evaluateContent = evaluateContent;
            }

            public List<ShareImgListBean> getShareImgList() {
                return shareImgList;
            }

            public void setShareImgList(List<ShareImgListBean> shareImgList) {
                this.shareImgList = shareImgList;
            }

            public static class ShareImgListBean {
                /**
                 * imageUrl : https://ygshopbucket.oss-cn-beijing.aliyuncs.com/%7B81F6A6B2-31F3-2C87-49D2-F65348F37D8E%7D.png
                 */

                private String imageUrl;

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }
            }
        }

        public static class StoreGoodsInfoActivityBean {


            private int activityId;
            private String activityName;
            private int activityType;
            private String discount;
            private String goodsDetailActivityPrcture;


            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getGoodsDetailActivityPrcture() {
                return goodsDetailActivityPrcture;
            }

            public void setGoodsDetailActivityPrcture(String goodsDetailActivityPrcture) {
                this.goodsDetailActivityPrcture = goodsDetailActivityPrcture;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public int getActivityId() {
                return activityId;
            }

            public void setActivityId(int activityId) {
                this.activityId = activityId;
            }

            public int getActivityType() {
                return activityType;
            }

            public void setActivityType(int activityType) {
                this.activityType = activityType;
            }
        }

    }
}
