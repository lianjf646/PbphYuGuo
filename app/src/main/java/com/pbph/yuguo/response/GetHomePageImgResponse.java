package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:
 */

public class GetHomePageImgResponse extends BaseResponse {


    /**
     * data : {"dto":{"advertisingPositionImg":"http://image.pbdsh.com/ygtestfold/28/1BA7680DF0F2C49B666A52687A21AAAE.png",
     * "advertisingPositionIsShow":1,"advertisingPositionTitle":"标题","advertisingPositionUrl":"https://www.baidu.com/",
     * "serviceImg":"http://image.pbdsh.com/ygtestfold/28/2D9AA1018FB99A03E6D69B1CEAEE5BD9.png","serviceIsShow":1,
     * "serviceTitle":"标题","serviceUrl":"","todayHotSaleImg":"http://image.pbdsh
     * .com/ygtestfold/28/1E4C6E075805F7483378454BFB5D35FB.png"}}
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
         * dto : {"advertisingPositionImg":"http://image.pbdsh.com/ygtestfold/28/1BA7680DF0F2C49B666A52687A21AAAE.png",
         * "advertisingPositionIsShow":1,"advertisingPositionTitle":"标题","advertisingPositionUrl":"https://www.baidu.com/",
         * "serviceImg":"http://image.pbdsh.com/ygtestfold/28/2D9AA1018FB99A03E6D69B1CEAEE5BD9.png","serviceIsShow":1,
         * "serviceTitle":"标题","serviceUrl":"","todayHotSaleImg":"http://image.pbdsh
         * .com/ygtestfold/28/1E4C6E075805F7483378454BFB5D35FB.png"}
         */

        private DtoBean dto;
        private String storeId;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public DtoBean getDto() {
            return dto;
        }

        public void setDto(DtoBean dto) {
            this.dto = dto;
        }

        public static class DtoBean {
            /**
             * advertisingPositionImg : http://image.pbdsh.com/ygtestfold/28/1BA7680DF0F2C49B666A52687A21AAAE.png
             * advertisingPositionIsShow : 1
             * advertisingPositionTitle : 标题
             * advertisingPositionUrl : https://www.baidu.com/
             * serviceImg : http://image.pbdsh.com/ygtestfold/28/2D9AA1018FB99A03E6D69B1CEAEE5BD9.png
             * serviceIsShow : 1
             * serviceTitle : 标题
             * serviceUrl :
             * todayHotSaleImg : http://image.pbdsh.com/ygtestfold/28/1E4C6E075805F7483378454BFB5D35FB.png
             */

            private String advertisingPositionImg;
            private int advertisingPositionIsShow;
            private String advertisingPositionTitle;
            private String advertisingPositionUrl;
            private String serviceImg;
            private int serviceIsShow;
            private String serviceTitle;
            private String serviceUrl;
            private String todayHotSaleImg;
            private int type;
            private int memberDayActivityId;

            public int getMemberDayActivityId() {
                return memberDayActivityId;
            }

            public void setMemberDayActivityId(int memberDayActivityId) {
                this.memberDayActivityId = memberDayActivityId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAdvertisingPositionImg() {
                return advertisingPositionImg;
            }

            public void setAdvertisingPositionImg(String advertisingPositionImg) {
                this.advertisingPositionImg = advertisingPositionImg;
            }

            public int getAdvertisingPositionIsShow() {
                return advertisingPositionIsShow;
            }

            public void setAdvertisingPositionIsShow(int advertisingPositionIsShow) {
                this.advertisingPositionIsShow = advertisingPositionIsShow;
            }

            public String getAdvertisingPositionTitle() {
                return advertisingPositionTitle;
            }

            public void setAdvertisingPositionTitle(String advertisingPositionTitle) {
                this.advertisingPositionTitle = advertisingPositionTitle;
            }

            public String getAdvertisingPositionUrl() {
                return advertisingPositionUrl;
            }

            public void setAdvertisingPositionUrl(String advertisingPositionUrl) {
                this.advertisingPositionUrl = advertisingPositionUrl;
            }

            public String getServiceImg() {
                return serviceImg;
            }

            public void setServiceImg(String serviceImg) {
                this.serviceImg = serviceImg;
            }

            public int getServiceIsShow() {
                return serviceIsShow;
            }

            public void setServiceIsShow(int serviceIsShow) {
                this.serviceIsShow = serviceIsShow;
            }

            public String getServiceTitle() {
                return serviceTitle;
            }

            public void setServiceTitle(String serviceTitle) {
                this.serviceTitle = serviceTitle;
            }

            public String getServiceUrl() {
                return serviceUrl;
            }

            public void setServiceUrl(String serviceUrl) {
                this.serviceUrl = serviceUrl;
            }

            public String getTodayHotSaleImg() {
                return todayHotSaleImg;
            }

            public void setTodayHotSaleImg(String todayHotSaleImg) {
                this.todayHotSaleImg = todayHotSaleImg;
            }
        }
    }
}
