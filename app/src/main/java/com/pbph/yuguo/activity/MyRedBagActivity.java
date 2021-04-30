package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MyRedBagAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.MySharePopRedBagWindow;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppRedPackageDealsInfoRequest;
import com.pbph.yuguo.response.GetAppRedPackageDealsInfoResponse;
import com.pbph.yuguo.wxutil.WechatUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;


public class MyRedBagActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView reRedBag;
    private MyRedBagAdapter myRedBagAdapter;
    private TextView tvShare;
    private TextView tvMoney;
    private NestedScrollView nestedScrollView;
    private SmartRefreshLayout refreshLayout;
    private TextView tvSumMaidProfit;

    private int customerId;
    private int pageNum = 1;

    private List<GetAppRedPackageDealsInfoResponse.DataBean.DealsInfoDtoListBean>
            infoDtoListBeanList;

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_red_bag);
        initTitle(TITLE_STYLE_WHITE, "我的红包", true, false);
        initView();
        initData();

    }

    private void initView() {
        reRedBag = findViewById(R.id.re_red_bag);
        tvShare = findViewById(R.id.tv_share);
        tvMoney = findViewById(R.id.tv_money);
        tvSumMaidProfit = findViewById(R.id.tv_sum_maid_profit);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        refreshLayout = findViewById(R.id.refreshLayout);
        tvShare.setOnClickListener(this);

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getAppRedPackageDealsInfo();
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getAppRedPackageDealsInfo();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //   创建分割线对象，第一个参数为上下文，第二个参数为RecyclerView排列方向
        DividerItemDecoration decoration = new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL);
        //   为RecyclerView添加分割线
        reRedBag.addItemDecoration(decoration);
        reRedBag.setLayoutManager(layoutManager);

        findViewById(R.id.btn_withdrawcash).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext, WithdrawCashActivity.class));
            }
        });
    }


    private void initData() {
        customerId = YuGuoApplication.userInfo.getCustomerId();
        myRedBagAdapter = new MyRedBagAdapter(this);
        reRedBag.setAdapter(myRedBagAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAppRedPackageDealsInfo();
        getCustomerInfoById();

    }

    MySharePopRedBagWindow mySharePopWindow;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share: {
                if (mySharePopWindow == null)
                    mySharePopWindow = new MySharePopRedBagWindow(this, onShareClickListener);

                mySharePopWindow.showDialog(v);
            }
            break;

        }
    }

    MySharePopRedBagWindow.ShareOnClickListener onShareClickListener = type -> {
        switch (type) {
            case SHARELIFEWX: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPage(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
            }
            break;
            case SHARETIMELINE: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPageTimeLine(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
            }
            break;
        }
    };

    /**
     * 获取收支明细
     */
    private void getAppRedPackageDealsInfo() {
        HttpAction.getInstance().getAppRedPackageDealsInfo(new GetAppRedPackageDealsInfoRequest
                (customerId, pageNum, ConstantData.PAGE_SIZE)).subscribe(new BaseObserver<>(this,
                response -> {
                    infoDtoListBeanList = response.getData().getDealsInfoDtoList();
                    if (pageNum == 1) {
                        myRedBagAdapter.setInfoDtoListBeanList(infoDtoListBeanList);
                        nestedScrollViewVisOrGone();
                    } else {
                        myRedBagAdapter.addInfoDtoListBeanList(infoDtoListBeanList);
                    }

                    if (infoDtoListBeanList.size() < ConstantData.PAGE_SIZE) {
                        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
                    } else {
                        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
                    }

                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();

                }, ((code, message) -> {
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();

        })));

    }

    private void nestedScrollViewVisOrGone() {
        if (infoDtoListBeanList.size() == 0) {
            nestedScrollView.setVisibility(View.VISIBLE);
        } else {
            nestedScrollView.setVisibility(View.GONE);
        }
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
//        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest
//                (YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>
//                (mContext, response -> {
//                    if (200 != response.getCode()) {
//                        showToast(response.getMsg());
//                        return;
//                    }
//                    tvMoney.setText("¥");
//                    tvMoney.append(MoneyHelper.getInstance4Fen(response.getData().getCustomer()
//                            .getRedPackageMoney()).change2Yuan().getString());
//                    tvSumMaidProfit.setText("累计收入:");
//                    tvSumMaidProfit.append(MoneyHelper.getInstance4Fen(response.getData()
//                            .getCustomer()
//                            .getSumMaidProfit()).change2Yuan().getString());
//                }));
//
    }
}
