package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/1/7.
 */
public class GetIndexRotationChartResponse extends BaseResponse {


    /**
     * data : {"slideDto":[{"goodsId":0,"slideId":3,"slideName":"啊啊啊","slideRedirect":"111","slideUrl":"http://image.pbdsh
     * .com/ygtestfold/16/C1FB822EDB4E9D8676C5F2B2F6ABBD02.png"},{"goodsId":0,"slideId":1,"slideName":"111",
     * "slideRedirect":"222","slideUrl":"a"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<SlideDtoBean> slideDto;

        public List<SlideDtoBean> getSlideDto() {
            return slideDto;
        }

        public void setSlideDto(List<SlideDtoBean> slideDto) {
            this.slideDto = slideDto;
        }

        public static class SlideDtoBean {
            /**
             * goodsId : 0
             * slideId : 3
             * slideName : 啊啊啊
             * slideRedirect : 111
             * slideUrl : http://image.pbdsh.com/ygtestfold/16/C1FB822EDB4E9D8676C5F2B2F6ABBD02.png
             */

            private int goodsId;
            private int slideId;
            private String slideName;
            private String slideRedirect;
            private String slideUrl;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getSlideId() {
                return slideId;
            }

            public void setSlideId(int slideId) {
                this.slideId = slideId;
            }

            public String getSlideName() {
                return slideName;
            }

            public void setSlideName(String slideName) {
                this.slideName = slideName;
            }

            public String getSlideRedirect() {
                return slideRedirect;
            }

            public void setSlideRedirect(String slideRedirect) {
                this.slideRedirect = slideRedirect;
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
