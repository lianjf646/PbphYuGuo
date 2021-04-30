package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MyGoodsListViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.response.GoodsInfoBean;

import java.util.List;

public class MyGoodsListActivity extends BaseActivity {

    private ListView mLvGroupPurchaseGoodsList;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YuGuoApplication application = (YuGuoApplication) getApplication();
        List<GoodsInfoBean> storeGoodsListBeans = (List<GoodsInfoBean>) application.object;
        application.object = null;

        setContentView(R.layout.activity_mygoodslist);
        initTitle(TITLE_STYLE_WHITE, "商品清单", true, false);
        initView();

        adapter.setDatas(storeGoodsListBeans);
    }

    private void initView() {
        mLvGroupPurchaseGoodsList = (ListView) mContentView.findViewById(R.id.lv_best_sellers);
        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, MyGoodsListViewHolder.class);
    }


    @Override
    public void onLeftClick() {
        finish();
    }
}
