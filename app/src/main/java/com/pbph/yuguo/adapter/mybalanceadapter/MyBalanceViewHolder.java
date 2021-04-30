package com.pbph.yuguo.adapter.mybalanceadapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.MyGridView;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MyBalanceViewHolder extends ViewHolder<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean> {


    private TextView textView;
    private MyGridView myGridView;
    private DataAdapter gridAdapter;


    @Override
    protected int getLayout() {
        return R.layout.adapter_mybalance;
    }

    @Override
    protected void getView(View itemView) {
        textView = (TextView) itemView.findViewById(R.id.textView);
        myGridView = (MyGridView) itemView.findViewById(R.id.myGridView);
        gridAdapter = new DataAdapter(adapter.context, myGridView, 2);
    }

    @Override
    protected void showView() {
        textView.setText("储值");
        textView.append(String.valueOf(MoneyHelper.getInstance4Fen(item.getStoredMoney()).change2Yuan().getInt()));
        textView.append("元");
        ////////////
        gridAdapter.clearDatas();

        gridAdapter.addData(item.getVipBean(), Grid1ViewHolder.class);

        for (GetStoredConfigResponse.DataBean.StoredConfigDtoListBean.CouponActivityDtoListBean vo : item.getCouponActivityDtoList()) {
            gridAdapter.addData(vo, Grid2ViewHolder.class);
        }

        gridAdapter.notifyDataSetChanged();
    }
}
