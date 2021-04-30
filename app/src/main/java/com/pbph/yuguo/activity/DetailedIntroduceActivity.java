package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;

public class DetailedIntroduceActivity extends BaseActivity {

    private int returnRate;
    private TextView tvTitleBugRebate;
    private TextView tvBugRebateDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(TITLE_STYLE_WHITE, "会员权益说明", true, false);
        setContentView(R.layout.activity_detailed_introduce);
        returnRate = getIntent().getIntExtra("returnRate", 0);
        initView();

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    private void initView() {
        tvTitleBugRebate = (TextView) findViewById(R.id.tv_title_bug_rebate);
        tvBugRebateDetail = (TextView) findViewById(R.id.tv_bug_rebate_detail);
        tvTitleBugRebate.setText("▪购物返利" + returnRate + "%");
        tvBugRebateDetail.setText("每单可享"+returnRate+"%的返利,反金额无上限.返现形式:存入优惠券-抵用券中，不可提现，用于下次消费.");
    }
}
