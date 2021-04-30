package com.pbph.yuguo.myview.choicegoods;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/5/9.
 */

public class ProductDetailResponse  {


    /**
     * data : {"goodsDetail":{"goodsInfo":{"goodsDetailCommentList":[{"commentContent
     * ":"aaaaaaaaaaaaaa","commentId":51,"customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing
     * .aliyuncs.com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
     * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167791000,
     * "shareImgList":["123.jgp","45fdf.jgp",
     * "/storage/emulated/0/DCIM/camera/IMG_20180529_134339.jpg",
     * "/storage/emulated/0/Pictures/1525338961130.jpg",
     * "/storage/emulated/0/DCIM/Camera/1525338940020.png"],"storeName":"鹏博科技"},
     * {"commentContent":"aaaaaaaaaaaaaa","commentId":52,"customerImg":"http://pbkjb2b2cbucket
     * .oss-cn-beijing.aliyuncs.com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg",
     * "customerName":"13333333333","goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,
     * "publishTime":1527167834000,"storeName":"鹏博科技"},{"commentContent":"aaaaaaaaaaaaaa",
     * "commentId":53,"customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
     * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
     * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167834000,
     * "storeName":"鹏博科技"}],"goodsImageList":["http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
     * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,h_160,w_160",
     * "http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
     * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,h_160,
     * w_160?x-oss-process=image/resize,m_fixed,h_160,w_160","http://pbkjb2b2cbucket
     * .oss-cn-beijing.aliyuncs.com/productimage2/3/1515728580245.png?x-oss-process=image/resize,
     * m_fixed,h_160,w_160?x-oss-process=image/resize,m_fixed,h_160,w_160"],
     * "goodsInfoAddedVal":1,"goodsInfoName":"上岛咖啡还看书(黑色二尺六50*20)","goodsInfoPreferPrice":123,
     * "goodsInfoSalesVolume":134,"goodsInfoSubtitle":"发生的发",
     * "goodsSpec":[{"specDetails":[{"goodsId":109,"specId":1,"specValue":"黑色","specValueId":1}],
     * "specId":1,"specName":"颜色"},{"specDetails":[{"goodsId":109,"specId":2,"specValue":"三尺三",
     * "specValueId":9},{"goodsId":109,"specId":2,"specValue":"二尺六","specValueId":2}],"specId":2,
     * "specName":"尺寸"},{"specDetails":[{"goodsId":109,"specId":7,"specValue":"50*20",
     * "specValueId":11},{"goodsId":109,"specId":7,"specValue":"50*30","specValueId":12}],
     * "specId":7,"specName":"宽高"}],"ppid":112,"isFollow":0,"ismailbay":0,
     * "npGoods":{"goodsDetailDesc":"<p>1<\/p>","ppid":109,"mobileDesc":"<p>1<\/p>","typeId":19},
     * "productCommentVo":{"colligate":"300","count":"3","highopinion":"3","inferior":"0",
     * "middlingopinion":"0"},"showMobile":1,"storeId":37,"storeName":"admin"},
     * "goodsReleExpandParamVo":[{"expandparamName":"属性名11","expandparamValueName":"属性值2"},
     * {"expandparamName":"属性名22","expandparamValueName":"属性名22"},{"expandparamName":"属性名33",
     * "expandparamValueName":"属性值34"}],"serviceList":["七天无理由退换","包邮","正品保障"],
     * "storeInfoVo":{"collectionNumber":51,"goodsInfoCount":"37","isCollection":1,
     * "overallMerit":95,"storeId":37,"storeLogo":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
     * .com/productimage2/37/1523861403780.jpg","storeName":"鹏博科技"}}}
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
         * goodsDetail : {"goodsInfo":{"goodsDetailCommentList":[{"commentContent
         * ":"aaaaaaaaaaaaaa","commentId":51,"customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing
         * .aliyuncs.com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg",
         * "customerName":"13333333333","goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,
         * "publishTime":1527167791000,"shareImgList":["123.jgp","45fdf.jgp",
         * "/storage/emulated/0/DCIM/camera/IMG_20180529_134339.jpg",
         * "/storage/emulated/0/Pictures/1525338961130.jpg",
         * "/storage/emulated/0/DCIM/Camera/1525338940020.png"],"storeName":"鹏博科技"},
         * {"commentContent":"aaaaaaaaaaaaaa","commentId":52,
         * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
         * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
         * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167834000,
         * "storeName":"鹏博科技"},{"commentContent":"aaaaaaaaaaaaaa","commentId":53,
         * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
         * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
         * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167834000,
         * "storeName":"鹏博科技"}],"goodsImageList":["http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
         * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,h_160,
         * w_160","http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
         * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,h_160,
         * w_160?x-oss-process=image/resize,m_fixed,h_160,w_160","http://pbkjb2b2cbucket
         * .oss-cn-beijing.aliyuncs.com/productimage2/3/1515728580245.png?x-oss-process=image
         * /resize,m_fixed,h_160,w_160?x-oss-process=image/resize,m_fixed,h_160,w_160"],
         * "goodsInfoAddedVal":1,"goodsInfoName":"上岛咖啡还看书(黑色二尺六50*20)",
         * "goodsInfoPreferPrice":123,"goodsInfoSalesVolume":134,"goodsInfoSubtitle":"发生的发",
         * "goodsSpec":[{"specDetails":[{"goodsId":109,"specId":1,"specValue":"黑色",
         * "specValueId":1}],"specId":1,"specName":"颜色"},{"specDetails":[{"goodsId":109,
         * "specId":2,"specValue":"三尺三","specValueId":9},{"goodsId":109,"specId":2,
         * "specValue":"二尺六","specValueId":2}],"specId":2,"specName":"尺寸"},
         * {"specDetails":[{"goodsId":109,"specId":7,"specValue":"50*20","specValueId":11},
         * {"goodsId":109,"specId":7,"specValue":"50*30","specValueId":12}],"specId":7,
         * "specName":"宽高"}],"ppid":112,"isFollow":0,"ismailbay":0,
         * "npGoods":{"goodsDetailDesc":"<p>1<\/p>","ppid":109,"mobileDesc":"<p>1<\/p>",
         * "typeId":19},"productCommentVo":{"colligate":"300","count":"3","highopinion":"3",
         * "inferior":"0","middlingopinion":"0"},"showMobile":1,"storeId":37,
         * "storeName":"admin"},"goodsReleExpandParamVo":[{"expandparamName":"属性名11",
         * "expandparamValueName":"属性值2"},{"expandparamName":"属性名22",
         * "expandparamValueName":"属性名22"},{"expandparamName":"属性名33",
         * "expandparamValueName":"属性值34"}],"serviceList":["七天无理由退换","包邮","正品保障"],
         * "storeInfoVo":{"collectionNumber":51,"goodsInfoCount":"37","isCollection":1,
         * "overallMerit":95,"storeId":37,"storeLogo":"http://pbkjb2b2cbucket.oss-cn-beijing
         * .aliyuncs.com/productimage2/37/1523861403780.jpg","storeName":"鹏博科技"}}
         */

        private GoodsDetailBean goodsDetail;

        public GoodsDetailBean getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(GoodsDetailBean goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public static class GoodsDetailBean {
            /**
             * goodsInfo : {"goodsDetailCommentList":[{"commentContent":"aaaaaaaaaaaaaa",
             * "commentId":51,"customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
             * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
             * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167791000,
             * "shareImgList":["123.jgp","45fdf.jgp",
             * "/storage/emulated/0/DCIM/camera/IMG_20180529_134339.jpg",
             * "/storage/emulated/0/Pictures/1525338961130.jpg",
             * "/storage/emulated/0/DCIM/Camera/1525338940020.png"],"storeName":"鹏博科技"},
             * {"commentContent":"aaaaaaaaaaaaaa","commentId":52,
             * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
             * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
             * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167834000,
             * "storeName":"鹏博科技"},{"commentContent":"aaaaaaaaaaaaaa","commentId":53,
             * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
             * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg","customerName":"13333333333",
             * "goodsInfoName":"阿斯顿发(黑色二尺六)","productDescription":80,"publishTime":1527167834000,
             * "storeName":"鹏博科技"}],"goodsImageList":["http://pbkjb2b2cbucket.oss-cn-beijing
             * .aliyuncs.com/productimage2/3/1515728580245.png?x-oss-process=image/resize,
             * m_fixed,h_160,w_160","http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
             * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,h_160,
             * w_160?x-oss-process=image/resize,m_fixed,h_160,w_160","http://pbkjb2b2cbucket
             * .oss-cn-beijing.aliyuncs
             * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,h_160,
             * w_160?x-oss-process=image/resize,m_fixed,h_160,w_160"],"goodsInfoAddedVal":1,
             * "goodsInfoName":"上岛咖啡还看书(黑色二尺六50*20)","goodsInfoPreferPrice":123,
             * "goodsInfoSalesVolume":134,"goodsInfoSubtitle":"发生的发",
             * "goodsSpec":[{"specDetails":[{"goodsId":109,"specId":1,"specValue":"黑色",
             * "specValueId":1}],"specId":1,"specName":"颜色"},{"specDetails":[{"goodsId":109,
             * "specId":2,"specValue":"三尺三","specValueId":9},{"goodsId":109,"specId":2,
             * "specValue":"二尺六","specValueId":2}],"specId":2,"specName":"尺寸"},
             * {"specDetails":[{"goodsId":109,"specId":7,"specValue":"50*20","specValueId":11},
             * {"goodsId":109,"specId":7,"specValue":"50*30","specValueId":12}],"specId":7,
             * "specName":"宽高"}],"ppid":112,"isFollow":0,"ismailbay":0,
             * "npGoods":{"goodsDetailDesc":"<p>1<\/p>","ppid":109,"mobileDesc":"<p>1<\/p>",
             * "typeId":19},"productCommentVo":{"colligate":"300","count":"3","highopinion":"3",
             * "inferior":"0","middlingopinion":"0"},"showMobile":1,"storeId":37,
             * "storeName":"admin"}
             * goodsReleExpandParamVo : [{"expandparamName":"属性名11",
             * "expandparamValueName":"属性值2"},{"expandparamName":"属性名22",
             * "expandparamValueName":"属性名22"},{"expandparamName":"属性名33",
             * "expandparamValueName":"属性值34"}]
             * serviceList : ["七天无理由退换","包邮","正品保障"]
             * storeInfoVo : {"collectionNumber":51,"goodsInfoCount":"37","isCollection":1,
             * "overallMerit":95,"storeId":37,"storeLogo":"http://pbkjb2b2cbucket.oss-cn-beijing
             * .aliyuncs.com/productimage2/37/1523861403780.jpg","storeName":"鹏博科技"}
             */

            private GoodsInfoBean goodsInfo;
            private StoreInfoVoBean storeInfoVo;
            private List<GoodsReleExpandParamVoBean> goodsReleExpandParamVo;
            private List<String> serviceList;

            public GoodsInfoBean getGoodsInfo() {
                return goodsInfo;
            }

            public void setGoodsInfo(GoodsInfoBean goodsInfo) {
                this.goodsInfo = goodsInfo;
            }

            public StoreInfoVoBean getStoreInfoVo() {
                return storeInfoVo;
            }

            public void setStoreInfoVo(StoreInfoVoBean storeInfoVo) {
                this.storeInfoVo = storeInfoVo;
            }

            public List<GoodsReleExpandParamVoBean> getGoodsReleExpandParamVo() {
                return goodsReleExpandParamVo;
            }

            public void setGoodsReleExpandParamVo(List<GoodsReleExpandParamVoBean>
                                                          goodsReleExpandParamVo) {
                this.goodsReleExpandParamVo = goodsReleExpandParamVo;
            }

            public List<String> getServiceList() {
                return serviceList;
            }

            public void setServiceList(List<String> serviceList) {
                this.serviceList = serviceList;
            }

            public static class GoodsInfoBean {
                /**
                 * goodsDetailCommentList : [{"commentContent":"aaaaaaaaaaaaaa","commentId":51,
                 * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg",
                 * "customerName":"13333333333","goodsInfoName":"阿斯顿发(黑色二尺六)",
                 * "productDescription":80,"publishTime":1527167791000,"shareImgList":["123.jgp",
                 * "45fdf.jgp","/storage/emulated/0/DCIM/camera/IMG_20180529_134339.jpg",
                 * "/storage/emulated/0/Pictures/1525338961130.jpg",
                 * "/storage/emulated/0/DCIM/Camera/1525338940020.png"],"storeName":"鹏博科技"},
                 * {"commentContent":"aaaaaaaaaaaaaa","commentId":52,
                 * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg",
                 * "customerName":"13333333333","goodsInfoName":"阿斯顿发(黑色二尺六)",
                 * "productDescription":80,"publishTime":1527167834000,"storeName":"鹏博科技"},
                 * {"commentContent":"aaaaaaaaaaaaaa","commentId":53,
                 * "customerImg":"http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg",
                 * "customerName":"13333333333","goodsInfoName":"阿斯顿发(黑色二尺六)",
                 * "productDescription":80,"publishTime":1527167834000,"storeName":"鹏博科技"}]
                 * goodsImageList : ["http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,
                 * h_160,w_160","http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,
                 * h_160,w_160?x-oss-process=image/resize,m_fixed,h_160,w_160",
                 * "http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/productimage2/3/1515728580245.png?x-oss-process=image/resize,m_fixed,
                 * h_160,w_160?x-oss-process=image/resize,m_fixed,h_160,w_160"]
                 * goodsInfoAddedVal : 1
                 * goodsInfoName : 上岛咖啡还看书(黑色二尺六50*20)
                 * goodsInfoPreferPrice : 123
                 * goodsInfoSalesVolume : 134
                 * goodsInfoSubtitle : 发生的发
                 * goodsSpec : [{"specDetails":[{"goodsId":109,"specId":1,"specValue":"黑色",
                 * "specValueId":1}],"specId":1,"specName":"颜色"},{"specDetails":[{"goodsId":109,
                 * "specId":2,"specValue":"三尺三","specValueId":9},{"goodsId":109,"specId":2,
                 * "specValue":"二尺六","specValueId":2}],"specId":2,"specName":"尺寸"},
                 * {"specDetails":[{"goodsId":109,"specId":7,"specValue":"50*20",
                 * "specValueId":11},{"goodsId":109,"specId":7,"specValue":"50*30",
                 * "specValueId":12}],"specId":7,"specName":"宽高"}]
                 * ppid : 112
                 * isFollow : 0
                 * ismailbay : 0
                 * npGoods : {"goodsDetailDesc":"<p>1<\/p>","ppid":109,"mobileDesc":"<p>1<\/p>",
                 * "typeId":19}
                 * productCommentVo : {"colligate":"300","count":"3","highopinion":"3",
                 * "inferior":"0","middlingopinion":"0"}
                 * showMobile : 1
                 * storeId : 37
                 * storeName : admin
                 */

                private int goodsInfoAddedVal;
                private String goodsInfoName;
                private int goodsInfoPreferPrice;
                private int goodsInfoSalesVolume;
                private String goodsInfoSubtitle;
                private int ppid;
                private int isFollow;
                private int ismailbay;
                private NpGoodsBean npGoods;
                private ProductCommentVoBean productCommentVo;
                private int showMobile;
                private int storeId;
                private String storeName;
                private List<GoodsDetailCommentListBean> goodsDetailCommentList;
                private List<String> goodsImageList;
                private List<GoodsSpecBean> goodsSpec;
                private String goodsInfoImgUrl;
                private String goodsInfoItemNo;
                private String packagSale; // 包装售后

                public String getPackagSale() {
                    return packagSale;
                }

                public void setPackagSale(String packagSale) {
                    this.packagSale = packagSale;
                }

                public String getGoodsInfoImgUrl() {
                    return goodsInfoImgUrl;
                }

                public void setGoodsInfoImgUrl(String goodsInfoImgUrl) {
                    this.goodsInfoImgUrl = goodsInfoImgUrl;
                }

                public String getGoodsInfoItemNo() {
                    return goodsInfoItemNo;
                }

                public void setGoodsInfoItemNo(String goodsInfoItemNo) {
                    this.goodsInfoItemNo = goodsInfoItemNo;
                }

                public int getGoodsInfoAddedVal() {
                    return goodsInfoAddedVal;
                }

                public void setGoodsInfoAddedVal(int goodsInfoAddedVal) {
                    this.goodsInfoAddedVal = goodsInfoAddedVal;
                }

                public String getGoodsInfoName() {
                    return goodsInfoName;
                }

                public void setGoodsInfoName(String goodsInfoName) {
                    this.goodsInfoName = goodsInfoName;
                }

                public int getGoodsInfoPreferPrice() {
                    return goodsInfoPreferPrice;
                }

                public void setGoodsInfoPreferPrice(int goodsInfoPreferPrice) {
                    this.goodsInfoPreferPrice = goodsInfoPreferPrice;
                }

                public int getGoodsInfoSalesVolume() {
                    return goodsInfoSalesVolume;
                }

                public void setGoodsInfoSalesVolume(int goodsInfoSalesVolume) {
                    this.goodsInfoSalesVolume = goodsInfoSalesVolume;
                }

                public String getGoodsInfoSubtitle() {
                    return goodsInfoSubtitle;
                }

                public void setGoodsInfoSubtitle(String goodsInfoSubtitle) {
                    this.goodsInfoSubtitle = goodsInfoSubtitle;
                }

                public int getPpid() {
                    return ppid;
                }

                public void setPpid(int ppid) {
                    this.ppid = ppid;
                }

                public int getIsFollow() {
                    return isFollow;
                }

                public void setIsFollow(int isFollow) {
                    this.isFollow = isFollow;
                }

                public int getIsmailbay() {
                    return ismailbay;
                }

                public void setIsmailbay(int ismailbay) {
                    this.ismailbay = ismailbay;
                }

                public NpGoodsBean getNpGoods() {
                    return npGoods;
                }

                public void setNpGoods(NpGoodsBean npGoods) {
                    this.npGoods = npGoods;
                }

                public ProductCommentVoBean getProductCommentVo() {
                    return productCommentVo;
                }

                public void setProductCommentVo(ProductCommentVoBean productCommentVo) {
                    this.productCommentVo = productCommentVo;
                }

                public int getShowMobile() {
                    return showMobile;
                }

                public void setShowMobile(int showMobile) {
                    this.showMobile = showMobile;
                }

                public int getStoreId() {
                    return storeId;
                }

                public void setStoreId(int storeId) {
                    this.storeId = storeId;
                }

                public String getStoreName() {
                    return storeName;
                }

                public void setStoreName(String storeName) {
                    this.storeName = storeName;
                }

                public List<GoodsDetailCommentListBean> getGoodsDetailCommentList() {
                    return goodsDetailCommentList;
                }

                public void setGoodsDetailCommentList(List<GoodsDetailCommentListBean>
                                                              goodsDetailCommentList) {
                    this.goodsDetailCommentList = goodsDetailCommentList;
                }

                public List<String> getGoodsImageList() {
                    return goodsImageList;
                }

                public void setGoodsImageList(List<String> goodsImageList) {
                    this.goodsImageList = goodsImageList;
                }

                public List<GoodsSpecBean> getGoodsSpec() {
                    return goodsSpec;
                }

                public void setGoodsSpec(List<GoodsSpecBean> goodsSpec) {
                    this.goodsSpec = goodsSpec;
                }

                public static class NpGoodsBean {
                    /**
                     * goodsDetailDesc : <p>1</p>
                     * ppid : 109
                     * mobileDesc : <p>1</p>
                     * typeId : 19
                     */

                    private String goodsDetailDesc;
                    private int ppid;
                    private String mobileDesc; //商品介绍
                    private int typeId;



                    public String getGoodsDetailDesc() {
                        return goodsDetailDesc;
                    }

                    public void setGoodsDetailDesc(String goodsDetailDesc) {
                        this.goodsDetailDesc = goodsDetailDesc;
                    }

                    public int getPpid() {
                        return ppid;
                    }

                    public void setPpid(int ppid) {
                        this.ppid = ppid;
                    }

                    public String getMobileDesc() {
                        return mobileDesc;
                    }

                    public void setMobileDesc(String mobileDesc) {
                        this.mobileDesc = mobileDesc;
                    }

                    public int getTypeId() {
                        return typeId;
                    }

                    public void setTypeId(int typeId) {
                        this.typeId = typeId;
                    }
                }

                public static class ProductCommentVoBean {
                    /**
                     * colligate : 300
                     * count : 3
                     * highopinion : 3
                     * inferior : 0
                     * middlingopinion : 0
                     */

                    private String colligate;
                    private String count;
                    private String highopinion;
                    private String inferior;
                    private String middlingopinion;

                    public String getColligate() {
                        return colligate;
                    }

                    public void setColligate(String colligate) {
                        this.colligate = colligate;
                    }

                    public String getCount() {
                        return count;
                    }

                    public void setCount(String count) {
                        this.count = count;
                    }

                    public String getHighopinion() {
                        return highopinion;
                    }

                    public void setHighopinion(String highopinion) {
                        this.highopinion = highopinion;
                    }

                    public String getInferior() {
                        return inferior;
                    }

                    public void setInferior(String inferior) {
                        this.inferior = inferior;
                    }

                    public String getMiddlingopinion() {
                        return middlingopinion;
                    }

                    public void setMiddlingopinion(String middlingopinion) {
                        this.middlingopinion = middlingopinion;
                    }
                }

                public static class GoodsDetailCommentListBean {
                    /**
                     * commentContent : aaaaaaaaaaaaaa
                     * commentId : 51
                     * customerImg : http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                     * .com/appImg/8e3a5f597d2a196ae6cd2e0ca0f1c669.jpg
                     * customerName : 13333333333
                     * goodsInfoName : 阿斯顿发(黑色二尺六)
                     * productDescription : 80
                     * publishTime : 1527167791000
                     * shareImgList : ["123.jgp","45fdf.jgp",
                     * "/storage/emulated/0/DCIM/camera/IMG_20180529_134339.jpg",
                     * "/storage/emulated/0/Pictures/1525338961130.jpg",
                     * "/storage/emulated/0/DCIM/Camera/1525338940020.png"]
                     * storeName : 鹏博科技
                     */

                    private String commentContent;
                    private int commentId;
                    private String customerImg;
                    private String customerName;
                    private String goodsInfoName;
                    private int productDescription;
                    private long publishTime;
                    private String storeName;
                    private List<String> shareImgList;
                    private int commentScore;

                    public int getCommentScore() {
                        return commentScore;
                    }

                    public void setCommentScore(int commentScore) {
                        this.commentScore = commentScore;
                    }

                    public String getCommentContent() {
                        return commentContent;
                    }

                    public void setCommentContent(String commentContent) {
                        this.commentContent = commentContent;
                    }

                    public int getCommentId() {
                        return commentId;
                    }

                    public void setCommentId(int commentId) {
                        this.commentId = commentId;
                    }

                    public String getCustomerImg() {
                        return customerImg;
                    }

                    public void setCustomerImg(String customerImg) {
                        this.customerImg = customerImg;
                    }

                    public String getCustomerName() {
                        return customerName;
                    }

                    public void setCustomerName(String customerName) {
                        this.customerName = customerName;
                    }

                    public String getGoodsInfoName() {
                        return goodsInfoName;
                    }

                    public void setGoodsInfoName(String goodsInfoName) {
                        this.goodsInfoName = goodsInfoName;
                    }

                    public int getProductDescription() {
                        return productDescription;
                    }

                    public void setProductDescription(int productDescription) {
                        this.productDescription = productDescription;
                    }

                    public long getPublishTime() {
                        return publishTime;
                    }

                    public void setPublishTime(long publishTime) {
                        this.publishTime = publishTime;
                    }

                    public String getStoreName() {
                        return storeName;
                    }

                    public void setStoreName(String storeName) {
                        this.storeName = storeName;
                    }

                    public List<String> getShareImgList() {
                        return shareImgList;
                    }

                    public void setShareImgList(List<String> shareImgList) {
                        this.shareImgList = shareImgList;
                    }
                }

                public static class GoodsSpecBean {
                    /**
                     * specDetails : [{"goodsId":109,"specId":1,"specValue":"黑色","specValueId":1}]
                     * specId : 1
                     * specName : 颜色
                     */

                    private int specId;
                    private String specName;
                    private List<SpecDetailsBean> specDetails;
                    public int choose_child_id;
                    public String choose_child_name;

                    public int getChoose_child_id() {
                        return choose_child_id;
                    }

                    public void setChoose_child_id(int choose_child_id) {
                        this.choose_child_id = choose_child_id;
                    }

                    public String getChoose_child_name() {
                        return choose_child_name;
                    }

                    public void setChoose_child_name(String choose_child_name) {
                        this.choose_child_name = choose_child_name;
                    }


                    public int getSpecId() {
                        return specId;
                    }

                    public void setSpecId(int specId) {
                        this.specId = specId;
                    }

                    public String getSpecName() {
                        return specName;
                    }

                    public void setSpecName(String specName) {
                        this.specName = specName;
                    }

                    public List<SpecDetailsBean> getSpecDetails() {
                        return specDetails;
                    }

                    public void setSpecDetails(List<SpecDetailsBean> specDetails) {
                        this.specDetails = specDetails;
                    }

                    public static class SpecDetailsBean {
                        /**
                         * goodsId : 109
                         * specId : 1
                         * specValue : 黑色
                         * specValueId : 1
                         */

                        private int goodsId;
                        private int specId;
                        private String specValue;
                        private int specValueId;
                        private int isPitchOn;

                        public int getIsPitchOn() {
                            return isPitchOn;
                        }

                        public void setIsPitchOn(int isPitchOn) {
                            this.isPitchOn = isPitchOn;
                        }

                        public int getGoodsId() {
                            return goodsId;
                        }

                        public void setGoodsId(int goodsId) {
                            this.goodsId = goodsId;
                        }

                        public int getSpecId() {
                            return specId;
                        }

                        public void setSpecId(int specId) {
                            this.specId = specId;
                        }

                        public String getSpecValue() {
                            return specValue;
                        }

                        public void setSpecValue(String specValue) {
                            this.specValue = specValue;
                        }

                        public int getSpecValueId() {
                            return specValueId;
                        }

                        public void setSpecValueId(int specValueId) {
                            this.specValueId = specValueId;
                        }
                    }
                }
            }

            public static class StoreInfoVoBean {
                /**
                 * collectionNumber : 51
                 * goodsInfoCount : 37
                 * isCollection : 1
                 * overallMerit : 95
                 * storeId : 37
                 * storeLogo : http://pbkjb2b2cbucket.oss-cn-beijing.aliyuncs
                 * .com/productimage2/37/1523861403780.jpg
                 * storeName : 鹏博科技
                 */

                private int collectionNumber;
                private String goodsInfoCount;
                private int isCollection;
                private int overallMerit;
                private int storeId;
                private String storeLogo;
                private String storeName;

                public int getCollectionNumber() {
                    return collectionNumber;
                }

                public void setCollectionNumber(int collectionNumber) {
                    this.collectionNumber = collectionNumber;
                }

                public String getGoodsInfoCount() {
                    return goodsInfoCount;
                }

                public void setGoodsInfoCount(String goodsInfoCount) {
                    this.goodsInfoCount = goodsInfoCount;
                }

                public int getIsCollection() {
                    return isCollection;
                }

                public void setIsCollection(int isCollection) {
                    this.isCollection = isCollection;
                }

                public int getOverallMerit() {
                    return overallMerit;
                }

                public void setOverallMerit(int overallMerit) {
                    this.overallMerit = overallMerit;
                }

                public int getStoreId() {
                    return storeId;
                }

                public void setStoreId(int storeId) {
                    this.storeId = storeId;
                }

                public String getStoreLogo() {
                    return storeLogo;
                }

                public void setStoreLogo(String storeLogo) {
                    this.storeLogo = storeLogo;
                }

                public String getStoreName() {
                    return storeName;
                }

                public void setStoreName(String storeName) {
                    this.storeName = storeName;
                }
            }

            public static class GoodsReleExpandParamVoBean {
                /**
                 * expandparamName : 属性名11
                 * expandparamValueName : 属性值2
                 */

                private String expandparamName;
                private String expandparamValueName;

                public String getExpandparamName() {
                    return expandparamName;
                }

                public void setExpandparamName(String expandparamName) {
                    this.expandparamName = expandparamName;
                }

                public String getExpandparamValueName() {
                    return expandparamValueName;
                }

                public void setExpandparamValueName(String expandparamValueName) {
                    this.expandparamValueName = expandparamValueName;
                }
            }
        }
    }
}
