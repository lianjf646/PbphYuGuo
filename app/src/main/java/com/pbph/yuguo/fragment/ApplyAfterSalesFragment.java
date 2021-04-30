package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.OrderDetailActivity;
import com.pbph.yuguo.adapter.ApplyAfterSalesAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetOrderListRequest;
import com.pbph.yuguo.response.OrderListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * Created by zyp on 2018/9/11 0011.
 * class note:售后相关fragment
 */

public class ApplyAfterSalesFragment extends BaseFragment {

    private RecyclerView rvOrderList;
    private SmartRefreshLayout srlMain;
    private View layoutNoList;
    private TextView tvTips;
    private int orderStatus;
    private ApplyAfterSalesAdapter adapter;
    private int pageNum = 1;

    @Override
    public void initView() {
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
        getApplyAfterSaleList();
    }

    //申请售后列表
    private void getApplyAfterSaleList() {
        WaitUI.Show(getActivity());
        GetOrderListRequest request = new GetOrderListRequest(YuGuoApplication.userInfo.getCustomerId(), pageNum, ConstantData.PAGE_SIZE, orderStatus);

        HttpAction.getInstance().getOrderList(request).subscribe(new BaseObserver<>(getActivity(), (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                OrderListResponse.DataBean dataBean = orderDate.getData();
                int totalSize = dataBean.getTotleSize();
                List<OrderListResponse.DataBean.OrderListBean> orderList = dataBean.getOrderList();
                adapter = new ApplyAfterSalesAdapter(mContext, orderStatus, orderList);
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

                    OrderListResponse.DataBean.OrderListBean order = orderList.get(position);
                    int orderId = order.getOrderId();
                    //跳转到售后的订单详情页面
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("isApplyAfter", true);
                    startActivity(intent);
                });
            }
            WaitUI.Cancel();
        })));
    }

    private void getList() {
        GetOrderListRequest request = new GetOrderListRequest(YuGuoApplication.userInfo.getCustomerId(), pageNum, ConstantData.PAGE_SIZE, orderStatus);

        HttpAction.getInstance().getOrderList(request).subscribe(new BaseObserver<>(getActivity(), (orderData -> {
            int code = orderData.getCode();
            notifyItem(code, orderData);
        })));
    }

    private void notifyItem(int code, OrderListResponse orderData) {
        if (code == 200) {
            OrderListResponse.DataBean dataBean = orderData.getData();
            List<OrderListResponse.DataBean.OrderListBean> orderList = dataBean.getOrderList();
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

    private void setOrderList(List<OrderListResponse.DataBean.OrderListBean> orderList) {
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
    }
}
