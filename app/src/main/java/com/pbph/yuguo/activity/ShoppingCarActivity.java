package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.fragment.ShoppingCarFragment;

public class ShoppingCarActivity extends BaseActivity {


    FrameLayout fl_container;

    ShoppingCarFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcar);
        initTitle(TITLE_STYLE_WHITE, "购物车", true, false);
        initView();
    }


    private void initView() {
        fl_container = findViewById(R.id.fl_container);

        fragment = new ShoppingCarFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment).commit();
    }


    @Override
    public void onLeftClick() {
        finish();
    }


}
