package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.OrderDetailActivity;
import com.pbph.yuguo.adapter.OrderAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetOrderListRequest;
import com.pbph.yuguo.response.OrderDetailResponse;
import com.pbph.yuguo.response.OrderListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * 订单列表
 * Created by zyp on 2018/8/10 0010.
 */

public class OrderStatusFragment extends BaseFragment {

    private RecyclerView rvOrderList;
    private SmartRefreshLayout srlMain;
    private View layoutNoList;
    private TextView tvTips;
    private int orderStatus;
    private OrderAdapter adapter;
    private int pageNum = 1;

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
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


    public void initData() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1) {
            ToastUtil.showToast(mContext, "获取用户id失败");
            return;
        }
        WaitUI.Show(getActivity());
        GetOrderListRequest request = new GetOrderListRequest(customerId, pageNum, ConstantData.PAGE_SIZE, orderStatus);

        HttpAction.getInstance().getOrderList(request).subscribe(new BaseObserver<>(getActivity(), (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                OrderListResponse.DataBean dataBean = orderDate.getData();
                List<OrderListResponse.DataBean.OrderListBean> orderList = dataBean.getOrderList();
                adapter = new OrderAdapter(this, orderStatus, orderList);
                rvOrderList.setLayoutManager(new LinearLayoutManager(mContext));
                rvOrderList.setAdapter(adapter);

                setEmptyView(orderList);

                adapter.setOnRecItemClickListener((view, position) -> {
                    OrderListResponse.DataBean.OrderListBean order = null;
                    if (orderList != null) {
                        order = orderList.get(position);
                    }
                    int orderId = 0;
                    if (order != null) {
                        orderId = order.getOrderId();
                    }
                    //跳转到正常订单的详情页面
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                });
            }
            WaitUI.Cancel();
        }), (code, message) -> {
            setEmptyView(null);
            ToastUtil.showToast(getActivity(),message);
        }));
    }

    public void getOrderList() {
        GetOrderListRequest request = new GetOrderListRequest(YuGuoApplication.userInfo.getCustomerId(), pageNum, ConstantData.PAGE_SIZE, orderStatus);

        HttpAction.getInstance().getOrderList(request).subscribe(new BaseObserver<>(getActivity(), (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                OrderListResponse.DataBean dataBean = orderDate.getData();
                List<OrderListResponse.DataBean.OrderListBean> orderList = dataBean.getOrderList();
                setOrderList(orderList);
                setEmptyView(orderList);
            }
            if (pageNum == 1) {
                srlMain.finishRefresh();
            } else {
                srlMain.finishLoadMore();
            }
        }), (code, message) -> {
            setEmptyView(null);
            ToastUtil.showToast(getActivity(),message);
        }));
    }

    private void initClick() {
        srlMain.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getOrderList();
        });

        srlMain.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getOrderList();
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

    private void setEmptyView(List<OrderListResponse.DataBean.OrderListBean> orderList) {
        if (orderList == null || orderList.size() == 0) {
            if (pageNum == 1) {
                rvOrderList.setVisibility(View.INVISIBLE);
                layoutNoList.setVisibility(View.VISIBLE);
                tvTips.setText("暂无订单，快去下一单吧~");
            }
        } else {
            rvOrderList.setVisibility(View.VISIBLE);
            layoutNoList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Subscribe
    public void refreshPage(OrderDetailResponse orderDetailResponse) {
        pageNum = 1;
        getOrderList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
