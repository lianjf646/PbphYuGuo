package com.pbph.yuguo.application;

import com.pbph.yuguo.util.SpHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/15.
 */

public final class UserInfo implements Serializable {

    public static UserInfo getInstance() {
        return UserInfoHolder.userInfo;
    }

    private static class UserInfoHolder {
        private static UserInfo userInfo = new UserInfo();
    }

    //用户id 内存级缓存。用户登出、程序退出则为null
    private Integer customerId = -1;
    private String token;

    private String customerPhone;

    private String jpushId;


    private int customerLevelType;//会员类型（0普通，1试用，2正式）


    //    经纬度 内存级缓存。 用户获取不到定位 为null
    private Double longitude;  //纬度
    private Double latitude;   //经度

    private String city = "";   //市
    private String street;      // 街道
    private String district;    // 区


    ///默认选择收货地址
    private Integer recAddId = null;//地址id

    private Double recLongitude;  //纬度
    private Double recLatitude;   //经度

    private String recCity = "";   //市
    private String recStreet;      //街道
    private String reDistrict;

    private String recUserName;
    private String recPhone;

    public String getRecUserName() {
        return recUserName;
    }

    public void setRecUserName(String recUserName) {
        this.recUserName = recUserName;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getReDistrict() {
        return reDistrict;
    }

    public void setReDistrict(String reDistrict) {
        this.reDistrict = reDistrict;
    }

    public Integer getCustomerId() {
        return SpHelper.getInstance().getIntUserId();
    }

    public int getCustomerLevelType() {
        return customerLevelType;
    }

    public void setCustomerLevelType(int customerLevelType) {
        this.customerLevelType = customerLevelType;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
        SpHelper.getInstance().setUserID(customerId + "");
    }

    public String getToken() {
        return SpHelper.getInstance().getToken();
    }

    public void setToken(String token) {
        this.token = token;
        SpHelper.getInstance().setToken(token);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getRecAddId() {
        return recAddId;
    }

    public void setRecAddId(Integer recAddId) {
        this.recAddId = recAddId;
    }

    public Double getRecLongitude() {
        return recLongitude;
    }

    public void setRecLongitude(Double recLongitude) {
        this.recLongitude = recLongitude;
    }

    public Double getRecLatitude() {
        return recLatitude;
    }

    public void setRecLatitude(Double recLatitude) {
        this.recLatitude = recLatitude;
    }

    public String getRecCity() {
        return recCity;
    }

    public void setRecCity(String recCity) {
        this.recCity = recCity;
    }

    public String getRecStreet() {
        return recStreet;
    }

    public void setRecStreet(String recStreet) {
        this.recStreet = recStreet;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getJpushId() {
        return jpushId;
    }

    public void setJpushId(String jpushId) {
        this.jpushId = jpushId;
    }
}
