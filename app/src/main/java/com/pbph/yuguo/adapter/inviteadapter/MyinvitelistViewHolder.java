package com.pbph.yuguo.adapter.inviteadapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetMyInvitationListResponse;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MyinvitelistViewHolder extends ViewHolder<GetMyInvitationListResponse.DataBean.UserInviteYieldListBean> {


    private ImageView tvShareranklist1;
    private TextView tvShareranklist2;
    private TextView tvShareranklist3;

    @Override
    protected int getLayout() {
        return R.layout.adapter_share_my_rankinglist;
    }

    @Override
    protected void getView(View itemView) {
        this.tvShareranklist1 = itemView.findViewById(R.id.iv_shareranklist1);
        this.tvShareranklist2 = itemView.findViewById(R.id.tv_shareranklist_2);
        this.tvShareranklist3 = itemView.findViewById(R.id.tv_shareranklist_3);
    }

    @Override
    protected void showView() {
        GlideUtil.displayCircleBitmap(YuGuoApplication.getContext(), item.getCustomerImgUrl(), tvShareranklist1);
        tvShareranklist2.setText(item.getCustomerNickName());
        tvShareranklist3.setText(item.getCustomerPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    }


}
