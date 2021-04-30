package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetAppWithdrawCashListResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class WithdrawCashRecordsViewHolder extends ViewHolder<GetAppWithdrawCashListResponse.DataBean.DealsInfoDtoListBean> {

    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvMoney;


    @Override
    protected int getLayout() {
        return R.layout.adapter_withdrawcashrecords;
    }

    @Override
    protected void getView(View itemView) {
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
    }

    @Override
    protected void showView() {

        tvTitle.setText(item.getDealWay() == 2 ? "微信" : "支付宝");
        tvTitle.append("提现");

        tvTime.setText(item.getCreateTime());


        tvMoney.setText("-");
        tvMoney.append(MoneyHelper.getInstance4Fen(item.getDealPrice()).change2Yuan().getString());
        tvMoney.append("元");
    }
}
