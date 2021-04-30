package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class RechargeViewHolder extends ViewHolder<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean> {

    private LinearLayout llItemMoney;
    private TextView tvItemMoney;


    @Override
    protected int getLayout() {
        return R.layout.adapter_recharge;
    }

    @Override
    protected void getView(View itemView) {
        llItemMoney = (LinearLayout) itemView.findViewById(R.id.ll_item_money);
        tvItemMoney = (TextView) itemView.findViewById(R.id.tv_item_money);
    }

    @Override
    protected void showView() {

        tvItemMoney.setText(String.valueOf(MoneyHelper.getInstance4Fen(item.getStoredMoney()).change2Yuan().getInt()));
        tvItemMoney.append("元");

        if (adapter.choiceHelper.isChoiced(position)) {
            llItemMoney.setBackgroundResource(R.drawable.withdrawach_item_bg);
            tvItemMoney.setTextColor(adapter.context.getResources().getColor(R.color.main_color));

        } else {
            llItemMoney.setBackgroundResource(R.drawable.withdrawach_item_bg1);
            tvItemMoney.setTextColor(adapter.context.getResources().getColor(R.color.black));
        }
    }
}
