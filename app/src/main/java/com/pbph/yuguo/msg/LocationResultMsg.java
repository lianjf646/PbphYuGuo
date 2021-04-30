package com.pbph.yuguo.msg;

/**
 * Created by Administrator on 2018/9/4.
 */

public class LocationResultMsg {
    public String longitude;  //纬度
    public String latitude;   //经度

    public LocationResultMsg(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
