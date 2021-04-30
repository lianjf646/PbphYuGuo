package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.WithdrawCashRecordsViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppWithdrawCashListResquest;
import com.pbph.yuguo.response.GetAppWithdrawCashListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

public class WithdrawCashRecordsActivity extends BaseActivity {

    private SmartRefreshLayout refreshLayout;

    private ListView listView;
    private DataAdapter adapter;

    private int page = 1;

    View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawcashrecords);

        initTitle(TITLE_STYLE_WHITE, "提现记录", true, false);
        initView();
    }


    private void initView() {
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setHeaderHeight(60);
        refreshLayout.setOnRefreshListener(refreshLayout -> getAppWithdrawCashList(page = 1));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> getAppWithdrawCashList(page));


        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.view_no_datas);
        emptyView.setVisibility(View.VISIBLE);

        adapter = new DataAdapter(mContext, listView, WithdrawCashRecordsViewHolder.class);

        listView.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
//                adapter.choiceHelper.putChoiceNotify(position);
            }
        });

        getAppWithdrawCashList(page = 1);
    }


    @Override
    public void onLeftClick() {
        finish();
    }


    private void getAppWithdrawCashList(int now_page) {

        if (page != now_page) {
            return;
        }
        if (page == -1) {
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadMore(false);
            return;
        }
        if (page > 1) {
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadMore(true);
        }

        HttpAction.getInstance().getAppWithdrawCashList(new GetAppWithdrawCashListResquest(YuGuoApplication.userInfo.getCustomerId(), now_page, ConstantData.PAGE_SIZE)).subscribe(new BaseObserver<>(mContext, (response -> {

            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }

            //如果 等于零 代表第一页 要清除数据
            if (page == now_page && now_page == 1) {
                adapter.clearDatas();
            }


            List<GetAppWithdrawCashListResponse.DataBean.DealsInfoDtoListBean> list = response.getData().getDealsInfoDtoList();
            if (list == null || list.isEmpty() || list.size() < ConstantData.PAGE_SIZE) {
                page = -1;
                refreshLayout.setEnableRefresh(true);
                refreshLayout.setEnableLoadMore(false);
            } else {
                //加一页
                page++;
                refreshLayout.setEnableRefresh(true);
                refreshLayout.setEnableLoadMore(true);
            }
            adapter.addDatas(list);

            if (adapter.getCount() <= 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        })));
    }
}
