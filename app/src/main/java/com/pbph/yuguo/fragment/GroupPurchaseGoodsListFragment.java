package com.pbph.yuguo.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.GroupPurchaseGoodsInfoActivity;
import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.adapter.GroupPurchaseGoodsViewHolder;
import com.pbph.yuguo.adapter.TimeChangeDataAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetGoodsGroupActivityListResquest;
import com.pbph.yuguo.request.GetMyGoodsGroupActivityListResquest;
import com.pbph.yuguo.response.GroupListResponse;
import com.pbph.yuguo.util.SecTimer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;


public class GroupPurchaseGoodsListFragment extends BaseFragment {


    private SmartRefreshLayout refreshLayout;
    private ListView mLvGroupPurchaseGoodsList;
    private TimeChangeDataAdapter adapter;

    View layout_no_list;

    int page = 1;

    SecTimer secTimer;

    RadioButton button1;
    RadioButton button2;

    View shop_logout;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_group_purchase_list;
    }

    @Override
    public void initView() {

        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setHeaderHeight(60);
        refreshLayout.setOnRefreshListener(refreshLayout -> getList(page = 1));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> getList(page));


        shop_logout = mContentView.findViewById(R.id.shop_logout);
        shop_logout.setVisibility(View.GONE);

        mContentView.findViewById(R.id.btn_shop_login).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        layout_no_list = mContentView.findViewById(R.id.layout_no_list);
        layout_no_list.setVisibility(View.GONE);

        mLvGroupPurchaseGoodsList = (ListView) mContentView.findViewById(R.id.lv_group_purchase_goods_list);
        adapter = new TimeChangeDataAdapter(mContext, mLvGroupPurchaseGoodsList, GroupPurchaseGoodsViewHolder.class);
        int height = mContext.getScreenWidth() / 2;
        height -= getResources().getDimensionPixelSize(R.dimen.dp_10dp);
        adapter.bundle.putInt("img_height", height);
        mLvGroupPurchaseGoodsList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            GroupListResponse.DataBean.ListBean vo = (GroupListResponse.DataBean.ListBean) adapter.getItem(position);
            startActivity(new Intent(getActivity(), GroupPurchaseGoodsInfoActivity.class)
                    .putExtra("goodsGroupActivityId", vo.getGoodsGroupActivityId())
                    .putExtra("type", type)
            );
        });
        adapter.onItemViewClickListener = (rid, holder, objs) -> {

        };


        button1 = mContentView.findViewById(R.id.button1);
        button2 = mContentView.findViewById(R.id.button2);
        type = 1;
        button1.setChecked(true);
        adapter.clearDatas();
        button1.setOnCheckedChangeListener(onCheckedChangeListener);
        button2.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    int type = 1;

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
        if (!isChecked) return;
        switch (buttonView.getId()) {
            case R.id.button1: {
                type = 1;
            }
            break;
            case R.id.button2: {
                type = 2;
            }
            break;
        }

        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();

        adapter.clearDatas();

        getList(page = 1);
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

//        button1.setOnCheckedChangeListener(null);
//        button2.setOnCheckedChangeListener(null);
//        type = 1;
//        button1.setChecked(true);
//        adapter.clearDatas();
//        button1.setOnCheckedChangeListener(onCheckedChangeListener);
//        button2.setOnCheckedChangeListener(onCheckedChangeListener);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.clearDatas();
        getList(page = 1);
    }

    @Override
    public void onStop() {
        if (secTimer != null) {
            secTimer.cancel();
        }
        super.onStop();
    }

    private void getList(int now_page) {
        if (!isNetworkConnected(mContext)) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            layout_no_list.setVisibility(View.VISIBLE);
            shop_logout.setVisibility(View.GONE);
            return;
        }
        layout_no_list.setVisibility(View.GONE);
        shop_logout.setVisibility(View.GONE);


        if (type == 1) {
            getGoodsGroupActivityList(now_page);
        } else {
            getMyGoodsGroupActivityList(now_page);
        }
    }

    private void getGoodsGroupActivityList(int now_page) {

        if (type != 1) {
            return;
        }

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

        HttpAction.getInstance().getGoodsGroupActivityList(new GetGoodsGroupActivityListResquest(now_page, ConstantData.PAGE_SIZE)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (type != 1) {
                return;
            }

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

            List<GroupListResponse.DataBean.ListBean> list = response.getData().getList();
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

            for (GroupListResponse.DataBean.ListBean vo : list) {
                vo.type = 1;
                adapter.addData(vo);
            }
            adapter.notifyDataSetChanged();

            if (adapter.getCount() <= 0) layout_no_list.setVisibility(View.VISIBLE);

            if (adapter.getCount() <= 0) {
                layout_no_list.setVisibility(View.VISIBLE);
            } else {
                layout_no_list.setVisibility(View.GONE);
            }
            if (secTimer != null) secTimer.cancel();
            secTimer = new SecTimer() {
                @Override
                public void onTick(long passTime) throws Exception {
                    for (int i = 0, c = adapter.interfaceList.size(); i < c; i++) {
                        adapter.interfaceList.get(i).onTimeChange(passTime);
                    }
                }
            };
            secTimer.start();
        })));
    }


    private void getMyGoodsGroupActivityList(int now_page) {
        if (type != 2) {
            return;
        }

        if (!YuGuoApplication.isLogin()) {
            layout_no_list.setVisibility(View.GONE);
            shop_logout.setVisibility(View.VISIBLE);
            return;
        }
        shop_logout.setVisibility(View.GONE);


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

        HttpAction.getInstance().getMyGoodsGroupActivityList(new GetMyGoodsGroupActivityListResquest(YuGuoApplication.userInfo.getCustomerId(), now_page, ConstantData.PAGE_SIZE)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (type != 2) {
                return;
            }
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

            List<GroupListResponse.DataBean.ListBean> list = response.getData().getList();
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

            for (GroupListResponse.DataBean.ListBean vo : list) {
                vo.type = 2;
                adapter.addData(vo);
            }
            adapter.notifyDataSetChanged();


            if (adapter.getCount() <= 0) {
                layout_no_list.setVisibility(View.VISIBLE);
            } else {
                layout_no_list.setVisibility(View.GONE);
            }

            if (secTimer != null) secTimer.cancel();
            secTimer = new SecTimer() {
                @Override
                public void onTick(long passTime) throws Exception {
                    for (int i = 0, c = adapter.interfaceList.size(); i < c; i++) {
                        adapter.interfaceList.get(i).onTimeChange(passTime);
                    }
                }
            };
            secTimer.start();
        })));
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


}
