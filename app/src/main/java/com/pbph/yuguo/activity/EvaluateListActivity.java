package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.EvaluateListAdapter;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.callback.MyCallBack;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetGoodsEvaluateListRequest;
import com.pbph.yuguo.response.GoodsEvaluateResponse;
import com.pbph.yuguo.response.OrderListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvaluateListActivity extends BaseActivity {
    private final Context context = EvaluateListActivity.this;
    private RecyclerView rvEvaluateList;
    private EvaluateListAdapter adapter;
    private TextView tvTips;
    private SmartRefreshLayout srlMain;

    private View layoutNoList;
    private int pageNum = 1;
    private int storeGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_list);
        initTitle(TITLE_STYLE_WHITE, "商品评价", true, false);
        initIntent();
        initView();
        initData();
        initClick();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            storeGoodsId = intent.getIntExtra("storeGoodsId", -1);
        }
    }

    private void initView() {
        rvEvaluateList = findViewById(R.id.rv_evaluate_list);
        layoutNoList = findViewById(R.id.layout_no_list);
        tvTips = layoutNoList.findViewById(R.id.tv_tips);
        srlMain = findViewById(R.id.srl_main);
        srlMain.setEnableAutoLoadMore(false);
    }

    private void initData() {
        WaitUI.Show(context);
        GetGoodsEvaluateListRequest request = new GetGoodsEvaluateListRequest(storeGoodsId, pageNum, ConstantData.PAGE_SIZE);
        HttpAction.getInstance().getGoodsEvaluateList(request).subscribe(new BaseObserver<>(context, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            if (200 == code) {

                GoodsEvaluateResponse.DataBean data = response.getData();
                List<GoodsEvaluateResponse.DataBean.EvaluateListBean> evaluateList = data.getEvaluateList();
                adapter = new EvaluateListAdapter(context, evaluateList);
                rvEvaluateList.setLayoutManager(new LinearLayoutManager(context));
                rvEvaluateList.setAdapter(adapter);
                setEmptyView(evaluateList);

                if (evaluateList == null || evaluateList.size() == 0) {
                    rvEvaluateList.setVisibility(View.INVISIBLE);
                    layoutNoList.setVisibility(View.VISIBLE);
                    tvTips.setText("暂无评论，快去评论吧~");
                } else {
                    rvEvaluateList.setVisibility(View.VISIBLE);
                    layoutNoList.setVisibility(View.INVISIBLE);
                }
            }
        }));
    }

    private void initClick() {
        srlMain.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getEvaluateList();

        });

        srlMain.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getEvaluateList();
        });
    }

    private void getEvaluateList() {
        GetGoodsEvaluateListRequest request = new GetGoodsEvaluateListRequest(storeGoodsId, pageNum, ConstantData.PAGE_SIZE);
        HttpAction.getInstance().getGoodsEvaluateList(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            if (200 == code) {
                GoodsEvaluateResponse.DataBean data = response.getData();
                List<GoodsEvaluateResponse.DataBean.EvaluateListBean> evaluateList = data.getEvaluateList();
                setEvaluateList(evaluateList);
                setEmptyView(evaluateList);
            }
            if (pageNum == 1) {
                srlMain.finishRefresh();
            } else {
                srlMain.finishLoadMore();
            }
        }));
    }

    private void setEvaluateList(List<GoodsEvaluateResponse.DataBean.EvaluateListBean> evaluateList) {
        if (pageNum == 1) {
            //下拉刷新
            adapter.refresh(evaluateList);
        } else {
            //上拉加载
            adapter.add(evaluateList);
        }
    }

    private void setEmptyView(List<GoodsEvaluateResponse.DataBean.EvaluateListBean> evaluateList) {
        if (evaluateList == null || evaluateList.size() == 0) {
            if (pageNum == 1) {
                rvEvaluateList.setVisibility(View.INVISIBLE);
                layoutNoList.setVisibility(View.VISIBLE);
                tvTips.setText("暂无评论，快去评论吧~");
            }
        } else {
            rvEvaluateList.setVisibility(View.VISIBLE);
            layoutNoList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
