package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.EventCoudanGiftViewHolder;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.adapter.choicehelper.abslistview.MultipleChoiceHelper;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetActivityGoodsInfoListResquest;
import com.pbph.yuguo.request.SetActivityGiftCheckedStatusResquest;
import com.pbph.yuguo.response.GetActivityGoodsInfoListResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/10.
 */

public class EventCoudanGiftActvity extends BaseActivity {

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

        setContentView(R.layout.activity_event_coudan_gift);
        initTitle(TITLE_STYLE_WHITE, "赠送清单", true, false);
        initView();

        getActivityGoodsInfoList();
    }

    private void initView() {


        tv_event_mianjian1 = findViewById(R.id.tv_event_mianjian1);
        tv_event_mianjian2 = findViewById(R.id.tv_event_mianjian2);

        mLvGroupPurchaseGoodsList = (ListView) findViewById(R.id.lv_best_sellers);
        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, EventCoudanGiftViewHolder.class, MultipleChoiceHelper.class);
        adapter.onItemViewClickListener = (rid, holder, objs) -> {
            switch (rid) {
                case R.id.cb_shoppingcar: {
                    GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean vo = (GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean) holder.item;

                    setActivityGiftCheckedStatus(vo.getStoreGoodsInfoId(), adapter.choiceHelper.isChoiced(vo) ? 0 : 1);
                }
                break;
            }
        };

        mLvGroupPurchaseGoodsList.setOnItemClickListener((AdapterView<?> parent, View view, int
                position, long id) -> {

            GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean vo = (GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean) adapter.getItem(position);
            startActivity(new Intent(mContext, GoodsInfoActivity.class)
                    .putExtra("storeGoodsId", vo.getStoreGoodsId())
                    .putExtra("storeGoodsInfoId", vo.getStoreGoodsInfoId())
            );
        });

    }

    private void getActivityGoodsInfoList() {

        GetActivityGoodsInfoListResquest resquest = new GetActivityGoodsInfoListResquest();
        resquest.acticeId = acticeId;
        resquest.acticeType = 0;
        resquest.isGift = 1;
        resquest.storeId = storeId;

        HttpAction.getInstance().getActivityGoodsInfoList(resquest).subscribe(new BaseObserver<>(mContext, (response -> {
            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetActivityGoodsInfoListResponse.DataBean data = response.getData();

            GetActivityGoodsInfoListResponse.DataBean.ActivityBean act = data.getActivity();

            setEventInfo(act.getIsActivityOk(), act.getPrice(), act.getSatisfactionMoney(), act.getSelectedCount(), act.getCount());

            adapter.clearDatas();

            List<GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean> list = data.getGoodsInfoList();
            for (int i = 0; i < list.size(); i++) {
                GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean vo = list.get(i);
                adapter.addData(vo);

                if (vo.getIsChecked() == 1) adapter.choiceHelper.putChoice(i);
            }

        })));
    }

    private void setActivityGiftCheckedStatus(int id, int check) {

        SetActivityGiftCheckedStatusResquest resquest = new SetActivityGiftCheckedStatusResquest();
        resquest.acticeId = acticeId;
        resquest.storeId = storeId;
        resquest.goodsInfoId = id;
        resquest.checked = check;

        HttpAction.getInstance().setActivityGiftCheckedStatus(resquest).subscribe(new BaseObserver<>(mContext, (response -> {
            if (200 != response.getCode()) {
                showToast(response.getMsg());
            }
            getActivityGoodsInfoList();
        })));
    }


    private void setEventInfo(int isActivityOk, int price1, int price2, int selectedCount, int count) {


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


        tv_event_mianjian2.setText("已选择");
        tv_event_mianjian2.append(String.valueOf(selectedCount));
        tv_event_mianjian2.append("/");
        tv_event_mianjian2.append(String.valueOf(count));
    }
}
