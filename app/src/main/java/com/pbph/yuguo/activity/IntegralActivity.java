package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.IntegralAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetMyPointSourceListRequest;
import com.pbph.yuguo.response.GetMyPointSourceListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/13.
 */

public class IntegralActivity extends BaseActivity {

    private ListView lv_integral;
    List<GetMyPointSourceListResponse.DataBean.PointListBean> pointListBeanList;

    private TextView tvIntegralNum;
    private TextView tvNoList;
    private IntegralAdapter integralAdapter;
    private SmartRefreshLayout mRefreshLayout;

    private int pageNum = 1;

    @Override
    public void onLeftClick() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        initTitle(TITLE_STYLE_WHITE, "积分", true, false);
        initView();
        initData();
    }

    private void initView() {
        lv_integral = findViewById(R.id.lv_integral);
        tvIntegralNum = findViewById(R.id.tv_integral_num);
        mRefreshLayout = findViewById(R.id.mRefreshLayout);
        tvNoList = findViewById(R.id.tv_no_list);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getMyPointSourceList();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getMyPointSourceList();
        });

    }

    private void initData() {
        integralAdapter = new IntegralAdapter(this);
        lv_integral.setAdapter(integralAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyPointSourceList();

    }

    /**
     * 获取积分来源列表
     */
    private void getMyPointSourceList() {
        HttpAction.getInstance().getMyPointSourceList(new GetMyPointSourceListRequest
                (YuGuoApplication.userInfo.getCustomerId(), pageNum, ConstantData.PAGE_SIZE))
                .subscribe(new BaseObserver<>(this, response -> {
                    mRefreshLayout.finishLoadMore();
                    mRefreshLayout.finishRefresh();
                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        return;
                    }
                    pointListBeanList = response.getData().getPointList();
                    if (pageNum==1){
                        defaultVisOrGone();
                        integralAdapter.setPointListBeanList(pointListBeanList);
                        tvIntegralNum.setText(String.valueOf(response.getData().getSumPointScore()));
                    }else {
                        integralAdapter.addPointListBeanList(pointListBeanList);
                        tvIntegralNum.setText(String.valueOf(response.getData().getSumPointScore()));
                    }
                    if (pointListBeanList.size() < ConstantData.PAGE_SIZE) {
                        mRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
                    } else {
                        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
                    }

                }, ((code, message) -> {
                    mRefreshLayout.finishLoadMore();
                    mRefreshLayout.finishRefresh();
                })));

    }
    private void defaultVisOrGone() {
        if (pointListBeanList.size() == 0) {
            tvNoList.setVisibility(View.VISIBLE);
        } else {
            tvNoList.setVisibility(View.GONE);
        }
    }
}
