package com.pbph.yuguo.adapter.mybalanceadapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetStoredConfigResponse;

/**
 * Created by Administrator on 2018/8/2.
 */

public class Grid1ViewHolder extends ViewHolder<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean.VipBean> {

    private TextView textView1;
    private TextView textView2;

    @Override
    protected int getLayout() {
        return R.layout.adapter_mybalance_grid1;
    }

    @Override
    protected void getView(View itemView) {
        textView1 = itemView.findViewById(R.id.textView1);
        textView2 = itemView.findViewById(R.id.textView2);
    }

    @Override
    protected void showView() {
        textView1.setText(String.valueOf(item.getMemberTime()));
        textView2.setText(item.getMemberFlag() == 1 ? "首充" : "");
        textView2.append("体验会员");
    }
}
