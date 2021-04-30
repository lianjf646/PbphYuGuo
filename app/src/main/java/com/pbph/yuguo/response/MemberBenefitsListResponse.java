package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

import java.util.List;

/**
 * Created by zyp on 2019/1/7 0007.
 * class note:
 */

public class MemberBenefitsListResponse extends BaseResponse {

    /**
     * data : {"memberBenefitsList":[{"icon":"http://image.pbdsh.com/ygtestfold/16/6F000C292E52859DC3BD7D26A2E50647.png","memberBenefitsName":"会员专享价","showPicture":"http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png"},{"icon":"","memberBenefitsName":"积分兑好礼","showPicture":""},{"icon":"","memberBenefitsName":"会员试吃","showPicture":""},{"icon":"","memberBenefitsName":"新鲜配送","showPicture":""},{"icon":"","memberBenefitsName":"专属客服","showPicture":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MemberBenefitsListBean> memberBenefitsList;

        public List<MemberBenefitsListBean> getMemberBenefitsList() {
            return memberBenefitsList;
        }

        public void setMemberBenefitsList(List<MemberBenefitsListBean> memberBenefitsList) {
            this.memberBenefitsList = memberBenefitsList;
        }

        public static class MemberBenefitsListBean {
            /**
             * icon : http://image.pbdsh.com/ygtestfold/16/6F000C292E52859DC3BD7D26A2E50647.png
             * memberBenefitsName : 会员专享价
             * showPicture : http://image.pbdsh.com/ygtestfold/16/760A7C8288056C3E654BE4D6BDD7D387.png
             */

            private String icon;
            private String memberBenefitsName;
            private String showPicture;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getMemberBenefitsName() {
                return memberBenefitsName;
            }

            public void setMemberBenefitsName(String memberBenefitsName) {
                this.memberBenefitsName = memberBenefitsName;
            }

            public String getShowPicture() {
                return showPicture;
            }

            public void setShowPicture(String showPicture) {
                this.showPicture = showPicture;
            }
        }
    }
}
