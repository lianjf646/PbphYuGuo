package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.VipDayAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.Add2ShoppingCarMsg;
import com.pbph.yuguo.myview.HeaderGridView;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetMemberDayActivityGoodsRequest;
import com.pbph.yuguo.response.GetMemberDayActivityGoodsResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.RxBusF;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sobot.chat.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by V on 2018/12/19.
 */
public class VipDayActivity extends BaseActivity {

    private HeaderGridView gvMoreGoods;
    private VipDayAdapter vipSpecialFieldAdapter;

    private ImageButton ivbtnBack;
    private TextView tvNotGoods;
    private ImageView ivHeard;

    private int memberDayActivityId;
    private int storeId;
    private int pageNum = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private List<GetMemberDayActivityGoodsResponse.DataBean.ResultListBean> resultListBeanList = new ArrayList<>();

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_day);
        hideTitleView();
        initView();
        initData();

    }

    private void initView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_heard_vip_day, null);

        vipSpecialFieldAdapter = new VipDayAdapter((goodsListBean, pos) -> {

            //           门店商品下,有多个货品的标识(0:有一个货品;1:有多个货品)
            if (goodsListBean.getManyGoodsInfoFlag() == 0) {
                addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), goodsListBean.getStoreGoodsId(), goodsListBean
                        .getStoreId(), 1, goodsListBean.getStoreGoodsInfoId(), 0, -1);
            } else {
                new SpecChoicePop(mContext, (specChoicePop) -> {
                    if (specChoicePop.type != SpecChoicePop.Type.ADDSHOP) return;
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), specChoicePop.storeGoodsId, specChoicePop
                            .storeId, specChoicePop.goodsNum, specChoicePop.storeGoodsInfoId, 0, -1);
                }).showPop(goodsListBean.getStoreGoodsInfoId(), goodsListBean.getStoreGoodsId(), "", 0, view);
            }
        });

        ivHeard = view.findViewById(R.id.iv_heard);
        tvNotGoods = view.findViewById(R.id.tv_vip_not_goods);

        ivbtnBack = findViewById(R.id.ivbtn_back);
        ivbtnBack.setOnClickListener(v -> finish());

        gvMoreGoods = findViewById(R.id.gv_more_goods);
        gvMoreGoods.addHeaderView(view);
        gvMoreGoods.setAdapter(vipSpecialFieldAdapter);
        gvMoreGoods.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                int pos = position - 2;
//                GetMemberDayActivityGoodsResponse.DataBean.ResultListBean goodsListBean = resultListBeanList.get(pos);
                GetMemberDayActivityGoodsResponse.DataBean.ResultListBean goodsListBean = (GetMemberDayActivityGoodsResponse
                        .DataBean.ResultListBean) vipSpecialFieldAdapter.getItem(pos);
                ;

                Intent intent = new Intent(VipDayActivity.this, GoodsInfoActivity.class);
                intent.putExtra("storeGoodsId", goodsListBean.getStoreGoodsId());
                intent.putExtra("storeGoodsInfoId", goodsListBean.getStoreGoodsInfoId());
                startActivity(intent);

            }
        });

        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setHeaderHeight(60);
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getMemberDayActivityGoods();
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            getMemberDayActivityGoods();
        });
    }

    private void initData() {
        storeId = Integer.valueOf(getIntent().getStringExtra("storeId"));
        memberDayActivityId = getIntent().getIntExtra("memberDayActivityId", -1);
        getMemberDayActivityGoods();

    }

    /**
     * 添加购物车
     *
     * @param customerId
     * @param storeGoodsId
     * @param storeId
     * @param goodsNum
     * @param storeGoodsInfoId
     * @param activeId
     * @param activeType
     */
    private void addShoppingCart(int customerId, int storeGoodsId, int storeId, int goodsNum, int storeGoodsInfoId, int
            activeId, int activeType) {
        if (goodsNum <= 0) {
            Toast.makeText(VipDayActivity.this, "暂无库存", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId, storeGoodsId, storeId, goodsNum,
                storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(VipDayActivity.this, (response -> {

            if (200 != response.getCode()) {
                ToastUtil.showToast(VipDayActivity.this, response.getMsg());
                return;
            }

            Toast.makeText(VipDayActivity.this, "加入购物车成功！", Toast.LENGTH_SHORT).show();
            RxBusF.post0(new Add2ShoppingCarMsg());

        })));
    }

    /**
     *
     */
    private void getMemberDayActivityGoods() {
        HttpAction.getInstance().getMemberDayActivityGoods(new GetMemberDayActivityGoodsRequest(memberDayActivityId, storeId,
                pageNum, ConstantData.PAGE_SIZE)).subscribe(new BaseObserver<>(this, response -> {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
            GetMemberDayActivityGoodsResponse.DataBean data = response.getData();
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (pageNum == 1) {
                GlideUtil.displayImage(this, data.getMemberActivity().getActivityPrcture(), ivHeard);
                resultListBeanList = data.getResultList();
                if (resultListBeanList.isEmpty()) tvNotGoods.setVisibility(View.VISIBLE);
                vipSpecialFieldAdapter.setStringList(resultListBeanList);
            } else {
                resultListBeanList.addAll(data.getResultList());

            }

            vipSpecialFieldAdapter.notifyDataSetChanged();

            if (data.getResultList().size() < 20) {
                smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
            } else {
                smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
            }


        }, (code, message) -> {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        }));
    }
}
