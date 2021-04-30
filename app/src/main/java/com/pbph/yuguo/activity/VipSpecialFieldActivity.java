package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.VipSpecialFieldAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.Add2ShoppingCarMsg;
import com.pbph.yuguo.myview.HeaderGridView;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetVipGoodsListRequest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.VipGoodsListResponse;
import com.pbph.yuguo.util.RxBusF;
import com.sobot.chat.utils.ToastUtil;

/**
 * Created by 挡风的纱窗 on 2018/12/19.
 */
public class VipSpecialFieldActivity extends BaseActivity implements View.OnClickListener {

    private HeaderGridView gvMoreGoods;
    private VipSpecialFieldAdapter vipSpecialFieldAdapter;
    private Button btnOpenVip;
    private ImageButton ivbtnBack;
    private TextView tvNotGoods;
    VipGoodsListResponse.DataBean data;

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_special_field);
        hideTitleView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerInfoById();

    }

    private void initData() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_heard_vip_special, null);
        vipSpecialFieldAdapter = new VipSpecialFieldAdapter((goodsListBean, pos) -> {


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
        ivbtnBack = findViewById(R.id.ivbtn_back);
        tvNotGoods = view.findViewById(R.id.tv_vip_not_goods);
        ivbtnBack.setOnClickListener(v -> finish());
        gvMoreGoods = findViewById(R.id.gv_more_goods);
        btnOpenVip = findViewById(R.id.btn_open_vip);
        btnOpenVip.setOnClickListener(this);
        gvMoreGoods.addHeaderView(view);
        gvMoreGoods.setAdapter(vipSpecialFieldAdapter);
        gvMoreGoods.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                int pos = position - 2;
                VipGoodsListResponse.DataBean.GoodsListBean goodsListBean = data.getGoodsList().get(pos);
                Intent intent = new Intent(VipSpecialFieldActivity.this, GoodsInfoActivity.class);
                intent.putExtra("storeGoodsId", goodsListBean.getStoreGoodsId());
                intent.putExtra("storeGoodsInfoId", goodsListBean.getStoreGoodsInfoId());
                startActivity(intent);

            }
        });

    }

    private void getVipGoodsList() {
        UserInfo userInfo = YuGuoApplication.userInfo;
        GetVipGoodsListRequest request;
        if (userInfo.getRecAddId() == null) {
            request = new GetVipGoodsListRequest(String.valueOf(userInfo.getLongitude()), String.valueOf(userInfo.getLatitude()));
        } else {
            request = new GetVipGoodsListRequest(String.valueOf(userInfo.getRecLongitude()), String.valueOf(userInfo
                    .getRecLatitude()));
        }

        WaitUI.Show(VipSpecialFieldActivity.this);
        HttpAction.getInstance().getVipGoodsList(request).subscribe(new BaseObserver<>(VipSpecialFieldActivity.this, response -> {
            int code = response.getCode();
            WaitUI.Cancel();
            data = response.getData();
            if (code != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (data.getGoodsList().isEmpty()) {
                tvNotGoods.setVisibility(View.VISIBLE);
            } else {
                vipSpecialFieldAdapter.setStringList(data.getGoodsList());
                tvNotGoods.setVisibility(View.GONE);
            }

        }, (code, message) -> {
            WaitUI.Cancel();
            tvNotGoods.setVisibility(View.VISIBLE);

        }));
    }

    private void addShoppingCart(int customerId, int storeGoodsId, int storeId, int goodsNum, int storeGoodsInfoId, int
            activeId, int activeType) {
        if (goodsNum <= 0) {
            Toast.makeText(VipSpecialFieldActivity.this, "暂无库存", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId, storeGoodsId, storeId, goodsNum,
                storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(VipSpecialFieldActivity.this, (response -> {

            if (200 != response.getCode()) {
                ToastUtil.showToast(VipSpecialFieldActivity.this, response.getMsg());
                return;
            }
            Toast.makeText(VipSpecialFieldActivity.this,  "加入购物车成功！", Toast.LENGTH_SHORT).show();

            RxBusF.post0(new Add2ShoppingCarMsg());

        })));
    }

    GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
        GetCustomerInfoByIdRequest request = new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId());
        HttpAction.getInstance().getCustomerInfoById(request).subscribe(new BaseObserver<>(mContext, response -> {
            customerBean = response.getData().getCustomer();
            if (customerBean.getCustomerLevelType() == 0) { //会员类型（0普通，1试用，2正式）
                btnOpenVip.setText("立即开通VIP会员");
            } else if (customerBean.getCustomerLevelType() == 1) {
                btnOpenVip.setText("立即开通VIP会员");
            } else {
                btnOpenVip.setText("立即续费");
            }
            getVipGoodsList();
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_vip: {
                startActivity(new Intent(VipSpecialFieldActivity.this, OpenVipActivity.class));
            }
            break;
        }
    }


}
