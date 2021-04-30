package com.pbph.yuguo.adapter.mybalanceadapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class Grid2ViewHolder extends ViewHolder<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean.CouponActivityDtoListBean> {

    private TextView textView1;
    private TextView textView2;

    @Override
    protected int getLayout() {
        return R.layout.adapter_mybalance_grid2;
    }

    @Override
    protected void getView(View itemView) {
        textView1 = itemView.findViewById(R.id.textView1);
        textView2 = itemView.findViewById(R.id.textView2);
    }

    @Override
    protected void showView() {
        textView1.setText(String.valueOf(MoneyHelper.getInstance4Fen(item.getCouponPrice()).change2Yuan().getInt()));
        textView2.setText(item.getDiscountFlag() == 1 ? "首充" : "");
        switch (item.getCouponRuleType()) {
            case 3:
                textView2.append("无门槛券");
                break;
        }


    }
}
