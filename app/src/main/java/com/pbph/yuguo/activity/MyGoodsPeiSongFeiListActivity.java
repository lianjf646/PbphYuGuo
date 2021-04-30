package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MyGoodsPeiSongFeiListViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.util.MoneyHelper;

import java.math.BigDecimal;
import java.util.List;

public class MyGoodsPeiSongFeiListActivity extends BaseActivity {

    private ListView mLvGroupPurchaseGoodsList;
    private DataAdapter adapter;

    TextView tv_heji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YuGuoApplication application = (YuGuoApplication) getApplication();
        List<GoodsInfoBean> storeGoodsListBeans = (List<GoodsInfoBean>) application.object;
        application.object = null;

        setContentView(R.layout.activity_mygoodspeisongfeilist);
        initTitle(TITLE_STYLE_WHITE, "商品包装费清单", true, false);
        initView();

        adapter.setDatas(storeGoodsListBeans);

        BigDecimal all = new BigDecimal(0);
        for (GoodsInfoBean vo : storeGoodsListBeans) {
            BigDecimal p1 = new BigDecimal(vo.getGoodsInfoPackCharges());
            BigDecimal num = new BigDecimal(vo.getGoodsInfoNum());

            all = p1.multiply(num).add(all);
        }

        tv_heji.setText("￥");
        tv_heji.append(MoneyHelper.getInstance4Fen(all).change2Yuan().getString());
    }

    private void initView() {
        mLvGroupPurchaseGoodsList = (ListView) mContentView.findViewById(R.id.lv_best_sellers);
        adapter = new DataAdapter(mContext, mLvGroupPurchaseGoodsList, MyGoodsPeiSongFeiListViewHolder.class);

        tv_heji = findViewById(R.id.tv_heji);
    }


    @Override
    public void onLeftClick() {
        finish();
    }
}
