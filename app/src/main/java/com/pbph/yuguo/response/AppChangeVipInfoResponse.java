package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 会员-充值成功更新会员信息,交易记录
 * Created by Administrator on 2018/8/6 0006.
 */

public class AppChangeVipInfoResponse extends BaseResponse {

    /**
     * data : {"rechargeId":75}
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
         * rechargeId : 75
         */

        private String rechargeId;

        public String getRechargeId() {
            return rechargeId;
        }

        public void setRechargeId(String rechargeId) {
            this.rechargeId = rechargeId;
        }
    }
}
