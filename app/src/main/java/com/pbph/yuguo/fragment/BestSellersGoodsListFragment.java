package com.pbph.yuguo.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.GoodsInfoActivity;
import com.pbph.yuguo.activity.InviteActivity;
import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.activity.MyBrowsersActivity;
import com.pbph.yuguo.activity.MyOrderActivity;
import com.pbph.yuguo.activity.VipDayActivity;
import com.pbph.yuguo.activity.VipSpecialFieldActivity;
import com.pbph.yuguo.adapter.BestSellersGoodsViewHolder;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.Add2ShoppingCarMsg;
import com.pbph.yuguo.msg.LocationResultMsg;
import com.pbph.yuguo.msg.SwitchFragmentMsg;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetHomePageImgResquest;
import com.pbph.yuguo.request.GetHotSaleGoodsListResquest;
import com.pbph.yuguo.request.GetIndexRotationChartRequest;
import com.pbph.yuguo.response.GetHomePageImgResponse;
import com.pbph.yuguo.response.GetHotSaleGoodsListResponse;
import com.pbph.yuguo.response.GetIndexRotationChartResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.RxBusF;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;


public class BestSellersGoodsListFragment extends BaseFragment implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private ListView mLvGroupPurchaseGoodsList;
    private DataAdapter adapter;
    private List<GetIndexRotationChartResponse.DataBean.SlideDtoBean> bannerDatas = new ArrayList<>();
    private Banner banner;

    View include_index_nodata;
    private ImageButton ivbtn_back_top;

    private View view;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    private TextView tvVipField, tvOpenVip, tvMyOrder, tvInvitationFriend;

    private ImageView ivEntry, ivServer, ivBestSeller;

    private GetHomePageImgResponse.DataBean.DtoBean dtoBean;
    private String storeId;//店铺Id

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_best_sellers_list;
    }

    @Override
    public void initView() {
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);

        ivbtn_back_top = mContentView.findViewById(R.id.ivbtn_back_top);
        ivbtn_back_top.setOnClickListener(v -> {
            setListViewPos(0);
        });

        view = mContentView.findViewById(R.id.view);
        mLvGroupPurchaseGoodsList = (ListView) mContentView.findViewById(R.id.lv_best_sellers);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View headView = inflater.inflate(R.layout.layout_best_sellers_list_head, null);
        tvVipField = headView.findViewById(R.id.tv_vip_field);
        tvVipField.setOnClickListener(this);
        tvOpenVip = headView.findViewById(R.id.tv_open_vip);
        tvOpenVip.setOnClickListener(this);
        tvMyOrder = headView.findViewById(R.id.tv_my_order);
        tvMyOrder.setOnClickListener(this);
        tvInvitationFriend = headView.findViewById(R.id.tv_invitation_friend);
        tvInvitationFriend.setOnClickListener(this);
        banner = headView.findViewById(R.id.best_sellers_banner);
        int height = mContext.getScreenWidth() / 2;
        banner.getLayoutParams().height = height;
        banner.setImageLoader(new GlideImageLoader());
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImages(bannerDatas);
        banner.start();
        banner.setOnBannerListener((position) -> {
            if (bannerDatas.size() == 0) return;
            GetIndexRotationChartResponse.DataBean.SlideDtoBean slideDtoBean = bannerDatas.get(position);
            Intent intent = new Intent(mContext, MyBrowsersActivity.class);
            if (slideDtoBean.getSlideRedirect().isEmpty()) return;
            intent.putExtra("url", slideDtoBean.getSlideRedirect());
            intent.putExtra("title", slideDtoBean.getSlideName());
            startActivity(intent);
        });
        include_index_nodata = headView.findViewById(R.id.include_index_nodata);
        ivEntry = headView.findViewById(R.id.iv_entry);
        ivServer = headView.findViewById(R.id.iv_server);
        ivBestSeller = headView.findViewById(R.id.iv_best_seller);
        setImgWH(ivEntry, 15, 4);
        setImgWH(ivServer, 15, 2);
        setImgWH(ivBestSeller, 15, 4);
        include_index_nodata.setVisibility(View.VISIBLE);
        ivEntry.setOnClickListener(this);
        ivServer.setOnClickListener(this);
        mLvGroupPurchaseGoodsList.addHeaderView(headView);
        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, BestSellersGoodsViewHolder.class);
        adapter.onItemViewClickListener = (rid, holder, objs) -> {

            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                return;
            }
            GetHotSaleGoodsListResponse.DataBean.GoodsListBean vo = (GetHotSaleGoodsListResponse.DataBean.GoodsListBean) holder
                    .item;

//           门店商品下,有多个货品的标识(0:有一个货品;1:有多个货品)
            if (vo.getManyGoodsInfoFlag() == 0) {
                addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), vo.getStoreGoodsId(), vo.getStoreId(), 1, vo
                        .getStoreGoodsInfoId(), 0, -1);
            } else {
                new SpecChoicePop(mContext, (specChoicePop) -> {
                    if (specChoicePop.type != SpecChoicePop.Type.ADDSHOP) return;
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), specChoicePop.storeGoodsId, specChoicePop
                            .storeId, specChoicePop.goodsNum, specChoicePop.storeGoodsInfoId, 0, -1);
                }).showPop(vo.getStoreGoodsInfoId(), vo.getStoreGoodsId(), "", 0, view);
            }
        };

        mLvGroupPurchaseGoodsList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            GetHotSaleGoodsListResponse.DataBean.GoodsListBean vo = (GetHotSaleGoodsListResponse.DataBean.GoodsListBean)
                    adapter.getItem(position - 1);
            startActivity(new Intent(getActivity(), GoodsInfoActivity.class).putExtra("storeGoodsId", vo.getStoreGoodsId())
                    .putExtra("storeGoodsInfoId", vo.getStoreGoodsInfoId()));
        });
        mLvGroupPurchaseGoodsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag = false;
                        // 判断滚动到底部
                        if (mLvGroupPurchaseGoodsList.getLastVisiblePosition() == (mLvGroupPurchaseGoodsList.getCount() - 1)) {
                            ivbtn_back_top.setVisibility(View.VISIBLE);
                        }
                        // 判断滚动到顶部
                        if (mLvGroupPurchaseGoodsList.getFirstVisiblePosition() == 0) {
                            ivbtn_back_top.setVisibility(View.GONE);
                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag = true;

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://
                        // 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
                    ivbtn_back_top.setVisibility(View.VISIBLE);
                } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
                    ivbtn_back_top.setVisibility(View.VISIBLE);
                } else {
                    return;
                }
                lastVisibleItemPosition = firstVisibleItem;


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getIndexRotationChart();

        hasLocation();
    }

    @Override
    public void onStop() {
        RxBusF.removeDisposable0(BestSellersGoodsListFragment.this, LocationResultMsg.class);
        super.onStop();
    }


    private void hasLocation() {
        //默认收货地址
        if (YuGuoApplication.userInfo.getRecAddId() != null) {
            getHotSaleGoodsList(String.valueOf(UserInfo.getInstance().getRecLatitude()), String.valueOf(UserInfo.getInstance()
                    .getRecLongitude()));
            getHomePageImg(String.valueOf(UserInfo.getInstance().getRecLatitude()), String.valueOf(UserInfo.getInstance()
                    .getRecLongitude()));
            return;
        }
//        定位地址
        if (YuGuoApplication.userInfo.getStreet() != null) {
            getHotSaleGoodsList(String.valueOf(UserInfo.getInstance().getLatitude()), String.valueOf(UserInfo.getInstance()
                    .getLongitude()));
            getHomePageImg(String.valueOf(UserInfo.getInstance().getLatitude()), String.valueOf(UserInfo.getInstance()
                    .getLongitude()));
            return;
        }
        RxBusF.register0(BestSellersGoodsListFragment.this, LocationResultMsg.class, locationMsg -> {
            getHotSaleGoodsList(locationMsg.latitude, locationMsg.longitude);
            getHomePageImg(locationMsg.latitude, locationMsg.longitude);
        });
    }


    private void getHotSaleGoodsList(String receiverLat, String receiverLng) {

        HttpAction.getInstance().getHotSaleGoodsList(new GetHotSaleGoodsListResquest(receiverLat, receiverLng)).subscribe(new
                BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            List<GetHotSaleGoodsListResponse.DataBean.GoodsListBean> list = response.getData().getGoodsList();

            if (list == null || list.isEmpty()) {
                include_index_nodata.setVisibility(View.VISIBLE);
            } else {
                include_index_nodata.setVisibility(View.GONE);
            }
            adapter.setDatas(list);
        })));
    }

    private void getIndexRotationChart() {
        HttpAction.getInstance().getIndexRotationChart(new GetIndexRotationChartRequest()).subscribe(new BaseObserver<>
                (mContext, (response -> {
            if (200 != response.getCode()) {
                return;
            }
            bannerDatas = response.getData().getSlideDto();
            ArrayList<String> list = new ArrayList<>(bannerDatas.size());

            if (bannerDatas != null) for (GetIndexRotationChartResponse.DataBean.SlideDtoBean vo : bannerDatas) {
                list.add(vo.getSlideUrl());
            }
            banner.update(list);

        })));
    }

    /**
     * 首页图片接口（广告位/今日特卖图/服务支持图片）
     */

    private void getHomePageImg(String la, String lo) {
        HttpAction.getInstance().getHomePageImg(new GetHomePageImgResquest(la, lo)).subscribe(new BaseObserver<>(getContext(),
                response -> {
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            dtoBean = response.getData().getDto();
            storeId = response.getData().getStoreId();
            if (dtoBean.getAdvertisingPositionIsShow() == 0) {
                ivEntry.setVisibility(View.GONE);
            } else {
                ivEntry.setVisibility(View.VISIBLE);
                GlideUtil.displayImage(mContext, dtoBean.getAdvertisingPositionImg(), ivEntry);
            }
            if (dtoBean.getServiceIsShow() == 0) {
                ivServer.setVisibility(View.GONE);
            } else {
                ivServer.setVisibility(View.VISIBLE);
                GlideUtil.displayImage(mContext, dtoBean.getServiceImg(), ivServer);
            }


        }, (code, message) -> {

        }));

    }

    private void addShoppingCart(int customerId, int storeGoodsId, int storeId, int goodsNum, int storeGoodsInfoId, int
            activeId, int activeType) {

        if (goodsNum <= 0) {
            showToast("暂无库存");
            return;
        }

        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId, storeGoodsId, storeId, goodsNum,
                storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            Toast.makeText(getContext(), "加入购物车成功！", Toast.LENGTH_SHORT).show();

            RxBusF.post0(new Add2ShoppingCarMsg());

        })));
    }

    /**
     * 滚动ListView到指定位置
     *
     * @param pos
     */
    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            mLvGroupPurchaseGoodsList.smoothScrollToPosition(pos);
        } else {
            mLvGroupPurchaseGoodsList.setSelection(pos);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_vip_field: {
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                if (UserInfo.getInstance().getLatitude() == null && UserInfo.getInstance().getRecLatitude() == null) return;
                startActivity(new Intent(mContext, VipSpecialFieldActivity.class));
            }
            break;
            case R.id.tv_open_vip: {
                RxBusF.post0(new SwitchFragmentMsg(true));
            }
            break;
            case R.id.tv_my_order: {
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, MyOrderActivity.class));
            }
            break;
            case R.id.tv_invitation_friend: {
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, InviteActivity.class));
            }
            break;
            case R.id.iv_entry: {
                if (dtoBean == null) return;
                if (dtoBean.getType() == 0) {
                    if (storeId == null) return;
                    Intent intent = new Intent(mContext, VipDayActivity.class);
                    intent.putExtra("storeId", storeId);
                    intent.putExtra("memberDayActivityId", dtoBean.getMemberDayActivityId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, MyBrowsersActivity.class);
                    intent.putExtra("url", dtoBean.getAdvertisingPositionUrl());
                    intent.putExtra("title", dtoBean.getAdvertisingPositionTitle());
                    startActivity(intent);
                }
            }
            break;
            case R.id.iv_server: {
                if (dtoBean == null) return;
                Intent intent = new Intent(mContext, MyBrowsersActivity.class);
                intent.putExtra("url", dtoBean.getServiceUrl());
                intent.putExtra("title", dtoBean.getServiceTitle());
                startActivity(intent);
            }
            break;
            default: {

            }
            break;
        }
    }


    /**
     * 设置图片宽高
     */
    private void setImgWH(ImageView iv, int w, int h) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm == null) return;
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
        params.width = width;
        params.height = width / w * h;
        iv.setLayoutParams(params);
    }


}
