package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;

/**
 * Created by 连嘉凡 on 2018/9/13.
 */

public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {

    private String orderId;
    private String money;
    private int jumpType = 0; // 0线上支付 1线下支付 （跳转到线上详情，或者线下详情）

    private Button btnLookOrder;
    private TextView tvMoney;

    @Override
    public void onLeftClick() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysuccess);
        initView();
        hideTitleView();

    }

    private void initView() {
        orderId = getIntent().getStringExtra("orderId");
        money = getIntent().getStringExtra("money");
        jumpType = getIntent().getIntExtra(PayCoreActivity.JUMP_TYPE, 0);
        btnLookOrder = (Button) findViewById(R.id.btn_look_order);
        tvMoney = findViewById(R.id.tv_money);
        btnLookOrder.setOnClickListener(this);
        tvMoney.setText(money);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_look_order: {
                if (jumpType == 0) {
                    Intent intent = new Intent(PaySuccessActivity.this, OrderDetailActivity.class);
                    intent.putExtra("orderId", Integer.valueOf(orderId));
                    startActivity(intent);
                } else if (jumpType ==1){
                    Intent intent = new Intent(PaySuccessActivity.this, StoreOrderDetailActivity.class);
                    intent.putExtra("orderId", Integer.valueOf(orderId));
                    startActivity(intent);
                }
                finish();
            }
            break;
        }
    }

}
