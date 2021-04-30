package com.pbph.yuguo.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/16.
 */

public class BaseResponse implements Serializable{
    private String code = "";
    private String msg = "";
//    private T data;


//    public int getCode() {
//        return this.code;
//    }
//
//    public boolean isSuccess() {
//        return this.code == 0;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getError() {
//        return this.message;
//    }
//
//    public void setError(String error) {
//        this.message = error;
//    }
//
//    public T getResult() {
//        return this.data;
//    }
//
//    public void setResult(T result) {
//        this.data = result;
//    }

    public int getCode() {
        return Integer.valueOf(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

//    public String toString() {
//        return "httpResponse{code=" + this.code + ", error='" + this.message + '\'' + ", result=" + this.data + '}';
//    }
}

