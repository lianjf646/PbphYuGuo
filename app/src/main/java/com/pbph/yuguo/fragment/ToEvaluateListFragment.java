package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.EvaluateCenterActivity;
import com.pbph.yuguo.adapter.WaitEvaluateGoodsAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetWaitEvaluateListRequest;
import com.pbph.yuguo.response.EvaluateOrderResponse;
import com.pbph.yuguo.response.OrderListResponse;
import com.pbph.yuguo.response.WaitEvaluateListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:待评价列表
 */

public class ToEvaluateListFragment extends BaseFragment {

    private SmartRefreshLayout srlMain;
    private RecyclerView rvEvaluateList;
    private WaitEvaluateGoodsAdapter adapter;
    private View layoutNoList;
    private TextView tvTips;

    private int orderStatus = -1;
    private int pageNum = 1;

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        orderStatus = bundle.getInt("orderStatus", -1);
        srlMain = mContentView.findViewById(R.id.srl_main);
        rvEvaluateList = mContentView.findViewById(R.id.rv_evaluate_list);
        layoutNoList = mContentView.findViewById(R.id.layout_no_list);
        tvTips = layoutNoList.findViewById(R.id.tv_tips);
        srlMain.setEnableAutoLoadMore(false);
        initData();
        initClick();
    }

    private void initData() {
        WaitUI.Show(getActivity());
        GetWaitEvaluateListRequest request = new GetWaitEvaluateListRequest(YuGuoApplication.userInfo.getCustomerId(), pageNum, ConstantData.PAGE_SIZE);

        HttpAction.getInstance().getWaitEvaluateList(request).subscribe(new BaseObserver<>(getActivity(), (orderDate -> {
            int code = orderDate.getCode();
            if (code == 200) {
                WaitEvaluateListResponse.DataBean dataBean = orderDate.getData();
                List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList = dataBean.getWaitEvaluateList();
                adapter = new WaitEvaluateGoodsAdapter(mContext, waitEvaluateList);
                rvEvaluateList.setLayoutManager(new LinearLayoutManager(mContext));
                rvEvaluateList.setAdapter(adapter);
                setEmptyView(waitEvaluateList);
                adapter.setOnRecItemClickListener((view, position) -> {
                    //跳转到填写商品评价页面
                    WaitEvaluateListResponse.DataBean.WaitEvaluateListBean waitEvaluate = adapter.getItem(position);
                    if (waitEvaluate == null) {
                        return;
                    }
                    Intent intent = new Intent(mContext, EvaluateCenterActivity.class);
                    intent.putExtra("waitEvaluate", waitEvaluate);
                    startActivity(intent);
                });
            }
            WaitUI.Cancel();
        }), (code, message) -> {
            setEmptyView(null);
            ToastUtil.showToast(getActivity(),message);
        }));
    }

    private void initClick() {

        srlMain.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getWaitEvaluateList();

        });

        srlMain.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getWaitEvaluateList();
        });
    }

    private void getWaitEvaluateList() {
        GetWaitEvaluateListRequest request = new GetWaitEvaluateListRequest(YuGuoApplication.userInfo.getCustomerId(), pageNum, ConstantData.PAGE_SIZE);

        HttpAction.getInstance().getWaitEvaluateList(request).subscribe(new BaseObserver<>(getActivity(), (response -> {
            int code = response.getCode();
            if (code == 200) {
                WaitEvaluateListResponse.DataBean dataBean = response.getData();
                List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList = dataBean.getWaitEvaluateList();
                setWaitEvaluateList(waitEvaluateList);
                setEmptyView(waitEvaluateList);
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

    private void setWaitEvaluateList(List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList) {
        if (pageNum == 1) {
            //下拉刷新
            adapter.refresh(waitEvaluateList);
        } else {
            //上拉加载
            adapter.add(waitEvaluateList);
        }
    }

    private void setEmptyView(List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList) {
        if (waitEvaluateList == null || waitEvaluateList.size() == 0) {
            if (pageNum == 1) {
                rvEvaluateList.setVisibility(View.INVISIBLE);
                layoutNoList.setVisibility(View.VISIBLE);
                tvTips.setText("暂无待评价列表~");
            }
        } else {
            rvEvaluateList.setVisibility(View.VISIBLE);
            layoutNoList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_to_evaluate_list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPage(EvaluateOrderResponse evaluateOrderResponse) {
        pageNum = 1;
        getWaitEvaluateList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
