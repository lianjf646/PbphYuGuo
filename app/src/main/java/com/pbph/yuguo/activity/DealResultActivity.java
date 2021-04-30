package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;

public class DealResultActivity extends BaseActivity implements View.OnClickListener {

    private ImageView image;
    private TextView tvResult;
    private TextView tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_result);
        hideTitleView();
//        initTitle(TITLE_STYLE_WHITE, "团购详情", true, false);
        initView();
    }


    private void initView() {
        image = (ImageView) findViewById(R.id.image);
        tvResult = (TextView) findViewById(R.id.tv_result);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        findViewById(R.id.button).setOnClickListener(this);
    }


    @Override
    public void onLeftClick() {
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                finish();
                break;
        }
    }
}
