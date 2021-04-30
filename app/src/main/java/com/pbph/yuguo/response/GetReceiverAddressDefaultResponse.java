package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetReceiverAddressDefaultResponse extends BaseResponse {

    /**
     * data : {"receiverAddressDto":{"addressId":33,"defaultFlag":1,"receiverAddressDetail":"慢慢","receiverCityName":"哈尔滨市","receiverLat":"","receiverLng":"","receiverName":"龙珠","receiverPhone":"123","receiverProvinceName":"","receiverRigionName":"","receiverVillage":"信恒现代城"}}
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
         * receiverAddressDto : {"addressId":33,"defaultFlag":1,"receiverAddressDetail":"慢慢","receiverCityName":"哈尔滨市","receiverLat":"","receiverLng":"","receiverName":"龙珠","receiverPhone":"123","receiverProvinceName":"","receiverRigionName":"","receiverVillage":"信恒现代城"}
         */

        private ReceiverAddressDtoBean receiverAddressDto;

        public ReceiverAddressDtoBean getReceiverAddressDto() {
            return receiverAddressDto;
        }

        public void setReceiverAddressDto(ReceiverAddressDtoBean receiverAddressDto) {
            this.receiverAddressDto = receiverAddressDto;
        }

        public static class ReceiverAddressDtoBean {
            /**
             * addressId : 33
             * defaultFlag : 1
             * receiverAddressDetail : 慢慢
             * receiverCityName : 哈尔滨市
             * receiverLat :
             * receiverLng :
             * receiverName : 龙珠
             * receiverPhone : 123
             * receiverProvinceName :
             * receiverRigionName :
             * receiverVillage : 信恒现代城
             */

            private int addressId;
            private int defaultFlag;
            private String receiverAddressDetail;
            private String receiverCityName;
            private String receiverLat;
            private String receiverLng;
            private String receiverName;
            private String receiverPhone;
            private String receiverProvinceName;
            private String receiverRigionName;
            private String receiverVillage;

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public int getDefaultFlag() {
                return defaultFlag;
            }

            public void setDefaultFlag(int defaultFlag) {
                this.defaultFlag = defaultFlag;
            }

            public String getReceiverAddressDetail() {
                return receiverAddressDetail;
            }

            public void setReceiverAddressDetail(String receiverAddressDetail) {
                this.receiverAddressDetail = receiverAddressDetail;
            }

            public String getReceiverCityName() {
                return receiverCityName;
            }

            public void setReceiverCityName(String receiverCityName) {
                this.receiverCityName = receiverCityName;
            }

            public String getReceiverLat() {
                return receiverLat;
            }

            public void setReceiverLat(String receiverLat) {
                this.receiverLat = receiverLat;
            }

            public String getReceiverLng() {
                return receiverLng;
            }

            public void setReceiverLng(String receiverLng) {
                this.receiverLng = receiverLng;
            }

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
            }

            public String getReceiverPhone() {
                return receiverPhone;
            }

            public void setReceiverPhone(String receiverPhone) {
                this.receiverPhone = receiverPhone;
            }

            public String getReceiverProvinceName() {
                return receiverProvinceName;
            }

            public void setReceiverProvinceName(String receiverProvinceName) {
                this.receiverProvinceName = receiverProvinceName;
            }

            public String getReceiverRigionName() {
                return receiverRigionName;
            }

            public void setReceiverRigionName(String receiverRigionName) {
                this.receiverRigionName = receiverRigionName;
            }

            public String getReceiverVillage() {
                return receiverVillage;
            }

            public void setReceiverVillage(String receiverVillage) {
                this.receiverVillage = receiverVillage;
            }
        }
    }
}
