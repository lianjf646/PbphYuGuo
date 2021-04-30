package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * 会员-充值成功更新会员信息,交易记录
 * Created by Administrator on 2018/8/6 0006.
 */

public class WxPayOrderResponse extends BaseResponse {


    /**
     * data : {"orderStatus":2,"payInfo":{"appid":"wxc66bbf41d0430eb6",
     * "sign":"4D047F8A47A8C8EF1B3332E7060A1F73",
     * "prepayid":"wx101417053943195691e30d932828546136","partnerid":"1512175461",
     * "packages":"Sign=WXPay","noncestr":"d18c255f89434eab3211813c0e765c6b",
     * "timestamp":"1536560225"}}
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
         * orderStatus : 2
         * payInfo : {"appid":"wxc66bbf41d0430eb6","sign":"4D047F8A47A8C8EF1B3332E7060A1F73",
         * "prepayid":"wx101417053943195691e30d932828546136","partnerid":"1512175461",
         * "packages":"Sign=WXPay","noncestr":"d18c255f89434eab3211813c0e765c6b",
         * "timestamp":"1536560225"}
         */

        private int orderStatus;
        private PayInfoBean payInfo;

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public PayInfoBean getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(PayInfoBean payInfo) {
            this.payInfo = payInfo;
        }

        public static class PayInfoBean {
            /**
             * appid : wxc66bbf41d0430eb6
             * sign : 4D047F8A47A8C8EF1B3332E7060A1F73
             * prepayid : wx101417053943195691e30d932828546136
             * partnerid : 1512175461
             * packages : Sign=WXPay
             * noncestr : d18c255f89434eab3211813c0e765c6b
             * timestamp : 1536560225
             */

            private String appid;
            private String sign;
            private String prepayid;
            private String partnerid;
            private String packages;
            private String noncestr;
            private String timestamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPackages() {
                return packages;
            }

            public void setPackages(String packages) {
                this.packages = packages;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
