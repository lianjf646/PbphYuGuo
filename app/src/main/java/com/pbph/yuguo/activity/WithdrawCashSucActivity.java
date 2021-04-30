package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;

/**
 * Created by 连嘉凡 on 2018/9/28.
 */

public class WithdrawCashSucActivity extends BaseActivity {
    private Button btnKnow;

    @Override
    public void onLeftClick() {
        goMainTabAct();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawcashsuc);
        btnKnow = findViewById(R.id.btn_know);
        btnKnow.setOnClickListener(v -> {
            goMainTabAct();
        });
        initTitle(TITLE_STYLE_WHITE, "提现申请", true, false);
    }

    private void goMainTabAct() {
        Intent intent = new Intent(this, MainTabActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goMainTabAct();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
