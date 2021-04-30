package com.pbph.yuguo.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.inviteadapter.MyinvitelistViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetJuniorUserMoneyListResquest;
import com.pbph.yuguo.request.GetMyInvitationListListResquest;
import com.pbph.yuguo.response.GetJuniorUserMoneyListResponse;
import com.pbph.yuguo.response.GetMyInvitationListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;


public class MyInviteListFragment extends BaseFragment {


    private SmartRefreshLayout refreshLayout;

    private ListView mLvGroupPurchaseGoodsList;
    private DataAdapter adapter;
    private int page = 1;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_share_rankinglist;
    }

    @Override
    public void initView() {
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setHeaderHeight(60);
        refreshLayout.setOnRefreshListener(refreshLayout -> getJuniorUserMoneyList(page = 1));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> getJuniorUserMoneyList(page));

        mLvGroupPurchaseGoodsList = (ListView) mContentView.findViewById(R.id.lv_best_sellers);

        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, MyinvitelistViewHolder.class);

        mLvGroupPurchaseGoodsList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
//            startActivity(new Intent(getActivity(), GoodsInfoActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getJuniorUserMoneyList(page = 1);
    }

    private void getJuniorUserMoneyList(int now_page) {

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

        HttpAction.getInstance().getMyInvitationList(new GetMyInvitationListListResquest(YuGuoApplication.userInfo.getCustomerId(), now_page, ConstantData.PAGE_SIZE)).subscribe(new BaseObserver<>(mContext, (response -> {

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


            List<GetMyInvitationListResponse.DataBean.UserInviteYieldListBean> list = response.getData().getUserInviteYieldList();
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
        })));
    }


}
