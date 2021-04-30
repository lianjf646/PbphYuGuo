package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetIndexPopupCouponResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MainDialogViewHolder extends ViewHolder<GetIndexPopupCouponResponse.DataBean.ListBean> {

    public TextView tvTitle;
    public TextView tvTime;
    public LinearLayout llBgGreen;
    public LinearLayout llBgGray;

    @Override
    protected int getLayout() {
        return R.layout.adapter_main_dialog;
    }

    @Override
    protected void getView(View itemView) {
        this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        this.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        this.llBgGreen = (LinearLayout) itemView.findViewById(R.id.ll_bg_green);
        this.llBgGray = (LinearLayout) itemView.findViewById(R.id.ll_bg_gray);
    }

    @Override
    protected void showView() {

        llBgGray.setVisibility(View.GONE);

        String xPrice = MoneyHelper.getInstance4Fen(item.getCouponXPrice()).change2Yuan().getStringDelZero();
        String price = MoneyHelper.getInstance4Fen(item.getCouponPrice()).change2Yuan().getStringDelZero();
        switch (item.getCouponRuleType()) {
            case 1:  //满减
                tvTitle.setText(price);
                tvTitle.append("元优惠券");

                tvTime.setText("满");
                tvTime.append(xPrice);
                tvTime.append("元使用");

                break;
            case 2:          //直降
                tvTitle.setText(price);
                tvTitle.append("元优惠券");

                tvTime.setText("直降");
                break;
            case 3:          //无门槛
                tvTitle.setText(price);
                tvTitle.append("元优惠券");

                tvTime.setText("无门槛");
                break;
        }
    }

}
