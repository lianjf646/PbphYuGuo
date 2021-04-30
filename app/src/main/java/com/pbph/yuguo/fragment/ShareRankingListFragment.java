package com.pbph.yuguo.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.inviteadapter.ShareRankinglistViewHolder;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetUserInviteNumberListResquest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


public class ShareRankingListFragment extends BaseFragment {


    private SmartRefreshLayout refreshLayout;

    private ListView mLvGroupPurchaseGoodsList;
    private DataAdapter adapter;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_share_rankinglist;
    }

    @Override
    public void initView() {
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
//        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setHeaderHeight(60);
        refreshLayout.setOnRefreshListener(refreshLayout -> getUserInviteNumberList());
//        refreshLayout.setOnLoadMoreListener(refreshLayout -> presenter.getHttpDatasNextPage());

        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout()
                .postDelayed(() -> {
                }, 2000));

        mLvGroupPurchaseGoodsList = (ListView) mContentView.findViewById(R.id.lv_best_sellers);

        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, ShareRankinglistViewHolder.class);

        mLvGroupPurchaseGoodsList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
//            startActivity(new Intent(getActivity(), GoodsInfoActivity.class));
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInviteNumberList();
    }

    private void getUserInviteNumberList() {
        HttpAction.getInstance().getUserInviteNumberList(new GetUserInviteNumberListResquest()).subscribe(new BaseObserver<>(mContext, (response -> {

            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            adapter.setDatas(response.getData().getUserInviteNumberList());
        })));
    }

}
