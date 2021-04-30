package com.pbph.yuguo.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.StoreOrderAdapter;
import com.pbph.yuguo.adapter.ViewPagerAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.fragment.OrderStatusFragment;
import com.pbph.yuguo.fragment.ToEvaluateListFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.StoreOrderListRequest;
import com.pbph.yuguo.response.StoreOrderListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sobot.chat.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity {

    private ImageView iv_back;

    //线上订单相关内容
    private View layout_app_order;
    private TabLayout tlMainTab;
    private ViewPager vpMain;
    private int orderStatus;

    //门店订单相关内容
    private View layout_store_order;
    private SmartRefreshLayout srl_store_main;
    private RecyclerView rv_store_order_list;
    private StoreOrderAdapter adapter;
    private View layout_no_list;
    private TextView tv_tips;
    private int storeOrderPageNum = 1;
    List<StoreOrderListResponse.DataBean.OrderListBean> orderList = new ArrayList<>();

    private int pageType = 1; //页面类型   1：APP订单  2：门店订单
    private RadioButton rb_app_order;
    private RadioButton rb_store_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
//        initTitle(TITLE_STYLE_WHITE, "我的订单", true, false);
        hideTitleView();
        initIntent();
        initView();
        initFragment();
        initClick();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            orderStatus = intent.getIntExtra("orderStatus", 0);
        }
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        rb_app_order = findViewById(R.id.rb_app_order);
        rb_store_order = findViewById(R.id.rb_store_order);
        rb_app_order.setChecked(true);
        rb_app_order.setTextColor(getResources().getColor(R.color.white));
        rb_store_order.setTextColor(getResources().getColor(R.color.main_color));

        //APP订单界面
        layout_app_order = findViewById(R.id.layout_app_order);
        tlMainTab = layout_app_order.findViewById(R.id.tl_main_tab);
        vpMain = layout_app_order.findViewById(R.id.vp_main);

        //门店订单界面
        layout_store_order = findViewById(R.id.layout_store_order);
        srl_store_main = layout_store_order.findViewById(R.id.srl_store_main);
        rv_store_order_list = layout_store_order.findViewById(R.id.rv_store_order_list);
        layout_no_list = layout_store_order.findViewById(R.id.layout_no_list);
        tv_tips = layout_no_list.findViewById(R.id.tv_tips);
        srl_store_main.setEnableAutoLoadMore(false);

        layout_app_order.setVisibility(View.VISIBLE);
        layout_store_order.setVisibility(View.GONE);

        adapter = new StoreOrderAdapter(this, orderList);
        rv_store_order_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_store_order_list.setAdapter(adapter);

//        getStoreOrder();
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
        if (!isChecked) return;
        switch (buttonView.getId()) {
            case R.id.rb_app_order: {
                pageType = 1;
                rb_app_order.setTextColor(getResources().getColor(R.color.white));
                rb_store_order.setTextColor(getResources().getColor(R.color.main_color));
            }
            break;
            case R.id.rb_store_order: {
                pageType = 2;
                rb_app_order.setTextColor(getResources().getColor(R.color.main_color));
                rb_store_order.setTextColor(getResources().getColor(R.color.white));
                getStoreOrder();
            }
            break;
        }
        switchPage();
    };

    private void switchPage() {
        if (pageType == 1) {
            //展示APP订单
            layout_app_order.setVisibility(View.VISIBLE);
            layout_store_order.setVisibility(View.GONE);
        } else {
            //展示门店订单
            layout_store_order.setVisibility(View.VISIBLE);
            layout_app_order.setVisibility(View.GONE);
        }
    }

    private void getStoreOrder() {
        storeOrderPageNum = 1;
        StoreOrderListRequest request = new StoreOrderListRequest(YuGuoApplication.userInfo.getCustomerId(), storeOrderPageNum, ConstantData.PAGE_SIZE);

        HttpAction.getInstance().getStoreOrderList(request).subscribe(new BaseObserver<>(mContext, (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                StoreOrderListResponse.DataBean dataBean = orderDate.getData();
                List<StoreOrderListResponse.DataBean.OrderListBean> orderList = dataBean.getOrderList();
//                adapter = new StoreOrderAdapter(this, orderList);
//                rv_store_order_list.setLayoutManager(new LinearLayoutManager(mContext));
//                rv_store_order_list.setAdapter(adapter);
                adapter.refresh(orderList);

                setEmptyView(orderList);

                /*adapter.setOnRecItemClickListener((view, position) -> {
                    StoreOrderListResponse.DataBean.OrderListBean order = null;
                    if (orderList != null) {
                        order = orderList.get(position);
                    }
                    int orderId = 0;
                    if (order != null) {
                        orderId = order.getOrderId();
                    }
                    //跳转到门店订单的详情页面
                    Intent intent = new Intent(mContext, StoreOrderDetailActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                });*/
            }
            WaitUI.Cancel();
        }), (code, message) -> {
            WaitUI.Cancel();
            setEmptyView(null);
            ToastUtil.showToast(mContext, message);
        }));
    }

    private void setEmptyView(List<StoreOrderListResponse.DataBean.OrderListBean> orderList) {
        if (orderList == null || orderList.size() == 0) {
            if (storeOrderPageNum == 1) {
                rv_store_order_list.setVisibility(View.INVISIBLE);
                layout_no_list.setVisibility(View.VISIBLE);
                tv_tips.setText("暂无订单，快去下一单吧~");
            }
        } else {
            rv_store_order_list.setVisibility(View.VISIBLE);
            layout_no_list.setVisibility(View.INVISIBLE);
        }
    }

    private void showStoreOrderList(List<StoreOrderListResponse.DataBean.OrderListBean> orderList) {
        if (storeOrderPageNum == 1) {
            //下拉刷新
            adapter.refresh(orderList);
        } else {
            //上拉加载
            adapter.add(orderList);
        }
    }

    private void initFragment() {
        OrderStatusFragment orderAll = new OrderStatusFragment();
        Bundle orderAllBundle = new Bundle();
        orderAllBundle.putInt("orderStatus", 0);

        orderAll.setArguments(orderAllBundle);
        OrderStatusFragment orderPay = new OrderStatusFragment();
        Bundle payBundle = new Bundle();
        payBundle.putInt("orderStatus", 1);
        orderPay.setArguments(payBundle);

        OrderStatusFragment orderShipping = new OrderStatusFragment();
        Bundle shippingBundle = new Bundle();
        shippingBundle.putInt("orderStatus", 2);
        orderShipping.setArguments(shippingBundle);

        OrderStatusFragment orderReceive = new OrderStatusFragment();
        Bundle receiveBundle = new Bundle();
        receiveBundle.putInt("orderStatus", 3);
        orderReceive.setArguments(receiveBundle);

        ToEvaluateListFragment orderAppraise = new ToEvaluateListFragment();
        Bundle appraiseBundle = new Bundle();
        appraiseBundle.putInt("orderStatus", 7);
        orderAppraise.setArguments(appraiseBundle);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(orderAll, "全部");
        adapter.addFragment(orderPay, "待付款");
        adapter.addFragment(orderShipping, "待发货");
        adapter.addFragment(orderReceive, "待收货");
        adapter.addFragment(orderAppraise, "待评价");
        vpMain.setAdapter(adapter);
        tlMainTab.setupWithViewPager(vpMain);
        tlMainTab.post(() -> setIndicator(tlMainTab));
        //设置跳转页面定位
        vpMain.setCurrentItem(orderStatus);
        tlMainTab.getTabAt(orderStatus).select();
    }

    private void initClick() {
        iv_back.setOnClickListener(v -> finish());

        rb_app_order.setOnCheckedChangeListener(onCheckedChangeListener);

        rb_store_order.setOnCheckedChangeListener(onCheckedChangeListener);

        srl_store_main.setOnRefreshListener(refreshLayout -> {
            storeOrderPageNum = 1;
            refreshOrderList();
        });

        srl_store_main.setOnLoadMoreListener(refreshLayout -> {
            storeOrderPageNum++;
            refreshOrderList();
        });

        adapter.setOnRecItemClickListener((view, position) -> {
            StoreOrderListResponse.DataBean.OrderListBean order = null;
            if (orderList != null) {
                order = orderList.get(position);
            }
            int orderId = 0;
            if (order != null) {
                orderId = order.getOrderId();
            }
            //跳转到门店订单的详情页面
            Intent intent = new Intent(mContext, StoreOrderDetailActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        });
    }

    private void refreshOrderList() {
        StoreOrderListRequest request = new StoreOrderListRequest(YuGuoApplication.userInfo.getCustomerId(), storeOrderPageNum, ConstantData.PAGE_SIZE);

        HttpAction.getInstance().getStoreOrderList(request).subscribe(new BaseObserver<>(mContext, (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                StoreOrderListResponse.DataBean dataBean = orderDate.getData();
                List<StoreOrderListResponse.DataBean.OrderListBean> orderList = dataBean.getOrderList();
                showStoreOrderList(orderList);
                setEmptyView(orderList);
                if (storeOrderPageNum == 1) {
                    srl_store_main.finishRefresh();
                } else {
                    srl_store_main.finishLoadMore();
                }
            }
            WaitUI.Cancel();
        }), (code, message) -> {
            WaitUI.Cancel();
            setEmptyView(null);
            ToastUtil.showToast(mContext, message);
        }));
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        getStoreOrder();
    }
}
