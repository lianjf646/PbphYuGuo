package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 获取收货地址列表（收货地址是否在配送范围）
 */

public class GetReceiverAddressListResponse extends BaseResponse implements Serializable {

    /**
     * data : {"receiverAddressDtoList":[{"addressId":11,"defaultFlag":0,
     * "receiverAddressDetail":"么么么","receiverCityName":"","receiverLat":"45.76481429023068",
     * "receiverLng":"126.71212757950802","receiverName":"看看","receiverPhone":"15636899875",
     * "receiverProvinceName":"","receiverRigionName":""},{"addressId":10,"defaultFlag":0,
     * "receiverAddressDetail":"么么么","receiverCityName":"","receiverLat":"45.76481429023068",
     * "receiverLng":"126.71212757950802","receiverName":"看看","receiverPhone":"15636899875",
     * "receiverProvinceName":"","receiverRigionName":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<ReceiverAddressDtoBean> receiverAddressDtoList;

        public List<ReceiverAddressDtoBean> getReceiverAddressDtoList() {
            return receiverAddressDtoList;
        }

        public void setReceiverAddressDtoList(List<ReceiverAddressDtoBean>
                                                      receiverAddressDtoList) {
            this.receiverAddressDtoList = receiverAddressDtoList;
        }

        public static class ReceiverAddressDtoBean implements Serializable{
            /**
             * addressId : 11
             * defaultFlag : 0
             * receiverAddressDetail : 么么么
             * receiverCityName :
             * receiverLat : 45.76481429023068
             * receiverLng : 126.71212757950802
             * receiverName : 看看
             * receiverPhone : 15636899875
             * receiverProvinceName :
             * receiverRigionName :
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

            public String getReceiverVillage() {
                return receiverVillage;
            }

            public void setReceiverVillage(String receiverVillage) {
                this.receiverVillage = receiverVillage;
            }

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
        }
    }
}
