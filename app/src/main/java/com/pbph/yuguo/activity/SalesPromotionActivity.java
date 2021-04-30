package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.SalesPromotionViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.adapter.recyclerview.DataAdapter;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetPinkageActivityGoodsResquest;
import com.pbph.yuguo.request.GetShoppingCartCountResquest;
import com.pbph.yuguo.response.GetPinkageActivityGoodsResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class SalesPromotionActivity extends BaseActivity {

    TextView tv_goods_info_shopping_trolley_count_self;

    LinearLayoutManager layoutManager;
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerView;
    DataAdapter adapter;


    private Integer pinkageActivityId;
    private Integer storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        pinkageActivityId = intent.getIntExtra("pinkageActivityId", -1);
        storeId = intent.getIntExtra("storeId", -1);

        setContentView(R.layout.activity_sales_promotion);
//        initTitle(TITLE_STYLE_WHITE, "促销清单", true, false);
        hideTitleView();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShoppingCartCount();
    }

    @Override
    public void onLeftClick() {
        finish();
    }


    private void initView() {

        findViewById(R.id.btn_left_self).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_right_self).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, ShoppingCarActivity.class));
            }
        });

        tv_goods_info_shopping_trolley_count_self = findViewById(R.id.tv_goods_info_shopping_trolley_count_self);

        smartRefreshLayout = findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
        smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setHeaderHeight(60);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        });

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        LineDividerDecoration decoration = new LineDividerDecoration(getContext());

//        //创建分割线对象，第一个参数为上下文，第二个参数为RecyclerView排列方向
//        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);

//为RecyclerView添加分割线
//        recyclerView.addItemDecoration(decoration);

//        recyclerView.addItemDecoration(Line);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new DataAdapter(mContext, recyclerView, SalesPromotionViewHolder.class);


        //item内控件点击
        adapter.onItemViewClickListener = (rid, holder, objs) -> {

            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }

            GetPinkageActivityGoodsResponse.DataBean.GoodsListBean vo = (GetPinkageActivityGoodsResponse.DataBean.GoodsListBean) holder.item;

//           门店商品下,有多个货品的标识(0:有一个货品;1:有多个货品)
            if (vo.getManyGoodsInfoFlag() == 0) {
//                int storeGoodsInfoId,
//                int activeId,
//                int activeType
                addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), vo.getStoreGoodsId(), vo.getStoreId(), 1, vo.getStoreGoodsInfoId(), 0, -1);
            } else {
                new SpecChoicePop(mContext, (specChoicePop) -> {
                    if (specChoicePop.type != SpecChoicePop.Type.ADDSHOP) return;
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), specChoicePop.storeGoodsId, specChoicePop.storeId, specChoicePop.goodsNum, specChoicePop.storeGoodsInfoId, 0, -1);
                }).showPop(vo.getStoreGoodsInfoId(), vo.getStoreGoodsId(), "", 0, recyclerView);
            }
        };
//        item点击
        adapter.onItemClickListener = (parent, viewHolder) -> {

            Object obj = viewHolder.item;
//            FruitGoodsViewHolder
            if (obj instanceof GetPinkageActivityGoodsResponse.DataBean.GoodsListBean) {
                GetPinkageActivityGoodsResponse.DataBean.GoodsListBean vo = (GetPinkageActivityGoodsResponse.DataBean.GoodsListBean) obj;
                startActivity(new Intent(mContext, GoodsInfoActivity.class)
                        .putExtra("storeGoodsId", vo.getStoreGoodsId())
                        .putExtra("storeGoodsInfoId", vo.getStoreGoodsInfoId())
                );
            }
        };

        recyclerView.setAdapter(adapter);

        getPinkageActivityGoods();
    }

    private void getPinkageActivityGoods() {
        showLoadingDialog();
        HttpAction.getInstance().getPinkageActivityGoods(new GetPinkageActivityGoodsResquest(pinkageActivityId, storeId))
                .subscribe(new BaseObserver<>(this, (response -> {
                    hideLoadingDialog();
                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        finish();
                        return;
                    }
                    adapter.setDatas(response.getData().getGoodsList());
                })));
    }


    private void addShoppingCart(int customerId,
                                 int storeGoodsId,
                                 int storeId,
                                 int goodsNum,
                                 int storeGoodsInfoId,
                                 int activeId,
                                 int activeType) {
        if (goodsNum <= 0) {
            showToast("暂无库存");
            return;
        }

        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId,
                storeGoodsId, storeId, goodsNum, storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(mContext,
                (response -> {

                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        return;
                    }
                    Toast.makeText(mContext,  "加入购物车成功！", Toast.LENGTH_SHORT).show();
                    getShoppingCartCount();

                })));
    }


    private void getShoppingCartCount() {
        if (!YuGuoApplication.isLogin()) {
            tv_goods_info_shopping_trolley_count_self.setVisibility(View.GONE);
            return;
        }
        HttpAction.getInstance().getShoppingCartCount(new GetShoppingCartCountResquest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                return;
            }
            tv_goods_info_shopping_trolley_count_self.setVisibility(View.VISIBLE);
            tv_goods_info_shopping_trolley_count_self.setText(String.valueOf(response.getData().getShoppingCartCount()));

        })));
    }
}
