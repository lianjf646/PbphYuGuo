package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.EventCoudanViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetActivityGoodsInfoListResquest;
import com.pbph.yuguo.response.AddShoppingCartResponse;
import com.pbph.yuguo.response.GetActivityGoodsInfoListResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by 连嘉凡 on 2018/8/10.
 */

public class EventCoudanActvity extends BaseActivity {

    private TextView tv_event_mianjian1;
    private TextView tv_event_mianjian2;

    private ListView mLvGroupPurchaseGoodsList;
    private DataAdapter adapter;


    int acticeId, storeId;


    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        acticeId = getIntent().getIntExtra("acticeId", 0);
        storeId = getIntent().getIntExtra("storeId", 0);

        setContentView(R.layout.activity_event_coudan);
        initTitle(TITLE_STYLE_WHITE, "促销清单", true, false);
        initView();

        getActivityGoodsInfoList();
    }

    private void initView() {

        tv_event_mianjian1 = findViewById(R.id.tv_event_mianjian1);
        tv_event_mianjian2 = findViewById(R.id.tv_event_mianjian2);

        mLvGroupPurchaseGoodsList = (ListView) findViewById(R.id.lv_best_sellers);
        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, EventCoudanViewHolder.class);
        adapter.onItemViewClickListener = (rid, holder, objs) -> {

            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }

            GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean vo = (GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean) holder.item;


            addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), vo.getStoreGoodsId(), storeId, 1, vo.getStoreGoodsInfoId(), acticeId, 0);

        };

        mLvGroupPurchaseGoodsList.setOnItemClickListener((AdapterView<?> parent, View view, int
                position, long id) -> {

            GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean vo = (GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean) adapter.getItem(position);
            startActivity(new Intent(mContext, GoodsInfoActivity.class)
                    .putExtra("storeGoodsId", vo.getStoreGoodsId())
                    .putExtra("storeGoodsInfoId", vo.getStoreGoodsInfoId())
            );
        });


        findViewById(R.id.button).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext, EventCoudanGiftActvity.class)
                        .putExtra("acticeId", acticeId)
                        .putExtra("storeId", storeId)
                );
            }
        });
    }

    private void getActivityGoodsInfoList() {

        GetActivityGoodsInfoListResquest resquest = new GetActivityGoodsInfoListResquest();
        resquest.acticeId = acticeId;
        resquest.acticeType = 0;
        resquest.isGift = 0;
        resquest.storeId = storeId;

        HttpAction.getInstance().getActivityGoodsInfoList(resquest).subscribe(new BaseObserver<>(mContext, (response -> {
            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetActivityGoodsInfoListResponse.DataBean data = response.getData();
            GetActivityGoodsInfoListResponse.DataBean.ActivityBean act = data.getActivity();

            setEventInfo(act.getIsActivityOk(), act.getPrice(), act.getSatisfactionMoney(), act.getStartTime(), act.getEndTime());

            adapter.setDatas(data.getGoodsInfoList());
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
        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId, storeGoodsId, storeId, goodsNum, storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            AddShoppingCartResponse.DataBean.ShoppingActivityCartBean act = response.getData().getShoppingActivityCart();

            setEventInfo(act.getIsActivityOk(), act.getFullXmoney(), act.getSatisfactionMoney(), null, null);

            Toast.makeText(mContext, "已加入购物车", Toast.LENGTH_SHORT).show();

        })));
    }

    private void setEventInfo(int isActivityOk, int price1, int price2, String startTime, String endTime) {


        String moneyAll = MoneyHelper.getInstance4Fen(price1).change2Yuan().getStringDelZero();
        if (isActivityOk == 0) {
            //未满足活动

            tv_event_mianjian1.setText("满");
            tv_event_mianjian1.append(moneyAll);
            tv_event_mianjian1.append("元，赠送超值商品，再购");
            tv_event_mianjian1.append(MoneyHelper.getInstance4Fen(price2).change2Yuan().getStringDelZero());
            tv_event_mianjian1.append("元即享");

        } else {
            //满足活动
            tv_event_mianjian1.setText("已满");
            tv_event_mianjian1.append(moneyAll);
            tv_event_mianjian1.append("元，可赠送超值商品");

        }
        if (startTime == null) return;

        tv_event_mianjian2.setText("活动时间：");
        tv_event_mianjian2.append(startTime);
        tv_event_mianjian2.append("至");
        tv_event_mianjian2.append(endTime);
    }

}
