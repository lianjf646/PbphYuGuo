package com.pbph.yuguo.response;


import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 获取积分来源列表
 * Created by Administrator on 2018/5/23.
 */

public class GetMyPointSourceListResponse extends BaseResponse {


    /**
     * data : {"pointList":[{"createTime":1534238366000,"pointDetail":"充值赠送","pointScore":50000,
     * "pointType":0},{"createTime":1534238366000,"pointDetail":"购买商品赠送","pointScore":150000,
     * "pointType":0},{"createTime":1534238366000,"pointDetail":"购买商品抵扣","pointScore":-30000,
     * "pointType":1},{"createTime":1534249273000,"pointDetail":"充值多增100积分","pointScore":100,
     * "pointType":0},{"createTime":1534249306000,"pointDetail":"赠送错误扣除100积分","pointScore":-100,
     * "pointType":1},{"createTime":1535973620000,"pointDetail":"充值赠送","pointScore":-100,
     * "pointType":1},{"createTime":1536135805000,"pointDetail":"bbbbbb","pointScore":100,
     * "pointType":0},{"createTime":1536135807000,"pointDetail":"ccccccccc","pointScore":1000,
     * "pointType":0},{"createTime":1536664804000,"pointDetail":"充值多增100积分","pointScore":200,
     * "pointType":0},{"createTime":1536751530000,"pointDetail":"aaaaaa","pointScore":-200,
     * "pointType":0},{"createTime":1537442385000,"pointDetail":"赠送错误扣除100积分","pointScore":100,
     * "pointType":1}],"sumPointScore":171100,"totleSize":11}
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
         * pointList : [{"createTime":1534238366000,"pointDetail":"充值赠送","pointScore":50000,
         * "pointType":0},{"createTime":1534238366000,"pointDetail":"购买商品赠送","pointScore":150000,
         * "pointType":0},{"createTime":1534238366000,"pointDetail":"购买商品抵扣","pointScore":-30000,
         * "pointType":1},{"createTime":1534249273000,"pointDetail":"充值多增100积分","pointScore":100,
         * "pointType":0},{"createTime":1534249306000,"pointDetail":"赠送错误扣除100积分",
         * "pointScore":-100,"pointType":1},{"createTime":1535973620000,"pointDetail":"充值赠送",
         * "pointScore":-100,"pointType":1},{"createTime":1536135805000,"pointDetail":"bbbbbb",
         * "pointScore":100,"pointType":0},{"createTime":1536135807000,"pointDetail":"ccccccccc",
         * "pointScore":1000,"pointType":0},{"createTime":1536664804000,
         * "pointDetail":"充值多增100积分","pointScore":200,"pointType":0},{"createTime":1536751530000,
         * "pointDetail":"aaaaaa","pointScore":-200,"pointType":0},{"createTime":1537442385000,
         * "pointDetail":"赠送错误扣除100积分","pointScore":100,"pointType":1}]
         * sumPointScore : 171100
         * totleSize : 11
         */

        private int sumPointScore;
        private int totleSize;
        private List<PointListBean> pointList;

        public int getSumPointScore() {
            return sumPointScore;
        }

        public void setSumPointScore(int sumPointScore) {
            this.sumPointScore = sumPointScore;
        }

        public int getTotleSize() {
            return totleSize;
        }

        public void setTotleSize(int totleSize) {
            this.totleSize = totleSize;
        }

        public List<PointListBean> getPointList() {
            return pointList;
        }

        public void setPointList(List<PointListBean> pointList) {
            this.pointList = pointList;
        }

        public static class PointListBean {
            /**
             * createTime : 1534238366000
             * pointDetail : 充值赠送
             * pointScore : 50000
             * pointType : 0
             */

            private String createTime;
            private String pointDetail;
            private int pointScore;
            private int pointType;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getPointDetail() {
                return pointDetail;
            }

            public void setPointDetail(String pointDetail) {
                this.pointDetail = pointDetail;
            }

            public int getPointScore() {
                return pointScore;
            }

            public void setPointScore(int pointScore) {
                this.pointScore = pointScore;
            }

            public int getPointType() {
                return pointType;
            }

            public void setPointType(int pointType) {
                this.pointType = pointType;
            }
        }
    }
}
