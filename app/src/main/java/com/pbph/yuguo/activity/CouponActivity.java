package com.pbph.yuguo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.ViewPagerAdapter;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.fragment.CouponFragment;

import java.lang.reflect.Field;

public class CouponActivity extends BaseActivity {
    private final Context context = CouponActivity.this;

    private TabLayout tlMainTab;
    private ViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        initTitle(TITLE_STYLE_WHITE, "优惠券", true, false);
        initView();
        initFragment();
    }

    private void initView() {
        tlMainTab = findViewById(R.id.tl_main_tab);
        vpMain = findViewById(R.id.vp_main);
    }

    private void initFragment() {
        CouponFragment unusedCoupon = new CouponFragment();
        Bundle unusedBundle = new Bundle();
        unusedBundle.putInt("couponStatus", 2);
        unusedCoupon.setArguments(unusedBundle);

        CouponFragment usedCoupon = new CouponFragment();
        Bundle usedBundle = new Bundle();
        usedBundle.putInt("couponStatus", 3);
        usedCoupon.setArguments(usedBundle);

        CouponFragment expiredCoupon = new CouponFragment();
        Bundle expiredBundle = new Bundle();
        expiredBundle.putInt("couponStatus", 4);
        expiredCoupon.setArguments(expiredBundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(unusedCoupon, "未使用");
        adapter.addFragment(usedCoupon, "已使用");
        adapter.addFragment(expiredCoupon, "已过期");

        vpMain.setAdapter(adapter);
        tlMainTab.setupWithViewPager(vpMain);
        tlMainTab.post(() -> setIndicator(tlMainTab));
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

        /*int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, Resources.getSystem().getDisplayMetrics());
        int tabWidth = SystemUtils.getWindowWidth(context) / 3;
        int space = (tabWidth - 60) / 2;*/
        for (int i = 0; i < (llTab != null ? llTab.getChildCount() : 0); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = 0;
            params.rightMargin = 0;*/
//            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
