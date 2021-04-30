package com.pbph.yuguo.adapter.shoppingcaradapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetShoppingCartResponse;

/**
 * Created by Administrator on 2018/8/2.
 */

public class EventVIPDayViewHolder extends ViewHolder<GetShoppingCartResponse.DataBean.MemberDayActivityBean> {

    public TextView tvShopcarTag;
    public TextView tvShopcarName;
    public TextView tvShopcarDo;

    @Override
    protected int getLayout() {
        return R.layout.adapter_shoppingcar_event;
    }

    @Override
    protected void getView(View itemView) {
        this.tvShopcarTag = (TextView) itemView.findViewById(R.id.tv_shopcar_tag);
        this.tvShopcarName = (TextView) itemView.findViewById(R.id.tv_shopcar_name);
        this.tvShopcarDo = (TextView) itemView.findViewById(R.id.tv_shopcar_do);

        tvShopcarTag.setText("");
        tvShopcarDo.setText("查看活动");

        itemView.findViewById(R.id.ll_shopcar_event).setOnClickListener(listener);
    }

    @Override
    protected void showView() {

        tvShopcarTag.setText(item.getActiveName());

        tvShopcarName.setText("尊享");
        tvShopcarName.append(item.getDiscount());
        tvShopcarName.append("折优惠");

    }

    private final OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (adapter.onItemViewClickListener == null) return;

            adapter.onItemViewClickListener.onItemViewClick(v.getId(), EventVIPDayViewHolder.this);
        }
    };

}
