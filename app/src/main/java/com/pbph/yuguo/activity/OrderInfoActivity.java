package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.OrderInfoAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetMessageInfoListResquest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Created by v on 2019/4/28.
 */
public class OrderInfoActivity extends BaseActivity {
    private int customerId;
    private int pageNum = 1;
    private int pageSize = ConstantData.PAGE_SIZE;
    private int messageType;

    private RecyclerView recycler;
    private OrderInfoAdapter orderInfoAdapter;

    private Button btnRefund;//退款售后
    private Button btnOrder; // 我的订单


    private SmartRefreshLayout smartRefreshLayout;

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderinfo);
        messageType = getIntent().getIntExtra("messageType", 0);
        initTitle(TITLE_STYLE_WHITE, "订单消息", true, false);
        customerId = UserInfo.getInstance().getCustomerId();
        initView();
    }

    private void initView() {
        orderInfoAdapter = new OrderInfoAdapter(this);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(orderInfoAdapter);

        btnRefund = findViewById(R.id.btn_refund);
        btnRefund.setOnClickListener(onSPClickListener);

        btnOrder = findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(onSPClickListener);

        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setHeaderHeight(60);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getMessageInfoList();
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getMessageInfoList();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageInfoList();
    }

    OnSPClickListener onSPClickListener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(OrderInfoActivity.this, LoginActivity.class));
                return;
            }
            switch (v.getId()) {
                case R.id.btn_refund: {
                    startActivity(new Intent(mContext, SalesServiceActivity.class));
                }
                break;
                case R.id.btn_order: {
                    startActivity(new Intent(mContext, MyOrderActivity.class));
                }
                break;
            }
        }
    };

    private void getMessageInfoList() {
        HttpAction.getInstance().getMessageInfoList(new GetMessageInfoListResquest(customerId,
                pageNum, pageSize, messageType)).subscribe(new BaseObserver<>(this, response -> {

            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (response.getData().getList().size() < 20) {
                smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
            } else {
                smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
            }
            if (pageNum == 1) {
                orderInfoAdapter.setListBeanList(response.getData().getList());
            } else {
                orderInfoAdapter.addListBeanList(response.getData().getList());
            }

        }, (code, message) -> {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        }));
    }
}
