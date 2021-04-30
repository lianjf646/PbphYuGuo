package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2018/9/13 0013.
 * class note:
 */

public class GetStoreListResponse extends BaseResponse {

    /**
     * data : {"storeList":[{"deliveryAreaList":[{"receiverLng":"126.705039","receiverLat":"45.772565"},
     * {"receiverLng":"126.685061","receiverLat":"45.764719"},{"receiverLng":"126.74284","receiverLat":"45.755966"},
     * {"receiverLng":"126.733498","receiverLat":"45.768541"},{"receiverLng":"126.71352","receiverLat":"45.765322"},
     * {"receiverLng":"126.711965","receiverLat":"45.77022"}],"storeName":"鹏博普华店","receiverLng":"126.711678","storeId":154,
     * "receiverLat":"45.766298"},{"deliveryAreaList":[{"receiverLng":"126.705039","receiverLat":"45.772565"},
     * {"receiverLng":"126.685061","receiverLat":"45.764719"},{"receiverLng":"126.74284","receiverLat":"45.755966"},
     * {"receiverLng":"126.733498","receiverLat":"45.768541"},{"receiverLng":"126.71352","receiverLat":"45.765322"}],
     * "storeName":"哈达店","receiverLng":"126.711678","storeId":155,"receiverLat":"45.766298"},
     * {"deliveryAreaList":[{"receiverLng":"126.705039","receiverLat":"45.772565"},{"receiverLng":"126.685061",
     * "receiverLat":"45.764719"},{"receiverLng":"126.74284","receiverLat":"45.755966"},{"receiverLng":"126.733498",
     * "receiverLat":"45.768541"},{"receiverLng":"126.71352","receiverLat":"45.765322"}],"storeName":"l门店",
     * "receiverLng":"126.711678","storeId":156,"receiverLat":"45.766298"},{"deliveryAreaList":[{"receiverLng":"126.705039",
     * "receiverLat":"45.772565"},{"receiverLng":"126.685061","receiverLat":"45.764719"},{"receiverLng":"126.74284",
     * "receiverLat":"45.755966"},{"receiverLng":"126.733498","receiverLat":"45.768541"},{"receiverLng":"126.71352",
     * "receiverLat":"45.765322"}],"storeName":"测试","receiverLng":"126.711678","storeId":157,"receiverLat":"45.766298"},
     * {"deliveryAreaList":[],"storeName":"11111111","receiverLng":"126.711678","storeId":158,"receiverLat":"45.766298"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<StoreListBean> storeList;

        public List<StoreListBean> getStoreList() {
            return storeList;
        }

        public void setStoreList(List<StoreListBean> storeList) {
            this.storeList = storeList;
        }

        public static class StoreListBean {
            /**
             * deliveryAreaList : [{"receiverLng":"126.705039","receiverLat":"45.772565"},{"receiverLng":"126.685061",
             * "receiverLat":"45.764719"},{"receiverLng":"126.74284","receiverLat":"45.755966"},{"receiverLng":"126.733498",
             * "receiverLat":"45.768541"},{"receiverLng":"126.71352","receiverLat":"45.765322"},{"receiverLng":"126.711965",
             * "receiverLat":"45.77022"}]
             * storeName : 鹏博普华店
             * receiverLng : 126.711678
             * storeId : 154
             * receiverLat : 45.766298
             */

            private String storeName;
            private String receiverLng;
            private int storeId;
            private String receiverLat;
            private List<DeliveryAreaListBean> deliveryAreaList;

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getReceiverLng() {
                return receiverLng;
            }

            public void setReceiverLng(String receiverLng) {
                this.receiverLng = receiverLng;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getReceiverLat() {
                return receiverLat;
            }

            public void setReceiverLat(String receiverLat) {
                this.receiverLat = receiverLat;
            }

            public List<DeliveryAreaListBean> getDeliveryAreaList() {
                return deliveryAreaList;
            }

            public void setDeliveryAreaList(List<DeliveryAreaListBean> deliveryAreaList) {
                this.deliveryAreaList = deliveryAreaList;
            }

            public static class DeliveryAreaListBean {
                /**
                 * receiverLng : 126.705039
                 * receiverLat : 45.772565
                 */

                private String receiverLng;
                private String receiverLat;

                public String getReceiverLng() {
                    return receiverLng;
                }

                public void setReceiverLng(String receiverLng) {
                    this.receiverLng = receiverLng;
                }

                public String getReceiverLat() {
                    return receiverLat;
                }

                public void setReceiverLat(String receiverLat) {
                    this.receiverLat = receiverLat;
                }
            }
        }
    }
}
