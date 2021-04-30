package com.pbph.yuguo.adapter.inviteadapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetUserInviteNumberListResponse;
import com.pbph.yuguo.util.AMUtils;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ShareRankinglistViewHolder extends ViewHolder<GetUserInviteNumberListResponse.DataBean.UserInviteNumberListBean> {


    private TextView tvShareranklist1;
    private TextView tvShareranklist2;
    private TextView tvShareranklist3;

    @Override
    protected int getLayout() {
        return R.layout.adapter_share_rankinglist;
    }

    @Override
    protected void getView(View itemView) {
        this.tvShareranklist1 = (TextView) itemView.findViewById(R.id.tv_shareranklist1);
        this.tvShareranklist2 = (TextView) itemView.findViewById(R.id.tv_shareranklist2);
        this.tvShareranklist3 = (TextView) itemView.findViewById(R.id.tv_shareranklist3);
    }

    @Override
    protected void showView() {
        tvShareranklist1.setText(String.valueOf(position + 1));

        if (AMUtils.isMobile(item.getCustomerNickName())) {
            tvShareranklist2.setText(item.getCustomerNickName().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        } else {
            tvShareranklist2.setText(item.getCustomerNickName());
        }

        tvShareranklist3.setText(String.valueOf(item.getCustomerNum()));
    }


}
