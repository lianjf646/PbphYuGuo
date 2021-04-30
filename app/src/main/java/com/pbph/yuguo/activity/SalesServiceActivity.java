package com.pbph.yuguo.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.ViewPagerAdapter;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.fragment.AfterSaleRecordFragment;
import com.pbph.yuguo.fragment.ApplyAfterSalesFragment;

import java.lang.reflect.Field;

public class SalesServiceActivity extends BaseActivity {
    private TabLayout tlMainTab;
    private ViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_service);
        initTitle(TITLE_STYLE_WHITE, "售后服务", true, false);
        initView();
        initFragment();
        initClick();
    }

    private void initView() {
        tlMainTab = findViewById(R.id.tl_main_tab);
        vpMain = findViewById(R.id.vp_main);
    }

    public void initFragment() {
        ApplyAfterSalesFragment applyAfterSales = new ApplyAfterSalesFragment();
        Bundle payBundle = new Bundle();
        payBundle.putInt("orderStatus", 4); //申请售后
        applyAfterSales.setArguments(payBundle);

        AfterSaleRecordFragment salesRecord = new AfterSaleRecordFragment();
        Bundle shippingBundle = new Bundle();
        shippingBundle.putInt("orderStatus", 9);    //售后记录
        salesRecord.setArguments(shippingBundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(applyAfterSales, "申请售后");
        adapter.addFragment(salesRecord, "售后记录");
        vpMain.setAdapter(adapter);
        tlMainTab.setupWithViewPager(vpMain);
        tlMainTab.post(() -> setIndicator(tlMainTab));
    }

    private void initClick() {

    }

    public void setIndicator(TabLayout tabs) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (tabStrip != null) {
            tabStrip.setAccessible(true);
        }
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) (tabStrip != null ? tabStrip.get(tabs) : null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < (llTab != null ? llTab.getChildCount() : 0); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
