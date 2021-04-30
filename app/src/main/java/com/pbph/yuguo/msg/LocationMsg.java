package com.pbph.yuguo.msg;

/**
 * Created by Administrator on 2018/9/4.
 */

public class LocationMsg {

    public boolean succ;
    public boolean loc;

    public String street;

    public LocationMsg(boolean succ) {
        this.succ = succ;
    }
}
