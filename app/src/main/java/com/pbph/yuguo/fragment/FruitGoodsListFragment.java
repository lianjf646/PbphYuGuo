package com.pbph.yuguo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.GoodsInfoActivity;
import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.activity.MainTabActivity;
import com.pbph.yuguo.adapter.fruitgoodsadapter.FruitAddViewHolder;
import com.pbph.yuguo.adapter.fruitgoodsadapter.FruitFirstViewHolder;
import com.pbph.yuguo.adapter.fruitgoodsadapter.FruitGoodsViewHolder;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.Add2ShoppingCarMsg;
import com.pbph.yuguo.msg.LocationResultMsg;
import com.pbph.yuguo.myview.adapter.recyclerview.DataAdapter;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetGoodsListResquest;
import com.pbph.yuguo.response.GetGoodsListResponse;
import com.pbph.yuguo.util.RxBusF;
import com.pbph.yuguo.util.quxian.AniManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

///记住 这里的 radtionbutton 的 id  是1000+0,1,2.。。等等。所以，在部分用到的地方 有+1000 也有-1000 请别弄错了。
public class FruitGoodsListFragment extends BaseFragment {

    LayoutInflater inflater;


    private HorizontalScrollView horizontalScrollView;
    private RadioGroup radioGroup;


    LinearLayoutManager layoutManager;
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerView;
    DataAdapter adapter;


    View include_nodata;

    boolean recyclerViewTouch = false;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_fruit_list;
    }

    @Override
    public void initView() {

        include_nodata = mContentView.findViewById(R.id.include_nodata);
        include_nodata.setVisibility(View.GONE);

        horizontalScrollView = mContentView.findViewById(R.id.horizontalScrollView1);

        radioGroup = mContentView.findViewById(R.id.radioGroup1);

        // 设置了滚动条，如果选中的东西不在滚动条中部，而又可以滚动到中部，则将控件移动到中部。言之无物，看效果就知道了
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton rb = (RadioButton) radioGroup.getChildAt(checkedId - 1000);
            if (rb == null) return;
            int left = rb.getLeft() + rb.getWidth() / 2;

            int half = 0;
            try {
                half = horizontalScrollView.getWidth() >> 1;
            } catch (Exception e) {
            }
            if (half <= 0) {
                return;
            }

            horizontalScrollView.smoothScrollBy((left - horizontalScrollView.getScrollX() - half), 0);

        });

        smartRefreshLayout = mContentView.findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setHeaderHeight(60);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {

            //默认收货地址
            if (YuGuoApplication.userInfo.getRecAddId() != null) {
                getGoodsList(String.valueOf(UserInfo.getInstance().getRecLatitude()), String.valueOf(UserInfo.getInstance()
                        .getRecLongitude()));

            }
//        定位地址
            else if (YuGuoApplication.userInfo.getStreet() != null) {
                getGoodsList(String.valueOf(UserInfo.getInstance().getLatitude()), String.valueOf(UserInfo.getInstance()
                        .getLongitude()));

            } else {
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
            }

        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        });

        recyclerView = mContentView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        LineDividerDecoration decoration = new LineDividerDecoration(getContext());

//        //创建分割线对象，第一个参数为上下文，第二个参数为RecyclerView排列方向
//        DividerItemDecoration decoration = new DividerItemDecoration(mContext,
// DividerItemDecoration.VERTICAL);

//为RecyclerView添加分割线
//        recyclerView.addItemDecoration(decoration);

//        recyclerView.addItemDecoration(Line);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new DataAdapter(getContext(), recyclerView, 3);

        int height = mContext.getScreenWidth() / 3;
        adapter.bundle.putInt("add_height", height);
        //item内控件点击
        adapter.onItemViewClickListener = (rid, holder, objs) -> {

            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                return;
            }

            GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean vo = (GetGoodsListResponse.DataBean
                    .GoodsCategoryListBean.CategoryGoodsListBean) holder.item;

//           门店商品下,有多个货品的标识(0:有一个货品;1:有多个货品)
            if (vo.getManyGoodsInfoFlag() == 0) {
//                int storeGoodsInfoId,
//                int activeId,
//                int activeType
               addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), vo.getStoreGoodsId(), vo.getStoreId(), 1, vo
                        .getStoreGoodsInfoId(), 0, -1);

//                startAnim(((FruitGoodsViewHolder)holder).ivAdapterGoodsListShoppingCart);
            } else {
                new SpecChoicePop(mContext, (specChoicePop) -> {
                    if (specChoicePop.type != SpecChoicePop.Type.ADDSHOP) return;
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), specChoicePop.storeGoodsId, specChoicePop
                            .storeId, specChoicePop.goodsNum, specChoicePop.storeGoodsInfoId, 0, -1);
                }).showPop(vo.getStoreGoodsInfoId(), vo.getStoreGoodsId(), "", 0, recyclerView);
            }
        };
//        item点击
        adapter.onItemClickListener = (parent, viewHolder) -> {

            Object obj = viewHolder.item;
//            FruitGoodsViewHolder
            if (obj instanceof GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean) {
                GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean vo = (GetGoodsListResponse.DataBean
                        .GoodsCategoryListBean.CategoryGoodsListBean) obj;
                startActivity(new Intent(getActivity(), GoodsInfoActivity.class).putExtra("storeGoodsId", vo.getStoreGoodsId())
                        .putExtra("storeGoodsInfoId", vo.getStoreGoodsInfoId()));

            }
        };

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter.getItemCount() <= 0) return;
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    recyclerViewTouch = true;
                }

                if (RecyclerView.SCROLL_STATE_IDLE != newState) return;
                if (!recyclerViewTouch) return;


                recyclerViewTouch = false;
                int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (firstItemPosition <= 0) return;
                Class clz = adapter.getItemViewTypeClass(firstItemPosition);
                if (clz == FruitFirstViewHolder.class) {
                    int id = 1000;
                    radioGroup.check(id);
                } else if (clz == FruitAddViewHolder.class) {

                    GetGoodsListResponse.DataBean.GoodsCategoryListBean addVo = (GetGoodsListResponse.DataBean
                            .GoodsCategoryListBean) adapter.getItem(firstItemPosition);
                    radioGroup.check(1000 + addVo.headId);
                } else if (clz == FruitGoodsViewHolder.class) {

                    GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean vo = (GetGoodsListResponse
                            .DataBean.GoodsCategoryListBean.CategoryGoodsListBean) adapter.getItem(firstItemPosition);
                    radioGroup.check(1000 + vo.headId);
                }
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        smartRefreshLayout.setVisibility(View.GONE);
        include_nodata.setVisibility(View.VISIBLE);
        hasLocation();
    }

    @Override
    public void onStop() {
        RxBusF.removeDisposable0(FruitGoodsListFragment.this, LocationResultMsg.class);
        super.onStop();
    }


    @Override
    public void onDestroy() {
//        if (null != banner) {
//            banner.releaseBanner();
//        }
        super.onDestroy();
    }

    private void addRadioGroup(List<GetGoodsListResponse.DataBean.GoodsCategoryListBean> list) {

        inflater = LayoutInflater.from(getContext());


        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setVisibility(View.GONE);
        }

//        if (radioGroup.getChildCount() > 0) {
//            RadioButton rb = (RadioButton) radioGroup.getChildAt(0);
//            rb.setVisibility(View.VISIBLE);
//
//            RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) rb.getLayoutParams();
//            params.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp_7dp);
//            rb.setLayoutParams(params);
//            rb.setText("热卖");
//            rb.setTag("");
////            rb.setOnCheckedChangeListener(onCheckedChangeListener);
//            rb.setOnTouchListener(touchListener);
//
//        } else {
//            addRadioButton(0, null);
//        }

        int count = list.size();
        for (int i = 0; i < count; i++) {
            int id = i + 1;
            if (i < radioGroup.getChildCount()) {
                RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
//                rb.setId(1000 + id);
                rb.setVisibility(View.VISIBLE);

                GetGoodsListResponse.DataBean.GoodsCategoryListBean vo = list.get(i);

                RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) rb.getLayoutParams();
                params.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp_7dp);
                rb.setLayoutParams(params);
                rb.setText(vo.getCateName());
                rb.setTag(vo);
//                rb.setOnCheckedChangeListener(onCheckedChangeListener);
                rb.setOnTouchListener(touchListener);

            } else {
                addRadioButton(id, list.get(i));
            }
        }

        if (radioGroup.getChildCount() <= 0) return;

        RadioButton rb = (RadioButton) radioGroup.getChildAt(0);
        RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) rb.getLayoutParams();
        params.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp_10dp);
        rb.setLayoutParams(params);

        rb = (RadioButton) radioGroup.getChildAt(radioGroup.getChildCount() - 1);
        params = (RadioGroup.LayoutParams) rb.getLayoutParams();
        params.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp_10dp);
        rb.setLayoutParams(params);
    }


    private void addRadioButton(int i, GetGoodsListResponse.DataBean.GoodsCategoryListBean vo) {
        RadioButton rb = (RadioButton) inflater.inflate(R.layout.radiobutton_fruit, null);
        rb.setId(1000 + i);

        radioGroup.addView(rb);

        RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) rb.getLayoutParams();
        params.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp_7dp);
        rb.setLayoutParams(params);
        if (vo == null) {
            rb.setText("热卖");
            rb.setTag("");
        } else {
            rb.setText(vo.getCateName());
            rb.setTag(vo);
        }

//        rb.setOnCheckedChangeListener(onCheckedChangeListener);
        rb.setOnTouchListener(touchListener);
    }

    private static final int MIN_CLICK_DELAY_TIME = 150;

    private long lastClickTime = 0;
    //    boolean scroll = false;
    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener touchListener = (v, event) -> {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) return true;
            lastClickTime = currentTime;

            radioGroup.check(v.getId());

//            if (scroll) {
//                scroll = false;
//                return true;
//            }
            Object obj = v.getTag();

            if (obj instanceof String) {
//            recyclerView.smoothScrollToPosition(0);
                move2position(0);
                return true;
            }

            GetGoodsListResponse.DataBean.GoodsCategoryListBean vo = (GetGoodsListResponse.DataBean.GoodsCategoryListBean) obj;

            for (int i = 0; i < adapter.getItemCount(); i++) {

                Object temp = adapter.getItem(i);

                if (!(temp instanceof GetGoodsListResponse.DataBean.GoodsCategoryListBean))
                    continue;

                GetGoodsListResponse.DataBean.GoodsCategoryListBean tVo = (GetGoodsListResponse.DataBean.GoodsCategoryListBean)
                        temp;

                if (vo.getGoodsCateId() != tVo.getGoodsCateId()) continue;

//            recyclerView.smoothScrollToPosition(i);
                move2position(i);
                break;
            }
        }
        return true;
    };

//    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView,
// isChecked) -> {
//        if (!isChecked) return;
//
////        if (scroll) {
////            scroll = false;
////            return;
////        }
////        int pos = buttonView.getId();
//        Object obj = buttonView.getTag();
//
//        if (obj instanceof String) {
////            recyclerView.smoothScrollToPosition(0);
//            move2position(0);
//            return;
//        }
//
//        GetGoodsListResponse.DataBean.CategoryContentBean.GoodsCategoryListBean vo =
// (GetGoodsListResponse.DataBean.CategoryContentBean.GoodsCategoryListBean) obj;
//
//        for (int i = 0; i < adapter.getItemCount(); i++) {
//
//            Object temp = adapter.getItem(i);
//
//            if (!(temp instanceof GetGoodsListResponse.DataBean.CategoryContentBean
// .GoodsCategoryListBean))
//                continue;
//
//            GetGoodsListResponse.DataBean.CategoryContentBean.GoodsCategoryListBean tVo =
// (GetGoodsListResponse.DataBean.CategoryContentBean.GoodsCategoryListBean) temp;
//
//            if (vo.getGoodsCateId() != tVo.getGoodsCateId()) continue;
//
////            recyclerView.smoothScrollToPosition(i);
//            move2position(i);
//            break;
//        }
//    };

    void move2position(int position) {
//        if (position != -1) {
//            recyclerView.scrollToPosition(position);
//            layoutManager.scrollToPositionWithOffset(position, 0);
//        }
        smoothMoveToPosition(recyclerView, position);
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    private void hasLocation() {
        //默认收货地址
        if (YuGuoApplication.userInfo.getRecAddId() != null) {
            getGoodsList(String.valueOf(UserInfo.getInstance().getRecLatitude()), String.valueOf(UserInfo.getInstance()
                    .getRecLongitude()));
            return;
        }
//        定位地址
        if (YuGuoApplication.userInfo.getStreet() != null) {
            getGoodsList(String.valueOf(UserInfo.getInstance().getLatitude()), String.valueOf(UserInfo.getInstance()
                    .getLongitude()));
            return;
        }
        RxBusF.register0(FruitGoodsListFragment.this, LocationResultMsg.class, locationMsg -> {
            getGoodsList(locationMsg.latitude, locationMsg.longitude);
        });
    }


    private void getGoodsList(String receiverLat, String receiverLng) {
        HttpAction.getInstance().getGoodsList(new GetGoodsListResquest(receiverLat, receiverLng)).subscribe(new BaseObserver<>
                (mContext, (response -> {

                    smartRefreshLayout.finishRefresh();
                    smartRefreshLayout.finishLoadMore();

                    if (200 != response.getCode()) {
                        include_nodata.setVisibility(View.VISIBLE);
                        smartRefreshLayout.setVisibility(View.GONE);
                        return;
                    }
                    List<GetGoodsListResponse.DataBean.GoodsCategoryListBean> fstList = response.getData().getGoodsCategoryList();

                    if (fstList == null || fstList.size() <= 0) {
                        include_nodata.setVisibility(View.VISIBLE);
                        smartRefreshLayout.setVisibility(View.GONE);
                    } else {
                        include_nodata.setVisibility(View.GONE);
                        smartRefreshLayout.setVisibility(View.VISIBLE);
                    }

                    addRadioGroup(fstList);

                    adapter.clearDatas();

                    int i = 1;
                    for (GetGoodsListResponse.DataBean.GoodsCategoryListBean addVo : fstList) {

                        addVo.headId = i;
                        adapter.addData(addVo, FruitAddViewHolder.class);

                        List<GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean> temps = addVo
                                .getCategoryGoodsList();

                        for (GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean vo : temps) {
                            vo.headId = i;


                            adapter.addData(vo, FruitGoodsViewHolder.class);
                        }
                        i++;
                    }

                    adapter.notifyDataSetChanged();

                    int id = 1001;
                    radioGroup.check(id);

                })));
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

    private AniManager mAniManager;
    //启动动画
    public void startAnim(View v) {
        mAniManager = new AniManager();
//        int[] end_location = new int[]{(int) (ScreenUtil.getScreenWidth(getContext())/4*3f),ScreenUtil.getScreenHeight(getContext())};
        int[] end_location = new int[2];
        int[] start_location = new int[2];
        v.getLocationInWindow(start_location);// 获取购买按钮的在屏幕的X、Y坐标（动画开始的坐标）
        ((MainTabActivity)getActivity()).tvPointNum.getLocationInWindow(end_location);
//        car.getLocationInWindow(end_location);// 这是用来存储动画结束位置，也就是购物车图标的X、Y坐标

        ImageView  buyImg = new ImageView(getContext());// buyImg是动画的图片
        buyImg.setImageResource(R.drawable.buy);// 设置buyImg的图片

        //        mAniManager.setTime(5500);//自定义时间
        mAniManager.setAnim(getActivity(), buyImg, start_location, end_location);// 开始执行动画

        mAniManager.setOnAnimListener(new AniManager.AnimListener() {
            @Override
            public void setAnimBegin(AniManager a) {
                //动画开始时的监听
            }

            @Override
            public void setAnimEnd(AniManager a) {
                //动画结束后的监听
//                num += 5;
//                buyNumView.setText(num + "");
//                buyNumView.show();
            }
        });
    }
}
