package com.pbph.yuguo.request;

import com.pbph.yuguo.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/23.
 */

public class CommitFeedbackResquest extends BaseRequest {

    //    customerId 	[string] 	是	用户id
//    feedbackType 	[string] 	是	意见类型（1功能异常，2优化建议）
//    feedbackUrl 	[string] 	是	图片
//    feedbackContent 	[string] 	是	内容
//    feedbackContact 	[string] 	是	联系方式
    private int customerId;
    private String feedbackType;
    private String feedbackUrl;
    private String feedbackContent;
    private String feedbackContact;

    public CommitFeedbackResquest(int customerId, String feedbackType, String feedbackUrl, String
            feedbackContent, String feedbackContact) {
        this.customerId = customerId;
        this.feedbackType = feedbackType;
        this.feedbackUrl = feedbackUrl;
        this.feedbackContent = feedbackContent;
        this.feedbackContact = feedbackContact;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("customerId", customerId);
            object.put("feedbackType", feedbackType);
            object.put("feedbackUrl", feedbackUrl);
            object.put("feedbackContent", feedbackContent);
            object.put("feedbackContact", feedbackContact);
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
