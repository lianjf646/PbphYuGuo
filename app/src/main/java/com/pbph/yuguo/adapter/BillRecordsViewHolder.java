package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GetAppStorageValueRecordResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class BillRecordsViewHolder extends ViewHolder<GetAppStorageValueRecordResponse.DataBean
        .DealsInfoDtoListBean> {

    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvMoney;


    @Override
    protected int getLayout() {
        return R.layout.adapter_billrecords;
    }

    @Override
    protected void getView(View itemView) {
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
    }

    @Override
    protected void showView() {
        tvTime.setText(item.getCreateTime());
        if (item.getDealType() == 1) {
            tvMoney.setText("-");
            tvTitle.setText("余额支付");
        } else if (item.getDealType() == 2) {
            tvMoney.setText("+");
            tvTitle.setText("退款");
        } else if (item.getDealType() == 3) {
            tvMoney.setText("+");
            tvTitle.setText("储值");
        } else if (item.getDealType() == 4) {
            tvMoney.setText("+");
            tvTitle.setText("会员充值");
        } else {
//            tvMoney.setText("+");
//            switch (item.getDealWay()) {
//                case 1:
//                    tvTitle.setText("h5储值");
//                    break;
//                case 2:
//                    tvTitle.setText("微信储值");
//                    break;
//                case 3:
//                    tvTitle.setText("支付宝储值");
//                    break;
//                case 4:
//                    tvTitle.setText("余额储值");
//                    break;
//            }
        }
        tvMoney.append(MoneyHelper.getInstance4Fen(item.getDealPrice()).change2Yuan().getString());
        tvMoney.append("元");


    }
}
