package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MyBalanceAdapterEx;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetStoredConfigResquest;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.ToastDialog;

import java.util.List;

public class MyBalanceActivity extends BaseActivity {
    private TextView tvMoneyAll;
//    private ListView myListView;
//    private DataAdapter adapter;
    private ExpandableListView exLv;
    private MyBalanceAdapterEx myBalanceAdapterEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybalance);

        initTitle(TITLE_STYLE_WHITE, "我的余额", true, false);
        btn_right1.setVisibility(View.VISIBLE);
        btn_right1.setText("账单记录");
        btn_right1.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext, BillRecordsActivity.class));
            }
        });
        myBalanceAdapterEx = new MyBalanceAdapterEx(this);
        initView();
    }

    private void initView() {
        exLv = findViewById(R.id.ex_lv);
        exLv.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
        exLv.setAdapter(myBalanceAdapterEx);
//        myListView = findViewById(R.id.myListView);
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_head_mybalance, null);
        tvMoneyAll = (TextView) view.findViewById(R.id.tv_money_all);

        view.findViewById(R.id.tv_money_desc).

                setOnClickListener(new OnSPClickListener() {
                    @Override
                    public void onClickSucc(View v) {
                        ToastDialog.show(mContext, "储值说明", "储值金额暂不支持提现", null);
                    }
                });

//        myListView.addHeaderView(view);
        exLv.addHeaderView(view);
//        adapter = new DataAdapter(mContext, myListView, MyBalanceViewHolder.class);
//        myListView.setOnItemClickListener(new OnItemSPClickListener() {
//            @Override
//            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
//                adapter.choiceHelper.putChoiceNotify(position);
//            }
//        });
        findViewById(R.id.button).setOnClickListener(new OnSPClickListener() {
                    @Override
                    public void onClickSucc(View v) {
                        startActivity(new Intent(mContext, RechargeActivity.class));
                    }
                });
    }


    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStoredConfig();
        getCustomerInfoById();
    }

    private void getStoredConfig() {

        HttpAction.getInstance().getStoredConfig(new GetStoredConfigResquest()).subscribe
                (new BaseObserver<>(mContext, (response -> {

                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        return;
                    }
                    List<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean> list = response
                            .getData().getStoredConfigDtoList();
                    myBalanceAdapterEx.setList(list);
                    for (int i = 0; i < list.size(); i++) {
                        exLv.expandGroup(i);

                    }
//                    for (GetStoredConfigResponse.DataBean.StoredConfigDtoListBean vo : list) {
//                        GetStoredConfigResponse.DataBean.StoredConfigDtoListBean.VipBean vipBean
//                                = new
//                                GetStoredConfigResponse.DataBean.StoredConfigDtoListBean
// .VipBean();
//                        vipBean.setMemberFlag(vo.getMemberFlag());
//                        vipBean.setMemberTime(vo.getMemberTime());
//
//                        vo.setVipBean(vipBean);
//
//                        for (GetStoredConfigResponse.DataBean.StoredConfigDtoListBean
//                                .CouponActivityDtoListBean item : vo.getCouponActivityDtoList()) {
//                            item.setDiscountFlag(vo.getDiscountFlag());
//                        }
//                    }
//                    adapter.setDatas(list);
                })));
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest
                (YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>
                (mContext, response -> {

                    if (200 != response.getCode()) {
                        showToast(response.getMsg());
                        return;
                    }
                    YuGuoApplication.userInfo.setCustomerLevelType(response.getData().getCustomer().getCustomerLevelType());
                    tvMoneyAll.setText(MoneyHelper.getInstance4Fen(response.getData().getCustomer()
                            .getStoredMoney()).change2Yuan().getString());
                }));

    }


}
