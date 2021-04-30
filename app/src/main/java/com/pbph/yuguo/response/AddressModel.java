package com.pbph.yuguo.response;

/**
 * 地址实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class AddressModel {
    private String userName;
    private String phone;
    private String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
