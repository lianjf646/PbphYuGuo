package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 查询消息信息以及未读消息数量
 */

public class GetMessageInfoAndUnreadMessageCountResponse extends BaseResponse {


    /**
     * data : {"list":[{"count":0,"createTime":"2019-04-28 11:28:23","goodsPicUrl":"444",
     * "messageContent":"您在【门店名称】的自提订单提货码为6068，请及时取货哦！门店地址：南岗区海河东路138号；门店电话：13636352365。点击查看详情>>","messageType":1,
     * "messageTypeName":"订单消息","receiveUserId":185}],"totalCount":0}
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
         * list : [{"count":0,"createTime":"2019-04-28 11:28:23","goodsPicUrl":"444",
         * "messageContent":"您在【门店名称】的自提订单提货码为6068，请及时取货哦！门店地址：南岗区海河东路138号；门店电话：13636352365。点击查看详情>>","messageType":1,
         * "messageTypeName":"订单消息","receiveUserId":185}]
         * totalCount : 0
         */

        private int totalCount;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * count : 0
             * createTime : 2019-04-28 11:28:23
             * goodsPicUrl : 444
             * messageContent : 您在【门店名称】的自提订单提货码为6068，请及时取货哦！门店地址：南岗区海河东路138号；门店电话：13636352365。点击查看详情>>
             * messageType : 1
             * messageTypeName : 订单消息
             * receiveUserId : 185
             */

            private int count;
            private String createTime;
            private String goodsPicUrl;
            private String messageContent;
            private int messageType;
            private String messageTypeName;
            private int receiveUserId;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

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

            public int getMessageType() {
                return messageType;
            }

            public void setMessageType(int messageType) {
                this.messageType = messageType;
            }

            public String getMessageTypeName() {
                return messageTypeName;
            }

            public void setMessageTypeName(String messageTypeName) {
                this.messageTypeName = messageTypeName;
            }

            public int getReceiveUserId() {
                return receiveUserId;
            }

            public void setReceiveUserId(int receiveUserId) {
                this.receiveUserId = receiveUserId;
            }
        }
    }
}
