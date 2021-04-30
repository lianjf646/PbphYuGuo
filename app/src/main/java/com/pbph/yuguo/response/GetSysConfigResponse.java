package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:售后规则配置
 */

public class GetSysConfigResponse extends BaseResponse {

    /**
     * data : {"sysConfig":{"adminId":2,"atOnceDeliveryFlag":0,"configId":5,"createTime":1545825276000,"inDueFormFlag":0,"inviteMemberVoucherLastTime":123,"inviteTimeEnd":1544025600000,"inviteTimeStart":1543593600000,"memberLastTime":"3","modifyTime":1546583124000,"onlineService":1,"servicePhone":"123123","storeCloseTime":"23:00","storeOpenTime":"00:05","tomorrowFreight":50,"totalWeight":456456456,"tryOutMemberFlag":1,"warningValue":555}}
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
         * sysConfig : {"adminId":2,"atOnceDeliveryFlag":0,"configId":5,"createTime":1545825276000,"inDueFormFlag":0,"inviteMemberVoucherLastTime":123,"inviteTimeEnd":1544025600000,"inviteTimeStart":1543593600000,"memberLastTime":"3","modifyTime":1546583124000,"onlineService":1,"servicePhone":"123123","storeCloseTime":"23:00","storeOpenTime":"00:05","tomorrowFreight":50,"totalWeight":456456456,"tryOutMemberFlag":1,"warningValue":555}
         */

        private SysConfigBean sysConfig;

        public SysConfigBean getSysConfig() {
            return sysConfig;
        }

        public void setSysConfig(SysConfigBean sysConfig) {
            this.sysConfig = sysConfig;
        }

        public static class SysConfigBean {
            /**
             * adminId : 2
             * atOnceDeliveryFlag : 0
             * configId : 5
             * createTime : 1545825276000
             * inDueFormFlag : 0
             * inviteMemberVoucherLastTime : 123
             * inviteTimeEnd : 1544025600000
             * inviteTimeStart : 1543593600000
             * memberLastTime : 3
             * modifyTime : 1546583124000
             * onlineService : 1
             * servicePhone : 123123
             * storeCloseTime : 23:00
             * storeOpenTime : 00:05
             * tomorrowFreight : 50
             * totalWeight : 456456456
             * tryOutMemberFlag : 1
             * warningValue : 555
             */

            private int adminId;
            private int atOnceDeliveryFlag;
            private int configId;
            private long createTime;
            private int inDueFormFlag;
            private int inviteMemberVoucherLastTime;
            private long inviteTimeEnd;
            private long inviteTimeStart;
            private String memberLastTime;
            private long modifyTime;
            private int onlineService;
            private String servicePhone;
            private String storeCloseTime;
            private String storeOpenTime;
            private int tomorrowFreight;
            private int totalWeight;
            private int tryOutMemberFlag;
            private int warningValue;

            private int newDateRangeFlag;
            private String wxCodeService;

            public String getWxCodeService() {
                return wxCodeService;
            }

            public void setWxCodeService(String wxCodeService) {
                this.wxCodeService = wxCodeService;
            }

            public int getNewDateRangeFlag() {
                return newDateRangeFlag;
            }

            public void setNewDateRangeFlag(int newDateRangeFlag) {
                this.newDateRangeFlag = newDateRangeFlag;
            }

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public int getAtOnceDeliveryFlag() {
                return atOnceDeliveryFlag;
            }

            public void setAtOnceDeliveryFlag(int atOnceDeliveryFlag) {
                this.atOnceDeliveryFlag = atOnceDeliveryFlag;
            }

            public int getConfigId() {
                return configId;
            }

            public void setConfigId(int configId) {
                this.configId = configId;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getInDueFormFlag() {
                return inDueFormFlag;
            }

            public void setInDueFormFlag(int inDueFormFlag) {
                this.inDueFormFlag = inDueFormFlag;
            }

            public int getInviteMemberVoucherLastTime() {
                return inviteMemberVoucherLastTime;
            }

            public void setInviteMemberVoucherLastTime(int inviteMemberVoucherLastTime) {
                this.inviteMemberVoucherLastTime = inviteMemberVoucherLastTime;
            }

            public long getInviteTimeEnd() {
                return inviteTimeEnd;
            }

            public void setInviteTimeEnd(long inviteTimeEnd) {
                this.inviteTimeEnd = inviteTimeEnd;
            }

            public long getInviteTimeStart() {
                return inviteTimeStart;
            }

            public void setInviteTimeStart(long inviteTimeStart) {
                this.inviteTimeStart = inviteTimeStart;
            }

            public String getMemberLastTime() {
                return memberLastTime;
            }

            public void setMemberLastTime(String memberLastTime) {
                this.memberLastTime = memberLastTime;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public int getOnlineService() {
                return onlineService;
            }

            public void setOnlineService(int onlineService) {
                this.onlineService = onlineService;
            }

            public String getServicePhone() {
                return servicePhone;
            }

            public void setServicePhone(String servicePhone) {
                this.servicePhone = servicePhone;
            }

            public String getStoreCloseTime() {
                return storeCloseTime;
            }

            public void setStoreCloseTime(String storeCloseTime) {
                this.storeCloseTime = storeCloseTime;
            }

            public String getStoreOpenTime() {
                return storeOpenTime;
            }

            public void setStoreOpenTime(String storeOpenTime) {
                this.storeOpenTime = storeOpenTime;
            }

            public int getTomorrowFreight() {
                return tomorrowFreight;
            }

            public void setTomorrowFreight(int tomorrowFreight) {
                this.tomorrowFreight = tomorrowFreight;
            }

            public int getTotalWeight() {
                return totalWeight;
            }

            public void setTotalWeight(int totalWeight) {
                this.totalWeight = totalWeight;
            }

            public int getTryOutMemberFlag() {
                return tryOutMemberFlag;
            }

            public void setTryOutMemberFlag(int tryOutMemberFlag) {
                this.tryOutMemberFlag = tryOutMemberFlag;
            }

            public int getWarningValue() {
                return warningValue;
            }

            public void setWarningValue(int warningValue) {
                this.warningValue = warningValue;
            }
        }
    }
}
