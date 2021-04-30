package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Upload2ossResquest extends BaseRequest {

    String file;
    String fileType;
    String suffixName;


    public Upload2ossResquest(String file, String fileType, String suffixName) {
        this.file = file;
        this.fileType = fileType;
        this.suffixName = suffixName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("file", file);
            object.put("fileType", fileType);
            object.put("suffixName", suffixName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
