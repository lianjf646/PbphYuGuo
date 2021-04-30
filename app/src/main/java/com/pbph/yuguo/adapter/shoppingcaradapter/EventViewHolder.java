package com.pbph.yuguo.adapter.shoppingcaradapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetShoppingCartResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class EventViewHolder extends ViewHolder<GetShoppingCartResponse.DataBean.ShoppingActivityListBean> {

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

        itemView.findViewById(R.id.ll_shopcar_event).setOnClickListener(listener);
    }

    @Override
    protected void showView() {
        String moneyAll = MoneyHelper.getInstance4Fen(item.getFullXmoney()).change2Yuan().getStringDelZero();
        if (item.getActiveType() == 0) {//活动类型(0:满赠; 1:满x元y件)
            tvShopcarTag.setText("赠品");

            if (item.getIsActivityOk() == 0) {
                //未满足活动

                tvShopcarName.setText("满");
                tvShopcarName.append(moneyAll);
                tvShopcarName.append("元，赠送超值商品，再购");
                tvShopcarName.append(MoneyHelper.getInstance4Fen(item.getSatisfactionMoney()).change2Yuan().getStringDelZero());
                tvShopcarName.append("元即享");

                tvShopcarDo.setText("去凑单");
            } else {
                //满足活动
                tvShopcarName.setText("已满");
                tvShopcarName.append(moneyAll);
                tvShopcarName.append("元，可赠送超值商品");

                tvShopcarDo.setText("去选择");
            }


        } else {
            tvShopcarTag.setText("超值");


            if (item.getIsActivityOk() == 0) {
                //未满足活动
                tvShopcarName.setText(moneyAll);
                tvShopcarName.append("元");
                tvShopcarName.append(String.valueOf(item.getYNumber()));
                tvShopcarName.append("件，再购");
                tvShopcarName.append(String.valueOf(item.getSatisfactionNum()));
                tvShopcarName.append("件即享");
            } else {
                //满足活动
                tvShopcarName.setText(moneyAll);
                tvShopcarName.append("元");
                tvShopcarName.append(String.valueOf(item.getYNumber()));
                tvShopcarName.append("件");
            }

            tvShopcarDo.setText("查看活动");


        }
    }

    private final OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (adapter.onItemViewClickListener == null) return;

            adapter.onItemViewClickListener.onItemViewClick(v.getId(), EventViewHolder.this);
        }
    };

}
