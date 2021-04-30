package com.pbph.yuguo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.CustomerCouponAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.RecycleViewDivider;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCouponListRequest;
import com.pbph.yuguo.response.CouponResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;


/**
 * 优惠券fragment
 * Created by zyp on 2018/8/10 0010.
 */

public class CouponFragment extends BaseFragment {

    private int couponStatus;
    private SmartRefreshLayout srlMain;
    private RecyclerView rvCouponList;
    private int pageNum = 1;
    private CustomerCouponAdapter adapter;
    private TextView tvTips;
    private View layoutNoCoupon;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        couponStatus = bundle.getInt("couponStatus", 2);
        srlMain = mContentView.findViewById(R.id.srl_main);
        rvCouponList = mContentView.findViewById(R.id.rv_coupon_list);
        layoutNoCoupon = mContentView.findViewById(R.id.layout_no_list);
        tvTips = layoutNoCoupon.findViewById(R.id.tv_tips);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dp_100dp);
        tvTips.setLayoutParams(params);
        srlMain.setEnableAutoLoadMore(false);
        initData();
        initClick();
    }

    private void initData() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        WaitUI.Show(getActivity());
        GetCouponListRequest request = new GetCouponListRequest(customerId, couponStatus, pageNum, ConstantData.PAGE_SIZE);
        HttpAction.getInstance().getCustomerCouponList(request).subscribe(new BaseObserver<>(getActivity(), response -> {
            int code = response.getCode();
            if (code == 200) {
                CouponResponse.DataBean data = response.getData();
                List<CouponResponse.DataBean.ListBean> couponList = data.getList();
                setEmptyView(couponList);

                adapter = new CustomerCouponAdapter(mContext, couponStatus, couponList);
                rvCouponList.setLayoutManager(new LinearLayoutManager(mContext));
                rvCouponList.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL,
                        getResources().getDimensionPixelOffset(R.dimen.dp_5dp), getResources().getColor(R.color.gray_ebebeb)));
                rvCouponList.setAdapter(adapter);
            }
            WaitUI.Cancel();
        }));
    }

    private void getCouponList() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        GetCouponListRequest request = new GetCouponListRequest(customerId, couponStatus, pageNum, ConstantData.PAGE_SIZE);
        HttpAction.getInstance().getCustomerCouponList(request).subscribe(new BaseObserver<>(getActivity(), response -> {
            int code = response.getCode();
            if (code == 200) {
                CouponResponse.DataBean data = response.getData();
                List<CouponResponse.DataBean.ListBean> couponList = data.getList();
                setCouponList(couponList);
                setEmptyView(couponList);
                if (pageNum == 1) {
                    srlMain.finishRefresh();
                } else {
                    srlMain.finishLoadMore();
                }
            }
        }));
    }

    private void setEmptyView(List<CouponResponse.DataBean.ListBean> couponList) {
        if (couponList == null || couponList.size() == 0) {
            if (pageNum == 1) {
                rvCouponList.setVisibility(View.INVISIBLE);
                layoutNoCoupon.setVisibility(View.VISIBLE);
            }
        } else {
            rvCouponList.setVisibility(View.VISIBLE);
            layoutNoCoupon.setVisibility(View.INVISIBLE);
        }
    }

    private void initClick() {
        srlMain.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getCouponList();
        });

        srlMain.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getCouponList();
        });
    }

    private void setCouponList(List<CouponResponse.DataBean.ListBean> couponList) {
        if (pageNum == 1) {
            //下拉刷新
            adapter.refresh(couponList);
        } else {
            //上拉加载
            adapter.add(couponList);
        }
    }
}
