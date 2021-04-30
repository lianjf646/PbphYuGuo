package com.pbph.yuguo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.SearchAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.FlowLayout;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetGoodsListResquest;
import com.pbph.yuguo.response.GetGoodsListResponse;
import com.pbph.yuguo.sq.RecordsDao;
import com.pbph.yuguo.util.YesNoDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 挡风的纱窗 on 2018/12/20.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    FlowLayout flowLayout;
    LayoutInflater inflater;
    ImageButton ibtn_del_recent;
    SmartRefreshLayout smartRefreshLayout;
    ListView lvSearch;
    SearchAdapter searchAdapter;
    View include_recent;
    RecordsDao recordsDao;
    List<String> stringList;
    List<GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean> categoryGoodsListBeanList;
    EditText edtSearch;
    ImageButton ibtnLeft, ivbtnCleanText;
    SpecChoicePop specChoicePop;
    Button btnSearch;
    TextView tvNotGoods;
    List<GetGoodsListResponse.DataBean.GoodsCategoryListBean> list;

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        hideTitleView();
        initView();
        initData();
        if (stringList.size() != 0) {
            setDatas4FlowLayout(stringList);
        }

    }


    private void initView() {
        flowLayout = findViewById(R.id.flowLayout);
        ibtn_del_recent = findViewById(R.id.ibtn_del_recent);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        ivbtnCleanText = findViewById(R.id.ivbtn_clean_text);
        lvSearch = findViewById(R.id.lv_search);
        include_recent = findViewById(R.id.include_recent);
        edtSearch = findViewById(R.id.edt_search);
        ibtnLeft = findViewById(R.id.ibtn_left);
        btnSearch = findViewById(R.id.btn_search);
        tvNotGoods = findViewById(R.id.tv_not_goods);
        btnSearch.setOnClickListener(this);
        ibtnLeft.setOnClickListener(this);
        ibtn_del_recent.setOnClickListener(this);
        ivbtnCleanText.setOnClickListener(this);
        lvSearch.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {

                if (searchAdapter == null) return;
                GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean bean = (GetGoodsListResponse.DataBean
                        .GoodsCategoryListBean.CategoryGoodsListBean) searchAdapter.getItem(position);
                if (bean == null) return;
                startActivity(new Intent(SearchActivity.this, GoodsInfoActivity.class).putExtra("storeGoodsId", bean
                        .getStoreGoodsId()).putExtra("storeGoodsInfoId", bean.getStoreGoodsInfoId()));
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    ivbtnCleanText.setVisibility(View.GONE);
                    btnSearch.setTextColor(Color.parseColor("#999999"));
                } else {
                    ivbtnCleanText.setVisibility(View.VISIBLE);
                    btnSearch.setTextColor(Color.parseColor("#23ce7f"));
                }
            }
        });
    }


    private void initData() {
        categoryGoodsListBeanList = new ArrayList<>();
        inflater = LayoutInflater.from(this);
        specChoicePop = new SpecChoicePop(this, (specChoicePop) -> {

        });
        searchAdapter = new SearchAdapter(pos -> {
            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean vo = (GetGoodsListResponse.DataBean
                    .GoodsCategoryListBean.CategoryGoodsListBean) searchAdapter.getItem(pos - 1);

//           门店商品下,有多个货品的标识(0:有一个货品;1:有多个货品)
            if (vo.getManyGoodsInfoFlag() == 0) {
                addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), vo.getStoreGoodsId(), vo.getStoreId(), 1, vo
                        .getStoreGoodsInfoId(), 0, -1);
            } else {
                new SpecChoicePop(mContext, (specChoicePop) -> {
                    if (specChoicePop.type != SpecChoicePop.Type.ADDSHOP) return;
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), specChoicePop.storeGoodsId, specChoicePop
                            .storeId, specChoicePop.goodsNum, specChoicePop.storeGoodsInfoId, 0, -1);
                }).showPop(vo.getStoreGoodsInfoId(), vo.getStoreGoodsId(), "", 0, lvSearch);
            }
        });
        recordsDao = new RecordsDao(this);
        stringList = recordsDao.getRecordsList();
        lvSearch.setAdapter(searchAdapter);
    }

    private void setDatas4FlowLayout(List<String> list) {
        flowLayout.removeAllViews();
        if (list == null || list.size() <= 0) return;
        Collections.reverse(list);
        for (int i = 0, count = list.size(); i < count; i++) {
            String vo = list.get(i);
            TextView cb;
            cb = (TextView) inflater.inflate(R.layout.layout_search_tv, null);
            flowLayout.addViewByLayoutParams(cb);

            cb.setId(i);
            cb.setTag(vo);
            cb.setMaxLines(1);
            cb.setText(vo);

            cb.setOnClickListener(v -> {
                edtSearch.setText(cb.getText().toString());
                include_recent.setVisibility(View.GONE);
                lvSearch.setVisibility(View.VISIBLE);
                getGoodsList();
            });
            cb.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_del_recent: {
                YesNoDialog.show(this, "您确定删除历史搜索?", new YesNoDialog.OnClickRateDialog() {
                    @Override
                    public void onClickLeft() {

                    }

                    @Override
                    public void onClickRight() {
                        recordsDao.deleteAllRecords();
                        setDatas4FlowLayout(null);
                    }
                }, false);


            }
            break;
            case R.id.btn_search: {
                String searchStr = edtSearch.getText().toString().trim();
                if (searchStr.isEmpty()) return;
                recordsDao.addRecords(edtSearch.getText().toString().trim());
                include_recent.setVisibility(View.GONE);
                lvSearch.setVisibility(View.VISIBLE);
                getGoodsList();
            }
            break;
            case R.id.ibtn_left: {
                finish();
            }
            break;
            case R.id.ivbtn_clean_text: {
                edtSearch.setText("");
            }
            break;
        }
    }

    private void getGoodsList() {
        String goodsName = edtSearch.getText().toString().trim();
        String la;
        String lo;
        UserInfo userInfo = YuGuoApplication.userInfo;
        if (userInfo.getRecAddId() == null) {
            la = String.valueOf(UserInfo.getInstance().getLatitude());
            lo = String.valueOf(UserInfo.getInstance().getLongitude());
        } else {
            la = String.valueOf(UserInfo.getInstance().getRecLatitude());
            lo = String.valueOf(UserInfo.getInstance().getRecLongitude());

        }
        WaitUI.Show(SearchActivity.this);
        HttpAction.getInstance().getGoodsList(new GetGoodsListResquest(la, lo, goodsName)).subscribe(new BaseObserver<>
                (SearchActivity.this, response -> {
                    WaitUI.Cancel();
                    if (response.getCode() != 200) {
                        Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    list = response.getData().getGoodsCategoryList();
                    if (list.isEmpty()) {
                        lvSearch.setVisibility(View.GONE);
                        tvNotGoods.setVisibility(View.VISIBLE);
                        return;
                    }
                    lvSearch.setVisibility(View.VISIBLE);
                    tvNotGoods.setVisibility(View.GONE);
                    categoryGoodsListBeanList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        categoryGoodsListBeanList.addAll(response.getData().getGoodsCategoryList().get(i).getCategoryGoodsList());
                    }
                    searchAdapter.setStringList(categoryGoodsListBeanList);


                }, (code, message) -> {

                    WaitUI.Cancel();
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
            Toast.makeText(SearchActivity.this, "加入购物车成功！", Toast.LENGTH_SHORT).show();

        })));
    }
}
