package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 *获取订单状态数量
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetOrderNumResponse extends BaseResponse{


    /**
     * data : {"count":{"pendingPaymentCount":0,"alreadyShippedCount":0,"waitEvaluateCount":0,
     * "pendingDeliveryCount":0}}
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
         * count : {"pendingPaymentCount":0,"alreadyShippedCount":0,"waitEvaluateCount":0,
         * "pendingDeliveryCount":0}
         */

        private CountBean count;

        public CountBean getCount() {
            return count;
        }

        public void setCount(CountBean count) {
            this.count = count;
        }

        public static class CountBean {
            /**
             * pendingPaymentCount : 0
             * alreadyShippedCount : 0
             * waitEvaluateCount : 0
             * pendingDeliveryCount : 0
             */

            private int pendingPaymentCount;
            private int alreadyShippedCount;
            private int waitEvaluateCount;
            private int pendingDeliveryCount;

            public int getPendingPaymentCount() {
                return pendingPaymentCount;
            }

            public void setPendingPaymentCount(int pendingPaymentCount) {
                this.pendingPaymentCount = pendingPaymentCount;
            }

            public int getAlreadyShippedCount() {
                return alreadyShippedCount;
            }

            public void setAlreadyShippedCount(int alreadyShippedCount) {
                this.alreadyShippedCount = alreadyShippedCount;
            }

            public int getWaitEvaluateCount() {
                return waitEvaluateCount;
            }

            public void setWaitEvaluateCount(int waitEvaluateCount) {
                this.waitEvaluateCount = waitEvaluateCount;
            }

            public int getPendingDeliveryCount() {
                return pendingDeliveryCount;
            }

            public void setPendingDeliveryCount(int pendingDeliveryCount) {
                this.pendingDeliveryCount = pendingDeliveryCount;
            }
        }
    }
}
