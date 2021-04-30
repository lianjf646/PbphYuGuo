package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 */

public class GetAddressListByCustomerIdResponse extends BaseResponse {


    /**
     * data : {"receiverAddressdto":[{"addressId":18,"customerId":11,"defaultFlag":0,"receiverAddressDetail":"你问一下",
     * "receiverLat":"45.767676","receiverLng":"126.716218","receiverName":"么么么","receiverPhone":"15636899875",
     * "receiverVillage":"信恒现代城"},{"addressId":17,"customerId":11,"defaultFlag":1,"receiverAddressDetail":"www",
     * "receiverLat":"45.765297","receiverLng":"126.712145","receiverName":"不我","receiverPhone":"15636899875",
     * "receiverVillage":"泰海花园小区"},{"addressId":16,"customerId":11,"defaultFlag":0,"receiverAddressDetail":"我问一下",
     * "receiverLat":"45.765297","receiverLng":"126.712145","receiverName":"急急急","receiverPhone":"15636899875",
     * "receiverVillage":"泰海花园小区"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ReceiverAddressdtoBean> receiverAddressdto;

        public List<ReceiverAddressdtoBean> getReceiverAddressdto() {
            return receiverAddressdto;
        }

        public void setReceiverAddressdto(List<ReceiverAddressdtoBean> receiverAddressdto) {
            this.receiverAddressdto = receiverAddressdto;
        }

        public static class ReceiverAddressdtoBean implements Serializable {
            /**
             * addressId : 18
             * customerId : 11
             * defaultFlag : 0
             * receiverAddressDetail : 你问一下
             * receiverLat : 45.767676
             * receiverLng : 126.716218
             * receiverName : 么么么
             * receiverPhone : 15636899875
             * receiverVillage : 信恒现代城
             */

            private String receiverCityName;
            private int addressId;
            private int customerId;
            private int defaultFlag;
            private String receiverAddressDetail;
            private String receiverLat;
            private String receiverLng;
            private String receiverName;
            private String receiverPhone;
            private String receiverVillage;


            public String getReceiverCityName() {
                if (receiverCityName==null){
                    return "哈尔滨市";
                }
                return receiverCityName;
            }

            public void setReceiverCityName(String receiverCityName) {
                this.receiverCityName = receiverCityName;
            }

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
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

            public String getReceiverVillage() {
                return receiverVillage;
            }

            public void setReceiverVillage(String receiverVillage) {
                this.receiverVillage = receiverVillage;
            }
        }
    }
}
