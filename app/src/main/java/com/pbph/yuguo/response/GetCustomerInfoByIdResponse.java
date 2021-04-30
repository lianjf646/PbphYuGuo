package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 根据用户id查询用户详情
 * Created by zyp on 2018/8/13 0013.
 */

public class GetCustomerInfoByIdResponse extends BaseResponse {
    /**
     * data : {"couponNo":0,"customer":{"createTime":1546849552000,"customerBirthday":"","customerId":5,"customerImgUrl":"","customerLevelId":0,"customerLevelType":0,"customerName":"13333333333","customerPhone":"13333333333","customerRealName":"","customerSex":0,"customerTotalPoint":0,"customerVipExpireTime":null,"equipmentNumber":"","firstLat":"","firstLng":"","firstLoginFlag":0,"freezeFlag":0,"freezeTime":null,"idCardNo":"","idCardUrl":"","loginFacility":0,"loginIp":"","loginTime":1546851173000,"openId":"","payPassword":"","saltVal":"","storedMoney":0,"wxNickName":""}}
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
         * couponNo : 0          * customer : {"createTime":1546849552000,"customerBirthday":"","customerId":5,"customerImgUrl":"","customerLevelId":0,"customerLevelType":0,"customerName":"13333333333","customerPhone":"13333333333","customerRealName":"","customerSex":0,"customerTotalPoint":0,"customerVipExpireTime":null,"equipmentNumber":"","firstLat":"","firstLng":"","firstLoginFlag":0,"freezeFlag":0,"freezeTime":null,"idCardNo":"","idCardUrl":"","loginFacility":0,"loginIp":"","loginTime":1546851173000,"openId":"","payPassword":"","saltVal":"","storedMoney":0,"wxNickName":""}
         */
        private int couponNo;
        private CustomerBean customer;

        public int getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(int couponNo) {
            this.couponNo = couponNo;
        }

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public static class CustomerBean {
            /**
             * createTime : 1546849552000              * customerBirthday :               * customerId : 5              * customerImgUrl :               * customerLevelId : 0              * customerLevelType : 0              * customerName : 13333333333              * customerPhone : 13333333333              * customerRealName :               * customerSex : 0              * customerTotalPoint : 0              * customerVipExpireTime : null              * equipmentNumber :               * firstLat :               * firstLng :               * firstLoginFlag : 0              * freezeFlag : 0              * freezeTime : null              * idCardNo :               * idCardUrl :               * loginFacility : 0              * loginIp :               * loginTime : 1546851173000              * openId :               * payPassword :               * saltVal :               * storedMoney : 0              * wxNickName :
             */
            private long createTime;
            private String customerBirthday;
            private int customerId;
            private String customerImgUrl;
            private int customerLevelId;
            private int customerLevelType;//会员类型（0普通，1试用，2正式）
            private String customerName;
            private String customerPhone;
            private String customerRealName;
            private int customerSex;
            private int customerTotalPoint;
            private String customerVipExpireTime;
            private String customerVipExpireTimeString;
            private String equipmentNumber;
            private String firstLat;
            private String firstLng;
            private int firstLoginFlag;
            private int freezeFlag;
            private Object freezeTime;
            private String idCardNo;
            private String idCardUrl;
            private int loginFacility;
            private String loginIp;
            private long loginTime;
            private String openId;
            private String payPassword;
            private String saltVal;
            private int storedMoney;
            private String wxNickName;
            private String customerUuid;


//            public String getRedPackageMoney() {
//                return redPackageMoney;
//            }
//
//            public void setRedPackageMoney(String redPackageMoney) {
//                this.redPackageMoney = redPackageMoney;
//            }


            public String getCustomerUuid() {
                return customerUuid;
            }

            public void setCustomerUuid(String customerUuid) {
                this.customerUuid = customerUuid;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getCustomerBirthday() {
                return customerBirthday;
            }

            public void setCustomerBirthday(String customerBirthday) {
                this.customerBirthday = customerBirthday;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getCustomerImgUrl() {
                return customerImgUrl;
            }

            public void setCustomerImgUrl(String customerImgUrl) {
                this.customerImgUrl = customerImgUrl;
            }

            public int getCustomerLevelId() {
                return customerLevelId;
            }

            public void setCustomerLevelId(int customerLevelId) {
                this.customerLevelId = customerLevelId;
            }

            public int getCustomerLevelType() {
                return customerLevelType;
            }

            public void setCustomerLevelType(int customerLevelType) {
                this.customerLevelType = customerLevelType;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public String getCustomerRealName() {
                return customerRealName;
            }

            public void setCustomerRealName(String customerRealName) {
                this.customerRealName = customerRealName;
            }

            public int getCustomerSex() {
                return customerSex;
            }

            public void setCustomerSex(int customerSex) {
                this.customerSex = customerSex;
            }

            public int getCustomerTotalPoint() {
                return customerTotalPoint;
            }

            public void setCustomerTotalPoint(int customerTotalPoint) {
                this.customerTotalPoint = customerTotalPoint;
            }

            public String getCustomerVipExpireTime() {
                return customerVipExpireTime;
            }

            public void setCustomerVipExpireTime(String customerVipExpireTime) {
                this.customerVipExpireTime = customerVipExpireTime;
            }

            public String getCustomerVipExpireTimeString() {
                return customerVipExpireTimeString;
            }

            public void setCustomerVipExpireTimeString(String customerVipExpireTimeString) {
                this.customerVipExpireTimeString = customerVipExpireTimeString;
            }

            public String getEquipmentNumber() {
                return equipmentNumber;
            }

            public void setEquipmentNumber(String equipmentNumber) {
                this.equipmentNumber = equipmentNumber;
            }

            public String getFirstLat() {
                return firstLat;
            }

            public void setFirstLat(String firstLat) {
                this.firstLat = firstLat;
            }

            public String getFirstLng() {
                return firstLng;
            }

            public void setFirstLng(String firstLng) {
                this.firstLng = firstLng;
            }

            public int getFirstLoginFlag() {
                return firstLoginFlag;
            }

            public void setFirstLoginFlag(int firstLoginFlag) {
                this.firstLoginFlag = firstLoginFlag;
            }

            public int getFreezeFlag() {
                return freezeFlag;
            }

            public void setFreezeFlag(int freezeFlag) {
                this.freezeFlag = freezeFlag;
            }

            public Object getFreezeTime() {
                return freezeTime;
            }

            public void setFreezeTime(Object freezeTime) {
                this.freezeTime = freezeTime;
            }

            public String getIdCardNo() {
                return idCardNo;
            }

            public void setIdCardNo(String idCardNo) {
                this.idCardNo = idCardNo;
            }

            public String getIdCardUrl() {
                return idCardUrl;
            }

            public void setIdCardUrl(String idCardUrl) {
                this.idCardUrl = idCardUrl;
            }

            public int getLoginFacility() {
                return loginFacility;
            }

            public void setLoginFacility(int loginFacility) {
                this.loginFacility = loginFacility;
            }

            public String getLoginIp() {
                return loginIp;
            }

            public void setLoginIp(String loginIp) {
                this.loginIp = loginIp;
            }

            public long getLoginTime() {
                return loginTime;
            }

            public void setLoginTime(long loginTime) {
                this.loginTime = loginTime;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public String getPayPassword() {
                return payPassword;
            }

            public void setPayPassword(String payPassword) {
                this.payPassword = payPassword;
            }

            public String getSaltVal() {
                return saltVal;
            }

            public void setSaltVal(String saltVal) {
                this.saltVal = saltVal;
            }

            public int getStoredMoney() {
                return storedMoney;
            }

            public void setStoredMoney(int storedMoney) {
                this.storedMoney = storedMoney;
            }

            public String getWxNickName() {
                return wxNickName;
            }

            public void setWxNickName(String wxNickName) {
                this.wxNickName = wxNickName;
            }
        }
    }
}

