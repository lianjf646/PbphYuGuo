package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * 抵用券实体类
 * Created by Administrator on 2018/8/6 0006.
 */

public class GetSlideResponse extends BaseResponse {

    /**
     * data : {"slideDtoList":[{"slideRedirect":"","slideSort":1,"slideUrl":"https://pbkj-scm-test.oss-cn-beijing.aliyuncs.com/1/upload3.jpg"},{"slideRedirect":"","slideSort":1,"slideUrl":"https://pbkj-scm-test.oss-cn-beijing.aliyuncs.com/1/upload3.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<SlideDtoListBean> slideDtoList;

        public List<SlideDtoListBean> getSlideDtoList() {
            return slideDtoList;
        }

        public void setSlideDtoList(List<SlideDtoListBean> slideDtoList) {
            this.slideDtoList = slideDtoList;
        }

        public static class SlideDtoListBean {
            /**
             * slideRedirect :
             * slideSort : 1
             * slideUrl : https://pbkj-scm-test.oss-cn-beijing.aliyuncs.com/1/upload3.jpg
             */

            private String slideRedirect;
            private int slideSort;
            private String slideUrl;

            public String getSlideRedirect() {
                return slideRedirect;
            }

            public void setSlideRedirect(String slideRedirect) {
                this.slideRedirect = slideRedirect;
            }

            public int getSlideSort() {
                return slideSort;
            }

            public void setSlideSort(int slideSort) {
                this.slideSort = slideSort;
            }

            public String getSlideUrl() {
                return slideUrl;
            }

            public void setSlideUrl(String slideUrl) {
                this.slideUrl = slideUrl;
            }
        }
    }
}
