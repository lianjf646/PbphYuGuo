package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 *
 */

public class GetMessageInfoListResponse extends BaseResponse {


    /**
     * data : {"list":[{"createTime":"2019-05-08 10:26:48","goodsPicUrl":"10","messageContent":"您的订单已发货","messageId":10,
     * "messageTitle":"您的订单已发货","orderId":0},{"createTime":"2019-05-08 10:26:27","goodsPicUrl":"999",
     * "messageContent":"您的订单已发货","messageId":9,"messageTitle":"您的订单已发货","orderId":99999},{"createTime":"2019-05-08 10:26:01",
     * "goodsPicUrl":"888","messageContent":"您的订单已发货","messageId":8,"messageTitle":"您的订单已发货","orderId":88888},
     * {"createTime":"2019-05-08 10:25:43","goodsPicUrl":"777","messageContent":"您的订单已发货","messageId":7,
     * "messageTitle":"您的订单已发货","orderId":77777},{"createTime":"2019-05-08 10:25:22","goodsPicUrl":"666",
     * "messageContent":"您的订单已发货","messageId":6,"messageTitle":"您的订单已发货","orderId":66666},{"createTime":"2019-05-08 10:24:57",
     * "goodsPicUrl":"555","messageContent":"您的订单已发货","messageId":5,"messageTitle":"您的订单已发货","orderId":55555},
     * {"createTime":"2019-04-28 11:28:23","goodsPicUrl":"444",
     * "messageContent":"您在【门店名称】的自提订单提货码为6068，请及时取货哦！门店地址：南岗区海河东路138号；门店电话：13636352365。点击查看详情>>","messageId":4,
     * "messageTitle":"订单待自提","orderId":44444},{"createTime":"2019-04-28 11:26:52","goodsPicUrl":"222",
     * "messageContent":"您的订单A1236325412554已取消。点击查看详情>>","messageId":2,"messageTitle":"您的订单已取消","orderId":22222},
     * {"createTime":"2019-04-28 11:25:42","goodsPicUrl":"111","messageContent":"您的订单【订单号】已发货。请注意查收，如有问题请联系花果山客服【客服电话】。点击查看详情
     * >>","messageId":1,"messageTitle":"您的订单已发货","orderId":11111}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createTime : 2019-05-08 10:26:48
             * goodsPicUrl : 10
             * messageContent : 您的订单已发货
             * messageId : 10
             * messageTitle : 您的订单已发货
             * orderId : 0
             */

            private String createTime;
            private String goodsPicUrl;
            private String messageContent;
            private int messageId;
            private String messageTitle;
            private int orderId;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getGoodsPicUrl() {
                return goodsPicUrl;
            }

            public void setGoodsPicUrl(String goodsPicUrl) {
                this.goodsPicUrl = goodsPicUrl;
            }

            public String getMessageContent() {
                return messageContent;
            }

            public void setMessageContent(String messageContent) {
                this.messageContent = messageContent;
            }

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public String getMessageTitle() {
                return messageTitle;
            }

            public void setMessageTitle(String messageTitle) {
                this.messageTitle = messageTitle;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }
        }
    }
}
