package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.OrderRefundActivity;
import com.pbph.yuguo.adapter.AfterSaleRecordAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetOrderListRequest;
import com.pbph.yuguo.response.BackOrderListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * Created by zyp on 2018/9/11 0011.
 * class note:售后相关fragment
 */

public class AfterSaleRecordFragment extends BaseFragment {

    private RecyclerView rvOrderList;
    private SmartRefreshLayout srlMain;
    private View layoutNoList;
    private TextView tvTips;
    private int orderStatus;
    private AfterSaleRecordAdapter adapter;
    private int pageNum = 1;

    @Override
    public void initView() {
//        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        orderStatus = bundle.getInt("orderStatus", 0);
        rvOrderList = mContentView.findViewById(R.id.rv_order_list);
        srlMain = mContentView.findViewById(R.id.srl_main);
        layoutNoList = mContentView.findViewById(R.id.layout_no_list);
        tvTips = layoutNoList.findViewById(R.id.tv_tips);
        srlMain.setEnableAutoLoadMore(false);
        initData();
        initClick();
    }

    private void initData() {
        getAfterSaleRecordList();

    }

    //售后记录列表
    private void getAfterSaleRecordList() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        GetOrderListRequest request = new GetOrderListRequest(customerId, pageNum, ConstantData.PAGE_SIZE, 0);

        HttpAction.getInstance().getBackOrderList(request).subscribe(new BaseObserver<>(getActivity(), response -> {
            int code = response.getCode();
            if (code == 200) {
                BackOrderListResponse.DataBean dataBean = response.getData();
                int totalSize = dataBean.getTotleSize();
                List<BackOrderListResponse.DataBean.OrderListBean> orderList = dataBean.getList();
                adapter = new AfterSaleRecordAdapter(mContext, orderList);
                rvOrderList.setLayoutManager(new LinearLayoutManager(mContext));
                rvOrderList.setAdapter(adapter);

                if (orderList == null || orderList.size() == 0) {
                    rvOrderList.setVisibility(View.INVISIBLE);
                    layoutNoList.setVisibility(View.VISIBLE);
                    tvTips.setText("暂无售后记录~");
                } else {
                    rvOrderList.setVisibility(View.VISIBLE);
                    layoutNoList.setVisibility(View.INVISIBLE);
                }

                adapter.setOnRecItemClickListener((view, position) -> {

                    BackOrderListResponse.DataBean.OrderListBean order = orderList.get(position);
                    int orderId = order.getOrderId();
                    //跳转到售后的订单详情页面
                    Intent intent = new Intent(mContext, OrderRefundActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);

                });
            }
        }));
    }

    private void getList() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        GetOrderListRequest request = new GetOrderListRequest(customerId, pageNum, ConstantData.PAGE_SIZE, 0);

        HttpAction.getInstance().getBackOrderList(request).subscribe(new BaseObserver<>(getActivity(), (orderData -> {
            int code = orderData.getCode();
            notifyItem(code, orderData);
        })));
    }

    private void notifyItem(int code, BackOrderListResponse orderData) {
        if (code == 200) {
            BackOrderListResponse.DataBean dataBean = orderData.getData();
            List<BackOrderListResponse.DataBean.OrderListBean> orderList = dataBean.getList();
            setOrderList(orderList);
        }
        if (pageNum == 1) {
            srlMain.finishRefresh();
        } else {
            srlMain.finishLoadMore();
        }
    }

    private void initClick() {

        srlMain.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getList();

        });

        srlMain.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getList();
        });
    }

    private void setOrderList(List<BackOrderListResponse.DataBean.OrderListBean> orderList) {
        if (pageNum == 1) {
            //下拉刷新
            adapter.refresh(orderList);
        } else {
            //上拉加载
            adapter.add(orderList);
        }
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
